package BronzeAndFaith.Map;

import java.util.ArrayList;
import java.util.List;

public class VillageSimulation {

	private List<Village> villages = new ArrayList<Village>();
	public void addVillage(Village village){
		villages.add(village);
	}
	public void removeVillage(Village village){
		villages.remove(village);
	}
	
	public VillageSimulation(int steps){
		for(Village v:villages){
			int workers = 5;
			
		}
	}
	
	//length of simulation
	//foreach village
	
	//give the village some starting workers and starting jobs
	//build main building

	
	//increment time and score
	//assess value
	//assess main resource, give extra job
	//assess availability of wood
	
	//techlevel 1
	//institute 1st degree hierarchy(village elder)
	//at x foodscore, create children, increase room_needed, give extra job places (farmer, gatherer)
	//at x lumberscore, create new building, increase room_spent, give extra job places (woodcutter, carpenter)
	//at x rockscore, create new building, increase room_spent, give extra job places(miner, mason)
	//at x number of inhabitants, increase size of territory, give extra job places(hunter)
	//at x clayscore, give extra job places(clay worker, potter)
	//add farmplots
	
	//techlevel 2
	//tech 1 still applies
	//2nd degree hierarchy(chieftain, elected)
	//create jobs (crafter, butcher, herbalist)
	//at x metalscore, create forge, increase manufactoring potential, give job places(miner, smelter, smith)
	//at x farmscore, create farmhouse, weavery, give extra job (weaver, animal caretaker, clothmaker)
	//at x totalscore, create tanner & leathermaker
	
	//techlevel 3
	//institute 3rd degree hierarchy (chieftain and warriors)
	//create jobs(armor maker, weaponsmith, warrior)
	
	
	//at x totalscore, increase technology level
	
	//chance for random event:
		//medium - fire destroys single building
		//low - fire destroys part of the village
		//low - plague kills number of people
		//high - accident kills single person
		//low - raid destroys much of the score
		//medium - bad crop reduces foodscore
		//low - good weather boosts foodscore
		//low - mining accident sets back metalscore and rockscore
		//low - new ore vein found increases metalscore
		//low - animals disappear, reducing foodscore
		
	
	//at the end, fill each job place with a person
	
}
