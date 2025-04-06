package database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbManager {
    String path = "src/main/resources/database.db";
    
    // Fetch all encrypted user passwords
    public List<EncryptedUserPw> getAllEncryptedUserPw() throws SQLException {
        List<EncryptedUserPw> allData = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:" + path);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT username, password, domain FROM user_passwords")) {
            
            while (rs.next()) {
                String name = rs.getString("username");
                String pw = rs.getString("password");
                String dom = rs.getString("domain");
                EncryptedUserPw userPw = new EncryptedUserPw(name, pw, dom);
                allData.add(userPw);
            }
        } catch (SQLException e) {
            System.err.println("Error accessing the database: " + e.getMessage());
            throw e; 
        }
        return allData;
    }
    
    // Fetch encrypted user passwords for a specific domain
    public List<EncryptedUserPw> getEncryptedUserPw(String domain) throws SQLException {
        List<EncryptedUserPw> encryptedUserPwList = new ArrayList<>();
        String sql = "SELECT username, password, domain FROM user_passwords WHERE domain = ?";
        
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:" + path);
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            
            stmt.setString(1, domain);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String un = rs.getString("username");
                String pw = rs.getString("password");
                String dom = rs.getString("domain");
                EncryptedUserPw encryptedUserPw = new EncryptedUserPw(un, pw, dom);
                encryptedUserPwList.add(encryptedUserPw);
            }
        } catch (SQLException e) {
            System.err.println("Error accessing the database: " + e.getMessage());
            throw e;
        }
        return encryptedUserPwList;
    }
    
    // Get the encrypted master key
    public String getHashedMasterPassword() throws SQLException {
        String masterKey = null;
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:" + path);
             Statement stmt = conn.createStatement()) {
            
            ResultSet rs = stmt.executeQuery("SELECT password FROM master");
            if (rs.next()) {
                masterKey = rs.getString("password");
            }
        }
        return masterKey;
    }
    
    // Set the encrypted master key if it doesn't exist
    public void setHashedMasterPassword(String key) throws SQLException {
        if (getHashedMasterPassword() != null) {
            throw new SQLException("Master Key already exists");
        }
        
        String sql = "INSERT INTO master (password) VALUES (?)";
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:" + path);
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            
            stmt.setString(1, key);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error setting master key" + e.getMessage());
            throw e;
        }
    }

    // Get the salt from the database
    public String getSalt() throws SQLException {
        String salt = null;
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:" + path);
             Statement stmt = conn.createStatement()) {
            
            ResultSet rs = stmt.executeQuery("SELECT * FROM salt");
            if (rs.next()) {
                salt = rs.getString("Salt");
                if (rs.next()) {
                    throw new SQLException("Multiple rows found in the 'salt' table, expected one row.");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving salt from the database: " + e.getMessage());
            throw e;
        }
        return salt; // Returns null if no salt exists
    }

    // Set the salt in the database if it doesn't exist
    public void setSalt(String salt) throws SQLException {
        if (getSalt() != null) {
            System.err.println("Error: Salt already exists");
            return;
        }
        
        String sql = "INSERT INTO salt (salt) VALUES (?)";
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:" + path);
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            
            stmt.setString(1, salt);
            stmt.executeUpdate();
            System.out.println("Salt has been successfully set.");
        } catch (SQLException e) {
            System.err.println("Error setting salt in the database: " + e.getMessage());
            throw e;
        }
    }
    
    public void wipeDB () {
    	try (Connection connection = DriverManager.getConnection("jdbc:sqlite:" + path);
    			Statement stmt = connection.createStatement()){
    		connection.setAutoCommit(false);
    		stmt.execute("DELETE FROM master");
    		stmt.execute("DELETE FROM salt");
    		stmt.execute("DELETE FROM user_passwords");
    		connection.commit();
    		
    	} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
