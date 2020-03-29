package skunk;
import edu.princeton.cs.introcs.StdOut;
import edu.princeton.cs.introcs.StdIn;

public class SkunkUI implements UI
{

	public SkunkDomain skunkDomain;

	public SkunkUI(SkunkDomain skunkDomain) {
		this.skunkDomain = skunkDomain;
	}
	
	public void setDomain(SkunkDomain skunkDomain)
	{
		this.skunkDomain = skunkDomain;
	}

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

	public void print(String toPrint)
	{
		StdOut.print(toPrint);

	}

	public void println(String toPrint)
	{
		StdOut.println(toPrint);

	}

}
