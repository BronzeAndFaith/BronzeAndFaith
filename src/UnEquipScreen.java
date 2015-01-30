
public class UnEquipScreen extends InventoryBase{
	
	public UnEquipScreen(Human player){
		super(player);
	}
	
	protected String getVerb(){
		return "unequip";
	}
	
	protected boolean isAcceptable(Item item){
		return item.isArmor() || item.isWeapon() /* || item.isBackpack()*/;
	}
	
	protected Screen use(Item item){
		player.unequip(item);
		return null;
	}
}