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

				String loginResult = bank.checkLogInDetails(userName, password);

				// Authenticate user and get customer ID token from the bank for use in subsequent requests
				if (loginResult.contains("Login successful")) {
					out.println("Log In Successful. What do you want to do?");
					loginSuccessful = true;

					while (true) {
						String request = in.readLine();
						System.out.println("Request from " + userName);
						String response = bank.processRequest(new CustomerID(userName), request);

						// Check for null response
						if (response != null) {
							out.println(response);
						} else {
							out.println("Server error: Null response");
							// Break out of the loop if the server response is null
							break;
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
