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
			getDescriptions().setText(getRewardCards().get(cardIndex).getDescription() + " Click the card again to add it to your hand");
		}
		else{
			getPlayer().addCard(getRewardCards().get(cardIndex));
			getDescriptions().setText(getRewardCards().get(cardIndex).getName() + " added to your deck!");
			for(int i = 0; i<3;i++){
				getRewardCardButtons().get(i).setVisible(false);
			}	
			getRewardCardButtons().get(cardIndex).setVisible(true);
			getRewardCardButtons().get(cardIndex).setDisable(true);
			numberClicks = 0;
		}
    }
}

