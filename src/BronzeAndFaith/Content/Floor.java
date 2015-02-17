package BronzeAndFaith.Content;
import BronzeAndFaith.Game.Destructible;
import BronzeAndFaith.Map.BuildingMaterial;

public class Floor extends Structure implements Destructible{
	
	private BuildingMaterial buildMat;

	public Floor(int x, int y, BuildingMaterial buildMat){
		super();
		this.x = x;
		this.y = y;
		this.buildMat = buildMat;
		this.name ="floor";
		this.isBlocking = false;
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
