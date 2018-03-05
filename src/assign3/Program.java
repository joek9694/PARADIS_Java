// Peter Idestam-Almquist, 2018-02-26.
// [Replace this comment with your own name.]

// [Do necessary modifications of this file.]

package assign3;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
// [You are welcome to add some import statements.]
import java.util.concurrent.BlockingQueue;

public class Program {
	final static int NUM_WEBPAGES = 40;
	private static WebPage[] webPages = new WebPage[NUM_WEBPAGES];
	// [You are welcome to add some variables.]
	private BlockingQueue<WebPage> block = new ArrayBlockingQueue<WebPage>(NUM_WEBPAGES);	//The size should be reasonably easy to handle since NUM_WEBPAGES is 40
	
	
	private class Producer implements Runnable {
		private BlockingQueue<WebPage> block;
		
		Producer(BlockingQueue<WebPage> block){
			this.block = block;
		}
		
		@Override
		public void run() {
			while(true) {
				try {
					Task task = produce();
					block.put(task);
				}catch(Exception exc) {
					System.out.println(exc);
				}
				
			}
			
		}
		
		Task produce() {
			Random random = new Random();
			try {
				Thread.sleep(random.nextInt(1000));
			}catch(Exception exc) {
				System.out.println(exc);
			}
			return new Task("Hello!");
		}
		
		
	}
	
	private class Consumer implements Runnable{
		private BlockingQueue<WebPage> block;
		private Random random = new Random();
		
		Consumer(BlockingQueue<WebPage> block){
			this.block = block;
		}

		@Override
		public void run() {
			while(true) {
				try {
					WebPage page = block.take();
					consume(page);
				}catch(Exceptino exc) {
					System.out.println(exc);
				}
			}
		}
		
		void consume(WebPage page){
			try {
				Thread.sleep(random.nextInt(1000));
			}catch(Exception exc) {
				System.out.println(exc);
			}
			System.out.println(task.getData());
		}
		
	}

	// [You are welcome to modify this method, but it should NOT be parallelized.]
	private static void initialize() {
		for (int i = 0; i < NUM_WEBPAGES; i++) {
			webPages[i] = new WebPage("http://www.site.se/page" + i + ".html");
		}
	}
	
	// [You are welcome to modify this method, but it should NOT be parallelized.]
	private static void presentResult() {
		for (int i = 0; i < NUM_WEBPAGES; i++) {
			System.out.println(webPages[i]);
		}
	}
	
	public static void main(String[] args) {
		// Initialize the list of webpages.
		initialize();
		
		// Start timing.
		long start = System.nanoTime();
		
		//Executors.newFixedThreadPool(4)
		
		// [Do modify this sequential part of the program.]
		for (int i = 0; i < NUM_WEBPAGES; i++)
			webPages[i].download();
		
		for (int i = 0; i < NUM_WEBPAGES; i++)
			webPages[i].analyze();
		
		
		for (int i = 0; i < NUM_WEBPAGES; i++)
			webPages[i].categorize();
		
		// Stop timing.
		long stop = System.nanoTime();

		// Present the result.
		presentResult();
		
		// Present the execution time.
		System.out.println("Execution time (seconds): " + (stop-start)/1.0E9);
	}
}
