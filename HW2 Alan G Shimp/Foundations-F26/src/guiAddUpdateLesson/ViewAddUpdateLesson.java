package guiAddUpdateLesson;

import entityClasses.User;
import entityClasses.LessonLearned;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.sql.SQLException;

/*******
 * <p> Title: ViewAddUpdateLesson Class. </p>
 * 
 * <p> Description: The Java/FX-based page for adding or updateing lessons learned.</p>
 * 
 * <p> Copyright: Alan G. Shimp © 2026 </p>
 * 
 * @author Alan G. Shimp
 * 
 * @version 1.00		2026-10-04 Initial version
 *  
 */
public class ViewAddUpdateLesson {
	/*-*******************************************************************************************

	Attributes
	
	*/
	
	// These are the application values required by the user interface
	
	private static double width = applicationMain.FoundationsMain.WINDOW_WIDTH;
	private static double height = applicationMain.FoundationsMain.WINDOW_HEIGHT;

		// Defines the pale blue-white background selected by the team
	private static final String PAGE_BACKGROUND = "#F5F7F4";

	// Defines the white background used for account cards
	private static final String CARD_BACKGROUND = "#FFFFFF";

	// Defines the clear modern blue used for important buttons and borders
	private static final String ACCENT_COLOR = "#607D70";

	// Defines the deeper blue displayed when hovering over a primary button
	private static final String ACCENT_HOVER = "#4F6A5E";

	// Defines the pale blue used when highlighting an account card
	private static final String HOVER_BACKGROUND = "#E8F0EB";

	// Defines the dark navy-gray used for primary text
	private static final String PRIMARY_TEXT = "#26332D";

	// Defines the slate blue-gray used for secondary text
	private static final String SECONDARY_TEXT = "#69776F";

	// Defines the light blue-gray border used around cards and panels
	private static final String BORDER_COLOR = "#D8E2DC";
	
	// Style presets constructed from color constants
	private static final String STYLE_TEXTFIELD = 
			"-fx-background-color: " + CARD_BACKGROUND + "; -fx-text-fill: " + PRIMARY_TEXT + 
			"; -fx-border-color: " + BORDER_COLOR + "; -fx-border-radius: 4; -fx-background-radius: 4; -fx-padding: 6;";
	private static final String STYLE_TEXTAREA =
			"-fx-control-inner-background: " + CARD_BACKGROUND + "; -fx-background-color: " +
			CARD_BACKGROUND +"; -fx-border-color: " + BORDER_COLOR + "; -fx-border-width: 1; " +
			"-fx-border-radius: 4; -fx-background-radius: 4; -fx-text-fill: " + PRIMARY_TEXT +
			"; -fx-prompt-text-fill: " + SECONDARY_TEXT + ";";
	private static final String STYLE_TEXTFIELD_FOCUS = 
			"-fx-background-color: " + HOVER_BACKGROUND + "; -fx-text-fill: " + PRIMARY_TEXT + 
			"; -fx-border-color: " + ACCENT_COLOR + "; -fx-border-radius: 4; -fx-background-radius: 4; -fx-padding: 6;";
	
	// These are the widget attributes for the GUI. There are 3 areas for this GUI.
	
	// GUI Area 1: It informs the user about the purpose of this page, whose account is being used,
	// and a button to allow this user to update the account settings.
	protected static Label label_PageTitle = new Label();
	protected static Label label_UserDetails = new Label();
	protected static Button button_UpdateThisUser = new Button("Account Update");
	
	// GUI Area 2: Here the user can input the new information for the lesson.
	protected static Label label_TitleField = new Label();
	protected static TextField text_Title = new TextField();
	protected static Label label_InfoField = new Label();
	protected static TextArea text_CoreInfo = new TextArea();
	protected static Button button_Confirm = new Button("Confirm");
	
	// Popup: This prepares a popup that will appear when the addition or update is confirmed.
    protected static Alert alert_AddUpdate = new Alert(AlertType.INFORMATION);
	
	// GUI Area 3: This is last of the GUI areas.  It is used for quitting the application, logging
	// out, and on other pages a return is provided so the user can return to a previous page when
	// the actions on that page are complete.  Be advised that in most cases in this code, the 
	// return is to a fixed page as opposed to the actual page that invoked the pages.
	protected static Button button_Return = new Button("Return to Contributor Home");
	protected static Button button_Logout = new Button("Logout");
	protected static Button button_Quit = new Button("Quit");

	// This is the end of the GUI objects for the page.
	
	// These attributes are used to configure the page and populate it with this user's information
	private static ViewAddUpdateLesson theView;	    // Used to determine if instantiation of the class
													// is needed
	protected static Stage theStage;				// The Stage that JavaFX has established for us
	protected static Pane theRootPane;				// The Pane that holds all the GUI widgets 
	protected static User theUser;					// The current user of the application
	protected static LessonLearned theLesson;		// The current lesson learned
	protected static boolean isNew;
	
	/**
	 * The Scene each invocation populates
	 */
	public static Scene theAddUpdateLessonScene = null;
	
	/*-*******************************************************************************************

	Constructors
	
	*/

	/**********
	 * <p> Method: displayAddUpdateLesson(Stage ps, User user, LessonLearned lesson) </p>
	 * 
	 * <p> Description: This method is the single entry point from outside this package to cause
	 * the AddUpdateLesson page to be displayed.
	 * 
	 * It first sets up very shared attributes so we don't have to pass parameters.
	 * 
	 * It then checks to see if the page has been setup.  If not, it instantiates the class, 
	 * initializes all the static aspects of the GUI widgets (e.g., location on the page, font,
	 * size, and any methods to be performed).
	 * 
	 * After the instantiation, the code then populates the elements that change based on the user
	 * and the system's current state.  It then sets the Scene onto the stage, and makes it visible
	 * to the user.
	 * 
	 * @param ps specifies the JavaFX Stage to be used for this GUI and it's methods
	 * 
	 * @param user specifies the User
	 * 
	 * @param lesson specifies the lesson to be updated, or null if a new one is being created
	 * 
	 * @param adding specifies whether a lesson is being added or updated
	 *
	 */
	public static void displayAddUpdateLesson(Stage ps, User user, LessonLearned lesson) {
		
		// Establish the references to the GUI and the current user
		theStage = ps;
		theUser = user;
		theLesson = lesson;
		
		// If lesson is null, the page will create a new lesson learned. Otherwise, it will update
		// lesson in the database.
		isNew = (lesson == null);
		
		// If not yet established, populate the static aspects of the GUI by creating the 
		// singleton instance of this class
		if (theView == null) theView = new ViewAddUpdateLesson();
		else ControllerAddUpdateLesson.repaintTheWindow();

		label_UserDetails.setText("User: " + theUser.getUserName());
		
		// Sets the application window title for this page
		theStage.setTitle("Add or Update a Lesson");

		// Places the View User Accounts scene into the application window
		theStage.setScene(theAddUpdateLessonScene);

		// Displays the application window
		theStage.show();
	}
	
	/**********
	 * <p> Method: GUIAddUpdateLessonPage() </p>
	 * 
	 * <p> Description: This method initializes all the elements of the graphical user interface.
	 * This method determines the location, size, font, color, and change and event handlers for
	 * each GUI object. </p>
	 * 
	 * <p> This is a singleton, so this is performed just once.  Subsequent uses fill in the
	 * changeable fields using the displayAddRempoveRoles method. </p>
	 * 
	 */
	public ViewAddUpdateLesson() {
		// Create the Pane for the list of widgets and the Scene for the window
		theRootPane = new Pane();
		theAddUpdateLessonScene = new Scene(theRootPane, width, height);

		// Applies the soft blue gray background to the page
		theRootPane.setStyle("-fx-background-color: " + PAGE_BACKGROUND + ";");
		
		// Populate the window with the title and other common widgets and set their static state
		
		// GUI Area 1
		if (isNew) label_PageTitle.setText("Add Lesson Learned Page");
		else label_PageTitle.setText("Update Lesson Learned Page");
		setupLabelUI(label_PageTitle, "Arial", 28, width, Pos.CENTER, 0, 15);

		// Applies the primary text color to the page title
		label_PageTitle.setStyle("-fx-text-fill: " + PRIMARY_TEXT + "; -fx-font-weight: bold;");

		label_UserDetails.setText("User: " + theUser.getUserName());
		setupLabelUI(label_UserDetails, "Arial", 16, width, Pos.BASELINE_LEFT, 30, 65);

		// Applies the secondary text color to the user label
		label_UserDetails.setStyle("-fx-text-fill: " + SECONDARY_TEXT + ";");
		
		setupButtonUI(button_UpdateThisUser, "Arial", 15, 170, Pos.CENTER, 610, 45, false);
		button_UpdateThisUser.setOnAction((_) -> 
			{guiUserUpdate.ViewUserUpdate.displayUserUpdate(theStage, theUser); });
		
		// GUI Area 2
		
		// For a new lesson, the fields should default to empty. For an update, they should default
		// to the existing values.
		if (isNew) {
			text_Title.setText("");
			text_Title.setPromptText("Enter the title of your lesson learned.");
			text_CoreInfo.setText("");
			text_CoreInfo.setPromptText("Enter the core information of your lesson learned.");
		}
		else {
			text_Title.setText(theLesson.getTitle());
			text_CoreInfo.setText(theLesson.getCoreInfo());
		}
		
		label_TitleField.setText("Title");
		setupLabelUI(label_TitleField, "Arial", 16, width, Pos.BASELINE_LEFT, 20, 175);
		
		setupTextUI(text_Title, "Arial", 16, 760, Pos.BASELINE_LEFT, 20, 205, true);
		
		label_InfoField.setText("Core Information");
		setupLabelUI(label_InfoField, "Arial", 16, width, Pos.BASELINE_LEFT, 20, 255);
		
		setupTextAreaUI(text_CoreInfo, "Arial", 16, 760, 8, 20, 285);
		
		setupButtonUI(button_Confirm, "Arial", 15, 150, Pos.CENTER, 20, 475, true);
		if (isNew) {
			button_Confirm.setOnAction((_) -> {
				try {
					ControllerAddUpdateLesson.addLesson();
				}
				catch (SQLException exception) {
					alert_AddUpdate.setTitle("Database Error");
					alert_AddUpdate.setHeaderText("Could not add lesson to database.");
					alert_AddUpdate.setContentText("Please try again.");
					alert_AddUpdate.showAndWait();
					
					// Print error message for debugging
		            System.err.println("Unable to load lessons: " + exception.getMessage());
				}
			});
		}
		else {
			button_Confirm.setOnAction((_) -> {
				try {
					ControllerAddUpdateLesson.updateLesson();
				}
				catch (SQLException exception) {
					alert_AddUpdate.setTitle("Database Error");
					alert_AddUpdate.setHeaderText("Could not update lesson in database.");
					alert_AddUpdate.setContentText("Please try again.");
					alert_AddUpdate.showAndWait();
					
					// Print error message for debugging
		            System.err.println("Unable to load lessons: " + exception.getMessage());
				}
			});
		}
		
		// GUI Area 3		
		setupButtonUI(button_Return, "Arial", 15, 230, Pos.CENTER, 200, 540, false);
		button_Return.setOnAction((_) -> {ControllerAddUpdateLesson.performReturn(); });

		setupButtonUI(button_Logout, "Arial", 15, 120, Pos.CENTER, 500, 540, false);
		button_Logout.setOnAction((_) -> {ControllerAddUpdateLesson.performLogout(); });
		  
		setupButtonUI(button_Quit, "Arial", 15, 120, Pos.CENTER, 650, 540, false);
		button_Quit.setOnAction((_) -> {ControllerAddUpdateLesson.performQuit(); });
				
		// This is the end of the GUI Widgets for the page
		
		// Place all of the established GUI elements into the pane
    	theRootPane.getChildren().clear();
    	theRootPane.getChildren().addAll(label_PageTitle, label_UserDetails,
    			button_UpdateThisUser, text_Title, text_CoreInfo, label_TitleField,
    			label_InfoField, button_Confirm, button_Return, button_Logout, button_Quit);
	}
	
	/*-*******************************************************************************************

	Helper methods used to minimizes the number of lines of code needed above
	
	*/

	/**********
	 * Private local method to initialize the standard fields for a label
	 * 
	 * @param l		The Label object to be initialized
	 * @param ff	The font to be used
	 * @param f		The size of the font to be used
	 * @param w		The width of the Button
	 * @param p		The alignment (e.g. left, centered, or right)
	 * @param x		The location from the left edge (x axis)
	 * @param y		The location from the top (y axis)
	 */
	
	private static void setupLabelUI(Label l, String ff, double f, double w, Pos p, double x,
			double y){
		l.setFont(Font.font(ff, f));
		l.setMinWidth(w);
		l.setAlignment(p);
		l.setLayoutX(x);
		l.setLayoutY(y);		
	}
	
	/**********
	 * Private local method to initialize the standard fields for a button
	 * 
	 * @param b			The Button object to be initialized
	 * @param ff		The font to be used
	 * @param f			The size of the font to be used
	 * @param w			The width of the Button
	 * @param p			The alignment (e.g. left, centered, or right)
	 * @param x			The location from the left edge (x axis)
	 * @param y			The location from the top (y axis)
	 * @param primary	Whether the button is primmary or secondary
	 */
	protected static void setupButtonUI(Button b, String ff, double f, double w, Pos p, double x,
			double y, boolean primary){
		b.setFont(Font.font(ff, f));
		b.setMinWidth(w);
		b.setAlignment(p);
		b.setLayoutX(x);
		b.setLayoutY(y);

		if (primary) {
			// Applies the default primary button appearance
			b.setStyle(
				"-fx-background-color: " + ACCENT_COLOR + ";" +
			    "-fx-text-fill: white;" +
				"-fx-background-radius: 8;" +
				"-fx-padding: 8 16 8 16;" +
				"-fx-font-weight: bold;" +
				"-fx-cursor: hand;");
			
			// Applies the darker blue appearance while the pointer is over the button
			b.setOnMouseEntered((_) -> b.setStyle(
				"-fx-background-color: " + ACCENT_HOVER + ";" +
				"-fx-text-fill: white;" +
				"-fx-background-radius: 8;" +
				"-fx-padding: 8 16 8 16;" +
				"-fx-font-weight: bold;" +
				"-fx-cursor: hand;"));
			
			// Restores the primary appearance when the pointer leaves the button
			b.setOnMouseExited((_) -> b.setStyle(
				"-fx-background-color: " + ACCENT_COLOR + ";" +
				"-fx-text-fill: white;" +
				"-fx-background-radius: 8;" +
				"-fx-padding: 8 16 8 16;" +
				"-fx-font-weight: bold;" +
				"-fx-cursor: hand;"));
		}

		else {
			// Applies the default secondary-button appearance
			b.setStyle(
				"-fx-background-color: " + CARD_BACKGROUND + ";" +
				"-fx-text-fill: " + PRIMARY_TEXT + ";" +
				"-fx-border-color: " + BORDER_COLOR + ";" +
				"-fx-border-radius: 8;" +
				"-fx-background-radius: 8;" +
				"-fx-padding: 8 16 8 16;" +
				"-fx-cursor: hand;");

			//
			b.setOnMouseEntered((_) -> b.setStyle(
				"-fx-background-color: " + HOVER_BACKGROUND + ";" +
				"-fx-text-fill: " + PRIMARY_TEXT + ";" +
				"-fx-border-color: " + ACCENT_COLOR + ";" +
				"-fx-border-radius: 8;" +
				"-fx-background-radius: 8;" +
				"-fx-padding: 8 16 8 16;" +
				"-fx-cursor: hand;"));

			//
			b.setOnMouseExited((_) -> b.setStyle(
				"-fx-background-color: " + CARD_BACKGROUND + ";" +
				"-fx-text-fill: " + PRIMARY_TEXT + ";" +
				"-fx-border-color: " + BORDER_COLOR + ";" +
				"-fx-border-radius: 8;" +
				"-fx-background-radius: 8;" +
				"-fx-padding: 8 16 8 16;" +
				"-fx-cursor: hand;"));
		}
	}
	
	/**********
	 * Private local method to initialize the standard fields for a text field
	 */
	private void setupTextUI(TextField t, String ff, double f, double w, Pos p, double x, double y, boolean e){
		t.setFont(Font.font(ff, f));
		t.setMinWidth(w);
		t.setMaxWidth(w);
		t.setAlignment(p);
		t.setLayoutX(x);
		t.setLayoutY(y);		
		t.setEditable(e);
		t.setStyle(STYLE_TEXTFIELD);
		t.focusedProperty().addListener((_, _, isNowFocused) -> {
			t.setStyle(isNowFocused ? STYLE_TEXTFIELD_FOCUS : STYLE_TEXTFIELD);
		});
	}
	
	/**********
	 * Private local method to initialize the standard fields for a text area
	 * 
	 * @param t			The TextArea object to be initialized
	 * @param ff		The font to be used
	 * @param f			The size of the font to be used
	 * @param w			The width of the TextArea
	 * @param r			The row count
	 * @param x			The location from the left edge (x axis)
	 * @param y			The location from the top (y axis)
	 */
	private void setupTextAreaUI(TextArea t, String ff, double f, double w, int r, double x, double y) {
		t.setFont(Font.font(ff, f));
		t.setMinWidth(w);
		t.setMaxWidth(w);
		t.setPrefRowCount(r);
		t.setLayoutX(x);
		t.setLayoutY(y);
		t.setWrapText(true);
		t.setEditable(true);
		t.setStyle(STYLE_TEXTAREA);
	}
}