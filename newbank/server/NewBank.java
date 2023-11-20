package newbank.server;
import java.util.ArrayList;

import java.util.Arrays;
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
		if (!customers.containsKey(userName)) {
			// Create a new customer and add it to the customers HashMap
			Customer newCustomer = new Customer(password);
			customers.put(userName, newCustomer);
			return "Registration successful. Welcome, " + userName + "!";
		} else {
			return "Registration failed: User already exists. Please choose a different username.";
		}
	}



	// commands from the NewBank customer are processed in this method
	public synchronized String processRequest(CustomerID customer, String request) {
		String[] requestParts = request.split(" ");
		String command = requestParts[0];
		String[] arguments = Arrays.copyOfRange(requestParts, 1, requestParts.length);

		if(customers.containsKey(customer.getKey())) {
			switch(command) {
				case "SHOWMYACCOUNTS" : return showMyAccounts(customer);
				case "NEWACCOUNT": return addNewAccount(customer, arguments);
				default : return "FAIL";
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
		if (customers.containsKey(customer.getKey())) {
			return customers.get(customer.getKey()).accountsToString();
		} else {
			return "FAIL";  // or an appropriate message
		}
	}

	private String addNewAccount(CustomerID customer, String[] arguments)
	{
		String name;

		if (arguments.length > 0) {
			name = arguments[0];
		} else {
			return "FAIL";
		}

		Customer accountHolder = customers.get(customer.getKey());

		if(accountHolder == null || name == null || name.isEmpty()) {
			return "FAIL";
		}

		if(accountHolder.hasAccount(name)) {
			return "FAIL";
		}

		accountHolder.addAccount(new Account(name, 0.00));

		return "SUCCESS";
	}
}