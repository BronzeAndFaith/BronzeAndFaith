package BronzeAndFaith.GUI;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class for calculation points, such as rectangle selection or distance emeasurement
 * @author Jeremy
 *
 */
public final class PointManipulation {

	private PointManipulation() {
        throw new RuntimeException("BronzeMath is not instantiable");
    }
	
	
	public static List<Point> selectRectangle(Point p, int width, int height){
		return selectRectangle(p, new Point(p.x+width, p.y+height));
	}
	
	public static List<Point> selectRectangle(Point p, Point t){
		List<Point> selected = new ArrayList<Point>();
		int px = p.x;
		int py = p.y;
		int tx = t.x;
		int ty = t.y;
		
		for(int x = px; x <= tx; x++){
			for(int y = py; y <= ty; y++){
				Point s = new Point(x,y);
				selected.add(s);
			}
		}
		return selected;
	}
	
	//select a line around the selection
	public static List<Point> selectBorder(List<Point> selection){
		List<Point> border = new ArrayList<Point>();
		for(Point p: selection){
			Point[] neighbors = neighbor8(p);
			border.addAll(Arrays.asList(neighbors));
		}
		border.removeAll(selection); //remove the selection itself from the border
		return border;
	}
	
	//expand the selection by 1 tile in eahc direction
	public static List<Point> selectBorderAndSelection(List<Point> selection){
		List<Point> border = new ArrayList<Point>();
		for(Point p: selection){
			Point[] neighbors = neighbor8(p);
			border.addAll(Arrays.asList(neighbors));
		}
		return border;
	}
	
	public static List<Point> expandSelection(List<Point> selection, int steps){
		List<Point> expanse = new ArrayList<Point>(selection);
		for(int i = 0; i<steps; i++){
			expanse.addAll(selectBorderAndSelection(expanse));
			System.out.println("expanding");
		}
		return expanse;
	}
	
	public static Point[] neighbor8(Point self){
		int x = self.x;
		int y = self.y;
		
		Point[] p = 
				{new Point(x-1, y),
				 new Point(x+1,y),
				 new Point(x,y-1),
				 new Point(x,y+1),
				 new Point(x-1,y-1),
				 new Point(x+1,y+1),
				 new Point(x-1,y+1),
				 new Point(x+1,y-1)
				};
		return p;
	}
	
}
