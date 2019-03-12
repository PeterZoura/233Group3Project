import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import javafx.application.Application;


/**
 * Uses a main method to run the primary game loop.
 */
public class Game {

	/**
	 * @param monsters
	 * @return if one or more of the Monsters in the given array are alive.
	 */
	public static boolean monstersAlive(Monster[] monsters) {
		for (Monster m : monsters) {
			if (m.alive())
				return true;
		}
		return false;
	}
	
	/**
	 * Prints the intentions of each Monster in the given array.
	 * @param monsters
	 */
	private static void printMonstersIntentions(Monster[] monsters) {
		for (Monster m : monsters)
			System.out.println(m.intentions() + "\n");
	}
	
	/**
	 * Removes dead Monsters from the given array.
	 * @param monsters
	 * @return the original Monster array with any/all dead Monsters removed.
	 */
	private static Monster[] removeDead(Monster[] monsters) {
		for (int i = monsters.length - 1; i >= 0; i --) {
			if (!monsters[i].alive()) {
				Monster[] newMonsters = new Monster[monsters.length - 1];
				for (int j = 0; j < monsters.length; j ++) {
					if (j == i)
						continue;
					newMonsters[(j < i) ? j : j - 1] = monsters[j];
				}
				monsters = newMonsters;
			}
		}
		
		return monsters;
	}
	
	/**
	 * @param tier the tier to access from the given array.
	 * @param monsters tiered array of all possible encounters.
	 * @return a random encounter from the given tier.
	 */
	private static Monster[] getEncounter(int tier, Monster[][][] encounters) {
		return encounters[tier][(int) (Math.random() * encounters[tier].length)];
	}
	
	/**
	 * Initializes all types of Monsters, and creates and returns all possible encounters using said monsters.
	 * @return all possible encounters, in a tiered array(encounters[0][0] would return the tier 1 encounter {slime, slime, slime}). 
	 */
	private static Monster[][][] getEncounters() {
		Monster slime   = new Monster("Slime", 19, CardsUtil.get("Monster Attack"), CardsUtil.get("Monster Block"), CardsUtil.get("Monster Frail"), CardsUtil.get("Monster Vulnerable"), CardsUtil.get("Monster Frail Attack"));
		
		Monster jawWorm = new Monster("Jaw Worm", 44, CardsUtil.get("Monster Attack"), CardsUtil.get("Monster Block"), CardsUtil.get("Monster BlAttack"), CardsUtil.get("Monster Strength"));
		
		Monster cultist = new Monster("Cultist", 48, CardsUtil.get("Monster Frail Attack"), CardsUtil.get("Monster Attack"), CardsUtil.get("Monster Strength Power"));
		
		Monster louse   = new Monster("Louse", 16, CardsUtil.get("Monster WkAttack"), CardsUtil.get("Monster BlAttack"), CardsUtil.get("Monster StBlock"), CardsUtil.get("Monster Block"), CardsUtil.get("Monster Frail"));
		
		Monster sphericGuardian = new Monster("Spheric Guardian", 20, CardsUtil.get("Monster StBlock"), CardsUtil.get("Monster Frail Attack"), CardsUtil.get("Monster Vulnerable Attack"), CardsUtil.get("Monster Attack"), CardsUtil.get("Monster Block"));
		sphericGuardian.block(30);
		sphericGuardian.getDexterity().modifyVal(2);
		sphericGuardian.getArmour().setModifyRate(0);
		
		Monster gremlinNob = new Monster("Gremlin Nob", 82, CardsUtil.get("Monster Attack"), CardsUtil.get("Monster StAttack"), CardsUtil.get("Monster HvAttack"), CardsUtil.get("Monster Strength Power"));
		
		Monster awakenedOne = new Monster("Awakened One", 300, CardsUtil.get("Monster HvAttack"), CardsUtil.get("Monster HvFrail Attack"), CardsUtil.get("Monster Strength"), CardsUtil.get("Monster Awakened Special"));
		awakenedOne.getStrength().addStartModifier(1, -1);
		awakenedOne.getRegeneration().setModifyRate(0);
		awakenedOne.getRegeneration().modifyVal(10);

		Monster[][] tier1 = new Monster[][] {{ new Monster(slime), new Monster(slime), new Monster(slime) }, { new Monster(cultist) }, { new Monster(louse), new Monster(louse) }, { new Monster(slime), new Monster(slime) }};
		Monster[][] tier2 = new Monster[][] {{ new Monster(sphericGuardian)}, {new Monster(jawWorm), new Monster(jawWorm) }, { new Monster(cultist), new Monster(jawWorm) }, { new Monster(sphericGuardian), new Monster(jawWorm) }};
		Monster[][] tier3 = new Monster[][] {{ new Monster(gremlinNob) }};
		Monster[][] tier4 = new Monster[][] {{ new Monster(cultist), new Monster(cultist), new Monster(awakenedOne) }};
		
		return new Monster[][][] {tier1, tier2, tier3, tier4};
		
	}
	/*
	new Thread() {
            @Override
            public void run() {
                javafx.application.Application.launch(GameAppGridPane.class);
            }
        }.start();
	*/

	/**
	 * Main game loop.
	 */
	public static void main(String[] args) {

		CardsUtil.load();
		
		Monster[][][] encounters = getEncounters();
		
		Scanner in = new Scanner(System.in);
		Player player = new Player(intro(in), 80, CardsUtil.get("Strike"), CardsUtil.get("Strike"), CardsUtil.get("Strike"), CardsUtil.get("Desperate Strike"),CardsUtil.randomP(), CardsUtil.randomP(), CardsUtil.randomP());
		
        

		for (int i = 0; i < 4; i ++) {
			if (!player.alive())
				break;
			Monster[] combatMonsters = getEncounter(i, encounters);
			
		//	monster.setStrategy("0.8,Monster Special,2/0.2,Monster Special,2");
		//	monster.setStrategy("1,Monster Special,3/0.9,Monster SpecialTwo,2");
			player.startCombat();

			while (monstersAlive(combatMonsters) && player.alive()) {
				new Thread() {
					@Override
					public void run() {
						javafx.application.Application.launch(GameAppGridPane.class);
					}
				}.start();
				GameAppGridPane G = new GameAppGridPane();
				G.setPlayerApp(player);
				G.setCombatMonsters(combatMonsters);
				playerTurn(player, combatMonsters);
				player.endTurn();
				combatMonsters = removeDead(combatMonsters);
				endTurn(in, player, combatMonsters);
				
			}
			player.endCombat();
			endCombat(in, player, combatMonsters);
		}

		in.close();
	}

	/**
	 * Prints the game intro and returns the user's name.
	 * @param in this Scanner will be used to get the user's name.
	 * @return the user's name.
	 */
	public static String intro(Scanner in) {
		System.out.println("Please enter your name:");
		String name = in.nextLine();
		pressEnter(in, "Welcome, " + name + ", to Slay the Spire! In this brief demo, you will be challenged by enemies until you are defeated."
				+ " Use your Cards to attack and defend against the monsters!");
		return name;
	}

	/**
	 * Runs the player's turn, and loops until the player ends their turn.
	 * @param player
	 * @param monster
	 */
	public static void playerTurn(Player player, Monster[] monster) {
		System.out.println(player.getName() + "'s turn!\n");
		for (Monster m : monster)
			m.setMove();
		printMonstersIntentions(monster);

		player.startTurn();
		printStats(player, monster);

		while (player.nextCard(monster) && monstersAlive(monster)) {
			monster = removeDead(monster);
			printMonstersIntentions(monster);
			printStats(player, monster);
		}
	}

	/**
	 * Prints out end turn messages and prompts the user to hit enter.
	 * @param in this Scanner will be used to take the player's input.
	 * @param player
	 * @param monster
	 */
	public static void endTurn(Scanner in, Player player, Monster[] monster) {
		if (monstersAlive(monster) && player.alive()) {
			pressEnter(in, player.getName() + "'s turn is over!");
			
			System.out.println("Monsters' turn!");
			for (Monster m : monster) {
				m.startTurn();
			}
			monster = removeDead(monster);
			printMonstersIntentions(monster);			
			printStats(player, monster);
			
			for (Monster m : monster ) {
				m.getMove().use(m, player);
				System.out.println(m.actionReport());
			}
			
			for (Monster m : monster) {
				m.endTurn();
			}
			monster = removeDead(monster);
			printStats(player, monster);
			pressEnter(in, "");
		}
	}

	/**
	 * Prints if the monster or player won. Adds a random card to the player's deck if they won.
	 * @param in this Scanner will be used to prompt the user to hit enter.
	 * @param player
	 * @param monster
	 */
	public static void endCombat(Scanner in, Player player, Monster[] monster) {
		if (!player.alive())
			System.out.println("DEFEAT!");
		else {
			pressEnter(in, "Victory!");
			Card reward = newCard();
			pressEnter(in, "Your reward: " + reward.getName() + "\n" + reward.getDescription());
			player.addCard(reward);
		}
	}

	/**
	 * @return a random player card, cannot be Strike.
	 */
	private static Card newCard() {
		Card c;
		while((c = CardsUtil.randomP()).getName().equals("Strike"));
		return c;
	}

	/**
	 * Returns a random monster from a given array, with full health.
	 * @param monsters the monsters to choose from.
	 * @return a random monster, with restored health.
	 */
	public static Monster getNextMonster(Monster[] monsters) {
		Monster m = monsters[(int) (Math.random() * monsters.length)];
		m.heal(m.getMaxHealth());
		return m;
	}

	/**
	 * Prints the health, armour and various non-zero attributes of the given player and monster, as well as the energy of the player.
	 */
	public static void printStats(Entity player, Monster[] monster) {
		ArrayList<String> attributeList = new ArrayList<String>(Arrays.asList("Strength", "Dexterity", "Weak", "Frail", "Vulnerable", "Regeneration", "Poison", "Constricted", "Armour"));

		String playerStatus = ("\n" + player.getName() + ":   health: " + player.getHealth() + "/" + player.getMaxHealth() +
				"      Energy: " + player.getEnergy() + "/"  + player.getMaxEnergy());
		int i = 0;
		for (Attribute a : new Attribute[] {player.getStrength(), player.getDexterity(), player.getWeak(),player.getFrail(), player.getVulnerable(), player.getRegeneration(),
		player.getPoison(), player.getConstricted(), player.getArmour()}){
			if (a.getCurrentVal()!= 0){
				playerStatus += String.format("      %s: %d", attributeList.get(i), a.getCurrentVal());
			}
			i++;
		}

		String monsterStatus = "";
		
		for (Monster m : monster) {
			if (m.alive()) {
				monsterStatus += ("\n" + m.getName() + ":    health: " + m.getHealth() + "/" + m.getMaxHealth());
				int j = 0;
				for (Attribute a : new Attribute[] {m.getStrength(), m.getDexterity(), m.getWeak(),m.getFrail(), m.getVulnerable(), m.getRegeneration(),
				m.getPoison(), m.getConstricted(), m.getArmour()}){
					if (a.getCurrentVal()!= 0){
						monsterStatus += String.format("      %s: %d", attributeList.get(j), a.getCurrentVal());
					}
					j++;
				}
			}
		
		}
		System.out.println(playerStatus + "\n");
		System.out.println(monsterStatus);
	}


	/**
	 * Prompts the user to press enter, accompanied by a given message.
	 * @param in Scanner to use to get the user's input.
	 * @param message the message to display before "Press enter to continue."
	 */
	public static void pressEnter(Scanner in, String message) {
		System.out.println(message + "\nPress enter to continue.");
		in.nextLine();
	}

}
