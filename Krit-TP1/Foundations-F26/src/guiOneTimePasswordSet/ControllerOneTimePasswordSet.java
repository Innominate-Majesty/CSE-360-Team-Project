package guiOneTimePasswordSet;

import database.Database;
import entityClasses.User;
import javafx.stage.Stage;

/*******
 * <p> Title: PasswordChange Class. </p>
 * 
 * <p> Description: The Java/FX-based User Login Page.  This class provides the controller
 * actions basic on the user's use of the JavaFX GUI widgets defined by the View class.
 * 
 * This controller determines if the log in is valid.  If so set up the link to the database, 
 * determines how many roles this user is authorized to play, and the calls one the of the array of
 * role home pages if there is only one role.  If there are more than one role, it setup up and
 * calls the multiple roles dispatch page for the user to determine which role the user wants to
 * play.
 * 
 * The class has been written assuming that the View or the Model are the only class methods that
 * can invoke these methods.  This is why each has been declared at "protected".  Do not change any
 * of these methods to public.</p>
 * 
 * <p> Copyright: Lynn Robert Carter © 2025 </p>
 * 
 * @author Lynn Robert Carter
 * 
 * @version 1.00		2025-08-17 Initial version
 * @version 1.01		2025-09-16 Update Javadoc documentation *  
 */

public class ControllerOneTimePasswordSet {
	
	/*-********************************************************************************************

	The User Interface Actions for this page
	
	This controller is not a class that gets instantiated.  Rather, it is a collection of protected
	static methods that can be called by the View (which is a singleton instantiated object) and 
	the Model is often just a stub, or will be a singleton instantiated object.
	
	*/

	/**
	 * Default constructor is not used.
	 */
	public ControllerOneTimePasswordSet() {
	}

	private static Database theDatabase = applicationMain.FoundationsMain.database;
	
	/**********
	 * <p> Method: public doPasswordChange() </p>
	 * 
	 * <p> Description: This method is called when the user has clicked on the Password Change button. This
	 * method checks the first and second passwords to see if they are valid.  If so, it updates
	 * the password and exits out of the application.
	 * 
	 */	
	protected static void doPasswordSet(Stage theStage, User adminUser) {
		String username = ViewOneTimePasswordSet.text_username.getText();
		String newPassword = ViewOneTimePasswordSet.text_password.getText();
    	
		if (theDatabase.getUserAccountDetails(username) == false) {
			ViewOneTimePasswordSet.alertUserPasswordError.setContentText(
    				"Invalid Username. Try again!");
			ViewOneTimePasswordSet.alertUserPasswordError.showAndWait();
    		return;
    	}
		// System.out.println("*** Password is valid for this user");
		
    	theDatabase.oneTimePasswordSet(username, newPassword);
    	
    	//Return to admin stage once one time password set
    	guiAdminHome.ViewAdminHome.displayAdminHome(theStage, adminUser);
	}
}
