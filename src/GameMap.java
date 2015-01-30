import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

public class GameMap {

	

	private World world;
	public void setWorld(World world){this.world = world;}
	
	// upper left point to start drawing
	public static final int offsetX = 0;
	public static final int offsetY = 32; // drawString draws on baseline
	
	//thresholds for tile flag assignment from 0 to 255
	public static final int THRESHOLD_MOUNTAIN = 230;
	public static final int THRESHOLD_HILL = 200;
	public static final int THRESHOLD_DRYGRASS = 150;
	public static final int THRESHOLD_WETGRASS = 100;
	public static final int THRESHOLD_SHALLOWWATER = 80;
	
	//thresholds for forests
	public static final int THRESHOLD_LEAFFOREST = 140;
	public static final int THRESHOLD_NEEDLEFOREST = 140;
	
	//terrain modifiers
	public static final int RIVER_COUNT = 20;
	public static final int RIVER_MAXLENGTH=8000; //multiplied with size of map, iterations for river pathfinding
	public static final int SWAMP_COUNT = 2;

	private static int width;
	private static int height;
	public int getWidth(){return GameMap.width;}
	public int getHeight(){return GameMap.height;}
	
	
	private int simplexOctaves = 10; //higher = more fractal 
	private int cellOctaves =4; //higher = more difference in terrain height, bigger change for several mountains
	private float sizeFactor;  //for values that need to be scaled with map size;
	
	private static List<Point> points;
	private static List<Point> chunkAnchors;
	private static List<Chunk> chunks;
	private static List<River> rivers;
	private static List<Point> riverPoints;
	private static List<Point> swampPoints;
	private static List<Point> clayPoints;
	
	private static List<Point> goodSpots;
	
	private static HashMap<Point, Tile> matrix = new HashMap<Point, Tile>(); //Tilemap
	private static HashMap<Point, Float> heightMap = new HashMap<Point, Float>(); //Heightmap
	private static HashMap<Point, Integer> wetMap = new HashMap<Point, Integer>(); 
	private static HashMap<Point, Float> needleForestMap = new HashMap<Point,Float>();
	private static HashMap<Point, Float> leafForestMap = new HashMap<Point,Float>();
	
	private HashMap<Point, Chunk> chunkMap = new HashMap<Point, Chunk>();


	private float min, max;
	private static Random random = new Random();

	private int[] permutations;
	private int length;

	/**
	 * Contains the game's tiles. Has some public functions to get Tile values, such as height and to edit terrain.
	 * Generates a map during initialization.
	 * 
	 * @param width Width of the map in tiles
	 * @param height Height of the map in tiles
	 * @param world World access for exchange of objects
	 */
	public GameMap(int width, int height, World world) {
		this.world = world;
		points = new ArrayList<Point>();
		chunks = new ArrayList<Chunk>();
		chunkAnchors = new ArrayList<Point>();
		rivers = new ArrayList<River>();
		riverPoints = new ArrayList<Point>();
		swampPoints = new ArrayList<Point>();
		goodSpots = new ArrayList<Point>();
		GameMap.width = width;
		GameMap.height = height;
		//get longest dimension
		if (width >= height) {
			length = width;
		} else {
			length = height;
		}
		sizeFactor = length/256;
		//create permutation table for simplex noise
		newPermutationTable();
		//prepare an image for map output to file
		img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				Point p = new Point(x, y);
				points.add(p);
				//matrix.put(p, Tile.FLOOR);
				heightMap.put(p, 0f);
				wetMap.put(p, 0);
			}
		}
		System.out.println("Generated " +points.size()+ " Points");

		//create terrain
		mapNoise();
		drawTerrain();
		cleanTerrain();
		for(int i = 0; i<RIVER_COUNT;i++) {createRiver();}
		for(int i = 0; i<SWAMP_COUNT;i++) {createSwamp();}
		ResourceDevelopmentChecker rdc = new ResourceDevelopmentChecker(0,0,10);
		goodSpots = rdc.bestPoints(400, 60);
		
		//createForest();
		drawTerrainMap();
		//drawHeightMap();
		markGoodSpots();
		fillChunks();
		saveImage();
		
	}
	


	/**
	 * Get the Tile type of the specified coordinate
	 * 
	 * @param x coordinate
	 * @param y coordinate
	 * @return Returns the Tile type of the coordinate or a Tile.BOUNDS if the coordinates are outside the map
	 */
	public static Tile getTile(int x, int y) {	
		Point p = new Point(x,y);
		if(matrix.containsKey(p)){
			return matrix.get(p);
		} else return Tile.BOUNDS;
	}

	/**
	 * Get blocking status of the target Tile
	 * 
	 * @param x coordinate
	 * @param y coordinate
	 * @return Returns true if Tile type blocks by definition
	 */
	public boolean isBlocked(int x, int y) {
		Tile tile = getTile(x, y);
		return tile.isBlocked();
	}
	
	/**
	 * Fills entire map with specified Tile type
	 * 
	 * @param flag Tile type
	 */
	public void fillMap(Tile flag) {
		for (int w = 0; w < width; w++) {
			for (int h = 0; h < height; h++) {
				setTile(w, h, flag);
			}
		}
	}

	/**
	 * Fills rectangular area with specified Tile type
	 * 
	 * @param x1 starting coordinate
	 * @param y1 starting coordinate
	 * @param x2 target coordinate
	 * @param y2 target coordinate
	 * @param flag Tile type
	 */
	public void drawRect(int x1, int y1, int x2, int y2, Tile flag) {
		if (x1 > x2) {
			int xBig = x1;
			x1 = x2;
			x2 = xBig;
		}
		if (y1 > x2) {
			int yBig = y1;
			y1 = y2;
			y2 = yBig;
		}
		for (int x = x1; x <= x2; x++) {
			for (int y = y1; y <= y2; y++) {
				setTile(x, y, flag);
			}
		}
	}

	/**
	 * Fill only the border of a rectangular area with the specified Tile type
	 * 
	 * @param x1 starting coordinate
	 * @param y1 starting coordinate
	 * @param x2 target coordinate
	 * @param y2 target coordinate
	 * @param c Tile type
	 */
	public void fillBorder(int x1, int y1, int x2, int y2, Tile c) {
		if (x1 > x2) {
			int xBig = x1;
			x1 = x2;
			x2 = xBig;
		}
		if (y1 > y2) {
			int yBig = y1;
			x1 = y2;
			y2 = yBig;
		}
		for (int x = x1; x <= x2; x++) {
			setTile(x, y1, c);
			setTile(x, y2, c);
		}
		for (int y = y1; y <= y2; y++) {
			setTile(x1, y, c);
			setTile(x2, y, c);
		}
	}

	private float getHeight(int x, int y){
		Point p = new Point(x,y);
		float height = heightMap.get(p);
		return height;
	}
	
	private void setHeight(int x, int y, float height) {
		Point p = new Point(x,y);
		heightMap.put(p, height);
	}
	
	
	/**
	 * Change single coordinate to a specific Tile
	 * 
	 * @param x coordinate
	 * @param y coordinate
	 * @param flag Tile the coordinate will be changed to
	 */
	void setTile(int x, int y, Tile tile) {
		if (x > width || y > height || x < 0 || y < 0)
			return;

		Point p = new Point(x, y);
		if (p != null) {
			matrix.put(p, tile);
		}

	}





	/*
	 * TERRAIN GENERATION ALGORHITHMS
	 * 
	 * SIMPLEX NOISE
	 * CELL NOISE
	 * RADIAL BLUR
	 */
	public void mapNoise() {
		System.out.println("Starting Map Noise...");
		int i, x, y;

		float amplitude, frequency, offset;
		float gain = 0.65f, lacunarity = 2.0f;

		min = Float.MAX_VALUE;
		max = -Float.MAX_VALUE;

		for (x = 0; x < width; x++) {
			//double done = (x/width);
			System.out.println(x +"/"+width);
			for (y = 0; y < height; y++) {
				// for each point
				amplitude = 2.0f;
				frequency = 2.0f / (float) width;
				float cellValue = 0.0f;
				float simplexValue = 0.0f;


				// apply cell noise
				for (i = 0; i < cellOctaves; i++) {
					offset = (float) i * 7.0f;
					cellValue += cellNoise((float) x * frequency + offset,
							(float) y * frequency + offset) * amplitude;
					frequency *= lacunarity;
					amplitude *= gain;
				}

				// details with simplex noise
				for (amplitude *= 60.0f; i < simplexOctaves; i++) {
					offset = (float) i * 7.0f;
					simplexValue += simplexNoise(
							(float) x * frequency + offset, (float) y
									* frequency + offset)
							* amplitude;
					frequency *= lacunarity;
					amplitude *= gain;
				}


				float height = (cellValue + simplexValue);
				// float height = radialValue;
				setHeight(x,y,height);
				if (height > max) {
					max = height;
				}
				if (height < min) {
					min = height;
				}

			}
		}
		normalizeHeightMap();

		System.out.println("Making island...");
		min = Float.MAX_VALUE;
		max = -Float.MAX_VALUE;
		for (x = 0; x < width; x++) {
			for (y = 0; y < height; y++) {
				makeIsland(x,y);
			}
		}
		normalizeHeightMap();
		
	}

	private void makeIsland(int x, int y){
		float f = distanceToCenter(x,y);
		float height = getHeight(x,y);
		height*=f*f;
		if (height > max) {
			max = height;
		}
		if (height < min) {
			min = height;
		}
		setHeight(x,y,height);
	}
	
	private float cellNoise(float x, float y) {
		int int_x = (int) x;
		int int_y = (int) y;
		int i, j, tempx, tempy;
	
		float  temp_dis, distance1, distance2;
		float[][][] points = new float[3][3][2];
	
		for (i = 0; i < 3; i++) {
			for (j = 0; j < 3; j++) {
				tempx = int_x + i - 1;
				tempy = int_y + j - 1;
				points[i][j][0] = tempx + random(tempx, tempy, 1);
				points[i][j][1] = tempy + random(tempx, tempy, 2);
			}
		}
		distance1 = distance2 = Float.MAX_VALUE;
	
		for (i = 0; i < 3; i++) {
			for (j = 0; j < 3; j++) {
				temp_dis = euclideanSquared(x, y, points[i][j][0],
						points[i][j][1]);
	
				if (temp_dis < distance1) {
					if (distance1 < distance2) {
						distance2 = distance1;
					}
					distance1 = temp_dis;
				} else if (temp_dis < distance2) {
					distance2 = temp_dis;
				}
			}
		}
		return (distance2 - distance1);
	}

	private float simplexNoise(float x, float y) {

		// noise values

		float disbx, disby, dismx, dismy, distx, disty, noiseb, noisem, noiset, tempdis, skew_value, unskew_value;

		int cornerbx, cornerby, cornermx, cornermy, cornertx, cornerty, gradb, gradm, gradt;

		// gradient table with 8 equal angles
		float[][] gradients = new float[8][2];
		for (int i = 0; i < 8; i++) {
			gradients[i][0] = (float) Math.cos((Math.PI / 4) * (float) i);
			gradients[i][1] = (float) Math.sin((Math.PI / 4) * (float) i);
		}

		// get bottom left corner of simplex in skewed space
		skew_value = (x + y) * general_skew;
		cornerbx = myFloor(x + skew_value);
		cornerby = myFloor(y + skew_value);

		// get distance from bottom corner in normal space
		unskew_value = (float) (cornerbx + cornerby) * general_unskew;
		disbx = x - (float) cornerbx + unskew_value;
		disby = y - (float) cornerby + unskew_value;

		// get middle corner in skewed space
		if (disbx > disby) {
			cornermx = 1 + cornerbx;// lower triangle
			cornermy = cornerby;
		} else {
			cornermx = cornerbx;// upper triangle
			cornermy = 1 + cornerby;
		}

		// get top corner in skewed space
		cornertx = 1 + cornerbx;
		cornerty = 1 + cornerby;

		// get distance from two other corners
		dismx = disbx - (float) (cornermx - cornerbx) + general_unskew;
		dismy = disby - (float) (cornermy - cornerby) + general_unskew;

		distx = disbx - 1.0f + general_unskew + general_unskew;
		disty = disby - 1.0f + general_unskew + general_unskew;

		// get gradient indices
		gradb = permutations[(cornerbx + permutations[cornerby & length - 1])
				& length - 1] & 7;
		gradm = permutations[(cornermx + permutations[cornermy & length - 1])
				& length - 1] & 7;
		gradt = permutations[(cornertx + permutations[cornerty & length - 1])
				& length - 1] & 7;

		// get noise form each corner using attenuation function
		tempdis = 0.5f - disbx * disbx - disby * disby;
		if (tempdis < 0.0f)
			noiseb = 0.0f;
		else
			noiseb = (float) Math.pow(tempdis, 4.0f)
					* dotproduct(gradients[gradb], disbx, disby);

		// middle corner
		tempdis = 0.5f - dismx * dismx - dismy * dismy;
		if (tempdis < 0.0f)
			noisem = 0.0f;
		else
			noisem = (float) Math.pow(tempdis, 4.0f)
					* dotproduct(gradients[gradm], dismx, dismy);

		// top corner
		tempdis = 0.5f - distx * distx - disty * disty;
		if (tempdis < 0.0f)
			noiset = 0.0f;
		else
			noiset = (float) Math.pow(tempdis, 4.0f)
					* dotproduct(gradients[gradt], distx, disty);

		return (noiseb + noisem + noiset);

	}


	float smoothedNoise(float x, float y) {
		int int_x = (int) x;
		int int_y = (int) y;

		float rem_x = x - (float) int_x, rem_y = y - (float) int_y, v1, v2, v3, v4, t1, t2;

		v1 = randInt(int_x, int_y);
		v2 = randInt(int_x + 1, int_y);
		v3 = randInt(int_x, int_y + 1);
		v4 = randInt(int_x + 1, int_y + 1);

		t1 = linear(v1, v2, rem_x);
		t2 = linear(v3, v4, rem_x);
		return (linear(t1, t2, rem_y));

	}

	private void normalizeHeightMap() {
		
		float dif = max - min;
		System.out.println("Normalizing with values MAX: " + max + ",  MIN: " + min + ", DIFF: " + dif);
		
		for (java.util.Map.Entry<Point, Float> entry : heightMap.entrySet()) {
			Point p = entry.getKey();
			float height = heightMap.get(p);
			height = ((height - min) / dif) * 255;
			
			heightMap.put(p, height);
		}
	}
	
	private void cleanTerrain(){
		//remove terrain sprinkles
		//apply only after terrain generation but before landmark (forest, river) generation
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				Point p = new Point(x,y);
				Point[] neighbors = neighbor8(p);
				int count = 0;
				Tile ownTile = getTile((int)p.x,(int)p.y);
				Tile otherTile = ownTile;
				for (Point point : neighbors){
					Tile t = getTile((int)point.x,(int)point.y);
					if(ownTile == t){
						count++;
					} else {
						otherTile = t;
					}
				}
				if(count <3){
					setTile(x,y,otherTile);
				}
				
			}
		}
	}
	
	private void createRiver(){
		System.out.println("Creating River...");
		Point start = randomHeightPoint(150,250);
		ArrayList<Tile> acceptableTiles = new ArrayList<Tile>();
		acceptableTiles.add(Tile.WATER_DEEP);
		acceptableTiles.add(Tile.WATER_SHALLOW);
		acceptableTiles.add(Tile.WATER_RIVER);
		
		Point end = nearestWater(start);
		River r = new River(start,end,RIVER_MAXLENGTH*length, heightMap);
		ArrayList<Point> riverP = r.river();
		for(Point p : riverP){
			Tile t = matrix.get(p);
			if(t != Tile.WATER_DEEP && t!= Tile.WATER_SHALLOW)
				matrix.put(p, Tile.WATER_RIVER);
				riverPoints.add(p);
		}
		rivers.add(r);
	}
	
	private void createClay(){
		System.out.println("Creating clay source");
		Point clay = randomHeightPoint(150,250);
		ArrayList<Tile> acceptableTiles = new ArrayList<Tile>();
		acceptableTiles.add(Tile.FLOOR_SWAMP);
		acceptableTiles.add(Tile.FLOOR_FOREST_LEAF);
		acceptableTiles.add(Tile.FLOOR_FOREST_NEEDLE);
		acceptableTiles.add(Tile.FLOOR_FOREST_RAW);
		acceptableTiles.add(Tile.FLOOR_GRASS_DEEP);
		acceptableTiles.add(Tile.FLOOR_GRASS_DRY);
		
		
		
	}
	
	private void sprinkleTileRect(Point p, Tile t, int range, int density){
		ArrayList<Point> points = pointsInRectRange(p, range);
		for(Point target : points){
		int chance = randInt(0,(int)(density));
			if(chance == 0){
				setTile(target.x, target.y, t);
			}

		}
	}
	
	/**
	 * Selects random points and returns them for further usage
	 * 
	 * @param center Center of the sprinkle
	 * @param range Radius of the sprinkle
	 * @param density Density of the sprinkle
	 * @return Returns an ArrayList of selected points 
	 */
	public static ArrayList<Point> getSprinkle(Point center, int range, int density){
		ArrayList<Point> points = pointsInCircleRange(center, range);
		for(Point target : points){
		int chance = randInt(0,(int)(density));
			if(chance == 0){
				points.add(target);
			}

		}
		return points;
	}
	
	
	private Point nearestWater(Point p){
		ArrayList<Point> points = new ArrayList<Point>();
		
		int i = 1;
		while (i>=1){
			points = pointsInRectRange(p,i);
			points.remove(p);
			for(Point w:points){
				Tile t = getTile(w.x, w.y);
				if(t==Tile.WATER_SHALLOW || t==Tile.WATER_DEEP){i = -1; return w;}
			}
			i++;
		}
		return p;
	}
	
	private Point nearestTile(Point p, ArrayList<Tile> type){
		ArrayList<Point> points = new ArrayList<Point>();
		
		int i = 1;
		while (i>=1){
			points = pointsInRectRange(p,i);
			points.remove(p);
			for(Point w:points){
				Tile t = getTile(w.x, w.y);
				if(type.contains(t)){i = -1; return w;}
			}
			i++;
		}
		return p;
	}
	
	public static int countTilesRect(Point center, Tile type, int range){
		ArrayList<Point> points = pointsInRectRange(center, range);
		ArrayList<Point> typePoints = new ArrayList<Point>();
		for(Point p: points){
			Tile t = getTile(p.x,p.y);
			if(t == type){
				typePoints.add(p);
			}
		}
		return typePoints.size();
	}
	
	public static int countTilesCircle(Point center, Tile type, int range){
		ArrayList<Point> points = pointsInCircleRange(center, range);
		ArrayList<Point> typePoints = new ArrayList<Point>();
		for(Point p: points){
			Tile t = getTile(p.x,p.y);
			if(t == type){
				typePoints.add(p);
			}
		}
		return typePoints.size();
	}
	
	/*
	 * Methods for outputting an image
	 * */
	BufferedImage img;

	public void drawHeightMap() {
		for(Point p:points){
			int x = p.x;
			int y = p.y;
			float height= getHeight(x,y);
			int r, g, b;

			r = (int) height;
			g = (int) height;
			b = (int) height;

			int col = (r << 16) | (g << 8) | b;
			img.setRGB(x, y, col);
		}
	}

	public void drawTerrainMap() {
		for(Point p:points){
			int x = p.x;
			int y = p.y;
			Tile flag= getTile(x,y);
			Color c = flag.color();
			img.setRGB(x,y,c.getRGB());
		}
	}

	public void markGoodSpots(){
		for(Point p:goodSpots){
			int x = p.x;
			int y = p.y;
			Color c = new Color(255,0,0);
			img.setRGB(x, y, c.getRGB());
		}
	}
	
	public void saveImage() {

		File f = new File("MapPic.jpg");

		try {
			ImageIO.write(img, "PNG", f);
			System.out.println("saved as "+f);
		} catch (IOException e) {
			System.out.println("Could not save image");
			e.printStackTrace();
		}
	}

	private static int myFloor(double x) {
		int xi = (int) x;
		return x < xi ? xi - 1 : xi;
	}

	private float distanceToCenter(float x, float y){
		int centerX = width / 2;
		int centerY = height / 2;
		float distanceX = (centerX -x)*(centerX-x);
		float distanceY = (centerY -y)*(centerY-y);
		float distanceToCenter = (float) Math.sqrt(distanceX+distanceY);
		distanceToCenter /= length;
		return 1-distanceToCenter;
	}
	
	static float distanceToPoint(Point center, float x, float y){
		float distanceX = (center.x -x)*(center.x-x);
		float distanceY = (center.y -y)*(center.y-y);
		float distanceToCenter = (float) Math.sqrt(distanceX+distanceY);
		return distanceToCenter;
	}
	
	static ArrayList<Point> pointsInRectRange(Point p, int range){
		ArrayList<Point> pointsInRectRange = new ArrayList<Point>();;
		for(int x = p.x-range; x < p.x+range; x++){
			for(int y = p.y-range; y < p.y+range; y++){
				if(x<0 || y<0||x>width||y>height)
					break;
				Point inRange = new Point(x,y);
				pointsInRectRange.add(inRange);
			}
		}
		return pointsInRectRange;
	}
	
	static ArrayList<Point> pointsInCircleRange(Point p, int range){
		ArrayList<Point> pointsInRoundRange = new ArrayList<Point>();;
		for(int x = p.x-range; x < p.x+range; x++){
			for(int y = p.y-range; y < p.y+range; y++){
				if(x<0 || y<0||x>width||y>height)
					break;
				Point inRange = new Point(x,y);
				float rangeFloat = distanceToPoint(p,x,y);
				
				if(rangeFloat<=range){
					pointsInRoundRange.add(inRange);
				}
			}
		}
		return pointsInRoundRange;
	}
	
	/**
	 * Random expansion starting from Point 
	 * @param start Point to start the expansion from
	 * @param size How big are expansion blobs
	 * @param iterations How often expansion is done
	 * @return Returns an Array of Points that have been touched by the expansion
	 */
	private ArrayList<Point> randomExpansion(Point start, int size, int iterations){
		ArrayList<Point> randomExpansion = new ArrayList<Point>();
		ArrayList<Point> currentSplash;
		Point p = start;
		
		for(int i = 0; i<iterations; i++){
			currentSplash = pointsInCircleRange(p, randInt(1,size));
			randomExpansion.addAll(currentSplash);
			int randomInt = randInt(0,currentSplash.size()-1);
			p = currentSplash.get(randomInt);
		}

		return randomExpansion;
	}
	
	
	
	

	//assign flags to terrain based on heightmap
	private void drawTerrain() {
		System.out.println("Assigning Tiles ...");
		for(Point p:points){
			int x = p.x;
			int y = p.y;
			float height= getHeight(x,y);
			Tile flag;
			if (height > THRESHOLD_MOUNTAIN) {
				flag = Tile.FLOOR_ROCK_RAW;
			} else if (height > THRESHOLD_HILL) {
				flag = Tile.FLOOR_ROCK_SMOOTH;

			} else if (height > THRESHOLD_DRYGRASS) {
				flag = Tile.FLOOR_GRASS_DRY;

			} else if (height > THRESHOLD_WETGRASS) {
				flag = Tile.FLOOR_GRASS_DEEP;

			} else if (height > THRESHOLD_SHALLOWWATER) {
				flag = Tile.WATER_SHALLOW;

			} else {
				flag = Tile.WATER_DEEP;
			}
			matrix.put(p, flag);
		}
	}
	
	private void createNeedleForest(){
		System.out.println("Creating Needle Forest...");

		int i,x,y;
		
		newPermutationTable();
		float amplitude, frequency, offset;
		float gain = 0.75f, lacunarity = 2.2f;
		
		float forestMin = Float.MAX_VALUE;
		float forestMax = Float.MIN_VALUE;
		for (x = 0; x < width; x++) {
			for (y = 0; y < height; y++) {

				amplitude = 2.1f;
				frequency = 4.2f / (float) width;
				float forestValue = 0.0f;
				Point p = new Point(x, y);
				float height = heightMap.get(p);
				if (height > 100 && height < 200) {
					for (i = 0; i < 12; i++) {
						offset = (float) i * 6.0f;

						forestValue += simplexNoise(x * frequency + offset, y
								* frequency + offset)
								* amplitude;
						frequency *= lacunarity;
						amplitude *= gain;
					}
				}
				needleForestMap.put(p, forestValue*northModifier(y));

				if (forestValue > forestMax)
					forestMax = forestValue;
				if (forestValue < forestMin)
					forestMin = forestValue;

			}
		}
		
		
			float dif = forestMax - forestMin;
			for (Point p : points) {
				float forest = needleForestMap.get(p);
				if(forest != 0f)forest = ((forest - forestMin) / dif) * 255;
				needleForestMap.put(p, forest);
			}
		random = null;
	}
	
	private void createLeafForest(){
		System.out.println("Creating Leaf Forest...");
		int i,x,y;
		
		newPermutationTable();
		float amplitude, frequency, offset;
		float gain = 0.75f, lacunarity = 1.6f;
		
		float forestMin = Float.MAX_VALUE;
		float forestMax = Float.MIN_VALUE;
		for (x = 0; x < width; x++) {
			for (y = 0; y < height; y++) {

				amplitude = 2.1f;
				frequency = 4.2f / (float) width;
				float forestValue = 0.0f;
				Point p = new Point(x, y);
				float height = heightMap.get(p);
				if (height > 100 && height < 200) {
					for (i = 0; i < 12; i++) {
						offset = (float) i * 6.0f;

						forestValue += simplexNoise(x * frequency + offset, y
								* frequency + offset)
								* amplitude;
						frequency *= lacunarity;
						amplitude *= gain;
					}
				}
				leafForestMap.put(p, forestValue);

				if (forestValue > forestMax)
					forestMax = forestValue;
				if (forestValue < forestMin)
					forestMin = forestValue;

			}
		}
		
		
			float dif = forestMax - forestMin;
			for (Point p : points) {
				float forest = leafForestMap.get(p);
				if(forest != 0f)forest = ((forest - forestMin) / dif) * 255;
				leafForestMap.put(p, forest);
			}
		random = null;
	}
	
	private void assignForestTiles(){
		//assign leaf, needle or mixed forest depending by mixing both forest maps
		for (Point p : points) {
			float needleForest = needleForestMap.get(p);
			float leafForest = leafForestMap.get(p);
			if(needleForest >= THRESHOLD_NEEDLEFOREST && leafForest >= THRESHOLD_LEAFFOREST && matrix.get(p)!=Tile.WATER_SHALLOW){
				matrix.put(p, Tile.FLOOR_FOREST_RAW);
			} else if (needleForest >= THRESHOLD_NEEDLEFOREST && leafForest <THRESHOLD_LEAFFOREST && matrix.get(p)!=Tile.WATER_SHALLOW){
				matrix.put(p, Tile.FLOOR_FOREST_NEEDLE);
			} else if (needleForest <THRESHOLD_NEEDLEFOREST && leafForest >= THRESHOLD_LEAFFOREST && matrix.get(p)!=Tile.WATER_SHALLOW){
				matrix.put(p, Tile.FLOOR_FOREST_LEAF);
			} else{
			}
		}
	}
	
	
	private void createForest(){
		System.out.println("Starting forest creation");
		createNeedleForest();
		createLeafForest();
		assignForestTiles();
	}
	
	//Find the lowest neighbor among those surrounding the parameter point horizontally or vertically
	private Point lowestNeighbor(Point self){
		int x = self.x;
		int y = self.y;
		
		//take no diagonal points
		Point lowPoint = self;
		float lowHeight = heightMap.get(self);
		Point[] p = 
				{new Point(x-1, y),
				 new Point(x+1,y),
				 new Point(x,y-1),
				 new Point(x,y+1),
				};
		for (Point pH : p){
			float h;
			if(heightMap.containsKey(pH))
			{ h = heightMap.get(pH);} else {
				h = 255;
			}
			if(h<lowHeight){
				lowHeight = h;
				lowPoint = pH;
			}
		}
		return lowPoint;
	}


	public Point randomNeighbor(Point self){
		int x = self.x;
		int y = self.y;
		
		Point[] p = 
				{new Point(x-1, y),
				 new Point(x+1,y),
				 new Point(x,y-1),
				 new Point(x,y+1),
				};
		return p[randInt(0,3)];
	}
	
	public Point randomNeighbor8(Point self){
		Point[] p = neighbor8(self);
		return p[randInt(0,p.length-1)];
	}

	//return an array of the neighbor Points
	public Point[] neighbor8(Point self){
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
	
	//Reinitialize the permutation table to increase randomness for Noise filters
	private void newPermutationTable(){
		random = new Random();
		//fill the permutation table
		permutations = new int[length];
		for (int i = 0; i < length; i++) {
			permutations[i] = i;
		}
		for (int i = 0; i < length; i++) {
			int j = (int) random.nextInt(length);
			int k = permutations[i];
			permutations[i] = permutations[j];
			permutations[j] = k;
		}
	}
	
	float random(int x, int y, int z) {
		int r1 = permutations[(x + permutations[(y + permutations[z & length
				- 1])
				& length - 1])
				& length - 1];
	
		return ((float) r1 / length);
	}

	static Point randomPoint(){
		random = new Random();
		Point p = new Point(randInt(0,width-1), randInt(0,height-1));
		return p;
	}
	
	private Point randomPointType(Tile t){
		Point p = randomPoint();
		if(getTile(p.x,p.y)!=t)
			return randomPointType(t);
		else
			return p;
	}
	
	/**Get a Point on a specified height range from 0 to 255.
	 * The smaller the range, the longer this method will run.
	 * Never use minHeight == maxHeight
	 * 
	 * @param minHeight Minimum Height
	 * @param maxHeight Maximum Height
	 * @return Returns a randomly selected Point that fits into the range
	 */
	public static Point randomHeightPoint(int minHeight, int maxHeight){
		//validate user input
		if(minHeight<0) minHeight=0;
		if(maxHeight>255) maxHeight = 255;
		if(minHeight>maxHeight){
			int height = maxHeight;
			maxHeight = minHeight;
			minHeight = height;
		}
		
		Point p = new Point(randInt(0,width-1), randInt(0,height-1));
		float height = heightMap.get(p);
		if(height<minHeight || height>maxHeight){
			return randomHeightPoint(minHeight, maxHeight);
		}
		else return p;
		
	}

	public float cosine(float x1, float x2, float a) {
		double temp;
		temp = (1.0f - Math.cos(a * (float) Math.PI)) / 2.0f;
		return (float) (x1 * (1.0f - temp) + x2 * temp);
	}

	float linear(float x1, float x2, float a) {
		return (x1 * (1 - a) + x2 * a);
	}

	private static float general_skew = (((float) Math.sqrt(3.0f) - 1.0f) * 0.5f);

	private static float general_unskew = (3.0f - (float) Math.sqrt(3.0f)) / 6.0f;

	public float dotproduct(float grad[], float x, float y) {
		return (grad[0] * grad[1] * y);
	}

	float euclideanSquared(float x1, float y1, float x2, float y2) {
		float dif_x = x1 - x2, dif_y = y1 - y2;
	
		return (dif_x * dif_x + dif_y * dif_y);
	}

	public static int randInt(int getMin, int getMax) {
		int min, max;
		if (getMin > getMax) {
			max = getMin;
			min = getMax;
		} else {
			min = getMin;
			max = getMax;
		}
		Random rand = new Random();
		int randomNum = rand.nextInt(max - min + 1) + min;
		rand = null;
		return randomNum;
	}
	
	//returns value from 0 to 1 depending on the y value(north/south), 1 is absolute North, 0 is absolute South
	float northModifier(float heightY){
		
		if(heightY<0) return 0;
		else if(heightY>=height) return 1;
		return 1f-(heightY/height);
	}

	private void fillChunks(){
		System.out.println("Filling Chunks...");
		for(int x = 0; x < width/Main.CHUNKSIZE;x++){
			for(int y = 0; y < height/Main.CHUNKSIZE;y++){
				Chunk c = new Chunk(x*Main.CHUNKSIZE, y*Main.CHUNKSIZE);
				for(int cx = 0; cx<Main.CHUNKSIZE;cx++){
					for(int cy = 0; cy<Main.CHUNKSIZE;cy++){
						Point p = new Point(cx+Main.CHUNKSIZE, cy+Main.CHUNKSIZE); 
						c.insertPoint(p);
					}
				}
				Point o = new Point(x*Main.CHUNKSIZE,y*Main.CHUNKSIZE);
				chunkMap.put(o, c); //use the origin as key
				chunkAnchors.add(o);
				chunks.add(c);
			}
		}
	} 
	
	public Chunk getChunk(int xOrigin, int yOrigin){
		if(xOrigin%Main.CHUNKSIZE==0 && yOrigin%Main.CHUNKSIZE==0){
			Point key = new Point(xOrigin,yOrigin);
			if(chunkMap.containsKey(key)){return chunkMap.get(key);	}
			else return new Chunk(-1,-1);
		} else return new Chunk(-1,-1);
	}
	
	public Chunk playerChunk(int x, int y){
		Point p = new Point(x,y);
		for(Chunk c : chunks){
			if (c.inChunk(p)){return c;}
		}
		return new Chunk(-1,-1);
	}
	
	
	private void createSwamp(){
		System.out.println("Swamp");
		int it = 200;
		int randomInt= randInt(0, riverPoints.size()-1);
		ArrayList<Point> thisSwampFloor = new ArrayList<Point>();
		ArrayList<Point> thisSwampWater = new ArrayList<Point>();

		Point next = riverPoints.get(randomInt);
		//run a swamp from River
		for(int i = 0; i<it; i++){
			Point randPoint = randomNeighbor(next);
			Tile targetType = getTile(randPoint.x, randPoint.y);
			if(targetType != Tile.WATER_DEEP && targetType != Tile.FLOOR_ROCK_RAW && targetType != Tile.FLOOR_ROCK_SMOOTH && targetType !=Tile.WATER_SWAMP){
				if(targetType==Tile.WATER_SHALLOW || targetType==Tile.WATER_RIVER){
					setTile(randPoint.x, randPoint.y, Tile.WATER_SWAMP);
					thisSwampWater.add(randPoint);}
				else
				{
					setTile(randPoint.x, randPoint.y, Tile.FLOOR_SWAMP);
					thisSwampFloor.add(randPoint);}
				

			}
			
			next = randPoint;
		}
		
		//make the swamp back up and expand
		ArrayList<Point> points = randomExpansion(next,(int)(5*sizeFactor),(int)(10*sizeFactor));
		for(Point p : points){
			Tile targetType = getTile(p.x, p.y);
			if(targetType != Tile.WATER_DEEP && targetType != Tile.FLOOR_ROCK_RAW && targetType != Tile.FLOOR_ROCK_SMOOTH && targetType !=Tile.WATER_SWAMP){
				if(targetType==Tile.WATER_SHALLOW || targetType==Tile.WATER_RIVER){
					setTile(p.x, p.y, Tile.WATER_SWAMP);
					thisSwampWater.add(p);}
				else{
					setTile(p.x, p.y, Tile.FLOOR_SWAMP);
					thisSwampFloor.add(p);}
				
			}
		}
		
		//add swampwater
		//Point openSwamp = randomPointType(Tile.FLOOR_SWAMP);
		Point openSwamp = thisSwampFloor.get(randInt(0,thisSwampFloor.size()-1));
		ArrayList<Point> swampWater = randomExpansion(openSwamp,(int)(3*sizeFactor),(int)(8*sizeFactor));
		for(Point p : swampWater){
			Tile targetType = getTile(p.x, p.y);
			if(targetType != Tile.WATER_DEEP && targetType != Tile.FLOOR_ROCK_RAW && targetType != Tile.FLOOR_ROCK_SMOOTH){
				setTile(p.x, p.y, Tile.WATER_SWAMP);
				thisSwampWater.add(p);
				if(thisSwampFloor.contains(p))
					thisSwampFloor.remove(p);
			}
		}

		//sprinkle swamp floor on the water, high density
		ArrayList<Point> sprinklePoints = pointsInRectRange(openSwamp, (int)(20*sizeFactor));
		for(Point target : sprinklePoints){
			if(getTile(target.x, target.y)==Tile.WATER_SWAMP && !riverPoints.contains(target)){
				int chance = randInt(0,2);
				if(chance == 0){
					setTile(target.x, target.y, Tile.FLOOR_SWAMP);
					thisSwampFloor.add(target);
					thisSwampWater.remove(target);
				}
			}
		}
		
		//inverse sprinkle to put water on swamp floor, lower density
		ArrayList<Point> sprinklePoints2 = pointsInRectRange(openSwamp, (int)(20*sizeFactor));
		for(Point target : sprinklePoints2){
			if(getTile(target.x, target.y)==Tile.FLOOR_SWAMP){
				int chance = randInt(0,6);
				if(chance == 0){
					setTile(target.x, target.y, Tile.WATER_SWAMP);
					thisSwampFloor.remove(target);
					thisSwampWater.add(target);
				}
			}
		}
		
		swampPoints.addAll(thisSwampFloor);
		swampPoints.addAll(thisSwampWater);
	}


}
