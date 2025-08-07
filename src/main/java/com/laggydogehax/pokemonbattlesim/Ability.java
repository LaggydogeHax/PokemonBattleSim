package com.laggydogehax.pokemonbattlesim;

class Ability{
	String triggerTime="";
	String name="";
	
	public Ability(String nam){ //ENORMOUS SWITCH STATEMENT!!!!!!!!!!!!!!!
		switch(nam){
			case "Custom":
				//this.name="";
			break;
			case "Venusaur":
				
			break;
			case "Charizard":
				
			break;
			case "Blastoise":
				
			break;
			case "Meowscarada":
				
			break;
			case "Ninetales":
				
			break;
			case "Empoleon":
				
			break;
			case "Raichu":
				
			break;
			case "Mewtwo":
				
			break;
			case "Gengar":
				
			break;
			case "Dragonite":
				
			break;
			case "Absol":
				this.name="Super Luck";
			break;
			case "Gardevoir":
				
			break;
			case "Glaceon":
				
			break;
			case "Luxray":
				
			break;
			case "Lucario":
				
			break;
			case "Duraludon":
				
			break;
			case "Mismagius":
				
			break;
			case "Golisopod":
				
			break;
			case "Heracross":
				
			break;
			case "Rampardos":
				
			break;
			case "Lycanroc":
				
			break;
			case "Aurorus":
				
			break;
			case "Dugtrio":
				
			break;
			case "Sandlash":
				
			break;
			case "Arbok":
				
			break;
			case "Sneasler":
				
			break;
			case "Pidgeot":
				
			break;
			case "Lugia":
				
			break;
			case "Urshifu":
				
			break;
			case "Audino":
				
			break;
			case "Tauros":
				
			break;
			case "Sylveon":
				this.name = "Pixilate";
			break;
			case "Tinkaton":
				
			break;
			case "Zarude":
				
			break;
			case "Dragapult":
				
			break;
			case "Mawile":
				
			break;
			//--------wave 2 of pokemen--------//
			case "Blaziken":
				
			break;
			case "Vaporeon":
				
			break;
			case "Ursaluna":
				
			break;
			case "Decidueye":
				
			break;
			case "Flareon":
				
			break;
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
			case "Lopunny":
				
			break;
			case "Cinccino":
				
			break;
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
			case "Delphox":
				
			break;
			case "Gyarados":
				
			break;
			case "Sceptile":
				
			break;
			case "Typhlosion":
				
			break;
			case "Greninja":
				this.name="Protean";
			break;
			case "Leafeon":
				
			break;
			case "Donphan":
				
			break;
			case "Corviknight":
				
			break;
			case "Umbreon":
				
			break;
			case "Jolteon":
				
			break;
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
			case "Azumarill":
				
			break;
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
		
		defineTriggerTime();
	}
	
	private void defineTriggerTime(){
		
		String[] abilityTableBeforeMove = new String[]{"Pixilate","Super Luck","Protean"};
		String[] abilityTableAfterGettingHit = new String[]{};
		String[] abilityTableBeforeGettingHit = new String[] {"Magic Bounce"};
		String[] abilityTableAtEndOfTurn = new String[]{};
		
		for(int i=0;i<abilityTableBeforeMove.length;i++){
			if(name.equals(abilityTableBeforeMove[i])){
				this.triggerTime="before move";
				return;
			}
		}
		
		this.triggerTime="None";
	}
	
}