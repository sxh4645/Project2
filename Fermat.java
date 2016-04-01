import edu.rit.pj2.Loop;
import edu.rit.pj2.Task;
import edu.rit.pj2.vbl.IntVbl;
import java.math.BigInteger;

public class Fermat extends Task{
	
	IntVbl counter = new IntVbl.Sum();
	int number;
	
	//Main must throw exception and NOT implement System.Exit(1);
	public void main(String[] args) throws Exception{
		if (args.length < 1) usage();
		
		number = Integer.parseInt(args[0]);
		
		parallelFor (0, number - 1) .exec (new Loop()
		{
			IntVbl thrCounter;
			BigInteger a;
			BigInteger p;
			
            public void start()
			{
				thrCounter = threadLocal (counter);
				
                // One-time thread-local initialization (optional method)
            }
            public void run (int i)
            {
				a = new BigInteger(Integer.toString(i));
				p = new BigInteger(Integer.toString(number));
				if (a.modPow(p, p).compareTo(a) != 0)
				{
					// a is a witness
					++ thrCounter.item;
				}				
                // Loop body code for iteration i (required method)
            }
		});
		
		System.out.printf ("Counter = %d%n", counter.item);
	}
	
	public void usage() throws Exception{
		System.err.println ("Usage: java pj2 Fermat <number>");
		throw new IllegalArgumentException();
	}
}