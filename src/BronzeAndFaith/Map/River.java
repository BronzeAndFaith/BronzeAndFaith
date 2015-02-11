package BronzeAndFaith.Map;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;

import BronzeAndFaith.Game.HeightPathFinder;
import BronzeAndFaith.Game.PathFinder;


public class River {

	private static PathFinder pf;
	private ArrayList<Point> path;
	public ArrayList<Point> river(){return path;}
	
	public River(Point start, Point end, int maxTries, HashMap<Point, Float>heightMap) {
		pf = new HeightPathFinder(heightMap);
		path = pf.findPath(start, end, maxTries);
	}
	


}
