import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class FlushLevel extends EqualPairLevel{

	
	protected FlushLevel(ScoreCounterLabel score,TurnsTakenCounterLabel validTurnTime, JFrame mainFrame) {
		super(score,validTurnTime, mainFrame);
		super.turnsTakenCounter.setDifficultyModeLabel("Flush Level");
		this.scoreLabel = score;
		this.turnsTakenCounter = validTurnTime;
		this.mainFrame = mainFrame;
		this.cardsToTurnUp = 5;
		this.cardsPerRow = 10;
		this.rowsPerGrid = 5;
	}

	@Override
	protected void makeDeck() {
		// In Trio level the grid consists of distinct cards, no repetitions
		ImageIcon cardIcon[] = this.loadCardIcons();

	//back card
		ImageIcon backIcon = cardIcon[TotalCardsPerDeck];

		int cardsToAdd[] = new int[this.getRowsPerGrid() * this.getCardsPerRow()];
		
		for(int i = 0; i < (this.getRowsPerGrid() * this.getCardsPerRow()); i++)
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
			Card otherCard3 = (Card) this.turnedCardsBuffer.get(2);
			Card otherCard4 = (Card) this.turnedCardsBuffer.get(3);
			Card otherCard5 = (Card) this.turnedCardsBuffer.get(4);
			
			if((card.sameSuit(otherCard1) && (card.sameSuit(otherCard2)) 
					&& (card.sameSuit(otherCard3))
					&& (card.sameSuit(otherCard4))
					&& (card.sameSuit(otherCard5)))) 
			{
				// Five cards match, so remove them from the list (they will remain face up)
				this.turnedCardsBuffer.clear();
				this.scoreLabel.addScore(700);//700 + sum of all ranks of cards
			}
			else 
			{
				// The cards do not match, so start the timer to turn them down
				this.turnDownTimer.start();
				this.scoreLabel.addScore(-5);
			}
		}
		return true;
	}
}