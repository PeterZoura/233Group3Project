package buttonHandlers;
import game.GameGUI;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
This class handles a button click and outputs a message.
The handle method is invoked when the button is clicked.
*/
public class HandleButtonClick extends GameGUI implements EventHandler<ActionEvent>
{
	int cardIndex;

	public HandleButtonClick(int cardIndex)
	{
		this.cardIndex = cardIndex;
	}
	@Override
	public void handle(ActionEvent event)
	{
        if (player.getDeck().getHand().get(cardIndex).use(player, combatMonsters[0])) {
			cardButtons.get(cardIndex).setDisable(true);
		} else {
			System.out.println("You don't have enough energy.");
		}
		GameGUI.refreshVisuals();
    }
}

