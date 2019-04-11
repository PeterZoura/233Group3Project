package buttonHandlers;
import game.GameGUI;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import logic.Card;
import logic.Skill;
import logic.CardsUtil;

/**
This class handles a button click and outputs a message.
The handle method is invoked when the button is clicked.
*/
public class PlayerTargetClick extends GameGUI implements EventHandler<ActionEvent>
{
	public PlayerTargetClick(){}
	@Override
	public void handle(ActionEvent event){
	
		if (getCardToUse() >= 0){//if the player selected a card
			Card card = getPlayer().getDeck().getHand().get(getCardToUse());//set card to variable
			if(card instanceof Skill){//if the card is a skill card
				Skill skillCard = (Skill) card;//typecast variable to skill
				if(!"weak vulnerable poison frail".contains(skillCard.getAttribute())){//if the card is not offensive
					skillCard.use(getPlayer(),getCombatMonsters());//activate the card's effects, Example: heal player and debuff monsters
					getCardButtons().get(getCardToUse()).setText("");
					getCardButtons().get(getCardToUse()).setVisible(false);//hides the card
					setCardToUse(-1);					
				}
				else{
					getDescriptions().setText("Invalid target");//if the card is not offensive you can't use it on the player
					setCardToUse(-1);				
				}
			}
			
			else if (!(card.getDamage()>0)){//if the card doesn't deal damage
				card.use(getPlayer(),getCombatMonsters());//use it on the player
				getCardButtons().get(getCardToUse()).setText("");
				getCardButtons().get(getCardToUse()).setVisible(false);//hide the card
				setCardToUse(-1);
			}

			else {//otherwise it is not a healing or buffing card and cannot be used on the player
				getDescriptions().setText("Invalid target");
				setCardToUse(-1);
			}
		}
		
		if (getPotionToUse() >= 0){//if the player selects a potion
			Card potion = getPlayer().getPotions().get(getPotionToUse());//set the potion to a variable
			if(potion instanceof Skill){//if the potion is a skill potion, example, Block/Armour potion
				Skill skillPotion = (Skill) potion;//typecast to skill
				if(!"weak vulnerable poison frail".contains(skillPotion.getAttribute())){//if it is not offensive
					skillPotion.use(getPlayer(),getCombatMonsters());//activate it's effects
					getPlayer().removePotion(getPotionToUse());//remove the potion from inventory
					setPotionToUse(-1);					
				}
				else{//otherwise the potion cannot be used on the player
					getDescriptions().setText("Invalid target");
					setPotionToUse(-1);				
				}
			}
			else if (!(potion.getDamage()>0)){//if the potion is not a skill potion but heals
				potion.use(getPlayer(),getCombatMonsters());//use it on the player
				getPlayer().removePotion(getPotionToUse());//remove the potion
				setPotionToUse(-1);
			}

			else {
				getDescriptions().setText("Invalid target");//otherwise the potion damages and cannot be used on the player
				setPotionToUse(-1);
			}
		}
		GameGUI.refreshVisuals();
	}
}
