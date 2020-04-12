package skunk;
import edu.princeton.cs.introcs.StdIn;
import edu.princeton.cs.introcs.StdOut;

public class SkunkUI implements UI
{

	public SkunkDomain skunkDomain;
	public SkunkBadEvents skunkBadEvents;

	public SkunkUI(SkunkDomain skunkDomain) {
		this.skunkDomain = skunkDomain;
	}
	
	public SkunkUI(SkunkBadEvents skunkDomain) {
		this.skunkBadEvents = skunkDomain;
	}
	
	public void setDomain(SkunkDomain skunkDomain)
	{
		this.skunkDomain = skunkDomain;
	}
	
	public void setBadDomain(SkunkBadEvents skunkBadEvents)
	{
		this.skunkBadEvents = skunkBadEvents;
	}

	@Override
	public String promptReadAndReturn(String question)
	{
		StdOut.print(question + " => ");
		String result = StdIn.readLine();
		return result;
	}

	public static void main(String[] args)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void print(String toPrint)
	{
		StdOut.print(toPrint);

	}

	@Override
	public void println(String toPrint)
	{
		StdOut.println(toPrint);

	}

}
