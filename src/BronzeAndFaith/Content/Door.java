package BronzeAndFaith.Content;

import BronzeAndFaith.Game.Destructible;
import BronzeAndFaith.Map.BuildingMaterial;

public class Door extends Structure implements Destructible, Openable{
	
	private BuildingMaterial buildMat;
	private boolean isOpen;
	
	
	public Door(int x, int y, BuildingMaterial buildMat){
		super();
		this.x = x;
		this.y = y;
		this.buildMat = buildMat;
		this.name="door";
		this.isBlocking=false;
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAttack() {
		// TODO Auto-generated method stub
	}

	@Override
	public void Open() {
		if(isOpen=false){
			isOpen = true;
			this.isBlocking=false;
			this.structureImage=1;
		}
		
		
	}

	@Override
	public void Close() {
		if(isOpen = true){
			isOpen = false;
			this.isBlocking = true;
			this.structureImage = 1;
			//TODO make different structureImage for doors, place next to each other to enable -1 and +1 for different styles	
		}
	
		
	}
	
	
	
}
