package launchPage;
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
    	byte[] keyBytes = masterKeyUnencrypted.getEncoded();
    	String masterKeyUnencryptedString = Base64.getEncoder().encodeToString(keyBytes);
    	String masterKeyEncrypted = CryptoUtils.encrypt(masterKeyUnencryptedString, masterKeyUnencrypted);
    	db.setEncryptedMasterKey(masterKeyEncrypted);
    }

}
