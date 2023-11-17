package newbank.server;

import java.util.Arrays;
import java.util.HashMap;

public class NewBank {
	
	private static final NewBank bank = new NewBank();
	private HashMap<String,Customer> customers;
	
	private NewBank() {
		customers = new HashMap<>();
		addTestData();
	}
	
	private void addTestData() {
		Customer bhagy = new Customer();
		bhagy.addAccount(new Account("Main", 1000.0));
		customers.put("Bhagy", bhagy);
		
		Customer christina = new Customer();
		christina.addAccount(new Account("Savings", 1500.0));
		customers.put("Christina", christina);
		
		Customer john = new Customer();
		john.addAccount(new Account("Checking", 250.0));
		customers.put("John", john);
	}
	
	public static NewBank getBank() {
		return bank;
	}
	
	public synchronized CustomerID checkLogInDetails(String userName, String password) {
		if(customers.containsKey(userName)) {
			return new CustomerID(userName);
		}
		return null;
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
	
	private String showMyAccounts(CustomerID customer) {
		return (customers.get(customer.getKey())).accountsToString();
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
