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
public class RewardButtonClick extends GameGUI implements EventHandler<ActionEvent>
{
	int cardIndex;
	int numberClicks;
	public RewardButtonClick(int cardIndex)
	{
		this.cardIndex = cardIndex;
		numberClicks = 0;
	}
	@Override
	public void handle(ActionEvent event)
	{
		numberClicks++;
		if (numberClicks==1){//if the player clicked on a reward card then tell them what it does
			getDescriptions().setText(getRewardCards().get(cardIndex).getDescription() + " Click the card again to add it to your hand");
		}
		else{//otherwise they clicked it twice
			getPlayer().addCard(getRewardCards().get(cardIndex));//add the card to their deck and tell them it was added
			getDescriptions().setText(getRewardCards().get(cardIndex).getName() + " added to your deck!");
			for(int i = 0; i<3;i++){//set all 3 reward card buttons to invisible because the player may only take one card
				getRewardCardButtons().get(i).setVisible(false);
			}	
			getRewardCardButtons().get(cardIndex).setVisible(true);//sets the button for the selected card to be visible but disabled
			getRewardCardButtons().get(cardIndex).setDisable(true);//this is to help the player see which card they chose
			numberClicks = 0;
		}
    }
}

