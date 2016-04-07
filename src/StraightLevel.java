import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class StraightLevel extends FlushLevel 
{
	protected StraightLevel(ScoreCounterLabel score,TurnsTakenCounterLabel validTurnTime, JFrame mainFrame)
	{
		super(score,validTurnTime, mainFrame);
		
	}
	
//	@Override
//	protected void makeDeck() {
//		// Creates a deck to fill the grid.  Each card appears twice in random places.
//		ImageIcon cardIcon[] = this.loadCardIcons();
//		ImageIcon backIcon = cardIcon[TotalCardsPerDeck];
//
//		// make an array of card numbers: 0, 0, 1, 1, 2, 2, ..., 7, 7
//		// duplicate the image in as many cards as the input imageClones
//		int totalCardsInGrid = getRowsPerGrid() * getCardsPerRow();
//		int totalUniqueCards = totalCardsInGrid/2;
//
//		// Generate one distinct random card number for each unique card	
//		int cardsToAdd[] = new int[totalCardsInGrid];
//		boolean cardChosen[] = new boolean[TotalCardsPerDeck];
//
//		int chosenCount = 0;
//		Random rand = new Random();
//		while (chosenCount < totalUniqueCards)
//		{
//			int nextCardNo = rand.nextInt(TotalCardsPerDeck);
//			if (!cardChosen[nextCardNo]) {
//				cardChosen[nextCardNo] = true;
//				cardsToAdd[2*chosenCount] = nextCardNo;
//				cardsToAdd[2*chosenCount + 1] = nextCardNo;
//				chosenCount++;
//			}
//		}
//
//		// randomize the order of the cards
//		this.randomizeIntArray(cardsToAdd);
//
//		// make each card object and add it to the game grid
//		for(int i = 0; i < cardsToAdd.length; i++)
//		{
//			// number of the card, randomized
//			int num = cardsToAdd[i];
//			// make the card object and add it to the panel
//			String rank = cardNames[num].substring(0, 1);
//			String suit = cardNames[num].substring(1, 2);
//			this.grid.add( new Card(this, cardIcon[num], backIcon, num, rank, suit));
//		}
//	}

	@Override
	protected boolean addToTurnedCardsBuffer(Card card) {
		// add the card to the list
		this.turnedCardsBuffer.add(card);
		
//		// there are two cards
//		if(this.turnedCardsBuffer.size() == getCardsToTurnUp()) 
//		{
//			// Were are turning up the last card
//			// record the player's turn
//			this.turnsTakenCounter.increment();
//			this.turnedCardsBuffer.clear();
//			// In easy mode nothing to be done here
//			
//
//		}
		if(this.turnedCardsBuffer.size() == getCardsToTurnUp())
		{
			// We are uncovering the last card in this turn
			// Record the player's turn
			this.turnsTakenCounter.increment();
			
			// get the other card (which was already turned up)
			Card otherCard1 = (Card) this.turnedCardsBuffer.get(0);
			Card otherCard2 = (Card) this.turnedCardsBuffer.get(1);
			Card otherCard3 = (Card) this.turnedCardsBuffer.get(2);
			Card otherCard4 = (Card) this.turnedCardsBuffer.get(3);
			Card otherCard5 = (Card) this.turnedCardsBuffer.get(4);
			
			//Logic needs to be adjusted for the Straight Level requirements
			if((card.sameSuit(otherCard1) 
					&& (card.sameSuit(otherCard2)) 
					&& (card.sameSuit(otherCard3)) 
					&& (card.sameSuit(otherCard4))
					&& (card.sameSuit(otherCard5)))){ 
				
				System.out.println("Entered the suit comparison if");
				// Three cards match, so remove them from the list (they will remain face up)
				this.turnedCardsBuffer.clear();
				
				//May need to fix the A,Q,K and J inputs since we converted them from string to int
				this.scoreLabel.addScore(1000 + 100 * maxRankInBuffer(this.grid));
				
				//if(!this.movesAvailable(this.grid))this.gameOver();//Needs the dialogue box
				
			}
			else 
			{
				// The cards do not match, so start the timer to turn them down
				this.turnDownTimer.start();
				//Subtracts the argument provided from the Score Label
				this.scoreLabel.addScore(-5);
			}
		
		}
		return true;
	}

	@Override
	protected boolean turnUp(Card card) {
		// the card may be turned
		if(this.turnedCardsBuffer.size() < 5) 
		{
			return this.addToTurnedCardsBuffer(card);
		}
		return false;
	}

	@Override
	protected String getMode() {
		// TODO Auto-generated method stub
		return "StraightMode";
	}
}
