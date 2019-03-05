import java.util.ArrayList;

/**
 * Allows easy use and modification of a wide array of attributes in the Entity, Player, and Monster classes. Each Attribute object will maintain
 * its own value through the use of a default value, turn-based modification rate, minimum value, and an option to reset the value to its default
 * each turn. The value of the attribute is stored in 'currentVal.' Each Attribute can also hold any number of modifiers that can take place at
 * the beginning or end of each turn, for any number of turns or for the entire combat. To maintain its own value, each Attribute only needs to
 * be notified of when a turn begins, when a turn ends, and when a new combat encounter begins.
 */
public class Attribute {
	
	boolean turnReset;
	int minimumVal, modifyRate, defaultVal, currentVal;
	private ArrayList<Integer> startModifiers, endModifiers;

	/**
	 * Creates an Attribute with the given turnReset boolean, as well as minimum value, modify rate, and default value. If turnReset is true, the Attribute's
	 * 'currentVal' will be reset to the given default value each turn, otherwise it will have the modify rate added to it each turn.
	 * @param turnReset
	 * @param minimumVal
	 * @param modifyRate
	 * @param defaultVal
	 */
	public Attribute(boolean turnReset, int minimumVal, int modifyRate, int defaultVal) {
		this.turnReset = turnReset;
		this.minimumVal = minimumVal;
		this.modifyRate = modifyRate;
		this.defaultVal = defaultVal;
		currentVal = 0;
		startModifiers = new ArrayList<Integer>();
		endModifiers = new ArrayList<Integer>();
	}
	
	/**
	 * Creates an Attribute with a given minimum value, and modify rate; the Attribute's 'currentVal' will be modified by this amount each turn.
	 * @param minimumVal currentVal cannot go below this value. If currentVal would be set below the minimum, it will instead be set to minimumVal.
	 * @param modifyRate add to currentVal by this amount each turn. ModifyRate will not be allowed to cause currentVal to go below minimumVal.
	 */
	public Attribute(int minimumVal, int modifyRate) {
		this(false, minimumVal, modifyRate, 0);
	}
	
	/**
	 * Creates an Attribute with a given minimum value. currentVal will be set to 0 each turn(use setModifyRate(modifyRate) to change this).
	 * @param minimumVal currentVal cannot go below this value by any means.
	 */
	public Attribute(int minimumVal) {
		this(true, minimumVal, 0, 0);
	}
	
	/**
	 * Clones a given Attribute exactly.
	 * @param a copy this Attribute's values and modifiers.
	 */
	public Attribute(Attribute a) {
		this(a.getTurnReset(), a.getMinimumVal(), a.getModifyRate(), a.getDefaultVal());
		currentVal = a.getCurrentVal();
		startModifiers = a.getStartModifiers();
		endModifiers = a.getEndModifiers();
	}
	/**
	 * @return The smallest possible value of currentVal.
	 */
	public int getMinimumVal() {
		return minimumVal;
	}

	/**
	 * @return the amount added to currentVal each turn(note that if turnReset is true, this value is not used).
	 */
	public int getModifyRate() {
		return modifyRate;
	}

	/**
	 * @return each combat begins with currentVal at this value. If turnReset is true, currentVal is set to this value each turn.
	 */
	public int getDefaultVal() {
		return defaultVal;
	}

	/**
	 * @return the Attribute's current numeric value(currentVal).
	 */
	public int getCurrentVal() {
		return currentVal;
	}
	
	/**
	 * @return true: each turn, currentVal is reset to defaultVal. false: each turn, modifyRate is added to currentVal.
	 */
	public boolean getTurnReset() {
		return turnReset;
	}
	
	/**
	 * @return a copy of the Attribute's list of start turn modifiers.
	 */
	public ArrayList<Integer> getStartModifiers() {
		return new ArrayList<Integer>(startModifiers);
	}

	/**
	 * @return a copy of the Attribute's list of end turn modifiers.
	 */
	public ArrayList<Integer> getEndModifiers() {
		return new ArrayList<Integer>(endModifiers);
	}

	/**
	 * Adds a given int to currentVal. If this would make currentVal be less than minimumVal, sets currentVal to minimumVal.
	 * @param amount adds this amount to currentVal.
	 */
	public void modifyVal(int amount) {
		currentVal = Math.max(minimumVal, currentVal + amount);
	}
	
	/**
	 * @param modifyRate add this amount to currentVal each turn.
	 */
	public void setModifyRate(int modifyRate) {
		turnReset = false;
		this.modifyRate = modifyRate;
	}
	
	/** 
	 * Private utility method, used to add a modifier to either startModifiers or endModifiers.
	 * @param amount how much to modify currentVal by each time a turn begins or ends.
	 * @param duration how many turns this modifier will last; -1 will cause the effect to last the entire combat.
	 * @param modifiers which list to use.
	 */
	private void addModifier(int amount, int duration, ArrayList<Integer> modifiers) {
		modifiers.add(amount);
		modifiers.add(duration);
	}
	
	/**
	 * Adds a modifier with a given amount and duration to the beginning of each turn.
	 * @param amount how much to modify currentVal by each time a turn begins.
	 * @param duration how many turns this modifier will last; -1 will cause the effect to last the entire combat.
	 */
	public void addStartModifier(int amount, int duration) {
		addModifier(amount, duration, startModifiers);
	}
	
	/**
	 * Adds a modifier with a given amount and duration to the end of each turn.
	 * @param amount how much to modify currentVal by each time a turn ends.
	 * @param duration how many turns this modifier will last; -1 will cause the effect to last the entire combat.
	 */
	public void addEndModifier(int amount, int duration) {
		addModifier(amount, duration, endModifiers);
	}
	
	/**
	 * Can be used for convenience(attribute.equals(2) instead of attribute.getCurrentVal() == 2).
	 * @param n the int to compare to currentVal.
	 * @return whether or not currentVal is equal to a given int.
	 */
	public boolean equals(int n) {
		return currentVal == n;
	}
	
	/**
	 * Private utility method; applies the modifiers found in either startModifiers or endModifiers. Each modifier list represents one modifer for every two elements.
	 * eg: modify the attribute by (amount)startModifiers.get(0) for (duration)startModifiers.get(1) turns. The counters are reduced by 1 each time this method is called,
	 * and currentVal is modified by the amount; when the counter reaches 0, both the counter and the amount are removed from the list. If the counter is -1, the modifier's
	 * counter will be ignored and the amount will be applied each time this method is called until the startCombat() method is called.
	 * @param modifiers
	 */
	private void applyModifiers(ArrayList<Integer> modifiers) {
		for (int i = modifiers.size() - 1; i >= 0; i -= 2) {
			
			if (modifiers.get(i) == 0) {
				modifiers.remove(i);
				modifiers.remove(i - 1);
			} else {
				if (modifiers.get(i) != -1)
					modifiers.set(i, modifiers.get(i) - 1);
				modifyVal(modifiers.get(i - 1));
			}
			
		}
	}
	
	/**
	 * If turnReset is true, resets currentVal to defaultVal. Otherwise, adds modifyRate to currentVal. Applies all start turn modifiers.
	 */
	public void startTurn() {
		if (turnReset)
			currentVal = defaultVal;
		else
			modifyVal(modifyRate);
		
		applyModifiers(startModifiers);
	}
	
	/**
	 * Applies all end turn modifiers.
	 */
	public void endTurn() {
		applyModifiers(endModifiers);
	}
	
	/**
	 * Sets currentVal to defaultVal. Clears all modifiers.
	 */
	public void startCombat() {
		currentVal = defaultVal;
		startModifiers.clear();
		endModifiers.clear();
	}
	
	//Feel free to screw around with this to figure out how things work(runs 25 turns with an Attribute and whatever modifiers you want).
	/*public static void main(String[] args) {
		Attribute strength = new Attribute(-999, 0);
		strength.addStartModifier(3, 2);
		//strength.addEndModifier(7, 5);
		strength.modifyVal(2);
		strength.addEndModifier(-2, 1);
		for (int i = 1; i < 25; i ++) {
			System.out.println(i + ": Before start   " + strength.getCurrentVal());
			strength.startTurn();
			System.out.println(i + ": After start    " + strength.getCurrentVal());
			System.out.println(i + ": Before end     " + strength.getCurrentVal());
			strength.endTurn(); 
			System.out.println(i + ": After end      " + strength.getCurrentVal());
			System.out.println();
			
		}
	}*/

}
