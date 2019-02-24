import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Holds and allows access to all Card objects. the load method must be called exactly once for this classes' other methods to be used without error.
 */
public class Cards {

	private static ArrayList<Card> cards;
	
	/**
	 * Initializes all Card objects as found in cards.txt. This method must be run once before other methods in Cards are used.
	 */
	public static void load () {
		cards = new ArrayList<Card>();
		try {
			Scanner read = new Scanner(new File("cards.txt"));
			while (read.hasNextLine()) {
				String[] traits = read.nextLine().split(",");
				cards.add(new Card(Integer.parseInt(traits[1]), Integer.parseInt(traits[2]), Integer.parseInt(traits[3]), Integer.parseInt(traits[4]), traits[0]));
			}
			read.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @param name name of the card to find.
	 * @return a Card object with the given name, or null if no such card exists.
	 */
	public static Card get (String name) {
		for (Card c : cards) {
			if (c.getName().equals(name))
				return c;
		}
		return null;
	}
	
	/**
	 * @return a random Card object (for use by the player).
	 */
	public static Card randomP () {
		Card c;
		while ((c = cards.get((int) (Math.random() * cards.size()))).getName().contains("Monster"));
		return c;
	}
	
	/**
	 * @return a random Card object (for use by the monsters).
	 */
	public static Card randomM () {
		Card c;
		while (!(c = cards.get((int) (Math.random() * cards.size()))).getName().contains("Monster"));
		return c;
	}

}
