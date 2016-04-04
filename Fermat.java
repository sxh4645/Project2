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
	
	boolean cont 	= true; 				//Used to gracefully exit
	IntVbl counter 	= new IntVbl.Sum();		//Count of Witnesses	
	int number;								//Argument passed in by user
	
	//Main must throw exception and NOT implement System.Exit(1);
	public void main(String[] args) throws Exception{
		if (args.length != 1) usage();
		
		//Do not continue if no argument found
		if (cont) number = verifyArg(args[0]);
		
		//Do not continue if argument is not a number
		if (cont) execute();
	}
	
	/**
	 * Will execute the following parallelFor thread for the
	 * Fermat's Little Theorem for the given number p.
	 */
	public void execute(){
		
		// 2 <= a <= p-1
		parallelFor (2, number - 1) .exec (new Loop()
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
	public void usage(){
		System.err.println ("Usage: java pj2 Fermat <number>");
		cont = false;
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
		
		int num = 0;
		
		try{
			num = Integer.parseInt(arg);
			
			//Validate where <p> is the number of type int to be 
			//tested for compositeness; p must be 3 or greater. 
			if (num < 3){
				System.err.println ("Usage: <number> must be an integer >= 3");
				cont = false;
			}	
		}
		catch(NumberFormatException ex){
			//Should only catch this for Integer.parseInt not being a number
			System.err.println ("Usage: <number> must be a valid integer");
			cont = false;
		}

		return num;
	}
}