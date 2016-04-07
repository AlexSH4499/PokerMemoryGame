import javax.swing.JLabel;

public class ScoreCounterLabel extends TurnsTakenCounterLabel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7144363845887524044L;
	private long score =0;
	private String DESCRIPTION;
	
	public ScoreCounterLabel()
	{
		super.reset();
	}
	
	public ScoreCounterLabel(long score)
	{
		super.reset();
		this.score += score;
	}
	
	//@SuppressWarnings("unused")
	private void update()
	{
		setText(DESCRIPTION + Long.toString(this.score));
		setHorizontalTextPosition(JLabel.RIGHT);
		
	}
	
	protected void addScore(long increment)
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
