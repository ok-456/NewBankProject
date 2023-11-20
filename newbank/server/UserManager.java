package newbank.server;

import java.io.*;
import java.util.Map;

public class UserManager {

    //Text file that allows new user accounts as well as adding new accounts to existing users
    private static final String USER_DATA_FILE = "user_data.txt";

    public void loadUserData(Map<String, Customer> users) {
        try {
            File file = new File(USER_DATA_FILE);

            // Create a new file if it doesn't exist
            if (!file.exists()) {
                file.createNewFile();
                return;
            }

            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] userData = line.split(":");
                    if (userData.length >= 2) {
                        String username = userData[0];
                        String password = userData[1];
                        Customer newCustomer = new Customer(password);

                        // If there are additional account details, add them to the customer
                        if (userData.length > 2) {
                            for (int i = 2; i < userData.length; i++) {
                                String[] accountData = userData[i].split(",");
                                if (accountData.length == 2) {
                                    String accountName = accountData[0];
                                    double balance = Double.parseDouble(accountData[1]);
                                    newCustomer.addAccount(new Account(accountName, balance));
                                }
                            }
                        }

                        users.put(username, newCustomer);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveUserData(Map<String, Customer> users) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USER_DATA_FILE))) {
            for (Map.Entry<String, Customer> entry : users.entrySet()) {
                String username = entry.getKey();
                String password = entry.getValue().getPassword(); // Get plaintext password
                writer.write(username + ":" + password);

                // If the customer has accounts, write them to the file
                for (Account account : entry.getValue().getAccounts()) {
                    writer.write(":" + account.getName() + "," + account.getBalance());
                }

                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean checkUserExists(String username, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader(USER_DATA_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userData = line.split(":");
                if (userData.length == 3) {
                    String storedUsername = userData[0];
                    String storedPassword = userData[1];
                    if (storedUsername.equals(username) && storedPassword.equals(password)) {
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}