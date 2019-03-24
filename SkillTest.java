
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Tests functionalities of Skill using JUnit.
 */
public class SkillTest {
	
	/**
	 * Tests a Skill with an instant modification.
	 */
	@Test
	public void testCurrentModifier() {
		CardsUtil.load();
		Player player = new Player("player", 80, CardsUtil.get("Strike"), CardsUtil.get("Strike"), CardsUtil.get("kill"), CardsUtil.get("Defend"),CardsUtil.randomP(), CardsUtil.randomP(), CardsUtil.randomP());
		Monster monster = new Monster("monster", 20, new Card[] {});
		CardsUtil.get("Monster Strength").use(monster, player);
		assertEquals("Checking skill with instant modifier", 3, monster.getStrength().getCurrentVal());
	}
	
	/**
	 * Tests a Skill with a start turn modifier.
	 */
	@Test
	public void testStartModifer() {
		CardsUtil.load();
		Player player = new Player("player", 80, CardsUtil.get("Strike"), CardsUtil.get("Strike"), CardsUtil.get("kill"), CardsUtil.get("Defend"),CardsUtil.randomP(), CardsUtil.randomP(), CardsUtil.randomP());
		Monster monster = new Monster("monster", 20, new Card[] {});
		CardsUtil.get("Monster Strength Power").use(monster, player);
		for (int i = 0; i < 4; i ++) {
			monster.startTurn();
			monster.endTurn();
		}
		assertEquals("Checking skill with start turn modifier", 8, monster.getStrength().getCurrentVal());
	}
	
	/**
	 * Tests a Skill with an end turn modifier.
	 */
	@Test
	public void testEndModifer() {
		CardsUtil.load();
		Player player = new Player("player", 80, CardsUtil.get("Strike"), CardsUtil.get("Strike"), CardsUtil.get("kill"), CardsUtil.get("Defend"),CardsUtil.randomP(), CardsUtil.randomP(), CardsUtil.randomP());
		Monster monster = new Monster("monster", 20, new Card[] {});
		new Skill("strength", 0, 0, 2, -1, 0, false, "endTest").use(monster, player);
		for (int i = 0; i < 4; i ++) {
			monster.startTurn();
			monster.endTurn();
		}
		assertEquals("Checking skill with end turn modifier", 8, monster.getStrength().getCurrentVal());
	}
	
	/**
	 * Tests a Skill with an end and start turn modifier.
	 */
	@Test
	public void testStartAndEndModifer() {
		CardsUtil.load();
		Player player = new Player("player", 80, CardsUtil.get("Strike"), CardsUtil.get("Strike"), CardsUtil.get("kill"), CardsUtil.get("Defend"),CardsUtil.randomP(), CardsUtil.randomP(), CardsUtil.randomP());
		Monster monster = new Monster("monster", 20, new Card[] {});
		CardsUtil.get("Monster Strength Power").use(monster, player);
		new Skill("strength", 0, 0, 2, -1, 0, false, "endTest").use(monster, player);
		for (int i = 0; i < 4; i ++) {
			monster.startTurn();
			monster.endTurn();
		}
		assertEquals("Checking skill with end and start turn modifier", 16, monster.getStrength().getCurrentVal());
	}
	
	/**
	 * Tests a Skill with an instant modifier and damage.
	 */
	@Test
	public void testSkillAction() {
		CardsUtil.load();
		Player player = new Player("player", 80, CardsUtil.get("Strike"), CardsUtil.get("Strike"), CardsUtil.get("kill"), CardsUtil.get("Defend"),CardsUtil.randomP(), CardsUtil.randomP(), CardsUtil.randomP());
		Monster monster = new Monster("monster", 20, new Card[] {});
		CardsUtil.get("Bash").use(player, monster);
		monster.startTurn();
		monster.endTurn();
		assertEquals("Checking skill with an instant modifier and damage", true, monster.getVulnerable().equals(1) && monster.getHealth() == 12);
	}

}
