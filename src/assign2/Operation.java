// Peter Idestam-Almquist, 2018-02-21.

package assign2;

class Operation implements Runnable {
	final int ACCOUNT_ID;
	final int AMOUNT;
	private final BankTest BankTest;
	
	Operation(BankTest BankTest, int accountId, int amount) {
		ACCOUNT_ID = accountId;
		AMOUNT = amount;
		this.BankTest = BankTest;
	}
	
	int getAccountId() {
		return ACCOUNT_ID;
	}
	
	int getAmount() {
		return AMOUNT;
	}
	
	public void run() {
		BankTest.runOperation(this);
	}
}	
