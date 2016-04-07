import javax.swing.JFrame;

public class ComboLevel extends StraightLevel{

		protected ComboLevel(ScoreCounterLabel score,TurnsTakenCounterLabel validTurnTime, JFrame mainFrame)
		{
			super(score,validTurnTime, mainFrame);
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
