package buttonHandlers;
import game.GameGUI;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import logic.Card;

/**
This class handles a button click and outputs a message.
The handle method is invoked when the button is clicked.
*/
public class PotionButtonClick extends GameGUI implements EventHandler<ActionEvent>
{
	int potionIndex;

	public PotionButtonClick(int potionIndex)
	{
		this.potionIndex = potionIndex;
	}
	@Override
	public void handle(ActionEvent event)
	{
		try{
			Card potion = getPlayer().getPotions().get(potionIndex);
			getDescriptions().setText(potion.getDescription());
			setPotionToUse(potionIndex);
			setCardToUse(-1);
			
		}
		catch (IndexOutOfBoundsException e){}
		
		//GameGUI.refreshVisuals();
    }
}

