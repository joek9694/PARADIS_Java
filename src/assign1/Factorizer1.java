package assign1;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/* Not functioning as supposed to, for now... */



public class Factorizer1 implements Runnable{
	
	static int threadCounter = 0;
	private int step, min;
	private long product, max, factor1, factor2;
	private boolean found = false;	//abort-serach-flag ...
	
	public Factorizer1(long product, int threads) {
		this.product = product;
		this.step = threads;
		this.max = product;		// kanske effektiviseras till kvadratrot?
		this.min = 4;			// ? 4 är minsta intressanta ?
		this.factor1 = 0;
		this.factor2 = 0;
	}
	
	public void run() {			// ska va utan argument
		synchronized(this) {
			long number = min + threadCounter; //  - threadCounter?
			while (number <= max) {
				if (product % number == 0) {
					found = true;
					this.factor1 = number;
					this.factor2 = product / this.factor1;
					return;
				}
				number = number + step;
			}
			threadCounter++;
		}
	}
	
	public long getF1(){
		return this.factor1;
	}
	
	public long getF2(){
		return this.factor2;
	}

	
	public static void main(String[] args) {
		try {
			
			BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
			System.out.print("Input (product)>");
			String input = consoleReader.readLine();
			System.out.println("input was: " + input);
			long product = Long.parseLong(input);
			System.out.print("Input (Number of threads)>");
			int numberOfThreads = Integer.parseInt(consoleReader.readLine());
			System.out.println("input was: " + numberOfThreads);
			long start = System.nanoTime();
			
			//Factorizer fac = new Factorizer(product, numberOfThreads);
			
			Thread[] threads = new Thread[numberOfThreads];
			Factorizer1[] factorizers = new Factorizer1[numberOfThreads];	//behövs?
			
			//loop skapa antal trådar enligt input
			for(int i = 0; i < numberOfThreads; i++) {
				factorizers[i] = new Factorizer1(product, numberOfThreads);
				threads[i] = new Thread(factorizers[i]); // på samma Factorizer.. borde va olika?
			}
			
			for(int i = 0; i < numberOfThreads; i++) {
				threads[i].start();	//fac.run();
			}
			
			long F1 = 0;
			long F2 = 0;
			
			for(int i = 0; i < numberOfThreads; i++) {
				threads[i].join();	// väntar på trådar!		FEL PLATS?
				System.out.println(i + ": " + factorizers[i].getF1());
				System.out.println(i + ": " + factorizers[i].getF2());
				 F1 = factorizers[i].getF1();
				 F2 = factorizers[i].getF2();
				
			}
			
			
			
			long stop = System.nanoTime();
			
			if(F1 > 3) {
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
