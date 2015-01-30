import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Stream;

/**
 * Sentinel that calculates Resources from surrounding Area and returns several values
 * 
 * @author Jeremy
 *
 */
public class ResourceDevelopmentChecker {



	Point origin;
	public int range;
	
	//values attributed to resource availability
	final static int POINTS_FOREST = 6;
	final static int POINTS_MINING = 8;
	final static int POINTS_FISHING = 10;
	final static int POINTS_FARMING = 1;
	final static int POINTS_SWAMP = 8;
	
	int totalValue;
	int forestryValue;
	int miningValue;
	int clayValue;
	int fishingValue;
	int farmingValue;
	int swampValue;
	
	private LinkedHashMap<Point, Integer> pointScores;
	
	/**Sentinel that calculates the development potential for colonies around coordinate x,y
	 * 
	 * @param world World with generated terrain
	 * @param x Coordinate
	 * @param y Coordinate
	 * @param range Range of the ResourceDevelopmentChecker to check (circular radius)
	 */
	public ResourceDevelopmentChecker(int x, int y, int range){
		origin = new Point(x,y);
		this.range = range;
		pointScores = new LinkedHashMap<Point, Integer>();
	}
	
	public int getRiverScore(Point p, int range){
		int number = GameMap.countTilesCircle(p, Tile.WATER_RIVER, range);
		return number;
	}
	
	public int getSwampScore(Point p, int range){
		int numberFloor = GameMap.countTilesCircle(p, Tile.WATER_SWAMP, range);
		int numberWater = GameMap.countTilesCircle(p, Tile.FLOOR_SWAMP, range);
		return numberFloor+numberWater;
	}
	
	public int getShallowWaterScore(Point p, int range){
		int number = GameMap.countTilesCircle(p, Tile.WATER_SHALLOW, range);
		return number;
	}
	
	
	
	public ArrayList<Point> bestPoints(int checks, int numberSpots){
		System.out.println("Checking for good spots...");
		ArrayList<Point> bestPoints = new ArrayList<Point>();
		for(int i = 0; i < checks; i++){
			totalValue = 0;
			forestryValue = 0;
			miningValue = 0;
			clayValue = 0;
			fishingValue = 0;
			farmingValue = 0;
			swampValue = 0;
			
			origin = GameMap.randomHeightPoint(100,255);
			calcScore();
			System.out.println(totalValue);
			pointScores.put(origin, totalValue);
			
		}

		pointScores = (LinkedHashMap<Point, Integer>) MapUtil.sortByValueDesc(pointScores);
		ArrayList<Point> keys = new ArrayList<Point>(pointScores.keySet());
		Collection<Integer> scores = pointScores.values();
		ArrayList<Integer> values = new ArrayList<Integer>(scores);
		for(int i = 0; i<numberSpots; i++){
			Point p = keys.get(i);
			bestPoints.add(p);
			System.out.println("Good spot: " +p.x + "    "+p.y+" with total score of "+values.get(i));
		}
		return bestPoints;
	}
	

	
	public static HashMap<Point, Integer> sortByValue( HashMap<Point, Integer> map )
    {
      HashMap<Point,Integer> result = new LinkedHashMap<>();
     Stream <Entry<Point,Integer>> st = map.entrySet().stream();

     st.sorted(Comparator.comparing(e -> e.getValue()))
          .forEach(e ->result.put(e.getKey(),e.getValue()));

     return result;
    }
	
	
	
	private void calcScore(){
			calcForestryValue();
			calcMiningValue();
			calcClayValue();
			calcFishingValue();
			calcFarmingValue();
			calcSwampValue();
			calcTotalValue();
	}
	
	private void calcForestryValue(){
		ArrayList<Point> points = GameMap.pointsInCircleRange(origin, range);
		for (Point p: points){
			Tile t = GameMap.getTile(p.x, p.y);
			if(t==Tile.FLOOR_FOREST_LEAF||t==Tile.FLOOR_FOREST_NEEDLE){
				forestryValue+=POINTS_FOREST;
				
			} else if(t==Tile.FLOOR_FOREST_RAW){
				forestryValue+=POINTS_FOREST;
				forestryValue+=1; //Give 1 point more for mixed forest
			}
		}
	}
	
	private void calcMiningValue(){
		ArrayList<Point> points = GameMap.pointsInCircleRange(origin, range);
		for (Point p: points){
			Tile t = GameMap.getTile(p.x, p.y);
			if(t==Tile.FLOOR_ROCK_SMOOTH||t==Tile.FLOOR_ROCK_RAW){
				miningValue+=POINTS_MINING;
				if(t==Tile.FLOOR_ROCK_SMOOTH){
					miningValue+=1;
				}
			}
			//give another extra points if metal is found at location
		}
	}
	
	private void calcClayValue(){
		ArrayList<Point> points = GameMap.pointsInCircleRange(origin, range);
		for (Point p: points){
			//TODO check for clay after clay is implemented
			//give  points if clay is found at location
		}
	}
	
	private void calcFishingValue(){
		ArrayList<Point> points = GameMap.pointsInCircleRange(origin, range);
		for (Point p: points){
			Tile t = GameMap.getTile(p.x, p.y);
			if(t==Tile.WATER_SHALLOW||t==Tile.WATER_RIVER||t==Tile.WATER_SWAMP){
				fishingValue+=POINTS_FISHING;
				if(t==Tile.WATER_RIVER){
					fishingValue+=10; //Rivers are very important
				}
			}
		}
	}
	
	private void calcSwampValue(){
		ArrayList<Point> points = GameMap.pointsInCircleRange(origin, range);
		for (Point p: points){
			Tile t = GameMap.getTile(p.x, p.y);
			if(t==Tile.FLOOR_SWAMP||t==Tile.WATER_SWAMP){
				swampValue += POINTS_SWAMP;
			}
		}
	}
	
	private void calcFarmingValue(){
		ArrayList<Point> points = GameMap.pointsInCircleRange(origin, range);
		for (Point p: points){
			Tile t = GameMap.getTile(p.x, p.y);
			if(t==Tile.FLOOR_GRASS_DRY||t==Tile.FLOOR_GRASS_DEEP){
				farmingValue+=POINTS_FARMING;
				if(t==Tile.FLOOR_GRASS_DEEP){
					farmingValue+=1; //Fresh Grass is more valuable
				}
	
			forestryValue*=farmingValue;
			miningValue*=farmingValue;
			clayValue*=farmingValue;
			fishingValue*=farmingValue;
			swampValue*=(farmingValue*2);
			}
		}
	}
	
	private void calcTotalValue(){
		totalValue = forestryValue+miningValue+clayValue+fishingValue+swampValue;
		if (farmingValue ==0){
			totalValue = 0;
		}
	}
	
	
	
	
	
}
