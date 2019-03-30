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
	
		if (cardToUse >= 0){
			Card card = player.getDeck().getHand().get(cardToUse);
			if(card instanceof Skill){
				Skill skillCard = (Skill) card;
				if(!"weak vulnerable poison frail".contains(skillCard.getAttribute())){
					skillCard.use(player,combatMonsters);
					cardButtons.get(cardToUse).setText("");
					cardButtons.get(cardToUse).setVisible(false);
					cardToUse = -1;					
				}
				else{
					descriptions.setText("Invalid target");
					cardToUse = -1;				
				}
			}
			
			else if (!(card.getDamage()>0)){
				card.use(player,combatMonsters);
				cardButtons.get(cardToUse).setText("");
				cardButtons.get(cardToUse).setVisible(false);
				cardToUse = -1;
			}

			else {
				descriptions.setText("Invalid target");
				cardToUse = -1;
			}
		}
		
		if (potionToUse >= 0){
			Card potion = player.getPotions().get(potionToUse);
			if(potion instanceof Skill){
				Skill skillPotion = (Skill) potion;
				if(!"weak vulnerable poison frail".contains(skillPotion.getAttribute())){
					skillPotion.use(player,combatMonsters);
					player.removePotion(potionToUse);
					potionToUse = -1;					
				}
				else{
					descriptions.setText("Invalid target");
					potionToUse = -1;				
				}
			}
			else if (!(potion.getDamage()>0)){
				potion.use(player,combatMonsters);
				player.removePotion(potionToUse);
				potionToUse = -1;
			}

			else {
				descriptions.setText("Invalid target");
				potionToUse = -1;
			}
		}
		GameGUI.refreshVisuals();
	}
}
