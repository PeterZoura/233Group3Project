package tests;


import static org.junit.Assert.*;
import org.junit.Test;

import logic.Card;
import logic.CardsUtil;
import logic.Player;
import logic.Relic;
import logic.Skill;


/**
 * Tests the functionality of Relic using JUnit.
 */
public class RelicTest {
	
	/**
	 * Tests a relic that applies its effect at the end of combat.
	 */
	@Test
	public void testEndCombat() {
		CardsUtil.load();
		Player player = new Player("player", 80, CardsUtil.get("Strike"), CardsUtil.get("Strike"), CardsUtil.get("kill"), CardsUtil.get("Defend"),CardsUtil.randomP(), CardsUtil.randomP(), CardsUtil.randomP());
		player.damage(20);
		player.addRelic(CardsUtil.getRelic("Iron Blood"));
		for (int i = 0; i < 2; i ++) {
			player.startCombat();
			player.endCombat();
		}
		assertEquals("Testing healing relic at end of combat.", 72, player.getHealth());
	}
	
	/**
	 * Tests a relic that applies its effect at the start of combat.
	 */
	@Test
	public void testStartCombat() {
		CardsUtil.load();
		Player player = new Player("player", 80, CardsUtil.get("Strike"), CardsUtil.get("Strike"), CardsUtil.get("kill"), CardsUtil.get("Defend"),CardsUtil.randomP(), CardsUtil.randomP(), CardsUtil.randomP());
		player.damage(20);
		player.addRelic(CardsUtil.getRelic("Vajra"));
		for (int i = 0; i < 2; i ++) {
			player.startCombat();
		}
		assertEquals("Testing strength relic at start of combat.", 1, player.getStrength().getCurrentVal());
	}
	
	/**
	 * Tests a relic that applies its effect without a card.
	 */
	@Test
	public void testNoCard() {
		CardsUtil.load();
		Player player = new Player("player", 80, CardsUtil.get("Strike"), CardsUtil.get("Strike"), CardsUtil.get("kill"), CardsUtil.get("Defend"),CardsUtil.randomP(), CardsUtil.randomP(), CardsUtil.randomP());
		player.damage(20);
		player.addRelic(new Relic("e", "noCardTest", null, 0, 5, 0));
		for (int i = 0; i < 2; i ++) {
			player.startCombat();
			player.endCombat();
		}
		assertEquals("Testing healing relic without a card.", 70, player.getHealth());
	}
	
	/**
	 * Tests a relic that effects energy.
	 */
	@Test
	public void testEnergy() {
		CardsUtil.load();
		Player player = new Player("player", 80, CardsUtil.get("Strike"), CardsUtil.get("Strike"), CardsUtil.get("kill"), CardsUtil.get("Defend"),CardsUtil.randomP(), CardsUtil.randomP(), CardsUtil.randomP());
		player.damage(20);
		player.addRelic(new Relic("iS", "energyTest", null, 1, 0, 1));
		player.startCombat();
		player.startTurn();
		assertEquals("Testing energy relic at start of turn.", 4, player.getEnergy());
	}
	
	/**
	 * Tests a relic that applies an effect each time a turn starts.
	 */
	@Test
	public void testIterativeStart() {
		CardsUtil.load();
		Player player = new Player("player", 80, CardsUtil.get("Strike"), CardsUtil.get("Strike"), CardsUtil.get("kill"), CardsUtil.get("Defend"),CardsUtil.randomP(), CardsUtil.randomP(), CardsUtil.randomP());
		player.damage(20);
		player.addRelic(new Relic("iS", "iterativeStartTest", new Card(0, 0, 5, 0, false, "blockTest"), 0, 0, 1));
		player.startCombat();
		player.startTurn();
		assertEquals("Testing relic that applies its effect at the start of each turn.", 5, player.getArmour().getCurrentVal());
	}
	
	/**
	 * Tests a relic that applies an effect at the start of every third turn.
	 */
	@Test
	public void testIterativeStartThreeTurns() {
		CardsUtil.load();
		Player player = new Player("player", 80, CardsUtil.get("Strike"), CardsUtil.get("Strike"), CardsUtil.get("kill"), CardsUtil.get("Defend"),CardsUtil.randomP(), CardsUtil.randomP(), CardsUtil.randomP());
		player.damage(20);
		player.addRelic(new Relic("iS", "iterativeStartTest", new Skill(0, 0, 0, "strength", 1, 0, 0, 0, 0, false, "threeTurnsTest"), 0, 0, 1));
		player.startCombat();
		for (int i = 0; i < 8; i ++) {
		player.startTurn();
		player.endTurn();
		}
		assertEquals("Testing relic that applies its effect at the start of every third turn.", 2, player.getStrength().getCurrentVal());
	}
	
	/**
	 * Tests a relic that applies an effect each time a turn ends.
	 */
	@Test
	public void testIterativeEnd() {
		CardsUtil.load();
		Player player = new Player("player", 80, CardsUtil.get("Strike"), CardsUtil.get("Strike"), CardsUtil.get("kill"), CardsUtil.get("Defend"),CardsUtil.randomP(), CardsUtil.randomP(), CardsUtil.randomP());
		player.damage(20);
		player.addRelic(new Relic("iE", "iterativeEndTest", new Skill(0, 0, 0, "strength", 1, 0, 0, 0, 0, false, "strengthTest"), 0, 0, 1));
		player.startCombat();
		for (int i = 0; i < 5; i ++) {
			player.startTurn();
			player.endTurn();
		}
		assertEquals("Testing relic that applies its effect at the end of each turn.", 5, player.getArmour().getCurrentVal());
	}
	
	/**
	 * Tests a relic that applies an effect at the end of every third turn.
	 */
	@Test
	public void testIterativeEndThreeTurns() {
		CardsUtil.load();
		Player player = new Player("player", 80, CardsUtil.get("Strike"), CardsUtil.get("Strike"), CardsUtil.get("kill"), CardsUtil.get("Defend"),CardsUtil.randomP(), CardsUtil.randomP(), CardsUtil.randomP());
		player.damage(20);
		player.addRelic(new Relic("iE", "iterativeEndTest", new Skill(0, 0, 0, "strength", 1, 0, 0, 0, 0, false, "threeTurnsTest"), 0, 0, 3));
		player.startCombat();
		for (int i = 0; i < 8; i ++) {
		player.startTurn();
		player.endTurn();
		}
		assertEquals("Testing relic that applies its effect at the end of every third turn.", 2, player.getStrength().getCurrentVal());
	}
	
	/**
	 * Tests a relic that applies a permanent health boost.
	 */
	@Test
	public void testPermanentHealthBoost() {
		CardsUtil.load();
		Player player = new Player("player", 80, CardsUtil.get("Strike"), CardsUtil.get("Strike"), CardsUtil.get("kill"), CardsUtil.get("Defend"),CardsUtil.randomP(), CardsUtil.randomP(), CardsUtil.randomP());
		player.damage(20);
		player.addRelic(new Relic("p", "permanentTest", null, 0, 5, 0));
		for (int i = 0; i < 5; i ++) {
			player.startCombat();
			player.startTurn();
			player.endTurn();
		}
		assertEquals("Testing relic that increases max HP by 5 permanently.", 85, player.getMaxHealth());
	}
	
	/**
	 * Tests a relic that applies a permanent energy boost.
	 */
	@Test
	public void testPermanentEnergyBoost() {
		CardsUtil.load();
		Player player = new Player("player", 80, CardsUtil.get("Strike"), CardsUtil.get("Strike"), CardsUtil.get("kill"), CardsUtil.get("Defend"),CardsUtil.randomP(), CardsUtil.randomP(), CardsUtil.randomP());
		player.damage(20);
		player.addRelic(new Relic("p", "permanentTest", null, 1, 0, 0));
		for (int i = 0; i < 5; i ++) {
			player.startCombat();
			player.startTurn();
			player.endTurn();
		}
		assertEquals("Testing relic that increases max energy by 1 permanently.", 4, player.getMaxEnergy());
	}
	
}
