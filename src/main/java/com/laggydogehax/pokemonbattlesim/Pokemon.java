package com.laggydogehax.pokemonbattlesim;

import java.util.Random;

class Pokemon{
	int baseHP,baseDEF,baseATK,baseSPEED;
	int currentHP,currentDEF,currentATK,currentSPEED;
	int numberOfHits=1;
	int extraDmg=0;
	String name,type="";
	String type2="";
	boolean isPoisoned,isBurning,isParalized,healingOverTime,megaEvolved,strike,permaBurn,energyDrink;
	Ability ability=null;
	String[] weakTo = new String[5];	//listing-weaknesses
	int[] weakToMults = new int[18]; //multis!!!!!!!!!!!!!!!
	String[] resists= new String[5]; //listing-resistances
	String[][] moveset = new String[2][4]; //[0=Name; 1=Type][Slot]
	String[] items = null;

	/* epic typing list:
		"Fire"=0,     "Water"=1,    "Grass"=2,
		"Normal"=3,   "Fighting"=4, "Flying"=5,
		"Poison"=6,   "Ground"=7,   "Rock"=8,
		"Bug"=9,      "Ghost"=10,   "Steel"=11,
		"Electric"=12,"Psychic"=13, "Ice"=14,
		"Dragon"=15,  "Dark"=16,    "Fairy"=17
	*/

	protected boolean resistsType(String typ){
		String[] vec = PokemonMaker3000.getTypesVector();

		for (int i=0;i<vec.length;i++){
			if(typ.contains(vec[i])){
				if(this.weakToMults[i]<0){
					return true;
				}
			}
		}
		
		return false;
	}
	
	protected boolean resistsType1(String pkmnType){
		for(int i=0;i<this.resists.length;i++){
			if(pkmnType.contains(this.resists[i])){
				return true;
			}
		}
		return false;
	}
	
	protected boolean resistsType2(String pkmnType){
		try{//second type
			String[] rest2 = getListOfWnR(this.type2,1);
			
			for(int i=0;i<rest2.length;i++){
				if(pkmnType.contains(rest2[i])){
					return true;
				}
			}
			
		}catch(Exception e){
			return false;
		}
		return false;
	}

	protected boolean isWeakToType(String typ){
		String[] vec = PokemonMaker3000.getTypesVector();
		
		for (int i=0;i<vec.length;i++){
			if(typ.contains(vec[i])){
				if(this.weakToMults[i]>0){
					return true;
				}
			}
		}
		
		return false;
	}

	protected boolean isWeakToType1(String pkmnType){
		for(int i=0;i<this.weakTo.length;i++){
			if(pkmnType.contains(this.weakTo[i])){
				return true;
			}
		}
		return false;
	}
	
	protected boolean isWeakToType2(String pkmnType){
		try{//second type
			String[] weak2 = getListOfWnR(this.type2,0);
			
			for(int i=0;i<weak2.length;i++){
				if(pkmnType.contains(weak2[i])){
					return true;
				}
			}
			
		}catch(Exception e){
			return false;
		}
		return false;
	}
	
	protected int getWRValue(String typ){
		String[] vec = PokemonMaker3000.getTypesVector();
		
		for(int i=0;i<vec.length;i++){
			if(typ.contains(vec[i])){
				return this.weakToMults[i];
			}
		}
		
		return 0;
	}

	protected boolean isOfType(String typ){
		if(this.type.equals(typ)){
			return true;
		}
		
		try{
			if(this.type2.equals(typ)){
				return true;
			}
		}catch(Exception e){
			return false;
		}
		
		return false;
	}

	protected boolean hasStatusAilment(){
		if (this.isParalized || this.isBurning || this.isPoisoned) {
			return true;
		}//healing over time doesnt count!
		else{
			return false;
		}
	}
	
	protected boolean moveIsAnAttack(int selec){
		if(this.moveset[1][selec].contains("Attack")){
			return true;
		}else{
			return false;
		}
	}

	protected void resetStats(){
		if(this.currentHP<=0){
			this.deMegaEvolve();
		}
		this.currentATK=this.baseATK;
		this.currentDEF=this.baseDEF;
		this.currentSPEED=this.baseSPEED;
		this.isBurning=false;
		this.isParalized=false;
		this.isPoisoned=false;
		this.healingOverTime=false;
		//this.numberOfHits=1;
		this.extraDmg=0;
	}

	protected void raiseStat(String stat){
		switch(stat){
			case "ATK":
				this.currentATK+=(this.baseATK/4);
			break;
			case "DEF":
				this.currentDEF+=(this.baseDEF/4);
				if(this.currentDEF>300){
					this.currentDEF=300; //nu uh
				}
			break;
			case "SPEED":
				this.currentSPEED+=(this.baseSPEED/4);
				if(this.currentSPEED>800){
					this.currentSPEED=800; //nu uh
				}
			break;
		}
	}

	protected void healSelf(String amount){
		switch(amount){
			case "full":
			this.currentHP=this.baseHP;
			break;
			case "half":
			if(this.energyDrink){
				this.healSelf("third");
				return;
			}
			this.currentHP+=(this.baseHP/2);
			if(this.currentHP>this.baseHP){
				this.currentHP=this.baseHP;
			}
			break;
			case "third":
			this.currentHP+=(this.baseHP/3);
			if(this.currentHP>this.baseHP){
				this.currentHP=this.baseHP;
			}
			break;
			case "quarter":
			this.currentHP+=(this.baseHP/4);
			if(this.currentHP>this.baseHP){
				this.currentHP=this.baseHP;
			}
			break;
		}
	}

	protected void decreaseStat(String stat){
		switch(stat){
			case "ATK":
				this.currentATK-=(this.baseATK/4);
				if(this.currentATK<20){this.currentATK=20;}
			break;
			case "DEF":
				this.currentDEF-=(this.baseDEF/4);
				if(this.currentDEF<2){this.currentDEF=2;}
			break;
			case "SPEED":
				this.currentSPEED-=(this.baseSPEED/4);
				if(this.currentSPEED<2){this.currentSPEED=2;}
			break;
		}
	}

	//YEOWCHH
	protected void aukPoisoned(){
		if(this.isPoisoned==true){
			int poisontick =this.baseHP/10;

			if(this.currentHP-poisontick<0){poisontick=this.currentHP;}

			this.currentHP-=poisontick;
		}
	}

	protected void aukBurning(){
		if(this.isBurning==true){
			int burningtick =this.baseHP/9;

			if(this.currentHP-burningtick<0){burningtick=this.currentHP;}

			this.currentHP-=burningtick;
		}
	}

	protected void healOverTime(){
		if(this.currentHP!=this.baseHP){
			int healtick = this.baseHP/16;
			
			if(this.currentHP+healtick>this.baseHP){
				healtick=this.baseHP-this.currentHP;
			}
			this.currentHP+=healtick;
		}
	}

	protected boolean hasSTAB(String moveName){
		String getType=defineMove(moveName);
		if((this.type+" Attack").equals(getType) || (this.type2+" Attack").equals(getType)){
			return true;
		}
		return false;
	}

	protected boolean canMegaEvolve(){
		String[] list = new String[]{//list of mon that can megaevolve
			//duh obviously absol goes first
			"Absol","Lopunny","Venusaur","Charizard","Blastoise","Ninetales","Mewtwo",
			"Aggron","Blaziken","Gengar","Lucario", "Cinccino", "Audino","Alakazam","Pidgeot", "Heracross",
			"Gardevoir","Mawile","Sceptile","Eevee","Citrus","Gyarados","Garchomp","Zamazenta","Zacian","Gallade",
			"Diance","Yanmega","Lapras","Togekiss","Weavile"
		};

		for(int i=0;i<list.length;i++){
			if(this.name.equals(list[i])){
				return true;
			}
		}
		return false;
	}

	protected void megaEvolve(){
		int addAtk=0,addDef=0,addSpeed=0,addHP=0;
		switch(this.name){
			case "Absol":
				addHP=0;
				addAtk=50;
				addDef=-10;
				addSpeed=20;
			break;
			case "Lopunny":
				addHP=80;
				addAtk=20;
				addDef=40;
				addSpeed=45;
				this.type="Fighting";
			break;
			case "Ninetales":
				addHP=30;
				addAtk=15;
				addDef=40;
				addSpeed=-10;
				this.type="Ice";
				this.moveset[0][0]="Freeze Dry";
				this.moveset[0][1]="Facade";
			break;
			case "Charizard":
				addHP=10;
				addAtk=20;
				addDef=20;
				addSpeed=-20;
				this.type2="Dragon";
				this.moveset[0][1]="Draco Meteor";
			break;
			case "Venusaur":
				addHP=10;
				addAtk=30;
				addDef=20;
				addSpeed=10;
			break;
			case "Blastoise":
				addHP=50;
				addAtk=10;
				addDef=30;
				addSpeed=-10;
			break;
			case "Mewtwo":
				addHP=5;
				addAtk=35;
				addDef=-10;
				addSpeed=-5;
			break;
			case "Aggron":
				addHP=30;
				addAtk=5;
				addDef=30;
				addSpeed=-10;
			break;
			case "Blaziken":
				addHP=0;
				addAtk=65;
				addDef=5;
				addSpeed=-20;
			break;
			case "Gengar":
				addHP=0;
				addAtk=30;
				addDef=10;
				addSpeed=30;
			break;
			case "Lucario":
				addHP=10;
				addAtk=25;
				addDef=30;
				addSpeed=10;
			break;
			case "Cinccino":
				addHP=30;
				addAtk=15;
				addDef=30;
				addSpeed=-10;
				this.moveset[0][3]="Last Resort";
			break;
			case "Audino":
				addHP=30;
				addAtk=10;
				addDef=-30;
				addSpeed=10;
				this.type="Fairy";
				this.moveset[0][1]="Moonblast";
			break;
			case "Alakazam":
				addHP=10;
				addAtk=30;
				addDef=0;
				addSpeed=-30;
			break;
			case "Pidgeot":
				addHP=0;
				addAtk=50;
				addDef=0;
				addSpeed=10;
				this.moveset[0][2]="Cyclone";
			break;
			case "Heracross":
				addHP=30;
				addAtk=-10;
				addDef=40;
				addSpeed=-10;
			break;
			case "Gardevoir":
				addHP=10;
				addAtk=30;
				addDef=30;
				addSpeed=-10;
			break;
			case "Mawile":
				addHP=10;
				addAtk=40;
				addDef=-10;
				addSpeed=10;
			break;
			case "Sceptile":
				addHP=10;
				addAtk=35;
				addDef=30;
				addSpeed=10;
			break;
			case "Citrus":
				addHP=50;
				addAtk=0;
				addDef=70;
				addSpeed=-20;
				this.moveset[0][2]="Last Resort";
				this.moveset[0][1]="Excite";
			break;
			case "Gyarados":
				addHP=10;
				addAtk=50;
				addDef=50;
				addSpeed=-50;
				this.type2="Dragon";
				this.moveset[0][1]="Draco Meteor";
			break;
			case "Garchomp":
				addHP=30;
				addAtk=10;
				addDef=40;
				addSpeed=-20;
			break;
			case "Zamazenta":
				addHP=30;
				addAtk=15;
				addDef=40;
				addSpeed=-10;
				this.type2="Steel";
				this.moveset[0][2]="Behemoth Bash";
			break;
			case "Zacian":
				addHP=0;
				addAtk=35;
				addDef=-40;
				addSpeed=10;
				this.type2="Steel";
				this.moveset[0][2]="Behemoth Blade";
			break;
			case "Gallade":
				addHP=0;
				addAtk=35;
				addDef=10;
				addSpeed=-10;
				this.moveset[0][0]="Sacred Sword";
			break;
			case "Diance":
				addHP=10;
				addAtk=60;
				addDef=-50;
				addSpeed=40;
			break;
			case "Yanmega":
				addHP=10;
				addAtk=50;
				addDef=10;
				addSpeed=10;
			break;
			case "Lapras":
				addHP=10;
				addAtk=30;
				addDef=5;
				addSpeed=10;
				type2="Steel";
			break;
			case "Togekiss":
				addHP=30;
				addDef=-20;
				addAtk=25;
				addSpeed=10;
				this.moveset[0][0]="OverdriveSmash";
			break;
			case "Weavile":
				addHP=10;
				addAtk=35;
				addDef=0;
				addSpeed=10;
				this.moveset[0][1]="ScratchingNails";
			break;
			case "Eevee": //eevee must go last in the switch statement o.o
				String listVee[] = new String[]{"Vaporeon","Jolteon","Flareon","Espeon","Umbreon","Leafeon","Glaceon","Sylveon"};
				Random rng = new Random(); 
				Pokemon newVeeVee = new Pokemon(listVee[rng.nextInt(listVee.length)]);
				rng=null;
				//evolve into a random eevee
				this.baseHP=newVeeVee.baseHP;
				addAtk=newVeeVee.baseATK-this.baseATK;
				addDef=newVeeVee.baseDEF-this.baseDEF;
				addSpeed=newVeeVee.baseSPEED-this.baseSPEED;
				addAtk+=25;
				addDef+=10; //extra stats :3
				addSpeed+=10;
				this.baseATK+=addAtk;
				this.baseDEF+=addDef;
				this.baseSPEED+=addSpeed;
				this.currentATK+=addAtk;
				this.currentDEF+=addDef;
				this.currentSPEED+=addSpeed;
				this.currentHP=this.baseHP;
				this.type=newVeeVee.type;
				this.setTypesWnR();
				this.moveset=newVeeVee.moveset;
				this.name=newVeeVee.name;
				
				if(this.currentATK<20){
					this.currentATK=20;
				}if(this.currentHP<1){
					this.currentHP=1;
				}if(this.currentDEF<2){
					this.currentDEF=2;
				}if(this.currentSPEED<2){
					this.currentSPEED=2;
				}
				return;
		}
		this.baseATK+=addAtk;
		this.baseDEF+=addDef;
		this.baseSPEED+=addSpeed;
		this.baseHP+=addHP;
		this.currentATK+=addAtk;
		this.currentDEF+=addDef;
		this.currentSPEED+=addSpeed;
		this.currentHP+=addHP;
		this.setTypesWnR();
		this.defineAllMoves();
		this.megaEvolved=true;
		this.name="Mega-"+this.name; // xd
		
		if(this.currentATK<50){
			this.currentATK=50;
		}if(this.currentHP<1){
			this.currentHP=1;
		}if(this.currentDEF<2){
			this.currentDEF=2;
		}if(this.currentSPEED<2){
			this.currentSPEED=2;
		}
	}

	protected void deMegaEvolve(){
		if(this.name.contains("Mega-")){
			String nam="";
			//remove mega from name xd
			try{
				//try to make a mon without Mega-. if fails, it's a custom mon with Mega- in the name -_-
				Pokemon aye = new Pokemon(this.name.substring(5));
			}catch(Exception e){
				return;
			}
			
			for(int i=5;i<this.name.length();i++){
				nam+=this.name.charAt(i);
			}
			
			//reset stats from base
			this.name=nam;
			Pokemon ref = new Pokemon(nam);
			this.baseHP=ref.baseHP;
			if(this.currentHP>this.baseHP){
				this.currentHP=this.baseHP;
			}
			this.baseATK=ref.baseATK;
			this.baseDEF=ref.baseDEF;
			this.baseSPEED=ref.baseSPEED;
			this.type=ref.type;
			this.type2=ref.type2;
			this.moveset=ref.moveset;
			this.moveset[1][0]=defineMove(this.moveset[0][0]);
			this.moveset[1][1]=defineMove(this.moveset[0][1]);
			this.moveset[1][2]=defineMove(this.moveset[0][2]);
			this.moveset[1][3]=defineMove(this.moveset[0][3]);
			this.setTypesWnR();
			ref=null;
		}
	}

	protected void disableAllItems(){
		this.items = new String[]{""};
	}
	
	protected String isSpecialMove(int moveIndex){
		//these are for attacking moves!!
		String movename=this.moveset[0][moveIndex];
		String movetype=this.moveset[1][moveIndex];
		
		switch(movename){
			//priority move
			case "Quick Attack": return "+priority";
			case "Vacuum Wave": return "+priority";

			//heal half of dmg dealt, -33% ATK
			case "Draining Kiss": return "lifedrain";
			case "Life Leech": return "lifedrain";
			case "Poison Leech": return "lifedrain";
			case "Giga Drain": return "lifedrain";
			case "Drain Punch": return "lifedrain";
			case "Bitter Blade": return "lifedrain";
			case "Excite": return "lifedrain";
			
			//random chance to apply burn
			case "Flamethrower": return "rngBurn";
			case "Scald": return "rngBurn";
			case "Fire Blast": return "rngBurn";
			
			//debuff enemy speed at random
			case "Mud Slap": return "rngDebuffSpeed";
			case "Electroweb": return "rngDebuffSpeed";
			case "Muddy Water": return "rngDebuffSpeed";
			case "Iron Head": return "rngDebuffSpeed";
			
			//debuff enemy def at random
			case "Crunch": return "rngDebuffDef";
			case "Earth Power": return "rngDebuffDef";
			case "Hammer Arm": return "rngDebuffDef";
			case "Shadow Ball": return "rngDebuffDef";
			case "Bug Buzz": return "rngDebuffDef";
			case "Energy Ball": return "rngDebuffDef";
			case "Focus Blast": return "rngDebuffDef";
			case "Psychic": return "rngDebuffDef";
			
			//debuff enemy atk at random
			case "Play Rough": return "rngDebuffAtk";
			case "Aurora Beam": return "rngDebuffAtk";
			case "Moonblast": return "rngDebuffAtk";
			
			//buff self speed
			case "Flame Charge": return "buffspeed";
			case "Dragon Rush": return "buffspeed";
			case "Trailblaze": return "buffspeed";
			
			//use move twice
			case "Double Kick": return "doublehit";
			case "Dual Wingbeat": return "doublehit";
			
			//apply poison at random
			case "Toxic Spikes": return "rngPoison";
			case "Poison Sting": return "rngPoison";
			case "Sludge Bomb": return "rngPoison";
			
			//roll twice for critical hit
			case "Night Slash": return "highcritrate";
			case "Attack Order": return "highcritrate";
			case "Leaf Blade": return "highcritrate";
			case "Psycho Cut": return "highcritrate";
			case "Stone Edge": return "highcritrate";
			case "Shadow Claw": return "highcritrate";
			case "Stone Axe": return "highcritrate";
			
			//x1.8 atk, debuff self atk twice after use
			case "Overheat": return "overclock";
			case "Superpower": return "overclock";
			case "Draco Meteor": return "overclock";
			case "Solar Beam": return "overclock";
			case "Fleur Cannon": return "overclock";
			case "Gigaton Hammer": return "overclock";
			
			//1.2x atk, debuff self def after use
			case "Close Combat": return "debuffselfdef";
			
			//act as if enemy def is 1
			case "Super Fang": return "ignoredef";
			case "Sacred Sword": return "ignoredef";
			
			//x1.5 atk, debuff speed after use
			case "Hyper Beam": return "powerboost";
			case "Plasma Fists": return "powerboost";
			case "Giga Impact": return "powerboost";
			case "Meteor Beam": return "powerboost";
			case "Thunder": return "powerboost";
			
			//debuff enemy atk
			case "Mystical Fire": return "debuffatk";
			case "Skitter Smack": return "debuffatk";
			case "Lunge": return "debuffatk";
			
			//use enemy def as atk value
			case "Psyshock": return "defisatk";
			case "Body Slam": return "defisatk";
			
			//use self def as atk
			case "Body Press": return "selfdefisatk";
			
			//more power but recieve a bit of dmg dealt as recoil
			case "Brave Bird": return "recoil";

			// random amount of hits 1-6;
			case "Pin Missile": return "rngMultihit";
			case "Powerful Bloom": return "rngMultihit";
			case "Water Shuriken": return "rngMultihit";
			case "Make it Rain": return "rngMultihit";
			case "Swift": return "rngMultihit";
			
			//-33% atk, always super effective
			case "Judgement": return "supEffective";
			
			//+2 hit
			case "Ice Slash": return "plus2hit";
			case "SurgingStrikes": return "plus2hit";
			case "Triple Axel": return "plus2hit";
			
			// +3 hit
			case "Halo": return "plus3hit";
			
			//gain half of lost hp as atk
			case "X": return "adversity";
			case "Ascension": return "adversity";
			
			//same but different (very useful comment)
			case "Assurance": return "adversity2";
			
			//more hits and atk the more alive mon you got in team
			case "Group Beating": return "groupB";

			//more hits the more alive mon the enemy has in team
			case "Cyclone": return "reverseGroupB";

			//more power the less alive mon u got in team
			case "Retaliate": return "avenger";
			
			// guaranteed paralysis
			case "Zap Cannon": return "paralyze";
			
			//x2 atk if enemy is mega evolved
			case "Behemoth Blade": return "MegaEvolutionHater";
			case "Behemoth Bash": return "MegaEvolutionHater";

			//randomly debuff one enemy stat if one of their stats is above base
			case "Alluring Voice": return "debuffIfBoosted";
			
			//x4 atk, faint after use
			case "MistyExplosion": return "kamikaze";

			//regieleki
			case "Thunder Cage": return "thundercage";

			//20% chance of paralysis for non-electric type moves
			case "Lick": return "rngParalysis";

			//random powah
			case "Magnitude": return "magnitude";

			//50% chance to buff def
			case "Diamond Storm": return "rngBuffDef";

			//myehehe
			case "Avalanche": return "buffPowerIfDebuffed";

			//cut hp in half
			case "Ruination": return "cuthp";

			//-33% atk, adds sword dance effect after use
			case "OverdriveSmash": return "osmash";

			//double atk if has status ailment
			case "Facade": return "facade";
			
			//guaranteed crit!
			case "Flower Trick": return "guaranteedCrit";
			
			//arceus dialga palkia gx augh
			case "AlteredCreation": return "brokenCardMove";

			//more power if enemy below full HP
			case "ScratchingNails": return "scnails";
			
			//50% chance for paralyze,poison or paralysis
			case "Dire Claw": return "rngPoisonBurnPara";
		}
		
		//----type only----//
		if(movetype.contains("Electric")){
			return "rngParalysis";
		}
		return "";
	}

	protected boolean hasMoveNameInMoveset(String nam){
		for(int i=0;i<4;i++){
			if(this.moveset[0][i].equals(nam)){
				return true;
			}
		}
		return false;
	}
	
	protected int countAttackingMoves(){
		int count=0;
		for(int i=0;i<4;i++){
			if(this.moveset[1][i].contains("Attack")){
				count++;
			}
		}
		return count;
	}
	
	protected int countStatusMoves(){
		int count=0;
		for(int i=0;i<4;i++){
			if(this.moveset[1][i].contains("Attack")==false){
				count++;
			}
		}
		return count;
	}

	public Pokemon(String pkmnName){//constructor frfr
		switch(pkmnName){
			/*op template
			case "":
				baseHP=;
				baseATK=;
				baseDEF=;
				baseSPEED=;
				type="";
				type2="";
				moveset = new String[][]{{"","","",""},{"","","",""}};
			break;
			*/
			case "Custom": //yay
				baseHP=1;
				baseATK=0;
				baseDEF=0;
				baseSPEED=0;
				type="";//all of these stats are overwritten by PokemonMaker3000 when loading them in
				type2="";
				moveset = new String[][]{{"","","",""},{"","","",""}};
				//moveset array cannot contain null spaces or it'll fail to define the moves
			break;
			case "Venusaur":
				baseHP=400;
				baseATK=95;
				baseDEF=80;
				baseSPEED=75;
				type="Grass";
				type2="Poison";
				moveset = new String[][]{{"Poison Powder","Vine Whip","Giga Drain","Earthquake"},{"","","",""}};
			break;
			case "Charizard":
				baseHP=325;
				baseATK=100;
				baseDEF=70;
				baseSPEED=90;
				type="Fire";
				type2="Flying";
				moveset = new String[][]{{"Will-O-Wisp","Dragon Breath","Flamethrower","Metal Claw"},{"","","",""}};
			break;
			case "Blastoise":
				baseHP=420;
				baseATK=92;
				baseDEF=100;
				baseSPEED=75;
				type="Water";
				moveset = new String[][]{{"Hydro Cannon","Ice Beam","Roar","Hyper Beam"},{"","","",""}};
			break;
			case "Meowscarada":
				baseHP=330;
				baseATK=90;
				baseDEF=60;
				baseSPEED=120;
				type="Grass";
				type2="Dark";
				moveset = new String[][]{{"Flower Trick","Bite","Play Rough","Hone Claws"},{"","","",""}};
			break;
			case "Ninetales":
				baseHP=360;
				baseATK=100;
				baseDEF=80;
				baseSPEED=80;
				type="Fire";
				moveset = new String[][]{{"Flamethrower","Quick Attack","Calm Mind","Shadow Ball"},{"","","",""}};
			break;
			case "Empoleon":
				baseHP=400;
				baseATK=90;
				baseDEF=70;
				baseSPEED=80;
				type="Water";
				type2="Steel";
				moveset = new String[][]{{"Whirlpool","Crunch","Mud Slap","Iron Defense"},{"","","",""}};
			break;
			case "Raichu":
				baseHP=350;
				baseATK=98;
				baseDEF=70;
				baseSPEED=110;
				type="Electric";
				moveset = new String[][]{{"Quick Attack","Hyper Beam","Electroweb","Charge"},{"","","",""}};
			break;
			case "Mewtwo":
				baseHP=360;
				baseATK=130;
				baseDEF=70;
				baseSPEED=115;
				type="Psychic";
				moveset = new String[][]{{"Psychic","Iron Tail","Shadow Ball","Sword Dance"},{"","","",""}};
			break;
			case "Gengar":
				baseHP=325;
				baseATK=115;
				baseDEF=70;
				baseSPEED=80;
				type="Ghost";
				moveset = new String[][]{{"Shadow Ball","Sucker Punch","Dream Eater","Toxic"},{"","","",""}};
			break;
			case "Dragonite":
				baseHP=320;
				baseATK=122;
				baseDEF=50;
				baseSPEED=60;
				type="Dragon";
				type2="Flying";
				moveset = new String[][]{{"Hyper Beam","Dragon Rush","Wing Attack","Dragon Dance"},{"","","",""}};
			break;
			case "Absol":
				baseHP=250;
				baseATK=140;
				baseDEF=30;
				baseSPEED=115;
				type="Dark";
				moveset = new String[][]{{"Night Slash","Play Rough","Psycho Cut","Sword Dance"},{"","","",""}};
			break;
			case "Gardevoir":
				baseHP=310;
				baseATK=110;
				baseDEF=65;
				baseSPEED=80;
				type="Psychic";
				type2="Fairy";
				moveset = new String[][]{{"Moonblast","Dream Eater","Calm Mind","Growl"},{"","","",""}};
			break;
			case "Glaceon":
				baseHP=280;
				baseATK=140;
				baseDEF=50;
				baseSPEED=77;
				type="Ice";
				moveset = new String[][]{{"Freeze Dry","Quick Attack","Charm","Calm Mind"},{"","","",""}};
			break;
			case "Luxray":
				baseHP=320;
				baseATK=111;
				baseDEF=66;
				baseSPEED=77;
				type="Electric";
				moveset = new String[][]{{"Thunder Fang","Crunch","Impulse","Thunder Wave"},{"","","",""}};
			break;
			case "Lucario":
				baseHP=300;
				baseATK=135;
				baseDEF=70;
				baseSPEED=90;
				type="Fighting";
				type2="Steel";
				moveset = new String[][]{{"Aura Sphere","Quick Attack","Dragon Pulse","Sword Dance"},{"","","",""}};
			break;
			case "Duraludon":
				baseHP=350;
				baseATK=100;
				baseDEF=81;
				baseSPEED=78;
				type="Steel";
				type2="Dragon";
				moveset = new String[][]{{"Hyper Beam","Flash Cannon","Iron Defense","Dragon Tail"},{"","","",""}};
			break;
			case "Mismagius":
				baseHP=210;
				baseATK=100;
				baseDEF=85;
				baseSPEED=115;
				type="Ghost";
				moveset = new String[][]{{"Shadow Ball","Flamethrower","Calm Mind","Growl"},{"","","",""}};
			break;
			case "Golisopod":
				baseHP=360;
				baseATK=90;
				baseDEF=100;
				baseSPEED=45;
				type="Bug";
				type2="Water";
				moveset = new String[][]{{"Bug Bite","Scald","Sword Dance","Iron Defense"},{"","","",""}};
			break;
			case "Heracross":
				baseHP=340;
				baseATK=111;
				baseDEF=67;
				baseSPEED=75;
				type="Bug";
				type2="Fighting";
				moveset = new String[][]{{"Bug Bite","Close Combat","Sword Dance","Iron Defense"},{"","","",""}};
			break;
			case "Rampardos":
				baseHP=330;
				baseATK=110;
				baseDEF=70;
				baseSPEED=55;
				type="Rock";
				moveset = new String[][]{{"Stone Edge","Close Combat","Dragon Tail","Bulk Up"},{"","","",""}};
			break;
			case "Lycanroc":
				baseHP=360;
				baseATK=95;
				baseDEF=65;
				baseSPEED=95;
				type="Rock";
				moveset = new String[][]{{"Stone Edge","Bite","Rock Smash","Growl"},{"","","",""}};
			break;
			case "Aurorus":
				baseHP=400;
				baseATK=95;
				baseDEF=90;
				baseSPEED=65;
				type="Ice";
				type2="Rock";
				moveset = new String[][]{{"Hyper Beam","Ice Beam","Iron Head","Calm Mind"},{"","","",""}};
			break;
			case "Dugtrio":
				baseHP=230;
				baseATK=95;
				baseDEF=60;
				baseSPEED=130;
				type="Ground";
				moveset = new String[][]{{"Magnitude","Sucker Punch","Hyper Beam","Sword Dance"},{"","","",""}};
			break;
			case "Sandlash":
				baseHP=280;
				baseATK=90;
				baseDEF=90;
				baseSPEED=77;
				type="Ground";
				moveset = new String[][]{{"Earthquake","Iron Head","Amnesia","Toxic"},{"","","",""}};
			break;
			case "Arbok":
				baseHP=280;
				baseATK=124;
				baseDEF=88;
				baseSPEED=67;
				type="Poison";
				moveset = new String[][]{{"Poison Leech","Shadow Ball","Scald","Toxic"},{"","","",""}};
			break;
			case "Sneasler":
				baseHP=250;
				baseATK=125;
				baseDEF=40;
				baseSPEED=110;
				type="Poison";
				type2="Fighting";
				moveset = new String[][]{{"Dire Claw","Shadow Ball","Close Combat","Sword Dance"},{"","","",""}};
			break;
			case "Pidgeot":
				baseHP=310;
				baseATK=96;
				baseDEF=70;
				baseSPEED=121;
				type="Flying";
				moveset = new String[][]{{"Wing Attack","Dragon Rush","Charm","Roost"},{"","","",""}};
			break;
			case "Lugia":
				baseHP=420;
				baseATK=120;
				baseDEF=100;
				baseSPEED=50;
				type="Flying";
				type2="Psychic";
				moveset = new String[][]{{"Aerial Ace","Psychic","Dragon Breath","Sword Dance"},{"","","",""}};
			break;
			case "Urshifu":
				baseHP=390;
				baseATK=110;
				baseDEF=55;
				baseSPEED=90;
				type="Fighting";
				type2="Dark";
				moveset = new String[][]{{"Close Combat","Aerial Ace","SurgingStrikes","Bulk Up"},{"","","",""}};
			break;
			case "Audino":
				baseHP=420;
				baseATK=90;
				baseDEF=80;
				baseSPEED=55;
				type="Normal";
				moveset = new String[][]{{"Heal Pulse","Hyper Beam","Psychic","Calm Mind"},{"","","",""}};
			break;
			case "Tauros": // RIP Slaking, got replaced by the much cooler gen 1 normal type
				baseHP=325;
				baseATK=125;
				baseDEF=45;
				baseSPEED=98;
				type="Normal";
				moveset = new String[][]{{"Hyper Beam","Body Slam","Ice Beam","Extreme Speed"},{"","","",""}};
			break;
			case "Sylveon":
				baseHP=360;
				baseATK=125;
				baseDEF=90;
				baseSPEED=60;
				type="Fairy";
				moveset = new String[][]{{"Moonblast","Calm Mind","Draining Kiss","Hyper Beam"},{"","","",""}};
			break;
			case "Tinkaton":
				baseHP=350;
				baseATK=110;
				baseDEF=80;
				baseSPEED=85;
				type="Fairy";
				type2="Steel";
				moveset = new String[][]{{"Play Rough","Gigaton Hammer","Dream Eater","Sword Dance"},{"","","",""}};
			break; // :3
			case "Zarude":
				baseHP=310;
				baseATK=95;
				baseDEF=90;
				baseSPEED=62;
				type="Dark";
				type2="Grass";
				moveset = new String[][]{{"Giga Drain","Crunch","Drain Punch","Jungle Healing"},{"","","",""}};
			break;
			case "Dragapult":
				baseHP=270;
				baseATK=120;
				baseDEF=65;
				baseSPEED=138;
				type="Dragon";
				type2="Ghost";
				moveset = new String[][]{{"Dragon Rush","Hex","Will-O-Wisp","Dragon Dance"},{"","","",""}};
			break;
			case "Mawile":
				baseHP=290;
				baseATK=100;
				baseDEF=98;
				baseSPEED=78;
				type="Steel";
				type2="Fairy";
				moveset = new String[][]{{"Play Rough","Iron Head","Crunch","Fake Tears"},{"","","",""}};
			break;
			//--------wave 2 of pokemen--------//
			case "Blaziken":
				baseHP=320;
				baseATK=100;
				baseDEF=65;
				baseSPEED=90;
				type="Fire";
				type2="Fighting";
				moveset = new String[][]{{"Flame Charge","Aerial Ace","Double Kick","Sword Dance"},{"","","",""}};
			break;
			case "Vaporeon": //hey guys
				baseHP=450;
				baseATK=86;
				baseDEF=70;
				baseSPEED=70;
				type="Water";
				moveset = new String[][]{{"Hydro Pump","Ice Beam","Aqua Ring","Calm Mind"},{"","","",""}};
			break;
			case "Ursaluna":
				baseHP=320;
				baseATK=105;
				baseDEF=95;
				baseSPEED=55;
				type="Ground";
				type2="Normal";
				moveset = new String[][]{{"Hammer Arm","Play Rough","Earth Power","Sword Dance"},{"","","",""}};
			break;
			case "Decidueye":
				baseHP=300;
				baseATK=105;
				baseDEF=70;
				baseSPEED=100;
				type="Grass";
				type2="Dark";
				moveset = new String[][]{{"Leaf Blade","Shadow Sneak","Air Slash","Sword Dance"},{"","","",""}};
			break;
			case "Flareon":
				baseHP=310;
				baseATK=130;
				baseDEF=80;
				baseSPEED=68;
				type="Fire";
				moveset = new String[][]{{"Overheat","Trailblaze","Quick Attack","Charm"},{"","","",""}};
			break;
			case "Lapras":
				baseHP=390;
				baseATK=95;
				baseDEF=100;
				baseSPEED=70;
				type="Water";
				type2="Ice";
				moveset = new String[][]{{"Hydro Pump","Thunder","Ice Beam","Dragon Dance"},{"","","",""}};
			break;
			case "Tsareena":
				baseHP=280;
				baseATK=120;
				baseDEF=95;
				baseSPEED=80;
				type="Grass";
				moveset = new String[][]{{"Razor Leaf","HJ Kick","Play Rough","Charm"},{"","","",""}};
			break;
			case "Braviary":
				baseHP=300;
				baseATK=121;
				baseDEF=70;
				baseSPEED=85;
				type="Flying";
				type2="Normal";
				moveset = new String[][]{{"Aerial Ace","Superpower","Iron Head","Hone Claws"},{"","","",""}};
			break;
			case "Toxtricity":
				baseHP=265;
				baseATK=125;
				baseDEF=70;
				baseSPEED=80;
				type="Electric";
				type2="Poison";
				moveset = new String[][]{{"Overdrive","Toxic Spikes","Hex","Scary Face"},{"","","",""}};
			break;
			case "Krookodile":
				baseHP=260;
				baseATK=115;
				baseDEF=70;
				baseSPEED=95;
				type="Ground";
				type2="Dark";
				moveset = new String[][]{{"Earthquake","Crunch","Close Combat","Bulk Up"},{"","","",""}};
			break;
			case "Toucannon":
				baseHP=280;
				baseATK=125;
				baseDEF=60;
				baseSPEED=75;
				type="Flying";
				type2="Normal";
				moveset = new String[][]{{"Flash Cannon","Overheat","Dual Wingbeat","Roost"},{"","","",""}};
			break;
			case "Zeraora":
				baseHP=300;
				baseATK=115;
				baseDEF=40;
				baseSPEED=135;
				type="Electric";
				moveset = new String[][]{{"Play Rough","Aura Sphere","Plasma Fists","Agility"},{"","","",""}};
			break;
			case "Weezing":
				baseHP=230;
				baseATK=90;
				baseDEF=110;
				baseSPEED=70;
				type="Poison";
				moveset = new String[][]{{"Toxic","Venoshock","Shadow Ball","Psychic"},{"","","",""}};
			break;
			case "Drapion":
				baseHP=270;
				baseATK=90;
				baseDEF=100;
				baseSPEED=95;
				type="Poison";
				type2="Dark";
				moveset = new String[][]{{"Crunch","Ice Fang","Toxic","Venoshock"},{"","","",""}};
			break;
			case "Walking Wake":
				baseHP=350;
				baseATK=130;
				baseDEF=65;
				baseSPEED=110;
				type="Dragon";
				type2="Water";
				moveset = new String[][]{{"Hydro Pump","Draco Meteor","Hyper Beam","Dragon Dance"},{"","","",""}};
			break;
			case "Roaring Moon":
				baseHP=380;
				baseATK=105;
				baseDEF=90;
				baseSPEED=100;
				type="Dragon";
				type2="Dark";
				moveset = new String[][]{{"Dragon Rush","Crunch","Flamethrower","Dragon Dance"},{"","","",""}};
			break;
			case "Togekiss":
				baseHP=230;
				baseATK=110;
				baseDEF=105;
				baseSPEED=70;
				type="Fairy";
				type2="Flying";
				moveset = new String[][]{{"Moonblast","Air Slash","Aura Sphere","Charm"},{"","","",""}};
			break;
			case "Florges":
				baseHP=300;
				baseATK=112;
				baseDEF=135;
				baseSPEED=60;
				type="Fairy";
				moveset = new String[][]{{"Moonblast","Grass Knot","Psychic","Calm Mind"},{"","","",""}};
			break;
			case "Lopunny":
				baseHP=300;
				baseATK=90;
				baseDEF=96;
				baseSPEED=105;
				type="Normal";
				moveset = new String[][]{{"Double Kick","Hyper Beam","Sword Dance","Agility"},{"","","",""}};
			break;
			case "Cinccino":
				baseHP=290;
				baseATK=95;
				baseDEF=70;
				baseSPEED=115;
				type="Normal";
				moveset = new String[][]{{"Super Fang","Facade","Drain Punch","Calm Mind"},{"","","",""}};
			break;
			case "Hawlucha":
				baseHP=350;
				baseATK=95;
				baseDEF=70;
				baseSPEED=110;
				type="Fighting";
				type2="Flying";
				moveset = new String[][]{{"Flying Press","Wing Attack","Stone Edge","Sword Dance"},{"","","",""}};
			break;
			case "Flutter Mane":
				baseHP=180;
				baseATK=135;
				baseDEF=100;
				baseSPEED=135;
				type="Ghost";
				type2="Fairy";
				moveset = new String[][]{{"Moonblast","Calm Mind","Shadow Ball","Mystical Fire"},{"","","",""}};
			break;
			case "Trevenant":
				baseHP=280;
				baseATK=110;
				baseDEF=82;
				baseSPEED=80;
				type="Ghost";
				type2="Grass";
				moveset = new String[][]{{"Wood Hammer","Hex","Toxic","Will-O-Wisp"},{"","","",""}};
			break;
			case "Volcarona":
				baseHP=300;
				baseATK=130;
				baseDEF=60;
				baseSPEED=90;
				type="Bug";
				type2="Fire";
				moveset = new String[][]{{"Bug Buzz","Fire Blast","Giga Drain","Amnesia"},{"","","",""}};
			break;
			case "Vespiquen":
				baseHP=300;
				baseATK=90;
				baseDEF=105;
				baseSPEED=60;
				type="Bug";
				type2="Flying";
				moveset = new String[][]{{"Attack Order","Air Slash","Poison Sting","Defend Order"},{"","","",""}};
			break;
			case "Pangoro":
				baseHP=320;
				baseATK=120;
				baseDEF=80;
				baseSPEED=59;
				type="Fighting";
				type2="Dark";
				moveset = new String[][]{{"Hammer Arm","Crunch","Bullet Punch","Bulk Up"},{"","","",""}};
			break;
			case "Aggron":
				baseHP=290;
				baseATK=90;
				baseDEF=150;
				baseSPEED=50;
				type="Steel";
				type2="Rock";
				moveset = new String[][]{{"Iron Tail","Stone Edge","Giga Impact","Iron Defense"},{"","","",""}};
			break;
			case "Scizor":
				baseHP=270;
				baseATK=120;
				baseDEF=100;
				baseSPEED=70;
				type="Bug";
				type2="Steel";
				moveset = new String[][]{{"X-Scissor","Iron Head","Iron Defense","Sword Dance"},{"","","",""}};
			break;
			case "Mew":
				baseHP=300;
				baseATK=100;
				baseDEF=100;
				baseSPEED=100;
				type="Psychic";
				moveset = new String[][]{{"Psychic","Hydro Cannon","Solar Beam","Overheat"},{"","","",""}};
			break;
			case "Alakazam":
				baseHP=210;
				baseATK=125;
				baseDEF=70;
				baseSPEED=121;
				type="Psychic";
				moveset = new String[][]{{"Dream Eater","Energy Ball","Amnesia","Calm Mind"},{"","","",""}};
			break;
			case "Froslass":
				baseHP=290;
				baseATK=90;
				baseDEF=70;
				baseSPEED=110;
				type="Ice";
				type2="Ghost";
				moveset = new String[][]{{"Blizzard","Shadow Ball","Will-O-Wisp","Draining Kiss"},{"","","",""}};
			break;
			case "Baxcalibur":
				baseHP=350;
				baseATK=130;
				baseDEF=90;
				baseSPEED=87;
				type="Ice";
				type2="Dragon";
				moveset = new String[][]{{"Ice Beam","Crunch","Dragon Tail","Sword Dance"},{"","","",""}};
			break;
			case "Hydreigon":
				baseHP=330;
				baseATK=100;
				baseDEF=80;
				baseSPEED=98;
				type="Dark";
				type2="Dragon";
				moveset = new String[][]{{"Hyper Beam","Dragon Rush","Crunch","Work Up"},{"","","",""}};
			break;
			case "Zoroark":
				baseHP=240;
				baseATK=135;
				baseDEF=60;
				baseSPEED=105;
				type="Dark";
				moveset = new String[][]{{"Night Slash","Focus Blast","Shadow Ball","Hone Claws"},{"","","",""}};
			break;
			case "Solrock":
				baseHP=300;
				baseATK=95;
				baseDEF=80;
				baseSPEED=70;
				type="Rock";
				type2="Psychic";
				moveset = new String[][]{{"Solar Beam","Stone Edge","Psychic","Amnesia"},{"","","",""}};
			break;
			case "Lunatone":
				baseHP=290;
				baseATK=100;
				baseDEF=70;
				baseSPEED=75;
				type="Rock";
				type2="Psychic";
				moveset = new String[][]{{"Moonblast","Stone Edge","Shadow Ball","Calm Mind"},{"","","",""}};
			break;
			//-------- wave 3 ---------//
			case "Delphox":
				baseHP=250;
				baseATK=116;
				baseDEF=100;
				baseSPEED=100;
				type="Fire";
				type2="Psychic";
				moveset = new String[][]{{"Mystical Fire","Psyshock","Shadow Ball","Calm Mind"},{"","","",""}};
			break;
			case "Gyarados":
				baseHP=310;
				baseATK=110;
				baseDEF=100;
				baseSPEED=80;
				type="Water";
				type2="Flying";
				moveset = new String[][]{{"Hydro Pump","Crunch","Dual Wingbeat","Dragon Dance"},{"","","",""}};
			break;
			case "Sceptile":
				baseHP=300;
				baseATK=105;
				baseDEF=60;
				baseSPEED=120;
				type="Grass";
				moveset = new String[][]{{"Solar Beam","X-Scissor","Earthquake","Agility"},{"","","",""}};
			break;
			case "Typhlosion":
				baseHP=210;
				baseATK=120;
				baseDEF=80;
				baseSPEED=100;
				type="Fire";
				moveset = new String[][]{{"Flamethrower","Stone Edge","Iron Head","Hone Claws"},{"","","",""}};
			break;
			case "Greninja":
				baseHP=270;
				baseATK=121;
				baseDEF=70;
				baseSPEED=120;
				type="Water";
				type2="Dark";
				moveset = new String[][]{{"Water Shuriken","Shadow Ball","Night Slash","Growl"},{"","","",""}};
			break;
			case "Leafeon":
				baseHP=260;
				baseATK=120;
				baseDEF=135;
				baseSPEED=90;
				type="Grass";
				moveset = new String[][]{{"Leaf Blade","Mud Slap","X-Scissor","Sword Dance"},{"","","",""}};
			break;
			case "Donphan":
				baseHP=390;
				baseATK=100;
				baseDEF=120;
				baseSPEED=50;
				type="Ground";
				moveset = new String[][]{{"Giga Impact","Earthquake","Crunch","Iron Defense"},{"","","",""}};
			break;
			case "Corviknight":
				baseHP=410;
				baseATK=90;
				baseDEF=115;
				baseSPEED=80;
				type="Flying";
				type2="Steel";
				moveset = new String[][]{{"Brave Bird","Steel Wing","Body Press","Bulk Up"},{"","","",""}};
			break;
			case "Umbreon":
				baseHP=400;
				baseATK=85;
				baseDEF=135;
				baseSPEED=70;
				type="Dark";
				moveset = new String[][]{{"Crunch","Shadow Ball","Charm","Quick Attack"},{"","","",""}};
			break;
			case "Jolteon":
				baseHP=270;
				baseATK=115;
				baseDEF=70;
				baseSPEED=130;
				type="Electric";
				moveset = new String[][]{{"Thunder","Pin Missile","Calm Mind","Agility"},{"","","",""}};
			break;
			case "Espeon":
				baseHP=230;
				baseATK=137;
				baseDEF=65;
				baseSPEED=100;
				type="Psychic";
				moveset = new String[][]{{"Psychic","Mud Slap","Facade","Charm"},{"","","",""}};
			break;
			case "Eevee":
				baseHP=280;
				baseATK=70;
				baseDEF=70;
				baseSPEED=60;
				type="Normal";
				moveset = new String[][]{{"Quick Attack","Facade","Charm","Swift"},{"","","",""}};
			break;
			case "Arceus":
				baseHP=300;
				baseATK=120;
				baseDEF=150;
				baseSPEED=50;
				type="Normal";
				moveset = new String[][]{{"Judgement","Calm Mind","Agility","Amnesia"},{"","","","",""}};
			break;
			case "Citrus":
				baseHP=460;
				baseATK=50;
				baseDEF=80;
				baseSPEED=60;
				type="Ground";
				moveset = new String[][]{{"X","Giga Drain","Agility","Ascension"},{"","","",""}};
			break;
			case "Toxicroak":
				baseHP=280;
				baseATK=100;
				baseDEF=65;
				baseSPEED=87;
				type="Poison";
				type2="Fighting";
				moveset = new String[][]{{"Group Beating","Venoshock","Drain Punch","Sword Dance"},{"","","",""}};
			break;
			case "Cyclizar":
				baseHP=250;
				baseATK=90;
				baseDEF=60;
				baseSPEED=125;
				type="Dragon";
				moveset = new String[][]{{"Dragon Tail","X-Scissor","Crunch","Shift Gear"},{"","","",""}};
			break;
			case "Garchomp":
				baseHP=312;
				baseATK=130;
				baseDEF=95;
				baseSPEED=90;
				type="Dragon";
				type2="Ground";
				moveset = new String[][]{{"Dragon Rush","Earthquake","Stone Edge","Sword Dance"},{"","","",""}};
			break;
			case "Gholdengo":
				baseHP=230;
				baseATK=133;
				baseDEF=90;
				baseSPEED=70;
				type="Steel";
				type2="Ghost";
				moveset = new String[][]{{"Make it Rain","Shadow Ball","Psychic","Thunder Wave"},{"","","",""}};
			break;
			case "Galvantula":
				baseHP=230;
				baseATK=100;
				baseDEF=90;
				baseSPEED=100;
				type="Electric";
				type2="Bug";
				moveset = new String[][]{{"Bug Buzz","Energy Ball","Thunder Wave","String Shot"},{"","","",""}};
			break;
			case "Ceruledge":
				baseHP=225;
				baseATK=130;
				baseDEF=120;
				baseSPEED=86;
				type="Ghost";
				type2="Fire";
				moveset = new String[][]{{"Shadow Claw","Bitter Blade","X-Scissor","Sword Dance"},{"","","",""}};
			break;
			case "Chandelure":
				baseHP=200;
				baseATK=155;
				baseDEF=80;
				baseSPEED=80;
				type="Ghost";
				type2="Fire";
				moveset = new String[][]{{"Hex","Overheat","Trailblaze","Acid Armor"},{"","","",""}};
			break;
			case "Flamigo":
				baseHP=260;
				baseATK=115;
				baseDEF=40;
				baseSPEED=90;
				type="Flying";
				type2="Fighting";
				moveset = new String[][]{{"Brave Bird","Double Kick","Cyclone","Roost"},{"","","",""}};
			break;
			case "Zamazenta":
				baseHP=290;
				baseATK=110;
				baseDEF=115;
				baseSPEED=110;
				type="Fighting";									//behemoth bash
				moveset = new String[][]{{"Body Press","Solar Beam","Iron Head","Iron Defense"},{"","","",""}};
			break;
			case "Zacian":
				baseHP=280;
				baseATK=120;
				baseDEF=80;
				baseSPEED=120;
				type="Fairy";										//behemoth blade
				moveset = new String[][]{{"Sacred Sword","Play Rough","Iron Head","Sword Dance"},{"","","",""}};
			break;
			case "Magearna":
				baseHP=275;
				baseATK=130;
				baseDEF=105;
				baseSPEED=70;
				type="Steel";
				type2="Fairy";
				moveset = new String[][]{{"Fleur Cannon","Zap Cannon","Flash Cannon","Shift Gear"},{"","","",""}};
			break;
			case "Celebi ex":
				baseHP=130;
				baseATK=230;
				baseDEF=1;
				baseSPEED=50;
				type="Grass";
				moveset= new String[][]{{"Powerful Bloom","Powerful Bloom","Powerful Bloom","Powerful Bloom"},{"","","",""}};
			break;
			case "Cresselia":
				baseHP=290;
				baseATK=90;
				baseDEF=130;
				baseSPEED=95;
				type="Psychic";
				moveset = new String[][]{{"Psychic","Aurora Beam","Shadow Ball","Lunar Plumage"},{"","","",""}};
			break;
			case "Kingambit":
				baseHP=310;
				baseATK=125;
				baseDEF=110;
				baseSPEED=62;
				type="Dark";
				type2="Steel";
				moveset = new String[][]{{"Retaliate","Assurance","Metal Sound","Sword Dance"},{"","","",""}};
			break;
			case "Azumarill":
				baseHP=300;
				baseATK=80;
				baseDEF=90;
				baseSPEED=80;
				type="Fairy";
				type2="Water";
				moveset= new String[][]{{"Muddy Water","Alluring Voice","MistyExplosion","Amnesia"},{"","","",""}};
			break;
			case "Gallade":
				baseHP=210;
				baseATK=130;
				baseDEF=65;
				baseSPEED=115;
				type="Fighting";
				type2="Psychic";
				moveset = new String[][]{{"Focus Blast","Psycho Cut","Leaf Blade","Sword Dance"},{"","","",""}};
			break;
			case "Regieleki":
				baseHP=250;
				baseATK=130;
				baseDEF=50;
				baseSPEED=200;
				type="Electric";
				moveset = new String[][]{{"Electroweb","Thunder Cage","Zap Cannon","Charge"},{"","","",""}};
			break;
			case "Seviper":
				baseHP=260;
				baseATK=110;
				baseDEF=70;
				baseSPEED=70;
				type="Poison";
				moveset = new String[][]{{"Sludge Bomb","Lick","Venoshock","Coil"},{"","","",""}};
			break;
			case "Garganacl":
				baseHP=380;
				baseATK=100;
				baseDEF=130;
				baseSPEED=45;
				type="Rock";
				moveset = new String[][]{{"Meteor Beam","Earthquake","Iron Hammer","Salt Cure"},{"","","",""}};
			break;
			case "Diance":
				baseHP=240;
				baseATK=100;
				baseDEF=150;
				baseSPEED=50;
				type="Rock";
				type2="Fairy";
				moveset = new String[][]{{"Diamond Storm","Moonblast","Rock Smash","Rock Polish"},{"","","",""}};
			break;
			case "Weavile":
				baseHP=285;
				baseATK=120;
				baseDEF=65;
				baseSPEED=125;
				type="Ice";
				type2="Dark";
				moveset = new String[][]{{"Triple Axel","Night Slash","Shadow Claw","Sword Dance"},{"","","",""}};
			break;
			case "Chien-Pao":
				baseHP=300;
				baseATK=135;
				baseDEF=40;
				baseSPEED=90;
				type="Ice";
				type2="Dark";
				moveset = new String[][]{{"Avalanche","Ruination","Hyper Beam","Scary Face"},{"","","",""}};
			break;
			case "Yanmega":
				baseHP=300;
				baseATK=115;
				baseDEF=90;
				baseSPEED=95;
				type="Bug";
				type2="Flying";
				moveset = new String[][]{{"Skitter Smack","Dual Wingbeat","Facade","Extreme Speed"},{"","","",""}};
			break;
			case "Kleavor":
				baseHP=285;
				baseATK=135;
				baseDEF=85;
				baseSPEED=85;
				type="Bug";
				type2="Rock";
				moveset = new String[][]{{"Lunge","Stone Axe","Vacuum Wave","Sword Dance"},{"","","",""}};
			break;
			case "ADP GX":
				baseHP=280;
				baseATK=90;
				baseDEF=120;
				baseSPEED=95;
				type="Dragon";
				moveset = new String[][]{{"AlteredCreation","Draco Meteor","Behemoth Bash","Amnesia"},{"","","",""}};
			break;
			case "Missing No":
				Random rng = new Random();
				baseHP=rng.nextInt(200)+100;
				baseATK=rng.nextInt(250)+10;
				baseDEF=rng.nextInt(250)+10;
				baseSPEED=rng.nextInt(250)+10;
				type="Normal";
				moveset = new String[][]{{"Water Gun","Tackle","Sky Attack","Assist"},{"","","",""}};
			break;
		}
		if(this.type.equals("")==false){
                    this.setTypesWnR();
                }
		
		currentHP=baseHP;
		currentATK=baseATK;
		currentDEF=baseDEF;
		currentSPEED=baseSPEED;
		name=pkmnName;
		isPoisoned=false;
		isParalized=false;
		isBurning=false;
		healingOverTime=false;
		strike=false; //guaranteed crit
		permaBurn=false; //unremovable burn status
		energyDrink=false; //+1 move per turn
		moveset[1][0]=defineMove(moveset[0][0]);
		moveset[1][1]=defineMove(moveset[0][1]);
		moveset[1][2]=defineMove(moveset[0][2]);
		moveset[1][3]=defineMove(moveset[0][3]);
		
		ability = new Ability(this.name);

		items= new String[]{"Potion","X-Attack","X-Defense","X-Speed","","Dash Earring","Strike Earrings","Energy Drink"};
		if(this.canMegaEvolve()){
			items[4]="Mega Stone";
		}
		
	}//class Pokemon constructor ends
	
	protected String[] getListOfWnR(String typ, int returntype){
		String[] weakTo = new String[1];
		String[] resists = new String[1];
		switch(typ){ //sets weaknesses and resistances
			case "Grass":
				weakTo= new String[]{"Flying","Fire","Poison","Bug","Ice"};
				resists= new String[]{"Ground","Water","Grass","Electric"};
			break;
			case "Fire":
				weakTo= new String[]{"Water","Rock","Ground"};
				resists= new String[]{"Fire","Grass","Fairy","Bug","Ice","Steel"};
			break;
			case "Water":
				weakTo= new String[]{"Electric","Grass"};
				resists= new String[]{"Steel","Fire","Water","Ice"};
			break;
			case "Psychic":
				weakTo= new String[]{"Bug","Ghost","Dark"};
				resists= new String[]{"Fighting","Psychic"};
			break;
			case "Dark":
				weakTo= new String[]{"Fairy","Bug","Fighting"};
				resists= new String[]{"Ghost","Dark","Psychic"};
			break;
			case "Fighting":
				weakTo= new String[]{"Flying","Psychic","Fairy"};
				resists= new String[]{"Bug","Rock","Dark"};
			break;
			case "Electric":
				weakTo= new String[]{"Ground"};
				resists= new String[]{"Electric","Flying","Steel"};
			break;
			case "Flying":
				weakTo= new String[]{"Electric","Ice","Rock"};
				resists= new String[]{"Fighting","Grass","Bug","Ground"};
			break;
			case "Bug":
				weakTo= new String[]{"Fire","Flying","Rock"};
				resists= new String[]{"Grass","Fighting","Ground"};
			break;
			case "Ground":
				weakTo= new String[]{"Water","Grass","Ice"};
				resists= new String[]{"Poison","Rock","Electric"};
			break;
			case "Rock":
				weakTo= new String[]{"Water","Grass","Fighting","Ground"};
				resists= new String[]{"Normal","Fire","Poison","Flying"};
			break;
			case "Poison":
				weakTo= new String[]{"Ground","Psychic"};
				resists= new String[]{"Grass","Fighting","Poison","Bug","Fairy"};
			break;
			case "Ghost":
				weakTo= new String[]{"Ghost","Dark"};
				resists= new String[]{"Poison","Bug","Normal","Fighting"};
			break;
			case "Steel":
				weakTo= new String[]{"Fire","Fighting","Ground"};
				resists= new String[]{"Normal","Grass","Ice","Flying","Psychic","Bug","Rock","Dragon","Steel","Fairy"};
			break;
			case "Dragon":
				weakTo= new String[]{"Dragon","Fairy","Ice"};
				resists= new String[]{"Fire","Water","Grass","Electric"};
			break;
			case "Fairy":
				weakTo= new String[]{"Poison","Steel"};
				resists= new String[]{"Fighting","Bug","Dark","Dragon"};
			break;
			case "Normal":
				weakTo= new String[]{"Fighting"};
				resists= new String[]{"Ghost"};
			break;
			case "Ice":
				weakTo= new String[]{"Fighting","Rock","Steel","Fire"};
				resists= new String[]{"Ice"};//truly best defensive type
			break;
		}
		if(returntype==0){
			return weakTo;
		}else{
			return resists;
		}
	}
	
	/* types list index from PokemonMaker3000
		"Fire"=0,     "Water"=1,    "Grass"=2,
		"Normal"=3,   "Fighting"=4, "Flying"=5,
		"Poison"=6,   "Ground"=7,   "Rock"=8,
		"Bug"=9,      "Ghost"=10,   "Steel"=11,
		"Electric"=12,"Psychic"=13, "Ice"=14,
		"Dragon"=15,  "Dark"=16,    "Fairy"=17
	*/
	
	
	protected void setTypesWnR(){
		this.weakTo = getListOfWnR(this.type,0);
		this.resists = getListOfWnR(this.type,1);
		
		String[] typesVector = PokemonMaker3000.getTypesVector();
		
		for(int i=0;i<weakToMults.length;i++){
			weakToMults[i]=0; //set all to zero (neutral)
		}
		
		for(int i=0;i<weakToMults.length;i++){
			
			if(this.isWeakToType1(typesVector[i])){
				weakToMults[i]+=1;
			}
			
			if(this.resistsType1(typesVector[i])){
				weakToMults[i]-=1;
			}
		}
		
		if(!this.type2.equals("")){
			
			for(int i=0;i<weakToMults.length;i++){
			
				if(this.isWeakToType2(typesVector[i])){
					weakToMults[i]+=1;
				}
				
				if(this.resistsType2(typesVector[i])){
					weakToMults[i]-=1;
				}
				
			}
			
		}
		
		this.finallizeWnR();
		
	}
	
	private void finallizeWnR(){
		String[] typesVector = PokemonMaker3000.getTypesVector();
		String[] newWeakto = new String[18];
		String[] newRes = new String[18];
		int counterWea=0;
		int counterRes=0;
		
		for(int i=0;i<18;i++){
			
			if(weakToMults[i]>0){
				counterWea++;
				newWeakto[i]=typesVector[i];
			}
			
			if(weakToMults[i]<0){
				counterRes++;
				newRes[i]=typesVector[i];
			}
		}
		
		int j=0;
		int k=0;
		
		this.weakTo = new String[counterWea];
		this.resists = new String[counterRes];
		
		for(int i=0;i<18;i++){
			
			if(newWeakto[i]!=null){
				this.weakTo[j]=newWeakto[i]; //trim
				j++;
			}
			
			if(newRes[i]!=null){
				this.resists[k]=newRes[i];
				k++;
			}
			
		}
		
	}

	public void defineAllMoves(){
		this.moveset[1][0]=defineMove(this.moveset[0][0]);
		this.moveset[1][1]=defineMove(this.moveset[0][1]);
		this.moveset[1][2]=defineMove(this.moveset[0][2]);
		this.moveset[1][3]=defineMove(this.moveset[0][3]);
	}

	protected String defineMove(String move){		
		PokemonDB db = new PokemonDB();
		String[] data = db.getMoveData(move);
		String tiep = "";
		if(data[1].equals("0")){
			tiep = "Status Effect";
		}else{
			tiep = data[0]+" Attack";
		}
		
		return tiep;
	}
}//class Pokemon ends