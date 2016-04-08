import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class RankTrioLevel extends EqualPairLevel {

	// TRIO LEVEL: The goal is to find, on each turn, three cards with the same rank

	protected RankTrioLevel(ScoreCounterLabel score,TurnsTakenCounterLabel validTurnTime, JFrame mainFrame) {
		super(score, validTurnTime, mainFrame);
		super.turnsTakenCounter.setDifficultyModeLabel("Trio Level");
		cardsToTurnUp = 3;
		cardsPerRow = 10;
		rowsPerGrid = 5;
	}

	@Override
	protected void makeDeck() {
		// In Trio level the grid consists of distinct cards, no repetitions
		ImageIcon cardIcon[] = this.loadCardIcons();

		//back card
		ImageIcon backIcon = cardIcon[TotalCardsPerDeck];

		int cardsToAdd[] = new int[getRowsPerGrid() * getCardsPerRow()];
		for(int i = 0; i < (getRowsPerGrid() * getCardsPerRow()); i++)
		{
			cardsToAdd[i] = i;
		}

		// randomize the order of the deck
		this.randomizeIntArray(cardsToAdd);

		// make each card object
		for(int i = 0; i < cardsToAdd.length; i++)
		{
			// number of the card, randomized
			int num = cardsToAdd[i];
			// make the card object and add it to the panel
			String rank = cardNames[num].substring(0, 1);
			String suit = cardNames[num].substring(1, 2);
			this.grid.add( new Card(this, cardIcon[num], backIcon, num, rank, suit));
		}
	}
	
	/*
	 * This method allows us to sort through a Card List
	 * While comparing n to n+1 and n+2.
	 * If the argument is true then the method 
	 * @ return is True
	 * @Param c is The ArrayList to be sorted
	 */
	@SuppressWarnings("unused")
	private boolean movesAvailable(ArrayList<Card> c) {
		
		// Our n's
		int i =1;
		int j =0;
		int k =2;
		for(Card cards: c)//we may change this back to a standard for loop, seems unnecessary as is
		{
			while(j< c.size())
			{

			//If there exists at least one trio
			//Then the return statement is true
			if(c.get(j).getRank().equalsIgnoreCase(c.get(i).getRank()) 
				&&	c.get(j).getRank().equalsIgnoreCase(c.get(k).getRank())	)
			
				{
					return true;
					
				}
				j++;
			}
			i+=2;
			k+=2;
		}
		return false;
	}


	@Override
	protected boolean addToTurnedCardsBuffer(Card card) {
		// add the card to the list
		this.turnedCardsBuffer.add(card);
		if(this.turnedCardsBuffer.size() == getCardsToTurnUp())
		{
			// We are uncovering the last card in this turn
			// Record the player's turn
			this.turnsTakenCounter.increment();
			
			// get the other card (which was already turned up)
			Card otherCard1 = (Card) this.turnedCardsBuffer.get(0);
			Card otherCard2 = (Card) this.turnedCardsBuffer.get(1);
			if((card.getRank().equals(otherCard1.getRank())) && (card.getRank().equals(otherCard2.getRank()))) {
				// Three cards match, so remove them from the list (they will remain face up)
				this.turnedCardsBuffer.clear();
				this.scoreLabel.addScore(100 + card.getNum() *3);//We need to fix this so we can multiply but card Rank not memory num
				
				//Will only be true when there are no possible combinations available
				//Still requires the pop-up dialogue
				if(!this.movesAvailable(this.grid)) this.gameOver();//we need to pass the corresponding deck
			}
			else 
			{
				// The cards do not match, so start the timer to turn them down
				this.turnDownTimer.start();
				//Decreases score when turn passes and the three cards didn't match
				this.scoreLabel.addScore(-5);
			}
		}
		return true;
	}
}
