package buttonHandlers;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import game.GameGUI;
import javafx.application.Platform;

/**
This class handles a button click and outputs a message.
The handle method is invoked when the button is clicked.
*/
public class EndButtonClick extends GameGUI implements EventHandler<ActionEvent>{
	public EndButtonClick(){}
	@Override
	public void handle(ActionEvent event){
		for (int i=0; i<5; i++){//This for loop is here to re-show the cards after the buttons are hidden
			getCardButtons().get(i).setVisible(true);
		}
		endTurn();//The end turn button calls the endTurn command from GameGUI
		refreshVisuals();
	}
	
}
