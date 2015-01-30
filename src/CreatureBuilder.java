import java.util.List;

/*Contains Presets for Creatures, including Player*/

public class CreatureBuilder {
	private World world;
	
	public CreatureBuilder(World world){
		this.world = world;
	}
	
	// Player Builds
	/**
	 * Manually place a player in  a specific location
	 * 
	 * @param x coordinate
	 * @param y coordinate
	 * @param message List that will contain messages to be displayed on screen
	 * @return Returns a Human player
	 */
	public Human newPlayer(int x, int y, List<String> message){
		Human newPlayer = new Human(world, x,y,1);
		world.addCreature(newPlayer);
		new PlayerAi(newPlayer, message);
		System.out.println("Player created");
		return newPlayer;
	}
	
	
	// NPC Dummy
	/**
	 * Manually place a dummy NPC in a specific location
	 * 
	 * @param x coordinate
	 * @param y coordinate
	 * @return Returns a Human dummy NPC
	 */
	public Human newNPCDummy(int x, int y){
		Human newDummy = new Human(world, x,y,1);
		world.addCreature(newDummy);
		return newDummy;
	}
}
