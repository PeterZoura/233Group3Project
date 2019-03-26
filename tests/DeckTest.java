package tests;


import static org.junit.Assert.*;
import java.util.ArrayList;
import org.junit.Test;

import logic.Card;
import logic.CardsUtil;
import logic.Deck;

/**
 * Tests functionality of Deck using JUnit.
 */
public class DeckTest {

	/**
	 * Tests the encapsulation for getDeck. Other getters do no need to be tested, because they all use the clone method.
	 */
	@Test
	public void testGetDeck() {
		CardsUtil.load();
		Deck deck = new Deck(CardsUtil.get("Strike"), CardsUtil.get("Strike"), CardsUtil.get("kill"), CardsUtil.get("Defend"),CardsUtil.randomP(), CardsUtil.randomP(), CardsUtil.randomP());
		ArrayList<Card> deckClone = deck.getDeck();
		deckClone.clear();
		assertEquals("Testing deck encapsulation", false, deckClone.equals(deck.getDeck()));
	}

	/**
	 * Tests that drawFromHand works as expected.
	 */
	@Test
	public void testDrawFromHand() {
		CardsUtil.load();
		Deck deck = new Deck(CardsUtil.get("Strike"), CardsUtil.get("Bite"), CardsUtil.get("kill"), CardsUtil.get("Defend"),CardsUtil.get("Demon Form"), CardsUtil.get("Die Die Die"), CardsUtil.get("Terror"));
		deck.startCombat();
		deck.startTurn();
		String cName = deck.getHand().get(0).getName();
		deck.drawFromHand(0);
		assertEquals("Checking drawFromHand works correctly", true, deck.getHand().get(0).getName() != cName && deck.getDiscardPile().get(0).getName() == cName);
	}

	/**
	 * Tests the endCombat method.
	 */
	@Test
	public void testEndCombat() {
		CardsUtil.load();
		Deck deck = new Deck(CardsUtil.get("Strike"), CardsUtil.get("Strike"), CardsUtil.get("kill"), CardsUtil.get("Defend"),CardsUtil.randomP(), CardsUtil.randomP(), CardsUtil.randomP());
		deck.startCombat();
		deck.startTurn();
		deck.drawFromHand(0);
		deck.endCombat();
		assertEquals("Testing deck end combat", true, deck.getDrawPile().isEmpty() && deck.getHand().isEmpty() && deck.getDiscardPile().isEmpty());
	}

	/**
	 * Tests the startCombat method.
	 */
	@Test
	public void testStartCombat() {
		CardsUtil.load();
		Deck deck = new Deck(CardsUtil.get("Strike"), CardsUtil.get("Strike"), CardsUtil.get("kill"), CardsUtil.get("Defend"),CardsUtil.randomP(), CardsUtil.randomP(), CardsUtil.randomP());
		deck.startCombat();
		assertEquals("Testing deck start combat", 7, deck.getDrawPileSize());
	}

	/**
	 * Tests the endTurn method.
	 */
	@Test
	public void testEndTurn() {
		CardsUtil.load();
		Deck deck = new Deck(CardsUtil.get("Strike"), CardsUtil.get("Strike"), CardsUtil.get("kill"), CardsUtil.get("Defend"),CardsUtil.randomP(), CardsUtil.randomP(), CardsUtil.randomP());
		deck.startCombat();
		deck.startTurn();
		deck.drawFromHand(0);
		deck.endTurn();
		assertEquals("Testing deck end turn", 5, deck.getDiscardPileSize());
	}

	/**
	 * Tests the startTurn method.
	 */
	@Test
	public void testStartTurn() {
		CardsUtil.load();
		Deck deck = new Deck(CardsUtil.get("Strike"), CardsUtil.get("Strike"), CardsUtil.get("kill"), CardsUtil.get("Defend"),CardsUtil.randomP(), CardsUtil.randomP(), CardsUtil.randomP());
		deck.startCombat();
		deck.startTurn();
		assertEquals("Testing deck start turn", 5, deck.getHand().size());
	}
}
