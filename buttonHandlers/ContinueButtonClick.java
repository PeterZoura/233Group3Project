package buttonHandlers;
import game.GameGUI;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import logic.Card;
import javafx.application.Platform;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;

/**
This class handles a button click and outputs a message.
The handle method is invoked when the button is clicked.
*/
public class ContinueButtonClick extends GameGUI implements EventHandler<ActionEvent>{
	public ContinueButtonClick(){}
	@Override
	public void handle(ActionEvent event){
		HBox monsters = new HBox();
		for (VBox mPane : getMonsterPanes()){//Cycles through each Monster pane
			monsters.getChildren().add(mPane);//Adds the mosnter pane to the HBox containing monsters
		}  //Adds the array of VBoxes representing the monsters.
		getRoot().setRight(monsters);//monsters will be displayed on the right of the BorderPane
		for(int i = 0; i<3;i++){//hides the card reward buttons
			getRewardCardButtons().get(i).setVisible(true);
			getRewardCardButtons().get(i).setDisable(false);
		}
		getEndTurnButton().setDisable(false);//Enables potion reward buttons for next reward after the next fight
		for (Button reward : getRewardPotionRelicButtons()){
			reward.setDisable(false);
		}
		for (Button card : getCardButtons()){//Makes the player's hand visible
			card.setVisible(true);
		}
		getPlayer().startCombat(getCombatMonsters());
		playerTurn();
		refreshVisuals();
	}
	
}
