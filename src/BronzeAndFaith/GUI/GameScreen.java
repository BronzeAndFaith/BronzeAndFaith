package BronzeAndFaith.GUI;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import BronzeAndFaith.Content.Building;
import BronzeAndFaith.Content.CreatureBuilder;
import BronzeAndFaith.Content.Human;
import BronzeAndFaith.Content.ItemBuilder;
import BronzeAndFaith.Content.Material;
import BronzeAndFaith.Content.TreeBuilder;
import BronzeAndFaith.Game.Main;
import BronzeAndFaith.Game.World;
import BronzeAndFaith.Map.BuildingMaterial;
import BronzeAndFaith.Map.BuildingPlanner;
import BronzeAndFaith.Map.Village;


public class GameScreen implements Screen{


	private World world;
	private BuildingPlanner bPlanner;
	private CreatureBuilder creatureBuilder;
	private TreeBuilder treeBuilder;
	private ItemBuilder itemBuilder;
	private Human player;
	boolean shift = false;
	
	public int widthInTiles=0;
	public int heightInTiles=0;
	
	private Screen subscreen;
	
	private List<String> messages;
	
	public GameScreen(){
		world = World.getInstance(Main.MAPWIDTH, Main.MAPHEIGHT, player);//new World(Main.MAPWIDTH, Main.MAPHEIGHT, player);
		world.initialize();
		creatureBuilder = new CreatureBuilder(world);
		itemBuilder = new ItemBuilder(world);
		treeBuilder = new TreeBuilder(world);
		this.messages = new ArrayList<String>();
		
		player = creatureBuilder.newPlayerRandomLocation(messages);
		viewPosition(getScrollX(),getScrollY());

				
		bPlanner = BuildingPlanner.getInstance(world);
		//bPlanner.buildAtPoint(new Point(player.x+1,player.y+1), "mainBuilding", BuildingMaterial.ROCK);
		List<Building> bList = world.buildings();
		
		Village v = new Village(new Point(player.x+10, player.y+10), world);
		v.addMainBuilding();
		bPlanner.buildInVillage(v, "farmingHut", BuildingMaterial.WOOD_AND_CLAY);

		bPlanner.buildInVillage(v, "smallHouse", BuildingMaterial.CLAY);
		bPlanner.buildInVillage(v, "smallHouse", BuildingMaterial.CLAY);

	}
	
	@Override
	public void displayOutput(RoguePanel roguepanel) {
		drawMap(roguepanel);
		if (subscreen != null){
		    subscreen.displayOutput(roguepanel);}
		writeMessages(roguepanel);
		widthInTiles=roguepanel.widthInTiles();
		heightInTiles=roguepanel.heightInTiles();

	}

	@Override
	public Screen respondToUserInput(KeyEvent e) {
		if (subscreen != null){
			subscreen = subscreen.respondToUserInput(e);
		}
		else {
			switch(e.getKeyCode()){
			case KeyEvent.VK_SHIFT:
				shift = true;
				break;
			
			case KeyEvent.VK_LEFT:
				if(shift == true){scrollX(-1);}
				else {player.move(-1, 0);viewPosition(getScrollX(),getScrollY());}
				break;
				
			case KeyEvent.VK_UP:
				if(shift == true) {scrollY(-1);}
				else {player.move(0, -1);viewPosition(getScrollX(),getScrollY());}
				break;
				
			case KeyEvent.VK_DOWN:
				if(shift == true) {scrollY(+1);}
				else {player.move(0, 1);viewPosition(getScrollX(),getScrollY());}
				break;
				
			case KeyEvent.VK_RIGHT:
				if(shift == true) {scrollX(+1);}
				else {player.move(1, 0);viewPosition(getScrollX(),getScrollY());}
				break;
				
			case KeyEvent.VK_I: subscreen = new InventoryScreen(player);break;
			case KeyEvent.VK_D: subscreen = new DropScreen(player); break;
			case KeyEvent.VK_K: player.ai().addMessage("Lets hope this works");break;
			case KeyEvent.VK_E: 
				if(shift==true) {subscreen = new UnEquipScreen(player);}
				else {subscreen = new EquipScreen(player);}
				break;
			case KeyEvent.VK_ESCAPE: System.exit(0);
			}
			
			switch(e.getKeyChar()){
				case ',': subscreen = new PickupScreen(player, world); break;
				case '?':subscreen = new HelpScreen(); break;
			}
		}
		return e.getKeyCode() == KeyEvent.VK_DELETE ? new GameScreen() : this;
		
	}
	
	@Override
	public Screen inputRelease(KeyEvent e){
		switch(e.getKeyCode()){
		case KeyEvent.VK_SHIFT:
			shift = false;
			break;
		}
		return null;
		
	}
	
	public int scrollX, scrollY;

	public void viewPosition(int x, int y) {
		scrollX=x;
		scrollY=y;
	}
	
	public void scrollX(int x){
		scrollX += x;
	}
	
	public void scrollY(int y){
		scrollY += y;
	}
	
	public int getScrollX() {
		return Math.max(0, Math.min(player.getX() - widthInTiles / 2, world.width() - widthInTiles));
	}
	
	public int getScrollY() {
		return Math.max(0, Math.min(player.getY() - heightInTiles / 2, world.height() - heightInTiles));
	}
	
	public void drawMap(RoguePanel roguepanel) {
		roguepanel.clear();
		for (int x = 0; x < roguepanel.widthInTiles()&& x <world.getMap().getWidth(); x++) {
			for (int y = 0; y < roguepanel.heightInTiles()&& y < world.getMap().getHeight(); y++) {
				int sX = x+scrollX;
				int sY = y+scrollY;
				roguepanel.drawTile(x, y, world.getGameTileIndex(sX,sY));
				roguepanel.drawCreature(x, y,world.creatureImage(sX, sY));
				roguepanel.drawItem(x, y, world.itemImage(sX, sY));
				roguepanel.drawStructure(x, y, world.structureImage(sX, sY));
			}
		}
	}
	
	public void writeMessages(RoguePanel roguepanel){
		if (!messages.isEmpty()){
			roguepanel.writeMessage(messages);
		}
	}
	




}
