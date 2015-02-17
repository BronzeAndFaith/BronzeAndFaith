package BronzeAndFaith.Map;

import java.awt.Point;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import BronzeAndFaith.Content.Door;
import BronzeAndFaith.Content.Floor;
import BronzeAndFaith.Content.Structure;
import BronzeAndFaith.Content.Wall;


/**
 * A minimap, containing only the Points of the building with modular Points marked.
 * Can read building patterns from file.
 * @author Jeremy
 *
 */
public class BuildingPattern {

	private int width;
	public int getWidth(){
		return width;
	}
	private int height;
	public int getHeight(){
		return height;
	}
	
	private HashMap<Point, PatternElement> map = new HashMap<Point, PatternElement>();
	public void setPattern(HashMap<Point,PatternElement> map){
		this.map = map;
	}
	public HashMap<Point, PatternElement> getMap(){
		return map;
	}	
	
	private BuildingMaterial buildMat;
	public BuildingMaterial getBuildMat(){
		return buildMat;
	}
	
	private String name;
	
	private ArrayList<Point> moduleNodes; // A ModuleNode will either be a wall or a passage to another room, if another room/building is appended
	public ArrayList<Point> getModuleNodes(){
		return moduleNodes;
	}
	
	private ArrayList<Structure> structures = new ArrayList<Structure>() ;
	public ArrayList<Structure> getStructure(){
		return structures;
	}
	

	/*public ArrayList<Point>getArea(){
		ArrayList<Point> list = new ArrayList<Point>(structureMap.keySet());
		return list;
	}*/
	
	private Point exit;
	public Point getExit(){
		return exit;
	}
	public void setExit(Point exit){
		this.exit = exit;
	}
	
	public BuildingPattern(String name, BuildingMaterial material){
		this.name = name;
		this.buildMat = material;
		scanPattern();
	}
	
	
	

	private void scanPattern(){
		String filename = "patterns/buildings/"+name+".txt";
		File file = new File(filename);
		FileInputStream fis;
		String pattern = "";
		try {
			fis = new FileInputStream(file);
			byte[] bytes = new byte[(int) file.length()];
			fis.read(bytes);
			fis.close();
			String text = new String(bytes, "UTF-8");
			text = text.substring(text.indexOf("<PATTERN>"), text.indexOf("</PATTERN>"));
			text = text.substring(text.indexOf('\n')+1);
			pattern = text;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String lines[] = pattern.split("\\r?\\n");
		System.out.println("linesSize: "+ lines.length);

		int x = 0;
		int y = 0;
		
		for(String s:lines){ //y first
			for(char c: s.toCharArray()){ //x next, as single characters
				
				switch(c){
					case 'X': makePatternWall(x,y);
						break;
					
					case '.': makePatternFloor(x,y);
						break;
						
					case 'O': makePatternDoor(x,y);
						break;
						
					case 'N': makePatternNode(x,y);
						break;
						
					case ' ': makePatternEmpty(x,y);
						
					default:
						break;
				}
				
				x++;
			}
			x=0;
			y++;
		}
		width = x;
		height = y;
	}
	
	
	private void makePatternWall(int x, int y){
		Point p = new Point(x,y);
		map.put(p, PatternElement.WALL);
		makeMaterialWall(p);
	}
	private void makeMaterialWall(Point p){
		Structure struct = new Wall(p.x,p.y,buildMat);
		structures.add(struct);
	}
	
	private void makePatternFloor(int x, int y){
		Point p = new Point(x,y);
		map.put(p, PatternElement.WALL);
		makeMaterialFloor(p);
	}
	private void makeMaterialFloor(Point p){
		Structure struct = new Floor(p.x,p.y,buildMat);
		structures.add(struct);
	}
	
	private void makePatternNode(int x, int y){
		Point p = new Point(x,y);
		map.put(p, PatternElement.MODULE_NODE);
		makeMaterialWall(p);
	}
	
	private void makePatternDoor(int x, int y){
		Point p = new Point(x,y);
		map.put(p, PatternElement.DOOR);
		makeMaterialDoor(p);
	}
	private void makeMaterialDoor(Point p){
		Structure struct = new Door(p.x,p.y,buildMat);
		structures.add(struct);
	}
	private void makePatternEmpty(int x, int y){
		Point p = new Point(x,y);
		map.put(p, PatternElement.MODULE_NODE);
	}
	
	/**
	 * translates all the structures from the current Point by dx, dy
	 * @param dx
	 * @param dy
	 */
	public void translateStructures(int dx, int dy){
		for(Structure s: structures){
			s.translate(dx,dy);
		}
	}
	
	/**
	 * sets all the structures to the target offset, no matter where they are now
	 * 
	 */
	public void offsetStructures(int dx, int dy){
		
		for(Structure s: structures){
			s.translate(dx, dy);
		}
	}
	
	private Point findSmallestX(List<Point> points){
		int x=Integer.MAX_VALUE;
		Point smallest = new Point(x,0);
		for(Point p: points){
			if (p.x < x){
				smallest = p;
			}
		}
		return smallest;
	}
	
	private Point findSmallestY(List<Point> points){
		int y=Integer.MAX_VALUE;
		Point smallest = new Point(0,y);
		for(Point p: points){
			if (p.y < y){
				smallest = p;
			}
		}
		return smallest;
	}

}
