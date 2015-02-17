package BronzeAndFaith.Map;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import BronzeAndFaith.Content.Building;
import BronzeAndFaith.Content.Creature;
import BronzeAndFaith.GUI.PointManipulation;
import BronzeAndFaith.Game.JobSpace;
import BronzeAndFaith.Game.World;


public class Village {

	//The absolute Center of the village. Is used for distance calculation etc.
	private Point villageCenter;
	public Point getVillageCenter(){return villageCenter;}
	private Building mainBuilding;
	public Building getMainBuilding(){
		return mainBuilding;
	}

	private World world;
	private BuildingPlanner bp = BuildingPlanner.getInstance(world);
	
	private List<Creature> inhabitants;
	public List<Creature> getInhabitants(){return inhabitants;}
	public void addInhabitant(Creature creature){inhabitants.add(creature);}
	public void removeInhabitant(Creature creature){
		if(inhabitants.contains(creature)){
			inhabitants.remove(creature);
		}
	}
	
	private List<Point> reservedSpace = new ArrayList<Point>(); // all coordinates that cannot be built over
	public List<Point> getReservedSpace(){
		return reservedSpace;
	}
	
	public int range=20; //standard range, allows for main Building and a couple of small houses
	
	private List<JobSpace> jobs;
	public List<JobSpace> getJobs(){
		return jobs;
	}
	public void addJob(JobSpace job){
		jobs.add(job);
	}
	public void removeJob(JobSpace job){
		jobs.remove(job);
	}
	
	private List<Building> buildings;
	public List<Building> getBuildings(){return buildings;}
	
	
	public Village(Point center, World world){
		this.villageCenter = center;
		inhabitants = new ArrayList<Creature>();
		buildings = new ArrayList<Building>();
		this.world = world;
	}
	
	@Override
    public boolean equals(Object obj) {
        if (obj == this) return true;

        if (obj instanceof Village){
        	Village v = (Village) obj;
        	if (v.getVillageCenter() == this.getVillageCenter()){
        		return true;
        	}
        }
        return false;
    }
	
	public void addBuilding(Building b){
		buildings.add(b);
		calculateReservedSpace();
	}
	
	public void addMainBuilding(){
		Building b = bp.buildAtPoint(getVillageCenter(), "mainBuilding", BuildingMaterial.WOOD_LOG);
		addBuilding(b);
		calculateReservedSpace();
	}
	
	private void calculateReservedSpace(){
		for(Building b:buildings){
			List<Point> buildingWithBorder = PointManipulation.expandSelection(b.getPoints(), 1);
			reservedSpace.addAll(buildingWithBorder);
		}
		
	}
	
}
