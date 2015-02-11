package BronzeAndFaith.Game;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class PathFinder {
	 protected ArrayList<Point> open;
	 protected ArrayList<Point> closed;
	 protected HashMap<Point, Point> parents;
	 protected HashMap<Point,Integer> totalCost;

	 public PathFinder(){
		 this.open = new ArrayList<Point>();
	     this.closed = new ArrayList<Point>();
	     this.parents = new HashMap<Point, Point>();
	     this.totalCost = new HashMap<Point, Integer>();
	 }
	 
	 protected int heuristicCost(Point from, Point to) {
	        return Math.max(Math.abs(from.x - to.x), Math.abs(from.y - to.y));
	 }
	 
	 protected int costToGetTo(Point from) {
	        return parents.get(from) == null ? 0 : (1 + costToGetTo(parents.get(from)));
	 }
	 
	protected int totalCost(Point from, Point to) {
		if (totalCost.containsKey(from))
			return totalCost.get(from);

		int cost = costToGetTo(from) + heuristicCost(from, to);
		totalCost.put(from, cost);
		return cost;
	}
	
    private void reParent(Point child, Point parent){
        parents.put(child, parent);
        totalCost.remove(child);
    }
    
	private Point getClosestPoint(Point end) {
		Point closest = open.get(0);
		for (Point other : open){
		    if (totalCost(other, end) < totalCost(closest, end))
		        closest = other;
		}
		return closest;
	}
	
	private void checkNeighbors(Point end, Point closest) {
		for (Point neighbor : neighbors4(closest)) {
		    if (closed.contains(neighbor) && !neighbor.equals(end)){
		        continue;}
			
		    if (open.contains(neighbor)){

				reParentNeighborIfNecessary(closest, neighbor);}
		    else{
		        reParentNeighbor(closest, neighbor);}
		}
	}
	
	private void reParentNeighbor(Point closest, Point neighbor) {
		reParent(neighbor, closest);
		open.add(neighbor);
	}

	private void reParentNeighborIfNecessary(Point closest, Point neighbor) {
		Point originalParent = parents.get(neighbor);
		double currentCost = costToGetTo(neighbor);
		reParent(neighbor, closest);
		double reparentCost = costToGetTo(neighbor);
		
		if (reparentCost < currentCost)
			open.remove(neighbor);
		else
			reParent(neighbor, originalParent);
	}
	
	public List<Point> neighbors8(Point p){
		List<Point> points = new ArrayList<Point>();
		int x = p.x;
		int y = p.y;
		for (int ox = -1; ox < 2; ox++){
			for (int oy = -1; oy < 2; oy++){
				if (ox == 0 && oy == 0)
					continue;
				
				points.add(new Point(x+ox, y+oy));
			}
		}

		Collections.shuffle(points);
		return points;
	}
	
	public List<Point> neighbors4(Point p){
		List<Point> points = new ArrayList<Point>();
		int x = p.x;
		int y = p.y;
		
		for (int ox = -1; ox < 2; ox++){
			for (int oy = -1; oy < 2; oy++){
				if (ox == 0 && oy == 0 )
					continue;
				if(ox !=0 && oy!=0) //allow only horizontal or vertical points
					continue;
				
				points.add(new Point(x+ox, y+oy));
			}
		}

		Collections.shuffle(points);
		return points;
	}
	
	private ArrayList<Point> createPath(Point start, Point end) {
		ArrayList<Point> path = new ArrayList<Point>();

		while (!end.equals(start)) {
		    path.add(end);
		    end = parents.get(end);
		}

		Collections.reverse(path);
		return path;
	}
	
    public ArrayList<Point> findPath( Point start, Point end, int maxTries) {
        open.clear();
        closed.clear();
        parents.clear();
        totalCost.clear();
    	
        open.add(start);
        for (int tries = 0; tries < maxTries && open.size() > 0; tries++){
            Point closest = getClosestPoint(end);
            
            open.remove(closest);
            closed.add(closest);

            if (closest.equals(end)){
                return createPath(start, closest);}
            else{
                checkNeighbors(end, closest);
            }
            
            if(tries==maxTries-1)
            	return createPath(start,closest);
        }
        return null;
    }

}
