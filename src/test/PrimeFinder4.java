package test;


//Peter Idestam-Almquist, 2017-01-13.
//Responsive console.

import java.util.List;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.InputStreamReader;

class PrimeFinder4 implements Runnable {
	private long min;
	private long max;
	private int step;
	private List<Long> primes = new ArrayList<Long>();
	
	PrimeFinder4(long min, long max, int step) {
		this.min = min;
		this.max = max;
		this.step = step;
	}
	
	// Inefficient implementation of isPrime on purpose.
	static boolean isPrime(long number) {
		boolean result = true;
		for (long denominator = 2; denominator < Math.sqrt(number); denominator++) {
			if (number % denominator == 0)
				result = false; 
		}
		return result;
	}
	
	public void run() {
		long start = System.nanoTime();
				
		for (long number = min; number <= max; number = number + step) {
			if (isPrime(number))
				primes.add(number);
		}

		long stop = System.nanoTime();

		// Output results.
		System.out.println("\nRange searched: " + min + " - " + max);
		System.out.println("Execution time (seconds): " + (stop-start)/1.0E9);
	}
	
	public static void main(String[] args) {
		try {
			// Read first input.
			BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
			System.out.print("Input (max)>");
			String input = consoleReader.readLine();
			
			// Repeat.
			while (!input.startsWith("quit")) {
				long max = Long.parseLong(input);
				
				// Create thread.
				PrimeFinder4 primeFinder = new PrimeFinder4(0, max, 1);
				Thread thread = new Thread(primeFinder);
				
				// Run thread.
				thread.start();

				// Read new input.
				System.out.print("Input (max)>");
				input = consoleReader.readLine();
			}
		}
		catch(Exception exception) {			
			System.out.println(exception);
		}
}
}


