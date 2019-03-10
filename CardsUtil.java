import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Holds and allows access to all Card objects. the load method must be called exactly once for this classes' other methods to be used without error.
 */
public class CardsUtil {

	private static ArrayList<Card> cards;
	
	/**
	 * Initializes all Card objects as found in cards.txt. This method must be run once before other methods in Cards are used.
	 * basic cards are cards that only do damage,heal and/or block.
	 * skill cards are cards that only change the attributes of the user or the target.
	 * skillAction cards are cards that do basic card functions (damage,heal,block) AND change the attributes of the user or target.
	 */
	public static void load () {
		cards = new ArrayList<Card>();
		try {
			Scanner read = new Scanner(new File("cards.txt"));
			while (read.hasNextLine()) {
				String[] traits = read.nextLine().split(",");
				
				if (traits[0].equals("basic")) {
					cards.add(new Card(Integer.parseInt(traits[2]), Integer.parseInt(traits[3]), Integer.parseInt(traits[4]), Integer.parseInt(traits[5]), Boolean.parseBoolean(traits[6]), traits[1]));
				}
				else if (traits[0].equals("skill")){
					cards.add(new Skill(traits[2], Integer.parseInt(traits[3]), Integer.parseInt(traits[4]), Integer.parseInt(traits[5]),
						Integer.parseInt(traits[6]),Integer.parseInt(traits[7]), Boolean.parseBoolean(traits[8]), traits[1]));				
				}
				else if (traits[0].equals("skillAction")){
					cards.add(new Skill(Integer.parseInt(traits[2]), Integer.parseInt(traits[3]), Integer.parseInt(traits[4]),
						traits[5], Integer.parseInt(traits[6]), Integer.parseInt(traits[7]), Integer.parseInt(traits[8]),
							Integer.parseInt(traits[9]),Integer.parseInt(traits[10]), Boolean.parseBoolean(traits[11]), traits[1]));
				}
			}
			read.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @param name name of the card to find.
	 * @return a Card object with the given name, or null if no such card exists.
	 */
	public static Card get(String name) {
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
