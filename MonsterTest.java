
import static org.junit.Assert.*;
import org.junit.Test;


/**
 * Uses JUnit to test the behavior of Monster.
 */
public class MonsterTest {

	/**
	 * Checks intentions with an attack.
	 */
	@Test
	public void testIntentionsAttack() {
		CardsUtil.load();
		Monster m = new Monster("test", 20, new Card[] {CardsUtil.get("Monster Attack")});
		m.setMove();
		assertEquals("Checking monster inentions with attack", "test's intentions: Attack: 7. ", m.intentions());
	}
	
	/**
	 * Checks intentions with block.
	 */
	@Test
	public void testIntentionsBlock() {
		CardsUtil.load();
		Monster m = new Monster("test", 20, new Card[] {CardsUtil.get("Monster Block")});
		m.setMove();
		assertEquals("Checking monster inentions with block", "test's intentions: Block. ", m.intentions());
	}
	
	/**
	 * Checks intentions with healing.
	 */
	@Test
	public void testIntentionsHeal() {
		CardsUtil.load();
		Monster m = new Monster("test", 20, new Card[] {new Card(5, 0, 0, 0, false, "healTest")});
		m.setMove();
		assertEquals("Checking monster inentions with healing", "test's intentions: Heal. ", m.intentions());
	}
	
	/**
	 * Checks intentions with a debuff.
	 */
	@Test
	public void testIntentionsDebuff() {
		CardsUtil.load();
		Monster m = new Monster("test", 20, new Card[] {CardsUtil.get("Monster Vulnerable")});
		m.setMove();
		assertEquals("Checking monster inentions with a debuff", "test's intentions: Use a negative effect on you. ", m.intentions());
	}
	
	/**
	 * Checks intentions with a buff.
	 */
	@Test
	public void testIntentionsBuff() {
		CardsUtil.load();
		Monster m = new Monster("test", 20, new Card[] {CardsUtil.get("Monster Strength")});
		m.setMove();
		assertEquals("Checking monster inentions with a buff", "test's intentions: Use a buff. ", m.intentions());
	}
	
	/**
	 * Checks intentions with a buff and attack.
	 */
	@Test
	public void testIntentionsDebuffAndAttack() {
		CardsUtil.load();
		Monster m = new Monster("test", 20, new Card[] {CardsUtil.get("Monster Frail Attack")});
		m.setMove();
		assertEquals("Checking monster inentions with a debuff and attack", "test's intentions: Attack: 7. Use a negative effect on you. ", m.intentions());
	}
	
	/**
	 * Checks intentions with an a buff.
	 */
	@Test
	public void testIntentionsUnknown() {
		CardsUtil.load();
		Monster m = new Monster("test", 20, new Card[] {new Card(0, 0, 0, 0, false, "")});
		m.setMove();
		assertEquals("Checking monster inentions with a debuff and block", "test's intentions: Unknown. ", m.intentions());
	}
	
	/**
	 * Checks intentions with an attack, block, heal, and buff.
	 */
	@Test
	public void testIntentionsAll() {
		CardsUtil.load();
		Monster m = new Monster("test", 20, new Card[] {new Skill(5, 5, 5, "strength", 2, 0, 0, 0, 0, false, "testSkill")});
		m.setMove();
		assertEquals("Checking monster inentions with all effects", "test's intentions: Attack: 5. Block. Heal. Use a buff. ", m.intentions());
	}
	
	/**
	 * Checks action report with an attack.
	 */
	@Test
	public void testActionReportAttack() {
		CardsUtil.load();
		Monster m = new Monster("test", 20, new Card[] {CardsUtil.get("Monster Attack")});
		m.setMove();
		assertEquals("Checking monster action report with attack", "test attacked for 7 damage! ", m.actionReport());
	}
	
	/**
	 * Checks action report with block.
	 */
	@Test
	public void testActionReportBlock() {
		CardsUtil.load();
		Monster m = new Monster("test", 20, new Card[] {CardsUtil.get("Monster Block")});
		m.setMove();
		assertEquals("Checking monster action report with block", "test blocked with 8 armour! ", m.actionReport());
	}
	
	/**
	 * Checks action report with healing.
	 */
	@Test
	public void testActionReportHeal() {
		CardsUtil.load();
		Monster m = new Monster("test", 20, new Card[] {new Card(5, 0, 0, 0, false, "healTest")});
		m.setMove();
		assertEquals("Checking monster action report with healing", "test restored 5 health! ", m.actionReport());
	}
	
	/**
	 * Checks action report with a buff.
	 */
	@Test
	public void testActionReportBuff() {
		CardsUtil.load();
		Monster m = new Monster("test", 20, new Card[] {CardsUtil.get("Monster Strength")});
		m.setMove();
		assertEquals("Checking monster action report with buff", "test gave itself 3 strength! ", m.actionReport());
	}
	
	/**
	 * Checks action report with a start-turn buff.
	 */
	@Test
	public void testActionReportStartTurnBuff() {
		CardsUtil.load();
		Monster m = new Monster("test", 20, new Card[] {CardsUtil.get("Monster Strength Power")});
		m.setMove();
		assertEquals("Checking monster action report with a start turn buff", "test gave itself 2 strength at the start of every turn! ", m.actionReport());
	}
	
	/**
	 * Checks action report with an end-turn debuff.
	 */
	@Test
	public void testActionReportEndTurnBuff() {
		CardsUtil.load();
		Monster m = new Monster("test", 20, new Card[] {new Skill("strength", 0, 0, 2, -1, 0, false, "testSkill")});
		m.setMove();
		assertEquals("Checking monster action report with end turn buff", "test gave itself 2 strength at the end of every turn! ", m.actionReport());
	}
	
	/**
	 * Checks action report with a start-turn debuff.
	 */
	@Test
	public void testActionReportStartTurnDebuff() {
		CardsUtil.load();
		Monster m = new Monster("test", 20, new Card[] {new Skill("weak", 0, 2, 0, -1, 0, false, "testSkill")});
		m.setMove();
		assertEquals("Checking monster action report with start turn debuff", "test gave you 2 weak at the start of every turn! ", m.actionReport());
	}
	
	/**
	 * Checks action report with an end-turn debuff.
	 */
	@Test
	public void testActionReportEndTurnDebuff() {
		CardsUtil.load();
		Monster m = new Monster("test", 20, new Card[] {new Skill("weak", 0, 0, 2, -1, 0, false, "testSkill")});
		m.setMove();
		assertEquals("Checking monster action report with end turn debuff", "test gave you 2 weak at the end of every turn! ", m.actionReport());
	}
	
	/**
	 * Checks intentions with an attack, block, heal, and buff.
	 */
	@Test
	public void testActionReportAll() {
		CardsUtil.load();
		Monster m = new Monster("test", 20, new Card[] {new Skill(5, 5, 5, "strength", 2, 0, 0, 0, 0, false, "testSkill")});
		m.setMove();
		assertEquals("Checking monster action report with all effects", "test attacked for 5 damage! test blocked with 5 armour! test restored 5 health! test gave itself 2 strength! ", m.actionReport());
	}
	
	/**
	 * Tests a strategy meant to make the monster use a specific card first.
	 */
	@Test
	public void testStrategyUseAtStart() {
		CardsUtil.load();
		Monster cultist = new Monster("Cultist", 48, CardsUtil.getMonsterMoveset("Cultist"));
		cultist.setStrategy("1,Monster Strength Power,1");
		cultist.setMove();
		assertEquals("Checking cultist's initial buff", "Cultist's intentions: Use a buff. ", cultist.intentions());
	}
	
	/**
	 * Tests a strategy meant to make the monster use a specific card at or below 30% health.
	 */
	@Test
	public void testStrategyUseAt30Percent() {
		CardsUtil.load();
		Monster cultist = new Monster("Cultist", 48, CardsUtil.getMonsterMoveset("Cultist"));
		cultist.setStrategy("0.3,Monster Strength Power,1");
		cultist.setHealth((int) (cultist.getHealth() * 0.3));
		cultist.setMove();
		assertEquals("Checking cultist's initial buff", "Cultist's intentions: Use a buff. ", cultist.intentions());
	}
	
}
