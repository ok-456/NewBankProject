package newbank.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;



public class NewBankClientHandler extends Thread {
	private NewBank bank;
	private BufferedReader in;
	private PrintWriter out;

	public NewBankClientHandler(Socket s) throws IOException {
		bank = NewBank.getBank();
		in = new BufferedReader(new InputStreamReader(s.getInputStream()));
		out = new PrintWriter(s.getOutputStream(), true);
	}


	public void run() {
		boolean loginSuccessful = false;

		while (!loginSuccessful) {
			try {
				// Ask for user name
				out.println("Enter Username");
				String userName = in.readLine();
				// Ask for password
				out.println("Enter Password");
				String password = in.readLine();
				out.println("Checking Details...");

				String loginResult = loginAuthentication(userName, password);

				// If login is successful, allow the user to interact with their accounts
				if (loginResult.contains("Login successful")) {
					out.println("Log In Successful. What do you want to do?");
					loginSuccessful = true;
					processUserRequests(userName);
				} else if (loginResult.contains("Would you like to register? (yes/no)")) {
					out.println(loginResult);
					String registerChoice = in.readLine();
					if (registerChoice.equalsIgnoreCase("yes")) {
						out.println("Enter a new Password");
						String newPassword = in.readLine();
						out.println("Confirm your Password");
						String confirmPassword = in.readLine();

						if (newPassword.equals(confirmPassword)) {
							String registerResult = bank.registerUser(userName, newPassword);
							out.println(registerResult);

							if (registerResult.contains("Registration successful")) {
								out.println("Log In Successful. What do you want to do?");
								loginSuccessful = true;
							}
						} else {
							out.println("Registration failed: Passwords do not match. Please try again.");
						}

					}
				} else {
					out.println("Login failed: " + loginResult);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Method for authenticating users newbank account
	 * @param userName Username that is stored in the txt database
	 * @param password Password that is stored in the txt database
	 * @return A string indicating whether the authentication is successful or not
	 */
	private String loginAuthentication(String userName, String password){
		return bank.checkLogInDetails(userName, password);
	}

	/**
	 * Method to process user requests after successful login
	 * @param userName Username that is stored in the txt database
	 * @return Returns relevant process request results, otherwise will either respond with an invalid command or server null
	 */
	private void processUserRequests(String userName) throws IOException{
		while(true){
			String request = in.readLine();
			System.out.println("Request from " + userName);
			String response = bank.processRequest(new newbank.server.CustomerID(userName), request);

			if (response != null){
				if (response.equals("Fail")){
					out.println("Invalid command. Try again.");
				}else{
					out.println(response);
				}
			}else{
				System.out.println("Server error: Null response");
				//Break out of loop if null server response
				break;
			}
		}
	}

	/**
	 * Method for processing registration for new users
	 * @param userName Username that is not stored in the txt database
	 * @return Returns relevant registration process results
	 */
	private void performRegistration(String userName) throws IOException{
		out.println("Enter a new Password");
		String newPassword = in.readLine();
		out.println("Confirm your Password");
		String confirmPassword = in.readLine();

		if(newPassword.equals(confirmPassword)){
			String registerResult = bank.registerUser(userName, newPassword);
			out.println(registerResult);

			if(registerResult.contains("Registration successful")){
				out.println("Log In Successful. What do you want to do?");
				processUserRequests(userName);
			}
		}else{
			out.println("Registration failed: Passwords do not match. Please try again.");
		}
	}





}
