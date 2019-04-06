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
		if (numberClicks==1){
			getDescriptions().setText(getRewardRelic().getStatDescription() + " Click the relic again to add it to your hand.");
		}
		else{
			getPlayer().addRelic(getRewardRelic());
			getDescriptions().setText(getRewardRelic().getName() + " added to your deck!");
			getRewardPotionRelicButtons().get(index).setDisable(true);
			numberClicks = 0;
		}
		refreshVisuals();
    }
}

