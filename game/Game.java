package game;
import java.util.Scanner;

import logic.Attribute;
import logic.Card;
import logic.CardsUtil;
import logic.Monster;
import logic.Player;
import logic.Relic;

import java.util.ArrayList;
import java.util.Arrays;


/**
 * Uses a main method to run the primary game loop.
 */
public class Game {
	
	private static int battleCounter = 0;

	/**
	 * @param monsters
	 * @return if one or more of the Monsters in the given array are alive.
	 */
	private static boolean monstersAlive(Monster[] monsters) {
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
		Monster acidSlime   = new Monster("Acid Slime", 21, CardsUtil.getMonsterMoveset("Acid Slime"));
		Monster spikeSlime   = new Monster("Spike Slime", 19, CardsUtil.getMonsterMoveset("Spike Slime"));
		
		Monster fungiBeast = new Monster("Fungi Beast", 26, CardsUtil.getMonsterMoveset("Fungi Beast"));
		
		Monster shelledParasite = new Monster("Shelled Parasite", 71, CardsUtil.getMonsterMoveset("Shelled Parasite"));
		shelledParasite.setStrategy("1,Monster Block,1");
		shelledParasite.getArmour().addStartModifier(6, -1);
		
		Monster snakePlant = new Monster("Snake Plant", 78, CardsUtil.getMonsterMoveset("Snake Plant"));
		snakePlant.getRegeneration().setModifyRate(0);
		snakePlant.getRegeneration().modifyVal(1);

		Monster jawWorm = new Monster("Jaw Worm", 44, CardsUtil.getMonsterMoveset("Jaw Worm"));

		Monster cultist = new Monster("Cultist", 48, CardsUtil.getMonsterMoveset("Cultist"));
		cultist.setStrategy("1,Monster Strength Power,1");

		Monster louse   = new Monster("Louse", 16, CardsUtil.getMonsterMoveset("Louse"));

		Monster sphericGuardian = new Monster("Spheric Guardian", 20, CardsUtil.getMonsterMoveset("Spheric Guardian"));
		sphericGuardian.block(30);
		sphericGuardian.getDexterity().modifyVal(2);
		sphericGuardian.getArmour().setModifyRate(0);
		sphericGuardian.setStrategy("1,Monster StBlock,1");

		Monster gremlinNob = new Monster("Gremlin Nob", 82, CardsUtil.getMonsterMoveset("Gremlin Nob"));
		
		Monster orbWalker = new Monster("Orb Walker", 91, CardsUtil.getMonsterMoveset("Orb Walker"));
		orbWalker.getStrength().addEndModifier(3, -1);

		Monster awakenedOne = new Monster("Awakened One", 300, CardsUtil.getMonsterMoveset("Awakened One"));
		awakenedOne.getStrength().addStartModifier(1, -1);
		awakenedOne.getRegeneration().setModifyRate(0);
		awakenedOne.getRegeneration().modifyVal(10);
		awakenedOne.setStrategy("1,Monster Awakened Special,1");
		
		Monster theChamp = new Monster("The Champ", 420, CardsUtil.getMonsterMoveset("The Champ"));
		theChamp.setStrategy("0.5,Monster Champ Anger,1");

		Monster[][] tier1 = new Monster[][] {{ new Monster(acidSlime), new Monster(acidSlime), new Monster(spikeSlime) }, { new Monster(cultist) }, { new Monster(louse), new Monster(louse) }, { new Monster(acidSlime), new Monster(spikeSlime) }, { new Monster(fungiBeast), new Monster(fungiBeast) }};
		Monster[][] tier2 = new Monster[][] {{ new Monster(sphericGuardian)}, { new Monster(jawWorm), new Monster(jawWorm) }, { new Monster(cultist), new Monster(jawWorm) }, { new Monster(sphericGuardian), new Monster(jawWorm) }};
		Monster[][] tier3 = new Monster[][] {{ new Monster(gremlinNob) }, { new Monster(orbWalker) }, { new Monster(shelledParasite) }, { new Monster(snakePlant) }};
		Monster[][] tier4 = new Monster[][] {{ new Monster(cultist), new Monster(cultist), new Monster(awakenedOne) }, { new Monster(theChamp) }};

		return new Monster[][][] {tier1, tier2, tier3, tier4};

	}


	/**
	 * Main game loop.
	 */
	public static void main(String[] args) {
		CardsUtil.load();

		Monster[][][] encounters = getEncounters();

		Scanner in = new Scanner(System.in);
		Player player = new Player(intro(in), 80, CardsUtil.get("Strike"), CardsUtil.get("Strike"), CardsUtil.get("kill"), CardsUtil.get("Defend"),CardsUtil.randomP(), CardsUtil.randomP(), CardsUtil.randomP());
		player.addRelic(CardsUtil.getRelic("Iron Blood"));
		
		for (int i = 0; i < 4; i ++) {
			if (!player.alive())
				break;
			Monster[] combatMonsters = getEncounter(i, encounters);
			player.startCombat(0, combatMonsters);
			int turnCount = 0;
			while (monstersAlive(combatMonsters) && player.alive()) {	
				playerTurn(player,turnCount, combatMonsters);
				player.endTurn();
				combatMonsters = removeDead(combatMonsters);
				endTurn(in, player,turnCount, combatMonsters);
				turnCount++;
			}
			battleCounter++;
			if (!player.alive()){
				endCombat(in, player, combatMonsters);
			}
			else{
				player.endCombat();
				endCombat(in, player, combatMonsters);
			}
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
	public static void playerTurn(Player player,int turnCount, Monster[] monster) {
		System.out.println(player.getName() + "'s turn!\n");
		for (Monster m : monster)
			m.setMove();
		printMonstersIntentions(monster);

		player.startTurn(turnCount, monster);
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
	public static void endTurn(Scanner in, Player player,int turnCount, Monster[] monster) {
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
			Rewards(in, player);
			pressEnter(in,"");
		}
	}
	
	/**
	 * Retrieves a reward for the player at the end of comabt consisting of a choice between 3 random cards, a relic and a potion
	 * @param in this Scanner will be used to prompt the user to hit enter.
	 * @param player
	 */
	public static void Rewards(Scanner in, Player player){
		ArrayList<Card> cardRewards = newCards();
		Card potionReward = newPotion();
		Relic relicReward = newRelic(player);
		player.addRelic(relicReward);
		player.addPotion(potionReward);
		printRewards(cardRewards, potionReward,relicReward);
		while(true){
			try {
				System.out.println("Type anything else if you do not want any of the cards");
				int whichCard = Integer.parseInt(in.nextLine());
				Card card = cardRewards.get(whichCard);
				System.out.println(card.getDescription() + "\nPress enter to add to your deck, type anything else to select a different card.");
				if (in.nextLine().equals("")){
					System.out.println(card.getName() + " added to your deck.");
					player.addCard(card);
					break;
				}
				else
					System.out.println("Select a new card");
			} catch(Exception e){
				System.out.println("No card selected");
				break;
			}
		}
	}

	/**
	 * @return an arraylist of 3 random player cards, cannot be Strike.
	 */
	private static ArrayList<Card> newCards() {
		ArrayList<Card> cards = new ArrayList<Card>();
		Card c;
		for (int i=0; i<3; i++){
			c = CardsUtil.randomP();
			while((c.getName().equals("Strike"))||c.getName().equals("Defend")){
				c = CardsUtil.randomP();
			}
			cards.add(c);
		}
		return cards;
	}
	
	/**
	 * @return a random potion card
	 */
	private static Card newPotion() {
		Card potion;
		potion = CardsUtil.randomPotion();
		return potion;
	}
	
	/**
	 * @return a random relic
	 */	
	private static Relic newRelic(Player player) {
		Relic r = CardsUtil.randomRelic();
		String playerRelics = player.listRelics();
		int i = 0;
		while((playerRelics.contains(r.getName()))&&(i<20)){
			r = CardsUtil.randomRelic();
			i++;
		}
		return r;
	}
	
	/**
	 * prints to the console the rewards received from the battle.
	 * @param cards array list of cards
	 * @param potion
	 * @param relic
	 */
	public static void printRewards(ArrayList<Card> cards,Card potion,Relic relic){
		System.out.println("Your rewards are:");
		if (relic!=null)
			System.out.println(relic.getName() + ": \n" + relic.getStatDescription());
		if (potion!= null)
			System.out.println(potion.getName() + ": \n" + potion.getDescription());
		System.out.println("Please select a card to add to your deck:");
		int i = 0;
		for (Card c : cards){
			System.out.println(i+": "+c.getName());
			i++;
		}
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
	public static void printStats(Player player, Monster[] monster) {
		ArrayList<String> attributeList = new ArrayList<String>(Arrays.asList("Strength", "Dexterity", "Weak", "Frail", "Vulnerable", "Regeneration", "Poison", "Constricted", "Armour"));

		String playerStatus = ("\n" + player.getName() + ":   health: " + player.getHealth() + "/" + player.getMaxHealth() +
				"      Energy: " + player.getEnergy() + "/"  + player.getMaxEnergy());
		playerStatus +="\nPlayer's Relics: "+player.listRelics();
		playerStatus +="\nPlayer's Potions: "+player.listPotions();
		
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
