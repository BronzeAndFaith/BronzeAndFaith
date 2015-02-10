import java.awt.event.KeyEvent;

public abstract class DirectionBaseScreen implements Screen{
	
	protected Human player;
	protected String caption;
	protected abstract String getVerb();

	private int x;
	private int y;
	
	public DirectionBaseScreen(Human player, String caption){
		this.player = player;
		this.caption = caption;
	}
	
	public void displayOutput(RoguePanel roguepanel){
		roguepanel.clear();
		roguepanel.write(caption,2,23);
	}
	
	public Screen respondToUserInput(KeyEvent key){
		
		int px = player.getX();
		int py = player.getX();
		
		switch (key.getKeyCode()){
		case KeyEvent.VK_LEFT:
			case KeyEvent.VK_H:	{x--; selectWorldCoordinate(px+x, py+y); return null;}
		case KeyEvent.VK_RIGHT:
			case KeyEvent.VK_K:  {x++; selectWorldCoordinate(px+x, py+y); return null;}
		case KeyEvent.VK_UP:
			case KeyEvent.VK_U: {y--; selectWorldCoordinate(px+x, py+y); return null;}
		case KeyEvent.VK_DOWN:
			case KeyEvent.VK_J:  {y++; selectWorldCoordinate(px+x, py+y); return null;}
		case KeyEvent.VK_ESCAPE: return null;
		}
		enterWorldCoordinate(player.getX() + x, player.getY() + y);
		return this;
	}
	
	public boolean isAcceptable(int x, int y){
		return true;
	}
	
	public void enterWorldCoordinate(int x, int y, int screenX, int screenY){}
	public void enterWorldCoordinate(int x, int y){}
	
	public void selectWorldCoordinate(int x, int y, int screenX, int screenY){}
	public void selectWorldCoordinate(int x, int y){};
	
}
