package BronzeAndFaith.GUI;

import BronzeAndFaith.Content.Human;
import BronzeAndFaith.Content.Item;
import BronzeAndFaith.Game.InventoryBase;

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