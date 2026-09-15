package com.laggydogehax.pokemonbattlesim;

import static com.laggydogehax.pokemonbattlesim.PokemonBattleSim.*; //imports static variables and methods
import java.io.IOException;
import java.util.InputMismatchException;

public class TheBossBattle extends TheBattle{
	
	static int playerMonActive2 = 1;
	static int playerMonActive3 = 2;
	
	static int moveSelec3 = 0;
	static int moveSelec4 = 0;
	
	private void getMoveSelec3() throws IOException, InterruptedException{
		moveSelec3 = 0;
		bufferedClear();
		printBattleHUDThing();
		System.out.println("What should " + playerMons[playerMonActive2].name + " do?");
		System.out.println("────────────────────────┬───────────────────────");
		printBattleMenuOptions();

		try {
			battleMenuSelec = tcl.nextInt();
		} catch (InputMismatchException e) {
			battleMenuSelec = 0;
			tcl.nextLine();
		}
		/*
		if (battleMenuSelec == 3 && !epicSoftLockPrevention1()) { //select an item to use
			tcl.nextLine();
			int selecItem = printSelectBattleItem();
			if (selecItem != 69) {
				p1SkipTurn = battleItemsHandler(selecItem);
				if (p1SkipTurn) {
					break;
				} else { //will mega evolve :)
					plyWillMegaEvolve = true;
				}
			}
		}
		*/
		if (battleMenuSelec == 4) {//mon info
			printMonInfo();
		}

		/*
		if (battleMenuSelec == 2 && !epicSoftLockPrevention1()) { //SWITCH PLAYER POKEMON
			if (playerSwitchMon(true) == 0) { //returns 1 if the player canceled the operation
				p1SkipTurn = true;
			} else {
				battleMenuSelec = 0;
			}
		}
		*/

		if (battleMenuSelec == 1) { //chose to fight!!!!!
			moveSelec3 = 0;
			tcl.nextLine();
			do {

				bufferedClear();
				printBattleHUDThing();
				System.out.println("What should " + playerMons[playerMonActive2].name + " do?");
				System.out.println("────────────────────────┬───────────────────────");
				printPlayerActivePkmnMoveset(false,3);

				System.out.println("[c]: Go back.");

				String inp = "";
				try {
					inp = tcl.nextLine();
					moveSelec3 = Integer.parseInt(inp);

				} catch (NumberFormatException e) {
					if (inp.equals("c")) {
						battleMenuSelec = 0;
					} else {
						moveSelec3 = 69;
						//tcl.nextLine();
					}

				}

			} while ((moveSelec3 != 1 && moveSelec3 != 2 && moveSelec3 != 3 && moveSelec3 != 4) && battleMenuSelec != 0);

			if (battleMenuSelec == 1) { //selected a move to fite
				moveSelec3--;
			}

			/*
			if (playerMons[playerMonActive].energyDrink && battleMenuSelec == 1) {
				do {
					bufferedClear();
					printBattleHUDThing();
					System.out.println("What should " + playerMons[playerMonActive].name + " do after " + playerMons[playerMonActive].moveset[0][moveSelec] + "?");
					System.out.println("────────────────────────┬───────────────────────");
					printPlayerActivePkmnMoveset(false);

					try {
						moveSelec2 = tcl.nextInt();
					} catch (InputMismatchException e) {
						moveSelec2 = 69;
						tcl.nextLine();
					}

				} while (moveSelec2 != 1 && moveSelec2 != 2 && moveSelec2 != 3 && moveSelec2 != 4);
				moveSelec2--;
			}
			*/

		}
	}
	
	private void getMoveSelec4() throws IOException, InterruptedException{
		moveSelec4 = 0;
		bufferedClear();
		printBattleHUDThing();
		System.out.println("What should " + playerMons[playerMonActive3].name + " do?");
		System.out.println("────────────────────────┬───────────────────────");
		printBattleMenuOptions();

		try {
			battleMenuSelec = tcl.nextInt();
		} catch (InputMismatchException e) {
			battleMenuSelec = 0;
			tcl.nextLine();
		}
		/*
		if (battleMenuSelec == 3 && !epicSoftLockPrevention1()) { //select an item to use
			tcl.nextLine();
			int selecItem = printSelectBattleItem();
			if (selecItem != 69) {
				p1SkipTurn = battleItemsHandler(selecItem);
				if (p1SkipTurn) {
					break;
				} else { //will mega evolve :)
					plyWillMegaEvolve = true;
				}
			}
		}
		*/
		if (battleMenuSelec == 4) {//mon info
			printMonInfo();
		}

		/*
		if (battleMenuSelec == 2 && !epicSoftLockPrevention1()) { //SWITCH PLAYER POKEMON
			if (playerSwitchMon(true) == 0) { //returns 1 if the player canceled the operation
				p1SkipTurn = true;
			} else {
				battleMenuSelec = 0;
			}
		}
		*/

		if (battleMenuSelec == 1) { //chose to fight!!!!!
			moveSelec4 = 0;
			tcl.nextLine();
			do {

				bufferedClear();
				printBattleHUDThing();
				System.out.println("What should " + playerMons[playerMonActive3].name + " do?");
				System.out.println("────────────────────────┬───────────────────────");
				printPlayerActivePkmnMoveset(false,4);

				System.out.println("[c]: Go back.");

				String inp = "";
				try {
					inp = tcl.nextLine();
					moveSelec4 = Integer.parseInt(inp);

				} catch (NumberFormatException e) {
					if (inp.equals("c")) {
						battleMenuSelec = 0;
					} else {
						moveSelec4 = 69;
						//tcl.nextLine();
					}

				}

			} while ((moveSelec4 != 1 && moveSelec4 != 2 && moveSelec4 != 3 && moveSelec4 != 4) && battleMenuSelec != 0);

			if (battleMenuSelec == 1) { //selected a move to fite
				moveSelec4--;
			}

			/*
			if (playerMons[playerMonActive].energyDrink && battleMenuSelec == 1) {
				do {
					bufferedClear();
					printBattleHUDThing();
					System.out.println("What should " + playerMons[playerMonActive].name + " do after " + playerMons[playerMonActive].moveset[0][moveSelec] + "?");
					System.out.println("────────────────────────┬───────────────────────");
					printPlayerActivePkmnMoveset(false);

					try {
						moveSelec2 = tcl.nextInt();
					} catch (InputMismatchException e) {
						moveSelec2 = 69;
						tcl.nextLine();
					}

				} while (moveSelec2 != 1 && moveSelec2 != 2 && moveSelec2 != 3 && moveSelec2 != 4);
				moveSelec2--;
			}
			*/

		}
	}
	
	@Override
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
                        } else { //will mega evolve :)
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
					
					moveSelec3 = 0;
					do{
						getMoveSelec3();
					}while((moveSelec3 > -1 && moveSelec3 < 4)==false);
					
					moveSelec4 = 0;
					do{
						getMoveSelec4();
					}while((moveSelec4 > -1 && moveSelec4 < 4)==false);
					
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
					plyDamageInTurn += plyerTurn2();
					plyDamageInTurn += plyerTurn3();
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
						plyDamageInTurn += plyerTurn2();
						plyDamageInTurn += plyerTurn3();
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

            statusAilmentsHandler(); //burn, poison, HoT, paralysis statuses
			playerMons[playerMonActive].ability.trigger_endOfTurn();
			cpuMons[cpuMonActive].ability.trigger_endOfTurn();

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
	
	@Override
	public Pokemon getCloneMon(int turnOf){
		switch(turnOf){
			case 1:
				return playerMons[playerMonActive];
			case 2:
				return cpuMons[cpuMonActive];
			case 3:
				return playerMons[playerMonActive2];
			case 4:
				return playerMons[playerMonActive3];
			default:
				return new Pokemon("Custom");
		}

	}
	
	@Override
	public Pokemon getEnemyMon(int turnOf){
		switch(turnOf){
			case 1,3,4:
				return cpuMons[cpuMonActive];
			case 2:
				return playerMons[playerMonActive];
			default:
				return new Pokemon("Custom");
		}
	}
	
	@Override
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
            if (turnOf == 1 || turnOf == 3 || turnOf == 4) {
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
                    if (turnOf == 1 || turnOf == 3 || turnOf == 4) {
                        numbHits += countAliveMonInTeam(playerMons);
                    } else {
                        numbHits += countAliveMonInTeam(cpuMons);
                    }
                    numbHits--;
                    break;
                case "reverseGroupB":
                    if (turnOf == 1 || turnOf == 3 || turnOf == 4) {
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
                    if (turnOf == 1 || turnOf == 3 || turnOf == 4) {
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
            if (turnOf == 1 || turnOf == 3 || turnOf == 4) {
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

            if (turnOf == 1 || turnOf == 3 || turnOf == 4) {
                statusPlayerHandler(cloneMon.moveset[0][selectedMove]);
            } else {
                statusCPUHandler(cloneMon.moveset[0][selectedMove]);
            }
            wair(s, 2);
        }
		
		return trueDmg; //returns damage dealt
    }

	
	@Override
	public int plyerTurn() throws IOException, InterruptedException {
        return pokemonBattleSequence(playerMons[playerMonActive], cpuMons[cpuMonActive], 1, moveSelec);
    }
	
	public int plyerTurn2() throws IOException, InterruptedException {
        return pokemonBattleSequence(playerMons[playerMonActive2], cpuMons[cpuMonActive], 3, moveSelec3);
    }
	
	public int plyerTurn3() throws IOException, InterruptedException {
        return pokemonBattleSequence(playerMons[playerMonActive3], cpuMons[cpuMonActive], 4, moveSelec4);
    }
	
	@Override
	public void printBattleHUDThing(int playerHit, Clr ANSIcolor, String msg) throws IOException {
        //playerHit.. 1=ye, 2=cpu hit. other = do nothing
        //ANSIcolor = get ANSI escape sequence from enum Clr

        if (playerHit != 1 && playerHit != 2) {
            ANSIcolor = Clr.R;
        }

        int plyAliveMon = countAliveMonInTeam(playerMons);
        int cpuAliveMon = countAliveMonInTeam(cpuMons);

        String pk1Name = playerMons[playerMonActive].name;
        String pk2Name = cpuMons[cpuMonActive].name;
		String pk3Name = playerMons[playerMonActive2].name;
		String pk4Name = playerMons[playerMonActive3].name;
		
        int pk1HP = playerMons[playerMonActive].currentHP;
        int pk2HP = cpuMons[cpuMonActive].currentHP;
		int pk3HP = playerMons[playerMonActive2].currentHP;
		int pk4HP = playerMons[playerMonActive3].currentHP;
		

        String cl1 = Color.getHPColor(playerMons[playerMonActive]) + "";
        String cl2 = Color.getHPColor(cpuMons[cpuMonActive]) + "";
		String cl3 = Color.getHPColor(playerMons[playerMonActive2]) + "";
		String cl4 = Color.getHPColor(playerMons[playerMonActive3]) + "";

        String spaces = "";
		String par = Clr.YELLOW_B + "[PAR]" + Clr.R,
			   psn = Clr.MAGENTA_B + "[PSN]" + Clr.R,
			   brn = Clr.RED_B + "[BRN]" + Clr.R;
        String pk1Conditions = " ", pk2Conditions = " ";
		String pk3Conditions = " ";
		String pk4Conditions = " ";

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
		
		if (playerMons[playerMonActive2].isParalized) {
            pk3Conditions += par;
            
        }
        if (playerMons[playerMonActive2].isPoisoned) {
            pk3Conditions += psn;
            
        }
        if (playerMons[playerMonActive2].isBurning) {
            pk3Conditions += brn;
            
        }
		
		if (playerMons[playerMonActive3].isParalized) {
            pk4Conditions += par;
            
        }
        if (playerMons[playerMonActive3].isPoisoned) {
            pk4Conditions += psn;
            
        }
        if (playerMons[playerMonActive3].isBurning) {
            pk4Conditions += brn;
            
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
		
		// -------- PRINTS THE REST OF THE PARTY TEAM!!  ----------- //

        //---- displays the name of pk3 ----//
        cout.write(" " + pk3Name);
		cout.write("\n");
		
        cout.write(" HP: " + cl3 + pk3HP + Clr.R);
		cout.write("\n");

        cout.write(pk3Conditions);
        cout.write("\n");

		//---- displays the name of pk4 ----//
        cout.write(" " + pk4Name);
		cout.write("\n");

        cout.write(" HP: " + cl4 + pk4HP + Clr.R);
		cout.write("\n");

        cout.write(pk4Conditions);
        cout.write("\n");
		
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
        // [PAR][PSN][BRN]			     [PAR][PSN][BRN] 
		// name3                                    
        // HP:69                                    
        // [PAR][PSN][BRN]
		// name4                                   
        // HP:69                                    
        // [PAR][PSN][BRN]
		//
        // name1 used Quick Attack!
    }
	
	public void printPlayerActivePkmnMoveset(boolean plyWillMegaEvolve, int turnOf) throws IOException {
        Pokemon mon = null;
		
		switch (turnOf){
			case 1:
				mon = playerMons[playerMonActive];
				break;
			case 3:
				mon = playerMons[playerMonActive2];
				break;
			case 4:
				mon = playerMons[playerMonActive3];
				break;
		}
		
        if (plyWillMegaEvolve && plyCanMegaEvolve) {
			if (mon.name.equals("Eevee")) {
				mon.moveset[0][0] = "???";
				mon.moveset[0][1] = "???";
				mon.moveset[0][2] = "???";
				mon.moveset[0][3] = "???";
			} else {
				switch (turnOf) {
					case 1:
						mon = new Pokemon(playerMons[playerMonActive].name);
						break;
					case 3:
						mon = new Pokemon(playerMons[playerMonActive2].name);
						break;
					case 4:
						mon = new Pokemon(playerMons[playerMonActive3].name);
						break;
				}
				mon.megaEvolve();//display megaevolved moves
			}
		}
		
        int coumter = 0;
        for (int i = 0; i < mon.moveset[0].length; i++) {//they always got 4 moves frfr <- not anymore i guess
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
    }

	@Override
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
	
}
