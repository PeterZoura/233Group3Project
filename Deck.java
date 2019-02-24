import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Holds a deck, discard pile, draw pile and hand, each holding Card objects, and methods to mimic the functionality of a deck of cards.
 */
public class Deck {
	private ArrayList<Card> deck = new ArrayList<Card>();
	private ArrayList<Card> discardPile = new ArrayList<Card>();
	private ArrayList<Card> drawPile = new ArrayList<Card>();
	private ArrayList<Card> hand = new ArrayList<Card>();
	
	
	/**
	 * Creates a Deck object with any number of given Cards.
	 * @param cards any number of cards as individual parameters to put in the deck.
	 */
	public Deck (Card...cards) {
		for (Card c : cards)
			deck.add(new Card(c));
	}
	
	/**
	 * Safely clones a given Deck object.
	 * @param d Deck object to clone.
	 */
	public Deck (Deck d) {
		this.deck = d.getDeck();
		this.discardPile = d.getDiscardPile();
		this.drawPile = d.getDrawPile();
		this.hand = d.getHand();
	}
	
	/**
	 * Used locally to reduce repetition, safely clones a given ArrayList of Cards.
	 * @param cardList ArrayList of Cards to clone.
	 * @return cloned list.
	 */
	private static ArrayList<Card> clone (ArrayList<Card> cardList) {
		ArrayList<Card> newList = new ArrayList<Card>();
		for (Card c: cardList)
			newList.add(new Card(c));
		return newList;
	}
	
	/**
	 * @return a numbered list showing each card in the hand.
	 */
	public String handToString() {
		String s = "";
		for (int i = 0; i < hand.size(); i ++) {
			s += "\n" + i + ": " + hand.get(i).getName();
		}
		return s;
	}
	
	/**
	 * Fills the draw pile with all cards present in the deck.
	 */
	public void startCombat() {
		drawPile = clone(deck);
		Collections.shuffle(drawPile);
	}
	
	/**
	 * Clears the draw pile, discard pile, and hand.
	 */
	public void endCombat() {
		drawPile.clear();
		hand.clear();
		discardPile.clear();
	}
	
	/**
	 * If less than 5 cards remain in the draw pile, shuffles the discard pile and moves it to the draw pile. Moves 5 cards from the draw pile to the hand. 
	 */
	public void startTurn() {
		if (drawPile.size() < 5) {
			Collections.shuffle(discardPile);
			drawPile.addAll(clone(discardPile));
			discardPile.clear();
		}
		
		for (int i = 0; i < 5; i ++) {
			hand.add(new Card(drawPile.get(0)));
			drawPile.remove(0);
		}
	}
	
	/**
	 * Moves the hand to the discard pile.
	 */
	public void endTurn() {
		discardPile.addAll(clone(hand));
		hand.clear();
	}

	/**
	 * @return all cards in the deck.
	 */
	public ArrayList<Card> getDeck() {
		return clone(deck);
	}

	/**
	 * @return the Deck's discard pile.
	 */
	public ArrayList<Card> getDiscardPile() {
		return clone(discardPile);
	}

	/**
	 * @return return the Deck's draw pile.
	 */
	public ArrayList<Card> getDrawPile() {
		return clone(drawPile);
	}

	/**
	 * @return return the Deck's hand.
	 */
	public ArrayList<Card> getHand() {
		return clone(hand);
	}

	/**
	 * @return amount of cards in the deck.
	 */
	public int getDeckSize() {
		return this.deck.size();
	}

	/**
	 * @return amount of cards in the draw pile.
	 */
	public int getDrawPileSize() {
		return this.drawPile.size();
	}

	/**
	 * @return amount of cards in the discard pile.
	 */
	public int getDiscardPileSize() {
		return this.discardPile.size();
	}

	/**
	 * @param aCard add this card to the deck.
	 */
	public void addToDeck(Card aCard) {
		deck.add(new Card(aCard));
	}

	/**
	 * @param aCard add this card to the discard pile.
	 */
	public void addToDiscardPile(Card aCard) {
		discardPile.add(new Card(aCard));
	}

	/**
	 * @param aCard add this card to the draw pile.
	 */
	public void addToDrawPile(Card aCard) {
		drawPile.add(new Card(aCard));
	}

	/**
	 * @param aCard add this card to the hand.
	 */
	public void addToHand(Card aCard) {
		hand.add(new Card(aCard));
	}

	/**
	 * @param Cards add this list of cards to the deck.
	 */
	public void addToDeck(List<Card> Cards) {
		deck.addAll(Cards);
	}

	/**
	 * @param Cards add this list of cards to the discard pile.
	 */
	public void addToDiscardPile(List<Card> Cards) {
		discardPile.addAll(Cards);
	}

	/**
	 * @param Cards add this list of cards to the draw pile.
	 */
	public void addToDrawPile(List<Card> Cards) {
		drawPile.addAll(Cards);
	}

	/**
	 * @param Cards add this list of cards to the hand.
	 */
	public void addToHand(List<Card> Cards) {
		hand.addAll(Cards);
	}

	/**
	 * Draw a card from the hand, and discard it.
	 * @param cardIndex location in the hand of the desired Card.
	 * @return the Card at the given index in the hand.
	 */
	public Card drawFromHand(int cardIndex) {
		Card aCard = new Card(hand.get(cardIndex));
		discardPile.add(aCard);
		hand.remove(cardIndex);
		return aCard;
	}

	/**
	 * Draw a card from the deck and remove it from the deck.
	 * @param cardIndex location in the deck of the desired Card.
	 * @return the Card at the given index in the deck.
	 */
	public Card drawFromDeck(int cardIndex) {
		Card aCard = new Card(deck.get(cardIndex));
		return aCard;
	}

}
