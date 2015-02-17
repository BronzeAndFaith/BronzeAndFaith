package BronzeAndFaith.Game;
import java.awt.Color;
import java.awt.DisplayMode;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import BronzeAndFaith.GUI.GameScreen;
import BronzeAndFaith.GUI.RoguePanel;
import BronzeAndFaith.GUI.Screen;
import BronzeAndFaith.GUI.ScreenManager;
import BronzeAndFaith.Map.BuildingPattern;

public class Main extends JFrame implements KeyListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2394338007866874854L;
	// CONSTANTS
	public static final int TILEWIDTH = 32;
	public static final int TILEHEIGHT = 32;
	
	public static final int MAPWIDTH = 256;
	public static final int MAPHEIGHT = 256;
	public static final int CHUNKSIZE = 32;

	public static Image terrain;
	public static Image creature;
	public static Image item;
	public static Image tree;
	

	
	private RoguePanel roguepanel;
	private Screen screen;
	

	public Main(){
		super();
	    terrain = getImage("assets/tiles/terrain/terrain.png");
	    creature = getImage("assets/tiles/creatures/creatures.png");
	    item = getImage("assets/tiles/items/items.png");
	    tree = getImage("assets/tiles/structures/trees.png");
		addKeyListener(this);
		roguepanel = new RoguePanel(1920, 1080);
		add(roguepanel);
		screen = new GameScreen();
		repaint();
	}
	
	public static void main(String[] args){

		
		DisplayMode displayMode;

		
		if(args.length == 3){
			displayMode = new DisplayMode(
					Integer.parseInt(args[0]),
					Integer.parseInt(args[1]),
					Integer.parseInt(args[2]),
					DisplayMode.REFRESH_RATE_UNKNOWN);
		} else {
			displayMode = new DisplayMode(1920,1080,16,DisplayMode.REFRESH_RATE_UNKNOWN);
		}

		Main test = new Main();
		test.run(displayMode);
	}
	
	private void run(DisplayMode displayMode) {
		setForeground(Color.BLACK);
		setFont(new Font("Dialog", Font.PLAIN, 16));
		
		ScreenManager screen = new ScreenManager();
		screen.setFullScreen(displayMode,this);
	}	


	@Override
	public void repaint(){
		screen.displayOutput(roguepanel);
		super.repaint();
		
	}
	

	@Override
	public void keyPressed(KeyEvent e) {
		screen.respondToUserInput(e);
		repaint();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		screen.inputRelease(e);
		
	}

	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}
	
	public static Image getImage(String img)
	{
		BufferedImage bImg;
		try {
		    bImg = ImageIO.read(new File(img));
		} catch (IOException e) {
			bImg = null;
		}
		return bImg;
	}

}
