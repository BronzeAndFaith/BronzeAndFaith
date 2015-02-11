package BronzeAndFaith.Map;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import BronzeAndFaith.Game.BronzeMath;


public class ClaySource {

	Point origin; // center Point for distance calculation
	
	private List<Point> clayPoints = new ArrayList<Point>();//points that contain clay
	public List<Point> getClayPoints(){return clayPoints;}
		
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
		System.out.println("Starting ClaySource at " +p.x + ","+p.y);
		ArrayList<Point> sprinkle = GameMap.getSprinkle(p, size, 2);
		clayPoints.addAll(sprinkle);
		
		for(Point clay:clayPoints){
			int quality = calcValue(clay, 20);
			GameMap.setClay(clay.x, clay.y, quality);
			System.out.println("Clay Quality: " + quality);
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
