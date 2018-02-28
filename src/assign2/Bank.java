// Peter Idestam-Almquist, 2018-02-21.

package assign2;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.HashMap;

// TODO: Make this class thread-safe and as performant as possible.
class Bank {
	// Instance variables.
	private int accountCounter = 0;
	private ConcurrentHashMap<Integer, Account> accounts = new ConcurrentHashMap<Integer, Account>();
	
	private Object idLock = new Object();	//..
	private Object accLock = new Object();
	// Instance methods.

	int newAccount(int balance) {
		int accountId;
		synchronized (idLock){accountId = accountCounter++;}	//...
		Account account = new Account(accountId, balance);
		accounts.put(accountId, account);
		return accountId;
	}
	
	int getAccountBalance(int accountId) {
		Account acc = accounts.get(accountId);
		synchronized(acc) {
			return acc.getBalance();
		}
	}
	
	void runOperation(Operation operation) {
		Account account = accounts.get(operation.getAccountId());
		synchronized(account) {
			account.setBalance(account.getBalance() + operation.getAmount());	
		}
		
	}
		
	// TODO: If you are not aiming for grade VG you should remove this method.
	void runTransaction(Transaction transaction) {
		List<Integer> accountIds = transaction.getAccountIds();
		List<Operation> operations = transaction.getOperations();

		Account account = null;
		for (int i = 0; i < operations.size(); i++) {
			account = accounts.get(operations.get(i).getAccountId());
			synchronized(account) {
				account.setBalance(account.getBalance() + operations.get(i).getAmount());	
			}
		}
	}
}



