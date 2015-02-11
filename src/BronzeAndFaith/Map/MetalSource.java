package BronzeAndFaith.Map;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import BronzeAndFaith.Content.Material;

public abstract class MetalSource {

	protected List<Point> veinPoints = new ArrayList<Point>();
	public List<Point> getVeinPoints(){
		return veinPoints;
	}
	
	protected Point origin;
	
	protected Material material;
	public Material getMaterial() {return material;}
	
	public MetalSource(Material material, Point origin, int size){
		this.material = material;
		this.origin = origin;
		grow(size);
	}
	
	protected abstract void grow(int size);
	
	protected abstract int calcValue(Point p, int range);
	
}
