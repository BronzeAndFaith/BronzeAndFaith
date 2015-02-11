package BronzeAndFaith.Content;
import java.util.ArrayList;
import java.util.List;

import BronzeAndFaith.Game.CoverageZone;
import BronzeAndFaith.Game.World;
/*
 * ITEM BUILDER
 * 
 * Definition of item types, such as armor, weapons, tools, nature objects etc. that derive from the Item class.
 * 
 */
public class ItemBuilder {
	
	//formula for calculating values that depend on material hardness
	private int materialHardness(int val, Material material){
		return (20*material.hardness())/100;
	}
	
	private World world;
	
	public ItemBuilder(World world){
		this.world = world;
	}
	
	//NATURAL ITEMS
	public Item newRock(int x, int y, Material material){
		Item rock = new Item(x,y,"rock", material);
		rock.modifyVolume(1);
		rock.modifyWeight(1);
		rock.setItemImage(2);
		world.addItem(rock);
		return rock;
	}
	
	public Item newBranch(int x, int y, Material material){
		Item branch = new Item(x,y, "branch", material);
		branch.modifyVolume(2);
		branch.modifyWeight(material.weight()*2);
		branch.setItemImage(1);
		world.addItem(branch);
		return branch;
	}
	
	//CLOTHING
	public Item newShirt(int x, int y, Material material){
		Item shirt = new Item(x,y,"shirt", material);
		shirt.modifyVolume(5);
		shirt.modifyWeight(material.weight()*5);
		shirt.setItemImage(1); //TODO change image
		List<Integer> coverage = new ArrayList<Integer>();
		coverage.add(CoverageZone.BACK.index());
		coverage.add(CoverageZone.BELLY.index());
		coverage.add(CoverageZone.CHEST.index());
		coverage.add(CoverageZone.GROIN.index());
		coverage.add(CoverageZone.LEFTSHOULDER.index());
		coverage.add(CoverageZone.RIGHTSHOULDER.index());
		coverage.add(CoverageZone.LEFTUPPERARM.index());
		coverage.add(CoverageZone.RIGHTUPPERARM.index());

		shirt.makeArmor(coverage, 0.5f);
		shirt.setCommonDescription("\n A shirt is a common cloth garment with or without sleeves that is worn on the upper body. "
								+ "\n Many men wear it as an undergarment.");
		world.addItem(shirt);
		return shirt;
	}
	
	public Item newCap(int x, int y, Material material){
		Item cap = new Item(x,y,"cap", material);
		cap.modifyVolume(1);
		cap.modifyWeight(material.weight()*1);
		cap.setItemImage(1); //TODO change image
		List<Integer> coverage = new ArrayList<Integer>();
		coverage.add(CoverageZone.SKULL.index());
		cap.makeArmor(coverage, 0.7f);
		cap.setCommonDescription("\n A cap is a simple cover for the head and provides sufficient protection from cold weather.");
		world.addItem(cap);
		return cap;
		
	}
	
	//WEAPONS
	//Knives, Daggers
	public Item newKnife(int x, int y, Material material){
		Item knife = new Item(x,y,"knife", material);
		knife.modifyVolume(1);
		knife.modifyWeight(material.weight()*1);
		knife.setItemImage(1);
		knife.makeWeapon(0,materialHardness(15,material),materialHardness(5,material));
		knife.setCommonDescription("\n Even though there exist special knives for special tasks, simple knives are still used by everyone. "
				+ "\n This particular type of knife finds large use among kitchen wives."
				+ "\n Knives can still be deadly weapons in the right hand.");
		knife.setHardToHit(0.8f);
		world.addItem(knife);
		return knife;
	}
	
	public Item primitiveKnife(int x, int y, Material material){
		Item knife = new Item(x,y,"primitive knife", material);
		knife.modifyVolume(3);
		knife.modifyWeight(material.weight()*3);
		knife.setItemImage(1);
		knife.makeWeapon(0,materialHardness(3,material),0);
		knife.setCommonDescription("\n A sharp stone with a plant fiber handle. "
				+ "\n One must be truly desperate to rely on a sharp stone as a tool.");
		knife.setHardToHit(0.5f);
		world.addItem(knife);
		return knife;
	}
	
	public Item newHuntingKnife(int x, int y, Material material){
		Item knife = new Item(x,y,"hunting knife", material);
		knife.modifyVolume(3);
		knife.modifyWeight(material.weight()*3);
		knife.setItemImage(1);
		knife.makeWeapon(0,materialHardness(25,material),materialHardness(20,material));
		knife.setCommonDescription("\n The hunting knife is very sharp and pointy, which makes it ideal for hunting and carving meat. "
				+ "\n However, it is quite bulky when compared to other knives.");
		world.addItem(knife);
		return knife;
	}
	
	public Item newCeremonialDagger(int x, int y, Material material){
		Item knife = new Item(x,y,"ceremonial dagger", material);
		knife.modifyVolume(3);
		knife.modifyWeight(material.weight()*3);
		knife.setItemImage(1);
		knife.makeWeapon(0,materialHardness(15,material),materialHardness(10,material));
		knife.setCommonDescription("\n This is a ceremonial dagger, which means it is not really meant for cutting. "
				+ "\n Although there may be exceptions, ceremonial daggers often look better than they cut.");
		world.addItem(knife);
		return knife;
	}
	
	public Item newCarvingknife(int x, int y, Material material){
		Item knife = new Item(x,y,"carving knife", material);
		knife.modifyVolume(1);
		knife.modifyWeight(material.weight()*1);
		knife.setItemImage(1);
		knife.makeWeapon(0,materialHardness(45,material),materialHardness(15,material));
		knife.setCommonDescription("\n Carving knives are terribly sharp, but short. "
				+ "\n They can be used for crafting and decorating bones or wood."
				+ "\n They can cut very well, but they are of no use in combat situation.");
		knife.setHardToHit(0.6f);
		world.addItem(knife);
		return knife;
	}
	
	public Item newBroadKnife(int x, int y, Material material){
		Item knife = new Item(x,y,"broad knife", material);
		knife.modifyVolume(3);
		knife.modifyWeight(material.weight()*3);
		knife.setItemImage(1);
		knife.makeWeapon(0,materialHardness(25,material),materialHardness(2,material));
		knife.setCommonDescription("\n Broad knives are mostly used for hideworking."
				+ "\n Their broad edge and dull point are good for scraping meat from skin."
				+ "\n Other than that, they are inferior to other knives.");
		world.addItem(knife);
		return knife;
	}
	
	//Swords
	public Item newShortSword(int x, int y, Material material){
		Item sword = new Item(x,y,"short sword", material);
		sword.modifyVolume(3);
		sword.modifyWeight(material.weight()*3);
		sword.setItemImage(1);
		sword.makeWeapon(0, (35*material.hardness())/100, (25*material.hardness())/100);
		sword.setCommonDescription("\n The short sword lies somewhere between a normal sword and a dagger in length. "
				+ "\n It is one of the more underestimated weapons, but still as sharp and deadly as it's longer brothers."
				+ "\n Young boys mostly get one of these for training if their father is rich enough."
				);
		world.addItem(sword);
		return sword;
	}
	
	public Item newSword(int x, int y, Material material){
		Item sword = new Item(x,y,"short sword", material);
		sword.modifyVolume(5);
		sword.modifyWeight(material.weight()*5);
		sword.setItemImage(1);
		sword.makeWeapon(0, materialHardness(40,material), materialHardness(20,material));
		sword.setCommonDescription("\n This is a sword of medium size."
				+ "\n The sword is a reliable weapon for nobles and rich warriors."
				+ "\n As long as the sword is treated well, it is a versatile and deadly weapon."
				);
		world.addItem(sword);
		return sword;
	}
	
	public Item newCurvedSword(int x, int y, Material material){
		Item sword = new Item(x,y,"curved sword", material);
		sword.modifyVolume(6);
		sword.modifyWeight(material.weight()*6);
		sword.setItemImage(1);
		sword.makeWeapon(0, materialHardness(44,material), 0);
		sword.setCommonDescription("\n This is a curved sword of medium size."
				+ "\n The curved sword is only meant for cutting, nothing else."
				);
		world.addItem(sword);
		return sword;
	}
	
	//Spears
	public Item newShortSpear(int x, int y, Material material){
		Item spear = new Item(x,y,"short spear",material);
		spear.modifyVolume(5);
		spear.modifyWeight(material.weight()*5);
		spear.setItemImage(1);
		spear.makeWeapon(materialHardness(10,material), 0, materialHardness(40,material));
		spear.setCommonDescription("\n Spears are primitive weapons that are quite common and cheap."
				+ "\n Everyone can use the spear for hunting but experienced spearmasters can be outstanding warriors in battle."
				+ "\n Commonly used as a secondary weapon or combined with a shield, short spears mean to penetrate armor.");
		world.addItem(spear);
		return spear;
	}
	
	public Item newLongSpear(int x, int y, Material material){
		Item spear = new Item(x,y,"long spear",material);
		spear.modifyVolume(14);
		spear.modifyWeight(material.weight()*10);
		spear.setItemImage(1);
		spear.makeWeapon(materialHardness(20,material), 0, materialHardness(45,material));
		spear.setCommonDescription("\n Spears are primitive weapons that are quite common and cheap."
				+ "\n The long spear is especially useful in combat or for big game hunt."
				+ "\n It should be wielded in both hands to take advantage of it's length swing it.");
		world.addItem(spear);
		return spear;
	}
	
	//Axes
	public Item newPrimitiveAxe(int x, int y, Material material){
		Item axe = new Item(x,y,"primitive axe",material);
		axe.modifyVolume(7);
		axe.modifyWeight(material.weight()*7);
		axe.setItemImage(1);
		axe.makeWeapon(materialHardness(12,material), materialHardness(14,material), 0);
		axe.setCommonDescription("\n The primitive Axe is basically a sharp stone on a stick."
				+ "\n Do not expect it to do wonders or to outlive a few weak cuts.");
		axe.setHardToHit(0.7f);
		world.addItem(axe);
		return axe;
	}
	
	public Item newHandAxe(int x, int y, Material material){
		Item axe = new Item(x,y,"hand axe",material);
		axe.modifyVolume(6);
		axe.modifyWeight(material.weight()*6);
		axe.setItemImage(1);
		axe.makeWeapon(materialHardness(10,material), materialHardness(25,material), 0);
		axe.setCommonDescription("\n The hand axe is a fine tool, small and nimble compared to others yet sharp enough to do some serious work."
				+ "\n It is extremely versatile, but experienced crafters will rely on special axes."
				+ "\n The hand axe is a fine, affordable weapon that can be used with one hand.");
		world.addItem(axe);
		return axe;
	}
	
	public Item newBroadAxe(int x, int y, Material material){
		Item axe = new Item(x,y,"broad axe",material);
		axe.modifyVolume(8);
		axe.modifyWeight(material.weight()*8);
		axe.setItemImage(1);
		axe.makeWeapon(materialHardness(2,material), materialHardness(30,material), 0);
		axe.setCommonDescription("\n The broad axe is good tool for lumber crafting due to its wide blade."
				+ "\n It less useful in combat than a hand axe due to its clumsy head but it is still quite sharp. ");
		axe.setHardToHit(0.8f);
		world.addItem(axe);
		return axe;
	}
	
	public Item newCombatAxe(int x, int y, Material material){
		Item axe = new Item(x,y,"combat axe",material);
		axe.modifyVolume(8);
		axe.modifyWeight(material.weight()*8);
		axe.setItemImage(1);
		axe.makeWeapon(materialHardness(15,material), materialHardness(36,material), 0);
		axe.setCommonDescription("\n The combat axe is meant to be wielded with both hands."
				+ "\n It has a blade similiar to the broad axe, but is easier to handle."
				+ "\n When swung well and with enough power, it can cut a man's neck.");
		world.addItem(axe);
		return axe;
	}
	
	//Clubs
	
	public Item newCudgel(int x, int y, Material material){
		Item club = new Item(x,y,"cudgel",material);
		club.modifyVolume(2);
		club.modifyWeight(material.weight()*2);
		club.setItemImage(1);
		club.makeWeapon((int)(materialHardness(15,material)*club.weight()), 0, 0);
		club.setCommonDescription("\n A cudgel is short and quite unspectacular."
				+ "\n It is light, but well balanced for a beating."
				+ "\n Dealing beatings with a cudgel is the best way to avoid killing people by accident.");
		world.addItem(club);
		return club;
	}
	
	public Item newClub(int x, int y, Material material){
		Item club = new Item(x,y,"club",material);
		club.modifyVolume(4);
		club.modifyWeight(material.weight()*4);
		club.setItemImage(1);
		club.makeWeapon((int)(materialHardness(22,material)*club.weight()), 0, 0);
		club.setCommonDescription("\n Clubs can be quite heavy."
				+ "\n They are the perfect choice for breaking bones.");
		world.addItem(club);
		return club;
	}
	
	public Item newHammer(int x, int y, Material material){
		Item club = new Item(x,y,"hammer",material);
		club.modifyVolume(3);
		club.modifyWeight(material.weight()*3);
		club.setItemImage(1);
		club.makeWeapon((int)(materialHardness(20,material)*club.weight()), 0, 0);
		club.setCommonDescription("\n The hammer has a massive head on a thin handle."
				+ "\n It is meant to be used as a tool and quite short.");
		club.setHardToHit(0.8f);
		world.addItem(club);
		return club;
	}
	
	public Item newMace(int x, int y, Material material){
		Item club = new Item(x,y,"mace",material);
		club.modifyVolume(4);
		club.modifyWeight(material.weight()*4);
		club.setItemImage(1);
		club.makeWeapon((int)(materialHardness(28,material)*club.weight()), 0, 0);
		club.setCommonDescription("\n Being a mix of hammer and club, the mace is destined to break bones and kill people."
				+ "\n It is heavy, but perfect for combat as it can be swung with one hand.");
		world.addItem(club);
		return club;
	}
	
	public Item newWarHammer(int x, int y, Material material){
		Item club = new Item(x,y,"war hammer",material);
		club.modifyVolume(8);
		club.modifyWeight(material.weight()*8);
		club.setItemImage(1);
		club.makeWeapon((int)(materialHardness(46,material)*club.weight()), 0, 0);
		club.setCommonDescription("\n The war hammer is a brutal tool for combat."
				+ "\n It is heavy and bulky, but it can be wielded with two hands.");
		world.addItem(club);
		return club;
	}
	
	//Tools
	
	public Item newSickle(int x, int y, Material material){
		Item tool = new Item(x,y,"sickle",material);
		tool.modifyVolume(3);
		tool.modifyWeight(material.weight()*3);
		tool.setItemImage(1);
		tool.makeWeapon(0, materialHardness(10,material), 0);
		tool.setCommonDescription("\n The sickle can be used to gather field products or herbs");
		world.addItem(tool);
		return tool;
	}

}
