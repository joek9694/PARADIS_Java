package test;

public class HelloFromThread1 extends Thread{
	public void run() {
		System.out.println("Hello from a thread !");
		
	}

	public static void main(String[] args) {
		HelloFromThread1 myThread = new HelloFromThread1();
		myThread.start();
	}
	
}
