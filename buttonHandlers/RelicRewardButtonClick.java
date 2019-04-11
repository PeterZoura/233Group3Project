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
public class RelicRewardButtonClick extends GameGUI implements EventHandler<ActionEvent>
{
	int index;
	int numberClicks;
	public RelicRewardButtonClick(int index)
	{
		this.index = index;
		numberClicks = 0;
	}
	@Override
	public void handle(ActionEvent event)
	{
		numberClicks++;
		if (numberClicks==1){//If the player clicked on a relic then tell them what it does
			getDescriptions().setText(getRewardRelic().getStatDescription() + " Click the relic again to add it to your hand.");
		}
		else{//if they clicked twice
			getPlayer().addRelic(getRewardRelic());//give them the relic
			getDescriptions().setText(getRewardRelic().getName() + " added to your inventory!");//tell them they have it
			getRewardPotionRelicButtons().get(index).setDisable(true);//disable the reward button
			numberClicks = 0;
		}
		refreshVisuals();
    }
}

