package guiPasswordChange;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.control.TextFormatter;

/*******
 * <p>
 * Title: ViewUserLogin Class.
 * </p>
 * 
 * <p>
 * Description: The Java/FX-based System Startup Page.
 * </p>
 * 
 * <p>
 * Copyright: Lynn Robert Carter © 2025
 * </p>
 * 
 * @author Lynn Robert Carter
 * 
 * @version 1.00 2025-04-20 Initial version
 * @version 1.1 2026-09-16 Updated to limit total characters for login and
 *          invitation code fields. - Alexander Robert Murray
 * 
 */

public class ViewPasswordChange {

	/*-********************************************************************************************
	
	Attributes
	
	 *********************************************************************************************/

	// These are the application values required by the user interface

	private static double width = applicationMain.FoundationsMain.WINDOW_WIDTH;
	private static double height = applicationMain.FoundationsMain.WINDOW_HEIGHT;

	private static Label label_ApplicationTitle = new Label("Change Password");

	protected static Alert alertPasswordError = new Alert(AlertType.INFORMATION);

	// private User user;
	protected static PasswordField text_first_Password = new PasswordField();
	protected static PasswordField text_second_Password = new PasswordField();
	private static Button button_Change_Password = new Button("Change Password");

	private static Stage theStage;
	private static String username;
	private static Pane theRootPane;
	public static Scene thePasswordChangeScene = null;

	private static ViewPasswordChange theView = null;

	// Password feedback widgets and match labels
	protected static Label label_PasswordsDoNotMatch = new Label();
	protected static Label validPassword = new Label();
	protected static Label label_Requirements = new Label(
			"A valid password must satisfy the following requirements:");
	protected static Label label_UpperCase = new Label();
	protected static Label label_LowerCase = new Label();
	protected static Label label_NumericDigit = new Label();
	protected static Label label_SpecialChar = new Label();
	protected static Label label_CorrectLength = new Label();

	/*-********************************************************************************************
	
	Constructor
	
	 *********************************************************************************************/

	public static void displayPasswordChange(Stage ps, String usNm) {

		// Establish the references to the GUI. There is no current user yet.
		theStage = ps;
		username = usNm;

		// If not yet established, populate the static aspects of the GUI
		if (theView == null)
			theView = new ViewPasswordChange();

		text_first_Password.setText("");
		text_second_Password.setText("");

		// Set the title for the window, display the page, and wait for the Admin to do
		// something
		theStage.setTitle("CSE 360 Foundation Code: Password Change");
		theStage.setScene(thePasswordChangeScene);
		theStage.show();
	}

	/**********
	 * <p>
	 * Method: ViewUserLoginPage()
	 * </p>
	 * 
	 * <p>
	 * Description: This method is called when the application first starts. It must
	 * handle
	 * two cases: 1) when no has been established and 2) when one or more users have
	 * been
	 * established.
	 * 
	 * If there are no users in the database, this means that the person starting
	 * the system jmust
	 * be an administrator, so a special GUI is provided to allow this Admin to set
	 * a username and
	 * password.
	 * 
	 * If there is at least one user, then a different display is shown for existing
	 * users to login
	 * and for potential new users to provide an invitation code and if it is valid,
	 * they are taken
	 * to a page where they can specify a username and password.
	 * </p>
	 * 
	 * @param ps      specifies the JavaFX Stage to be used for this GUI and it's
	 *                methods
	 * 
	 * @param theRoot specifies the JavaFX Pane to be used for this GUI and it's
	 *                methods
	 * 
	 * @param db      specifies the Database to be used by this GUI and it's methods
	 * 
	 */
	private ViewPasswordChange() {

		// Create the Pane for the list of widgets and the Scene for the window
		theRootPane = new Pane();
		thePasswordChangeScene = new Scene(theRootPane, width, height);

		// Populate the window with the title and other common widgets and set their
		// static state
		setupLabelUI(label_ApplicationTitle, "Arial", 32, width, Pos.CENTER, 0, 10);

		// Establish the text input operand field for the first password
		setupTextUI(text_first_Password, "Arial", 18, 300, Pos.BASELINE_LEFT, 50, 160, true);
		text_first_Password.setPromptText("Enter Password");
		applyLengthLimiter(text_first_Password, 40);
		text_first_Password.textProperty().addListener((_, _, _) -> {
			ControllerPasswordChange.setPassword1();
		});

		// Visual layout configuration for password validation
		setupLabelUI(label_Requirements, "Arial", 13, 400, Pos.BASELINE_LEFT, 50, 260);
		setupLabelUI(label_UpperCase, "Arial", 12, 350, Pos.BASELINE_LEFT, 65, 280);
		setupLabelUI(label_LowerCase, "Arial", 12, 350, Pos.BASELINE_LEFT, 65, 300);
		setupLabelUI(label_NumericDigit, "Arial", 12, 350, Pos.BASELINE_LEFT, 65, 320);
		setupLabelUI(label_SpecialChar, "Arial", 12, 350, Pos.BASELINE_LEFT, 65, 340);
		setupLabelUI(label_CorrectLength, "Arial", 12, 400, Pos.BASELINE_LEFT, 65, 360);
		setupLabelUI(validPassword, "Arial", 13, 350, Pos.BASELINE_LEFT, 50, 385);
		resetAssessments();

		// Establish the text input operand field for the second password
		setupTextUI(text_second_Password, "Arial", 18, 300, Pos.BASELINE_LEFT, 50, 210, true);
		text_second_Password.setPromptText("Enter Password Again");
		applyLengthLimiter(text_second_Password, 40);
		text_second_Password.textProperty().addListener((_, _, _) -> {
			ControllerPasswordChange.setPassword2();
		});
		text_second_Password.focusedProperty().addListener((_, _, isNowFocused) -> {
			ControllerPasswordChange.handlePassword2FocusChange(isNowFocused);
		});

		// Set up the Change Password button
		setupButtonUI(button_Change_Password, "Dialog", 18, 200, Pos.CENTER, 475, 180);
		button_Change_Password.setOnAction((_) -> {
			ControllerPasswordChange.doPasswordChange(theStage, username);
		});

		// Label to display whether the two passwords match
		setupLabelUI(label_PasswordsDoNotMatch, "Arial", 13, 300, Pos.BASELINE_LEFT, 50, 450);

		alertPasswordError.setTitle("Invalid passwords!");
		alertPasswordError.setHeaderText(null);

		// theRootPane.getChildren().clear();

		theRootPane.getChildren().addAll(
				label_ApplicationTitle,
				text_first_Password,
				text_second_Password,
				button_Change_Password, label_Requirements, label_UpperCase, label_LowerCase,
				label_NumericDigit, label_SpecialChar, label_CorrectLength, validPassword,
				label_PasswordsDoNotMatch);
	}

	/*-********************************************************************************************
	
	Helper methods to reduce code length
	
	 *********************************************************************************************/

	/*******
	 * <p>
	 * Title: resetAssessments - Resets widgets to their default state
	 * </p>
	 */
	public static void resetAssessments() {
		label_UpperCase.setText("At least one upper case letter - Not yet satisfied");
		label_UpperCase.setTextFill(Color.RED);

		label_LowerCase.setText("At least one lower case letter - Not yet satisfied");
		label_LowerCase.setTextFill(Color.RED);

		label_NumericDigit.setText("At least one numeric digit - Not yet satisfied");
		label_NumericDigit.setTextFill(Color.RED);

		label_SpecialChar.setText("At least one special character - Not yet satisfied");
		label_SpecialChar.setTextFill(Color.RED);

		label_CorrectLength.setText("Between 8 and 58 characters - Not yet satisfied");
		label_CorrectLength.setTextFill(Color.RED);
	}

	/**********
	 * Private local method to apply a maximum character length limiter to a text
	 * input field.
	 * 
	 * @param field     The TextField or PasswordField to limit
	 * @param maxLength The maximum allowed characters
	 */
	private void applyLengthLimiter(TextField field, int maxLength) {
		field.setTextFormatter(new TextFormatter<String>(change -> {
			if (change.getControlNewText().length() <= maxLength) {
				return change;
			}
			return null;
		}));
	}

	/**********
	 * Private local method to initialize the standard fields for a label
	 */

	private void setupLabelUI(Label l, String ff, double f, double w, Pos p, double x, double y) {
		l.setFont(Font.font(ff, f));
		l.setMinWidth(w);
		l.setAlignment(p);
		l.setLayoutX(x);
		l.setLayoutY(y);
	}

	/**********
	 * Private local method to initialize the standard fields for a button
	 * 
	 * @param b  The Button object to be initialized
	 * @param ff The font to be used
	 * @param f  The size of the font to be used
	 * @param w  The width of the Button
	 * @param p  The alignment (e.g. left, centered, or right)
	 * @param x  The location from the left edge (x axis)
	 * @param y  The location from the top (y axis)
	 */
	private void setupButtonUI(Button b, String ff, double f, double w, Pos p, double x, double y) {
		b.setFont(Font.font(ff, f));
		b.setMinWidth(w);
		b.setAlignment(p);
		b.setLayoutX(x);
		b.setLayoutY(y);
	}

	/**********
	 * Private local method to initialize the standard fields for a text field
	 */
	private void setupTextUI(TextField t, String ff, double f, double w, Pos p, double x, double y, boolean e) {
		t.setFont(Font.font(ff, f));
		t.setMinWidth(w);
		t.setMaxWidth(w);
		t.setAlignment(p);
		t.setLayoutX(x);
		t.setLayoutY(y);
		t.setEditable(e);
	}
}
