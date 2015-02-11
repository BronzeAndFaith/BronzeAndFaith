package BronzeAndFaith.Content;
import java.awt.Point;
import java.util.ArrayList;

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
	
	private ArrayList<Point> points;
	public ArrayList<Point>getPoints(){return points;}
	
	private Creature owner = null;
	public void setOwner(Creature owner){this.owner = owner;}
	public Creature getOwner(){return owner;}
	
	public Building(ArrayList<Point> points){
		this.points = points;
	}
	
	
	
}
