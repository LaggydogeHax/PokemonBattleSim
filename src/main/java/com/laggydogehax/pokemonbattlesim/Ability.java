package com.laggydogehax.pokemonbattlesim;

class AbilityFactory{
	public static Ability create(Pokemon nam){ //ENORMOUS SWITCH STATEMENT!!!!!!!!!!!!!!!
		switch(nam.name){
			default:
				return new Ability();
			
			case "Venusaur": return new Ability_Overgrow(nam);
				
			case "Charizard": return new Ability_Blaze(nam);
					
			case "Blastoise": return new Ability_Torrent(nam);
				
			case "Meowscarada": return new Ability_Protean(nam);
			
			case "Ninetales": return new Ability_FlashFire(nam);
				
			case "Empoleon": return new Ability_Competitive(nam);
				
			case "Raichu": return new Ability_LightningRod(nam);
				
			case "Mewtwo":
				
			break;
			case "Gengar": return new Ability_Levitate(nam);
				
			case "Dragonite": return new Ability_Levitate(nam);
				
			case "Absol": return new Ability_SuperLuck(nam);
				
			case "Gardevoir": return new Ability_Trace(nam);
				
			case "Glaceon": return new Ability_IceBody(nam);
				
			case "Luxray": return new Ability_Guts(nam);

			case "Lucario": return new Ability_Justified(nam);
			
			case "Duraludon":
				
			break;
			case "Mismagius": return new Ability_Levitate(nam);
				
			case "Golisopod":
				
			break;
			case "Heracross": return new Ability_Moxie(nam);
				
			case "Rampardos":
				
			break;
			case "Lycanroc":
				
			break;
			case "Aurorus": return new Ability_Refrigerate(nam);
				
			case "Dugtrio": return new Ability_SandRush(nam);
				
			case "Sandlash": return new Ability_SandRush(nam);
				
			case "Arbok":
				
			break;
			case "Sneasler": return new Ability_Unburden(nam);
				
			case "Pidgeot":
				
			break;
			case "Lugia": return new Ability_Multiscale(nam);
				
			case "Urshifu":
				
			break;
			case "Audino":
				
			break;
			case "Tauros":
				
			break;
			case "Sylveon":
				return new Ability_Pixilate(nam);

			case "Tinkaton":
				
			break;
			case "Zarude":
				
			break;
			case "Dragapult":
				
			break;
			case "Mawile":
				
			break;
			//--------wave 2 of pokemen--------//
			case "Blaziken": return new Ability_SpeedBoost(nam);
				
			case "Vaporeon": return new Ability_WaterAbsorb(nam);
				
			case "Ursaluna":
				
			break;
			case "Decidueye": return new Ability_Overgrow(nam);
				
			case "Flareon": return new Ability_Guts(nam);
				
			case "Lapras":
				
			break;
			case "Tsareena":
				
			break;
			case "Braviary":
				
			break;
			case "Toxtricity":
				
			break;
			case "Krookodile":
				
			break;
			case "Toucannon":
				
			break;
			case "Zeraora":
				
			break;
			case "Weezing":
				
			break;
			case "Drapion":
				
			break;
			case "Walking Wake":
				
			break;
			case "Roaring Moon":
				
			break;
			case "Togekiss":
				
			break;
			case "Florges":
				
			break;
			case "Lopunny": return new Ability_Limber(nam);
				
			case "Cinccino": return new Ability_Guts(nam);
				
			case "Hawlucha":
				
			break;
			case "Flutter Mane":
				
			break;
			case "Trevenant":
				
			break;
			case "Volcarona":
				
			break;
			case "Vespiquen":
				
			break;
			case "Pangoro":
				
			break;
			case "Aggron":
				
			break;
			case "Scizor":
				
			break;
			case "Mew":
				
			break;
			case "Alakazam":
				
			break;
			case "Froslass":
				
			break;
			case "Baxcalibur":
				
			break;
			case "Hydreigon":
				
			break;
			case "Zoroark":
				
			break;
			case "Solrock":
				
			break;
			case "Lunatone":
				
			break;
			//-------- wave 3 ---------//
			case "Delphox": return new Ability_Blaze(nam);
				
			case "Gyarados":
				
			break;
			case "Sceptile":
				
			break;
			case "Typhlosion": return new Ability_FlashFire(nam);
				
			case "Greninja": return new Ability_Protean(nam);

			case "Leafeon":
				
			break;
			case "Donphan":
				
			break;
			case "Corviknight":
				
			break;
			case "Umbreon": return new Ability_Synchronize(nam);
				
			case "Jolteon": return new Ability_QuickFeet(nam);
				
			case "Espeon":
				
			break;
			case "Eevee":
				
			break;
			case "Arceus":
				
			break;
			case "Citrus":
				
			break;
			case "Toxicroak":
				
			break;
			case "Cyclizar":
				
			break;
			case "Garchomp":
				
			break;
			case "Gholdengo":
				
			break;
			case "Galvantula":
				
			break;
			case "Ceruledge":
				
			break;
			case "Chandelure":
				
			break;
			case "Flamigo":
				
			break;
			case "Zamazenta":
				
			break;
			case "Zacian":
				
			break;
			case "Magearna":
				
			break;
			case "Celebi ex":
				
			break;
			case "Cresselia":
				
			break;
			case "Kingambit":
				
			break;
			case "Azumarill": return new Ability_Guts(nam);
				
			case "Gallade":
				
			break;
			case "Regieleki":
				
			break;
			case "Seviper":
				
			break;
			case "Garganacl":
				
			break;
			case "Diance":
				
			break;
			case "Weavile":
				
			break;
			case "Chien-Pao":
				
			break;
			case "Yanmega":
				
			break;
			case "Kleavor":
				
			break;
			case "ADP GX":
				
			break;
			case "Missing No":
				
			break;
		}
		
		return new Ability();

	}
	
}

//this is practically a skeleton
class Ability{
	Pokemon me;
	String name="";
	
	public Ability(){
		this.name = "";
		//this.defineTriggerTime();
	}
	
	
	//template methods jumpscare
	public void trigger_beforeMove(){
		
	}
	
	public void trigger_beforeMove(int moveSelec){
		this.trigger_beforeMove();
	}
	
	public void trigger_startOfTurn(){
		
	}
	
	public void trigger_startOfTurn(Pokemon enemyMon){
		this.trigger_startOfTurn();
	}
	
	public void trigger_afterGettingHit(){
		
	}
	
	public void trigger_afterGettingHit(Pokemon enemyMon, int enemySelec){
		this.trigger_afterGettingHit();
	}
	
	public void trigger_beforeGettingHit(){
		
	}
	
	public void trigger_beforeGettingHit(Pokemon enemyMon, int enemySelec){
		this.trigger_beforeGettingHit();
	}
	
	public void trigger_endOfTurn(){
		
	}
	
	public void trigger_endOfTurn(Pokemon enemyMon, int moveSelec){
		this.trigger_endOfTurn();
	}
	
}

class Ability_Pixilate extends Ability {
	
	
	public Ability_Pixilate(Pokemon me) {
		this.me = me;
		this.name="Pixilate";
	}
	
	@Override
	public void trigger_beforeMove() {
		for (int i = 0; i < 4; i++) {
			if (me.moveset[1][i].equals("Normal Attack")) {
				me.moveset[1][i] = "Fairy Attack";
			}
		}
	}
	
	@Override
	public void trigger_endOfTurn(){
		me.defineAllMoves();
	}
	
}

class Ability_SuperLuck extends Ability{
	
	public Ability_SuperLuck(Pokemon me) {
		this.me = me;
		this.name="Super Luck";
	}
	
	@Override
	public void trigger_beforeMove() {
		me.currentSPEED *= 2;
	}
	
	@Override
	public void trigger_endOfTurn(){
		me.currentSPEED /= 2;
	}
}

class Ability_Protean extends Ability {
	
	public Ability_Protean(Pokemon me) {
		this.me = me;
		this.name="Protean";
	}
	
	@Override
	public void trigger_beforeMove(int moveSelec) {
		String typeToUse = "";

		if (me.moveIsAnAttack(moveSelec)) {
			for (int i = 0; i < me.moveset[1][moveSelec].length(); i++) {

				if (me.moveset[1][moveSelec].charAt(i) != ' ') {
					typeToUse += me.moveset[1][moveSelec].charAt(i);
				} else {
					break;
				}
			}

			me.type = typeToUse;
			me.type2 = "";

			me.setTypesWnR();
			if (me.energyDrink) {
				me.resists = new String[]{"Nothing!"};
			}
		}
	}
}

class Ability_Justified extends Ability {
	
	public Ability_Justified(Pokemon me) {
		this.me = me;
		this.name="Justified";
	}
	
	@Override
	public void trigger_afterGettingHit(Pokemon enemyMon, int enemySelec){
		if(enemyMon.moveset[1][enemySelec].contains("Dark")){
			me.raiseStat("ATK");
		}
	}
}

class Ability_Overgrow extends Ability{
	
	public Ability_Overgrow(Pokemon me){
		this.me = me;
		this.name = "Overgrow";
	}
	
	@Override
	public void trigger_beforeMove(int moveSelec){
		if(me.currentHP < me.baseHP/3 && me.moveset[1][moveSelec].contains("Grass")){
			me.currentATK += me.baseATK/2;
		}
	}
	
	@Override
	public void trigger_endOfTurn(Pokemon enemyMon, int moveSelec){
		if(me.currentHP < me.baseHP/3 && me.moveset[1][moveSelec].contains("Grass")){
			me.currentATK -= me.baseATK/2;
		}
	}
}

class Ability_Blaze extends Ability{
	
	public Ability_Blaze(Pokemon me){
		this.me = me;
		this.name = "Blaze";
	}
	
	@Override
	public void trigger_beforeMove(int moveSelec){
		if(me.currentHP < me.baseHP/3 && me.moveset[1][moveSelec].contains("Fire")){
			me.currentATK += me.baseATK/2;
		}
	}
	
	@Override
	public void trigger_endOfTurn(Pokemon enemyMon, int moveSelec){
		if(me.currentHP < me.baseHP/3 && me.moveset[1][moveSelec].contains("Fire")){
			me.currentATK -= me.baseATK/2;
		}
	}
}

class Ability_Torrent extends Ability{
	
	public Ability_Torrent(Pokemon me){
		this.me = me;
		this.name = "Torrent";
	}
	
	@Override
	public void trigger_beforeMove(int moveSelec){
		if(me.currentHP < me.baseHP/3 && me.moveset[1][moveSelec].contains("Water")){
			me.currentATK += me.baseATK/2;
		}
	}
	
	@Override
	public void trigger_endOfTurn(Pokemon enemyMon, int moveSelec){
		if(me.currentHP < me.baseHP/3 && me.moveset[1][moveSelec].contains("Water")){
			me.currentATK -= me.baseATK/2;
		}
	}
}

class Ability_FlashFire extends Ability{
	
	public Ability_FlashFire(Pokemon me){
		this.me = me;
		this.name = "Flash Fire";
	}
	
	@Override
	public void trigger_beforeGettingHit(Pokemon enemyMon, int enemySelec){
		if(enemyMon.moveset[1][enemySelec].contains("Fire")){
			me.currentATK += me.baseATK/2;
			me.currentDEF += me.baseDEF/2;
		}
	}
	
	@Override
	public void trigger_afterGettingHit(Pokemon enemyMon, int enemySelec){
		if(enemyMon.moveset[1][enemySelec].contains("Fire")){
			me.currentDEF -= me.baseDEF/2;
		}
	}
	
	@Override
	public void trigger_endOfTurn(){
		if(me.isBurning){
			me.currentATK += me.baseATK/2;
			me.isBurning = false;
			me.permaBurn = false;
		}
	}
}

class Ability_Competitive extends Ability{
	
	public Ability_Competitive(Pokemon me){
		this.me = me;
		this.name = "Competitive";
	}
	
	@Override
	public void trigger_afterGettingHit(Pokemon enemyMon, int enemySelec){
		switch(enemyMon.statusMoveHandler(enemySelec)){
			case "debuffdef","debuffdef2","debuffatk","debuffatk2","debuffspeed2","debuffspeed":
				me.raiseStat("ATK");
				me.raiseStat("ATK");
			break;
		}
	}
}

class Ability_LightningRod extends Ability {
	
	public Ability_LightningRod(Pokemon me){
		this.me = me;
		this.name = "Lightning Rod";
	}
	
	@Override
	public void trigger_beforeGettingHit(Pokemon enemyMon, int enemySelec){
		if(enemyMon.moveset[1][enemySelec].contains("Electric")){
			me.currentATK += me.baseATK/2;
			me.currentDEF += me.baseDEF/2;
		}
	}
	
	@Override
	public void trigger_afterGettingHit(Pokemon enemyMon, int enemySelec){
		if(enemyMon.moveset[1][enemySelec].contains("Electric")){
			me.currentDEF -= me.baseDEF/2;
		}
	}
	
}

class Ability_Trace extends Ability{
	
	public Ability_Trace(Pokemon me){
		this.me = me;
		this.name = "Trace";
	}
	
	@Override
	public void trigger_startOfTurn(Pokemon enemyMon){
		if(!enemyMon.ability.name.equals("") && !enemyMon.ability.name.equals("Trace")){
			me.ability = AbilityFactory.create(enemyMon);
		}
		
	}
}

class Ability_IceBody extends Ability{
	
	public Ability_IceBody(Pokemon me){
		this.me = me;
		this.name = "Ice Body";
	}
	
	@Override
	public void trigger_beforeMove(int moveSelec){
		if(me.moveset[1][moveSelec].contains("Ice")){
			me.healOverTime();
		}
	}
}


class Ability_Guts extends Ability{
	
	public Ability_Guts(Pokemon me){
		this.me = me;
		this.name = "Guts";
	}
	
	@Override
	public void trigger_beforeMove(){
		if(me.hasStatusAilment()){
			me.raiseStat("ATK");
			me.raiseStat("ATK");
		}
	}
	
	@Override
	public void trigger_endOfTurn(){
		if(me.hasStatusAilment()){
			me.decreaseStat("ATK");
			me.decreaseStat("ATK");
		}
	}
}

class Ability_Levitate extends Ability{
	
	public Ability_Levitate(Pokemon me){
		this.me = me;
		this.name = "Levitate";
	}
	
	@Override //can't add full immunity so this will do for now
	public void trigger_beforeGettingHit(Pokemon enemyMon,int moveSelec){
		if(enemyMon.moveset[1][moveSelec].contains("Ground")){
			enemyMon.currentATK -= enemyMon.baseATK;
			me.currentDEF += me.baseDEF*4;
		}
	}
	
	@Override
	public void trigger_afterGettingHit(Pokemon enemyMon,int moveSelec){
		if(enemyMon.moveset[1][moveSelec].contains("Ground")){
			enemyMon.currentATK += enemyMon.baseATK;
			me.currentDEF -= me.baseDEF*4;
		}
	}
}

class Ability_SpeedBoost extends Ability{
	
	public Ability_SpeedBoost(Pokemon me){
		this.me = me;
		this.name = "Speed Boost";
	}
	
	@Override
	public void trigger_endOfTurn(){
		me.raiseStat("SPEED");
	}
}

class Ability_WaterAbsorb extends Ability{
	
	public Ability_WaterAbsorb(Pokemon me){
		this.me = me;
		this.name = "Water Absorb";
	}
	
	@Override
	public void trigger_afterGettingHit(Pokemon enemyMon, int moveSelec){
		if(enemyMon.moveset[1][moveSelec].contains("Water")){
			me.healSelf("quarter");
		}
	}
}

class Ability_Synchronize extends Ability{
	
	public Ability_Synchronize(Pokemon me){
		this.me = me;
		this.name = "Synchronize";
	}
	
	@Override
	public void trigger_endOfTurn(Pokemon enemyMon, int moveSelec){
		if(me.isBurning){
			enemyMon.isBurning = true;
		}
		
		if(me.isParalized){
			enemyMon.isParalized = true;
		}
		
		if(me.isPoisoned){
			enemyMon.isPoisoned = true;
		}
	}
}

class Ability_Unburden extends Ability{
	
	public Ability_Unburden(Pokemon me){
		this.me = me;
		this.name = "Unburden";
	}
	
	@Override
	public void trigger_startOfTurn(){
		if(me.items.length < 2){
			me.currentSPEED *= 2;
		}
	}
	
	@Override
	public void trigger_endOfTurn(){
		if(me.items.length < 2){
			me.currentSPEED /= 2;
		}
	}
}

class Ability_Moxie extends Ability {
	
	public Ability_Moxie(Pokemon me){
		this.me = me;
		this.name = "Moxie";
	}
	
	@Override
	public void trigger_endOfTurn(Pokemon enemyMon, int moveSelec){
		if(enemyMon.currentHP < 1){
			this.me.raiseStat("ATK");
			this.me.raiseStat("ATK");
		}
	}
}

class Ability_Refrigerate extends Ability {
	
	public Ability_Refrigerate(Pokemon me){
		this.me = me;
		this.name = "Refrigerate";
	}
	
	@Override
	public void trigger_beforeMove(int moveSelec){
		if(this.me.moveset[1][moveSelec].contains("Normal")){
			this.me.raiseStat("ATK");
		}
	}
	
	@Override
	public void trigger_endOfTurn(Pokemon enemyMon, int moveSelec){
		if(this.me.moveset[1][moveSelec].contains("Normal")){
			this.me.decreaseStat("ATK");
			this.me.moveset[1][moveSelec] = "Ice Attack";
		}
	}
}

class Ability_SandRush extends Ability {
	
	public Ability_SandRush(Pokemon me){
		this.me = me;
		this.name = "Sand Rush";
	}
	
	@Override
	public void trigger_startOfTurn(){
		if(this.me.hasStatusAilment()){
			this.me.currentSPEED *= 2;
		}
	}
	
	@Override
	public void trigger_endOfTurn(){
		if(this.me.hasStatusAilment()){
			this.me.currentSPEED /= 2;
		}
	}
	
}

class Ability_Multiscale extends Ability{
	private boolean gothit = false;
	
	public Ability_Multiscale(Pokemon me){
		this.me = me;
		this.name = "Multiscale";
	}
	
	@Override
	public void trigger_beforeGettingHit(){
		if(this.me.isAtMaxHP()){
			gothit = true;
			this.me.raiseStat("DEF");
			this.me.raiseStat("DEF");
		}
	}
	
	@Override
	public void trigger_afterGettingHit(){
		if(gothit){
			gothit = false;
			this.me.decreaseStat("DEF");
			this.me.decreaseStat("DEF");
		}
	}
}

class Ability_Limber extends Ability {
	
	public Ability_Limber(Pokemon nam){
		this.me = nam;
		this.name = "Limber";
	}
	
	@Override
	public void trigger_endOfTurn(){
		if(this.me.isParalized){
			this.me.isParalized = false;
		}
	}
}

class Ability_QuickFeet extends Ability {
	private boolean triggerd = false;
	
	public Ability_QuickFeet(Pokemon nam){
		this.me = nam;
		this.name = "Quick Feet";
	}
	
	@Override
	public void trigger_startOfTurn(){
		if(this.me.hasStatusAilment()){
			triggerd = true;
			this.me.raiseStat("SPEED");
			this.me.raiseStat("SPEED");
		}
	}
	
	@Override
	public void trigger_endOfTurn(){
		if(triggerd){
			triggerd = false;
			this.me.decreaseStat("SPEED");
			this.me.decreaseStat("SPEED");
		}
	}
}

