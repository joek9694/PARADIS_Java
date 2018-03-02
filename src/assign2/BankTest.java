// Peter Idestam-Almquist, 2018-02-21.

package assign2;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

//Deadlocks!?

// TODO: Make this class thread-safe and as performant as possible.
class BankTest {
	// Instance variables.
	private int accountCounter = 0;
	private Map<Integer, Account> accounts = new ConcurrentHashMap<>();
	private Map<Integer, ReentrantLock> accLocks = new ConcurrentHashMap<>();
	

	// Instance methods.

	int newAccount(int balance) {
		int accountId = accountCounter++;
		Account account = new Account(accountId, balance);
		ReentrantLock accountLock = new ReentrantLock();
		accounts.put(accountId, account);
		accLocks.put(accountId, accountLock);
		return accountId;
	}

	int getAccountBalance(int accountId) {
		int balance;
		Lock lock = accLocks.get(accountId);
		lock.lock();
		try{
			balance = accounts.get(accountId).getBalance();
		}finally{
			lock.unlock();
		}
		return balance;
		
	}

	void runOperation(Operation operation) {
		Account account = accounts.get(operation.getAccountId());
		Lock lock = accLocks.get(operation.getAccountId());
		lock.lock();
		try{		
			account.setBalance(account.getBalance() + operation.getAmount());	
		}finally {
			lock.unlock();
		}
	}

	// TODO: If you are not aiming for grade VG you should remove this method.
	void runTransaction(Transaction transaction) {
		List<Integer> accountIds = transaction.getAccountIds();
		List<Operation> operations = transaction.getOperations();
		
		synchronized(accLocks) {
			for(int i = 0; i < accountIds.size(); i++) {
				int id = accountIds.get(i);
				Lock lock = accLocks.get(id);
				lock.lock();
				
			}
		}
		
		try {
			
			for (int i = 0; i < operations.size(); i++) {
				
				runOperation(operations.get(i));	// Synchronized as described by runOperation().
				
				
			}
			
			
		}finally {
			synchronized(accLocks) {
				for(int i = 0; i < accountIds.size(); i++) {
					int id = accountIds.get(i);
					Lock lock = accLocks.get(id);
					lock.unlock();
				}
			}
		}
		
		
		
		
		/*
		traLock.lock();
		try {
			Account account = null;
			for (int i = 0; i < operations.size(); i++) {

				runOperation(operations.get(i));

			}
		} finally {
			traLock.unlock();
		}

		 */
		
		// jämför mot balances?
	}

}
