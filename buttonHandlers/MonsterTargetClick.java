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
public class MonsterTargetClick extends GameGUI implements EventHandler<ActionEvent>
{
	int monsterIndex;

	public MonsterTargetClick(int mIndex)
	{
		this.monsterIndex = mIndex;
	}
	@Override
	public void handle(ActionEvent event){
	
		if (cardToUse >= 0){
			Card card = player.getDeck().getHand().get(cardToUse);
			if ((card.getDamage()>0)){
				if (!card.requiresTarget()){
						card.use(player,combatMonsters);
					}
					else{
						card.use(player,combatMonsters[monsterIndex]);
					}
					cardButtons.get(cardToUse).setText("");
					cardButtons.get(cardToUse).setVisible(false);
					cardToUse = -1;	
			}
			else if(card instanceof Skill){
				Skill skillCard = (Skill) card;
				if("weak vulnerable poison frail".contains(skillCard.getAttribute())){
					if (!skillCard.requiresTarget()){
						skillCard.use(player,combatMonsters);
					}
					else{
						skillCard.use(player,combatMonsters[monsterIndex]);
					}
					cardButtons.get(cardToUse).setText("");
					cardButtons.get(cardToUse).setVisible(false);
					cardToUse = -1;					
				}
				else{
					descriptions.setText("Invalid target");
					cardToUse = -1;				
				}
			}
			else {
				descriptions.setText("Invalid target");
				cardToUse = -1;
			}
		}
		
		if (potionToUse >= 0){
			Card potion = player.getPotions().get(potionToUse);
			if (potion.getDamage()>0){
				if (!potion.requiresTarget()){
					potion.use(player,combatMonsters);
				}
				else{
					potion.use(player,combatMonsters[monsterIndex]);
				}
				player.removePotion(potionToUse);
				potionToUse = -1;
			}
			else if(potion instanceof Skill){
				Skill skillPotion = (Skill) potion;
				if("weak vulnerable poison frail".contains(skillPotion.getAttribute())){
					if (!skillPotion.requiresTarget()){
						skillPotion.use(player,combatMonsters);
					}
					else{
						skillPotion.use(player,combatMonsters[monsterIndex]);
					}
					player.removePotion(potionToUse);
					potionToUse = -1;				
				}
				else{
					descriptions.setText("Invalid target");
					potionToUse = -1;				
				}
			}
			else {
				descriptions.setText("Invalid target");
				potionToUse = -1;
			}
		}
		GameGUI.refreshVisuals();
	}
}
