# PokemonBattleSim.java
An epic and totally awesome battle simulator for the terminal/cmd

too lazy to make documentation on the code itself so here's the game mechanics

##


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

### Status Ailments
Status Ailments affect your Mon in various ways (yay)
#### Burned
Burning status can be inflicted by a few Fire Type Moves

Burning deals damage equal to 1/9 of Base HP

Chance to free from Burn: 25%

#### Poisoned
Poisoned status can be inflicted by some Poison type moves!!

Poison deals damage equal to 1/10 of Base HP

Chance to free from Poison: 25%

#### Paralized
Paralized status can be inflicted by most Electric type moves.

Paralized status has a 50% chance to paralyze the Pokemon and prevent it from using a move during that turn. it also halves the SPEED stat.

Chance to free from Paralysis: 30%
#### Healing over time

Healing over time will heal a bit of the Pokemon's HP at the end of each turn. it can only be gained with Status Moves.

HoT cures the Pokemon's HP by 1/16 of its Base HP. It will last until the Pokemon faints or its switched out.

### Base and Current stats:
**CURRENT STAT:** A Mon's current stats are their total stats. These get affected by buffs, debuffs, status ailments and so on. They get reset back to base when the Pokemon is switched out.

**BASE STAT:** A Mon's base stats are its initial stats and they are different for each Pokemon. Most stat calculations are pulled from base stats and they cant be changed through normal means.

**Example:** a +25% ATK buff is adding 25% of base ATK to current ATK.
