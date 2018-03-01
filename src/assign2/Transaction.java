// Peter Idestam-Almquist, 2018-02-21.

package assign2;

import java.util.ArrayList;
import java.util.List;

class Transaction implements Runnable {
	private List<Operation> operations = new ArrayList<Operation>();
	private List<Integer> accountIds = new ArrayList<Integer>();
	private final Bank1 bank;
	
	Transaction(Bank1 bank) {
		this.bank = bank;
	}

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
