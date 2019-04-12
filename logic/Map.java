package logic;
import java.util.Scanner;
import java.math;

public class Map 
{
	private static int xPlayer;
	private static int yPlayer;
	private static int xMonster = 45;
	private static int yMonster = 5;
	private static int floor;
	
	private static String characterSprite;
	private static Scanner s2;

	//Select player 'sprite', and draws map.
	public static void mapStart()
	{
		System.out.println("Select your perfered sprite by typing the associated "
				+ "\n" + "number (ie. '1'). 1 (ง'̀-'́)ง ; 2 ಠ╭╮ಠ ; 3 ( ͡° ͜ʖ ͡°)");
		s2 = new Scanner(System.in);
		if (s2.hasNextInt())
		{
		int face = s2.nextInt();
		if ( face == 1)
		{
			characterSprite = "(ง'̀-'́)ง";
		}
		else if ( face == 2 )
		{
			characterSprite = "ಠ╭╮ಠ";
		}
		else if ( face == 3 )
		{
			characterSprite = "( ͡° ͜ʖ ͡°)";
		}
		else 
		{
			System.out.println("You discovered the secret face, (uWu)");
			characterSprite = "(uWu)";
		}
		drawMap();
		}
		else
		{
			System.out.println("You have to select a number!");
			mapStart();
		}
	}

	//Shows where the main character is located, tells player when their turn is.
	public static void drawMap()
	{
		//Top of the map (esthetic add on), and current floor number.
		if (floor == 0)
		{
			System.out.println("-------------------------------------------Entrance " );
		}
		else if (floor != 3)
		{
			System.out.println("------------------------------------------- Floor " + floor );
		}
		else
		{
			System.out.println("------------------------------------------Boss Battle" );
		}
		
		//Prints player and enemy
		for( int y = 0; y< 6; y++)
		{
			for( int x = 0; x< 50; x++ )
			{
				if ( y == yPlayer && x == xPlayer )
				{
					System.out.print(characterSprite);
				}
				System.out.print(" ");
				if (y == yMonster && x == xMonster )
				{
					System.out.print("ლ(ಠ益ಠლ)");
				}
			}
			System.out.println("");
		}
		//Bottom of the map (esthetic add on)
		System.out.println("----------------------------------------------------");
		System.out.println("Legend: " + characterSprite + " You ; ლ(ಠ益ಠლ) Enemy  "
				+ "\nRIGHT move to the right ; DOWN to move downwards"
				+ "\nLEFT move to the left   ; UP move upwards."
				+ "\nOnce the enemy is in front, charge and shout your battlecry!");
		playerMove();
	}

	//Prompts the player to move around the map. Then calls the drawMap() method.
	public static void playerMove()
	{
		System.out.print( "Your move:" );
		s2 = new Scanner(System.in);
		String move = s2.next();
		
		if ( (xMonster == xPlayer) && (yMonster == yPlayer))
		{
			System.out.println( move + "! You shout as you head into battle.");
			nextFloor();
			System.out.println( "Congratualtions on your victory, on to the next floor!");
			
		}
		else if ( move.equals("DOWN") || move.equals("D") )
		{
			if ( yPlayer + 1 <= 5)
			{
				yPlayer += 1;
drawMap();
			}
			else
			{
				System.out.println( move + " is out of bounds! Try again.");
drawMap();
			}
		}
		else if ( move.equals("UP") || move.equals("U") )
		{
			if ( yPlayer - 1 >= 0)
			{
				yPlayer -= 1;
drawMap();
			}
			else
			{
				System.out.println( move + " is out of bounds! Try again.");
drawMap();
			}
		}
		else if ( move.equals("RIGHT") || move.equals("R") )
		{
			if ( xPlayer + 5 <= 45)
			{
				xPlayer += 5;
drawMap();
			}
			else
			{
				System.out.println( move + " is out of bounds! Try again.");
drawMap();
			}
			
		}
		else if ( move.equals("LEFT") || move.equals("L") )
		{
			if ( xPlayer - 5 >= 0)
			{
				xPlayer -= 5;
drawMap();
			}
			else
			{
				System.out.println( move + " is out of bounds! Try again.");
drawMap();
			}
		}
		else 
		{
			System.out.println( move + " is not an option.");
			System.out.println( "Make sure you have the right spelling! Try again." );
drawMap();
		}
		
	}

	//Resets Positions
	public static void nextFloor()
	{
		xPlayer = 0;
		yPlayer = 0;
		xMonster = 45;
		yMonster = 5;
		floor++;
	}
	
	public static void main(String[] args) 
	{
		mapStart();
		 
	}
 
}
