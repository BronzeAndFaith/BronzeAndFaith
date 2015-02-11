package BronzeAndFaith.GUI;

import BronzeAndFaith.Content.Human;
import BronzeAndFaith.Content.Item;
import BronzeAndFaith.Game.InventoryBase;

public class InventoryScreen extends InventoryBase{
	
	public InventoryScreen(Human player){
		super(player);
	}
	
	protected String getVerb(){
		return "examine";
	}
	
	protected boolean isAcceptable(Item item){
		return true;
	}
	
	protected Screen use(Item item) {
		
		item.composeDescription();
		String description = item.description();
		return new DescriptionScreen(description);
    }
	
}	
