package launchPage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LaunchPageVisual extends JFrame {

    
    private JTextField textFieldUsername;
    private JPasswordField passwordField;
    private JButton btnUnlockVault;  // Declare btnUnlockVault as an instance variable
    private JButton resetButton;

    public LaunchPageVisual() throws Exception {
        // Set window properties
        setTitle("Password Manager");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null); // Center the window on the screen
        getContentPane().setBackground(new Color(240, 248, 255)); // Light blue background color
        setLayout(null); // Disable layout manager for absolute positioning

        // Title label for "Password Manager"
        JLabel lblTitle = new JLabel("Password Manager", JLabel.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitle.setForeground(new Color(0, 102, 204)); // Soft blue color
        // Center the title label horizontally
        lblTitle.setBounds(40, 20, 300, 30); 
        add(lblTitle);

        // Password label and text field
        JLabel lblPassword = new JLabel("Password: ");
        lblPassword.setFont(new Font("Arial", Font.PLAIN, 14));
        lblPassword.setForeground(new Color(50, 50, 50)); // Dark text color
        // Center the password label horizontally and position vertically with padding
        lblPassword.setBounds(80, 80, 80, 25); 
        add(lblPassword);

        // Password field (password input with asterisks)
        passwordField = new JPasswordField(20); // Use JPasswordField
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordField.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1)); // Soft border
        // Center the password field horizontally with the label and position vertically with padding
        passwordField.setBounds(170, 80, 120, 25); 
        add(passwordField);

        // Unlock Vault button
        btnUnlockVault = new JButton("");  // Initialize the button
        btnUnlockVault.setFont(new Font("Arial", Font.BOLD, 16));
        btnUnlockVault.setForeground(Color.WHITE);
        btnUnlockVault.setBackground(new Color(0, 102, 204)); // Soft blue background color
        btnUnlockVault.setFocusPainted(false); // Remove focus highlight on button press
        // Center the button horizontally and position vertically below the password field
        btnUnlockVault.setBounds(110, 120, 160, 30); 
        add(btnUnlockVault);
        
        resetButton = new JButton("Reset Vault");  // Initialize the button
        resetButton.setFont(new Font("Arial", Font.BOLD, 16));
        resetButton.setForeground(Color.WHITE);
        resetButton.setBackground(new Color(0, 102, 204)); // Soft blue background color
        resetButton.setFocusPainted(false); // Remove focus highlight on button press
        // Center the button horizontally and position vertically below the password field
        resetButton.setBounds(110, 160, 160, 30); 
        add(resetButton);

        setVisible(true); // Show the frame
        LaunchPageController controller = new LaunchPageController(this);
    }


    
    public void updateUnlockButton(String text, int x, int y, int width, int height) {
    	btnUnlockVault.setText(text);
    	btnUnlockVault.setBounds(x, y, width, height);
    }
    
    public void addUnlockVaultButtonListener(ActionListener listener) {
    	btnUnlockVault.addActionListener(listener);
    }
    
    public void addResetDbButtonListener(ActionListener listener) {
    	resetButton.addActionListener(listener);
    }
    
    public String getPassword() {
        // Convert the char array to a String
        return new String(passwordField.getPassword());
    }
    
    public void createMessage(String message) {
    	JOptionPane.showMessageDialog(null, message);
    }
    
    public void createErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
    
    public void closePage() {
    	this.dispose();
    }
    

    
}
