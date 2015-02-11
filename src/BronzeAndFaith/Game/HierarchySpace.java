package BronzeAndFaith.Game;

import BronzeAndFaith.Content.Creature;
import BronzeAndFaith.Content.Human;

public class HierarchySpace {

	public boolean isVacant = false;
	private Human owner;
	public Creature getOwner(){
		return owner;
	}
	
	private String name;
	public String getName(){
		return name;
	}
	
	private HierarchySpace(String name){
		this.name = name;
	}
	
	public void assignOwner(Human owner){
		if(isVacant){
			isVacant = false;
			this.owner = owner;
		}
		else{
			emptyHierarchy();
			assignOwner(owner);
		}
	}
	
	public void emptyHierarchy(){
		owner = null;
		isVacant = true;
	}
	
	public HierarchySpace VillageElder(){
		HierarchySpace h = new HierarchySpace("village elder");
		return h;
	}
	
	public HierarchySpace Chieftain(){
		HierarchySpace h = new HierarchySpace("chieftain");
		return h;
	}
	
}
