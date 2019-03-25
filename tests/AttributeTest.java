package tests;


import static org.junit.Assert.*;
import org.junit.Test;

import logic.Attribute;
import logic.CardsUtil;
import logic.Monster;
import logic.Player;

/**
 * Uses JUnit to test the behavior of Attribute.
 */
public class AttributeTest {
	
	/**
	 * Checks that the cloning constructor for Attribute copies values correctly.
	 */
	@Test
	public void testAttributeCloneAccuracy() {
		Attribute a = new Attribute(0, 2);
		a.addStartModifier(2, -1);
		a.addEndModifier(-1, 3);
		Attribute b = new Attribute(a);
		
		assertEquals("Testing if cloned Attribute is equal to the original", true, 
				   b.getStartModifiers().size() == a.getStartModifiers().size() 
				&& b.getEndModifiers().size() == a.getEndModifiers().size() 
				&& b.getTurnReset() == a.getTurnReset() 
				&& b.getModifyRate() == a.getModifyRate() 
				&& b.equals(a.getCurrentVal()));
	}
	
	/**
	 * Checks that the cloning constructor for Attribute does not create a privacy leak.
	 */
	@Test
	public void testAttributeCloneEncapsulation() {
		Attribute a = new Attribute(0, 2);
		Attribute b = new Attribute(a);
		b.modifyVal(5);
		assertEquals("Checking Attribute cloning encapsulation", false, b.equals(a));
	}
	
	/**
	 * Tests that start modifiers for Attributes modify the value by the correct amount.
	 */
	@Test
	public void testAttributeStartModifier() {
		Attribute a = new Attribute(0, 0);
		a.addStartModifier(2, 1);
		a.startTurn();
		a.endTurn();
		assertEquals("Checking Attribute start turn modifier effect", a.getCurrentVal(), 2);
	}
	
	/**
	 * Tests that end modifiers for Attributes modify the value by the correct amount.
	 */
	@Test
	public void testAttributeEndModifier() {
		Attribute a = new Attribute(0, 0);
		a.addEndModifier(2, 1);
		a.startTurn();
		a.endTurn();
		assertEquals("Checking Attribute end turn modifier effect", a.getCurrentVal(), 2);
	}

	/**
	 * Tests that multiple modifiers for Attributes modify the value by the correct amount.
	 */
	@Test
	public void testAttributeMultipleModifiers() {
		Attribute a = new Attribute(0, 0);
		a.addStartModifier(-1, 1);
		a.addStartModifier(2, -1);
		a.addEndModifier(-1,  1);
		a.addEndModifier(2, 1);
		a.startTurn();
		a.endTurn();
		assertEquals("Checking Attribute with multiple turn modifier effects", a.getCurrentVal(), 2);
	}
	
	/**
	 * Tests that start modifiers for Attributes last the correct number of turns.
	 */
	@Test
	public void testAttributeStartModifierDuration2() {
		Attribute a = new Attribute(0, 0);
		a.addStartModifier(2, 2);
		for (int i = 0; i < 3; i ++) {
			a.startTurn();
			a.endTurn();
		}
		assertEquals("Checking Attribute start turn modifier with duration of 2", a.getCurrentVal(), 4);
	}
	
	/**
	 * Tests that start modifiers for Attributes last the entire combat with a duration of -1.
	 */
	@Test
	public void testAttributeStartModifierFullCombat() {
		Attribute a = new Attribute(0, 0);
		a.addStartModifier(2, -1);
		for (int i = 0; i < 3; i ++) {
			a.startTurn();
			a.endTurn();
		}
		assertEquals("Checking Attribute start turn modifier with duration of -1", a.getCurrentVal(), 6);
	}
	
	/**
	 * Tests that end modifiers for Attributes last the correct number of turns.
	 */
	@Test
	public void testAttributeEndModifierDuration2() {
		Attribute a = new Attribute(0, 0);
		a.addEndModifier(2, 2);
		for (int i = 0; i < 3; i ++) {
			a.startTurn();
			a.endTurn();
		}
		assertEquals("Checking Attribute end turn modifier with duration of 2", a.getCurrentVal(), 4);
	}
	
	/**
	 * Tests that end modifiers for Attributes last the entire combat with a duration of -1.
	 */
	@Test
	public void testAttributeEndModifierFullCombat() {
		Attribute a = new Attribute(0, 0);
		a.addEndModifier(2, -1);
		for (int i = 0; i < 3; i ++) {
			a.startTurn();
			a.endTurn();
		}
		assertEquals("Checking Attribute end turn modifier with duration of -1", a.getCurrentVal(), 6);
	}
	
	/**
	 * Tests if Attribute.modifyVal() works correctly.
	 */
	@Test
	public void testAttributeModifyVal() {
		Attribute a = new Attribute(0, 0);
		a.modifyVal(6);
		assertEquals("Testing Attribute modifyVal", 6, a.getCurrentVal());
	}
	
	/**
	 * Tests that an Attribute's minimum value is effective.
	 */
	@Test
	public void testAttributeMinimumVal() {
		Attribute a = new Attribute(0, 0);
		a.modifyVal(4);
		a.modifyVal(-6);
		assertEquals("Checking Attribute minimum of 0", 0, a.getCurrentVal());
	}
	
	/**
	 * Tests an Attribute with reset set to true, with a default value of 0.
	 */
	@Test
	public void testAttributeReset() {
		Attribute a = new Attribute(0);
		a.modifyVal(6);
		a.startTurn();
		a.endTurn();
		assertEquals("Checking Attribute with reset set to true with default of 0", 0, a.getCurrentVal());
	}
	
	/**
	 * Tests an Attribute with reset set to true, with a default value of 6.
	 */
	@Test
	public void testAttributeResetWithCustomDefault() {
		Attribute a = new Attribute(true, 0, 0, 6);
		a.startTurn();
		a.endTurn();
		assertEquals("Checking Attribute with reset set to true with default of 6", 6, a.getCurrentVal());
	}
	
	/**
	 * Tests that an Attribute's reset property is automatically disabled if a modifyRate is set.
	 */
	@Test
	public void TestAttributeResetAutoDisable() {
		Attribute a = new Attribute(0);
		a.setModifyRate(2);
		assertEquals("Checking that an Attribute's reset property is automatically disabled if a modifyRate is set", false, a.getTurnReset());
	}
	
	/**
	 * tests that Attribute's startCombat method resets all values correctly.
	 */
	@Test
	public void testAttributeStartCombat() {
		Attribute a = new Attribute(true, 0, 0, 6);
		a.addStartModifier(2, -1);
		a.addEndModifier(3, -1);
		a.startTurn();
		a.endTurn();
		a.startCombat();
		assertEquals("Checking that Attribute's startCombat method resets all values correctly", true, 
				   a.getStartModifiers().size() == 0 
				&& a.getEndModifiers().size() == 0 
				&& a.getCurrentVal() == 6);
	}
	
	/**
	 * Tests poison.
	 */
	@Test
	public void testPoison() {
		CardsUtil.load();
		Player p = new Player("Player", 80, CardsUtil.get("Strike"), CardsUtil.get("Strike"), CardsUtil.get("Strike"), CardsUtil.get("Defend"),CardsUtil.randomP(), CardsUtil.randomP(), CardsUtil.randomP());
		p.startCombat();
		p.getPoison().modifyVal(3);
		for (int i = 0; i < 4; i ++) {
			p.startTurn();
			p.endTurn();
		}
		assertEquals("Testing poison", true, p.getHealth() == 74 && p.getPoison().equals(0));
	}
	
	/**
	 * Tests constricted.
	 */
	@Test
	public void testConstricted() {
		CardsUtil.load();
		Player p = new Player("Player", 80, CardsUtil.get("Strike"), CardsUtil.get("Strike"), CardsUtil.get("Strike"), CardsUtil.get("Defend"),CardsUtil.randomP(), CardsUtil.randomP(), CardsUtil.randomP());
		p.startCombat();
		p.getConstricted().modifyVal(3);
		for (int i = 0; i < 3; i ++) {
			p.startTurn();
			p.endTurn();
		}
		assertEquals("Testing constricted", true, p.getHealth() == 71 && p.getConstricted().equals(3));
	}
	
	/**
	 * Tests weak.
	 */
	@Test
	public void testWeak() {
		CardsUtil.load();
		Player p = new Player("Player", 80, CardsUtil.get("Strike"), CardsUtil.get("Strike"), CardsUtil.get("Strike"), CardsUtil.get("Defend"),CardsUtil.randomP(), CardsUtil.randomP(), CardsUtil.randomP());
		p.startCombat();
		p.getWeak().modifyVal(3);
		for (int i = 0; i < 2; i ++) {
			p.startTurn();
			p.endTurn();
		}
		
		Monster m = new Monster("Monster", 5, CardsUtil.getMonsterMoveset("Louse"));
		CardsUtil.get("Strike").use(p, m);
		
		assertEquals("Testing weak", true, p.getWeak().equals(1) && m.alive());
	}
	
	/**
	 * Tests strength.
	 */
	@Test
	public void testStrength() {
		CardsUtil.load();
		Player p = new Player("Player", 80, CardsUtil.get("Strike"), CardsUtil.get("Strike"), CardsUtil.get("Strike"), CardsUtil.get("Defend"),CardsUtil.randomP(), CardsUtil.randomP(), CardsUtil.randomP());
		p.startCombat();
		p.getStrength().modifyVal(3);
		for (int i = 0; i < 2; i ++) {
			p.startTurn();
			p.endTurn();
		}
		
		Monster m = new Monster("Monster", 8, CardsUtil.getMonsterMoveset("Louse"));
		CardsUtil.get("Strike").use(p, m);
		
		assertEquals("Testing strength", true, p.getStrength().equals(3) && !m.alive());
	}
	
	/**
	 * Tests frail.
	 */
	@Test
	public void testFrail() {
		CardsUtil.load();
		Player p = new Player("Player", 80, CardsUtil.get("Strike"), CardsUtil.get("Strike"), CardsUtil.get("Strike"), CardsUtil.get("Defend"),CardsUtil.randomP(), CardsUtil.randomP(), CardsUtil.randomP());
		p.startCombat();
		p.getFrail().modifyVal(3);
		for (int i = 0; i < 2; i ++) {
			p.startTurn();
			p.endTurn();
		}
		
		CardsUtil.get("Defend").use(p, p);
		
		assertEquals("Testing frail", true, p.getFrail().equals(1) && p.getArmour().getCurrentVal() < 5);
	}
	
	/**
	 * Tests dexterity.
	 */
	@Test
	public void testDexterity() {
		CardsUtil.load();
		Player p = new Player("Player", 80, CardsUtil.get("Strike"), CardsUtil.get("Strike"), CardsUtil.get("Strike"), CardsUtil.get("Defend"),CardsUtil.randomP(), CardsUtil.randomP(), CardsUtil.randomP());
		p.startCombat();
		p.getDexterity().modifyVal(3);
		for (int i = 0; i < 2; i ++) {
			p.startTurn();
			p.endTurn();
		}
		
		CardsUtil.get("Defend").use(p, p);
		
		assertEquals("Testing frail", true, p.getDexterity().equals(3) && p.getArmour().equals(8));
	}
	
	/**
	 *  Tests that armour resets to 0 each turn.
	 */
	@Test
	public void testArmourReset() {
		CardsUtil.load();
		Player p = new Player("Player", 80, CardsUtil.get("Strike"), CardsUtil.get("Strike"), CardsUtil.get("Strike"), CardsUtil.get("Defend"),CardsUtil.randomP(), CardsUtil.randomP(), CardsUtil.randomP());
		p.startCombat();
		p.block(5);
		p.startTurn();
		p.endTurn();
		assertEquals("Testing armour with turn start/end", 0, p.getArmour().getCurrentVal());
	}
	
	/**
	 *  Tests armour with more damage than armour.
	 */
	@Test
	public void testArmourDamage() {
		Player p = new Player("Player", 80, CardsUtil.get("Strike"), CardsUtil.get("Strike"), CardsUtil.get("Strike"), CardsUtil.get("Defend"),CardsUtil.randomP(), CardsUtil.randomP(), CardsUtil.randomP());
		p.block(5);
		p.damage(6);
		assertEquals("Testing armour with more damage than armour", true, p.getArmour().equals(0) && p.getHealth() == 79);
	}
	
	/**
	 *  Tests armour with more armour than damage.
	 */
	@Test
	public void testArmourBlockedDamage() {
		Player p = new Player("Player", 80, CardsUtil.get("Strike"), CardsUtil.get("Strike"), CardsUtil.get("Strike"), CardsUtil.get("Defend"),CardsUtil.randomP(), CardsUtil.randomP(), CardsUtil.randomP());
		p.block(5);
		p.damage(3);
		assertEquals("Testing armour with damage fully blocked", true, p.getArmour().equals(2) && p.getHealth() == 80);
	}
	
	/**
	 *  Tests vulnerable.
	 */
	@Test
	public void testVulnerable() {
		CardsUtil.load();
		Player p = new Player("Player", 80, CardsUtil.get("Strike"), CardsUtil.get("Strike"), CardsUtil.get("Strike"), CardsUtil.get("Defend"),CardsUtil.randomP(), CardsUtil.randomP(), CardsUtil.randomP());
		p.startCombat();
		p.getVulnerable().modifyVal(3);
		for (int i = 0; i < 2; i ++) {
			p.startTurn();
			p.endTurn();
		}
		
		p.damage(5);
		assertEquals("Testing vulnerable", true, p.getVulnerable().equals(1) && p.getHealth() < 75);
	}
	
	/**
	 * Tests regeneration.
	 */
	@Test
	public void testRegeneration() {
		CardsUtil.load();
		Player p = new Player("Player", 80, CardsUtil.get("Strike"), CardsUtil.get("Strike"), CardsUtil.get("Strike"), CardsUtil.get("Defend"),CardsUtil.randomP(), CardsUtil.randomP(), CardsUtil.randomP());
		p.startCombat();
		p.damage(20);
		p.getRegeneration().modifyVal(3);
		for (int i = 0; i < 4; i ++) {
			p.startTurn();
			p.endTurn();
		}
		assertEquals("Testing Regeneration", true, p.getHealth() == 66 && p.getRegeneration().equals(0));
	}

}
