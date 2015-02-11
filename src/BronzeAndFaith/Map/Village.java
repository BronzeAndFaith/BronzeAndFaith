package BronzeAndFaith.Map;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import BronzeAndFaith.Content.Building;
import BronzeAndFaith.Content.Creature;
import BronzeAndFaith.Game.JobSpace;


public class Village {

	//The absolute Center of the village. Is used for distance calculation etc.
	private Point villageCenter;
	public Point getVillageCenter(){return villageCenter;}

	private List<Creature> inhabitants;
	public List<Creature> getInhabitants(){return inhabitants;}
	public void addInhabitant(Creature creature){inhabitants.add(creature);}
	public void removeInhabitant(Creature creature){
		if(inhabitants.contains(creature)){
			inhabitants.remove(creature);
		}
	}
	
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
	
	
	public Village(Point center){
		this.villageCenter = center;
		inhabitants = new ArrayList<Creature>();
		buildings = new ArrayList<Building>();
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
	
}
