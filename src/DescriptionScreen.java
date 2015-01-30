import java.awt.event.KeyEvent;


public class DescriptionScreen implements Screen{

	private String description;
	public DescriptionScreen(String description){
		this.description = description;
	}
	
	public void displayOutput(RoguePanel roguepanel) {
		roguepanel.clear();
		roguepanel.write(description, 1, 1);

	}

	public Screen respondToUserInput(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) return null;
		else return this;
	}

	public Screen inputRelease(KeyEvent e) {
		return null;
	}

}
