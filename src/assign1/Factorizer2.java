package assign1;

@ThreadSafe
class Factorizer2 implements Runnable{
	private long product, factor1, factor2, max;
	private int min, step;
	
	public Factorizer2(long product, int numOfThreads) {
		this.product = product;
		this.step = numOfThreads;
		min = 4;
		max = product;
		factor1 = 0;
		factor2 = 0;
	}
	
	public void run() {
		
		//number = min + antalet startade tr�dar, f�r att l�ta olika tr�dar jobba p� olika nummer
		for(long number = min; number <= max; number = number + step) {
			synchronized(this){		// beh�vd?
				if (product % number == 0) {
					factor1 = number;
					factor2 = product / factor1;
					return;
				}
				number = number + step;
			}
		}
	}
	
	public static void main(String [] args) {
		
	}
}
