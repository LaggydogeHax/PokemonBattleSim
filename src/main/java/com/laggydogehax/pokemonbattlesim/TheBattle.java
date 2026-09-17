package com.laggydogehax.pokemonbattlesim;

import static com.laggydogehax.pokemonbattlesim.PokemonBattleSim.*; //imports static variables and methods
import java.io.IOException;
import java.util.InputMismatchException;

public class TheBattle {
	
	public void doTheBattling() throws IOException, InterruptedException {
        boolean playerFirst = true;
        boolean p1SkipTurn = false, cpuSkipTurn = false;
        boolean plyWillMegaEvolve = false;
        boolean cpuWillMegaEvolve = false;

        do {//---------------BATTLE!!!!!!!!!---------------//
			int plyDamageInTurn = 0;
			int cpuDamageInTurn = 0;

            do {//get player inputs frfr
                bufferedClear();
                battleMenuSelec = 0;
                moveSelec = 0;

                printBattleHUDThing();
                System.out.println("What should " + playerMons[playerMonActive].name + " do?");
                System.out.println("────────────────────────┬───────────────────────");
                printBattleMenuOptions();

                try {
                    battleMenuSelec = tcl.nextInt();
                } catch (InputMismatchException e) {
                    battleMenuSelec = 0;
                    tcl.nextLine();
                }
                if (battleMenuSelec == 3 && !epicSoftLockPrevention1()) { //select an item to use
                    tcl.nextLine();
                    int selecItem = printSelectBattleItem();
                    if (selecItem != 69) {
                        p1SkipTurn = battleItemsHandler(selecItem);
                        if (p1SkipTurn) {
                            break;
                        }else if (playerMons[playerMonActive].canMegaEvolve()){
                            plyWillMegaEvolve = true;
                        }
                    }
                }
                if (battleMenuSelec == 4) {//mon info
                    printMonInfo();
                }
				
				if (battleMenuSelec == 2 && !epicSoftLockPrevention1()) { //SWITCH PLAYER POKEMON
					if(playerSwitchMon(true)==0){ //returns 1 if the player canceled the operation
						p1SkipTurn = true;
					}else{
						battleMenuSelec = 0;
					}
                }
                
                if (battleMenuSelec == 1) { //chose to fight!!!!!
                    moveSelec = 0;
                    tcl.nextLine();
                    do {
                        
                        bufferedClear();
                        printBattleHUDThing();
                        System.out.println("What should " + playerMons[playerMonActive].name + " do?");
                        System.out.println("────────────────────────┬───────────────────────");
                        printPlayerActivePkmnMoveset(plyWillMegaEvolve);
                        
                        System.out.println("[c]: Go back.");

                        String inp = "";
                        try {
                            inp = tcl.nextLine();
                            moveSelec = Integer.parseInt(inp);
                            
                        } catch (NumberFormatException e) {
                            if(inp.equals("c")){
                                battleMenuSelec = 0;
                            }else{
                                moveSelec = 69;
                                //tcl.nextLine();
                            }
                            
                        }

                    } while ((moveSelec != 1 && moveSelec != 2 && moveSelec != 3 && moveSelec != 4) && battleMenuSelec!=0);

                    if(battleMenuSelec==1){ //selected a move to fite
                        moveSelec--;
                    }

                    if (playerMons[playerMonActive].energyDrink && battleMenuSelec==1) {
                        do {
                            bufferedClear();
                            printBattleHUDThing();
                            System.out.println("What should " + playerMons[playerMonActive].name + " do after " + playerMons[playerMonActive].moveset[0][moveSelec] + "?");
                            System.out.println("────────────────────────┬───────────────────────");
                            printPlayerActivePkmnMoveset(plyWillMegaEvolve);

                            try {
                                moveSelec2 = tcl.nextInt();
                            } catch (InputMismatchException e) {
                                moveSelec2 = 69;
                                tcl.nextLine();
                            }

                        } while (moveSelec2 != 1 && moveSelec2 != 2 && moveSelec2 != 3 && moveSelec2 != 4);
                        moveSelec2--;
                    }

                }

            } while ((battleMenuSelec != 1 && battleMenuSelec != 2) || epicSoftLockPrevention1());

            

            //-----------cpu ai---------//
            cpuMoveSelec = 0;
            cpuMoveSelec = cpuAIHandler();
            if (cpuMoveSelec == 69) {//cpu switch mon
                cpuSkipTurn = true;
                cpuMoveSelec = 0;
                cpuSwitchMon();
            }
            if (cpuMoveSelec == 420) { //mega-evolve
                cpuSkipTurn = false;
                cpuWillMegaEvolve = true;
                do { //force cpu to choose a move xd
                    cpuMoveSelec = cpuAIHandler();
                } while (cpuMoveSelec > 3);
            }
            if (cpuMoveSelec > 600 && cpuMoveSelec < 700) { //handle items
                cpuSkipTurn = true;
                cpuWillMegaEvolve = false;
                cpuBattleItemsHandler(cpuMoveSelec);
                cpuMoveSelec = 0;
            }
            //-------------------------//

            //---epic battle preparations----//
			playerMons[playerMonActive].ability.trigger_startOfTurn(cpuMons[cpuMonActive]);
			cpuMons[cpuMonActive].ability.trigger_startOfTurn(playerMons[playerMonActive]);
			
            playerFirst = whoGoesFirst();
            if (plyWillMegaEvolve && playerFirst && !p1SkipTurn && plyCanMegaEvolve) {
                plyWillMegaEvolve = false;
                playerMegaEvolveSequence();
            }
            if (playerMons[playerMonActive].isParalized && playerFirst && !p1SkipTurn) {
                p1SkipTurn = rollForParalysis(playerMons[playerMonActive]);
            }

            //-------------------------------//
            //this is where da epic battle takes place
            if (playerFirst && !p1SkipTurn) {
                //player first
                if (!p1SkipTurn) {
                    plyDamageInTurn = plyerTurn();
                    if (cpuMons[cpuMonActive].currentHP == 0) {
                        cpuSkipTurn = true;
                    }
                    if (playerMons[playerMonActive].energyDrink) {
                        moveSelec = moveSelec2;
                        plyDamageInTurn += plyerTurn();
                        if (cpuMons[cpuMonActive].currentHP == 0) {
                            cpuSkipTurn = true;
                        }
                    }
                } else {
                    p1SkipTurn = false;
                }

                if (!cpuSkipTurn) {
                    if (cpuWillMegaEvolve && cpuCanMegaEvolve) {
                        cpuCanMegaEvolve = false;
                        cpuMegaEvolveSequence();
                    }
                    cpuSkipTurn = rollForParalysis(cpuMons[cpuMonActive]);
                    if (!cpuSkipTurn) {
                        cpuDamageInTurn = cpuTurn();
                    } else {
                        cpuSkipTurn = false;
                    }
                } else {
                    cpuSkipTurn = false;
                }

            } else {
                //cpu first
                if (!cpuSkipTurn) {
                    if (cpuWillMegaEvolve && cpuCanMegaEvolve) {
                        cpuCanMegaEvolve = false;
                        cpuMegaEvolveSequence();
                    }
                    cpuSkipTurn = rollForParalysis(cpuMons[cpuMonActive]);
                    if (!cpuSkipTurn) {
                        cpuDamageInTurn = cpuTurn();
                        if (playerMons[playerMonActive].currentHP == 0) {//fainted lol
                            p1SkipTurn = true;
                            //let the block of code below handle pkmon switching
                            wair(s, 1);
                        }
                    } else {
                        cpuSkipTurn = false;
                    }
                } else {
                    cpuSkipTurn = false;
                }
                if (!p1SkipTurn) {
                    if (plyCanMegaEvolve && plyWillMegaEvolve) {//mega evolve
                        plyWillMegaEvolve = false;
                        playerMegaEvolveSequence();
                    }
                    p1SkipTurn = rollForParalysis(playerMons[playerMonActive]);
                    if (!p1SkipTurn) {
                        plyDamageInTurn = plyerTurn();
                        if (playerMons[playerMonActive].energyDrink) {
                            moveSelec = moveSelec2;
                            plyDamageInTurn = plyerTurn();
                            if (cpuMons[cpuMonActive].currentHP == 0) {
                                cpuSkipTurn = true;
                            }
                        }
                    } else {
                        p1SkipTurn = false;
                    }
                } else {
                    p1SkipTurn = false;
                }
            }
			
			playerMons[playerMonActive].ability.trigger_endOfTurn(cpuMons[cpuMonActive],moveSelec);
			cpuMons[cpuMonActive].ability.trigger_endOfTurn(playerMons[playerMonActive],cpuMoveSelec);
            statusAilmentsHandler(); //burn, poison, HoT, paralysis statuses

            //----------------------CPU------------------//
            if (cpuMons[cpuMonActive].currentHP == 0) {//if mon ded-- i mean fainted
                //change mon
				if(plyDamageInTurn >= cpuMons[cpuMonActive].baseHP * 3){
					System.out.println(cpuMons[cpuMonActive].name + " straight up died!");
				}else{
					System.out.println(cpuMons[cpuMonActive].name + " fainted!");
				}
                
                wair(s, 2);
                if (checkAllCPUMons()) {
                    cpuSwitchMon();
                    cpuSkipTurn = false;
                } else {
                    System.out.println(cpuName + " is out of Pokemon!");
                    wair(s, 2);
                }
            }
            //-------------end of cpu section--------------// <---bro is out of cpus after this

            //player's mon fainted 
            if (playerMons[playerMonActive].currentHP == 0) {
				
				if(cpuDamageInTurn >= playerMons[playerMonActive].baseHP * 3){
					System.out.println(playerMons[playerMonActive].name + " straight up died!");
				}else{
					System.out.println(playerMons[playerMonActive].name + " fainted!");
				}
                
                wair(s, 2);
                if (checkAllPlayerMons()) {
                    playerSwitchMon();
                } else {
                    System.out.println("You're out of Pokemon!!");
                    wair(s, 3);
                }
            }

            //---- end of turn stuff ----//
            numbahOfTurns++;
            plyWillMegaEvolve = false;
            cpuWillMegaEvolve = false;
            plyCanFreeFromAilment = new boolean[]{true, true, true};
            cpuCanFreeFromAilment = new boolean[]{true, true, true};

            if (playerMons[playerMonActive].permaBurn) {
                playerMons[playerMonActive].isBurning = true;
                plyCanFreeFromAilment[0] = false;
            }
            if (cpuMons[cpuMonActive].permaBurn) {
                cpuMons[cpuMonActive].isBurning = true;
                cpuCanFreeFromAilment[0] = false;
            }

        } while (checkAllPlayerMons() && checkAllCPUMons());

        //--------end result screen-------//
        if (checkAllCPUMons()) {
            //player dieded
            clear();
            System.out.println("...");
            wair(s, 2);
            System.out.println("Your team got wiped out!!");
            wair(s, 2);
            System.out.println("");
            printPlayerTeam();
            printCPUTeam();
            System.out.println("");
            wair(s, 2);
            System.out.println(cpuName + " Won!!");
            System.out.println("Better Luck next time!");
            wair(s, 1);
            printMiscStats();
            wair(s, 5);
        } else {//<-- this means that if somehow both are wiped out, cpu wins by deault- ACTUALLY, TH PLAYER WINS
            // but im too lazy to add an extremely rare "YOU TIED!" screen so imma leave it like this
            //cpu dieded
            clear();
            System.out.println("...");
            wair(s, 2);
            System.out.println(cpuName + "'s team got wiped out!!");
            wair(s, 2);
            System.out.println("");
            printPlayerTeam();
            printCPUTeam();
            System.out.println("");
            wair(s, 2);
            System.out.println("You Won!!");
            System.out.println("Congratulations!!!!!!!!!!");
            wair(s, 1);
            printMiscStats();
            wair(s, 3);
        }
        System.out.println("");
        System.out.println("Press Enter to exit");
        wair(s, 1);
        tcl.nextLine();
    }
	
	public Pokemon getCloneMon(int turnOf){
		switch(turnOf){
			case 1:
				return playerMons[playerMonActive];
			case 2:
				return cpuMons[cpuMonActive];
			default:
				return new Pokemon("Custom");
		}

	}
	
	public Pokemon getEnemyMon(int turnOf){
		switch(turnOf){
			case 1:
				return cpuMons[cpuMonActive];
			case 2:
				return playerMons[playerMonActive];
			default:
				return new Pokemon("Custom");
		}
	}
	
	public int pokemonBattleSequence(Pokemon cloneMon, Pokemon enemyMon, int turnOf, int selectedMove) throws IOException, InterruptedException {
		int trueDmg = 0;

        cloneMon.extraDmg = rng.nextInt(7);
		
		cloneMon.ability.trigger_beforeMove(selectedMove);
		enemyMon.ability.trigger_beforeGettingHit(cloneMon, selectedMove);

        if (cloneMon.moveIsAnAttack(selectedMove)) {

            trueDmg = damageCalc(cloneMon, enemyMon, selectedMove, 0);
            int getSmackedBich = trueDmg;
            int effectiveness = 0;
            boolean crit = false, shakeScreen = false;

            switch (getMoveEffectiveness(selectedMove, cloneMon, enemyMon)) {
                case 1:
                    effectiveness = 1;
                    break;
                case 11:
                    shakeScreen = true;
                    effectiveness = 11;
                    break;
                case 2:
                    effectiveness = 2;
                    break;
                case 22:
                    effectiveness = 22;
                    break;
            }

            if (rollForCrit(cloneMon, selectedMove, enemyMon)) {
                crit = true;
                trueDmg *= 2;
                getSmackedBich = trueDmg;
                saveHighestDmg(cloneMon.name, trueDmg);
                shakeScreen = true;
            }

            if (turnOf == 1) {
                totalPlayerDamage += trueDmg;
            } else {
                totalCPUDamage += trueDmg;
            }

            if (getSmackedBich > enemyMon.currentHP) {
                getSmackedBich = enemyMon.currentHP;
            }

            //start printing... now!
            bufferedClear();
            printBattleHUDThing();
            System.out.println(cloneMon.name + " used " + cloneMon.moveset[0][selectedMove] + "!");
            if (cloneMon.isSpecialMove(selectedMove).equals("magnitude")) {
                wair(m, 750000);
                System.out.println("Magnitude " + (cloneMon.extraDmg + 4) + "!");
            }
            wair(s, 1);
            bufferedClear();
            enemyMon.currentHP -= getSmackedBich;//applies dmg
			
			enemyMon.ability.trigger_afterGettingHit(cloneMon, selectedMove);

            //animation!!!
            Clr color2 = Color.getColorFromMoveType(cloneMon, selectedMove);
            Clr color1 = Color.getBrightColorFromMoveType(cloneMon, selectedMove);
            int colorName;
            if (turnOf == 1) {
                colorName = 2;
            } else {
                colorName = 1;
            }

            if (battleAnimations) {
                printBattleHUDSequence(colorName, color1, color2, shakeScreen, cloneMon.name + " used " + cloneMon.moveset[0][selectedMove] + "!"); //animation!!
                System.out.println(cloneMon.name + " used " + cloneMon.moveset[0][selectedMove] + "!");
            } else {
                printBattleHUDThing(0, Clr.R, cloneMon.name + " used " + cloneMon.moveset[0][selectedMove] + "!");
                wair(m, 500000);
            }

            if (cloneMon.isSpecialMove(selectedMove).equals("magnitude")) {
                System.out.println("Magnitude " + (cloneMon.extraDmg + 4) + "!");
            }
            wair(m, 500000);

            switch (effectiveness) {
                case 1:
                    System.out.println("It's super effective!!");
                    wair(m, 750000);
                    break;
                case 11:
                    System.out.println(Clr.CYAN_B + "It's extremely effective!!" + Clr.R);
                    wair(m, 750000);
                    break;
                case 2:
                    System.out.println("It's not very effective...");
                    wair(m, 750000);
                    break;
                case 22:
                    System.out.println(Clr.YELLOW_B + "It's mostly ineffective..." + Clr.R);
                    wair(m, 750000);
                    break;
            }

            if (crit) {
                System.out.println(Clr.RED_B + "Critical Hit!!" + Clr.R);
                crit = false;
                wair(m, 750000);
            }

            //print dmg dealt
            int numbHits = cloneMon.numberOfHits;
            switch (cloneMon.isSpecialMove(selectedMove)) {
                case "rngMultihit":
                    numbHits += cloneMon.extraDmg;
                    break;
                case "plus2hit":
                    numbHits += 2;
                    break;
                case "plus3hit":
                    numbHits += 3;
                    break;
                case "adversity":
                    if (cloneMon.moveset[0][selectedMove].equals("X")) {
                        numbHits++;
                    }
                    break;
                case "groupB":
                    if (turnOf == 1) {
                        numbHits += countAliveMonInTeam(playerMons);
                    } else {
                        numbHits += countAliveMonInTeam(cpuMons);
                    }
                    numbHits--;
                    break;
                case "reverseGroupB":
                    if (turnOf == 1) {
                        numbHits += countAliveMonInTeam(cpuMons);
                    } else {
                        numbHits += countAliveMonInTeam(playerMons);
                    }

                    numbHits--;
                    if (enemyMon.currentHP == 0) {
                        numbHits++;
                    }
                    break;
                case "avenger":
                    if (turnOf == 1) {
                        if (countAliveMonInTeam(playerMons) == 1) {
                            numbHits++;
                        }
                    } else {
                        if (countAliveMonInTeam(cpuMons) == 1) {
                            numbHits++;
                        }
                    }
                    break;
            }

            int dmgToPrint = (trueDmg / numbHits);
            int[] dmgVector = null;
            if (numbHits > 1) {
                dmgVector = randomizeMultihitValues(trueDmg, numbHits);
            }
            for (int i = 0; i < numbHits; i++) {
                if (numbHits == 1) {
                    System.out.println("Damaged " + enemyMon.name + " for " + dmgToPrint + " points");
                } else {
                    System.out.print("Damaged " + enemyMon.name + " for " + dmgVector[i] + " points");
                    if (i == numbHits - 1) {
                        System.out.print(" (" + (trueDmg) + ")!");
                    }
                    System.out.println();
                }
                wair(m, 80000);
            }
            if (!cloneMon.energyDrink) {
                wair(s, 1);
            }
            //if is special
            if (turnOf == 1) {
                specialMoveHandlerPlayerToCPU(selectedMove, getSmackedBich / numbHits);
                wair(s, 1);
            } else {
                specialMoveHandlerCPUToPlayer(selectedMove, getSmackedBich / numbHits);
                wair(s, 1);
            }

        } else { //move is a status effect
            bufferedClear();
            printBattleHUDThing();
            System.out.println(cloneMon.name + " used " + cloneMon.moveset[0][selectedMove] + "!");
            wair(s, 1);

            if (turnOf == 1) {
                statusPlayerHandler(cloneMon.moveset[0][selectedMove]);
            } else {
                statusCPUHandler(cloneMon.moveset[0][selectedMove]);
            }
            wair(s, 2);
        }
		
		return trueDmg; //returns damage dealt
    }

    public int plyerTurn() throws IOException, InterruptedException {
        return pokemonBattleSequence(playerMons[playerMonActive], cpuMons[cpuMonActive], 1, moveSelec);
    }
	
	public int playerSwitchMon() throws IOException, InterruptedException {
		return playerSwitchMon(false);
	}

    public int playerSwitchMon(boolean canCancel) throws IOException, InterruptedException {
        int switchin = 0;
		//will return 1 if the player canceled the operation
        //boolean flag1=true;
        bufferedClear();
        printBattleHUDThing();
        cout.write("Select one of your Pokemon to switch in:\n");
        cout.write("─────────────────┬──────────────────────────────\n");
        for (int i = 0; i < playerMons.length; i++) {
            if (i != playerMonActive) {
                String typ2 = "";
                if (playerMons[i].type2.equals("") == false) {
                    typ2 = "/" + Color.getColorFromString(playerMons[i].type2) + playerMons[i].type2 + Clr.R;
                }
                String monHP = "HP: [" + Color.getHPColor(playerMons[i]) + playerMons[i].currentHP + Clr.R + "/" + playerMons[i].baseHP + "]";
                cout.write("[" + (i + 1) + "] " + playerMons[i].name);
                for (int j = 0; j < 13 - playerMons[i].name.length(); j++) {
                    cout.write(" ");
                }
                cout.write("│ " + monHP + "[" + Color.getColorFromString(playerMons[i].type) + playerMons[i].type + Clr.R + typ2 + "]");
				cout.write("\n");
            }
        }
        cout.write("\n");
		
		if(canCancel){
			cout.write("[c]: Go back.\n");
		}
		
		cout.flush();
        do {
			String inp = "";
            try {
				inp = tcl.nextLine();
                switchin = Integer.parseInt(inp);
                if (switchin > 0) {
                    switchin--;
                }
            } catch (NumberFormatException e) {
				
				if((inp.equals("c") || inp.equals("C")) && canCancel){
					return 1;
				}else{
					switchin = 99;
					inp="";
				}

            }

            try {
                if (playerMons[switchin].currentHP == 0) {
                    System.out.println("That Pokemon can't continue battling...");
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                switchin = 99;
            }

        } while (checkSwitchIn(switchin, playerMonActive, playerMons) || playerMons[switchin].currentHP == 0);

        bufferedClear();
        printBattleHUDThing();
        System.out.println(getRandomSwitchOutQuote(playerMons[playerMonActive].name));
        wair(s, 2);

        playerMons[playerMonActive].resetStats();

        playerMonActive = switchin;
        if (playerMons[playerMonActive].permaBurn) {
            playerMons[playerMonActive].isBurning = true;
        }

        bufferedClear();
        printBattleHUDThing();
        System.out.println(getRandomSwitchInQuote(playerMons[playerMonActive].name));
        wair(s, 2);
		
		return 0;
    }

    public int cpuTurn() throws IOException, InterruptedException {
        return pokemonBattleSequence(cpuMons[cpuMonActive], playerMons[playerMonActive],2, cpuMoveSelec);
    }

    public void cpuSwitchMon() throws IOException, InterruptedException {
        //run a check before calling this method
        prevCpuMove = "";
        int switching = 0;
        //try to find a mon in cpu team with advantage over the active player pokemon
        int advantageousBoi = cpuMonActive;
        for (int i = 0; i < playerMons.length; i++) {
            if (i != cpuMonActive && cpuMons[i].currentHP > 0) {
                String typ = cpuMons[i].type;
                if (playerMons[playerMonActive].isWeakToType(typ)) {
                    advantageousBoi = i;
                }
            }
        }
        if (advantageousBoi != cpuMonActive) {//successfully found mon
            switching = advantageousBoi;
        } else {
            do {
                switching = rng.nextInt(cpuMons.length);
            } while (checkSwitchIn(switching, cpuMonActive, cpuMons) || cpuMons[switching].currentHP == 0);
        }
        bufferedClear();
        printBattleHUDThing();
        System.out.println(getRandomSwitchOutQuote(cpuMons[cpuMonActive].name));
        wair(s, 2);

        cpuMons[cpuMonActive].resetStats();

        cpuMonActive = switching;

        if (cpuMons[cpuMonActive].permaBurn) {
            cpuMons[cpuMonActive].isBurning = true;
        }

        bufferedClear();
        printBattleHUDThing();
        System.out.println(getRandomSwitchInQuote(cpuMons[cpuMonActive].name, true));
        wair(s, 2);
    }

    public int getMoveEffectiveness(int moveselec, Pokemon mon1, Pokemon mon2) {
        //String mon2Type=mon2.type;
        String movType = mon1.moveset[1][moveselec];
        String movName = mon1.moveset[0][moveselec];

        // 1=x2 ;   11=x4  ;   2=/2  ;  22=/4 ;  0=x1
        //special conditions:
        if (movName.equals("Freeze Dry") && mon2.isOfType("Water")) {

            if (mon2.getWRValue("Ice") == 0) {
                return 1;
            }
            if (mon2.getWRValue("Ice") >= 1) {
                return 11;
            }

            return 0;
        }

        if (movName.equals("Flying Press")) {
            if (mon2.isWeakToType("Flying") || mon2.isWeakToType("Fighting")) {
                if (mon2.getWRValue("Flying") >= mon2.getWRValue("Fighting")) {
                    return mon2.getWRValue("Flying");
                } else {
                    return mon2.getWRValue("Fighting");
                }
            }
        }

        if (mon1.isSpecialMove(moveselec).equals("supEffective")) {
            return 1;
        }

        if (mon1.isSpecialMove(moveselec).equals("nihilLight")) {
            if (mon2.resistsType("Dragon")) {
                return 0;
            }
        }

        if (mon2.resistsType(movType)) {
            if (mon2.getWRValue(movType) == -1) {
                return 2;
            }
            if (mon2.getWRValue(movType) == -2) {
                return 22;
            }
        }

        if (mon2.isWeakToType(movType)) {
            if (mon2.getWRValue(movType) == 1) {
                return 1;
            }
            if (mon2.getWRValue(movType) == 2) {
                return 11;
            }
        }

        return 0; // NEUTRAL!!
    }

    public boolean rollForParalysis(Pokemon pkmn) throws IOException, InterruptedException {
        //call this only when mon is paralized
        boolean yn = false;//skip turn or not
        if (pkmn.isParalized) {
            int para = rng.nextInt(2);
            bufferedClear();
            printBattleHUDThing();
            System.out.println(pkmn.name + " is paralized!");
            wair(s, 1);

            if (para == 0) {//dont move
                System.out.println(pkmn.name + " can't move!");
                wair(s, 2);
                yn = true;
            } else {
                yn = false;
            }
        }
        return yn;
    }

    public boolean whoGoesFirst() {
        boolean playerfirst = true;
        //thank god these are all static variables ong
        int plySpeed = playerMons[playerMonActive].currentSPEED;
        int cpuSpeed = cpuMons[cpuMonActive].currentSPEED;

        if (playerMons[playerMonActive].isParalized) {
            plySpeed /= 2;
        }
        if (cpuMons[cpuMonActive].isParalized) { //halve speed stat (for calculation) if paralized
            cpuSpeed /= 2;
        }

        //----------determine who goes first------------//
        if (plySpeed > cpuSpeed) {
            //player first due to speed stat
            playerfirst = true;
        } else {
            if (cpuSpeed > plySpeed) {
                //cpu first all thanks to the speed stat!!
                playerfirst = false;
            } else {
                // if they got same speed
                if (plySpeed == cpuSpeed) {
                    if (rng.nextInt(2) == 0) { // leave it to random chance frfr
                        playerfirst = true;
                    } else {
                        playerfirst = false;
                    }
                }
            }
        }

        //-------quick attack------//
        if (playerMons[playerMonActive].isSpecialMove(moveSelec).equals("+priority")
                || cpuMons[cpuMonActive].isSpecialMove(cpuMoveSelec).equals("+priority")) {
            //iff if iff iff ififff if if
            //yanderedev would be proud  // what
            if (playerMons[playerMonActive].isSpecialMove(moveSelec).equals("+priority")
                    && (cpuMons[cpuMonActive].isSpecialMove(cpuMoveSelec).equals("+priority")) == false) {
                playerfirst = true;
            } else {
                if (cpuMons[cpuMonActive].isSpecialMove(cpuMoveSelec).equals("+priority")
                        && (playerMons[playerMonActive].isSpecialMove(moveSelec).equals("+priority")) == false) {
                    playerfirst = false;
                }
            }
            // if both use it xd
            if (playerMons[playerMonActive].isSpecialMove(moveSelec).equals("+priority")
                    && cpuMons[cpuMonActive].isSpecialMove(cpuMoveSelec).equals("+priority")) {
                if (rng.nextInt(2) == 0) { // leave it to random chance frfr
                    playerfirst = true;
                } else {
                    playerfirst = false;
                }
            }
        }

        return playerfirst;
    }

    public boolean rollForCrit(Pokemon mon, int movselec, Pokemon enemymon) {
        if (mon.isParalized) {
            mon.currentSPEED /= 2;
        }

        if (mon.moveset[0][movselec].equals("Ruination")) {
            return false;
        }

        if ((mon.currentSPEED) > rng.nextInt(806)//RANDOM CRIT MWAHAHAHAHAHA
                || (mon.moveset[0][movselec].equals("Flower Trick"))) {//Flower trick guaranteed crit
            return true;
        } else {
            if (enemymon.hasStatusAilment() && mon.moveset[0][movselec].equals("Hex")
                    || (enemymon.isPoisoned && mon.moveset[0][movselec].equals("Venoshock"))) {
                return true;
            }
        }
        if (mon.isSpecialMove(movselec).equals("highcritrate")) {
            if ((mon.currentSPEED) > rng.nextInt(806)) {
                return true;//roll again
            }
        }
        if (mon.strike) {
            return true;
        }
        return false;
    }

    public int damageCalc(Pokemon pkmn1, Pokemon pkmn2, int moveInteger, int turnOf) {
        //only call this method if the specified move is not a status move
        //turnOf 0 = player, 1 = cpu
        int atk1 = pkmn1.currentATK;
        int nHits = pkmn1.numberOfHits;
        int extraD = pkmn1.extraDmg;
        int baseatk1 = pkmn1.baseATK;
        double doEmStab = baseatk1;
        int def2 = pkmn2.currentDEF;
        String movename = pkmn1.moveset[0][moveInteger]; //gets move name blablabla
        int totalDmgTaken;
        //formula dmg dealt = TotalATK - (enemyDEF/400 x TotalATK) x (Type Weakness Mult) + (rng nonesense) x (Number of Hits) x (Crit Damage Mult)
        //(stab bonus= base atk*1.5);
        int hehehe = rng.nextInt(21);
        hehehe -= 10; //<-- random nonesense

        //def range from 0 to 300, 400=100% reduction, 300=75% reduction...
        //--if move has special conditions--//
		MoveCalcHandler pmch = new MoveCalcHandler(pkmn1,pkmn2,moveInteger,turnOf);
		//Calc is short for calculations, im just using slang
		pmch.specialMoveCalculator(); //do all the calcs inside this little machine

		//extract the data !!
		nHits = pmch.nHits;
		atk1 = pmch.atk1;
		extraD = pmch.extraD;
		baseatk1 = pmch.baseatk1;
		doEmStab = pmch.doEmStab;
		def2 = pmch.def2;
		
		pkmn1 = pmch.pkmn1;
		pkmn2 = pmch.pkmn2;

        // add Same Type Attack Bonus
        if (pkmn1.hasSTAB(movename)) {
            doEmStab = doEmStab / 2;
            atk1 += (int) doEmStab;//adds +50% atk from base
        }

        //reduce dmg by def%
        float def22 = def2;
        def22 = def22 / 400;//<--def/100%
        float atk11 = atk1;
        if (def22 <= 0) {
            def22 = 1;
        }
        totalDmgTaken = (int) (def22 * atk11);
        if (def22 != 1) {
            totalDmgTaken = atk1 - totalDmgTaken;
        }

        //clamp!!
        if (totalDmgTaken < 2) {
            totalDmgTaken = 2;
        }

        //soup effective?
        switch (getMoveEffectiveness(moveInteger, pkmn1, pkmn2)) {
            case 0:
                //neutral ouo
                break;
            case 1:
                totalDmgTaken *= 2;
                break;
            case 2:
                totalDmgTaken /= 2;
                break;
            case 11: // x4 weakness
                totalDmgTaken *= 4;
                break;
            case 22: // 1/4 resistance
                totalDmgTaken /= 4;
                break;
        }

        totalDmgTaken += hehehe; //adds the random nonsense
        totalDmgTaken *= nHits; // multihit multiplier

        if ("cuthp".equals(pkmn1.isSpecialMove(moveInteger))) {
            totalDmgTaken = pkmn2.currentHP / 2;
            if (getMoveEffectiveness(moveInteger, pkmn1, pkmn2) == 2 || getMoveEffectiveness(moveInteger, pkmn1, pkmn2) == 22) {
                totalDmgTaken /= 2;
            }
        }

        //record highest dmg
        saveHighestDmg(pkmn1.name, totalDmgTaken);

        if (totalDmgTaken < 1) {
            totalDmgTaken = 1;
        }

        return totalDmgTaken;//amount to substract from the pokemon's HP
    }

    public boolean epicSoftLockPrevention1() {// im running out of names for these methods
        int pk1ded = 0;
        if (battleMenuSelec == 2) {
            pk1ded = countAliveMonInTeam(playerMons);

			if (pk1ded == 1) {
				System.out.println("You can't change pokemon right now");
				wair(s, 1);
				battleMenuSelec = 69;
				return true;
			}//prevents player from entering pokmon switch screen if their other mons are ded
			
        }

        if (battleMenuSelec == 3) { //item selec
            if (playerMons[playerMonActive].items.length < 2) {
                System.out.println("You can't use any more items with this Pokemon");
                wair(s, 1);
				return true;
            }
        }

        return false;
    }
	
	
    public int cpuAIHandler() {//still!! no intelligence!!
        Pokemon myMon = playerMons[playerMonActive];
        Pokemon cpuMon = cpuMons[cpuMonActive];
        //boolean[] shoulduse = {true, true, true, true};
        boolean[] shoulduse = new boolean[cpuMons[cpuMonActive].moveset[0].length];
        String[] monsWeaknesses = myMon.weakTo;
        String[] monsResistances = myMon.resists;
        int rand = 0;

        //might switch mon according to these conditions
        if (!cpuJustSwitched) {
            if (cpuMon.currentHP < (cpuMon.baseHP / 2)
                    || cpuMon.currentATK < 30
                    || cpuMon.currentDEF < (cpuMon.baseDEF / 2)
                    || cpuMon.currentSPEED < (cpuMon.baseSPEED / 2)
                    || cpuMon.isPoisoned
                    || (cpuMon.isBurning && !cpuMon.permaBurn)
                    || cpuMon.isParalized) {
                
                if (countAliveMonInTeam(cpuMons) > 1) {
                    int shouldswitch = rng.nextInt(100);
                    if (shouldswitch > 69) {
                        if (cpuMon.name.contains("Mega-")) {
                            shouldswitch = rng.nextInt(80);
                            if (shouldswitch > 69) {
                                cpuJustSwitched = true;
                                return 69; //less likely to switch if mega evolved
                            }
                        }
                        cpuJustSwitched = true;
                        return 69; //30% chance to switch
                    }
                }
            }
        }

        //use item?
        if (cpuMons[cpuMonActive].items.length > 1 && myMon.currentHP >= 100) {
            //mega evolve?
            if (cpuMons[cpuMonActive].canMegaEvolve() && cpuMon.currentHP > (cpuMon.baseHP / 2) && cpuCanMegaEvolve) {
                if (rng.nextInt(100) > 59) {
                    return 420; //megaevolve cpu
                }
            }

            int selecItem = rng.nextInt(6);
            switch (selecItem) {
                case 0:
                    if (cpuMon.currentHP <= (cpuMon.baseHP / 2) && (cpuMon.hasMoveNameInMoveset("Jungle Healing") == false) && (cpuMon.hasMoveNameInMoveset("Roost") == false) && (cpuMon.hasMoveNameInMoveset("Healing Pulse") == false)) {
                        if (rng.nextInt(100) > 30) {
                            return 660; //potion
                        }
                    }
                    break;
                case 1:
                    if (cpuMon.currentSPEED <= myMon.currentSPEED && (cpuMon.currentSPEED + cpuMon.baseSPEED * 1 / 2) >= myMon.currentSPEED) {
                        if (rng.nextInt(100) > 50) {
                            return 661; //x-speed
                        }
                    }
                    break;
                case 2:
                    if ((cpuMon.currentDEF < 110 || cpuMon.baseDEF > 120) && (cpuMon.hasMoveNameInMoveset("Amnesia") == false)) {
                        if (rng.nextInt(100) > 60) {
                            return 662; //x-def
                        }
                    }
                    break;
                case 3:
                    if (cpuMon.baseATK > 89 && (cpuMon.hasMoveNameInMoveset("Sword Dance") == false || cpuMon.hasMoveNameInMoveset("Work Up") == false)) {
                        if (rng.nextInt(100) > 60) {
                            return 663; //x-attack
                        }
                    }
                    break;
                case 4:
                    if (cpuMon.currentATK > 100) {
                        if (rng.nextInt(100) > 80) {
                            return 669; //dash earring
                        }
                    }
                    break;
                case 5:
                    if (cpuMon.currentATK > 80 || cpuMon.hasMoveNameInMoveset("Facade") || cpuMon.hasMoveNameInMoveset("Judgement")) {
                        if (rng.nextInt(100) > 70) {
                            return 670; //strike earrings
                        }
                    }
            }
        }

        if (rand != 69) {
            //didnt decide to switch mon
            //look for super effective attacking move
            for (int i = 0; i < cpuMons[cpuMonActive].moveset[0].length; i++) {
				for (String monsWeakness : monsWeaknesses) {
					if (cpuMon.moveset[1][i].contains(monsWeakness)) {
						shoulduse[i] = true;
					}
				}
            }

            //look for not very effective at all attacking move
            for (int i = 0; i < cpuMons[cpuMonActive].moveset[0].length; i++) {
				for (String monsResistance : monsResistances) {
					if (cpuMon.moveset[1][i].contains(monsResistance)) {
						shoulduse[i] = false;
					}
				}
            }
            //count nonUsables xd
            int nonUsables = 0;
            for (int i = 0; i < shoulduse.length; i++) {
                if (shoulduse[i] == false) {
                    nonUsables++;
                }
            }

            if (nonUsables == cpuMon.moveset[0].length) {
                //YOLO
                rand = rng.nextInt(cpuMons[cpuMonActive].moveset[0].length);
                if (cpuMons[cpuMonActive].moveset[0][rand].equals(prevCpuMove)) {
                    rand = rng.nextInt(cpuMons[cpuMonActive].moveset[0].length);
                }
            } else {
                if (cpuMon.countAttackingMoves() == nonUsables) { //all attacking moves are not effective
                    for (int i = 0; i < shoulduse.length; i++) {
                        shoulduse[i] = true;
                    }
                }
                do {
                    rand = rng.nextInt(cpuMons[cpuMonActive].moveset[0].length);
                    if (cpuMons[cpuMonActive].moveset[0][rand].equals(prevCpuMove)) {
                        rand = rng.nextInt(cpuMons[cpuMonActive].moveset[0].length); //roll again ouo
                    }
                } while (!epicCpuAiRandomMoveCheckerThing(shoulduse, rand));
            }
        }
        cpuJustSwitched = false;
        if (getMoveEffectiveness(rand, cpuMons[cpuMonActive], playerMons[playerMonActive]) == 1
                || getMoveEffectiveness(rand, cpuMons[cpuMonActive], playerMons[playerMonActive]) == 11) {
            //if superefective, dont register as prev used move
            prevCpuMove = "";
        } else {
            prevCpuMove = cpuMons[cpuMonActive].moveset[0][rand];
        }

        return rand;
    }
    //so many functions aaggfhghfhgfhgfjgfg
    //(^bro also made another fuction just for this)

    public boolean epicCpuAiRandomMoveCheckerThing(boolean[] nu, int num) {
        boolean ret = false;

        if (nu[num]) {
            ret = true;
        }
        if (!cpuMons[cpuMonActive].moveIsAnAttack(num)) {
            //tells the cpu if it should use these status moves depending on da situation
            switch (cpuMons[cpuMonActive].statusMoveHandler(num)) {
                case "hot":
                    if (cpuMons[cpuMonActive].healingOverTime) {
                        ret = false;
                    } else {
                        ret = true;
                    }
                    break;
                case "healhalf":
                    if (cpuMons[cpuMonActive].currentHP > cpuMons[cpuMonActive].baseHP / 1.5) {
                        ret = false;
                    } else {
                        ret = true;
                    }
                    break;
                case "buffatk2":
                    if (cpuMons[cpuMonActive].currentATK > cpuMons[cpuMonActive].baseATK * 2) {
                        ret = false;
                    } else {
                        ret = true;
                    }
                    break;
                case "buffatk":
                    if (cpuMons[cpuMonActive].currentATK > cpuMons[cpuMonActive].baseATK * 2) {
                        ret = false;
                    } else {
                        ret = true;
                    }
                    break;
                case "buffdef2":
                    if (cpuMons[cpuMonActive].currentDEF > 290) {
                        ret = false;
                    } else {
                        ret = true;
                    }
                    break;
                case "buffdef":
                    if (cpuMons[cpuMonActive].currentDEF > 290) {
                        ret = false;
                    } else {
                        ret = true;
                    }
                    break;
                case "buffspeed2":
                    if (cpuMons[cpuMonActive].currentSPEED > playerMons[playerMonActive].currentSPEED * 1.2) {
                        ret = false;
                    } else {
                        ret = true;
                    }
                    break;
                case "poison":
                    if (playerMons[playerMonActive].isPoisoned == false) {
                        ret = true;
                    } else {
                        ret = false;
                    }
                    break;
                case "burn":
                    if (playerMons[playerMonActive].isBurning == false) {
                        ret = true;
                    } else {
                        ret = false;
                    }
                    break;
                case "debuffatk":
                    if (playerMons[playerMonActive].currentATK < playerMons[playerMonActive].baseATK / 2) {
                        ret = false;
                    } else {
                        ret = true;
                    }
                    break;
                case "debuffdef2":
                    if (playerMons[playerMonActive].currentDEF < 50) {
                        ret = false;
                    } else {
                        ret = true;
                    }
                    break;
                case "debuffdef":
                    if (playerMons[playerMonActive].currentDEF < 50) {
                        ret = false;
                    } else {
                        ret = true;
                    }
                    break;
                case "debuffspeed2":
                    if (playerMons[playerMonActive].currentSPEED < playerMons[playerMonActive].baseSPEED / 2) {
                        ret = false;
                    } else {
                        ret = true;
                    }
                    break;
                case "paralyze":
                    if (playerMons[playerMonActive].isParalized == false) {
                        ret = false;
                    } else {
                        ret = true;
                    }
                    break;
                default:
                    ret = true;
                    break;
            }
        }
        // :p
        return ret;
    }
	
	public void statusMoveHandler(Pokemon myMon, Pokemon enemyMon, String movv, boolean playerTeam) throws IOException, InterruptedException{
		//movv = name of move used
		Pokemon[] myTeam, enemyTeam;
		boolean[] canFreeFromAilment, enemyCanFreeFromAilment;
		
		if(playerTeam){
			myTeam = playerMons;
			enemyTeam = cpuMons;
			canFreeFromAilment = plyCanFreeFromAilment;
			enemyCanFreeFromAilment = cpuCanFreeFromAilment;
		}else{ //cpu turn
			myTeam = cpuMons;
			enemyTeam = playerMons;
			canFreeFromAilment = cpuCanFreeFromAilment;
			enemyCanFreeFromAilment = plyCanFreeFromAilment;
		}
		
		switch (Pokemon.statusMoveHandler(movv)) {
            case "buffatk&def":
                myMon.raiseStat("ATK");
                myMon.raiseStat("DEF");
                System.out.println(myMon.name + "'s ATK & DEF rose!");
                break;
            case "buffatk":
                myMon.raiseStat("ATK");
                System.out.println(myMon.name + "'s ATK rose!");
                break;
            case "buffatk2":
                myMon.raiseStat("ATK");
                myMon.raiseStat("ATK");
                System.out.println(myMon.name + "'s ATK rose greatly!");
                break;
            case "buffspeed":
                myMon.raiseStat("SPEED");
                System.out.println(myMon.name + "'s SPEED rose!");
                break;
            case "buffspeed2":
                myMon.raiseStat("SPEED");
                myMon.raiseStat("SPEED");
                System.out.println(myMon.name + "'s SPEED rose greatly!");
                break;
            case "buffatk&speed":
                myMon.raiseStat("ATK");
                myMon.raiseStat("SPEED");
                System.out.println(myMon.name + "'s ATK & SPEED rose!");
                break;
            case "buffdef":
                myMon.raiseStat("DEF");
                System.out.println(myMon.name + "'s DEF rose!");
                break;
            case "buffdef2":
                myMon.raiseStat("DEF");
                myMon.raiseStat("DEF");
                System.out.println(myMon.name + "'s DEF rose greatly!");
                break;
            case "debuffdef":
                enemyMon.decreaseStat("DEF");
                System.out.println(enemyMon.name + "'s DEF fell!");
                break;
            case "debuffdef2":
                enemyMon.decreaseStat("DEF");
                enemyMon.decreaseStat("DEF");
                System.out.println(enemyMon.name + "'s DEF fell greatly!");
                break;
            case "debuffspeed2":
                enemyMon.decreaseStat("SPEED");
                enemyMon.decreaseStat("SPEED");
                System.out.println(enemyMon.name + "'s SPEED fell greatly!");
                break;
            case "debuffatk":
                enemyMon.decreaseStat("ATK");
                System.out.println(enemyMon.name + "'s ATK fell!");
                break;
            case "debuffatk2":
                enemyMon.decreaseStat("ATK");
                enemyMon.decreaseStat("ATK");
                System.out.println(enemyMon.name + "'s ATK fell greatly!");
                break;
            case "healhalf":
                myMon.healSelf("half");
                System.out.println(myMon.name + " recovered health!");
                break;
            case "poison":
                enemyMon.isPoisoned = true;
                System.out.println(enemyMon.name + " is badly poisoned!");
                enemyCanFreeFromAilment[1] = false;
                break;
            case "burn":
                enemyMon.isBurning = true;
                System.out.println(enemyMon.name + " is on fire!");
                enemyCanFreeFromAilment[0] = false;
                break;
            case "paralyze":
                enemyMon.isParalized = true;
                System.out.println(enemyMon.name + " is paralized! it may not move!");
                enemyCanFreeFromAilment[2] = false;
                break;
            case "hot":
                myMon.healingOverTime = true;
                System.out.println(myMon.name + " will recover HP over time!");
                break;
            case "lr":
                myMon.isBurning = true;
                System.out.println(myMon.name + " is in deep trouble!!");
                if (((myMon.currentHP * 2) / 1.65) > myMon.baseHP) {
                    myMon.aukBurning();
                    myMon.aukPoisoned();
                }
                int lostHP = myMon.baseHP - myMon.currentHP;
                myMon.currentATK += lostHP;
                wair(s, 1);
                System.out.println(myMon.name + " gained " + lostHP + " ATK!");
                break;
            case "assist":
                String guh = myMon.moveset[0][moveSelec];

                String[] teamMatesMoves = new String[(myTeam.length - 1) * 4];

                int k = 0;
                for (int i = 0; i < myTeam.length; i++) {
                    if (i != playerMonActive) {
                        for (int j = 0; j < 4; j++) {
                            teamMatesMoves[k] = myTeam[i].moveset[0][j];
                            k++;
                        }
                    }
                }

                String randomMove = teamMatesMoves[rng.nextInt(teamMatesMoves.length)];

                myMon.moveset[0][moveSelec] = randomMove;
                myMon.defineSelfMove(moveSelec);

                plyerTurn();

                myMon.moveset[0][moveSelec] = guh;
                myMon.defineSelfMove(moveSelec);

                break;
        }
		
		//hfjgfdjfdg
		if(playerTeam){
			plyCanFreeFromAilment = canFreeFromAilment;
			cpuCanFreeFromAilment = enemyCanFreeFromAilment;
		}else{ //cpu turn
			plyCanFreeFromAilment = enemyCanFreeFromAilment;
			cpuCanFreeFromAilment = canFreeFromAilment;
		}
	}

    public void statusPlayerHandler(String movv) throws IOException, InterruptedException {
        statusMoveHandler(playerMons[playerMonActive], cpuMons[cpuMonActive], movv, true);
    }

    public void statusCPUHandler(String movv) throws IOException, InterruptedException {
        statusMoveHandler(cpuMons[cpuMonActive], playerMons[playerMonActive], movv, false);
    }

	public void specialMoveHandler(Pokemon myMon, Pokemon enemyMon, int moveSelec, int getSmackedBich, boolean playerTeam)throws IOException, InterruptedException{
		
		Pokemon[] myTeam, enemyTeam;
		boolean[] canFreeFromAilment, enemyCanFreeFromAilment;
		
		if(playerTeam){
			myTeam = playerMons;
			enemyTeam = cpuMons;
			canFreeFromAilment = plyCanFreeFromAilment;
			enemyCanFreeFromAilment = cpuCanFreeFromAilment;
		}else{ //cpu turn
			myTeam = cpuMons;
			enemyTeam = playerMons;
			canFreeFromAilment = cpuCanFreeFromAilment;
			enemyCanFreeFromAilment = plyCanFreeFromAilment;
		}
		
		switch (myMon.isSpecialMove(moveSelec)) {
            case "lifedrain":
                int healfor = getSmackedBich / 2;
                if ((healfor + myMon.currentHP) > myMon.baseHP) {
                    healfor = myMon.currentHP - myMon.baseHP;
                    //bandaid patch
                    if (healfor < 0) {
                        healfor = myMon.baseHP - myMon.currentHP;
                    }
                }
                myMon.currentHP += healfor;//heal half of dmg dealt

                System.out.println(myMon.name + " healed for " + healfor + " points");
                wair(s, 1);
                break;
            case "rngBurn":
                if (rng.nextInt(10) > 7) {
                    enemyMon.isBurning = true;
                    System.out.println(enemyMon.name + " is on fire!");
                    enemyCanFreeFromAilment[0] = false;
                    wair(s, 1);
                }
                break;
            case "rngPoison":
                if (rng.nextInt(10) > 7) {
                    enemyMon.isPoisoned = true;
                    System.out.println(enemyMon.name + " is badly poisoned!");
                    enemyCanFreeFromAilment[1] = false;
                    wair(s, 1);
                }
                break;
            case "rngParalysis":
                if (rng.nextInt(10) > 7) {
                    enemyMon.isParalized = true;
                    System.out.println(enemyMon.name + " is paralyzed! it may not move!");
                    enemyCanFreeFromAilment[2] = false;
                    wair(s, 1);
                }
                break;
            case "paralyze":
                enemyMon.isParalized = true;
                System.out.println(enemyMon.name + " is paralyzed! it may not move!");
                enemyCanFreeFromAilment[2] = false;
                wair(s, 1);
                break;
            case "rngDebuffSpeed":
                if (rng.nextInt(10) > 5) {
                    enemyMon.decreaseStat("SPEED");
                    System.out.println(enemyMon.name + "'s SPEED fell!");
                    wair(s, 1);
                }
                break;
            case "rngDebuffDef":
                if (rng.nextInt(10) > 7) {
                    enemyMon.decreaseStat("DEF");
                    System.out.println(enemyMon.name + "'s DEF fell!");
                    wair(s, 1);
                }
                break;
            case "rngDebuffAtk":
                if (rng.nextInt(10) > 7) {
                    enemyMon.decreaseStat("ATK");
                    System.out.println(enemyMon.name + "'s ATK fell!");
                    wair(s, 1);
                }
                break;
            case "debuffatk":
                enemyMon.decreaseStat("ATK");
                System.out.println(enemyMon.name + "'s ATK fell!");
                wair(s, 1);
                break;
            case "buffspeed":
                myMon.raiseStat("SPEED");
                System.out.println(myMon.name + "'s SPEED rose!");
                wair(s, 1);
                break;
            case "doublehit":
                if (!doublehitPlayer) {
                    doublehitPlayer = true;
                    plyerTurn();
                } else {
                    doublehitPlayer = false;
                }
                break;
            case "overclock"://debuff self atk after using
                myMon.decreaseStat("ATK");
                myMon.decreaseStat("ATK");
                System.out.println(myMon.name + "'s ATK fell greatly!");
                wair(s, 1);
                break;
            case "recoil":
				int recoildmg = getSmackedBich / 3;
                myMon.currentHP -= recoildmg;
                if (myMon.currentHP < 0) {
                    myMon.currentHP = 0;
                }
                bufferedClear();
                printBattleHUDThing();
                System.out.println(myMon.name + " hurt itself in recoil! (-"+recoildmg+")");
                wair(s, 1);
                break;
            case "adversity":
                if (myMon.moveset[0][moveSelec].equals("Ascension")) {
                    myMon.healingOverTime = true;
                }
                int lostHP = myMon.baseHP - myMon.currentHP;
                myMon.currentATK += lostHP / 20;
                break;
            case "powerboost":
                myMon.decreaseStat("SPEED");
                System.out.println(myMon.name + "'s SPEED fell!");
                wair(s, 1);
                break;
            case "kamikaze":
                myMon.currentHP = 0;
                System.out.println(myMon.name + " Exploded!!!");
                wair(s, 1);
                break;
            case "debuffIfBoosted":
                Pokemon enemiMon = enemyMon;
                if (enemiMon.currentATK > enemiMon.baseATK || enemiMon.currentDEF > enemiMon.baseDEF || enemiMon.currentSPEED > enemiMon.baseSPEED) {
                    int todebuff = rng.nextInt(3);
                    if (todebuff == 0) {
                        enemyMon.decreaseStat("ATK");
                        System.out.println(enemyMon.name + "'s ATK fell!");
                        wair(s, 1);
                    }
                    if (todebuff == 1) {
                        enemyMon.decreaseStat("DEF");
                        System.out.println(enemyMon.name + "'s DEF fell!");
                        wair(s, 1);
                    } else {
                        enemyMon.decreaseStat("SPEED");
                        System.out.println(enemyMon.name + "'s SPEED fell!");
                        wair(s, 1);
                    }
                }
                break;
            case "rngBuffDef":
                if (rng.nextInt(2) > 0) {
                    myMon.raiseStat("DEF");
                    System.out.println(myMon.name + "'s DEF rose!");
                    wair(s, 1);
                }
                break;
            case "osmash":
                myMon.raiseStat("ATK");
                myMon.raiseStat("ATK");
                System.out.println(myMon.name + "'s ATK rose greatly!");
                wair(s, 1);
                break;
            case "brokenCardMove":
                for (int i = 0; i < myTeam.length; i++) {
                    myTeam[i].baseATK += 25;
                    myTeam[i].currentATK += 25;
                }
				
				if(playerTeam){
					playerMons = myTeam;
				}else{
					cpuMons = enemyTeam;
				}
				
				
				
                System.out.println(myMon+"'s team recieved a boost!!");
                wair(s, 1);
                break;
            case "rngPoisonBurnPara":
                if (rng.nextInt(2) == 0) {
                    int crippling = rng.nextInt(3);
                    switch (crippling) {
                        case 0:
                            enemyMon.isBurning = true;
                            System.out.println(enemyMon.name + " is on fire!");
                            enemyCanFreeFromAilment[0] = false;
                            break;
                        case 1:
                            enemyMon.isPoisoned = true;
                            System.out.println(enemyMon.name + " is badly poisoned!");
                            enemyCanFreeFromAilment[1] = false;
                            break;
                        case 2:
                            enemyMon.isParalized = true;
                            System.out.println(enemyMon.name + " is paralyzed! it may not move!");
                            enemyCanFreeFromAilment[2] = false;
                            break;
                    }
                    wair(s, 1);
                }
                break;
            case "debuffselfdef":
                myMon.decreaseStat("DEF");
                System.out.println(myMon.name + "'s DEF fell!");
                wair(s, 1);
                break;
        }
		
		//hyegfgdhsfsdffsd
		if(playerTeam){
			plyCanFreeFromAilment = canFreeFromAilment;
			cpuCanFreeFromAilment = enemyCanFreeFromAilment;
		}else{ //cpu turn
			plyCanFreeFromAilment = enemyCanFreeFromAilment;
			cpuCanFreeFromAilment = canFreeFromAilment;
		}
	}
	
    public void specialMoveHandlerPlayerToCPU(int moveSelec, int getSmackedBich) throws IOException, InterruptedException {
        specialMoveHandler(playerMons[playerMonActive], cpuMons[cpuMonActive], moveSelec, getSmackedBich, true);
    }

    public void specialMoveHandlerCPUToPlayer(int moveSelec, int getSmackedBich) throws IOException, InterruptedException {
        specialMoveHandler(cpuMons[cpuMonActive], playerMons[playerMonActive], moveSelec, getSmackedBich, false);
    }

    public void statusAilmentsHandler() throws IOException, InterruptedException {
        //this is for poisoned and burning statuses
        //and healing over time!
        // array : 0=burn, 1=poison, 2=paralysis
        // prevent the ailments from getting yeeted on the same turn
        boolean playerFirst = whoGoesFirst();

        if (playerFirst) {
			statusAilments(playerMons[playerMonActive], plyCanFreeFromAilment);
			statusAilments(cpuMons[cpuMonActive], cpuCanFreeFromAilment);
        } else {
            statusAilments(cpuMons[cpuMonActive], cpuCanFreeFromAilment);
            statusAilments(playerMons[playerMonActive], plyCanFreeFromAilment);
        }
    }
	
	public void statusAilments(Pokemon statusMon, boolean[] canFreeFromAilment)throws IOException, InterruptedException{
		//statusMon is the pokemon to recieve the status effeks
		
		if (!statusMon.isDed()) {
            if (statusMon.healingOverTime && !statusMon.isAtMaxHP()) {
                bufferedClear();
                printBattleHUDThing();
                System.out.println(statusMon.name + " recovered health!");
                wair(s, 1);
                bufferedClear();
                statusMon.healOverTime();
                printBattleHUDThing();
                System.out.println(statusMon.name + " recovered health!");
                wair(s, 1);
            }
            if (statusMon.isBurning) {
                bufferedClear();
                printBattleHUDThing();
                System.out.println(statusMon.name + " is burning up!");
                wair(s, 1);
                bufferedClear();
                statusMon.aukBurning();
                printBattleHUDThing();
                System.out.println(statusMon.name + " is burning up!");
                wair(s, 1);
            }
            if (statusMon.isPoisoned) {
                bufferedClear();
                printBattleHUDThing();
                System.out.println(statusMon.name + " is hurt by poison!");
                wair(s, 1);
                bufferedClear();
                statusMon.aukPoisoned();
                printBattleHUDThing();
                System.out.println(statusMon.name + " is hurt by poison!");
                wair(s, 1);
            }
        } else {
            statusMon.resetStats();
        }
		
        // try to free from status ailment
		if (!statusMon.isDed()) {
			if ((statusMon.isBurning && !statusMon.permaBurn) && canFreeFromAilment[0]) {
				if (rng.nextInt(10) > 6) {
					statusMon.isBurning = false;
					System.out.println(statusMon.name + " freed from burn!");
					wair(s, 2);
				}
			}
			if (statusMon.isPoisoned && canFreeFromAilment[1]) {
				if (rng.nextInt(10) > 6) {
					statusMon.isPoisoned = false;
					System.out.println(statusMon.name + " cured itself from poison!");
					wair(s, 2);
				}
			}
			if (statusMon.isParalized && canFreeFromAilment[2]) {
				if (rng.nextInt(10) > 5) {
					statusMon.isParalized = false;
					System.out.println(statusMon.name + " freed from paralysis!");
					wair(s, 2);
				}
			}
		}
	}

    public void playerMegaEvolveSequence() throws IOException, InterruptedException {
        for (int i = 0; i < playerMons[playerMonActive].items.length; i++) {
            if (playerMons[playerMonActive].items[i].equals("Mega Stone")) {
                playerMons[playerMonActive].disableAllItems();
                break;
            }
        }
        String prevName = playerMons[playerMonActive].name;
        bufferedClear();
        printBattleHUDThing();
        if (prevName.equals("Eevee")) {
            System.out.println(playerMons[playerMonActive].name + " is evolving!");
            wair(s, 2);
            playerMons[playerMonActive].megaEvolve();
            plyCanMegaEvolve = false;
            bufferedClear();
            printBattleHUDThing();
            System.out.println(prevName + " is evolving!");
            System.out.println(prevName + " has evolved into " + playerMons[playerMonActive].name + "!");
            wair(s, 2);
        } else {
            System.out.println(playerMons[playerMonActive].name + " is Mega-Evolving!");
            wair(s, 2);
            playerMons[playerMonActive].megaEvolve();
            plyCanMegaEvolve = false;
            bufferedClear();
            printBattleHUDThing();
            System.out.println(prevName + " is Mega-Evolving!");
            System.out.println(prevName + " has Mega-Evolved into " + playerMons[playerMonActive].name + "!");
            wair(s, 2);
        }

    }

    public void cpuMegaEvolveSequence() throws IOException, InterruptedException {
        cpuMons[cpuMonActive].disableAllItems();

        String prevName = cpuMons[cpuMonActive].name;
        bufferedClear();
        printBattleHUDThing();
        if (prevName.equals("Eevee")) {
            System.out.println(cpuMons[cpuMonActive].name + " is evolving!");
            wair(s, 2);
            cpuMons[cpuMonActive].megaEvolve();
            cpuCanMegaEvolve = false;
            bufferedClear();
            printBattleHUDThing();
            System.out.println(prevName + " is evolving!");
            System.out.println(prevName + " has evolved into " + cpuMons[cpuMonActive].name + "!");
            wair(s, 2);
        } else {
            System.out.println(cpuMons[cpuMonActive].name + " is Mega-Evolving!");
            wair(s, 2);
            cpuMons[cpuMonActive].megaEvolve();
            cpuCanMegaEvolve = false;
            bufferedClear();
            printBattleHUDThing();
            System.out.println(prevName + " is Mega-Evolving!");
            System.out.println(prevName + " has Mega-Evolved into " + cpuMons[cpuMonActive].name + "!");
            wair(s, 2);
        }
    }

    public boolean battleItemsHandler(int selecItem) throws IOException, InterruptedException {
        String itemToUse = playerMons[playerMonActive].items[selecItem];
        switch (itemToUse) {
            case "Potion":
                bufferedClear();
                printBattleHUDThing();
                System.out.println("You used a Potion!");
                wair(s, 1);
                playerMons[playerMonActive].healSelf("half");
                bufferedClear();
                printBattleHUDThing();
                System.out.println("You used a Potion!");
                System.out.println(playerMons[playerMonActive].name + " recovered health!");
                wair(s, 2);
                break;
            case "X-Attack":
                bufferedClear();
                printBattleHUDThing();
                System.out.println("You used X-Attack!");
                wair(s, 1);
                playerMons[playerMonActive].raiseStat("ATK");
                playerMons[playerMonActive].raiseStat("ATK");
                System.out.println(playerMons[playerMonActive].name + "'s ATK rose greatly!");
                wair(s, 2);
                break;
            case "X-Defense":
                bufferedClear();
                printBattleHUDThing();
                System.out.println("You used X-Defense!");
                wair(s, 1);
                playerMons[playerMonActive].raiseStat("DEF");
                playerMons[playerMonActive].raiseStat("DEF");
                System.out.println(playerMons[playerMonActive].name + "'s DEF rose greatly!");
                wair(s, 2);
                break;
            case "X-Speed":
                bufferedClear();
                printBattleHUDThing();
                System.out.println("You used X-Speed!");
                wair(s, 1);
                playerMons[playerMonActive].raiseStat("SPEED");
                playerMons[playerMonActive].raiseStat("SPEED");
                System.out.println(playerMons[playerMonActive].name + "'s SPEED rose greatly!");
                wair(s, 2);
                break;
            case "Dash Earring":
                bufferedClear();
                printBattleHUDThing();
                System.out.println("You gave " + playerMons[playerMonActive].name + " a Dash Earring!");
                wair(s, 1);
                playerMons[playerMonActive].baseATK /= 2;
                playerMons[playerMonActive].currentATK -= playerMons[playerMonActive].baseATK;
                playerMons[playerMonActive].numberOfHits += 1; //:3
                System.out.println(playerMons[playerMonActive].name + " gained another hit to its attacks!");
                wair(s, 2);
                break;
            case "Strike Earrings":
                bufferedClear();
                printBattleHUDThing();
                System.out.println("You gave " + playerMons[playerMonActive].name + " Strike Earrings!");
                wair(s, 1);
                playerMons[playerMonActive].isBurning = true;
                playerMons[playerMonActive].strike = true;
                playerMons[playerMonActive].permaBurn = true;
                playerMons[playerMonActive].decreaseStat("DEF");
                playerMons[playerMonActive].decreaseStat("SPEED");
                playerMons[playerMonActive].decreaseStat("SPEED");
                playerMons[playerMonActive].baseSPEED /= 2;
                System.out.println(playerMons[playerMonActive].name + "'s DEF fell!");
                wair(s, 1);
                System.out.println(playerMons[playerMonActive].name + "'s SPEED fell greatly!");
                wair(s, 1);
                System.out.println(playerMons[playerMonActive].name + " will only deal " + Clr.RED_B + "Critical hits!" + Clr.R);
                wair(s, 2);
                break;
            case "Energy Drink":
                bufferedClear();
                printBattleHUDThing();
                System.out.println("You gave " + playerMons[playerMonActive].name + " an Energy Drink!");
                wair(s, 1);
                playerMons[playerMonActive].energyDrink = true;
                playerMons[playerMonActive].decreaseStat("ATK");
                playerMons[playerMonActive].baseATK /= 2;

                playerMons[playerMonActive].resists = new String[]{"Nothing!"};

                for (int i = 0; i < playerMons[playerMonActive].weakToMults.length; i++) {

                    if (playerMons[playerMonActive].weakToMults[i] < 0) {
                        playerMons[playerMonActive].weakToMults[i] = 0;
                    }

                }

                System.out.println(playerMons[playerMonActive].name + "'s ATK fell!");
                wair(s, 1);
                System.out.println(playerMons[playerMonActive].name + "'s resistances were removed!");
                wair(s, 1);
                System.out.println(playerMons[playerMonActive].name + " can use 2 moves per turn!");
                wair(s, 2);
                break;
			case "Arceus' Plates":{
				if(playerMons[playerMonActive].ability instanceof Ability_Multitype){
					
					int selectedPlate = printSelecPlateItem();
					
					if(selectedPlate != 69){

						String[] plateNames = {"Flame", "Splash", "Meadow", "Blank", "Fist",
									"Sky", "Toxic", "Earth", "Stone", "Insect", "Spooky", "Iron",
									"Zap", "Mind", "Icicle", "Draco", "Dread", "Pixie"};
						String[] typesVector = PokemonMaker3000.getTypesVector();
						
						bufferedClear();
						printBattleHUDThing();
						System.out.println("You gave "+playerMons[playerMonActive].name+ " a "+ plateNames[selectedPlate]+ " plate!");
						wair(s, 1);
						System.out.println(playerMons[playerMonActive].name+" changed into the "
							+ typesVector[selectedPlate]
							+ " type!");
						wair(s, 2);
						
						playerMons[playerMonActive].setType(typesVector[selectedPlate]);
						playerMons[playerMonActive].disableAllItems();
					}

				}else{
					System.out.println("Something went wrong!!!");
					wair(s, 2);
				}
				
				return false;
			}
            case "Mega Stone":
                if (plyCanMegaEvolve) {
                    if (playerMons[playerMonActive].name.equals("Eevee")) {
                        System.out.println(playerMons[playerMonActive].name + " will Evolve during its next turn");
                    } else {
                        System.out.println(playerMons[playerMonActive].name + " will Mega-Evolve during its next turn");
                    }
                } else {
                    System.out.println("You can only Mega-Evolve a Pokemon once per battle");
                }
                wair(s, 2);
                return false;
        }
        if (itemToUse.equals("") == false || itemToUse != null) {
            playerMons[playerMonActive].disableAllItems(); //disables items
        }
        return true;
    }

    public void cpuBattleItemsHandler(int selecItem) throws IOException, InterruptedException {
        int itemToUse = selecItem;
        cpuMons[cpuMonActive].disableAllItems();
        switch (itemToUse) {
            case 660:
                bufferedClear();
                printBattleHUDThing();
                System.out.println(cpuName + " used a Potion!");
                wair(s, 1);
                cpuMons[cpuMonActive].healSelf("half");
                bufferedClear();
                printBattleHUDThing();
                System.out.println(cpuName + " used a Potion!");
                System.out.println(cpuMons[cpuMonActive].name + " recovered health!");
                wair(s, 2);
                break;
            case 663:
                bufferedClear();
                printBattleHUDThing();
                System.out.println(cpuName + " used X-Attack!");
                wair(s, 1);
                cpuMons[cpuMonActive].raiseStat("ATK");
                cpuMons[cpuMonActive].raiseStat("ATK");
                System.out.println(cpuMons[cpuMonActive].name + "'s ATK rose greatly!");
                wair(s, 2);
                break;
            case 662:
                bufferedClear();
                printBattleHUDThing();
                System.out.println(cpuName + " used X-Defense!");
                wair(s, 1);
                cpuMons[cpuMonActive].raiseStat("DEF");
                cpuMons[cpuMonActive].raiseStat("DEF");
                System.out.println(cpuMons[cpuMonActive].name + "'s DEF rose greatly!");
                wair(s, 2);
                break;
            case 661:
                bufferedClear();
                printBattleHUDThing();
                System.out.println(cpuName + " used X-Speed!");
                wair(s, 1);
                cpuMons[cpuMonActive].raiseStat("SPEED");
                cpuMons[cpuMonActive].raiseStat("SPEED");
                System.out.println(cpuMons[cpuMonActive].name + "'s SPEED rose greatly!");
                wair(s, 2);
                break;
            case 669:
                bufferedClear();
                printBattleHUDThing();
                System.out.println(cpuName + " gave " + cpuMons[cpuMonActive].name + " a Dash Earring!");
                wair(s, 1);
                cpuMons[cpuMonActive].baseATK /= 2;
                cpuMons[cpuMonActive].currentATK -= cpuMons[cpuMonActive].baseATK;
                cpuMons[cpuMonActive].numberOfHits += 1; //:3
                System.out.println(cpuMons[cpuMonActive].name + " gained another hit to its attacks!");
                wair(s, 2);
                break;
            case 670:
                bufferedClear();
                printBattleHUDThing();
                System.out.println(cpuName + " gave " + cpuMons[cpuMonActive].name + " Strike Earrings!");
                wair(s, 1);
                cpuMons[cpuMonActive].isBurning = true;
                cpuMons[cpuMonActive].strike = true;
                cpuMons[cpuMonActive].permaBurn = true;
                cpuMons[cpuMonActive].decreaseStat("DEF");
                cpuMons[cpuMonActive].decreaseStat("SPEED");
                cpuMons[cpuMonActive].decreaseStat("SPEED");
                cpuMons[cpuMonActive].baseSPEED /= 2;
                System.out.println(cpuMons[cpuMonActive].name + "'s DEF fell!");
                wair(s, 1);
                System.out.println(cpuMons[cpuMonActive].name + "'s SPEED fell greatly!");
                wair(s, 1);
                System.out.println(cpuMons[cpuMonActive].name + " will only deal " + Clr.RED_B + "Critical hits!" + Clr.R);
                wair(s, 2);
                break;
        }
    }
	
	public int[] randomizeMultihitValues(int dmg, int nHits) {
        int[] dmgVector = new int[nHits];

        for (int i = 0; i < dmgVector.length; i++) {
            dmgVector[i] = dmg / nHits;
        }

        int deviation = 0;

        for (int i = 0; i < dmgVector.length; i++) {
            try {
                deviation = rng.nextInt(dmg / 30);
                deviation -= dmg / 30;
                dmgVector[i] -= deviation;
                dmgVector[rng.nextInt(dmgVector.length)] += deviation;
            } catch (Exception e) {
                for (int k = 0; k < dmgVector.length; k++) {
                    dmgVector[k] = dmg / nHits;
                }
                return dmgVector;
            }
        }
        return dmgVector;
    }

    public boolean checkSwitchIn(int switchin, int active, Pokemon[] pkmnList) {
        if (switchin != active) {
            if (switchin < 0 || switchin >= pkmnList.length) {
                return true;
            }
        } else {
            return true;
        }

        return false;
    }

    public boolean checkAllPlayerMons() {
        for (int i = 0; i < playerMons.length; i++) {
            if (playerMons[i].currentHP != 0) {
                return true;
            }
        }
        return false;
    }

    public boolean checkAllCPUMons() {
        for (int i = 0; i < cpuMons.length; i++) {
            if (cpuMons[i].currentHP != 0) {
                return true;
            }
        }
        return false;
    }
	
	public String getRandomSwitchInQuote(String monName, boolean nameCpu) {
        String[] quote = new String[]{
            monName + " enters the field!", "Go! " + monName + "!", "It's " + monName + "!!", "Go for it, " + monName + "!"
        };

        if (nameCpu) {
            String[] cpuQuotes = new String[]{
                cpuName + " sent out " + monName + "!", cpuName + "'s " + monName + " has entered the battle!"
            };
            String[] quoteCopy = new String[quote.length + cpuQuotes.length];
            for (int i = 0; i < quote.length; i++) {
                quoteCopy[i] = quote[i];
            }
            int j = 0;
            for (int i = quote.length; i < quoteCopy.length; i++) {
                quoteCopy[i] = cpuQuotes[j];
                j++;
            }
            quote = quoteCopy;
        }
        return quote[rng.nextInt(quote.length)];
    }

    public String getRandomSwitchInQuote(String monName) { //method overloading!!!!1
        return getRandomSwitchInQuote(monName, false);
    }

    public String getRandomSwitchOutQuote(String monName) {
        String[] quote = new String[]{
            monName + " retreated!", monName + " went back into its pokeball!", monName + ", get back!"
        };
        return quote[rng.nextInt(quote.length)];
    }

	// ------------ print methods ------------- 
	
    public int printSelectBattleItem() throws IOException, InterruptedException {
        bufferedClear();
        printBattleHUDThing();
        cout.write("Select an item to use"+"\n");
		if(playerMons[playerMonActive].items.length > 1){
			cout.write("────────────────────────┬───────────────────────\n");
		}else{
			cout.write("────────────────────────────────────────────────\n");
		}
        
        int coumter = 0;
        for (int i = 0; i < playerMons[playerMonActive].items.length; i++) {
            if (playerMons[playerMonActive].items[i].equals("") == false
                    && playerMons[playerMonActive].items[i] != null) {
                if (coumter < 2) {
                    cout.write("[" + (i + 1) + "] " + playerMons[playerMonActive].items[i]);
                    for (int j = 0; j < 20 - (playerMons[playerMonActive].items[i].length()); j++) {
                        cout.write(" ");
                    }
                    coumter++;
                    if (coumter < 2) {
                        cout.write("│ ");
                    }
                } else {
                    coumter = 0;
                    cout.write("\n");
                    i--;
                }
            }
        }
        cout.write("\n");
        cout.write("[c]: Cancel"+"\n");
		cout.flush();
        int selec = 0;
        String selecSt = "";
        do {
            try {
                selecSt = tcl.nextLine();
                if (selecSt.equals("c")) {
                    return 69;
                }
                selec = Integer.parseInt(selecSt);
            } catch (NumberFormatException e) {
                selec = 0;
                selecSt = "";
                tcl.nextLine();
            }
            if (selec > playerMons[playerMonActive].items.length || selec < 1
                    || playerMons[playerMonActive].items[selec - 1].equals("")) {
                selec = 0;
            }
        } while (selec < 1);

        selec--;
        return selec;
    }
	
	public int printSelecPlateItem() throws IOException, InterruptedException {
		//the order of the plates here are the same as in the typesVector for easier handling
		String[] plateNames = {"Flame", "Splash", "Meadow", "Blank", "Fist",
			"Sky", "Toxic", "Earth", "Stone", "Insect", "Spooky", "Iron",
			"Zap", "Mind", "Icicle", "Draco", "Dread", "Pixie"};

		for(int i=0; i < plateNames.length ; i++ ){
			plateNames[i] += " Plate"; 
		}
		
		String[] typesVector = PokemonMaker3000.getTypesVector();
		Clr[] colors = new Clr[typesVector.length];
		
		for (int i = 0; i < typesVector.length; i++){
			colors[i] = Color.getBrightColorFromString(typesVector[i]);
		}

        bufferedClear();
        printBattleHUDThing();
        cout.write("Select a plate to use"+"\n");
		cout.write("────────────────────────┬───────────────────────\n");
		
        
        int coumter = 0;
		for (int i = 0; i < plateNames.length; i++) {
			if (coumter < 2) {
				cout.write("[" + (i + 1) + "] " + colors[i] +plateNames[i] + Clr.R);
				for (int j = 0; j < 19 - (plateNames[i].length()); j++) {
					cout.write(" ");
				}
				if(i<9){
					cout.write(" ");
				}
				coumter++;
				if (coumter < 2) {
					cout.write("│ ");
				}
			} else {
				coumter = 0;
				cout.write("\n");
				i--;
			}

		}
        cout.write("\n");
        cout.write("[c]: Cancel"+"\n");
		cout.flush();
        int selec = 0;
        String selecSt = "";
		
        do {
            try {
                selecSt = tcl.nextLine();
                if (selecSt.equals("c")) {
                    return 69;
                }
                selec = Integer.parseInt(selecSt);
            } catch (NumberFormatException e) {
                selec = 0;
                selecSt = "";
                tcl.nextLine();
            }
            if (selec > plateNames.length || selec < 1) {
                selec = 0;
            }
        } while (selec < 1);

        selec--;
        return selec;
    }

    public void printBattleMenuOptions() throws IOException {
        //System.out.println("Your active Pokemon:       CPU's active Pokemon:");
        cout.write("[1] " + Clr.RED_B + "Fight" + Clr.R + "               │ [2] " + Clr.CYAN_B + "Pokemon" + Clr.R + "\n");
        cout.write("[3] " + Clr.GREEN_B + "Items" + Clr.R + "               │ [4] " + Clr.WHITE_B + "PKMN Info" + Clr.R + "\n");
        cout.flush();
		/*
		System.out.println("[1] Fight               | [2] Pokemon");
		System.out.println("[3] Items               | [4] PKMN Info");
         */
    }

    public void printBattleHUDThing(int playerHit, Clr ANSIcolor, String msg) throws IOException {
        //playerHit.. 1=ye, 2=cpu hit. other = do nothing
        //ANSIcolor = get ANSI escape sequence from enum Clr

        if (playerHit != 1 && playerHit != 2) {
            ANSIcolor = Clr.R;
        }

        //BufferedWriter is fast as heck
        //BufferedWriter cout = new BufferedWriter(new OutputStreamWriter(System.out)); now a static object
        //THESE VARIALES ARE TOO LONG WTH
        //"playerPokemonTeam[playerPokemonActive].name"
        int plyAliveMon = countAliveMonInTeam(playerMons);
        int cpuAliveMon = countAliveMonInTeam(cpuMons);

        String pk1Name = playerMons[playerMonActive].name;
        String pk2Name = cpuMons[cpuMonActive].name;
        int pk1HP = playerMons[playerMonActive].currentHP;
        int pk2HP = cpuMons[cpuMonActive].currentHP;

        String cl1 = Color.getHPColor(playerMons[playerMonActive]) + "";
        String cl2 = Color.getHPColor(cpuMons[cpuMonActive]) + "";

        String spaces = "";
		String par = Clr.YELLOW_B + "[PAR]" + Clr.R,
			   psn = Clr.MAGENTA_B + "[PSN]" + Clr.R,
			   brn = Clr.RED_B + "[BRN]" + Clr.R;
        String pk1Conditions = " ", pk2Conditions = " ";

        //int ansiLength = ((ANSIcolor + "").length());
        //int resemtLength = ((Clr.R + "").length());

        int truePk1NameLength = pk1Name.length();
        int truePk2NameLength = pk2Name.length();
        if (playerHit == 1) {// name string = ANSI.COLOR + name + ANSI.RESET
            //truePk1NameLength= ansiLength+(pk1Name.length())+resemtLength;
            pk1Name = ANSIcolor + pk1Name + Clr.R;
        }
        if (playerHit == 2) {
            pk2Name = ANSIcolor + pk2Name + Clr.R;
        }

        int cond1Length = 1, cond2Length = 1;

        if (playerMons[playerMonActive].isParalized) {
            pk1Conditions += par;
            cond1Length += 5;
        }
        if (playerMons[playerMonActive].isPoisoned) {
            pk1Conditions += psn;
            cond1Length += 5;
        }
        if (playerMons[playerMonActive].isBurning) {
            pk1Conditions += brn;
            cond1Length += 5;
        }

        if (cpuMons[cpuMonActive].isParalized) {
            pk2Conditions += par;
            cond2Length += 5;
        }
        if (cpuMons[cpuMonActive].isPoisoned) {
            pk2Conditions += psn;
            cond2Length += 5;
        }
        if (cpuMons[cpuMonActive].isBurning) {
            pk2Conditions += brn;
            cond2Length += 5;
        }

        //get & print number of alive mons in both teams on hud
        String al1 = "";
        String al2 = "";
        String alSpaces = "";
        for (int i = 0; i < plyAliveMon; i++) {
            al1 += "[o]";
        }
        for (int i = 0; i < playerMons.length - plyAliveMon; i++) {
            al1 += "[x]";
        }

        for (int i = 0; i < cpuMons.length - cpuAliveMon; i++) {
            al2 += "[x]";
        }
        for (int i = 0; i < cpuAliveMon; i++) {
            al2 += "[o]";
        }

        for (int i = 0; i < 48 - al1.length() - al2.length(); i++) {
            alSpaces += " ";
        }

        //there's 50 spaces frfrfrfrfr <-- there's actually 48
        //----top line: displays number of alive mon in each team----//
        cout.write(al1 + alSpaces + al2 + "\n");

        //---second line: just displays "Your Pokemon:  CPU's Pokemon:"---//
        cout.write(Clr.BOLD + "Your Pokemon:");
        for (int i = 0; i < 35 - cpuName.length() - 11; i++) {
            spaces += " ";
        }
        cout.write(spaces);
        spaces = "";
        cout.write(cpuName + "'s Pokemon:" + Clr.R + "\n");

        //----third line: displays the names of the active Pokemon----//
        cout.write(" " + pk1Name);
        for (int i = 0; i < 46 - truePk2NameLength - truePk1NameLength; i++) {
            spaces += " ";
        }
        cout.write(spaces);
        spaces = "";

        cout.write(pk2Name + "\n");

        //----fourth line: display HP count for both pokemon----//
        cout.write(" HP: " + cl1 + pk1HP + Clr.R);
        for (int i = 0; i < 38 - (pk1HP + "").length() - ((pk2HP + "").length()); i++) {
            spaces += " ";
        }
        cout.write(spaces);
        spaces = "";

        cout.write("HP: " + cl2 + pk2HP + Clr.R + "\n");

        //----fifth line: display status ailments (if any) for both pokemon---//
        cout.write(pk1Conditions);
        for (int i = 0; i < 47 - (cond1Length) - (cond2Length); i++) {
            spaces += " ";
        }
        cout.write(spaces);
        spaces = "";
        cout.write(pk2Conditions + "\n");
        cout.write("\n");
        if (!msg.equals("")) { //prints whatever string was passed
            cout.write(msg + "\n");
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

    public void printBattleHUDThing() throws IOException {
        printBattleHUDThing(0, Clr.R, "");
    }

    public void printBattleHUDSequence(int ply, Clr color1, Clr color2, boolean shake, String msg) throws IOException, InterruptedException {
        //colored animation wip
        //int ply = 1 player ; 2 = cpu
        //print colored name with color1, then color2, then go back to normal

        if (shake) {
            cout.write("\n"); //xd
            printBattleHUDThing(ply, color1, msg);
            wair(m, 80000);
            bufferedClear();
            printBattleHUDThing(ply, color1, msg);
            wair(m, 80000);
            bufferedClear();
            cout.write("\n");
            printBattleHUDThing(ply, color2, msg);
            wair(m, 80000);
            bufferedClear();
            printBattleHUDThing();
        } else {
            printBattleHUDThing(ply, color1, msg);
            wair(m, 80000);
            bufferedClear();
            printBattleHUDThing(ply, color2, msg);
            wair(m, 80000);
            bufferedClear();
            printBattleHUDThing();
        }
    }
	
    public void printMonInfo() throws IOException, InterruptedException {
        //this just a copypaste
        bufferedClear();
        //again gotta make this cus em variables are too long frfrfr
        Pokemon tempPkmn;
        int input = 0;
        float reducPerc = 0;
        float critPerc = 0;
        printBattleHUDThing();
        System.out.println("Which Pokemon do you want to inspect? O_o");
        System.out.println("────────────────────────────────────────────────");
        System.out.println("[1] Your Mon (" + playerMons[playerMonActive].name + ")");
        System.out.println("[2] " + cpuName + "'s Mon (" + cpuMons[cpuMonActive].name + ")");
        System.out.println("\n" + "[3] Your Pokemon moveset");
        do {
            try {
                input = tcl.nextInt();
            } catch (Exception e) {
                input = 69;
                tcl.nextLine();
            }
        } while (input != 1 && input != 2 && input != 3);

        if (input == 3) {
            tcl.nextLine();
            printMoveInfo();
            return;
        }
        bufferedClear();
        if (input == 1) {
            tempPkmn = playerMons[playerMonActive];
            cout.write("Your current Pokemon:");
			cout.write("\n");
        } else {
            tempPkmn = cpuMons[cpuMonActive];
            cout.write(cpuName + "'s current Pokemon:");
			cout.write("\n");
        }

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

        cout.write("The Pokemon's stats are reset when switching out \n"); cout.write("\n");
        cout.write("Name:    " + tempPkmn.name); cout.write("\n");
        cout.write("Type:    " + typ1 + typ2); cout.write("\n");
        cout.write("Ability: " + tempPkmn.ability.name); cout.write("\n");
        cout.write("HP:      " + tempPkmn.currentHP + "/" + tempPkmn.baseHP); cout.write("\n");
        cout.write("Attack:  " + tempPkmn.currentATK + "/" + tempPkmn.baseATK); cout.write("\n");
        cout.write("Defense: " + tempPkmn.currentDEF + "/" + tempPkmn.baseDEF + " (" + perc + "% reduction)"); cout.write("\n");
        cout.write("Speed:   " + tempPkmn.currentSPEED + "/" + tempPkmn.baseSPEED + " (" + cperc + "% crit. chance)"); cout.write("\n");
        cout.write("Weak to: ");

        for (int i = 0; i < tempPkmn.weakTo.length; i++) {
            cout.write(Color.getColorFromString(tempPkmn.weakTo[i]) + tempPkmn.weakTo[i] + Clr.R);
            if (i != tempPkmn.weakTo.length - 1) {
                cout.write(", ");
            }
        }
        cout.write("\n");
        cout.write("\n");

        cout.write("Resists: ");

        for (int i = 0; i < tempPkmn.resists.length; i++) {
            cout.write(Color.getColorFromString(tempPkmn.resists[i]) + tempPkmn.resists[i] + Clr.R);
            if (i != tempPkmn.resists.length - 1) {
                cout.write(", ");
            }
        }
        cout.write("\n");
        cout.write("\n");

        cout.write("Moveset: ");
		cout.write("\n");
        for (int i = 0; i < tempPkmn.moveset[0].length; i++) {
            cout.write("        " + (i + 1) + ": " + tempPkmn.moveset[0][i] + " (" + Color.getBrightColorFromMoveType(tempPkmn, i) + tempPkmn.moveset[1][i] + Clr.R + ")");
			cout.write("\n");
		}
        cout.write("\n");
        cout.write("Press Enter to go back"); cout.write("\n");
		cout.flush();
        tcl.nextLine();
        tcl.nextLine(); //java shenanigans
    }
	
    public void printMoveInfo() throws IOException, InterruptedException {
        int selec = 69;
        bufferedClear();
        printBattleHUDThing();
        System.out.println("Select a move to see its info.");
        System.out.println("────────────────────────┬───────────────────────");
        printPlayerActivePkmnMoveset(false);

        do {
            try {
                selec = Integer.parseInt(tcl.nextLine());
            } catch (NumberFormatException e) {
                selec = 69;
            }
        } while (selec < 1 || selec > 4);
        selec--;

        bufferedClear();
        printBattleHUDThing();

        Clr coulour = Color.getBrightColorFromMoveType(playerMons[playerMonActive], selec);

        System.out.println("────────────────────────────────────────────────");
        System.out.println(Clr.WHITE_BB + playerMons[playerMonActive].moveset[0][selec] + ":" + Clr.R);
        System.out.println(coulour + playerMons[playerMonActive].moveset[1][selec] + Clr.R + " move \n");
        if (playerMons[playerMonActive].moveIsAnAttack(selec)) {
            switch (playerMons[playerMonActive].isSpecialMove(selec)) {
                default:
                    System.out.println("Deals damage!");
                   
                    if (playerMons[playerMonActive].moveset[1][selec].contains("Electric")) {
                        System.out.println("Also has a 20% chance to inflict " + Clr.YELLOW_B + "paralysis" + Clr.R + "\n after using the move.");
                    }
                    
                    break;
                case "+priority":
                    System.out.println("This move has priority, making it go first!");
                    break;
                case "lifedrain":
                    if(playerMons[playerMonActive].moveset[0][selec].equals("Excite")){
                        System.out.println("33% of missing HP -> ATK for this move");
                    }else{
                        System.out.println("-33% ATK");
                    }
                    System.out.println("Half of damage dealt -> HP recovery");
                    break;
                case "rngBurn":
                    System.out.println("20% chance to inflict " + Clr.RED_B + "burn" + Clr.R + " after using the move.");
                    break;
                case "rngPoison":
                    System.out.println("20% chance to inflict " + Clr.MAGENTA_B + "poison" + Clr.R + " after using the move.");
                    break;
                case "rngParalysis":
                    System.out.println("20% chance to inflict " + Clr.YELLOW_B + "paralysis" + Clr.R + " after using the move.");
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
                    if (playerMons[playerMonActive].moveset[0][selec].equals("X")) {
                        System.out.println("-33% ATK");
                        System.out.println("+1 Hit");
                    }
                    if (playerMons[playerMonActive].moveset[0][selec].equals("Ascension")) {
                        System.out.println("Adds Healing Over Time effect after using the move.");
                    }
                    System.out.println("Adds 5% of missing HP to ATK after using the move.");
                    break;
                case "adversity2":
                    System.out.println("-66% ATK");
                    System.out.println("50% of missing HP -> ATK for this move.");
                    break;
                case "groupB":
                    int value = 0;
                    int value2 = 0;
                    if (playerMons.length > 3) {
                        value = 7;
                    } else {
                        value = 25;
                    }
                    System.out.println("Combines " + value + "% of all of your Pokemon's ATK that haven't fainted.");
                    System.out.println("Adds +1 Hit for every alive Pokemon in your team,\n excluding the one using this move.");
                    break;
                case "reverseGroupB":
                    value = 0;
                    value2 = 0;
                    if (playerMons.length > 3) {
                        value = 80;
                        value2 = 20;
                    } else {
                        value = 66;
                        value2 = 33;
                    }
                    System.out.println("-" + value + "% ATK");
                    System.out.println("Adds +" + value2 + "% ATK for every alive Pokemon in the enemy team.");
                    break;
                case "MegaEvolutionHater":
                    System.out.println("Doubles ATK for this move if the enemy Pokemon is Mega-Evolved.");
                    break;
                case "avenger":
                    value = 0;
                    value2 = 0;
                    if (playerMons.length > 3) {
                        value = 25;
                    } else {
                        value = 50;
                    }
                    System.out.println("-25% ATK");
                    System.out.println("Adds +" + value + "% ATK for every fallen ally");
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
                    System.out.println("Multiplies ATK by a random amount (up to x7)");
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
                case "critIfPoisoned":
                    System.out.println("Lands a Critical Hit if");
                    System.out.println("The opponent is Poisoned.");
                    break;
                case "critIfEnemyHasStatusAil":
                    System.out.println("Lands a Critical Hit if");
                    System.out.println("The opponent is Poisoned, Burning\n"
                            + "or Paralized.");
                    break;
            }
        } else {
            switch (Pokemon.statusMoveHandler(playerMons[playerMonActive].moveset[0][selec])) {
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
                    System.out.println("Inflicts " + Clr.MAGENTA_B + "Poison" + Clr.R + " on the enemy.");
                    break;
                case "burn":
                    System.out.println("Inflicts " + Clr.RED_B + "Burn" + Clr.R + " on the enemy.");
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
                    System.out.println("Inflicts " + Clr.YELLOW_B + "Paralysis" + Clr.R + " on the enemy.");
                    break;
                case "buffatk&speed":
                    System.out.println("Increases ATK & SPEED by 25%");
                    break;
                case "lr":
                    System.out.println("Inflicts " + Clr.RED_B + "Burn" + Clr.R + " on self.");
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

        System.out.println("\n" + "Press Enter to go back");
        tcl.nextLine();
    }

	public void printPlayerActivePkmnMoveset(boolean plyWillMegaEvolve) throws IOException {
        Pokemon mon = null;
        if (plyWillMegaEvolve && plyCanMegaEvolve) {
            if (playerMons[playerMonActive].name.equals("Eevee")) {
                mon = playerMons[playerMonActive];
                mon.moveset[0][0] = "???";
                mon.moveset[0][1] = "???";
                mon.moveset[0][2] = "???";
                mon.moveset[0][3] = "???";
            } else {
                mon = new Pokemon(playerMons[playerMonActive].name);
                mon.megaEvolve();//display megaevolved moves
            }
        } else {
            mon = playerMons[playerMonActive];
        }
        int coumter = 0;
        for (int i = 0; i < 4; i++) {//they always got 4 moves frfr
            if (coumter < 2) {
                Clr coulour = Color.getBrightColorFromMoveType(mon, i);

                cout.write("[" + (i + 1) + "] " + coulour + mon.moveset[0][i] + Clr.R);
                for (int j = 0; j < 20 - (mon.moveset[0][i].length()); j++) {
                    cout.write(" ");
                }
                coumter++;
                if (coumter < 2) {
                    cout.write("│ ");
                }
            } else {
                coumter = 0;
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
	
}
