package guiCurator;

import java.util.List;

import database.Database;
import entityClasses.LessonsLearned;
import entityClasses.LessonsLearnedList;
import entityClasses.User;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.binding.NumberBinding;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;


/*******
 * <p> Title: ViewCuratorHome Class. </p>
 * 
 * <p> Description: The JavaFX-based Curator Home Page. This class displays the Curator
 * workspace using the shared sage theme and provides access to account settings. </p>
 * 
 * <p> Copyright: Lynn Robert Carter © 2025 </p>
 * 
 * @author Lynn Robert Carter
 * 
 * @author Venus Ho
 * @author Alexander Murray
 * @author Alan Shimp
 * @author Caleb Beaven
 * @author Krithik Duraisamy
 * 
 * @version 1.00		2025-04-20 Initial version
 * 
 * @version 2.0			2026-10-22 Refactored Role 2 to Curator
 * @version 2.1			2026-10-22 Updated the Curator UI to use the shared sage theme
 * @version 2.2			2026-10-22 Added responsive window sizing
 * 
 * @version 2.3			2026-11-17 Added the lesson publication interface
 * @version 2.4			2026-11-27 Added lesson search and AI search suggestions
 *  
 */

public class ViewCuratorHome {
	
	/*-*******************************************************************************************

	Attributes
	
	 */
	
	// These are the application values required by the user interface
	
	private static double width = applicationMain.FoundationsMain.WINDOW_WIDTH;
	private static double height = applicationMain.FoundationsMain.WINDOW_HEIGHT;

	// Defines the muted sage page background
	private static final String PAGE_BACKGROUND = "#F1F4F0";

	// Defines the white surface color used by buttons and menus
	private static final String SURFACE_COLOR = "#FFFFFF";

	// Defines the dark sage accent color
	private static final String ACCENT_COLOR = "#405D52";

	// Defines the soft sage hover color
	private static final String HOVER_BACKGROUND = "#E1E9E4";

	// Defines the primary text color
	private static final String PRIMARY_TEXT = "#203029";

	// Defines the secondary text color
	private static final String SECONDARY_TEXT = "#65736B";

	// Defines the subtle border color
	private static final String BORDER_COLOR = "#CAD7D0";


	// These are the widget attributes for the GUI. There are 3 areas for this GUI.
	
	// GUI Area 1: It informs the user about the purpose of this page, whose account is being used,
	// and a button to allow this user to update the account settings
	protected static Label label_PageTitle = new Label();
	protected static Label label_UserDetails = new Label();
	protected static Label label_Welcome = new Label();
	protected static Button button_UpdateThisUser = new Button("Account Update");

	// Displays the active username inside Account Settings
	private static Label label_MenuUserName = new Label();

	// Opens the existing account update page
	private static Label label_AccountUpdates = new Label("Account Updates");

	// Logs the active curator out
	private static Label label_Logout = new Label("Logout");

	// Quits the application from Account Settings
	private static Label label_Quit = new Label("Quit");

	// Displays the Account Settings menu
	private static Popup menu_AccountSettings = new Popup();
		
	// This is a separator and it is used to partition the GUI for various tasks
	protected static Line line_Separator1 = new Line(20, 95, width-20, 95);

	// GUI Area 2: Displays every lesson and allows the Curator to publish one
	protected static Label label_LessonsTitle = new Label("LESSONS TO REVIEW");
	protected static Button button_RefreshLessons = new Button("Refresh");
	protected static Button button_Publish = new Button("Publish");
	protected static Label label_Status = new Label();
	protected static VBox box_Lessons = new VBox(8);
	protected static ScrollPane scroll_Lessons = new ScrollPane(box_Lessons);
	
	// Searches the lessons and displays AI-recommended search terms
	protected static TextField field_Search = new TextField();
	protected static Button button_Search = new Button("Search");
	protected static Button button_ClearSearch = new Button("Clear");
	private static Label label_Suggestions = new Label("Try:");
	private static HBox box_Suggestions = new HBox(8);

	// Stores the lesson and card currently selected by the Curator
	private static LessonsLearned selectedLesson;
	private static Button selectedLessonCard;
	
	// This is a separator and it is used to partition the GUI for various tasks
	protected static Line line_Separator4 = new Line(20, 525, width-20,525);
	
	// GUI Area 3: This is last of the GUI areas.  It is used for quitting the application and for
	// logging out.
	protected static Button button_Logout = new Button("Logout");
	protected static Button button_Quit = new Button("Quit");

	// This is the end of the GUI objects for the page.
	
	// These attributes are used to configure the page and populate it with this user's information
	private static ViewCuratorHome theView;		// Used to determine if instantiation of the class
												// is needed

	// Reference for the in-memory database so this package has access
	private static Database theDatabase = applicationMain.FoundationsMain.database;

	protected static Stage theStage;			// The Stage that JavaFX has established for us
	protected static Pane theRootPane;			// The Pane that holds all the GUI widgets	

	// Centers the scaled Curator page inside the available window
	private static StackPane theResponsiveRoot;

	// Groups the Curator page so all widgets scale together
	private static Group theScaledContent;

	protected static User theUser;				// The current logged in User
	
	private static Scene theCuratorHomeScene;		// The shared Scene each invocation populates
	protected static final int theRole = 3;		// Admin: 1; Contributor: 2; Curator: 3

	/*-*******************************************************************************************

	Constructors
	
	 */

	/**********
	 * <p> Method: displayCuratorHome(Stage ps, User user) </p>
	 * 
	 * <p> Description: This method is the single entry point from outside this package to cause
	 * the Curator Home page to be displayed.
	 * 
	 * It first sets up every shared attributes so we don't have to pass parameters.
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
	 * @param user specifies the User for this GUI and it's methods
	 * 
	 */
	public static void displayCuratorHome(Stage ps, User user) {
		
		// Establish the references to the GUI and the current user
		theStage = ps;
		theUser = user;
		
		// If not yet established, populate the static aspects of the GUI
		if (theView == null) theView = new ViewCuratorHome();		// Instantiate singleton if needed
		
		// Populate the dynamic aspects of the GUI with the data from the user and the current
		// state of the system.
		theDatabase.getUserAccountDetails(user.getUserName());
		applicationMain.FoundationsMain.activeHomePage = theRole;
		
		// Stores the active username in the hidden user details label
		label_UserDetails.setText("User: " + theUser.getUserName());

		// Displays the username inside Account Settings
		label_MenuUserName.setText(theUser.getUserName());

		// Retrieves the curator's preferred first name
		String preferredName = theUser.getPreferredFirstName();

		// Uses the regular first name when no preferred name is available
		if (preferredName == null || preferredName.isBlank()) {
			preferredName = theUser.getFirstName();
		}

		// Uses the username when neither first-name field contains a value
		if (preferredName == null || preferredName.isBlank()) {
			preferredName = theUser.getUserName();
		}

		// Displays the personalized welcome message
		label_Welcome.setText("Welcome, " + preferredName);

		// Loads the current lesson collection whenever the page is displayed
		ControllerCuratorHome.performClearSearch();

		// Set the title for the window, display the page, and wait for the Admin to do something
		theStage.setTitle("CSE 360 Foundations: Curator Home Page");
		theStage.setScene(theCuratorHomeScene);						// Set this page onto the stage
		theStage.show();											// Display it to the user
	}
	
	/**********
	 * <p> Method: ViewCuratorHome() </p>
	 * 
	 * <p> Description: This method initializes all the elements of the graphical user interface.
	 * This method determines the location, size, font, color, and change and event handlers for
	 * each GUI object. </p>
	 * 
	 * This is a singleton and is only performed once.  Subsequent uses fill in the changeable
	 * fields using the displayCuratorHome method.</p>
	 * 
	 */
	private ViewCuratorHome() {
		
		// Creates the pane that contains the Curator page
		theRootPane = new Pane();

		// Keeps the original Curator page dimensions
		theRootPane.setPrefSize(width, height);
		theRootPane.setMinSize(width, height);
		theRootPane.setMaxSize(width, height);

		// Applies the muted sage page background
		theRootPane.setStyle("-fx-background-color: " + PAGE_BACKGROUND + ";");

		// Groups every Curator page element so they scale together
		theScaledContent = new Group(theRootPane);

		// Centers the scaled page inside the available window
		theResponsiveRoot = new StackPane(theScaledContent);
		theResponsiveRoot.setAlignment(Pos.CENTER);
		theResponsiveRoot.setStyle("-fx-background-color: " + PAGE_BACKGROUND + ";");

		// Creates the responsive Curator scene
		theCuratorHomeScene = new Scene(theResponsiveRoot, width, height);

		// Configures proportional scaling for the page
		configureResponsiveLayout();
		
		// Set the title for the window
		
		// Populate the window with the title and other common widgets and set their static state
		
		// GUI Area 1
		label_PageTitle.setText("Curator Workspace");
		setupLabelUI(label_PageTitle, "Helvetica", 14, 360, Pos.BASELINE_LEFT, 30, 15);
		label_PageTitle.setStyle("-fx-text-fill: " + PRIMARY_TEXT + "; -fx-font-weight: bold;");

		label_UserDetails.setText("User: " + theUser.getUserName());
		setupLabelUI(label_UserDetails, "Helvetica", 12, 300, Pos.BASELINE_LEFT, 31, 78);
		label_UserDetails.setStyle("-fx-text-fill: " + SECONDARY_TEXT + ";");
		label_UserDetails.setVisible(false);

		// Positions the personalized welcome message
		setupLabelUI(label_Welcome, "Helvetica", 27, 500, Pos.BASELINE_LEFT, 30, 38);
		label_Welcome.setStyle("-fx-text-fill: " + PRIMARY_TEXT + "; -fx-font-weight: bold;");
		
		// Positions and styles Account Settings
		button_UpdateThisUser.setText("Account Settings");
		setupButtonUI(button_UpdateThisUser, "Helvetica", 12, 170, Pos.CENTER, 600, 31);
		styleSecondaryButton(button_UpdateThisUser);
		configureAccountMenu();
		
		// GUI Area 2

		// Displays the heading for the lesson review workspace
		setupLabelUI(label_LessonsTitle, "Helvetica", 12, 260, Pos.BASELINE_LEFT, 30, 112);
		label_LessonsTitle.setStyle(
				"-fx-text-fill: " + ACCENT_COLOR + ";"
				+ "-fx-font-weight: bold;");

		// Refreshes the lesson collection from the database
		setupButtonUI(button_RefreshLessons, "Helvetica", 12, 130, Pos.CENTER, 500, 100);
		styleSecondaryButton(button_RefreshLessons);
		button_RefreshLessons.setOnAction(
				(_) -> ControllerCuratorHome.performRefreshLessons());

		// Publishes or unpublishes the selected lesson
		setupButtonUI(button_Publish, "Helvetica", 12, 130, Pos.CENTER, 640, 105);
		stylePrimaryButton(button_Publish);
		button_Publish.setDisable(true);
		button_Publish.setOnAction(
				(_) -> ControllerCuratorHome.performTogglePublication());
		
		// Configures the search field; pressing Enter runs the search
		field_Search.setPromptText("Search lessons by title, author, or category");
		field_Search.setLayoutX(30);
		field_Search.setLayoutY(140);
		field_Search.setPrefWidth(450);
		field_Search.setOnAction(
				(_) -> ControllerCuratorHome.performSearch(field_Search.getText()));

		// Runs the search using the text in the search field
		setupButtonUI(button_Search, "Helvetica", 12, 100, Pos.CENTER, 490, 140);
		stylePrimaryButton(button_Search);
		button_Search.setOnAction(
				(_) -> ControllerCuratorHome.performSearch(field_Search.getText()));

		// Clears the search and shows every lesson again
		setupButtonUI(button_ClearSearch, "Helvetica", 12, 100, Pos.CENTER, 600, 140);
		styleSecondaryButton(button_ClearSearch);
		button_ClearSearch.setOnAction(
				(_) -> ControllerCuratorHome.performClearSearch());

		// Configures the row of AI-recommended search terms
		label_Suggestions.setFont(Font.font("Helvetica", 12));
		label_Suggestions.setStyle("-fx-text-fill: " + SECONDARY_TEXT + ";");
		box_Suggestions.setAlignment(Pos.CENTER_LEFT);
		box_Suggestions.setLayoutX(30);
		box_Suggestions.setLayoutY(176);
		box_Suggestions.getChildren().add(label_Suggestions);

		// Arranges the lesson cards inside the review list
		box_Lessons.setPadding(new Insets(10));
		box_Lessons.setStyle("-fx-background-color: " + SURFACE_COLOR + ";");

		// Creates the scrollable white card containing the lesson collection
		scroll_Lessons.setLayoutX(30);
		scroll_Lessons.setLayoutY(210);
		scroll_Lessons.setPrefSize(740, 250);
		scroll_Lessons.setFitToWidth(true);
		scroll_Lessons.setStyle(
				"-fx-background-color: " + SURFACE_COLOR + ";"
				+ "-fx-border-color: " + BORDER_COLOR + ";"
				+ "-fx-border-width: 1;"
				+ "-fx-background-radius: 10;"
				+ "-fx-border-radius: 10;");

		// Displays selection, success, and error information below the list
		setupLabelUI(label_Status, "Helvetica", 11, 740, Pos.BASELINE_LEFT, 32, 472);
		label_Status.setStyle("-fx-text-fill: " + SECONDARY_TEXT + ";");
		
		
		// Hides the original session buttons because their actions are in Account Settings
		button_Logout.setVisible(false);
		button_Quit.setVisible(false);

		// Makes the old separator lines blend into the page background
		line_Separator1.setStyle("-fx-stroke: " + PAGE_BACKGROUND + ";");
		line_Separator4.setStyle("-fx-stroke: " + PAGE_BACKGROUND + ";");

		// This is the end of the GUI initialization code
		
		// Place all of the widget items into the Root Pane's list of children
		theRootPane.getChildren().addAll(
				label_PageTitle,
				label_UserDetails,
				label_Welcome,
				button_UpdateThisUser,
				label_LessonsTitle,
				button_RefreshLessons,
				button_Publish,
				field_Search,
				button_Search,
				button_ClearSearch,
				box_Suggestions,
				scroll_Lessons,
				label_Status,
				line_Separator1,
				line_Separator4,
				button_Logout,
				button_Quit);
	}
	
	/******
	 *
	 * <p> Method: displayLessons() </p>
	 *
	 * <p> Description: Rebuilds the Curator lesson review list using the current
	 * records returned by the Model. </p>
	 *
	 * @param lessons specifies the current lesson collection
	 *
	 */
	protected static void displayLessons(LessonsLearnedList lessons) {

		// Removes outdated lesson cards and selection state
		box_Lessons.getChildren().clear();
		selectedLesson = null;
		selectedLessonCard = null;
		button_Publish.setText("Publish");
		button_Publish.setDisable(true);

		// Displays an empty state when there are no lessons to review
		if (lessons == null || lessons.isEmpty()) {
			Label emptyMessage = new Label("No lessons to display.");
			emptyMessage.setStyle(
					"-fx-text-fill: " + SECONDARY_TEXT + ";"
					+ "-fx-font-family: 'Helvetica';"
					+ "-fx-font-size: 12;"
					+ "-fx-padding: 14;");
			box_Lessons.getChildren().add(emptyMessage);
			return;
		}

		// Creates one selectable card for every lesson
		for (LessonsLearned lesson : lessons.getLessons()) {
			box_Lessons.getChildren().add(createLessonCard(lesson));
		}
	}
	
	/******
	 *
	 * <p> Method: clearSearchField() </p>
	 *
	 * <p> Description: Removes the text from the search field. </p>
	 *
	 */
	protected static void clearSearchField() {
		field_Search.clear();
	}

	/******
	 *
	 * <p> Method: showSuggestionsLoading() </p>
	 *
	 * <p> Description: Replaces the suggestion chips with a loading message while
	 * the AI service is working. </p>
	 *
	 */
	protected static void showSuggestionsLoading() {
		label_Suggestions.setText("Finding suggestions...");
		box_Suggestions.getChildren().setAll(label_Suggestions);
	}

	/******
	 *
	 * <p> Method: displaySuggestions() </p>
	 *
	 * <p> Description: Displays one clickable chip for each suggested search term.
	 * Clicking a chip fills the search field and runs that search. </p>
	 *
	 * @param terms specifies the suggested search terms
	 *
	 */
	protected static void displaySuggestions(List<String> terms) {
		label_Suggestions.setText("Try:");
		box_Suggestions.getChildren().setAll(label_Suggestions);

		for (String term : terms) {
			Button chip = new Button(term);
			chip.setFont(Font.font("Helvetica", 11));
			styleSecondaryButton(chip);
			chip.setOnAction((_) -> {
				field_Search.setText(term);
				ControllerCuratorHome.performSearch(term);
			});
			box_Suggestions.getChildren().add(chip);
		}
	}

	/******
	 *
	 * <p> Method: createLessonCard() </p>
	 *
	 * <p> Description: Creates one selectable lesson card showing its title,
	 * author, and current publication status. </p>
	 *
	 * @param lesson specifies the lesson displayed by the card
	 * @return the configured lesson card
	 *
	 */
	private static Button createLessonCard(LessonsLearned lesson) {

		// Creates the card text using the lesson's review information
		String publicationStatus = lesson.isPublished() ? "Published" : "Unpublished";
		Button lessonCard = new Button(
				lesson.getTitle()
				+ "\nBy " + lesson.getAuthor()
				+ "  •  " + lesson.getCategory()
				+ "  •  " + publicationStatus);

		// Allows the card to fill the available list width
		lessonCard.setMaxWidth(Double.MAX_VALUE);
		lessonCard.setMinHeight(58);
		lessonCard.setAlignment(Pos.CENTER_LEFT);
		applyLessonCardStyle(lessonCard, false);

		// Applies the soft sage hover color to an unselected card
		lessonCard.setOnMouseEntered((_) -> {
			if (lessonCard != selectedLessonCard) {
				lessonCard.setStyle(getLessonCardStyle(HOVER_BACKGROUND, ACCENT_COLOR));
			}
		});

		// Restores the white card after the pointer leaves
		lessonCard.setOnMouseExited((_) -> {
			if (lessonCard != selectedLessonCard) {
				applyLessonCardStyle(lessonCard, false);
			}
		});

		// Selects the lesson represented by this card
		lessonCard.setOnAction((_) -> {
			highlightLessonCard(lessonCard);
			ControllerCuratorHome.performSelectLesson(lesson);
		});

		return lessonCard;
	}

	/******
	 *
	 * <p> Method: setSelectedLesson() </p>
	 *
	 * <p> Description: Stores the lesson selected by the Curator and updates the
	 * publication action to match its current status. </p>
	 *
	 * @param lesson specifies the lesson selected for review
	 *
	 */
	protected static void setSelectedLesson(LessonsLearned lesson) {
		selectedLesson = lesson;
		button_Publish.setDisable(lesson == null);

		if (lesson == null) {
			button_Publish.setText("Publish");
			return;
		}

		button_Publish.setText(lesson.isPublished() ? "Unpublish" : "Publish");
		showStatus("Selected: " + lesson.getTitle(), false);
	}

	/******
	 *
	 * <p> Method: getSelectedLesson() </p>
	 *
	 * <p> Description: Returns the lesson currently selected for a publication
	 * action. </p>
	 *
	 * @return the selected lesson, or null when no lesson is selected
	 *
	 */
	protected static LessonsLearned getSelectedLesson() {
		return selectedLesson;
	}

	/******
	 *
	 * <p> Method: highlightLessonCard() </p>
	 *
	 * <p> Description: Applies the selected style to one lesson card and restores
	 * the previous card's normal appearance. </p>
	 *
	 * @param lessonCard specifies the newly selected lesson card
	 *
	 */
	private static void highlightLessonCard(Button lessonCard) {
		if (selectedLessonCard != null) {
			applyLessonCardStyle(selectedLessonCard, false);
		}

		selectedLessonCard = lessonCard;
		applyLessonCardStyle(selectedLessonCard, true);
	}

	/******
	 *
	 * <p> Method: applyLessonCardStyle() </p>
	 *
	 * <p> Description: Applies either the normal or selected appearance to a
	 * lesson card. </p>
	 *
	 * @param lessonCard specifies the lesson card being styled
	 * @param selected specifies whether the card is currently selected
	 *
	 */
	private static void applyLessonCardStyle(Button lessonCard, boolean selected) {
		lessonCard.setStyle(selected
				? getLessonCardStyle(HOVER_BACKGROUND, ACCENT_COLOR)
				: getLessonCardStyle(SURFACE_COLOR, BORDER_COLOR));
	}

	/******
	 *
	 * <p> Method: getLessonCardStyle() </p>
	 *
	 * <p> Description: Builds a lesson card style using the requested background
	 * and border colors. </p>
	 *
	 * @param backgroundColor specifies the card background color
	 * @param borderColor specifies the card border color
	 * @return the completed JavaFX style
	 *
	 */
	private static String getLessonCardStyle(String backgroundColor, String borderColor) {
		return "-fx-background-color: " + backgroundColor + ";"
				+ "-fx-text-fill: " + PRIMARY_TEXT + ";"
				+ "-fx-border-color: " + borderColor + ";"
				+ "-fx-border-width: 1;"
				+ "-fx-background-radius: 8;"
				+ "-fx-border-radius: 8;"
				+ "-fx-font-family: 'Helvetica';"
				+ "-fx-font-size: 12;"
				+ "-fx-padding: 9 12 9 12;"
				+ "-fx-cursor: hand;";
	}

	/******
	 *
	 * <p> Method: showStatus() </p>
	 *
	 * <p> Description: Displays a publication, selection, or error message beneath
	 * the lesson review list. </p>
	 *
	 * @param message specifies the message displayed to the Curator
	 * @param error specifies whether the message uses the error color
	 *
	 */
	protected static void showStatus(String message, boolean error) {
		label_Status.setText(message == null ? "" : message);
		label_Status.setStyle(
				"-fx-text-fill: " + (error ? "#B44747" : SECONDARY_TEXT) + ";"
				+ "-fx-font-family: 'Helvetica';"
				+ "-fx-font-size: 11;");
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

	/******
	 *
	 * <p> Method: stylePrimaryButton() </p>
	 *
	 * <p> Description: Applies the dark sage appearance used by the primary
	 * publication action. </p>
	 *
	 * @param button button receiving the primary style
	 *
	 */
	private static void stylePrimaryButton(Button button) {

		// Defines the primary action's normal appearance
		String base = "-fx-background-color: " + ACCENT_COLOR + ";"
				+ "-fx-text-fill: white;"
				+ "-fx-border-color: " + ACCENT_COLOR + ";"
				+ "-fx-border-width: 1;"
				+ "-fx-background-radius: 8;"
				+ "-fx-border-radius: 8;"
				+ "-fx-cursor: hand;";

		// Defines the primary action's hover appearance
		String hover = "-fx-background-color: #304A40;"
				+ "-fx-text-fill: white;"
				+ "-fx-border-color: #304A40;"
				+ "-fx-border-width: 1;"
				+ "-fx-background-radius: 8;"
				+ "-fx-border-radius: 8;"
				+ "-fx-cursor: hand;";

		// Applies the normal and hover appearances
		button.setStyle(base);
		button.setOnMouseEntered((_) -> button.setStyle(hover));
		button.setOnMouseExited((_) -> button.setStyle(base));
	}

	/******
	 * 
	 * <p> Method: styleSecondaryButton() </p>
	 * 
	 * <p> Description: Applies the white and sage appearance used by secondary
	 * Curator page actions. </p>
	 * 
	 * @param button button receiving the secondary style
	 * 
	 */

	private static void styleSecondaryButton(Button button) {

		// Defines the button's normal appearance
		String base = "-fx-background-color: " + SURFACE_COLOR + ";"
				+ "-fx-text-fill: " + PRIMARY_TEXT + ";"
				+ "-fx-border-color: " + BORDER_COLOR + ";"
				+ "-fx-border-width: 1;"
				+ "-fx-background-radius: 8;"
				+ "-fx-border-radius: 8;"
				+ "-fx-background-insets: 0;"
				+ "-fx-padding: 8 14 8 14;"
				+ "-fx-cursor: hand;"
				+ "-fx-focus-color: transparent;"
				+ "-fx-faint-focus-color: transparent;";

		// Defines the button's hover appearance
		String hover = "-fx-background-color: " + HOVER_BACKGROUND + ";"
				+ "-fx-text-fill: " + PRIMARY_TEXT + ";"
				+ "-fx-border-color: " + ACCENT_COLOR + ";"
				+ "-fx-border-width: 1;"
				+ "-fx-background-radius: 8;"
				+ "-fx-border-radius: 8;"
				+ "-fx-background-insets: 0;"
				+ "-fx-padding: 8 14 8 14;"
				+ "-fx-cursor: hand;"
				+ "-fx-focus-color: transparent;"
				+ "-fx-faint-focus-color: transparent;";

		// Applies the normal appearance
		button.setStyle(base);

		// Applies the sage appearance when the pointer enters
		button.setOnMouseEntered((_) -> button.setStyle(hover));

		// Restores the normal appearance when the pointer exits
		button.setOnMouseExited((_) -> button.setStyle(base));
	}

	/******
	 * 
	 * <p> Method: styleMenuAction() </p>
	 * 
	 * <p> Description: Styles an Account Settings action using either the normal
	 * sage appearance or the destructive red appearance. </p>
	 * 
	 * @param label clickable Account Settings row
	 * @param danger true when the action should use destructive styling
	 * 
	 */

	private static void styleMenuAction(Label label, boolean danger) {

		// Selects the correct text color for the action
		String textColor = danger ? "#B44747" : PRIMARY_TEXT;

		// Selects the correct hover color for the action
		String hoverColor = danger ? "#F7E8E8" : HOVER_BACKGROUND;

		// Defines the action's normal appearance
		String base = "-fx-background-color: " + SURFACE_COLOR + ";"
				+ "-fx-text-fill: " + textColor + ";"
				+ "-fx-font-family: 'Helvetica';"
				+ "-fx-font-size: 13;"
				+ "-fx-padding: 9 14 9 14;"
				+ "-fx-background-radius: 6;"
				+ "-fx-cursor: hand;";

		// Defines the action's hover appearance
		String hover = "-fx-background-color: " + hoverColor + ";"
				+ "-fx-text-fill: " + textColor + ";"
				+ "-fx-font-family: 'Helvetica';"
				+ "-fx-font-size: 13;"
				+ "-fx-padding: 9 14 9 14;"
				+ "-fx-background-radius: 6;"
				+ "-fx-cursor: hand;";

		// Gives each action a consistent width
		label.setMinWidth(155);

		// Applies the normal appearance
		label.setStyle(base);

		// Applies the hover appearance when the pointer enters
		label.setOnMouseEntered((_) -> label.setStyle(hover));

		// Restores the normal appearance when the pointer exits
		label.setOnMouseExited((_) -> label.setStyle(base));
	}

	/******
	 * 
	 * <p> Method: configureAccountMenu() </p>
	 * 
	 * <p> Description: Configures Account Settings and connects the account update,
	 * logout, and quit actions. </p>
	 * 
	 */

	private static void configureAccountMenu() {

		// Gives the username row room for longer usernames
		label_MenuUserName.setMinWidth(170);

		// Applies the secondary username style
		label_MenuUserName.setStyle(
				"-fx-text-fill: " + SECONDARY_TEXT + ";"
				+ "-fx-font-family: 'Helvetica';"
				+ "-fx-font-size: 12;"
				+ "-fx-padding: 7 12 5 12;");

		// Styles the menu actions
		styleMenuAction(label_AccountUpdates, false);
		styleMenuAction(label_Logout, false);
		styleMenuAction(label_Quit, true);

		// Creates the menu dividers
		Separator accountSeparator = new Separator();
		Separator sessionSeparator = new Separator();

		// Arranges the menu content vertically
		VBox menuContent = new VBox(
				2,
				label_MenuUserName,
				accountSeparator,
				label_AccountUpdates,
				sessionSeparator,
				label_Logout,
				label_Quit);

		// Sets the menu width
		menuContent.setMinWidth(185);

		// Applies the white card appearance
		menuContent.setStyle(
				"-fx-background-color: " + SURFACE_COLOR + ";"
				+ "-fx-border-color: " + BORDER_COLOR + ";"
				+ "-fx-border-width: 1;"
				+ "-fx-background-radius: 8;"
				+ "-fx-border-radius: 8;"
				+ "-fx-padding: 5;"
				+ "-fx-effect: dropshadow(gaussian, rgba(38, 51, 45, 0.18), 12, 0.15, 0, 4);");

		// Adds the menu content only once
		if (menu_AccountSettings.getContent().isEmpty()) {
			menu_AccountSettings.getContent().add(menuContent);
		}

		// Allows outside clicks and Escape to close the menu
		menu_AccountSettings.setAutoHide(true);
		menu_AccountSettings.setHideOnEscape(true);

		// Opens the account update page
		label_AccountUpdates.setOnMouseClicked((_) -> {
			menu_AccountSettings.hide();
			ControllerCuratorHome.performUpdate();
		});

		// Logs out the active curator
		label_Logout.setOnMouseClicked((_) -> {
			menu_AccountSettings.hide();
			ControllerCuratorHome.performLogout();
		});

		// Quits the application
		label_Quit.setOnMouseClicked((_) -> {
			menu_AccountSettings.hide();
			ControllerCuratorHome.performQuit();
		});

		// Opens Account Settings when clicked
		button_UpdateThisUser.setOnAction((_) -> showAccountMenu());

		// Opens Account Settings when the pointer enters
		button_UpdateThisUser.addEventHandler(
				MouseEvent.MOUSE_ENTERED,
				(_) -> showAccountMenu());
	}

	/******
	 * 
	 * <p> Method: showAccountMenu() </p>
	 * 
	 * <p> Description: Displays Account Settings directly beneath its button. </p>
	 * 
	 */

	private static void showAccountMenu() {

		// Stops when the menu is already displayed
		if (menu_AccountSettings.isShowing() || button_UpdateThisUser.getScene() == null) {
			return;
		}

		// Calculates the position beneath Account Settings
		Point2D location = button_UpdateThisUser.localToScreen(
				0,
				button_UpdateThisUser.getHeight() + 6);

		// Displays the menu when the screen position is available
		if (location != null) {
			menu_AccountSettings.show(
					button_UpdateThisUser.getScene().getWindow(),
					location.getX(),
					location.getY());
		}
	}

	/******
	 * 
	 * <p> Method: configureResponsiveLayout() </p>
	 * 
	 * <p> Description: Scales the complete Curator interface to fit the
	 * available window while preserving its original proportions and positions. </p>
	 * 
	 */

	private static void configureResponsiveLayout() {

		// Calculates the available horizontal scale
		DoubleBinding widthScale =
				theResponsiveRoot.widthProperty().divide(width);

		// Calculates the available vertical scale
		DoubleBinding heightScale =
				theResponsiveRoot.heightProperty().divide(height);

		// Uses the smaller scale to prevent the page from being cut off
		NumberBinding pageScale =
				Bindings.min(widthScale, heightScale);

		// Scales the page horizontally
		theScaledContent.scaleXProperty().bind(pageScale);

		// Scales the page vertically
		theScaledContent.scaleYProperty().bind(pageScale);
	}

}
