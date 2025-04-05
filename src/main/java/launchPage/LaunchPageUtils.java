package launchPage;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.util.Base64;

import javax.crypto.SecretKey;
import database.DbManager;
import utils.CryptoUtils;

public class LaunchPageUtils {
	DbManager db = new DbManager();
	CryptoUtils crypto = new CryptoUtils();
	
	
	//Checks if the master key has been created
	public boolean masterKeyExists() throws SQLException {
		String masterKey = db.getEncryptedMasterKey();
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
    	SecretKey masterKeyUnencrypted = crypto.deriveKey(pwList);
    	String hashedMasterKey = CryptoUtils.hashSecretKey(masterKeyUnencrypted);    	
    	db.setEncryptedMasterKey(hashedMasterKey);
    }
    
    public boolean loginPasswordIsCorrect(String pw) throws Exception {
    	char[] pwList = pw.toCharArray();
    	SecretKey keyInput = crypto.deriveKey(pwList);
    	String hashedLoginPassword = CryptoUtils.hashSecretKey(keyInput); 
    	String hashedMasterKey = db.getEncryptedMasterKey();
    	if(hashedLoginPassword.equals(hashedMasterKey)) {
    		return true;
    	} 
    	return false;
    	
    }
    
    

}
