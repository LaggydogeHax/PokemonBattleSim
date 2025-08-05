package com.laggydogehax.pokemonbattlesim;

enum Clr{
	R("\033[0m"),//RESET
	BLACK("\033[0;30m"),    // BLACK
    RED("\033[0;31m"),      // RED
    GREEN("\033[0;32m"),    // GREEN
    YELLOW("\033[0;33m"),   // YELLOW
    BLUE("\033[0;34m"),     // BLUE
    MAGENTA("\033[0;35m"),  // MAGENTA
    CYAN("\033[0;36m"),     // CYAN
    WHITE("\033[0;37m"),    // WHITE
	
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
    WHITE_BB("\033[1;97m"); // WHITE

	//ty stackoverflow https://stackoverflow.com/questions/5762491
	
	private final String code;

    Clr(String code) {
        this.code = code;
    }
    @Override
    public String toString() {
        return code;
    }
}
