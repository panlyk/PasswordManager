package launchPage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import passwordLibrary.PasswordLibraryVisual;

public class LaunchPageController {
	private LaunchPageVisual visual;
	private LaunchPageUtils utils;
	
	
	public LaunchPageController(LaunchPageVisual visual) throws Exception {
		this.visual = visual;
		this.utils = new LaunchPageUtils();
		initialize();
	}
	
    private void initialize() throws Exception {
        // Check if the master key exists and update the view accordingly
        if (utils.masterKeyExists()) {
            visual.updateUnlockButton("Unlock Vault", 110, 120, 160, 30);
        } else {
        	visual.updateUnlockButton("Create Master Key", 90, 120, 200, 30);
        }
        
        //add listeners
        visual.addUnlockVaultButtonListener(e-> {
			try {
				handleUnlockVaultAction();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
        
        //setup the Reset Button
        visual.addResetDbButtonListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int choice = JOptionPane.showConfirmDialog(null, "This will reset ALL PASSWORDS. This action cannot be undone",
						"Confirm Reset",
						JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
				
				if(choice == JOptionPane.YES_OPTION) {
					utils.ResetDB();
					visual.createMessage("Vault has been reset!");
		        	visual.updateUnlockButton("Create Master Key", 90, 120, 200, 30);
				}
				
			}
		});
        
    }
    
    private void handleUnlockVaultAction() throws Exception {
    	if (utils.masterKeyExists()) {
    		//attempt login with password input
    		Boolean correctPassword = utils.attemptLogin(visual.getPassword());
    		
    		if(correctPassword) {
    			visual.createMessage("Correct Password!");
    			visual.closePage();
    			PasswordLibraryVisual libraryPage = new PasswordLibraryVisual();
    		}
    		else {
				visual.createErrorMessage("Incorrect Password!");
			}
    		
    	}
    	else {
    		String pw = visual.getPassword();
    		if (pw.length()>5) {
    			utils.createMasterKeyAction(pw);
    			visual.closePage();
    			PasswordLibraryVisual libraryPage = new PasswordLibraryVisual();
    		} else {
    			visual.createErrorMessage("Password should be longer than 5 digits");
    		}
    		
    	}
    }
    
    private void ResetDB() {
    	utils.ResetDB();
    }
    

    
    
}
