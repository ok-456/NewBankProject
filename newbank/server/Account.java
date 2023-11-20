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
}
