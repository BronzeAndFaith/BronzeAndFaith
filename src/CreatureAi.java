
public class CreatureAi {
	protected Creature creature;
	
	public CreatureAi (Creature creature){
		this.creature = creature;
		this.creature.setCreatureAi(this);
	}
	
	public void addMessage(String message){
	}
}
