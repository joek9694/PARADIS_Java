// Peter Idestam-Almquist, 2018-02-21.

package assign2;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

// TODO: Make this class thread-safe and as performant as possible.
class Bank2 {
	// Instance variables.
	private int accountCounter = 0;
	private Map<Integer, Account> accounts = new HashMap<Integer, Account>();
	private Object idLock = new Object();
	// Instance methods.

	int newAccount(int balance) {
		int accountId;
		synchronized (idLock){accountId = accountCounter++;}
		Account account = new Account(accountId, balance);
		synchronized(accounts) {
			accounts.put(accountId, account);
		}
		
		return accountId;
	}
	
	int getAccountBalance(int accountId) {
		Account acc;
		synchronized(accounts) {
			acc = accounts.get(accountId);
		}
		
		synchronized(acc) {return acc.getBalance();}
	}
	
	void runOperation(Operation operation) {
		Account account;
		synchronized (accounts) {account = accounts.get(operation.getAccountId());}
		int balance;
		synchronized (account) {balance = account.getBalance();
		
		account.setBalance(balance + operation.getAmount());
		}
	}
		
	// TODO: If you are not aiming for grade VG you should remove this method.
	void runTransaction(Transaction transaction) {
		List<Integer> accountIds = transaction.getAccountIds();
		List<Operation> operations = transaction.getOperations();

		Account account = null;
		for (int i = 0; i < operations.size(); i++) {
			account = accounts.get(operations.get(i).getAccountId());
			account.setBalance(account.getBalance() + operations.get(i).getAmount());
		}
	}
}

