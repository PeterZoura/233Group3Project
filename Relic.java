
/**
 * Relics can be used by the player for a variety of buffs that can be permanent or occur based on amount of turns, or at the start/end of every combat.
 */
public class Relic {
	private String type; // can be "s"-start combat,"e"-end combat,"p"-permanent,"iS"-iterative start
							// turn,"iE"-iterative End turn
	private String name;
	private String description;
	private Card cardEffect;
	private int energyEffect;
	private int healthEffect;
	private int iterations;

	/**
	 * a constructor that accepts description
	 * 
	 * @param type
	 * @param name
	 * @param description
	 * @param cardEffect
	 * @param energyEffect
	 * @param healthEffect
	 * @param iterations
	 */
	public Relic(String type, String name, String description, Card cardEffect, int energyEffect, int healthEffect,
			int iterations) {
		this.type = type;
		this.name = name;
		this.description = description;
		this.cardEffect = cardEffect;
		if (cardEffect != null) {
			this.cardEffect.setCost(0);
		}
		this.energyEffect = energyEffect;
		this.healthEffect = healthEffect;
		this.iterations = iterations;
	}

	/**
	 * a constructor that does not accept description
	 * 
	 * @param type
	 * @param name
	 * @param cardEffect
	 * @param energyEffect
	 * @param healthEffect
	 * @param iterations
	 */
	public Relic(String type, String name, Card cardEffect, int energyEffect, int healthEffect, int iterations) {
		this.type = type;
		this.name = name;
		this.cardEffect = cardEffect;
		if (cardEffect != null) {
			this.cardEffect.setCost(0);
		}
		this.energyEffect = energyEffect;
		this.healthEffect = healthEffect;
		this.iterations = iterations;

	}

	/**
	 * copy constructor
	 * 
	 * @param aRelic
	 */
	public Relic(Relic aRelic) {
		this.type = aRelic.getType();
		this.name = aRelic.getName();
		this.description = aRelic.getDescription();
		this.cardEffect = aRelic.getCardEffect();
		if (cardEffect != null) {
			this.cardEffect.setCost(0);
		}
		this.energyEffect = aRelic.getEnergyEffect();
		this.healthEffect = aRelic.getHealthEffect();
		this.iterations = aRelic.getIterations();

	}

	/**
	 * 
	 * @return String type of relic
	 */
	public String getType() {
		return this.type;
	}

	/**
	 * 
	 * @return String name of relic
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * 
	 * @return String description
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * 
	 * @return the card that a relic uses
	 */
	public Card getCardEffect() {
		Card toBeReturned = new Card(this.cardEffect);
		return toBeReturned;
	}

	/**
	 * 
	 * @return the amount of energy it gives the player
	 */
	public int getEnergyEffect() {
		return this.energyEffect;
	}

	/**
	 * 
	 * @return an Int of how many counts before the relic uses it's effects during a
	 *         combat
	 */
	public int getIterations() {
		return this.iterations;
	}

	/**
	 * 
	 * @return an Int of how much it affects player's health or max health
	 */
	public int getHealthEffect() {
		return this.healthEffect;
	}

	/**
	 * 
	 * @return String description of the efects of the relics
	 */
	public String getStatDescription() {
		String statDescription = "";
		if (this.cardEffect != null) {
			statDescription = this.cardEffect.describeStats();
		}
		if (this.type.equals("p")) {
			statDescription += (healthEffect > 0) ? " Increases player's max health by: " + this.healthEffect : "";
			statDescription += (energyEffect > 0) ? " Increases player's max Energy by: " + energyEffect : "";
		}
		if (!this.type.equals("p")) {
			statDescription += (healthEffect > 0) ? " Increases player's  health by: " + this.healthEffect : "";
			statDescription += (energyEffect > 0) ? " Increases player's  Energy by: " + energyEffect : "";
		}
		if (this.type.equals("e"))
			statDescription += " at the end of combat.";
		if (this.type.equals("s"))
			statDescription += " at the start of combat.";
		if (this.type.equals("p"))
			statDescription += " upon pick up.";
		if (this.type.equals("iS")) {
			if (iterations == 1)
				statDescription += " at the start of your turn.";
			else
				statDescription += " at the start of your turn every " + iterations + " turns.";
		}
		return statDescription;

	}

	/**
	 * uses the relic of types"s"- start combat "iS"- iterative Start turn
	 * 
	 * @param iteration
	 * @param user
	 * @param target
	 */
	public void use(int iteration, Entity user, Entity... target) {
		switch (this.type) {
		case "s":// uses the relic at the start of the combat
			useRelic(user, target);
			break;
		case "iS":// uses the relic in iterations during a battle in iteration at the start turn
			if (this.iterations != 0 && (iteration % this.iterations) == 0) {
				useRelic(user, target);
			}
			break;
		case "iE":// uses the relic in iterations during a battle in iteration at the end of the
					// turn( not used)
			if (this.iterations != 0 && (iteration % this.iterations) == 0) {
				useRelic(user, target);
			}
			break;
		}
	}

	/**
	 * uses relics of type"p"-permanent and "e"- end combat
	 * 
	 * @param user
	 */
	public void use(Entity user) {
		switch (this.type) {
		case "e":// uses the relic at the end of combat
			useRelic(user);
			break;
		case "p":// uses the relic immediately after getting added to player's relics
			if (this.iterations == 1) {
				useRelic(user);
				this.iterations = 0;
			}
			break;
		}
	}

	/**
	 * private method used by use(user, iterations, monsters...) in relic
	 * 
	 * @param user
	 * @param target
	 */
	private void useRelic(Entity user, Entity... target) {
		if (this.cardEffect != null) {
			// use it
			CardsUtil.get(cardEffect.getName()).use(user, target);
		}
		if (this.energyEffect != 0) {
			// use energy
			user.gainEnergy(this.energyEffect);
		}
		if (this.healthEffect != 0) {
			user.heal(this.healthEffect);
		}
	}

	/**
	 * rivate method used by use(user) in relic
	 * 
	 * @param user
	 */
	private void useRelic(Entity user) {

		if (this.energyEffect != 0 && this.type != "p") {
			// use energy
			user.gainEnergy(this.energyEffect);
		}
		if (this.energyEffect != 0 && this.type == "p") {
			user.gainMaxEnergy(this.energyEffect);
		}
		if (this.healthEffect != 0 && this.type != "p") {
			user.heal(this.healthEffect);
		}
		if (this.healthEffect != 0 && this.type == "p") {
			int newMaxHealth = user.getMaxHealth() + this.healthEffect;
			user.setMaxHealth(newMaxHealth);
		}

	}
}
