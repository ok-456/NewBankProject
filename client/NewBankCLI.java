package newbank.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class NewBankCLI {

    private ExampleClient exampleClient;

    public NewBankCLI(ExampleClient exampleClient) {
        this.exampleClient = exampleClient;
    }

    public void start() {
        showLoginPage();
        showHomePage();
    }

    private void showLoginPage() {
        System.out.println("=== NewBank Login ===");
        System.out.print("Enter Username: ");
        String username = getUserInput();
        System.out.print("Enter Password: ");
        String password = getUserInput();

        String loginResult = exampleClient.login(username, password);
        System.out.println(loginResult);

        if (loginResult.contains("Login successful")) {
            System.out.println("Welcome, " + username + "!");
        } else if (loginResult.contains("Would you like to register? (yes/no)")) {
            System.out.print("Would you like to register? (yes/no): ");
            String registerChoice = getUserInput();
            if (registerChoice.equalsIgnoreCase("yes")) {
                performRegistration(username);
            } else {
                showLoginPage();
            }
        } else {
            showLoginPage();
        }
    }

    private void showHomePage() {
        System.out.println("=== NewBank Home Page ===");
        System.out.println("Available Commands:");
        System.out.println("1. SHOWMYACCOUNTS");
        System.out.println("2. NEWACCOUNT <account_name>");
        System.out.println("3. MOVE <from_account> <to_account> <amount>");
        System.out.println("4. PAY <receiver_name> <amount>");
        System.out.println("5. EXIT");

        while (true) {
            System.out.print("Enter command: ");
            String command = getUserInput();

            if (command.equalsIgnoreCase("EXIT")) {
                System.out.println("Exiting NewBank Application. Thank you for using our app.");
                break;
            }

            String response = exampleClient.processCommand(command);
            System.out.println(response);
        }
    }

    private void performRegistration(String username) {
        System.out.print("Enter a new Password: ");
        String newPassword = getUserInput();
        System.out.print("Confirm your Password: ");
        String confirmPassword = getUserInput();

        if (newPassword.equals(confirmPassword)) {
            String registerResult = exampleClient.register(username, newPassword);
            System.out.println(registerResult);

            if (registerResult.contains("Registration successful")) {
                System.out.println("Welcome, " + username + "!");
                showHomePage();
            } else {
                showLoginPage();
            }
        } else {
            System.out.println("Registration failed: Passwords do not match. Please try again.");
            showLoginPage();
        }
    }

    private String getUserInput() {
        try {
            return exampleClient.getUserInput();
        } catch (IOException e) {
            e.printStackTrace();
            return "";  // You might want to return a default value or handle it differently
        }
    }

    public static void main(String[] args) {
        try {
            ExampleClient exampleClient = new ExampleClient("localhost", 14002);
            NewBankCLI newBankCLI = new NewBankCLI(exampleClient);
            newBankCLI.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
