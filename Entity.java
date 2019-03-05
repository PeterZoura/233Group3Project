/**
 * Contains basic behavior and traits to be used by both the Player and Monster Classes.
 */
public class Entity {
	
	private String name;
	private int maxHealth;
	private int health;
	private int maxEnergy;
	private int energy;
	
	//The Entity's attributes impact the effectiveness of various actions. See descriptions of each attribute with their getters below.
	private Attribute strength, dexterity, weak, frail, vulnerable, regeneration, poison, constricted, armour;

	/**
	 * Creates an Entity with a given name, maximum health, health, armour, and energy.
	 */
	public Entity(String name, int maxHealth, int health, int armour, int maxEnergy, int energy) {
		strength = new Attribute(-999, 0);
		dexterity = new Attribute(-999, 0);
		weak = new Attribute(0, -1);
		frail = new Attribute(0, -1);
		vulnerable = new Attribute(0, -1);
		regeneration = new Attribute(0, -1);
		poison = new Attribute(0, -1);
		constricted = new Attribute(0, 0);
		this.armour = new Attribute(0);
		
		this.name = name;
		this.maxHealth = maxHealth;
		this.health = health;
		this.maxEnergy = maxEnergy;
		this.energy = energy;
		this.armour.modifyVal(armour);
		
	}

	/**
	 * Clones a given Entity.
	 * @param anEntity The Entity to clone.
	 */
	public Entity(Entity anEntity) {
		this(anEntity.getName(), anEntity.getMaxHealth(), anEntity.getHealth(), anEntity.getArmour().getCurrentVal(), anEntity.getMaxEnergy(), anEntity.getEnergy());
		strength = new Attribute(anEntity.getStrength());
		dexterity = new Attribute(anEntity.getDexterity());
		weak = new Attribute(anEntity.getWeak());
		frail = new Attribute(anEntity.getFrail());
		vulnerable = new Attribute(anEntity.getVulnerable());
		regeneration = new Attribute(anEntity.getRegeneration());
		poison = new Attribute(anEntity.getPoison());
		constricted = new Attribute(anEntity.getConstricted());
		armour = new Attribute(anEntity.getArmour());
	}
	
	/**
	 * Applies the Entity's regeneration and poison. Updates the Entity's attributes.
	 */
	public void startTurn() {
		for (Attribute a : new Attribute[] {strength, dexterity, weak, vulnerable, regeneration, poison, constricted, armour})
			a.startTurn();
		heal(regeneration.getCurrentVal());
		damage(poison.getCurrentVal());
	}
	
	/**
	 * Applies damage from the Entity's constricted attribute. Updates the Entity's attributes.
	 */
	public void endTurn() {
		for (Attribute a : new Attribute[] {strength, dexterity, weak, vulnerable, regeneration, poison, constricted, armour})
			a.endTurn();
		damage(constricted.getCurrentVal());
	}
	
	/**
	 * Clears all modifiers from each attribute.
	 */
	public void startCombat() {
		for (Attribute a : new Attribute[] {strength, dexterity, weak, vulnerable, regeneration, poison, constricted, armour})
			a.startCombat();
	}

	/**
	 * @param amount damage to deal to the entity. Deals 25% more damage if the Entity has any vulnerability. Damages armour first, then health.
	 */
	public void damage(int amount) {
		
		if (!vulnerable.equals(0)) {
			amount *= 1.25;
		}
		
		if (amount > 0) {
			setHealth(health - Math.max(amount - armour.getCurrentVal(), 0));
			armour.modifyVal(-amount);
		}
	}
	
	/**
	 * @param amount heal this amount health (cannot exceed maximum health).
	 */
	public void heal(int amount) {
		setHealth(health + amount);
	}
	
	/**
	 * @param amount lose this amount of energy.
	 */
	public void useEnergy(int amount) {
		if (amount > 0)
			setEnergy(energy - amount);
	}
	
	/**
	 * @param amount gain this amount of energy.
	 */
	public void gainEnergy(int amount) {
		if (amount > 0)
			setEnergy(energy + amount);
	}
	
	/**
	 * @param amount add this amount of armour.
	 */
	public void block(int amount) {
		if (amount > 0)
			armour.modifyVal(amount);
	}

	/**
	 * Sets the Entity's name.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Sets the Entity's health; if the given value is below 0, health is set to 0, and if it is above the Entity's maximum health, it is set to the Entity's maximum health.
	 */
	public void setHealth(int health) {
		if (health >= maxHealth) {
			this.health = maxHealth;
		} else if (health <= 0) {
			this.health = 0;
		} else {
			this.health = health;
		}
	}

	/**
	 * Sets the Entity's maximum health. If the given value is less than 1, defaults to 1.
	 */
	public void setMaxHealth(int maximumHealth) {
		if (maximumHealth >= 1) {
			this.maxHealth = maximumHealth;
		} else {
			this.maxHealth = 1;
		}
	}
	
	/**
	 * Sets the Entity's energy. If the given value is less than 0, defaults to 0.
	 */
	public void setEnergy(int energy) {
		if (energy >= 0) {
			this.energy = energy;
		} else {
			this.energy = 0;
		}
	}
	
	/**
	 * Sets the Entity's maximum energy. If the given value is less than 0, defaults to 0.
	 */
	public void setMaxEnergy(int maxEnergy) {
		if (maxEnergy >= 0) {
			this.maxEnergy = maxEnergy;
		} else {
			this.maxEnergy = 0;
		}
	}

	/**
	 * @return the Entity's name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the Entity's maximum health.
	 */
	public int getMaxHealth() {
		return maxHealth;
	}

	/**
	 * @return the Entity's health.
	 */
	public int getHealth() {
		return health;
	}

	/**
	 * @return the Entity's energy.
	 */
	public int getEnergy() {
		return energy;
	}

	/**
	 * @return the Entity's maximum energy.
	 */
	public int getMaxEnergy() {
		return maxEnergy;
	}
	
	/**
	 * @return each attack deals the Entity's strength in extra damage(can be negative);
	 */
	public Attribute getStrength() {
		return strength;
	}

	/**
	 * @return each block attempt blocks the Entity's dexterity in extra armour(can be negative).
	 */
	public Attribute getDexterity() {
		return dexterity;
	}

	/**
	 * @return Reduces by 1 each turn, with a minimum of 0. An Entity deals 25% less damage if it has 1 or more weak.
	 */
	public Attribute getWeak() {
		return weak;
	}
	
	/**
	 * @return reduces by 1 each turn, with a minimum of 0. An Entity blocks with 25% less armour if it has 1 or more frail.
	 */
	public Attribute getFrail() {
		return frail;
	}

	/**
	 * @return reduces by 1 each turn, with a minimum of 0. An Entity takes 25% more damage from all sources if it has 1 or more vulnerable.
	 */
	public Attribute getVulnerable() {
		return vulnerable;
	}

	/**
	 * @return reduces by 1 each turn after use, with a minimum of 0. Heals the Entity for the value of regeneration before each turn.
	 */
	public Attribute getRegeneration() {
		return regeneration;
	}

	/**
	 * @return reduces by 1 each turn after use, with a minimum of 0. Damages the Entity for the value of poison before each turn.
	 */
	public Attribute getPoison() {
		return poison;
	}

	/**
	 * @return Does not get reduced each turn, damages the Entity for the value of constricted at the end of each turn.
	 */
	public Attribute getConstricted() {
		return constricted;
	}
	
	/**
	 * @return the Entity's armour.
	 */
	public Attribute getArmour() {
		return armour;
	}
		

	/**
	 * @return whether or not the Entity's health is above 0.
	 */
	public boolean alive() {
		if (health > 0) {
			return true;
		} else {
			return false;
		}
	}
}
