

public class Card {
	
	private int suit = 0;
	private int rank = 0;
	
	//APPLY STATIC VARIABLE NAME TO SUIT INT VALUE
	private final static int CLUBS = 0, 
    						 DIAMONDS = 1,
    						 HEARTS = 2,
    						 SPADES = 3;
	
	public Card(int suit, int rank) 
	{
		this.suit = suit;		
		this.rank = rank;
	}
	
	/* NEVER USED IN THE PROGRAM
	public int getSuit(){
		return this.suit;
	}*/
	
	public int getRank(){
		return this.rank;
	}
	
	public String getSuitAsString(int suit) {

		switch (suit) 
		{
			case CLUBS:    return "CLUBS";
			case DIAMONDS: return "DIAMONDS";
			case HEARTS:   return "HEARTS";
			case SPADES:   return "SPADES";
		    default:       return "INVALID SUIT";
		}
       
    }
    
    public String getRankAsString(int rank) {
    	switch (rank) {
    		case 1:   return "ACE";
    		case 2:   return "2";
    		case 3:   return "3";
    		case 4:   return "4";
    		case 5:   return "5";
    		case 6:   return "6";
    		case 7:   return "7";
    		case 8:   return "8";
    		case 9:   return "9";
    		case 10:  return "10";
    		case 11:  return "JACK";
    		case 12:  return "QUEEN";
    		case 13:  return "KING";
    		default:  return "INVALID RANK";
    	}
    }
	
	public String toString() {
        return getRankAsString(this.rank) + " of " + getSuitAsString(this.suit);
    }


}
