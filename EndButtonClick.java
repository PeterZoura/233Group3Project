import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
			cardButtons.get(i).setVisible(true);
		}
		endTurn();
		playerTurn();
		refreshVisuals();
	}
	
}