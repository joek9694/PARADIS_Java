// Peter Idestam-Almquist, 2018-02-20.

package assign2;

class Operation {
	// Instance variables.
	final int ACCOUNT_ID;
	final int AMOUNT;
	
	// Constructor.
	Operation(int accountId, int amount) {
		ACCOUNT_ID = accountId;
		AMOUNT = amount;
	}
	
	// Instance methods.

	int getAccountId() {
		return ACCOUNT_ID;
	}
	
	int getAmount() {
		return AMOUNT;
	}
}	
