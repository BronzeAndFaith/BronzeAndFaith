package BronzeAndFaith.Game;
import java.awt.Point;
import java.util.HashMap;


public class HeightPathFinder extends PathFinder{

	private static HashMap<Point,Float> heightMap;
	
	public HeightPathFinder(HashMap<Point,Float> heightMap){
		super();
		HeightPathFinder.heightMap = heightMap;
	}

	@Override
	protected int totalCost(Point from, Point to) {
		if (totalCost.containsKey(from))
			return totalCost.get(from);
		int heightDiff;
		if(heightMap.containsKey(to)&&heightMap.containsKey(from)){
			 heightDiff = (int)(heightMap.get(to)-heightMap.get(from));
			 } else {heightDiff = 255;}
		
		int cost = costToGetTo(from) + heuristicCost(from, to)+heightDiff;
		totalCost.put(from, cost);
		return cost;
	}
	
}
