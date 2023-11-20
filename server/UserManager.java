package newbank.server;

import java.io.*;
<<<<<<< HEAD:server/UserManager.java
import java.util.Map;


/**
 * UserManager class handles the loading and saving of user data from/to a text file.
 * Enables new user accounts as well as adding new bank accounts to current users in the system.
 */
public class UserManager {

    //Text file that allows new user accounts as well as adding new accounts to existing users
    private static final String USER_DATA_FILE = "user_data.txt";

    /**
     * Loads user data from the text file and populates the provided Map with Customer objects.
     * @param users The Map to populate with user data
     */
=======
import java.util.HashMap;
import java.util.Map;

public class UserManager {

    private static final String USER_DATA_FILE = "user_data.txt";

>>>>>>> 9b9fd66 (removed password hashing, user details are now stored in plaintext and saved in txt file):newbank/server/UserManager.java
    public void loadUserData(Map<String, Customer> users) {
        try {
            File file = new File(USER_DATA_FILE);

<<<<<<< HEAD:server/UserManager.java
            // Create new user_data txt file if it doesn't exist
            if (!file.exists()) {
                file.createNewFile();
                return;
=======
            // Create a new file if it doesn't exist
            if (!file.exists()) {
                file.createNewFile();
                return; // No need to proceed further as the file is new and empty
>>>>>>> 9b9fd66 (removed password hashing, user details are now stored in plaintext and saved in txt file):newbank/server/UserManager.java
            }

            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] userData = line.split(":");
                    if (userData.length >= 2) {
                        String username = userData[0];
                        String password = userData[1];
                        Customer newCustomer = new Customer(password);

<<<<<<< HEAD:server/UserManager.java
                        // If there are additional account details i.e different bank account, add them to the customer
=======
                        // If there are additional account details, add them to the customer
>>>>>>> 9b9fd66 (removed password hashing, user details are now stored in plaintext and saved in txt file):newbank/server/UserManager.java
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
<<<<<<< HEAD:server/UserManager.java
                        // Put new customer in the Map
=======

>>>>>>> 9b9fd66 (removed password hashing, user details are now stored in plaintext and saved in txt file):newbank/server/UserManager.java
                        users.put(username, newCustomer);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

<<<<<<< HEAD:server/UserManager.java
    /**
     * Saves user data from the provided Map to the user_data.txt file.
     * @param users The Map containing user data to be saved
     */
=======
>>>>>>> 9b9fd66 (removed password hashing, user details are now stored in plaintext and saved in txt file):newbank/server/UserManager.java
    public void saveUserData(Map<String, Customer> users) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USER_DATA_FILE))) {
            for (Map.Entry<String, Customer> entry : users.entrySet()) {
                String username = entry.getKey();
                String password = entry.getValue().getPassword(); // Get plaintext password
                writer.write(username + ":" + password);

<<<<<<< HEAD:server/UserManager.java
                // If customer has accounts, write them to the file
=======
                // If the customer has accounts, write them to the file
>>>>>>> 9b9fd66 (removed password hashing, user details are now stored in plaintext and saved in txt file):newbank/server/UserManager.java
                for (Account account : entry.getValue().getAccounts()) {
                    writer.write(":" + account.getName() + "," + account.getBalance());
                }

                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

<<<<<<< HEAD:server/UserManager.java
=======
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
>>>>>>> 9b9fd66 (removed password hashing, user details are now stored in plaintext and saved in txt file):newbank/server/UserManager.java
}