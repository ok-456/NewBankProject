package newbank.server;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;


public class Customer {

	private String passwordHash;
	private ArrayList<Account> accounts;

	
	public Customer(String password) {

		this.passwordHash = hashPassword(password);
		this.accounts = new ArrayList<>();
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public String hashPassword(String password) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hashBytes = digest.digest(password.getBytes());
			return Base64.getEncoder().encodeToString(hashBytes);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			// Handle exception appropriately, for example, throw a custom exception
			return "not working";
		}
	}
	
	public String accountsToString() {
		String s = "";
		for(Account a : accounts) {
			s += a.toString();
		}
		return s;
	}

	public void addAccount(Account account) {
		accounts.add(account);		
	}
}
