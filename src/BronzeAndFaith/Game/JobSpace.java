package BronzeAndFaith.Game;

import BronzeAndFaith.Content.Creature;
import BronzeAndFaith.Content.Human;

/**
 * Open Job Slots for Villages to use. They may or may not be filled by someone.
 * 
 * @author Jeremy
 *
 */

public class JobSpace {

	public boolean isVacant = false;
	private Human worker;
	public Creature getWorker(){
		return worker;
	}
	
	private String name;
	public String getName(){
		return name;
	}
	
	//low-tech jobs
	private JobSpace(String name){
		this.name = name;
	}
	
	public void assignWorker(Human worker){
		if(isVacant){
			isVacant = false;
			this.worker = worker;
		}
		else{
			emptyJob();
			assignWorker(worker);
		}
	}
	
	public void emptyJob(){
		worker = null;
		isVacant = true;
	}
	
	public JobSpace NewFisherJob(){
		JobSpace job = new JobSpace("fisher");
		return job;
	}
	
	public JobSpace NewFarmerJob(){
		JobSpace job = new JobSpace("farmer");
		return job;
	}
	
	public JobSpace NewWoodcutterJob(){
		JobSpace job = new JobSpace("woodcutter");
		return job;
	}
	
	public JobSpace NewCarpenterJob(){
		JobSpace job = new JobSpace("carpenter");
		return job;
	}
	
	public JobSpace NewGathererJob(){
		JobSpace job = new JobSpace("gatherer");
		return job;
	}
	
	public JobSpace NewMinerJob(){
		JobSpace job = new JobSpace("miner");
		return job;
	}
	
	public JobSpace NewMasonJob(){
		JobSpace job = new JobSpace("mason");
		return job;
	}
	
	public JobSpace NewHunterJob(){
		JobSpace job = new JobSpace("hunter");
		return job;
	}
	
	public JobSpace NewPotterJob(){
		JobSpace job = new JobSpace("potter");
		return job;
	}
	
}
