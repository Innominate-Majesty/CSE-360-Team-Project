package guiRole1;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import database.Database;
import entityClasses.User;

/*******
 * <p> Title: ViewRole1Home Class. </p>
 * 
 * <p> Description: The Java/FX-based Role1 Home Page.  The page is a stub for some role needed for
 * the application.  The widgets on this page are likely the minimum number and kind for other role
 * pages that may be needed.</p>
 * 
 * <p> Copyright: Lynn Robert Carter © 2025 </p>
 * 
 * @author Lynn Robert Carter
 * 
 * @version 1.00		2025-08-20 Initial version
 * @version 1.01		2026-10-04 Added Contributor role commands
 *  
 */

public class ViewRole1Home {
	
	/*-*******************************************************************************************

	Attributes
	
	 */
	
	// These are the application values required by the user interface
	
	private static double width = applicationMain.FoundationsMain.WINDOW_WIDTH;
	private static double height = applicationMain.FoundationsMain.WINDOW_HEIGHT;


	// These are the widget attributes for the GUI. There are 3 areas for this GUI.
	
	// GUI Area 1: It informs the user about the purpose of this page, whose account is being used,
	// and a button to allow this user to update the account settings
	protected static Label label_PageTitle = new Label();
	protected static Label label_UserDetails = new Label();
	protected static Button button_UpdateThisUser = new Button("Account Update");
	
	// This is a separator and it is used to partition the GUI for various tasks
	protected static Line line_Separator1 = new Line(20, 95, width-20, 95);

	// GUI Area 2:
	// Labels and Text Fields for the base home page: LessonID, Title, Category only
	protected static Label label_LessonId = new Label("Lesson ID:");
	protected static Label label_Title = new Label("Title:");
	protected static Label label_Category = new Label("Category:");
		
	protected static TextField textfield_LessonId = new TextField();
	protected static TextField textfield_Title = new TextField();
	protected static TextField textfield_Category = new TextField();
		
	// Action buttons
	protected static Button button_CreateLesson = new Button("Create");
	protected static Button button_ReadAll = new Button("My Lessons");
	protected static Button button_ReadById = new Button("Examine");
	protected static Button button_UpdateLesson = new Button("Update");
	protected static Button button_Experience = new Button("Experience");
	protected static Button button_DeleteLesson = new Button("Delete");
		
	// Search by category
	protected static Label label_FilterCategory = new Label("Filter Category:");
	protected static TextField textfield_FilterCategory = new TextField();
	protected static Button button_FilterCategory = new Button("Filter");
		
	// Output area for records, status updates, and test-case error messages
	protected static TextArea textarea_Display = new TextArea();
	
	// This is a separator and it is used to partition the GUI for various tasks
	protected static Line line_Separator4 = new Line(20, 525, width-20,525);
	
	// GUI Area 3: This is last of the GUI areas.  It is used for quitting the application and for
	// logging out.
	protected static Button button_Logout = new Button("Logout");
	protected static Button button_Quit = new Button("Quit");

	// This is the end of the GUI objects for the page.
	
	// These attributes are used to configure the page and populate it with this user's information
	private static ViewRole1Home theView;		// Used to determine if instantiation of the class
												// is needed

	// Reference for the in-memory database so this package has access
	private static Database theDatabase = applicationMain.FoundationsMain.database;

	protected static Stage theStage;			// The Stage that JavaFX has established for us	
	protected static Pane theRootPane;			// The Pane that holds all the GUI widgets
	protected static User theUser;				// The current logged in User
	

	private static Scene theViewRole1HomeScene;	// The shared Scene each invocation populates
	protected static final int theRole = 2;		// Admin: 1; Role1: 2; Role2: 3

	/*-*******************************************************************************************

	Constructors
	
	 */

	/**********
	 * <p> Method: displayRole1Home(Stage ps, User user) </p>
	 * 
	 * <p> Description: This method is the single entry point from outside this package to cause
	 * the Role1 Home page to be displayed.
	 * 
	 * It first sets up every shared attributes so we don't have to pass parameters.
	 * 
	 * It then checks to see if the page has been setup.  If not, it instantiates the class, 
	 * initializes all the static aspects of the GIUI widgets (e.g., location on the page, font,
	 * size, and any methods to be performed).
	 * 
	 * After the instantiation, the code then populates the elements that change based on the user
	 * and the system's current state.  It then sets the Scene onto the stage, and makes it visible
	 * to the user.
	 * 
	 * @param ps specifies the JavaFX Stage to be used for this GUI and it's methods
	 * 
	 * @param user specifies the User for this GUI and it's methods
	 * 
	 */
	public static void displayRole1Home(Stage ps, User user) {
		
		// Establish the references to the GUI and the current user
		theStage = ps;
		theUser = user;
		
		// If not yet established, populate the static aspects of the GUI
		if (theView == null) theView = new ViewRole1Home();		// Instantiate singleton if needed
		
		// Populate the dynamic aspects of the GUI with the data from the user and the current
		// state of the system.
		theDatabase.getUserAccountDetails(user.getUserName());
		applicationMain.FoundationsMain.activeHomePage = theRole;
		
		label_UserDetails.setText("User: " + theUser.getUserName());
				
		// Set the title for the window, display the page, and wait for the Admin to do something
		theStage.setTitle("CSE 360 Foundations: Contributor Home Page");
		theStage.setScene(theViewRole1HomeScene);
		theStage.show();
	}
	
	/**********
	 * <p> Method: ViewRole1Home() </p>
	 * 
	 * <p> Description: This method initializes all the elements of the graphical user interface.
	 * This method determines the location, size, font, color, and change and event handlers for
	 * each GUI object.</p>
	 * 
	 * This is a singleton and is only performed once.  Subsequent uses fill in the changeable
	 * fields using the displayRole2Home method.</p>
	 * 
	 */
	private ViewRole1Home() {

		// Create the Pane for the list of widgets and the Scene for the window
		theRootPane = new Pane();
		theViewRole1HomeScene = new Scene(theRootPane, width, height);	// Create the scene
		
		// Set the title for the window
		
		// Populate the window with the title and other common widgets and set their static state
		
		// GUI Area 1
		label_PageTitle.setText("Role1 Home Page");
		setupLabelUI(label_PageTitle, "Arial", 28, width, Pos.CENTER, 0, 5);

		label_UserDetails.setText("User: " + theUser.getUserName());
		setupLabelUI(label_UserDetails, "Arial", 20, width, Pos.BASELINE_LEFT, 20, 55);
		
		setupButtonUI(button_UpdateThisUser, "Dialog", 18, 170, Pos.CENTER, 610, 45);
		button_UpdateThisUser.setOnAction((_) -> {ControllerRole1Home.performUpdate(); });
		
		// GUI Area 2
		label_PageTitle.setText("Contributor Home Page");

		// Row 1: Only Lesson ID, Title, and Category text fields
		setupLabelUI(label_LessonId, "Arial", 14, 100, Pos.BASELINE_LEFT, 20, 110);
		setupTextUI(textfield_LessonId, "Arial", 14, 100, 20, 135, true);
		
		setupLabelUI(label_Title, "Arial", 14, 380, Pos.BASELINE_LEFT, 140, 110);
		setupTextUI(textfield_Title, "Arial", 14, 380, 140, 135, true);
		
		setupLabelUI(label_Category, "Arial", 14, 220, Pos.BASELINE_LEFT, 540, 110);
		setupTextUI(textfield_Category, "Arial", 14, 220, 540, 135, true);

		// Row 2: Action Buttons (Create, My Lessons, Examine, Update, Experience, Delete)
		setupButtonUI(button_CreateLesson, "Dialog", 14, 110, Pos.CENTER, 20, 185);
		button_CreateLesson.setOnAction((_) -> { ControllerRole1Home.handleCreateLesson(); });

		setupButtonUI(button_ReadAll, "Dialog", 14, 110, Pos.CENTER, 145, 185);
		button_ReadAll.setOnAction((_) -> { ControllerRole1Home.handleReadAllLessons(); });

		setupButtonUI(button_ReadById, "Dialog", 14, 110, Pos.CENTER, 270, 185);
		button_ReadById.setOnAction((_) -> { ControllerRole1Home.handleReadSingleLesson(); });

		setupButtonUI(button_UpdateLesson, "Dialog", 14, 110, Pos.CENTER, 395, 185);
		button_UpdateLesson.setOnAction((_) -> { ControllerRole1Home.handleOpenUpdateWindow(); });

		setupButtonUI(button_Experience, "Dialog", 14, 110, Pos.CENTER, 520, 185);
		button_Experience.setOnAction((_) -> { ControllerRole1Home.handleOpenExperienceWindow(); });

		setupButtonUI(button_DeleteLesson, "Dialog", 14, 110, Pos.CENTER, 645, 185);
		button_DeleteLesson.setOnAction((_) -> { ControllerRole1Home.handleDeleteLesson(); });

		// Row 3: Filter Query Search
		setupLabelUI(label_FilterCategory, "Arial", 14, 140, Pos.BASELINE_LEFT, 20, 235);
		setupTextUI(textfield_FilterCategory, "Arial", 14, 220, 150, 235, true);
		
		setupButtonUI(button_FilterCategory, "Dialog", 14, 110, Pos.CENTER, 390, 235);
		button_FilterCategory.setOnAction((_) -> { ControllerRole1Home.handleReadFilteredSubset(); });

		// Row 4: Results & Error Message Display Area
		textarea_Display.setFont(Font.font("Monospaced", 13));
		textarea_Display.setLayoutX(20);
		textarea_Display.setLayoutY(280);
		textarea_Display.setMinWidth(width - 40);
		textarea_Display.setMaxWidth(width - 40);
		textarea_Display.setPrefHeight(230);
		textarea_Display.setEditable(false);
		textarea_Display.setWrapText(true);
		
		// GUI Area 3
        setupButtonUI(button_Logout, "Dialog", 18, 250, Pos.CENTER, 20, 540);
        button_Logout.setOnAction((_) -> {ControllerRole1Home.performLogout(); });
        
        setupButtonUI(button_Quit, "Dialog", 18, 250, Pos.CENTER, 300, 540);
        button_Quit.setOnAction((_) -> {ControllerRole1Home.performQuit(); });

		// This is the end of the GUI initialization code
		
		// Place all of the widget items into the Root Pane's list of children
        theRootPane.getChildren().addAll(
    			label_PageTitle, label_UserDetails, button_UpdateThisUser, line_Separator1,
    	        line_Separator4, button_Logout, button_Quit, 
    	        label_LessonId, textfield_LessonId, label_Title, textfield_Title, label_Category, textfield_Category,
    	        button_CreateLesson, button_ReadAll, button_ReadById, button_UpdateLesson, button_Experience, button_DeleteLesson,
    	        label_FilterCategory, textfield_FilterCategory, button_FilterCategory, textarea_Display
    	        );
}
	
	
	/*-********************************************************************************************

	Helper methods to reduce code length

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
	 * @param b		The Button object to be initialized
	 * @param ff	The font to be used
	 * @param f		The size of the font to be used
	 * @param w		The width of the Button
	 * @param p		The alignment (e.g. left, centered, or right)
	 * @param x		The location from the left edge (x axis)
	 * @param y		The location from the top (y axis)
	 */
	private static void setupButtonUI(Button b, String ff, double f, double w, Pos p, double x, 
			double y){
		b.setFont(Font.font(ff, f));
		b.setMinWidth(w);
		b.setAlignment(p);
		b.setLayoutX(x);
		b.setLayoutY(y);		
	}
	
	/**********
	 * Private local method to initialize standard fields for a textfield widget.
	 * 
	 * @param t the TextField object to initialize
	 * @param ff the font family name
	 * @param f the size of the font
	 * @param w the layout width of the field
	 * @param x the location from the left edge (x axis)
	 * @param y the location from the top edge (y axis)
	 * @param e editable flag
	 */
	private static void setupTextUI(TextField t, String ff, double f, double w, double x, double y, boolean e) {
		t.setFont(Font.font(ff, f));
		t.setMinWidth(w);
		t.setMaxWidth(w);
		t.setLayoutX(x);
		t.setLayoutY(y);
		t.setEditable(e);
	}
}
