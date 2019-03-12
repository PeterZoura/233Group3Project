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
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
/* */

public class GameAppGridPane extends Application {
	
/////////////////////////////////////////////////////////////////////
/////			INSTANCE VARIABLES  											/////
///////////////////////////////////////////////////////////////////

	private Card cardSelected; 
	private char cardBtnLetter;
	private Label notificationLabel;
	
//HERE IS THE PROBLEM, we need playerApp to directly reference the player in the game loop, can we make that player an instance variable of Game?	
	public Player playerApp = new Player("player", 80, CardsUtil.get("Strike"), CardsUtil.get("Strike"), CardsUtil.get("Strike"), CardsUtil.get("Desperate Strike"),CardsUtil.randomP(), CardsUtil.randomP(), CardsUtil.randomP());
	private Monster [] combatMonsters;
	private int monsterCount;
	
	private Monster monster1;
	private Monster monster2;
	private Monster monster3;
	
	private Label monsterHPBar1;
	private Label monsterHPBar2;
	private Label monsterHPBar3;
	
	private Label monsterName1; 
	private Label monsterName2; 
	private Label monsterName3; 
	
	private Button btnMonsterImage1;
	private Button btnMonsterImage2;
	private Button btnMonsterImage3;

	private HBox masterMonsterStack = new HBox();
	
	private Button btnPlayerImage;
	
	private Label playerHPBar;
	private Label playerEnergyBar;
	
	private Button cardA = new Button ("Card A");
	private Button cardB = new Button ("Card B");
	private Button cardC = new Button ("Card C");
	private Button cardD = new Button ("Card D");
	private Button cardE = new Button ("Card E");
	
	private Button enterBtn = new Button ("Enter");
	
	
/////////////////////////////////////////////////////////////////////////	
/////			MONSTER METHODS													//////
////////////////////////////////////////////////////////////////////////	
	
	//Method for importing all of the monsters on the field (in combat)
	public void prepForMonsterImport() {
			for (int i = 0; i < combatMonsters.length; i++){
				if (i == 0)
					importMonster1(combatMonsters[i]);
				else if (i == 1)
					importMonster2(combatMonsters[i]);
				else if (i == 2)
					importMonster3(combatMonsters[i]);	
			}
	}
	
	public void updateMonsterValues1(Monster m) {
			monsterHPBar1 = new Label (m.getHealth() + "/" + m.getMaxHealth());
	}
	public void updateMonsterValues2(Monster m) {
			if (monsterCount >= 2)
				monsterHPBar2 = new Label (m.getHealth() + "/" + m.getMaxHealth());
	}
	public void updateMonsterValues3(Monster m) {
			if (monsterCount >= 3)			
				monsterHPBar3 = new Label (m.getHealth() + "/" + m.getMaxHealth());
	}
	
	public void setCombatMonsters (Monster [] cBMonsters) {
		combatMonsters = cBMonsters; 
		monsterCount = cBMonsters.length;
	}
////////////////////////////////////////////////////////////////////////
/////			PLAYER METHODS														/////
///////////////////////////////////////////////////////////////////////	
	/*
	 * Setter method for playerApp.  
	 */
	
	public void setPlayerApp(Player p) {
		playerApp = new Player(p);
	}
	
	public void updatePlayerValues(Player p) {
		
	}
	
//////////////////////////////////////////////////////////////////////
/////			CARD METHODS													//////
////////////////////////////////////////////////////////////////////	
	
	/*
	 * Checks if the player has enough energy available to play a card in their hand
	 */
	public boolean enoughEnergyToPlayCard (Card input, Entity e) {
		if (input.getCost() >= e.getEnergy() ) {
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
			
		}
		return false;
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
		else
			return false;
	}
	
	/*
	 * Checks conditions for disabling either monster or player btn, and disables which ever one depending on the conditions. 
	 */
	public void disablePlayerOrMonsterButton () {
		if (	(isForPlayer(cardSelected) == true) && (isSkillForMonster(cardSelected) == false) ){
			btnMonsterImage1.setDisable(true);
			btnMonsterImage2.setDisable(true);
			btnMonsterImage3.setDisable(true);
		}
		else if (	(isForPlayer(cardSelected) == false) && (isSkillForMonster(cardSelected) == true) ) {
			btnPlayerImage.setDisable(true);
		}
	}
	/*
	 * Checks the card that was played, based on the letter corresponding to the card.
	 * Disables the card after usage. 
	 */
	public void disableCardAfterUse(char inputLetter) {
		if (inputLetter == 'A')
			cardA.setDisable(true);
		else if (inputLetter == 'B')
			cardB.setDisable(true);
		else if (inputLetter == 'C')
			cardC.setDisable(true);
		else if (inputLetter == 'D')
			cardD.setDisable(true);
		else if (inputLetter == 'E')
			cardE.setDisable(true);
	}
	
		public void importMonster1(Monster m) {
		monster1 = m;
	//Creates a VBox for a single monster GUI
		VBox monsterStack1 = new VBox();
		monsterStack1.setAlignment(Pos.CENTER);	
			
		//Monster Name [Display]
		monsterName1 = new Label(m.getName());
		monsterName1.setFont(Font.font("Arial", 12));
		
		//Monster Image Area [Button]
		/**
		*Button monster image1
		*the first button for the monster in slot 1
		*/
		btnMonsterImage1 = new Button ("MonsterButton1");
		btnMonsterImage1.setFont(Font.font("Arial", 36));
		monsterStack1.getChildren().add(btnMonsterImage1);
		
		//Monster HP Bar [Display]
		monsterHPBar1 = new Label (m.getHealth() + "/" + m.getMaxHealth());
		monsterStack1.getChildren().add(monsterHPBar1);	
		
		//Adds to the HBox
		masterMonsterStack.getChildren().add(monsterStack1);
	}
	
		public void importMonster2(Monster m) {
		monster2 = m;
	//Creates a VBox for a single monster GUI
		VBox monsterStack2 = new VBox();
		monsterStack2.setAlignment(Pos.CENTER);	
			
		//Monster Name [Display]
		monsterName2 = new Label(m.getName());
		monsterName2.setFont(Font.font("Arial", 12));
		
		//Monster Image Area [Button]
		btnMonsterImage2 = new Button ("MonsterButton2");
		btnMonsterImage2.setFont(Font.font("Arial", 36));
		monsterStack2.getChildren().add(btnMonsterImage2);
		
		//Monster HP Bar [Display]
		monsterHPBar2 = new Label (m.getHealth() + "/" + m.getMaxHealth());
		monsterStack2.getChildren().add(monsterHPBar2);	
		
		//Adds to the HBox
		masterMonsterStack.getChildren().add(monsterStack2);	
	}	
	
	public void importMonster3(Monster m) {
		monster3 = m;
	//Creates a VBox for a single monster GUI
		VBox monsterStack3 = new VBox();
		monsterStack3.setAlignment(Pos.CENTER);	
			
		//Monster Name [Display]
		monsterName3 = new Label(m.getName());
		monsterName3.setFont(Font.font("Arial", 12));
		
		//Monster Image Area [Button]
		btnMonsterImage3 = new Button ("MonsterButton3");
		btnMonsterImage3.setFont(Font.font("Arial", 36));
		monsterStack3.getChildren().add(btnMonsterImage3);
		
		//Monster HP Bar [Display]
		monsterHPBar3 = new Label (m.getHealth() + "/" + m.getMaxHealth());
		monsterStack3.getChildren().add(monsterHPBar3);	
		
		//Adds to the HBox
		masterMonsterStack.getChildren().add(monsterStack3);
	}

			
	
/////////////////////////////////////////////////////////////////////////
//					VOID START  												 ////////
////////////////////////////////////////////////////////////////////////	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		//Creating GridPane layout
		GridPane root = new GridPane();
		root.setMinSize(1270, 720);
		root.add(notificationLabel, 635, 360);
		
// *******PLAYER SCENE***************----------------------------------------------------------------		

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
		playerHPBar = new Label (playerApp.getHealth() + "/" + playerApp.getMaxHealth());
		playerStack.getChildren().add(playerHPBar);
		
//Add playerStack to GridPane
		root.add(playerStack, 191, 240);		
		
	//Player Energy Counter [Display]
		playerEnergyBar = new Label (playerApp.getEnergy() + "/" + playerApp.getMaxEnergy());
		
//Add playerEnergyBar to GridPane
		root.add(playerEnergyBar, 191, 480);
				
	//End Player Turn [Button]
		Button btnEndTurn;
		btnEndTurn = new Button ("End Turn");
//Add btnEndTurn to GridPane
		root.add(btnEndTurn, 1143, 480);
		
// CARDS SCENE****************************--------------------------------------------------------------------
		HBox cardStack = new HBox();		
		/* Card Buttons [Button]
		 * Adds the card buttons to the scene in a HBox layout
		 */
		
		cardStack.getChildren().add(cardA);
		cardStack.getChildren().add(cardB);
		cardStack.getChildren().add(cardC);
		cardStack.getChildren().add(cardD);
		cardStack.getChildren().add(cardE);
		
		root.add(cardStack, 254, 617);
		
		
// MONSTERS SCENE**********************------------------------------------------------------------------

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
				cardSelected.use(playerApp, playerApp);
				disableCardAfterUse(cardBtnLetter);
			}
		}	);
		

		
		
		/*
		//Pressing Monster Image Area 1
		btnMonsterImage1.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event) {
				cardSelected.use(playerApp, monster1);
				disableCardAfterUse(cardBtnLetter);
			}
		} );
		
		//Pressing Monster Image Area 2
		btnMonsterImage2.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				cardSelected.use(playerApp, monster2);
				disableCardAfterUse(cardBtnLetter);
			}
		} );
		
		//Pressing Monster Image Area 3
		btnMonsterImage3.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				cardSelected.use(playerApp, monster3);
				disableCardAfterUse(cardBtnLetter);
			}
		} );
		
		
		//Pressing Card
		cardA.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				cardSelected = playerApp.getDeck().getHand().get(0);
				if (enoughEnergyToPlayCard(cardSelected, playerApp) == true) {
					disablePlayerOrMonsterButton();
					cardBtnLetter = 'A';
				}
				
			}
		});
		cardB.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				cardSelected = playerApp.getDeck().getHand().get(1);
				if (enoughEnergyToPlayCard(cardSelected, playerApp) == true) {
					disablePlayerOrMonsterButton();
					cardBtnLetter = 'B';
				}
				
			}
		}		);
		cardC.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				cardSelected = playerApp.getDeck().getHand().get(2);
				if (enoughEnergyToPlayCard(cardSelected, playerApp) == true) {
					disablePlayerOrMonsterButton();
					cardBtnLetter = 'C';
				}
				
			}
		} )	;
		cardD.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				cardSelected = playerApp.getDeck().getHand().get(3);
				if (enoughEnergyToPlayCard(cardSelected, playerApp) == true) {
					disablePlayerOrMonsterButton();
					cardBtnLetter = 'D';
				}
				
			}
		}	);
		cardE.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				cardSelected = playerApp.getDeck().getHand().get(4);
				if (enoughEnergyToPlayCard(cardSelected, playerApp) == true) {
					disablePlayerOrMonsterButton();
					cardBtnLetter = 'E';
				}
				
			}
		}	);
		
		//Pressing btnEndTurn
		
		btnEndTurn.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				playerApp.endTurn();
			}
		}	);
		*/
		
		//Making Scene as root
		Scene scene = new Scene(root, 1270, 720);			
		
		//Adding a Title
		primaryStage.setTitle("Slay the Spire");
		
		//Setting Scene
		primaryStage.setScene(scene);
		
		//Showing the Stage
		primaryStage.show();
		
	} //END OF START METHOD --------------------------------------------------------------------------------
			
		public void handle(ActionEvent event){
			if (event.getSource() instanceof Button){
				Button btnClicked = (Button) event.getSource();
				if (btnClicked.getText().equals("MonsterButton1")){
					cardSelected.use(playerApp, monster1);
					disableCardAfterUse(cardBtnLetter);
				}else if (btnClicked.getText().equals("MonsterButton2")){
					cardSelected.use(playerApp, monster2);
					disableCardAfterUse(cardBtnLetter);
				}else if (btnClicked.getText().equals("MonsterButton3")){
					cardSelected.use(playerApp, monster3);
					disableCardAfterUse(cardBtnLetter);
				}else if (btnClicked.getText().equals("Card A")){
					cardSelected = playerApp.getDeck().getHand().get(0);
					if (enoughEnergyToPlayCard(cardSelected, playerApp) == true) {
						disablePlayerOrMonsterButton();
						cardBtnLetter = 'A';
					}
				}else if (btnClicked.getText().equals("Card B")){
					cardSelected = playerApp.getDeck().getHand().get(1);
					if (enoughEnergyToPlayCard(cardSelected, playerApp) == true) {
						disablePlayerOrMonsterButton();
						cardBtnLetter = 'B';
					}
				}else if (btnClicked.getText().equals("Card C")){
					cardSelected = playerApp.getDeck().getHand().get(2);
					if (enoughEnergyToPlayCard(cardSelected, playerApp) == true) {
						disablePlayerOrMonsterButton();
						cardBtnLetter = 'C';
					}
				}else if (btnClicked.getText().equals("Card D")){
					cardSelected = playerApp.getDeck().getHand().get(3);
					if (enoughEnergyToPlayCard(cardSelected, playerApp) == true) {
						disablePlayerOrMonsterButton();
						cardBtnLetter = 'D';
					}
				}else if (btnClicked.getText().equals("Card E")){
					cardSelected = playerApp.getDeck().getHand().get(4);
					if (enoughEnergyToPlayCard(cardSelected, playerApp) == true) {
						disablePlayerOrMonsterButton();
						cardBtnLetter = 'E';
					}
				}else if (btnClicked.getText().equals("End Turn")){
					playerApp.endTurn();
				}
			}
		}


}