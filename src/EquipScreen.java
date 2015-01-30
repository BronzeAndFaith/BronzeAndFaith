
public class EquipScreen extends InventoryBase{
	
	public EquipScreen(Human player){
		super(player);
	}
	
	protected String getVerb(){
		return "equip";
	}
	
	protected boolean isAcceptable(Item item){
		return item.isArmor() || item.isWeapon() /* || item.isBackpack()*/;
	}
	
	protected Screen use(Item item){
		player.equip(item);
		return null;
	}
}