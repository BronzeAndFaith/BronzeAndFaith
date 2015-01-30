import java.awt.event.KeyEvent;
import java.util.ArrayList;


public abstract class InventoryBase implements Screen {
	
	protected Human player;
	private String alphabet;
	
	protected abstract String getVerb();
	protected abstract boolean isAcceptable(Item item);
	protected abstract Screen use(Item item);
	

	public InventoryBase(Human player){
		this.player = player;
		alphabet = "abcdefghijklmnopqrstuvwyz";
	}
	
	@Override
	public void displayOutput(RoguePanel roguepanel) {
		ArrayList<String> lines = getList();
		
		int y = 2;
		int x = 2;
		
		if(lines.size() > 0){
			roguepanel.clear();
		}
		
		for(String line : lines){
			roguepanel.write(line, x, y++);
		}
		roguepanel.write("what would you like to " + getVerb() + "?", 2, 1);
		
		roguepanel.clear();
		
		
		roguepanel.repaint();
	}

	public Screen respondToUserInput(KeyEvent key) {
        char c = key.getKeyChar();
 
        Item[] items = player.inventory().getItems();
     
        if (alphabet.indexOf(c) > -1
             && items.length > alphabet.indexOf(c)
             && items[alphabet.indexOf(c)] != null
             && isAcceptable(items[alphabet.indexOf(c)]))
            return use(items[alphabet.indexOf(c)]);
        else if (key.getKeyCode() == KeyEvent.VK_ESCAPE)
            return null;
        else
            return this;
    }

	@Override
	public Screen inputRelease(KeyEvent e) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public ArrayList<String> getList(){
		ArrayList<String> lines = new ArrayList<String>();
		Item[] inventory = player.inventory().getItems();
		
		for(int i = 0; i < inventory.length; i++){
			Item item = inventory[i];
			
			if(item == null || !isAcceptable(item))
				continue;
			
			String line = alphabet.charAt(i) + " - " + item.name();
			if(player.isEquipped(item))
			      line += " (equipped)";
			
			lines.add(line);
			}
		return lines;
	}

}
