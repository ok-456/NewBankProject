package newbank.server;

import java.io.*;
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
    public void loadUserData(Map<String, Customer> users) {
        try {
            File file = new File(USER_DATA_FILE);

            // Create new user_data txt file if it doesn't exist
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

                        // If there are additional account details i.e different bank account, add them to the customer
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
                        // Put new customer in the Map
                        users.put(username, newCustomer);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Saves user data from the provided Map to the user_data.txt file.
     * @param users The Map containing user data to be saved
     */
    public void saveUserData(Map<String, Customer> users) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USER_DATA_FILE))) {
            for (Map.Entry<String, Customer> entry : users.entrySet()) {
                String username = entry.getKey();
                String password = entry.getValue().getPassword(); // Get plaintext password
                writer.write(username + ":" + password);

                // If customer has accounts, write them to the file
                for (Account account : entry.getValue().getAccounts()) {
                    writer.write(":" + account.getName() + "," + account.getBalance());
                }

                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}