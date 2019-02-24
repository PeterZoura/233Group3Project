import java.util.Scanner;

/**
 * Inherits all properties and methods of the Entity class, with additional methods for user interaction and control. Holds a Deck object to store and use Card objects.
 */
public class Player extends Entity {
	
	private Deck deck;
	
	/**
	 * Creates a Player object with a given name, maximum health, and any number of Cards to put in their deck.
	 */
	public Player(String name, int maxHealth, Card... cards) {
		super(name, maxHealth, maxHealth, 0, 3, 3);
		deck = new Deck(cards);
	}
	
	/**
	 * Prompts the player to select a Card from their hand, and attempts to use it on the player(as the user) and the given target. If the user enters
	 * an incorrect index or String value, this will be caught by the try catch block as an IndexOutOfBoundsException or InputMismatchException, and
	 * returns false, ending the player's turn. Otherwise, whether the card use is successful or not, nextCard will return true, continuing the turn.
	 * 
	 * @param target the Entity to attempt to use any cards on.
	 * @return false if the player wishes to end his/her turn, true if they wish to continue.
	 */
	public boolean nextCard(Entity target) {
		Scanner in = new Scanner(System.in);
		System.out.println("Enter the number of the card you wish to use, or anything else to end your turn." + "\n" + deck.handToString());
		try {
			int whichCard = Integer.parseInt(in.nextLine());
			
			Card card = deck.getHand().get(whichCard);
			System.out.println(card.getDescription() + "\nPress enter to use, type anything else to go back.");
			
			if (in.nextLine().equals("")) {
				
				if (card.use(this, target)) {
					deck.drawFromHand(whichCard);
				} else {
					System.out.println("You don't have enough energy.");
				}
				
			} else {
				System.out.println("Card cancelled");
			}
			
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * Fills the Player's hand and resets the Player's energy.
	 */
	public void startTurn() {
		deck.startTurn();
		setEnergy(getMaxEnergy());
	}
	
	/**
	 * Empties the Player's hand into the discard pile.
	 */
	public void endTurn() {
		deck.endTurn();
	}
	
	/**
	 * Fills the Player's draw pile and fills the Player's energy.
	 */
	public void startCombat() {
		deck.startCombat();
		setEnergy(getMaxEnergy());
	}
	
	/**
	 * Empties the Player's draw pile, hand, and discard pile.
	 */
	public void endCombat() {
		deck.endCombat();
	}
	
}
