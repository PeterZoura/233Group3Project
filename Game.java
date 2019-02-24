import java.util.Scanner;


/**
 * Uses a main method to run the primary game loop.
 */
public class Game {

	/**
	 * Main game loop.
	 */
	public static void main(String[] args) {
		
		Cards.load();
		
		Monster[] monsters = {new Monster("Slime", 19, Cards.get("Monster Attack"), Cards.get("Monster Block")),
							  new Monster("Jaw Worm", 44, Cards.get("Monster Attack"), Cards.get("Monster Block"), Cards.get("Monster BlAttack")),
							  new Monster("Louse", 16, Cards.get("Monster WkAttack"), Cards.get("Monster BlAttack"), Cards.get("Monster StBlock"), Cards.get("Monster Block")),
							  new Monster("Gremlin Nob", 82, Cards.get("Monster Attack"), Cards.get("Monster StAttack"), Cards.get("Monster HvAttack"))};
		
		Scanner in = new Scanner(System.in);
		
		System.out.println("Please enter your name:");
		String name = in.nextLine();
		Player player = new Player(name, 50, Cards.get("Strike"), Cards.get("Strike"), Cards.get("Strike"), Cards.get("Block"), Cards.randomP(), Cards.randomP(), Cards.randomP());
		
		pressEnter(in, "Welcome, " + name + ", to Slay the Spire! In this brief demo, you will be challenged by enemies until you are defeated."
				+ " Use your cards to attack and defend against the monsters!");
		
		while (player.alive()) {
			
			Monster monster = getNextMonster(monsters);
			
			System.out.println("An opponent has arrived: " + monster.getName());
			
			player.startCombat();
			while (monster.alive() && player.alive()) {
				
				System.out.println(player.getName() + "'s turn!");
				monster.setMove();
				System.out.println(monster.intentions());
				
				player.setArmour(0);
				player.startTurn();
				printStats(player, monster);
				
				while (player.nextCard(monster) && monster.alive()) {
					System.out.println(monster.intentions());
					printStats(player, monster);
				}
				
				player.endTurn();
				
				if (monster.alive() && player.alive()) {
					pressEnter(in, player.getName() + "'s turn is over!");
					
					System.out.println(monster.getName() + "'s turn!");
					monster.setArmour(0);
					printStats(player, monster);
					
					monster.getMove().use(monster, player);
					System.out.println(monster.actionReport());
					printStats(player, monster);
					pressEnter(in, "");
				}
				
			}
			
			player.endCombat();
			
			if (!player.alive())
				System.out.println("DEFEAT!");
			else
				pressEnter(in, monster.getName() + " has been slain!");
			
		}	
		
		in.close();
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
				"      Armour: " + player.getArmour() + "\n" + 
				monster.getName() + ":    health: " + monster.getHealth() + "/" + monster.getMaxHealth() + 
				"      Armour: " + monster.getArmour() + "\n");
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
