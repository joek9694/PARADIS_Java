package test;

// Peter Idestam-Almquist, 2017-01-13.
// Single-threaded execution.


import java.util.List;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.InputStreamReader;

class PrimeFinder1 implements Runnable {
	private long min;
	private long max;
	private int step;
	private List<Long> primes = new ArrayList<Long>();
	
	PrimeFinder1(long min, long max, int step) {
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
		for (long number = min; number <= max; number = number + step) {
			if (isPrime(number))
				primes.add(number);
		}
	}
	
	public static void main(String[] args) {
		try {
			// Read input.
			BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
			System.out.print("Input (max)>");
			String input = consoleReader.readLine();
			long min = 0;
			long max = Long.parseLong(input);

			// Start timing.
			long start = System.nanoTime();

			// Run.
			PrimeFinder1 primeFinder = new PrimeFinder1(min, max, 1);
			primeFinder.run();
			
			// Stop timing.
			long stop = System.nanoTime();

			// Output results.
			System.out.println("Range searched: " + min + " - " + max);
			System.out.println("Execution time (seconds): " + (stop-start)/1.0E9);
		}
		catch(Exception exception) {			
			System.out.println(exception);
		}
    }
}