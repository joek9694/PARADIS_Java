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
	
	static boolean isPrime(long n) {
	    if(n == 2 || n == 3)
	    	return true;
	    
	    if (n%2==0 || n < 2) 
	    	return false;
	    
	    for(long i=3; i*i<=n; i+=2) { //checks odd numbers
	        if(n%i==0)
	            return false;
	    }
	    return true;
	}
	
	@Override
	public void run() {
		long number = min;
		while (number <= max) {
			if (flag.get()) {		// flag == true == another thread was faster
				//System.out.println(min - 2 + "; 1st if");	//for testing
				return;
			}

			if (product % number == 0 && isPrime(number)) {
				
				if (!flag.getAndSet(true)) {	//commented by return
					long stop = System.nanoTime();
					factor1 = number;
					factor2 = product / factor1;

					if (factor1 > 1) {
						System.out.println("factor1 =" + factor1 + ", factor2 = " + factor2);
						System.out.println("Execution time (seconds): " + (stop - start) / 1.0E9);
					}
					
					return;
				}
				
				//System.out.println(min - 2 + "; 2nd if"); //for testing
				return;		// flag == true == another thread was faster
			}
			
			number = number + step;
		}
	}
	
	
	public static void main(String[] args) {
		try {
			long product = Long.parseLong(args[0]);
			int numOfThreads = Integer.parseInt(args[1]);
			
			if(product > 3) {
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
			}
			
			if(isPrime(product)) {
				System.out.println ("No factorization possible");
			}

		} catch (Exception exception) {
			System.out.println(exception);
			exception.printStackTrace();
		}
	}
}
