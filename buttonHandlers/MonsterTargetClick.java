package buttonHandlers;
import game.GameGUI;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import logic.Card;
import logic.Skill;
import logic.CardsUtil;

/**
This class handles a button click and outputs a message.
The handle method is invoked when the button is clicked.
*/
public class MonsterTargetClick extends GameGUI implements EventHandler<ActionEvent>
{
	int monsterIndex;

	public MonsterTargetClick(int mIndex)
	{
		this.monsterIndex = mIndex;
	}
	@Override
	public void handle(ActionEvent event){
	
		if (getCardToUse() >= 0){
			Card card = getPlayer().getDeck().getHand().get(getCardToUse());
			if ((card.getDamage()>0)){
				if (!card.requiresTarget()){
						card.use(getPlayer(),getCombatMonsters());
					}
					else{
						card.use(getPlayer(),getCombatMonsters()[monsterIndex]);
					}
					getCardButtons().get(getCardToUse()).setText("");
					getCardButtons().get(getCardToUse()).setVisible(false);
					setCardToUse(-1);	
			}
			else if(card instanceof Skill){
				Skill skillCard = (Skill) card;
				if("weak vulnerable poison frail".contains(skillCard.getAttribute())){
					if (!skillCard.requiresTarget()){
						skillCard.use(getPlayer(),getCombatMonsters());
					}
					else{
						skillCard.use(getPlayer(),getCombatMonsters()[monsterIndex]);
					}
					getCardButtons().get(getCardToUse()).setText("");
					getCardButtons().get(getCardToUse()).setVisible(false);
					setCardToUse(-1);					
				}
				else{
					getDescriptions().setText("Invalid target");
					setCardToUse(-1);				
				}
			}
			else {
				getDescriptions().setText("Invalid target");
				setCardToUse(-1);
			}
		}
		
		if (getPotionToUse() >= 0){
			Card potion = getPlayer().getPotions().get(getPotionToUse());
			if (potion.getDamage()>0){
				if (!potion.requiresTarget()){
					potion.use(getPlayer(),getCombatMonsters());
				}
				else{
					potion.use(getPlayer(),getCombatMonsters()[monsterIndex]);
				}
				getPlayer().removePotion(getPotionToUse());
				setPotionToUse(-1);
			}
			else if(potion instanceof Skill){
				Skill skillPotion = (Skill) potion;
				if("weak vulnerable poison frail".contains(skillPotion.getAttribute())){
					if (!skillPotion.requiresTarget()){
						skillPotion.use(getPlayer(),getCombatMonsters());
					}
					else{
						skillPotion.use(getPlayer(),getCombatMonsters()[monsterIndex]);
					}
					getPlayer().removePotion(getPotionToUse());
					setPotionToUse(-1);				
				}
				else{
					getDescriptions().setText("Invalid target");
					setPotionToUse(-1);				
				}
			}
			else {
				getDescriptions().setText("Invalid target");
				setPotionToUse(-1);
			}
		}
		GameGUI.refreshVisuals();
	}
}
