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
		this.potionIndex = potionIndex;//if the player click's a potion  then set that potion's index to potionIndex
	}
	@Override
	public void handle(ActionEvent event)
	{
		try{
			Card potion = getPlayer().getPotions().get(potionIndex);//set that potion to a variable using proper getter
			getDescriptions().setText(potion.getDescription());//display the description
			setPotionToUse(potionIndex);//tell the game that the player has selected this potion
			setCardToUse(-1);//tell the game that the player is not using a card
			
		}
		catch (IndexOutOfBoundsException e){}
    }
}

