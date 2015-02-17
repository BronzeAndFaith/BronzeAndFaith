package BronzeAndFaith.Content;

public class Structure {

	protected boolean isBlocking;
	public boolean isBlocking() {
		return isBlocking;
	}
	public void setBlocking(boolean isBlocking) {
		this.isBlocking = isBlocking;
	}
	
	protected int x;
	protected int y;
	public int getX(){return x;}
	public int getY(){return y;}
	public void setX(int x){this.x=x;}
	public void setY(int y){this.y=y;}
	protected String name;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	protected int structureImage;
	protected int structureTop;
	
	public int getStructureImage() {
		return structureImage;
	}
	public void setStructureImage(int structureImage) {
		this.structureImage = structureImage;
	}
	public int getStructureTop() {
		return structureTop;
	}
	public void setStructureTop(int structureImage) {
		this.structureTop = structureImage;
	}

	@Override
	public String toString(){
		String string = "Structure ["+name+"] at ["+x+", "+y+"]";
		return string;
	}
	
	public void translate(int dx, int dy){
		setX(x+dx);
		setY(y+dy);
	}
	
}
