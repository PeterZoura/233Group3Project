import java.util.*;
public class Card{
	int heal;
	int damage;
	int block;
	String name;
	String description;
	public Card(int heal, int damage, int block, String name, String description)
	{
		this.heal = heal;
		this.damage = damage;
		this.block = block;
		this.name = name;
		this.description = description;
	}

	public Card(int heal, int damage, int block, String name)
	{
		this.heal = heal;
		this.damage = damage;
		this.block = block;
		this.name = name;
		this.description = "";

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
		this(aCard.heal, aCard.damage, aCard.block, aCard.name, aCard.description);
	}

	public String getName()
	{
		return this.name;
	}
	public String getDescription()
	{
		return this.description;
	}
	/*
	public void use(Player player, Monster enemy)
	{
		if (this.heal>0)
		{
			player.heal(this.heal);
		}
		if (this.damage>0)
		{
			enemy.damage(this.damage);
		}
		if (this.block>0)
		{
			player.block(this.block);
		}
	}
	*/
	/*
	public static void main(String[] Args)
	{
		Card nCard = new Card(1,2,0,"Vampiric Touch");
		System.out.println(nCard.getName());
		System.out.println(nCard.getDescription());
	}
	*/
}
