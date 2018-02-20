// Peter Idestam-Almquist, 2018-02-20.

package assign2;

import java.util.ArrayList;
import java.util.List;

class Transaction implements Runnable {
	// Instance variables.
	private List<Operation> operations = new ArrayList<Operation>();
	private List<Integer> accountIds = new ArrayList<Integer>();
	private Bank bank;
	
	// Constructor.
	Transaction(Bank bank) {
		this.bank = bank;
	}

	// Instance methods.

	void add(Operation operation) {
		operations.add(operation);
		accountIds.add(operation.getAccountId());
	}
	
	List<Integer> getAccountIds() {
		return accountIds;
	}
	
	List<Operation> getOperations() {
		return operations;
	}
	
	public void run() {
		bank.runTransaction(this);
	}
}	
