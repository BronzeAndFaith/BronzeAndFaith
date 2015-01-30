
public class Human extends Creature{
	
	/*Values for all body parts in complex health system
	 * Almost every part is made of a skin, muscle and bone layer.
	 * Below or between the layers are organs.
	 * Depending on weapon type, the top layer must be destroyed in order to pass the lower layer.
	 * Destroying tissue or organs will result in blood loss per turn.
	 * Definite death will happen if the brain or other organs are damaged or when there is no more blood. *  
	 * */
	
	//Dynamic Values
		//SKULL
			public int skinSkull = 100;
			public int muscleSkull = 20;
			public int boneSkull = 100;
			public int brain=3;
			public int headArtery = 3;
			
			/*FACE
			Remember that face organs are not protected by layers 
			can also hit brain or face artery*/
			
			public int skinFace = 40;
			public int muscleFace = 30;
			public int boneFace = 50;
			
			public int leftEye=20;
			public int rightEye=20;
			public int leftEar=30;
			public int rightEar=30;
			public int tongue=30;
			public int leftCheek=40;
			public int rightCheek=40;
			public int forehead=40;
			
			//NECK
			public int skinNeck=80;
			public int muscleNeck=40;
			public int boneNeck=40;
			public int neckArtery=3;
			
			//LEFT SHOULDER
			public int skinShoulderLeft = 100;
			public int muscleShoulderLeft = 80;
			public int boneShoulderLeft = 70;
			
			//RIGHT SHOULDER
			public int skinShoulderRight = 100;
			public int muscleShoulderRight = 80;
			public int boneShoulderRight = 70;
			
			//CHEST
			//has small chance of letting a point hit trespass bones completely
			public int skinChest = 100;
			public int muscleChest = 90;
			public int boneChest = 70;
			
			public int leftLung = 5;
			public int rightLung = 5;
			public int heart = 3;	
			
			//BELLY
			public int skinBelly = 80;
			public int muscleBelly = 70; //default value, can be upped to 100 if trained
			public int intestines = 10;
			
			//GROIND
			//Note: the reproducton organs are included to allow gelding,
			//aswell as making it impossible to get children
			//and simulate sicknesses
			public int skinGroin = 40;
			public int muscleGroin = 30;
			
			public int leftNut = 3;//for females, it is called ovary
			public int rightNut = 3;//for females, it is called ovary
			public int penis = 3;//for females, it is called vagina
			
			//BACK
			public int skinBack=100;
			public int muscleBack=90;
			public int boneBack=90;
			
			//Left Arm
			public int skinArmUpperLeft = 100;
			public int muscleArmUpperLeft = 100;
			public int boneArmUppperLeft = 70;
			
			public int skinArmLowerLeft = 90;
			public int muscleArmLowerLeft = 90;
			public int boneArmLowerLeft = 60;
			
			public int skinHandLeft = 50; //extremely variable, can be injured by tools, softened or hardened by job
			public int muscleHandLeft = 50;
			public int boneHandLeft = 50;
			
			
			//RIGHT ARM
			public int skinArmUpperRight = 100;
			public int muscleArmUpperRight = 100;
			public int boneArmUpperRight = 70;
			
			public int skinArmLowerRight = 90;
			public int muscleArmLowerRight = 90;
			public int boneArmLowerRight = 60;
			
			public int skinHandRight = 50;
			public int muscleHandRight = 50;
			public int boneHandRight = 50;
			
			public int skinLegUpperLeft = 100;
			public int muscleLegUpperLeft = 100;
			public int boneLegUpperLeft = 100;
			public int arteryLeftLeg = 3;
			
			public int skinLegLowerLeft = 90;
			public int muscleLegLowerLeft = 90;
			public int boneLegLowerLeft = 90;
			
			public int skinFootLeft = 50; //can harden
			public int muscleFootLeft = 50;
			public int boneFootLeft = 50;
			
			public int skinLegUpperRight = 100;
			public int muscleLegUpperRight = 100;
			public int boneLegUpperRight = 100;
			public int arteryRightLeg = 3;
			
			public int skinLegLowerRight = 90;
			public int muscleLegLowerRight = 90;
			public int boneLegLowerRight = 90;
			
			public int skinFootRight = 50;
			public int muscleFootRight = 50;
			public int boneFootRight = 50;
			



		//Static Values
		
		//SKULL
		public int skinSkullMax = 100;
		public int muscleSkullMax = 20;
		public int boneSkullMax = 100;
		public int brainMax=3;
		public int headArteryMax = 3;
		
		/*FACE
		Remember that face organs are not protected by layers 
		can also hit brain or face artery*/
		
		public int skinFaceMax = 40;
		public int muscleFaceMax = 30;
		public int boneFaceMax = 50;
		
		public int leftEyeMax=20;
		public int rightEyeMax=20;
		public int leftEarMax=30;
		public int rightEarMax=30;
		public int tongueMax=30;
		public int leftCheekMax=40;
		public int rightCheekMax=40;
		public int foreheadMax=40;
		
		//NECK
		public int skinNeckMax=80;
		public int muscleNeckMax=40;
		public int boneNeckMax=40;
		public int neckArteryMax=3;
		
		//LEFT SHOULDER
		public int skinShoulderLeftMax = 100;
		public int muscleShoulderLeftMax = 80;
		public int boneShoulderLeftMax = 70;
		
		//RIGHT SHOULDER
		public int skinShoulderRightMax = 100;
		public int muscleShoulderRightMax = 80;
		public int boneShoulderRightMax = 70;
		
		//CHEST
		//has small chance of letting a point hit trespass bones completely
		public int skinChestMax = 100;
		public int muscleChestMax = 90;
		public int boneChestMax = 70;
		
		public int leftLungMax = 5;
		public int rightLungMax = 5;
		public int heartMax = 3;	
		
		//BELLY
		public int skinBellyMax = 80;
		public int muscleBellyMax = 70; //default value, can be upped to 100 if trained
		public int intestinesMax = 10;
		
		//GROIND
		//Note: the reproducton organs are included to allow gelding someone,
		//aswell as making it impossible to get children
		//and simulate sicknesses
		public int skinGroinMax = 40;
		public int muscleGroinMax = 30;
		
		public int leftNutMax = 3;//for females, it is called ovary
		public int rightNutMax = 3;//for females, it is called ovary
		public int penisMax = 3;//for females, it is called vagina
		
		//BACK
		public int skinBackMax=100;
		public int muscleBackMax=90;
		public int boneBackMax=90;
		
		//Left Arm
		public int skinArmUpperLeftMax = 100;
		public int muscleArmUpperLeftMax = 100;
		public int boneArmUppperLeftMax = 70;
		
		public int skinArmLowerLeftMax = 90;
		public int muscleArmLowerLeftMax = 90;
		public int boneArmLowerLeftMax = 60;
		
		public int skinHandLeftMax = 50; //extremely variable, can be injured by tools, softened or hardened by job
		public int muscleHandLeftMax = 50;
		public int boneHandLeftMax = 50;
		
		
		//RIGHT ARM
		public int skinArmUpperRightMax = 100;
		public int muscleArmUpperRightMax = 100;
		public int boneArmUpperRightMax = 70;
		
		public int skinArmLowerRightMax = 90;
		public int muscleArmLowerRightMax = 90;
		public int boneArmLowerRightMax = 60;
		
		public int skinHandRightMax = 50;
		public int muscleHandRightMax = 50;
		public int boneHandRightMax = 50;
		
		public int skinLegUpperLeftMax = 100;
		public int muscleLegUpperLeftMax = 100;
		public int boneLegUpperLeftMax = 100;
		public int arteryLeftLegMax = 3;
		
		public int skinLegLowerLeftMax = 90;
		public int muscleLegLowerLeftMax = 90;
		public int boneLegLowerLeftMax = 90;
		
		public int skinFootLeftMax = 50; //can harden
		public int muscleFootLeftMax = 50;
		public int boneFootLeftMax = 50;
		
		public int skinLegUpperRightMax = 100;
		public int muscleLegUpperRightMax = 100;
		public int boneLegUpperRightMax = 100;
		public int arteryRightLegMax = 3;
		
		public int skinLegLowerRightMax = 90;
		public int muscleLegLowerRightMax = 90;
		public int boneLegLowerRightMax = 90;
		
		public int skinFootRightMax = 50;
		public int muscleFootRightMax = 50;
		public int boneFootRightMax = 50;

		/********************************************************************************************/
		
		private CreatureAi ai;
		public void setCreatureAi(CreatureAi ai) {this.ai = ai;}
		public CreatureAi ai() {return ai;}	
		
		/**
		 * A Human is a Creature with a complex Health System
		 * 
		 * @param world World this creature lives in
		 * @param x cooridnate
		 * @param y coordinate
		 * @param imageIndex Initial image index for display
		 */
	public Human(World world, int x, int y, int imageIndex) {
		super(world, x, y, imageIndex);
	}

	
}
