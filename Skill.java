/**
 * Each Skill object is a also a card object that posseses the ability to alter attribute objects of the user or the target.
 * Can also accomplish basic card functions (damage, block,heal)
 */
public class Skill extends Card {
	String whichAttribute;
	int currentTurnModify;
	int startTurnModify;
	int endTurnModify;
	int duration;
	
	/**
	* Private utility method that alters the start and end turn modification amounts of a given attribute until the end of combat
	* @param startTurnModify
	* @param endTurnModify
	* @param whichAttribute
	*/
	private void setAttributes(int currentTurnModify, int startTurnModify, int endTurnModify, int duration, Attribute whichAttribute) {
		whichAttribute.addStartModifier(startTurnModify, duration);
		whichAttribute.addEndModifier(endTurnModify, duration);
		whichAttribute.modifyVal(currentTurnModify);
	}
	
	/**
	* Constructor method that creates a power card that ONLY modifies a given attributes start turn modifications and end turn modifications.
	* Also specifies name and description
	*/
	public Skill(String whichAttribute, int currentTurnModify, int startTurnModify, int endTurnModify, int duration, int cost, String name, String description) {
		super(0,0,0,cost,name,description);
		this.whichAttribute = whichAttribute;
		this.currentTurnModify = currentTurnModify;
		this.startTurnModify = startTurnModify;
		this.endTurnModify = endTurnModify;
		this.duration = duration;
	}
	
	/**
	* Constructor method that creates a power card that ONLY modifies a given attributes start turn modifications and end turn modifications.
	* Also specifies name, but auto-generates the description
	*/
	public Skill(String whichAttribute, int currentTurnModify, int startTurnModify, int endTurnModify, int duration, int cost, String name) {
		super(0,0,0,cost,name);
		this.whichAttribute = whichAttribute;
		this.currentTurnModify = currentTurnModify;
		this.startTurnModify = startTurnModify;
		this.endTurnModify = endTurnModify;
		this.duration = duration;
		String skillDescription = String.format("Costs %d mana. ", cost);
		if (currentTurnModify!=0) {
			skillDescription += String.format("Get/Afflict %d %s this turn. ", currentTurnModify, whichAttribute);
		}
		if (startTurnModify!=0) {
			skillDescription += String.format("Get/Afflict %d %s at the beginning of your turn. ",startTurnModify,whichAttribute);
		}
		if (endTurnModify!=0) {
			skillDescription += String.format("Get/Afflict %d %s at the end of your turn. ", endTurnModify,whichAttribute);
		}
		if (duration == -1){
			skillDescription += "Effect lasts until the end of combat.";
		}
		else {
			skillDescription += String.format("Effect lasts for %d turn(s).", duration);
		}
		super.setDescription(skillDescription);
	}
	
	/**
	* Constructor method that creates a skill card that has similiar functions to a normal card (heals, damages and blocks, but ALSO
	* modifies a given attributes start turn modifications and end turn modifications.
	* Also specifies name, but auto-generates the description.
	*/	
	public Skill(int heal, int damage, int block, String whichAttribute, int currentTurnModify, int startTurnModify, int endTurnModify, int duration, int cost, String name) {
		super(heal,damage,block,cost,name);
		this.whichAttribute = whichAttribute;
		this.currentTurnModify = currentTurnModify;
		this.startTurnModify = startTurnModify;
		this.endTurnModify = endTurnModify;
		this.duration = duration;
		
		String skillDescription = String.format("Costs %d mana. ", cost);
		if (heal < 0)
			skillDescription += "Take " + -1*this.getHeal() + " damage to yourself once. ";
		if (heal > 0)
			skillDescription += String.format("Heal %d health to yourself once. ", this.getHeal());
		if (damage > 0)
			skillDescription += String.format("Deal %d damage to an enemy once. ", this.getDamage());
		if (block > 0)
			skillDescription += String.format("Block %d damage for the next turn once. ", this.getBlock());
		if (currentTurnModify!=0) {
			skillDescription += String.format("Get/Afflict %d %s this turn. ", currentTurnModify, whichAttribute); 
		}
		if (startTurnModify!=0) {
			skillDescription += String.format("Get/Afflict %d %s at the beginning of your turn. ",startTurnModify,whichAttribute);
		}
		if (endTurnModify!=0) {
			skillDescription += String.format("Get/Afflict %d %s at the end of your turn. ", endTurnModify,whichAttribute);
		}
		if (duration == -1){
			skillDescription += "Effect lasts until the end of combat.";
		}
		else {
			skillDescription += String.format("Effect lasts for %d turn(s).", duration);
		}
		super.setDescription(skillDescription);
	}
	/**
	* Constructor method that creates a skill card that has similiar functions to a normal card (heals, damages and blocks, but ALSO
	* modifies a given attributes start turn modifications and end turn modifications.
	* Also specifies name and description of the skill card.
	*/	
	public Skill(int heal, int damage, int block, String whichAttribute, int currentTurnModify, int startTurnModify, int endTurnModify, int duration, int cost, String name, String description) {
		super(heal,damage,block,cost,name,description);
		this.whichAttribute = whichAttribute;
		this.currentTurnModify = currentTurnModify;
		this.startTurnModify = startTurnModify;
		this.endTurnModify = endTurnModify;
		this.duration = duration;
	}
	/**
	* Constructor copy method that copies a Skill card.
	*/
	public Skill(Skill aSkill) {
		this(aSkill.getHeal(), aSkill.getDamage(), aSkill.getBlock(), aSkill.getAttribute(), aSkill.getCurrentModify(),
			aSkill.getStartModify(), aSkill.getEndModify(), aSkill.getDuration(), aSkill.getCost(), aSkill.getName(), aSkill.getDescription());
	}
	
	/**
	* @return String of the attribute being targeted
	*/
	public String getAttribute() {
		return this.whichAttribute;
	}
	/**
	* @return the amount the card modifies the attribute by on the current turn
	*/
	public int getCurrentModify() {
		return this.currentTurnModify;
	}
	/**
	* @return the amount the card modifies the attribute by at the beginning of the turn
	*/
	public int getStartModify() {
		return this.startTurnModify;
	}
	/**
	* @return the amount the card modifies the attribute by at the end of the turn
	*/
	public int getEndModify() {
		return this.endTurnModify;
	}
	/**
	* @return the number of turns the card modifies the targeted attribute for
	*/	
	public int getDuration() {
		return this.duration;
	}
	/**
	 * Attempts to use the Card on a given user and target. Returns true if the user has enough mana and the use is successful, otherwise returns false.
	 * If the power card has standard card abilities (heal, damage, block), use those first, then apply changes to the specified attribute.
	 * @param user the Entity using the card.
	 * @param target the Entity the user is targeting with the card.
	 * @return whether or not the user had enough mana for the Card to be used.
	*/
	@Override
	public boolean use(Entity user,Entity target) {
		boolean playable = super.use(user,target);
		switch(whichAttribute) {
			case "strength":
				setAttributes(currentTurnModify,startTurnModify,endTurnModify,duration,user.getStrength());
				break;
			case "dexterity":
				setAttributes(currentTurnModify,startTurnModify,endTurnModify,duration,user.getDexterity());
				break;
			case "weak":
				setAttributes(currentTurnModify,startTurnModify,endTurnModify,duration,target.getWeak());
				break;
			case "frail":
				setAttributes(currentTurnModify,startTurnModify,endTurnModify,duration,target.getFrail());
				break;
			case "vulnerable":
				setAttributes(currentTurnModify,startTurnModify,endTurnModify,duration,target.getVulnerable());
				break;
			case "regeneration":
				setAttributes(currentTurnModify,startTurnModify,endTurnModify,duration,user.getRegeneration());
				break;
			case "poison":
				setAttributes(currentTurnModify,startTurnModify,endTurnModify,duration,target.getPoison());
				break;
			case "constricited":
				setAttributes(currentTurnModify,startTurnModify,endTurnModify,duration,target.getConstricted());
				break;
			case "armour":
				setAttributes(currentTurnModify,startTurnModify,endTurnModify,duration,user.getArmour());
				break;
		}
		return playable;
	}	
}