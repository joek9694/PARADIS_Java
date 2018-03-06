// Peter Idestam-Almquist, 2018-02-26.
// [Replace this comment with your own name.]

// [Do necessary modifications of this file.]

package assign3;

import java.util.stream.Stream;

// [You are welcome to add some import statements.]

public class Program2 {
	final static int NUM_WEBPAGES = 40;
	private static WebPage[] webPages = new WebPage[NUM_WEBPAGES];
	// [You are welcome to add some variables.]

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
		
		// [Do modify this sequential part of the ProgramTest.]

		Stream<WebPage> stream = Stream.of(webPages);
		stream.parallel();
		stream.forEach(x -> {x.download(); x.analyze(); x.categorize();});
		
		// Stop timing.
		long stop = System.nanoTime();

		// Present the result.
		presentResult();
		
		// Present the execution time.
		System.out.println("Execution time (seconds): " + (stop-start)/1.0E9);
	}
}
