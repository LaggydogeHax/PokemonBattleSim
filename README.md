# PokemonBattleSim.java
An epic and totally awesome battle simulator for the terminal/cmd

too lazy to make documentation on the code itself so here's the game mechanics


## Pokemon's stats and attributes

### HP
A Pokemon's HP (Health Points) determines how much damage a Pokemon can take before fainting.
HP cannot go below 0 ofc.
HP cannot exceed base HP (acts as Max HP).

### ATK
A Pokemon's ATK stat determines the attacking power of the move it uses, the higher the better!!
The minimum ATK a Pokemon can have at any time is 20.
There's no maximum amount of ATK a Pokemon can have.

### DEF
A Pokemon's DEF acts as damage reduction from incoming attacks. A Pokemon with high DEF might survive for longer! The stat is converted to a percentage which tells the program how much it should reduce from damage taken.

**Formula:** REDUCTION(Percentage) = (current DEF / 400) * 100.

**NOTE:** End result is rounded down.

The minimum DEF a Pokemon can have is 1.
The maximum DEF a Pokemon can have is 300 (75% damage reduction).

### SPEED
A Pokemon's SPEED determines which Pokemon will act first. it also determines the chance of a critical hit to occur. if both Pokemon have the exact same SPEED, whichever Pokemon goes first that turn is determined by a coin flip.
The minimum SPEED a Pokemon can have is 2.
There's no maximum amount of SPEED a Pokemon can have.

### CRIT CHANCE
Critical hit chance is dependant on the Pokemon's SPEED stat. Upon successful, damage dealt is doubled.

**Formula:** CRIT CHANCE = (current SPEED / 806) * 100.

**NOTE:** Some moves (Like Night Slash) have increased critical hit chance. Others (Like Flower Trick) have guaranteed critical hits.

### Base and Current stats:
**CURRENT STAT:** A Mon's current stats are their total stats. These get affected by buffs, debuffs, status ailments and so on. They get reset back to base when the Pokemon is switched out.

**BASE STAT:** A Mon's base stats are its initial stats and they are different for each Pokemon. Most stat calculations are pulled from base stats and they cant be changed through normal means.

**Example:** a +25% ATK buff is adding 25% of base ATK to current ATK.
