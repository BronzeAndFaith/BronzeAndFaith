package BronzeAndFaith.Map;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import BronzeAndFaith.Game.BronzeMath;


public class Swamp {

	private List<Point> swampFloor;
	private List<Point> swampWater;
	private List<Point> riverPoints;
	
	public List<Point> getSwampFloor(){return swampFloor;}
	public List<Point> getSwampWater(){return swampWater;}
	public List<Point> getSwamp(){
		List<Point> swamp = new ArrayList<Point>();
		swamp.addAll(swampFloor);
		swamp.addAll(swampWater);
		return swamp;
	}
	
	public Swamp(List<Point> riverPoints){
		swampFloor = new ArrayList<Point>();
		swampWater = new ArrayList<Point>();
		this.riverPoints = riverPoints;
		startSwamp();
		
	}
	
	private static final int ITERATIONS = 200;

	private void startSwamp(){
		int randomInt = BronzeMath.randInt(0, riverPoints.size()-1);
		Point next = riverPoints.get(randomInt);
		
		for(int i = 0; i<ITERATIONS; i++){
			Point randPoint = GameMap.randomNeighbor(next);
			Tile targetType = GameMap.getTileType(randPoint.x, randPoint.y);
			if(		targetType != Tile.WATER_SHALLOW 
					&& targetType != Tile.FLOOR_ROCK_RAW 
					&& targetType != Tile.FLOOR_ROCK_SMOOTH 
					&& targetType != Tile.WATER_SWAMP){
				if(targetType == Tile.WATER_SHALLOW || targetType == Tile.WATER_RIVER){
					GameTile gt = GameTile.GameTileSwampWater(randPoint.x, randPoint.y);
					GameMap.setGameTile(randPoint.x, randPoint.y, gt);
					swampWater.add(randPoint);}
				}
				else
				{
					GameTile gt = GameTile.GameTileSwampFloor(randPoint.x, randPoint.y);
					GameMap.setGameTile(randPoint.x, randPoint.y, gt);
					swampFloor.add(randPoint);
				}
			next = randPoint;
			}
		
		backUpSwamp(next);
		Point randomSwamp = swampFloor.get(BronzeMath.randInt(0,swampFloor.size()-1));
		
		addWater(randomSwamp);
		
		sprinkleFloor(randomSwamp);
		
		sprinkleWater(randomSwamp);
	}


	private void backUpSwamp(Point next){
		ArrayList<Point> points = GameMap.randomExpansion(next,(int)(5*GameMap.sizeFactor),(int)(10*GameMap.sizeFactor));
		for(Point p : points){
			Tile targetType = GameMap.getTileType(p.x, p.y);
			if(targetType != Tile.WATER_DEEP && targetType != Tile.FLOOR_ROCK_RAW && targetType != Tile.FLOOR_ROCK_SMOOTH && targetType !=Tile.WATER_SWAMP){
				if(targetType==Tile.WATER_SHALLOW || targetType==Tile.WATER_RIVER){
					GameTile gt = GameTile.GameTileSwampWater(p.x, p.y);
					GameMap.setGameTile(p.x, p.y, gt);
					swampWater.add(p);}
				else{
					GameTile gt = GameTile.GameTileSwampFloor(p.x, p.y);
					GameMap.setGameTile(p.x, p.y, gt);
					swampFloor.add(p);
					}
				
			}
		}
	}
	
	private void addWater(Point openSwamp){
		ArrayList<Point> water = GameMap.randomExpansion(openSwamp,(int)(3*GameMap.sizeFactor),(int)(8*GameMap.sizeFactor));
		for(Point p : water){
			Tile targetType = GameMap.getTileType(p.x, p.y);
			if(targetType != Tile.WATER_DEEP && targetType != Tile.FLOOR_ROCK_RAW && targetType != Tile.FLOOR_ROCK_SMOOTH){
				GameTile gt = GameTile.GameTileSwampWater(p.x, p.y);
				GameMap.setGameTile(p.x, p.y, gt);
				swampWater.add(p);
				if(swampFloor.contains(p))
					swampFloor.remove(p);
			}
		}
	}
	
	private void sprinkleFloor(Point openSwamp){
		ArrayList<Point> sprinklePoints = GameMap.pointsInRectRange(openSwamp, (int)(20*GameMap.sizeFactor));
		for(Point p : sprinklePoints){
			if(GameMap.getTileType(p.x, p.y)==Tile.WATER_SWAMP && !riverPoints.contains(p)){
				int chance = BronzeMath.randInt(0,2);
				if(chance == 0){
					GameTile gt = GameTile.GameTileSwampFloor(p.x, p.y);
					GameMap.setGameTile(p.x, p.y, gt);
					swampFloor.add(p);
					swampWater.remove(p);
				}
			}
		}
	}
	
	private void sprinkleWater(Point openSwamp){
		ArrayList<Point> sprinklePoints = GameMap.pointsInRectRange(openSwamp, (int)(20*GameMap.sizeFactor));
		for(Point p : sprinklePoints){
			if(GameMap.getTileType(p.x, p.y)==Tile.FLOOR_SWAMP){
				int chance = BronzeMath.randInt(0,6);
				if(chance == 0){
					GameTile gt = GameTile.GameTileSwampWater(p.x, p.y);
					GameMap.setGameTile(p.x, p.y, gt);
					swampWater.add(p);
					swampFloor.remove(p);
				}
			}
		}
	}
}



