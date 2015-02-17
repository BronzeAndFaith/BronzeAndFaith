package BronzeAndFaith.Content;

import BronzeAndFaith.Game.Destructible;
import BronzeAndFaith.Map.BuildingMaterial;

public class Wall extends Structure implements Destructible{
	
	private BuildingMaterial buildMat;

	public Wall(int x, int y, BuildingMaterial buildMat){
		super();
		this.x = x;
		this.y = y;
		this.buildMat = buildMat;
		//TODO change image
		this.structureImage=1;
		this.name = "wall";
		this.isBlocking=true;
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAttack() {
		// TODO Auto-generated method stub
	}
	
}
