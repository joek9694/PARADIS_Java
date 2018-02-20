package assign1;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Factorizer implements Runnable{
	
	private int step, min;
	private long product, max, factor1, factor2;
	
	public Factorizer(long product, int threads) {
		this.product = product;
		this.step = threads;
		max = product;		// kanske effektiviseras till kvadratrot?
		min = 4;			// ? 4 är minsta intressanta ?
	}
	
	public void run() {			// ska va utan argument
		long number = min;
		while (number <= max) {
			if (product % number == 0) {
				factor1 = number;
				factor2 = product / factor1;
				System.out.println("factor1 =" + factor1 + ", factor2 = " + factor2);
				return;
			}
			number = number + step;
		}
		System.out.println ("No factorization possible");
	}

	
	public static void main(String[] args) {
		try {
			
			BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
			System.out.print("Input (product)>");
			String input = consoleReader.readLine();
			long product = Long.parseLong(input);
			
			
			Factorizer fac = new Factorizer(product, 1);
			fac.run();
			
		}catch(Exception exception) {
			System.out.println(exception);
		}
		
		
	}
}
