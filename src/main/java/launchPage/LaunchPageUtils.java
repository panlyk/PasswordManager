package launchPage;
import java.security.MessageDigest;
import java.sql.SQLException;
import java.util.Base64;

import javax.crypto.SecretKey;
import database.DbManager;
import session.Session;
import utils.CryptoUtils;

public class LaunchPageUtils {
	DbManager db = new DbManager();
	CryptoUtils crypto = new CryptoUtils();
	
	
	//Checks if the master key has been created
	public boolean masterKeyExists() throws SQLException {
		String masterKey = db.getHashedMasterPassword();
		if(masterKey!=null) {
			return true;
		} 
		else
		{
			return false;
		}
	}
	
    public void unlockAction () {
    }
    
    public void createMasterKeyAction(String pw) throws Exception {
    	char[] pwList = pw.toCharArray();
    	SecretKey masterKey = crypto.deriveKey(pwList);
    	byte[] keyBytes = masterKey.getEncoded();
    	db.setHashedMasterPassword(crypto.getHashString(pw));
    	Session session = Session.getInstance();
    	session.setMasterKey(masterKey);
    }
    
    public boolean attemptLogin (String pw) {
    	//turn input into hash
    	String inputPasswordHash = crypto.getHashString(pw);
    	
    	//get the master password hash
    	String masterPasswordHash = null;
    	try {
    		masterPasswordHash = db.getHashedMasterPassword();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	if (inputPasswordHash.equals(masterPasswordHash)) {
    		return true;
    	} else {
			return false;
		}
    }

}
