package com.laggydogehax.pokemonbattlesim;

public class AbilityFactory{
	public static Ability create(Pokemon nam){ //ENORMOUS SWITCH STATEMENT!!!!!!!!!!!!!!!
		switch(nam.name){
			default:
				return new Ability();
			
			case "Venusaur": return AbilityFactory.create("Overgrow", nam);
				
			case "Charizard": return AbilityFactory.create("Blaze", nam);
					
			case "Blastoise": return AbilityFactory.create("Torrent", nam);
				
			case "Meowscarada": return AbilityFactory.create("Protean", nam);
			
			case "Ninetales": return AbilityFactory.create("Flash Fire", nam);
				
			case "Empoleon": return AbilityFactory.create("Competitive", nam);
				
			case "Raichu": return AbilityFactory.create("Lightning Rod", nam);
				
			case "Mewtwo":
				
			break;
			case "Gengar": return AbilityFactory.create("Levitate", nam);
				
			case "Dragonite": return AbilityFactory.create("Levitate", nam);
				
			case "Absol": return AbilityFactory.create("Super Luck", nam);
				
			case "Gardevoir": return AbilityFactory.create("Trace", nam);
				
			case "Glaceon": return AbilityFactory.create("Ice Body", nam);
				
			case "Luxray": return AbilityFactory.create("Guts", nam);

			case "Lucario": return AbilityFactory.create("Justified", nam);
			
			case "Duraludon":
				
			break;
			case "Mismagius": return AbilityFactory.create("Levitate", nam);
				
			case "Golisopod":
				
			break;
			case "Heracross": return AbilityFactory.create("Moxie", nam);
				
			case "Rampardos":
				
			break;
			case "Lycanroc":
				
			break;
			case "Aurorus": return AbilityFactory.create("Refrigerate", nam);
				
			case "Dugtrio": return AbilityFactory.create("Sand Rush", nam);
				
			case "Sandlash": return AbilityFactory.create("Sand Rush", nam);
				
			case "Arbok": return AbilityFactory.create("Intimidate", nam);
				
			case "Sneasler": return AbilityFactory.create("Unburden", nam);
				
			case "Pidgeot":
				
			break;
			case "Lugia": return AbilityFactory.create("Multiscale", nam);
				
			case "Urshifu":
				
			break;
			case "Audino": return AbilityFactory.create("Regenerator", nam);
				
			case "Tauros":
				
			break;
			case "Sylveon": return AbilityFactory.create("Pixilate", nam);

			case "Tinkaton":
				
			break;
			case "Zarude":
				
			break;
			case "Dragapult":
				
			break;
			case "Mawile": return AbilityFactory.create("Intimidate", nam);
				
			//--------wave 2 of pokemen--------//
			case "Blaziken": return AbilityFactory.create("Speed Boost", nam);
				
			case "Vaporeon": return AbilityFactory.create("Water Absorb", nam);
				
			case "Ursaluna":
				
			break;
			case "Decidueye": return AbilityFactory.create("Overgrow", nam);
				
			case "Flareon": return AbilityFactory.create("Guts", nam);
				
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
			case "Lopunny": return AbilityFactory.create("Limber", nam);
				
			case "Cinccino": return AbilityFactory.create("Guts", nam);
				
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
			case "Mew": return AbilityFactory.create("Synchronize", nam);
				
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
			case "Delphox": return AbilityFactory.create("Blaze", nam);
				
			case "Gyarados": return AbilityFactory.create("Intimidate", nam);
				
			case "Sceptile": return AbilityFactory.create("Unburden", nam);
				
			case "Typhlosion": return AbilityFactory.create("Flash Fire", nam);
				
			case "Greninja": return AbilityFactory.create("Protean", nam);

			case "Leafeon":
				
			case "Donphan":
				
			break;
			case "Corviknight":
				
			break;
			case "Umbreon": return AbilityFactory.create("Synchronize", nam);
				
			case "Jolteon": return AbilityFactory.create("Quick Feet", nam);
				
			case "Espeon":
				
			break;
			case "Eevee":
				
			break;
			case "Arceus": return AbilityFactory.create("Multitype", nam);
				
			case "Citrus": return AbilityFactory.create("Resilient", nam);
			
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
			case "Ceruledge": return AbilityFactory.create("Weak Armor", nam);
				
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
			case "Kingambit": return AbilityFactory.create("SuperOverlord", nam);
				
			case "Azumarill": return AbilityFactory.create("Guts", nam);
				
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
			case "Kleavor": return AbilityFactory.create("Swarm", nam);
				
			case "ADP GX":
				
			break;
			case "Missing No":
				
			break;
		}
		
		return new Ability();

	}
	
	public static Ability create(String nameOfAbility, Pokemon nam){
		return switch(nameOfAbility){
			default: yield new Ability();
			case "Blaze": yield new Ability_Blaze(nam);
			case "Competitive": yield new Ability_Competitive(nam);
			case "Flash Fire": yield new Ability_FlashFire(nam);
			case "Ice Body": yield new Ability_IceBody(nam);
			case "Intimidate": yield new Ability_Intimidate(nam);
			case "Justified": yield new Ability_Justified(nam);
			case "Levitate": yield new Ability_Levitate(nam);
			case "Lightning Rod": yield new Ability_LightningRod(nam);
			case "Limber": yield new Ability_Limber(nam);
			case "Moxie": yield new Ability_Moxie(nam);
			case "Multiscale": yield new Ability_Multiscale(nam);
			case "Multitype": yield new Ability_Multitype(nam);
			case "Overgrow": yield new Ability_Overgrow(nam);
			case "Dash": yield new Ability_Dash(nam);
			case "Guts": yield new Ability_Guts(nam);
			case "Pixilate": yield new Ability_Pixilate(nam);
			case "Protean": yield new Ability_Protean(nam);
			case "Quick Feet": yield new Ability_QuickFeet(nam);
			case "Refrigerate": yield new Ability_Refrigerate(nam);
			case "Regenerator": yield new Ability_Regenerator(nam);
			case "Resilient": yield new Ability_Resilient(nam);
			case "Sand Rush": yield new Ability_SandRush(nam);
			case "Swarm": yield new Ability_Swarm(nam);
			case "Speed Boost": yield new Ability_SpeedBoost(nam);
			case "Super Luck": yield new Ability_SuperLuck(nam);
			case "Synchronize": yield new Ability_Synchronize(nam);
			case "Torrent": yield new Ability_Torrent(nam);
			case "Trace": yield new Ability_Trace(nam);
			case "Unburden": yield new Ability_Unburden(nam);
			case "Water Absorb": yield new Ability_WaterAbsorb(nam);
			case "Weak Armor": yield new Ability_WeakArmor(nam);
			case "SuperOverlord": yield new Ability_SuperOverlord(nam);
		};
	}
	
}
