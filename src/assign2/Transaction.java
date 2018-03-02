// Peter Idestam-Almquist, 2018-02-21.

package assign2;

import java.util.ArrayList;
import java.util.List;

class Transaction implements Runnable {
	private List<Operation> operations = new ArrayList<Operation>();
	private List<Integer> accountIds = new ArrayList<Integer>();
	private final BankTest BankTest;
	
	Transaction(BankTest BankTest) {
		this.BankTest = BankTest;
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
		BankTest.runTransaction(this);
	}
}	
