package BronzeAndFaith.Map;

/**
 * Reduced version of Materials, used for Buildings and their Patterns.
 * @author Jeremy
 *
 */
public enum BuildingMaterial {

	WOOD_LOG("wooden log"),
	WOOD_BOARD("wooden board"),
	CLAY("clay"),
	ROCK("rock"),
	WOOD_AND_CLAY("wood and clay"),
	WOOD_AND_ROCK("wood and rock"),
	WOOD_AND_CLAY_AND_ROCK("wood and clay and rock");
	
	private String materialName;
	
	BuildingMaterial(String name){
		this.materialName = name;
	}
	
}
