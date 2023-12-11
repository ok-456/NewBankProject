package newbank.server;

import java.util.UUID;

public class CustomerID {

	private String key;
	private String uniqueKey;

	
	public CustomerID(String key) {
		this.key = key; // Used as Username at the moment, should be renamed as username
		this.uniqueKey = generateUniqueKey();
	}
	
	public String getKey() {
		return key;
	}

	public String getUniqueKey() {
		return uniqueKey;
	}

	private String generateUniqueKey() {
		// Use UUID to generate a random key
		return UUID.randomUUID().toString();
	}
}
