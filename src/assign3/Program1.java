// Peter Idestam-Almquist, 2018-02-26.
// [Replace this comment with your own name.]

// [Do necessary modifications of this file.]

package assign3;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

// [You are welcome to add some import statements.]

public class Program1 {
	final static int NUM_WEBPAGES = 40;
	private static WebPage[] webPages = new WebPage[NUM_WEBPAGES];
	// [You are welcome to add some variables.]
	
	//------------------------------------------ PRODUCER -----------------------------------------------------//
	class Producer implements Runnable {
		private BlockingQueue<WebPage> block;
		private int id;
		
		public Producer(BlockingQueue<WebPage> block, int id) {
			this.block = block;
			this.id = id;
		}
		

		@Override
		public void run() {
			WebPage page = webPages[id];
			try {
				block.put(page);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	// ---------------------------------------------- CONSUMER ---------------------------------------------------//
	class Consumer implements Runnable {
		private BlockingQueue<WebPage> block;
		
		public Consumer(BlockingQueue<WebPage> block) {
			this.block = block;
		}
		
		@Override
		public void run() {
			
			try {
				WebPage page = block.take();
				page.download();
				page.analyze();
				page.categorize();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}

	}
	
	// ----------------------------------------------- PROGRAM -----------------------------------------------------//

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
	
	
	// ------------------------------------------------- MAIN ---------------------------------------------------------//
	public static void main(String[] args) {
		Program1 prog = new Program1();
		// Initialize the list of webpages.
		initialize();
		
		// Start timing.
		long start = System.nanoTime();
		
		ExecutorService exec = Executors.newFixedThreadPool(4);
		BlockingQueue<WebPage> block = new ArrayBlockingQueue<>(NUM_WEBPAGES);
		
		// [Do modify this sequential part of the ProgramTest.]
		
		for(int i = 0; i < NUM_WEBPAGES; i++) {
			exec.submit(prog.new Producer(block, i));
		}
		
		for(int i = 0; i < NUM_WEBPAGES; i++)
			exec.submit(prog.new Consumer(block));
		
		exec.shutdown();
		
		try {
			exec.awaitTermination(5, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		
		// Stop timing.
		long stop = System.nanoTime();

		// Present the result.
		presentResult();
		
		// Present the execution time.
		System.out.println("Execution time (seconds): " + (stop-start)/1.0E9);
	}
}
