import java.util.ArrayList;
import java.util.List;


public class PlayerAi extends CreatureAi{

	int x,y;
	
	private List<String> message;
	public List<String>message(){return message;}
	
	public PlayerAi(Human creature, List<String> messages){
		super(creature);
		this.message = messages;
	}
	
	@Override
	public void addMessage(String message){
		System.out.println("Got this far");
		this.message.add(message);
	}
	
	
}
