
public class Spawner {

	public int x;
	public int y;
	private int range;
	public int getRange(){return range;}
	public void setRange(int range){this.range = range;}
	
	public Spawner(int x, int y, int range){
		this.x = x;
		this.y = y;
		this.range = range;
	}
	
}
