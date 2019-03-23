import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
This class handles a button click and outputs a message.
The handle method is invoked when the button is clicked.
*/
public class CardButtonClick extends GameGUI implements EventHandler<ActionEvent>
{
	int cardIndex;

	public CardButtonClick(int cardIndex)
	{
		this.cardIndex = cardIndex;
	}
	@Override
	public void handle(ActionEvent event)
	{
		
		Card card = player.getDeck().getHand().get(cardIndex);
		descriptions.setText(card.getDescription());
		if (card.getCost()<=player.getEnergy()){
			cardToUse = cardIndex;
		}
		else{
			descriptions.setText("Not enough energy.");
			cardToUse = -1;
		}
		potionToUse = -1;
		//GameGUI.refreshVisuals();
    }
}

