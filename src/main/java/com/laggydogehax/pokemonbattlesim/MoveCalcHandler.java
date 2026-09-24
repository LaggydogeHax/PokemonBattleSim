package com.laggydogehax.pokemonbattlesim;

import static com.laggydogehax.pokemonbattlesim.PokemonBattleSim.cpuMonActive;
import static com.laggydogehax.pokemonbattlesim.PokemonBattleSim.cpuMons;
import static com.laggydogehax.pokemonbattlesim.PokemonBattleSim.playerMonActive;
import static com.laggydogehax.pokemonbattlesim.PokemonBattleSim.playerMons;

class MoveCalcHandler {
	int atk1;
	int nHits;
	int extraD;
	int baseatk1;
	double doEmStab;
	int def2;
	String movename;
	
	Pokemon pkmn1;
	Pokemon pkmn2;
	
	int moveInteger;
	int turnOf;
	
	public MoveCalcHandler(Pokemon pkmn1, Pokemon pkmn2, int moveInteger, int turnOf){
		this.atk1 = pkmn1.currentATK;
        this.nHits = pkmn1.numberOfHits;
        this.extraD = pkmn1.extraDmg;
        this.baseatk1 = pkmn1.baseATK;
        this.doEmStab = baseatk1;
        this.def2 = pkmn2.currentDEF;
        this.movename = pkmn1.moveset[0][moveInteger]; //gets move name blablabla
		
		this.pkmn1 = pkmn1;
		this.pkmn2 = pkmn2;
		this.moveInteger = moveInteger;
		this.turnOf = turnOf;
	}
	
	//template method jumpscare
	public void specialMoveCalculator() {
		switch (pkmn1.isSpecialMove(moveInteger)) {
            case "lifedrain":
                this.lifedrain();
                break;
            case "overclock":
                this.overclock();
                break;
            case "doublehit":
                this.doublehit();
                break;
            case "powerboost"://hyperbeam and some others
                this.powerboost();
                break;
            case "debuffselfdef":
                this.debuffselfdef();
                break;
            case "ignoredef":
                this.ignoredef();
                break;
            case "defisatk":
                this.defisatk();
                break;
            case "selfdefisatk":
                this.selfdefisatk();
                break;
            case "recoil":
                this.recoil();
                break;
            case "rngMultihit":
                this.rngMultihit();
                break;
            case "plus2hit":
                this.plus2hit();
                break;
            case "plus3hit":
                this.plus3hit();
                break;
            case "adversity":
                this.adversity();
                break;
            case "adversity2":
                this.adversity2();
                break;
            case "supEffective":
                this.supEffective();
                break;
            case "groupB":
                this.groupB();
                break;
            case "reverseGroupB":
                this.reverseGroupB();
                break;
            case "MegaEvolutionHater":
                this.MegaEvolutionHater();
                break;
            case "kamikaze":
                this.kamikaze();
                break;
            case "thundercage":
                this.thundercage();
                break;
            case "magnitude":
                this.magnitude();
                break;
            case "buffPowerIfDebuffed":
                this.buffPowerIfDebuffed();
                break;
            case "osmash":
                this.osmash();
                break;
            case "guaranteedCrit":
                this.guaranteedCrit();
                break;
            case "facade":
                this.facade();
                break;
            case "brokenCardMove":
                this.brokenCardMove();
                break;
            case "scnails":
                this.scnails();
                break;
            case "rngPoisonBurnPara":
                this.rngPoisonBurnPara();
                break;
            case "nihilLight":
                this.nihilLight();
                break;
			case "staticstrike":
				this.staticStrike();
				break;
        }//special move switch ends
	}

	private void lifedrain() {
		if (movename.equals("Excite")) {
			this.adversity();
		}
		atk1 -= atk1 / 3;
	}
	
	private void overclock() {
		atk1 *= 1.8;//DOUBLE ATK, WOOOOOOO-- nvm it was too op
	}
	
	private void doublehit() {
		atk1 -= atk1 / 3;
	}
	
	private void powerboost() {
		atk1 += atk1 / 2;
	}
	
	private void debuffselfdef() {
		atk1 *= 1.2;
	}
	
	private void ignoredef() {
		atk1 -= atk1 / 4;
		def2 = 0;
	}
	
	private void defisatk() {
		atk1 = def2;
		def2 /= 2;
	}

	private void selfdefisatk() {
		atk1 = pkmn1.currentDEF;
	}

	private void recoil() {
		atk1 += atk1 / 3;
	}

	private void rngMultihit() {
		atk1 = atk1 / 3; //aughgh
		doEmStab /= 2; //nerf stab ._.
		doEmStab /= 4;
		nHits += extraD;
	}

	private void plus2hit() {
		atk1 *= 0.25;
		doEmStab /= 2;
		nHits += 2;
	}

	private void plus3hit() {
		atk1 -= atk1 / 1.85;
		doEmStab /= 3;
		nHits += 3;
	}

	private void adversity() {
		int lostHP = pkmn1.baseHP - pkmn1.currentHP;
		atk1 += lostHP / 2;
		if (movename.equals("X")) {
			nHits += 1;
			atk1 -= atk1 / 3;
		}
		if (pkmn1.energyDrink) {
			atk1 -= atk1 / 3;
		}
	}

	private void adversity2() {
		atk1 /= 3;
		int lostHP = pkmn1.baseHP - pkmn1.currentHP;
		atk1 += lostHP / 2;
		if (pkmn1.energyDrink) {
			atk1 -= atk1 / 3;
		}
	}

	private void supEffective() {
		atk1 -= atk1 / 3;
	}

	private void groupB() {
		Pokemon[] monlist = null;
		int monactive = 0;
		if (turnOf == 0) {
			monlist = new Pokemon[playerMons.length];
			monlist = playerMons;
			monactive = playerMonActive;
		} else {
			monlist = new Pokemon[cpuMons.length];
			monlist = cpuMons;
			monactive = cpuMonActive;
		}
		int reduce = 1;
		if (monlist.length > 3) {
			reduce = 14;
		} else {
			reduce = 4;
		}

		atk1 /= reduce;
		for (int i = 0; i < monlist.length; i++) {
			if (i != monactive && monlist[i].currentHP > 0) {
				atk1 += monlist[monactive].baseATK / reduce;
				nHits += 1;
			}
		}
	}

	private void reverseGroupB() {
		Pokemon monlist[] = null;
		int reduce = 1;
		
		if (turnOf == 1) {
			monlist = new Pokemon[playerMons.length];
			monlist = playerMons;
		} else {
			monlist = new Pokemon[cpuMons.length];
			monlist = cpuMons;
		}

		if (monlist.length > 3) {
			reduce = 5;
		} else {
			reduce = 3;
		}

		atk1 /= reduce;
		for (Pokemon mon : monlist) {
			if (mon.currentHP > 0) {
				atk1 += baseatk1 / reduce;
			}
		}
	}

	private void MegaEvolutionHater() {
		if (pkmn2.megaEvolved) {
			atk1 *= 2;
		}
	}

	private void avenger() {
		Pokemon monlist[] = null;
		int reduce = 0;
		//fallen allies = more power for this move
		atk1 -= atk1 / 4;
		if (turnOf == 0) {
			monlist = new Pokemon[playerMons.length];
			monlist = playerMons;
		} else {
			monlist = new Pokemon[cpuMons.length];
			monlist = cpuMons;
		}

		if (monlist.length > 3) {
			reduce = 4;
		} else {
			reduce = 2;
		}

		for (Pokemon mon : monlist) {
			if (mon.currentHP < 1) {
				atk1 += baseatk1 / reduce;
			}
		}
		if (PokemonBattleSim.countAliveMonInTeam(playerMons) == 1 && turnOf == 0) {
			nHits += 1;
		}
		if (PokemonBattleSim.countAliveMonInTeam(cpuMons) == 1 && turnOf == 1) {
			nHits += 1;
		}
	}

	private void kamikaze() {
		atk1 += doEmStab;
		atk1 *= 4; //AAAAAAAAAAAAA
	}

	private void thundercage() {
		atk1 += pkmn2.baseHP / 8;
	}

	private void magnitude() {
		int mag = pkmn1.extraDmg;
		atk1 /= 3;
		atk1 *= mag;
		nHits += mag / 4;
	}

	private void buffPowerIfDebuffed() {
		if ((pkmn1.currentATK < pkmn1.baseATK) || (pkmn1.currentDEF < pkmn1.baseDEF)
			|| (pkmn1.currentSPEED < pkmn1.baseSPEED)) {
			atk1 += baseatk1 / 2;
		} else {
			atk1 -= atk1 / 3;
		}
	}

	private void osmash() {
		atk1 -= atk1 / 3;
	}

	private void guaranteedCrit() {
		atk1 -= atk1 / 5;
	}

	private void facade() {
		atk1 -= atk1 / 5;
		if (pkmn1.hasStatusAilment()) {
			atk1 *= 2; // DOUBLE ATK WOOOOO
		}
	}

	private void brokenCardMove() {
		atk1 -= atk1 / 3;
	}

	private void scnails() {
		Pokemon monlist[] = null;
		atk1 /= 2;
		int monactive = 0;
		if (turnOf == 1) {
			monlist = new Pokemon[playerMons.length];
			monlist = playerMons;
			monactive = playerMonActive;
		} else {
			monlist = new Pokemon[cpuMons.length];
			monlist = cpuMons;
			monactive = cpuMonActive;
		}

		if (monlist[monactive].currentHP <= ((monlist[monactive].baseHP / 3) * 2)) {
			atk1 *= 4;
		}
	}

	private void rngPoisonBurnPara() {
		atk1 -= atk1 / 5;
	}

	private void nihilLight() {
		atk1 *= 3; //perfectly balanced
		if (def2 > pkmn2.baseDEF) {
			def2 = pkmn2.baseDEF;
		}
	}
	
	private void staticStrike(){
		atk1 -= atk1/2;
		if(pkmn2.isParalized){
			nHits +=2;
		}
	}
}
