// Peter Idestam-Almquist, 2018-02-21.

package assign2;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.HashMap;

// TODO: Make this class thread-safe and as performant as possible.
class BankTest {
	// Instance variables.
	private int accountCounter = 0;
	private Map<Integer, Account> accounts = new HashMap<Integer, Account>();
	private Lock traLock = new ReentrantLock();

	// Instance methods.

	int newAccount(int balance) {
		int accountId = accountCounter++;
		Account account = new Account(accountId, balance);
		accounts.put(accountId, account);
		return accountId;
	}

	int getAccountBalance(int accountId) {
		return accounts.get(accountId).getBalance();
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

		traLock.lock();
		try {
			Account account = null;
			for (int i = 0; i < operations.size(); i++) {

				runOperation(operations.get(i));

			}
		} finally {
			traLock.unlock();
		}

		// jämför mot balances?
	}

}
