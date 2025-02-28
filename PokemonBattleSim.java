import java.util.*;
import java.io.*;
import java.util.concurrent.*;

// @author LaggyDogeHax :P

public class PokemonBattleSim{
	static final String OsName = System.getProperty("os.name");
	static char s='s', m='m';

	static Pokemon[] playerMons = new Pokemon[3];
	static Pokemon[] cpuMons = new Pokemon[3];
	//cpu >> ai  there's NO intelligence to be found here

	static int playerMonActive=0;
	static int cpuMonActive=0;
	
	static Random rng = new Random();
	static Scanner tcl = new Scanner(System.in); //STATIC SCANNER, LES GOOOO
	static String cpuName = getNewCPUName(); // random cpu name
	
	private static String[] getPkmnNamesVector(){
		String[] pkmnNamesVector = new String[]{
		"Venusaur","Blastoise","Charizard",  "Meowscarada","Ninetales","Empoleon",  "Raichu","Mewtwo","Gengar",
		"Dragonite","Absol","Gardevoir",     "Glaceon","Luxray","Lucario",          "Duraludon","Mismagius","Golisopod",
		"Heracross","Rampardos","Lycanroc",  "Aurorus","Dugtrio","Sandlash",        "Arbok","Sneasler","Pidgeot",
		"Lugia","Urshifu","Audino",          "Tauros","Sylveon","Tinkaton",         "Zarude","Dragapult","Mawile",
		//-----page 2----// each page shall be 36 mons, 2 of each type
		"Blaziken","Vaporeon","Decidueye",   "Flareon","Lapras","Tsareena",         "Ursaluna","Braviary","Toxtricity",
		"Krookodile","Toucannon","Zeraora",  "Weezing","Walking Wake","Togekiss",   "Drapion","Roaring Moon","Florges",
		"Lopunny","Cinccino","Hawlucha",     "Flutter Mane","Trevenant","Volcarona","Vespiquen","Pangoro","Aggron",
		"Scizor","Mew","Alakazam",           "Froslass","Baxcalibur","Hydreigon",   "Zoroark","Solrock","Lunatone",
		//-----page 3----//
		"Delphox","Gyarados","Sceptile",     "Typhlosion","Greninja","Leafeon",     "Donphan","Corviknight","Umbreon",
		"Jolteon","Espeon","Eevee",          "Arceus","Citrus","Toxicroak",         "Cyclizar","Garchomp","Gholdengo",
		"Galvantula","Ceruledge","Chandelure","Flamigo","Zamazenta","Zacian",       "Magearna","Cresselia","Kingambit",
		"Azumarill","Gallade","Seviper",     "Garganacl","Diance","Chien-Pao",      "Weavile","Yanmega","Kleavor"};
		
		return pkmnNamesVector;
	}

	public static void main(String[] args)throws IOException, InterruptedException{
		String selecshon="";
		boolean correctName=false;
		boolean errBypass=false; //this is here so the invalid msg can be skipped o_o
		int page=1,lastPage=3;
		String[] pkmnNamesVector = getPkmnNamesVector();
		
		do{
			clear();
			selecshon="";
			
			System.out.println(Clr.YELLOW_BB+"[Pokemon Battle Sim beta5 dev1]"+Clr.R);
			System.out.println("Choose a Pokemon!!");
			System.out.println("Type its name to select it");
			System.out.println("Type a number to view that page");
			System.out.println("Type help for more commands");
			if(playerMons[0]==null){
				System.out.println("");
			} else{
				printPlayerTeam();
			}

			System.out.println("");
			
			printPkmnNamesPage(pkmnNamesVector,page,lastPage);
			
			System.out.print(">");
			selecshon=tcl.nextLine();

			try{//auto capitalize
				
				selecshon=autoCapitalizeMonName(selecshon);
				correctName=isNameCorrect(selecshon);

				if(selecshon.equals("Rng")){
					correctName=true;
				}if(selecshon.equals("Custom")){
					correctName=true;
				}if(selecshon.equals("Help") || selecshon.equals("6mon") || selecshon.equals("3mon")
					|| selecshon.equals("Cpu") || selecshon.equals("Reset")){
					errBypass=true;
				}
				
			}catch(StringIndexOutOfBoundsException e){
				correctName=false;
			}

			if(!correctName && !errBypass){//try page switch
				try{
					int selecInt=0;
					selecInt = Integer.parseInt(selecshon);
					if(selecInt>0 && selecInt<=lastPage){
						page=selecInt;
						correctName=false;
						errBypass=true;
						selecshon="";
					}else{
						selecshon="";
						errBypass=false;
					}
				}catch(NumberFormatException e){
					selecshon=""; //will throw invalid option
					errBypass=false;
				}
			}

			//op spaguetti
			if(!correctName && !errBypass){
				System.out.println("Invalid option, please try again");
				wair(s,1);
			}if(!correctName && errBypass){
				if(selecshon.equals("Help")){
					printHelpMMScreen();
				}if(selecshon.equals("6mon")){
					Pokemon[] mon1 = new Pokemon[6];
					Pokemon[] mon2 = new Pokemon[6];
					for(int i=0;i<playerMons.length;i++){
						mon1[i]=playerMons[i];
						mon2[i]=cpuMons[i];
					}
					playerMons = new Pokemon[6];
					cpuMons = new Pokemon[6];
					for(int i=0;i<playerMons.length;i++){
						playerMons[i]=mon1[i];
						cpuMons[i]=mon2[i];
					}
					System.out.println("Team size changed to 6 Pokemon");
					wair(s,2);
				}if(selecshon.equals("3mon")){
					Pokemon[] mon1 = new Pokemon[3];
					Pokemon[] mon2 = new Pokemon[3];
					for(int i=0;i<2;i++){
						mon1[i]=playerMons[i];
						mon2[i]=cpuMons[i];
					}
					playerMons = new Pokemon[3];
					cpuMons = new Pokemon[3];
					for(int i=0;i<2;i++){
						playerMons[i]=mon1[i];
						cpuMons[i]=mon2[i];
					}
					System.out.println("Team size changed to 3 Pokemon");
					wair(s,2);
				}if(selecshon.equals("Cpu")){
					cpuTeamManager(lastPage);
				}if(selecshon.equals("Reset")){
					playerMons= new Pokemon[playerMons.length];
					System.out.println("Player Team has been reset.");
					wair(s,2);
				}
				errBypass=false;
			}
			if(correctName){
				switch(selecshon){
					case "Rng":
						for(int i=0;i<playerMons.length;i++){
							savePokemonInTeam(pkmnNamesVector[rng.nextInt(pkmnNamesVector.length)]);
						}
					break;
					case "Custom": //----HELL YEA CUSTOM MON
						Pokemon customMon=PokemonMaker3000.makeCustomMon();
						if(customMon!=null){
							for(int i=0;i<playerMons.length;i++){
								if(playerMons[i]==null){
									playerMons[i] = customMon;
									break;
								}
							}
						}
					break;
					default://other

					printSelectedMonInfo(selecshon);
					
					System.out.println("");
					System.out.println("Confirm?  [1]: Yes [2]: No");
					try{
						int selecshon2 = tcl.nextInt();
						tcl.nextLine();
				
						if(selecshon2==1){
							correctName=true;
							savePokemonInTeam(selecshon);
						} else{
							correctName=false;
						}
					}catch(InputMismatchException e){
						correctName=false;
					}
					break;
				}
			}
			
		}while(!correctName || playerMons[(playerMons.length)-1]==null);
		
		//----------ASSIGN RANDOM POKEMON TO CPU------------//
		for(int i=0; i<cpuMons.length;i++){
			savePokemonInCPUTeam(pkmnNamesVector[rng.nextInt(pkmnNamesVector.length)]);
		}
		
		//-----------SHOW PLAYER AND CPU TEAMS------------//
		clear();
		System.out.println("");
		printPlayerTeam();
		System.out.println("                 VS");
		printCPUTeam();
		System.out.println("");
		wair(s,1);
		System.out.println(Clr.RED_B+"Epic battle begins in:"+Clr.R);
		wair(s,1);
		System.out.println("3");
		wair(s,1);
		System.out.println("2");
		wair(s,1);
		System.out.println("1");
		wair(s,1);

		doTheBattling();//<-- battle loop
	}//main method ends
	
	//---------------STORE METHODS------------//
	static void savePokemonInTeam(String name){
		for(int i=0;i<playerMons.length;i++){
			if(playerMons[i]==null){
				playerMons[i] = new Pokemon(name);
				break;
			}
		}
	}
	
	static void savePokemonInCPUTeam(String name){
		for(int i=0;i<cpuMons.length;i++){
			if(cpuMons[i]==null){
				cpuMons[i] = new Pokemon(name);
				break;
			}
		}
	}

	//---------------------------------------------//

	static int moveSelec=0;
	static int moveSelec2=0;
	static int battleMenuSelec=0;  //<-- quite the important variables if i say so myself
	static int cpuMoveSelec=0;
	static boolean cpuJustSwitched=false; //<--prevent cpu from switching twice in a row
	static boolean doublehitPlayer=false;
	static boolean doublehitCpu=false;
	static boolean plyCanMegaEvolve=true; //can only mega-evolve once per battle
	static boolean cpuCanMegaEvolve=true;
	static boolean[] plyCanFreeFromAilment = new boolean[]{true,true,true};
	static boolean[] cpuCanFreeFromAilment = new boolean[]{true,true,true};
	//------misc variables for funsies---//
	static int numbahOfTurns=0;
	static int highestDamage=0;
	static int totalPlayerDamage=0;
	static int totalCPUDamage=0;
	static String highestDamageName="";

	//----------POKEMON BATTLE METHODS-------------//
	
	private static void doTheBattling()throws IOException, InterruptedException{
		//playerMons[playerMonActive].currentHP=1;//testing healing moves :)
		boolean playerFirst=true;
		boolean p1SkipTurn=false,cpuSkipTurn=false;
		boolean plyWillMegaEvolve=false;
		boolean cpuWillMegaEvolve=false;
		
		do{//---------------BATTLE!!!!!!!!!---------------//
			//get player inputs frfr
			do{
				clear();
				battleMenuSelec=0;
				moveSelec=0;
		
				printBattleHUDThing();
				System.out.println("What should "+playerMons[playerMonActive].name+" do?");
				System.out.println("________________________________________________");
				printBattleMenuOptions();
					
				try{
					battleMenuSelec=tcl.nextInt();
				}catch(InputMismatchException e){
					battleMenuSelec=0;
					tcl.nextLine();
				}
				if(battleMenuSelec==3){ //select an item to use
					tcl.nextLine();
					int selecItem=printSelectBattleItem();
					if(selecItem!=69){
						p1SkipTurn=battleItemsHandler(selecItem);
						if(p1SkipTurn){
							break;
						}else{ //will mega evolve :)
							plyWillMegaEvolve=true;
						}
					}
				}
				if(battleMenuSelec==4){//mon info
					printMonInfo();
				}
				
			}while(battleMenuSelec!=1 && epicSoftLockPrevention1());
			
			if(battleMenuSelec==1){ //chose to fight!!!!!
				moveSelec=0;
				
				do{
					clear();
					printBattleHUDThing();
					System.out.println("What should "+playerMons[playerMonActive].name+" do?");
					System.out.println("________________________________________________");
					printPlayerActivePkmnMoveset(plyWillMegaEvolve);

					try{
						moveSelec=tcl.nextInt();
					}catch(InputMismatchException e){
						moveSelec=69;
						tcl.nextLine();
					}
					
				}while(moveSelec!=1 && moveSelec!=2 && moveSelec!=3 && moveSelec!=4);
			
				moveSelec--;

				if(playerMons[playerMonActive].energyDrink){
					do{
						clear();
						printBattleHUDThing();
						System.out.println("What should "+playerMons[playerMonActive].name+" do after "+playerMons[playerMonActive].moveset[0][moveSelec]+"?");
						System.out.println("________________________________________________");
						printPlayerActivePkmnMoveset(plyWillMegaEvolve);

						try{
							moveSelec2=tcl.nextInt();
						}catch(InputMismatchException e){
							moveSelec2=69;
							tcl.nextLine();
						}
						
					}while(moveSelec2!=1 && moveSelec2!=2 && moveSelec2!=3 && moveSelec2!=4);
					moveSelec2--;
				}

			}if(battleMenuSelec==2){ //SWITCH PLAYER POKEMON
				p1SkipTurn=true;
				playerSwitchMon();
			}

			//-----------cpu ai---------//
			cpuMoveSelec=0;
			cpuMoveSelec=cpuAIHandler();
			if(cpuMoveSelec==69){//cpu switch mon
				cpuSkipTurn=true;
				cpuMoveSelec=0;
				cpuSwitchMon();
			}if(cpuMoveSelec==420){ //mega-evolve
				cpuSkipTurn=false;
				cpuWillMegaEvolve=true;
				do{ //force cpu to choose a move xd
					cpuMoveSelec=cpuAIHandler();
				}while(cpuMoveSelec>3);
			}if(cpuMoveSelec>600 && cpuMoveSelec<700){
				cpuSkipTurn=true;
				cpuWillMegaEvolve=false;
				cpuBattleItemsHandler(cpuMoveSelec);
				cpuMoveSelec=0;
			}
			//-------------------------//

			//---epic battle preparations----//
			
			playerFirst=whoGoesFirst();
			if(plyWillMegaEvolve && playerFirst && !p1SkipTurn && plyCanMegaEvolve){
				plyWillMegaEvolve=false;
				playerMegaEvolveSequence();
			}
			if(playerMons[playerMonActive].isParalized && playerFirst && !p1SkipTurn){
				p1SkipTurn=rollForParalysis(playerMons[playerMonActive]);
			}
			
			//-------------------------------//
			
			//this is where da epic battle takes place
			if(playerFirst && !p1SkipTurn){
				//player first
				if(!p1SkipTurn){
					plyerTurn();
					if(cpuMons[cpuMonActive].currentHP==0){
						cpuSkipTurn=true;
					}
					if(playerMons[playerMonActive].energyDrink){
						moveSelec=moveSelec2;
						plyerTurn();
						if(cpuMons[cpuMonActive].currentHP==0){
							cpuSkipTurn=true;
						}
					}
				}else{ p1SkipTurn=false;}

				if(!cpuSkipTurn){
					if(cpuWillMegaEvolve && cpuCanMegaEvolve){
						cpuCanMegaEvolve=false;
						cpuMegaEvolveSequence();
					}
					cpuSkipTurn=rollForParalysis(cpuMons[cpuMonActive]);
					if(!cpuSkipTurn){
						cpuTurn();
					}else{
						cpuSkipTurn=false;
					}
				}else{
					cpuSkipTurn=false;
				}				
				
			}else{
				//cpu first
				if(!cpuSkipTurn){
					if(cpuWillMegaEvolve && cpuCanMegaEvolve){
						cpuCanMegaEvolve=false;
						cpuMegaEvolveSequence();
					}
					cpuSkipTurn=rollForParalysis(cpuMons[cpuMonActive]);
					if(!cpuSkipTurn){
						cpuTurn();
						if(playerMons[playerMonActive].currentHP==0){//fainted lol
							p1SkipTurn=true;
							//let the block of code below handle pkmon switching
							wair(s,1);
						}
					}else{
						cpuSkipTurn=false;
					}
				}else{cpuSkipTurn=false;}
				if(!p1SkipTurn){
					if(plyCanMegaEvolve && plyWillMegaEvolve){//mega evolve
						plyWillMegaEvolve=false;
						playerMegaEvolveSequence();
					}
					p1SkipTurn=rollForParalysis(playerMons[playerMonActive]);
					if(!p1SkipTurn){
						plyerTurn();
						if(playerMons[playerMonActive].energyDrink){
							moveSelec=moveSelec2;
							plyerTurn();
							if(cpuMons[cpuMonActive].currentHP==0){
								cpuSkipTurn=true;
							}
						}
					}else{
						p1SkipTurn=false;
					}
				} else{
					p1SkipTurn=false;
				}
			}

			statusAilmentsHandler(); //burn, poison, HoT, paralysis statuses

			//----------------------CPU------------------//
			if(cpuMons[cpuMonActive].currentHP==0){//if mon ded-- i mean fainted
				//change mon
				//int switching=0;
				System.out.println(cpuMons[cpuMonActive].name+" fainted!");
				wair(s,2);
				if(checkAllCPUMons()){
					cpuSwitchMon();
					cpuSkipTurn=false;
				}else{
					System.out.println(cpuName+" is out of Pokemon!");
					wair(s,2);
				}
			}
			//-------------end of cpu section--------------// <---bro is out of cpus after this

			//player's mon fainted 
			if(playerMons[playerMonActive].currentHP==0){
				System.out.println(playerMons[playerMonActive].name+" fainted!");
				wair(s,2);		
				if(checkAllPlayerMons()){
					playerSwitchMon();
				}else{
					System.out.println("You're out of Pokemon!!");
					wair(s,3);
				}
			}

			//---- end of turn stuff ----//
			numbahOfTurns++;
			plyWillMegaEvolve=false;
			cpuWillMegaEvolve=false;
			plyCanFreeFromAilment = new boolean[]{true,true,true};
			cpuCanFreeFromAilment = new boolean[]{true,true,true};
			
			if(playerMons[playerMonActive].permaBurn){
				playerMons[playerMonActive].isBurning=true;
				plyCanFreeFromAilment[0]=false;
			}if(cpuMons[cpuMonActive].permaBurn){
				cpuMons[cpuMonActive].isBurning=true;
				cpuCanFreeFromAilment[0]=false;
			}
			
		}while(checkAllPlayerMons() && checkAllCPUMons());

		//--------end result screen-------//
		if(checkAllCPUMons()){
			//player dieded
			clear();
			System.out.println("...");
			wair(s,2);
			System.out.println("Your team got wiped out!!");
			wair(s,2);
			System.out.println("");
			printPlayerTeam();
			printCPUTeam();
			System.out.println("");
			wair(s,2);
			System.out.println(cpuName+" Won!!");
			System.out.println("Better Luck next time!");
			wair(s,1);
			printMiscStats();
			wair(s,5);
		}else{//<-- this means that if somehow both are wiped out, cpu wins by deault
			  // but im too lazy to add an extremely rare "YOU TIED!" screen so imma leave it like this
			//cpu dieded
			clear();
			System.out.println("...");
			wair(s,2);
			System.out.println(cpuName+"'s team got wiped out!!");
			wair(s,2);
			System.out.println("");
			printPlayerTeam();
			printCPUTeam();
			System.out.println("");
			wair(s,2);
			System.out.println("You Won!!");
			System.out.println("Congratulations!!!!!!!!!!");
			wair(s,1);
			printMiscStats();
			wair(s,3);
		}
		System.out.println("");
		System.out.println("Press Enter to exit");
		wair(s,1);
		tcl.nextLine();
		tcl.nextLine();
	}

	private static void plyerTurn()throws IOException, InterruptedException{
		playerMons[playerMonActive].extraDmg=rng.nextInt(7);
		if(moveIsAnAttack(playerMons[playerMonActive].moveset[1][moveSelec])){//player mon attacks cpu mon
			int trueDmg=damageCalc(playerMons[playerMonActive], cpuMons[cpuMonActive], moveSelec,0);
			int getSmackedBich=trueDmg;
			boolean crit=false;
			if(rollForCrit(playerMons[playerMonActive], moveSelec, cpuMons[cpuMonActive])){//Flower trick guaranteed crit
				crit=true;
				trueDmg*=2;
				getSmackedBich=trueDmg;
				saveHighestDmg(playerMons[playerMonActive].name, trueDmg);
			}
			
			totalPlayerDamage+=trueDmg;

			if(getSmackedBich>cpuMons[cpuMonActive].currentHP){
				getSmackedBich=cpuMons[cpuMonActive].currentHP;
			}

			clear();
			printBattleHUDThing();
			System.out.println(playerMons[playerMonActive].name+" used "+playerMons[playerMonActive].moveset[0][moveSelec]+"!");
			if(playerMons[playerMonActive].isSpecialMove(moveSelec).equals("magnitude")){
				wair(m,750000);
				System.out.println("Magnitude "+(playerMons[playerMonActive].extraDmg+4)+"!");
			}
			wair(s,1);
			clear();
			cpuMons[cpuMonActive].currentHP-=getSmackedBich;//applies dmg

			printBattleHUDSequence(2, Clr.YELLOW_BB, Clr.YELLOW,true, playerMons[playerMonActive].name+" used "+playerMons[playerMonActive].moveset[0][moveSelec]+"!"); //animation!!
			
			System.out.println(playerMons[playerMonActive].name+" used "+playerMons[playerMonActive].moveset[0][moveSelec]+"!");
			if(playerMons[playerMonActive].isSpecialMove(moveSelec).equals("magnitude")){
				System.out.println("Magnitude "+(playerMons[playerMonActive].extraDmg+4)+"!");
			}
			wair(m,500000);
			
			switch (isMoveEffective(moveSelec, playerMons[playerMonActive], cpuMons[cpuMonActive])){
				case 1:
					System.out.println("It's super effective!!");
					wair(m,750000); //2/3 of a second
				break;
				case 2:
					System.out.println("It's not very effective...");
					wair(m,750000);
				break;
			}
			
			if(crit){
				System.out.println(Clr.RED_B+"Critical Hit!!"+Clr.R);
				crit=false;
				wair(m,750000);
			}
			
			//print dmg dealt
			int numbHits=playerMons[playerMonActive].numberOfHits;
			switch(playerMons[playerMonActive].isSpecialMove(moveSelec)){
				case "rngMultihit":
					numbHits+=playerMons[playerMonActive].extraDmg;
				break;
				case "plus2hit":
					numbHits+=2;
				break;
				case "plus3hit":
					numbHits+=3;
				break;
				case "adversity":
					if(playerMons[playerMonActive].moveset[0][moveSelec].equals("X")){
						numbHits++;
					}
				break;
				case "groupB":
					numbHits+=countAliveMonInTeam(playerMons);
					numbHits--;
				break;
				case "reverseGroupB":
					numbHits+=countAliveMonInTeam(cpuMons);
					numbHits--;
					if(cpuMons[cpuMonActive].currentHP==0){
						numbHits++;
					}
				break;
				case "avenger":
					if(countAliveMonInTeam(playerMons)==1){
						numbHits++;
					}
				break;
			}

			int dmgToPrint=(trueDmg/numbHits);
			int[] dmgVector=null;
			if(numbHits>1){
				dmgVector=randomizeMultihitValues(trueDmg,numbHits);
			}
			for(int i=0;i<numbHits;i++){
				if(numbHits==1){
					System.out.println("Damaged "+cpuMons[cpuMonActive].name+" for "+dmgToPrint+" points");
				}else{
					System.out.print("Damaged "+cpuMons[cpuMonActive].name+" for "+dmgVector[i]+" points");
					if(i==numbHits-1){
						System.out.print(" ("+(trueDmg)+")!");
					}
					System.out.println();
				}
				wair(m,80000);
			}
			if(!playerMons[playerMonActive].energyDrink){
				wair(s,1);
			}
			//if is special
			specialMoveHandlerPlayerToCPU(moveSelec,getSmackedBich/numbHits);
			wair(s,1);
		} else {//move is a status effect
			clear();
			printBattleHUDThing();
			System.out.println(playerMons[playerMonActive].name+" used "+playerMons[playerMonActive].moveset[0][moveSelec]+"!");
			wair(s,1);
			//hands it over to a function mwahahah
			statusPlayerHandler(playerMons[playerMonActive].moveset[0][moveSelec]);
			wair(s,2);
		}
	}

	private static void playerSwitchMon()throws IOException, InterruptedException{
		int switchin=0;
		//boolean flag1=true;
		clear();
		printBattleHUDThing();
		System.out.println("Select one of your Pokemon to switch in:");
		System.out.println("________________________________________________");
		for(int i=0;i<playerMons.length;i++){
			if(i!=playerMonActive){
				String monHP= "HP: ["+playerMons[i].currentHP+" / "+playerMons[i].baseHP+"]";
				System.out.print("["+(i+1)+"] "+playerMons[i].name);
				for(int j=0;j<16-playerMons[i].name.length();j++){
					System.out.print(" ");
				}
				System.out.println("| "+monHP+"["+playerMons[i].type+"]");
			}
		}
		System.out.println("");
		do{
			try{
				switchin=tcl.nextInt();
				if(switchin>0){
					switchin--;
				}
			}catch(InputMismatchException e){
				switchin=99;
				tcl.nextLine();
			}
				
			try{
				if(playerMons[switchin].currentHP==0){
					System.out.println("That Pokemon can't continue battling...");
				}
			}catch(ArrayIndexOutOfBoundsException e){
				switchin=99;
			}
			
		}while(checkSwitchIn(switchin,playerMonActive,playerMons) || playerMons[switchin].currentHP==0);

		clear();
		printBattleHUDThing();
		System.out.println(getRandomSwitchOutQuote(playerMons[playerMonActive].name));
		wair(s,2);

		playerMons[playerMonActive].resetStats();

		playerMonActive=switchin;
		if(playerMons[playerMonActive].permaBurn){
			playerMons[playerMonActive].isBurning=true;
		}
		
		clear();
		printBattleHUDThing();
		System.out.println(getRandomSwitchInQuote(playerMons[playerMonActive].name));
		wair(s,2);
	}

	private static void cpuTurn()throws IOException, InterruptedException{
		cpuMons[cpuMonActive].extraDmg=rng.nextInt(7);
		if(moveIsAnAttack(cpuMons[cpuMonActive].moveset[1][cpuMoveSelec])){//cpu mon attacks player mon
			int trueDmg=damageCalc(cpuMons[cpuMonActive], playerMons[playerMonActive], cpuMoveSelec,1);
			int getSmackedBich=trueDmg;
			boolean crit=false;
			if(rollForCrit(cpuMons[cpuMonActive], cpuMoveSelec, playerMons[playerMonActive])){//guranteedcrit
				crit=true;
				trueDmg*=2;
				getSmackedBich=trueDmg;
				saveHighestDmg(cpuMons[cpuMonActive].name, trueDmg);
			}

			totalCPUDamage+=trueDmg;
			
			if(getSmackedBich>playerMons[playerMonActive].currentHP){
				getSmackedBich=playerMons[playerMonActive].currentHP;
			}

			clear();
			printBattleHUDThing();
			System.out.println(cpuMons[cpuMonActive].name+" used "+cpuMons[cpuMonActive].moveset[0][cpuMoveSelec]+"!");
			if(cpuMons[cpuMonActive].isSpecialMove(cpuMoveSelec).equals("magnitude")){
				wair(m,750000);
				System.out.println("Magnitude "+(cpuMons[cpuMonActive].extraDmg+4)+"!");
			}
			wair(s,1);
			clear();
			playerMons[playerMonActive].currentHP-=getSmackedBich;//applies dmg

			printBattleHUDSequence(1, Clr.CYAN_BB, Clr.CYAN,false, cpuMons[cpuMonActive].name+" used "+cpuMons[cpuMonActive].moveset[0][cpuMoveSelec]+"!");

			System.out.println(cpuMons[cpuMonActive].name+" used "+cpuMons[cpuMonActive].moveset[0][cpuMoveSelec]+"!");
			if(cpuMons[cpuMonActive].isSpecialMove(cpuMoveSelec).equals("magnitude")){
				System.out.println("Magnitude "+(cpuMons[cpuMonActive].extraDmg+4)+"!");
			}
			wair(m,500000);
			
			switch(isMoveEffective(cpuMoveSelec, cpuMons[cpuMonActive],playerMons[playerMonActive])){
				case 0:
					//neutral ouo
				break;
				case 1:
					System.out.println("It's super effective!!");
					wair(m,750000);
				break;
				case 2:
					System.out.println("It's not very effective...");
					wair(m,750000);
				break;
			}
			
			if(crit){
				System.out.println(Clr.RED_B+"Critical Hit!!"+Clr.R);
				crit=false;
				wair(m,750000);
			}
			
			//print dmg dealt
			int numbHits=cpuMons[cpuMonActive].numberOfHits;
			switch(cpuMons[cpuMonActive].isSpecialMove(cpuMoveSelec)){
				case "rngMultihit":
					numbHits+=cpuMons[cpuMonActive].extraDmg;
				break;
				case "plus2hit":
					numbHits+=2;
				break;
				case "plus3hit":
					numbHits+=3;
				break;
				case "adversity":
					if(cpuMons[cpuMonActive].moveset[0][moveSelec].equals("X")){
						numbHits++;
					}
				break;
				case "groupB":
					numbHits+=countAliveMonInTeam(cpuMons);
					numbHits--;
				break;
				case "reverseGroupB":
					numbHits+=countAliveMonInTeam(playerMons);
					numbHits--;
					if(playerMons[playerMonActive].currentHP==0){
						numbHits++;
					}
				break;
				case "avenger":
					if(countAliveMonInTeam(cpuMons)==1){
						numbHits++;
					}
				break;
			}

			int dmgToPrint=(trueDmg/numbHits);
			int[] dmgVector=null;
			if(numbHits>1){
				dmgVector=randomizeMultihitValues(trueDmg,numbHits);
			}
			for(int i=0;i<numbHits;i++){
				if(numbHits==1){
					System.out.println("Damaged "+playerMons[playerMonActive].name+" for "+dmgToPrint+" points");
				}else{
					System.out.print("Damaged "+playerMons[playerMonActive].name+" for "+dmgVector[i]+" points");
					if(i==numbHits-1){
						System.out.print(" ("+(trueDmg)+")!");
					}
					System.out.println();
				}
				wair(m,80000);
			}
			wair(s,1);
			//if is special
			specialMoveHandlerCPUToPlayer(cpuMoveSelec,getSmackedBich);
			wair(s,1);
		} else {//move is a status effect
			clear();
			printBattleHUDThing();
			System.out.println(cpuMons[cpuMonActive].name+" used "+cpuMons[cpuMonActive].moveset[0][cpuMoveSelec]+"!");
			wair(s,1);
			//hands it over to a function mwahahah
			statusCPUHandler(cpuMons[cpuMonActive].moveset[0][cpuMoveSelec]);

			wair(s,2);
		}
	}

	private static void cpuSwitchMon()throws IOException, InterruptedException{
		//run a check before calling this method
		prevCpuMove="";
		int switching=0;
		//try to find a mon in cpu team with advantage over the active player pokemon
		int advantageousBoi=cpuMonActive;
		for(int i=0;i<playerMons.length;i++){
			if(i!=cpuMonActive && cpuMons[i].currentHP>0){
				String typ = cpuMons[i].type;
				if(playerMons[playerMonActive].isWeakToType(typ)){
					advantageousBoi=i;
				}
			}
		}
		if(advantageousBoi!=cpuMonActive){//successfully found mon
			switching=advantageousBoi;
		}else{
			do{
				switching=rng.nextInt(cpuMons.length);
			}while(checkSwitchIn(switching, cpuMonActive, cpuMons) || cpuMons[switching].currentHP==0);
		}
		clear();
		printBattleHUDThing();
		System.out.println(getRandomSwitchOutQuote(cpuMons[cpuMonActive].name));
		wair(s,2);

		cpuMons[cpuMonActive].resetStats();

		cpuMonActive=switching;
		
		if(cpuMons[cpuMonActive].permaBurn){
			cpuMons[cpuMonActive].isBurning=true;
		}
	
		clear();
		printBattleHUDThing();
		System.out.println(getRandomSwitchInQuote(cpuMons[cpuMonActive].name,true));
		wair(s,2);
	}

	private static int isMoveEffective(int moveselec, Pokemon mon1, Pokemon mon2){
		//String mon2Type=mon2.type;
		String movType=mon1.moveset[1][moveselec];
		String movName=mon1.moveset[0][moveselec];
		
		if(mon2.resistsType(movType)){
			if(movName.equals("Freeze Dry") && mon2.type.equals("Water")
			|| (movName.equals("Flying Press") && mon2.isWeakToType("Flying"))
			|| mon1.isSpecialMove(moveselec).equals("supEffective")){
				//freeze dry is super effective agaisnt water
				//Flying Press doubles as a Flying type move
				//judgement is almighty!!!!!
				return 1; // SUPER EFFECTIVE!
			}else{
				return 2; // NOT VERY EFFECTIVE!
			}
		}if(mon2.isWeakToType(movType)
			|| (movName.equals("Flying Press") && mon2.isWeakToType("Flying"))
			|| mon1.isSpecialMove(moveselec).equals("supEffective")){
			return 1; //SUPER EFFECTIVE!!!
		}
	
		return 0; // NEUTRAL!!
	}


	private static boolean rollForParalysis(Pokemon pkmn)throws IOException, InterruptedException{
		//call this only when mon is paralized
		boolean yn=false;//skip turn or not
		if(pkmn.isParalized){
			int para = rng.nextInt(2);
			clear();
			printBattleHUDThing();
			System.out.println(pkmn.name+" is paralized!");
			wair(s,1);
		
			if(para==0){//dont move
				System.out.println(pkmn.name+" can't move!");
				wair(s,2);
				yn=true;
			}else{
				yn=false;
			}
		}
		return yn;
	}

	private static boolean whoGoesFirst(){
		boolean playerfirst=true;
		//thank god these are all static variables ong
		int plySpeed = playerMons[playerMonActive].currentSPEED;
		int cpuSpeed = cpuMons[cpuMonActive].currentSPEED;
		
		if(playerMons[playerMonActive].isParalized){
			plySpeed/=2;
		}if(cpuMons[cpuMonActive].isParalized){ //halve speed stat (for calculation) if paralized
			cpuSpeed/=2;
		}

		//----------determine who goes first------------//
		if(plySpeed>cpuSpeed){
			//player first due to speed stat
			playerfirst=true;
		}else{
			if(cpuSpeed>plySpeed){
			//cpu first all thanks to the speed stat!!
			playerfirst=false;
			}else{
				// if they got same speed
				if(plySpeed==cpuSpeed){
					if(rng.nextInt(2)==0){ // leave it to random chance frfr
						playerfirst=true;
					}else{
						playerfirst=false;
					}
				}
			}
		}
		
		//-------quick attack------//
		if(playerMons[playerMonActive].isSpecialMove(moveSelec).equals("+priority")
		|| cpuMons[cpuMonActive].isSpecialMove(cpuMoveSelec).equals("+priority")){
			//iff if iff iff ififff if if
			//yanderedev would be proud  // what
			if(playerMons[playerMonActive].isSpecialMove(moveSelec).equals("+priority")
			&& (cpuMons[cpuMonActive].isSpecialMove(cpuMoveSelec).equals("+priority"))==false){
				playerfirst=true;
			}else{
				if(cpuMons[cpuMonActive].isSpecialMove(cpuMoveSelec).equals("+priority")
				&& (playerMons[playerMonActive].isSpecialMove(moveSelec).equals("+priority"))==false){
					playerfirst=false;
				}
			}
			// if both use it xd
			if(playerMons[playerMonActive].isSpecialMove(moveSelec).equals("+priority")
			&& cpuMons[cpuMonActive].isSpecialMove(cpuMoveSelec).equals("+priority")){
				if(rng.nextInt(2)==0){ // leave it to random chance frfr
					playerfirst=true;
				}else{
					playerfirst=false;
				}
			}
		}

		return playerfirst;
	}

	private static boolean rollForCrit(Pokemon mon, int movselec,Pokemon enemymon){
		if(mon.isParalized){
			mon.currentSPEED/=2;
		}

		if(mon.moveset[0][movselec].equals("Ruination")){
			return false;
		}

		if((mon.currentSPEED)>rng.nextInt(806)//RANDOM CRIT MWAHAHAHAHAHA
			|| (mon.moveset[0][movselec].equals("Flower Trick"))){//Flower trick guaranteed crit
				return true;
			}else{
				if(enemymon.hasStatusAilment() && mon.moveset[0][movselec].equals("Hex")
				||(enemymon.isPoisoned && mon.moveset[0][movselec].equals("Venoshock"))){
					return true;
				}
			}if(mon.isSpecialMove(movselec).equals("highcritrate")){
				if((mon.currentSPEED)>rng.nextInt(806)){
					return true;//roll again
				}
			}
		if(mon.strike){
			return true;
		}
		return false;
	}

	private static int damageCalc(Pokemon pkmn1, Pokemon pkmn2, int moveInteger, int turnOf){
		//only call this method if the specified move is not a status move
		//turnOf 0 = player, 1 = cpu
		int atk1=pkmn1.currentATK;
		int nHits=pkmn1.numberOfHits;
		int extraD = pkmn1.extraDmg;
		int baseatk1=pkmn1.baseATK;
		double doEmStab=baseatk1;
		int def2=pkmn2.currentDEF;
		//String movetype = pkmn1.moveset[1][moveInteger]; //gets type from selected move slot
		String movename = pkmn1.moveset[0][moveInteger]; //gets move name blablabla
		int totalDmgTaken;
		//formula dmg dealt = (atk+STAB)-(def/2)*weakness or resistance (x2 if weakness, 1/2 if resists)
		//(stab bonus= base atk*1.5);
		//then add a bit of epic randomness by doin a bit or less dmg than normal
		int hehehe = rng.nextInt(21);
		hehehe-=10;

		//time to test a new defense formula, using percentage dmg reduction
		//def range from 0 to 300, 400=100% reduction, 300=75% reduction...
		
		//--if move has special conditions--//
		switch(pkmn1.isSpecialMove(moveInteger)){
			case "lifedrain":
				if(movename.equals("Excite")){
					int lostHP=pkmn1.baseHP- pkmn1.currentHP;
					atk1+=lostHP/2;
					//gain adversity effect
				}
				atk1-=atk1/3;
			break;
			case "overclock":
				atk1*=1.8;//DOUBLE ATK, WOOOOOOO-- nvm it was too op
			break;
			case "doublehit":
				atk1-=atk1/3;
			break;
			case "powerboost"://hyperbeam and some others
				atk1+=atk1/2;
			break;
			case "ignoredef":
				atk1-=atk1/4;
				def2=0;
			break;
			case "defisatk":
				atk1=def2;
			break;
			case "recoil":
				atk1+=atk1/3;
			break;
			case "rngMultihit":
				atk1=atk1/3; //aughgh
				doEmStab/=2; //nerf stab ._.
				doEmStab/=4;
				nHits+=extraD;
			break;
			case "plus2hit":
				atk1-=atk1/2;
				atk1-=atk1/5;
				doEmStab/=2;
				nHits+=2;
			break;
			case "plus3hit":
				atk1-=atk1/1.65;
				doEmStab/=3;
				nHits+=3;
			break;
			case "adversity":
				int lostHP=pkmn1.baseHP- pkmn1.currentHP;
				atk1+=lostHP/2;
				if(movename.equals("X")){
					nHits+=1;
					atk1-=atk1/3;
				}
				if(pkmn1.energyDrink){
					atk1-=atk1/3;
				}
			break;
			case "adversity2":
				atk1/=3;
				lostHP=pkmn1.baseHP- pkmn1.currentHP;
				atk1+=lostHP/2;
				if(pkmn1.energyDrink){
					atk1-=atk1/3;
				}
			break;
			case "supEffective":
				atk1-=atk1/3;
			break;
			case "groupB":
				Pokemon[] monlist = null;
				int monactive=0;
				if(turnOf==0){
					monlist = new Pokemon[playerMons.length];
					monlist=playerMons;
					monactive=playerMonActive;
				}else{
					monlist = new Pokemon[cpuMons.length];
					monlist=cpuMons;
					monactive=cpuMonActive;
				}
				int reduce=1;
				if(monlist.length>3){reduce=14;}else{reduce=4;}

				atk1/=reduce;
				for(int i=0;i<monlist.length;i++){
					if(i!=monactive && monlist[i].currentHP>0){
						atk1+=monlist[monactive].baseATK/reduce;
						nHits+=1;
					}
				}
			break;
			case "reverseGroupB":
				if(turnOf==1){
					monlist = new Pokemon[playerMons.length];
					monlist=playerMons;
				}else{
					monlist = new Pokemon[cpuMons.length];
					monlist=cpuMons;
				}
				
				if(monlist.length>3){reduce=5;}else{reduce=3;}
				
				atk1/=reduce;
				for(int i=0;i<monlist.length;i++){
					if(monlist[i].currentHP>0){
						atk1+=baseatk1/reduce;
					}
				}
			break;
			case "MegaEvolutionHater":
				String opMonName=pkmn2.name;
				if(opMonName.contains("Mega-")){
					atk1*=2;
				}
			break;
			case "avenger":
				//fallen allies = more power for this move
				atk1-=atk1/4;
				if(turnOf==0){
					monlist = new Pokemon[playerMons.length];
					monlist=playerMons;
				}else{
					monlist = new Pokemon[cpuMons.length];
					monlist=cpuMons;
				}
				
				if(monlist.length>3){reduce=4;}else{reduce=2;}
				
				for(int i=0;i<monlist.length;i++){
					if(monlist[i].currentHP<1){
						atk1+=baseatk1/reduce;
					}
				}
				if(countAliveMonInTeam(playerMons)==1 && turnOf==0){
					nHits+=1;
				}if(countAliveMonInTeam(cpuMons)==1 && turnOf==1){
					nHits+=1;
				}
			break;
			case "kamikaze":
				atk1+=doEmStab;
				atk1*=4; //AAAAAAAAAAAAA
			break;
			case "thundercage":
				atk1+=pkmn2.baseHP/8;
			break;
			case "magnitude":
				int mag = pkmn1.extraDmg;
				atk1/=3;
				atk1*=mag;
				nHits+=mag/4;
			break;
			case "buffPowerIfDebuffed":
				if((pkmn1.currentATK<pkmn1.baseATK) || (pkmn1.currentDEF<pkmn1.baseDEF) ||
				(pkmn1.currentSPEED<pkmn1.baseSPEED)){
					atk1+=baseatk1/2;
				}else{
					atk1-=atk1/3;
				}
			break;
			case "osmash":
				atk1-=atk1/3;
			break;
			case "guaranteedCrit":
				atk1-=atk1/5;
			break;
			case "facade":
				atk1-=atk1/5;
				if(pkmn1.hasStatusAilment()){
					atk1*=2; // DOUBLE ATK WOOOOO
				}
			break;
			case "brokenCardMove":
				atk1-=atk1/3;
			break;
			case "scnails":
				atk1/=2;
				monactive=0;
				if(turnOf==1){
					monlist = new Pokemon[playerMons.length];
					monlist=playerMons;
					monactive=playerMonActive;
				}else{
					monlist = new Pokemon[cpuMons.length];
					monlist=cpuMons;
					monactive=cpuMonActive;
				}

				if(monlist[monactive].currentHP<=((monlist[monactive].baseHP/3)*2)){
					atk1*=4;
				}
			break;
		}

		if(pkmn1.hasSTAB(movename)){
			doEmStab=doEmStab/2;
			atk1+=(int)doEmStab;//adds +50% atk from base
		}
		
		float def22=def2;
		def22=def22/400;//<--def/100%
		float atk11=atk1;
		if(def22<=0){
			def22=1;
		}
		totalDmgTaken=(int)(def22*atk11);
		if(def22!=1){
			totalDmgTaken=atk1-totalDmgTaken;
		}
		
		if(totalDmgTaken<2){totalDmgTaken=2;}
		
		switch(isMoveEffective(moveInteger, pkmn1, pkmn2)){
			case 0:
				//neutral ouo
			break;
			case 1:
				totalDmgTaken*=2;
			break;
			case 2:
				totalDmgTaken/=2;
			break;
		}

		totalDmgTaken+=hehehe; //adds the random nonsense
		totalDmgTaken*=nHits; // auk

		if(pkmn1.isSpecialMove(moveInteger)=="cuthp"){
			totalDmgTaken=pkmn2.currentHP/2;
			if(isMoveEffective(moveInteger, pkmn1, pkmn2)==2){
				totalDmgTaken/=2;
			}
		}

		//record highest dmg
		saveHighestDmg(pkmn1.name, totalDmgTaken);

		if(totalDmgTaken<1){totalDmgTaken=1;}
		//if(pkmn2.currentHP-totalDmgTaken<0){totalDmgTaken=pkmn2.currentHP;}

		return totalDmgTaken;//amount to substract from the pokemon's HP
	}

	private static boolean epicSoftLockPrevention1(){// im running out of names for these methods
		boolean sHALLNOTPASS=false;
		int pk1ded=0;
		if(battleMenuSelec==2){
			for(int i=0;i<playerMons.length;i++){
				if(i!=playerMonActive && playerMons[i].currentHP==0){
					pk1ded++;
				}
			}
		}else{
			if(battleMenuSelec!=1 && battleMenuSelec!=2){
				battleMenuSelec=69;
				sHALLNOTPASS=true;
			}
		}

		if(battleMenuSelec==3){ //item selec
			int unusables=0;
			for(int i=0;i<playerMons[playerMonActive].items.length;i++){
				if(playerMons[playerMonActive].items[i].equals("")){
					unusables++;
				}else{
					unusables=0;
					break;
				}
			}
			if(unusables>=(playerMons[playerMonActive].items.length-1)){
				sHALLNOTPASS=true;
				System.out.println("You can't use any more items with this Pokemon");
				wair(s,1);
			}
		}

		if(pk1ded>=(playerMons.length-1)){
			sHALLNOTPASS=true;
			System.out.println("You can't change pokemon right now");
			wair(s,1);
			
		}//prevents player from entering pokmon switch screen if their other mons are ded

		return sHALLNOTPASS;
	}
	
	//this should be a method inside the pokemon class tbh frfr
	private static boolean moveIsAnAttack(String move){
		// move = Pokemon.moveset[1][int] <--gets type of move
		if(move.contains("Attack")){
			return true;
		}else{
			return false;
		}
	}

	static String prevCpuMove=""; //SECRET STATIC VARIABLE!!!
	
	private static int cpuAIHandler(){//still!! no intelligence!!
		Pokemon myMon=playerMons[playerMonActive];
		Pokemon cpuMon=cpuMons[cpuMonActive];
		boolean[] shoulduse = {true,true,true,true};
		String[] monsWeaknesses = myMon.weakTo;
		String[] monsResistances = myMon.resists;
		int rand=0;

		//might switch mon according to these conditions
		if(!cpuJustSwitched){
			if(cpuMon.currentHP<(cpuMon.baseHP/2) || cpuMon.currentATK<30 || cpuMon.currentDEF<(cpuMon.baseDEF/2)
			|| cpuMon.currentSPEED<(cpuMon.baseSPEED/2) || cpuMon.isPoisoned || (cpuMon.isBurning && !cpuMon.permaBurn) || cpuMon.isParalized){
				if(countAliveMonInTeam(cpuMons)>1){
					int shouldswitch=rng.nextInt(100);
					if(shouldswitch>69){
						if(cpuMon.name.contains("Mega-")){
							shouldswitch=rng.nextInt(80);
							if(shouldswitch>69){
								cpuJustSwitched=true;
								return 69; //less likely to switch if mega evolved
							}
						}
						cpuJustSwitched=true;
						return 69; //30% chance to switch
					}
				}
			}
		}
		
		//use item?
		if(cpuMons[cpuMonActive].items.length>1 && myMon.currentHP>=100){
			//mega evolve?
			if(cpuMons[cpuMonActive].canMegaEvolve() && cpuMon.currentHP>(cpuMon.baseHP/2) && cpuCanMegaEvolve){
				if(rng.nextInt(100)>59){
					return 420; //megaevolve cpu
				}
			}

			int selecItem=rng.nextInt(6);
			switch(selecItem){
				case 0:
				if(cpuMon.currentHP<=(cpuMon.baseHP/2) && (cpuMon.hasMoveNameInMoveset("Jungle Healing")==false) && (cpuMon.hasMoveNameInMoveset("Roost")==false) && (cpuMon.hasMoveNameInMoveset("Healing Pulse")==false)){
					if(rng.nextInt(100)>30){
						return 660; //potion
					}
				}
				break;
				case 1:
				if(cpuMon.currentSPEED<=myMon.currentSPEED && (cpuMon.currentSPEED+cpuMon.baseSPEED*1/2)>=myMon.currentSPEED){
					if(rng.nextInt(100)>50){
						return 661; //x-speed
					}
				}
				break;
				case 2:
				if((cpuMon.currentDEF<110 || cpuMon.baseDEF>120) && (cpuMon.hasMoveNameInMoveset("Amnesia")==false)){
					if(rng.nextInt(100)>60){
						return 662; //x-def
					}
				}
				break;
				case 3:
				if(cpuMon.baseATK>89 && (cpuMon.hasMoveNameInMoveset("Sword Dance")==false || cpuMon.hasMoveNameInMoveset("Work Up")==false)){
					if(rng.nextInt(100)>60){
						return 663; //x-attack
					}
				}
				break;
				case 4:
				if(cpuMon.currentATK>100){
					if(rng.nextInt(100)>80){
						return 669; //dash earring
					}
				}
				break;
				case 5:
				if(cpuMon.currentATK>80 || cpuMon.hasMoveNameInMoveset("Facade") || cpuMon.hasMoveNameInMoveset("Judgement")){
					if(rng.nextInt(100)>70){
						return 670; //strike earrings
					}
				}
			}
		}

		if(rand!=69){
			//didnt decide to switch mon
			//look for super effective attacking move
			for(int i=0;i<4;i++){
				for(int j=0;j<monsWeaknesses.length;j++){
					if(cpuMon.moveset[1][i].contains(monsWeaknesses[j])){
						shoulduse[i]=true;
					}
				}
			}
	
			//look for not very effective at all attacking move
			for(int i=0;i<4;i++){
				for(int j=0;j<monsResistances.length;j++){
					if(cpuMon.moveset[1][i].contains(monsResistances[j])){
						shoulduse[i]=false;
					}
				}
			}
			//count nonUsables xd
			int nonUsables=0;
			for(int i=0;i<shoulduse.length;i++){
				if(shoulduse[i]==false){
					nonUsables++;
				}
			}
	
			if(nonUsables==4){
				//YOLO
				rand=rng.nextInt(4);
				if(cpuMons[cpuMonActive].moveset[0][rand].equals(prevCpuMove)){
					rand=rng.nextInt(4);
				}
			}else{
				if(cpuMon.countAttackingMoves()==nonUsables){ //all attacking moves are not effective
					for(int i=0;i<shoulduse.length;i++){
						shoulduse[i]=true;
					}
				}
				do{
					rand=rng.nextInt(4);
					if(cpuMons[cpuMonActive].moveset[0][rand].equals(prevCpuMove)){
						rand=rng.nextInt(4); //roll again ouo
					}
				}while(!epicCpuAiRandomMoveCheckerThing(shoulduse,rand));
			}
		}
		cpuJustSwitched=false;
		if(isMoveEffective(rand,cpuMons[cpuMonActive],playerMons[playerMonActive])==1){
			//if superefective, dont register as prev used move
			prevCpuMove="";
		}else{
			prevCpuMove=cpuMons[cpuMonActive].moveset[0][rand];
		}

		return rand;
	}
	//so many functions aaggfhghfhgfhgfjgfg
	//(^bro also made another fuction just for this)
	
	private static boolean epicCpuAiRandomMoveCheckerThing(boolean[] nu, int num){
		boolean ret=false;
		
		if(nu[num]){
			ret=true;
		}
		if(!moveIsAnAttack(cpuMons[cpuMonActive].moveset[1][num])){
			//tells the cpu if it should use these status moves depending on da situation
			switch(statusMoveHandler(cpuMons[cpuMonActive].moveset[0][num])){
				case "hot":
					if(cpuMons[cpuMonActive].healingOverTime){
						ret=false;}else{ret=true;
					}
				break;
				case "healhalf":
					if(cpuMons[cpuMonActive].currentHP>cpuMons[cpuMonActive].baseHP/1.5){
						ret=false;}else{ret=true;
					}
				break;
				case "buffatk2":
					if(cpuMons[cpuMonActive].currentATK>cpuMons[cpuMonActive].baseATK*2){
						ret=false;}else{ret=true;
					}
				break;
				case "buffatk":
					if(cpuMons[cpuMonActive].currentATK>cpuMons[cpuMonActive].baseATK*2){
						ret=false;}else{ret=true;
					}
				break;
				case "buffdef2":
					if(cpuMons[cpuMonActive].currentDEF>290){
						ret=false;}else{ret=true;
					}
				break;
				case "buffdef":
					if(cpuMons[cpuMonActive].currentDEF>290){
						ret=false;}else{ret=true;
					}
				break;
				case "buffspeed2":
					if(cpuMons[cpuMonActive].currentSPEED>playerMons[playerMonActive].currentSPEED*1.2){
						ret=false;}else{ret=true;
					}
				break;
				case "poison":
					if(playerMons[playerMonActive].isPoisoned==false){
						ret=true;}else{ret=false;
					}
				break;
				case "burn":
					if(playerMons[playerMonActive].isBurning==false){
						ret=true;}else{ret=false;
					}
				break;
				case "debuffatk":
					if(playerMons[playerMonActive].currentATK<playerMons[playerMonActive].baseATK/2){
						ret=false;}else{ret=true;
					}
				break;
				case "debuffdef2":
					if(playerMons[playerMonActive].currentDEF<50){
						ret=false;}else{ret=true;
					}
				break;
				case "debuffdef":
					if(playerMons[playerMonActive].currentDEF<50){
						ret=false;}else{ret=true;
					}
				break;
				case "debuffspeed2":
					if(playerMons[playerMonActive].currentSPEED<playerMons[playerMonActive].baseSPEED/2){
						ret=false;}else{ret=true;
					}
				break;
				case "paralyze":
					if(playerMons[playerMonActive].isParalized==false){
						ret=false;}else{ret=true;
					}
				break;
				default:
					ret=true;
				break;
			}
		}
		// :p
		return ret;
	}

	private static void statusPlayerHandler(String movv){
		switch(statusMoveHandler(movv)){
			case "buffatk&def":
				playerMons[playerMonActive].raiseStat("ATK");
				playerMons[playerMonActive].raiseStat("DEF");
				System.out.println(playerMons[playerMonActive].name+"'s ATK & DEF rose!");
			break;
			case "buffatk":
				playerMons[playerMonActive].raiseStat("ATK");
				System.out.println(playerMons[playerMonActive].name+"'s ATK rose!");
			break;
			case "buffatk2":
				playerMons[playerMonActive].raiseStat("ATK");
				playerMons[playerMonActive].raiseStat("ATK");
				System.out.println(playerMons[playerMonActive].name+"'s ATK rose greatly!");
			break;
			case "buffspeed":
				playerMons[playerMonActive].raiseStat("SPEED");
				System.out.println(playerMons[playerMonActive].name+"'s SPEED rose!");
			break;
			case "buffspeed2":
				playerMons[playerMonActive].raiseStat("SPEED");
				playerMons[playerMonActive].raiseStat("SPEED");
				System.out.println(playerMons[playerMonActive].name+"'s SPEED rose greatly!");
			break;
			case "buffatk&speed":
				playerMons[playerMonActive].raiseStat("ATK");
				playerMons[playerMonActive].raiseStat("SPEED");
				System.out.println(playerMons[playerMonActive].name+"'s ATK & SPEED rose!");
			break;
			case "buffdef":
				playerMons[playerMonActive].raiseStat("DEF");
				System.out.println(playerMons[playerMonActive].name+"'s DEF rose!");
			break;
			case "buffdef2":
				playerMons[playerMonActive].raiseStat("DEF");
				playerMons[playerMonActive].raiseStat("DEF");
				System.out.println(playerMons[playerMonActive].name+"'s DEF rose greatly!");
			break;
			case "debuffdef":
				cpuMons[cpuMonActive].decreaseStat("DEF");
				System.out.println(cpuMons[cpuMonActive].name+"'s DEF fell!");
			break;
			case "debuffdef2":
				cpuMons[cpuMonActive].decreaseStat("DEF");
				cpuMons[cpuMonActive].decreaseStat("DEF");
				System.out.println(cpuMons[cpuMonActive].name+"'s DEF fell greatly!");
			break;
			case "debuffspeed2":
				cpuMons[cpuMonActive].decreaseStat("SPEED");
				cpuMons[cpuMonActive].decreaseStat("SPEED");
				System.out.println(cpuMons[cpuMonActive].name+"'s SPEED fell greatly!");
			break;
			case "debuffatk":
				cpuMons[cpuMonActive].decreaseStat("ATK");
				System.out.println(cpuMons[cpuMonActive].name+"'s ATK fell!");
			break;
			case "debuffatk2":
				cpuMons[cpuMonActive].decreaseStat("ATK");
				cpuMons[cpuMonActive].decreaseStat("ATK");
				System.out.println(cpuMons[cpuMonActive].name+"'s ATK fell greatly!");
			break;
			case "healhalf":
				playerMons[playerMonActive].healSelf("half");
				System.out.println(playerMons[playerMonActive].name+" recovered health!");
			break;
			case "poison":
				cpuMons[cpuMonActive].isPoisoned=true;
				System.out.println(cpuMons[cpuMonActive].name+" is badly poisoned!");
				cpuCanFreeFromAilment[1]=false;
			break;
			case "burn":
				cpuMons[cpuMonActive].isBurning=true;
				System.out.println(cpuMons[cpuMonActive].name+" is on fire!");
				cpuCanFreeFromAilment[0]=false;
			break;
			case "paralyze":
				cpuMons[cpuMonActive].isParalized=true;
				System.out.println(cpuMons[cpuMonActive].name+" is paralized! it may not move!");
				cpuCanFreeFromAilment[2]=false;
			break;
			case "hot":
				playerMons[playerMonActive].healingOverTime=true;
				System.out.println(playerMons[playerMonActive].name+" will recover HP over time!");
			break;
			case "lr":
				playerMons[playerMonActive].isBurning=true;
				System.out.println(playerMons[playerMonActive].name+" is in deep trouble!!");
				if(((playerMons[playerMonActive].currentHP*2)/1.65) > playerMons[playerMonActive].baseHP){
					playerMons[playerMonActive].aukBurning();
					playerMons[playerMonActive].aukPoisoned();
				}
				int lostHP=playerMons[playerMonActive].baseHP - playerMons[playerMonActive].currentHP;
				playerMons[playerMonActive].currentATK+=lostHP;
				wair(s,1);
				System.out.println(playerMons[playerMonActive].name+" gained "+lostHP+" ATK!");
			break;
		}
	}

	private static void statusCPUHandler(String movv){
		switch(statusMoveHandler(movv)){
			case "buffatk&def":
				cpuMons[cpuMonActive].raiseStat("ATK");
				cpuMons[cpuMonActive].raiseStat("DEF");
				System.out.println(cpuMons[cpuMonActive].name+"'s ATK & DEF rose!");
			break;
			case "buffatk":
				cpuMons[cpuMonActive].raiseStat("ATK");
				System.out.println(cpuMons[cpuMonActive].name+"'s ATK rose!");
			break;
			case "buffatk2":
				cpuMons[cpuMonActive].raiseStat("ATK");
				cpuMons[cpuMonActive].raiseStat("ATK");
				System.out.println(cpuMons[cpuMonActive].name+"'s ATK rose greatly!");
			break;
			case "buffatk&speed":
				cpuMons[cpuMonActive].raiseStat("ATK");
				cpuMons[cpuMonActive].raiseStat("SPEED");
				System.out.println(cpuMons[cpuMonActive].name+"'s ATK & SPEED rose!");
			break;
			case "buffspeed":
				cpuMons[cpuMonActive].raiseStat("SPEED");
				System.out.println(cpuMons[cpuMonActive].name+"'s SPEED rose!");
			break;
			case "buffspeed2":
				cpuMons[cpuMonActive].raiseStat("SPEED");
				cpuMons[cpuMonActive].raiseStat("SPEED");
				System.out.println(cpuMons[cpuMonActive].name+"'s SPEED rose greatly!");
			break;
			case "buffdef":
				cpuMons[cpuMonActive].raiseStat("DEF");
				System.out.println(cpuMons[cpuMonActive].name+"'s DEF rose!");
			break;
			case "buffdef2":
				cpuMons[cpuMonActive].raiseStat("DEF");
				cpuMons[cpuMonActive].raiseStat("DEF");
				System.out.println(cpuMons[cpuMonActive].name+"'s DEF rose greatly!");
			break;
			case "debuffdef":
				playerMons[playerMonActive].decreaseStat("DEF");
				System.out.println(playerMons[playerMonActive].name+"'s DEF fell!");
			break;
			case "debuffdef2":
				playerMons[playerMonActive].decreaseStat("DEF");
				playerMons[playerMonActive].decreaseStat("DEF");
				System.out.println(playerMons[playerMonActive].name+"'s DEF fell greatly!");
			break;
			case "debuffspeed2":
				playerMons[playerMonActive].decreaseStat("SPEED");
				playerMons[playerMonActive].decreaseStat("SPEED");
				System.out.println(playerMons[playerMonActive].name+"'s SPEED fell greatly!");
			break;
			case "debuffatk":
				playerMons[playerMonActive].decreaseStat("ATK");
				System.out.println(playerMons[playerMonActive].name+"'s ATK fell!");
			break;
			case "debuffatk2":
				playerMons[playerMonActive].decreaseStat("ATK");
				playerMons[playerMonActive].decreaseStat("ATK");
				System.out.println(playerMons[playerMonActive].name+"'s ATK fell greatly!");
			break;
			case "healhalf":
				cpuMons[cpuMonActive].healSelf("half");
				System.out.println(cpuMons[cpuMonActive].name+" recovered health!");
			break;
			case "poison":
				playerMons[playerMonActive].isPoisoned=true;
				System.out.println(playerMons[playerMonActive].name+" is badly poisoned!");
				plyCanFreeFromAilment[1]=false;
			break;
			case "burn":
				playerMons[playerMonActive].isBurning=true;
				System.out.println(playerMons[playerMonActive].name+" is on fire!");
				plyCanFreeFromAilment[0]=false;
			break;
			case "paralyze":
				playerMons[playerMonActive].isParalized=true;
				System.out.println(playerMons[playerMonActive].name+" is paralized! it may not move!");
				plyCanFreeFromAilment[2]=false;
			break;
			case "hot":
				cpuMons[cpuMonActive].healingOverTime=true;
				System.out.println(cpuMons[cpuMonActive].name+" will recover HP over time!");
			break;
			case "lr":
				cpuMons[cpuMonActive].isBurning=true;
				System.out.println(cpuMons[cpuMonActive].name+" is in deep trouble!!");
				if(((cpuMons[cpuMonActive].currentHP*2)/1.65) > cpuMons[cpuMonActive].baseHP){
					cpuMons[cpuMonActive].aukBurning();
					cpuMons[cpuMonActive].aukPoisoned();
				}
				int lostHP=cpuMons[cpuMonActive].baseHP - cpuMons[cpuMonActive].currentHP;
				cpuMons[cpuMonActive].currentATK+=lostHP;
				wair(s,1);
				System.out.println(cpuMons[cpuMonActive].name+" gained "+lostHP+" ATK!");
			break;
		}
	}

	private static String statusMoveHandler(String move){
		String ret="";
		switch(move){
			case "Calm Mind": ret="buffatk&def"; break;
			case "Coil": ret="buffatk&def"; break;
			case "Sword Dance": ret="buffatk2"; break;
			case "Work Up": ret="buffatk2"; break;
			case "Charge": ret="buffatk2"; break;
			case "Hone Claws" : ret="buffatk"; break;
			case "Roar": ret="debuffdef"; break;
			case "Fake Tears": ret="debuffdef2"; break;
			case "Dragon Dance": ret="buffatk&speed"; break;
			case "Growl": ret="debuffatk"; break;
			case "Charm": ret="debuffatk2"; break;
			case "Metal Sound": ret="debuffatk2"; break;
			case "Agility": ret="buffspeed2"; break;
			case "Scary Face": ret="debuffspeed2"; break;
			case "Bulk Up": ret="buffatk&def"; break;
			case "Iron Defense": ret="buffdef"; break;
			case "Amnesia": ret="buffdef2"; break;
			case "Defend Order": ret="buffdef2"; break;
			case "Acid Armor": ret="buffdef2"; break;
			case "Toxic": ret="poison"; break;
			case "Poison Powder": ret="poison"; break;
			case "Will-O-Wisp": ret="burn"; break;
			case "Heal Pulse": ret="healhalf"; break;
			case "Roost": ret="healhalf"; break;
			case "Extreme Speed": ret="buffspeed2"; break;
			case "Impulse": ret="debuffatk"; break;
			case "Aqua Ring":ret="hot"; break;
			case "Jungle Healing":ret="healhalf"; break;
			case "Thunder Wave": ret="paralyze"; break;
			case "Last Resort": ret="lr"; break;
			case "Shift Gear": ret="buffatk&speed"; break;
			case "String Shot": ret="debuffspeed2"; break;
			case "Lunar Plumage": ret="hot"; break;
			case "Salt Cure": ret="hot"; break;
			case "Rock Polish": ret="buffspeed2"; break;
		}
		return ret;
	}

	private static void specialMoveHandlerPlayerToCPU(int moveSelec, int getSmackedBich)throws IOException, InterruptedException{
		switch(playerMons[playerMonActive].isSpecialMove(moveSelec)){
			case "lifedrain":
				int healfor=getSmackedBich/2;
				if((healfor+playerMons[playerMonActive].currentHP)>playerMons[playerMonActive].baseHP){
					healfor=playerMons[playerMonActive].currentHP-playerMons[playerMonActive].baseHP;
					//bandaid patch
					if(healfor<0){
						healfor=playerMons[playerMonActive].baseHP-playerMons[playerMonActive].currentHP;
					}
				}
				playerMons[playerMonActive].currentHP+=healfor;//heal half of dmg dealt
				
				System.out.println(playerMons[playerMonActive].name+" healed for "+healfor+" points");
				wair(s,1);
			break;
			case "rngBurn":
				if(rng.nextInt(10)>7){
					cpuMons[cpuMonActive].isBurning=true;
					System.out.println(cpuMons[cpuMonActive].name+" is on fire!");
					cpuCanFreeFromAilment[0]=false;
					wair(s,1);
				}
			break;
			case "rngPoison":
				if(rng.nextInt(10)>7){
					cpuMons[cpuMonActive].isPoisoned=true;
					System.out.println(cpuMons[cpuMonActive].name+" is badly poisoned!");
					cpuCanFreeFromAilment[1]=false;
					wair(s,1);
				}
			break;
			case "rngParalysis":
				if(rng.nextInt(10)>7){
					cpuMons[cpuMonActive].isParalized=true;
					System.out.println(cpuMons[cpuMonActive].name+" is paralyzed! it may not move!");
					cpuCanFreeFromAilment[2]=false;
					wair(s,1);
				}
			break;
			case "paralyze":
				cpuMons[cpuMonActive].isParalized=true;
				System.out.println(cpuMons[cpuMonActive].name+" is paralyzed! it may not move!");
				cpuCanFreeFromAilment[2]=false;
				wair(s,1);
			break;
			case "rngDebuffSpeed":
				if(rng.nextInt(10)>5){
					cpuMons[cpuMonActive].decreaseStat("SPEED");
					System.out.println(cpuMons[cpuMonActive].name+"'s SPEED fell!");
					wair(s,1);
				}
			break;
			case "rngDebuffDef":
				if(rng.nextInt(10)>7){
					cpuMons[cpuMonActive].decreaseStat("DEF");
					System.out.println(cpuMons[cpuMonActive].name+"'s DEF fell!");
					wair(s,1);
				}
			break;
			case "rngDebuffAtk":
				if(rng.nextInt(10)>7){
					cpuMons[cpuMonActive].decreaseStat("ATK");
					System.out.println(cpuMons[cpuMonActive].name+"'s ATK fell!");
					wair(s,1);
				}
			break;
			case "debuffatk":
				cpuMons[cpuMonActive].decreaseStat("ATK");
				System.out.println(cpuMons[cpuMonActive].name+"'s ATK fell!");
				wair(s,1);
			break;
			case "buffspeed":
				playerMons[playerMonActive].raiseStat("SPEED");
				System.out.println(playerMons[playerMonActive].name+"'s SPEED rose!");
				wair(s,1);
			break;
			case "doublehit":
				if(!doublehitPlayer){
					doublehitPlayer=true;
					plyerTurn();
				}else{
					doublehitPlayer=false;
				}
			break;
			case "overclock"://debuff self atk after using
				playerMons[playerMonActive].decreaseStat("ATK");
				playerMons[playerMonActive].decreaseStat("ATK");
				System.out.println(playerMons[playerMonActive].name+"'s ATK fell greatly!");
				wair(s,1);
			break;
			case "recoil":
				playerMons[playerMonActive].currentHP-=(getSmackedBich/3);
				if(playerMons[playerMonActive].currentHP<0){
					playerMons[playerMonActive].currentHP=0;
				}
				clear();
				printBattleHUDThing();
				System.out.println(playerMons[playerMonActive].name+" hurt itself in recoil!");
				wair(s,1);
			break;
			case "adversity":
				if(playerMons[playerMonActive].moveset[0][moveSelec].equals("Ascension")){
					playerMons[playerMonActive].healingOverTime=true;
				}
				int lostHP=playerMons[playerMonActive].baseHP - playerMons[playerMonActive].currentHP;
				playerMons[playerMonActive].currentATK+=lostHP/20;
			break;
			case "powerboost":
				playerMons[playerMonActive].decreaseStat("SPEED");
				System.out.println(playerMons[playerMonActive].name+"'s SPEED fell!");
				wair(s,1);
			break;
			case "kamikaze":
				playerMons[playerMonActive].currentHP=0;
				System.out.println(playerMons[playerMonActive].name+" Exploded!!!");
				wair(s,1);
			break;
			case "debuffIfBoosted":
				Pokemon enemiMon = cpuMons[cpuMonActive];
				if(enemiMon.currentATK>enemiMon.baseATK || enemiMon.currentDEF>enemiMon.baseDEF || enemiMon.currentSPEED>enemiMon.baseSPEED){
					int todebuff=rng.nextInt(3);
					if(todebuff==0){
						cpuMons[cpuMonActive].decreaseStat("ATK");
						System.out.println(cpuMons[cpuMonActive].name+"'s ATK fell!");
						wair(s,1);
					}if(todebuff==1){
						cpuMons[cpuMonActive].decreaseStat("DEF");
						System.out.println(cpuMons[cpuMonActive].name+"'s DEF fell!");
						wair(s,1);
					}else{
						cpuMons[cpuMonActive].decreaseStat("SPEED");
						System.out.println(cpuMons[cpuMonActive].name+"'s SPEED fell!");
						wair(s,1);
					}
				}
			break;
			case "rngBuffDef":
				if(rng.nextInt(2)>0){
					playerMons[playerMonActive].raiseStat("DEF");
					System.out.println(playerMons[playerMonActive].name+"'s DEF rose!");
					wair(s,1);
				}
			break;
			case "osmash":
				playerMons[playerMonActive].raiseStat("ATK");
				playerMons[playerMonActive].raiseStat("ATK");
				System.out.println(playerMons[playerMonActive].name+"'s ATK rose greatly!");
				wair(s,1);
			break;
			case "brokenCardMove":
				for(int i=0;i<playerMons.length;i++){
					playerMons[i].baseATK+=25;
					playerMons[i].currentATK+=25;
				}
				System.out.println("Your entire team recieved a boost!!");
				wair(s,1);
			break;
		}
	}

	private static void specialMoveHandlerCPUToPlayer(int moveSelec, int getSmackedBich)throws IOException, InterruptedException{
		switch(cpuMons[cpuMonActive].isSpecialMove(moveSelec)){
			case "lifedrain":
				int healfor=getSmackedBich/2;
				if((healfor+cpuMons[cpuMonActive].currentHP)>cpuMons[cpuMonActive].baseHP){
					healfor=cpuMons[cpuMonActive].currentHP-cpuMons[cpuMonActive].baseHP;
					//bandaid patch lol
					if(healfor<0){
						healfor=cpuMons[cpuMonActive].baseHP-cpuMons[cpuMonActive].currentHP;
					}
				}
				cpuMons[cpuMonActive].currentHP+=healfor;//heal half of dmg dealt
				
				System.out.println(cpuMons[cpuMonActive].name+" healed for "+healfor+" points");
				wair(s,1);
			break;
			case "rngBurn":
				if(rng.nextInt(10)>7){
					playerMons[playerMonActive].isBurning=true;
					System.out.println(playerMons[playerMonActive].name+" is on fire!");
					plyCanFreeFromAilment[0]=false;
					wair(s,1);
				}
			break;
			case "rngPoison":
				if(rng.nextInt(10)>7){
					playerMons[playerMonActive].isPoisoned=true;
					System.out.println(playerMons[playerMonActive].name+" is badly poisoned!");
					plyCanFreeFromAilment[1]=false;
					wair(s,1);
				}
			break;
			case "rngParalysis":
				if(rng.nextInt(10)>7){
					playerMons[playerMonActive].isParalized=true;
					System.out.println(playerMons[playerMonActive].name+" is paralyzed! it may not move!");
					plyCanFreeFromAilment[2]=false;
					wair(s,1);
				}
			break;
			case "paralyze":
				playerMons[playerMonActive].isParalized=true;
				System.out.println(playerMons[playerMonActive].name+" is paralyzed! it may not move!");
				plyCanFreeFromAilment[2]=false;
				wair(s,1);
			break;
			case "rngDebuffSpeed":
				if(rng.nextInt(10)>5){
					playerMons[playerMonActive].decreaseStat("SPEED");
					System.out.println(playerMons[playerMonActive].name+"'s SPEED fell!");
					wair(s,1);
				}
			break;
			case "rngDebuffDef":
				if(rng.nextInt(10)>7){
					playerMons[playerMonActive].decreaseStat("DEF");
					System.out.println(playerMons[playerMonActive].name+"'s DEF fell!");
					wair(s,1);
				}
			break;
			case "rngDebuffAtk":
				if(rng.nextInt(10)>7){
					playerMons[playerMonActive].decreaseStat("ATK");
					System.out.println(playerMons[playerMonActive].name+"'s ATK fell!");
					wair(s,1);
				}
			break;
			case "debuffatk":
				playerMons[playerMonActive].decreaseStat("ATK");
				System.out.println(playerMons[playerMonActive].name+"'s ATK fell!");
				wair(s,1);
			break;
			case "buffspeed":
				cpuMons[cpuMonActive].raiseStat("SPEED");
				System.out.println(cpuMons[cpuMonActive].name+"'s SPEED rose!");
				wair(s,1);
			break;
			case "doublehit":
				if(!doublehitCpu){
					doublehitCpu=true;
					cpuTurn();
				}else{
					doublehitCpu=false;
				}
			break;
			case "overclock"://debuff self atk after using
				cpuMons[cpuMonActive].decreaseStat("ATK");
				cpuMons[cpuMonActive].decreaseStat("ATK");
				System.out.println(cpuMons[cpuMonActive].name+"'s ATK fell greatly!");
				wair(s,1);
			break;
			case "recoil":
				cpuMons[cpuMonActive].currentHP-=(getSmackedBich/3);
				if(cpuMons[cpuMonActive].currentHP<0){
					cpuMons[cpuMonActive].currentHP=0;
				}
				clear();
				printBattleHUDThing();
				System.out.println(cpuMons[cpuMonActive].name+" hurt itself in recoil!");
				wair(s,1);
			break;
			case "adversity":
				if(cpuMons[cpuMonActive].moveset[0][cpuMoveSelec].equals("Ascension")){
					cpuMons[cpuMonActive].healingOverTime=true;
				}
				int lostHP=cpuMons[cpuMonActive].baseHP - cpuMons[cpuMonActive].currentHP;
				cpuMons[cpuMonActive].currentATK+=lostHP/20;
			break;
			case "powerboost":
				cpuMons[cpuMonActive].decreaseStat("SPEED");
				System.out.println(cpuMons[cpuMonActive].name+"'s SPEED fell!");
				wair(s,1);
			break;
			case "kamikaze":
				cpuMons[cpuMonActive].currentHP=0;
				System.out.println(cpuMons[cpuMonActive].name+" Exploded!!!");
				wair(s,1);
			break;
			case "debuffIfBoosted":
				Pokemon enemiMon = playerMons[playerMonActive];
				if(enemiMon.currentATK>enemiMon.baseATK || enemiMon.currentDEF>enemiMon.baseDEF || enemiMon.currentSPEED>enemiMon.baseSPEED){
					int todebuff=rng.nextInt(3);
					if(todebuff==0){
						playerMons[playerMonActive].decreaseStat("ATK");
						System.out.println(playerMons[playerMonActive].name+"'s ATK fell!");
						wair(s,1);
					}if(todebuff==1){
						playerMons[playerMonActive].decreaseStat("DEF");
						System.out.println(playerMons[playerMonActive].name+"'s DEF fell!");
						wair(s,1);
					}else{
						playerMons[playerMonActive].decreaseStat("SPEED");
						System.out.println(playerMons[playerMonActive].name+"'s SPEED fell!");
						wair(s,1);
					}
				}
			break;
			case "rngBuffDef":
				if(rng.nextInt(2)>0){
					cpuMons[cpuMonActive].raiseStat("DEF");
					System.out.println(cpuMons[cpuMonActive].name+"'s DEF rose!");
					wair(s,1);
				}
			break;
			case "osmash":
				cpuMons[cpuMonActive].raiseStat("ATK");
				cpuMons[cpuMonActive].raiseStat("ATK");
				System.out.println(cpuMons[cpuMonActive].name+"'s ATK rose greatly!");
				wair(s,1);
			break;
			case "brokenCardMove":
				for(int i=0;i<cpuMons.length;i++){
					cpuMons[i].baseATK+=25;
					cpuMons[i].currentATK+=25;
				}
				System.out.println(cpuName+"'s entire team recieved a boost!!");
				wair(s,1);
			break;
		}
	}

	private static void statusAilmentsHandler()throws IOException, InterruptedException{
		//this is for poisoned and burning statuses
		//and healing over time!
		// array : 0=burn, 1=poison, 2=paralysis
		// prevent the ailments from getting yeeted on the same turn
		boolean playerFirst=whoGoesFirst();

		if(playerFirst){
			statusAilmentsPlayer();
			statusAilmentsCPU();
		}else{
			statusAilmentsCPU();
			statusAilmentsPlayer();
		}
	}

	private static void statusAilmentsPlayer()throws IOException, InterruptedException{
		//------playerer
		if(playerMons[playerMonActive].currentHP!=0){
			if(playerMons[playerMonActive].healingOverTime && playerMons[playerMonActive].currentHP != playerMons[playerMonActive].baseHP){
				clear();
				printBattleHUDThing();
				System.out.println(playerMons[playerMonActive].name+" recovered health!");
				wair(s,1);
				clear();
				playerMons[playerMonActive].healOverTime();
				printBattleHUDThing();
				System.out.println(playerMons[playerMonActive].name+" recovered health!");
				wair(s,1);
			}
			if(playerMons[playerMonActive].isBurning){
				clear();
				printBattleHUDThing();
				System.out.println(playerMons[playerMonActive].name+" is burning up!");
				wair(s,1);
				clear();
				playerMons[playerMonActive].aukBurning();
				printBattleHUDThing();
				System.out.println(playerMons[playerMonActive].name+" is burning up!");
				wair(s,1);
			}if(playerMons[playerMonActive].isPoisoned){
				clear();
				printBattleHUDThing();
				System.out.println(playerMons[playerMonActive].name+" is hurt by poison!");
				wair(s,1);
				clear();
				playerMons[playerMonActive].aukPoisoned();
				printBattleHUDThing();
				System.out.println(playerMons[playerMonActive].name+" is hurt by poison!");
				wair(s,1);
			}
		}else{
			playerMons[playerMonActive].resetStats();
		}
		// try to free from status ailment
		if((playerMons[playerMonActive].isBurning && !playerMons[playerMonActive].permaBurn) && plyCanFreeFromAilment[0]){
			if(rng.nextInt(10)>6){
				playerMons[playerMonActive].isBurning=false;
				System.out.println(playerMons[playerMonActive].name+" freed from burn!");
				wair(s,2);
			}
		}if(playerMons[playerMonActive].isPoisoned && plyCanFreeFromAilment[1]){
			if(rng.nextInt(10)>6){
				playerMons[playerMonActive].isPoisoned=false;
				System.out.println(playerMons[playerMonActive].name+" cured itself from poison!");
				wair(s,2);
			}
		}if(playerMons[playerMonActive].isParalized && plyCanFreeFromAilment[2]){
			if(rng.nextInt(10)>5){
				playerMons[playerMonActive].isParalized=false;
				System.out.println(playerMons[playerMonActive].name+" freed from paralysis!");
				wair(s,2);
			}
		}
	}

	private static void statusAilmentsCPU()throws IOException, InterruptedException{
		//--------cpu
		if(cpuMons[cpuMonActive].currentHP!=0){
			if(cpuMons[cpuMonActive].healingOverTime && cpuMons[cpuMonActive].currentHP!=cpuMons[cpuMonActive].baseHP){
				clear();
				printBattleHUDThing();
				System.out.println(cpuMons[cpuMonActive].name+" recovered health!");
				wair(s,1);
				clear();
				cpuMons[cpuMonActive].healOverTime();
				printBattleHUDThing();
				System.out.println(cpuMons[cpuMonActive].name+" recovered health!");
				wair(s,1);
			}
			if(cpuMons[cpuMonActive].isBurning){
				clear();
				printBattleHUDThing();
				System.out.println(cpuMons[cpuMonActive].name+" is burning up!");
				wair(s,1);
				clear();
				cpuMons[cpuMonActive].aukBurning();
				printBattleHUDThing();
				System.out.println(cpuMons[cpuMonActive].name+" is burning up!");
				wair(s,1);
			}if(cpuMons[cpuMonActive].isPoisoned){
				clear();
				printBattleHUDThing();
				System.out.println(cpuMons[cpuMonActive].name+" is hurt by poison!");
				wair(s,1);
				clear();
				cpuMons[cpuMonActive].aukPoisoned();
				printBattleHUDThing();
				System.out.println(cpuMons[cpuMonActive].name+" is hurt by poison!");
				wair(s,1);
			}
		}else{
			cpuMons[cpuMonActive].resetStats();
		}

		// try to free from status ailment
		if((cpuMons[cpuMonActive].isBurning && !cpuMons[cpuMonActive].permaBurn) && cpuCanFreeFromAilment[0]){
			if(rng.nextInt(10)>7){
				cpuMons[cpuMonActive].isBurning=false;
				System.out.println(cpuMons[cpuMonActive].name+" freed from burn!");
				wair(s,2);
			}
		}if(cpuMons[cpuMonActive].isPoisoned && cpuCanFreeFromAilment[1]){
			if(rng.nextInt(10)>7){
				cpuMons[cpuMonActive].isPoisoned=false;
				System.out.println(cpuMons[cpuMonActive].name+" cured itself from poison!");
				wair(s,2);
			}
		}if(cpuMons[cpuMonActive].isParalized && cpuCanFreeFromAilment[2]){
			if(rng.nextInt(10)>6){
				cpuMons[cpuMonActive].isParalized=false;
				System.out.println(cpuMons[cpuMonActive].name+" freed from paralysis!");
				wair(s,2);
			}
		}
	}

	private static void playerMegaEvolveSequence()throws IOException, InterruptedException{
		for(int i=0;i<playerMons[playerMonActive].items.length;i++){
			if(playerMons[playerMonActive].items[i].equals("Mega Stone")){
				playerMons[playerMonActive].disableAllItems();
				break;
			}
		}
		String prevName=playerMons[playerMonActive].name;
		clear();
		printBattleHUDThing();
		if(prevName.equals("Eevee")){
			System.out.println(playerMons[playerMonActive].name+" is evolving!");
			wair(s,2);
			playerMons[playerMonActive].megaEvolve();
			plyCanMegaEvolve=false;
			clear();
			printBattleHUDThing();
			System.out.println(prevName+" is evolving!");
			System.out.println(prevName+" has evolved into "+playerMons[playerMonActive].name+"!");
			wair(s,2);
		}else{
			System.out.println(playerMons[playerMonActive].name+" is Mega-Evolving!");
			wair(s,2);
			playerMons[playerMonActive].megaEvolve();
			plyCanMegaEvolve=false;
			clear();
			printBattleHUDThing();
			System.out.println(prevName+" is Mega-Evolving!");
			System.out.println(prevName+" has Mega-Evolved into "+playerMons[playerMonActive].name+"!");
			wair(s,2);
		}
		
	}

	private static void cpuMegaEvolveSequence()throws IOException, InterruptedException{
		cpuMons[cpuMonActive].disableAllItems();

		String prevName=cpuMons[cpuMonActive].name;
		clear();
		printBattleHUDThing();
		if(prevName.equals("Eevee")){
			System.out.println(cpuMons[cpuMonActive].name+" is evolving!");
			wair(s,2);
			cpuMons[cpuMonActive].megaEvolve();
			cpuCanMegaEvolve=false;
			clear();
			printBattleHUDThing();
			System.out.println(prevName+" is evolving!");
			System.out.println(prevName+" has evolved into "+cpuMons[cpuMonActive].name+"!");
			wair(s,2);
		}else{
			System.out.println(cpuMons[cpuMonActive].name+" is Mega-Evolving!");
			wair(s,2);
			cpuMons[cpuMonActive].megaEvolve();
			cpuCanMegaEvolve=false;
			clear();
			printBattleHUDThing();
			System.out.println(prevName+" is Mega-Evolving!");
			System.out.println(prevName+" has Mega-Evolved into "+cpuMons[cpuMonActive].name+"!");
			wair(s,2);
		}
	}
	
	private static boolean battleItemsHandler(int selecItem)throws IOException, InterruptedException{
		String itemToUse = playerMons[playerMonActive].items[selecItem];
		switch(itemToUse){
			case "Potion":
				clear();
				printBattleHUDThing();
				System.out.println("You used a Potion!");
				wair(s,1);
				playerMons[playerMonActive].healSelf("half");
				clear();
				printBattleHUDThing();
				System.out.println("You used a Potion!");
				System.out.println(playerMons[playerMonActive].name+" recovered health!");
				wair(s,2);
			break;
			case "X-Attack":
				clear();
				printBattleHUDThing();
				System.out.println("You used X-Attack!");
				wair(s,1);
				playerMons[playerMonActive].raiseStat("ATK");
				playerMons[playerMonActive].raiseStat("ATK");
				System.out.println(playerMons[playerMonActive].name+"'s ATK rose greatly!");
				wair(s,2);
			break;
			case "X-Defense":
				clear();
				printBattleHUDThing();
				System.out.println("You used X-Defense!");
				wair(s,1);
				playerMons[playerMonActive].raiseStat("DEF");
				playerMons[playerMonActive].raiseStat("DEF");
				System.out.println(playerMons[playerMonActive].name+"'s DEF rose greatly!");
				wair(s,2);
			break;
			case "X-Speed":
				clear();
				printBattleHUDThing();
				System.out.println("You used X-Speed!");
				wair(s,1);
				playerMons[playerMonActive].raiseStat("SPEED");
				playerMons[playerMonActive].raiseStat("SPEED");
				System.out.println(playerMons[playerMonActive].name+"'s SPEED rose greatly!");
				wair(s,2);
			break;
			case "Dash Earring":
				clear();
				printBattleHUDThing();
				System.out.println("You gave "+playerMons[playerMonActive].name+" a Dash Earring!");
				wair(s,1);
				playerMons[playerMonActive].baseATK/=2;
				playerMons[playerMonActive].currentATK-=playerMons[playerMonActive].baseATK;
				playerMons[playerMonActive].numberOfHits+=1; //:3
				System.out.println(playerMons[playerMonActive].name+ " gained another hit to its attacks!");
				wair(s,2);
			break;
			case "Strike Earrings":
				clear();
				printBattleHUDThing();
				System.out.println("You gave "+playerMons[playerMonActive].name+" Strike Earrings!");
				wair(s,1);
				playerMons[playerMonActive].isBurning=true;
				playerMons[playerMonActive].strike=true;
				playerMons[playerMonActive].permaBurn=true;
				playerMons[playerMonActive].decreaseStat("DEF");
				playerMons[playerMonActive].decreaseStat("SPEED");
				playerMons[playerMonActive].decreaseStat("SPEED");
				playerMons[playerMonActive].baseSPEED/=2;
				System.out.println(playerMons[playerMonActive].name+"'s DEF fell!");
				wair(s,1);
				System.out.println(playerMons[playerMonActive].name+"'s SPEED fell greatly!");
				wair(s,1);
				System.out.println(playerMons[playerMonActive].name+ " will only deal "+Clr.RED_B+"Critical hits!"+Clr.R);
				wair(s,2);
			break;
			case "Energy Drink":
				clear();
				printBattleHUDThing();
				System.out.println("You gave "+playerMons[playerMonActive].name+" an Energy Drink!");
				wair(s,1);
				playerMons[playerMonActive].energyDrink=true;
				playerMons[playerMonActive].decreaseStat("ATK");
				playerMons[playerMonActive].baseATK/=2;
				String[]res=playerMons[playerMonActive].resists;
				String[]wek=playerMons[playerMonActive].weakTo;
				String[]newWeakto=new String[(res.length)+(wek.length)];
				for(int i=0;i<wek.length;i++){
					newWeakto[i]=wek[i];
				}
				int j=0;
				for(int i=wek.length;i<newWeakto.length;i++){
					newWeakto[i]=res[j];
					j++;
				}
				playerMons[playerMonActive].weakTo=newWeakto;
				playerMons[playerMonActive].resists= new String[]{"Nothing!"};
				System.out.println(playerMons[playerMonActive].name+"'s ATK fell!");
				wair(s,1);
				System.out.println(playerMons[playerMonActive].name+"'s resistances were removed!");
				wair(s,1);
				System.out.println(playerMons[playerMonActive].name+" can use 2 moves per turn!");
				wair(s,2);
			break;
			case "Mega Stone":
				if(plyCanMegaEvolve){
					if(playerMons[playerMonActive].name.equals("Eevee")){
						System.out.println(playerMons[playerMonActive].name+" will Evolve during its next turn");
					}else{
						System.out.println(playerMons[playerMonActive].name+" will Mega-Evolve during its next turn");
					}
				}else{
					System.out.println("You can only Mega-Evolve a Pokemon once per battle");
				}
				wair(s,2);
				return false;
		}
		if(itemToUse.equals("")==false || itemToUse!=null){
			playerMons[playerMonActive].disableAllItems(); //disables items
		}
		return true;
	}

	private static void cpuBattleItemsHandler(int selecItem)throws IOException, InterruptedException{
		int itemToUse = selecItem;
		cpuMons[cpuMonActive].disableAllItems();
		switch(itemToUse){
			case 660:
				clear();
				printBattleHUDThing();
				System.out.println(cpuName+" used a Potion!");
				wair(s,1);
				cpuMons[cpuMonActive].healSelf("half");
				clear();
				printBattleHUDThing();
				System.out.println(cpuName+" used a Potion!");
				System.out.println(cpuMons[cpuMonActive].name+" recovered health!");
				wair(s,2);
			break;
			case 663:
				clear();
				printBattleHUDThing();
				System.out.println(cpuName+" used X-Attack!");
				wair(s,1);
				cpuMons[cpuMonActive].raiseStat("ATK");
				cpuMons[cpuMonActive].raiseStat("ATK");
				System.out.println(cpuMons[cpuMonActive].name+"'s ATK rose greatly!");
				wair(s,2);
			break;
			case 662:
				clear();
				printBattleHUDThing();
				System.out.println(cpuName+" used X-Defense!");
				wair(s,1);
				cpuMons[cpuMonActive].raiseStat("DEF");
				cpuMons[cpuMonActive].raiseStat("DEF");
				System.out.println(cpuMons[cpuMonActive].name+"'s DEF rose greatly!");
				wair(s,2);
			break;
			case 661:
				clear();
				printBattleHUDThing();
				System.out.println(cpuName+" used X-Speed!");
				wair(s,1);
				cpuMons[cpuMonActive].raiseStat("SPEED");
				cpuMons[cpuMonActive].raiseStat("SPEED");
				System.out.println(cpuMons[cpuMonActive].name+"'s SPEED rose greatly!");
				wair(s,2);
			break;
			case 669:
				clear();
				printBattleHUDThing();
				System.out.println(cpuName+" gave "+cpuMons[cpuMonActive].name+" a Dash Earring!");
				wair(s,1);
				cpuMons[cpuMonActive].baseATK/=2;
				cpuMons[cpuMonActive].currentATK-=cpuMons[cpuMonActive].baseATK;
				cpuMons[cpuMonActive].numberOfHits+=1; //:3
				System.out.println(cpuMons[cpuMonActive].name+ " gained another hit to its attacks!");
				wair(s,2);
			break;
			case 670:
				clear();
				printBattleHUDThing();
				System.out.println(cpuName+" gave "+cpuMons[cpuMonActive].name+" Strike Earrings!");
				wair(s,1);
				cpuMons[cpuMonActive].isBurning=true;
				cpuMons[cpuMonActive].strike=true;
				cpuMons[cpuMonActive].permaBurn=true;
				cpuMons[cpuMonActive].decreaseStat("DEF");
				cpuMons[cpuMonActive].decreaseStat("SPEED");
				cpuMons[cpuMonActive].decreaseStat("SPEED");
				cpuMons[cpuMonActive].baseSPEED/=2;
				System.out.println(cpuMons[cpuMonActive].name+"'s DEF fell!");
				wair(s,1);
				System.out.println(cpuMons[cpuMonActive].name+"'s SPEED fell greatly!");
				wair(s,1);
				System.out.println(cpuMons[cpuMonActive].name+ " will only deal "+Clr.RED_B+"Critical hits!"+Clr.R);
				wair(s,2);
			break;
		}
	}

	private static void cpuTeamManager(int lastpage)throws IOException, InterruptedException{
		int op=0;
		do{
			clear();
			System.out.println(Clr.WHITE_B+"--[CPU TEAM MANAGER]--"+Clr.R);
			System.out.println("");
			System.out.println("Current name for the CPU opponent: "+cpuName);
			System.out.println("\n Select an option:");
			System.out.println("[1]: Change CPU's name");
			System.out.println("[2]: Manage CPU Team");
			System.out.println("[3]: Go back \n");

			try{
				op=tcl.nextInt();
			}catch(Exception e){
				op=0;
				tcl.nextLine();
			}if(op==3){
				tcl.nextLine();
				return;
			}

		}while(op!=1 && op!=2);
		tcl.nextLine();
		if(op==1){
			String epicname="";
			do{
				clear();
				System.out.println(Clr.WHITE_B+"--[CPU TEAM MANAGER]--"+Clr.R);
				System.out.println("");
				System.out.println("Enter a new name for the CPU opponent: ");
				System.out.print("-> ");
				epicname=tcl.nextLine();

				if(epicname.length()>10 || epicname.equals("")){
					System.out.println("it can't be longer than 10 characters!");
					wair(s,2);
					epicname="";
				}

			}while(epicname.length()>10 || epicname.equals(""));

			cpuName=epicname;
			System.out.println("Saved successfully!!!");
			wair(s,2);
		}else{
			op=0;
			do{
				clear();
				System.out.println(Clr.WHITE_B+"--[CPU TEAM MANAGER]--"+Clr.R);
				System.out.println("");
				System.out.println("Team size: "+cpuMons.length);
				System.out.print(cpuName+ "'s team: ");
				for(int i=0;i<cpuMons.length;i++){
					if(cpuMons[i]==null){
						System.out.print("[...] ");
					}else{
						System.out.print("["+cpuMons[i].name+"] ");
					}
					if(i==2 && cpuMons.length>3){
						System.out.println("");
						System.out.print("     ");
					}
				}
				System.out.println("\n");
				System.out.println("Select an option:");
				System.out.println("[1]: Reset team");
				System.out.println("[2]: Assign a Mon to team");
				System.out.println("[3]: Exit");
				
				try{
					op=tcl.nextInt();
					
					if(op==1){
						cpuMons=new Pokemon[cpuMons.length];
						System.out.println("CPU team reset successfully");
						wair(s,2);
					}if(op==3){
						tcl.nextLine();
						return;
					}if(op==2){
						tcl.nextLine();
						cpuTeamManagerAssignPokemon(lastpage);
						return;
					}
				}catch(Exception e){
					op=0;
					tcl.nextLine();
				}
			}while(true);
		}
	}
	
	private static void cpuTeamManagerAssignPokemon(int lastpage)throws IOException, InterruptedException{
		int op=0;
		do{
			clear();
			System.out.println(Clr.WHITE_B+"--[CPU TEAM MANAGER]--"+Clr.R);
			System.out.println("");
			System.out.println("Team size: "+cpuMons.length);
			System.out.print(cpuName+ "'s team: ");
			for(int i=0;i<cpuMons.length;i++){
				if(cpuMons[i]==null){
					System.out.print("[...] ");
				}else{
					System.out.print("["+cpuMons[i].name+"] ");
				}
				if(i==2 && cpuMons.length>3){
					System.out.println("");
					System.out.print("     ");
				}
			}
			System.out.println("\n");
			System.out.println("Assign Pokemon from...");
			System.out.println("[1]: Pokemon list");
			System.out.println("[2]: Custom Pokemon");
			System.out.println("[3]: Exit");
			
			try{
				op=tcl.nextInt();
				if(op==3){
					tcl.nextLine();
					return;
				}
			}catch(Exception e){
				op=0;
				tcl.nextLine();
			}
			
			if(op==2){
				Pokemon custm=PokemonMaker3000.makeCustomMon();
				if(custm!=null){
					saveMonInCPUTeam2(custm);
				}
			}if(op==1){
				String[] namesVector=getPkmnNamesVector();
				int page=1;
				boolean correctName=false;
				boolean errBypass=false;
				do{
					String selecshon="";
					clear();
					System.out.println(Clr.WHITE_B+"--[CPU TEAM MANAGER]--"+Clr.R);
					System.out.println("Type "+Clr.MAGENTA_B+"cancel"+Clr.R+" to go back");
					System.out.print(cpuName+ "'s team: ");
					for(int i=0;i<cpuMons.length;i++){
						if(cpuMons[i]==null){
							System.out.print("[...] ");
						}else{
							System.out.print("["+cpuMons[i].name+"] ");
						}
						if(i==2 && cpuMons.length>3){
							System.out.println("");
							System.out.print("     ");
						}
					}
					System.out.println("\n");
					printPkmnNamesPage(namesVector,page,lastpage);
					
					try{
						System.out.print(">");
						selecshon=tcl.nextLine();
						
						selecshon=autoCapitalizeMonName(selecshon);
						correctName=isNameCorrect(selecshon);
						
						if(selecshon.equals("Cancel")){
							return;
						}
					}catch(Exception e){
						selecshon="";
						correctName=false;
						errBypass=false;
					}
					
					if(!correctName && !errBypass){//try page switch
						try{
							int selecInt=0;
							selecInt = Integer.parseInt(selecshon);
							if(selecInt>0 && selecInt<=lastpage){
								page=selecInt;
								correctName=false;
								errBypass=true;
								selecshon="";
							}else{
								selecshon="";
								errBypass=false;
							}
						}catch(NumberFormatException e){
							selecshon=""; //will throw invalid option
							errBypass=false;
						}
					}

					if(!correctName && !errBypass){
						System.out.println("Invalid option, please try again");
						wair(s,1);
					}
					errBypass=false;
					if(correctName){
						clear();
						printSelectedMonInfo(selecshon);
						System.out.println("");
						System.out.println("Confirm?  [1]: Yes [2]: No");
						try{
							int selecshon2 = tcl.nextInt();
							tcl.nextLine();
					
							if(selecshon2==1){
								correctName=true;
								savePokemonInCPUTeam(selecshon);
							} else{
								correctName=false;
							}
						}catch(InputMismatchException e){
							correctName=false;
						}
					}
				}while(cpuMons[cpuMons.length-1]==null);
			}
		}while(true);
	}

	private static void saveMonInCPUTeam2(Pokemon mon){ // works with custom mon :3
		for(int i=0;i<cpuMons.length;i++){
			if(cpuMons[i]==null){
				cpuMons[i]=mon;
				break;
			}
		}
	}

	//-------------PRINT METHODS-----------//
	
	private static void printPkmnNamesPage(String[] namesVector, int page, int lastPage)throws IOException{
		BufferedWriter cout = new BufferedWriter(new OutputStreamWriter(System.out));
		int coumter=0;
		int from=0, to=0;//0-35, 36-71, 72-107
		boolean toLeft=false, toRight=false;
		String arrLeft=" "; String arrRight=" ";
		switch(page){
			case 1:
				from=0;
				to=35;
			break;
			case 2:
				from=36;
				to=71;
			break;
			case 3:
				from=72;
				to=107;
			break;
			default:
				from=0;
				to=35;
			break;
		}

		if(page==1){
			toLeft=false;
			toRight=true;
		}if(page<lastPage && page>1){
			toLeft=true;
			toRight=true;
		}if(page==lastPage){
			toRight=false;
			toLeft=true;
		}

		if(toLeft){
			arrLeft="<";
		}if(toRight){
			arrRight=">";
		}

		cout.write("                 "+arrLeft+" Page "+page+" "+arrRight+"\n");
		for(int i=from; i<=to;i++){
			String space="";
			if(coumter<3){
				cout.write(" "+namesVector[i]);
				for(int j=0;j<=12-(namesVector[i].length());j++){
					space+=" ";
				}
				cout.write(space+"|");
				coumter++;
			} else{
				coumter=0;
				cout.write("\n");
				i--;
			}
		}
		cout.write("\n");
		cout.flush();
		//cout.close(); <-- DO NOT
	}

	private static void printHelpMMScreen()throws IOException, InterruptedException{
		clear();
		System.out.println(Clr.YELLOW_BB+"[Pokemon Battle Sim beta5 dev1]"+Clr.R);
		System.out.println("");
		System.out.println("Totally super cool commands for the Main Menu:");
		System.out.println("");
		System.out.println("CUSTOM: allows you to create or manage a\n customized Pokemon. it can be saved to a txt file.\n");
		System.out.println("RNG: fills empty slots in your team with randomly\n selected Pokemon. then starts the battle.\n");
		System.out.println("<Number>: view selected page of Pokemon.\n you can select any Pokemon while vieweing any page.\n");
		System.out.println("<Pokemon Name>: select a Pokemon.\n tip: you can just type the first 4 letters.\n");
		System.out.println("RESET: Deletes all Pokemon in your team. \n");
		System.out.println("CPU: Enter the CPU Manager menu. \n");
		System.out.println("6mon: Changes the Pokemon Team size to 6 Pokemon. \n");
		System.out.println("3mon: Changes the Pokemon Team size to 3 Pokemon. \n");
		System.out.println("HELP: brings up this very cool looking screen.");

		System.out.println("");
		System.out.println("Press Enter to go back");
		tcl.nextLine();
	}

	private static void printPlayerActivePkmnMoveset(boolean plyWillMegaEvolve)throws IOException{
		BufferedWriter cout = new BufferedWriter(new OutputStreamWriter(System.out));
		Pokemon mon= null;
		if(plyWillMegaEvolve && plyCanMegaEvolve){
			if(playerMons[playerMonActive].name.equals("Eevee")){
				mon = playerMons[playerMonActive];
				mon.moveset[0][0]="???";
				mon.moveset[0][1]="???";
				mon.moveset[0][2]="???";
				mon.moveset[0][3]="???";
			}else{
				mon = new Pokemon(playerMons[playerMonActive].name);
				mon.megaEvolve();//display megaevolved moves
			}
		}else{
			mon = playerMons[playerMonActive];
		}
		int coumter=0;
		for(int i=0;i<4;i++){//they always got 4 moves frfr
			if(coumter<2){
				cout.write("["+(i+1)+"] "+mon.moveset[0][i]);
				for(int j=0;j<20-(mon.moveset[0][i].length());j++){
					cout.write(" ");
				}
				coumter++;
				if(coumter<2){
					cout.write("| ");
				}
			}else{
				coumter=0;
				cout.write("\n");
				i--;
			}
		}
		cout.write("\n");
		cout.flush();
		/*Your active Pokemon:       CPU's Active Pokemon:
		 * [1] move 1             | [2] move 2
		 * [3] move 3             | [4] move 4
		 */
	}

	private static void printSelectedMonInfo(String selecshon) throws IOException, InterruptedException{
		clear();
		System.out.println("Selected Pokemon: ");
		
		Pokemon tempPkmn = new Pokemon(selecshon);
		System.out.println("Name:    "+tempPkmn.name);
		System.out.println("Type:    "+tempPkmn.type);
		System.out.println("HP:      "+tempPkmn.baseHP);
		System.out.println("Attack:  "+tempPkmn.baseATK);
		System.out.println("Defense: "+tempPkmn.baseDEF);
		System.out.println("Speed:   "+tempPkmn.baseSPEED);
		System.out.print("Weak to: ");
		
		for(int i=0;i<tempPkmn.weakTo.length;i++){
			System.out.print(tempPkmn.weakTo[i]);
			if(i!=tempPkmn.weakTo.length-1){
				System.out.print(", ");
			}
		}
		System.out.println("");
		System.out.print("Resists: ");
			
		for(int i=0;i<tempPkmn.resists.length;i++){
			System.out.print(tempPkmn.resists[i]);
			if(i!=tempPkmn.resists.length-1){
				System.out.print(", ");
			}
		}
		System.out.println("");
					
		System.out.println("Moveset: ");
		for(int i=0;i<4;i++){
			System.out.println("        "+(i+1)+":"+tempPkmn.moveset[0][i]+" ("+tempPkmn.moveset[1][i]+")");
		}
		int totalstats =tempPkmn.baseATK+tempPkmn.baseDEF+tempPkmn.baseHP+tempPkmn.baseSPEED;
		System.out.println("Total stat points: "+totalstats);
	}

	private static void printMonInfo()throws IOException, InterruptedException{
		//this just a copypaste
		clear();
		//again gotta make this cus em variables are too long frfrfr
		Pokemon tempPkmn;
		int input=0;
		float reducPerc=0;
		float critPerc=0;
		printBattleHUDThing();
		System.out.println("Which Pokemon do you want to inspect? O_o");
		System.out.println("________________________________________________");
		System.out.println("[1] Your Mon ("+playerMons[playerMonActive].name+")");
		System.out.println("[2] "+cpuName+"'s Mon ("+cpuMons[cpuMonActive].name+")");
		System.out.println("\n"+"[3] Your Pokemon moveset");
		do{
			try{
				input=tcl.nextInt();
			}catch(Exception e){
				input=69;
				tcl.nextLine();
			}
		}while(input!=1 && input!=2 && input!=3);

		if(input==3){
			tcl.nextLine();
			printMoveInfo();
			return;
		}
		clear();
		if(input==1){
			tempPkmn=playerMons[playerMonActive];
			System.out.println("Your current Pokemon:");
		}else{
			tempPkmn=cpuMons[cpuMonActive];
			System.out.println(cpuName+"'s current Pokemon:");
		}

		//shows reduction percentage
		reducPerc=(float)tempPkmn.currentDEF/400;
		reducPerc*=100;
		String rpString = reducPerc+"";
		String perc="";
		for(int i=0;i<4;i++){
			try{
				perc+=rpString.charAt(i);
			}catch(StringIndexOutOfBoundsException e){
				break;
			}
		}
		//shows crit chance
		critPerc=(float)tempPkmn.currentSPEED/806;
		critPerc*=100;
		String cpString = critPerc+"";
		String cperc="";
		for(int i=0;i<4;i++){
			try{
				cperc+=cpString.charAt(i);
			}catch(StringIndexOutOfBoundsException e){
				break;
			}
		}

		System.out.println("The Pokemon's stats are reset when switching out \n");//<-- C++ reference!??
		System.out.println("Name:    "+tempPkmn.name);
		System.out.println("Type:    "+tempPkmn.type);
		System.out.println("HP:      "+tempPkmn.currentHP+"/"+tempPkmn.baseHP);
		System.out.println("Attack:  "+tempPkmn.currentATK+"/"+tempPkmn.baseATK);
		System.out.println("Defense: "+tempPkmn.currentDEF+"/"+tempPkmn.baseDEF+" ("+perc+"% reduction)");
		System.out.println("Speed:   "+tempPkmn.currentSPEED+"/"+tempPkmn.baseSPEED+" ("+cperc+"% crit. chance)");
		System.out.print("Weak to: ");
		
		for(int i=0;i<tempPkmn.weakTo.length;i++){
			System.out.print(tempPkmn.weakTo[i]);
			if(i!=tempPkmn.weakTo.length-1){
				System.out.print(", ");
			}
		}
		System.out.println("");
	
		System.out.print("Resists: ");

		for(int i=0;i<tempPkmn.resists.length;i++){
			System.out.print(tempPkmn.resists[i]);
			if(i!=tempPkmn.resists.length-1){
				System.out.print(", ");
			}
		}
		System.out.println("");
		
		System.out.println("Moveset: ");
		for(int i=0;i<4;i++){
			System.out.println("        "+(i+1)+":"+tempPkmn.moveset[0][i]+" ("+tempPkmn.moveset[1][i]+")");
		}
		System.out.println("");
		System.out.println("Press Enter to go back");
		tcl.nextLine();
		tcl.nextLine(); //java shenanigans
	}

	private static void printMoveInfo()throws IOException, InterruptedException{
		int selec=69;
		clear();
		printBattleHUDThing();
		System.out.println("Select a move to see its info.");
		System.out.println("________________________________________________");
		printPlayerActivePkmnMoveset(false);

		do{
			try{
				selec=Integer.parseInt(tcl.nextLine());
			}catch(Exception e){
				selec=69;
			}
		}while(selec<1 || selec>4);
		selec--;

		clear();
		printBattleHUDThing();
		System.out.println("________________________________________________");
		System.out.println(playerMons[playerMonActive].moveset[0][selec]+":");
		System.out.println(playerMons[playerMonActive].moveset[1][selec]+" move \n");
		if(moveIsAnAttack(playerMons[playerMonActive].moveset[1][selec])){
			switch(playerMons[playerMonActive].isSpecialMove(selec)){
				default:
					System.out.println("Deals damage!");
				break;
				case "+priority":
					System.out.println("This move has priority, making it go first!");
				break;
				case "lifedrain":
					System.out.println("-33% ATK");
					System.out.println("Half of damage dealt -> HP recovery");
				break;
				case "rngBurn":
					System.out.println("20% chance to inflict "+Clr.RED_B+"burn"+Clr.R+" after using the move.");
				break;
				case "rngPoison":
					System.out.println("20% chance to inflict "+Clr.MAGENTA_B+"poison"+Clr.R+" after using the move.");
				break;
				case "rngParalysis":
					System.out.println("20% chance to inflict "+Clr.YELLOW_B+"paralysis"+Clr.R+" after using the move.");
				break;
				case "rngDebuffSpeed":
					System.out.println("40% chance to decrease the enemy's SPEED.");
				break;
				case "rngDebuffDef":
					System.out.println("20% chance to decrease the enemy's DEF.");
				break;
				case "rngDebuffAtk":
					System.out.println("20% chance to decrease the enemy's ATK.");
				break;
				case "buffspeed":
					System.out.println("Increases self SPEED after using the move.");
				break;
				case "doublehit":
					System.out.println("-33% ATK");
					System.out.println("This move will be used twice in a row in the same turn. \n"+"Crit chance is calculated individually.");
				break;
				case "highcritrate":
					System.out.println("Crit chance is higher for this move.");
				break;
				case "overclock":
					System.out.println("ATK x 1.8");
					System.out.println("-50% ATK after using the move.");
				break;
				case "ignoredef":
					System.out.println("-25% ATK");
					System.out.println("This move ignores the enemy Pokemon's DEF");
				break;
				case "powerboost":
					System.out.println("ATK x 1.5");
					System.out.println("Decreases self SPEED after using the move.");
				break;
				case "debuffatk":
					System.out.println("Debuffs the enemy's ATK after using this move.");
				break;
				case "defisatk":
					System.out.println("Uses the enemy Pokemon's DEF stat as ATK");
				break;
				case "recoil":
					System.out.println("ATK x 1.3");
					System.out.println("Recieve 1/3 of damage dealt as recoil damage.");
				break;
				case "rngMultihit":
					System.out.println("-66% ATK");
					System.out.println("STAB reduced by 87.5%");
					System.out.println("Adds a random number of Hits to the move");
					System.out.println("from +0 to +6)");
				break;
				case "supEffective":
					System.out.println("-33% ATK");
					System.out.println("This move will always be super effective.");
				break;
				case "plus2hit":
					System.out.println("-75% ATK");
					System.out.println("STAB reduced by 50%");
					System.out.println("+2 Hits");
				break;
				case "adversity":
					System.out.println("50% of missing HP -> ATK for this move");
					if(playerMons[playerMonActive].moveset[0][selec].equals("X")){
						System.out.println("-33% ATK");
						System.out.println("+1 Hit");
					}if(playerMons[playerMonActive].moveset[0][selec].equals("Ascension")){
						System.out.println("Adds Healing Over Time effect after using the move.");
					}
					System.out.println("Adds 5% of missing HP to ATK after using the move.");
				break;
				case "adversity2":
					System.out.println("-66% ATK");
					System.out.println("50% of missing HP -> ATK for this move.");
				break;
				case "groupB":
					int value=0; int value2=0;
					if(playerMons.length>3){
						value=7;}else{value=25;
					}
					System.out.println("Combines "+value+"% of all of your Pokemon's ATK that haven't fainted.");
					System.out.println("Adds +1 Hit for every alive Pokemon in your team,\n excluding the one using this move.");
				break;
				case "reverseGroupB":
					value=0; value2=0;
					if(playerMons.length>3){
						value=80;value2=20;}else{value=66;value2=33;
					}
					System.out.println("-"+value+"% ATK");
					System.out.println("Adds +"+value2+"% ATK for every alive Pokemon in the enemy team.");
				break;
				case "MegaEvolutionHater":
					System.out.println("Doubles ATK for this move if the enemy Pokemon is Mega-Evolved");
				break;
				case "avenger":
					value=0; value2=0;
					if(playerMons.length>3){
						value=25;}else{value=50;
					}
					System.out.println("-25% ATK");
					System.out.println("Adds +"+value+"% ATK for every fallen ally");
					System.out.println("If this Pokemon is the last one standing:");
					System.out.println(" +1 Hit for this move.");
				break;
				case "debuffIfBoosted":
					System.out.println("If the ATK, DEF or SPEED of the enemy Pokemon are buffed:");
					System.out.println(" Debuffs the enemy's ATK, DEF or SPEED at random.");
				break;
				case "kamikaze":
					System.out.println("Adds +50% ATK, then ATK x 4");
					System.out.println("The user faints after using this move");
				break;
				case "thundercage":
					System.out.println("Adds 1/8 of the enemy Pokemon's HP as ATK");
					System.out.println("for this move.");
				break;
				case "magnitude":
					System.out.println("-66% ATK");
					System.out.println("Multiplies ATK by a random amount (upto x7)");
				break;
				case "rngBuffDef":
					System.out.println("50% chance to buff self DEF after using the move");
				break;
				case "buffPowerIfDebuffed":
					System.out.println("If this Pokemon's ATK, DEF or SPEED are debuffed:");
					System.out.println(" +50% ATK");
					System.out.println("If not:");
					System.out.println(" -33% ATK");
				break;
				case "cuthp":
					System.out.println("This move cuts the enemy Pokemon's HP in half.");
					System.out.println("This move ignores type weaknesses");
					System.out.println("This move cannot Crit");
				break;
				case "osmash":
					System.out.println("-33% ATK");
					System.out.println("Buffs self ATK by 50% after using the move.");
				break;
				case "facade":
					System.out.println("-20% ATK");
					System.out.println("Doubles ATK if the user is Poisoned, Paralyzed");
					System.out.println("or Burning");
				break;
				case "guaranteedCrit":
					System.out.println("-20% ATK");
					System.out.println("This move always results in a Critical hit");
				break;
				case "brokenCardMove":
					System.out.println("-33% ATK");
					System.out.println("After using this move, your entire team gets");
					System.out.println("a permanent +25 base ATK bonus");
				break;
				case "scnails":
					System.out.println("Halves ATK");
					System.out.println("If the enemy Pokemon's HP is below 2/3 of");
					System.out.println("its max HP:");
					System.out.println(" ATK x 4");
				break;
			}
		}else{
			switch(statusMoveHandler(playerMons[playerMonActive].moveset[0][selec])){
				case "buffatk&def":
					System.out.println("Increases ATK and DEF by 25%");
				break;
				case "buffatk2":
					System.out.println("Increases ATK by 50%");
				break;
				case "buffatk":
					System.out.println("Increases ATK by 25%");
				break;
				case "debuffdef":
					System.out.println("Decreases the enemy's DEF by 25%");
				break;
				case "debuffdef2":
					System.out.println("Decreases the enemy's DEF by 50%");
				break;
				case "debuffatk":
					System.out.println("Decreases the enemy's ATK by 25%");
				break;
				case "debuffatk2":
					System.out.println("Decreases the enemy's ATK by 50%");
				break;
				case "buffspeed2":
					System.out.println("Increases SPEED by 50%");
				break;
				case "debuffspeed2":
					System.out.println("Decreases the enemy's DEF by 50%");
				break;
				case "buffdef":
					System.out.println("Increases DEF by 25%");
				break;
				case "buffdef2":
					System.out.println("Increases DEF by 50%");
				break;
				case "poison":
					System.out.println("Inflicts "+Clr.MAGENTA_B+"Poison"+Clr.R+" on the enemy.");
				break;
				case "burn":
					System.out.println("Inflicts "+Clr.RED_B+"Burn"+Clr.R+" on the enemy.");
				break;
				case "healhalf":
					System.out.println("Recovers half of the Pokemon's max HP");
					System.out.println("(will only recover 1/4 if the Pokemon is using an Energy Drink)");
				break;
				case "hot":
					System.out.println("The Pokemon gets Healing Over Time effect.");
				break;
				case "paralyze":
					System.out.println("Inflicts "+Clr.YELLOW_B+"Paralysis"+Clr.R+" on the enemy.");
				break;
				case "buffatk&speed":
					System.out.println("Increases ATK & SPEED by 25%");
				break;
				case "lr":
					System.out.println("Inflicts "+Clr.RED_B+"Burn"+Clr.R+" on self.");
					System.out.println("Missing HP -> +ATK");
					System.out.println("The Pokemon will lose some HP when using this");
					System.out.println("move if it's close to Full HP.");

				break;
			}
		}
		

		System.out.println("\n"+"Press Enter to go back");
		tcl.nextLine();
	}

	private static int printSelectBattleItem()throws IOException, InterruptedException{
		clear();
		printBattleHUDThing();
		System.out.println("Select an item to use");
		System.out.println("________________________________________________");
		int coumter=0;
		for(int i=0;i<playerMons[playerMonActive].items.length;i++){
			if(playerMons[playerMonActive].items[i].equals("")==false &&
			playerMons[playerMonActive].items[i]!=null){
				if(coumter<2){
					System.out.print("["+(i+1)+"] "+playerMons[playerMonActive].items[i]);
					for(int j=0;j<20-(playerMons[playerMonActive].items[i].length());j++){
						System.out.print(" ");
					}
					coumter++;
					if(coumter<2){
						System.out.print("| ");
					}
				}else{
					coumter=0;
					System.out.println("");
					i--;
				}
			}
		}
		System.out.println("");
		System.out.println("[c]: Cancel");
		int selec=0;
		String selecSt="";
		do{
			try{
				selecSt=tcl.nextLine();
				if(selecSt.equals("c")){
					return 69;
				}
				selec=Integer.parseInt(selecSt);
			}catch(Exception e){
				selec=0;
				selecSt="";
				tcl.nextLine();
			}if(selec>playerMons[playerMonActive].items.length || selec<1 ||
			playerMons[playerMonActive].items[selec-1].equals("")){
				selec=0;
			}
		}while(selec<1);

		selec--;
		return selec;
	}

	private static void printBattleMenuOptions(){
	  //System.out.println("Your active Pokemon:       CPU's active Pokemon:");
		System.out.println("[1] "+Clr.RED_B+"Fight"+Clr.R+"               | [2]"+Clr.CYAN_B+" Pokemon"+Clr.R);
		System.out.println("[3] "+Clr.GREEN_B+"Items"+Clr.R+"               | [4]"+Clr.WHITE_B+" PKMN Info"+Clr.R);
		/*
		System.out.println("[1] Fight               | [2] Pokemon");
		System.out.println("[3] Items               | [4] PKMN Info");
		*/
	}

	private static void printBattleHUDThing(int playerHit, Clr ANSIcolor,String msg)throws IOException{
		//playerHit.. 1=ye, 2=cpu hit. other = do nothing
		//ANSIcolor = get ANSI escape sequence from enum Clr

		if(playerHit!=1 && playerHit!=2){
			ANSIcolor=Clr.R;
		}

		//BufferedWriter is fast as heck
		BufferedWriter cout = new BufferedWriter(new OutputStreamWriter(System.out));
		//THESE VARIALES ARE TOO LONG WTH
		//"playerPokemonTeam[playerPokemonActive].name"
		int plyAliveMon= countAliveMonInTeam(playerMons);
		int cpuAliveMon= countAliveMonInTeam(cpuMons);

		String pk1Name=playerMons[playerMonActive].name;
		String pk2Name=cpuMons[cpuMonActive].name;
		int pk1HP=playerMons[playerMonActive].currentHP;
		int pk2HP=cpuMons[cpuMonActive].currentHP;

		String spaces="";
		String par=Clr.YELLOW_B+"[PAR]"+Clr.R,
			   psn=Clr.MAGENTA_B+"[PSN]"+Clr.R,
			   brn=Clr.RED_B+"[BRN]"+Clr.R;
		String pk1Conditions=" ",pk2Conditions=" ";

		int ansiLength = ((ANSIcolor+"").length());
		int resemtLength = ((Clr.R+"").length());

		int truePk1NameLength = pk1Name.length();
		int truePk2NameLength = pk2Name.length();
		if(playerHit==1){// name string = ANSI.COLOR + name + ANSI.RESET
			//truePk1NameLength= ansiLength+(pk1Name.length())+resemtLength;
			pk1Name=ANSIcolor+pk1Name+Clr.R;
		}if(playerHit==2){
			pk2Name=ANSIcolor+pk2Name+Clr.R;
		}

		int cond1Length=1, cond2Length=1;

		if(playerMons[playerMonActive].isParalized){
			pk1Conditions+=par;
			cond1Length+=5;
		}if(playerMons[playerMonActive].isPoisoned){
			pk1Conditions+=psn;
			cond1Length+=5;
		}if(playerMons[playerMonActive].isBurning){
			pk1Conditions+=brn;
			cond1Length+=5;
		}

		if(cpuMons[cpuMonActive].isParalized){
			pk2Conditions+=par;
			cond2Length+=5;
		}if(cpuMons[cpuMonActive].isPoisoned){
			pk2Conditions+=psn;
			cond2Length+=5;
		}if(cpuMons[cpuMonActive].isBurning){
			pk2Conditions+=brn;
			cond2Length+=5;
		}

		//get & print number of alive mons in both teams on hud
		String al1="";
		String al2="";
		String alSpaces="";
		for(int i=0;i<plyAliveMon;i++){
			al1+="[o]";
		}for(int i=0;i<playerMons.length-plyAliveMon;i++){
			al1+="[x]";
		}

		for(int i=0;i<cpuMons.length-cpuAliveMon;i++){
			al2+="[x]";
		}for(int i=0;i<cpuAliveMon;i++){
			al2+="[o]";
		}

		for(int i=0;i<48-al1.length()-al2.length();i++){
			alSpaces+=" ";
		}

		//there's 50 spaces frfrfrfrfr <-- there's actually 48

		//----top line: displays number of alive mon in each team----//
		cout.write(al1+alSpaces+al2+"\n");

		//---second line: just displays "Your Pokemon:  CPU's Pokemon:"---//
		cout.write(Clr.WHITE_B+"Your Pokemon:");
		for(int i=0;i<35-cpuName.length()-11;i++){
			spaces+=" ";
		}
		cout.write(spaces); spaces="";
		cout.write(cpuName+"'s Pokemon:"+Clr.R+"\n");
		

		//----third line: displays the names of the active Pokemon----//
		cout.write(" "+pk1Name);
		for(int i=0;i<46-truePk2NameLength-truePk1NameLength;i++){
			spaces+=" ";
		}
		cout.write(spaces); spaces="";

		cout.write(pk2Name+"\n");

		//----fourth line: display HP count for both pokemon----//
		cout.write(" HP: "+pk1HP);
		for(int i=0;i<38-(pk1HP+"").length()-((pk2HP+"").length());i++){
			spaces+=" ";
		}
		cout.write(spaces); spaces="";

		cout.write("HP: "+pk2HP+"\n");

		//----fifth line: display status ailments (if any) for both pokemon---//
		cout.write(pk1Conditions);
		for(int i=0;i<47-(cond1Length)-(cond2Length);i++){
			spaces+=" ";
		}
		cout.write(spaces); spaces="";
		cout.write(pk2Conditions+"\n");
		cout.write("\n");
		if(!msg.equals("")){ //prints whatever string was passed
			cout.write(msg+"\n");
		}
		cout.flush();

	  //it's gotta look like this
	  //                    <----------------------48---------------------->
	  //System.out.println("Your Pokemon:                     CPU's Pokemon:");
	  //System.out.println(" name1                                    name2 ");
	  //System.out.println(" HP:69                                    HP:69 ");
	  //System.out.println(" [PAR][PSN][BRN]			    [PAR][PSN][BRN] ");
	}

	private static void printBattleHUDThing()throws IOException{
		printBattleHUDThing(0,Clr.R,"");
	}

	private static void printBattleHUDSequence(int ply, Clr color1, Clr color2,boolean shake,String msg)throws IOException, InterruptedException{
		//colored animation wip
		//int ply = 1 player ; 2 = cpu
		//print colored name with color1, then color2, then go back to normal
		if(shake){
			System.out.println(); //xd
			printBattleHUDThing(ply, color1, msg);
			wair(m,20000);
			clear();
			printBattleHUDThing(ply, color1, msg);
			wair(m,20000);
			clear();
			System.out.println();
			printBattleHUDThing(ply, color2, msg);
			wair(m,10000);
			clear();
			printBattleHUDThing();
		}else{
			printBattleHUDThing(ply, color1, msg);
			wair(m,20000);
			clear();
			printBattleHUDThing(ply, color2, msg);
			wair(m,20000);
			clear();
			printBattleHUDThing();
		}
	}


	static void printPlayerTeam(){
		System.out.print("Your team: ");
		for(int i=0;i<playerMons.length;i++){
			if(playerMons[i]!=null){
				if(i==3){
					if(playerMons.length>3){
						if(playerMons[5]!=null){
							System.out.println();
							System.out.print("           ");
						}
					}
				}
				System.out.print("["+playerMons[i].name+"] ");
			}
			
		}
		System.out.println("");
	}
	
	static void printCPUTeam(){
		System.out.print(cpuName+"'s team: ");
		for(int i=0;i<cpuMons.length;i++){
			if(cpuMons[i]!=null){
				if(i==3){
					System.out.println();
					System.out.print("           ");
				}
				System.out.print("["+cpuMons[i].name+"] ");
			}
		}
		System.out.println("");
	}

	static void printMiscStats(){
		System.out.println("");
		wair(s,1);
		System.out.println("Turns played: "+numbahOfTurns);
		wair(s,1);
		System.out.println("Highest damage dealt: "+highestDamage+", by: "+highestDamageName);
		wair(s,1);
		System.out.println("Damage done by you: "+totalPlayerDamage);
		wair(s,1);
		System.out.println("Damage done by "+cpuName+": "+totalCPUDamage);
	}

	//------------OTHER METHODS---------------//

	static private String autoCapitalizeMonName(String selecshon){
		String autoCap1=selecshon.charAt(0)+"";
		String autoCap2="";
		String autoCap3="";
		String[] pkmnNamesVector=getPkmnNamesVector();
		String[] secretMons=PokemonMaker3000.getSuperSecretMonList();
		
		autoCap1=autoCap1.toUpperCase();
		for(int i=1;i<selecshon.length();i++){
			autoCap2+=selecshon.charAt(i); // remove first letter
		}
		autoCap2=autoCap2.toLowerCase();
		for(int i=0;i<autoCap2.length();i++){
			if(i!=0 && (autoCap2.charAt(i-1))==' '){//if previus char was a space, capitalize char
				char upp = Character.toUpperCase(autoCap2.charAt(i));
				autoCap3+=upp;//all thanks to the paradox mon -_-
			}else{
				autoCap3+=autoCap2.charAt(i);
			}
		}
		selecshon=autoCap1+autoCap3;

		if(selecshon.contains("Adp ")){ // Arceus & Dialga & Palkia GX real!
			selecshon="ADP GX";
		}

		if(selecshon.length()>3){
			for(int i=0; i<pkmnNamesVector.length;i++){
				if(pkmnNamesVector[i].contains(selecshon)){
					selecshon=pkmnNamesVector[i];
					break;
				}if(i<secretMons.length){
					if(secretMons[i].contains(selecshon)){
						selecshon=secretMons[i];
						break;
					}
				}
			}
		}
		
		return selecshon;
	}
	
	static private boolean isNameCorrect(String selecshon){
		//auto capitalize
		String[] pkmnNamesVector=getPkmnNamesVector();
		String[] secretMons=PokemonMaker3000.getSuperSecretMonList();
		if(selecshon.equals("Mew")){// xd
			return true;
		}else{
			if(selecshon.length()>3){
				for(int i=0; i<pkmnNamesVector.length;i++){
					if(pkmnNamesVector[i].equals(selecshon)){
						return true;
					}if(i<secretMons.length){
						if(secretMons[i].equals(selecshon)){
							return true;
						}
					}
				}
			}else{
				return false;
			}
		}
		return false;
	}

	static private int[] randomizeMultihitValues(int dmg, int nHits){
		int[] dmgVector = new int[nHits];

		for(int i=0;i<dmgVector.length;i++){
			dmgVector[i]=dmg/nHits;
		}

		int deviation=0;

		for(int i=0;i<dmgVector.length;i++){
			try{
				deviation=rng.nextInt(dmg/30);
				deviation-=dmg/30;
				dmgVector[i]-=deviation;
				dmgVector[rng.nextInt(dmgVector.length)]+=deviation;
			}catch(Exception e){
				for(int k=0;k<dmgVector.length;k++){
					dmgVector[k]=dmg/nHits;
				}
				return dmgVector;
			}
		}
		return dmgVector;
	}

	static private boolean checkSwitchIn(int switchin,int active, Pokemon[] pkmnList){
		if(switchin!=active){
			if(switchin<0 || switchin>=pkmnList.length){
				return true;
			}
		}else{
			return true;
		}
		
		return false;
	}
	
	static private boolean checkAllPlayerMons(){
		for(int i=0;i<playerMons.length;i++){
			if(playerMons[i].currentHP!=0){
				return true;
			}
		}
		return false;
	}
	
	static private boolean checkAllCPUMons(){
		for(int i=0;i<cpuMons.length;i++){
			if(cpuMons[i].currentHP!=0){
				return true;
			}
		}
		return false;
	}
	
	static private int countAliveMonInTeam(Pokemon[] team){
		int count=0;
		for(int i=0;i<team.length;i++){
			if(team[i].currentHP!=0){
				count++;
			}
		}
		return count;
	}
	
	static private void saveHighestDmg(String name, int damag){
		if(damag>highestDamage){
			highestDamage=damag;
			highestDamageName=name;
		}
	}

	static private String getNewCPUName(){
		String[] names = new String[]{
			"Gary","Cyn","Sunna","Mario","Hop","Niko","Blue","Red","Green","Peter","N","Cebollin","CPU",
			"Nokia","Moya","Evie","Luigi","Noodle","Joel","Oatmeal","Nestle","Panda","Pingu","Gaby",
			"Maigol","Luci","Java","TWM","Sunflower","Nina","Lola","Obama","Guide","Steve","Freeman","Goku",
			"Cocuy","Socks","Bacon","Tocino","Arepa","Sans","Meevin","Zazu","Kevin","May","Eleki","Glue"
		};

		return names[rng.nextInt(names.length)];
	}

	static private String getRandomSwitchInQuote(String monName, boolean nameCpu){
		String[] quote = new String[]{
			monName+" enters the field!", "Go, "+monName+"!", "It's "+monName+"!!"
		};
		
		if(nameCpu){
			String[] cpuQuotes = new String[]{
				cpuName+" sent out "+monName+"!", cpuName+"'s "+monName+" has entered the battle!"
			};
			String[] quoteCopy = new String[quote.length+cpuQuotes.length];
			for(int i=0;i<quote.length;i++){
				quoteCopy[i]=quote[i];
			}
			int j=0;
			for(int i=quote.length;i<quoteCopy.length;i++){
				quoteCopy[i]=cpuQuotes[j];
				j++;
			}
			quote=quoteCopy;
		}
		return quote[rng.nextInt(quote.length)];
	}
	
	static private String getRandomSwitchInQuote(String monName){ //method overloading!!!!1
		return getRandomSwitchInQuote(monName,false);
	}
	
	static private String getRandomSwitchOutQuote(String monName){
		String[] quote = new String[]{
			monName+" retreated!", monName+" went back into its pokeball!" //ughh only 2 for now!???
		};
		return quote[rng.nextInt(quote.length)];
	}

	static void clear()throws IOException, InterruptedException{
		if(OsName.contains("Windows")){
			new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor(); // Windows cmd
		} else {
			System.out.print("\033[H\033[2J"); // Linux terminal
		}
	}
	
	static void wair(char opc,int tim){ //opciones.... s=segundos.... m=microsegundos. tim = tiempo
		switch(opc){
			case 's':
				try{TimeUnit.SECONDS.sleep(tim);
				} catch (InterruptedException e){System.out.println("o_o");}
			break;
			case 'm':
				try{TimeUnit.MICROSECONDS.sleep(tim);
				} catch (InterruptedException e){System.out.println("o_o");}
			break;
		}
	}
	
}
//main class ends

class Pokemon{
	int baseHP,baseDEF,baseATK,baseSPEED;
	int currentHP,currentDEF,currentATK,currentSPEED;
	int numberOfHits=1;
	int extraDmg=0;
	String name,type;
	boolean isPoisoned,isBurning,isParalized,healingOverTime,megaEvolved,strike,permaBurn,energyDrink;
	String[] weakTo = new String[5]; //listing-weaknesses
	String[] resists= new String[5]; //listing-resistances
	String[][] moveset = new String[2][4]; //[0=Name; 1=Type][Slot]
	String[] items = null;

	protected boolean resistsType(String pkmnType){
		boolean yayornay=false;
		for(int i=0;i<this.resists.length;i++){
			if(pkmnType.contains(this.resists[i])){
				yayornay=true;
				break;
			}
		}
		return yayornay;
	}

	protected boolean isWeakToType(String pkmnType){
		boolean yayornay=false;
		for(int i=0;i<this.weakTo.length;i++){
			if(pkmnType.contains(this.weakTo[i])){
				yayornay=true;
				break;
			}
		}
		return yayornay;
	}

	protected boolean hasStatusAilment(){
		if (this.isParalized || this.isBurning || this.isPoisoned) {
			return true;
		}//healing over time doesnt count!
		else{
			return false;
		}
	}

	protected void resetStats(){
		this.deMegaEvolve();
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
		boolean yayornay=false;
		String getType=defineMove(moveName);
		if((this.type+" Attack").equals(getType)){
			yayornay=true;
		}
		return yayornay;
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
				this.type="Dragon";
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
				this.type="Fairy";
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
				this.type="Dragon";
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
				this.type="Steel";
				this.moveset[0][2]="Behemoth Bash";
			break;
			case "Zacian":
				addHP=0;
				addAtk=35;
				addDef=-40;
				addSpeed=10;
				this.type="Steel";
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
				this.type="Fairy";
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
		this.moveset[1][0]=defineMove(this.moveset[0][0]);
		this.moveset[1][1]=defineMove(this.moveset[0][1]);
		this.moveset[1][2]=defineMove(this.moveset[0][2]);
		this.moveset[1][3]=defineMove(this.moveset[0][3]);
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
			
			//act as if enemy def is 1
			case "Super Fang": return "ignoredef";
			case "Sacred Sword": return "ignoredef";
			
			//x1.5 atk, debuff speed after use
			case "Hyper Beam": return "powerboost";
			case "Plasma Fists": return "powerboost";
			case "Giga Impact": return "powerboost";
			case "Meteor Beam": return "powerboost";
			
			//debuff enemy atk
			case "Mystical Fire": return "debuffatk";
			case "Skitter Smack": return "debuffatk";
			case "Lunge": return "debuffatk";
			
			//use enemy def as atk value
			case "Psyshock": return "defisatk";
			case "Body Slam": return "defisatk";
			
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

			case "Facade": return "facade";

			case "Flower Trick": return "guaranteedCrit";

			case "AlteredCreation": return "brokenCardMove";

			//more power if enemy below full HP
			case "ScratchingNails": return "scnails";
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
				moveset = new String[][]{{"","","",""},{"","","",""}};
			break;
			*/
			case "Custom": //yay
				baseHP=1;
				baseATK=0;
				baseDEF=0;
				baseSPEED=0;
				type="";//all of these stats are overwritten by PokemonMaker3000 when loading them in
				moveset = new String[][]{{"","","",""},{"","","",""}};
				//moveset array cannot contain null spaces or it'll fail to define the moves
			break;
			case "Venusaur":
				baseHP=400;
				baseATK=95;
				baseDEF=80;
				baseSPEED=75;
				type="Grass";
				moveset = new String[][]{{"Poison Powder","Vine Whip","Giga Drain","Earthquake"},{"","","",""}};
			break;
			case "Charizard":
				baseHP=325;
				baseATK=100;
				baseDEF=70;
				baseSPEED=90;
				type="Fire";
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
				moveset = new String[][]{{"Aura Sphere","Quick Attack","Dragon Pulse","Sword Dance"},{"","","",""}};
			break;
			case "Duraludon":
				baseHP=350;
				baseATK=100;
				baseDEF=81;
				baseSPEED=78;
				type="Steel";
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
				moveset = new String[][]{{"Bug Bite","Scald","Sword Dance","Iron Defense"},{"","","",""}};
			break;
			case "Heracross":
				baseHP=340;
				baseATK=111;
				baseDEF=67;
				baseSPEED=75;
				type="Bug";
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
				baseHP=280;
				baseATK=95;
				baseDEF=40;
				baseSPEED=120;
				type="Poison";
				moveset = new String[][]{{"Hyper Beam","Shadow Ball","Toxic Spikes","Sword Dance"},{"","","",""}};
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
				moveset = new String[][]{{"Aerial Ace","Psychic","Dragon Breath","Sword Dance"},{"","","",""}};
			break;
			case "Urshifu":
				baseHP=390;
				baseATK=110;
				baseDEF=55;
				baseSPEED=90;
				type="Fighting";
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
				moveset = new String[][]{{"Play Rough","Gigaton Hammer","Dream Eater","Sword Dance"},{"","","",""}};
			break;
			//--I THOUGHT I HAD 36 MONS BEFORE WAVE 2 ALREADY BUT I WAS MISSING 3 ALL THIS TIME--//
			//STEEL, DARK AND DRAGON o_o
			case "Zarude":
				baseHP=310;
				baseATK=95;
				baseDEF=90;
				baseSPEED=62;
				type="Dark";
				moveset = new String[][]{{"Giga Drain","Crunch","Drain Punch","Jungle Healing"},{"","","",""}};
			break;
			case "Dragapult":
				baseHP=270;
				baseATK=120;
				baseDEF=65;
				baseSPEED=138;
				type="Dragon";
				moveset = new String[][]{{"Dragon Rush","Hex","Will-O-Wisp","Dragon Dance"},{"","","",""}};
			break;
			case "Mawile":
				baseHP=290;
				baseATK=100;
				baseDEF=98;
				baseSPEED=78;
				type="Steel";
				moveset = new String[][]{{"Play Rough","Iron Head","Crunch","Fake Tears"},{"","","",""}};
			break;
			//--------wave 2 of pokemen--------//
			case "Blaziken":
				baseHP=320;
				baseATK=100;
				baseDEF=65;
				baseSPEED=90;
				type="Fire";
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
				moveset = new String[][]{{"Hammer Arm","Play Rough","Earth Power","Sword Dance"},{"","","",""}};
			break;
			case "Decidueye":
				baseHP=300;
				baseATK=105;
				baseDEF=70;
				baseSPEED=100;
				type="Grass";
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
				moveset = new String[][]{{"Aerial Ace","Superpower","Iron Head","Hone Claws"},{"","","",""}};
			break;
			case "Toxtricity":
				baseHP=265;
				baseATK=125;
				baseDEF=70;
				baseSPEED=80;
				type="Electric";
				moveset = new String[][]{{"Overdrive","Toxic Spikes","Hex","Scary Face"},{"","","",""}};
			break;
			case "Krookodile":
				baseHP=260;
				baseATK=115;
				baseDEF=70;
				baseSPEED=95;
				type="Ground";
				moveset = new String[][]{{"Earthquake","Crunch","Close Combat","Bulk Up"},{"","","",""}};
			break;
			case "Toucannon":
				baseHP=280;
				baseATK=125;
				baseDEF=60;
				baseSPEED=75;
				type="Flying";
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
				moveset = new String[][]{{"Crunch","Ice Fang","Toxic","Venoshock"},{"","","",""}};
			break;
			case "Walking Wake":
				baseHP=350;
				baseATK=130;
				baseDEF=65;
				baseSPEED=110;
				type="Dragon";
				moveset = new String[][]{{"Hydro Pump","Draco Meteor","Hyper Beam","Dragon Dance"},{"","","",""}};
			break;
			case "Roaring Moon":
				baseHP=380;
				baseATK=105;
				baseDEF=90;
				baseSPEED=100;
				type="Dragon";
				moveset = new String[][]{{"Dragon Rush","Crunch","Flamethrower","Dragon Dance"},{"","","",""}};
			break;
			case "Togekiss":
				baseHP=230;
				baseATK=110;
				baseDEF=105;
				baseSPEED=70;
				type="Fairy";
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
				moveset = new String[][]{{"Flying Press","Wing Attack","Stone Edge","Sword Dance"},{"","","",""}};
			break;
			case "Flutter Mane":
				baseHP=180;
				baseATK=135;
				baseDEF=100;
				baseSPEED=135;
				type="Ghost";
				moveset = new String[][]{{"Moonblast","Calm Mind","Shadow Ball","Mystical Fire"},{"","","",""}};
			break;
			case "Trevenant":
				baseHP=280;
				baseATK=110;
				baseDEF=82;
				baseSPEED=80;
				type="Ghost";
				moveset = new String[][]{{"Wood Hammer","Hex","Toxic","Will-O-Wisp"},{"","","",""}};
			break;
			case "Volcarona":
				baseHP=300;
				baseATK=130;
				baseDEF=60;
				baseSPEED=90;
				type="Bug";
				moveset = new String[][]{{"Bug Buzz","Fire Blast","Giga Drain","Amnesia"},{"","","",""}};
			break;
			case "Vespiquen":
				baseHP=300;
				baseATK=90;
				baseDEF=105;
				baseSPEED=60;
				type="Bug";
				moveset = new String[][]{{"Attack Order","Air Slash","Poison Sting","Defend Order"},{"","","",""}};
			break;
			case "Pangoro":
				baseHP=320;
				baseATK=120;
				baseDEF=80;
				baseSPEED=59;
				type="Fighting";
				moveset = new String[][]{{"Hammer Arm","Crunch","Bullet Punch","Bulk Up"},{"","","",""}};
			break;
			case "Aggron":
				baseHP=290;
				baseATK=90;
				baseDEF=150;
				baseSPEED=50;
				type="Steel";
				moveset = new String[][]{{"Iron Tail","Stone Edge","Giga Impact","Iron Defense"},{"","","",""}};
			break;
			case "Scizor":
				baseHP=270;
				baseATK=120;
				baseDEF=100;
				baseSPEED=70;
				type="Bug";
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
				moveset = new String[][]{{"Blizzard","Shadow Ball","Will-O-Wisp","Draining Kiss"},{"","","",""}};
			break;
			case "Baxcalibur":
				baseHP=350;
				baseATK=130;
				baseDEF=90;
				baseSPEED=87;
				type="Ice";
				moveset = new String[][]{{"Ice Beam","Crunch","Dragon Tail","Sword Dance"},{"","","",""}};
			break;
			case "Hydreigon":
				baseHP=330;
				baseATK=100;
				baseDEF=80;
				baseSPEED=98;
				type="Dark";
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
				moveset = new String[][]{{"Solar Beam","Stone Edge","Psychic","Amnesia"},{"","","",""}};
			break;
			case "Lunatone":
				baseHP=290;
				baseATK=100;
				baseDEF=70;
				baseSPEED=75;
				type="Rock";
				moveset = new String[][]{{"Moonblast","Stone Edge","Shadow Ball","Calm Mind"},{"","","",""}};
			break;
			//-------- wave 3 ---------//
			case "Delphox":
				baseHP=250;
				baseATK=116;
				baseDEF=100;
				baseSPEED=100;
				type="Fire";
				moveset = new String[][]{{"Mystical Fire","Psyshock","Shadow Ball","Calm Mind"},{"","","",""}};
			break;
			case "Gyarados":
				baseHP=310;
				baseATK=110;
				baseDEF=100;
				baseSPEED=80;
				type="Water";
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
				baseDEF=105;
				baseSPEED=80;
				type="Flying";
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
				moveset = new String[][]{{"Dragon Rush","Earthquake","Stone Edge","Sword Dance"},{"","","",""}};
			break;
			case "Gholdengo":
				baseHP=230;
				baseATK=133;
				baseDEF=90;
				baseSPEED=70;
				type="Steel";
				moveset = new String[][]{{"Make it Rain","Shadow Ball","Psychic","Thunder Wave"},{"","","",""}};
			break;
			case "Galvantula":
				baseHP=230;
				baseATK=100;
				baseDEF=90;
				baseSPEED=100;
				type="Electric";
				moveset = new String[][]{{"Bug Buzz","Energy Ball","Thunder Wave","String Shot"},{"","","",""}};
			break;
			case "Ceruledge":
				baseHP=225;
				baseATK=130;
				baseDEF=120;
				baseSPEED=86;
				type="Ghost";
				moveset = new String[][]{{"Shadow Claw","Bitter Blade","X-Scissor","Sword Dance"},{"","","",""}};
			break;
			case "Chandelure":
				baseHP=200;
				baseATK=155;
				baseDEF=80;
				baseSPEED=80;
				type="Ghost";
				moveset = new String[][]{{"Hex","Overheat","Trailblaze","Acid Armor"},{"","","",""}};
			break;
			case "Flamigo":
				baseHP=260;
				baseATK=115;
				baseDEF=40;
				baseSPEED=90;
				type="Flying";
				moveset = new String[][]{{"Brave Bird","Double Kick","Cyclone","Roost"},{"","","",""}};
			break;
			case "Zamazenta":
				baseHP=290;
				baseATK=110;
				baseDEF=115;
				baseSPEED=110;
				type="Fighting";									//behemoth bash
				moveset = new String[][]{{"Close Combat","Solar Beam","Iron Head","Iron Defense"},{"","","",""}};
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
				moveset = new String[][]{{"Retaliate","Assurance","Metal Sound","Sword Dance"},{"","","",""}};
			break;
			case "Azumarill":
				baseHP=300;
				baseATK=80;
				baseDEF=90;
				baseSPEED=80;
				type="Fairy";
				moveset= new String[][]{{"Muddy Water","Alluring Voice","MistyExplosion","Amnesia"},{"","","",""}};
			break;
			case "Gallade":
				baseHP=210;
				baseATK=130;
				baseDEF=65;
				baseSPEED=115;
				type="Fighting";
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
				moveset = new String[][]{{"Diamond Storm","Moonblast","Rock Smash","Rock Polish"},{"","","",""}};
			break;
			case "Weavile":
				baseHP=285;
				baseATK=120;
				baseDEF=65;
				baseSPEED=125;
				type="Ice";
				moveset = new String[][]{{"Triple Axel","Night Slash","Shadow Claw","Sword Dance"},{"","","",""}};
			break;
			case "Chien-Pao":
				baseHP=300;
				baseATK=135;
				baseDEF=40;
				baseSPEED=90;
				type="Ice";
				moveset = new String[][]{{"Avalanche","Ruination","Hyper Beam","Scary Face"},{"","","",""}};
			break;
			case "Yanmega":
				baseHP=300;
				baseATK=115;
				baseDEF=90;
				baseSPEED=95;
				type="Bug";
				moveset = new String[][]{{"Skitter Smack","Dual Wingbeat","Facade","Extreme Speed"},{"","","",""}};
			break;
			case "Kleavor":
				baseHP=285;
				baseATK=135;
				baseDEF=85;
				baseSPEED=85;
				type="Bug";
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
		}
		
		setTypesWnR();

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

		items= new String[]{"Potion","X-Attack","X-Defense","X-Speed","","Dash Earring","Strike Earrings","Energy Drink"};
		if(this.canMegaEvolve()){
			items[4]="Mega Stone";
		}
		
	}//class Pokemon constructor ends
	
	protected void setTypesWnR(){//had to put this in a fuction thanks to the custom mons lol
		switch(type){ //sets weaknesses and resistances
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
			case "Ice": //I FORGOT ICE LMAOOOOOOOOO
				weakTo= new String[]{"Fighting","Rock","Steel","Fire"};
				resists= new String[]{"Ice"};//truly best defensive type
			break;
		}
	}

	protected String defineMove(String move){
		String[] moveTableStatus = PokemonMaker3000.getMoveTable("status");
		String[] moveTableAtkNormal = PokemonMaker3000.getMoveTable("normal");
		String[] moveTableAtkFire = PokemonMaker3000.getMoveTable("fire");
		String[] moveTableAtkWater = PokemonMaker3000.getMoveTable("water");
		String[] moveTableAtkElectric = PokemonMaker3000.getMoveTable("electric");
		String[] moveTableAtkGrass = PokemonMaker3000.getMoveTable("grass");
		String[] moveTableAtkIce = PokemonMaker3000.getMoveTable("ice");
		String[] moveTableAtkFighting = PokemonMaker3000.getMoveTable("fighting");
		String[] moveTableAtkPoison = PokemonMaker3000.getMoveTable("poison");
		String[] moveTableAtkGround = PokemonMaker3000.getMoveTable("ground");
		String[] moveTableAtkFlying = PokemonMaker3000.getMoveTable("flying");
		String[] moveTableAtkPsychic = PokemonMaker3000.getMoveTable("psychic");
		String[] moveTableAtkBug = PokemonMaker3000.getMoveTable("bug");
		String[] moveTableAtkRock = PokemonMaker3000.getMoveTable("rock");
		String[] moveTableAtkGhost = PokemonMaker3000.getMoveTable("ghost");
		String[] moveTableAtkDragon = PokemonMaker3000.getMoveTable("dragon");
		String[] moveTableAtkDark = PokemonMaker3000.getMoveTable("dark");
		String[] moveTableAtkSteel = PokemonMaker3000.getMoveTable("steel");
		String[] moveTableAtkFairy = PokemonMaker3000.getMoveTable("fairy");
		
		String returnar = "";
		
		// if move is a status
		for(int i=0;i<moveTableStatus.length;i++){
			if(move.equals(moveTableStatus[i])){
				return "Status Effect";
			}
		}
		
		// ------------move is an attack---------- //
		
		// NORMAL
		if(returnar.equals("")){// these if("") are unnecessary now but im too lazy to remove them
			for(int i=0;i<moveTableAtkNormal.length;i++){
				if(move.equals(moveTableAtkNormal[i])){
					return "Normal Attack";}}}
		
		// GRASS
		if(returnar.equals("")){
		for(int i=0;i<moveTableAtkGrass.length;i++){
			if(move.equals(moveTableAtkGrass[i])){
				return "Grass Attack";
			}
		}}
		
		// FIRE
		if(returnar.equals("")){
		for(int i=0;i<moveTableAtkFire.length;i++){
			if(move.equals(moveTableAtkFire[i])){
				return "Fire Attack";
			}
		}}
		// WATER
		if(returnar.equals("")){
		for(int i=0;i<moveTableAtkWater.length;i++){
			if(move.equals(moveTableAtkWater[i])){
				return "Water Attack";
			}
		}}
		// ELECTRIC
		if(returnar.equals("")){
			for(int i=0;i<moveTableAtkElectric.length;i++){
				if(move.equals(moveTableAtkElectric[i])){
					return "Electric Attack";
				}
		}}
		// FIGHTING
		if(returnar.equals("")){
			for(int i=0;i<moveTableAtkFighting.length;i++){
				if(move.equals(moveTableAtkFighting[i])){
					return "Fighting Attack";
				}
		}}
		// POISON
		if(returnar.equals("")){
			for(int i=0;i<moveTableAtkPoison.length;i++){
				if(move.equals(moveTableAtkPoison[i])){
					return "Poison Attack";
				}
		}}
		// FLYING
		if(returnar.equals("")){
			for(int i=0;i<moveTableAtkFlying.length;i++){
				if(move.equals(moveTableAtkFlying[i])){
					return "Flying Attack";
			}
		}}
		// GROUND
		if(returnar.equals("")){
		for(int i=0;i<moveTableAtkGround.length;i++){
			if(move.equals(moveTableAtkGround[i])){
				return "Ground Attack";
			}
		}}
		// BUG
		if(returnar.equals("")){
			for(int i=0;i<moveTableAtkBug.length;i++){
				if(move.equals(moveTableAtkBug[i])){
					return "Bug Attack";
			}
		}}
		// ROCK
		if(returnar.equals("")){
			for(int i=0;i<moveTableAtkRock.length;i++){
				if(move.equals(moveTableAtkRock[i])){
					return "Rock Attack";
				}
		}}
		// DRAGON
		if(returnar.equals("")){
		for(int i=0;i<moveTableAtkDragon.length;i++){
			if(move.equals(moveTableAtkDragon[i])){
				return "Dragon Attack";
			}
		}}
		
		// STEEL
		if(returnar.equals("")){
		for(int i=0;i<moveTableAtkSteel.length;i++){
			if(move.equals(moveTableAtkSteel[i])){
				return "Steel Attack";
			}
		}}
		// ICE
		if(returnar.equals("")){
		for(int i=0;i<moveTableAtkIce.length;i++){
			if(move.equals(moveTableAtkIce[i])){
				return "Ice Attack";
			}
		}}
		
		// PSYCHIC
		if(returnar.equals("")){
		for(int i=0;i<moveTableAtkPsychic.length;i++){
			if(move.equals(moveTableAtkPsychic[i])){
				return "Psychic Attack";
			}
		}}
		// DARK
		if(returnar.equals("")){
			for(int i=0;i<moveTableAtkDark.length;i++){
				if(move.equals(moveTableAtkDark[i])){
					return "Dark Attack";
			}
		}}
		// GHOST
		if(returnar.equals("")){
			for(int i=0;i<moveTableAtkGhost.length;i++){
				if(move.equals(moveTableAtkGhost[i])){
					return "Ghost Attack";
				}
		}}
		// FAIRY
		if(returnar.equals("")){
			for(int i=0;i<moveTableAtkFairy.length;i++){
				if(move.equals(moveTableAtkFairy[i])){
					return "Fairy Attack";
				}
		}}
		
		return returnar; //will return "" if failed to define the move
	}
}//class Pokemon ends

class PokemonMaker3000 extends PokemonBattleSim{
	//funny class name
	private static void printTypes(){
		String[] typesVector=getTypesVector();
		int coumter=0;
		for(int i=0;i<typesVector.length;i++){
			if(coumter<3){
				System.out.print("["+(i+1)+"]"+typesVector[i]);
				for(int j=0;j<8-typesVector[i].length();j++){
					System.out.print(" ");
				}
				if(i<9){
					System.out.print(" "); //ajdfjshdfjdskjfds
				}
				System.out.print("| ");
				coumter++;
			}else{
				System.out.println("");
				coumter=0;
				i--;
			}
		}
		System.out.println("");
	}

	private static void printSillyMessage(String name,int atk,int def,int speed,int hp){
		//i love making unnecessary functions!!!!
		int total=atk+speed+def+hp;
		String msg="";
		if(atk>90 && def<81 && speed>99 && hp<350){msg="glasscannon";
		}if(speed<90 && atk<110 && def>90 && hp>350){msg="tank";
		}if(msg==""){
			if(total>450 && total<650){msg="balanced";
			}if(total>=651 && total<900){msg="strong";
			}if(total>900 && total<1850){msg="op";
			}if(total<350){msg="weak";
			}if(total>1849){msg="max";}
		}
		switch(msg){
			case "glasscannon":
			System.out.println(name+" is looking quite condifent!");break;
			case "tank":
			System.out.println(name+" is looking pretty robust!");break;
			case "balanced":
			System.out.println(name+" is looking alright!");break;
			case "strong":
			System.out.println(name+" is looking quite strong!");break;
			case "op":
			System.out.println(name+" is looking pretty OP!");break;
			case "weak":
			System.out.println(name+" could use a few nutrients!");break;
			case "max":
			System.out.println(name+" is looking perfectly balanced!");break;
			default:
			System.out.println(name+" is looking good!");break;
		}
		System.out.println("");
	}

	private static String[] getTypesVector(){
		String[] typesVector = {"Fire","Water","Grass","Normal","Fighting","Flying","Poison",
		"Ground","Rock","Bug","Ghost","Steel","Electric","Psychic","Ice","Dragon","Dark","Fairy"};
		return typesVector;
	}

	public static String[] getMoveTable(String typ){
		String[] table = {};
		String[] moveTableStatus = new String[]{"Poison Powder","Will-O-Wisp","Sword Dance","Roar","Hone Claws","Calm Mind",
		"Iron Defense","Toxic","Dragon Dance","Growl","Charm","Bulk Up","Heal Pulse","Charge","Roost","Extreme Speed","Amnesia",
		"Aqua Ring","Impulse","Jungle Healing","Fake Tears","Scary Face","Agility","Defend Order","Work Up","Thunder Wave","Last Resort",
		"Shift Gear","String Shot","Acid Armor","Lunar Plumage","Metal Sound","Coil","Salt Cure","Rock Polish"};
		String[] moveTableAtkNormal = new String[]{"Quick Attack","Hyper Beam","Giga Impact","Super Fang","Facade","Swift","Judgement","Ascension","Group Beating","Retaliate"};
		String[] moveTableAtkFire = new String[]{"Flamethrower","Flame Charge","Overheat","Fire Blast","Mystical Fire","Bitter Blade"};
		String[] moveTableAtkWater = new String[]{"Hydro Pump","Hydro Cannon","Surf","Whirlpool","Scald","Water Shuriken","SurgingStrikes","Muddy Water"};
		String[] moveTableAtkElectric = new String[]{"Thunder","Thunder Fang","Electroweb","Overdrive","Plasma Fists","Zap Cannon","Thunder Cage"};
		String[] moveTableAtkGrass = new String[]{"Vine Whip","Giga Drain","Flower Trick","Trailblaze","Razor Leaf","Grass Knot","Wood Hammer","Leaf Blade","Solar Beam","Energy Ball","Powerful Bloom"};
		String[] moveTableAtkIce = new String[]{"Ice Beam","Ice Fang","Freeze Dry","Blizzard","Ice Slash","Aurora Beam","Triple Axel","Avalanche"};
		String[] moveTableAtkFighting = new String[]{"Aura Sphere","Close Combat","Rock Smash","Body Slam","Double Kick","Hammer Arm","Drain Punch","HJ Kick","Superpower","Flying Press","Focus Blast","Body Press","Sacred Sword","Vacuum Wave"};
		String[] moveTableAtkPoison = new String[]{"Poison Leech","Toxic Spikes","Venoshock","Poison Sting","Sludge Bomb"};
		String[] moveTableAtkGround = new String[]{"Earthquake","Mud Slap","Earth Power","X","Excite","Magnitude"};
		String[] moveTableAtkFlying = new String[]{"Wing Attack","Gust","Aerial Ace","Dual Wingbeat","Air Slash","Brave Bird","Cyclone"};
		String[] moveTableAtkPsychic = new String[]{"Psystrike","Psychic","Dream Eater","Psybeam","Psycho Cut","Psyshock"};
		String[] moveTableAtkBug = new String[]{"Bug Bite","Life Leech","Bug Buzz","Attack Order","Pin Missile","X-Scissor","Skitter Smack","Lunge"};
		String[] moveTableAtkRock = new String[]{"Rock Throw","Head Smash","Stone Edge","Meteor Beam","Diamond Storm","Stone Axe"};
		String[] moveTableAtkGhost = new String[]{"Shadow Ball","Hex","Shadow Sneak","Shadow Claw","Lick"};
		String[] moveTableAtkDragon = new String[]{"Dragon Breath","Dragon Rush","Dragon Pulse","Dragon Tail","Draco Meteor","AlteredCreation"};
		String[] moveTableAtkDark = new String[]{"Pursuit","Bite","Sucker Punch","Crunch","Night Slash","Assurance","Ruination","ScratchingNails"};
		String[] moveTableAtkSteel = new String[]{"Metal Claw","Iron Tail","Iron Head","Flash Cannon","Iron Hammer","Bullet Punch","Steel Wing","Make it Rain","Behemoth Blade","Behemoth Bash","Gigaton Hammer"};
		String[] moveTableAtkFairy = new String[]{"Moonblast","Play Rough","Draining Kiss","Halo","Fleur Cannon","MistyExplosion","Alluring Voice","OverdriveSmash"};

		switch (typ) {
			case "status":table=moveTableStatus;break;
			case "normal":table=moveTableAtkNormal;break;
			case "fire":table=moveTableAtkFire;break;
			case "water":table=moveTableAtkWater;break;
			case "grass":table=moveTableAtkGrass;break;
			case "bug":table=moveTableAtkBug;break;
			case "electric":table=moveTableAtkElectric;break;
			case "steel":table=moveTableAtkSteel;break;
			case "ghost":table=moveTableAtkGhost;break;
			case "rock":table=moveTableAtkRock;break;
			case "ground":table=moveTableAtkGround;break;
			case "dark":table=moveTableAtkDark;break;
			case "fairy":table=moveTableAtkFairy;break;
			case "flying":table=moveTableAtkFlying;break;
			case "fighting":table=moveTableAtkFighting;break;
			case "psychic":table=moveTableAtkPsychic;break;
			case "dragon":table=moveTableAtkDragon;break;
			case "ice":table=moveTableAtkIce;break;
			case "poison":table=moveTableAtkPoison;break;
		}
		return table;
	}

	private static String[] getFilePaths(){
		String path = System.getProperty("user.home");
		String filenames[] = new String[]{path+"\\"+"customPokemon.txt",path+"\\"+"customPokemon2.txt",
		path+"\\"+"customPokemon3.txt",path+"\\"+"customPokemon4.txt"};

		return filenames;
	}

	//returns a whole ass pokemon!!!!!
	public static Pokemon makeCustomMon()throws IOException, InterruptedException{
		String[] savePaths = getFilePaths();
		boolean txtfile=true; //<--must be true by default!!!
		String monName="", monType="";
		String[] monMoveset = new String[4];
		String[] typesVector = getTypesVector();
		int monHP=0,monAtk=0, monDef=0, monSpeed=0;
		File[] txt = new File[savePaths.length];
		for (int i=0;i<txt.length;i++) {
			txt[i]=new File(savePaths[i]);
		}
		Pokemon customMon = null;

		int yayornay=0;

		try{
			if(txt[0].exists() || txt[1].exists() || txt[2].exists() || txt[3].exists()){
				int selectedMon=69;
				do{
					clear();
					System.out.println(Clr.CYAN_B+"---[POKEMON MAKER 3000]---"+Clr.R);
					System.out.println("Save files have been found! \n");

					printCustomMonSlots(false);
					System.out.println(Clr.YELLOW_B+"Select an option:"+Clr.R+"\n");
					System.out.println("[1]: Pick a 'Mon to bring to battle");
					System.out.print("[2]: Make a new 'Mon");
					if(txt[0].exists() && txt[1].exists() && txt[2].exists() && txt[3].exists()){
						System.out.print(" without saving"); // if all 4 slots are taken
					}
					System.out.println();
					System.out.println("[3]: Delete a saved 'Mon");
					System.out.println("[0]: Cancel");

					try{
						yayornay=tcl.nextInt();
						tcl.nextLine();
					}catch(InputMismatchException e){
						yayornay=69;
						tcl.nextLine();
					}
					if(yayornay==0){//cancel
						return null;
					}
				}while(yayornay!=1 && yayornay!=2 && yayornay!=3);

				if(yayornay==1){// try to read pokemon data
					do{
						clear();
						System.out.println(Clr.CYAN_B+"---[POKEMON MAKER 3000]---"+Clr.R);
						System.out.println("\n Select which Pokemon to use");

						printCustomMonSlots(true);
						System.out.println("[0]: Cancel");
						try{
							selectedMon=Integer.parseInt(tcl.nextLine());
						}catch(InputMismatchException | NumberFormatException e){
							selectedMon=69;
						}if(selectedMon==0){
							return null;
						}
					}while(selectedMon!=1 && selectedMon!=2 && selectedMon!=3 && selectedMon!=4);

					selectedMon--;
					customMon=readMonFromTxt(txt[selectedMon]);
					if(customMon!=null){
						System.out.println("Read from file successfully!");
						System.out.println("Press Enter to continue");
						tcl.nextLine();
						return customMon;
					}else{
						System.out.println("COULDN'T READ FROM FILE");
						if(txt[selectedMon].exists()==false){
							System.out.println("There's no saved Pokemon in that slot!");
							wair(s,2);
							tcl.nextLine();
							return null;
						}else{
							System.out.println("Data inside might be broken or incorrect");
							System.out.println("Delete the file? \n");
							System.out.println("[1]: Yes  [2]: No");
							try{
								yayornay=tcl.nextInt();
							}catch(InputMismatchException e){
								yayornay=2;
							}
							if(yayornay==1){
								if(txt[selectedMon].delete()){
									System.out.println("File deleted");
									wair(s,2);
								}else{
									System.out.println("Failed to delete the file! o_o");
									wair(s,2);
								}
							}else{
								tcl.nextLine();
								return null;
							}
						}
						yayornay=0;
					}

				}if(yayornay==2){
					if(txt[0].exists() && txt[1].exists() && txt[2].exists() && txt[3].exists()){
						// if all 4 slots are taken
						txtfile=false;
					}else{
						txtfile=true;
					}
					yayornay=0;
				}if(yayornay==3){//delet
					do{
						clear();
						System.out.println(Clr.CYAN_B+"---[POKEMON MAKER 3000]---"+Clr.R);
						System.out.println("\n Select which Pokemon to DELETE");

						printCustomMonSlots(true);
						System.out.println("[0]: Cancel");
						try{
							selectedMon=tcl.nextInt();
						}catch(InputMismatchException e){
							selectedMon=69;
							tcl.nextLine();
						}if(selectedMon==0){
							tcl.nextLine();
							return null;
						}
					}while(selectedMon!=1 && selectedMon!=2 && selectedMon!=3 && selectedMon!=4);
					selectedMon--;
					if(txt[selectedMon].exists()){
						if(txt[selectedMon].delete()){
							System.out.println("File deleted");
							wair(s,2);
							return null;
						}else{
							System.out.println("Failed to delete the file! o_o");
							wair(s,2);
							return null;
						}
					}else{
						System.out.println("File doesn't exist!");
						wair(s,3);
						return null;
					}
				}
			}
		}catch(Exception e){
			// "Write code for the catch!"
			// :P BLEEHH you can't make me! :3 >_<
		}
		yayornay=0;

		if(txtfile){//-----save it?
			do{
				clear();
				System.out.println(Clr.CYAN_B+"---[POKEMON MAKER 3000]---"+Clr.R);
				System.out.println("Let's make a custom mon!! \n");
				System.out.println("Do you want to save it to a txt file?");
				System.out.println("[1]: Yes  [2]: No");
				try{
					yayornay=tcl.nextInt();
				}catch(InputMismatchException e){
					yayornay=69;
					tcl.nextLine();
				}
			}while(yayornay!=1 && yayornay!=2);
			
			if(yayornay==1){
				txtfile=true;
			}else{
				txtfile=false;
			}
		}
		
		do{//--------Name
			clear();
			System.out.println(Clr.CYAN_B+"---[POKEMON MAKER 3000]---"+Clr.R);
			System.out.println("What will its name be?\n");
			System.out.print("->");
			try{
				monName=tcl.nextLine();
			}catch(InputMismatchException e){
				monName="";
			}

			if(monName.length()>12){
				System.out.println("The name cannot be longer than 12 characters");
				wair(s,2);
			}

		}while(monName.equals("") || monName.length()>12);

		do{//------Type
			int selectedInt=0;
			clear();
			System.out.println(Clr.CYAN_B+"---[POKEMON MAKER 3000]---"+Clr.R);
			System.out.println("Select a type for your mon");
			System.out.println("");
			
			printTypes();

			try{
				monType=tcl.nextLine();
				selectedInt=Integer.parseInt(monType);
				if(selectedInt<1 || selectedInt>18){
					System.out.println("Invalid value");
					monType="";
					wair(s,2);
				}else{
					selectedInt--;
					monType=typesVector[selectedInt];
				}
			}catch(Exception e){
				monType="";
				selectedInt=0;
				System.out.println("Please try again");
				wair(s,2);
			}
		}while(monType.equals(""));

		//----------------Stats--------------//
		do{
			do{//---------HP
				clear();
				System.out.println(Clr.CYAN_B+"---[POKEMON MAKER 3000]---"+Clr.R);
				System.out.println("Your Mon's base stats:");
				System.out.println("HP:"+"\n"+" How much damage it can take before fainting");
				System.out.println("");
				System.out.print("->");
				try{
					monHP=tcl.nextInt();
					if(monHP<1){
						System.out.println("It can't be lower than 1!!");
						wair(s,2);
					}if(monHP>900){
						System.out.println("It can't be higher than 900!");
						wair(s,2);
					}

				}catch(InputMismatchException e){
					monHP=0;
					tcl.nextLine();
					System.out.println("Please try again");
					wair(s,2);
				}

			}while(monHP<1 || monHP>900);

			do{//---------ATK
				clear();
				System.out.println(Clr.CYAN_B+"---[POKEMON MAKER 3000]---"+Clr.R);
				System.out.println("Your Mon's base stats:");
				System.out.println("ATTACK:"+"\n"+" Determines how strong its attacking moves are");
				System.out.println("");
				System.out.print("->");
				try{
					monAtk=tcl.nextInt();
					if(monAtk<50){
						System.out.println("It can't be lower than 50");
						wair(s,2);
					}if(monAtk>500){
						System.out.println("It can't be higher than 500!");
						wair(s,2);
					}

				}catch(InputMismatchException e){
					monAtk=0;
					tcl.nextLine();
					System.out.println("Please try again");
					wair(s,2);
				}

			}while(monAtk<50 || monAtk>500);
			
			do{//---------DEF
				clear();
				System.out.println(Clr.CYAN_B+"---[POKEMON MAKER 3000]---"+Clr.R);
				System.out.println("Your Mon's base stats:");
				System.out.println("DEFENSE:"+"\n"+" The higher, the more damage it can absorb");
				System.out.println("");
				System.out.print("->");
				try{
					monDef=tcl.nextInt();
					if(monDef<2){
						System.out.println("It can't be lower than 2");
						wair(s,2);
					}if(monDef>150){
						System.out.println("It can't be higher than 150!");
						wair(s,2);
					}

				}catch(InputMismatchException e){
					monDef=0;
					tcl.nextLine();
					System.out.println("Please try again");
					wair(s,2);
				}

			}while(monDef<2 || monDef>150);
			
			do{//---------SPEED
				clear();
				System.out.println(Clr.CYAN_B+"---[POKEMON MAKER 3000]---"+Clr.R);
				System.out.println("Your Mon's base stats:");
				System.out.println("SPEED:"+"\n"+" Determines turn order and critical chance");
				System.out.println("");
				System.out.print("->");
				try{
					monSpeed=tcl.nextInt();
					if(monSpeed<2){
						System.out.println("It can't be lower than 2");
						wair(s,2);
					}if(monSpeed>300){
						System.out.println("It can't be higher than 300!");
						wair(s,2);
					}

				}catch(InputMismatchException e){
					monSpeed=0;
					tcl.nextLine();
					System.out.println("Please try again");
					wair(s,2);
				}

			}while(monSpeed<2 || monSpeed>300);

			clear();
			System.out.println(Clr.CYAN_B+"---[POKEMON MAKER 3000]---"+Clr.R);
			System.out.println("Your Mon's base stats:"+"\n");//<-- C++ reference!!?

			System.out.println("HP:      "+monHP);
			System.out.println("Attack:  "+monAtk);
			System.out.println("Defense: "+monDef);
			System.out.println("Speed:   "+monSpeed);
			System.out.println("");
			printSillyMessage(monName,monAtk,monDef,monSpeed,monHP);
			System.out.println("Total stats: "+(monAtk+monDef+monHP+monSpeed)+"\n");
			
			System.out.println("Proceed?  [1]: Yes [2]: Re-do");
			try{
				yayornay=tcl.nextInt();
			}catch(Exception e){
				yayornay=0;
				tcl.nextLine();
			}

		}while(yayornay!=1);

		//------MOVESET------//
		for(int i=0;i<monMoveset.length;i++){
			int selecshon=0;
			clear();
			System.out.println(Clr.CYAN_B+"---[POKEMON MAKER 3000]---"+Clr.R);
			System.out.println(" MOVESET:");
			printSelectedMoves(monMoveset);
			System.out.println(" Select move for slot ["+(i+1)+"]:"+"\n");

			System.out.println("[1]: Status moves");
			System.out.println("[2]: Attacking moves");
			System.out.println("");
			try{
				selecshon=tcl.nextInt();
				tcl.nextLine();
			}catch(Exception e){
				selecshon=0;
				tcl.nextLine();
			}
			
			if(selecshon==1 || selecshon==2){
				if(selecshon==1){
					monMoveset[i]=selectAMove("status",i,monMoveset);
					if(monMoveset[i].equals("cancel")){
						monMoveset[i]="";
						i--;
					}
				}else{
					String movType="";
					int selectedInt=0;
					//loops LOOPS LOOPS LOOPS
					do{
						clear();
						System.out.println(Clr.CYAN_B+"---[POKEMON MAKER 3000]---"+Clr.R);
						System.out.println(" MOVESET:");
						printSelectedMoves(monMoveset);
						System.out.println(" Select move for slot ["+(i+1)+"]:");
						System.out.println(" Select a type:"+"\n");
						printTypes();
						try{
							movType=tcl.nextLine();
							selectedInt=Integer.parseInt(movType);
							if(selectedInt<1 || selectedInt>18){
								System.out.println("Invalid value");
								movType="";
								wair(s,2);
							}else{
								selectedInt--;
								movType=typesVector[selectedInt];
							}
						}catch(Exception e){
							movType="";
							selectedInt=0;
							System.out.println("Please try again");
							wair(s,2);
						}
					}while(movType=="");
					
					movType=movType.toLowerCase();
					monMoveset[i]=selectAMove(movType,i,monMoveset);
					if(monMoveset[i].equals("cancel")){
						monMoveset[i]="";
						i--;
					}
				}
			}else{
				System.out.println("Try again!");
				wair(s,1);
				i--;
			}

		}


		clear();
		System.out.println(Clr.CYAN_B+"---[POKEMON MAKER 3000]---"+Clr.R);
		System.out.println("Epically making Mons since 1984");
		System.out.println("");
		wair(s,1);

		int slot=-1; //try to find a free slot to save the custom Mon
		for(int i=0;i<savePaths.length;i++){
			if(txt[i].exists()==false){
				slot=i;
				break;
			}
		}if(slot==-1 && txtfile){
			System.out.println("ERROR: No free slots available!");
			wair(s,1);
			txtfile=false;
		}

		//----save the mon----//
		if(txtfile){
			boolean flag = saveMonInTxt(monAtk,monDef,monHP,monSpeed,monName,monType,monMoveset,savePaths[slot]);
			if(flag){
				System.out.println("File saved successfully!!!!!");
				System.out.println("Directory: "+savePaths[slot]);
				System.out.println("");
				wair(s,2);
			}else{
				System.out.println("ERROR, COULDN'T SAVE THE FILE :(");
				System.out.println("Directory: "+savePaths[slot]);
				System.out.println("");
				wair(s,2);
			}
		}

		customMon = new Pokemon("Custom");
		
		customMon.name=monName;
		customMon.type=monType;
		customMon.setTypesWnR();
		customMon.baseHP=monHP;
		customMon.baseATK=monAtk;
		customMon.baseDEF=monDef;
		customMon.baseSPEED=monSpeed;

		customMon.currentHP=customMon.baseHP;
		customMon.currentATK=customMon.baseATK;
		customMon.currentDEF=customMon.baseDEF;
		customMon.currentSPEED=customMon.baseSPEED;

		customMon.moveset[0][0]=monMoveset[0];
		customMon.moveset[0][1]=monMoveset[1];
		customMon.moveset[0][2]=monMoveset[2];
		customMon.moveset[0][3]=monMoveset[3];

		customMon.moveset[1][0]=customMon.defineMove(customMon.moveset[0][0]);
		customMon.moveset[1][1]=customMon.defineMove(customMon.moveset[0][1]);
		customMon.moveset[1][2]=customMon.defineMove(customMon.moveset[0][2]);
		customMon.moveset[1][3]=customMon.defineMove(customMon.moveset[0][3]);
		
		System.out.println("Pokemon created!!");
		System.out.println(customMon.name+" is ready for battle!");
		System.out.println("Press Enter to continue");
		tcl.nextLine();

		return customMon;
	}

	private static boolean saveMonInTxt(int atk, int def, int hp, int speed, String name, String type,
	String[] moves, String savePath){
		File txt = new File(savePath);
		boolean success=true;
		try{
			FileWriter fw = new FileWriter(txt,true);

			fw.write(name+"\n");
			fw.write(type+"\n");
			fw.write(hp+"\n");
			fw.write(atk+"\n");
			fw.write(def+"\n");
			fw.write(speed+"\n");
			fw.write(moves[0]+"\n");
			fw.write(moves[1]+"\n");
			fw.write(moves[2]+"\n");
			fw.write(moves[3]+"\n");

			fw.close();
		}catch(Exception e){
			success=false;
		}

		return success;
	}

	private static Pokemon readMonFromTxt(File txt){
		Pokemon customMon = null;
		int mhp,matk,mdef,mspeed;
		String mname,mtype;
		String[] monMoveset = new String[4];
		String[] lines = new String[10];
		//read AND verify mon from txt file (located in C:\Users\USERNAME\ as customPokemon.txt)
		/* FORMAT:
		 * NAME <-- first line
		 * TYPE
		 * HP
		 * ATK
		 * DEF
		 * SPEED
		 * MOVESLOT1
		 * MOVESLOT2
		 * MOVESLOT3
		 * MOVESLOT4
		 */
		Scanner sc = null;
		try{
			sc = new Scanner(txt);//read and store first 10 lines in an array
			for(int i=0;i<10;i++){
				if(sc.hasNextLine()){
					lines[i]=sc.nextLine();
				}else{
					throw new NullPointerException("invalid");
				}
			}
			
			//checkering
			mname=lines[0];
			mtype=lines[1];
			mhp=Integer.parseInt(lines[2]);
			matk=Integer.parseInt(lines[3]);
			mdef=Integer.parseInt(lines[4]);
			mspeed=Integer.parseInt(lines[5]);
			monMoveset[0]=lines[6];
			monMoveset[1]=lines[7];
			monMoveset[2]=lines[8];
			monMoveset[3]=lines[9];

			//apply data
			customMon = new Pokemon("Custom");
			
			customMon.name=mname;
			customMon.type=mtype;
			customMon.setTypesWnR();
			customMon.baseHP=mhp;
			customMon.baseATK=matk;
			customMon.baseDEF=mdef;
			customMon.baseSPEED=mspeed;

			customMon.currentHP=customMon.baseHP;
			customMon.currentATK=customMon.baseATK;
			customMon.currentDEF=customMon.baseDEF;
			customMon.currentSPEED=customMon.baseSPEED;

			customMon.moveset[0][0]=monMoveset[0];
			customMon.moveset[0][1]=monMoveset[1];
			customMon.moveset[0][2]=monMoveset[2];
			customMon.moveset[0][3]=monMoveset[3];

			customMon.moveset[1][0]=customMon.defineMove(customMon.moveset[0][0]);
			customMon.moveset[1][1]=customMon.defineMove(customMon.moveset[0][1]);
			customMon.moveset[1][2]=customMon.defineMove(customMon.moveset[0][2]);
			customMon.moveset[1][3]=customMon.defineMove(customMon.moveset[0][3]);

			for(int i=0;i<4;i++){//check that the moves got defined correctly
				if(customMon.moveset[1][i]==""){
					throw new NullPointerException("invalid!");
				}
			}
			
			if(customMon.name.length()>12 || customMon.baseHP<1 || customMon.baseATK<0 
			|| customMon.baseDEF<0 || customMon.baseSPEED<0){
				throw new NullPointerException("nu uh!");
			}
			
			for(int i=0;i<customMon.weakTo.length;i++){
				if(customMon.weakTo[i].equals("") || customMon.weakTo[i]==null){
					throw new NullPointerException("failed to apply type weaknesses");
				}
			}
			
			for(int i=0;i<customMon.resists.length;i++){
				if(customMon.resists[i].equals("") || customMon.resists[i]==null){
					throw new NullPointerException("failed to apply type resistances");
				}
			}
			
		}catch(Exception e){
			if(sc!=null){
				sc.close();
			} 
			return null;
		}finally{
			if(sc!=null){
				sc.close();
			}
		}

		return customMon;
	}

	private static void printCustomMonSlots(boolean printNumbers){
		Pokemon savedMons[] = new Pokemon[]{null,null,null,null};
		String filenames[] = getFilePaths();
		for(int i=0;i<savedMons.length;i++){
			File txt = new File(filenames[i]);
			try{
				savedMons[i]=readMonFromTxt(txt);
			}catch(Exception e){
				savedMons[i]=null;
			}finally{
				if(txt!=null){
					txt=null;
				}
			}
		}

		String monNames[] = new String[filenames.length];
		for(int i=0;i<monNames.length;i++){
			if(savedMons[i]!=null){
				monNames[i]=savedMons[i].name;
			}else{
				monNames[i]="...";
			}
		}
		int flag=0;
		for(int i=0;i<monNames.length;i++){
			if(flag<2){
				if(printNumbers){
					System.out.print("["+(i+1)+"]"+monNames[i]);
				}else{
					System.out.print("[-]"+monNames[i]);
				}
				for(int j=0;j<12-monNames[i].length();j++){
					System.out.print(" ");
				}
				flag++;
			}else{
				System.out.println("");
				flag=0;
				i--;
			}
		}
		System.out.println("\n");
		savedMons=null;
	}

	private static String selectAMove(String typ,int k,String[] moveset)throws IOException, InterruptedException{
		String ret="";
		String[] moves=null;
		String input="";
		switch (typ) {
			case "normal":moves=getMoveTable("normal");break;
			case "fire":moves=getMoveTable("fire"); break;
			case "water":moves=getMoveTable("water"); break;
			case "grass":moves=getMoveTable("grass"); break;
			case "bug":moves=getMoveTable("bug"); break;
			case "electric":moves=getMoveTable("electric"); break;
			case "steel":moves=getMoveTable("steel"); break;
			case "ghost":moves=getMoveTable("ghost"); break;
			case "rock":moves=getMoveTable("rock"); break;
			case "ground":moves=getMoveTable("ground"); break;
			case "dark":moves=getMoveTable("dark"); break;
			case "fairy":moves=getMoveTable("fairy"); break;
			case "flying":moves=getMoveTable("flying"); break;
			case "fighting":moves=getMoveTable("fighting"); break;
			case "psychic":moves=getMoveTable("psychic"); break;
			case "dragon":moves=getMoveTable("dragon"); break;
			case "ice":moves=getMoveTable("ice"); break;
			case "poison":moves=getMoveTable("poison"); break;
			case "status":moves=getMoveTable("status"); break;
		}

		do{
			input="";
			clear();
			System.out.println(Clr.CYAN_B+"---[POKEMON MAKER 3000]---"+Clr.R);
			System.out.println(" MOVESET:");
			printSelectedMoves(moveset);
			System.out.println(" Select move for slot ["+(k+1)+"]:"+"\n");
			System.out.println("Type the name to select it "+Clr.RED_B+"(CASE SENSITIVE!)"+Clr.R);
			System.out.println("or type cancel to go back"+"\n");

			int coumter=0;
			for(int i=0;i<moves.length;i++){
				if(coumter<3){// <-- un corazoncito
					System.out.print(" "+moves[i]);
					for(int j=0;j<14-moves[i].length();j++){
						System.out.print(" ");
					}
					System.out.print("|");
					coumter++;
				}else{
					coumter=0;
					i--;
					System.out.println("");
				}
			}
			System.out.println("");

			input=tcl.nextLine();

			for(int i=0;i<moves.length;i++){
				if((moves[i].contains(input) && input.length()>3) || input.equals(moves[i])){
					input=moves[i];
					ret=input;
					break;
				}
			}

			if(input.equals("cancel")){
				ret=input;
			}

			if(ret==""){
				System.out.println("Invalid option, try again!");
				wair(s,2);
			}
			
		}while(ret=="");

		return ret;
	}

	private static void printSelectedMoves(String[] moveset){
		int coumter=0;
		for(int i=0;i<4;i++){
			if(coumter<2){
				System.out.print("["+(i+1)+"] ");
				if(moveset[i]==null || moveset[i]==""){
					System.out.print("...");
					System.out.print("             ");
				}else{
					System.out.print(moveset[i]);
					for(int j=0;j<16-(moveset[i].length());j++){
						System.out.print(" ");
					}
				}
				coumter++;
				if(coumter<2){
					System.out.print("| ");
				}
			}else{
				coumter=0;
				System.out.println("");
				i--;
			}
		}
		System.out.println("");
		System.out.println("");
	}

	public static String[] getSuperSecretMonList(){ //extremely secret ok? shhh
		String[] secretMonList = new String[]{"Celebi ex","Regieleki","ADP GX"};

		return secretMonList;
	}

}// pokimonmaker3000 class ends frfr

enum Clr{
	R("\033[0m"),//RESET
	BLACK("\033[0;30m"),    // BLACK
    RED("\033[0;31m"),      // RED
    GREEN("\033[0;32m"),    // GREEN
    YELLOW("\033[0;33m"),   // YELLOW
    BLUE("\033[0;34m"),     // BLUE
    MAGENTA("\033[0;35m"),  // MAGENTA
    CYAN("\033[0;36m"),     // CYAN
    WHITE("\033[0;37m"),    // WHITE
	
	//BRIGHT COLORS
	BLACK_B("\033[0;90m"),     // BLACK
    RED_B("\033[0;91m"),       // RED
    GREEN_B("\033[0;92m"),     // GREEN
    YELLOW_B("\033[0;93m"),    // YELLOW
    BLUE_B("\033[0;94m"),      // BLUE
    MAGENTA_B("\033[0;95m"),   // MAGENTA
    CYAN_B("\033[0;96m"),      // CYAN
    WHITE_B("\033[0;97m"),     // WHITE

	//BOLD + BRIGHT
	BLACK_BB("\033[1;90m"), // BLACK
	RED_BB("\033[1;91m"),   // RED
    GREEN_BB("\033[1;92m"), // GREEN
    YELLOW_BB("\033[1;93m"),// YELLOW
    BLUE_BB("\033[1;94m"),  // BLUE
    MAGENTA_BB("\033[1;95m"),// PURPLE
    CYAN_BB("\033[1;96m"),  // CYAN
    WHITE_BB("\033[1;97m"); // WHITE

	//ty stackoverflow https://stackoverflow.com/questions/5762491
	
	private final String code;

    Clr(String code) {
        this.code = code;
    }
    @Override
    public String toString() {
        return code;
    }
}