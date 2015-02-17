package BronzeAndFaith.Map;
import java.awt.Color;

import BronzeAndFaith.Game.Definitions;

public enum Tile {

	//int flag, String name, Color color, char glyph, boolean isBlocked, int imageIndex 
	BOUNDS(0,"bounds",Color.BLACK,' ',true,2),
	FLOOR(1,"floor",Color.WHITE,'.',false,0),
	WALL(2,"wall",Color.WHITE,'X',true,1),
	WATER(3,"water",Color.BLUE,'w',true,3),
	FLOOR_GRASS_DEEP(4,"deep grass",Definitions.DEEPGRASS,'I',false,0),
	FLOOR_GRASS_DRY(5,"dry grass",Definitions.DRYGRASS,'i',false,4),
	FLOOR_RAW(6,"soil",Definitions.RAWFLOOR,'_',false,5),
	FLOOR_FLATTENED(7, "flattened soil", Definitions.FLATFLOOR,'_',false,5),
	FLOOR_FOREST_RAW(8, "forest floor", Definitions.RAWFOREST, '~', false,6),
	FLOOR_FOREST_NEEDLE(9, "needle forest floor", Definitions.NEEDLEFOREST, '~', false,6),
	FLOOR_FOREST_LEAF(10, "leaf forest floor", Definitions.LEAFFOREST, '~', false,6),
	FLOOR_ROCK_RAW(11,"raw rock", Definitions.RAWROCK, '#', false, 1),
	FLOOR_ROCK_SMOOTH(12, "smooth rock", Definitions.SMOOTHROCK,'#', false, 6),
	FLOOR_SWAMP(13, "swamp", Definitions.SWAMPFLOOR, 'M', false, 6),
	WATER_SWAMP(14, "swamp water",Definitions.SWAMPWATER,'m',true, 3),
	WATER_SHALLOW(15, "shallow water", Definitions.SHALLOWWATER, 'w', true, 3),
	WATER_DEEP(16, "deep water", Definitions.DEEPWATER, 'W', true, 3),
	FLOOR_WOOD(17, "wooden floor", Definitions.WOODFLOOR, 'H', false, 0),
	WATER_RIVER(18, "shallow water", Definitions.SHALLOWWATER, 'w', true, 3),
	STRUCTURE_WOODEN_WALL(19, "wooden wall", Definitions.WOODWALL, 'X', true, 4),
	STRUCTURE_FLOOR_WOOD(20, "wooden floor", Definitions.WOODFLOOR, 'H', false, 0);
	
	public Tile getTile(int flag){
		for(Tile t :Tile.values()){
			if(t.flag()==flag){
				return this;
			}
		}
		return null;
	}
	
	private final int flag;
	public int flag(){return flag;}
	
	private final String tileName;
	public String tileName(){return tileName;}
	
	private final Color color;
	public Color color(){return color;}
	
	private final char glyph;
	public char glyph(){return glyph;}
	
	private final boolean isWater;
	public boolean isWater(){return isWater;}
	
	private final int imageIndex;
	public int imageIndex(){return imageIndex;}

	Tile(int flag, String name, Color color, char glyph, boolean isWater, int imageIndex ){
		this.flag = flag;
		this.tileName = name;
		this.color = color;
		this.glyph = glyph;
		this.isWater = isWater;
		this.imageIndex = imageIndex;
		
	}
	
	
	///////////////
	/*
	// Tile Type
	public static final int BOUNDS = 0;
	public static final int FLOOR = 1;
	public static final int WALL = 2;
	public static final int WATER = 3;
	
	//Grass Tiles
	public static final int FLOOR_GRASS_DEEP = 4;
	public static final int FLOOR_GRASS_DRY = 5;
	
	//Earth Tiles
	public static final int FLOOR_RAW = 6;
	public static final int FLOOR_FLATTENED = 7;
	
	//Forest Tiles
	public static final int FLOOR_FOREST_RAW = 8;
	public static final int FLOOR_FOREST_NEEDLE = 9;
	public static final int FLOOR_FOREST_LEAF = 10;
	
	//Rock Tiles
	public static final int FLOOR_ROCK_RAW = 11;
	public static final int FLOOR_ROCK_SMOOTH = 12; //hill
	
	//Swamp Tiles
	public static final int FLOOR_SWAMP = 13;
	public static final int WATER_SWAMP = 14;
	
	//Water Tiles
	public static final int WATER_SHALLOW = 15;
	public static final int WATER_DEEP = 16;
	
	//Other Tiles
	public static final int FLOOR_WOOD = 17;
	
	*/


/*
	public static final String[] name =			 {"BOUNDS", 		"FLOOR", 		"WALL",		"WATER", 	"DEEP GRASS",	"DRY GRASS",	"RAW FLOOR",	"FLATTENED FLOOR", 
		"HUMUS",	"NEEDLE",	"LEAF",	"RAW ROCK",	"HILL",	"SWAMP",	"SWAMPWATER",	"SHALLOW WATER",	"DEEP WATER",	"WOODEN FLOOR"};
	public static final Color[] color =			 {Color.BLACK, 	Color.WHITE, 	Color.WHITE,	Color.BLUE,	deepGrass, dryGrass, rawFloor, flatFloor, rawForest, needleForest, leafForest, rawRock, smoothRock, swampFloor, swampWater, shallowWater, deepWater, woodFloor };
	public static final char[] glyph = 			 {' ', 			'.', 			'X',			'w',		'I', 			'i', 			'_',		'_', '~', '~', '~', '#', '#', 'm', 'W', 'w', 'W', 'H'};
	public static final boolean[] isBlocked =    {true, 		false,			 true,			true, false, false, false, false, false, false,false, false, false, false, false, false, false, false};
	public static final int[] image = 			 {2,			 0, 			1, 				3,0,4,5,5,5,5,5,6,2,0,4,4,4,6};
*/
}
