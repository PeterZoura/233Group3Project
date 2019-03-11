

import javafx.application.Application;
import javafx.scene.Scene.*;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.*;
import javafx.scene.paint.*;
import javafx.scene.canvas.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.HBox;
import javafx.scene.control.Button;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.control.Label;
import javafx.scene.control.Button;

import javafx.scene.layout.GridPane;
import javafx.geometry.Pos; 
import javafx.geometry.Insets; 

/* */

public class GameAppGridPane extends javafx.application.Application {
	//Private Instance Variables
	//private boolean forPlayer; NOT USED
	//private boolean forMonster; NOT USED
	private Card cardSelected; 
	private char cardBtnLetter;
	
	/*
	 * Boolean method to check if the card is for increasing block or healing the player
	 * Returns true if card affects the player
	 * Returns false if it does not affect the player, thus making it a card that affects the monster
	 */
	public boolean isForPlayer(Card input) {
		if (	(input.getBlock() > 0) || (input.getHeal() > 0)  ) {
			return true;
		}
		else if ( (input.getDamage() > 0) )
			return false; 
	}
	
	
	public void disablePlayerOrMonsterButton () {
		if (this.isForPlayer(cardSelected) == true) {
			btnMonsterImage.setDisable(true);
		}
		else if (this.isForPlayer(cardSelected) == false) {
			btnPlayerImage.setDisable(true);
		}
	}
	
	public void disableCardAfterUse(char inputLetter) {
		if (inputLetter = 'A')
			cardA.setDisable(true);
		else if (inputLetter = 'B')
			cardB.setDisable(true);
		else if (inputLetter = 'C')
			cardC.setDisable(true);
		else if (inputLetter = 'D')
			cardD.setDisable(true);
		else if (inputLetter = 'E')
			cardE.setDisable(true);
	}
	
	@Override
	public void start(Stage primaryStage) {
		//Creating GridPane layout
		GridPane root = new GridPane();
		root.setMinSize(1270, 720);
		
// PLAYER --------------------------------------------------------------------		
		VBox playerStack = new VBox();
		playerStack.setAlignment(Pos.CENTER);		
	//Player Name [Display]
		Label playerName = new Label("Player");
		playerName.setFont(Font.font("Arial", 12));
		playerStack.getChildren().add(playerName);			
	//Player Image Area [Button]
		Button btnPlayerImage;	
		btnPlayerImage = new Button ("Player");
		btnPlayerImage.setFont(Font.font("Arial", 36));
		playerStack.getChildren().add(btnPlayerImage);
	//Player HP Bar [Display]
		Label playerHPBar = new Label (Player.getHealth() + "/" + Player.getMaxHealth());
		playerStack.getChildren().add(playerHPBar);
//Add playerStack to GridPane
		root.add(playerStack, 190.5, 240);		
		
	//Player Energy Counter [Display]
		Label playerEnergyBar = new Label (Player.getEnergy() + "/" + Player.getMaxEnergy());
//Add playerEnergyBar to GridPane
		root.add(playerEnergyBar, 190.5, 480);
				
	//End Player Turn [Button]
		Button btnEndTurn;
		btnEndTurn = new Button ("End Turn");
//Add btnEndTurn to GridPane
		root.add(btnEndTurn, 1143, 480);
		
// CARDS --------------------------------------------------------------------
		HBox cardStack = new HBox();		
		//Card Buttons [Button]
		Button cardA = new Button ("Card A");
		cardStack.getChildren().add(cardA);
		Button cardB = new Button ("Card B");
		cardStack.getChildren().add(cardB);
		Button cardC = new Buton ("Card C");
		cardStack.getChildren().add(cardC);
		Button cardD = new Button ("Card D");
		cardStack.getChildren().add(cardD);
		Button cardE = new Button ("Card E");
		cardStack.getChildren().add(cardE);
		
		root.add(cardStack, 254, 617);
		
		
// MONSTERS ------------------------------------------------------------------
		VBox monsterStack = new VBox();
		monsterStack.setAlignment(Pos.CENTER);		
		//Monster Name [Display]
		Label monsterName = new Label(Monster.getName());
		monsterName.setFont(Font.font("Arial", 12));
		//Monster Image Area [Button]
		Button btnMonsterImage;	
		btnMonsterImage = new Button ("Monster");
		btnMonsterImage.setFont(Font.font("Arial", 36));
		monsterStack.getChildren().add(btnMonsterImage);
		//Monster HP Bar [Display]
		Label monsterHPBar = new Label (Monster.getHealth() + "/" + Monster.getMaxHealth());
		monsterStack.getChildren().add(monsterHPBar);		

// Organizing Monsters into a HBox, in order to accommodate for multiple monsters 
		HBox masterMonsterStack = new HBox();
		while (monsterCount > 0) {
			masterMonsterStack.getChildren().add(monsterStack)
			monsterCount--;		
		}
		
		root.add(masterMonsterStack, 508, 240);

		
//EventHandler--------------------------------------------------------------------
		/* Pressing Player Image Area Button
		 * Enabled if card is for player
		 * disabled if card is NOT for player
		 * activates the card effects once selected
		 */
		btnPlayerImage.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				this.cardSelected.use(player, player);
				disableCardAfterUse(cardBtnLetter);
			}
		}	
		
		//Pressing Monster Image Area
		btnMonsterImage.setOnAction(new EventHandler<ActionEvent>(){
		
		}
		

		
		//Pressing Card
		cardA.setOnAction(new EventHandler<ActionEvent(){
			@Override
			public void handle(ActionEvent event) {
				cardSelected = player.getDeck().getHand().get(0);
				this.disablePlayerOrMonsterButton();
				cardBtnLetter = 'A';
				
			}
		}
		cardB.setOnAction(new EventHandler<ActionEvent(){
			@Override
			public void handle(ActionEvent event) {
				cardSelected = player.getDeck().getHand().get(1);
				this.disablePlayerOrMonsterButton();
				cardBtnLetter = 'B';
			}
		}		 
		cardC.setOnAction(new EventHandler<ActionEvent(){
			@Override
			public void handle(ActionEvent event) {
				cardSelected = player.getDeck().getHand().get(2);
				this.disablePlayerOrMonsterButton();
				cardBtnLetter = 'C';
			}
		}	
		cardD.setOnAction(new EventHandler<ActionEvent(){
			@Override
			public void handle(ActionEvent event) {
				cardSelected = player.getDeck().getHand().get(3);
				this.disablePlayerOrMonsterButton();
				cardBtnLetter = 'D';
			}
		}	
		cardE.setOnAction(new EventHandler<ActionEvent(){
			@Override
			public void handle(ActionEvent event) {
				cardSelected = player.getDeck().getHand().get(4);
				this.disablePlayerOrMonsterButton();
				cardBtnLetter = 'E';
			}
		}	
		
		//Pressing btnEndTurn
		btnEndTurn.setOnAction(new EventHandler<ActionEvent>(){
		
		}		
		
		//Making Scene as root
		Scene scene = new Scene(root, 1270, 720);			
		
		//Adding a Title
		primaryStage.setTitle("Slay the Spire");
		
		//Setting Scene
		primaryStage.setScene(scene);
		
		//Showing the Stage
		primaryStage.show();
		
	}



}







