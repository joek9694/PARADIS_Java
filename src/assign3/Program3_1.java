//Peter Idestam-Almquist, 2018-02-26.
//[Replace this comment with your own name.]

//[Do necessary modifications of this file.]

package assign3;	// OBS! paradis.assignment3		FIXME

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

//[You are welcome to add some import statements.]

public class Program3_1 {
	final static int NUM_WEBPAGES = 40;
	private static WebPage[] webPages = new WebPage[NUM_WEBPAGES];
	private static Task[] tasks = new Task[NUM_WEBPAGES];

	// [You are welcome to add some variables.]

	// [You are welcome to modify this method, but it should NOT be parallelized.]
	private static void initialize() {
		for (int i = 0; i < NUM_WEBPAGES; i++) {
			webPages[i] = new WebPage("http://www.site.se/page" + i + ".html");
		}	
		for (int i = 0; i < NUM_WEBPAGES; i++) {
			tasks[i] = new Task(webPages[i]);
		}
	}

	private class Task implements Callable{
		private WebPage page;
		private boolean down, anal, cate;
		
		Task(WebPage page){
			this.page = page;
			this.down = false;
			this.anal = false;
			this.cate = false;
		}

		@Override
		public Task call() throws Exception {
			if(!down) {
				page.download();
				down = true;
			}else if(!anal) {
				page.analyze();
				anal = true;
			}else if(!cate) {
				page.categorize();
				cate = true;
			}else {
				System.out.println("WHAT IS THIS???");
			}
			return this;
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
		
		// [Do modify this sequential part of the ProgramTest.]
		/*
		for(int i = 0; i < NUM_WEBPAGES; i++) {
			CompletableFuture<WebPage> future = futures[i];
		}
		*/
		
		CompletableFuture<Task> future1;
		CompletableFuture<Task> future2;
		CompletableFuture<Task> future3;
		CompletableFuture<Task> future4 = null;
		
		
		
		for(int i = 0; i < NUM_WEBPAGES; i++) {
			future1 = CompletableFuture
					.supplyAsync(() -> tasks[i]);
					
			future2 =
					future1.thenApply();
		
			
	}
		
		
		// Stop timing.
		long stop = System.nanoTime();

		// Present the result.
		presentResult();
		
		// Present the execution time.
		System.out.println("Execution time (seconds): " + (stop-start)/1.0E9);
	}
}
