import java.util.ArrayList;
import java.util.Arrays;

public class Monster extends Entity {
	private ArrayList<Card> moves = new ArrayList<Card>();
	private Card move;
	
	public Monster(String name, int maxHealth, Card... moves) {
		super(name, maxHealth, maxHealth, 0, Integer.MAX_VALUE, Integer.MAX_VALUE);
		fillMoves(new ArrayList<Card> (Arrays.asList(moves)));
	}
	
	public Monster(Monster m) {
		super(m);
		fillMoves(m.getMoves());
	}
	
	private void fillMoves(ArrayList<Card> moves) {
		for (Card c : moves)
			this.moves.add(new Card(c));
	}
	
	public ArrayList<Card> getMoves() {
		ArrayList<Card> newList = new ArrayList<Card>();
		for (Card c: moves)
			newList.add(new Card(c));
		return newList;		
	}
	
	public String intentions() {
		move = moves.get((int) (Math.random() * moves.size()));
		
		String intentions = "";
		if (move.getDamage() > 0)
			intentions += "\nAttack for " + move.getDamage() + " damage";
		if (move.getBlock() > 0)
			intentions += "\nBlock for next turn";
		if (move.getHeal() > 0)
			intentions += "\nHeal";
		
		if (intentions.equals(""))
			intentions = "Unkown";
		
		return getName() +  " intends to:" + intentions;
	}
	
	public Card getMove() {
		return new Card(move);
	}
	
}
