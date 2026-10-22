package guiContributor;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import database.Database;
import entityClasses.LessonsLearned;
import entityClasses.LessonsLearnedList;
import entityClasses.User;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;


/*******
 * 
 * <p> Title: ViewContributorHome Class. </p>
 * 
 * <p> Description: The JavaFX-based Contributor Home Page. This class defines and displays
 * the contributor interface for creating, searching, viewing, updating, and deleting lessons
 * and managing lesson experience information. </p>
 * 
 * <p> Copyright: Lynn Robert Carter © 2025 </p>
 * 
 * @author Lynn Robert Carter
 * 
 * @version 1.00		2025-08-20 Initial version
 * @version 1.01		2026-10-04 Added Contributor role commands
 * @version 1.02 		2026-10-12 Updated Contributor Home Page UI 
 *  
 */

public class ViewContributorHome {
	
	/*-*******************************************************************************************

	Attributes
	
	 */
	
	// These are the application values required by the user interface

	private static double width = applicationMain.FoundationsMain.WINDOW_WIDTH;
	private static double height = applicationMain.FoundationsMain.WINDOW_HEIGHT;
	
	// Defines the soft sage background used by the main page
	private static final String PAGE_BACKGROUND = "#F1F4F0";

	// Defines the white background used by cards, forms, and menus
	private static final String SURFACE_COLOR = "#FFFFFF";

	// Defines the primary dark-sage accent color
	private static final String ACCENT_COLOR = "#405D52";

	// Defines the darker sage used when primary buttons are hovered
	private static final String ACCENT_HOVER = "#344C43";

	// Defines the main dark text color
	private static final String PRIMARY_TEXT = "#203029";

	// Defines the lighter text color used for secondary information
	private static final String SECONDARY_TEXT = "#65736B";

	// Defines the subtle border color around fields and cards
	private static final String BORDER_COLOR = "#CAD7D0";

	// Defines the soft sage background used when secondary controls are hovered
	private static final String HOVER_BACKGROUND = "#E2EBE6";

	// These are the widget attributes for the GUI. There are 3 areas for this GUI.
	
	// GUI Area 1: It informs the user about the purpose of this page, whose account is being used,
	// and a button to allow this user to update the account settings

	protected static Label label_PageTitle = new Label();

	// Displays a personalized welcome message for the contributor
	protected static Label label_Welcome = new Label();

	// Labels the expandable advanced-search section
	protected static Label label_LessonSection = new Label("ADVANCED SEARCH OPTIONS");

	// Labels the main lesson-search card
	protected static Label label_SearchSection = new Label("SEARCH LESSONS");

	// Labels either the contributor’s lessons or the current search results
	protected static Label label_ResultsSection = new Label("RESULTS");

	// Opens filtering and sorting controls for My Lessons
	protected static Button button_FilterSort = new Button("Filter & Sort");

	// Returns from search results to the contributor’s complete lesson list
	protected static Button button_BackToLessons = new Button("Back");

	// Contains the category and ordering controls
	protected static ContextMenu menu_FilterSort = new ContextMenu();

	// Selects a category used to filter the contributor’s lessons
	protected static ComboBox<String> combo_CategoryFilter = new ComboBox<>();

	// Selects the ordering applied to the contributor’s lessons
	protected static ComboBox<String> combo_SortOrder = new ComboBox<>();

	// Applies the selected filter and sort settings
	protected static Button button_ApplyFilterSort = new Button("Apply");

	// Restores the default filter and ordering settings
	protected static Button button_ClearFilterSort = new Button("Clear");

	protected static Label label_UserDetails = new Label();
	protected static Button button_UpdateThisUser = new Button("Account Update");	

	// Contains account-related actions without using JavaFX's native blue menu skin
	protected static Popup menu_AccountSettings = new Popup();

	// Displays the active username inside Account Settings
	protected static Label label_MenuUserName = new Label();

	// Opens the existing account-update page
	protected static Label label_AccountUpdates = new Label("Account Updates");

	// Logs the current user out
	protected static Label label_Logout = new Label("Logout");

	// Closes the application
	protected static Label label_Quit = new Label("Quit");

	// Opens actions that add information to a lesson
	protected static Button button_Add = new Button("Add");

	// Contains the available Add actions
	protected static Popup menu_Add = new Popup();

	// Opens the standalone Experience form
	protected static Label label_AddExperience = new Label("Experience");

	// Provides the white card behind advanced-search controls
	protected static Pane pane_LessonCard = new Pane();

	// Provides the white card behind the normal search controls
	protected static Pane pane_SearchCard = new Pane();
	
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

	// Expands or collapses the Lesson ID and Category search fields
	protected static Button button_AdvancedSearch = new Button("Advanced Search");
	
	// Clears all text fields for new inputs
	protected static Button button_ClearSearch = new Button("Clear Search");
		
	// Output area for records, status updates, and test-case error messages
	protected static TextArea textarea_Display = new TextArea();

	// Vertically arranges the contributor’s clickable lesson-title cards
	protected static VBox box_MyLessons = new VBox(8);

	// Allows a long collection of lesson-title cards to be scrolled
	protected static ScrollPane scroll_MyLessons = new ScrollPane(box_MyLessons);
	
	// This is a separator and it is used to partition the GUI for various tasks
	protected static Line line_Separator4 = new Line(20, 525, width-20,525);
	
	// GUI Area 3: This is last of the GUI areas.  It is used for quitting the application and for
	// logging out.
	protected static Button button_Logout = new Button("Logout");
	protected static Button button_Quit = new Button("Quit");

	// This is the end of the GUI objects for the page.
	
	// These attributes are used to configure the page and populate it with this user's information
	private static ViewContributorHome theView;		// Used to determine if instantiation of the class
												// is needed

	// Reference for the in-memory database so this package has access
	private static Database theDatabase = applicationMain.FoundationsMain.database;

	protected static Stage theStage;			// The Stage that JavaFX has established for us	
	protected static Pane theRootPane;			// The Pane that holds all the GUI widgets
	protected static User theUser;				// The current logged in User
	

	private static Scene theViewContributorHomeScene;	// The shared Scene each invocation populates
	protected static final int theRole = 2;		// Admin: 1; Contributor: 2; Curator: 3

	/*-*******************************************************************************************

	Constructors
	
	 */

	/**********
	 * <p> Method: displayContributorHome(Stage ps, User user) </p>
	 * 
	 * <p> Description: This method is the single entry point from outside this package to cause
	 * the Contributor home page to be displayed.
	 * 
	 * It first establishes shared attributes to avoid repeatedly passing the same parameters.
	 * 
	 * It then checks to see if the page has been setup.  If not, it instantiates the class, 
	 * initializes all the static aspects of the GUI widgets (e.g., location on the page, font,
	 * size, and any methods to be performed).
	 * 
	 * After the instantiation, the code then populates the elements that change based on the user
	 * and the system's current state.  It then sets the Scene onto the stage, and makes it visible
	 * to the user.
	 * 
	 * @param ps specifies the JavaFX Stage to be used for this GUI and its methods
	 * 
	 * @param user specifies the User for this GUI and its methods
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

		// Stores the active username in the original user details label
		label_UserDetails.setText("User: " + theUser.getUserName());

		// Hides the original username label to keep the header uncluttered
		label_UserDetails.setVisible(false);

		// Changes the original account button text to the new menu name
		button_UpdateThisUser.setText("Account Settings");

		// Displays the active username inside the Account Settings menu
		label_MenuUserName.setText(theUser.getUserName());

		// Retrieves the contributor's preferred first name
		String preferredName = theUser.getPreferredFirstName();

		// Uses the regular first name when no preferred name is available
		if (preferredName == null || preferredName.isBlank()) {
			preferredName = theUser.getFirstName();
		}

		// Uses the username when neither first-name field contains a value
		if (preferredName == null || preferredName.isBlank()) {
			preferredName = theUser.getUserName();
		}

		// Displays the personalized welcome message.
		label_Welcome.setText("Welcome, " + preferredName);

		// Displays My Lessons as the contributor's default landing view
		showMyLessons();
				
		// Set the title for the window, display the page, and wait for the Admin to do something
		theStage.setTitle("CSE 360 Foundations: Contributor Home Page");
		theStage.setScene(theViewContributorHomeScene);
		theStage.show();
	}
	
	/**********
	 * <p> Constructor: ViewContributorHome() </p>
	 * 
	 * <p> Description: This method initializes all the elements of the graphical user interface.
	 * This method determines the location, size, font, color, and change and event handlers for
	 * each GUI object.</p>
	 * 
	 * This is a singleton and is only performed once.  Subsequent uses fill in the changeable
	 * fields using the displayContributorHome method.</p>
	 * 
	 */
	private ViewContributorHome() {

		// Creates the root pane that contains all controls on this page.
		theRootPane = new Pane();

		// Applies the muted-sage background to the complete page.
		theRootPane.setStyle("-fx-background-color: " + PAGE_BACKGROUND + ";");

		// Creates the scene using the project’s standard window dimensions.
		theViewContributorHomeScene = new Scene(theRootPane, width, height);
		
		// Set the title for the window
		
		// Populate the window with the title and other common widgets and set their static state
		
		// GUI Area 1

		// Changes the original page title into the contributor workspace heading
		label_PageTitle.setText("Contributor Workspace");

		// Positions the workspace heading in the upper-left corner
		setupLabelUI(label_PageTitle, "Helvetica", 14, 360, Pos.BASELINE_LEFT, 30, 15);

		// Applies the dark primary text color to the workspace heading
		label_PageTitle.setStyle("-fx-text-fill: " + PRIMARY_TEXT + "; -fx-font-weight: bold;");

		// Stores the active username in the original user-details label
		label_UserDetails.setText("User: " + theUser.getUserName());

		// Configures the original username label even though it remains hidden
		setupLabelUI(label_UserDetails, "Helvetica", 12, 300, Pos.BASELINE_LEFT, 31, 78);

		// Applies the secondary text color to the hidden username label
		label_UserDetails.setStyle("-fx-text-fill: " + SECONDARY_TEXT + ";");

		// Positions the personalized welcome message below the workspace heading
		setupLabelUI(label_Welcome, "Helvetica", 27, 500, Pos.BASELINE_LEFT, 30, 38);

		// Applies the primary heading style to the welcome message
		label_Welcome.setStyle("-fx-text-fill: " + PRIMARY_TEXT + "; -fx-font-weight: bold;");

		// Positions the Account Settings button in the upper-right corner
		setupButtonUI(button_UpdateThisUser, "Helvetica", 12, 170, Pos.CENTER, 600, 31);

		// Temporarily preserves the original account-update action
		button_UpdateThisUser.setOnAction((_) -> ControllerContributorHome.performUpdate());
		
		// GUI Area 2

		// Creates the white card that contains the regular search controls
		configureCard(pane_SearchCard, 20, 108, width - 40, 112);

		// Creates the initially hidden card that contains the advanced search controls
		configureCard(pane_LessonCard, 20, 232, width - 40, 150);

		// Positions the Search Lessons heading inside the regular search card
		setupSectionLabel(label_SearchSection, 38, 117);

		// Positions the Advanced Search Options heading inside the advanced-search card
		setupSectionLabel(label_LessonSection, 38, 241);

		// Positions the heading displayed above lesson results
		setupSectionLabel(label_ResultsSection, 22, 236);

		// Positions the Create button inside the regular search card
		setupButtonUI(button_CreateLesson, "Helvetica", 12, 105, Pos.CENTER, 540, 113);

		// Opens the existing Create Lesson form when the Create button is clicked
		button_CreateLesson.setOnAction((_) -> ControllerContributorHome.handleCreateLesson());

		// Applies the primary sage button style to the Create button
		stylePrimaryButton(button_CreateLesson);

		// Positions the Add menu button beside the Create button
		setupButtonUI(button_Add, "Helvetica", 12, 105, Pos.CENTER, 657, 113);

		// Applies the secondary button style to the Add button
		styleSecondaryButton(button_Add);

		// Configures the Add popup
		configureAddMenu();

		// Advanced Search Fields

		// Positions the Lesson ID label inside the advanced-search card
		setupLabelUI(label_LessonId, "Helvetica", 12, 280, Pos.BASELINE_LEFT, 38, 263);

		// Positions and sizes the Lesson ID field
		setupTextUI(textfield_LessonId, "Helvetica", 13, 280, 38, 282, true);

		// Applies the secondary sage text color to the Lesson ID label
		label_LessonId.setStyle("-fx-text-fill: " + SECONDARY_TEXT + "; -fx-font-weight: bold;");

		// Applies the modern input style to the Lesson ID field
		styleTextField(textfield_LessonId);

		// Keeps the original Title controls available to the controller but hides them from this layout
		label_Title.setVisible(false);

		// Hides the original Title field because title searches use the regular search bar
		textfield_Title.setVisible(false);

		// Applies the modern input style in case the retained Title field is used elsewhere
		styleTextField(textfield_Title);

		// Positions the Category label inside the advanced-search card
		setupLabelUI(label_Category, "Helvetica", 12, 420, Pos.BASELINE_LEFT, 342, 263);

		// Positions and sizes the Category field
		setupTextUI(textfield_Category, "Helvetica", 13, 420, 342, 282, true);

		// Applies the secondary sage text color to the Category label
		label_Category.setStyle("-fx-text-fill: " + SECONDARY_TEXT + "; -fx-font-weight: bold;");

		// Applies the modern input style to the Category field
		styleTextField(textfield_Category);

		// Hides My Lessons because the contributor's lessons will appear automatically
		button_ReadAll.setVisible(false);

		// Hides Examine because clicking a lesson card will open its details
		button_ReadById.setVisible(false);

		// Hides Update because it will appear inside the Lesson Details window
		button_UpdateLesson.setVisible(false);

		// Hides Experience because it will appear inside the Add menu
		button_Experience.setVisible(false);

		// Hides Delete because it will appear inside the Lesson Details window
		button_DeleteLesson.setVisible(false);

		// Main Search Controls

		// Hides the original Filter Category label
		label_FilterCategory.setVisible(false);

		// Positions and sizes the main title-or-keyword search field
		setupTextUI(textfield_FilterCategory, "Helvetica", 13, 420, 38, 157, true);

		// Explains how to use the main search field
		textfield_FilterCategory.setPromptText("Search by entering the lesson title or keyword, then press Enter");

		// Applies the modern input-field style
		styleTextField(textfield_FilterCategory);

		// Runs the main search when Enter is pressed inside the search field
		textfield_FilterCategory.setOnAction((_) -> performQuickSearch());

		// Positions the Advanced Search button beside the main search field
		setupButtonUI(button_AdvancedSearch, "Helvetica", 12, 142, Pos.CENTER, 620, 157);
		
		// Applies the secondary sage button style
		styleSecondaryButton(button_AdvancedSearch);

		// Expands or collapses the advanced-search form
		button_AdvancedSearch.setOnAction((_) -> setAdvancedSearchVisible(!pane_LessonCard.isVisible()));
		
		// Positions the Clear Search button beside the main search field
		setupButtonUI(button_ClearSearch, "Helvetica", 12, 142, Pos.CENTER, 468, 157);
		
		// Applies the secondary sage button style
		styleSecondaryButton(button_ClearSearch);

		// Clears text fields
		button_ClearSearch.setOnAction((_) -> clearSearchFields());

		// Changes the original filter button text to Search
		button_FilterCategory.setText("Search");

		// Positions the Search button on the right side of the advanced-search card
		setupButtonUI(button_FilterCategory, "Helvetica", 12, 128, Pos.CENTER, 626, 334);

		// Applies the primary sage button style
		stylePrimaryButton(button_FilterCategory);

		// Runs the advanced search when the Search button is clicked
		button_FilterCategory.setOnAction((_) -> performAdvancedSearch());

		// Runs the advanced search when Enter is pressed inside the Lesson ID field
		textfield_LessonId.setOnAction((_) -> performAdvancedSearch());

		// Runs the advanced search when Enter is pressed inside the Category field
		textfield_Category.setOnAction((_) -> performAdvancedSearch());

		// Starts the page with the advanced-search form collapsed
		setAdvancedSearchVisible(false);

		// Results Area

		// Uses a monospaced font for temporary text-based results
		textarea_Display.setFont(Font.font("Monospaced", 13));

		// Positions the text-results area below the collapsed search card
		textarea_Display.setLayoutX(20);
		textarea_Display.setLayoutY(266);

		// Sizes the text-results area to fit the page width
		textarea_Display.setMinWidth(width - 40);
		textarea_Display.setMaxWidth(width - 40);
		textarea_Display.setPrefHeight(252);

		// Prevents users from editing displayed results
		textarea_Display.setEditable(false);

		// Wraps long result text within the display
		textarea_Display.setWrapText(true);

		// Explains where lesson results and messages will appear
		textarea_Display.setPromptText("Your lesson details and status messages will appear here");

		// Applies the modern results-area style
		styleTextArea(textarea_Display);

		// Positions the scrollable lesson-card results
		scroll_MyLessons.setLayoutX(20);
		scroll_MyLessons.setLayoutY(266);

		// Sizes the scrollable lesson-card results
		scroll_MyLessons.setPrefSize(width - 40, 302);

		// Makes lesson cards expand to the available results width
		scroll_MyLessons.setFitToWidth(true);

		// Hides the lesson-card results until they are populated
		scroll_MyLessons.setVisible(false);

		// Removes the hidden lesson-card results from the active layout
		scroll_MyLessons.setManaged(false);

		// Applies the card styling to the scrollable results area
		scroll_MyLessons.setStyle(
				"-fx-background: " + SURFACE_COLOR + ";"
				+ "-fx-background-color: " + SURFACE_COLOR + ";"
				+ "-fx-border-color: " + BORDER_COLOR + ";"
				+ "-fx-border-width: 1;"
				+ "-fx-border-radius: 10;"
				+ "-fx-background-radius: 10;"
				+ "-fx-padding: 10;");

		// Adds spacing inside the lesson-card collection
		box_MyLessons.setPadding(new Insets(4));

		// Applies the white surface color behind the lesson cards
		box_MyLessons.setStyle("-fx-background-color: " + SURFACE_COLOR + ";");

		// Positions the Filter and Sort button above the results
		setupButtonUI(button_FilterSort, "Helvetica", 11, 118, Pos.CENTER, 650, 228);

		// Applies the secondary sage button style
		styleSecondaryButton(button_FilterSort);

		// Hides Filter and Sort until My Lessons is displayed
		button_FilterSort.setVisible(false);

		// Configures the Filter and Sort popup
		configureFilterSortMenu();

		// Positions the Back button below search results
		setupButtonUI(button_BackToLessons, "Helvetica", 11, 90, Pos.CENTER, 22, 526);

		// Applies the secondary sage button style
		styleSecondaryButton(button_BackToLessons);

		// Hides the Back button until a search is performed
		button_BackToLessons.setVisible(false);

		// Restores My Lessons when Back is clicked
		button_BackToLessons.setOnAction((_) -> showMyLessons());
		
		// GUI Area 3

		// Hides the original footer Logout button
		button_Logout.setVisible(false);

		// Hides the original footer Quit button
		button_Quit.setVisible(false);

		// Applies the secondary sage style to Account Settings
		styleSecondaryButton(button_UpdateThisUser);

		// Configures the Account Settings popup
		configureAccountMenu();

		// Keeps the subtle page separator invisible against the background
		line_Separator1.setStyle("-fx-stroke: " + PAGE_BACKGROUND + ";");

		// Hides the original footer separator
		line_Separator4.setVisible(false);

		// This is the end of the GUI initialization code
		
		// Places the page cards behind their corresponding controls
		theRootPane.getChildren().addAll(
				pane_LessonCard,
				pane_SearchCard,

				// Adds the contributor heading and account controls
				label_PageTitle,
				label_Welcome,
				label_UserDetails,
				button_UpdateThisUser,

				// Adds the section headings
				label_LessonSection,
				label_SearchSection,
				label_ResultsSection,

				// Adds the main search-card controls
				button_CreateLesson,
				button_Add,
				textfield_FilterCategory,
				button_AdvancedSearch,
				button_ClearSearch,

				// Adds the advanced-search controls
				label_LessonId,
				textfield_LessonId,
				label_Title,
				textfield_Title,
				label_Category,
				textfield_Category,
				button_FilterCategory,

				// Adds the results controls
				button_FilterSort,
				button_BackToLessons,
				textarea_Display,
				scroll_MyLessons,

				// Retains the original hidden lesson controls
				button_ReadAll,
				button_ReadById,
				button_UpdateLesson,
				button_Experience,
				button_DeleteLesson,
				label_FilterCategory,

				// Retains the existing page and session controls
				line_Separator1,
				line_Separator4,
				button_Logout,
				button_Quit);

}
	
	
	/******
	 * 
	 * <p> Method: setupLabelUI() </p>
	 * 
	 * <p> Description: Private local method to initialize the standard fields for
	 * a label. </p>
	 * 
	 * @param l		The Label object to be initialized
	 * @param ff	The font to be used
	 * @param f		The size of the font to be used
	 * @param w		The width of the Label
	 * @param p		The alignment (e.g. left, centered, or right)
	 * @param x		The location from the left edge (x axis)
	 * @param y		The location from the top (y axis)
	 * 
	 */

	private static void setupLabelUI(Label l, String ff, double f, double w, Pos p, double x, 
			double y){
		l.setFont(Font.font(ff, f));
		l.setMinWidth(w);
		l.setAlignment(p);
		l.setLayoutX(x);
		l.setLayoutY(y);		
	}
	
	/******
	 * 
	 * <p> Method: setupButtonUI() </p>
	 * 
	 * <p> Description: Private local method to initialize the standard fields for
	 * a button. </p>
	 * 
	 * @param b		The Button object to be initialized
	 * @param ff	The font to be used
	 * @param f		The size of the font to be used
	 * @param w		The width of the Button
	 * @param p		The alignment (e.g. left, centered, or right)
	 * @param x		The location from the left edge (x axis)
	 * @param y		The location from the top (y axis)
	 * 
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
	 * <p> Method: setupTextUI() </p>
	 * 
	 * <p> Description: Private local method to initialize the standard fields for
	 * a text field. </p>
	 * 
	 * @param t		The TextField object to initialize
	 * @param ff	The font family name
	 * @param f		The size of the font
	 * @param w		The layout width of the field
	 * @param x		The location from the left edge (x axis)
	 * @param y		The location from the top edge (y axis)
	 * @param e		The editable flag
	 * 
	 */

	private static void setupTextUI(TextField t, String ff, double f, double w, double x, double y, boolean e) {
		t.setFont(Font.font(ff, f));
		t.setMinWidth(w);
		t.setMaxWidth(w);
		t.setLayoutX(x);
		t.setLayoutY(y);
		t.setEditable(e);
	}

	/******
	 * 
	 * <p> Method: setAdvancedSearchVisible() </p>
	 * 
	 * <p> Description: Expands or collapses the advanced lesson-search controls
	 * and repositions the results area to fit the current page layout. </p>
	 * 
	 * @param visible true to display advanced search or false to hide it
	 * 
	 */
	
	private static void setAdvancedSearchVisible(boolean visible) {

		// Groups every control belonging to the advanced-search form
		Node[] advancedControls = {
				pane_LessonCard,
				label_LessonSection,
				label_LessonId,
				textfield_LessonId,
				label_Category,
				textfield_Category,
				button_FilterCategory
		};

		// Updates the visibility and layout participation of every advanced control
		for (Node control : advancedControls) {
			control.setVisible(visible);
			control.setManaged(visible);
		}

		// Changes the button text to describe its current action
		button_AdvancedSearch.setText(visible ? "Hide Advanced" : "Advanced Search");

		// Moves the results heading beneath the expanded or collapsed search area
		label_ResultsSection.setLayoutY(visible ? 395 : 236);

		// Moves the Filter and Sort button with the results heading
		button_FilterSort.setLayoutY(visible ? 386 : 228);

		// Moves the text-results area beneath the advanced-search form
		textarea_Display.setLayoutY(visible ? 425 : 266);

		// Resizes the text-results area to fit the available space
		textarea_Display.setPrefHeight(visible ? 93 : 252);

		// Moves the lesson-card results beneath the advanced-search form
		scroll_MyLessons.setLayoutY(visible ? 425 : 266);

		// Checks whether the page is currently showing search results
		boolean showingSearchResults = button_BackToLessons.isVisible();

		// Resizes the lesson-card results based on the current page state
		scroll_MyLessons.setPrefHeight(
				visible
						? (showingSearchResults ? 93 : 143)
						: (showingSearchResults ? 252 : 302));

		// Keeps the Back button aligned near the bottom of the page
		button_BackToLessons.setLayoutY(526);
	}

	/******
	 * 
	 * <p> Method: showTextResults() </p>
	 * 
	 * <p> Description: Displays the text-results area and hides the lesson-card
	 * results area. </p>
	 * 
	 */

	private static void showTextResults() {

		// Displays the text-results area
		textarea_Display.setVisible(true);

		// Allows the text-results area to participate in the layout
		textarea_Display.setManaged(true);

		// Hides the lesson-card results
		scroll_MyLessons.setVisible(false);

		// Removes the lesson-card results from the active layout
		scroll_MyLessons.setManaged(false);

		// Displays the Back button while search results are active
		button_BackToLessons.setVisible(true);
	}
	
	/******
	 * 
	 * <p> Method: clearSearchFields() </p>
	 * 
	 * <p> Description: Clears all text fields so that they are empty for a fresh search. </p>
	 * 
	 * 
	 */
	
	private static void clearSearchFields() {
		
		textfield_FilterCategory.clear();
		textfield_LessonId.clear();
		textfield_Title.clear();
		textfield_Category .clear();
		
	}

	/******
	 * 
	 * <p> Method: displayMyLessons() </p>
	 * 
	 * <p> Description: Displays the contributor's lessons as clickable title
	 * cards in the My Lessons results area. </p>
	 * 
	 * @param lessons collection of lessons being displayed
	 * 
	 */

	protected static void displayMyLessons(LessonsLearnedList lessons) {

		// Removes any previously displayed lesson cards
		box_MyLessons.getChildren().clear();

		// Identifies the displayed cards as the contributor's lessons
		label_ResultsSection.setText("MY LESSONS");

		// Displays Filter and Sort for the contributor's lesson collection
		button_FilterSort.setVisible(true);

		// Hides the temporary text-results area
		textarea_Display.setVisible(false);

		// Removes the text-results area from the active layout
		textarea_Display.setManaged(false);

		// Displays the scrollable lesson-card results
		scroll_MyLessons.setVisible(true);

		// Adds the lesson-card results to the active layout
		scroll_MyLessons.setManaged(true);

		// Sizes the results based on whether advanced search is expanded
		scroll_MyLessons.setPrefHeight(
				pane_LessonCard.isVisible() ? 143 : 302);

		// Hides Back while displaying the default My Lessons view
		button_BackToLessons.setVisible(false);

		// Creates one clickable title card for each lesson
		for (LessonsLearned lesson : lessons.getLessons()) {

			// Creates the lesson-title card
			Label title = new Label(lesson.getTitle());

			// Allows the card to fill the available results width
			title.setMaxWidth(Double.MAX_VALUE);

			// Defines the card's normal appearance
			String base =
					"-fx-background-color: " + SURFACE_COLOR + ";"
					+ "-fx-text-fill: " + PRIMARY_TEXT + ";"
					+ "-fx-border-color: " + BORDER_COLOR + ";"
					+ "-fx-border-width: 1;"
					+ "-fx-border-radius: 8;"
					+ "-fx-background-radius: 8;"
					+ "-fx-padding: 13 15 13 15;"
					+ "-fx-font-family: 'Helvetica';"
					+ "-fx-font-size: 14;"
					+ "-fx-font-weight: bold;"
					+ "-fx-cursor: hand;";

			// Defines the soft sage hover appearance
			String hover = base.replace(
					"-fx-background-color: " + SURFACE_COLOR,
					"-fx-background-color: " + HOVER_BACKGROUND);

			// Applies the normal card appearance
			title.setStyle(base);

			// Applies the hover appearance when the pointer enters
			title.setOnMouseEntered((_) -> title.setStyle(hover));

			// Restores the normal appearance when the pointer exits
			title.setOnMouseExited((_) -> title.setStyle(base));

			// Opens the selected lesson in the Lesson Details popup
			title.setOnMouseClicked((_) -> showLessonDetails(lesson));

			// Adds the completed title card to the results
			box_MyLessons.getChildren().add(title);
		}
	}

	/******
	 * 
	 * <p> Method: displaySearchResults() </p>
	 * 
	 * <p> Description: Displays lessons returned by a search using the same
	 * clickable title cards used by My Lessons. </p>
	 * 
	 * @param lessons collection of lessons returned by the database
	 * 
	 */
	
	private static void displaySearchResults(LessonsLearnedList lessons) {

		// Displays matches using the same clickable cards as My Lessons
		displayMyLessons(lessons);

		// Identifies the cards as search results
		label_ResultsSection.setText("RESULTS");

		// Hides Filter and Sort while displaying search results
		button_FilterSort.setVisible(false);

		// Resizes the results based on whether advanced search is expanded
		scroll_MyLessons.setPrefHeight(
				pane_LessonCard.isVisible() ? 93 : 252);

		// Displays the Back button
		button_BackToLessons.setVisible(true);

		// Keeps the Back button above the results container
		button_BackToLessons.toFront();
	}

	/******
	 * 
	 * <p> Method: showMyLessons() </p>
	 * 
	 * <p> Description: Restores the contributor's default My Lessons view, clears
	 * the main search field, and reloads the contributor's stored lessons. </p>
	 * 
	 */

	private static void showMyLessons() {

		// Clears the main search field
		textfield_FilterCategory.clear();

		// Changes the section heading back to My Lessons
		label_ResultsSection.setText("MY LESSONS");

		// Displays Filter and Sort for the contributor's lesson collection
		button_FilterSort.setVisible(true);

		// Hides Back while the default view is active
		button_BackToLessons.setVisible(false);

		// Retrieves and displays the contributor's lessons
		ControllerContributorHome.handleReadAllLessons();
	}

	/******
	 * 
	 * <p> Method: performAdvancedSearch() </p>
	 * 
	 * <p> Description: Searches the active contributor's lessons using either a
	 * Lesson ID or category and displays matching lessons or a validation
	 * message. </p>
	 * 
	 */

	private static void performAdvancedSearch() {

		// Changes the section heading to identify the displayed search results
		label_ResultsSection.setText("RESULTS");

		// Displays the text-results area for validation and empty-result messages
		showTextResults();

		// Hides Filter and Sort until lesson cards are implemented
		button_FilterSort.setVisible(false);

		// Checks whether the user entered a Lesson ID
		if (!textfield_LessonId.getText().trim().isEmpty()) {
			try {
				// Converts the entered Lesson ID into an integer
				int id = Integer.parseInt(textfield_LessonId.getText().trim());

				// Retrieves the lesson associated with the entered ID
				LessonsLearned lesson = theDatabase.getLessonById(id);

				// Checks that the lesson exists and belongs to the active contributor
				if (lesson != null && lesson.getAuthor().equals(theUser.getUserName())) {

					// Creates a collection for the matching lesson
					LessonsLearnedList match = new LessonsLearnedList();

					// Adds the matching lesson to the result collection
					match.addLesson(lesson);

					// Displays the matching lesson
					displaySearchResults(match);
				} else {
					// Explains that no accessible lesson matched the entered ID
					textarea_Display.setText("No lesson record exists with ID " + id);
				}
			} catch (NumberFormatException exception) {
				// Explains that Lesson ID must contain a number
				textarea_Display.setText("Enter a valid numeric lesson ID");
			}

			// Stops after completing the Lesson ID search
			return;
		}

		// Checks whether the user entered a category
		if (!textfield_Category.getText().trim().isEmpty()) {

			// Retrieves lessons matching the category and active contributor
			LessonsLearnedList matches = theDatabase.getLessonsByFilter(
					textfield_Category.getText().trim(),
					theUser.getUserName());

			// Checks whether the category search returned any lessons
			if (matches.isEmpty()) {
				// Explains that the category did not match any lessons
				textarea_Display.setText("No lessons matched that category");
			} else {
				// Displays the lessons returned by the category search
				displaySearchResults(matches);
			}

			// Stops after completing the category search
			return;
		}

		// Explains that at least one advanced-search field is required
		textarea_Display.setText("Enter a lesson ID or category to search");
	}

	/******
	 * 
	 * <p> Method: performQuickSearch() </p>
	 * 
	 * <p> Description: Searches the active contributor's lessons using a partial
	 * title or content keyword and displays the matching lesson cards. </p>
	 * 
	 */

	private static void performQuickSearch() {

		// Changes the section heading to identify the displayed search results
		label_ResultsSection.setText("RESULTS");

		// Displays the text-results area
		showTextResults();

		// Reads and normalizes the entered search text
		String query = textfield_FilterCategory.getText().trim().toLowerCase();

		// Checks whether the user entered anything
		if (query.isEmpty()) {
			textarea_Display.setText("Enter a lesson title or keyword to search");
			return;
		}

		// Retrieves every lesson created by the active contributor
		LessonsLearnedList storedLessons =
				theDatabase.getAllLessonsForContributor(theUser.getUserName());

		// Creates a collection for lessons that match the search
		LessonsLearnedList matches = new LessonsLearnedList();

		// Checks each stored lesson for the entered title or keyword
		for (LessonsLearned lesson : storedLessons.getLessons()) {

			// Checks the searchable lesson fields for a partial match
			if (containsKeyword(lesson.getTitle(), query)
					|| containsKeyword(lesson.getProblemDescription(), query)
					|| containsKeyword(lesson.getSolution(), query)
					|| containsKeyword(lesson.getWhatWasDone(), query)
					|| containsKeyword(lesson.getHowItWasDone(), query)
					|| containsKeyword(lesson.getTeamEffort(), query)) {

				// Adds the matching lesson to the result collection
				matches.addLesson(lesson);
			}
		}

		// Checks whether the search found any lessons
		if (matches.isEmpty()) {
			textarea_Display.setText(
					"No lessons matched \"" + textfield_FilterCategory.getText().trim() + "\"");
		} else {
			// Displays the matching lessons
			displaySearchResults(matches);
		}
	}

	/******
	 * 
	 * <p> Method: containsKeyword() </p>
	 * 
	 * <p> Description: Performs a null-safe and case-insensitive partial keyword
	 * comparison. </p>
	 * 
	 * @param value lesson text being searched
	 * @param query normalized keyword entered by the user
	 * @return true when the lesson text contains the keyword
	 * 
	 */

	private static boolean containsKeyword(String value, String query) {

		// Returns whether the available text contains the keyword
		return value != null && value.toLowerCase().contains(query);
	}

	/******
	 * 
	 * <p> Method: configureCard() </p>
	 * 
	 * <p> Description: Builds a white card that visually groups related page
	 * controls. </p>
	 * 
	 * @param pane specifies the pane being styled as a card
	 * @param x specifies the card's horizontal position
	 * @param y specifies the card's vertical position
	 * @param cardWidth specifies the card's width
	 * @param cardHeight specifies the card's height
	 * 
	 */

	private static void configureCard(
			Pane pane,
			double x,
			double y,
			double cardWidth,
			double cardHeight) {

		// Sets the card's horizontal position
		pane.setLayoutX(x);

		// Sets the card's vertical position
		pane.setLayoutY(y);

		// Sets the preferred width and height of the card
		pane.setPrefSize(cardWidth, cardHeight);

		// Prevents the decorative card from blocking its controls
		pane.setMouseTransparent(true);

		// Applies the white background, sage border, and rounded corners
		pane.setStyle("-fx-background-color: " + SURFACE_COLOR + ";"
				+ "-fx-border-color: " + BORDER_COLOR + ";"
				+ "-fx-border-width: 1;"
				+ "-fx-border-radius: 12;"
				+ "-fx-background-radius: 12;");
	}

	/******
	 * 
	 * <p> Method: setupSectionLabel() </p>
	 * 
	 * <p> Description: Configures the compact uppercase heading displayed above a
	 * page section. </p>
	 * 
	 * @param label specifies the section-heading label
	 * @param x specifies the heading's horizontal position
	 * @param y specifies the heading's vertical position
	 * 
	 */

	private static void setupSectionLabel(
			Label label,
			double x,
			double y) {

		// Configures the heading's font, width, alignment, and position
		setupLabelUI(label, "Helvetica", 11, 220, Pos.BASELINE_LEFT, x, y);

		// Applies the dark-sage section-heading appearance
		label.setStyle("-fx-text-fill: " + ACCENT_COLOR + ";" + "-fx-font-weight: bold;");
	}

	/******
	 * 
	 * <p> Method: styleTextField() </p>
	 * 
	 * <p> Description: Applies the rounded sage styling used by editable text
	 * fields. </p>
	 * 
	 * @param field specifies the text field being styled
	 * 
	 */

	private static void styleTextField(TextField field) {

		// Applies the white surface, sage border, spacing, and text colors
		field.setStyle("-fx-background-color: " + SURFACE_COLOR + ";"
				+ "-fx-border-color: " + BORDER_COLOR + ";"
				+ "-fx-border-width: 1;"
				+ "-fx-border-radius: 8;"
				+ "-fx-background-radius: 8;"
				+ "-fx-padding: 8 10 8 10;"
				+ "-fx-text-fill: " + PRIMARY_TEXT + ";"
				+ "-fx-prompt-text-fill: " + SECONDARY_TEXT + ";"
				+ "-fx-focus-color: transparent;"
				+ "-fx-faint-focus-color: transparent;");
	}

	/******
	 * 
	 * <p> Method: styleTextArea() </p>
	 * 
	 * <p> Description: Styles the read-only results panel as a rounded white
	 * card. </p>
	 * 
	 * @param area specifies the text area being styled
	 * 
	 */

	private static void styleTextArea(TextArea area) {

		// Applies the white surface, sage border, spacing, and text colors
		area.setStyle("-fx-control-inner-background: "
				+ SURFACE_COLOR + ";"
				+ "-fx-background-color: " + SURFACE_COLOR + ";"
				+ "-fx-border-color: " + BORDER_COLOR + ";"
				+ "-fx-border-width: 1;"
				+ "-fx-border-radius: 10;"
				+ "-fx-background-radius: 10;"
				+ "-fx-padding: 8;"
				+ "-fx-text-fill: " + PRIMARY_TEXT + ";"
				+ "-fx-focus-color: transparent;"
				+ "-fx-faint-focus-color: transparent;");
	}

	/******
	 * 
	 * <p> Method: stylePrimaryButton() </p>
	 * 
	 * <p> Description: Styles a primary action using dark sage and a darker hover
	 * state. </p>
	 * 
	 * @param button specifies the primary button being styled
	 * 
	 */

	private static void stylePrimaryButton(Button button) {

		// Defines the primary button's normal appearance
		String base = "-fx-background-color: " + ACCENT_COLOR + ";"
				+ "-fx-text-fill: white;"
				+ "-fx-background-radius: 8;"
				+ "-fx-border-radius: 8;"
				+ "-fx-background-insets: 0;"
				+ "-fx-padding: 8 14 8 14;"
				+ "-fx-cursor: hand;"
				+ "-fx-focus-color: transparent;"
				+ "-fx-faint-focus-color: transparent;";

		// Defines the primary button's darker hover appearance
		String hover = "-fx-background-color: " + ACCENT_HOVER + ";"
				+ "-fx-text-fill: white;"
				+ "-fx-background-radius: 8;"
				+ "-fx-border-radius: 8;"
				+ "-fx-background-insets: 0;"
				+ "-fx-padding: 8 14 8 14;"
				+ "-fx-cursor: hand;"
				+ "-fx-focus-color: transparent;"
				+ "-fx-faint-focus-color: transparent;";

		// Applies the normal button appearance
		button.setStyle(base);

		// Applies the darker appearance when the pointer enters the button
		button.setOnMouseEntered((_) -> button.setStyle(hover));

		// Restores the normal appearance when the pointer leaves the button
		button.setOnMouseExited((_) -> button.setStyle(base));
	}

	/******
	 * 
	 * <p> Method: styleSecondaryButton() </p>
	 * 
	 * <p> Description: Styles a secondary action with a subtle border and sage
	 * hover state. </p>
	 * 
	 * @param button specifies the secondary button being styled
	 * 
	 */

	private static void styleSecondaryButton(Button button) {

		// Defines the secondary button's normal appearance
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

		// Defines the secondary button's soft-sage hover appearance
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

		// Applies the normal secondary-button appearance
		button.setStyle(base);

		// Applies the soft-sage appearance when the pointer enters
		button.setOnMouseEntered((_) -> button.setStyle(hover));

		// Restores the normal appearance when the pointer leaves
		button.setOnMouseExited((_) -> button.setStyle(base));
	}

	/******
	 * 
	 * <p> Method: showPopupBelow() </p>
	 * 
	 * <p> Description: Positions and displays a custom popup directly beneath its
	 * anchor button. </p>
	 * 
	 * @param popup popup being displayed
	 * @param anchor button used to position the popup
	 * 
	 */

	private static void showPopupBelow(Popup popup, Button anchor) {

		// Checks that the popup is closed and its button belongs to a scene
		if (!popup.isShowing() && anchor.getScene() != null) {

			// Calculates the screen position directly below the button
			Point2D location = anchor.localToScreen(0, anchor.getHeight() + 6);

			// Checks that JavaFX successfully calculated the position
			if (location != null) {

				// Displays the popup beneath the button
				popup.show(
						anchor.getScene().getWindow(),
						location.getX(),
						location.getY());
			}
		}
	}

	/******
	 * 
	 * <p> Method: styleSecondaryButtonHover() </p>
	 * 
	 * <p> Description: Applies the hover appearance used by secondary menu
	 * buttons. </p>
	 * 
	 * @param button button receiving the hover appearance
	 * 
	 */

	private static void styleSecondaryButtonHover(Button button) {

		// Applies the soft sage hover style
		button.setStyle(
				"-fx-background-color: " + HOVER_BACKGROUND + ";"
				+ "-fx-text-fill: " + PRIMARY_TEXT + ";"
				+ "-fx-border-color: " + ACCENT_COLOR + ";"
				+ "-fx-border-width: 1;"
				+ "-fx-background-radius: 8;"
				+ "-fx-border-radius: 8;"
				+ "-fx-background-insets: 0;"
				+ "-fx-padding: 8 14 8 14;"
				+ "-fx-cursor: hand;"
				+ "-fx-focus-color: transparent;"
				+ "-fx-faint-focus-color: transparent;");
	}

	/******
	 * 
	 * <p> Method: styleMenuAction() </p>
	 * 
	 * <p> Description: Styles a clickable row inside a custom popup menu using
	 * either normal or destructive-action colors. </p>
	 * 
	 * @param label clickable menu row
	 * @param danger true when the action should use destructive red styling
	 * 
	 */
	
	private static void styleMenuAction(Label label, boolean danger) {

		// Selects the normal or destructive text color
		String textColor = danger ? "#B44747" : PRIMARY_TEXT;

		// Selects the normal or destructive hover color
		String hoverColor = danger ? "#F7E8E8" : HOVER_BACKGROUND;

		// Defines the menu row's normal appearance
		String base =
				"-fx-background-color: " + SURFACE_COLOR + ";"
				+ "-fx-text-fill: " + textColor + ";"
				+ "-fx-font-family: 'Helvetica';"
				+ "-fx-font-size: 13;"
				+ "-fx-padding: 9 14 9 14;"
				+ "-fx-background-radius: 6;"
				+ "-fx-cursor: hand;";

		// Defines the menu row's hover appearance
		String hover =
				"-fx-background-color: " + hoverColor + ";"
				+ "-fx-text-fill: " + textColor + ";"
				+ "-fx-font-family: 'Helvetica';"
				+ "-fx-font-size: 13;"
				+ "-fx-padding: 9 14 9 14;"
				+ "-fx-background-radius: 6;"
				+ "-fx-cursor: hand;";

		// Gives the menu row a consistent width
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
	 * <p> Description: Configures the Account Settings popup and connects its
	 * account-update, logout, and quit actions. </p>
	 * 
	 */

	private static void configureAccountMenu() {

		// Gives the username row enough room for longer usernames
		label_MenuUserName.setMinWidth(170);

		// Applies the secondary text style to the username
		label_MenuUserName.setStyle(
				"-fx-text-fill: " + SECONDARY_TEXT + ";"
				+ "-fx-font-family: 'Helvetica';"
				+ "-fx-font-size: 12;"
				+ "-fx-padding: 7 12 5 12;");

		// Styles Account Updates as a normal menu action
		styleMenuAction(label_AccountUpdates, false);

		// Styles Logout as a normal menu action
		styleMenuAction(label_Logout, false);

		// Styles Quit as a destructive menu action
		styleMenuAction(label_Quit, true);

		// Creates a divider beneath the username
		Separator accountSeparator = new Separator();

		// Creates a divider between account and session actions
		Separator sessionSeparator = new Separator();

		// Arranges the menu rows vertically
		VBox menuContent = new VBox(
				2,
				label_MenuUserName,
				accountSeparator,
				label_AccountUpdates,
				sessionSeparator,
				label_Logout,
				label_Quit);

		// Gives the menu a consistent minimum width
		menuContent.setMinWidth(185);

		// Applies the popup card style
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

		// Closes the menu when the user clicks elsewhere
		menu_AccountSettings.setAutoHide(true);

		// Allows Escape to close the menu
		menu_AccountSettings.setHideOnEscape(true);

		// Opens the existing account-update page
		label_AccountUpdates.setOnMouseClicked((_) -> {
			menu_AccountSettings.hide();
			ControllerContributorHome.performUpdate();
		});

		// Logs out the active contributor
		label_Logout.setOnMouseClicked((_) -> {
			menu_AccountSettings.hide();
			ControllerContributorHome.performLogout();
		});

		// Quits the application
		label_Quit.setOnMouseClicked((_) -> {
			menu_AccountSettings.hide();
			ControllerContributorHome.performQuit();
		});

		// Opens the menu when Account Settings is clicked
		button_UpdateThisUser.setOnAction((_) -> showAccountMenu());

		// Opens the menu and applies its hover style when the pointer enters
		button_UpdateThisUser.setOnMouseEntered((_) -> {
			styleSecondaryButtonHover(button_UpdateThisUser);
			showAccountMenu();
		});
	}

	/******
	 * 
	 * <p> Method: showAccountMenu() </p>
	 * 
	 * <p> Description: Displays the Account Settings menu beneath its button. </p>
	 * 
	 */
	
	private static void showAccountMenu() {

		// Positions and displays the account menu
		showPopupBelow(menu_AccountSettings, button_UpdateThisUser);
	}

	/******
	 * 
	 * <p> Method: configureAddMenu() </p>
	 * 
	 * <p> Description: Configures the Add popup and connects its lesson-experience
	 * action. </p>
	 * 
	 */

	private static void configureAddMenu() {

		// Styles Experience as a normal menu action
		styleMenuAction(label_AddExperience, false);

		// Places Experience inside the popup
		VBox menuContent = new VBox(label_AddExperience);

		// Gives the Add menu a consistent minimum width
		menuContent.setMinWidth(165);

		// Applies the popup card style
		menuContent.setStyle(
				"-fx-background-color: " + SURFACE_COLOR + ";"
				+ "-fx-border-color: " + BORDER_COLOR + ";"
				+ "-fx-border-width: 1;"
				+ "-fx-background-radius: 8;"
				+ "-fx-border-radius: 8;"
				+ "-fx-padding: 5;"
				+ "-fx-effect: dropshadow(gaussian, rgba(38, 51, 45, 0.18), 12, 0.15, 0, 4);");

		// Adds the popup content only once
		if (menu_Add.getContent().isEmpty()) {
			menu_Add.getContent().add(menuContent);
		}

		// Closes the menu when the user clicks elsewhere
		menu_Add.setAutoHide(true);

		// Allows Escape to close the menu
		menu_Add.setHideOnEscape(true);

		// Opens the existing Experience form
		label_AddExperience.setOnMouseClicked((_) -> {
			menu_Add.hide();
			ControllerContributorHome.handleOpenExperienceWindow();
		});

		// Opens the menu when Add is clicked
		button_Add.setOnAction((_) -> showAddMenu());

		// Opens the menu and applies its hover style when the pointer enters
		button_Add.setOnMouseEntered((_) -> {
			styleSecondaryButtonHover(button_Add);
			showAddMenu();
		});
	}

	/******
	 * 
	 * <p> Method: showAddMenu() </p>
	 * 
	 * <p> Description: Displays the Add menu beneath its button. </p>
	 * 
	 */

	private static void showAddMenu() {

		// Positions and displays the Add menu
		showPopupBelow(menu_Add, button_Add);
	}

	/******
	 * 
	 * <p> Method: selectLesson() </p>
	 * 
	 * <p> Description: Places the selected lesson's identifying information into
	 * the existing lesson fields for controller compatibility. </p>
	 * 
	 * @param lesson lesson selected by the contributor
	 * 
	 */

	private static void selectLesson(LessonsLearned lesson) {

		// Places the selected lesson ID into the Lesson ID field
		textfield_LessonId.setText(String.valueOf(lesson.getLessonId()));

		// Places the selected lesson title into the retained Title field
		textfield_Title.setText(lesson.getTitle());

		// Places the selected lesson category into the Category field
		textfield_Category.setText(lesson.getCategory());
	}

	/******
	 * 
	 * <p> Method: styleDeleteButton() </p>
	 * 
	 * <p> Description: Applies red destructive-action styling and hover behavior
	 * to a button. </p>
	 * 
	 * @param button destructive action button being styled
	 * 
	 */

	private static void styleDeleteButton(Button button) {

		// Defines the normal destructive-action appearance
		String base =
				"-fx-background-color: " + SURFACE_COLOR + ";"
				+ "-fx-text-fill: #B44747;"
				+ "-fx-border-color: #E3BABA;"
				+ "-fx-border-width: 1;"
				+ "-fx-border-radius: 8;"
				+ "-fx-background-radius: 8;"
				+ "-fx-padding: 8 14 8 14;"
				+ "-fx-cursor: hand;";

		// Defines the destructive-action hover appearance
		String hover =
				"-fx-background-color: #F7E8E8;"
				+ "-fx-text-fill: #9B3A3A;"
				+ "-fx-border-color: #C96A6A;"
				+ "-fx-border-width: 1;"
				+ "-fx-border-radius: 8;"
				+ "-fx-background-radius: 8;"
				+ "-fx-padding: 8 14 8 14;"
				+ "-fx-cursor: hand;";

		// Applies the normal destructive-action appearance
		button.setStyle(base);

		// Applies the hover appearance when the pointer enters
		button.setOnMouseEntered((_) -> button.setStyle(hover));

		// Restores the normal appearance when the pointer exits
		button.setOnMouseExited((_) -> button.setStyle(base));
	}

	/******
	 * 
	 * <p> Method: showLessonDetails() </p>
	 * 
	 * <p> Description: Opens the selected lesson in a smaller details window and
	 * provides Update, Delete, and Close actions. </p>
	 * 
	 * @param lesson lesson whose information will be displayed
	 * 
	 */

	protected static void showLessonDetails(LessonsLearned lesson) {

		// Creates the Lesson Details window
		Stage detailStage = new Stage();

		// Keeps the detail window associated with the contributor page
		detailStage.initModality(Modality.WINDOW_MODAL);

		// Sets the contributor page as the owner
		detailStage.initOwner(theStage);

		// Sets the window title
		detailStage.setTitle("Lesson Details");

		// Creates the lesson-title heading
		Label heading = new Label(lesson.getTitle());

		// Allows a long lesson title to wrap
		heading.setWrapText(true);

		// Allows the heading to use the available header width
		heading.setMaxWidth(Double.MAX_VALUE);

		// Applies the heading style
		heading.setStyle(
				"-fx-font-family: 'Helvetica';"
				+ "-fx-font-size: 22;"
				+ "-fx-font-weight: bold;"
				+ "-fx-text-fill: " + PRIMARY_TEXT + ";");

		// Creates the lesson-information section
		VBox details = new VBox(
				11,
				createDetailLabel("Lesson ID", String.valueOf(lesson.getLessonId())),
				createDetailLabel("Category", lesson.getCategory()),
				createDetailLabel("Author", lesson.getAuthor()),
				createDetailLabel("Problem Description", lesson.getProblemDescription()),
				createDetailLabel("Solution", lesson.getSolution()),
				createDetailLabel("Time Spent", lesson.getTimeSpentHours() + " hours"),
				createDetailLabel("What Was Done", lesson.getWhatWasDone()),
				createDetailLabel("How It Was Done", lesson.getHowItWasDone()),
				createDetailLabel("Team Effort", lesson.getTeamEffort()));

		// Creates the Close button
		Button close = new Button("Close");

		// Applies the primary sage button style
		stylePrimaryButton(close);

		// Allows Enter to activate Close
		close.setDefaultButton(true);

		// Closes the detail window
		close.setOnAction((_) -> detailStage.close());

		// Creates the Update button
		Button update = new Button("Update");

		// Applies the secondary button style
		styleSecondaryButton(update);

		// Opens the existing Update form for the selected lesson
		update.setOnAction((_) -> {
			selectLesson(lesson);
			detailStage.close();
			ControllerContributorHome.handleOpenUpdateWindow();
		});

		// Creates the Delete button
		Button delete = new Button("Delete");

		// Applies the destructive-action style
		styleDeleteButton(delete);

		// Runs the existing Delete action for the selected lesson
		delete.setOnAction((_) -> {
			selectLesson(lesson);
			detailStage.close();
			ControllerContributorHome.handleDeleteLesson();
		});

		// Allows the heading to occupy unused horizontal space
		HBox.setHgrow(heading, Priority.ALWAYS);

		// Places Update and Delete in the upper-right corner
		HBox header = new HBox(10, heading, update, delete);

		// Aligns the header actions
		header.setAlignment(Pos.TOP_RIGHT);

		// Places Close in a separate lower-right action row
		HBox closeAction = new HBox(close);

		// Aligns Close to the right
		closeAction.setAlignment(Pos.CENTER_RIGHT);

		// Arranges the header, details, and Close action vertically
		VBox content = new VBox(16, header, details, closeAction);

		// Adds space around the popup content
		content.setPadding(new Insets(24));

		// Applies the page background color
		content.setStyle("-fx-background-color: " + PAGE_BACKGROUND + ";");

		// Makes long lesson details scrollable
		ScrollPane scroll = new ScrollPane(content);

		// Makes the content fill the popup width
		scroll.setFitToWidth(true);

		// Applies the sage background to the scrollable area
		scroll.setStyle(
				"-fx-background: " + PAGE_BACKGROUND + ";"
				+ "-fx-background-color: " + PAGE_BACKGROUND + ";");

		// Adds the popup content to its scene
		detailStage.setScene(new Scene(scroll, 570, 500));

		// Keeps the popup at its intended size
		detailStage.setResizable(false);

		// Displays the popup and waits for it to close
		detailStage.showAndWait();
	}

	/******
	 * 
	 * <p> Method: createDetailLabel() </p>
	 * 
	 * <p> Description: Creates one labeled and wrapped information block for the
	 * Lesson Details window. </p>
	 * 
	 * @param name field name displayed above the value
	 * @param value lesson information being displayed
	 * @return vertically arranged field name and value
	 * 
	 */
	
	private static VBox createDetailLabel(String name, String value) {

		// Creates the uppercase field name
		Label nameLabel = new Label(name.toUpperCase());

		// Applies the accent style to the field name
		nameLabel.setStyle(
				"-fx-font-family: 'Helvetica';"
				+ "-fx-font-size: 10;"
				+ "-fx-font-weight: bold;"
				+ "-fx-text-fill: " + ACCENT_COLOR + ";");

		// Displays a fallback when the lesson field is empty
		Label valueLabel = new Label(
				value == null || value.isBlank() ? "Not provided" : value);

		// Allows long lesson information to wrap
		valueLabel.setWrapText(true);

		// Limits the value to the popup content width
		valueLabel.setMaxWidth(500);

		// Applies the primary text style to the value
		valueLabel.setStyle(
				"-fx-font-family: 'Helvetica';"
				+ "-fx-font-size: 13;"
				+ "-fx-text-fill: " + PRIMARY_TEXT + ";");

		// Returns the completed information block
		return new VBox(3, nameLabel, valueLabel);
	}

	/******
	 * 
	 * <p> Method: styleComboBox() </p>
	 * 
	 * <p> Description: Applies the updated UI style to a dropdown control. </p>
	 * 
	 * @param comboBox dropdown being styled
	 * 
	 */

	private static void styleComboBox(ComboBox<String> comboBox) {

		// Applies the smooth white field and sage border style
		comboBox.setStyle(
				"-fx-background-color: " + SURFACE_COLOR + ";"
				+ "-fx-border-color: " + BORDER_COLOR + ";"
				+ "-fx-border-width: 1;"
				+ "-fx-border-radius: 7;"
				+ "-fx-background-radius: 7;"
				+ "-fx-font-family: 'Helvetica';"
				+ "-fx-font-size: 12;");
	}

	/******
	 * 
	 * <p> Method: stylePopupLabel() </p>
	 * 
	 * <p> Description: Applies the updated UI style to a label displayed inside a
	 * popup menu. </p>
	 * 
	 * @param label popup label being styled
	 * 
	 */

	private static void stylePopupLabel(Label label) {

		// Applies the secondary heading style
		label.setStyle(
				"-fx-text-fill: " + SECONDARY_TEXT + ";"
				+ "-fx-font-family: 'Helvetica';"
				+ "-fx-font-size: 11;"
				+ "-fx-font-weight: bold;");
	}

	/******
	 * 
	 * <p> Method: configureFilterSortMenu() </p>
	 * 
	 * <p> Description: Configures the Filter and Sort popup controls, styling,
	 * choices, and action handlers. </p>
	 * 
	 */

	private static void configureFilterSortMenu() {

		// Sets the category dropdown width
		combo_CategoryFilter.setPrefWidth(210);

		// Displays the default category instruction
		combo_CategoryFilter.setPromptText("All Categories");

		// Sets the sorting dropdown width
		combo_SortOrder.setPrefWidth(210);

		// Adds the available sorting choices
		combo_SortOrder.getItems().setAll(
				"Newest first",
				"Oldest first",
				"Title A–Z",
				"Title Z–A",
				"Category A–Z");

		// Selects the default sorting order
		combo_SortOrder.setValue("Newest first");

		// Styles the category dropdown
		styleComboBox(combo_CategoryFilter);

		// Styles the sorting dropdown
		styleComboBox(combo_SortOrder);

		// Creates the category heading
		Label categoryLabel = new Label("Filter by category");

		// Creates the sorting heading
		Label sortLabel = new Label("Sort lessons");

		// Styles the category heading
		stylePopupLabel(categoryLabel);

		// Styles the sorting heading
		stylePopupLabel(sortLabel);

		// Styles the Apply button
		stylePrimaryButton(button_ApplyFilterSort);

		// Styles the Clear button
		styleSecondaryButton(button_ClearFilterSort);

		// Arranges the popup action buttons horizontally
		HBox actions = new HBox(
				8,
				button_ClearFilterSort,
				button_ApplyFilterSort);

		// Aligns the popup actions to the right
		actions.setAlignment(Pos.CENTER_RIGHT);

		// Arranges the dropdowns and actions vertically
		VBox content = new VBox(
				7,
				categoryLabel,
				combo_CategoryFilter,
				sortLabel,
				combo_SortOrder,
				actions);

		// Applies the popup content background and spacing
		content.setStyle(
				"-fx-padding: 10;"
				+ "-fx-background-color: " + SURFACE_COLOR + ";");

		// Wraps the custom controls inside the context menu
		CustomMenuItem contentItem = new CustomMenuItem(content, false);

		// Places the custom content into the menu
		menu_FilterSort.getItems().setAll(contentItem);

		// Applies the popup card style
		menu_FilterSort.setStyle(
				"-fx-background-color: " + SURFACE_COLOR + ";"
				+ "-fx-border-color: " + BORDER_COLOR + ";"
				+ "-fx-border-width: 1;"
				+ "-fx-background-radius: 8;"
				+ "-fx-border-radius: 8;"
				+ "-fx-padding: 0;");

		// Opens the Filter and Sort popup
		button_FilterSort.setOnAction((_) -> showFilterSortMenu());

		// Applies the selected filtering and sorting choices
		button_ApplyFilterSort.setOnAction((_) -> applyFilterAndSort());

		// Restores the default filtering and sorting choices
		button_ClearFilterSort.setOnAction((_) -> {
			combo_CategoryFilter.setValue("All Categories");
			combo_SortOrder.setValue("Newest first");
			applyFilterAndSort();
		});

	}

	/******
	 * 
	 * <p> Method: showFilterSortMenu() </p>
	 * 
	 * <p> Description: Loads the contributor's available lesson categories and
	 * displays the Filter and Sort popup. </p>
	 * 
	 */

	private static void showFilterSortMenu() {

		// Remembers the currently selected category
		String currentCategory = combo_CategoryFilter.getValue();

		// Retrieves every lesson created by the active contributor
		LessonsLearnedList lessons = theDatabase.getAllLessonsForContributor(theUser.getUserName());

		// Creates an alphabetically ordered collection of unique categories
		Set<String> categories = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);

		// Checks every lesson for an available category
		for (LessonsLearned lesson : lessons.getLessons()) {

			// Adds each nonempty category once
			if (lesson.getCategory() != null
					&& !lesson.getCategory().isBlank()) {
				categories.add(lesson.getCategory());
			}
		}

		// Restores the default category option
		combo_CategoryFilter.getItems().setAll("All Categories");

		// Adds the contributor's available lesson categories
		combo_CategoryFilter.getItems().addAll(categories);

		// Restores the previous selection when it is still available
		combo_CategoryFilter.setValue(
				currentCategory != null
						&& combo_CategoryFilter.getItems().contains(currentCategory)
						? currentCategory
						: "All Categories");

		// Opens the popup beneath the Filter and Sort button
		if (!menu_FilterSort.isShowing()) {
			menu_FilterSort.show(
					button_FilterSort,
					Side.BOTTOM,
					0,
					5);
		}
	}

	/******
	 * 
	 * <p> Method: applyFilterAndSort() </p>
	 * 
	 * <p> Description: Applies the selected category filter and sorting order to
	 * the contributor's lessons and displays the resulting lesson cards. </p>
	 * 
	 */
	
	private static void applyFilterAndSort() {

		// Retrieves every lesson created by the active contributor
		LessonsLearnedList storedLessons = theDatabase.getAllLessonsForContributor(theUser.getUserName());

		// Creates a modifiable list for filtering and sorting
		List<LessonsLearned> lessons = new ArrayList<>(storedLessons.getLessons());

		// Reads the selected category
		String category = combo_CategoryFilter.getValue();

		// Removes lessons that do not match the selected category
		if (category != null && !category.equals("All Categories")) {
			lessons.removeIf((lesson) -> !category.equalsIgnoreCase(lesson.getCategory()));
		}

		// Reads the selected sorting order
		String order = combo_SortOrder.getValue();

		// Stores the sorting rule that will be applied
		Comparator<LessonsLearned> comparator;

		// Sorts lower lesson IDs before higher IDs
		if ("Oldest first".equals(order)) {
			comparator = Comparator.comparingInt(LessonsLearned::getLessonId);

		// Sorts lesson titles alphabetically
		} else if ("Title A–Z".equals(order)) {
			comparator =
					Comparator.comparing(
							LessonsLearned::getTitle,
							String.CASE_INSENSITIVE_ORDER);

		// Sorts lesson titles in reverse alphabetical order
		} else if ("Title Z–A".equals(order)) {
			comparator =
					Comparator.comparing(
							LessonsLearned::getTitle,
							String.CASE_INSENSITIVE_ORDER)
					.reversed();

		// Sorts categories alphabetically and then sorts matching titles
		} else if ("Category A–Z".equals(order)) {
			comparator =
					Comparator.comparing(
							LessonsLearned::getCategory,
							String.CASE_INSENSITIVE_ORDER)
					.thenComparing(
							LessonsLearned::getTitle,
							String.CASE_INSENSITIVE_ORDER);

		// Sorts higher lesson IDs before lower IDs
		} else {
			comparator =
					Comparator.comparingInt(LessonsLearned::getLessonId)
					.reversed();
		}

		// Applies the selected sorting rule
		lessons.sort(comparator);

		// Converts the filtered list into the project's lesson collection
		LessonsLearnedList results =
				new LessonsLearnedList(lessons);

		// Checks whether the selected filter returned any lessons
		if (results.isEmpty()) {

			// Displays the text-results area
			showTextResults();

			// Keeps the default section heading
			label_ResultsSection.setText("MY LESSONS");

			// Keeps Filter and Sort available
			button_FilterSort.setVisible(true);

			// Hides Back because this remains the default My Lessons view
			button_BackToLessons.setVisible(false);

			// Explains that the selected filter returned no lessons
			textarea_Display.setText(
					"No lessons matched the selected filter");
		} else {
			// Displays the filtered and sorted lesson cards
			displayMyLessons(results);
		}

		// Closes the Filter and Sort popup
		menu_FilterSort.hide();

	}


}
