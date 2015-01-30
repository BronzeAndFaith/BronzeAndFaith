import java.awt.Point;
import java.util.ArrayList;
import java.util.List;


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
	
	private List<Building> buildings;
	public List<Building> getBuildings(){return buildings;}
	
	
	public Village(Point center){
		this.villageCenter = center;
		inhabitants = new ArrayList<Creature>();
		buildings = new ArrayList<Building>();
	}
	
}
