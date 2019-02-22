import java.util.Scanner;

public class Player extends Entity {
	private Deck deck;
	
	
	public Player(String name, int maxHealth, Card... cards) {
		super(name, maxHealth, maxHealth, 0, 3, 3);
		deck = new Deck(cards);
	}
	
	public Card nextCard() {
		Scanner in = new Scanner(System.in);
		System.out.println("Enter the number of the card you wish to use, or anything else to end your turn." + "\n" + deck.handToString());
		try {
			return deck.drawFromHand(Integer.parseInt(in.nextLine()));
		} catch (Exception e) {
			return null;
		}
	}
	
	public void startTurn() {
		deck.startTurn();
		setEnergy(getMaxEnergy());
	}
	
	public void endTurn() {
		deck.endTurn();
	}
	
	public void startCombat() {
		deck.startCombat();
		setEnergy(getMaxEnergy());
	}
	
	public void endCombat() {
		deck.endCombat();
	}
	
}
