package logic;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Holds and allows access to all Card objects. the load method must be called exactly once for this classes' other methods to be used without error.
 */
public class CardsUtil {

	private static ArrayList<Card> playerCards;
	private static ArrayList<Card> monsterCards;
	private static ArrayList<Card> potions;
	private static ArrayList<Relic> relics;
	private static ArrayList<Card> relicCards;
	private static HashMap<String, Card[]> monsterMovesets;
	
	
	public static void load(){
		loadPlayerCards();
		loadMonsterCards();
		loadPotions();
		loadRelicCards();
		loadRelics();
		loadMonsterMovesets();
	}
	/**
	 * Initializes all player Card objects as found in PlayerCards.txt.
	 * basic cards are cards that only do damage,heal and/or block.
	 * skill cards are cards that only change the attributes of the user or the target.
	 * skillAction cards are cards that do basic card functions (damage,heal,block) AND change the attributes of the user or target.
	 */
	public static void loadPlayerCards() {
		playerCards = new ArrayList<Card>();
		try {
			Scanner read = new Scanner(new File("PlayerCards.txt"));
			while (read.hasNextLine()) {
				String[] traits = read.nextLine().split(",");
				
				if (traits[0].equals("basic")) {
					playerCards.add(new Card(Integer.parseInt(traits[2]), Integer.parseInt(traits[3]), Integer.parseInt(traits[4]), Integer.parseInt(traits[5]), Boolean.parseBoolean(traits[6]), traits[1]));
				}
				else if (traits[0].equals("skill")){
					playerCards.add(new Skill(traits[2], Integer.parseInt(traits[3]), Integer.parseInt(traits[4]), Integer.parseInt(traits[5]),
						Integer.parseInt(traits[6]),Integer.parseInt(traits[7]), Boolean.parseBoolean(traits[8]), traits[1]));				
				}
				else if (traits[0].equals("skillAction")){
					playerCards.add(new Skill(Integer.parseInt(traits[2]), Integer.parseInt(traits[3]), Integer.parseInt(traits[4]),
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
	 * Initializes all potion Card objects as found in Potions.txt.
	 * basic cards are cards that only do damage,heal and/or block.
	 * skill cards are cards that only change the attributes of the user or the target.
	 * skillAction cards are cards that do basic card functions (damage,heal,block) AND change the attributes of the user or target.
	 */	
	public static void loadPotions() {
		potions = new ArrayList<Card>();
		try {
			Scanner read = new Scanner(new File("Potions.txt"));
			while (read.hasNextLine()) {
				String[] traits = read.nextLine().split(",");
				
				if (traits[0].equals("basic")) {
					potions.add(new Card(Integer.parseInt(traits[2]), Integer.parseInt(traits[3]), Integer.parseInt(traits[4]), Integer.parseInt(traits[5]), Boolean.parseBoolean(traits[6]), traits[1]));
				}
				else if (traits[0].equals("skill")){
					potions.add(new Skill(traits[2], Integer.parseInt(traits[3]), Integer.parseInt(traits[4]), Integer.parseInt(traits[5]),
						Integer.parseInt(traits[6]),Integer.parseInt(traits[7]), Boolean.parseBoolean(traits[8]), traits[1]));				
				}
				else if (traits[0].equals("skillAction")){
					potions.add(new Skill(Integer.parseInt(traits[2]), Integer.parseInt(traits[3]), Integer.parseInt(traits[4]),
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
	 * Initializes all monster Card objects as found in MonsterCards.txt.
	 * basic cards are cards that only do damage,heal and/or block.
	 * skill cards are cards that only change the attributes of the user or the target.
	 * skillAction cards are cards that do basic card functions (damage,heal,block) AND change the attributes of the user or target.
	 */	
	public static void loadMonsterCards() {
		monsterCards = new ArrayList<Card>();
		try {
			Scanner read = new Scanner(new File("MonsterCards.txt"));
			while (read.hasNextLine()) {
				String[] traits = read.nextLine().split(",");
				
				if (traits[0].equals("basic")) {
					monsterCards.add(new Card(Integer.parseInt(traits[2]), Integer.parseInt(traits[3]), Integer.parseInt(traits[4]), Integer.parseInt(traits[5]), Boolean.parseBoolean(traits[6]), traits[1]));
				}
				else if (traits[0].equals("skill")){
					monsterCards.add(new Skill(traits[2], Integer.parseInt(traits[3]), Integer.parseInt(traits[4]), Integer.parseInt(traits[5]),
						Integer.parseInt(traits[6]),Integer.parseInt(traits[7]), Boolean.parseBoolean(traits[8]), traits[1]));				
				}
				else if (traits[0].equals("skillAction")){
					monsterCards.add(new Skill(Integer.parseInt(traits[2]), Integer.parseInt(traits[3]), Integer.parseInt(traits[4]),
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
	 * Loads all monster movesets, which can be accessed by monster name.
	 */
	public static void loadMonsterMovesets() {
		monsterMovesets = new HashMap<String, Card[]>();
		try {
			Scanner read = new Scanner(new File("MonsterMovesets.txt"));
			while(read.hasNextLine()) {
				String[] a = read.nextLine().split(",");
				Card[] moves = new Card[a.length - 1];
				for (int i = 1; i < a.length; i ++)
					moves[i - 1] = get("Monster " + a[i]);
				
				monsterMovesets.put(a[0], moves);
					
			}
			read.close();
		} catch (FileNotFoundException e) {
			System.out.println("MonsterMovesets.txt not found.");
		}
		
	}
	
	/**
	 * @param monsterName the name of the monster to get the moveset for.
	 * @return the moveset for the monster with the given name.
	 */
	public static Card[] getMonsterMoveset(String monsterName) {
		return monsterMovesets.get(monsterName);
	}
	
	/**
	* Initializes all relics in Relics.txt, relics use cards which are initialized in loadRelicCards
	*/
	public static void loadRelics()  {
		relics = new ArrayList<Relic>();
		try{
			Scanner read = new Scanner(new File("Relics.txt"));
			int i =0;
			while (read.hasNextLine()){
				String[] traits = read.nextLine().split(",");
				relics.add(new Relic(traits[0],traits[1],relicCards.get(i),Integer.parseInt(traits[2]),Integer.parseInt(traits[3]),Integer.parseInt(traits[4])));
				i++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	* Initializes all cards that relics use in RelicCards.txt
	*/	
	
	private static void loadRelicCards() {
		relicCards = new ArrayList<Card>();
		try {
			Scanner read = new Scanner(new File("RelicCards.txt"));
			while (read.hasNextLine()) {
				String[] traits = read.nextLine().split(",");
				
				if (traits[0].equals("basic")) {
					relicCards.add(new Card(Integer.parseInt(traits[2]), Integer.parseInt(traits[3]), Integer.parseInt(traits[4]), Integer.parseInt(traits[5]), Boolean.parseBoolean(traits[6]), traits[1]));
				}
				else if (traits[0].equals("skill")){
					relicCards.add(new Skill(traits[2], Integer.parseInt(traits[3]), Integer.parseInt(traits[4]), Integer.parseInt(traits[5]),
						Integer.parseInt(traits[6]),Integer.parseInt(traits[7]), Boolean.parseBoolean(traits[8]), traits[1]));				
				}
				else if (traits[0].equals("skillAction")){
					relicCards.add(new Skill(Integer.parseInt(traits[2]), Integer.parseInt(traits[3]), Integer.parseInt(traits[4]),
						traits[5], Integer.parseInt(traits[6]), Integer.parseInt(traits[7]), Integer.parseInt(traits[8]),
							Integer.parseInt(traits[9]),Integer.parseInt(traits[10]), Boolean.parseBoolean(traits[11]), traits[1]));
				}
				else if (traits[0].equals("null")){
					relicCards.add(null);
				}
			}
			read.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	
	
	/**
	* Checks the type of the card and creates a copy of it
	* basic cards only belong to the superclass Card, skill cards belong to the subclass Skill.
	* @param aCard card to be checked and copied.
	* @return copy of the card returned.
	*/
	public static Card checkTypeAndCopy(Card aCard) {
		Card copyCard;
		if (aCard instanceof Skill) {
			copyCard = new Skill((Skill)aCard);
		}
		else {
			copyCard = new Card(aCard);
		}
		return copyCard;
	}
	
	/**
	 * @param name name of the card to find.
	 * @return a Card object with the given name, or null if no such card exists.
	 */
	public static Card get(String name) {
		ArrayList<Card> cards = new ArrayList<Card>(playerCards);
		cards.addAll(monsterCards);
		cards.addAll(potions);
		cards.addAll(relicCards);
		for (Card c : cards) {
			if (c.getName().equals(name)) {
				return checkTypeAndCopy(c);
			}
		}
		return null;
	}

	/**
	 * @param name name of the relic to find.
	 * @return a relic object with the given name, or null if no such relic exists.
	 */
	public static Relic getRelic(String name){
		for (Relic r : relics) {
			if (r.getName().equals(name)) {
				return new Relic(r);
			}
		}
		return null;
	}
	
	/**
	 * @return a random Card object (for use by the player).
	 */
	public static Card randomP () {
		Card c;
		while ((c = playerCards.get((int) (Math.random() * playerCards.size()))).getName().contains("Monster"));
		return checkTypeAndCopy(c);
	}
	
	/**
	 * @return a random Card object (for use by the monsters).
	 */
	public static Card randomM () {
		Card c;
		while (!(c = monsterCards.get((int) (Math.random() * monsterCards.size()))).getName().contains("Monster"));
		return checkTypeAndCopy(c);
	}
	
	/**
	 * @return a random potion (which is just a card) (for use by the player).
	 */	
	public static Card randomPotion(){
		Card c;
		while (!(c = potions.get((int) (Math.random() * potions.size()))).getName().contains("Potion"));
		return checkTypeAndCopy(c);
	}
	
	/**
	 * @return a random relic (for use by the player).
	 */	
	public static Relic randomRelic(){
		Relic r;
		r = relics.get((int) (Math.random() * relics.size()));
		return new Relic(r);
	}	

}
