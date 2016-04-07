import javax.swing.JLabel;

public class ScoreCounterLabel extends TurnsTakenCounterLabel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7144363845887524044L;
	private int score =0;
	private String DESCRIPTION = "Score:";
	
	public ScoreCounterLabel()
	{
		super();
		reset();
	}
	
	public ScoreCounterLabel(int score)
	{
		reset();
		this.score += score;
	}
	
	//@SuppressWarnings("unused")
	
	public void update()
	{
		setText(DESCRIPTION + Integer.toString(this.score));
		setHorizontalTextPosition(JLabel.NORTH_EAST);
		
	}
	
	protected void addScore(int increment)
	{
		this.score += increment;
		update();
	}
	
	@Override
	public void reset()
	{
		this.score = 0;
		update();
	}
}
