import java.util.ArrayList;
import java.util.Arrays;

/**
 * Inherits all properties and methods of the Entity class, with additional methods and member variables to allow the random access of any given Cards.
 */
public class Monster extends Entity {
	
	private ArrayList<Card> moves = new ArrayList<Card>();
	private Card move;
	
	/**
	 * Creates a Monster with a given name, maximum health, and any number of Cards, which will be randomly accessed as the Monster's moves.
	 */
	public Monster(String name, int maxHealth, Card... moves) {
		super(name, maxHealth, maxHealth, 0, 0, 0);
		fillMoves(new ArrayList<Card> (Arrays.asList(moves)));
	}
	
	/**
	 * Creates a Monster exactly equal to another given Monster.
	 * @param m the Monster to copy.
	 */
	public Monster(Monster m) {
		super(m);
		fillMoves(m.getMoves());
	}
	
	/**
	 * Utility method only used locally, fills the Monster's moves list with an ArrayList of Cards.
	 * @param moves ArrayList of moves(Cards) to give the Monster.
	 */
	private void fillMoves(ArrayList<Card> moves) {
		for (Card c : moves)
			this.moves.add(new Card(c));
	}
	
	/**
	 * @return a copy of the Monster's move list.
	 */
	public ArrayList<Card> getMoves() {
		ArrayList<Card> newList = new ArrayList<Card>();
		for (Card c: moves)
			newList.add(new Card(c));
		return newList;		
	}
	
	/**
	 * Randomly selects a Card as the Monster's next move.
	 */
	public void setMove() {
		move = moves.get((int) (Math.random() * moves.size()));
	}
	
	/**
	 * @return a vague description of the Monster's next move.
	 */
	public String intentions() {
		String intentions = "";
		
		if (move.getDamage() > 0)
			intentions += "Attack: " + move.getDamage() + ". ";
		if (move.getBlock() > 0)
			intentions += "Block. ";
		if (move.getHeal() > 0)
			intentions += "Heal. ";
		
		if (intentions.equals(""))
			intentions = "Unkown. ";
		
		return getName() +  "'s intentions: " + intentions;
	}
	
	/**
	 * @return the actions following the usage of the Monster's move.
	 */
	public String actionReport() {
		String report = "";
		if (move.getDamage() > 0)
			report += getName() + " attacked for " + move.getDamage() + " damage! ";
		if (move.getBlock() > 0)
			report += getName() + " blocked with " + move.getBlock() + " armour! ";	
		if (move.getHeal() > 0)
			report += getName() + " restored " + move.getHeal() + " health! ";
		return report;
	}
	
	/**
	 * @return the Monster's next move.
	 */
	public Card getMove() {
		return new Card(move);
	}
	
}
