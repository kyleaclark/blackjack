import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Stack;
import java.util.Collections;

public class Deck {
	
	private Scanner inputStream;

	private Stack<Card> deck = new Stack<Card>();
	
	private Card card;
	
	private int handSuit = 0;
	private int handRank = 0;
	private int cardsLeft = 0;
	private int userScore = 0;
	private int dealerScore = 0;
	
	private boolean[] userAce = new boolean[4];
	private boolean[] dealerAce = new boolean[4];

	public Deck(String deckFileName) 
	{		
		try
		{
		inputStream = new Scanner(new FileInputStream(deckFileName));
		}
		catch(FileNotFoundException e)
		{
			System.out.println("Problem opening files.");
			System.exit(0);
		}
		
		int cardCounter = 0;
		
		for(int i = 0; i < 4; i++){
			handSuit = inputStream.nextInt();
				
			for(int j = 0; j < 13; j++){
				handRank = inputStream.nextInt();
				deck.push(new Card(handSuit, handRank));
				cardCounter++;
			}		
		}	
		
		cardsLeft = 52;
				
	}
		
	public void shuffle()
	{		
		Collections.shuffle(deck);
	}
	
	public void deal()
	{
		handRank = 0;
		handSuit = 0;
		userScore = 0;
		dealerScore = 0;
		
		for(int i = 0; i < 4; i++)
		{
			userAce[i] = false;
			dealerAce[i] = false;
		}
	}
	
	public void userHit()
    {
		card = deck.pop();
		cardsLeft--;
		
		handRank = card.getRank();
		
		if(handRank == 1)
		{
			for(int i = 0; i < 4; i++)
			{
				if(userAce[i] = false)
				{
					userAce[i] = true;
					break;
				}
			}
		}
		
		setUserScore(handRank);
    }
	
	public void setUserScore(int handRank) 
    {
		if(handRank >= 10)
		{
			userScore+=10;
		}
		else if((handRank == 1) && (userScore < 11))
		{
			userScore+=11;
			
			for(int i = 0; i < 4; i++)
			{
				if(userAce[i] == false)
				{
					userAce[i] = true;
					break;
				}
			}
		}
		else
		{
			userScore+=handRank;
		}
		
		if(userScore > 21)
		{
			for(int j = 0; j < 4; j++)
			{
				if((userAce[j] == true) && (userScore > 21))
				{
					userScore+=10;
					userAce[j] = false;
				}
			}
		}	
    }
	
	public void dealerHit()
    {
		card = deck.pop();
		cardsLeft--;
		
		handRank = card.getRank();
		
		if(handRank == 1)
		{
			for(int i = 0; i < 4; i++)
			{
				if(dealerAce[i] = false)
				{
					dealerAce[i] = true;
					break;
				}
			}
		}
		
		setDealerScore(handRank);
    }
	
	public void setDealerScore(int handRank) 
    {
		if(handRank >= 10)
		{
			dealerScore+=10;
		}
		else if((handRank == 1) && (dealerScore < 11))
		{
			dealerScore+=11;
			
			for(int i = 0; i < 4; i++)
			{
				if(dealerAce[i] == false)
				{
					dealerAce[i] = true;
					break;
				}
			}
		}
		else
		{
			dealerScore+=handRank;
		}
		
		if(dealerScore > 21)
		{
			for(int j = 0; j < 4; j++)
			{
				if((dealerAce[j] == true) && (dealerScore > 21))
				{
					dealerScore+=10;
					dealerAce[j] = false;
				}
			}
		}	
    }
	
	public int getUserScore()
	{
		return userScore;
	}
	
	public int getDealerScore()
	{
		return dealerScore;
	}
	
	public int getCardsLeft()
	{
		return cardsLeft;
	}
	
	public String toString()
	{
		return card.toString();
	}
	
}