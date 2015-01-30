
public class TreeBuilder {
	private World world;
	
	//DEFAULT TREE HEALTH
	static final int OAK_HEALTH=100;
	
	public TreeBuilder(World world){
		this.world = world;
	}
	
	public Tree newOak(int x, int y){
		Tree t = new Tree("oak", 1, x, y, OAK_HEALTH, Material.WOOD_OAK);
		t.setBlocking(true);
		//t.set structureTop ("index:crown of oak")
		world.addStructure(t);
		return t;
	} 
	
}
