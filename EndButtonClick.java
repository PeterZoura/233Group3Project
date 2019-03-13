import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
This class handles a button click and outputs a message.
The handle method is invoked when the button is clicked.
*/
public class EndButtonClick extends GameGUI implements EventHandler<ActionEvent>{
	public EndButtonClick(){}
	@Override
	public void handle(ActionEvent event){
		endTurn();
		playerTurn();
		refreshVisuals();
	}
	
}