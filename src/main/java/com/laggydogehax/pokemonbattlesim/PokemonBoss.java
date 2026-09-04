package com.laggydogehax.pokemonbattlesim;

class PokemonBoss extends Pokemon{
    
    public PokemonBoss(String pkmnName) {
        super(pkmnName);
        
        this.baseHP *= 20;
        this.baseATK *= 1.2;
        
        this.currentHP = this.baseHP;
        this.currentATK = this.baseATK;
    }
    
    public Pokemon bossToPokemon(){
        Pokemon pok = new Pokemon("Custom");
        
        /*
        pok.name = this.name;
        pok.baseATK = this.baseATK;
        pok.baseHP = this.baseHP;
        pok.baseDEF = this.baseDEF;
        pok.baseSPEED = this.baseSPEED;
        
        pok.currentATK = this.currentATK;
        */
        
        pok = this;
        
        return pok;
    }
    
    
}
