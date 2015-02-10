import java.util.ArrayList;
import java.util.List;


public class Creature {

	private int x,y;
	public int getX(){return this.x;}
	public int getY(){return this.y;}
	public void setX(int x){this.x=x;}
	public void setY(int y){this.y=y;}
	
	int imageIndex; //image in tilesheet creatures.png
	
	protected World world;
	public World world(){return world;}
	
	private CreatureAi ai;
	public void setCreatureAi(CreatureAi ai) {this.ai = ai;}
	public CreatureAi ai() {return ai;}	
	
	private boolean isAlive = true;
	public boolean isAlive() {return isAlive;}
		
	private Inventory inventory;
	public Inventory inventory(){return inventory;}
	
	private ArrayList<ArrayList<Item>> coverZones;
	
	private ArrayList<Item> skullArmor;
	private ArrayList<Item> faceArmor;
	private ArrayList<Item> neckArmor;
	private ArrayList<Item> chestArmor;
	private ArrayList<Item> bellyArmor; 
	private ArrayList<Item> groinArmor; 
	private ArrayList<Item> backArmor;
	private ArrayList<Item> leftShoulderArmor;
	private ArrayList<Item> leftUpperArmArmor;
	private ArrayList<Item> leftLowerArmArmor;
	private ArrayList<Item> leftHandArmor;
	private ArrayList<Item> leftUpperLegArmor;
	private ArrayList<Item> leftLowerLegArmor;
	private ArrayList<Item> leftFootArmor;
	private ArrayList<Item> rightShoulderArmor;
	private ArrayList<Item> rightUpperArmArmor;
	private ArrayList<Item> rightLowerArmArmor;
	private ArrayList<Item> rightHandArmor;
	private ArrayList<Item> rightUpperLegArmor;
	private ArrayList<Item> rightLowerLegArmor;
	private ArrayList<Item> rightFootArmor;
	
	private Item weapon;
	public Item weapon(){return weapon;}
	
	
	
	/**Create a new creature and put it at the coordinates
	 * @param world Creature needs to inherit a world
	 * @param x coordinate
	 * @param y coordinate
	 * @param imageIndex the index of the image on the tileset
	 * */
	public Creature(World world, int x, int y, int imageIndex){
		this.world = world;
		this.x = x;
		this.y = y;
		this.imageIndex = imageIndex;
		inventory = new Inventory(30);
		skullArmor = new ArrayList<Item>();
		faceArmor = new ArrayList<Item>();
		neckArmor = new ArrayList<Item>();
		chestArmor = new ArrayList<Item>();
		bellyArmor = new ArrayList<Item>();
		groinArmor = new ArrayList<Item>();
		backArmor = new ArrayList<Item>();
		leftShoulderArmor = new ArrayList<Item>();
		leftUpperArmArmor = new ArrayList<Item>();
		leftLowerArmArmor = new ArrayList<Item>();
		leftHandArmor = new ArrayList<Item>();
		leftUpperLegArmor = new ArrayList<Item>();
		leftLowerLegArmor = new ArrayList<Item>();
		leftFootArmor = new ArrayList<Item>();
		rightShoulderArmor = new ArrayList<Item>();
		rightUpperArmArmor = new ArrayList<Item>();
		rightLowerArmArmor = new ArrayList<Item>();
		rightHandArmor = new ArrayList<Item>();
		rightUpperLegArmor = new ArrayList<Item>();
		rightLowerLegArmor = new ArrayList<Item>();
		rightFootArmor = new ArrayList<Item>();
		
		coverZones = new ArrayList<ArrayList<Item>>();
		updateCoverList();
	}
	

	public void move(int x, int y){
		int mx = this.x+x;
		int my = this.y+y;
		//only move if other tile is not blocked by type or creature
		Creature other = world.creature(mx, my);
		if (other == null){
			if(!world.isBlocked(mx, my)){
				this.x = mx;
				this.y =my;
			}
		}
	}
	

	public void pickupFromOwnLocation(){
        Item item = world.item(x, y);
     
        if (inventory.isFull() || item == null){
        	System.out.println("Inventory full");
        } else {
            world.remove(item);
            inventory.add(item);
        }
		//updateCoverList();

    }
	

	public void pickupItem(Item item){
         if (inventory.isFull() || item == null){
        	System.out.println("inventory full");
        } else {
            world.remove(item);
            inventory.add(item);
        }
		//updateCoverList();
    }
	
	public void dropItem(Item item){
		unequip(item);
		inventory.remove(item);
		world.addItem(item, x, y);
		System.out.println("dropped");
		updateCoverList();

	}
	
	/**Puts Item into appropriate slot. If not already in Inventory, it goes there.*/
	public void equip(Item item){
		if(!inventory.contains(item)){
			pickupItem(item);
		}
		
		if (item.isWeapon()){
			unequip(weapon);
			weapon = item;
		} else if(item.isArmor()){
			
			List<Integer>coverage = item.coverage();
			int i = 0;
			for(ArrayList<Item> l : coverZones){
				if(coverage.contains(i)){
					if(!l.contains(item)){l.add(item); System.out.println("equipped Item");}
				}  
				i++;
			}
			updateCoverList();
		}

	}
	
	/**Unequip Item, put back to inventory*/
	public void unequip(Item item){
		if (item == null)
			return;
		
		if (item == weapon){weapon = null;}
		
		else if(item.isArmor()){
			
			List<Integer>coverage = item.coverage();
			int i = 0;
			for(ArrayList<Item> l : coverZones){
				if(coverage.contains(i)){
					if(l.contains(item)){l.remove(item); System.out.println("removed Item");}
				}  
				i++;
			}
		}
		updateCoverList();
	}
	
	public boolean isEquipped(Item item){
		updateCoverList();
		if(item.isArmor() || item.isWeapon()){
			if (weapon == item)return true;
			for(ArrayList<Item> l : coverZones){
				if (l != null){
					if(l.contains(item)){return true;}
				}
				else break;
			}
			return false;
		}
		return false;	
	}
	
	private void updateCoverList(){
		coverZones.clear();
		
		coverZones.add(skullArmor);
		coverZones.add(faceArmor);
		coverZones.add(neckArmor);
		coverZones.add(chestArmor);
		coverZones.add(bellyArmor);
		coverZones.add(groinArmor);
		coverZones.add(backArmor);
		coverZones.add(leftShoulderArmor);
		coverZones.add(leftUpperArmArmor);
		coverZones.add(leftLowerArmArmor);
		coverZones.add(leftHandArmor);
		coverZones.add(leftUpperLegArmor);
		coverZones.add(leftLowerLegArmor);
		coverZones.add(leftFootArmor);
		coverZones.add(rightShoulderArmor);
		coverZones.add(rightUpperArmArmor);
		coverZones.add(rightLowerArmArmor);
		coverZones.add(rightHandArmor);
		coverZones.add(rightUpperLegArmor);
		coverZones.add(rightLowerLegArmor);
		coverZones.add(rightFootArmor);
	}
}
