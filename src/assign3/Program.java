// Peter Idestam-Almquist, 2018-02-26.
// [Replace this comment with your own name.]

// [Do necessary modifications of this file.]

// instantieringen av Tasks är inte snygg... 

package assign3;

import java.util.concurrent.ArrayBlockingQueue;
// [You are welcome to add some import statements.]
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Program {
	final static int NUM_WEBPAGES = 40;
	private static WebPage[] webPages = new WebPage[NUM_WEBPAGES];
	private static Task[] tasks = new Task[NUM_WEBPAGES];
	// [You are welcome to add some variables.]
	private BlockingQueue<Task> block = new ArrayBlockingQueue<Task>(NUM_WEBPAGES); // The size should be reasonably
																					// easy to handle since NUM_WEBPAGES
																					// is 40
	private static int count = 0;

	private class Task {
		private WebPage page;
		private int num = 0; // Represents which task to be performed on a certain webpage.
		// Instead of separate blockingqueues for different tasks

		Task(WebPage page) {
			this.page = page;
		}

		int num() {
			return num;
		}

		void incr() {
			num++;
		}

		String getData() {
			return page.toString();
		}

		public void download() {
			page.download();
		}

		public void analyse() {
			page.analyze();
		}

		public void categorize() {
			page.categorize();
		}
	}

	private class Producer implements Runnable {
		private BlockingQueue<Task> block;

		Producer(BlockingQueue<Task> block) {
			this.block = block;
		}

		@Override
		public void run() {
			while (count < NUM_WEBPAGES) {
				try {
					Task task = produce();
					if (task != null)
						block.put(task);
					System.out.println("Produce.run()");
				} catch (Exception exc) {
					System.out.println(exc);
				}
			}
		}

		Task produce() {
			Task t = null;
			try {
				synchronized (this) {
					t = tasks[count];
					count++;
				}
			} catch (Exception exc) {
				System.out.println(exc);
			}
			return t;
		}

	}

	private class Consumer implements Runnable {
		private BlockingQueue<Task> block;

		Consumer(BlockingQueue<Task> block) {
			this.block = block;
		}

		@Override
		public void run() {
			while (true) { // osäker! FIXME	//(count != NUM_WEBPAGES) & !block.isEmpty()
				try {
					Task task = block.take();
					consume(task);
					//System.out.println("Consume.run()");
				} catch (Exception exc) {
					System.out.println(exc);
				}
			}
		}

		void consume(Task task) {
			try {
				int num = task.num();
				if (num < 3) { // Consumer & Producer

					switch (num) {
					case 0:
						task.download();
						task.incr();
						block.put(task);
						break;
					case 1:
						task.analyse();
						task.incr();
						block.put(task);
						break;
					case 2:
						task.categorize();
						task.incr();
						block.put(task);
						break;
					}
				} else { // Consumer
					System.out.println(task.getData());
				}
			} catch (Exception exc) {
				System.out.println(exc);
			}
		}// End of consume()
	}

	// [You are welcome to modify this method, but it should NOT be parallelized.]
	private static void initialize(Program prog) {
		for (int i = 0; i < NUM_WEBPAGES; i++) {
			webPages[i] = new WebPage("http://www.site.se/page" + i + ".html");
		}

		for (int i = 0; i < NUM_WEBPAGES; i++) {
			WebPage p = webPages[i];
			tasks[i] = prog.new Task(p);
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
		Program prog = new Program();
		initialize(prog);

		// Start timing.
		long start = System.nanoTime();

		ExecutorService executor = Executors.newFixedThreadPool(4); //FIXME
		executor.execute(prog.new Producer(prog.block));
		executor.execute(prog.new Consumer(prog.block));
		
		/*
		executor.shutdown();
		if(!executor.isShutdown()) {
			System.out.println("1");
			executor.shutdownNow();
		}

		else {
			System.out.println("2");
			executor.shutdownNow();
		}
		*/
			//executor.awaitTermination(5, TimeUnit.SECONDS);
			
		
		
		/*
		(new Thread(prog.new Producer(prog.block))).start();
		(new Thread(prog.new Producer(prog.block))).start();
		(new Thread(prog.new Producer(prog.block))).start();
		(new Thread(prog.new Producer(prog.block))).start();

		(new Thread(prog.new Consumer(prog.block))).start();
		(new Thread(prog.new Consumer(prog.block))).start();
		(new Thread(prog.new Consumer(prog.block))).start();
		(new Thread(prog.new Consumer(prog.block))).start();
		*/
		
		// Stop timing.
		long stop = System.nanoTime();

		// Present the result.
		presentResult();

		// Present the execution time.
		System.out.println("Execution time (seconds): " + (stop - start) / 1.0E9);
		executor.shutdownNow();
	}
}
