package newbank.server;
import java.util.ArrayList;

public class Customer {

	private String password;
	private ArrayList<Account> accounts;
	private CustomerID customerID;

	public ArrayList<Account> getAccounts() {
		return accounts;
	}

	/**
	 * Check to see if the customer has an account with the given name
	 * @param accountName
	 * @return true if the customer has an account with the given name
	 */
	public boolean hasAccount(String accountName) {
		return  accounts.stream().anyMatch(account -> accountName.toLowerCase().equals(account.toString().split(":")[0].trim().toLowerCase()));
	}

	public Customer(String password) {
		this.customerID = new CustomerID("");
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

	public String getCustomerKey(){
		return customerID.getUniqueKey();
	}
}