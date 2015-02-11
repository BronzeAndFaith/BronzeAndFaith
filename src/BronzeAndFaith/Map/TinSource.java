package BronzeAndFaith.Map;

import java.awt.Point;
import java.util.ArrayList;

import BronzeAndFaith.Content.Material;

public class TinSource extends MetalSource{

	public TinSource(Point origin, int size) {
		super(Material.METAL_TIN, origin, size);
	}

	@Override
	public void grow(int size) {
		// TODO Auto-generated method stub
		ArrayList<Point> expansion = GameMap.randomExpansion(origin, 3, size);
		for (Point p: expansion){
			int value = calcValue(p,30);
			GameMap.setOre(p.x,p.y,value,Material.METAL_TIN);
			veinPoints.add(p);
		}
	}
	
	protected int calcValue(Point p, int range){
		ResourceDevelopmentChecker rdc = new ResourceDevelopmentChecker(p.x,p.y,20);
		int value = 0;
		value += rdc.getSmoothRockScore(p, range);
		value += rdc.getAllGrassScore(p, range);
		value += rdc.getShallowWaterScore(p, range);
		return value;
	}

}
