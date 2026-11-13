package guiFirstAdmin;

import java.sql.SQLException;

import database.Database;
import entityClasses.User;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import userNameRecognizer.UserNameRecognizer;
import passwordValidator.PasswordValidator;
import javafx.animation.PauseTransition;
import javafx.util.Duration;

/*******
 * <p> Title: ControllerFirstAdmin Class. </p>
 * 
 * <p> Description: ControllerFirstAdmin class provides the controller actions based on the user's
 *  use of the JavaFX GUI widgets defined by the View class.
 * 
 * This page contains a number of buttons that have not yet been implemented.  WHhen those buttons
 * are pressed, an alert pops up to tell the user that the function associated with the button has
 * not been implemented. Also, be aware that What has been implemented may not work the way the
 * final product requires and there maybe defects in this code.
 * 
 * The class has been written assuming that the View or the Model are the only class methods that
 * can invoke these methods.  This is why each has been declared at "protected".  Do not change any
 * of these methods to public.</p>
 * 
 * <p> Copyright: Lynn Robert Carter © 2025 </p>
 * 
 * @author Lynn Robert Carter
 * 
 * @version 1.1		2026-09-13 Updated version to add password validation - Alexander Robert Murray
 *  
 */

@SuppressWarnings("unused")
public class ControllerFirstAdmin {
	/*-********************************************************************************************

	The controller attributes for this page
	
	This controller is not a class that gets instantiated.  Rather, it is a collection of protected
	static methods that can be called by the View (which is a singleton instantiated object) and 
	the Model is often just a stub, or will be a singleton instantiated object.
	
	*/
	
	private static String adminUsername = "";
	private static String adminPassword1 = "";
	private static String adminPassword2 = "";		
	protected static Database theDatabase = applicationMain.FoundationsMain.database;	
	
	private static PauseTransition password2InactivityTimer = new PauseTransition(Duration.seconds(3));
	static {
		password2InactivityTimer.setOnFinished(event -> checkPasswordMatch());
	}

	/*-********************************************************************************************

	The User Interface Actions for this page
	
	*/
	
	/**
	 * Default constructor is not used.
	 */
	public ControllerFirstAdmin() {
	}

	/**********
	 * <p> Method: setAdminUsername() </p>
	 * 
	 * <p> Description: This method is called when the user adds text to the username field in the
	 * View.  A private local copy of what was last entered is kept here.</p>
	 * 
	 */
	protected static void setAdminUsername() {
		adminUsername = ViewFirstAdmin.text_AdminUsername.getText();
	}
	
	
	/**********
	 * <p> Method: setAdminPassword1() </p>
	 * 
	 * <p> Description: This method is called when the user adds text to the password 1 field in
	 * the View.  A private local copy of what was last entered is kept here.</p>
	 * 
	 */
	protected static void setAdminPassword1() {
		adminPassword1 = ViewFirstAdmin.text_AdminPassword1.getText();
		ViewFirstAdmin.label_PasswordsDoNotMatch.setText("");
		validatePassword();
	}
	
	
	/**********
	 * <p> Method: setAdminPassword2() </p>
	 * 
	 * <p> Description: This method is called when the user adds text to the password 2 field in
	 * the View.  A private local copy of what was last entered is kept here.</p>
	 * 
	 */
	protected static void setAdminPassword2() {
		adminPassword2 = ViewFirstAdmin.text_AdminPassword2.getText();		
		password2InactivityTimer.playFromStart();	
	}
	
	/**********
	* <p> Method: handlePassword2FocusChange() </p>
	* <p> Description: Handles focus changes on password field 2. When the field loses focus
	* (user exits the field), any pending 3-second timer is stopped and checkPasswordMatch()
	* runs immediately.</p>
	*/
	protected static void handlePassword2FocusChange(boolean isFocused) {
		if (!isFocused) {
			password2InactivityTimer.stop();
			checkPasswordMatch();
		}
	}
	
	/*****
	 *  <p> Method: checkPasswordMatch() </p>
	 *  <p> Description: Verifies whether the confirmation password matches the primary
	 *  password and updates ViewFirstAdmin.label_PasswordsDoNotMatch accordingly.</p>
	 */
	private static void checkPasswordMatch() {
		if (adminPassword2.isEmpty()) {
			ViewFirstAdmin.label_PasswordsDoNotMatch.setText("");
			return;
		}
		if (adminPassword1.compareTo(adminPassword2) == 0) {
			ViewFirstAdmin.label_PasswordsDoNotMatch.setTextFill(Color.GREEN);
			ViewFirstAdmin.label_PasswordsDoNotMatch.setText("The two passwords match.");
		} else {
			ViewFirstAdmin.label_PasswordsDoNotMatch.setTextFill(Color.RED);
			ViewFirstAdmin.label_PasswordsDoNotMatch.setText("The two passwords do not match. Please try again!");
		}
	}
	
	
	/**********
	 * <p> Method: doSetupAdmin() </p>
	 * 
	 * <p> Description: This method is called when the user presses the button to set up the Admin
	 * account.  It start by trying to establish a new user and placing that user into the
	 * database.  If that is successful, we proceed to the UserUpdate page.</p>
	 * 
	 */
	protected static void doSetupAdmin(Stage ps, int r) {
		
		// Checking username setup
		String usernameErrorMessage = UserNameRecognizer.checkForValidUserName(adminUsername);

		if (!usernameErrorMessage.isEmpty()) {

			// Show the username error message
			ViewFirstAdmin.alertUsernameError.setContentText(usernameErrorMessage);
			ViewFirstAdmin.alertUsernameError.showAndWait();

			// Stop account creation 
			return;
		}
		// Verify password satisfies PasswordValidator requirements before proceeding
		String passwordErr = PasswordValidator.evaluatePassword(adminPassword1);
		if (!passwordErr.equals("")) {
			ViewFirstAdmin.label_PasswordsDoNotMatch.setTextFill(Color.RED);
			ViewFirstAdmin.label_PasswordsDoNotMatch.setText("Password does not meet all requirements.");
			return;
		}
		
		password2InactivityTimer.stop();
		
		// Make sure the two passwords are the same
		if (adminPassword1.compareTo(adminPassword2) == 0) {
        	// Create the passwords and proceed to the user home page
        	User user = new User(adminUsername, adminPassword1, "", "", "", "", "", true, false, 
        			false);
            try {
            	// Create a new User object with admin role and register in the database
            	theDatabase.register(user);
            	}
            catch (SQLException e) {
                System.err.println("*** ERROR *** Database error trying to register a user: " + 
                		e.getMessage());
                e.printStackTrace();
                System.exit(0);
            }
            
            // User was established in the database, so navigate to the User Update Page
        	guiUserUpdate.ViewUserUpdate.displayUserUpdate(ViewFirstAdmin.theStage, user);
		}
	}
	
	/*****
	 *  <p> Method: validatePassword() </p>
	 *  <p> Description: Performs password validation by calling PasswordValidator.evaluatePassword()
	 *  and updating labels and error messages on ViewFirstAdmin.</p>
	 */
	private static void validatePassword() {
		ViewFirstAdmin.resetAssessments();
		if (adminPassword1.isEmpty()) {
			ViewFirstAdmin.validPassword.setText("");
			return;
		}
		String errMessage = PasswordValidator.evaluatePassword(adminPassword1);
		updateFlags();
		
		if (!errMessage.equals("")) {
			ViewFirstAdmin.validPassword.setTextFill(Color.RED);
			ViewFirstAdmin.validPassword.setText("Failure! The password does not meet all requirements.");
		} else {
			ViewFirstAdmin.validPassword.setTextFill(Color.GREEN);
			ViewFirstAdmin.validPassword.setText("Success! The password satisfies the requirements.");
		}
	}
	
	/**********
	 * <p> Method: updateFlags() </p>
	 * <p> Description: Reads boolean flags from PasswordValidator and updates the checklist
	 * in ViewFirstAdmin with green text.</p>
	 */
	private static void updateFlags() {
		if (PasswordValidator.foundUpperCase) {
			ViewFirstAdmin.label_UpperCase.setText("At least one upper case letter - Satisfied");
			ViewFirstAdmin.label_UpperCase.setTextFill(Color.GREEN);
		}
		if (PasswordValidator.foundLowerCase) {
			ViewFirstAdmin.label_LowerCase.setText("At least one lower case letter - Satisfied");
			ViewFirstAdmin.label_LowerCase.setTextFill(Color.GREEN);
		}
		if (PasswordValidator.foundNumericDigit) {
			ViewFirstAdmin.label_NumericDigit.setText("At least one numeric digit - Satisfied");
			ViewFirstAdmin.label_NumericDigit.setTextFill(Color.GREEN);
		}
		if (PasswordValidator.foundSpecialChar) {
			ViewFirstAdmin.label_SpecialChar.setText("At least one special character - Satisfied");
			ViewFirstAdmin.label_SpecialChar.setTextFill(Color.GREEN);
		}
		if (PasswordValidator.foundCorrectLength) {
			ViewFirstAdmin.label_CorrectLength.setText("Between 8 and 58 characters - Satisfied");
			ViewFirstAdmin.label_CorrectLength.setTextFill(Color.GREEN);
		}
	}	
	
	/**********
	 * <p> Method: performQuit() </p>
	 * 
	 * <p> Description: This method terminates the execution of the program.  It leaves the
	 * database in a state where the normal login page will be displayed when the application is
	 * restarted.</p>
	 * 
	 */
	protected static void performQuit() {
		System.out.println("Perform Quit");
		System.exit(0);
	}	
}

