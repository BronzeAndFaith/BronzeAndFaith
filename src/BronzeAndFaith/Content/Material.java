package BronzeAndFaith.Content;

public enum Material {
	//(String name, int hardness, int value, int durability, int weight)
	CLOTH_WOOL("wool", 2,20,20,12),
	CLOTH_NETTLE("nettle", 5, 10, 15, 10),
	CLOTH_FLAX("flax", 5, 12, 15, 10),
	
	LEATHER_THIN("thin leather", 8, 25, 25, 15),
	LEATHER_THICK("thick leather", 20, 50, 50, 30),
	
	FUR_HARE("hare fur", 8, 35, 25, 20),
	FUR_BEAR("bear fur", 20, 60, 50, 40),
	FUR_DEER("deer fur", 12, 45, 35, 30),
		
	WOOD_PINE("pine", 30, 5, 50, 10),
	WOOD_OAK("oak", 70, 5, 60, 10),
	WOOD_MAPLE("maple", 60, 5, 50, 10),
	WOOD_YEW("yew", 40, 5, 50, 10),
	
	METAL_BRONZE("bronze", 95, 90, 95, 90),
	METAL_COPPER("copper", 75, 75, 85, 90),
	METAL_TIN("tin", 65, 65, 70, 75),
	METAL_SILVER("silver", 85, 90, 85, 90),
	METAL_GOLD("gold", 75, 95, 85, 95),
	
	BONE("bone", 50, 10, 30, 8),
	
	CLAY_RAW("raw clay", 2, 2,5,20),
	CLAY_BURNT("burnt clay", 80, 30, 30,12),
	
	ROCK("rock", 100, 2, 100, 50);
	
	private String writtenName;
	public String writtenName(){return writtenName;}
	
	private final int hardness;
	public int hardness(){return hardness;}
	
	private final int value;
	public int value(){return value;}
	
	private final int durability;
	public int durability(){return durability;}
	
	private final int weight;
	public int weight(){return weight;}
	
	/**Defines Materials that are used on a wide number of objects and has constant values
	 * @param hardness non-flexibility of the material, from 0 to 100
	 * @param value how much is 1 kg of this material worth, for reference only
	 * @param durability how durable is this material, how much damage can it take before breaking, from 0 to 100
	 * @param weight how much should 1 volume unit of this material weigh in tenths of kg (1 weight = 0,1kg)
	 * */
	Material(String name, int hardness, int value, int durability, int weight){
		this.writtenName = name;
		this.hardness= hardness;
		this.value=value;
		this.durability=durability;
		this.weight=weight;
	}
}
