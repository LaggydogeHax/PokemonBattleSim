package com.laggydogehax.pokemonbattlesim;

import java.util.Random;

class PokemonBoss extends Pokemon{
    
    public PokemonBoss(String pkmnName) {
        super(pkmnName);
        this.bossify();
    }
    
    private void bossify(){ //funny name
        this.baseHP *= 18;
        this.baseATK *= 1.1;
		this.baseSPEED *= 0.8;
        
        this.currentHP = this.baseHP;
        this.currentATK = this.baseATK;
		this.currentSPEED = this.baseSPEED;
        
        //they'll have 8 moves!!
        
        String[][] movesetcopy = this.moveset;
        this.moveset = new String[2][8];
        
        for(int i=0 ; i < 2 ; i++){
            System.arraycopy(movesetcopy[i], 0, this.moveset[i], 0, 4);
        }
        PokemonDB db = new PokemonDB();
        Random rng = new Random();
        
        String[] movesList = db.getMoveNames();
        
        for (int i=4; i < 8 ; i ++){ //fills the 4 new move slots with random moves
            this.moveset[0][i] = movesList[rng.nextInt(movesList.length)];
        }
        
        this.defineAllMoves();
    }
	
	@Override
	public boolean isBoss(){
		return true; // >:)
	}
    
    @Override
    protected void healOverTime(){
		if(this.currentHP!=this.baseHP){
			int healtick = this.baseHP/82;
			
			if(this.currentHP+healtick>this.baseHP){
				healtick=this.baseHP-this.currentHP;
			}
			this.currentHP+=healtick;
		}
	}
    
    @Override
    protected void aukPoisoned(){
		if(this.isPoisoned==true){
			int poisontick =this.baseHP/86;

			if(this.currentHP-poisontick<0){poisontick=this.currentHP;}

			this.currentHP-=poisontick;
		}
	}
    
    @Override
    protected void aukBurning(){
        if(this.isBurning==true){
			int burningtick =this.baseHP/62;

			if(this.currentHP-burningtick<0){burningtick=this.currentHP;}

			this.currentHP-=burningtick;
		}
    }
    
    @Override
    protected void healSelf(String amount) {
        switch (amount) {
            case "full":
                this.currentHP = this.baseHP; //im pretty sure this one is unused?
                break;
            case "half":
                if (this.energyDrink) {
                    this.healSelf("third");
                    break;
                }
                this.currentHP += (this.baseHP / 20);
                break;
            case "third":
                this.currentHP += (this.baseHP / 33);
                break;
            case "quarter":
                this.currentHP += (this.baseHP / 45);
                break;
        }
       
        if (this.currentHP > this.baseHP) {
            this.currentHP = this.baseHP;
        }
    }
    
    public Pokemon bossToPokemon(){
        Pokemon pok = new Pokemon("Custom");

        pok = this;
        
        return pok;
    }
    
    public PokemonBoss pokemonToBoss(Pokemon pok){
        //copies provided existing pokemon 
        this.name=pok.name;
		this.type=pok.type;
        this.type2=pok.type2;
		this.setTypesWnR();
		this.baseHP=pok.baseHP;
		this.baseATK=pok.baseATK;
		this.baseDEF=pok.baseDEF;
		this.baseSPEED=pok.baseSPEED;
		
		this.ability = pok.ability;

		this.currentHP=this.baseHP;
		this.currentATK=this.baseATK;
		this.currentDEF=this.baseDEF;
		this.currentSPEED=this.baseSPEED;

		this.moveset[0][0]=pok.moveset[0][0];
		this.moveset[0][1]=pok.moveset[0][1];
		this.moveset[0][2]=pok.moveset[0][2];
		this.moveset[0][3]=pok.moveset[0][3];

		this.defineAllMoves();
        this.bossify();
        
        return this;
    }
    
}
