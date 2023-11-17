package newbank.server;

import java.util.ArrayList;

public class Customer {
	
	private ArrayList<Account> accounts;
	
	public Customer() {
		accounts = new ArrayList<>();
	}
	
	public String accountsToString() {
		StringBuilder accountsList = new StringBuilder();
		for (Account acc : accounts) {
			accountsList.append(acc.getName()).append(": ").append(acc.getBalance()).append("\n");
		}
		return accountsList.toString().trim(); // Remove the last newline character
	}
	public void addAccount(Account account) {
		accounts.add(account);		
	}
}
