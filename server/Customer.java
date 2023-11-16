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
