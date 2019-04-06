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
		for (VBox mPane : getMonsterPanes()){
			monsters.getChildren().add(mPane);
		}  //Adds the array of VBoxes representing the monsters.
		getRoot().setRight(monsters);
		for(int i = 0; i<3;i++){
			getRewardCardButtons().get(i).setVisible(true);
			getRewardCardButtons().get(i).setDisable(false);
		}
		getEndTurnButton().setDisable(false);
		for (Button reward : getRewardPotionRelicButtons()){
			reward.setDisable(false);
		}
		for (Button card : getCardButtons()){
			card.setVisible(true);
		}
		getPlayer().startCombat(getCombatMonsters());
		playerTurn();
		refreshVisuals();
	}
	
}
