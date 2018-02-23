package assign1;

public class Factorizer3 implements Runnable{
	
	private long product, factor1, factor2, max, starttime;
	private int step, min;	//, count // just for tests 
	private static volatile boolean flag = false;
	
	public Factorizer3(long product, int threads, int min, long start) {
		this.product = product;
		step = threads;
		//count = min;		//just for tests
		this.min = min + 2;
		starttime = start;
		max = (long)Math.ceil(Math.sqrt(product));
	}
	

	
	//just for tests
	@Override
	public String toString() {
		return "" + product + " " + step;
		//return "Im: " + count;
	}
	
	public void run() {
		
		long number = min; 
		while (number <= max && !flag) {
			if (product % number == 0) {
				factor1 = number;
				factor2 = product / factor1;
				flag = true;
				this.printResult();
				return;
			}
			number = number + step;
		}
		//System.out.println("" + this + "; I went out of loop.");	//just for tests
	}
	
	private synchronized void printResult() {
		long stop = System.nanoTime();
		if(factor1 > 1) {
			System.out.println("factor1 =" + factor1 + ", factor2 = " + factor2);
			System.out.println("Execution time (seconds): " + (stop - starttime) / 1.0E9);
		}else {
			System.out.println ("No factorization possible");
		}
		
	}

	public static void main(String[] args) {
		try {
			
			long product = Long.parseLong(args[0]);
			int numOfThreads = Integer.parseInt(args[1]);
			
			long start = System.nanoTime();
			
			
			Thread[] threads = new Thread[numOfThreads];
			Factorizer3[] factorizers = new Factorizer3[numOfThreads];
			
			for(int i = 0; i < numOfThreads; i++) {
				factorizers[i] = new Factorizer3(product, numOfThreads, i, start);
				threads[i] = new Thread(factorizers[i]);
			}
			
			for(int i = 0; i < numOfThreads; i++) {
				threads[i].start();	//starts run() in instance of Factorizer2;
			}
			
			
			
			
		}catch(Exception exception) {
			System.out.println(exception);
			exception.printStackTrace();
		}
	}
}
