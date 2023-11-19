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
					e.printStackTrace();
					return;
				}
			}
		};
		bankServerResponseThread.start();
	}

	public void run() {
		boolean running = true;

		try {
			while (running) {
				// Read user input
				String command = userInput.readLine();

				// Send user input to the server
				bankServerOut.println(command);

				// Check if the user wants to exit
				if (command.equalsIgnoreCase("exit")) {
					running = false;
					System.out.println("Exiting...");
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

	public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException {
		new ExampleClient("localhost", 14002).start();
	}
}