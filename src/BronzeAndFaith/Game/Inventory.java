package BronzeAndFaith.Game;

import BronzeAndFaith.Content.Item;

public class Inventory {

	/*
	 * CORE PROPERTIES
	 * 
	 * AND PUBLIC VALUES
	 * */
	private Item[] items;
	public Item[] getItems(){return items;}
	public Item getItem(int i){return items[i];}
	
	/**The basic volume of the inventory. Will be changed according to creature properties.*/
	private int baseVolume = 10;
	
	/**Maximum volume of Items that can fit into the inventory. Can be modified by having container items, such as a backpack.*/
	private float maxVolume;
	public float maxVolume(){return maxVolume;}
	public void setMaxVolume(float amount){maxVolume += amount;}
	
	/*CONSTRUCTOR*/
	/**creates an Item[] array with the given size*/
	public Inventory(int max){
		items = new Item[max];
		this.maxVolume = baseVolume;
	}
	
	
	/*METHODS*/
	/**Returns the total weight of all the items in the inventory.*/
	public float weight(){
		float weight = 0;
		for(int i = 0; i < items.length; i++){
			if(items[i]!= null){
				weight+=items[i].weight();
			}
		}
		return weight;
	};

	/**Returns the total volume of all the items in the inventory.*/
	public float volume(){
		float volume = 0;
		for(int i = 0; i < items.length; i++){
			if(items[i]!= null){
				volume+=items[i].volume();
			}
		}
		return volume;
	}
	
	/*Add item to array*/
	public void add(Item item){
		for(int i = 0; i < items.length; i++){
			if(items[i] == null){
				items[i] = item;
				break;
			}	
		}
	}
	
	/*remove item from array, needs precise item*/
	public void remove(Item item){
		for(int i = 0; i < items.length; i++){
			if(items[i] == item){
				items[i] = null;
				return;
			}
		}
	}
	
	/*remove item form array, using generic item name*/
	public void remove(String name){
		for(int i = 0; i < items.length; i++){
			if(items[i] != null){
				if(items[i].name() == name)
					items[i] = null;
					return;
			}
		}
	}
	
	/*returns true, if array length is full*/
	public boolean isFull(){
	    int size = 0;
	    for (int i = 0; i < items.length; i++){
	        if (items[i] != null)
	             size++;
	    }
	    return size == items.length;
	}
	
	/*return true if total item volume exceeds maxVolume*/
	public boolean noPlace(){
		return volume() >= maxVolume();
	}
	
	/*return true if total item volume + parameter value would make the inventory full*/
	public boolean noPlace(float f){
		return volume()+f >= maxVolume();
	}
	
	/*return true if parameter Item is contained in this inventory*/
	public boolean contains(Item item) {
		for (Item i : items){
			if (i == item)
				return true;
		}
		return false;
	}
	
	/*return true if an item with the given generic name is contained in this inventory*/
	public boolean contains(String itemname) {
		for (int i = 0; i < items.length; i++){
	        if (items[i] != null){
	        	if(items[i].name() == itemname)
	        		return true;
	        }   
	    }
		return false;
	}
}