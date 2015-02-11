package BronzeAndFaith.Map;
import java.awt.Image;

import BronzeAndFaith.Game.Main;


public enum Season {

	SPRING("Spring", Main.terrain),
	SUMMER("Summer", Main.terrain),
	AUTUMN("Autumn", Main.terrain),
	WINTER("Winter", Main.terrain);
	
	private final String seasonName;
	public final String getSeasonName(){
		return seasonName;
	}
	
	private final Image seasonTerrainFile;
	public final Image getSeasonTerrainFile(){
		return seasonTerrainFile;
	}
	
	Season(String seasonName, Image seasonTerrainFile){
		this.seasonName = seasonName;
		this.seasonTerrainFile = seasonTerrainFile;
	}

	
	
	
	
}
