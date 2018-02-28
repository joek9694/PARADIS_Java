package assign1;

public class Factorizer2 implements Runnable{
	
	private long product, factor1, factor2, max;
	private int step, min;
	private static volatile boolean flag = false;
	
	public Factorizer2(long product, int threads, int min) {
		this.product = product;
		step = threads;
		this.min = min + 2;
		max = (long)Math.ceil(Math.sqrt(product));
	}
	
	public long getF1() {
		return factor1;
	}
	public long getF2() {
		return factor2;
	}
	
	//just for tests
	@Override
	public String toString() {
		return "" + product + " " + step;
	}
	
	public static boolean isPrime(long n) {
        if (n <= 1) {
            return false;
        }
        if (n == 2) {
            return true;
        }
        if (n % 2 == 0) {
            return false;
        }
        for (long i = 3; i <= Math.sqrt(n) + 1; i = i + 2) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }

	
	public void run() {
		/*long number = 0;
		
		synchronized(lock) {
		 number = min++;
				}
		*/
		
		long number = min; 
		while (number <= max && !flag) {
			if (product % number == 0 && isPrime(number)) {
				factor1 = number;
				factor2 = product / factor1;
				flag = true;
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
			
			//System.out.println(product);
			//System.out.println(numOfThreads);
			
			Thread[] threads = new Thread[numOfThreads];
			Factorizer2[] factorizers = new Factorizer2[numOfThreads];
			
			for(int i = 0; i < numOfThreads; i++) {
				factorizers[i] = new Factorizer2(product, numOfThreads, i);
				threads[i] = new Thread(factorizers[i]);
			}
			
			
			for(int i = 0; i < numOfThreads; i++) {
				threads[i].start();	//fac.run();
			}
			
			long F1 = 0;
			long F2 = 0;
			
			for(int i = 0; i < numOfThreads; i++) {
				threads[i].join();
				
				//System.out.println(i + ": " + factorizers[i].getF1());
				//System.out.println(i + ": " + factorizers[i].getF2());
				
				if(factorizers[i].getF1() > 0) {	// chose smallest of possible calculations
					if(F1 == 0) {
						F1 = factorizers[i].getF1();
						F2 = factorizers[i].getF2();
						
					}else if (factorizers[i].getF1() < F1) {
						F1 = factorizers[i].getF1();
						F2 = factorizers[i].getF2();
					}
				}
			}
			
			long stop = System.nanoTime();
			
			if(F1 > 1 ) {
				System.out.println("factor1 =" + F1 + ", factor2 = " + F2);
				System.out.println("Execution time (seconds): " + (stop - start) / 1.0E9);
			}else {
				System.out.println ("No factorization possible");
			}
			
		}catch(Exception exception) {
			System.out.println(exception);
			exception.printStackTrace();
		}
	}
}
