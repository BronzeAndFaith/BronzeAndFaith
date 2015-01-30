import java.awt.Point;
import java.util.AbstractList;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;



public class IndexPointMap{

	
	private List<Point> points;
	private List<Integer> ints;
	
	public IndexPointMap(){
		points = new ArrayList<Point>();
		ints = new ArrayList<Integer>();
	}
	
	public void set(int x, int y, int value){
		Point p = new Point(x,y);
		points.add(p);
		ints.add(value);
	}
	public void add(Point p, int value){
		if(points.contains(p)){
			changeValue(p,value);
		} else {
			points.add(p);
			ints.add(value);
		}	
	}
	
	public void changeValue(Point p, int value){
		int index = points.indexOf(p);
		ints.set(index, value);
	}
	
	public int get(Point p){
		int index = points.indexOf(p);
		return (int)ints.get(index);
	}
	
	public int get(int x, int y){
		Point p = new Point(x,y);
		int index = points.indexOf(p);
		return (int)ints.get(index);
	}
	
	public Point get(int index){
		Point p = points.get(index);
		return p;
	}
	
	public int size (){
		return points.size();
	}
	
	public void remove(Point p){
		int index = points.indexOf(p);
		points.remove(index);
		ints.remove(index);
	}
	
	public boolean contains(Point p){
		if (points.contains(p))return true;
		return false;
	}
	
}
