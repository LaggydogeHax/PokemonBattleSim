package com.laggydogehax.pokemonbattlesim;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;

// @author LaggyDogeHax :P
public class PokemonBattleSim {

    static final String OsName = System.getProperty("os.name");
    static final String version = "beta5 dev22";
    static final char s = 's', m = 'm';

    static boolean battleAnimations = true;

    static Pokemon[] playerMons = new Pokemon[3];
    static Pokemon[] cpuMons = new Pokemon[3];
    //cpu >> ai  there's NO intelligence to be found here

    static int playerMonActive = 0;
    static int cpuMonActive = 0;

    static Random rng = new Random();
    static Scanner tcl = new Scanner(System.in); //STATIC SCANNER, LES GOOOO
    static String cpuName = getNewCPUName(); // random cpu name
    
    static String orderOfNames = "default";
	
	static BufferedWriter cout = new BufferedWriter(new OutputStreamWriter(System.out));

    private static String[] getPkmnNamesVector() {
        PokemonDB db = new PokemonDB();
        String[] pkmnNamesVector = db.getPokemonNamesInDB();
        
        return pkmnNamesVector;
    }
    
    private static String[] orderPkmnNamesVector(String[] namesVector){
        switch(orderOfNames){
            case "alphabetically":
                Arrays.sort(namesVector);
                break;
            case "type":
				PokemonDB db = new PokemonDB();
                namesVector = db.getPokemonNamesInTypeOrder();
                break;
            default:
                //in order of addition :)
        }
        
        return namesVector;
    }

    static private void setUpConfigs() throws IOException, InterruptedException {

        bufferedClear();
        cout.write(Clr.YELLOW_BB + "[Pokemon Battle Sim " + version + "]" + Clr.R + "\n");
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
        System.out.println("Located at: " + PBSFileReader.getSaveFilePath() + "\n");

        if (fr.noErrors) {
            int[] list = fr.configList;

            playerMons = new Pokemon[list[0]];
            cpuMons = new Pokemon[list[0]];

            battleAnimations = list[1] == 1;

            System.out.println("File loaded successfully!!");
            System.out.println("Starting up in 3 secs...");
            wair(s, 3);

        } else {
            System.out.println("An error ocurred! check your permissions");
            System.out.println("Starting up normally in 3 secs...");
            wair(s, 3);
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        setUpConfigs();
        String selecshon = "";
        boolean correctName = false;
        boolean errBypass = false; //this is here so the invalid msg can be skipped o_o
        int page = 1, lastPage = 3;
        String[] pkmnNamesVector = getPkmnNamesVector();
        String[] commandList = {"Help", "6mon", "3mon", "Cpu", "Reset", "Anims Off", "Anims On","Order Def","Order Al","Order Type","Boss","Bossme"};

        do {
            clear();
            selecshon = "";

            System.out.println(Clr.YELLOW_BB + "[Pokemon Battle Sim " + version + "]" + Clr.R);
            System.out.println("Choose a Pokemon!!");
            System.out.println("Type its name to select it");
            System.out.println("Type a number to view that page");
            System.out.println("Type help for more commands");
            if (playerMons[0] == null) {
                System.out.println("");
            } else {
                printPlayerTeam();
            }

            System.out.println("");

            printPkmnNamesPage(pkmnNamesVector, page, lastPage);

            System.out.print(">");
            selecshon = tcl.nextLine();

            try {//auto capitalize

                selecshon = autoCapitalizeMonName(selecshon);
                correctName = isNameCorrect(selecshon);

                if (selecshon.equals("Rng") || selecshon.equals("Custom")) {
                    correctName = true;
                } else {
                    for (String i : commandList) {
                        if (selecshon.equals(i)) {
                            errBypass = true;
                            break;
                        }
                    }
                }

            } catch (StringIndexOutOfBoundsException e) {
                correctName = false;
            }

            if (!correctName && !errBypass) {//try page switch
                try {
                    int selecInt = 0;
                    selecInt = Integer.parseInt(selecshon);
                    if (selecInt > 0 && selecInt <= lastPage) {
                        page = selecInt;
                        correctName = false;
                        errBypass = true;
                        selecshon = "";
                    } else {
                        selecshon = "";
                        errBypass = false;
                    }
                } catch (NumberFormatException e) {
                    selecshon = ""; //will throw invalid option
                    errBypass = false;
                }
            }

            //op spaguetti
            if (!correctName && !errBypass) {
                System.out.println("Invalid option, please try again");
                wair(s, 1);
            }

            if (!correctName && errBypass) {

                switch (selecshon) { // commands!
                    case "Help":
                        printHelpMMScreen();
                        break;
                    case "6mon": {
                        Pokemon[] mon1 = new Pokemon[6];
                        Pokemon[] mon2 = new Pokemon[6];
                        for (int i = 0; i < playerMons.length; i++) {
                            mon1[i] = playerMons[i];
                            mon2[i] = cpuMons[i];
                        }
                        playerMons = new Pokemon[6];
                        cpuMons = new Pokemon[6];
                        for (int i = 0; i < playerMons.length; i++) {
                            playerMons[i] = mon1[i];
                            cpuMons[i] = mon2[i];
                        }

                        PBSFileReader fr = new PBSFileReader();
                        int anims = 1;
                        if (!battleAnimations) {
                            anims = 0;
                        }
                        fr.saveSettingsToFile(6, anims);

                        System.out.println("Team size changed to 6 Pokemon");
                        wair(s, 2);
                    }
                    break;
                    case "3mon": {
                        Pokemon[] mon1 = new Pokemon[3];
                        Pokemon[] mon2 = new Pokemon[3];
                        for (int i = 0; i < 2; i++) {
                            mon1[i] = playerMons[i];
                            mon2[i] = cpuMons[i];
                        }
                        playerMons = new Pokemon[3];
                        cpuMons = new Pokemon[3];
                        for (int i = 0; i < 2; i++) {
                            playerMons[i] = mon1[i];
                            cpuMons[i] = mon2[i];
                        }

                        PBSFileReader fr = new PBSFileReader();
                        int anims = 1;
                        if (!battleAnimations) {
                            anims = 0;
                        }
                        fr.saveSettingsToFile(3, anims);

                        System.out.println("Team size changed to 3 Pokemon");
                        wair(s, 2);
                    }
                    break;
                    case "Cpu":
                        cpuTeamManager(lastPage);
                        break;
                    case "Reset":
                        playerMons = new Pokemon[playerMons.length];
                        System.out.println("Player Team has been reset.");
                        wair(s, 2);
                        break;
                    case "Anims On": {
                        battleAnimations = true;

                        PBSFileReader fr = new PBSFileReader();
                        fr.saveSettingsToFile(playerMons.length, 1);

                        System.out.println("Battle Animations are now ON");
                        wair(s, 2);
                    }
                    break;
                    case "Anims Off": {
                        battleAnimations = false;

                        PBSFileReader fr = new PBSFileReader();
                        fr.saveSettingsToFile(playerMons.length, 0);

                        System.out.println("Battle Animations are now OFF");
                        wair(s, 2);
                    }
                    break;
					case "Order Def", "Order Default":
						orderOfNames="default";
						pkmnNamesVector = orderPkmnNamesVector(getPkmnNamesVector());
						break;
					case "Order Al":
						orderOfNames="alphabetically";
						pkmnNamesVector = orderPkmnNamesVector(pkmnNamesVector);
						break;
					case "Order Type":
						orderOfNames="type";
						pkmnNamesVector = orderPkmnNamesVector(pkmnNamesVector);
						break;
                    case "Boss":{
                        //super secret mode in development ok dont tell anybody ok shhh
                        Pokemon bos;
                        if(cpuMons[0]!=null){ //pokemon was assigned before using cputeammanager
                            bos = new PokemonBoss("Custom").pokemonToBoss(cpuMons[0]).bossToPokemon();
                        }else{
                            bos = new PokemonBoss(pkmnNamesVector[rng.nextInt(pkmnNamesVector.length)]).bossToPokemon();
                        }
                        
                        cpuMons = new Pokemon[1];
                        
                        cpuMons[cpuMonActive] = bos;
                        
                        System.out.println("enjoy :P");
                        wair(s, 2);
                        break;
                    }
                    case "Bossme":{ //this wont stay
                        Pokemon bos;
                        if(playerMons[0]!=null){ //pokemon was assigned before
                            bos = new PokemonBoss("Custom").pokemonToBoss(playerMons[0]).bossToPokemon();
                        }else{
                            bos = new PokemonBoss(pkmnNamesVector[rng.nextInt(pkmnNamesVector.length)]).bossToPokemon();
                        }
                        
                        playerMons = new Pokemon[1];
                        
                        playerMons[playerMonActive] = bos;
                        
                        playerMons[0] = bos;
                    }
                }

                errBypass = false;
            }

            if (correctName) {
                switch (selecshon) {
                    case "Rng":
                        for (int i = 0; i < playerMons.length; i++) {
                            savePokemonInTeam(pkmnNamesVector[rng.nextInt(pkmnNamesVector.length)]);
                        }
                        break;
                    case "Custom": //----HELL YEA CUSTOM MON 
                        Pokemon customMon = PokemonMaker3000.makeCustomMon();
                        if (customMon != null) {
                            savePokemonInTeam(customMon);
                        }
                        break;
                    default://other

                        printSelectedMonInfo(selecshon);

                        System.out.println("");
                        System.out.println("Confirm?  [1]: Yes [2]: No");
                        try {
                            int selecshon2 = tcl.nextInt();
                            tcl.nextLine();

                            if (selecshon2 == 1) {
                                correctName = true;
                                savePokemonInTeam(selecshon);
                            } else {
                                correctName = false;
                            }
                        } catch (InputMismatchException e) {
                            correctName = false;
                        }
                        break;
                }
            }

        } while (!correctName || playerMons[(playerMons.length) - 1] == null);

		//----------ASSIGN RANDOM POKEMON TO CPU------------//
		for (Pokemon i : cpuMons) {
			savePokemonInCPUTeam(pkmnNamesVector[rng.nextInt(pkmnNamesVector.length)]);
		}

        //-----------SHOW PLAYER AND CPU TEAMS------------//
        clear();
        System.out.println("");
        printPlayerTeam();
        System.out.println(Clr.YELLOW_B + "                     V.S." + Clr.R);
        printCPUTeam();
        System.out.println("");
        wair(s, 1);
        System.out.println(Clr.RED_B + "            EPIC BATTLE BEGINS IN:" + Clr.R);
        wair(s, 1);
        System.out.println("                    3...");
        wair(s, 1);
        System.out.println("                      2...");
        wair(s, 1);
        System.out.println("                        1..!");
        wair(s, 1);

        doTheBattling();//<-- battle loop
    }//main method ends

    //---------------STORE METHODS------------//
    static void savePokemonInTeam(String name) {
        for (int i = 0; i < playerMons.length; i++) {
            if (playerMons[i] == null) {
                playerMons[i] = new Pokemon(name);
                break;
            }
        }
    }
	
	static void savePokemonInTeam(Pokemon pok){
		for (int i = 0; i < playerMons.length; i++) {
            if (playerMons[i] == null) {
                playerMons[i] = pok;
                break;
            }
        }
	}

    static void savePokemonInCPUTeam(String name) {
        for (int i = 0; i < cpuMons.length; i++) {
            if (cpuMons[i] == null) {
                cpuMons[i] = new Pokemon(name);
                break;
            }
        }
    }
	
	static void savePokemonInCPUTeam(Pokemon pok) {
        for (int i = 0; i < cpuMons.length; i++) {
            if (cpuMons[i] == null) {
                cpuMons[i] = pok;
                break;
            }
        }
    }
	
	    private static void cpuTeamManager(int lastpage) throws IOException, InterruptedException {
        int op = 0;
        do {
            clear();
            System.out.println(Clr.WHITE_B + "--[CPU TEAM MANAGER]--" + Clr.R);
            System.out.println("");
            System.out.println("Current name for the CPU opponent: " + cpuName);
            System.out.println("\n Select an option:");
            System.out.println("[1]: Change CPU's name");
            System.out.println("[2]: Manage CPU Team");
            System.out.println("[3]: Go back \n");

            try {
                op = tcl.nextInt();
            } catch (Exception e) {
                op = 0;
                tcl.nextLine();
            }
            if (op == 3) {
                tcl.nextLine();
                return;
            }

        } while (op != 1 && op != 2);
        tcl.nextLine();
        if (op == 1) {
            String epicname = "";
            do {
                clear();
                System.out.println(Clr.WHITE_B + "--[CPU TEAM MANAGER]--" + Clr.R);
                System.out.println("");
                System.out.println("Enter a new name for the CPU opponent: ");
                System.out.print("-> ");
                epicname = tcl.nextLine();

                if (epicname.length() > 10 || epicname.equals("")) {
                    System.out.println("it can't be longer than 10 characters!");
                    wair(s, 2);
                    epicname = "";
                }

            } while (epicname.length() > 10 || epicname.equals(""));

            cpuName = epicname;
            System.out.println("Saved successfully!!!");
            wair(s, 2);
        } else {
            op = 0;
            do {
                clear();
                System.out.println(Clr.WHITE_B + "--[CPU TEAM MANAGER]--" + Clr.R);
                System.out.println("");
                System.out.println("Team size: " + cpuMons.length);
                System.out.print(cpuName + "'s team: ");
                for (int i = 0; i < cpuMons.length; i++) {
                    if (cpuMons[i] == null) {
                        System.out.print("[...] ");
                    } else {
                        System.out.print("[" + cpuMons[i].name + "] ");
                    }
                    if (i == 2 && cpuMons.length > 3) {
                        System.out.println("");
                        System.out.print("     ");
                    }
                }
                System.out.println("\n");
                System.out.println("Select an option:");
                System.out.println("[1]: Reset team");
                System.out.println("[2]: Assign a Mon to team");
                System.out.println("[3]: Exit");

                try {
                    op = tcl.nextInt();

                    if (op == 1) {
                        cpuMons = new Pokemon[cpuMons.length];
                        System.out.println("CPU team reset successfully");
                        wair(s, 2);
                    }
                    if (op == 3) {
                        tcl.nextLine();
                        return;
                    }
                    if (op == 2) {
                        tcl.nextLine();
                        cpuTeamManagerAssignPokemon(lastpage);
                        return;
                    }
                } catch (IOException | InterruptedException e) {
                    op = 0;
                    tcl.nextLine();
                }
            } while (true);
        }
    }

    private static void cpuTeamManagerAssignPokemon(int lastpage) throws IOException, InterruptedException {
        int op = 0;
        do {
            clear();
            System.out.println(Clr.WHITE_B + "--[CPU TEAM MANAGER]--" + Clr.R);
            System.out.println("");
            System.out.println("Team size: " + cpuMons.length);
            System.out.print(cpuName + "'s team: ");
            for (int i = 0; i < cpuMons.length; i++) {
                if (cpuMons[i] == null) {
                    System.out.print("[...] ");
                } else {
                    System.out.print("[" + cpuMons[i].name + "] ");
                }
                if (i == 2 && cpuMons.length > 3) {
                    System.out.println("");
                    System.out.print("     ");
                }
            }
            System.out.println("\n");
            System.out.println("Assign Pokemon from...");
            System.out.println("[1]: Pokemon list");
            System.out.println("[2]: Custom Pokemon");
            System.out.println("[3]: Exit");

            try {
                op = tcl.nextInt();
                if (op == 3) {
                    tcl.nextLine();
                    return;
                }
            } catch (Exception e) {
                op = 0;
                tcl.nextLine();
            }

            if (op == 2) {
				tcl.nextLine();
                Pokemon custm = PokemonMaker3000.makeCustomMon();
                if (custm != null) {
                    savePokemonInCPUTeam(custm);
                }
            }
            if (op == 1) {
				tcl.nextLine();
                String[] namesVector = getPkmnNamesVector();
                int page = 1;
                boolean correctName = false;
                boolean errBypass = false;
                do {
                    String selecshon = "";
                    clear();
                    System.out.println(Clr.WHITE_B + "--[CPU TEAM MANAGER]--" + Clr.R);
                    System.out.println("Type " + Clr.MAGENTA_B + "cancel" + Clr.R + " to go back");
                    System.out.print(cpuName + "'s team: ");
                    for (int i = 0; i < cpuMons.length; i++) {
                        if (cpuMons[i] == null) {
                            System.out.print("[...] ");
                        } else {
                            System.out.print("[" + cpuMons[i].name + "] ");
                        }
                        if (i == 2 && cpuMons.length > 3) {
                            System.out.println("");
                            System.out.print("     ");
                        }
                    }
                    System.out.println("\n");
                    printPkmnNamesPage(namesVector, page, lastpage);

                    try {
                        System.out.print(">");
                        selecshon = tcl.nextLine();

                        selecshon = autoCapitalizeMonName(selecshon);
                        correctName = isNameCorrect(selecshon);

                        if (selecshon.equals("Cancel")) {
                            return;
                        }
                    } catch (Exception e) {
                        selecshon = "";
                        correctName = false;
                        errBypass = false;
                    }

                    if (!correctName && !errBypass) {//try page switch
                        try {
                            int selecInt = 0;
                            selecInt = Integer.parseInt(selecshon);
                            if (selecInt > 0 && selecInt <= lastpage) {
                                page = selecInt;
                                correctName = false;
                                errBypass = true;
                                selecshon = "";
                            } else {
                                selecshon = "";
                                errBypass = false;
                            }
                        } catch (NumberFormatException e) {
                            selecshon = ""; //will throw invalid option
                            errBypass = false;
                        }
                    }

                    if (!correctName && !errBypass) {
                        System.out.println("Invalid option, please try again");
                        wair(s, 1);
                    }
                    errBypass = false;
                    if (correctName) {
                        clear();
                        printSelectedMonInfo(selecshon);
                        System.out.println("");
                        System.out.println("Confirm?  [1]: Yes [2]: No");
                        try {
                            int selecshon2 = tcl.nextInt();
                            tcl.nextLine();

                            if (selecshon2 == 1) {
                                correctName = true;
                                savePokemonInCPUTeam(selecshon);
                            } else {
                                correctName = false;
                            }
                        } catch (InputMismatchException e) {
                            correctName = false;
                        }
                    }
                } while (cpuMons[cpuMons.length - 1] == null);
            }
        } while (true);
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
	
	static String prevCpuMove = "";

    //----------POKEMON BATTLE METHODS-------------//
    private static void doTheBattling() throws IOException, InterruptedException {
		if(!cpuMons[cpuMonActive].isBoss()){
			TheBattle b = new TheBattle();
			b.doTheBattling();
		}else{
			TheBossBattle b = new TheBossBattle();
			b.doTheBattling();
		}
		
    }

    //-------------PRINT METHODS-----------//
    private static void printPkmnNamesPage(String[] namesVector, int page, int lastPage) throws IOException {
        int coumter = 0;
        int from = 0, to = 0;//0-35, 36-71, 72-107
        boolean toLeft = false, toRight = false;
        String arrLeft = " ";
        String arrRight = " ";
        switch (page) {
            case 1:
                from = 0;
                to = 35;
                break;
            case 2:
                from = 36;
                to = 71;
                break;
            case 3:
                from = 72;
                to = 107;
                break;
            default:
                from = 0;
                to = 35;
                break;
        }

        if (page == 1) {
            toLeft = false;
            toRight = true;
        }
        if (page < lastPage && page > 1) {
            toLeft = true;
            toRight = true;
        }
        if (page == lastPage) {
            toRight = false;
            toLeft = true;
        }

        if (toLeft) {
            arrLeft = "<";
        }
        if (toRight) {
            arrRight = ">";
        }

        cout.write("                 " + arrLeft + " Page " + page + " " + arrRight + "\n");
		
		//crop the names vector to the only 36 pokemon we need
		String[] displayedNamesVector = new String[36];
		int k=0;
		for(int i = from; i <= to; i++){
			displayedNamesVector[k] = namesVector[i];
			k++;
		}
        
        String[] typeColors = new String[displayedNamesVector.length];
        PokemonDB db = new PokemonDB();
        String[] tyeps = db.getArrayOfTypesFromNames(displayedNamesVector);
        
        for (int i = 0; i < typeColors.length; i++) {
			typeColors[i] = Color.getColorFromString(tyeps[i])+"";
        }
        
        for (int i = 0; i < displayedNamesVector.length; i++) {
            String space = "";
            if (coumter < 3) {
                cout.write(" " + typeColors[i] + displayedNamesVector[i] + Clr.R);
                
                for (int j = 0; j <= 12 - (displayedNamesVector[i].length()); j++) {
                    space += " ";
                }
                cout.write(space);
                coumter++;
                if(coumter!=3){
                    cout.write("│");
                }
            } else {
                coumter = 0;
                cout.write("\n");
                i--;
            }
        }
        cout.write("\n");
        cout.flush();
        //cout.close(); <-- DO NOT
    }

    private static void printHelpMMScreen() throws IOException, InterruptedException {
        clear();
        System.out.println(Clr.YELLOW_BB + "[Pokemon Battle Sim " + version + "]" + Clr.R);
        System.out.println("Totally super cool commands for the Main Menu:");
        System.out.println("");
        System.out.println(Clr.WHITE_BB + "CUSTOM:" + Clr.R + " allows you to create or manage a\n customized Pokemon. it can be saved to a txt file.\n");
        System.out.println(Clr.WHITE_BB + "RNG:" + Clr.R + " fills empty slots in your team with randomly\n selected Pokemon. then starts the battle.\n");
        System.out.println(Clr.WHITE_BB + "<Number>:" + Clr.R + " view selected page of Pokemon.\n you can select any Pokemon while vieweing any page.\n");
        System.out.println(Clr.WHITE_BB + "<Pokemon Name>:" + Clr.R + " select a Pokemon.\n tip: you can just type the first 4 letters.\n");
        System.out.println(Clr.WHITE_BB + "RESET:" + Clr.R + " Deletes all Pokemon in your team. \n");
        System.out.println(Clr.WHITE_BB + "CPU:" + Clr.R + " Enter the CPU Manager menu. \n");
        System.out.println(Clr.WHITE_BB + "6mon:" + Clr.R + " Changes the Pokemon Team size to 6 Pokemon. \n");
        System.out.println(Clr.WHITE_BB + "3mon:" + Clr.R + " Changes the Pokemon Team size to 3 Pokemon. \n");
        System.out.println(Clr.WHITE_BB + "Anims {on|off}:" + Clr.R + " Enables or disables the battle animations, \n turn OFF if you experience slowdown or flickering.\n");
		System.out.println(Clr.WHITE_BB + "Order {Def|Al|Type}:" +Clr.R+" Changes the order of the Pokemon in the Main Menu.\n");
        System.out.println(Clr.WHITE_BB + "HELP:" + Clr.R + " brings up this very cool looking screen.");
		
		
        System.out.println("");
        System.out.println("Press Enter to go back");
        tcl.nextLine();
    }

    static void printPlayerTeam() {
        System.out.print("Your team: ");
        for (int i = 0; i < playerMons.length; i++) {
            if (playerMons[i] != null) {
                if (i == 3) {
                    if (playerMons[5] != null) {
                        System.out.println();
                        System.out.print("           ");
                    }
                }
                System.out.print("[" 
					+ Color.getColorFromString(playerMons[i].type)
					+ playerMons[i].name 
					+ Clr.R
					+ "] ");
            }

        }
        System.out.println("");
    }

    static void printCPUTeam() {
        System.out.print(cpuName + "'s team: ");
        for (int i = 0; i < cpuMons.length; i++) {
            if (cpuMons[i] != null) {
                if (i == 3) {
                    System.out.println();
                    System.out.print("           ");
                }
                System.out.print("[" 
					+ Color.getColorFromString(cpuMons[i].type)
					+ cpuMons[i].name 
					+ Clr.R
					+  "] ");
            }
        }
        System.out.println("");
    }
	
	public static void printSelectedMonInfo(String selecshon) throws IOException, InterruptedException {
        clear();
        cout.write("Selected Pokemon: "+"\n");

        Pokemon tempPkmn = new Pokemon(selecshon);
        if (tempPkmn.name.equals("Missing No")) {
            cout.write("Name:    " + tempPkmn.name+"\n");
            cout.write("Type:    " + tempPkmn.type+"\n");
            cout.write("HP:      ???"+"\n");
            cout.write("Attack:  ???"+"\n");
            cout.write("Defense: ???"+"\n");
            cout.write("Speed:   ???"+"\n");
        } else {

            float reducPerc = 0;
            float critPerc = 0;

            //shows reduction percentage
            reducPerc = (float) tempPkmn.currentDEF / 400;
            reducPerc *= 100;
            String rpString = reducPerc + "";
            String perc = "";
            for (int i = 0; i < 4; i++) {
                try {
                    perc += rpString.charAt(i);
                } catch (StringIndexOutOfBoundsException e) {
                    break;
                }
            }
            //shows crit chance
            critPerc = (float) tempPkmn.currentSPEED / 806;
            critPerc *= 100;
            String cpString = critPerc + "";
            String cperc = "";
            for (int i = 0; i < 4; i++) {
                try {
                    cperc += cpString.charAt(i);
                } catch (StringIndexOutOfBoundsException e) {
                    break;
                }
            }
            String typ1 = Color.getColorFromString(tempPkmn.type) + "" + tempPkmn.type + Clr.R;
            String typ2 = "";
            if (tempPkmn.type2.equals("") == false) {
                typ2 = "/" + Color.getColorFromString(tempPkmn.type2) + tempPkmn.type2 + Clr.R;
            }

            cout.write("The Pokemon's stats are reset when switching out \n"+"\n");
            cout.write("Name:    " + tempPkmn.name+"\n");
            cout.write("Type:    " + typ1 + typ2+"\n");
            cout.write("Ability: " + tempPkmn.ability.name+"\n");
            cout.write("HP:      " + tempPkmn.baseHP+"\n");
            cout.write("Attack:  " + tempPkmn.baseATK+"\n");
            cout.write("Defense: " + tempPkmn.baseDEF + " (" + perc + "% reduction)"+"\n");
            cout.write("Speed:   " + tempPkmn.baseSPEED + " (" + cperc + "% crit. chance)"+"\n");
        }

        cout.write("Weak to: ");

        for (int i = 0; i < tempPkmn.weakTo.length; i++) {
            cout.write(Color.getColorFromString(tempPkmn.weakTo[i]) + tempPkmn.weakTo[i] + Clr.R);
            if (i != tempPkmn.weakTo.length - 1) {
                cout.write(", ");
            }
        }
        cout.write("\n");

        cout.write("Resists: ");

        for (int i = 0; i < tempPkmn.resists.length; i++) {
            cout.write(Color.getColorFromString(tempPkmn.resists[i]) + tempPkmn.resists[i] + Clr.R);
            if (i != tempPkmn.resists.length - 1) {
                cout.write(", ");
            }
        }
        cout.write("\n");
        
        cout.write("Moveset: "+"\n");
		for (int i = 0; i < 4; i++) {
			cout.write("        "
				+ (i + 1)
				+ ":"
				+ tempPkmn.moveset[0][i]
				+ " (" + Color.getBrightColorFromMoveType(tempPkmn, i) + tempPkmn.moveset[1][i] + Clr.R + ")"
				+ "\n");
		}
        cout.write("\n");

        int totalstats = tempPkmn.baseATK + tempPkmn.baseDEF + tempPkmn.baseHP + tempPkmn.baseSPEED;
        cout.write("Total stat points: " + totalstats+"\n");
		
		cout.flush();
    }

    static void printMiscStats() {
        System.out.println("");
        wair(s, 1);
        System.out.println("Turns played: " + numbahOfTurns);
        wair(s, 1);
        System.out.println("Highest damage dealt: " + highestDamage + ", by: " + highestDamageName);
        wair(s, 1);
        System.out.println("Damage you dealt: " + totalPlayerDamage);
        wair(s, 1);
        System.out.println("Damage done by " + cpuName + ": " + totalCPUDamage);
    }

    //------------OTHER METHODS---------------//
	static int countAliveMonInTeam(Pokemon[] team) {
        int count = 0;
        for (int i = 0; i < team.length; i++) {
            if (team[i].currentHP != 0) {
                count++;
            }
        }
        return count;
    }
	
    static private String autoCapitalizeMonName(String selecshon) {
        String autoCap1 = selecshon.charAt(0) + "";
        String autoCap2 = "";
        String autoCap3 = "";
        String[] pkmnNamesVector = getPkmnNamesVector();
        String[] secretMons = PokemonMaker3000.getSuperSecretMonList();

        autoCap1 = autoCap1.toUpperCase();
        for (int i = 1; i < selecshon.length(); i++) {
            autoCap2 += selecshon.charAt(i); // remove first letter
        }
        autoCap2 = autoCap2.toLowerCase();
        for (int i = 0; i < autoCap2.length(); i++) {
            if (i != 0 && (autoCap2.charAt(i - 1)) == ' ') {//if previus char was a space, capitalize char
                char upp = Character.toUpperCase(autoCap2.charAt(i));
                autoCap3 += upp;//all thanks to the paradox mon -_-
            } else {
                autoCap3 += autoCap2.charAt(i);
            }
        }
        selecshon = autoCap1 + autoCap3;

        if (selecshon.contains("Adp ")) { // Arceus & Dialga & Palkia GX real!
            selecshon = "ADP GX";
        }

        if (selecshon.length() > 3) {
            for (int i = 0; i < pkmnNamesVector.length; i++) {
                if (pkmnNamesVector[i].contains(selecshon)) {
                    selecshon = pkmnNamesVector[i];
                    break;
                }
                if (i < secretMons.length) {
                    if (secretMons[i].contains(selecshon)) {
                        selecshon = secretMons[i];
                        break;
                    }
                }
            }
        }

        return selecshon;
    }

    static private boolean isNameCorrect(String selecshon) {
        //auto capitalize
        String[] pkmnNamesVector = getPkmnNamesVector();
        String[] secretMons = PokemonMaker3000.getSuperSecretMonList();
        if (selecshon.equals("Mew")) {// xd
            return true;
        } else {
            if (selecshon.length() > 3) {
                for (int i = 0; i < pkmnNamesVector.length; i++) {
                    if (pkmnNamesVector[i].equals(selecshon)) {
                        return true;
                    }
                    if (i < secretMons.length) {
                        if (secretMons[i].equals(selecshon)) {
                            return true;
                        }
                    }
                }
            } else {
                return false;
            }
        }
        return false;
    }

	/**
	 * compares the damage with the highest recorded number then saves it along with the name of the Pokemon
	 * @param name the name of the Pokemon
	 * @param damag the damage dealt to save
	 */
    static public void saveHighestDmg(String name, int damag) {
        if (damag > highestDamage) {
            highestDamage = damag;
            highestDamageName = name;
        }
    }

    static private String getNewCPUName() {
        String[] names = new String[]{
            "Gary", "Cyn", "Sunna", "Mario", "Hop", "Niko", "Blue", "Red", "Green", "Peter", "N", "Cebollin", "CPU",
            "Nokia", "Moya", "Evie", "Luigi", "Noodle", "Joel", "Oatmeal", "Nestle", "Panda", "Pingu", "Gaby",
            "Maigol", "Luci", "Java", "TWM", "Sunflower", "Nina", "Lola", "Obama", "Guide", "Steve", "Freeman", "Goku",
            "Cocuy", "Socks", "Bacon", "Tocino", "Arepa", "Sans", "Meevin", "Zazu", "Kevin", "May", "Eleki", "Glue",
            "Geminy", "Gippidy", "Tux", "Xenia", "Suzanne", "Wilber", "Emule", "Xue", "Gnome", "Edwin", "Maomao",
			"Kit", "Bliko","Owen", "Lyphe", "Ross", "Garfield", "Jacket", "Poof"
        };

        return names[rng.nextInt(names.length)];
    }

    static void clear() throws IOException, InterruptedException {
        if (OsName.contains("Windows")) {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor(); // Windows cmd
        } else {
            System.out.print("\033[H\033[2J"); // Linux terminal
        }
    }
	
	static void bufferedClear() throws IOException, InterruptedException{
		if(OsName.contains("Windows")){
			clear();
			return;
		}
		
		cout.write("\033[H\033[2J");
	}

    static void wair(char opc, int tim) { //opciones.... s=segundos.... m=microsegundos. tim = tiempo
        switch (opc) {
            case 's':
                try {
                    TimeUnit.SECONDS.sleep(tim);
                } catch (InterruptedException e) {
                    System.out.println("o_o");
                }
                break;
            case 'm':
                try {
                    TimeUnit.MICROSECONDS.sleep(tim);
                } catch (InterruptedException e) {
                    System.out.println("o_o");
                }
                break;
        }
    }

}
//main class ends
