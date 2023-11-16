package newbank.server;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.ArrayList;

public class Customer {

	private String passwordHash;
	private ArrayList<Account> accounts;

	
	public Customer() {

		this.passwordHash = hashPassword(password);
		this.accounts = new ArrayList<>();
	}


	private String hashPassword(String password) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hashBytes = digest.digest(password.getBytes());
			return Base64.getEncoder().encodeToString(hashBytes);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			// Handle exception appropriately, for example, throw a custom exception
			return null;
		}
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
