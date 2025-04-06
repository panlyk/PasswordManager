package passwordLibrary;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.util.List;

public class PasswordLibraryController {
	
	private PasswordLibraryVisual visual;
	private PasswordLibraryUtils utils;
	
	public PasswordLibraryController(PasswordLibraryVisual visual, PasswordLibraryUtils utils) throws Exception {
		this.visual = visual;
		this.utils = utils;

		initialize();
	}
	
    public void showAddPasswordDialog() throws Exception {
        // Create input fields
        JTextField domainField = new JTextField();
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();

        // Create a panel with a GridLayout for input fields and labels
        JPanel panel = new JPanel(new GridLayout(0, 1, 5, 5));
        panel.add(new JLabel("Domain:"));
        panel.add(domainField);
        panel.add(new JLabel("Username:"));
        panel.add(usernameField);
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);
        panel.setVisible(true);

        // Show the panel in a confirm dialog
        int result = JOptionPane.showConfirmDialog(
                null, panel, "Add Password", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        // Process the input if the user clicks OK
        if (result == JOptionPane.OK_OPTION) {
            String domain = domainField.getText().trim();
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();

            // Simple validation to ensure all fields are filled
            if (domain.isEmpty() || username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(null, "All fields must be filled!", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Add the entry to the visual table
            visual.addEntry(domain, username, password);
            utils.addPasswordEntry(domain, username, password);
        }
    }
    
    private void initializeTable() throws Exception {
    	String[] columnNames = {"Domain", "Username", "Password"};
    	DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
    		@Override
    	    public boolean isCellEditable(int row, int column) {
    	        return false; // All cells are not editable
    	    }
    	};
    	List<Object[]> rowsList = utils.getAllExistingPasswordsForTable();
    	for (Object[] row : rowsList) {
    		model.addRow(row);
    	}
		JTable table = visual.getTable();
		visual.setTableModel(model);
		table.setModel(model);
    }
    
    private void initializeAddButton() {
		visual.getAddButton().addActionListener(e->{
			try {
				showAddPasswordDialog();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
    }
    
    private void initializeRemoveButton(){
    	visual.getRemoveButton().addActionListener(e->{
        	int confirm = JOptionPane.showConfirmDialog(
        			null,
        			"Are you sure you want to remove this entry?",
        			"Confirm Deletion",
        			JOptionPane.YES_NO_OPTION,
        			JOptionPane.WARNING_MESSAGE
        			);
        	
        	if (confirm == JOptionPane.YES_OPTION) {
        		removeSelectedRowFromTable();
        	} else {
        		return;
        	}
    	});
    }
    
    private void initialize() throws Exception {
    	initializeAddButton();
    	initializeRemoveButton();
    	initializeTable();
    }
    
    private void removeSelectedRowFromTable() {
		JTable table = visual.getTable();
		int rowIndex = table.getSelectedRow();
		String domain  = table.getValueAt(rowIndex, 0).toString();
    	String username = table.getValueAt(rowIndex, 1).toString();
    	try {
			utils.removeSelectedPassword(domain, username);
			DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
			tableModel.removeRow(rowIndex);
			table.setModel(tableModel);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    }

}
