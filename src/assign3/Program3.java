// Peter Idestam-Almquist, 2018-02-26.
// [Replace this comment with your own name.]

// [Do necessary modifications of this file.]

package assign3;	// OBS! paradis.assignment3		FIXME

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

// [You are welcome to add some import statements.]

public class Program3 {
	final static int NUM_WEBPAGES = 40;
	static int i = 0;
	private static WebPage[] webPages = new WebPage[NUM_WEBPAGES];
	private static CompletableFuture<WebPage>[] futures = new CompletableFuture[NUM_WEBPAGES];
	// [You are welcome to add some variables.]

	// [You are welcome to modify this method, but it should NOT be parallelized.]
	private static void initialize() {
		for (int i = 0; i < NUM_WEBPAGES; i++) {
			webPages[i] = new WebPage("http://www.site.se/page" + i + ".html");
		}
		
		//instantiera massa futures
		
	}
	
	private static WebPage download(WebPage p) {
		p.download();
		return p;
	}
	private static WebPage analyze(WebPage p) {
		p.analyze();
		return p;
	}
	private static WebPage categorize(WebPage p) {
		p.categorize();
		return p;
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
		
		CompletableFuture<WebPage> future1;
		CompletableFuture<WebPage> future2;
		CompletableFuture<WebPage> future3;
		CompletableFuture<Void> future4 = null;
		
		
		
		for(i = 0; i < NUM_WEBPAGES; i++) {
			future1 = CompletableFuture
					.supplyAsync(() -> webPages[i]);
					
			//CompletableFuture<Void> future2 =
			//		future1.thenApply(future1.get().download());
			future2 =
					future1.thenApply((x)-> download(x));
			future3 =
					future2.thenApply((x)-> analyze(x));
			future4 =
					future3.thenAccept((x)-> categorize(x));
					//.thenAccept(System.out.println());
			try {
				future4.get();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
		
		
		// Stop timing.
		long stop = System.nanoTime();

		// Present the result.
		presentResult();
		
		// Present the execution time.
		System.out.println("Execution time (seconds): " + (stop-start)/1.0E9);
	}
}
