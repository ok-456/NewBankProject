package newbank.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class ExampleClient extends Thread {

	private Socket server;
	private PrintWriter bankServerOut;
	private BufferedReader userInput;
	private Thread bankServerResponseThread;

	public ExampleClient(String ip, int port) throws UnknownHostException, IOException {
		server = new Socket(ip, port);
		userInput = new BufferedReader(new InputStreamReader(System.in));
		bankServerOut = new PrintWriter(server.getOutputStream(), true);

		bankServerResponseThread = new Thread() {
			private BufferedReader bankServerIn = new BufferedReader(new InputStreamReader(server.getInputStream()));

			public void run() {
				try {
					while (true) {
						String response = bankServerIn.readLine();
						System.out.println(response);
					}
				} catch (IOException e) {
					// Ignore IOException when closing the BufferedReader
				}
			}
		};
		bankServerResponseThread.start();
	}

	public void run() {
		try {
			while (true) {
				// Read user input
				String command = getUserInput();

				// Send user input to the server
				bankServerOut.println(command);

				// Check if the user wants to exit
				if (command.equalsIgnoreCase("exit")) {
					System.out.println("Exiting NewBank Application. Thank you for using our app.");
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				// Close resources
				userInput.close();
				bankServerOut.close();
				server.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// Add these methods to your ExampleClient class
	public String getUserInput() throws IOException {
		return userInput.readLine();
	}

	public String login(String username, String password) {
		// You need to implement the logic for login here
		// It might involve sending a specific command to the server
		// and handling the server's response
		return "LOGIN_RESULT";  // Replace with actual logic
	}

	public String processCommand(String command) {
		// You need to implement the logic for processing commands here
		// It might involve sending the command to the server
		// and handling the server's response
		return "COMMAND_RESULT";  // Replace with actual logic
	}

	public String register(String username, String password) {
		// You need to implement the logic for registration here
		// It might involve sending a specific command to the server
		// and handling the server's response
		return "REGISTRATION_RESULT";  // Replace with actual logic
	}

	public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException {
		new ExampleClient("localhost", 14002).start();
	}
}
