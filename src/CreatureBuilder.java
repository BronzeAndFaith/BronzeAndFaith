import java.awt.Point;
import java.util.List;

/*Contains Presets for Creatures, including Player*/

public class CreatureBuilder {
	private World world;
	
	public CreatureBuilder(World world){
		this.world = world;
	}
	
	// Player Builds
	public Human newPlayer(int x, int y, List<String> message){
		Human newPlayer = new Human(world, x,y,1);
		world.addCreature(newPlayer);
		new PlayerAi(newPlayer, message);
		System.out.println("Player created");
		return newPlayer;
	}
	
	public Human newPlayerRandomLocation(List<String> message){
		Point p = world.getStartLocation();
		Human newPlayer = new Human(world, p.x,p.y,1);
		world.addCreature(newPlayer);
		new PlayerAi(newPlayer, message);
		System.out.println("Player created");
		return newPlayer;
	}

	
	
	
	// NPC Dummy
	public Human newNPCDummy(int x, int y){
		Human newDummy = new Human(world, x,y,1);
		world.addCreature(newDummy);
		return newDummy;
	}
}
