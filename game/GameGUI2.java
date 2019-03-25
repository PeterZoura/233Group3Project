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
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.geometry.Pos; 
import javafx.geometry.Insets;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.application.Platform;
import java.lang.Thread;
import javafx.stage.Modality;

/**
*GameGUI this will take care of most of the logic
*/
public class GameGUI2 extends Application{
	static ArrayList<Button> rewardButtons = new ArrayList<Button>();
	static int tierCounter = 0;
	static Player player;
	static Monster[] combatMonsters;
	static Monster[][][] monsterEncounters;
	static VBox playerPane = new VBox();
	static ArrayList<VBox> monsterPanes = new ArrayList<VBox>();
	static ArrayList<Button> cardButtons = new ArrayList<Button>();
	static int cardToUse;
	static Label descriptions= new Label("");
	static int potionToUse;
	static ArrayList<Button> potionButtons = new ArrayList<Button>();
	static ArrayList<Button> relicButtons = new ArrayList<Button>();
	static int turnCount;
	

	//Main method launches the gui.
	public static void main(String[] args)
	{
		launch(args);
	}
	
	/**
	*Method refreshVisuals
	*no parameters and returns nothing, it clears everything in the borderpane and removes dead monsters.
	*it then refreshes all the visuals.
	*/
	public static void refreshVisuals(){
		playerPane.getChildren().clear();
		for (VBox panes : monsterPanes){
			panes.getChildren().clear();
		}
		combatMonsters = removeDead(combatMonsters);
		
		//Player Pane.
		Image playerImage = new Image("RawCards/PlayerImage.png");
		ImageView playerImageView = new ImageView(playerImage);
		Label playerEnergy = new Label(player.getEnergy()+"/"+player.getMaxEnergy());
		playerPane.getChildren().add(playerEnergy);
		Button PlayerButton = new Button(player.getName(),playerImageView);
		playerPane.getChildren().add(PlayerButton);
		Label playerHP = new Label(player.getHealth()+"/"+player.getMaxHealth());
		playerPane.getChildren().add(playerHP);
		Label playerAttributes = new Label(getPlayerAttributes());
		playerPane.getChildren().add(playerAttributes);
		
		//monsterPanes.
		ArrayList<String> monsterAttributes = getMonsterAttributes();
		try{
			for (int j = 0; j < combatMonsters.length; j++){
				Label monsterIntentions = new Label(combatMonsters[j].intentions());
				monsterPanes.get(j).getChildren().add(monsterIntentions);
				
				Button MonsterButton = new Button(combatMonsters[j].getName());
				monsterPanes.get(j).getChildren().add(MonsterButton);
				
				MonsterTargetClick clickEvent = new MonsterTargetClick(j);
				MonsterButton.setOnAction(clickEvent);
				
				Label monsterHP = new Label(combatMonsters[j].getHealth()+"/"+combatMonsters[j].getMaxHealth());
				monsterPanes.get(j).getChildren().add(monsterHP);
				
				Label aMonsterAttributes = new Label(monsterAttributes.get(j));
				monsterPanes.get(j).getChildren().add(aMonsterAttributes);
			}
		}catch(Exception e){}
		
		//Hand.
		
		for(int i=0;i<player.getDeck().getHand().size();i++){
				String cardName = player.getDeck().getHand().get(i).getName();
				Image cardImage = new Image("RawCards/" + cardName + ".png");
				ImageView cardImageView = new ImageView(cardImage);
				cardButtons.get(i).setGraphic(cardImageView);
		}
		
		//Potions
		try{
			for(int j=0;j<player.getPotions().size();j++){
				String potionName = player.getPotions().get(j).getName();
				//Image cardImage = new Image("RawCards/" + cardName + ".png");
				//ImageView cardImageView = new ImageView(cardImage);
				potionButtons.get(j).setText(potionName);
				//cardButtons.get(i).setGraphic(cardImageView);
			}
		}catch(Exception e){}
		
		//Relics
		for(int i=0;i<player.getRelics().size();i++){
			String relicName = player.getRelics().get(i).getName();
			relicButtons.get(i).setText(relicName);
			relicButtons.get(i).setVisible(true);
		}
		
	}
	
	/**
	*Start method
	*@param stage
	*This is where the root and all children are constructed and added
	*/
    public void start(Stage stage) throws Exception
	{
		loadGame();
		refreshVisuals();
		BorderPane root = new BorderPane();
	//Top Pane elements
		Button endTurnButton = new Button("End Turn");
		EndButtonClick endClick = new EndButtonClick();
		endTurnButton.setOnAction(endClick);
		
		HBox potionInv = new HBox();
		int j = 0;
		for (Button potion : potionButtons){
			potionInv.getChildren().add(potion);
			PotionButtonClick clickEvent = new PotionButtonClick(j);
			potion.setOnAction(clickEvent);
			j++;
		}
		
		HBox relicInv = new HBox();
		int h = 0;
		for (Button relic : relicButtons){
			relicInv.getChildren().add(relic);
			RelicButtonClick clickEvent = new RelicButtonClick(h);
			relic.setOnAction(clickEvent);
			h++;
		}
		
		HBox topPane = new HBox();
		
		topPane.getChildren().add(endTurnButton);
		topPane.getChildren().add(potionInv);
		topPane.getChildren().add(relicInv);
		
		
		
	//Setting first background. We plan to have the background change after each battle.
		Image back1Image = new Image("RawCards/background1.png"); //Load image
		
		//Construct BackgroundImage, needed to construct Background.
		//The four nulls are parameters for repeating the image in x and y, changing image position, and size.
		BackgroundImage background1Image = new BackgroundImage(back1Image, null, null, null, null);

		Background firstBack = new Background(background1Image); //Construct background using 1 background image.
		root.setBackground(firstBack); //add to root
		
		HBox monsters = new HBox(); //This HBox stores the vboxes for each monster.
		
		for (VBox mPane : monsterPanes){
			monsters.getChildren().add(mPane);
		}  //Adds the array of VBoxes representing the monsters.
		
		HBox hand = new HBox();  //This HBox contains the 5 buttons representing the player's hand.
		int i = 0;
		
		//Adds the array of buttons that represents the player's hand.
		for (Button card : cardButtons){
			hand.getChildren().add(card);
			CardButtonClick clickEvent = new CardButtonClick(i);
			card.setOnAction(clickEvent);
			i++;
		}
		
		root.setTop(topPane);
		root.setLeft(playerPane);
		root.setRight(monsters);
		root.setBottom(hand);
		root.setCenter(descriptions);
		
		Scene scene = new Scene(root,1000,500);
		stage.setTitle("Slay the Spire");
		stage.setScene(scene);
		stage.show();

		gameLoop();
		refreshVisuals();
	}
	
	/**
	*Method gameLoop used to instantiate new Attributes to be used for one battle and playerTurn sets the monsters strategies
	*and it enables the buttons that represent the player's hand.
	*/
	public static void gameLoop(){
		player.startCombat(turnCount,combatMonsters);
		playerTurn();			
	}
	/**
	*loadGame method. uses CardsUtil.load to construct all cards, creates the different kinds of encounters,
	*sets the monsnters to be used for the current battle, creates the player, and adds and creates the buttons for monsters and cards in hand.
	*/
	public static void loadGame(){
		CardsUtil.load();
		monsterEncounters = getEncounters();
		Scanner in = new Scanner(System.in);
		combatMonsters = getEncounter(tierCounter,monsterEncounters);
		player = new Player(intro(in), 80, CardsUtil.get("Strike"), CardsUtil.get("Strike"), CardsUtil.get("Strike"), CardsUtil.get("Desperate Strike"),CardsUtil.randomP(), CardsUtil.randomP(), CardsUtil.randomP());
		player.addPotion(CardsUtil.get("Explosive Potion"));
		player.addRelic(CardsUtil.getRelic("Sunflower"));
		for(int i = 0; i<5;i++){
			cardButtons.add(new Button());
		}
		
		for(int i = 0; i<3; i++){
			potionButtons.add(new Button());
		}
		
		for(int i = 0; i<10; i++){
			relicButtons.add(new Button());
			relicButtons.get(i).setVisible(false);
		}
		
		for(Monster m : combatMonsters){
			monsterPanes.add(new VBox());
		}
		descriptions.setWrapText(true);
		
	}
	
	/**
	*Method monstersAlive returns a boolean. True if there is one or more enemies, false if there are no enemies.
	*@param Monster[] monsters, the list of monsters in the current battle.
	*@return boolean true or false based on if one or more monsters are alive.
	*/
	public static boolean monstersAlive(Monster[] monsters) {
		for (Monster m : monsters) {
			if (m.alive())
				return true;
		}
		return false;
	}
	

	/**
	 * Removes dead Monsters from the given array.
	 * @param monsters
	 * @return the original Monster array with any/all dead Monsters removed.
	 */
	private static Monster[] removeDead(Monster[] monsters) {
		for (int i = monsters.length - 1; i >= 0; i --) {
			if (!monsters[i].alive()) {
				Monster[] newMonsters = new Monster[monsters.length - 1];
				for (int j = 0; j < monsters.length; j ++) {
					if (j == i)
						continue;
					newMonsters[(j < i) ? j : j - 1] = monsters[j];
				}
				monsters = newMonsters;
			}
		}
		
		return monsters;
	}
	
	/**
	 * @param tier the tier to access from the given array.
	 * @param monsters tiered array of all possible encounters.
	 * @return a random encounter from the given tier.
	 */
	private static Monster[] getEncounter(int tier, Monster[][][] encounters) {
		return encounters[tier][(int) (Math.random() * encounters[tier].length)];
	}
	
	/**
	 * Initializes all types of Monsters, and creates and returns all possible encounters using said monsters.
	 * @return all possible encounters, in a tiered array(encounters[0][0] would return the tier 1 encounter {slime, slime, slime}).
	 */
	private static Monster[][][] getEncounters() {
		Monster acidSlime   = new Monster("Acid Slime", 21, CardsUtil.getMonsterMoveset("Acid Slime"));
		Monster spikeSlime   = new Monster("Spike Slime", 19, CardsUtil.getMonsterMoveset("Spike Slime"));
		
		Monster fungiBeast = new Monster("Fungi Beast", 26, CardsUtil.getMonsterMoveset("Fungi Beast"));
		
		Monster shelledParasite = new Monster("Shelled Parasite", 71, CardsUtil.getMonsterMoveset("Shelled Parasite"));
		shelledParasite.setStrategy("1,Monster Block,1");
		shelledParasite.getArmour().addStartModifier(6, -1);
		
		Monster snakePlant = new Monster("Snake Plant", 78, CardsUtil.getMonsterMoveset("Snake Plant"));
		snakePlant.getRegeneration().setModifyRate(0);
		snakePlant.getRegeneration().modifyVal(1);

		Monster jawWorm = new Monster("Jaw Worm", 44, CardsUtil.getMonsterMoveset("Jaw Worm"));

		Monster cultist = new Monster("Cultist", 48, CardsUtil.getMonsterMoveset("Cultist"));
		cultist.setStrategy("1,Monster Strength Power,1");

		Monster louse   = new Monster("Louse", 16, CardsUtil.getMonsterMoveset("Louse"));

		Monster sphericGuardian = new Monster("Spheric Guardian", 20, CardsUtil.getMonsterMoveset("Spheric Guardian"));
		sphericGuardian.block(30);
		sphericGuardian.getDexterity().modifyVal(2);
		sphericGuardian.getArmour().setModifyRate(0);
		sphericGuardian.setStrategy("1,Monster StBlock,1");

		Monster gremlinNob = new Monster("Gremlin Nob", 82, CardsUtil.getMonsterMoveset("Gremlin Nob"));
		
		Monster orbWalker = new Monster("Orb Walker", 91, CardsUtil.getMonsterMoveset("Orb Walker"));
		orbWalker.getStrength().addEndModifier(3, -1);

		Monster awakenedOne = new Monster("Awakened One", 300, CardsUtil.getMonsterMoveset("Awakened One"));
		awakenedOne.getStrength().addStartModifier(1, -1);
		awakenedOne.getRegeneration().setModifyRate(0);
		awakenedOne.getRegeneration().modifyVal(10);
		awakenedOne.setStrategy("1,Monster Awakened Special,1");
		
		Monster theChamp = new Monster("The Champ", 420, CardsUtil.getMonsterMoveset("The Champ"));
		theChamp.setStrategy("0.5,Monster Champ Anger,1");

		Monster[][] tier1 = new Monster[][] {{ new Monster(acidSlime), new Monster(acidSlime), new Monster(spikeSlime) }, { new Monster(cultist) }, { new Monster(louse), new Monster(louse) }, { new Monster(acidSlime), new Monster(spikeSlime) }, { new Monster(fungiBeast), new Monster(fungiBeast) }};
		Monster[][] tier2 = new Monster[][] {{ new Monster(sphericGuardian)}, { new Monster(jawWorm), new Monster(jawWorm) }, { new Monster(cultist), new Monster(jawWorm) }, { new Monster(sphericGuardian), new Monster(jawWorm) }};
		Monster[][] tier3 = new Monster[][] {{ new Monster(gremlinNob) }, { new Monster(orbWalker) }, { new Monster(shelledParasite) }, { new Monster(snakePlant) }};
		Monster[][] tier4 = new Monster[][] {{ new Monster(cultist), new Monster(cultist), new Monster(awakenedOne) }, { new Monster(theChamp) }};

		return new Monster[][][] {tier1, tier2, tier3, tier4};

	}


	/**
	 * Prints the game intro and returns the user's name.
	 * @param in this Scanner will be used to get the user's name.
	 * @return the user's name.
	 */
	public static String intro(Scanner in) {
		System.out.println("Please enter your name:");
		String name = in.nextLine();
		pressEnter(in, "Welcome, " + name + ", to Slay the Spire! In this brief demo, you will be challenged by enemies until you are defeated." + " Use your Cards to attack and defend against the monsters!");
		return name;
	}

	/**
	 * Runs the player's turn, and loops until the player ends their turn.
	 * @param player
	 * @param monster
	 */
	public static void playerTurn() {
		turnCount++;
		if (!player.alive()){
			descriptions.setText("You have died!NOOO! You were soo yooouung. *shakes fist into sky*.");
			Platform.exit();
		}
		for (Monster m : combatMonsters){
			m.setMove();
		}
		for (Button card : cardButtons){
			card.setDisable(false);
		}
		player.startTurn(turnCount,combatMonsters);
		
	}

	/**
	 * Prints out end turn messages and prompts the user to hit enter.
	 * @param in this Scanner will be used to take the player's input.
	 * @param player
	 * @param monster
	 */
	public static void endTurn() {
		if (monstersAlive(combatMonsters) && player.alive()) {
			
			player.endTurn();
			
			for (Monster m : combatMonsters) {
				m.startTurn();
			}
			combatMonsters = removeDead(combatMonsters);		
			
			String describeTurn = "";
			for (Monster m : combatMonsters ) {
				m.getMove().use(m, player);
				describeTurn += m.actionReport();
			}
			
			descriptions.setText(describeTurn);
			for (Monster m : combatMonsters) {
				m.endTurn();
			}
			combatMonsters = removeDead(combatMonsters);
		}else if (!player.alive()){
			descriptions.setText("You have died!NOOO! You were soo yooouung. *shakes fist into sky*.");
			System.out.println("You have died!NOOO! You were soo yooouung. *shakes fist into sky*.");
			Platform.exit();
		}else if (!monstersAlive(combatMonsters)){
			/*
			Card rewardCard1 = newCard();
			Card rewardCard2 = newCard();
			Card rewardCard3 = newCard();
			ArrayList<Card> rewardCards = new ArrayList<Card>();
			rewardCards.add(rewardCard1);
			rewardCards.add(rewardCard2);
			rewardCards.add(rewardCard3);
			Card rewardPotion = newPotion();
			Relic rewardRelic = newRelic(player);
			
			Button rewardButt1 = new Button(rewardCard1.getName());
			Button rewardButt2 = new Button(rewardCard2.getName());
			Button rewardButt3 = new Button(rewardCard3.getName());
			Button rewardButt4 = new Button(rewardPotion.getName());
			Button rewardButt5 = new Button(rewardRelic.getName());
			rewardButtons.add(rewardButt1);
			rewardButtons.add(rewardButt2);
			rewardButtons.add(rewardButt3);
			rewardButtons.add(rewardButt4);
			rewardButtons.add(rewardButt5);
			HandleRewardClick reward1Click = new HandleRewardClick(1);
			HandleRewardClick reward2Click = new HandleRewardClick(2);
			HandleRewardClick reward3Click = new HandleRewardClick(3);
			HandleRewardClick reward4Click = new HandleRewardClick(4);
			HandleRewardClick reward5Click = new HandleRewardClick(5);
			rewardButt1.setOnAction(reward1Click);
			rewardButt2.setOnAction(reward2Click);
			rewardButt3.setOnAction(reward3Click);
			rewardButt4.setOnAction(reward4Click);
			rewardButt5.setOnAction(reward5Click);
			VBox secondaryLayout = new VBox();
			secondaryLayout.getChildren().add(rewardButt1);
			secondaryLayout.getChildren().add(rewardButt2);
			secondaryLayout.getChildren().add(rewardButt3);
			secondaryLayout.getChildren().add(rewardButt4);
			secondaryLayout.getChildren().add(rewardButt5);
			Scene secondaryScene= new Scene(secondaryLayout);
			Stage rewardsWindow = new Stage();
			rewardsWindow.setTitle("Pick a card, and take your potion and relic.");
			rewardsWindow.setScene(secondaryScene);
			
			rewardsWindow.initModality(Modality.WINDOW_MODAL);
			rewardsWindow.initOwner(stage);
			
			rewardsWindow.show();
			*/
			Card rewardCard = newCard();
			if (rewardCard.getName().contains("Potion")){
				player.addPotion(rewardCard);
			}else{
				player.addCard(rewardCard);
			}
			descriptions.setText("You got a " + rewardCard.getName() + "!");
			player.endTurn();
			player.endCombat();
			tierCounter++;
			combatMonsters = getEncounter(tierCounter, monsterEncounters);
			player.startCombat(turnCount, combatMonsters);
			
		}
	}

	/**
	 * Prints if the monster or player won. Adds a random card to the player's deck if they won.
	 * @param in this Scanner will be used to prompt the user to hit enter.
	 * @param player
	 * @param monster
	 */
	public static void endCombat(Scanner in, Player player, Monster[] monster) {
		if (!player.alive())
			System.out.println("DEFEAT!");
		else {
			pressEnter(in, "Victory!");
			Card reward = newCard();
			pressEnter(in, "Your reward: " + reward.getName() + "\n" + reward.getDescription());
			player.addCard(reward);
		}
	}

	/**
	 * @return a random player card, cannot be Strike.
	 */
	private static Card newCard() {
		int chance = (int) (Math.random() * 10);
		Card c;
		if (chance < 7){
			c = CardsUtil.get("Strike");
			while(c.getName().equals("Strike") || c.getName().equals("Desperate Strike")){
				c = CardsUtil.randomP();
			}
		}else{
			c = newPotion();
		}
		return c;
	}

	/**
	 * Returns a random monster from a given array, with full health.
	 * @param monsters the monsters to choose from.
	 * @return a random monster, with restored health.
	 */
	public static Monster getNextMonster(Monster[] monsters) {
		Monster m = monsters[(int) (Math.random() * monsters.length)];
		m.heal(m.getMaxHealth());
		return m;
	}

	/**
	 * Prints the health, armour and various non-zero attributes of the given player and monster, as well as the energy of the player.
	 */
	public static String getPlayerAttributes() {
		ArrayList<String> attributeList = new ArrayList<String>(Arrays.asList("Strength", "Dexterity", "Weak", "Frail", "Vulnerable", "Regeneration",
			"Poison", "Constricted", "Armour"));

		String playerStatus = "";
		int i = 0;
		for (Attribute a : new Attribute[] {player.getStrength(), player.getDexterity(), player.getWeak(),player.getFrail(),
			player.getVulnerable(), player.getRegeneration(), player.getPoison(), player.getConstricted(), player.getArmour()}){
			if (a.getCurrentVal()!= 0){
				playerStatus += String.format("%s: %d", attributeList.get(i), a.getCurrentVal())+"\n";
			}
			i++;
		}
		return playerStatus;
	}
	
	/**
	*Method getMonsterAttributes returns a list of the status effects that each monster is currently affected by.
	*@return monsterAttributes, a list of the attributes that each monster currently has.
	*/
	public static ArrayList<String> getMonsterAttributes(){
		ArrayList<String> attributeList = new ArrayList<String>(Arrays.asList("Strength", "Dexterity", "Weak", "Frail", "Vulnerable", "Regeneration",
			"Poison", "Constricted", "Armour"));
			
		ArrayList<String> monsterAttributes = new ArrayList<String>();
		
		for (Monster m : combatMonsters) {
			String aMonsterAttribute = "";
			if (m.alive()) {
				int j = 0;
				for (Attribute a : new Attribute[] {m.getStrength(), m.getDexterity(), m.getWeak(),m.getFrail(), m.getVulnerable(), m.getRegeneration(),
				m.getPoison(), m.getConstricted(), m.getArmour()}){
					if (a.getCurrentVal()!= 0){
						aMonsterAttribute += String.format("%s: %d", attributeList.get(j), a.getCurrentVal())+"\n";
					}
					j++;
				}
			}
			monsterAttributes.add(aMonsterAttribute);
		}
		return  monsterAttributes;
	}


	/**
	* Prompts the user to press enter, accompanied by a given message.
	* @param in Scanner to use to get the user's input.
	* @param message the message to display before "Press enter to continue."
	*/
	public static void pressEnter(Scanner in, String message) {
		System.out.println(message + "\nPress enter to continue.");
		in.nextLine();
	}


	/**
	 * @return a random potion card
	 */
	private static Card newPotion() {
		Card potion;
		potion = CardsUtil.randomPotion();
		return potion;
	}
	
	/**
	 * @return a random relic
	 */	
	private static Relic newRelic(Player player) {
		Relic r = CardsUtil.randomRelic();
		String playerRelics = player.listRelics();
		int i = 0;
		while((playerRelics.contains(r.getName()))&&(i<20)){
			r = CardsUtil.randomRelic();
			i++;
		}
		return r;
	}

}