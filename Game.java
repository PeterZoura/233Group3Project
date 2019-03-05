import java.util.Scanner;
import java.util.*;


/**
 * Uses a main method to run the primary game loop.
 */
public class Game {

	/**
	 * Main game loop.
	 */
	public static void main(String[] args) {
		
		CardsUtil.load();
		
		Monster[] monsters = {new Monster("Slime", 19, CardsUtil.get("Monster Attack"), CardsUtil.get("Monster Block")),
							  new Monster("Jaw Worm", 44, CardsUtil.get("Monster Attack"), CardsUtil.get("Monster Block"), CardsUtil.get("Monster BlAttack")),
							  new Monster("Louse", 16, CardsUtil.get("Monster WkAttack"), CardsUtil.get("Monster BlAttack"), CardsUtil.get("Monster StBlock"), CardsUtil.get("Monster Block")),
							  new Monster("Gremlin Nob", 82, CardsUtil.get("Monster Attack"), CardsUtil.get("Monster StAttack"), CardsUtil.get("Monster HvAttack"))};
		
		Scanner in = new Scanner(System.in);
		
		System.out.println("Please enter your name:");
		String name = in.nextLine();
		Player player = new Player(name, 50, CardsUtil.get("Strike"), CardsUtil.get("Strike"), CardsUtil.get("Strike"), CardsUtil.get("Defend"), CardsUtil.get("Flex"), CardsUtil.get("Seeping"), CardsUtil.get("Regrow"), CardsUtil.randomP(), CardsUtil.randomP(), CardsUtil.randomP());
		
		pressEnter(in, "Welcome, " + name + ", to Slay the Spire! In this brief demo, you will be challenged by enemies until you are defeated."
				+ " Use your CardsUtil to attack and defend against the monsters!");
		
		while (player.alive()) {
			
			Monster monster = getNextMonster(monsters);
			
			System.out.println("An opponent has arrived: " + monster.getName());
			
			player.startCombat();
			while (monster.alive() && player.alive()) {
				
				System.out.println(player.getName() + "'s turn!");
				monster.setMove();
				System.out.println(monster.intentions());
				
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
					monster.startTurn();
					printStats(player, monster);
					
					monster.getMove().use(monster, player);
					System.out.println(monster.actionReport());
					monster.endTurn();
					printStats(player, monster);
					pressEnter(in, "");
				}
				
			}
			
			player.endCombat();
			
			if (!player.alive())
				System.out.println("DEFEAT!");
			else {
				pressEnter(in, monster.getName() + " has been slain!");
				Card reward = newCard();
				pressEnter(in, "Your reward: " + reward.getName() + "\n" + reward.getDescription());
				player.addCard(reward);
			}
			
		}	
		
		in.close();
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
	 * Prints the health, armour and various non-zero attributes of the given player and monster, as well as the energy of the player.
	 */
	public static void printStats(Entity player, Entity monster) {
		ArrayList<String> attributeList = new ArrayList<String>(Arrays.asList("Strength", "Dexterity", "Weak", "Vulnerable", "Regeneration", "Poison", "Constricted", "Armour"));
		
		String playerStatus = ("\n" + player.getName() + ":   health: " + player.getHealth() + "/" + player.getMaxHealth() + 
				"      Energy: " + player.getEnergy() + "/"  + player.getMaxEnergy());
		int i = 0;
		for (Attribute a : new Attribute[] {player.getStrength(), player.getDexterity(), player.getWeak(), player.getVulnerable(), player.getRegeneration(), 
		player.getPoison(), player.getConstricted(), player.getArmour()}){
			if (a.getCurrentVal()!= 0){
				playerStatus += String.format("      %s: %d", attributeList.get(i), a.getCurrentVal());
			}
			i++;
		}
		
		String monsterStatus = (monster.getName() + ":    health: " + monster.getHealth() + "/" + monster.getMaxHealth());
		int j = 0;
		for (Attribute a : new Attribute[] {monster.getStrength(), monster.getDexterity(), monster.getWeak(), monster.getVulnerable(), monster.getRegeneration(), 
		monster.getPoison(), monster.getConstricted(), monster.getArmour()}){
			if (a.getCurrentVal()!= 0){
				monsterStatus += String.format("      %s: %d", attributeList.get(j), a.getCurrentVal());
			}
			j++;
		}
		System.out.println(playerStatus);
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
