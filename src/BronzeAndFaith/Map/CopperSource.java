package BronzeAndFaith.Map;

import java.awt.Point;
import java.util.ArrayList;

import BronzeAndFaith.Content.Material;

public class CopperSource extends MetalSource{

	public CopperSource(Point origin, int size) {
		super(Material.METAL_COPPER, origin, size);
	}

	@Override
	public void grow(int size) {
		// TODO Auto-generated method stub
		ArrayList<Point> expansion = GameMap.randomExpansion(origin, 3, size);
		for (Point p: expansion){
			int value = calcValue(p,30);
			GameMap.setOre(p.x,p.y,value,Material.METAL_COPPER);
			veinPoints.add(p);
		}
	}
	
	protected int calcValue(Point p, int range){
		ResourceDevelopmentChecker rdc = new ResourceDevelopmentChecker(p.x,p.y,20);
		int value = 0;
		value += rdc.getSmoothRockScore(p, range);
		value += rdc.getRawRockScore(p, range);
		return value;
	}

}
