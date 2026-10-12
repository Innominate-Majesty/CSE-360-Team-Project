package guiDeleteUser;

import java.util.ArrayList;
import java.util.List;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import database.Database;
import entityClasses.User;
import guiDeleteUser.ControllerDeleteUser;
import guiDeleteUser.ViewDeleteUser;

/*******
 * <p> Title: GUIDeleteUserPage Class. </p>
 * 
 * <p> Description: The Java/FX-based page for deleting users.</p>
 * 
 * <p> Copyright: Alan G. Shimp © 2026 </p>
 * 
 * @author Alan G. Shimp
 * 
 * @version 1.00		2026-09-12 Initial version
 *  
 */

public class ViewDeleteUser {
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


    // These are the widget attributes for the GUI. There are 3 areas for this GUI.
	
	// GUI Area 1: It informs the user about the purpose of this page, whose account is being used,
	// and a button to allow this user to update the account settings.
	protected static Label label_PageTitle = new Label();
	protected static Label label_UserDetails = new Label();
	protected static Button button_UpdateThisUser = new Button("Account Update");
	
	// When no user has been selected, only Area 2a is shown.  If a user in the ComboBox in Area 1a
	// has been specified, then Area 2b is made visible.
	
	// Area 2a: This allows the admin to select a user of the system as the first step in adding or
	// removing a role.  The act of selecting a user causes the change is the GUI.  The Admin does
	// not need to push a button to make this happen.
	protected static Label label_SelectUser = new Label("Select a user to be deleted:");
	protected static ComboBox <String> combobox_SelectUser = new ComboBox <String>();
	protected static List<String> userList = new ArrayList<String>();
	
	// Area 2b: When the current user has selected themself, this widget is shown
    protected static Label label_SelectSelf = new Label("You cannot delete yourself. Please select"
        + " another user");

    // Area 2c: When a user has been selected these widgets are shown and can be used
    protected static Label label_AreYouSure = new Label("Are you sure?");
    protected static Button button_Yes = new Button("Yes");

    // Popup: This prepares a popup that will appear if a user is deleted.
    protected static Alert alertDeleted = new Alert(AlertType.INFORMATION);
	
	// GUI Area 3: This is last of the GUI areas.  It is used for quitting the application, logging
	// out, and on other pages a return is provided so the user can return to a previous page when
	// the actions on that page are complete.  Be advised that in most cases in this code, the 
	// return is to a fixed page as opposed to the actual page that invoked the pages.
	protected static Button button_Return = new Button("Return to Admin Home");
	protected static Button button_Logout = new Button("Logout");
	protected static Button button_Quit = new Button("Quit");

	// This is the end of the GUI objects for the page.
	
	// These attributes are used to configure the page and populate it with this user's information
	private static ViewDeleteUser theView;	    // Used to determine if instantiation of the class
												// is needed
	// Reference for the in-memory database so this package has access
	private static Database theDatabase = applicationMain.FoundationsMain.database;		

	protected static Stage theStage;			// The Stage that JavaFX has established for us
	protected static Pane theRootPane;			// The Pane that holds all the GUI widgets 
	protected static User theUser;				// The current user of the application
	
	public static Scene theDeleteUserScene = null;	// The Scene each invocation populates
	protected static String theSelectedUser = "";	// The user who is being deleted



    /*-*******************************************************************************************

	Constructors
	
	*/

	/**********
	 * <p> Method: displayDeleteUser(Stage ps, User user) </p>
	 * 
	 * <p> Description: This method is the single entry point from outside this package to cause
	 * the DeleteUser page to be displayed.
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
	 * @param user specifies the User whose roles will be updated
	 *
	 */
	public static void displayDeleteUser(Stage ps, User user) {
		
		// Establish the references to the GUI and the current user
		theStage = ps;
		theUser = user;
		
		// If not yet established, populate the static aspects of the GUI by creating the 
		// singleton instance of this class
		if (theView == null) theView = new ViewDeleteUser();
		
		// Default to no user selected
		combobox_SelectUser.getSelectionModel().select(0);
		
		// Populate the dynamic aspects of the GUI with the data from the user and the current
		// state of the system.  This page is different from the others.  Since there are two 
		// modes (1: user has not been selected, and 2: user has been selected) there are two
		// lists of widgets to be displayed.  For this reason, we have implemented the following 
		// two controller methods to deal with this dynamic aspect.
		ControllerDeleteUser.repaintTheWindow();
		ControllerDeleteUser.doSelectUser();
	}

	
	/**********
	 * <p> Method: GUIDeleteUserPage() </p>
	 * 
	 * <p> Description: This method initializes all the elements of the graphical user interface.
	 * This method determines the location, size, font, color, and change and event handlers for
	 * each GUI object. </p>
	 * 
	 * This is a singleton, so this is performed just one.  Subsequent uses fill in the changeable
	 * fields using the displayAddRempoveRoles method.</p>
	 * 
	 */
	public ViewDeleteUser() {
		
		// This page is used by all roles, so we do not specify the role being used		
			
		// Create the Pane for the list of widgets and the Scene for the window
		theRootPane = new Pane();
		theDeleteUserScene = new Scene(theRootPane, width, height);

		// Applies the soft blue gray background to the page
		theRootPane.setStyle("-fx-background-color: " + PAGE_BACKGROUND + ";");
		
		// Populate the window with the title and other common widgets and set their static state
		
		// GUI Area 1
		label_PageTitle.setText("Delete User Page");
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
		
		// GUI Area 2a
		setupLabelUI(label_SelectUser, "Arial", 20, 300, Pos.BASELINE_LEFT, 20, 130);
		
		setupComboBoxUI(combobox_SelectUser, "Arial", 16, 250, 280, 125);
		userList = theDatabase.getUserList();	
		combobox_SelectUser.setItems(FXCollections.observableArrayList(userList));
		combobox_SelectUser.getSelectionModel().select(0);
		combobox_SelectUser.getSelectionModel().selectedItemProperty()
    	.addListener((@SuppressWarnings("unused") ObservableValue<? extends String> observable, 
    		@SuppressWarnings("unused") String oldvalue, 
    		@SuppressWarnings("unused") String newValue) -> {ControllerDeleteUser.doSelectUser();});
		
		// GUI Area 2b
        setupLabelUI(label_SelectSelf, "Arial", 16, 300, Pos.BASELINE_LEFT, 50, 170);
        // GUI Area 2c
        setupLabelUI(label_AreYouSure, "Arial", 16, 300, Pos.BASELINE_LEFT, 20, 210);
        setupButtonUI(button_Yes, "Arial", 15, 150, Pos.CENTER, 280, 205, true);
        ViewDeleteUser.button_Yes.setOnAction((_) ->
            {ControllerDeleteUser.performDeleteUser(); });
        alertDeleted.setTitle("Success");
		alertDeleted.setContentText("Select another user or return to role menu.");
		
		// GUI Area 3		
		setupButtonUI(button_Return, "Arial", 15, 230, Pos.CENTER, 200, 540, false);
		button_Return.setOnAction((_) -> {ControllerDeleteUser.performReturn(); });

		setupButtonUI(button_Logout, "Arial", 15, 120, Pos.CENTER, 500, 540, false);
		button_Logout.setOnAction((_) -> {ControllerDeleteUser.performLogout(); });
    
		setupButtonUI(button_Quit, "Arial", 15, 120, Pos.CENTER, 650, 540, false);
		button_Quit.setOnAction((_) -> {ControllerDeleteUser.performQuit(); });
		
		// This is the end of the GUI Widgets for the page
		
		// Due to the very dynamic nature of this page, setting the widget into the Root Pane has 
		// has been delegated to the repaintTheWindow and doSelectUser controller methods.
		// Don't follow this pattern if formatting of the page does not change dynamically.
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
	 * Private local method to initialize the standard fields for a ComboBox
	 * 
	 * @param c		The ComboBox object to be initialized
	 * @param ff	The font to be used
	 * @param f		The size of the font to be used
	 * @param w		The width of the ComboBox
	 * @param x		The location from the left edge (x axis)
	 * @param y		The location from the top (y axis)
	 */
	protected static void setupComboBoxUI(ComboBox <String> c, String ff, double f, double w,
			double x, double y){
		c.setStyle("-fx-font: " + f + " " + ff + ";");
		c.setMinWidth(w);
		c.setLayoutX(x);
		c.setLayoutY(y);
	}
}
