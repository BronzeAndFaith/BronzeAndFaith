
public enum CoverageZone {

	SKULL(0, "skull"), 
	FACE(1, "face"),
	NECK(2, "neck"), 
	CHEST(3, "chest"), 
	BELLY(4, "belly"), 
	GROIN(5, "groin"), 
	BACK(6, "back"), 
	LEFTSHOULDER(7,"left shoulder"), 
	LEFTUPPERARM(8, "left upper arm"),
	LEFTLOWERARM(9, "left lower arm"),
	LEFTHAND(10, "left hand"),
	LEFTUPPERLEG(11, "left upper leg"),
	LEFTLOWERLEG(12, "left lower leg"),
	LEFTFOOT(13, "left foot"),
	RIGHTSHOULDER(14, "right shoulder"), 
	RIGHTUPPERARM(15, "right upper arm"),
	RIGHTLOWERARM(16, "right lower arm"),
	RIGHTHAND(17, "right hand"),
	RIGHTUPPERLEG(18, "right upper leg"),
	RIGHTLOWERLEG(19, "right lower leg"),
	RIGHTFOOT(20, "right foot");
	
	private final int index;
	public int index(){return index;}
	
	private final String writtenName;
	public String writtenName(){return writtenName;}
	
	CoverageZone(int index, String name){
		this.index = index;
		this.writtenName = name;
	}
}
