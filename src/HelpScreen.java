import java.awt.event.KeyEvent;


public class HelpScreen implements Screen {

	public void displayOutput(RoguePanel roguepanel) {
		roguepanel.clear();
		roguepanel.write("Help", 2, 2);
		roguepanel.write("Currently no key functions are implemented except ? for Help", 2, 4);
	}

	public Screen respondToUserInput(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) return null;
		else return this;
	}

	public Screen inputRelease(KeyEvent e) {
		return null;
	}

}
