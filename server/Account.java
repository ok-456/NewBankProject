package newbank.server;

<<<<<<< HEAD:server/Account.java

public class Account {

=======
import java.io.Serializable;

public class Account implements  Serializable{

	private static final long serialVersionUID = 1L;
	
>>>>>>> 9b9fd66 (removed password hashing, user details are now stored in plaintext and saved in txt file):newbank/server/Account.java
	private String accountName;
	private double openingBalance;

	public Account(String accountName, double openingBalance) {
		this.accountName = accountName;
		this.openingBalance = openingBalance;
	}
	
	public String toString() {
		return (accountName + ": " + openingBalance);
	}

	public String getName() {
		return accountName;
	}
	public double getBalance() {
		return openingBalance;
	}

	/**
	 * Add the given amount to the account balance
	 * @param amount
	 */
	public void deposit(double amount) {
		openingBalance += amount;
	}

	/**
	 * Subtract the given amount from the account balance
	 * @param amount
	 */
	public void withdraw(double amount) {
		openingBalance -= amount;
	}
}
