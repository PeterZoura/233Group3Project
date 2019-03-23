import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
This class handles a button click and outputs a message.
The handle method is invoked when the button is clicked.
*/
public class RelicButtonClick extends GameGUI implements EventHandler<ActionEvent>
{
	int relicIndex;

	public RelicButtonClick(int relicIndex)
	{
		this.relicIndex = relicIndex;
	}
	@Override
	public void handle(ActionEvent event)
	{
		descriptions.setText(player.getRelics().get(relicIndex).getStatDescription());
		GameGUI.refreshVisuals();
    }
}

