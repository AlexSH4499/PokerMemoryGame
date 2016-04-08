//import java.util.Random;
//
//import javax.swing.ImageIcon;
//import javax.swing.JFrame;
//
//public class StraightLevel extends FlushLevel 
//{
//	protected StraightLevel(ScoreCounterLabel score,TurnsTakenCounterLabel validTurnTime, JFrame mainFrame)
//	{
//		super(score,validTurnTime, mainFrame);
//		
//	}
//
//	@Override
//	protected boolean addToTurnedCardsBuffer(Card card) {
//		// add the card to the list
//		this.turnedCardsBuffer.add(card);
//
//		if(this.turnedCardsBuffer.size() == getCardsToTurnUp())
//		{
//			// We are uncovering the last card in this turn
//			// Record the player's turn
//			this.turnsTakenCounter.increment();
//			
//			// get the other card (which was already turned up)
//			Card otherCard1 = (Card) this.turnedCardsBuffer.get(0);
//			Card otherCard2 = (Card) this.turnedCardsBuffer.get(1);
//			Card otherCard3 = (Card) this.turnedCardsBuffer.get(2);
//			Card otherCard4 = (Card) this.turnedCardsBuffer.get(3);
//			Card otherCard5 = (Card) this.turnedCardsBuffer.get(4);
//		
//			if()
//			else 
//			{
//				// The cards do not match, so start the timer to turn them down
//				this.turnDownTimer.start();
//				//Subtracts the argument provided from the Score Label
//				this.scoreLabel.addScore(-5);
//			}
//		
//		}
//		return true;
//	}
//
//	@Override
//	protected boolean turnUp(Card card) {
//		// the card may be turned
//		if(this.turnedCardsBuffer.size() < 5) 
//		{
//			return this.addToTurnedCardsBuffer(card);
//		}
//		return false;
//	}
//
//	@Override
//	protected String getMode() {
//		// TODO Auto-generated method stub
//		return "StraightMode";
//	}
//}
