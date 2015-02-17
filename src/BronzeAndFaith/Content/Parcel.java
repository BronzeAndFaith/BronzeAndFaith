package BronzeAndFaith.Content;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 * A Parcel is the space on which Buildings can be placed and that ensures villages are neither too crowded or spread out.
 * 
 * @author Jeremy
 *
 */
public class Parcel {

	private List<Point> parcelPoints = new ArrayList<Point>();
	private Building building; //each Parcel is allowed to have only 1 Building
	private Point parcelCenter; //Buildings will be placed on the center of a parcel
	
	public Parcel(int x, int y, int size){
		fillParcel(x,y,size);
		calcCenter(x,y,size);
	}
	
	private void fillParcel(int x, int y, int size){
		for(int dx = x; dx<x+size; dx++){
			for(int dy = y; dy<y+size; dy++){
				Point p = new Point(dx,dy);
				parcelPoints.add(p);
			}
		}
	}
	
	private void calcCenter(int x, int y, int size){
		Point p = new Point(x+(int)size/2, y+(int)size/2);
		parcelCenter = p;
	}
	
	public boolean extendsParcel(Point p){
		return parcelPoints.contains(p);
	}
	
	public boolean intersectsParcel(List<Point>points){
		for(Point p:points){
			if (parcelPoints.contains(p))
				return true;
		}
		return false;
	}
	
}
