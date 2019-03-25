package logic;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Inherits all properties and methods of the Entity class, with additional methods for user interaction and control. Holds a Deck object to store and use Card objects.
 */
public class Player extends Entity {
	private Deck deck;
	private ArrayList<Relic> relics = new ArrayList<Relic>();
	private ArrayList<Card> potions = new ArrayList<Card>();
	private int potionsLimit=3;


	/**
	*getter for deck
	*/
	public Deck getDeck(){
		return new Deck(deck);
	}

	/**
	* getter for Potions
	*/
	public ArrayList<Card> getPotions(){
		return Deck.clone(potions);
	}

	/**
	* getter for Relics
	*/
	public ArrayList<Relic> getRelics(){
		ArrayList<Relic> newList = new ArrayList<Relic>();
			for (Relic r: relics)
				newList.add(new Relic(r));
			return newList;
	}

	/**
	*	remove Potions
	*/

	public void removePotion(int index){
		potions.remove(index);
	}

	/**
	 * Creates a Player object with a given name, maximum health, and any number of Cards to put in their deck.
	 */
	public Player(String name, int maxHealth, Card... cards) {
		super(name, maxHealth, maxHealth, 0, 3, 3);
		deck = new Deck(cards);
	}

	/**
	*@param Relic a relic to be added
	*/
	public void addRelic(Relic aRelic){
		relics.add(aRelic);
		useRelics("p");
	}
	/**
	 * adds potion(a Card) to potions  if it is below or equal to limit and sets it's cost to 0
	 */

	public void addPotion(Card aCard) {
		if(potions.size()<= potionsLimit){
			aCard.setCost(0);
			potions.add(aCard);
		}
	}
	/**
	 * sets the capacity of  a player to hold potions
	 * @param i
	 */
	public void setPotionsLimit(int i) {
		potionsLimit  = i;
	}

	/** Private utility method used to number and print all Entities from an array.
	 * @param targets print these Entities.
	 */
	private void printTargets(Entity[] targets) {
		for (int i = 0; i < targets.length; i ++)
			System.out.println((i + 1) + ": " + targets[i].getName());
	}

	/** Attempts to use a given card. Prints "You don't have enough energy" when it is unsuccessful. Discards the card if it is successful.
	 * @param card the card to attempt to use.
	 * @param cardIndex the index of the given card(used to discard the card if it is successfully used).
	 * @param targets any number of Entities to target with the card.
	 */
	private void attemptCardUse(Card card, int cardIndex, Entity... targets) {
		if (card.use(this, targets)) {
			deck.drawFromHand(cardIndex);
		} else {
			System.out.println("You don't have enough energy.");
		}
	}

	/**
	 * uses potion and removes it from potions arraylist
	 * @param card
	 * @param cardIndex
	 * @param targets
	 */
	private boolean usePotions(Card card, int index, Entity... targets) {
		potions.get(index).use(this, targets);
		potions.remove(index);
		return true;
	}

	/**
	 * displays potions in order with indexing
	 * @return s
	 */
	public String displayPotions() {
		String s = "";
		for (int i = 0; i < potions.size(); i ++) {
			s += "\n" + i + ": " + potions.get(i).getName();
		}
		return s;
	}
	/**
	 * lists potions in one line
	 * @return string
	 */
	public String listPotions() {
		String toBeReturned= "";
		int i = 0;
		for(Card c: potions) {
			i++;
			toBeReturned +=c.getName()+" ";
			toBeReturned +=(potions.size()==i)?".":", ";
		}
		return toBeReturned;
	}
	/**
	 * lists relics in one line
	 * @return string
	 */
	public String listRelics() {
		String toBeReturned= "";
		int i = 0;
		for(Relic r: relics) {
			i++;
			toBeReturned +=r.getName();
			toBeReturned +=(relics.size()==i)?".":", ";
		}
		return toBeReturned;
	}

	/**
	 * Asks player if player wants to use potions,given player has available potions.Prompts the player to select a Card from their hand, and attempts to use it on the player(as the user) and the given target. If the user enters
	 * an incorrect index or String value, this will be caught by the try catch block as an IndexOutOfBoundsException or InputMismatchException, and
	 * returns false, ending the player's turn. Otherwise, whether the card use is successful or not, nextCard will return true, continuing the turn.
	 *
	 * @param target the Entity to attempt to use any cards on.
	 * @return false if the player wishes to end his/her turn, true if they wish to continue.
	 */
	public boolean nextCard(Entity... target) {
		Scanner in = new Scanner(System.in);
		if (potions.size()>0) {
			System.out.println("\nEnter p if you want to use potions\nEnter anything else to use cards");
			if (in.nextLine().equals("p") ) {
				//System.out.println("Potions: "+displayPotions());
				System.out.println("Enter the number of the potions you wish to use, or anything else to use cards." + "\n" + displayPotions());
				try {
					int whichCard = Integer.parseInt(in.nextLine());

					Card card = potions.get(whichCard);

					if (!card.requiresTarget()) {
						System.out.println(card.describeStats() + "\nPress enter to use, type anything else to go back.");

						if (in.nextLine().equals("")) {
							usePotions(card, whichCard, target);
						} else {
							System.out.println("Potion cancelled");
						}
					} else {
						System.out.println(card.describeStats() + "\nEnter the number corresponding to your target, or anything else to go back.");
						printTargets(target);
						try {
							usePotions(card, whichCard, target[Integer.parseInt(in.nextLine()) - 1]);
						} catch(Exception e) {
							System.out.println("Potion Cancelled");
						}

					}
					return true;
				} catch (Exception e) {
					return false;
				}
			}
		}

		System.out.println("Enter the number of the card you wish to use, anything else to end your turn." + "\n" + deck.handToString());
		try {
			int whichCard = Integer.parseInt(in.nextLine());

			Card card = deck.getHand().get(whichCard);

			if (!card.requiresTarget()) {
				System.out.println(card.getDescription() + "\nPress enter to use, type anything else to go back.");

				if (in.nextLine().equals("")) {
					attemptCardUse(card, whichCard, target);
				} else {
					System.out.println("Card cancelled");
				}
			} else {
				System.out.println(card.getDescription() + "\nEnter the number corresponding to your target, or anything else to go back.");
				printTargets(target);
				try {
					attemptCardUse(card, whichCard, target[Integer.parseInt(in.nextLine()) - 1]);
				} catch(Exception e) {
					System.out.println("Card Cancelled");
				}

			}

			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Updates the Player's attributes, fills the Player's hand and resets the Player's energy.
	 */
	public void startTurn(int turnCount,Entity... target) {
		super.startTurn();
		deck.startTurn();
		setEnergy(getMaxEnergy());
		useRelics("iS",turnCount, target);
	}

	/**
	 * Updates the Player's attributes, and empties the Player's hand into the discard pile.
	 */
	public void endTurn(int turnCount,Entity... target) {
		super.endTurn();
		deck.endTurn();
		useRelics("iE",turnCount, target);
	}

	/**
	 * Resets the Player's attributes, and fills the Player's draw pile.
	 */
	public void startCombat(int iterations,Entity... target) {
		super.startCombat();
		deck.startCombat();
		useRelics("s",iterations ,target);
	}

	/**
	 * Empties the Player's draw pile, hand, and discard pile.
	 */
	public void endCombat() {
		deck.endCombat();
		useRelics("e");
	}

	/**
	 * @param c add this card to the player's deck.
	 */
	public void addCard(Card c) {
		deck.addToDeck(c);
	}


	/**
	* @param use a relics card ability if it has one
	*/
	public void useRelics(String type ,int iteration, Entity... target){
		for(Relic r: this.relics)
			if(type.equals(r.getType())){
				r.use(iteration,this,target);
			}
	}
	/**
	* @param
	*/
	public void useRelics(String type){
		for(Relic r: this.relics)
			if(type.equals(r.getType())){
				r.use(this);
			}
	}

}
