package BronzeAndFaith.Content;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 * A Building is an entity of Coordinates, which ideally contain Structures.
 * Buildings can have owners
 * 
 * @author Jeremy
 *
 */
public class Building {

	private boolean isComplete;
	public boolean isComplete(){return isComplete;}
	public void setComplete(boolean complete){this.isComplete = complete;}
	
	public int width;
	public int height;
	
	private List<Point> points;
	public List<Point>getPoints(){return points;}
	private List<Structure> structures;
	public List<Structure>getStructure(){return structures;}
	public void setStructureList(List<Structure> structureList){
		this.structures = structureList;
	}
	
	private Creature owner = null;
	public void setOwner(Creature owner){this.owner = owner;}
	public Creature getOwner(){return owner;}
	
	public Building(List<Point> points){
		this.points = points;
	}
	
	@Override
	public String toString(){
		String s = "Building. Size["+structures.size()+"]";
		return s;
		
	}
	
	
	
}
