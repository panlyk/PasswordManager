package database;

public class EncryptedUserPw {
	String username;
	String password;
	String domain;
	
	public EncryptedUserPw(String un, String pw, String dom) {
		this.username = un;
		this.password = pw;
		this.domain = dom;
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public String getDomain() {
		return domain;
	}
 
}
