package BronzeAndFaith.Map;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import BronzeAndFaith.Content.Building;
import BronzeAndFaith.Content.Structure;
import BronzeAndFaith.Game.BronzeMath;
import BronzeAndFaith.Game.World;

public class BuildingPlanner {
	
	private static BuildingPlanner instance = null;
	public static BuildingPlanner getInstance(World world) {
		if(instance==null)
			instance = new BuildingPlanner(world);
		return instance;
	}
	private BuildingPlanner(World world){
		this.world = world;
	}

	private List<Building> buildings = new ArrayList<Building>();

	private World world;
	
	//private BuildingPattern pattern;
	private List<Point> patternArea = new ArrayList<Point>();
	
	
	private int offsetX;
	private int offsetY;
	
	//private void checkInterior
	//check room
	//check entrance
	
	//assignPattern
	//buildfrompattern
	//checkifpossibletobuild
	

	/**
	 * createBuilding selects the appropriate Pattern and assigns it to the BuildingPlanner.
	 * It then adds the building structures to the world and sets the tiles as inside.
	 * The building reference itself is stored in the Planner itself.
	 **/
	//TODO create a building manager when appropriate that keeps track of ownership and removal
	public Building buildAtPoint(Point target, String name, BuildingMaterial buildMat){
		BuildingPattern pattern = new BuildingPattern(name, buildMat);
		pattern.translateStructures(target.x, target.y);
		Building b = getBuildingFromPattern(pattern);
		buildings.add(b);
		List<Structure> buildingStructure = b.getStructure();
		world.addBuilding(b);
		world.addStructure(buildingStructure);
		System.out.println(buildingStructure);
		return b;
	}
	
	
	private int counter = 0;
	public void buildInVillage(Village village, String name, BuildingMaterial buildMat){

		BuildingPattern pattern = new BuildingPattern(name, buildMat);
		int range = village.range;
		List<Building> otherBuildings = village.getBuildings();
		ArrayList<Point> rangePoints = GameMap.pointsInCircleRange(village.getVillageCenter(), village.range); //mark all eligible points for building construction
		List<Point> reservedSpace = village.getReservedSpace();
		rangePoints.removeAll(reservedSpace); //remove all points that cannot be used
		List<Point> obstructed = world.selectObstructed(rangePoints);
		rangePoints.removeAll(obstructed); // remove all that are obstructed

		Building b = tryPositionOrOffset(rangePoints, pattern); //may return null if no free space can be found
		
		if(b!=null){
			village.addBuilding(b);
			buildings.add(b);
			List<Structure> buildingStructure = b.getStructure();
			world.addBuilding(b);
			world.addStructure(buildingStructure);
		}
		else {
			System.out.println("No place for building in this village");
		}
		
		//find free space within village area
		
	}
	
	
	private Building tryPositionOrOffset(ArrayList<Point> possiblePositions, BuildingPattern pattern){
		int randInt = BronzeMath.randInt(0, possiblePositions.size()-1);
		Point randomPoint = possiblePositions.get(randInt); // select a random point
		pattern.translateStructures(randomPoint.x, randomPoint.y);
		Building b = getBuildingFromPattern(pattern);
		List<Point> buildingPoints = b.getPoints();
		
		if(possiblePositions.containsAll(buildingPoints)){
			counter = 0;
			return b; //return b only if all coordinates are free
		}
		else if(counter <400) { //allow several recursions
			counter++;
			tryPositionOrOffset(possiblePositions, pattern);//recursive method
			return null;
		}
		else {
			return null; //if no space is found after 100 recursions, return null
		}
		
		
	}
	
	
	//if intersects, change offset and repeat
	
	private boolean buildingsIntersect(Building active, Building b2){
		List<Point> points1 = active.getPoints();
		List<Point> points2 = b2.getPoints();
		for(Point p:points1){
			if (points2.contains(p)){
				return true;
			}
		}
		return false;
	}
	
	private Building getBuildingFromPattern(BuildingPattern pattern){
		List<Point> buildingPoints = new ArrayList<Point>();
		List<Structure> structures = new ArrayList<Structure>();
		for (Structure s: pattern.getStructure()){
			Point p = new Point(s.getX(), s.getY());
			buildingPoints.add(p);
			structures.add(s);
		}
		Building b = new Building(buildingPoints);
		b.setStructureList(structures);
		return b;
	}


}
