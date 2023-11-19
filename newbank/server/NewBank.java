package newbank.server;
import java.util.ArrayList;
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
		john.addAccount(new Account("Main", 1000.0));
		john.addAccount(new Account("Savings", 1500.0));
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



	}
}
