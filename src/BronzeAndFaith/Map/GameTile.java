package BronzeAndFaith.Map;
import java.awt.Point;

import BronzeAndFaith.Game.BronzeMath;


/**
 * Provide a clean access to a coordinate's important values within one class
 * @author Jeremy
 *
 */

public class GameTile extends Point{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6321813884899444070L;
	
	private Season season = Season.SPRING; // the Season file to choose from
	public Season getSeason(){
		return season;
	}
	private int imageIndex = 0; //all terrain graphics will be in one file
	public int getImageIndex(){
		return imageIndex;
	}
		
	public boolean isBlocked = false;
	public boolean hasWater = false;
	public boolean hasClay = false;
	public boolean isInside = false;
	
	private int clayAmount = 0;
	public int getClayAmount(){
		return clayAmount;
	}
	public void setClayAmount(int amount){
		clayAmount = amount;
		if(clayAmount<0)
			clayAmount = 0;
	}
	
	private static Tile tileType;
	public Tile getTileType() {
		return tileType;
	}
	
	
	private GameTile(int x, int y){
		super(x,y);
	}
	

	private GameTile(int x, int y, int imageIndex, boolean isBlocked, Season season){
		super(x,y);
		this.imageIndex = imageIndex;
		this.isBlocked = isBlocked;
		this.season = season;
	}
	
	public static GameTile GameTileDeepGrass(int x, int y){
		int grassIndex = BronzeMath.randInt(170, 179);
		GameTile tile = new GameTile(x,y,grassIndex,false,Season.SPRING);
		tileType = Tile.FLOOR_GRASS_DEEP;
		return tile;
	}
	
	public static GameTile GameTileDryGrass(int x, int y){
		int grassIndex = BronzeMath.randInt(180, 189);
		GameTile tile = new GameTile(x,y,grassIndex,false,Season.SPRING);
		tileType = Tile.FLOOR_GRASS_DRY;
		return tile;
	}
	
	public static GameTile GameTileSwampFloor(int x, int y){
		int swampIndex = BronzeMath.randInt(210, 219);
		GameTile tile = new GameTile(x,y,swampIndex,false,Season.SPRING);
		tileType = Tile.FLOOR_SWAMP;
		return tile;
	}
	
	public static GameTile GameTileSwampWater(int x, int y){
		int swampIndex = BronzeMath.randInt(260, 269);
		GameTile tile = new GameTile(x,y,swampIndex,false,Season.SPRING);
		tile.hasWater = true;
		tileType = Tile.WATER_SWAMP;
		return tile;
	}
	
	public static GameTile GameTileShallowWater(int x, int y){
		int waterIndex = BronzeMath.randInt(270, 279);
		GameTile tile = new GameTile(x,y,waterIndex,false,Season.SPRING);
		tile.hasWater = true;
		tileType = Tile.WATER_SHALLOW;
		return tile;
	}
	

	public static GameTile GameTileDeepWater(int x, int y){
		int waterIndex = BronzeMath.randInt(280, 289);
		GameTile tile = new GameTile(x,y,waterIndex,false,Season.SPRING);
		tile.hasWater = true;
		tileType = Tile.WATER_DEEP;
		return tile;
	}
	
	public static GameTile GameTileSmoothRock(int x, int y){
		int rockIndex = BronzeMath.randInt(220, 229);
		GameTile tile = new GameTile(x,y,rockIndex,false,Season.SPRING);
		tileType = Tile.FLOOR_ROCK_SMOOTH;
		return tile;
	}
	
	public static GameTile GameTileRawRock(int x, int y){
		int rockIndex = BronzeMath.randInt(240, 249);
		GameTile tile = new GameTile(x,y,rockIndex,false,Season.SPRING);
		tileType = Tile.FLOOR_ROCK_RAW;
		return tile;
	}
	
	public static GameTile GameTileRiver(int x, int y){
		int riverIndex = BronzeMath.randInt(240, 249);
		GameTile tile = new GameTile(x,y,riverIndex,false,Season.SPRING);
		tile.hasWater = true;
		tileType = Tile.WATER_RIVER;
		return tile;
	}
	
	@Override
    public boolean equals(Object obj) {
        if (obj == this) return true;

        if (obj instanceof Point){
        	Point p = (Point) obj;
        	if (this.x ==  p.x && this.y == p.y){
        		return true;
        	}
        }
        return false;
    }
	
}
