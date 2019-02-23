import java.util.Scanner;


public class Game {

	public static void main(String[] args) {
		Cards.load();
		Scanner in = new Scanner(System.in);
		System.out.println("What would you like to name your character?");
		String name = in.nextLine();
		Player player = new Player(name, 80, Cards.get("Strike"), Cards.get("Strike"), Cards.get("Strike"), Cards.get("Block"), Cards.get("Block"), Cards.get("Block"), Cards.get("Block"));
		
		while (player.alive()) {
			
			Monster monster = new Monster("Slime", 19, Cards.get("Over Powered"));
			
			player.startCombat();
			while (monster.alive() && player.alive()) {
				
				System.out.println(monster.intentions());
				
				player.startTurn();
				printStats(player, monster);
				Card c;
				while ((c = player.nextCard()) != null) {
					c.use(player, monster);
					printStats(player, monster);
				}
				player.endTurn();
				
				printStats(player, monster);
				
				monster.getMove().use(monster, player);
				monster.setArmour(0);
				player.setArmour(0);
				
			}
			
			player.endCombat();
			
		}	
		
		in.close();
	}
	
	public static void printStats(Entity player, Entity monster) {
		System.out.println("\n\n\n\n\n\nHealth: " + player.getHealth() + "/" + player.getMaxHealth() + 
				"      Energy: " + player.getEnergy() + "/" + player.getMaxEnergy()      + 
				"      Armour: " + player.getArmour() + 
				"\nMonster health: " + monster.getHealth() + "/" + monster.getMaxHealth() + 
				"      Monster armour: " + monster.getArmour() + "\n");
	}
	
}
