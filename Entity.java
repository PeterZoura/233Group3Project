/**
 * Contains basic behavior and traits to be used by both the Player and Monster Classes.
 */
public class Entity {
	
	private String name;
	private int maxHealth;
	private int health;
	private int armour;
	private int maxEnergy;
	private int energy;

	/**
	 * Creates an Entity with a given name, maximum health, health, armour, and energy.
	 */
	public Entity(String name, int maxHealth, int health, int armour, int maxEnergy, int energy) {
		this.name = name;
		this.maxHealth = maxHealth;
		this.health = health;
		this.armour = armour;
		this.maxEnergy = maxEnergy;
		this.energy = energy;
	}

	/**
	 * Clones a given Entity.
	 * @param anEntity The Entity to clone.
	 */
	public Entity(Entity anEntity) {
		this(anEntity.getName(), anEntity.getMaxHealth(), anEntity.getHealth(), anEntity.getArmour(), anEntity.getMaxEnergy(), anEntity.getEnergy());
	}
	
	/**
	 * @param amount damage to deal to the entity. Damages armour first, then health.
	 */
	public void damage(int amount) {
		if (amount > 0) {
			setHealth(health - Math.max(amount - armour, 0));
			setArmour(armour - amount);
		}
	}
	
	/**
	 * @param amount heal this amount health (cannot exceed maximum health).
	 */
	public void heal(int amount) {
		if (amount > 0)
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
			setArmour(armour + amount);
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
	 * Sets the Entity's armour. If the given value is less than 0, defaults to 0.
	 */
	public void setArmour(int armour) {
		if (armour >= 0) {
			this.armour = armour;
		} else {
			this.armour = 0;
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
	 * @return the Entity's armour.
	 */
	public int getArmour() {
		return armour;
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
