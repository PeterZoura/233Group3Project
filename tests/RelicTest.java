package tests;


import static org.junit.Assert.*;
import org.junit.Test;

import logic.CardsUtil;
import logic.Player;


public class RelicTest {
	
	@Test
	public void testStartCombat() {
		CardsUtil.load();
		Player player = new Player("player", 80, CardsUtil.get("Strike"), CardsUtil.get("Strike"), CardsUtil.get("kill"), CardsUtil.get("Defend"),CardsUtil.randomP(), CardsUtil.randomP(), CardsUtil.randomP());
		player.damage(10);
		player.addRelic(CardsUtil.getRelic("Iron Blood"));
		player.startCombat();
		player.endCombat();
		assertEquals("Testing healing relic at end of combat.", 76, player.getHealth());
	}
}
