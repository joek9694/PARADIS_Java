package test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class Processor implements Runnable{
	
	private int id;
	
	public Processor(int id) {
		this.id = id;
	}
	
	@Override
	public void run() {
		System.out.println("Starting: " + id);
		
		try {
			Thread.sleep(2000);
			if(id == 12) {
				System.out.println("Im 12");
				Thread.sleep(5000);
			}
				
				
		}catch(InterruptedException e){
			System.out.println(e.getStackTrace());
		}
		
		System.out.println("Completed: " + id);
	}
	
}

public class ThreadPooler {
	
	public static void main(String[] args) {
		
		ExecutorService executor = Executors.newFixedThreadPool(4);
		
		
		for(int i = 0; i < 13; i++) {
			executor.submit(new Processor(i));
		}
		executor.shutdown();
		System.out.println("All tasks submitted.");
		
		try {
			executor.awaitTermination(4, TimeUnit.SECONDS);
		}catch(InterruptedException e) {
			System.out.println(e.getStackTrace());
		}
		
		System.out.println("All tasks completed.");
		
	}
}
