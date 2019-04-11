package buttonHandlers;
import game.GameGUI;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import logic.Card;

/**
*This class handles a button click and outputs a message.
*The handle method is invoked when the button is clicked.
*/
public class CardButtonClick extends GameGUI implements EventHandler<ActionEvent>
{
	int cardIndex;//represents the index, in the array of cards, of the card that has been clicked
	/**
	*CardButtonClick detects when a card has been selected
	*@param int cardIndex, represents the index of the card that has been click in the array of cards 
	*/
	public CardButtonClick(int cardIndex)
	{
		this.cardIndex = cardIndex;
	}
	@Override
	public void handle(ActionEvent event)
	{
		
		Card card = getPlayer().getDeck().getHand().get(cardIndex);
		getDescriptions().setText(card.getDescription());
		if (card.getCost()<=getPlayer().getEnergy()){
			setCardToUse(cardIndex);
		}
		else{
			getDescriptions().setText("Not enough energy.");
			setCardToUse(-1);
		}
		setPotionToUse(-1);
    }
}

