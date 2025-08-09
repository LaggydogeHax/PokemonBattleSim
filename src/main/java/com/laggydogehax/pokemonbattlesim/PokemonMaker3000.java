package com.laggydogehax.pokemonbattlesim;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

//why does this extend the main class??? i forgor
class PokemonMaker3000 extends PokemonBattleSim{
	//funny class name
	static final char s='s', m='m';
        
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
		}if("".equals(msg)){
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

	public static String[] getTypesVector(){
		/* nu uh, now we get this from the database cus its more epic and unnecessarily complicated
		String[] typesVector = {"Fire","Water","Grass","Normal","Fighting","Flying","Poison",
		"Ground","Rock","Bug","Ghost","Steel","Electric","Psychic","Ice","Dragon","Dark","Fairy"};
		*/
		
		PokemonDB db = new PokemonDB();
		return db.getTypesVectorInDB();
	}

	public static String[] getMoveTable(String typ){
		String[] table = {};
		String[] moveTableStatus = new String[]{"Poison Powder","Will-O-Wisp","Sword Dance","Roar","Hone Claws","Calm Mind",
		"Iron Defense","Toxic","Dragon Dance","Growl","Charm","Bulk Up","Heal Pulse","Charge","Roost","Extreme Speed","Amnesia",
		"Aqua Ring","Impulse","Jungle Healing","Fake Tears","Scary Face","Agility","Defend Order","Work Up","Thunder Wave","Last Resort",
		"Shift Gear","String Shot","Acid Armor","Lunar Plumage","Metal Sound","Coil","Salt Cure","Rock Polish","Assist"};
		String[] moveTableAtkNormal = new String[]{"Quick Attack","Hyper Beam","Giga Impact","Super Fang","Facade","Swift","Judgement","Ascension","Group Beating","Retaliate","Tackle"};
		String[] moveTableAtkFire = new String[]{"Flamethrower","Flame Charge","Overheat","Fire Blast","Mystical Fire","Bitter Blade"};
		String[] moveTableAtkWater = new String[]{"Hydro Pump","Hydro Cannon","Surf","Whirlpool","Scald","Water Shuriken","SurgingStrikes","Muddy Water","Water Gun"};
		String[] moveTableAtkElectric = new String[]{"Thunder","Thunder Fang","Electroweb","Overdrive","Plasma Fists","Zap Cannon","Thunder Cage"};
		String[] moveTableAtkGrass = new String[]{"Vine Whip","Giga Drain","Flower Trick","Trailblaze","Razor Leaf","Grass Knot","Wood Hammer","Leaf Blade","Solar Beam","Energy Ball","Powerful Bloom"};
		String[] moveTableAtkIce = new String[]{"Ice Beam","Ice Fang","Freeze Dry","Blizzard","Ice Slash","Aurora Beam","Triple Axel","Avalanche"};
		String[] moveTableAtkFighting = new String[]{"Aura Sphere","Close Combat","Rock Smash","Body Slam","Double Kick","Hammer Arm","Drain Punch","HJ Kick","Superpower","Flying Press","Focus Blast","Body Press","Sacred Sword","Vacuum Wave"};
		String[] moveTableAtkPoison = new String[]{"Poison Leech","Toxic Spikes","Venoshock","Poison Sting","Sludge Bomb","Dire Claw"};
		String[] moveTableAtkGround = new String[]{"Earthquake","Mud Slap","Earth Power","X","Excite","Magnitude"};
		String[] moveTableAtkFlying = new String[]{"Wing Attack","Gust","Aerial Ace","Dual Wingbeat","Air Slash","Brave Bird","Cyclone","Sky Attack"};
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
		}catch(IOException | InterruptedException e){
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
			
			txtfile = yayornay==1;
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
			}catch(NumberFormatException e){
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
						}catch(NumberFormatException e){
							movType="";
							selectedInt=0;
							System.out.println("Please try again");
							wair(s,2);
						}
					}while("".equals(movType));
					
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
		}catch(IOException e){
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
				if("".equals(customMon.moveset[1][i])){
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
			
		}catch(FileNotFoundException | NullPointerException | NumberFormatException e){
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
		String[] moves=getMoveTable(typ);
		String input="";

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

			if("".equals(ret)){
				System.out.println("Invalid option, try again!");
				wair(s,2);
			}
			
		}while("".equals(ret));

		return ret;
	}

	private static void printSelectedMoves(String[] moveset){
		int coumter=0;
		for(int i=0;i<4;i++){
			if(coumter<2){
				System.out.print("["+(i+1)+"] ");
				if(moveset[i]==null || "".equals(moveset[i])){
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
		String[] secretMonList = new String[]{"Celebi ex","Regieleki","ADP GX","Missing No"};

		return secretMonList;
	}

}