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
	int cardIndex;
	int numberClicks;
	public PotionRewardButtonClick(int cardIndex)
	{
		this.cardIndex = cardIndex;
		numberClicks = 0;
	}
	@Override
	public void handle(ActionEvent event)
	{
		numberClicks++;
		if (numberClicks==1){
			getDescriptions().setText(getRewardPotion().getDescription() + " Click the potion again to add it to your hand.");
		}
		else if (getPlayer().getPotions().size()<3){
			getPlayer().addPotion(getRewardPotion());
			getDescriptions().setText(getRewardPotion().getName() + " added to your deck!");
			getRewardPotionRelicButtons().get(cardIndex).setDisable(true);
			numberClicks = 0;
		}else{
			getDescriptions().setText("No space, potion not added.");
		}
		refreshVisuals();
    }
}

