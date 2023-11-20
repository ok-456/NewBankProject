package newbank.server;
<<<<<<< HEAD:server/Customer.java
import java.util.ArrayList;
=======
import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.io.Serializable;
>>>>>>> 9b9fd66 (removed password hashing, user details are now stored in plaintext and saved in txt file):newbank/server/Customer.java


public class Customer implements Serializable {

	private String password;
	private ArrayList<Account> accounts;

	public ArrayList<Account> getAccounts() {
		return accounts;
	}

	public boolean hasAccounts() {
		return !accounts.isEmpty();
	}

	public Customer(String password) {

		this.password = password;
		this.accounts = new ArrayList<>();
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return password;
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

<<<<<<< HEAD:server/Customer.java
	/**
	 * Get the account object with the given name
	 * @param accountName
	 * @return the account object with the given name
	 */
	public Account getAccount(String accountName) {
		return accounts.stream().filter(account -> accountName.toLowerCase().equals(account.toString().split(":")[0].trim().toLowerCase())).findFirst().orElse(null);
	}

=======
	public Customer(String password) {

		this.password = password;
		this.accounts = new ArrayList<>();
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return password;
	}


	public String accountsToString() {
		StringBuilder accountsList = new StringBuilder();
		for (Account acc : accounts) {
			accountsList.append(acc.getName()).append(": ").append(acc.getBalance()).append("\n");
		}
		return accountsList.toString().trim(); // Remove the last newline character
	}
>>>>>>> 9b9fd66 (removed password hashing, user details are now stored in plaintext and saved in txt file):newbank/server/Customer.java

	public void addAccount(Account account) {
		accounts.add(account);
	}
}
