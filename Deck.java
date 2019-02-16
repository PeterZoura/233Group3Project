import java.util.*;
public class Deck
{
  private ArrayList<Card> deck = new ArrayList<Card>();
  private ArrayList<Card> discardPile= new ArrayList<Card>();
  private ArrayList<Card> drawPile = new ArrayList<Card>();
  private ArrayList<Card> hand = new ArrayList<Card>();


  // Add ONE card Methods
  public void addToDeck(Card aCard)
    {
      deck.add(new Card(aCard));
    }

  public void addToDiscardPile(Card aCard)
    {
      discardPile.add(new Card(aCard));
    }

  public void addToDrawPile(Card aCard)
    {
      drawPile.add(new Card(aCard));
    }

  public void addToHand(Card aCard)
    {
      hand.add(new Card(aCard));
    }



  //Add an ArrayList of cards
  public void addToDeck(List<Card> Cards)
    {
      deck.addAll(Cards);
    }
  public void addToDiscardPile(List<Card> Cards)
    {
      discardPile.addAll(Cards);
    }
  public void addToDrawPile(List<Card> Cards)
    {
      drawPile.addAll(Cards);
    }
  public void addToHand(List<Card> Cards)
    {
      hand.addAll(Cards);
    }



  //Utility Methods
  public Card drawFromHand(int cardIndex)
    {
      Card aCard = new Card(hand.get(cardIndex));
      discardPile.add(aCard);
      hand.remove(cardIndex);
      return aCard;
    }
  public Card drawFromDeck(int cardIndex)
    {
      Card aCard = new Card(deck.get(cardIndex));
      deck.remove(cardIndex);
      return aCard;
    }



/*
  public String toString()
  {
    StringBuilder builder = new StringBuilder();

    builder.append("____Deck");
      for (Card aCard : deck )
        {
          builder.append("\n");
          builder.append(aCard.getName());
        }
    builder.append("\n");


    builder.append("____DiscardPile");
      for (Card aCard : discardPile)
        {
        builder.append("\n");
        builder.append(aCard.getName());
        }
    builder.append("\n");


    builder.append("____Hand");
      for (Card aCard : hand)
        {
        builder.append("\n");
        builder.append(aCard.getName());
        }
    builder.append("\n");


    return builder.toString();
  }



  public static void main(String[] Args)
  {
    Card nCard = new Card(1,2,0,"Vampiric Touch");
    Card bCard = new Card(0,0,0,"Splash");
    Card cCard = new Card(0,1,0,"Poke");
    Card dCard = new Card(5,5,5,"Divine Slap","Wow such holiness");
    Card zCard = new Card(6,6,6,"Summoning");
		System.out.println(nCard.getName());
		System.out.println(nCard.getDescription());
    Deck aDeck = new Deck();

    List<Card> stack = Arrays.asList(nCard,bCard,cCard,dCard,zCard);

    aDeck.addToDeck(stack);
    aDeck.addToHand(stack);
    Card takenFromHand = new Card(aDeck.drawFromHand(1));
    Card takenFromDeck = new Card(aDeck.drawFromDeck(3));
    System.out.println(aDeck.toString());
*/

  }

}
