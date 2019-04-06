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
	
		if (getCardToUse() >= 0){
			Card card = getPlayer().getDeck().getHand().get(getCardToUse());
			if(card instanceof Skill){
				Skill skillCard = (Skill) card;
				if(!"weak vulnerable poison frail".contains(skillCard.getAttribute())){
					skillCard.use(getPlayer(),getCombatMonsters());
					getCardButtons().get(getCardToUse()).setText("");
					getCardButtons().get(getCardToUse()).setVisible(false);
					setCardToUse(-1);					
				}
				else{
					getDescriptions().setText("Invalid target");
					setCardToUse(-1);				
				}
			}
			
			else if (!(card.getDamage()>0)){
				card.use(getPlayer(),getCombatMonsters());
				getCardButtons().get(getCardToUse()).setText("");
				getCardButtons().get(getCardToUse()).setVisible(false);
				setCardToUse(-1);
			}

			else {
				getDescriptions().setText("Invalid target");
				setCardToUse(-1);
			}
		}
		
		if (getPotionToUse() >= 0){
			Card potion = getPlayer().getPotions().get(getPotionToUse());
			if(potion instanceof Skill){
				Skill skillPotion = (Skill) potion;
				if(!"weak vulnerable poison frail".contains(skillPotion.getAttribute())){
					skillPotion.use(getPlayer(),getCombatMonsters());
					getPlayer().removePotion(getPotionToUse());
					setPotionToUse(-1);					
				}
				else{
					getDescriptions().setText("Invalid target");
					setPotionToUse(-1);				
				}
			}
			else if (!(potion.getDamage()>0)){
				potion.use(getPlayer(),getCombatMonsters());
				getPlayer().removePotion(getPotionToUse());
				setPotionToUse(-1);
			}

			else {
				getDescriptions().setText("Invalid target");
				setPotionToUse(-1);
			}
		}
		GameGUI.refreshVisuals();
	}
}
