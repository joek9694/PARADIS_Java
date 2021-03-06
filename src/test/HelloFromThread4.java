package test;

class HelloFromThread4 implements Runnable {
	public void run() {
		System.out.println("Sleeping ...");
		try {
			Thread.sleep(1000); //Thread sleeps for 1 s.
		}
		catch (InterruptedException e) { 
			System.out.println(e);
		}
		System.out.println("Hello from a thread!");
	}
	
	public static void main(String[] args) {
		HelloFromThread4 hello = new HelloFromThread4();
        Thread myThread = new Thread(hello);
		myThread.start();
		System.out.println("Main thread finished!");
	}
}
