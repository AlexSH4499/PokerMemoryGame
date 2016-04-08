import java.awt.Container;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ComboLevel extends GameLevel {

	// COMBO LEVEL: The goal is to find, on each turn, 5 cards with the same suit, different suits or .
	
	protected ComboLevel(ScoreCounterLabel score,TurnsTakenCounterLabel validTurnTime, JFrame mainFrame) {
		super(score, validTurnTime, 5 ,mainFrame);
		super.turnsTakenCounter.setDifficultyModeLabel("Combo Level");
		cardsToTurnUp = 5;
		cardsPerRow = 10;
		rowsPerGrid = 5;
		
//		JPanel panel = new JPanel();
//		
//		
//		JButton button = new JButton("PASS");
//		JButton button2 = new JButton("SUBMIT");
//		
//		button.setSize(200,250);
//		
//		panel.getBounds();
//		panel.add(button);
//		panel.add(button2);
		
//		this.mainFrame.add(panel);
		
		JOptionPane pane = new OptionDialog("");
		
	}
	


	@Override
	protected void makeDeck() {
		// In Flush level the grid consists of distinct cards, no repetitions
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

	@Override
	protected boolean addToTurnedCardsBuffer(Card card) {
		// add the card to the list
		this.turnedCardsBuffer.add(card);
		if(this.turnedCardsBuffer.size() == getCardsToTurnUp())
		{
			
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
				
				this.turnsTakenCounter.increment();
				// Three cards match, so remove them from the list (they will remain face up)
				this.turnedCardsBuffer.clear();
				
				//May need to fix the A,Q,K and J inputs since we converted them from string to int
				this.scoreLabel.addScore(700 + addAllCardsInBuffer(this.turnedCardsBuffer));//We need to fix this so we can multiply but card Rank not memory num
				if(!this.movesAvailable(this.grid))this.gameOver();//Needs the dialogue box
				
			}
			else if((card.sameRank(otherCard1) 
					|| (card.sameRank(otherCard2)) 
					|| (card.sameRank(otherCard3)) 
					|| (card.sameRank(otherCard4))
					|| (card.sameRank(otherCard5)))){
				
				this.turnsTakenCounter.increment();
				// Five cards match, so remove them from the list (they will remain face up)
				this.turnedCardsBuffer.clear();
				//returns wrong score
				this.scoreLabel.addScore(700 + addAllCardsInBuffer(this.turnedCardsBuffer));//We need to fix this so we can multiply but card Rank not memory num
				}
				if(!this.movesAvailable(this.grid))this.gameOver();//Needs the dialogue box
			
				
			else 
			{
				this.turnsTakenCounter.increment();
				// The cards do not match, so start the timer to turn them down
				this.turnDownTimer.start();
				//Subtracts the argument provided from the Score Label
				this.scoreLabel.addScore(-5);
			}
		
		
			}
		return true;
	}

	private int addAllCardsInBuffer(Vector<Card> turnedCardsBuffer)
	{
		int sum =0;
		for(Card cards: turnedCardsBuffer)
		{
			sum += Integer.parseInt(cards.getRank());
		}
		return sum;
	}
	@Override
	protected boolean turnUp(Card card) {
		// the card may be turned
				if(this.turnedCardsBuffer.size() < getCardsToTurnUp()) 
				{
					return this.addToTurnedCardsBuffer(card);
				}
				// there are already the number of EasyMode (5 face up cards) in the turnedCardsBuffer
				return false;
	}
	
	/*
	 * This method allows us to sort through a Card List
	 * While comparing n to n+1, n+2,n+3 ,and n+4.
	 * If the argument is true then the method 
	 * @ return is True
	 * @Param c is The ArrayList to be sorted
	 */
	@SuppressWarnings("unused")
	private boolean movesAvailable(ArrayList<Card> c) {
		
		// Our n's
		
		int j =0;
		int i =1;
		int k =2;
		int m =3;
		int n =4;
		for(int l =0 ; l < c.size(); l++)
		{
			while(j< c.size())
			{

			//If there exists at least one flush
			//Then the return statement is true
			if(	   c.get(j).getSuit().equalsIgnoreCase(c.get(i).getSuit()) 
				&& c.get(j).getSuit().equalsIgnoreCase(c.get(k).getSuit())
				&& c.get(j).getSuit().equalsIgnoreCase(c.get(m).getSuit())
				&& c.get(j).getSuit().equalsIgnoreCase(c.get(n).getSuit()))
				{
					return true;
					
				}
				j++;
			}
			i+=4;
			k+=4;
			m+=4;
			n+=4;
		}
		return false;
	}

	@Override
	protected String getMode() {
		// TODO Auto-generated method stub
		return "ComboMode";
	}
}
