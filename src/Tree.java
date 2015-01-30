
public class Tree extends Structure implements Destructible{


	private Material material;
	public Material getMaterial(){return material;}
	public void setMaterial(Material material) {
		this.material = material;
	}
	
	private int health;
	public int getHealth() {
		return health;
	}
	public void setHealth(int health) {
		this.health = health;
	}
	@Override
	public void onDestroy() {
		// leave tree stump
		
	}
	@Override
	public void onAttack() {
		// randomly drop splinters etc...
	}


	
	public Tree(String name, int image, int x, int y, int health, Material material){
		super();
		this.x = x;
		this.y = y;
		this.name = name;
		this.material = material;
		this.health = health;
		this.structureImage = image;	
	}
	
}
