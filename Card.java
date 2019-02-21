import java.util.*;
public class Card{
	private int heal;
	private int damage;
	private int block;
	private int cost;
	private String name;
	private String description;

// Constructors
	public Card(int heal, int damage, int block, String name, String description)
	{
		this.heal = heal;
		this.damage = damage;
		this.block = block;
		this.name = name;
		this.description = description;
	}
	
	public Card(int heal, int damage, int block, int cost, String name)
	{
		this.heal = heal;
		this.damage = damage;
		this.block = block;
		this.cost = cost;
		this.name = name;
		this.description = String.format("Costs %d mana. ", cost);
		if (heal>0)
		{
			description = description + String.format("Heal %d health to yourself. ", this.heal);
		}
		
		if (damage>0)
		{
			description = description + String.format("Deal %d damage to an enemy. ", this.damage);
		}
		
		if (block>0)
		{
			description = description + String.format("Block %d damage for the next turn. ", this.block);
		}
		
		if (description.equals(""))
		{
			description = "This card does nothing";
		}
	}
	
	public Card(Card aCard)
	{
		this(aCard.getHeal(), aCard.getDamage(), aCard.getBlock(), aCard.getCost(), aCard.getName(), aCard.getDescription());
	}
	
// Getter Methods
	public String getName()
	{
		return this.name;
	}
	
	public String getDescription()
	{
		return this.description;
	}
	
	public int getDamage()
	{
		return this.damage;
	}
	
	public int getHeal()
	{
		return this.heal;
	}
	
	public int getBlock()
	{
		return this.armour;
	}
	
	public int getCost()
	{
		return this.cost;
	}
	
// Utility Methods
	// Returns true if user has enough energy, returns false if user does not.
	public boolean use(Entity user, Entity target)
	{
		if (user.energy >= this.cost)
		{
			if (this.heal>0)
			{
				user.setHealth(user.getHealth()+this.heal);
			}
			if (this.damage>0)
			{
				target.setHealth(user.getHealth()-this.damage);
			}
			if (this.block>0)
			{
				user.setArmour(user.getArmour()+this.block);
			}
			user.setEnergy(user.getEnergy()-this.cost);
			return true;
		}
		else
		{
			return false;
		}
	}

// Test Main Function (not to be used during demo 1
	/*
	public static void main(String[] Args)
	{
		Card nCard = new Card(1,2,0,"Vampiric Touch");
		Card card2 = new Card(nCard);
		System.out.println(card2.getName());
		System.out.println(card2.getDescription());
	}
	*/
}