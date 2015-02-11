package BronzeAndFaith.Map;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;


public class Chunk {
	private List<Point>  tiles;
	public List<Point> getTiles(){return tiles;}
	private Point origin;
	public Point getOrigin(){return origin;}
	private boolean isLoaded;
	public boolean isLoaded(){return isLoaded;}
	public void setLoaded(boolean loaded){this.isLoaded=loaded;}
	
	/**A Chunk collects Point references and has a public origin of its own.
	 * It is fed by the Map class and is supposed to ease loading, as only chunks around the player are loaded
	 *  
	 * @param x  coordinate of origin
	 * @param y  coordinate of origin
	 */
	public Chunk(int x, int y){
		this.tiles = new ArrayList<Point>();
		origin = new Point(x,y);
	}
	
	public void insertPoint(Point p){
		tiles.add(p);
	}
	
	public boolean inChunk(Point p){
		return tiles.contains(p);
	}
	
	@Override
	public boolean equals(Object obj){
		if(obj instanceof Chunk)
		{
		   Chunk otherChunk = (Chunk) obj;
		   if(this.origin == otherChunk.getOrigin()){
			   return true;
		   }
		}
		else
		{
			return false;
		}
		return false;		
	}
	
	@Override
	public String toString(){
		return origin.getX()+" "+origin.getY();
	}
}
