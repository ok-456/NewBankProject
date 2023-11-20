package newbank.server;

import java.io.Serializable;

public class Account implements  Serializable{

	private static final long serialVersionUID = 1L;
	
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
