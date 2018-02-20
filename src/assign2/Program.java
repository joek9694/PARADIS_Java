// Peter Idestam-Almquist, 2018-02-20.

package assign2;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Program {
	// Static variables.
	private static final int NUM_THREADS = 4;
	private static final int NUM_ACCOUNTS = 6;
	private static final int FACTOR = 100000;
	private static final int TIMEOUT = 60; // Seconds;
	private static final int NUM_TRANSACTIONS = NUM_ACCOUNTS * FACTOR;
	private static Bank bank = new Bank();
	private static ExecutorService executor = Executors.newFixedThreadPool(NUM_THREADS);
	private static Integer[] accountIds = new Integer[NUM_ACCOUNTS];
	private static Transaction[] transactions = new Transaction[NUM_TRANSACTIONS];
	
	// Static methods.

	private static void initiate() {
		for (int i = 0; i < NUM_ACCOUNTS; i++) {
			accountIds[i] = bank.newAccount(1000);
		}
		
		Operation[] withdrawals = new Operation[NUM_ACCOUNTS];
		for (int i = 0; i < NUM_ACCOUNTS; i++) {
			withdrawals[i] = new Operation(accountIds[i], -100);;
		}
		
		Operation[] deposits = new Operation[NUM_ACCOUNTS];
		for (int i = 0; i < NUM_ACCOUNTS; i++) {
			deposits[i] = new Operation(accountIds[i], +100);;
		}
		
		for (int i = 0; i < NUM_TRANSACTIONS; i++) {
			transactions[i] = new Transaction(bank);
			transactions[i].add(withdrawals[i % NUM_ACCOUNTS]);
			transactions[i].add(deposits[(i + 1) % NUM_ACCOUNTS]);
		}
	}
	
	private static void runTest() {
		try {
			long time = System.nanoTime();
			for (int i = 0; i < NUM_TRANSACTIONS; i++) {
				executor.execute(transactions[i]);
			}
			executor.shutdown();
			boolean completed = executor.awaitTermination(TIMEOUT, TimeUnit.SECONDS);
			time = System.nanoTime() - time;
			
			System.out.println("Completed: " + completed);
			System.out.println("Time [ms]: " + time / 1000000);
			
			for (int i = 0; i < NUM_ACCOUNTS; i++) {
				int balance = bank.getAccountBalance(accountIds[i]);
				System.out.println("Account: " + accountIds[i] + "; Balance: " + balance);
			}
		}
		catch (Exception exception) {
			exception.printStackTrace();
		}
	}
	
	// Entry point.
	public static void main(String[] args) {
		initiate();
		runTest();
	}
}

