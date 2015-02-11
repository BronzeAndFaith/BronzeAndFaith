package BronzeAndFaith.GUI;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JTextArea;

import BronzeAndFaith.Game.Main;


public class RoguePanel extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9061573789472900707L;
	
	private Graphics panelGraphics;
	private Image panelImage;
	private static Image terrain;
	private static Image creature;
	private static Image item;
	private static Image tree;
	
	private int fontSize = 30;
	
	private int[][] tiles;
	private int[][] creatures;
	private int[][] items;
	private int[][] structures;
	
	private int width, height;
	private int widthInTiles, heightInTiles;
	
	private HashMap<String, int[]> text;
	private List<String> messages;
		
	public int width(){
		return this.width;
	}
	public int height(){
		return this.height;
	}
	public int widthInTiles(){
		return this.widthInTiles;
	}
	public int heightInTiles(){
		return this.heightInTiles;
	}
	
	public RoguePanel(int width, int height){
		super();
		this.width = width;
		this.height = height;
		this.widthInTiles = width/Main.TILEWIDTH;
		this.heightInTiles = height/Main.TILEHEIGHT;

		text = new HashMap<String, int[]>();
		messages = new ArrayList<String>();
		
		tiles = new int[widthInTiles][heightInTiles];
		creatures = new int [widthInTiles][heightInTiles];
		items = new int[widthInTiles][heightInTiles];
		structures = new int[widthInTiles][heightInTiles];
		
		if (width < 1)
            throw new IllegalArgumentException("width: " + width + ". Must be greater than 0." );

        if (height < 1)
            throw new IllegalArgumentException("height: " + height + ". Must be greater than 0." );
        
        setPreferredSize(new Dimension(width, height));
        
        terrain = Main.terrain;
        creature = Main.creature;
        item = Main.item;
        tree = Main.tree;
        
	}
		
	@Override
	public void update(Graphics g){
		paint(g);
	} 
	
	public void paint(Graphics g){
		super.paintComponent(g);
		
		if (g == null){
            throw new NullPointerException("Graphics == null");}
		if (panelImage == null){
			panelImage = createImage(width(), height());
			panelGraphics = panelImage.getGraphics();
		}
		
		JTextArea textArea= new JTextArea();
		Font font = new Font("TimesRoman", Font.PLAIN, fontSize);
		textArea.setFont(font); 
		g.setFont(font);
		g.setFont(font);
		g.setColor(Color.BLACK);
		//textArea.setBackground(Color.BLACK);
		textArea.setForeground(Color.WHITE);
		
		for (int x = 0; x < widthInTiles; x++){
			for (int y = 0; y < heightInTiles; y++){
				//load image indexes
				int image = tiles[x][y];
				//int image = Tile.image[flag];
				int structureImage = structures[x][y];
				int itemImage = items[x][y];
				int creatureImage = creatures[x][y];
				
				
				int px = x*Main.TILEWIDTH;
				int py = y*Main.TILEHEIGHT;
				
				//set up source coords
				int sx=(image%10)*Main.TILEWIDTH; //floor
		        int sy=(image/10)*Main.TILEHEIGHT;
		        int ix=(itemImage%20)*Main.TILEWIDTH; //item
		        int iy=(itemImage/20)*Main.TILEHEIGHT;
		        int cx=(creatureImage%20)*Main.TILEWIDTH; //creature
		        int cy=(creatureImage/20)*Main.TILEHEIGHT;
		        int stx=(structureImage%20)*Main.TILEWIDTH; //structure
		        int sty=(structureImage/20)*Main.TILEHEIGHT;
		        
		        //draw onto panel. Order is important.
				panelGraphics.drawImage(terrain,px,py,px+Main.TILEWIDTH,py+Main.TILEHEIGHT,sx,sy,sx+Main.TILEWIDTH,sy+Main.TILEHEIGHT,null); 
				if (itemImage != 0 ){
					panelGraphics.drawImage(item,px,py,px+Main.TILEWIDTH,py+Main.TILEHEIGHT,ix,iy,ix+Main.TILEWIDTH,iy+Main.TILEHEIGHT,null);
				}
				if (creatureImage != 0 ){
					panelGraphics.drawImage(creature,px,py,px+Main.TILEWIDTH,py+Main.TILEHEIGHT,cx,cy,cx+Main.TILEWIDTH,cy+Main.TILEHEIGHT,null);
				}
				if (structureImage != 0 ){
					panelGraphics.drawImage(tree,px,py,px+Main.TILEWIDTH,py+Main.TILEHEIGHT,stx,sty,stx+Main.TILEWIDTH,sty+Main.TILEHEIGHT,null);
				}
			}
		}
		
		

		g.drawImage(panelImage,0,0,this);
		
		if(!text.isEmpty()){
			Iterator<String> keySetIterator = text.keySet().iterator();
			while(keySetIterator.hasNext()){
			  String key = keySetIterator.next();
			  int[] coords = text.get(key);
			  textArea.insert(key,0);
			  g.setColor(Color.WHITE);
			  for (String line : key.split("\n"))
			        g.drawString(line, coords[0], coords[1] += g.getFontMetrics().getHeight());
			}
		}

		for (int i = 0; i < messages.size(); i++) {
			Collections.reverse(messages);
			g.fillRect(0, 900, 1920, 1080);
			g.setColor(Color.WHITE);
			g.drawString(messages.get(i),2, 900+fontSize+i*fontSize);
			
		}
		
		messages.clear();
		text.clear();
	}
	
	public RoguePanel drawTile(int x, int y, int imageIndex) {
		if(x<Main.MAPWIDTH-1 && x >= 0 && y >= 0 && y<Main.MAPHEIGHT-1){
			tiles[x][y]=imageIndex;
			}
		else{
			tiles[x][y] = 0;
			}
		return this;
	}
	
	public RoguePanel drawCreature(int x, int y, int creatureImage){
		
		if(x<Main.MAPWIDTH-1 && x >= 0 && y >= 0 && y<Main.MAPHEIGHT-1){
			creatures[x][y]=creatureImage;
		}
		return this;
	}
	
	public RoguePanel drawItem(int x, int y, int itemImage){
		if(x<Main.MAPWIDTH-1 && x >= 0 && y >= 0 && y<Main.MAPHEIGHT-1){
			items[x][y]=itemImage;
		}
		return this;
	}
	
	public RoguePanel drawStructure(int x, int y, int structureImage){
		if(x<Main.MAPWIDTH-1 && x >= 0 && y >= 0 && y<Main.MAPHEIGHT-1){
			structures[x][y]=structureImage;
		}
		return this;
	}
	
	
	public void write(String string, int x, int y){
		int[] coords = {x,y*fontSize};
		text.put(string, coords);
	}
	
	public void writeMessage(List<String> messages){
		this.messages = messages;
	}
	
	public void clear(){
		for (int x = 0; x < widthInTiles()&& x <Main.MAPWIDTH; x++) {
			for (int y = 0; y < heightInTiles()&& y < Main.MAPHEIGHT; y++) {
				drawTile(x, y, 0);
				drawCreature(x, y, 0);
				drawItem(x, y, 0);
				drawStructure(x,y,0);
			}
		}
	}	
	
}
