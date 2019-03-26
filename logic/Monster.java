package logic;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Inherits all properties and methods of the Entity class, with additional methods and member variables to allow the random access of any given Cards.
 */
public class Monster extends Entity {

	private ArrayList<Card> moves = new ArrayList<Card>();
	private Card move;

	private String Strategy;
	private ArrayList<String[]> movesList = new ArrayList<String[]>();

	/**
	 * Creates a Monster with a given name, maximum health, and any number of Cards, which will be randomly accessed as the Monster's moves.
	 */
	public Monster(String name, int maxHealth, Card... moves) {
		super(name, maxHealth, maxHealth, 0, 0, 0);
		fillMoves(new ArrayList<Card> (Arrays.asList(moves)));
	}

	/**
	 * Creates a Monster exactly equal to another given Monster.
	 * @param m the Monster to copy.
	 */
	public Monster(Monster m) {
		super(m);
		fillMoves(m.getMoves());
		if (m.getStrategy()!= null){
		this.setStrategy(m.getStrategy()) ;
		}
	}

	/**
	 * Utility method only used locally, fills the Monster's moves list with an ArrayList of Cards.
	 * @param moves ArrayList of moves(Cards) to give the Monster.
	 */
	private void fillMoves(ArrayList<Card> moves) {
		for (Card c : moves) {
			this.moves.add(CardsUtil.checkTypeAndCopy(c));
		}
	}

	/**
	 * @return a copy of the Monster's move list.
	 */
	public ArrayList<Card> getMoves() {
		ArrayList<Card> newList = new ArrayList<Card>();
		for (Card c: moves)
			newList.add(CardsUtil.checkTypeAndCopy(c));
		return newList;
	}

	/**
	 *Sets the card returned from method checkStrategy().
	 */
	public void setMove() {
		move = checkStrategy();
	}

	/**
	 * @return a vague description of the Monster's next move.
	 */
	public String intentions() {
		String intentions = "";

		if (move.getDamage() > 0)
			intentions += "Attack: " + ((int)((move.getDamage() + getStrength().getCurrentVal()) * (((getWeak().getCurrentVal()) == 0) ? 1 : 0.75))) + ". ";
		if (move.getBlock() > 0)
			intentions += "Block. ";
		if (move.getHeal() > 0)
			intentions += "Heal. ";
		if (move.getClass().getSimpleName().equals("Skill")) {

			Skill skill = (Skill)move;
			if ("weak vulnerable poison frail".contains(skill.getAttribute()))
				intentions += "Use a negative effect on you. ";
			else
				intentions += "Use a buff. ";

		}

		if (intentions.equals(""))
			intentions = "Unknown. ";

		return getName() +  "'s intentions: " + intentions;
	}

	/**
	 * @return the actions following the usage of the Monster's move.
	 */
	public String actionReport() {
		String report = "";
		if (move.getDamage() > 0)
			report += getName() + " attacked for " + ((int)((move.getDamage() + getStrength().getCurrentVal()) * (((getWeak().getCurrentVal()) == 0) ? 1 : 0.75))) + " damage! ";
		if (move.getBlock() > 0)
			report += getName() + " blocked with " + (move.getBlock() + getDexterity().getCurrentVal()) + " armour! ";
		if (move.getHeal() > 0)
			report += getName() + " restored " + move.getHeal() + " health! ";

		if (move.getClass().getSimpleName().equals("Skill")) {
			Skill skill = (Skill)move;
			if (skill.getCurrentModify() != 0)
				report += (getName() + (("weak vulnerable poison frail".contains(skill.getAttribute())) ? " gave you " : " gave itself ") + skill.getCurrentModify() + " " + skill.getAttribute() + "! ");
			if (skill.getStartModify() != 0)
				report += (getName() + (("weak vulnerable poison frail".contains(skill.getAttribute())) ? " gave you " : " gave itself ") + skill.getStartModify() + " " + skill.getAttribute() + " at the start of every turn! ");
			if (skill.getEndModify() != 0)
				report += (getName() + (("weak vulnerable poison frail".contains(skill.getAttribute())) ? " gave you " : " gave itself ") + skill.getEndModify() + " " + skill.getAttribute() + " at the end of every turn! ");
		}
		return report;
	}

	/**
	 * @return the Monster's next move.
	 */
	public Card getMove() {
		return CardsUtil.checkTypeAndCopy(move);
	}


//-----------------------------------------------------------------------------------
/**
* @return aCard to be used on setMove(). Card given is determined through Strategy of each monster. if no Strategy is set
* it will return a random card from monster's moves. if a move in movesList exhausts it's turn while it is still inside the bounds(health range where move works) of a move,
*it will return a random card from monster's moves
*/
	private Card checkStrategy(){
		//CardsUtil.load();
		Card toBeUsed =  moves.get((int) (Math.random() * moves.size()));
		refreshMovesList();
		if(this.movesList!= null && this.movesList.size()>1)
		{
			double highBound = Double.parseDouble(this.movesList.get(0)[0]);
			double lowBound = Double.parseDouble(this.movesList.get(1)[0]);
			if (this.getHealth()<= (this.getMaxHealth()*highBound) && this.getHealth()> (this.getMaxHealth()*lowBound))
			{
				if (Integer.parseInt(this.movesList.get(0)[2])> 0)
				{
					toBeUsed = CardsUtil.get(this.movesList.get(0)[1]);
					//toBeUsed = moves.get(0);
					this.movesList.get(0)[2] = Integer.toString(Integer.parseInt(this.movesList.get(0)[2])-1);
					if(Integer.parseInt(this.movesList.get(0)[2]) == 0){
						this.movesList.remove(0);}
				}
			}


		}else if (this.movesList!= null &&this.movesList.size() == 1 && Integer.parseInt(this.movesList.get(0)[2])!=0 &&this.getHealth()<=(this.getMaxHealth()*Double.parseDouble(this.movesList.get(0)[0]))){
				toBeUsed = CardsUtil.get(this.movesList.get(0)[1]);
				//	toBeUsed = moves.get(0);
				this.movesList.get(0)[2] = Integer.toString(Integer.parseInt(this.movesList.get(0)[2])-1);
				if(Integer.parseInt(this.movesList.get(0)[2]) == 0){
					this.movesList.remove(0);
		}
	}
	return toBeUsed;
}

	/**
	* refreshes movesList by removing moves that are out of the bounds in which the move is set to work. this is a Private Method and is only used internally.
	*/
	private void refreshMovesList(){
		while(movesList.size()>1){
			if (this.getHealth() < this.getMaxHealth()*Double.parseDouble(movesList.get(1)[0]))
			{
				movesList.remove(0);
			}else{break;}
		}
	}


/**
*sets's the Strategy of the monster as well as setting the movesList that the monster is going to use. Should be set every time a game is started for the monster to hava a strategy
* @param aStrategy
*/
	public void setStrategy(String aStrategy){
		this.Strategy = aStrategy;
		if (aStrategy.indexOf("/") != -1){
			String[] separatedStrategy =  aStrategy.split("/");
			for(int i = 0 ;i<separatedStrategy.length; i++){
				movesList.add(separatedStrategy[i].split(","));
			}
		}else{
			movesList.add(aStrategy.split(","));
		}
	}

	/**
	* @return Strategy
	*/
	public String getStrategy()
	{
		return this.Strategy;
	}


}
