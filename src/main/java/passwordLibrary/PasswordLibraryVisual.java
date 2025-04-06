package passwordLibrary;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PasswordLibraryVisual extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton addButton;
    private JButton removeButton;
    
    public PasswordLibraryVisual() throws Exception {
        // Set window properties
        setTitle("Password Library");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(615, 400);
        setLocationRelativeTo(null); // Center the window on the screen
        getContentPane().setBackground(new Color(240, 248, 255)); // Light blue background color
        setLayout(null); // Use absolute positioning
        
        // Title label
        JLabel lblTitle = new JLabel("Password Library", JLabel.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitle.setForeground(new Color(0, 102, 204)); // Soft blue color
        lblTitle.setBounds(0, 10, 600, 30);
        add(lblTitle);
        
        // Create table model with columns: Domain, Username, Password
        String[] columnNames = {"Domain", "Username", "Password"};
        tableModel = new DefaultTableModel(columnNames, 0);
        
        // Create the table using the model
        table = new JTable(tableModel);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        
        // Create a scroll pane for the table and set its bounds
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20, 50, 560, 250);
        add(scrollPane);
        
        // Create the Add button (green)
        addButton = new JButton("Add");
        addButton.setFont(new Font("Arial", Font.BOLD, 16));
        addButton.setForeground(Color.WHITE);
        addButton.setBackground(new Color(0, 153, 0)); // Green color
        addButton.setFocusPainted(false);
        addButton.setBounds(180, 320, 100, 30);
        add(addButton);
        
        // Create the Remove button (red)
        removeButton = new JButton("Remove");
        removeButton.setFont(new Font("Arial", Font.BOLD, 16));
        removeButton.setForeground(Color.WHITE);
        removeButton.setBackground(new Color(204, 0, 0)); // Red color
        removeButton.setFocusPainted(false);
        removeButton.setBounds(320, 320, 100, 30);
        add(removeButton);
   
        setVisible(true);
        
        PasswordLibraryUtils utils = new PasswordLibraryUtils();
        PasswordLibraryController controller = new PasswordLibraryController(this, utils);
    }
    
    // Helper method to add a new entry (row) to the table
    public void addEntry(String domain, String username, String password) {
        tableModel.addRow(new Object[]{domain, username, password});
    }
    
    // Getters for the buttons to add action listeners later
    public JButton getAddButton() {
        return addButton;
    }
    
    public JTable getTable () {
    	return table;
    }
    
    public void setTableModel(DefaultTableModel tableModel) {
    	this.tableModel = tableModel;
    }
    
    public JButton getRemoveButton() {
        return removeButton;
    }
}
