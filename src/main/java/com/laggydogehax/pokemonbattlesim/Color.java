package com.laggydogehax.pokemonbattlesim;

class Color{
	
	static public Clr getColorFromString(String typ){
		return getClrBright(typ);
	}
	
	static public Clr getBGColorFromString(String typ){
		return getBackground(typ);
	}
	
	static public Clr getColorFromMoveType(Pokemon mon,int moveselec){
		String montype = findMonTypeFromMove(mon.moveset[1][moveselec]);
		return getClrNormal(montype);
	}
	
	static public Clr getBrightColorFromMoveType(Pokemon mon,int moveselec){
		String montype = findMonTypeFromMove(mon.moveset[1][moveselec]);
		return getClrBright(montype);
	}
	
	static public Clr getBGColorFromMoveType(Pokemon mon,int moveselec){
		String montype = findMonTypeFromMove(mon.moveset[1][moveselec]);
		return getBackground(montype);
	}
	
	private static String findMonTypeFromMove(String movtype){
		String augh = "";
		String[] typesArray = PokemonMaker3000.getTypesVector();
		
		for (String arr : typesArray) {
			if (movtype.contains(arr)) {
				augh = arr;
				break;
			}
		}
		return augh;
	}
	
	public static Clr getHPColor(Pokemon mon){
		float maxhp = (float)mon.baseHP;
		float currenthp = (float)mon.currentHP;
		
		float op = (currenthp / maxhp) * 100;
		
		if(op > 49.9){
			return Clr.GREEN_B;
		}if(op > 24.9 && op < 49.9){
			return Clr.YELLOW_B;
		}if(op < 24.9){
			return Clr.RED_B;
		}
		
		return Clr.R;
	}
	
	private static Clr getClrNormal(String montype) {
		switch (montype) {
			case "Grass":
				return Clr.GREEN;

			case "Fire":
				return Clr.ORANGE;

			case "Water":
				return Clr.BLUE;

			case "Psychic":
				return Clr.MAGENTA;

			case "Dark":
				return Clr.BLACK;

			case "Fighting":
				return Clr.RED;

			case "Electric":
				return Clr.YELLOW;

			case "Flying":
				return Clr.LBLUE;

			case "Bug":
				return Clr.GREEN;

			case "Ground":
				return Clr.YELLOW;

			case "Rock":
				return Clr.YELLOW;

			case "Poison":
				return Clr.MAGENTA;

			case "Ghost":
				return Clr.MAGENTA;

			case "Steel":
				return Clr.BLACK;

			case "Dragon":
				return Clr.BLUE;

			case "Fairy":
				return Clr.PINK;

			case "Normal":
				return Clr.BLACK;

			case "Ice":
				return Clr.CYAN;
		}
		return Clr.R;
	}

	private static Clr getClrBright(String montype) {
		switch (montype) {
			case "Grass":
				return Clr.GREEN_BB;

			case "Fire":
				return Clr.ORANGE_BB;

			case "Water":
				return Clr.BLUE_BB;

			case "Psychic":
				return Clr.MAGENTA_BB;

			case "Dark":
				return Clr.WHITE_BB;

			case "Fighting":
				return Clr.RED_BB;

			case "Electric":
				return Clr.YELLOW_BB;

			case "Flying":
				return Clr.LBLUE_BB;

			case "Bug":
				return Clr.GREEN_BB;

			case "Ground":
				return Clr.YELLOW_BB;

			case "Rock":
				return Clr.YELLOW_BB;

			case "Poison":
				return Clr.MAGENTA_BB;

			case "Ghost":
				return Clr.MAGENTA_BB;

			case "Steel":
				return Clr.WHITE_BB;

			case "Dragon":
				return Clr.BLUE_BB;

			case "Fairy":
				return Clr.PINK_BB;

			case "Normal":
				return Clr.WHITE_BB;

			case "Ice":
				return Clr.CYAN_BB;
		}
		return Clr.R;
	}
	
	private static Clr getBackground(String montype){
		switch (montype) {
			case "Grass":
				return Clr.GREEN_BG;

			case "Fire":
				return Clr.RED_BG;

			case "Water":
				return Clr.BLUE_BG;

			case "Psychic":
				return Clr.MAGENTA_BG;

			case "Dark":
				return Clr.WHITE_BG;

			case "Fighting":
				return Clr.RED_BG;

			case "Electric":
				return Clr.YELLOW_BG;

			case "Flying":
				return Clr.CYAN_BG;

			case "Bug":
				return Clr.GREEN_BG;

			case "Ground":
				return Clr.YELLOW_BG;

			case "Rock":
				return Clr.YELLOW_BG;

			case "Poison":
				return Clr.MAGENTA_BG;

			case "Ghost":
				return Clr.MAGENTA_BG;

			case "Steel":
				return Clr.WHITE_BG;

			case "Dragon":
				return Clr.BLUE_BG;

			case "Fairy":
				return Clr.MAGENTA_BG;

			case "Normal":
				return Clr.WHITE_BG;

			case "Ice":
				return Clr.CYAN_BG;
		}
		return Clr.R;
	}
}

enum Clr{
	R("\033[0m"),//RESET
	BOLD("\033[1m"),		// ACTUALLY BOLD!
	BLACK("\033[0;30m"),    // BLACK
    RED("\033[0;31m"),      // RED
    GREEN("\033[0;32m"),    // GREEN
    YELLOW("\033[0;33m"),   // YELLOW
    BLUE("\033[0;34m"),     // BLUE
    MAGENTA("\033[0;35m"),  // MAGENTA
    CYAN("\033[0;36m"),     // CYAN
    WHITE("\033[0;37m"),    // WHITE
	ORANGE("\033[38;5;130m"),  // ORANGE
	PINK("\033[38;5;132m"),  // PINK
	LBLUE("\033[38;5;75m"), // LIGHT BLUE
	
	//BRIGHT COLORS
	BLACK_B("\033[0;90m"),     // BLACK
    RED_B("\033[0;91m"),       // RED
    GREEN_B("\033[0;92m"),     // GREEN
    YELLOW_B("\033[0;93m"),    // YELLOW
    BLUE_B("\033[0;94m"),      // BLUE
    MAGENTA_B("\033[0;95m"),   // MAGENTA
    CYAN_B("\033[0;96m"),      // CYAN
    WHITE_B("\033[0;97m"),     // WHITE

	//BOLD + BRIGHT
	BLACK_BB("\033[1;90m"), // BLACK
	RED_BB("\033[1;91m"),   // RED
    GREEN_BB("\033[1;92m"), // GREEN
    YELLOW_BB("\033[1;93m"),// YELLOW
    BLUE_BB("\033[1;94m"),  // BLUE
    MAGENTA_BB("\033[1;95m"),// PURPLE
    CYAN_BB("\033[1;96m"),  // CYAN
    WHITE_BB("\033[1;97m"), // WHITE
	ORANGE_BB("\033[38;5;214m"), //ORANGE
	PINK_BB("\033[38;5;218m"), // PINK
	LBLUE_BB("\033[38;5;123m"), // LIGHT BLUE
	
	// BACKGROUND COLOR!!1
    BLACK_BG("\033[40;30m"),   // BLACK AND WHITE TEXT
    RED_BG("\033[41m"),     // RED
    GREEN_BG("\033[42m"),   // GREEN
    YELLOW_BG("\033[43m"),  // YELLOW
    BLUE_BG("\033[44;97m"),    // BLUE AND WHITE TEXT
    MAGENTA_BG("\033[45m"), // MAGENTA
    CYAN_BG("\033[46;90m"),    // CYAN
    WHITE_BG("\033[47;90m");   // WHITE AND BLACK TEXT

	//ty stackoverflow https://stackoverflow.com/questions/5762491
	//and https://gist.github.com/fnky/458719343aabd01cfb17a3a4f7296797
	
	private final String code;

    Clr(String code) {
        this.code = code;
    }
    @Override
    public String toString() {
        return code;
    }
}
