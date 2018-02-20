// Peter Idestam-Almquist, 2018-02-20.

package assign2;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

class Bank {
	// Do not modify the following instance variables.
	private int accountCounter = 0;
	private Map<Integer, Account> accounts = new HashMap<Integer, Account>();
	// TODO: Add your own instance variables below this comment.
	
	// TODO: Modify the instance methods below to make this class 
	// thread-safe and as performant as possible.

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
		account.setBalance(account.getBalance() + operation.getAmount());
	}
	
	// TODO: You only need to modify this method if you are aiming for grade VG.
	void runTransaction(Transaction transaction) {
		List<Integer> accountIds = transaction.getAccountIds();
		List<Operation> operations = transaction.getOperations();
		Account account = null;
		int balance = 0;
		for (int j = 0; j < operations.size(); j++) {
			account = accounts.get(operations.get(j).getAccountId());
			account.setBalance(account.getBalance() + operations.get(j).getAmount());
		}
	}
}
