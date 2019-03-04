import java.util.Scanner;


/**
 * Uses a main method to run the primary game loop.
 */
public class Game {

	/**
	 * Main game loop.
	 */
	public static void main(String[] args) {
		CardsUtil.load();		
		Monster slime = new Monster("Slime", 19, CardsUtil.get("Monster Attack"), CardsUtil.get("Monster Block")),
				jawWorm = new Monster("Jaw Worm", 44, CardsUtil.get("Monster Attack"), CardsUtil.get("Monster Block"), CardsUtil.get("Monster BlAttack")),
			    louse = new Monster("Louse", 16, CardsUtil.get("Monster WkAttack"), CardsUtil.get("Monster BlAttack"), CardsUtil.get("Monster StBlock"), CardsUtil.get("Monster Block")),
			    gremlinNob = new Monster("Gremlin Nob", 82, CardsUtil.get("Monster Attack"), CardsUtil.get("Monster StAttack"), CardsUtil.get("Monster HvAttack"));
		
		Monster[] monsters = new Monster[] {slime, jawWorm, louse, gremlinNob};
		
		Scanner in = new Scanner(System.in);
		Player player = new Player(intro(in), 50, CardsUtil.get("Strike"), CardsUtil.get("Strike"), CardsUtil.get("Strike"), CardsUtil.get("Defend"), CardsUtil.randomP(), CardsUtil.randomP(), CardsUtil.randomP());
		
		while (player.alive()) {
			Monster monster = getNextMonster(monsters);
			System.out.println("An opponent has arrived: " + monster.getName());
			player.startCombat();
			
			while (monster.alive() && player.alive()) {
				playerTurn(player, monster);
				player.endTurn();
				endTurn(in, player, monster);
			}
			player.endCombat();
			endCombat(in, player, monster);
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
				+ " Use your CardsUtil to attack and defend against the monsters!");
		return name;
	}
	
	/**
	 * Runs the player's turn, and loops until the player ends their turn.
	 * @param player
	 * @param monster
	 */
	public static void playerTurn(Player player, Monster monster) {
		System.out.println(player.getName() + "'s turn!");
		monster.setMove();
		System.out.println(monster.intentions());
		
		player.startTurn();
		printStats(player, monster);
		
		while (player.nextCard(monster) && monster.alive()) {
			System.out.println(monster.intentions());
			printStats(player, monster);
		}
	}
	
	/**
	 * Prints out end turn messages and prompts the user to hit enter.
	 * @param in this Scanner will be used to take the player's input.
	 * @param player
	 * @param monster
	 */
	public static void endTurn(Scanner in, Player player, Monster monster) {
		if (monster.alive() && player.alive()) {
			pressEnter(in, player.getName() + "'s turn is over!");
			
			System.out.println(monster.getName() + "'s turn!");
			printStats(player, monster);
			
			monster.getMove().use(monster, player);
			System.out.println(monster.actionReport());
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
	public static void endCombat(Scanner in, Player player, Monster monster) {
		if (!player.alive())
			System.out.println("DEFEAT!");
		else {
			pressEnter(in, monster.getName() + " has been slain!");
			Card reward = newCard();
			pressEnter(in, "Your reward: " + reward.getName() + "\n" + reward.getDescription());
			player.addCard(reward);
		}
	}
	
	/**
	 * @return a random player card, cannot be Strike.
	 */
	public static Card newCard() {
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
	 * Prints the health and armour of the given player and monster, as well as the energy of the player.
	 */
	public static void printStats(Entity player, Entity monster) {
		System.out.println("\n" + player.getName() + ":   health: " + player.getHealth() + "/" + player.getMaxHealth() + 
				"      Energy: " + player.getEnergy() + "/"  + player.getMaxEnergy()     + 
				"      Armour: " + player.getArmour().getCurrentVal() + "\n" + 
				monster.getName() + ":    health: " + monster.getHealth() + "/" + monster.getMaxHealth() + 
				"      Armour: " + monster.getArmour().getCurrentVal() + "\n");
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
