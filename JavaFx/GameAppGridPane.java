

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
	private Card cardSelected; 
	private char cardBtnLetter;
	private Label notificationLabel;
	notificationLabel.setFont(Font.font("Arial", 18));
	
	/*
	 * Checks if the player has enough energy available to play a card in their hand
	 */
	public boolean enoughEnergyToPlayCard (Card input, Entity e) {
		if (input.getEnergy() >= e.getEnergy() ) {
			notificationLabel.setText("");
			return true;
		}
		else {
			notificationLabel.setText("Not enough energy to use card, please select another");
			return false;
		}
	}
	
	/*
	 * Checks if the card is of a skill class, if so, then checks if it is an offensive skill class or defensive skill class 
	 */
	public boolean isSkillForMonster(Card card) {
		if (card.getClass().getSimpleName().equals("Skill")) {  //Checks if the card is a skill subclass
			Skill skill = (Skill)card;
			if ("weak vulnerable poison frail".contains(skill.getAttribute())) //Checks if it's monster, returns true if monster. Returns false if card is for player.
				return true;
			else
				return false;
		}
	}
	
	/*
	 * Boolean method to check if the card is for increasing block or healing the player
	 * Returns true if card affects the player
	 * Returns false if it does not affect the player, thus making it a card that affects the monster
	 * 3 boolean condition groups in place: 
	 */
	public boolean isForPlayer(Card input) {
		if (	(input.getBlock() > 0) || (input.getHeal() > 0)  ) {
			return true;
		}
		else if ( (input.getDamage() > 0) )
			return false; 
	}
	
	/*
	 * Checks conditions for disabling either monster or player btn, and disables which ever one depending on the conditions. 
	 */
	public void disablePlayerOrMonsterButton () {
		if (	(this.isForPlayer(cardSelected) == true) && (this.isSkillForMonster(cardSelected) == false) ){
			btnMonsterImage.setDisable(true);
		}
		else if (	(this.isForPlayer(cardSelected) == false) && (this.isSkillForMonster(cardSelected) == true) ) {
			btnPlayerImage.setDisable(true);
		}
	}
	/*
	 * Checks the card that was played, based on the letter corresponding to the card.
	 * Disables the card after usage. 
	 */
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
	
/////////////////////////////////////////////////////////////////////////
//	MAIN START ROOT 											////////
////////////////////////////////////////////////////////////////////////	
	@Override
	public void start(Stage primaryStage) {
		//Creating GridPane layout
		GridPane root = new GridPane();
		root.setMinSize(1270, 720);
		root.add(notificationLabel, 635, 360);
		
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
		root.add(playerStack, 191, 240);		
		
	//Player Energy Counter [Display]
		Label playerEnergyBar = new Label (Player.getEnergy() + "/" + Player.getMaxEnergy());
//Add playerEnergyBar to GridPane
		root.add(playerEnergyBar, 191, 480);
				
	//End Player Turn [Button]
		Button btnEndTurn;
		btnEndTurn = new Button ("End Turn");
//Add btnEndTurn to GridPane
		root.add(btnEndTurn, 1143, 480);
		
// CARDS --------------------------------------------------------------------
		HBox cardStack = new HBox();		
		/* Card Buttons [Button]
		 * Adds the card buttons to the scene in a HBox layout
		 */
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
		HBox masterMonsterStack = new HBox();
		
	/*
	 * Method takes a monster object, then gets its properties and adds them to the javafx scene
	 */
	public void importMonster(Monster m) {
	//Creates a VBox for a single monster GUI
		VBox monsterStack = new VBox();
		monsterStack.setAlignment(Pos.CENTER);		
		//Monster Name [Display]
		Label monsterName = new Label(m.getName());
		monsterName.setFont(Font.font("Arial", 12));
		//Monster Image Area [Button]
		Button btnMonsterImage;	
		btnMonsterImage = new Button ("Monster");
		btnMonsterImage.setFont(Font.font("Arial", 36));
		monsterStack.getChildren().add(btnMonsterImage);
		//Monster HP Bar [Display]
		Label monsterHPBar = new Label (m.getHealth() + "/" + m.getMaxHealth());
		monsterStack.getChildren().add(monsterHPBar);	
		//Adds to the HBox
		masterMonsterStack.getChildren().add(monsterStack);
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
			@Override
			public void handle(ActionEvent event) {
				this.cardSelected.use(player, monster);
				disableCardAfterUse(cardBtnLetter);
			}
		}
		
		//Pressing Card
		cardA.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				cardSelected = player.getDeck().getHand().get(0);
				if (enoughEnergyToPlayCard(cardSelected, player) == true) {
					this.disablePlayerOrMonsterButton();
					cardBtnLetter = 'A';
				}
				else {
					return ("Invalid selection, please select another card");
				}
			}
		}
		cardB.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				cardSelected = player.getDeck().getHand().get(1);
				if (enoughEnergyToPlayCard(cardSelected, player) == true) {
					this.disablePlayerOrMonsterButton();
					cardBtnLetter = 'B';
				}
				else {
					return ("Invalid selection, please select another card");
				}
			}
		}		 
		cardC.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				cardSelected = player.getDeck().getHand().get(2);
				if (enoughEnergyToPlayCard(cardSelected, player) == true) {
					this.disablePlayerOrMonsterButton();
					cardBtnLetter = 'C';
				}
				else {
					return ("Invalid selection, please select another card");
				}
			}
		}	
		cardD.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				cardSelected = player.getDeck().getHand().get(3);
				if (enoughEnergyToPlayCard(cardSelected, player) == true) {
					this.disablePlayerOrMonsterButton();
					cardBtnLetter = 'D';
				}
				else {
					return ("Invalid selection, please select another card");
				}
			}
		}	
		cardE.setOnAction(new EventHandler<ActionEvent(){
			@Override
			public void handle(ActionEvent event) {
				cardSelected = player.getDeck().getHand().get(4);
				if (enoughEnergyToPlayCard(cardSelected, player) == true) {
					this.disablePlayerOrMonsterButton();
					cardBtnLetter = 'E';
				}
				else {
					return ("Invalid selection, please select another card");
				}
			}
		}	
		
		//Pressing btnEndTurn
		btnEndTurn.setOnAction(new EventHandler<ActionEvent>(){
			player.setEnergy(0);
			player.endTurn();
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







