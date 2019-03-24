
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Uses JUnit to test the functionality of Card.
 */
public class CardTest {
	
	/**
	 * Tests a card with only damage.
	 */
	@Test
	public void testDamage() {
		CardsUtil.load();
		Player player = new Player("player", 80, CardsUtil.get("Strike"), CardsUtil.get("Strike"), CardsUtil.get("kill"), CardsUtil.get("Defend"),CardsUtil.randomP(), CardsUtil.randomP(), CardsUtil.randomP());
		Monster cultist = new Monster("Cultist", 48, CardsUtil.getMonsterMoveset("Cultist"));
		CardsUtil.get("Strike").use(player, cultist);
		assertEquals("Checking card damage", 43, cultist.getHealth());
	}
	
	/**
	 * Tests a card with only block.
	 */
	@Test
	public void testBlock() {
		CardsUtil.load();
		Player player = new Player("player", 80, CardsUtil.get("Strike"), CardsUtil.get("Strike"), CardsUtil.get("kill"), CardsUtil.get("Defend"),CardsUtil.randomP(), CardsUtil.randomP(), CardsUtil.randomP());
		Monster cultist = new Monster("Cultist", 48, CardsUtil.getMonsterMoveset("Cultist"));
		CardsUtil.get("Defend").use(player, cultist);
		assertEquals("Checking card block", 5, player.getArmour().getCurrentVal());
	}
	
	/**
	 * Tests a card with only healing.
	 */
	@Test
	public void testHeal() {
		CardsUtil.load();
		Player player = new Player("player", 80, CardsUtil.get("Strike"), CardsUtil.get("Strike"), CardsUtil.get("kill"), CardsUtil.get("Defend"),CardsUtil.randomP(), CardsUtil.randomP(), CardsUtil.randomP());
		player.damage(10);
		Monster cultist = new Monster("Cultist", 48, CardsUtil.getMonsterMoveset("Cultist"));
		new Card(5, 0, 0, 0, false, "healTest").use(player, cultist);
		assertEquals("Checking card block", 75, player.getHealth());
	}
	
	/**
	 * Tests a card with damage against multiple enemies.
	 */
	@Test
	public void testDamageMultipleEnemies() {
		CardsUtil.load();
		Player player = new Player("player", 80, CardsUtil.get("Strike"), CardsUtil.get("Strike"), CardsUtil.get("kill"), CardsUtil.get("Defend"),CardsUtil.randomP(), CardsUtil.randomP(), CardsUtil.randomP());
		Monster cultist = new Monster("Cultist", 48, CardsUtil.getMonsterMoveset("Cultist"));
		Monster cultist2 = new Monster("Cultist", 48, CardsUtil.getMonsterMoveset("Cultist"));
		CardsUtil.get("Strike").use(player, cultist, cultist2);
		assertEquals("Checking card damage against multiple enemies", 86, cultist.getHealth() + cultist2.getHealth());
	}
	
	/**
	 * Tests a card with all effects. 
	 */
	@Test
	public void testAllEffects() {
		CardsUtil.load();
		Player player = new Player("player", 80, CardsUtil.get("Strike"), CardsUtil.get("Strike"), CardsUtil.get("kill"), CardsUtil.get("Defend"),CardsUtil.randomP(), CardsUtil.randomP(), CardsUtil.randomP());
		player.damage(10);
		Monster cultist = new Monster("Cultist", 48, CardsUtil.getMonsterMoveset("Cultist"));
		new Card(5, 5, 5, 0, false, "allTest").use(player, cultist);
		assertEquals("Checking card with all effects", true, cultist.getHealth() == 43 && player.getArmour().getCurrentVal() == 5 && player.getHealth() == 75);
	}

}
