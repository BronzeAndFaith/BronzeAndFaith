
public class DropScreen extends InventoryBase{
	
	public DropScreen(Human player){
		super(player);
	}
	
	protected String getVerb(){
		return "drop";
	}
	
	protected boolean isAcceptable(Item item){
		return true;
	}
	
	protected Screen use(Item item) {
		System.out.println("dropping");
		player.dropItem(item);
        return null;
    }
	
}	
