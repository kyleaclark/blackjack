import java.util.Scanner;

public class Dealer {

	private Deck myDeck; //OBJECT TO ACCESS THE DECK CLASS
	private String deckFileName; //FILE NAME TO READ IN DECK FROM DECK CLASS
	
	private boolean playHand; //LOOP GAME WHILE PLAYING
	
	private boolean usersTurn; //CHECK FOR USERS TURN
	private boolean dealersTurn; //CHECK FOR DEALERS TURN
	
	private int userInput; //USER INPUT FROM KEYBOARD
	
	private int cardCount; //NUMBER OF TOTAL CARDS IN A HAND
	private int handCount; //TRACKING TOTAL NUMBER OF HANDS
	
	private int userHandRank; //SCORE OF USERS CURRENT HAND
	private String userHandString; //STRING OF USERS CURRENT HAND
	
	private boolean userBust; //CHECK FOR USER BUST
	private boolean userBlackJack; //CHECK FOR USER BLACKJACK
	
	private int dealerHandRank; //SCORE OF DEALERS CURRENT HAND
	private String dealerOpenHandString; //STRING OF DEALERS FACE UP CARDS
	private String dealerHideHandString; //STRING OF DEALERS FACE UP & FACE DOWN CARDS
	
	private boolean dealerBust; //CHECK FOR DEALER BUST
	private boolean dealerBlackJack; //CHECK FOR DEALER BLACKJACK
	
	
	
	public Dealer(String deckFileName)
	{
		this.deckFileName = deckFileName;
		createDeck();
	}
	
	public void createDeck()
	{
		myDeck = new Deck(deckFileName);
	}
	
	public void play()
	{				
		System.out.println("WELCOME TO BLACKJACK");
		myDeck.shuffle();
		playHand = true;
		
		do
		{
			if(myDeck.getCardsLeft() < 15)
			{
				createDeck();
				System.out.println("Cards left: " + myDeck.getCardsLeft());
			}
			
			deal();
			checkUserHandRank();	
			
			if((checkForUserBlackJack() == true) || (checkForDealerBlackJack() == true))
			{
				usersTurn = false;
				dealersTurn = false;
			}
					
			do
			{					
				getUserInput();
				checkUserHandRank();
			}while(usersTurn == true);
			
			if(checkForDealerBlackJack() == true)
			{
				dealersTurn = false;
			}
			
			if((usersTurn == false) && (dealersTurn == true))
			{
				do
				{
					checkDealerHandRank();
				}while(dealersTurn == true);
			}
			
			checkHandWinner();
			
			checkUserPlayHand();
			
		}while(playHand == true);

	}
	
	public void deal()
	{
		handCount++;
		System.out.println("\nHAND #" + handCount + "\n");
		
		usersTurn = true; //SET HAND TO USERS TURN
		userHandRank = 0;
		userHandString = "";
		dealerHandRank = 0;
		dealerHideHandString = "";
		dealerOpenHandString = "";
		
		myDeck.deal();
		
		//DEAL USER CARD 1
		myDeck.userHit();
		userHandRank = myDeck.getUserScore();
		userHandString = myDeck.toString();
		
		//DEAL DEALER CARD 1
		myDeck.dealerHit();
		dealerHandRank = myDeck.getDealerScore();
		dealerHideHandString = myDeck.toString();
		
		//DEAL USER CARD 2
		myDeck.userHit();
		cardCount = 2;
		userHandRank = myDeck.getUserScore();
		userHandString = userHandString + ", " + myDeck.toString();
		System.out.println("YOUR HAND: " + userHandString + " (" + userHandRank + ")");
		
		//DEAL DEALER CARD 2
		myDeck.dealerHit();
		dealerHandRank = myDeck.getDealerScore();
		dealerHideHandString = dealerHideHandString + ", " + myDeck.toString();
		dealerOpenHandString = myDeck.toString();
		System.out.println("DEALER HAND: " + "FACE DOWN CARD, " + dealerOpenHandString  + "\n");		
	}
	
	public void getUserInput()
	{
		Scanner keyboard = new Scanner(System.in);
		
		if(cardCount == 2)
		{			
			do
			{
				System.out.print("ENTER 1 TO HIT, 2 TO STAY, 3 TO DOUBLE DOWN: ");
				userInput = keyboard.nextInt();
			}while((userInput != 1) && (userInput != 2) && (userInput != 3));
		
			if(userInput == 1)
			{
				System.out.println("\nHIT\n");
				myDeck.userHit();
				cardCount++;
				userHandRank = myDeck.getUserScore();
				userHandString = userHandString + ", " + myDeck.toString();
				System.out.println("YOUR HAND: " + userHandString + " (" + userHandRank + ")");
			}
			else if(userInput == 2)
			{
				System.out.println("\nSTAY\n");
				System.out.println("YOUR HAND: " + userHandString + " (" + userHandRank + ")");
				checkUserHandRank();
				usersTurn = false;
			}
			else if(userInput == 3)
			{
				System.out.println("\nDOUBLE DOWN\n");
				myDeck.userHit();
				cardCount++;
				userHandRank = myDeck.getUserScore();
				userHandString = userHandString + ", " + myDeck.toString();
				System.out.println("YOUR HAND: " + userHandString + " (" + userHandRank + ")");
				checkUserHandRank();
				usersTurn = false;
			}
		}
		else
		{
			do
			{
				System.out.println("DEALER HAND: " + "FACE DOWN CARD, " + dealerOpenHandString  + "\n");
				System.out.print("ENTER 1 TO HIT, 2 TO STAY: ");
				userInput = keyboard.nextInt();
			}while((userInput != 1) && (userInput != 2));
		
			if(userInput == 1)
			{
				System.out.println("\nHIT\n");
				myDeck.userHit();
				cardCount++;
				userHandRank = myDeck.getUserScore();
				userHandString = userHandString + ", " + myDeck.toString();
				System.out.println("YOUR HAND: " + userHandString + " (" + userHandRank + ")");
			}
			else if(userInput == 2)
			{
				System.out.println("\nSTAY\n");
				System.out.println("YOUR HAND: " + userHandString + " (" + userHandRank + ")");
				checkUserHandRank();
				usersTurn = false;
			}
		}
		
		userInput = 0;
	}
		
	public void checkUserHandRank()
	{
		if(userHandRank > 21)
		{
			//BUST
			usersTurn = false;
			dealersTurn = false;
			userBust = true;
		}
		else if(userHandRank == 21)
		{
			//BLACKJACK
			usersTurn = false;
			userBlackJack = true;
			dealersTurn = true;
		}		
		else
		{
			userBust = false;
			userBlackJack = false;
			dealersTurn = true;
		}		
	}
	
	public void checkDealerHandRank()
	{
		while(dealerHandRank < 17)
		{
			myDeck.dealerHit();
			dealerHandRank = myDeck.getDealerScore();
			dealerHideHandString = dealerHideHandString + ", " + myDeck.toString();
		}
		
		
		if(dealerHandRank > 21)
		{
			//BUST
			dealerBust = true;
			dealersTurn = false;
		}
		else if(dealerHandRank == 21)
		{
			//BLACKJACK
			dealerBlackJack = true;
			dealersTurn = false;
			
		}
		else
		{
			//17-20
			dealerBust = false;
			dealerBlackJack = false;
			dealersTurn = false;
		}
	
	}
	
	public void checkHandWinner()
	{		
		System.out.println("\nYOUR HAND: " + userHandString + " (" + userHandRank + ")");
		System.out.println("DEALER HAND: " + dealerHideHandString + " (" + dealerHandRank + ")\n");
		
		if(userBust == true)
		{
			System.out.println("HAND LOST: YOU BUSTED");
		}
		else if(dealerBust == true)
		{
			System.out.println("HAND WON: DEALER BUSTED");
		}
		else if((userBlackJack == true) && (dealerBlackJack == false))
		{
			System.out.println("HAND WON: YOU HAVE BLACKJACK");
		}
		else if((userBlackJack == false) && (dealerBlackJack == true))
		{
			System.out.println("HAND WON: YOU HAVE BLACKJACK");
		}
		else if(userHandRank > dealerHandRank)
		{
			System.out.println("HAND WON: YOU BEAT THE DEALER");
		}
		else if(userHandRank < dealerHandRank)
		{
			System.out.println("HAND LOST: THE DEALER BEAT YOU");
		}
		else if(userHandRank == dealerHandRank)
		{
			System.out.println("HAND PUSH: YOU TIED THE DEALER");
		}
	}
	
	public boolean checkForUserBlackJack()
	{
		if(userHandRank == 21)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public boolean checkForDealerBlackJack()
	{
		if(dealerHandRank == 21)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public void checkUserPlayHand()
	{
		boolean checkPlayHand = false; //CONTINUE LOOPING FOR INPUT UNTIL EQUALS TRUE
		
		do
		{
			System.out.print("\nDO YOU WANT TO PLAY ANOTHER HAND? ENTER YES OR NO: ");
			Scanner keyboard = new Scanner(System.in);
			String playHand = keyboard.next();			
			
			if(playHand.equalsIgnoreCase("yes"))
			{
				this.playHand = true; //PLAY AGAIN
				checkPlayHand = true; //END CHECKING TO PLAY AGAIN LOOP
			}
			else if(playHand.equalsIgnoreCase("no"))
			{
				System.exit(0); //END BLACKJACK
			}
		}while(checkPlayHand == false);
	}
	
	public static void main(String[] args)
	{
		Dealer myDealer = new Dealer("src/cards.txt");
		myDealer.play();
	}

}
