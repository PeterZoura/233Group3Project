package buttonHandlers;
import game.GameGUI;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import logic.Card;
import logic.Skill;
import logic.CardsUtil;

/**
*This class handles a when the player clicks on a monster button
*/
public class MonsterTargetClick extends GameGUI implements EventHandler<ActionEvent>
{
	int monsterIndex;
	/**
	*MonsterTargetClick constructor used to set eventhandler for the monster buttons
	*@param mIndex, the index of the monster in combatMonsters to set this eventhandler to
	*/
	public MonsterTargetClick(int mIndex)
	{
		this.monsterIndex = mIndex;
	}
	@Override
	public void handle(ActionEvent event){
	
		if (getCardToUse() >= 0){//If the player has selected a card
			Card card = getPlayer().getDeck().getHand().get(getCardToUse());//set that card to a variable using the proper getter
			if ((card.getDamage()>0)){//if the card is a damage dealing card
				if (!card.requiresTarget()){//and the card hits all enemies
						card.use(getPlayer(),getCombatMonsters());//use the card on all enemies
					}
					else{//otherwise the card hits one enemy
						card.use(getPlayer(),getCombatMonsters()[monsterIndex]);//use the card on that enemy
					}
					getCardButtons().get(getCardToUse()).setText("");
					getCardButtons().get(getCardToUse()).setVisible(false);//Hides the card after use
					setCardToUse(-1);//sets cardToUse to -1 so that the game knows that the player hasn't selected another card yet
			}
			else if(card instanceof Skill){//if the card is not a damage dealing card
				Skill skillCard = (Skill) card;//set the card to variable
				if("weak vulnerable poison frail".contains(skillCard.getAttribute())){//if the card has one of these attributes
					if (!skillCard.requiresTarget()){//if the card hits all enemies
						skillCard.use(getPlayer(),getCombatMonsters());//use the card on all enemies
					}
					else{//other wise use the card on the target
						skillCard.use(getPlayer(),getCombatMonsters()[monsterIndex]);
					}
					getCardButtons().get(getCardToUse()).setText("");
					getCardButtons().get(getCardToUse()).setVisible(false);//hides the card
					setCardToUse(-1);					
				}
				else{//if the card does not have one of those attributes
					getDescriptions().setText("Invalid target");//You can't use the card on a monster
					setCardToUse(-1);				
				}
			}
			else {//if the card is not a skillcard and it doesn't deal damage then it is a healing card
				getDescriptions().setText("Invalid target");//And you can't use it on a monster
				setCardToUse(-1);
			}
		}
		
		if (getPotionToUse() >= 0){//if the player selected a potion to use
			Card potion = getPlayer().getPotions().get(getPotionToUse());//set potion to variable
			if (potion.getDamage()>0){//if the potion deals damage
				if (!potion.requiresTarget()){//and the potion hits all enemies
					potion.use(getPlayer(),getCombatMonsters());//use the potion on all enemies
				}
				else{//otherwise use the potion on the target enemy
					potion.use(getPlayer(),getCombatMonsters()[monsterIndex]);
				}
				getPlayer().removePotion(getPotionToUse());//remove the potion from inventory because it has been used
				setPotionToUse(-1);
			}
			else if(potion instanceof Skill){//if it is a skill potion
				Skill skillPotion = (Skill) potion;//set to variable
				if("weak vulnerable poison frail".contains(skillPotion.getAttribute())){//if the potion has an offensive attribute
					if (!skillPotion.requiresTarget()){//and hits all enemies
						skillPotion.use(getPlayer(),getCombatMonsters());//then hit all enemies
					}
					else{//or it hits one enemy
						skillPotion.use(getPlayer(),getCombatMonsters()[monsterIndex]);//hit the enemy with the potion
					}
					getPlayer().removePotion(getPotionToUse());//remove the potion from inventory
					setPotionToUse(-1);				
				}
				else{
					getDescriptions().setText("Invalid target");//If the potion isn't offensive then it can not be used on a monster
					setPotionToUse(-1);				
				}
			}
			else {//if the potion isn't offensive it can't be used on a monster
				getDescriptions().setText("Invalid target");
				setPotionToUse(-1);
			}
		}
		GameGUI.refreshVisuals();
	}
}
