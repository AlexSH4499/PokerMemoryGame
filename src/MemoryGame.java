import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class MemoryGame implements ActionListener {

	public static boolean DEBUG = true;
	private JFrame mainFrame;					// top level window
	private Container mainContentPane;			// frame that holds card field and turn counter
	private TurnsTakenCounterLabel turnCounterLabel;
	private GameLevel difficulty;
	private ScoreCounterLabel scoreCounterLabel;

	/**
	 * Make a JMenuItem, associate an action command and listener, add to menu
	 */
	private static void newMenuItem(String text, JMenu menu, ActionListener listener)
	{
		JMenuItem newItem = new JMenuItem(text);
		newItem.setActionCommand(text);
		newItem.addActionListener(listener);
		menu.add(newItem);
	}

	/**
	 * Default constructor loads card images, makes window
	 * @throws IOException 
	 */
	public MemoryGame () throws IOException
	{

		// Make toplevel window
		this.mainFrame = new JFrame("Mr Fresh Memory Game");
		this.mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.mainFrame.setSize(800,700);
		this.mainContentPane = this.mainFrame.getContentPane();
		this.mainContentPane.setLayout(new BoxLayout(this.mainContentPane, BoxLayout.PAGE_AXIS));

		// Menu bar
		JMenuBar menuBar = new JMenuBar();
		this.mainFrame.setJMenuBar(menuBar);

		// Game menu
		JMenu gameMenu = new JMenu("Memory");
		menuBar.add(gameMenu);
		newMenuItem("Exit", gameMenu, this);

		// Difficulty menu
		JMenu difficultyMenu = new JMenu("New Game");
		menuBar.add(difficultyMenu);
		newMenuItem("Easy Level", difficultyMenu, this);
		newMenuItem("Equal Pair Level", difficultyMenu, this);
		newMenuItem("Same Rank Trio Level", difficultyMenu, this);
		newMenuItem("Flush Level",difficultyMenu,this);
		newMenuItem("Straight Level",difficultyMenu,this);
		newMenuItem("Combo Level",difficultyMenu,this);
		
		// Help menu
		JMenu helpMenu = new JMenu("Help");
		menuBar.add(helpMenu);
		newMenuItem("How To Play", helpMenu, this);
		newMenuItem("About", helpMenu, this);

		//this.leaderBoard = new ScoreLeaderBoard("EasyMode");
	}


	/**
	 * Handles menu events.  Necessary for implementing ActionListener.
	 *
	 * @param e object with information about the event
	 */
	public void actionPerformed(ActionEvent e)
	{
		dprintln("actionPerformed " + e.getActionCommand());
		try {
			if(e.getActionCommand().equals("Easy Level")) newGame("easy");
			else if(e.getActionCommand().equals("Equal Pair Level")) newGame("medium");
			else if(e.getActionCommand().equals("Same Rank Trio Level")) newGame("trio");
			else if(e.getActionCommand().equals("Flush Level")) newGame("Flush Level");
			else if(e.getActionCommand().equals("Straight Level"))newGame("Straight Level");
			else if(e.getActionCommand().equals("Combo Level"))newGame("Combo level");
			else if(e.getActionCommand().equals("How To Play")) showInstructions();
			else if(e.getActionCommand().equals("About")) showAbout();
			else if(e.getActionCommand().equals("Exit")) System.exit(0);
		} catch (IOException e2) {
			e2.printStackTrace(); throw new RuntimeException("IO ERROR");
		}
	}


	/**
	 * Prints debugging messages to the console
	 *
	 * @param message the string to print to the console
	 */
	static public void dprintln( String message )
	{
		if (DEBUG) System.out.println( message );
	}

	public JPanel showCardDeck()
	{
		// make the panel to hold all of the cards
		JPanel panel = new JPanel(new GridLayout(difficulty.getRowsPerGrid(),difficulty.getCardsPerRow()));

		// this set of cards must have their own manager
		this.difficulty.makeDeck();

		for(int i= 0; i<difficulty.getGrid().size();i++){
			panel.add(difficulty.getGrid().get(i));
		}
		return panel;
	}

	/**
	 * Prepares a new game (first game or non-first game)
	 * @throws IOException 
	 */
	public void newGame(String difficultyMode) throws IOException
	{
		// reset the turn counter & score counter to zero
		this.turnCounterLabel = new TurnsTakenCounterLabel();
		
		
		// make a new card field with cards, and add it to the window

		if(difficultyMode.equalsIgnoreCase("easy")) {
			this.scoreCounterLabel = new ScoreCounterLabel(5);
			this.difficulty = new EasyLevel(this.scoreCounterLabel,this.turnCounterLabel, this.mainFrame);
		
	
		}
		else if(difficultyMode.equalsIgnoreCase("medium")){
			this.scoreCounterLabel = new ScoreCounterLabel(10);
			this.difficulty = new EqualPairLevel(this.scoreCounterLabel,this.turnCounterLabel, this.mainFrame);
		
		}

		else if(difficultyMode.equalsIgnoreCase("trio")){
			this.scoreCounterLabel = new ScoreCounterLabel(15);
			this.difficulty = new RankTrioLevel(this.scoreCounterLabel,this.turnCounterLabel, this.mainFrame);
			
			
		}
		else if(difficultyMode.equalsIgnoreCase("Flush Level"))
		{
			this.scoreCounterLabel = new ScoreCounterLabel(20);
			this.difficulty = new FlushLevel(this.scoreCounterLabel,this.turnCounterLabel,this.mainFrame);
		
		}
//		else if(difficultyMode.equalsIgnoreCase("Straight Level"))
//		{
//			this.scoreCounterLabel = new ScoreCounterLabel(25);
//			this.difficulty = new StraightLevel(this.scoreCounterLabel,this.turnCounterLabel,this.mainFrame);
//		}
		
		else if(difficultyMode.equalsIgnoreCase("Combo Level"))
		{
			this.scoreCounterLabel = new ScoreCounterLabel(25);
			this.difficulty = new ComboLevel(this.scoreCounterLabel,this.turnCounterLabel,this.mainFrame);
		}
		
		

		else {
			throw new RuntimeException("Illegal Game Level Detected");
		}

		this.turnCounterLabel.reset();
		this.scoreCounterLabel.reset();
		
		// clear out the content pane (removes turn counter label, score label, and card field)
		this.mainContentPane.removeAll();

		this.mainContentPane.add(showCardDeck());

		// add the turn counter label back in again
		this.mainContentPane.add(this.turnCounterLabel);
		
		//add the points counter label back in again
		this.mainContentPane.add(this.scoreCounterLabel);

		// show the window (in case this is the first game)
		this.mainFrame.setVisible(true);
	}

	//Something may be wrong here
	public boolean gameOver() throws FileNotFoundException, InterruptedException{
		return difficulty.gameOver();
	}

	/**
	 * Shows an instructional dialog box to the user
	 */
	private void showInstructions()
	{
		dprintln("MemoryGame.showInstructions()");
		final String HOWTOPLAYTEXT = 
				"How To Play\r\n" +
						"\r\n" +
						"EQUAL PAIR Level\r\n"+
						"The game consists of 8 pairs of cards.  At the start of the game,\r\n"+
						"every card is face down.  The object is to find all the pairs and\r\n"+
						"turn them face up.\r\n"+
						"\r\n"+
						"Click on two cards to turn them face up. If the cards are the \r\n"+
						"same, then you have discovered a pair.  The pair will remain\r\n"+
						"turned up.  If the cards are different, they will flip back\r\n"+
						"over automatically after a short delay.  Continue flipping\r\n"+
						"cards until you have discovered all of the pairs.  The game\r\n"+
						"is won when all cards are face up.\r\n"+
						"\r\n"+
						"SAME RANK TRIO Level\r\n"+
						"The game consists of a grid of distinct cards.  At the start of the game,\r\n"+
						"every card is face down.  The object is to find all the trios \r\n"+
						"of cards with the same rank and turn them face up.\r\n"+
						"\r\n"+
						"Click on three cards to turn them face up. If the cards have the \r\n"+
						"same rank, then you have discovered a trio.  The trio will remain\r\n"+
						"turned up.  If the cards are different, they will flip back\r\n"+
						"over automatically after a short delay.  Continue flipping\r\n"+
						"cards until you have discovered all of the pairs.  The game\r\n"+
						"is won when all cards are face up.\r\n"+
						"\r\n"+
						"Each time you flip two cards up, the turn counter will\r\n"+
						"increase.  Try to win the game in the fewest number of turns!"+
						"\r\n"+
						"Flush Level\r\n"+
						"The game consists of a grid of distinct cards.  At the start of the game,\r\n"+
						"every card is face down.  The object is to find five cards having the same suit T \r\n"+
						"and turn them face up.\r\n"+
						"\r\n"+
						"Click on five cards to turn them face up. If the cards have the \r\n"+
						"same suit, then you have discovered a flush.  The flush will remain\r\n"+
						"turned up.  If the cards don't meet the condition, they will flip back\r\n"+
						"over automatically after a short delay.  Continue flipping\r\n"+
						"cards until you have discovered all of the pairs of cards.  The game\r\n"+
						"is won when all cards are face up.\r\n"+
						"\r\n"+
						"Each time you flip five cards up, the turn counter will\r\n"+
						"increase.  Try to win the game in the fewest number of turns!"+
						"\r\n"+
						"Straight Level\r\n"+
						"The game consists of a grid of distinct cards.  At the start of the game,\r\n"+
						"every card is face down.  The object is to find five cards in sequence  T \r\n"+
						"with at least two distinct suits and turn them face up.\r\n"+
						"\r\n"+
						"Click on five cards to turn them face up. If the cards are \r\n"+
						"in sequence and have at least 2 distinct suits, then you have discovered a straight. The straight will remain\r\n"+
						"turned up.  If the cards don't meet the condition, they will flip back\r\n"+
						"over automatically after a short delay.  Continue flipping\r\n"+
						"cards until you have discovered all of the pairs of cards.  The game\r\n"+
						"is won when all cards are face up.\r\n"+
						"\r\n"+
						"Each time you flip five cards up, the turn counter will\r\n"+
						"increase.  Try to win the game in the fewest number of turns!"+
						"\r\n"+
						"Combo Level\r\n"+
						"The game consists of a grid of distinct cards.  At the start of the game,\r\n"+
						"every card is face down.  The object is to find five cards with the same suit, in sequence  T \r\n"+
						"with at least two distinct suits or with at least two cards with the same rank,  and turn them face up.\r\n"+
						"\r\n"+
						"Click on five cards to turn them face up. If the cards have the same suit, then you have discovered a flush. \r\n"+
						"If the cards are in sequence and have at least two distinct suits,  \r\n"+
						"then you have discovered a straight. If you have at least two cards with the same rank, then you have discovered a unique.  \r\n"+
						"The straight, flush or unique will remain turned up.  If the cards don't meet the condition, they will flip back\r\n"+
						"over automatically after a short delay.  Continue flipping\r\n"+
						"cards until you have discovered all of the pairs of cards.  The game\r\n"+
						"is won when all cards are face up.\r\n"+
						"\r\n"+
						"Each time you flip five cards up, the turn counter will\r\n"+
						"increase.  Try to win the game in the fewest number of turns!"+
						"\r\n";
						

		JOptionPane.showMessageDialog(this.mainFrame, HOWTOPLAYTEXT
				, "How To Play", JOptionPane.PLAIN_MESSAGE);
	}

	/**
	 * Shows a dialog box with information about the program
	 */
	private void showAbout()
	{
		dprintln("MemoryGame.showAbout()");
		final String ABOUTTEXT = "Game Customized at UPRM. Originally written by Mike Leonhard."
				+ "/n"+ "Modified by Alejandro Rodriguez and Edgardo Hernandez.";

		JOptionPane.showMessageDialog(this.mainFrame, ABOUTTEXT
				, "About Memory Game", JOptionPane.PLAIN_MESSAGE);
	}


}





