package assign2;

// By: joek9694 - Johan Eklundh

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

// TODO: Make this class thread-safe and as performant as possible.
@ThreadSafe()
class Bank {
	// Instance variables.
	@GuardedBy("idLock")
	private int accountCounter = 0;
	
	// ConcurrentHashMap so that no race conditions towards put or get could occur.
	private ConcurrentHashMap<Integer, Account> accounts = new ConcurrentHashMap<Integer, Account>();
	
	private Object idLock = new Object();	//Guarding accountCounter
	
	// Instance methods.
	int newAccount(int balance) {
		int accountId;
		synchronized (idLock){accountId = accountCounter++;}	//Synchronized so that 
		//no risk of multiple threads accessing value of accountCounter at close to the same time whereby adding
		// twice would result in +1 instead of +2 and also that one of the threads would have the wrong value.
		Account account = new Account(accountId, balance);
		accounts.put(accountId, account);	//Is synchronized through the locking mechanism of ConcurrentHashMap
		return accountId;
	}
	
	int getAccountBalance(int accountId) {
		Account acc = accounts.get(accountId);
		synchronized(acc) {	// synchronized so that, no two threads could try to getBalance 
			// or setBalance of the same account at the same time. 
			return acc.getBalance();
		}
	}
	
	void runOperation(Operation operation) {
		Account account = accounts.get(operation.getAccountId());
		synchronized(account) {		// SetBalance and getBalance synchronized as described above.
			// + operation kept within synchronized block since I have a hard time seeing whether
			// lifting it out would actually save and time.
			account.setBalance(account.getBalance() + operation.getAmount());	
		}
		
	}
		
	// TODO: If you are not aiming for grade VG you should remove this method.
	void runTransaction(Transaction transaction) {
		
		List<Operation> operations = transaction.getOperations();

		Account account = null;
		for (int i = 0; i < operations.size(); i++) {
			account = accounts.get(operations.get(i).getAccountId()); // Synchronized through ConcurrentHashMap as described above.
			synchronized(account) {	// Synchronized on the actual account as described above.
				account.setBalance(account.getBalance() + operations.get(i).getAmount());	
			}
		}
	}
}



