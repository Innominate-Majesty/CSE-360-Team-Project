package guiPasswordChange;

import database.Database;
import guiUserLogin.ViewUserLogin;
import javafx.animation.PauseTransition;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import passwordValidator.PasswordValidator;
import javafx.stage.Stage;

/*******
 * <p>
 * Title: PasswordChange Class.
 * </p>
 * 
 * <p>
 * Description: The Java/FX-based User Login Page. This class provides the
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
 * 
 * <p>
 * Copyright: Lynn Robert Carter © 2025
 * </p>
 * 
 * @author Lynn Robert Carter
 * 
 * @version 1.00 2025-08-17 Initial version
 * @version 1.01 2025-09-16 Update Javadoc documentation *
 */

public class ControllerPasswordChange {

	/*-********************************************************************************************
	
	The User Interface Actions for this page
	
	This controller is not a class that gets instantiated.  Rather, it is a collection of protected
	static methods that can be called by the View (which is a singleton instantiated object) and 
	the Model is often just a stub, or will be a singleton instantiated object.
	
	*/

	private static Database theDatabase = applicationMain.FoundationsMain.database;

	// Cached input strings and 3-second debounce timer
	private static String password1 = "";
	private static String password2 = "";

	private static PauseTransition password2InactivityTimer = new PauseTransition(Duration.seconds(3));
	static {
		password2InactivityTimer.setOnFinished(_ -> checkPasswordMatch());
	}

	/**
	 * Default constructor is not used.
	 */
	public ControllerPasswordChange() {
	}

	/**********
	 * <p>
	 * Method: setPassword1()
	 * </p>
	 * 
	 * <p>
	 * Description: Handles real-time updates when text is entered into the first
	 * password field.
	 * Runs password validation and updates the matching status if the second field
	 * has text.
	 * </p>
	 */
	protected static void setPassword1() {
		password1 = ViewPasswordChange.text_first_Password.getText();
		ViewPasswordChange.label_PasswordsDoNotMatch.setText("");
		validatePassword();
	}

	/**********
	 * <p>
	 * Method: setPassword2()
	 * </p>
	 * 
	 * <p>
	 * Description: Handles text input in the confirmation password field by
	 * restarting
	 * the 3-second debounce timer.
	 * </p>
	 */
	protected static void setPassword2() {
		password2 = ViewPasswordChange.text_second_Password.getText();
		password2InactivityTimer.playFromStart();
	}

	/**********
	 * <p>
	 * Method: handlePassword2FocusChange()
	 * </p>
	 * 
	 * <p>
	 * Description: When focus shifts away from the second password field, stops any
	 * active timer
	 * and checks for matching passwords immediately.
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
	 * Description: Checks if the two password fields match and updates
	 * ViewPasswordChange.label_PasswordsDoNotMatch.
	 * </p>
	 */
	private static void checkPasswordMatch() {
		if (password2.isEmpty()) {
			ViewPasswordChange.label_PasswordsDoNotMatch.setText("");
			return;
		}
		if (password1.compareTo(password2) == 0) {
			ViewPasswordChange.label_PasswordsDoNotMatch.setTextFill(Color.GREEN);
			ViewPasswordChange.label_PasswordsDoNotMatch.setText("The two passwords match.");
		} else {
			ViewPasswordChange.label_PasswordsDoNotMatch.setTextFill(Color.RED);
			ViewPasswordChange.label_PasswordsDoNotMatch
					.setText("The two passwords do not match. Please try again!");
		}
	}

	/*****
	 * <p>
	 * Method: validatePassword()
	 * </p>
	 * <p>
	 * Description: Runs PasswordValidator.evaluatePassword() on password1 and
	 * updates the checklist.
	 * </p>
	 */
	private static void validatePassword() {
		ViewPasswordChange.resetAssessments();
		if (password1.isEmpty()) {
			ViewPasswordChange.validPassword.setText("");
			return;
		}
		String errMessage = PasswordValidator.evaluatePassword(password1);
		updateFlags();

		if (!errMessage.equals("")) {
			ViewPasswordChange.validPassword.setTextFill(Color.RED);
			ViewPasswordChange.validPassword
					.setText("Failure! The password does not meet all requirements.");
		} else {
			ViewPasswordChange.validPassword.setTextFill(Color.GREEN);
			ViewPasswordChange.validPassword.setText("Success! The password satisfies the requirements.");
		}
	}

	/**********
	 * <p>
	 * Method: updateFlags()
	 * </p>
	 * <p>
	 * Description: Updates the visual requirement labels based on PasswordValidator
	 * results.
	 * </p>
	 */
	private static void updateFlags() {
		if (PasswordValidator.foundUpperCase) {
			ViewPasswordChange.label_UpperCase.setText("At least one upper case letter - Satisfied");
			ViewPasswordChange.label_UpperCase.setTextFill(Color.GREEN);
		}
		if (PasswordValidator.foundLowerCase) {
			ViewPasswordChange.label_LowerCase.setText("At least one lower case letter - Satisfied");
			ViewPasswordChange.label_LowerCase.setTextFill(Color.GREEN);
		}
		if (PasswordValidator.foundNumericDigit) {
			ViewPasswordChange.label_NumericDigit.setText("At least one numeric digit - Satisfied");
			ViewPasswordChange.label_NumericDigit.setTextFill(Color.GREEN);
		}
		if (PasswordValidator.foundSpecialChar) {
			ViewPasswordChange.label_SpecialChar.setText("At least one special character - Satisfied");
			ViewPasswordChange.label_SpecialChar.setTextFill(Color.GREEN);
		}
		if (PasswordValidator.foundCorrectLength) {
			ViewPasswordChange.label_CorrectLength.setText("Between 8 and 58 characters - Satisfied");
			ViewPasswordChange.label_CorrectLength.setTextFill(Color.GREEN);
		}
	}

	/**********
	 * <p>
	 * Method: public doPasswordChange()
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
	protected static void doPasswordChange(Stage theStg, String username) {
		String firstPassword = ViewPasswordChange.text_first_Password.getText();
		String secondPassword = ViewPasswordChange.text_second_Password.getText();

		if (firstPassword.compareTo(secondPassword) != 0) {
			ViewPasswordChange.alertPasswordError.setContentText(
					"Passwords Don't Match. Try again!");
			ViewPasswordChange.alertPasswordError.showAndWait();
			return;
		}

		String passwordErr = PasswordValidator.evaluatePassword(secondPassword);
		if (!passwordErr.equals("")) {
			ViewPasswordChange.label_PasswordsDoNotMatch.setTextFill(Color.RED);
			ViewPasswordChange.label_PasswordsDoNotMatch.setText("Password does not meet all requirements.");
			return;
		}

		password2InactivityTimer.stop();

		// System.out.println("*** Password is valid for this user");

		theDatabase.passwordChange(username, firstPassword);

		System.out.println("Password Changed, Please Login Again");
		ViewUserLogin.displayUserLogin(theStg);
	}
}
