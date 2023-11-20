package newbank.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class NewBankServer extends Thread{

	private ServerSocket server;
	private UserManager userManager;
<<<<<<< HEAD:server/NewBankServer.java

=======
	
>>>>>>> 9b9fd66 (removed password hashing, user details are now stored in plaintext and saved in txt file):newbank/server/NewBankServer.java
	public NewBankServer(int port) throws IOException {
		server = new ServerSocket(port);
		userManager = new UserManager();

		//Load user data on server startup
		userManager.loadUserData(NewBank.getBank().getCustomers());
	}

	public void run() {
		// starts up a new client handler thread to receive incoming connections and process requests
		System.out.println("New Bank Server listening on " + server.getLocalPort());
		try {
			while(true) {
				Socket s = server.accept();
				NewBankClientHandler clientHandler = new NewBankClientHandler(s);
				clientHandler.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally {
<<<<<<< HEAD:server/NewBankServer.java
			//Saves any new entry to the user_data.txt upon server connection close
=======
>>>>>>> 9b9fd66 (removed password hashing, user details are now stored in plaintext and saved in txt file):newbank/server/NewBankServer.java
			userManager.saveUserData(NewBank.getBank().getCustomers());
			try {
				server.close();
			} catch (IOException e) {
				e.printStackTrace();
				Thread.currentThread().interrupt();
			}
		}
	}

	public static void main(String[] args) throws IOException {
		// starts a new NewBankServer thread on a specified port number
		new NewBankServer(14002).start();
	}
}