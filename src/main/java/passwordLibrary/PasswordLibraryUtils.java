package passwordLibrary;

import java.util.ArrayList;
import java.util.List;

import javax.crypto.SecretKey;

import database.DbManager;
import database.EncryptedUserPw;
import session.Session;

public class PasswordLibraryUtils {
	
	private DbManager dbManager = new DbManager();

	public void addPasswordEntry (String domain, String username, String password) throws Exception {
		dbManager.add(domain, username, password);
	}
	
	public void removeSelectedPassword (String domain, String username) throws Exception{
		dbManager.remove(domain, username);
	}
	
	public List<Object[]> getAllExistingPasswordsForTable() throws Exception {
	    // Get the list of EncryptedUserPw objects from your database manager.
	    List<EncryptedUserPw> pwList = dbManager.getAllUnencryptedUserPw();
	    
	    // Prepare a list to hold rows for the table.
	    List<Object[]> tableRows = new ArrayList<>();
	    
	    // For each entry, create a row with the three values.
	    for (EncryptedUserPw entry : pwList) {
	        Object[] row = new Object[] {
	            entry.getDomain(),   
	            entry.getUsername(),     
	            entry.getPassword()   
	        };
	        tableRows.add(row);
	    }

	    return tableRows;
	}
}
