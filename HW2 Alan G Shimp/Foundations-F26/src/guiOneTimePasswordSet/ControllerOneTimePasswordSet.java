package guiOneTimePasswordSet;

import database.Database;
import entityClasses.User;
import javafx.animation.PauseTransition;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import passwordValidator.PasswordValidator;

/*******
 * <p>
 * Title: PasswordChange Class.
 * </p>
 * 
 * <p>
 * Description: The Java/FX-based Admin User One Time Password Change Page. This
 * class provides the
 * controller
 * actions basic on the user's use of the JavaFX GUI widgets defined by the View 
 * class.
 * 
 * This controller determines if the log in is valid. If so set up the link to
 * the database,
 * determines how many roles this user is authorized to play, and the calls one
 * the of the array of
 * role home pages if there is only one role. If there are more than one role,
 * it setup up and
 * calls the multiple roles dispatch page for the user to determine which role
 * the user wants to
 * play.
 * 
 * The class has been written assuming that the View or the Model are the only
 * class methods that
 * can invoke these methods. This is why each has been declared at "protected".
 * Do not change any
 * of these methods to public.
 * </p>
 */

public class ControllerOneTimePasswordSet {

	/*-********************************************************************************************
	
	The User Interface Actions for this page
	
	This controller is not a class that gets instantiated.  Rather, it is a collection of protected
	static methods that can be called by the View (which is a singleton instantiated object) and 
	the Model is often just a stub, or will be a singleton instantiated object.
	
	*/

	private static String text_Password1 = "";
	private static String text_Password2 = "";

	private static PauseTransition password2InactivityTimer = new PauseTransition(Duration.seconds(3));
	static {
		password2InactivityTimer.setOnFinished(_ -> checkPasswordMatch());
	}

	/**
	 * Default constructor is not used.
	 */
	public ControllerOneTimePasswordSet() {
	}

	private static Database theDatabase = applicationMain.FoundationsMain.database;

	/**********
	 * <p>
	 * Method: public doPasswordSet()
	 * </p>
	 * 
	 * <p>
	 * Description: This method is called when the user has clicked on the Password
	 * Change button. This
	 * method checks the first and second passwords to see if they are valid. If so,
	 * it updates
	 * the password and exits out of the application.
	 * 
	 */
	protected static void doPasswordSet(Stage theStage, User adminUser) {
		String username = ViewOneTimePasswordSet.text_username.getText();
		String newPassword = ViewOneTimePasswordSet.text_password.getText();
		String newPassword2 = ViewOneTimePasswordSet.text_password2.getText();

		if (theDatabase.getUserAccountDetails(username) == false) {
			ViewOneTimePasswordSet.alertUserPasswordError.setContentText(
					"Invalid Username. Try again!");
			ViewOneTimePasswordSet.alertUserPasswordError.showAndWait();
			return;
		}
		// System.out.println("*** Password is valid for this user");

		if (newPassword.compareTo(newPassword2) != 0) {
			ViewOneTimePasswordSet.alertUserPasswordError.setContentText(
					"Passwords Don't Match! Try again!");
			ViewOneTimePasswordSet.alertUserPasswordError.showAndWait();
			return;
		}

		String passwordErr = PasswordValidator.evaluatePassword(newPassword);
		if (!passwordErr.equals("")) {
			ViewOneTimePasswordSet.label_PasswordsDoNotMatch.setTextFill(Color.RED);
			ViewOneTimePasswordSet.label_PasswordsDoNotMatch
					.setText("Password does not meet all requirements.");
			return;
		}

		password2InactivityTimer.stop();

		theDatabase.oneTimePasswordSet(username, newPassword);

		// Return to admin stage once one time password set
		guiAdminHome.ViewAdminHome.displayAdminHome(theStage, adminUser);
	}

	/**********
	 * <p>
	 * Method: setAdminPassword1()
	 * </p>
	 * 
	 * <p>
	 * Description: This method is called when the user adds text to the password 1
	 * field in
	 * the View. A private local copy of what was last entered is kept here.
	 * </p>
	 * 
	 */
	protected static void setPassword1() {
		text_Password1 = ViewOneTimePasswordSet.text_password.getText();
		ViewOneTimePasswordSet.label_PasswordsDoNotMatch.setText("");
		validatePassword();
	}

	/**********
	 * <p>
	 * Method: setAdminPassword2()
	 * </p>
	 * 
	 * <p>
	 * Description: This method is called when the user adds text to the password 2
	 * field in
	 * the View. A private local copy of what was last entered is kept here.
	 * </p>
	 * 
	 */
	protected static void setPassword2() {
		text_Password2 = ViewOneTimePasswordSet.text_password2.getText();
		password2InactivityTimer.playFromStart();
	}

	/**********
	 * <p>
	 * Method: handlePassword2FocusChange()
	 * </p>
	 * <p>
	 * Description: Handles focus changes on password field 2. When the field loses
	 * focus
	 * (user exits the field), any pending 3-second timer is stopped and
	 * checkPasswordMatch()
	 * runs immediately.
	 * </p>
	 */
	protected static void handlePassword2FocusChange(boolean isFocused) {
		if (!isFocused) {
			password2InactivityTimer.stop();
			checkPasswordMatch();
		}
	}

	/*****
	 * <p>
	 * Method: checkPasswordMatch()
	 * </p>
	 * <p>
	 * Description: Verifies whether the confirmation password matches the primary
	 * password and updates ViewOneTimePasswordSet.label_PasswordsDoNotMatch
	 * accordingly.
	 * </p>
	 */
	private static void checkPasswordMatch() {
		if (text_Password2.isEmpty()) {
			ViewOneTimePasswordSet.label_PasswordsDoNotMatch.setText("");
			return;
		}
		if (text_Password1.compareTo(text_Password2) == 0) {
			ViewOneTimePasswordSet.label_PasswordsDoNotMatch.setTextFill(Color.GREEN);
			ViewOneTimePasswordSet.label_PasswordsDoNotMatch.setText("The two passwords match.");
		} else {
			ViewOneTimePasswordSet.label_PasswordsDoNotMatch.setTextFill(Color.RED);
			ViewOneTimePasswordSet.label_PasswordsDoNotMatch
					.setText("The two passwords do not match. Please try again!");
		}
	}

	/*****
	 * <p>
	 * Method: validatePassword()
	 * </p>
	 * <p>
	 * Description: Performs password validation by calling
	 * PasswordValidator.evaluatePassword()
	 * and updating labels and error messages on ViewOneTimePasswordSet.
	 * </p>
	 */
	private static void validatePassword() {
		ViewOneTimePasswordSet.resetAssessments();
		if (text_Password1.isEmpty()) {
			ViewOneTimePasswordSet.validPassword.setText("");
			return;
		}
		String errMessage = PasswordValidator.evaluatePassword(text_Password1);
		updateFlags();

		if (!errMessage.equals("")) {
			ViewOneTimePasswordSet.validPassword.setTextFill(Color.RED);
			ViewOneTimePasswordSet.validPassword
					.setText("Failure! The password does not meet all requirements.");
		} else {
			ViewOneTimePasswordSet.validPassword.setTextFill(Color.GREEN);
			ViewOneTimePasswordSet.validPassword
					.setText("Success! The password satisfies the requirements.");
		}
	}

	/**********
	 * <p>
	 * Method: updateFlags()
	 * </p>
	 * <p>
	 * Description: Reads boolean flags from PasswordValidator and updates the
	 * checklist
	 * in ViewOneTimePasswordSet with green text.
	 * </p>
	 */
	private static void updateFlags() {
		if (PasswordValidator.foundUpperCase) {
			ViewOneTimePasswordSet.label_UpperCase.setText("At least one upper case letter - Satisfied");
			ViewOneTimePasswordSet.label_UpperCase.setTextFill(Color.GREEN);
		}
		if (PasswordValidator.foundLowerCase) {
			ViewOneTimePasswordSet.label_LowerCase.setText("At least one lower case letter - Satisfied");
			ViewOneTimePasswordSet.label_LowerCase.setTextFill(Color.GREEN);
		}
		if (PasswordValidator.foundNumericDigit) {
			ViewOneTimePasswordSet.label_NumericDigit.setText("At least one numeric digit - Satisfied");
			ViewOneTimePasswordSet.label_NumericDigit.setTextFill(Color.GREEN);
		}
		if (PasswordValidator.foundSpecialChar) {
			ViewOneTimePasswordSet.label_SpecialChar.setText("At least one special character - Satisfied");
			ViewOneTimePasswordSet.label_SpecialChar.setTextFill(Color.GREEN);
		}
		if (PasswordValidator.foundCorrectLength) {
			ViewOneTimePasswordSet.label_CorrectLength.setText("Between 8 and 58 characters - Satisfied");
			ViewOneTimePasswordSet.label_CorrectLength.setTextFill(Color.GREEN);
		}
	}
}
