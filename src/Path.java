import java.awt.Point;
import java.util.ArrayList;

public class Path {
	
	private static PathFinder pf = new PathFinder();
	
	protected ArrayList<Point> path;
	public ArrayList<Point> path(){return path;}
	
	public Path(Point start, Point end, int maxTries){
		path = pf.findPath(start, end, maxTries);
	}
}
