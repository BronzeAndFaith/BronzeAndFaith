import java.awt.event.KeyEvent;


public class PickupScreen extends WorldInventoryBasedScreen{
	
	public PickupScreen(Human player, World world){
		super(player, world);
	}
	
	protected String getVerb(){
		return "pick up";
	}
	
	protected boolean isAcceptable(Item item){
		return true;
	}
	
	protected Screen use(Item item) {
        player.pickup(item);
        return null;
    }
	
	protected Screen use(Item[] items) {
		for (Item i: items)
			player.pickup(i);
        return null;
    }

	@Override
	public Screen inputRelease(KeyEvent e) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
}
