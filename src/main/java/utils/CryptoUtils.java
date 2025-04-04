package utils;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.sql.SQLException;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import database.DbManager;


public class CryptoUtils {
	DbManager dbManger = new DbManager();
	
	//returns new encrypted master key
	public SecretKey deriveKey (char[] masterPassword) throws NoSuchAlgorithmException, InvalidKeySpecException, SQLException {
		byte[] salt = getOrCreateSalt();		
		SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
		KeySpec spec = new PBEKeySpec(masterPassword, salt, 65536, 256);
		SecretKey temp = factory.generateSecret(spec);
		return new SecretKeySpec(temp.getEncoded(), "AES");
	}
	
	public static String encrypt(String plainText, SecretKey key) throws Exception {
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		byte[] iv = new byte[16];
		SecureRandom random = new SecureRandom();
		random.nextBytes(iv);
		IvParameterSpec spec = new IvParameterSpec(iv);
		
		cipher.init(Cipher.ENCRYPT_MODE, key, spec);
		byte[] encrypted = cipher.doFinal(plainText.getBytes());
		
		byte[] combined = new byte[encrypted.length + iv.length];
		System.arraycopy(iv, 0, combined, 0, iv.length);
		System.arraycopy(encrypted, 0, combined, iv.length, encrypted.length);
		
		return(Base64.getEncoder().encodeToString(combined));
	}
	
	public static String decrypt(String encodedPassword, SecretKey key) throws Exception{
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		byte[] combined = Base64.getDecoder().decode(encodedPassword);
		byte[] iv = new byte[16];
		byte[] encrypted = new byte[combined.length-iv.length];
		System.arraycopy(combined, 0, iv, 0, iv.length);  
		System.arraycopy(combined, iv.length, encrypted, 0, encrypted.length); 
		
		IvParameterSpec ivSpec = new IvParameterSpec(iv);
		cipher.init(cipher.DECRYPT_MODE, key, ivSpec);
		
		byte[] decrypted = new byte[encrypted.length];
		decrypted = cipher.doFinal(encrypted);
		
		return (new String(decrypted, StandardCharsets.UTF_8));
	}
	
	public byte[] CreateSalt() {
		byte[] salt = new byte[16];
		SecureRandom sr = new SecureRandom();
		sr.nextBytes(salt);
		return salt;
	}
	
	private byte[] getOrCreateSalt() throws SQLException {
		byte[] salt;
		if(dbManger.getSalt()!=null) {
			salt = Base64.getDecoder().decode(dbManger.getSalt());
		} else {
			salt = CreateSalt();
			dbManger.setSalt(Base64.getEncoder().encodeToString(salt));
		}
		return salt;
	}
}