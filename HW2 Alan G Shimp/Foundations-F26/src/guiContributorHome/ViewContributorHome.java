package guiContributorHome;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import database.Database;
import entityClasses.User;
import entityClasses.LessonLearned;
import entityClasses.LessonsLearnedList;


/*******
 * <p> Title: ViewContributorHome Class. </p>
 * 
 * <p> Description: The Java/FX-based Contributor Home Page.  The page is a stub for some role needed for
 * the application.  The widgets on this page are likely the minimum number and kind for other role
 * pages that may be needed.</p>
 * 
 * <p> Copyright: Lynn Robert Carter © 2025 </p>
 * 
 * @author Lynn Robert Carter
 * @author Alan G. Shimp
 * 
 * @version 1.00		2025-08-20 Initial version
 * @version 1.01		2026-10-03 Contributor class version
 *  
 */

public class ViewContributorHome {
	
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
	// and a button to allow this user to update the account settings
	protected static Label label_PageTitle = new Label();
	protected static Label label_UserDetails = new Label();
	protected static Button button_addLesson = new Button("+");
	protected static Button button_UpdateThisUser = new Button("Account Update");

	// GUI ARea 2: The widgets needed to manage accounts.
	// Displays the empty list and database error messages
	protected static Label label_StatusMessage = new Label();

	// Holds the lesson learned cards vertically
	protected static VBox lessonListContainer = new VBox();

	// Allows the contributor to scroll through the lesson cards
	protected static ScrollPane lessonScrollPane = new ScrollPane();

	// Holds the permitted details for the selected account
	protected static VBox lessonDetailsContainer = new VBox();

	// Refreshes the lesson information from the database
	protected static Button button_Refresh = new Button("Refresh");

	// Returns from lesson details to the lesson-card list
	protected static Button button_BackToList = new Button("Back to Lesson List");
	
	// Updates a lesson from its details page
	protected static Button button_UpdateLesson = new Button("Update");
	
	// Deletes a lesson from its details page
	protected static Button button_DeleteLesson = new Button("Delete");
	
	
	// GUI Area 3: This is last of the GUI areas.  It is used for quitting the application and for
	// logging out.
	protected static Button button_Logout = new Button("Logout");
	protected static Button button_Quit = new Button("Quit");

	// This is the end of the GUI objects for the page.
	
	// These attributes are used to configure the page and populate it with this user's information
	private static ViewContributorHome theView;	// Used to determine if instantiation of the class
												// is needed

	// Reference for the in-memory database so this package has access
	private static Database theDatabase = applicationMain.FoundationsMain.database;

	protected static Stage theStage;			// The Stage that JavaFX has established for us	
	protected static Pane theRootPane;			// The Pane that holds all the GUI widgets
	protected static User theUser;				// The current logged in User
	

	private static Scene theViewContributorHomeScene;	// The shared Scene each invocation populates
	protected static final int theRole = 2;				// Admin: 1; Contributor: 2; Role2: 3
	
	protected static LessonsLearnedList lessons;		// The list of lessons to be displayed

	/*-*******************************************************************************************

	Constructors
	
	 */


	/**********
	 * <p> Method: displayContributorHome(Stage ps, User user) </p>
	 * 
	 * <p> Description: This method is the single entry point from outside this package to cause
	 * the Contributor Home page to be displayed.
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
	public static void displayContributorHome(Stage ps, User user) {
		
		// Establish the references to the GUI and the current user
		theStage = ps;
		theUser = user;
		
		// If not yet established, populate the static aspects of the GUI
		if (theView == null) theView = new ViewContributorHome();		// Instantiate singleton if needed
		
		// Populate the dynamic aspects of the GUI with the data from the user and the current
		// state of the system.
		theDatabase.getUserAccountDetails(user.getUserName());
		applicationMain.FoundationsMain.activeHomePage = theRole;
		
		label_UserDetails.setText("User: " + theUser.getUserName());
		
		// Clear previously displayed items
		lessonListContainer.getChildren().clear();
				
		// Set the title for the window, display the page, and wait for the Admin to do something
		theStage.setTitle("CSE 360 Foundations: Contributor Home Page");
		theStage.setScene(theViewContributorHomeScene);
		theStage.show();
	}
	
	/**********
	 * <p> Method: ViewContributorHome() </p>
	 * 
	 * <p> Description: This method initializes all the elements of the graphical user interface.
	 * This method determines the location, size, font, color, and change and event handlers for
	 * each GUI object.</p>
	 * 
	 * This is a singleton and is only performed once.  Subsequent uses fill in the changeable
	 * fields using the displayRole2Home method.</p>
	 * 
	 */
	private ViewContributorHome() {

		// Create the Pane for the list of widgets and the Scene for the window
		theRootPane = new Pane();
		theViewContributorHomeScene = new Scene(theRootPane, width, height);	// Create the scene
		
		// Applies the soft blue gray background to the page
		theRootPane.setStyle("-fx-background-color: " + PAGE_BACKGROUND + ";");
		
		// Set the title for the window
		
		// Populate the window with the title and other common widgets and set their static state
		
		// GUI Area 1
		label_PageTitle.setText("Contributor Home Page");
		setupLabelUI(label_PageTitle, "Arial", 28, width, Pos.CENTER, 0, 5);

		label_UserDetails.setText("User: " + theUser.getUserName());
		setupLabelUI(label_UserDetails, "Arial", 20, width, Pos.BASELINE_LEFT, 20, 55);
		
		setupButtonUI(button_addLesson, "Arial", 15, 15, Pos.CENTER, 325, 45, true);
		button_addLesson.setOnAction((_) ->
			{guiAddUpdateLesson.ViewAddUpdateLesson.displayAddUpdateLesson(theStage, theUser,
					null); });
		
		setupButtonUI(button_UpdateThisUser, "Arial", 15, 170, Pos.CENTER, 610, 45, false);
		button_UpdateThisUser.setOnAction((_) -> 
			{guiUserUpdate.ViewUserUpdate.displayUserUpdate(theStage, theUser); });
		
		// Styles and positions the Update Lesson button
        setupButtonUI(button_UpdateLesson, "Arial", 15, 120, Pos.CENTER, 200, 55, true);
        
        //Styles and positions the Delete Lesson button
        setupButtonUI(button_DeleteLesson, "Arial", 15, 120, Pos.CENTER, 400, 55, true);
		
		// GUI Area 2
		// Adds spacing between account cards
		lessonListContainer.setSpacing(12);

		// Adds padding around the account-card list
		lessonListContainer.setPadding(new Insets(12));

		// Makes the account card container fill the scrollable width
		lessonListContainer.setFillWidth(true);

		// Places the account card container inside the scrolling area
		lessonScrollPane.setContent(lessonListContainer);

		// Makes account cards use the available scrolling width
		lessonScrollPane.setFitToWidth(true);

		// Prevents unnecessary horizontal scrolling
		lessonScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

		// Displays the vertical scrollbar only when it is needed
		lessonScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

		// Gives the scrollable account list a transparent outer background
		lessonScrollPane.setStyle(
				"-fx-background: transparent;" +
				"-fx-background-color: transparent;");

		// Places the account list beneath the page header
		lessonScrollPane.setLayoutX(20);

		// Places the account list vertically beneath the page header
		lessonScrollPane.setLayoutY(100);

		// Sets the width of the scrollable account list
		lessonScrollPane.setPrefWidth(width - 40);

		// Sets the height of the scrollable account list
		lessonScrollPane.setPrefHeight(410);

		// Adds spacing between selected account detail rows
		lessonDetailsContainer.setSpacing(12);

		// Adds padding inside the selected account details panel
		lessonDetailsContainer.setPadding(new Insets(20));

		// Applies the card appearance to the details panel
		lessonDetailsContainer.setStyle(
				"-fx-background-color: " + CARD_BACKGROUND + ";" +
				"-fx-border-color: " + BORDER_COLOR + ";" +
				"-fx-border-radius: 10;" +
				"-fx-background-radius: 10;");

		// Places the account details panel beneath the page header
		lessonDetailsContainer.setLayoutX(20);

		// Places the account details panel vertically beneath the header
		lessonDetailsContainer.setLayoutY(100);

		// Sets the width of the account details panel
		lessonDetailsContainer.setPrefWidth(width - 40);

		// Sets the height of the account details panel
		lessonDetailsContainer.setPrefHeight(410);

		// Hides the account details until an account is selected
		lessonDetailsContainer.setVisible(false);

		// Applies readable styling to the empty list and database error messages
		label_StatusMessage.setStyle(
				"-fx-text-fill: " + SECONDARY_TEXT + ";" +
				"-fx-font-size: 16px;");

		// Gives status messages enough width for centered text
		label_StatusMessage.setMinWidth(width - 40);

		// Centers status messages within the page
		label_StatusMessage.setAlignment(Pos.CENTER);

		// Places status messages in the main content area
		label_StatusMessage.setLayoutX(20);

		// Places status messages near the center of the content area
		label_StatusMessage.setLayoutY(280);

		// Hides the status message until it is needed
		label_StatusMessage.setVisible(false);

		// Hides the back button until lesson details are displayed
		button_BackToList.setVisible(false);
		
		// Hides the update button until lesson details are displayed
		button_UpdateLesson.setVisible(false);
		
		// Hides the delete button until lesson details are displayed
		button_DeleteLesson.setVisible(false);
		
		// Styles and positions the Back to Lesson List button
		setupButtonUI(button_BackToList, "Arial", 15, 160, Pos.CENTER, 20, 540, true);
		// Returns to the account card list when the admin clicks Back
        button_BackToList.setOnAction((_) -> ControllerContributorHome.performBackToList());

		//GUI Area 3
		// Styles and positions the Refresh button
		setupButtonUI(button_Refresh, "Arial", 15, 120, Pos.CENTER, 20, 540, true);
		// Reloads the account summaries when the admin clicks Refresh
        button_Refresh.setOnAction((_) -> ControllerContributorHome.performRefresh());
		
		setupButtonUI(button_Logout, "Arial", 15, 120, Pos.CENTER, 500, 540, false);
		button_Logout.setOnAction((_) -> {ControllerContributorHome.performLogout(); });
    
		setupButtonUI(button_Quit, "Arial", 15, 120, Pos.CENTER, 650, 540, false);
		button_Quit.setOnAction((_) -> {ControllerContributorHome.performQuit(); });

		// This is the end of the GUI initialization code
		
		// Place all of the widget items into the Root Pane's list of children
         theRootPane.getChildren().addAll(label_PageTitle, label_UserDetails, button_addLesson,
        		 button_UpdateThisUser, button_UpdateLesson, button_DeleteLesson,
        		 lessonListContainer, lessonScrollPane, lessonDetailsContainer,
        		 label_StatusMessage, button_BackToList, button_Refresh, button_Logout,
        		 button_Quit);
	}
	
	/*******
	 *
	 * <p> Method: displayLessonsLearned(LessonsLearnedList list) </p>
	 *
	 * <p> Description: Displays one lesson card for each lesson learned returned by the
	 * database </p>
	 *
	 * @param list specifies the password-free accounts to display
	 *
	 */

	protected static void displayLessonsLearned(LessonsLearnedList list) {

		// Stores the lessons currently displayed by the page
		lessons = list;

		// Removes lesson cards left over from an earlier display or refresh
		lessonListContainer.getChildren().clear();

		// Hides the lesson-details panel while displaying the lesson list
		lessonDetailsContainer.setVisible(false);

		// Hides the Back to User List button while displaying the lesson list
		button_BackToList.setVisible(false);
		
		// Hides the update button until lesson details are displayed
		button_UpdateLesson.setVisible(false);
		
		// Hides the delete button until lesson details are displayed
		button_DeleteLesson.setVisible(false);
		
		// Displays the add lesson button while displaying the lesson list
		button_addLesson.setVisible(true);

		// Displays the Refresh button while displaying the lesson list
		button_Refresh.setVisible(true);

		// Checks whether the provided lesson list is unavailable
		if (list == null) {

			// Hides the scrollable lesson list
			lessonScrollPane.setVisible(false);

			// Displays an understandable database error message
			label_StatusMessage.setText("Lessons learned could not be loaded. Please try again.");

			// Makes the database-error message visible
			label_StatusMessage.setVisible(true);

			// Stops before trying to process an unavailable list
			return;

		}

		// Checks whether the database contains no lessons by this user
		if (list.getSize() == 0) {

			// Hides the empty scrollable lesson list
			lessonScrollPane.setVisible(false);

			// Displays an understandable empty-state message
			label_StatusMessage.setText("No lessons were found.");

			// Makes the empty-state message visible
			label_StatusMessage.setVisible(true);

			// Stops because there are no lesson cards to construct
			return;

		}

		// Hides any earlier empty state or database error message(s)
		label_StatusMessage.setVisible(false);

		// Displays the scrollable lesson list
		lessonScrollPane.setVisible(true);

		// Processes every lesson
		for (int i = 0; i < list.getSize(); i++) {
			// Creates and adds a card for the current lesson
			lessonListContainer.getChildren().add(
					createLessonCard(list.getLesson(i)));
		}
	}
	
	/*******
	 *
	 * <p> Method: createLessonCard(LessonLearned lesson) </p>
	 *
	 * <p> Description: Constructs a modern lesson card containing the
	 * lesson's title, creator, and core info </p>
	 *
	 * @param lesson specifies the lesson displayed by the card
	 *
	 * @return the constructed lesson card
	 *
	 */

	private static Button createLessonCard(LessonLearned lesson) {

		// Creates the primary title displayed on the lesson card
		Label titleLabel = new Label(lesson.getTitle());

		// Applies the primary lesson title appearance
		titleLabel.setStyle(
				"-fx-text-fill: " + PRIMARY_TEXT + ";" +
			    "-fx-font-size: 17px;" +
				"-fx-font-weight: bold;");

		// Retrieves the creator name displayed beneath the lesson title
		String displayedCreator = lesson.getCreatorName();

		// Checks whether the lesson has a usable creator name
		if (displayedCreator == null || displayedCreator.isBlank()) {

			// Provides readable text instead of displaying null
			displayedCreator = "Creator name unavailable";

		}

		// Creates the smaller creator name displayed on the account card
		Label creatorLabel = new Label(displayedCreator);

		// Applies the secondary username appearance
		creatorLabel.setStyle(
				"-fx-text-fill: " + SECONDARY_TEXT + ";" + "-fx-font-size: 13px;");

		// Creates the vertical text layout used inside the card
		VBox cardText = new VBox();

		// Adds a small amount of space between the title and creator
		cardText.setSpacing(4);

		// Aligns the card text to the left
		cardText.setAlignment(Pos.CENTER_LEFT);

		// Adds the title and creator name to the card layout
		cardText.getChildren().addAll(titleLabel, creatorLabel);

		// Creates the clickable account card
		Button lessonCard = new Button();

		// Places the name and username layout inside the card
		lessonCard.setGraphic(cardText);

		// Aligns the card contents to the left
		lessonCard.setAlignment(Pos.CENTER_LEFT);

		// Allows the card to use the full available width
		lessonCard.setMaxWidth(Double.MAX_VALUE);

		// Gives the card a consistent minimum height
		lessonCard.setMinHeight(70);

		// Applies the default modern card appearance
		lessonCard.setStyle(
				"-fx-background-color: " + CARD_BACKGROUND + ";" +
				"-fx-border-color: " + BORDER_COLOR + ";" +
				"-fx-border-radius: 10;" +
				"-fx-background-radius: 10;" +
				"-fx-padding: 12;" +
				"-fx-cursor: hand;");

		// Applies the pale-blue appearance while the pointer is over the card
		lessonCard.setOnMouseEntered((_) -> lessonCard.setStyle(
				"-fx-background-color: " + HOVER_BACKGROUND + ";" +
				"-fx-border-color: " + ACCENT_COLOR + ";" +
				"-fx-border-radius: 10;" +
				"-fx-background-radius: 10;" +
				"-fx-padding: 12;" +
				"-fx-cursor: hand;"));

		// Restores the white card appearance when the pointer leaves the card
		lessonCard.setOnMouseExited((_) -> lessonCard.setStyle(
				"-fx-background-color: " + CARD_BACKGROUND + ";" +
				"-fx-border-color: " + BORDER_COLOR + ";" +
				"-fx-border-radius: 10;" +
				"-fx-background-radius: 10;" +
				"-fx-padding: 12;" +
				"-fx-cursor: hand;"));

       // Displays the permitted account details when the admin clicks this card
		lessonCard.setOnAction((_) -> displayLessonDetails(lesson));

		// Returns the completed account card
		return lessonCard;

	}
	
	/*******
	 *
	 * <p> Method: displayLessonDetails(LessonLearned lesson) </p>
	 *
	 * <p> Description: Displays the information for the lesson learned </p>
	 *
	 * @param account specifies the selected password-free account summary
	 *
	 */

	private static void displayLessonDetails(LessonLearned lesson) {

		// Hides the lesson card list
		lessonScrollPane.setVisible(false);

		// Hides any empty list or database error message
		label_StatusMessage.setVisible(false);

		// Hides the Refresh button while displaying lesson details
		button_Refresh.setVisible(false);
		
		// Hides the add lesson button while displaying lesson details
		button_addLesson.setVisible(false);

		// Displays the Back to Lesson List button
		button_BackToList.setVisible(true);
		
		// Displays the Update Lesson button
		button_UpdateLesson.setVisible(true);
		
		// Displays the Delete Lesson button
		button_DeleteLesson.setVisible(true);

		// Removes information from any previously selected lesson
		lessonDetailsContainer.getChildren().clear();

		// Creates the heading for the selected lesson
		Label detailHeading = new Label(lesson.getTitle());

		// Applies the heading appearance
		detailHeading.setStyle(
				"-fx-text-fill: " + PRIMARY_TEXT + ";" +
				"-fx-font-size: 21px;" +
				"-fx-font-weight: bold;");

		// Adds the lesson title to the details panel
		lessonDetailsContainer.getChildren().add(detailHeading);

		// Adds the lesson creator to the details panel
		lessonDetailsContainer.getChildren().add(
				createDetailRow("Creator", lesson.getCreatorName()));
		
		// Adds the lesson's locked status to the details panel
		String status;
		if (lesson.getLockedStatus()) status = "Locked";
		else status = "Unlocked";
		lessonDetailsContainer.getChildren().add(
				createDetailRow("Locked", status));

		// Adds the lesson's core information to the details panel
		lessonDetailsContainer.getChildren().add(
				createDetailRow("Core Information", lesson.getCoreInfo()));

		// Displays the completed lesson-details panel
		lessonDetailsContainer.setVisible(true);
		
		button_UpdateLesson.setOnAction((_) ->
    		{guiAddUpdateLesson.ViewAddUpdateLesson.displayAddUpdateLesson(theStage, theUser,
    				lesson); });
		
		button_DeleteLesson.setOnAction((_) ->
			{guiContributorHome.ControllerContributorHome.deleteLesson(lesson); });
	}
	
	/*******
	 *
	 * <p> Method: createDetailRow(String fieldName, String fieldValue) </p>
	 *
	 * <p> Description: Constructs one labeled row for the selected lesson's information </p>
	 *
	 * @param fieldName specifies the readable name of the lesson field
	 *
	 * @param fieldValue specifies the account field's stored value
	 *
	 * @return the completed account-detail row
	 *
	 */

	private static VBox createDetailRow(String fieldName, String fieldValue) {

		// Creates the label identifying the lesson field
		Label fieldNameLabel = new Label(fieldName);

		// Applies the secondary appearance to the field name
		fieldNameLabel.setStyle(
				"-fx-text-fill: " + SECONDARY_TEXT + ";" +
				"-fx-font-size: 12px;" +
				"-fx-font-weight: bold;");

		// Creates the label containing the safely formatted field value
		Label fieldValueLabel = new Label(
				getDisplayValue(fieldValue));

		// Applies the primary appearance to the field value
		fieldValueLabel.setStyle(
				"-fx-text-fill: " + PRIMARY_TEXT + ";" +
				"-fx-font-size: 15px;");

		// Allows a long field value to wrap within the details panel
		fieldValueLabel.setWrapText(true);

		// Limits the field value to the available panel width
		fieldValueLabel.setMaxWidth(width - 90);

		// Creates a vertical container for the field name and value
		VBox detailRow = new VBox();

		// Adds a small space between the field name and value
		detailRow.setSpacing(2);

		// Aligns the detail row to the left
		detailRow.setAlignment(Pos.CENTER_LEFT);

		// Adds the field name and field value to the row
		detailRow.getChildren().addAll(
				fieldNameLabel, fieldValueLabel);

		// Returns the completed lesson-detail row
		return detailRow;

	}
	
	/*******
	 *
	 * <p> Method: getDisplayValue(String value) </p>
	 *
	 * <p> Description: Replaces a missing optional lesson value with
	 * readable text so the interface never displays null </p>
	 *
	 * @param value specifies the account value being prepared for display
	 *
	 * @return the stored value or Not provided when the value is blank
	 *
	 */

	private static String getDisplayValue(String value) {

		// Checks whether the stored lesson value is missing or blank
		if (value == null || value.isBlank()) {

			// Returns readable text for a missing optional value
			return "Not provided";
		}

		// Returns the stored lesson value without surrounding spaces
		return value.trim();

	}
	
	/*******
	 *
	 * <p> Method: displayLessonList() </p>
	 *
	 * <p> Description: Returns from the selected lesson details panel to
	 * the current list of lesson cards </p>
	 *
	 */

	protected static void displayLessonList() {

		// Redisplays the currently loaded account summaries
		displayLessonsLearned(lessons);

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
				"-fx-text-fill: " + SECONDARY_TEXT + ";" +
				"-fx-border-color: " + BORDER_COLOR + ";" +
				"-fx-border-radius: 8;" +
				"-fx-background-radius: 8;" +
				"-fx-padding: 8 16 8 16;" +
				"-fx-cursor: hand;");

			//
			b.setOnMouseEntered((_) -> b.setStyle(
				"-fx-background-color: " + HOVER_BACKGROUND + ";" +
				"-fx-text-fill: " + SECONDARY_TEXT + ";" +
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
}
