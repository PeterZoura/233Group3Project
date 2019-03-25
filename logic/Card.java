package logic;
/**
 * Each Card object possesses the ability to heal, damage and block, and a cost, name, and description.
 */
public class Card {
	
	private int heal;
	private int damage;
	private int block;
	private int cost;
	private boolean zone;
	private String name;
	private String description;

	/**
	 * Creates the Card with the given amount of healing, damage, block and mana cost, as well as a name and description.
	 */
	public Card(int heal, int damage, int block, int cost, boolean zone, String name, String description) {
		this.heal = heal;
		this.damage = damage;
		this.block = block;
		this.cost = cost;
		this.zone = zone;
		this.name = name;
		this.description = description;
	}

	/**
	 * Creates the Card with the given amount of healing, damage, block and mana cost, as well as a name. Automatically generates a description based on the given values.
	 */
	public Card(int heal, int damage, int block, int cost, boolean zone, String name) {
		this.heal = heal;
		this.damage = damage;
		this.block = block;
		this.cost = cost;
		this.zone = zone;
		this.name = name;
		
		description = String.format("Costs %d mana. ", cost);
		if (heal < 0)
			description += "Take " + -1*this.heal + " damage to yourself. ";
		if (heal > 0)
			description += String.format("Heal %d health to yourself. ", this.heal);

		if (damage > 0)
			description += String.format("Deal %d damage to " + (zone ? "all enemies." : "an enemy. "), this.damage);

		if (block > 0)
			description += String.format("Block %d damage for the next turn. ", this.block);	
	}

	/**
	 * @param aCard clones this Card object.
	 */
	public Card(Card aCard) {
		this(aCard.getHeal(), aCard.getDamage(), aCard.getBlock(), aCard.getCost(), aCard.isZone(),  aCard.getName(), aCard.getDescription());
	}
	
	/**
	 * generates stat description of a card without the cost (used for relics) 
	 */
	public String describeStats() {
		String statDescription = "";
		if (heal < 0)
			statDescription += "Take " + -1*this.heal + " damage to yourself. ";
		if (heal > 0)
			statDescription += String.format("Heal %d health to yourself. ", this.heal);

		if (damage > 0)
			statDescription += String.format("Deal %d damage to " + (zone ? "all enemies." : "an enemy. "), this.damage);

		if (block > 0)
			statDescription += String.format("Block %d damage for the next turn. ", this.block);
		return statDescription;
	}
	
	/**
	 * @return if the card is designed to target all enemies at once(false means a specific enemy will be targeted, or it is a self-buff).
	 */
	public boolean isZone() {
		return zone;
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
	* Set a card's description
	* @param description the string that the card's description will be changed to
	*/
	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setCost(int i) {
		this.cost = i;
	}
	/**
	 * @return if the card requires a target to be selected in order to be used.
	 */
	public boolean requiresTarget() {
		return (!zone && damage != 0);
	}
	

	/**
	 * Attempts to use the Card on a given user and target. Returns true if the user has enough mana and the use is successful, otherwise returns false.
	 * Applies additional modifiers on the card's effects based on the user's values for strength, weak, dexterity, and frail.
	 * @param user the Entity using the card.
	 * @param target the Entity the user is targeting with the card.
	 * @return whether or not the user had enough mana for the Card to be used.
	 */
	public boolean use(Entity user, Entity... target) {
		if (user.getEnergy() >= this.cost) {
			if (heal != 0) 
				user.heal(heal);	
			if (block > 0) 
				user.block((int) ((block + user.getDexterity().getCurrentVal()) * (user.getFrail().equals(0) ? 1 : 0.75)));
			
			for (Entity e : target) {
				if (damage > 0)
					e.damage((int) ((damage + user.getStrength().getCurrentVal()) * (user.getWeak().equals(0) ? 1 : 0.75)));	
			}
			
			user.useEnergy(cost);
			return true;
		} else {
			return false;
		}
	}
	
}