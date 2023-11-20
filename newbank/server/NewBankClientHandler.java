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

	private void runInnerLoop(){

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

				String loginResult = bank.checkLogInDetails(userName, password);

				if (loginResult.contains("Login successful")) {
					out.println("Log In Successful. What do you want to do?");
					loginSuccessful = true;

					while (true) {

						String request = in.readLine();
						System.out.println("Request from " + userName);
						String response = bank.processRequest(new CustomerID(userName), request);

						// Check for null response
						if (response != null) {
							if (response.equals("FAIL")) {
								out.println("Invalid command. Try again.");
							} else {
								out.println(response);
							}
						} else {
							System.out.println("Server error: Null response");
							// Break out of the loop if the server response is null
							break;
						}
					}
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
}
