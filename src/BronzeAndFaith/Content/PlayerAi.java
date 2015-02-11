package BronzeAndFaith.Content;
import java.util.List;


public class PlayerAi extends CreatureAi{

	private List<String> message;
	public List<String>message(){return message;}
	
	public PlayerAi(Human creature, List<String> messages){
		super(creature);
		this.message = messages;
	}
	
	@Override
	public void addMessage(String message){
		this.message.add(message);
	}
	
	
}
