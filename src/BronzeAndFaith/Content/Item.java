package BronzeAndFaith.Content;
import java.util.List;


public class Item {

	private int x;
	private int y;
	
	private int itemImage;
	public int itemImage(){return itemImage;}
	public void setItemImage(int index){itemImage = index;}
	
	private String commonDescription;
	public String commonDescription(){return commonDescription;}
	public void setCommonDescription(String newDescription){commonDescription = newDescription;}
	
	private String description;
	public String description(){return description;}
	public void setDescription(String newDescription){description = newDescription;}
	
	private float volume;
	public float volume(){return volume;}
	public void modifyVolume(float amount){volume += amount;}
	
	private float weight;
	public float weight(){return weight;}
	public void modifyWeight(float amount){weight += amount;}
	
	private String name;
	public String name(){return name;}
	
	private int foodValue;
	public int foodValue(){return foodValue;}
	public void modifyFoodValue(int amount){foodValue += amount;}
	
	/*For club-like weapons*/
	private int bashValue = 0;
	public int bashValue() {return bashValue;}
	public void modifyBashValue(int amount) {bashValue += amount;}
	
	/*For sharp weapons. Necessary for tools that are supposed to be sharp.*/
	private int cutValue = 0;
	public int cutValue() {return cutValue;}
	public void modifyCutValue(int amount) {cutValue += amount;}
	
	/*Arrows, spears*/
	private int pierceValue = 0;
	public int pierceValue() {return pierceValue;}
	public void modifyPierceValue(int amount) {pierceValue += amount;}
	
	private boolean isWeapon;
	public boolean isWeapon(){return isWeapon;}
	
	private boolean isArmor;
	public boolean isArmor(){return isArmor;}
	private float armorStrength;
	public float armorStrength(){return armorStrength;}
	public void setArmorStrength(float strength){armorStrength=strength;}
	/**indexes of parts that are covered by this part*/
	private List<Integer>coverage;
	public List<Integer>coverage(){return coverage;}
	
	private Material material;
	public String materialName(){
		return material.writtenName();
	}
	public int materialWeight(){
		return material.weight();
	}
	public int materialValue(){
		return material.value();
	}
	public int materialHardness(){
		return material.hardness();
	}
	public int materialDurability(){
		return material.durability();
	}
	
	private boolean isEquipped;
	public boolean isEquipped(){return isEquipped;}
	
		
	/*total attack value*/
	public int attackValue() {return bashValue + cutValue + pierceValue;}
	
	
	//SPECIAL MODIFIERS FOR ITEMS
	private boolean hardToHit; //this weapon will receive an accuracy penalty
	public boolean hardToHit(){return hardToHit;}
	private float accPenalty; //accuracy Penalty, to be calculated with user skills
	public float accPenalty(){return accPenalty;}
	public void setHardToHit(float penalty){
		hardToHit = true;
		accPenalty = penalty;
	}
	
	public Item(int x, int y, String name, Material material){
		this.material = material;
		this.setX(x);
		this.setY(y);
		this.name = name;
		this.volume = 0;
		this.weight = 0;
		composeDescription();
	}
	
	/**Define this item as armor. It can be worn as civilian cloth or combat armor and will provide defense while worn
	 * @param coverage pass an int with the coverage zone values. Refer to CoverageZone class for exact values
	 * @param armorStrength is the modifier for the armor and means fitness for battle in general. It will be multiplied with the material hardness and durability 
	 * */
	
	public void makeArmor(List<Integer> coverage, float armorStrength){
		isArmor = true;
		this.armorStrength=armorStrength;
		this.coverage = coverage;
	}
	
	public void makeWeapon(int bash, int cut, int pierce){
		this.bashValue = bash;
		this.cutValue = cut;
		this.pierceValue = pierce;
		this.isWeapon=true;
	}
	
	public void composeDescription(){
		String description = "This is a ";
		//description += "This is a ";
		
		description += name+" made of "+material.writtenName()+".";
		//description += " \n It is made of " + material.writtenName() +".";
		if(commonDescription!=null) description += commonDescription;
		if (bashValue != 0){
			if(bashValue > 90){
				description += "\n It looks so powerful it might crack the earth when it falls on the ground.";
			} else if (bashValue >70){
				description += "\n This weapon looks very dangerous and heavy. It wields immense power and is perfectly balanced.";
			} else if(bashValue > 50){
				description += "\n You feel this weapon is made for breaking bones like little twigs.";
			} else if(bashValue > 30){
				description += "\n This is a fine smashing tool.";
			} else if(bashValue >10 ){
				description += "\n You could use this to give someone a beating.";
			} else if(bashValue > 0){
				description += "\n This is barely useful as a smashing weapon.";
			}
		}

		if (cutValue != 0)
			if(cutValue > 90){
				description += "\n When you swing it through the air, you might cut the fabric of the gods itself. Be careful!";
			} else if (cutValue >70){
				description += "\n This is so sharp, you barely dare looking at it. "
						+ "\n When listening closely, you can hear the blade singing and urging for blood.";
			} else if(cutValue > 50){
				description += "\n This is not the average tool to leave around children. It has a damn fine blade.";
			} else if(cutValue > 30){
				description += "\n It seems to be quite sharp and useful for cutting.";
			} else if(cutValue >10 ){
				description += "\n It is not really what one would consider a real weapon, but you could cut someone quite badly with this.";
			} else if(cutValue > 0){
				description += "\n The edge is dull.";
			}

		if (pierceValue != 0)
			if(cutValue > 90){
				description += "\n This weapon is so powerful that the gods must have used it for hunting stone boars.";
			} else if (cutValue >70){
				description += "\n You can barely make out where the point ends. You can image this breaking even bronze armor like a rotten leaf.";
			} else if(cutValue > 50){
				description += "\n This is a quite sharp point, fit for thrusting boars and warriors alike. At the same time.";
			} else if(cutValue > 30){
				description += "\n It is a good pointy weapon, this one.";
			} else if(cutValue >10 ){
				description += "\n The point is quite dull, but if you press hard enough, you will probably break skin.";
			} else if(cutValue > 0){
				description += "\n The point should not be rounded off like that.";
			}
		
		if(isArmor==true){
			description += "\n It covers ";
			for (int i : coverage){
				
				switch(i){
				case 0: description += "the skull"; break;
				case 1:description += "the face"; break;
				case 2:description += "the neck"; break;
				case 3:description += "the chest"; break;
				case 4:description += "the belly"; break;
				case 5:description += "the groin"; break;
				case 6:description += "the back"; break;
				case 7:description += "the left shoulder"; break;
				case 8: description += "the left upper arm"; break;
				case 9: description += "the left forearm";break;
				case 10: description += "the left hand";break;
				case 11: description += "the left thigh";break;
				case 12: description += "the left lower leg";break;
				case 13: description += "the left foot";break;
				case 14: description += "the right shoulder";break;
				case 15: description += "the right upper arm";break;
				case 16: description += "the right forearm";break;
				case 17: description += "the right hand";break;
				case 18: description += "the right thigh";break;
				case 19: description += "the right lower leg";break;
				case 20: description += "the right foot";break;
							}
				
				description += ", ";
			}
			if (	description.contains("the left shoulder")
					&&description.contains("the right shoulder")
					&&description.contains("the left upper arm")
					&&description.contains("the right upper arm")
					&&description.contains("the left lower arm")
					&&description.contains("the right lower arm")){
					description = description.replace("the left shoulder, ", "");
					description = description.replace("the right shoulder, ", "");
					description = description.replace("the left upper arm, ", "");
					description = description.replace("the right upper arm, ", "");
					description = description.replace("the left lower arm, ", "");
					description = description.replace("the right lower arm, ", "");
					description += "both arms completely, ";
				
			} else if (description.contains("the left shoulder")
					&&description.contains("the right shoulder")
					&&description.contains("the left upper arm")
					&&description.contains("the right upper arm")
					){
					description = description.replace("the left shoulder, ", "");
					description = description.replace("the right shoulder, ", "");
					description = description.replace("the left upper arm, ", "");
					description = description.replace("the right upper arm, ", "");
					description += "both arms up to the elbow, ";}
			
			else if (description.contains("the left shoulder")&&description.contains("the right shoulder")){
				description = description.replace("the left shoulder, ", "");
				description = description.replace("the right shoulder, ", "");
				description +=("the shoulders, ");
			}
			
			if(	description.contains("the chest")
				&&description.contains("the belly")
				&&description.contains("the back")
				&&description.contains("the groin")
					){
				description = description.replace("the chest, ", "");
				description = description.replace("the belly, ", "");
				description = description.replace("the back, ", "");
				description = description.replace("the groin, ", "");
				description += "the whole torso, ";
			}
			description += "and nothing else."; 
			//return description;
		}	
		
		//return description;
		this.description = description;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	
}
