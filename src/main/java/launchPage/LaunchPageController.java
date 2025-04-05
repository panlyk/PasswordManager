package launchPage;

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
    }
    
    private void handleUnlockVaultAction() throws Exception {
    	if (utils.masterKeyExists()) {
    		//attempt login with password input
    		Boolean correctPassword = utils.attemptLogin(visual.getPassword());
    		
    		if(correctPassword) {
    			visual.createMessage("Correct Password!");
    		}
    		else {
				visual.createErrorMessage("Incorrect Password!");
			}
    		
    	}
    	else {
    		String pw = visual.getPassword();
    		if (pw.length()>5) {
    			utils.createMasterKeyAction(pw);
    		} else {
    			visual.createErrorMessage("Password should be longer than 5 digits");
    		}
    		
    	}
    }
    

    
    
}
