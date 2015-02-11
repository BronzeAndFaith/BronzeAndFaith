package BronzeAndFaith.GUI;
import java.awt.event.KeyEvent;

public interface Screen {

	public void displayOutput(RoguePanel roguepanel);
	public Screen respondToUserInput(KeyEvent e);
	public Screen inputRelease(KeyEvent e);
}
