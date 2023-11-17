package newbank.server;

import java.util.ArrayList;

public class Customer {
	
	private ArrayList<Account> accounts;
	
	public Customer() {
		accounts = new ArrayList<>();
	}
	
	public String accountsToString() {
		String s = "";
		for(Account a : accounts) {
			s += a.toString();
		}
		return s;
	}

	/**
	 * Check to see if the customer has an account with the given name
	 * @param accountName
	 * @return true if the customer has an account with the given name
	 */
	public boolean hasAccount(String accountName) {
		return  accounts.stream().anyMatch(account -> accountName.toLowerCase().equals(account.toString().split(":")[0].trim().toLowerCase()));
	}

	public void addAccount(Account account) {
		accounts.add(account);		
	}
}
