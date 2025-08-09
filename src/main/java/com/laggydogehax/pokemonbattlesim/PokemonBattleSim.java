package com.laggydogehax.pokemonbattlesim;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;

// @author LaggyDogeHax :P

public class PokemonBattleSim{
	static final String OsName = System.getProperty("os.name");
	static final String version = "beta5 dev9 part 1: the db update";
	static final char s='s', m='m';
	
	static boolean battleAnimations = true;
	
	static Pokemon[] playerMons = new Pokemon[3];
	static Pokemon[] cpuMons = new Pokemon[3];
	//cpu >> ai  there's NO intelligence to be found here

	static int playerMonActive = 0;
	static int cpuMonActive = 0;
	
	static Random rng = new Random();
	static Scanner tcl = new Scanner(System.in); //STATIC SCANNER, LES GOOOO
	static String cpuName = getNewCPUName(); // random cpu name
	
	static String[] typeList = PokemonMaker3000.getTypesVector();
	
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

	static private void setUpConfigs()throws IOException, InterruptedException{
		
		BufferedWriter cout = new BufferedWriter(new OutputStreamWriter(System.out));
		
		clear();
		System.out.println(Clr.YELLOW_BB+"[Pokemon Battle Sim "+version+"]"+Clr.R);
		cout.write("                                      :~~~~     \n");
		cout.write("^^^^:                               :^~~~~~     \n");
		cout.write("!!~~~::                             ~~~~~!!     \n");
		cout.write("..^!~~~J5~                       .5Y~~~!~..     \n");
		cout.write("  .:^PP@@#!777.    ^777777777777!G@@PP!..       \n");
		cout.write("    .##@@@@@@@5:^^^&@@@@@@@@@@@@@@@@@@7         \n");
		cout.write("       G&&&&@@@@@@@&&@@@@@@@@@@@@@@@&&7..       \n");
		cout.write("           .@@@@@@@  1@@@@@@@@@@@@@@  P@#       \n");
		cout.write("         ~@@@@@@@@@@&@@@@@@@@//@@@@@@&@@@@@.    \n");
		cout.write("         7@@@@B    @@@@@  B@&  &@B  @@B   :.    \n");
		cout.write("GGGGGGGGGJ^!@@5    &@@@@GP   @@   @@@@5   ~     \n");
		cout.write("@@@@@@@@@&??JJPBBBB@@@@@@@@@@@@@@@@@@@&B5.:     \n");
		cout.write("GP&@@@@@@@@@  !BGGGPP#@@@@@@@@@@@&PPGGGBY       \n");
		cout.write("  ?&&@@@@@@@^:     ..J@@@@@@@@@@@G..            \n");
		cout.write("    .&&@@@@@@@!    B@@@@@@@@@@@@@@@@            \n");
		cout.write("       &@@@@.    ~~!!G@@@@@@@@@@@@@@.           \n");
		cout.write("    .&&@@G..    .@@@@@@@@@@@@@@@@@@@&&&&G       \n");
		cout.write("     ^^?Y7::  .:^JJ@@@@@@@@@@@@@@@@@@@@@&       \n");
		cout.write("        ....::Y#BBB@@@@@@@@@@@@@@@@@@@G!~       \n");
		cout.write("            ..G@@@@@@@@@@@@@@@@@@@@@@@!         \n");
		cout.write("            ^^B@@@@@@@@@@@@@@@@@@@@@@@5^:       \n");
		cout.write("          .^@@@@@@@&&&&&&&&&&&&@@@@@@@@@&       \n");
		cout.write("         !@@@@@@@@@:           #@@@@@@@@#       \n");
		cout.flush();
		
		System.out.println("Creating files if necessary...");
        PokemonDB pdb = new PokemonDB();
		pdb.forceReplaceSavedDB();
		System.out.println("Loading configuration file...");
        PBSFileReader fr = new PBSFileReader();
		System.out.println("Located at: "+fr.getSaveFilePath()+"\n");
		
		wair(m,500000);
                
		if(fr.noErrors){
			int[] list = fr.configList;
			
			playerMons = new Pokemon[list[0]];
			cpuMons = new Pokemon[list[0]];
			
			if(list[1]==1){
				battleAnimations=true;
			}else{
				battleAnimations=false;
			}
			
			System.out.println("File loaded successfully!!");
			System.out.println("Starting up in 3 secs...");
			wair(s,3);
			
		}else{
			System.out.println("An error ocurred! check your permissions");
			System.out.println("Starting up normally in 3 secs...");
			wair(s,3);
		}
	}

	public static void main(String[] args)throws IOException, InterruptedException{
		String selecshon="";
		boolean correctName=false;
		boolean errBypass=false; //this is here so the invalid msg can be skipped o_o
		int page=1,lastPage=3;
		String[] pkmnNamesVector = getPkmnNamesVector();
		
		setUpConfigs();
		
		do{
			clear();
			selecshon="";
			
			System.out.println(Clr.YELLOW_BB+"[Pokemon Battle Sim "+version+"]"+Clr.R);
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
					|| selecshon.equals("Cpu") || selecshon.equals("Reset") || selecshon.equals("Anims Off")
					|| selecshon.equals("Anims On")){
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
			}
			
			if(!correctName && errBypass){
				
				switch(selecshon){ // commands!
					case "Help":
						printHelpMMScreen();
					break;
					case "6mon":{
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
						
						PBSFileReader fr = new PBSFileReader();
						int anims=1;
						if(!battleAnimations){anims=0;}
						fr.saveSettingsToFile(6,anims);
						
						System.out.println("Team size changed to 6 Pokemon");
						wair(s,2);
					}
					break;
					case "3mon":{
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
						
						PBSFileReader fr = new PBSFileReader();
						int anims=1;
						if(!battleAnimations){anims=0;}
						fr.saveSettingsToFile(3,anims);
						
						System.out.println("Team size changed to 3 Pokemon");
						wair(s,2);
					}
					break;
					case "Cpu":
						cpuTeamManager(lastPage);
					break;
					case "Reset":
						playerMons= new Pokemon[playerMons.length];
						System.out.println("Player Team has been reset.");
						wair(s,2);
					break;
					case "Anims On":{
						battleAnimations=true;
						
						PBSFileReader fr = new PBSFileReader();
						fr.saveSettingsToFile(playerMons.length,1);
						
						System.out.println("Battle Animations are now ON");
						wair(s,2);
					}
					break;
					case "Anims Off":{
						battleAnimations=false;
						
						PBSFileReader fr = new PBSFileReader();
						fr.saveSettingsToFile(playerMons.length,0);
						
						System.out.println("Battle Animations are now OFF");
						wair(s,2);
					}
					break;
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
		System.out.println(Clr.YELLOW_B+"                     V.S."+Clr.R);
		printCPUTeam();
		System.out.println("");
		wair(s,1);
		System.out.println(Clr.RED_B+"            EPIC BATTLE BEGINS IN:"+Clr.R);
		wair(s,1);
		System.out.println("                    3...");
		wair(s,1);
		System.out.println("                      2...");
		wair(s,1);
		System.out.println("                        1..!");
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

	static int moveSelec = 0;
	static int moveSelec2 = 0;
	static int battleMenuSelec = 0;  //<-- quite the important variables if i say so myself
	static int cpuMoveSelec = 0;
	static boolean cpuJustSwitched = false; //<--prevent cpu from switching twice in a row
	static boolean doublehitPlayer = false;
	static boolean doublehitCpu = false;
	static boolean plyCanMegaEvolve = true; //can only mega-evolve once per battle
	static boolean cpuCanMegaEvolve = true;
	static boolean[] plyCanFreeFromAilment = new boolean[]{true, true, true};
	static boolean[] cpuCanFreeFromAilment = new boolean[]{true, true, true};
	//------misc variables for funsies---//
	static int numbahOfTurns = 0;
	static int highestDamage = 0;
	static int totalPlayerDamage = 0;
	static int totalCPUDamage = 0;
	static String highestDamageName = "";

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
			pokemonAbiliyHandler(1,false);
			pokemonAbiliyHandler(2,false);

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

	private static void pokemonBattleSequence(int turnOf)throws IOException, InterruptedException{
		Pokemon cloneMon;
		Pokemon enemyMon;
		
		int selectedMove;
		
		pokemonAbiliyHandler(turnOf,true);
		
		if(turnOf==1){
			cloneMon = playerMons[playerMonActive];
			enemyMon = cpuMons[cpuMonActive];
			selectedMove=moveSelec;
		}else{
			cloneMon = cpuMons[cpuMonActive];
			enemyMon = playerMons[playerMonActive];
			selectedMove=cpuMoveSelec;
		}
		
		cloneMon.extraDmg=rng.nextInt(7);
		
		if(cloneMon.moveIsAnAttack(selectedMove)){
		
			int trueDmg=damageCalc(cloneMon, enemyMon, selectedMove,0);
			int getSmackedBich=trueDmg;
			int effectiveness=0;
			boolean crit=false, shakeScreen=false;
			
			switch (getMoveEffectiveness(selectedMove, cloneMon, enemyMon)){
				case 1:
					effectiveness=1;
				break;
				case 11:
					shakeScreen=true;
					effectiveness=11;
				break;
				case 2:
					effectiveness=2;
				break;
				case 22:
					effectiveness=22;
				break;
			}
			
			if(rollForCrit(cloneMon, selectedMove, enemyMon)){
				crit=true;
				trueDmg*=2;
				getSmackedBich=trueDmg;
				saveHighestDmg(cloneMon.name, trueDmg);
				shakeScreen=true;
			}
			
			if(turnOf==1){
				totalPlayerDamage+=trueDmg;
			}else{
				totalCPUDamage+=trueDmg;
			}
			
			if(getSmackedBich>enemyMon.currentHP){
				getSmackedBich=enemyMon.currentHP;
			}
			
			//start printing... now!
			clear();
			printBattleHUDThing();
			System.out.println(cloneMon.name+" used "+cloneMon.moveset[0][selectedMove]+"!");
			if(cloneMon.isSpecialMove(selectedMove).equals("magnitude")){
				wair(m,750000);
				System.out.println("Magnitude "+(cloneMon.extraDmg+4)+"!");
			}
			wair(s,1);
			clear();
			enemyMon.currentHP-=getSmackedBich;//applies dmg
			
			// auhgfjdkgkdfd
			if(turnOf==1){
				playerMons[playerMonActive] = cloneMon;
				cpuMons[cpuMonActive] = enemyMon;
			}else{
				cpuMons[cpuMonActive] = cloneMon;
				playerMons[playerMonActive] = enemyMon;
			}
			
			//animation!!!
			Clr color2 = getColorFromType(cloneMon,selectedMove);
			Clr color1 = getBrightColorFromType(cloneMon,selectedMove);
			int colorName;
			if(turnOf==1){ colorName=2; }else{ colorName=1; }
			
			if(battleAnimations){
				printBattleHUDSequence(colorName, color1, color2, shakeScreen, cloneMon.name+" used "+cloneMon.moveset[0][selectedMove]+"!"); //animation!!
				System.out.println(cloneMon.name+" used "+cloneMon.moveset[0][selectedMove]+"!");
			}else{
				printBattleHUDThing(0,Clr.R,cloneMon.name+" used "+cloneMon.moveset[0][selectedMove]+"!");
				wair(m,500000);
			}
			
			if(cloneMon.isSpecialMove(selectedMove).equals("magnitude")){
				System.out.println("Magnitude "+(cloneMon.extraDmg+4)+"!");
			}
			wair(m,500000);
			
			switch (effectiveness){
				case 1:
					System.out.println("It's super effective!!");
					wair(m,750000);
				break;
				case 11:
					System.out.println(Clr.CYAN_B+"It's super effective!!"+Clr.R);
					wair(m,750000);
				break;
				case 2:
					System.out.println("It's not very effective...");
					wair(m,750000);
				break;
				case 22:
					System.out.println(Clr.YELLOW_B+"It's not very effective..."+Clr.R);
					wair(m,750000);
				break;
			}
			
			if(crit){
				System.out.println(Clr.RED_B+"Critical Hit!!"+Clr.R);
				crit=false;
				wair(m,750000);
			}
			
			//print dmg dealt
			int numbHits=cloneMon.numberOfHits;
			switch(cloneMon.isSpecialMove(selectedMove)){
				case "rngMultihit":
					numbHits+=cloneMon.extraDmg;
				break;
				case "plus2hit":
					numbHits+=2;
				break;
				case "plus3hit":
					numbHits+=3;
				break;
				case "adversity":
					if(cloneMon.moveset[0][selectedMove].equals("X")){
						numbHits++;
					}
				break;
				case "groupB":
					if(turnOf==1){
						numbHits+=countAliveMonInTeam(playerMons);
					}else{
						numbHits+=countAliveMonInTeam(cpuMons);
					}
					numbHits--;
				break;
				case "reverseGroupB":
					if(turnOf==1){
						numbHits+=countAliveMonInTeam(cpuMons);
					}else{
						numbHits+=countAliveMonInTeam(playerMons);
					}
					
					numbHits--;
					if(enemyMon.currentHP==0){
						numbHits++;
					}
				break;
				case "avenger":
					if(turnOf==1){
						if(countAliveMonInTeam(playerMons)==1){
							numbHits++;
						}
					}else{
						if(countAliveMonInTeam(cpuMons)==1){
						numbHits++;
						}
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
					System.out.println("Damaged "+enemyMon.name+" for "+dmgToPrint+" points");
				}else{
					System.out.print("Damaged "+enemyMon.name+" for "+dmgVector[i]+" points");
					if(i==numbHits-1){
						System.out.print(" ("+(trueDmg)+")!");
					}
					System.out.println();
				}
				wair(m,80000);
			}
			if(!cloneMon.energyDrink){
				wair(s,1);
			}
			//if is special
			if(turnOf==1){
				specialMoveHandlerPlayerToCPU(selectedMove,getSmackedBich/numbHits);
				wair(s,1);
			}else{
				specialMoveHandlerCPUToPlayer(selectedMove,getSmackedBich/numbHits);
				wair(s,1);
			}
			
		} else { //move is a status effect
			clear();
			printBattleHUDThing();
			System.out.println(cloneMon.name+" used "+cloneMon.moveset[0][selectedMove]+"!");
			wair(s,1);
			
			if(turnOf==1){
				statusPlayerHandler(cloneMon.moveset[0][selectedMove]);
			}else{
				statusCPUHandler(cloneMon.moveset[0][selectedMove]);
			}
			wair(s,2);
		}
		
		//if only java had c++ pointers frfr
		if(turnOf==1){
			playerMons[playerMonActive] = cloneMon;
			cpuMons[cpuMonActive] = enemyMon;
		}else{
			cpuMons[cpuMonActive] = cloneMon;
			playerMons[playerMonActive] = enemyMon;
		}
	}

	private static void plyerTurn()throws IOException, InterruptedException{
		pokemonBattleSequence(1);
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
				String typ2="";
				if(playerMons[i].type2.equals("")==false){
					typ2="/"+playerMons[i].type2;
				}
				String monHP= "HP: ["+playerMons[i].currentHP+" / "+playerMons[i].baseHP+"]";
				System.out.print("["+(i+1)+"] "+playerMons[i].name);
				for(int j=0;j<16-playerMons[i].name.length();j++){
					System.out.print(" ");
				}
				System.out.println("| "+monHP+"["+playerMons[i].type+typ2+"]");
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
		pokemonBattleSequence(2);
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

	private static int getMoveEffectiveness(int moveselec, Pokemon mon1, Pokemon mon2){
		//String mon2Type=mon2.type;
		String movType=mon1.moveset[1][moveselec];
		String movName=mon1.moveset[0][moveselec];
		
		// 1=x2 ;   11=x4  ;   2=/2  ;  22=/4 ;  0=x1
		
		//special conditions:
		if(movName.equals("Freeze Dry") && mon2.isOfType("Water")){
			
			if(mon2.getWRValue("Ice")==0){
				return 1;
			}if(mon2.getWRValue("Ice")>=1){
				return 11;
			}
			
			return 0;
		}
		
		if(movName.equals("Flying Press")){
			if(mon2.isWeakToType("Flying") || mon2.isWeakToType("Fighting")){
				if(mon2.getWRValue("Flying")>=mon2.getWRValue("Fighting")){
					return mon2.getWRValue("Flying");
				}else{
					return mon2.getWRValue("Fighting");
				}
			}
		}
		
		if(mon1.isSpecialMove(moveselec).equals("supEffective")){
			return 1;
		}
		
		if(mon2.resistsType(movType)){
			if(mon2.getWRValue(movType)==-1){
				return 2;
			}if(mon2.getWRValue(movType)==-2){
				return 22;
			}
		}
		
		if(mon2.isWeakToType(movType)){
			if(mon2.getWRValue(movType)==1){
				return 1;
			}if(mon2.getWRValue(movType)==2){
				return 11;
			}
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

	private static void pokemonAbiliyHandler(int turnOf, boolean beforeTurn){
		if(turnOf==1){ //player
			if(playerMons[playerMonActive].ability.triggerTime.equals("before move")){
				if(beforeTurn){
					switch(playerMons[playerMonActive].ability.name){
						case "Pixilate":
							for(int i=0;i<4;i++){
								if(playerMons[playerMonActive].moveset[1][i].equals("Normal Attack")){
									playerMons[playerMonActive].moveset[1][i] = "Fairy Attack";
								}
							}
						break;
						case "Super Luck":
							playerMons[playerMonActive].currentSPEED*=2;
						break;
						case "Protean":
							String typeToUse = "";
							
							if(playerMons[playerMonActive].moveIsAnAttack(moveSelec)){
								for(int i=0;i<playerMons[playerMonActive].moveset[1][moveSelec].length();i++){
									
									if(playerMons[playerMonActive].moveset[1][moveSelec].charAt(i)!=' '){
										typeToUse+=playerMons[playerMonActive].moveset[1][moveSelec].charAt(i);
									}else{
										break;
									}
								}
								
								playerMons[playerMonActive].type=typeToUse;
								
								playerMons[playerMonActive].setTypesWnR();
								if(playerMons[playerMonActive].energyDrink){
									playerMons[playerMonActive].resists= new String[]{"Nothing!"};
								}
							}
							
						break;
					}
				}else{
					switch(playerMons[playerMonActive].ability.name){
						case "Pixilate":
							for(int i=0;i<4;i++){
								playerMons[playerMonActive].moveset[1][i]=playerMons[playerMonActive].defineMove(playerMons[playerMonActive].moveset[0][i]);
							}//maybe i should rewrite defineMove()
						break;
						case "Super Luck":
							playerMons[playerMonActive].currentSPEED/=2;
						break;
					}
				}
				
			}
		}
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
		String movename = pkmn1.moveset[0][moveInteger]; //gets move name blablabla
		int totalDmgTaken;
		//formula dmg dealt = TotalATK - (enemyDEF/400 x TotalATK) x (Type Weakness Mult) + (rng nonesense) x (Number of Hits) x (Crit Damage Mult)
		//(stab bonus= base atk*1.5);
		int hehehe = rng.nextInt(21);
		hehehe-=10; //<-- random nonesense
		
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
			case "debuffselfdef":
				atk1*=1.2;
			break;
			case "ignoredef":
				atk1-=atk1/4;
				def2=0;
			break;
			case "defisatk":
				atk1=def2;
				def2/=2;
			break;
			case "selfdefisatk":
				atk1=pkmn1.currentDEF;
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
				atk1*=0.25;
				doEmStab/=2;
				nHits+=2;
			break;
			case "plus3hit":
				atk1-=atk1/1.85;
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
			case "rngPoisonBurnPara":
				atk1-=atk1/5;
			break;
		}//special move switch ends

		// add Same Type Attack Bonus
		if(pkmn1.hasSTAB(movename)){
			doEmStab=doEmStab/2;
			atk1+=(int)doEmStab;//adds +50% atk from base
		}
		
		//reduce dmg by def%
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
		
		//clamp!!
		if(totalDmgTaken<2){totalDmgTaken=2;}
		
		//soup effective?
		switch(getMoveEffectiveness(moveInteger, pkmn1, pkmn2)){
			case 0:
				//neutral ouo
			break;
			case 1:
				totalDmgTaken*=2;
			break;
			case 2:
				totalDmgTaken/=2;
			break;
			case 11: // x4 weakness
				totalDmgTaken*=4;
			break;
			case 22: // 1/4 resistance
				totalDmgTaken/=4;
			break;
		}

		totalDmgTaken+=hehehe; //adds the random nonsense
		totalDmgTaken*=nHits; // multihit multiplier

		if(pkmn1.isSpecialMove(moveInteger)=="cuthp"){
			totalDmgTaken=pkmn2.currentHP/2;
			if(getMoveEffectiveness(moveInteger, pkmn1, pkmn2)==2 || getMoveEffectiveness(moveInteger, pkmn1,pkmn2)==22){
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
		if(getMoveEffectiveness(rand,cpuMons[cpuMonActive],playerMons[playerMonActive])==1
			|| getMoveEffectiveness(rand,cpuMons[cpuMonActive],playerMons[playerMonActive])==11){
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
		if(!cpuMons[cpuMonActive].moveIsAnAttack(num)){
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

	private static void statusPlayerHandler(String movv)throws IOException, InterruptedException{
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
			case "assist":
				String guh = playerMons[playerMonActive].moveset[0][moveSelec];
				
				String[] teamMatesMoves = new String[(playerMons.length-1)*4];
				
				int k=0;
				for(int i=0;i<playerMons.length;i++){
					if(i!=playerMonActive){
						for(int j=0;j<4;j++){
							teamMatesMoves[k]=playerMons[i].moveset[0][j];
							k++;
						}
					}
				}
				
				String randomMove = teamMatesMoves[rng.nextInt(teamMatesMoves.length)];
				
				playerMons[playerMonActive].moveset[0][moveSelec]=randomMove;
				playerMons[playerMonActive].moveset[1][moveSelec]=playerMons[playerMonActive].defineMove(playerMons[playerMonActive].moveset[0][moveSelec]);
				
				plyerTurn();
				
				playerMons[playerMonActive].moveset[0][moveSelec]=guh; 
				playerMons[playerMonActive].moveset[1][moveSelec]=playerMons[playerMonActive].defineMove(playerMons[playerMonActive].moveset[0][moveSelec]);
				
			break;
		}
	}

	private static void statusCPUHandler(String movv)throws IOException, InterruptedException{
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
			case "assist":
				String guh = cpuMons[cpuMonActive].moveset[0][cpuMoveSelec];
				
				String[] teamMatesMoves = new String[(cpuMons.length-1)*4];
				
				int k=0;
				for(int i=0;i<cpuMons.length;i++){
					if(i!=cpuMonActive){
						for(int j=0;j<4;j++){
							teamMatesMoves[k]=cpuMons[i].moveset[0][j];
							k++;
						}
					}
				}
				
				String randomMove = teamMatesMoves[rng.nextInt(teamMatesMoves.length)];
				
				cpuMons[cpuMonActive].moveset[0][moveSelec]=randomMove;
				cpuMons[cpuMonActive].moveset[1][moveSelec]=cpuMons[cpuMonActive].defineMove(cpuMons[cpuMonActive].moveset[0][cpuMoveSelec]);
				
				cpuTurn();
				
				cpuMons[cpuMonActive].moveset[0][moveSelec]=guh; 
				cpuMons[cpuMonActive].moveset[1][moveSelec]=cpuMons[cpuMonActive].defineMove(cpuMons[cpuMonActive].moveset[0][cpuMoveSelec]);
				
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
			case "Assist": ret="assist"; break;
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
			case "rngPoisonBurnPara":
				if(rng.nextInt(2)==0){
					int crippling = rng.nextInt(3);
					switch(crippling){
						case 0:
							cpuMons[cpuMonActive].isBurning=true;
							System.out.println(cpuMons[cpuMonActive].name+" is on fire!");
							cpuCanFreeFromAilment[0]=false;
						break;
						case 1:
							cpuMons[cpuMonActive].isPoisoned=true;
							System.out.println(cpuMons[cpuMonActive].name+" is badly poisoned!");
							cpuCanFreeFromAilment[1]=false;
						break;
						case 2:
							cpuMons[cpuMonActive].isParalized=true;
							System.out.println(cpuMons[cpuMonActive].name+" is paralyzed! it may not move!");
							cpuCanFreeFromAilment[2]=false;
						break;
					}
					wair(s,1);
				}
			break;
			case "debuffselfdef":
				playerMons[playerMonActive].decreaseStat("DEF");
				System.out.println(playerMons[playerMonActive].name+"'s DEF fell!");
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
			case "rngPoisonBurnPara":
				if(rng.nextInt(2)==0){
					int crippling = rng.nextInt(3);
					switch(crippling){
						case 0:
							playerMons[playerMonActive].isBurning=true;
							System.out.println(playerMons[playerMonActive].name+" is on fire!");
							plyCanFreeFromAilment[0]=false;
						break;
						case 1:
							playerMons[playerMonActive].isPoisoned=true;
							System.out.println(playerMons[playerMonActive].name+" is badly poisoned!");
							plyCanFreeFromAilment[1]=false;
						break;
						case 2:
							playerMons[playerMonActive].isParalized=true;
							System.out.println(playerMons[playerMonActive].name+" is paralyzed! it may not move!");
							plyCanFreeFromAilment[2]=false;
						break;
					}
					wair(s,1);
				}
			break;
			case "debuffselfdef":
				cpuMons[cpuMonActive].decreaseStat("DEF");
				System.out.println(cpuMons[cpuMonActive].name+"'s DEF fell!");
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
				
				playerMons[playerMonActive].resists= new String[]{"Nothing!"};
				
				for(int i=0;i<playerMons[playerMonActive].weakToMults.length;i++){
					
					if(playerMons[playerMonActive].weakToMults[i]<0){
						playerMons[playerMonActive].weakToMults[i]=0;
					}
					
				}
				
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
		System.out.println(Clr.YELLOW_BB+"[Pokemon Battle Sim "+version+"]"+Clr.R);
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
		System.out.println("Anims ON/OFF: Turns ON or OFF the battle animations, \n turn OFF if you experience slowdown or flickering.\n");
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
		
		if(tempPkmn.name.equals("Missing No")){
			System.out.println("Name:    "+tempPkmn.name);
			System.out.println("Type:    "+tempPkmn.type);
			System.out.println("HP:      ???");
			System.out.println("Attack:  ???");
			System.out.println("Defense: ???");
			System.out.println("Speed:   ???");
		}else{
			String typ2="";
			if(tempPkmn.type2.equals("")==false){
				typ2="/"+tempPkmn.type2;
			}
			
			System.out.println("Name:    "+tempPkmn.name);
			System.out.println("Type:    "+tempPkmn.type+typ2);
			System.out.println("Ability: "+tempPkmn.ability.name);
			System.out.println("HP:      "+tempPkmn.baseHP);
			System.out.println("Attack:  "+tempPkmn.baseATK);
			System.out.println("Defense: "+tempPkmn.baseDEF);
			System.out.println("Speed:   "+tempPkmn.baseSPEED);
		}
		
		
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
	
		String typ2="";
		if(tempPkmn.type2.equals("")==false){
			typ2="/"+tempPkmn.type2;
		}
	
		System.out.println("The Pokemon's stats are reset when switching out \n");//<-- C++ reference!?? //<-- huh?
		System.out.println("Name:    "+tempPkmn.name);
		System.out.println("Type:    "+tempPkmn.type+typ2);
		System.out.println("Ability: "+tempPkmn.ability.name);
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
		if(playerMons[playerMonActive].moveIsAnAttack(selec)){
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
					System.out.println("This move will be used twice in a row in the same turn.");
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
					System.out.println("Uses the enemy Pokemon's DEF stat as ATK.");
					System.out.println("Acts as if their DEF was 50% lower");
				break;
				case "recoil":
					System.out.println("ATK x 1.3");
					System.out.println("Recieve 1/3 of damage dealt as recoil damage.");
				break;
				case "rngMultihit":
					System.out.println("-66% ATK");
					System.out.println("STAB reduced by 87.5%");
					System.out.println("Adds a random number of Hits to the move.");
					System.out.println("(from +0 to +6)");
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
					System.out.println("Doubles ATK for this move if the enemy Pokemon is Mega-Evolved.");
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
					System.out.println("Quadruples ATK if the enemy Pokemon's HP");
					System.out.println("is below 2/3 of its max HP:");
				break;
				case "rngPoisonBurnPara":
					System.out.println("-20% ATK");
					System.out.println("50% chance to inflict Paralysis,");
					System.out.println("Burn or Poison on the enemy.");
				break;
				case "debuffselfdef":
					System.out.println("+20% ATK");
					System.out.println("decreases self DEF after using");
					System.out.println("the move.");
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
					System.out.println("(will only recover 1/4 if the Pokemon");
					System.out.println("is using an Energy Drink)");
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
					System.out.println("adds missing HP to ATK");
					System.out.println("The Pokemon will lose some HP when using this");
					System.out.println("move if it's close to Full HP.");
				break;
				case "assist":
					System.out.println("Uses a random move from one of your");
					System.out.println("team members");
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
	  //<----------------------48---------------------->
	  //[o][o][x][x][x][x]            [x][x][o][o][o][o]
	  //Your Pokemon:                     CPU's Pokemon:
	  // name1                                    name2 
	  // HP:69                                    HP:69 
	  // [PAR][PSN][BRN]			    [PAR][PSN][BRN] 
	  //
	  // name1 used Quick Attack!
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
			wair(m,80000);
			clear();
			printBattleHUDThing(ply, color1, msg);
			wair(m,80000);
			clear();
			System.out.println();
			printBattleHUDThing(ply, color2, msg);
			wair(m,80000);
			clear();
			printBattleHUDThing();
		}else{
			printBattleHUDThing(ply, color1, msg);
			wair(m,80000);
			clear();
			printBattleHUDThing(ply, color2, msg);
			wair(m,80000);
			clear();
			printBattleHUDThing();
		}
	}


	static void printPlayerTeam(){
		System.out.print("Your team: ");
		for(int i=0;i<playerMons.length;i++){
			if(playerMons[i]!=null){
				if(i==3){
					if(playerMons[5]!=null){
						System.out.println();
						System.out.print("           ");
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
		System.out.println("Damage you dealt: "+totalPlayerDamage);
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
	
	
	static private Clr getColorFromType(Pokemon mon,int moveselec){
		String montype = mon.moveset[1][moveselec];
		String[] typesArray = PokemonMaker3000.getTypesVector();
		
		for(int i=0;i<typesArray.length;i++){
			if(montype.contains(typesArray[i])){
				montype = typesArray[i];
				break;
			}
		}
		
		switch(montype){
			case "Grass": return Clr.GREEN;
			
			case "Fire": return Clr.RED;
			
			case "Water": return Clr.BLUE;
			
			case "Psychic": return Clr.MAGENTA;
			
			case "Dark": return Clr.BLACK;
			
			case "Fighting": return Clr.RED;
			
			case "Electric": return Clr.YELLOW;
			
			case "Flying": return Clr.CYAN;
			
			case "Bug": return Clr.GREEN;
			
			case "Ground": return Clr.YELLOW;
			
			case "Rock": return Clr.YELLOW;
			
			case "Poison": return Clr.MAGENTA;
			
			case "Ghost": return Clr.MAGENTA;
			
			case "Steel": return Clr.BLACK;
			
			case "Dragon": return Clr.BLUE;
			
			case "Fairy": return Clr.MAGENTA;
			
			case "Normal": return Clr.BLACK;
			
			case "Ice": return Clr.CYAN;
		}
		return Clr.R;
	}
	
	static private Clr getBrightColorFromType(Pokemon mon,int moveselec){
		String montype = mon.moveset[1][moveselec];
		String[] typesArray = PokemonMaker3000.getTypesVector();
		
		for(int i=0;i<typesArray.length;i++){
			if(montype.contains(typesArray[i])){
				montype = typesArray[i];
				break;
			}
		}
		
		switch(montype){
			case "Grass": return Clr.GREEN_BB;
			
			case "Fire": return Clr.RED_BB;
			
			case "Water": return Clr.BLUE_BB;
			
			case "Psychic": return Clr.MAGENTA_BB;
			
			case "Dark": return Clr.WHITE_BB;
			
			case "Fighting": return Clr.RED_BB;
			
			case "Electric": return Clr.YELLOW_BB;
			
			case "Flying": return Clr.CYAN_BB;
			
			case "Bug": return Clr.GREEN_BB;
			
			case "Ground": return Clr.YELLOW_BB;
			
			case "Rock": return Clr.YELLOW_BB;
			
			case "Poison": return Clr.MAGENTA_BB;
			
			case "Ghost": return Clr.MAGENTA_BB;
			
			case "Steel": return Clr.WHITE_BB;
			
			case "Dragon": return Clr.BLUE_BB;
			
			case "Fairy": return Clr.MAGENTA_BB;
			
			case "Normal": return Clr.WHITE_BB;
			
			case "Ice": return Clr.CYAN_BB;
		}
		return Clr.R;
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
			monName+" enters the field!", "Go! "+monName+"!", "It's "+monName+"!!", "Go for it, "+monName+"!"
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
			monName+" retreated!", monName+" went back into its pokeball!", monName+", get back!"
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