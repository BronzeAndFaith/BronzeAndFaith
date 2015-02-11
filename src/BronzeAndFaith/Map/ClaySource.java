package BronzeAndFaith.Map;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ClaySource {

	Point origin; // center Point for distance calculation
	
	private List<Point> clayPoints;//points that contain clay
	public List<Point> getClayPoints(){return clayPoints;}
	
	private HashMap<Point, Integer> clayAmount;
	
	private int quality; // determines how much clay can be harvested before digging again
	public int getQuality(){
		return quality;
	}
		
	public void dig(){
		//reset tile
	}
	
	public void harvest(){
		//collect clay
	}
	
	public ClaySource(Point p, int size){
		this.origin = p;
		createPoints(p,size);
	}
	
	private void createPoints(Point p, int size){
		ArrayList<Point> sprinkle = GameMap.getSprinkle(p, size, 2);
		clayPoints.addAll(sprinkle);
		int value = calcValue(p,size);
		for(Point clay:clayPoints){
			clayAmount.put(clay,value);
			System.out.println(clay+":   "+value);
		}
	}
	
	private int calcValue(Point p, int range){
		ResourceDevelopmentChecker rdc = new ResourceDevelopmentChecker(p.x,p.y,20);
		int value = 1+rdc.getRiverScore(p, range);
		value += rdc.getShallowWaterScore(p, range);
		value += rdc.getSwampScore(p, range);
		return value;
	}
	
}
