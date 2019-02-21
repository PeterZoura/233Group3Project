/**
 * Each Card object possesses the ability to heal, damage and block, and a cost, name, and description.
 */
public class Card {
	
	private int heal;
	private int damage;
	private int block;
	private int cost;
	private String name;
	private String description;

	/**
	 * Creates the Card with the given amount of healing, damage, block and mana cost, as well as a name and description.
	 */
	public Card(int heal, int damage, int block, int cost, String name, String description) {
		this.heal = heal;
		this.damage = damage;
		this.block = block;
		this.name = name;
		this.cost = cost;
		this.description = description;
	}

	/**
	 * Creates the Card with the given amount of healing, damage, block and mana cost, as well as a name. Automatically generates a description based on the given values.
	 */
	public Card(int heal, int damage, int block, int cost, String name) {
		this.heal = heal;
		this.damage = damage;
		this.block = block;
		this.cost = cost;
		this.name = name;
		
		description = String.format("Costs %d mana. ", cost);
		if (heal > 0)
			description += String.format("Heal %d health to yourself. ", this.heal);

		if (damage > 0)
			description += String.format("Deal %d damage to an enemy. ", this.damage);

		if (block > 0)
			description += String.format("Block %d damage for the next turn. ", this.block);
		
	}

	/**
	 * @param aCard clones this Card object.
	 */
	public Card(Card aCard) {
		this(aCard.getHeal(), aCard.getDamage(), aCard.getBlock(), aCard.getCost(), aCard.getName(), aCard.getDescription());
	}
	
	/**
	 * @return the Card's name.
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * @return the Card's description.
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * @return the Card's damage.
	 */
	public int getDamage() {
		return this.damage;
	}

	/**
	 * @return the Card's healing.
	 */
	public int getHeal() {
		return this.heal;
	}

	/**
	 * @return the Card's block.
	 */
	public int getBlock() {
		return this.block;
	}

	/**
	 * @return the Card's mana cost.
	 */
	public int getCost() {
		return this.cost;
	}

	/**
	 * Attempts to use the Card on a given user and target. Returns true if the user has enough mana and the use is successful, otherwise returns false.
	 * @param user the Entity using the card.
	 * @param target the Entity the user is targeting with the card.
	 * @return whether or not the user had enough mana for the Card to be used.
	 */
	public boolean use(Entity user, Entity target) {
		if (user.getEnergy() >= this.cost) {
			if (this.heal > 0) {
				user.setHealth(user.getHealth() + this.heal);
			}
			if (this.damage > 0) {
				target.setHealth(user.getHealth() - this.damage);
			}
			if (this.block > 0) {
				user.setArmour(user.getArmour() + this.block);
			}
			user.setEnergy(user.getEnergy() - this.cost);
			return true;
		} else {
			return false;
		}
	}

// Test Main Function (not to be used during demo 1
	/*
	 * public static void main(String[] Args) { Card nCard = new
	 * Card(1,2,0,"Vampiric Touch"); Card card2 = new Card(nCard);
	 * System.out.println(card2.getName());
	 * System.out.println(card2.getDescription()); }
	 */
}
