// Peter Idestam-Almquist, 2018-02-21.

package assign2;

class Operation implements Runnable {
	final int ACCOUNT_ID;
	final int AMOUNT;
	private final Bank bank;
	
	Operation(Bank bank, int accountId, int amount) {
		ACCOUNT_ID = accountId;
		AMOUNT = amount;
		this.bank = bank;
	}
	
	int getAccountId() {
		return ACCOUNT_ID;
	}
	
	int getAmount() {
		return AMOUNT;
	}
	
	public void run() {
		bank.runOperation(this);
	}
}	
