import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public abstract class WorldInventoryBasedScreen implements Screen{
	
	protected Creature player;
	protected World world;
	private String letters;
	
	protected abstract String getVerb();
	protected abstract boolean isAcceptable(Item item);
	protected abstract Screen use(Item item);
	protected abstract Screen use(Item[] items);
	
	public WorldInventoryBasedScreen(Creature player, World world){
		this.player = player;
		this.world = world;
		this.letters = "abcdefghijklmnopqrstuvwxyz";
	}
	
	public void displayOutput(RoguePanel roguepanel){
		ArrayList<String> lines = getList();
		
		int y = 2 - lines.size();
		int x = 2;
		
		if(lines.size() > 0){
			roguepanel.clear();
		}
		for(String line : lines){
			roguepanel.write(line, x, y++);
		}
		
		roguepanel.clear();
		roguepanel.write("what would you like to " + getVerb() + "?", 2, 2);
		
		roguepanel.repaint();
	}
	
	public ArrayList<String> getList(){
		ArrayList<String> lines = new ArrayList<String>();
		List<Item> items = world.items(player.getX(), player.getY());
		Item[] inventory = (Item[]) items.toArray(new Item[0]);
		
		for(int i = 0; i < inventory.length; i++){
			Item item = inventory[i];
			
			if(item == null || !isAcceptable(item))
				continue;
			
			String line = letters.charAt(i) + " - " + item.name();
			lines.add(line);
			}
		if(inventory.length > 1){
			String line = "A"+ " - " + "[]" + " " + "ALL Items";
			lines.add(line);
		}
		return lines;
	}
	
		
	public Screen respondToUserInput(KeyEvent key) {
		 char c = key.getKeyChar();
		 
	        List<Item> worldItems = world.items(player.getX(), player.getY());
			Item[] items = (Item[]) worldItems.toArray(new Item[0]);
	     
	        if (letters.indexOf(c) > -1
	             && items.length > letters.indexOf(c)
	             && items[letters.indexOf(c)] != null
	             && isAcceptable(items[letters.indexOf(c)]))
	            return use(items[letters.indexOf(c)]);
	        else if(c == 'A')
	        	return use(items);
	        else if (key.getKeyCode() == KeyEvent.VK_ESCAPE)
	            return null;
	        else
	            return this;
    }
	
	
	
	
}