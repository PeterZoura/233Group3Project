import java.util.ArrayList;
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

	// Getter method for Deck,discardPile and drawPile
	public ArrayList<Card> getPile(ArrayList<Card> cardSource) { //Not sure what the purpose of this method is.
		ArrayList<Card> tempDeck = new ArrayList<Card>();
		for (Card temp : cardSource) {
			tempDeck.add(temp);
		}
		return tempDeck;
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
		deck.remove(cardIndex);
		return aCard;
	}

	/*
	 * Didn't make a constructor class for Deck because I assume you'll need to add
	 * cards separately, to Hand, discard Pile...
	 */
	
	/*

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("____Deck");
		for (Card aCard : deck) {
			builder.append("\n");
			builder.append(aCard.getName());
		}
		builder.append("\n");
		builder.append("____DiscardPile");
		for (Card aCard : discardPile) {
			builder.append("\n");
			builder.append(aCard.getName());
		}
		builder.append("\n");
		builder.append("____Hand");
		for (Card aCard : hand) {
			builder.append("\n");
			builder.append(aCard.getName());
		}
		builder.append("\n");
		return builder.toString();
	}

	public static void main(String[] Args) {
		Card nCard = new Card(1, 2, 0, "Vampiric Touch");
		Card bCard = new Card(0, 0, 0, "Splash");
		Card cCard = new Card(0, 1, 0, "Poke");
		Card dCard = new Card(5, 5, 5, "Divine Slap", "Wow such holiness");
		Card zCard = new Card(6, 6, 6, "Summoning");
		System.out.println(nCard.getName());
		System.out.println(nCard.getDescription());
		Deck aDeck = new Deck();
		List<Card> stack = Arrays.asList(nCard, bCard, cCard, dCard, zCard);
		aDeck.addToDeck(stack);
		aDeck.addToHand(stack);
		Card takenFromHand = new Card(aDeck.drawFromHand(1));
		Card takenFromDeck = new Card(aDeck.drawFromDeck(3));
		System.out.println(aDeck.toString());

	}
	
	*/

}
