package newbank.server;

import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;
import java.util.Map;

public class NewBank {

	private static final NewBank bank = new NewBank();

	private UserManager userManager;
	private HashMap<String,Customer> customers;

	private NewBank() {
		userManager = new UserManager();
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

	public Map<String, Customer> getCustomers() {
		return customers;
	}

	// Checks login details
	public synchronized String checkLogInDetails(String userName, String password) {
		if (customers.containsKey(userName)) {
			Customer customer = customers.get(userName);
			if (customer.getPassword().equals(password)) {
				return "Login successful. Welcome, " + userName + "!";
			} else {
				return "Incorrect password for user " + userName + ". Please try again.";
			}
		} else {

			return "User " + userName + " not found. Would you like to register? (yes/no)";

		}
	}
	public synchronized String registerUser(String userName, String password) {
		if (!customers.containsKey(userName)) {
			// Create a new customer and add it to the customers HashMap
			Customer newCustomer = new Customer(password);
			newCustomer.setPassword(password); // Set plaintext password

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

		if (customers.containsKey(customer.getKey())) {
			switch (command) {
				case "SHOWMYACCOUNTS":
					return showMyAccounts(customer);
				case "NEWACCOUNT":
					return addNewAccount(customer, arguments);
				case "MOVE":
					return moveMoney(customer, arguments);
				case "EXIT":
					return null;
				case "exit":
					return null;

				default:
					return "FAIL";
			}
			return "FAIL";
		}
	}

	private String showMyAccounts(CustomerID customer) {
		if (customers.containsKey(customer.getKey())) {
			if (customers.get(customer.getKey()).accountsToString().isEmpty()) {
				return "NO ACCOUNTS";
			} else {
				return customers.get(customer.getKey()).accountsToString();
			}
		} else {
			return "FAIL";  // or an appropriate message
		}
	}

	/**
	 * Adds a new account to the customer's account list
	 * @param customer The customer to add the account to
	 * @param arguments The arguments passed in by the user
	 * @return A string indicating whether the operation was successful or not
	 */
	private String addNewAccount(CustomerID customer, String[] arguments)
	{
		String name;

		if (customers.containsKey(customer.getKey())) {
			if (customers.get(customer.getKey()).accountsToString().isEmpty()) {
				return "NO ACCOUNTS";
			} else {
				return customers.get(customer.getKey()).accountsToString();
			}
		} else {
			return "FAIL";  // or an appropriate message
		}
	}

	/**
	 * Adds a new account to the customer's account list
	 * @param customer The customer to add the account to
	 * @param arguments The arguments passed in by the user
	 * @return A string indicating whether the operation was successful or not
	 */
	private String addNewAccount(CustomerID customer, String[] arguments) {
		String addNewAccountResult;

		String name;
		if (arguments.length > 0) {
			name = arguments[0];
		} else {
			return "FAIL";
		}

		Customer accountHolder = customers.get(customer.getKey());

		if (accountHolder == null || name == null || name.isEmpty()) {
			return "FAIL";
		}

		if (accountHolder.hasAccount(name)) {
			return "FAIL";
		}

		accountHolder.addAccount(new Account(name, 0.00));

		// Save the updated user data
		userManager.saveUserData(customers);

		return "SUCCESS";
	}

	/**
	 * Moves money from one account to another
	 * @param customer The customer to move the money for
	 * @param arguments The arguments passed in by the user
	 * @return A string indicating whether the operation was successful or not
	 */
	private String moveMoney(CustomerID customer, String[] arguments) {
		String fromAccount;
		String toAccount;
		double amount;

		if (arguments.length > 0) {
			fromAccount = arguments[1];
			toAccount = arguments[2];

			try {
				amount = Double.parseDouble(arguments[3]);
			} catch (Exception e) {
				return "FAIL";
			}

		} else {
			return "FAIL";
		}

		Customer accountHolder = customers.get(customer.getKey());

		if(accountHolder == null || fromAccount == null || fromAccount.isEmpty() || toAccount == null || toAccount.isEmpty()) {
			return "FAIL";
		}

		if(!accountHolder.hasAccount(fromAccount) || !accountHolder.hasAccount(toAccount)) {
			return "FAIL";
		}

		Account from = accountHolder.getAccount(fromAccount);
		Account to = accountHolder.getAccount(toAccount);

		if(from.getBalance() < amount) {
			return "FAIL";
		}

		from.withdraw(amount);
		to.deposit(amount);

		return "SUCCESS";
	}
}
