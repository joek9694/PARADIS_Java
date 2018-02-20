package test;

class HelloFromThread2 implements Runnable {
	public void run() {
		System.out.println("Hello from a thread!");
	}

	public static void main(String[] args) {
		HelloFromThread2 hello = new HelloFromThread2();
        Thread myThread = new Thread(hello);
		myThread.start();
    }
}
