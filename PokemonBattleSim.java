import java.util.*;
import java.io.*;
import java.util.concurrent.*;

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

	public static void main(String[] args)throws IOException, InterruptedException{
		String selecshon="";
		boolean correctName=false;
		boolean errBypass=false; //this is here so the invalid msg can be skipped o_o
		int page=1,lastPage=3;
		final String[] pkmnNamesVector = new String[]{
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
		"Jolteon","Espeon","Eevee",          "Arceus","Citrus","Toxicroak"};
		
		do{
			clear();
			selecshon="";
			
			System.out.println(Clr.YELLOW_B+"[Pokemon Battle Sim beta4]"+Clr.R);
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
				String autoCap1=selecshon.charAt(0)+"";
				String autoCap2="";
				String autoCap3="";

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

				if(selecshon.equals("Mew")){// xd
					correctName=true;
				}else{
					if(selecshon.length()>3){
						for(int i=0; i<pkmnNamesVector.length;i++){
							if(pkmnNamesVector[i].contains(selecshon)){
								correctName=true;
								selecshon=pkmnNamesVector[i];
								break;
							} else{
								correctName=false;
							}
						}
					}else{
						correctName=false;
					}
				}
				

				if(selecshon.equals("Rng")){
					correctName=true;
				}
				if(selecshon.equals("Custom")){
					correctName=true;
				}if(selecshon.equals("Help") || selecshon.equals("6mon") || selecshon.equals("3mon")){
					errBypass=true;
				}if(selecshon.equals("Cpu")){
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
					playerMons = new Pokemon[6];
					cpuMons = new Pokemon[6];
					System.out.println("Team size changed to 6 Pokemon");
					wair(s,2);
				}if(selecshon.equals("3mon")){
					playerMons = new Pokemon[3];
					cpuMons = new Pokemon[3];
					System.out.println("Team size changed to 3 Pokemon");
					wair(s,2);
				}if(selecshon.equals("Cpu")){
					cpuTeamManager();
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
						tcl.nextLine();
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
		wair(s,1);
		System.out.println("Epic battle begins in:");
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
			
			playerMons[playerMonActive].extraDmg=rng.nextInt(7);
			cpuMons[cpuMonActive].extraDmg=rng.nextInt(7);
			
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
				int switching=0;
				System.out.println(cpuMons[cpuMonActive].name+" fainted!");
				wair(s,2);
				if(checkAllCPUMons()){
					do{
						switching=rng.nextInt(cpuMons.length);
					}while(checkSwitchIn(switching, cpuMonActive, cpuMons) || cpuMons[switching].currentHP==0);
					clear();
					printBattleHUDThing();
					System.out.println(cpuMons[cpuMonActive].name+" retreated!");
					wair(s,2);

					cpuMons[cpuMonActive].resetStats();

					cpuMonActive=switching;
				
					clear();
					printBattleHUDThing();
					System.out.println(cpuName+" sent out "+cpuMons[cpuMonActive].name+"!!");
					wair(s,2);
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
			wair(s,5);
		}
		System.out.println("");
		System.out.println("Press Enter to exit");
		wair(s,1);
		tcl.nextLine();
		tcl.nextLine();
	}

	private static void plyerTurn()throws IOException, InterruptedException{
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
			wair(s,1);
			clear();
			cpuMons[cpuMonActive].currentHP-=getSmackedBich;//applies dmg
			printBattleHUDThing();
			System.out.println(playerMons[playerMonActive].name+" used "+playerMons[playerMonActive].moveset[0][moveSelec]+"!");
			wair(s,1);
			
			switch (isMoveEffective(moveSelec, playerMons[playerMonActive], cpuMons[cpuMonActive])){
				case 0:
					//neutral ouo
				break;
				case 1:
					System.out.println("It's super effective!!");
					wair(s,1);
				break;
				case 2:
					System.out.println("It's not very effective...");
					wair(s,1);
				break;
			}
			
			if(crit){
				System.out.println(Clr.RED_B+"Critical Hit!!"+Clr.R);
				crit=false;
				wair(s,1);
			}
			
			//print dmg dealt
			int numbHits=playerMons[playerMonActive].numberOfHits;
			if(playerMons[playerMonActive].isSpecialMove(moveSelec)=="rngMultihit"){
				numbHits+=playerMons[playerMonActive].extraDmg;
			}if(playerMons[playerMonActive].isSpecialMove(moveSelec)=="plus2hit"){
				numbHits+=2;
			}if(playerMons[playerMonActive].moveset[0][moveSelec].equals("X")){
				numbHits+=1;
			}if(playerMons[playerMonActive].isSpecialMove(moveSelec)=="plus3hit"){
				numbHits+=3;
			}if(playerMons[playerMonActive].isSpecialMove(moveSelec)=="groupB"){
				numbHits+=countAliveMonInTeam(playerMons);
				numbHits--;
			}if(playerMons[playerMonActive].isSpecialMove(moveSelec)=="reverseGroupB"){
				numbHits+=countAliveMonInTeam(cpuMons);
				numbHits--;
				if(cpuMons[cpuMonActive].currentHP==0){
					numbHits++;
				}
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
						System.out.print(" ("+(dmgToPrint*(i+1))+")!");
					}
					System.out.println();
				}
				wair(m,80000);
			}
			wair(s,1);

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
				System.out.println("| "+monHP);
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
		System.out.println(playerMons[playerMonActive].name+" retreated!");
		wair(s,2);

		playerMons[playerMonActive].resetStats();

		playerMonActive=switchin;
		if(playerMons[playerMonActive].permaBurn){
			playerMons[playerMonActive].isBurning=true;
		}
		
		clear();
		printBattleHUDThing();
		System.out.println("Go! "+playerMons[playerMonActive].name+"!!");
		wair(s,2);
	}

	private static void cpuTurn()throws IOException, InterruptedException{
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
			wair(s,1);
			clear();
			playerMons[playerMonActive].currentHP-=getSmackedBich;//applies dmg
			printBattleHUDThing();
			System.out.println(cpuMons[cpuMonActive].name+" used "+cpuMons[cpuMonActive].moveset[0][cpuMoveSelec]+"!");
			wair(s,1);
			
			switch(isMoveEffective(cpuMoveSelec, cpuMons[cpuMonActive],playerMons[playerMonActive])){
				case 0:
					//neutral ouo
				break;
				case 1:
					System.out.println("It's super effective!!");
					wair(s,1);
				break;
				case 2:
					System.out.println("It's not very effective...");
					wair(s,1);
				break;
			}
			
			if(crit){
				System.out.println(Clr.RED_B+"Critical Hit!!"+Clr.R);
				crit=false;
				wair(s,1);
			}
			
			//print dmg dealt
			int numbHits=cpuMons[cpuMonActive].numberOfHits;
			if(cpuMons[cpuMonActive].isSpecialMove(cpuMoveSelec)=="rngMultihit"){
				numbHits+=cpuMons[cpuMonActive].extraDmg;
			}if(cpuMons[cpuMonActive].isSpecialMove(cpuMoveSelec)=="plus2hit"){
				numbHits+=2;
			}if(cpuMons[cpuMonActive].moveset[0][cpuMoveSelec].equals("X")){
				numbHits+=1;
			}if(cpuMons[cpuMonActive].isSpecialMove(cpuMoveSelec)=="plus3hit"){
				numbHits+=3;
			}if(cpuMons[cpuMonActive].isSpecialMove(cpuMoveSelec)=="groupB"){
				numbHits+=countAliveMonInTeam(cpuMons);
				numbHits--;
			}if(cpuMons[cpuMonActive].isSpecialMove(cpuMoveSelec)=="reverseGroupB"){
				numbHits+=countAliveMonInTeam(playerMons);
				numbHits--;
				if(playerMons[playerMonActive].currentHP==0){
					numbHits++;
				}
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
						System.out.print(" ("+(dmgToPrint*(i+1))+")!");
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
		System.out.println(cpuMons[cpuMonActive].name+" retreated!");
		wair(s,2);

		cpuMons[cpuMonActive].resetStats();

		cpuMonActive=switching;
		
		if(cpuMons[cpuMonActive].permaBurn){
			cpuMons[cpuMonActive].isBurning=true;
		}
	
		clear();
		printBattleHUDThing();
		System.out.println(cpuName+" sent out "+cpuMons[cpuMonActive].name+"!!");
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
		if(playerMons[playerMonActive].moveset[0][moveSelec].equals("Quick Attack")
		|| cpuMons[cpuMonActive].moveset[0][cpuMoveSelec].equals("Quick Attack")){
			//iff if iff iff ififff if if
			//yanderedev would be proud
			if(playerMons[playerMonActive].moveset[0][moveSelec].equals("Quick Attack")
			&& (cpuMons[cpuMonActive].moveset[0][cpuMoveSelec].equals("Quick Attack"))==false){
				playerfirst=true;
			}else{
				if(cpuMons[cpuMonActive].moveset[0][cpuMoveSelec].equals("Quick Attack")
				&& (playerMons[playerMonActive].moveset[0][moveSelec].equals("Quick Attack"))==false){
					playerfirst=false;
				}
			}
			// if both use it xd
			if(playerMons[playerMonActive].moveset[0][moveSelec].equals("Quick Attack")
			&& cpuMons[cpuMonActive].moveset[0][cpuMoveSelec].equals("Quick Attack")){
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
		//def range from 0 to 270, 300=100% reduction, 270=90% reduction...
		
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
				atk1-=atk1/4;
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
		}

		if(movename.equals("Flower Trick")){
			//too op
			atk1-=atk1/5;
		}if(movename.equals("Facade") && pkmn1.hasStatusAilment()){
			atk1*=2; // DOUBLE ATK WOOOOO
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
		boolean shoulduse1=true,shoulduse2=true,shoulduse3=true,shoulduse4=true;
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
						cpuJustSwitched=true;
						return 69; //30% chance to switch
					}
				}
			}
		}
		
		//use item?
		if(cpuMons[cpuMonActive].items.length>1){
			//mega evolve?
			if(cpuMons[cpuMonActive].canMegaEvolve() && cpuMon.currentHP>(cpuMon.baseHP/2) && cpuCanMegaEvolve){
				if(rng.nextInt(100)>59){
					return 420; //megaevolve cpu
				}
			}

			int selecItem=rng.nextInt(5);
			switch(selecItem){
				case 0:
				if(cpuMon.currentHP<=(cpuMon.baseHP/2)){
					if(rng.nextInt(100)>40){
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
			}
		}

		if(rand!=69){
			//didnt decide to switch mon
			//look for super effective attacking move
			for(int i=0;i<4;i++){
				for(int j=0;j<monsWeaknesses.length;j++){
					if(cpuMon.moveset[1][i].contains(monsWeaknesses[j])){
						switch(i){
							case 0: shoulduse1=true; break;
							case 1: shoulduse2=true; break;
							case 2: shoulduse3=true; break;
							case 3: shoulduse4=true; break;
						}
					}
				}
			}
	
			//look for not very effective at all attacking move
			for(int i=0;i<4;i++){
				for(int j=0;j<monsResistances.length;j++){
					if(cpuMon.moveset[1][i].contains(monsResistances[j])){
						switch(i){
							case 0: shoulduse1=false; break;
							case 1: shoulduse2=false; break;
							case 2: shoulduse3=false; break;
							case 3: shoulduse4=false; break;
						}
					}
				}
			}
			//count nonUsables xd
			int nonUsables=0;
			if(!shoulduse1){
				nonUsables++;
			}if(!shoulduse2){
				nonUsables++;
			}if(!shoulduse3){
				nonUsables++;
			}if(!shoulduse4){
				nonUsables++;
			}
	
			if(nonUsables==4){
				//YOLO
				rand=rng.nextInt(4);
				if(cpuMons[cpuMonActive].moveset[0][rand].equals(prevCpuMove)){
					rand=rng.nextInt(4);
				}
			}else{
				if(cpuMon.countAttackingMoves()==nonUsables){ //all attacking moves are not effective
					shoulduse1=true; shoulduse2=true; shoulduse3=true; shoulduse4=true;
				}
				do{
					rand=rng.nextInt(4);
					if(cpuMons[cpuMonActive].moveset[0][rand].equals(prevCpuMove)){
						rand=rng.nextInt(4); //roll again ouo
					}
				}while(!epicCpuAiRandomMoveCheckerThing(shoulduse1, shoulduse2, shoulduse3, shoulduse4, rand));
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
	
	private static boolean epicCpuAiRandomMoveCheckerThing(boolean nu1,boolean nu2,boolean nu3,boolean nu4, int num){//long ahh parameter list
		boolean ret=false;
		if(num==0 && nu1==true){
			ret=true;
		}if(num==1 && nu2==true){
			ret=true;
		}if(num==2 && nu3==true){
			ret=true;
		}if(num==3 && nu4==true){
			ret=true;
		}

		if(!moveIsAnAttack(cpuMons[cpuMonActive].moveset[1][num])){
			ret=true;
		}
		//i forgot how this one works
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
			case "Sword Dance": ret="buffatk2"; break;
			case "Work Up": ret="buffatk2"; break;
			case "Charge": ret="buffatk2"; break;
			case "Hone Claws" : ret="buffatk"; break;
			case "Roar": ret="debuffdef"; break;
			case "Fake Tears": ret="debuffdef2"; break;
			case "Dragon Dance": ret="buffatk&speed"; break;
			case "Growl": ret="debuffatk"; break;
			case "Charm": ret="debuffatk2"; break;
			case "Agility": ret="buffspeed2"; break;
			case "Scary Face": ret="debuffspeed2"; break;
			case "Bulk Up": ret="buffatk&def"; break;
			case "Iron Defense": ret="buffdef"; break;
			case "Amnesia": ret="buffdef2"; break;
			case "Defend Order": ret="buffdef2"; break;
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
		}
	}

	private static void statusAilmentsHandler()throws IOException, InterruptedException{
		//this is for poisoned and burning statuses
		//and healing over time!
		// array : 0=burn, 1=poison, 2=paralysis
		// prevent the ailments from getting yeeted on the same turn
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

		//------try to free from poison/burn/paralysis------//
		// player
		if(playerMons[playerMonActive].isBurning && plyCanFreeFromAilment[0]){
			if(rng.nextInt(10)>7){
				playerMons[playerMonActive].isBurning=false;
				System.out.println(playerMons[playerMonActive].name+" freed from burn!");
				wair(s, 2);
			}
		}if(playerMons[playerMonActive].isPoisoned && plyCanFreeFromAilment[1]){
			if(rng.nextInt(10)>7){
				playerMons[playerMonActive].isPoisoned=false;
				System.out.println(playerMons[playerMonActive].name+" cured itself from poison!");
				wair(s, 2);
			}
		}if(playerMons[playerMonActive].isParalized && plyCanFreeFromAilment[2]){
			if(rng.nextInt(10)>6){
				playerMons[playerMonActive].isParalized=false;
				System.out.println(playerMons[playerMonActive].name+" freed from paralysis!");
				wair(s, 2);
			}
		}
		// cpu
		if(cpuMons[cpuMonActive].isBurning && cpuCanFreeFromAilment[0]){
			if(rng.nextInt(10)>7){
				cpuMons[cpuMonActive].isBurning=false;
				System.out.println(cpuMons[cpuMonActive].name+" freed from burn!");
				wair(s, 2);
			}
		}if(cpuMons[cpuMonActive].isPoisoned && cpuCanFreeFromAilment[1]){
			if(rng.nextInt(10)>7){
				cpuMons[cpuMonActive].isPoisoned=false;
				System.out.println(cpuMons[cpuMonActive].name+" cured itself from poison!");
				wair(s, 2);
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
				System.out.println(playerMons[playerMonActive].name+ " will only deal "+Clr.RED_B+"Critical hits!"+Clr.R);
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
		}
	}

	private static void cpuTeamManager()throws IOException, InterruptedException{
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
						cpuTeamManagerAssignPokemon();
						return;
					}
				}catch(Exception e){
					op=0;
					tcl.nextLine();
				}
			}while(true);
		}
	}
	
	private static void cpuTeamManagerAssignPokemon()throws IOException, InterruptedException{
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
				tcl.nextLine();
				Pokemon custm=PokemonMaker3000.makeCustomMon();
				if(custm!=null){
					saveMonInCPUTeam2(custm);
				}
			}
			
		}while(true);
	}

	private static void saveMonInCPUTeam2(Pokemon mon){
		for(int i=0;i<cpuMons.length;i++){
			if(cpuMons[i]==null){
				cpuMons[i]=mon;
				break;
			}
		}
	}

	//-------------PRINT METHODS-----------//
	
	private static void printPkmnNamesPage(String[] namesVector, int page, int lastPage){
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
			to=namesVector.length-1; //MODIFY!!
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

		System.out.println("                 "+arrLeft+" Page "+page+" "+arrRight);
		for(int i=from; i<=to;i++){
			if(coumter<3){
				System.out.print(" "+namesVector[i]);
				for(int j=0;j<=12-(namesVector[i].length());j++){
					System.out.print(" ");
				}
				System.out.print("|");
				coumter++;
			} else{
				coumter=0;
				System.out.println("");
				i--;
			}
		}
		System.out.println("");
	}

	private static void printHelpMMScreen()throws IOException, InterruptedException{
		clear();
		System.out.println(Clr.YELLOW_B+"[Pokemon Battle Sim beta4]"+Clr.R);
		System.out.println("");
		System.out.println("Totally super cool commands for the Main Menu:");
		System.out.println("");
		System.out.println("CUSTOM: allows you to create or manage a\n customized Pokemon. it can be saved to a txt file.\n");
		System.out.println("RNG: fills empty slots in your team with randomly\n selected Pokemon. then starts the battle.\n");
		System.out.println("<Number>: view selected page of Pokemon.\n you can select any Pokemon while vieweing any page.\n");
		System.out.println("<Pokemon Name>: select a Pokemon.\n tip: you can just type the first 4 letters.\n");
		System.out.println("6mon: Changes the Pokemon Team size to 6 Pokemon. \n");
		System.out.println("3mon: Changes the Pokemon Team size to 3 Pokemon. \n");
		System.out.println("HELP: brings up this very cool looking screen.");

		System.out.println("");
		System.out.println("Press Enter to go back");
		tcl.nextLine();
	}

	private static void printPlayerActivePkmnMoveset(boolean plyWillMegaEvolve){
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
				System.out.print("["+(i+1)+"] "+mon.moveset[0][i]);
				for(int j=0;j<20-(mon.moveset[0][i].length());j++){
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
		System.out.println("");
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
		do{
			try{
				input=tcl.nextInt();
			}catch(Exception e){
				input=69;
				tcl.nextLine();
			}
		}while(input!=1 && input!=2);
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

	private static void printBattleHUDThing(){
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

		//i hate this
		//there's 50 spaces frfrfrfrfr <-- there's actually 48
		System.out.println(al1+alSpaces+al2);
		System.out.print(Clr.WHITE_B+"Your Pokemon:");
		for(int i=0;i<35-cpuName.length()-11;i++){
			spaces+=" ";
		}
		System.out.print(spaces); spaces="";
		System.out.println(cpuName+"'s Pokemon:"+Clr.R);
		System.out.print(" "+pk1Name);
		for(int i=0;i<46-pk2Name.length()-(pk1Name.length());i++){
			spaces+=" ";
		}
		System.out.print(spaces); spaces="";

		System.out.println(pk2Name);

		System.out.print(" HP: "+pk1HP);
		for(int i=0;i<38-(pk1HP+"").length()-((pk2HP+"").length());i++){
			spaces+=" ";
		}
		System.out.print(spaces); spaces="";

		System.out.println("HP: "+pk2HP);

		System.out.print(pk1Conditions);
		for(int i=0;i<47-(cond1Length)-(cond2Length);i++){
			spaces+=" ";
		}
		System.out.print(spaces); spaces="";
		System.out.println(pk2Conditions);
		System.out.println("");

	  //it's gotta look like this
	  //                    <----------------------48---------------------->
	  //System.out.println("Your Pokemon:                     CPU's Pokemon:");
	  //System.out.println(" name1                                    name2 ");
	  //System.out.println(" HP:69                                    HP:69 ");
	  //System.out.println(" [PAR][PSN][BRN]			    [PAR][PSN][BRN] ");
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
			"Cocuy","Socks","Bacon","Tocino","Arepa"
		};

		return names[rng.nextInt(names.length)];
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
	boolean isPoisoned,isBurning,isParalized,healingOverTime,megaEvolved,strike,permaBurn;
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
			break;
		}
	}

	protected void healSelf(String amount){
		switch(amount){
			case "full":
			this.currentHP=this.baseHP;
			break;
			case "half":
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
			"Absol","Lopunny","Venusaur","Charizard","Blastoise","Ninetales","Mewtwo",
			"Aggron","Blaziken","Gengar","Lucario", "Cinccino", "Audino","Alakazam","Pidgeot", "Heracross",
			"Gardevoir","Mawile","Sceptile","Eevee","Citrus","Gyarados",
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
				addHP=10;
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
				addAtk=45;
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
			case "Eevee":
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
		
		if(movename.equals("Draining Kiss") || movename.equals("Life Leech")
			|| movename.equals("Poison Leech") || movename.equals("Giga Drain")
			|| movename.equals("Drain Punch") || movename.equals("Excite")){
			return "lifedrain";
		}if(movename.equals("Flamethrower") || movename.equals("Scald")
			|| movename.equals("Fire Blast")){
			return "rngBurn";
		}if(movename.equals("Mud Slap") || movename.equals("Electroweb")){
			return "rngDebuffSpeed";
		}if(movename.equals("Crunch")|| movename.equals("Earth Power")|| movename.equals("Hammer Arm")
			|| movename.equals("Shadow Ball") || movename.equals("Bug Buzz")|| movename.equals("Energy Ball")
			|| movename.equals("Focus Blast") || movename.equals("Psychic")){
			return "rngDebuffDef";
		}if(movename.equals("Play Rough")){
			return "rngDebuffAtk";
		}if(movename.equals("Flame Charge") || movename.equals("Dragon Rush")
		|| movename.equals("Trailblaze")){
			return "buffspeed";
		}if(movename.equals("Double Kick")||movename.equals("Dual Wingbeat")){
			return "doublehit";
		}if(movename.equals("Toxic Spikes")|| movename.equals("Poison Sting")){
			return "rngPoison";
		}if(movename.equals("Night Slash")||movename.equals("Attack Order")||movename.equals("Leaf Blade")
			|| movename.equals("Psycho Cut")){
			return "highcritrate";
		}if(movename.equals("Overheat") || movename.equals("Superpower")
			|| movename.equals("Draco Meteor")|| movename.equals("Solar Beam")){
			return "overclock"; //idk what to name these
		}if(movename.equals("Super Fang")){
			return "ignoredef";
		}if(movename.equals("Hyper Beam")||movename.equals("Giga Impact")
			|| movename.equals("Plasma Fists")){
			return "powerboost";//+1/2 atk boost
		}if(movename.equals("Mystical Fire")){
			return "debuffatk";
		}if(movename.equals("Psyshock") || movename.equals("Body Slam")){
			return "defisatk"; //use enemy def as atk
		}if(movename.equals("Brave Bird")){
			return "recoil";
		}if(movename.equals("Pin Missile") || movename.equals("Water Shuriken") || movename.equals("Swift")
			|| movename.equals("Powerful Bloom")){
			return "rngMultihit";
		}if(movename.equals("Judgement")){
			return "supEffective"; //soup
		}if(movename.equals("Ice Slash")){
			return "plus2hit";
		}if(movename.equals("Halo")){
			return "plus3hit";
		}if(movename.equals("X") || movename.equals("Ascension")){
			return "adversity";
		}if(movename.equals("Group Beating")){
			return "groupB";
		}if(movename.equals("Cyclone")){
			return "reverseGroupB";
		}if(movename.equals("Revenge")){
			return "avenger";
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
				baseHP=270;
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
				moveset = new String[][]{{"Earthquake","Sucker Punch","Hyper Beam","Sword Dance"},{"","","",""}};
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
				moveset = new String[][]{{"Close Combat","Aerial Ace","Iron Head","Bulk Up"},{"","","",""}};
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
				baseATK=100;
				baseDEF=80;
				baseSPEED=85;
				type="Fairy";
				moveset = new String[][]{{"Play Rough","Iron Hammer","Dream Eater","Sword Dance"},{"","","",""}};
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
				baseATK=80;
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
				baseATK=75;
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
				baseATK=150;
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
		permaBurn=false;
		moveset[1][0]=defineMove(moveset[0][0]);
		moveset[1][1]=defineMove(moveset[0][1]);
		moveset[1][2]=defineMove(moveset[0][2]);
		moveset[1][3]=defineMove(moveset[0][3]);

		items= new String[]{"Potion","X-Attack","X-Defense","X-Speed","","Dash Earring","Strike Earrings"};
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
		"Aqua Ring","Impulse","Jungle Healing","Fake Tears","Scary Face","Agility","Defend Order","Work Up","Thunder Wave","Last Resort"};
		String[] moveTableAtkNormal = new String[]{"Quick Attack","Hyper Beam","Giga Impact","Super Fang","Facade","Swift","Judgement","Ascension","Group Beating"};
		String[] moveTableAtkFire = new String[]{"Flamethrower","Flame Charge","Overheat","Fire Blast","Mystical Fire"};
		String[] moveTableAtkWater = new String[]{"Hydro Pump","Hydro Cannon","Surf","Whirlpool","Scald","Water Shuriken"};
		String[] moveTableAtkElectric = new String[]{"Thunder","Thunder Fang","Electroweb","Overdrive","Plasma Fists"};
		String[] moveTableAtkGrass = new String[]{"Vine Whip","Giga Drain","Flower Trick","Trailblaze","Razor Leaf","Grass Knot","Wood Hammer","Leaf Blade","Solar Beam","Energy Ball","Powerful Bloom"};
		String[] moveTableAtkIce = new String[]{"Ice Beam","Ice Fang","Freeze Dry","Blizzard","Ice Slash"};
		String[] moveTableAtkFighting = new String[]{"Aura Sphere","Close Combat","Rock Smash","Body Slam","Double Kick","Hammer Arm","Drain Punch","HJ Kick","Superpower","Flying Press","Focus Blast","Body Press"};
		String[] moveTableAtkPoison = new String[]{"Poison Leech","Toxic Spikes","Venoshock","Poison Sting"};
		String[] moveTableAtkGround = new String[]{"Earthquake","Mud Slap","Earth Power","X","Excite"};
		String[] moveTableAtkFlying = new String[]{"Wing Attack","Gust","Aerial Ace","Dual Wingbeat","Air Slash","Brave Bird","Cyclone"};
		String[] moveTableAtkPsychic = new String[]{"Psystrike","Psychic","Dream Eater","Psybeam","Psycho Cut","Psyshock"};
		String[] moveTableAtkBug = new String[]{"Bug Bite","Life Leech","Bug Buzz","Attack Order","Pin Missile","X-Scissor"};
		String[] moveTableAtkRock = new String[]{"Rock Throw","Head Smash","Stone Edge"};
		String[] moveTableAtkGhost = new String[]{"Shadow Ball","Hex","Shadow Sneak"};
		String[] moveTableAtkDragon = new String[]{"Dragon Breath","Dragon Rush","Dragon Pulse","Dragon Tail","Draco Meteor"};
		String[] moveTableAtkDark = new String[]{"Pursuit","Bite","Sucker Punch","Crunch","Night Slash"};
		String[] moveTableAtkSteel = new String[]{"Metal Claw","Iron Tail","Iron Head","Flash Cannon","Iron Hammer","Bullet Punch","Steel Wing"};
		String[] moveTableAtkFairy = new String[]{"Moonblast","Play Rough","Draining Kiss","Halo"};

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
			System.out.println("Type the name to select it (CASE SENSITIVE!)");
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
				if(input.equals(moves[i])){
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
    WHITE_B("\033[0;97m");     // WHITE
	//ty stackoverflow for the ANSI codes :3
	
	private final String code;

    Clr(String code) {
        this.code = code;
    }
    @Override
    public String toString() {
        return code;
    }
}