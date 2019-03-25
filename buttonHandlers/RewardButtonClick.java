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
		if (numberClicks==1){
			descriptions.setText(rewardCards.get(cardIndex).getDescription() + " Click the card again to add it to your hand");
		}
		else{
			player.addCard(rewardCards.get(cardIndex));
			descriptions.setText(rewardCards.get(cardIndex).getName() + " added to your deck!");
			for(int i = 0; i<3;i++){
				rewardCardButtons.get(i).setVisible(false);
			}	
			rewardCardButtons.get(cardIndex).setVisible(true);
			rewardCardButtons.get(cardIndex).setDisable(true);
			numberClicks = 0;
		}
    }
}

