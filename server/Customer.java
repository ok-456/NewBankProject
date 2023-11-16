package newbank.server;
import org.mindrot.jbcrypt.BCrypt;

import java.util.ArrayList;

public class Customer {
	
	private ArrayList<Account> accounts;
	private String passwordHash;

	
	public Customer() {

		this.passwordHash = BCrypt.hashpw(password, BCrypt.gensalt());
		this.accounts = new ArrayList<>();
	}
	
	public String accountsToString() {
		StringBuilder accountsList = new StringBuilder();
		for (Account acc : accounts) {
			accountsList.append(acc.getName()).append(": ").append(acc.getBalance()).append("\n");
		}
		return accountsList.toString().trim(); // Remove the last newline character
	}


	/**
	 * Check to see if the customer has an account with the given name
	 * @param accountName
	 * @return true if the customer has an account with the given name
	 */
	public boolean hasAccount(String accountName) {
		return  accounts.stream().anyMatch(account -> accountName.toLowerCase().equals(account.toString().split(":")[0].trim().toLowerCase()));
	}

	/**
	 * Get the account object with the given name
	 * @param accountName
	 * @return the account object with the given name
	 */
	public Account getAccount(String accountName) {
		return accounts.stream().filter(account -> accountName.toLowerCase().equals(account.toString().split(":")[0].trim().toLowerCase())).findFirst().orElse(null);
	}


	public void addAccount(Account account) {
		accounts.add(account);		
	}
}
