package assign1;

import java.util.concurrent.atomic.AtomicBoolean;

public class Factorizer implements Runnable{
	
	private final long product, max, start;
	private long factor1, factor2;
	private final int step, min;
	private AtomicBoolean flag;
	
	
	public Factorizer(long product, int step, int min, long start, AtomicBoolean flag) {
		this.product = product;
		this.step = step;
		this.min = min +2;
		this.flag = flag;
		this.start = start;
		this.max = (long)Math.ceil(Math.sqrt(product));
		
	}
	
	boolean isPrime(long n) {
	    //check if n is a multiple of 2
	    if (n%2==0) 
	    	return false;
	    
	    for(long i=3;i*i<=n;i+=2) { //if not, checks the odd numbers
	        if(n%i==0)
	            return false;
	    }
	    return true;
	}
	
	@Override
	public void run() {
		long number = min;
		while (number <= max) {
			if(flag.get()) {
				return;
			}
			if (product % number == 0 && isPrime(number)) {
				if(! flag.getAndSet(true)) {
					long stop = System.nanoTime();
					factor1 = number;
					factor2 = product / factor1;
					
					
					
					
					// OBS! isPrime utesluter 2,3 osv... Factorizer2 skriver ut korrekt
					
					if(factor1 > 1) {
						System.out.println("factor1 =" + factor1 + ", factor2 = " + factor2);
						System.out.println("Execution time (seconds): " + (stop - start) / 1.0E9);
					}else {
						System.out.println ("No factorization possible");
					}
					return;
				}
				
				return;
			}
			number = number + step;
		}
	}
	
	
	public static void main(String[] args) {
		try {
			long product = Long.parseLong(args[0]);
			int numOfThreads = Integer.parseInt(args[1]);

			long start = System.nanoTime();

			Thread[] threads = new Thread[numOfThreads];
			Factorizer[] factorizers = new Factorizer[numOfThreads];
			AtomicBoolean flag = new AtomicBoolean();

			for (int i = 0; i < numOfThreads; i++) {
				factorizers[i] = new Factorizer(product, numOfThreads, i, start, flag);
				threads[i] = new Thread(factorizers[i]);
			}

			for (int i = 0; i < numOfThreads; i++) {
				threads[i].start(); // starts run() in instance of Factorizer;
			}

		} catch (Exception exception) {
			System.out.println(exception);
			exception.printStackTrace();
		}

	}

}
