package newbank.server;
import java.util.ArrayList;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.HashMap;

public class NewBank {
	
	private static final NewBank bank = new NewBank();
	private HashMap<String,Customer> customers;
	
	private NewBank() {
		customers = new HashMap<>();
		addTestData();
	}

	private void addTestData() {
		Customer bhagy = new Customer("password1");
		bhagy.addAccount(new Account("Main", 1000.0));
		customers.put("Bhagy", bhagy);

		Customer christina = new Customer("password2");
		christina.addAccount(new Account("Savings", 1500.0));
		customers.put("Christina", christina);

		Customer john = new Customer("password3");
		john.addAccount(new Account("Checking", 250.0));
		customers.put("John", john);
	}
	
	public static NewBank getBank() {
		return bank;
	}

	public synchronized String checkLogInDetails(String userName, String password) {
		if (customers.containsKey(userName)) {
			Customer customer = customers.get(userName);
			if (customer.getPasswordHash().equals(hashPassword(password))) {
				return "Login successful. Welcome, " + userName + "!";
			} else {
				return "Incorrect password for user " + userName + ". Please try again.";
			}
		} else {
			return "User " + userName + " not found. Would you like to register? (yes/no)";
		}
	}

	// New method for user registration
	public synchronized String registerUser(String userName, String password) {
		if (customers.containsKey(userName)) {
			return "Registration failed: Username already exists.";
		} else {
			Customer newCustomer = new Customer(password);
			customers.put(userName, newCustomer);
			return "Registration successful. Welcome, " + userName + "!";
		}
	}


	// commands from the NewBank customer are processed in this method
	public synchronized String processRequest(CustomerID customer, String request) {
		if (customers.containsKey(customer.getKey())) {
			switch (request) {
				case "SHOWMYACCOUNTS":
					return showMyAccounts(customer);
				default:
					return "FAIL"; // Return "FAIL" for unrecognized commands
			}
		}
		return "FAIL";
	}

	private String hashPassword(String password) {
		// Same hash function as used in the Customer class
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

	private String showMyAccounts(CustomerID customer) {
		// Check if the customer has accounts
		Customer customerObj = customers.get(customer.getKey());
		if (customerObj != null && !customerObj.getAccounts().isEmpty()) {
			return customerObj.accountsToString();
		} else {
			return "You have no accounts.";
		}
	}
}
