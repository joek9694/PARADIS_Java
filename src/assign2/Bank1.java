package assign2;

// By: joek9694 - Johan Eklundh 

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

// TODO: Make this class thread-safe and as performant as possible.
@ThreadSafe()
class Bank1 {
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
		// no risk of multiple threads accessing value of accountCounter at close to the same time whereby adding
		// twice would result in overwriting the first add-operation with the second one and that 
		// the threads would each save the same accountId.
		Account account = new Account(accountId, balance);
		accounts.put(accountId, account);	//Is synchronized through the locking mechanism of ConcurrentHashMap
		return accountId;
	}
	
	int getAccountBalance(int accountId) {
		Account acc = accounts.get(accountId);
		synchronized(acc) {	// synchronized (even though the Program doesn't use it in a way that jeopardizes
			// thread safety and that account.setBalance() is never used within this class
			// outside of the runOperation and runTransaction()) so that, no two threads could try to getBalance 
			// or setBalance of the same account at the same time using this class. 
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
	
	// Since there is no explicit requirement of possible role-backs of transactions there is no need to 
	// lock a transaction so that another thread can't make an operation on an account that is apart of the ongoing
	// transaction unless the two threads are trying to access the same account at the same time, therefore 
	// the solution of lock account/ operation holds in this method as well. This solution should in fact have faster 
	// performance for the overall use of bank than another possible solution of locking so that no two transactions
	// could occur at the same time.
	void runTransaction(Transaction transaction) {
		
		List<Operation> operations = transaction.getOperations();

		
		for (int i = 0; i < operations.size(); i++) {
			
			runOperation(operations.get(i));	// Synchronized as described by runOperation().
			
			
		}
	}
}
/*
Account account = null;

account = accounts.get(operations.get(i).getAccountId()); // Synchronized through ConcurrentHashMap as described above.
synchronized(account) {	// Synchronized on the actual account as described by runOperation().
	account.setBalance(account.getBalance() + operations.get(i).getAmount());	
}
*/

