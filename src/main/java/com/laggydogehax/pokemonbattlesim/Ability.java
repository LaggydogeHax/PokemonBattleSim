package com.laggydogehax.pokemonbattlesim;

//this is practically a skeleton
class Ability{
	Pokemon me;
	String name="";
	String[] desc = new String[1];
	
	public Ability(){
		this.name = "";
		this.desc[0] = "";
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
	
	public void trigger_switchedOut(){
		
	}
	
	public void trigger_switchedIn(){
		
	}
	
	public void trigger_switchedIn(Pokemon enemyMon){
		this.trigger_switchedIn();
	}
	
	public void trigger_turnInBench(){
		
	}
	
}

class Ability_Pixilate extends Ability {
	
	public Ability_Pixilate(Pokemon me) {
		this.me = me;
		this.name="Pixilate";
		this.desc= new String[]{"All Normal-type attacks from this Pokemon",
		"are treated as if they were Fairy-type"};
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
		this.desc= new String[]{"Critical hit chance is","x2 higher for this Pokemon."};
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
		this.desc= new String[]{"Before the Pokemon uses a move,",
			"it becomes a pure Pokemon of that type."};
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

			me.setType(typeToUse);
			
		}
	}
}

class Ability_Justified extends Ability {
	
	public Ability_Justified(Pokemon me) {
		this.me = me;
		this.name="Justified";
		this.desc = new String[]{"When this Pokemon gets hit by a",
		"Dark-type move, its attack raises by 25%"};
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
		this.desc = new String[]{"When HP is below 1/3rd its maximum,",
			"the power of Grass-type moves is increased by 50%."};
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
		this.desc = new String[]{"When HP is below 1/3rd its maximum,",
			"the power of Fire-type moves is increased by 50%."};
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
		this.desc = new String[]{"When HP is below 1/3rd its maximum,",
			"the power of Water-type moves is increased by 50%."};
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

class Ability_Swarm extends Ability{
	
	public Ability_Swarm(Pokemon me){
		this.me = me;
		this.name = "Swarm";
		this.desc = new String[]{"When HP is below 1/3rd its maximum,",
			"the power of Bug-type moves is increased by 50%."};
	}
	
	@Override
	public void trigger_beforeMove(int moveSelec){
		if(me.currentHP < me.baseHP/3 && me.moveset[1][moveSelec].contains("Bug")){
			me.currentATK += me.baseATK/2;
		}
	}
	
	@Override
	public void trigger_endOfTurn(Pokemon enemyMon, int moveSelec){
		if(me.currentHP < me.baseHP/3 && me.moveset[1][moveSelec].contains("Bug")){
			me.currentATK -= me.baseATK/2;
		}
	}
}

class Ability_FlashFire extends Ability{
	
	public Ability_FlashFire(Pokemon me){
		this.me = me;
		this.name = "Flash Fire";
		this.desc = new String[]{"Raises ATK by 50% when the Pokemon",
			"gets hit by a Fire-type move or",
			"when it's burning, also cures",
			"the burning status."};
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
		this.desc = new String[]{"Raises ATK by 50% when the opponent",
			"uses a Status move that decreases any stat."};
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
		this.desc = new String[]{"Raises ATK by 50% when the Pokemon",
			"gets hit by an Electric-type move"};
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
		this.desc = new String[]{"Raises ATK by 50% when the Pokemon",
			"gets hit by a Fire-type move or"};
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
		this.desc = new String[]{"Heals itself a bit when using",
			"an Ice-type move."};
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
		this.desc = new String[]{"Raises ATK by 50% when the Pokemon",
			"has a status-ailment."};
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
		this.desc = new String[]{"Grants huge damage reduction against",
			"Ground-type moves."};
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
		this.desc = new String[]{"Raises SPEED by 25% at",
			"the end of every turn."};
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
	public void trigger_beforeGettingHit(Pokemon enemyPokemon, int move){
		if(this.me.isAtMaxHP() && enemyPokemon.moveIsAnAttack(move)){
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
		this.desc = new String[]{"Cures itself from Paralysis",
			"at the end of a turn."};
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
		this.desc = new String[]{"Raises SPEED by 50% when the Pokemon",
		"has a status-ailment."};
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

class Ability_Multitype extends Ability {
	
	public Ability_Multitype(Pokemon me){
		this.me = me;
		this.name = "Multitype";
		
		this.desc = new String[]{"Allows the Pokemon to change types once.",
		"also transforms Normal-type attacks into",
		"the Pokemon's new type."};
		
		if(me.items.length > 1){
			me.items[8] = "Arceus' Plates";
		}
	}
	//basically this just gives STAB to Judgement no matter what type Arceus is
	@Override
	public void trigger_beforeMove() {
		for (int i = 0; i < 4; i++) {
			if (me.moveset[1][i].equals("Normal Attack")) {
				me.moveset[1][i] = me.type+" Attack";
			}
		}
	}
	
	@Override
	public void trigger_endOfTurn(){
		me.defineAllMoves();
	}
}

class Ability_Regenerator extends Ability {
	public Ability_Regenerator(Pokemon me){
		this.me = me;
		this.name = "Regenerator";
		this.desc = new String[]{"The Pokemon heals 1/3rd of its max HP",
		"when switched out."};
	}
	
	@Override
	public void trigger_switchedOut(){
		if(!me.isDed()){
			me.healSelf("third");
		}
	}
}

class Ability_Intimidate extends Ability {
	public Ability_Intimidate(Pokemon me){
		this.me = me;
		this.name = "Intimidate";
	}
	
	@Override
	public void trigger_switchedIn(Pokemon enemyMon){
		enemyMon.decreaseStat("ATK");
	}
}

class Ability_WeakArmor extends Ability {
	public Ability_WeakArmor(Pokemon me){
		this.me = me;
		this.name = "Weak Armor";
	}
	
	@Override
	public void trigger_afterGettingHit(Pokemon enemyMon,int move){
		if(enemyMon.moveIsAnAttack(move)){
			me.decreaseStat("DEF");
			me.raiseStat("SPEED");
			me.raiseStat("SPEED");
		}
	}
}

class Ability_Dash extends Ability {
	public Ability_Dash(Pokemon me){
		this.me = me;
		this.name = "Dash";
		this.desc = new String[]{"The Pokemon gets -48% less ATK at all times,",
		"but gains +1 hit."};
	}
	
	int reducedAtk = 0;
	
	@Override
	public void trigger_beforeMove(){
		this.reducedAtk = this.me.currentATK * 48 / 100;
		this.me.currentATK -= reducedAtk;
		this.me.numberOfHits +=1;
	}
	
	@Override
	public void trigger_endOfTurn(){
		this.me.currentATK += this.reducedAtk;
		this.me.numberOfHits -=1;
	}
}

class Ability_Resilient extends Ability {
	public Ability_Resilient(Pokemon me){
		this.me = me;
		this.name = "Resilient";
		this.desc = new String[]{"The Pokemon gains DEF based on % of missing HP",
		"up to +50 DEF when at 25% of max HP."};
	}
	
	int defGained = 0;
	
	public int calcDefGained(){
		// ??????????????????????????????????
		float perc = this.me.currentHP - this.me.baseHP;
		perc = perc / this.me.baseHP;
		perc = perc * -100;
		perc = (float) (perc * 1.333);
		perc = perc / 2;
		
		if(perc > 50){
			perc = 50;
		}
		
		return Math.round(perc);
	}
	
	@Override
	public void trigger_startOfTurn(){
		this.defGained= calcDefGained();
		
		this.me.currentDEF += defGained;
	}
	
	@Override
	public void trigger_endOfTurn(){
		this.me.currentDEF -= this.defGained;
	}
}

class Ability_SuperOverlord extends Ability{
	public Ability_SuperOverlord(Pokemon me){
		this.me = me;
		this.name = "SuperOverlord";
		this.desc = new String[]{"The Pokemon gains +6 base ATK",
		"for every turn it isn't the",
		"active Pokemon"};
	}
	
	@Override
	public void trigger_turnInBench(){
		me.baseATK += 6;
		me.currentATK = me.baseATK;
	}
	
	
}