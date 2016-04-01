import edu.rit.pj2.Loop;
import edu.rit.pj2.Task;

public class Fermat extends Task{
	
	//Main must throw exception and NOT implement System.Exit(1);
	public void main(String[] args) throws Exception{
		if (args.length < 1) usage();
		
		int number = Integer.parseInt(args[0]);
		
		parallelFor (0, number - 1) .exec (new Loop()
		{
            public void start()
			{
                 // One-time thread-local initialization (optional method)
            }
            public void run (int i)
            {
                 // Loop body code for iteration i (required method)
            }
             public void finish()
            {
                 // One-time thread-local finalization (optional method)
            }

		});
	}
	
	public void usage() throws Exception{
		System.err.println ("Usage: java pj2 Fermat <number>");
		throw new IllegalArgumentException();
	}
}