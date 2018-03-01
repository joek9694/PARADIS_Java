// Peter Idestam-Almquist, 2018-02-21.

package assign2;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

// TODO: Make this class thread-safe and as performant as possible.
class Bank {
	// Instance variables.
	private int accountCounter = 0;
	private Map<Integer, Account> accounts = new HashMap<Integer, Account>();
	
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
		account.setBalance(account.getBalance() + operation.getAmount());
	}
		
	// TODO: If you are not aiming for grade VG you should remove this method.
	void runTransaction(Transaction transaction) throws TransactionFailedException{
		List<Integer> accountIds = transaction.getAccountIds();
		List<Operation> operations = transaction.getOperations();
		Map<Integer, Integer> balances = new HashMap<>();
		
		synchronized(accountIds) {	// doesn't make sense..?
			
			for(int i = 0; i < accountIds.size(); i ++) {	//kan kanske hjälpa för rollback?
				Account acc = accounts.get(accountIds.get(i));
				balances.put(acc.getId(), acc.getBalance());
			}
			
			//genomför all transaktioner
			Account account = null;
			for (int i = 0; i < operations.size(); i++) {
				account = accounts.get(operations.get(i).getAccountId());
				
				account.setBalance(account.getBalance() + operations.get(i).getAmount());
			}
			
			//jämför mot balances?
		}
		
		
		
	}
	
	public static class TransactionFailedException extends Exception {
		
	}
}
