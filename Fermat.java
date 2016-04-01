import edu.rit.pj2.Loop;
import edu.rit.pj2.Task;
import edu.rit.pj2.vbl.IntVbl;
import java.math.BigInteger;

/**
 * @author Shane Hare
 * @project Project2 - Fermat.java
 *
 * Calculates the number of witnesses
 * for an given integer using the parallelFor
 * classes provided by the pj2 library. Print
 * out the number of witnesses on one line
 * at the end of the program.
 *
 */
public class Fermat extends Task{
	
	IntVbl counter = new IntVbl.Sum();
	int number;
	
	//Main must throw exception and NOT implement System.Exit(1);
	public void main(String[] args) throws Exception{
		if (args.length < 1) usage();
		
		number = verifyArg(args[0]);
		
		parallelFor (0, number - 1) .exec (new Loop()
		{
			IntVbl thrCounter;
			BigInteger a;
			BigInteger p;
			
			// One-time thread-local initialization (optional method)
            public void start()
			{
				thrCounter = threadLocal (counter);
            }
			
			// Loop body code for iteration i (required method)
            public void run (int i)
            {
				a = new BigInteger(Integer.toString(i));
				p = new BigInteger(Integer.toString(number));
				if (a.modPow(p, p).compareTo(a) != 0)
				{
					// a is a witness
					++ thrCounter.item;
				}				
            }
		});
		
		//Print number of Witnesses
		System.out.printf ("%d%n", counter.item);
	}
	
	/**
	 * Print the Usage statement on how the program works
	 * and throw an Exception to exit the pj2 program.
	 */	
	public void usage() throws Exception{
		System.err.println ("Usage: java pj2 Fermat <number>");
		throw new IllegalArgumentException();
	}
	
	/**
	 * Validates the parameters passed in from the user
	 * meet the specification requirements of a valid
	 * integer and that integer must be >= 3.
	 *
	 * @param arg  information passed in from arguments
	 * @return     the int provided by the user converted from a String
	 */
	public int verifyArg(String arg) throws Exception{
		int num = Integer.parseInt(arg);
		
		//Validate where <p> is the number of type int to be 
		//tested for compositeness; p must be 3 or greater. 
		if (num < 3){
			System.err.println ("Usage: <number> must be an integer >= 3");
			throw new IllegalArgumentException();
		}
		
		return num;
	}
}