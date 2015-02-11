import java.awt.Point;
import java.util.List;
import java.util.ArrayList;
public class World {


	
	private int width, height;
	public int width(){return width;}
	public int height(){return height;}
	
	protected GameMap map;
	
	private Creature player;
	
	private List <Creature> creatures;
	public List<Creature> creatures(){return creatures;}
	
	private List <Item> items;
	public List<Item> items(){return items;}
	
	private List<Structure> structures;
	public List<Structure> structures(){return structures;}
	
	private List<Chunk> loadedChunks;
	public List<Chunk> loadedChunks(){return loadedChunks;}
	

	public World(int width, int height, Creature player){
		creatures = new ArrayList<Creature>();
		items = new ArrayList<Item>();
		structures = new ArrayList<Structure>();
		this.width = width;
		this.height = height;
		this.player = player;
	}
	
	public Creature creature(int x, int y){
		for(Creature c : creatures){
			if(c.getX() == x && c.getY() == y)
				return c;
		}
		return null;
	}
	public Structure getStructure(int x, int y){
		for(Structure s : structures){
			if(s.x == x && s.y == y)
				return s;
		}
		return null;
	}
	
	public int creatureImage(int x, int y){
		for(Creature c : creatures){
			if(c.getX() == x && c.getY() == y)
				return c.imageIndex;
		}
		return 0;
	}
	
	public int structureImage(int x, int y){
		for(Structure s : structures){
			if(s.x == x && s.y == y)
				return s.getStructureImage();
		}
		return 0;
	}
	
	
	public Item item(int x, int y){
		for(Item i : items){
			if(i.getX() ==x && i.getY() == y)
				return i;
		}
		return null;
	}
	
	public List<Item> items(int x, int y){
		
		List<Item> returnItems = new ArrayList<Item>();
		
		for(Item i : items){
			if(i.getX() == x && i.getY() == y)
				returnItems.add(i);
		}
		return returnItems;
	}
	
	public int itemImage(int x, int y){
		for(Item i : items){
			if(i.getX() ==x && i.getY() == y)
				return i.itemImage();
		}
		return 0;
	}
	
	public boolean isBlocked(int x, int y){
		GameTile tile = GameMap.getGameTile(x, y);
		Structure struct = getStructure(x,y);
		if(tile.hasWater) return true;
		if(struct != null) if(struct.isBlocking()) return true;
		return false;
	}
	
	public void addCreature(Creature creature){
		creatures.add(creature);
	}
	
	public void addItem(Item item){
		items.add(item);
	}
	
	public void addStructure(Structure structure){
		structures.add(structure);
	}

	
	public void remove(Creature creature){
		creatures.remove(creature);
	}
	
	public void remove(Item item){
		items.remove(item);
	}
	
	public void remove(Structure structure){
		structures.remove(structure);
	}
	
	public void addItem(Item item, int x, int y){
		
		if(!map.isBlocked(x,y)){
			item.setX(x);
			item.setY(y);
			items.add(item);
		}
		else
			System.out.println("floor blocked");
	}
	
	private  void initializeMap(){
		map = new GameMap(Main.MAPWIDTH, Main.MAPHEIGHT, this);
		map.setWorld(this);
		System.out.println("Map created");
	}
	
	public Tile getTile(int x, int y){
		return GameMap.getTile(x, y);
	}
	
	public int getGameTileIndex(int x, int y){
		return GameMap.getGameTileIndex(x, y);
	}
	
	/*
	 * Get a starting location for the player.
	 */
	//TODO create own class as soon as this gets big
	public Point getStartLocation(){
		Point p = GameMap.randomHeightPoint(100, 200);
			if(isBlocked(p.x, p.y))
				getStartLocation();
		return p;
	}
	
	protected  void initialize(){
		initializeMap();
	}
	
	private Chunk getPlayerChunk(){
		return map.getChunk(player.getX(), player.getY());
	}
	
	public void loadChunks(){
		Chunk chunk = getPlayerChunk();
		Point o = chunk.getOrigin();
		int x = (int) o.getX();
		int y = (int) o.getY();
		
		Chunk a = map.getChunk(x-Main.CHUNKSIZE, y-Main.CHUNKSIZE);
		Chunk b = map.getChunk(x-Main.CHUNKSIZE,y);
		Chunk c = map.getChunk(x-Main.CHUNKSIZE, y+Main.CHUNKSIZE);
		Chunk d = map.getChunk(x, y-Main.CHUNKSIZE);
		Chunk e = map.getChunk(x, y+Main.CHUNKSIZE);
		Chunk f = map.getChunk(x+Main.CHUNKSIZE, y-Main.CHUNKSIZE);
		Chunk g = map.getChunk(x+Main.CHUNKSIZE,y);
		Chunk h = map.getChunk(x+Main.CHUNKSIZE, y+Main.CHUNKSIZE);

		loadedChunks.clear();
		
		loadedChunks.add(chunk);
		loadedChunks.add(a);
		loadedChunks.add(b);
		loadedChunks.add(c);
		loadedChunks.add(d);
		loadedChunks.add(e);
		loadedChunks.add(f);
		loadedChunks.add(g);
		loadedChunks.add(h);
	}
	
	public boolean isPointLoaded(Point p){
		for(Chunk c : loadedChunks){
			if (c.inChunk(p)){return true;}
		}
		return false;
	}
	
	private boolean isItemLoaded(Item i){

		Point p = new Point(i.getX(),i.getY());	
		for(Chunk c : loadedChunks){
			if (c.inChunk(p)){return true;}
		}
		return false;
	}
	
	private boolean isCreatureLoaded(Creature cr){

		Point p = new Point(cr.getX(),cr.getY());	
		for(Chunk c : loadedChunks){
			if (c.inChunk(p)){return true;}
		}
		return false;
	}
	
	private boolean isStructureLoaded(Structure s){

		Point p = new Point(s.getX(),s.getY());	
		for(Chunk c : loadedChunks){
			if (c.inChunk(p)){return true;}
		}
		return false;
	}
	

}
