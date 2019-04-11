package buttonHandlers;
import game.GameGUI;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import logic.Card;
import javafx.scene.control.Button;
import logic.Player;
/**
This class handles a button click and outputs a message.
The handle method is invoked when the button is clicked.
*/
public class PotionRewardButtonClick extends GameGUI implements EventHandler<ActionEvent>
{
	int cardIndex;//represents the index of the card in the rewards pane that the player has clicked
	int numberClicks;//represents the number of times the card was clicked
	public PotionRewardButtonClick(int cardIndex)
	{
		this.cardIndex = cardIndex;
		numberClicks = 0;
	}
	@Override
	public void handle(ActionEvent event)
	{
		numberClicks++;//if it is clicked then increase numberClicks
		if (numberClicks==1){//if the card was clicked once then tell the player what it does
			getDescriptions().setText(getRewardPotion().getDescription() + " Click the potion again to add it to your hand.");
		}
		else if (getPlayer().getPotions().size()<3){//otherwise it was clicked twice and the player has space for it
			getPlayer().addPotion(getRewardPotion());//add the potion to their inventory
			getDescriptions().setText(getRewardPotion().getName() + " added to your inventory!");//tell them it was added
			getRewardPotionRelicButtons().get(cardIndex).setDisable(true);//Disable the button for the taken reward
			numberClicks = 0;
		}else{//otherwise the player clicked twice and does not have space for the potion
			getDescriptions().setText("No space, potion not added.");//tell them they don't have space
		}
		refreshVisuals();
    }
}

