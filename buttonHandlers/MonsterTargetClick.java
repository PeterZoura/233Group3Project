package buttonHandlers;
import game.GameGUI;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import logic.Card;

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
		
		if (potionToUse >= 0){
			Card potion = player.getPotions().get(potionToUse);
			if (!potion.requiresTarget()){
				potion.use(player,combatMonsters);
			}
			
			else{
				potion.use(player,combatMonsters[monsterIndex]);
			}
			player.removePotion(potionToUse);
			potionButtons.get(potionToUse).setText("");
			potionToUse = -1;
		}
		GameGUI.refreshVisuals();
	}
}