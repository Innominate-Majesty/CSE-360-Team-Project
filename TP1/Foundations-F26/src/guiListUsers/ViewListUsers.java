package guiListUsers;

import java.util.List;

import entityClasses.User;
import entityClasses.UserAccountSummary;
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

/******
 * 
 * <p> Title: ViewListUsers Class </p>
 * 
 * <p> Description: JavaFX based View User Accounts page. This class displays a scrollable list of user account cards and the permitted information for a selected account </p>
 * 
 */

public class ViewListUsers {

    // Stores the application window width
	private static double width = applicationMain.FoundationsMain.WINDOW_WIDTH;

	// Stores the application window height
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

	// Displays the title of the page
	private static Label label_PageTitle = new Label("All User Accounts");

	// Displays the username of the currently logged in admin user
	private static Label label_AdminDetails = new Label();

	// Displays the empty list and database error messages
	protected static Label label_StatusMessage = new Label();

	// Holds the user account cards vertically
	protected static VBox accountListContainer = new VBox();

	// Allows the admin to scroll through the account cards
	protected static ScrollPane accountScrollPane = new ScrollPane();

	// Holds the permitted details for the selected account
	protected static VBox accountDetailsContainer = new VBox();

	// Refreshes the account information from the database
	protected static Button button_Refresh = new Button("Refresh");

	// Returns from account details to the account-card list
	protected static Button button_BackToList = new Button("Back to User List");

	// Returns to the Admin Home page
	protected static Button button_Return = new Button("Return to Admin Home");

	// Logs the current admin out
	protected static Button button_Logout = new Button("Logout");

	// Closes the application
	protected static Button button_Quit = new Button("Quit");

	// Stores the single view instance used by this page
	private static ViewListUsers theView;

	// Stores the JavaFX application window
	protected static Stage theStage;

	// Stores the main container holding this page's controls
	protected static Pane theRootPane;

	// Stores the current admin
	protected static User theUser;

	// Stores the scene displayed for the View User Accounts page
	protected static Scene theListUsersScene;

	// Stores the account summaries currently displayed by the view
	protected static List<UserAccountSummary> accountSummaries;

    /*******
	 *
	 * <p> Method: displayListUsers(Stage ps, User user) </p>
	 *
	 * <p> Description: Displays the View User Accounts page for user with admin role </p>
	 *
	 * @param ps specifies the JavaFX Stage used by the application
	 *
	 * @param user specifies the currently authenticated administrator
	 *
	 */

	public static void displayListUsers(Stage ps, User user) {

		// Stores the application window used to display this page
		theStage = ps;

		// Stores the currently logged in admin
		theUser = user;

		// Creates the page once if it has not already been constructed
		if (theView == null) {

			// Constructs the single View User Accounts page
			theView = new ViewListUsers();
		}

		// Displays the admin's username
		label_AdminDetails.setText("Admin: " + theUser.getUserName());

        // Loads the latest user account summaries whenever the page opens
        ControllerListUsers.loadUserAccounts();

		// Sets the application window title for this page
		theStage.setTitle("View All User Accounts");

		// Places the View User Accounts scene into the application window
		theStage.setScene(theListUsersScene);

		// Displays the application window
		theStage.show();
	}

    /*******
	 *
	 * <p> Method: ViewListUsers() </p>
	 *
	 * <p> Description: Constructs and styles the View User Accounts page </p>
	 *
	 */

	private ViewListUsers() {

		// Creates the main container for the page
		theRootPane = new Pane();

		// Applies the soft blue gray background to the page
		theRootPane.setStyle("-fx-background-color: " + PAGE_BACKGROUND + ";");

		// Creates the scene using the application's standard dimensions
		theListUsersScene = new Scene(theRootPane, width, height);

		// Applies the page title font
		label_PageTitle.setFont(Font.font("Arial", 28));

		// Applies the primary text color to the page title
		label_PageTitle.setStyle("-fx-text-fill: " + PRIMARY_TEXT + "; -fx-font-weight: bold;");

		// Gives the page title the full window width
		label_PageTitle.setMinWidth(width);

		// Centers the page title
		label_PageTitle.setAlignment(Pos.CENTER);

		// Places the page title near the top of the window
		label_PageTitle.setLayoutY(15);

		// Applies the admin label font
		label_AdminDetails.setFont(Font.font("Arial", 16));

		// Applies the secondary text color to the admin label
		label_AdminDetails.setStyle("-fx-text-fill: " + SECONDARY_TEXT + ";");

		// Places the admin label below the page title
		label_AdminDetails.setLayoutX(30);

		// Places the admin label vertically below the title
		label_AdminDetails.setLayoutY(65);

		// Adds spacing between account cards
		accountListContainer.setSpacing(12);

		// Adds padding around the account-card list
		accountListContainer.setPadding(new Insets(12));

		// Makes the account card container fill the scrollable width
		accountListContainer.setFillWidth(true);

		// Places the account card container inside the scrolling area
		accountScrollPane.setContent(accountListContainer);

		// Makes account cards use the available scrolling width
		accountScrollPane.setFitToWidth(true);

		// Prevents unnecessary horizontal scrolling
		accountScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

		// Displays the vertical scrollbar only when it is needed
		accountScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

		// Gives the scrollable account list a transparent outer background
		accountScrollPane.setStyle(
				"-fx-background: transparent;" +
				"-fx-background-color: transparent;");

		// Places the account list beneath the page header
		accountScrollPane.setLayoutX(20);

		// Places the account list vertically beneath the page header
		accountScrollPane.setLayoutY(100);

		// Sets the width of the scrollable account list
		accountScrollPane.setPrefWidth(width - 40);

		// Sets the height of the scrollable account list
		accountScrollPane.setPrefHeight(410);

		// Adds spacing between selected account detail rows
		accountDetailsContainer.setSpacing(12);

		// Adds padding inside the selected account details panel
		accountDetailsContainer.setPadding(new Insets(20));

		// Applies the card appearance to the details panel
		accountDetailsContainer.setStyle(
				"-fx-background-color: " + CARD_BACKGROUND + ";" +
				"-fx-border-color: " + BORDER_COLOR + ";" +
				"-fx-border-radius: 10;" +
				"-fx-background-radius: 10;");

		// Places the account details panel beneath the page header
		accountDetailsContainer.setLayoutX(20);

		// Places the account details panel vertically beneath the header
		accountDetailsContainer.setLayoutY(100);

		// Sets the width of the account details panel
		accountDetailsContainer.setPrefWidth(width - 40);

		// Sets the height of the account details panel
		accountDetailsContainer.setPrefHeight(410);

		// Hides the account details until an account is selected
		accountDetailsContainer.setVisible(false);

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

		// Hides the back button until account details are displayed
		button_BackToList.setVisible(false);

		// Adds the page controls to the main container

        // Styles and positions the Refresh button
        setupPrimaryButton(button_Refresh, 120, 20);

        // Styles and positions the Back to User List button
        setupPrimaryButton(button_BackToList, 160, 20);

        // Styles and positions the Return to Admin Home button
        setupSecondaryButton(button_Return, 230, 200);

        // Styles and positions the Logout button
        setupSecondaryButton(button_Logout, 120, 500);

        // Styles and positions the Quit button
        setupSecondaryButton(button_Quit, 120, 650);

		theRootPane.getChildren().addAll(
				label_PageTitle,
				label_AdminDetails,
				accountScrollPane,
				accountDetailsContainer,
				label_StatusMessage,
				button_Refresh,
				button_BackToList,
				button_Return,
				button_Logout,
				button_Quit);
        
        // Reloads the account summaries when the admin clicks Refresh
        button_Refresh.setOnAction((_) -> ControllerListUsers.performRefresh());

        // Returns to the account card list when the admin clicks Back
        button_BackToList.setOnAction((_) -> ControllerListUsers.performBackToList());

        // Returns to Admin Home when the admin clicks Return
        button_Return.setOnAction((_) -> ControllerListUsers.performReturn());

        // Logs the admin out when the admin clicks Logout
        button_Logout.setOnAction((_) -> ControllerListUsers.performLogout());

        // Terminates the application when the admin clicks Quit
        button_Quit.setOnAction((_) -> ControllerListUsers.performQuit());

	}

    /*******
	 *
	 * <p> Method: setupPrimaryButton(Button button, double buttonWidth,
	 * double xPosition) </p>
	 *
	 * <p> Description: Applies the primary blue appearance and position
	 * to an important page button </p>
	 *
	 * @param button specifies the button being configured
	 *
	 * @param buttonWidth specifies the width of the button
	 *
	 * @param xPosition specifies the horizontal position of the button
	 *
	 */

	private static void setupPrimaryButton(Button button, double buttonWidth, double xPosition) {

		// Applies the standard button font
		button.setFont(Font.font("Arial", 15));

		// Sets the button width
		button.setPrefWidth(buttonWidth);

		// Centers the button text
		button.setAlignment(Pos.CENTER);

		// Places the button horizontally
		button.setLayoutX(xPosition);

		// Places the button near the bottom of the page
		button.setLayoutY(540);

		// Applies the default primary button appearance
		button.setStyle(
				"-fx-background-color: " + ACCENT_COLOR + ";" +
			    "-fx-text-fill: white;" +
				"-fx-background-radius: 8;" +
				"-fx-padding: 8 16 8 16;" +
				"-fx-font-weight: bold;" +
				"-fx-cursor: hand;");

		// Applies the darker blue appearance while the pointer is over the button
		button.setOnMouseEntered((_) -> button.setStyle(
				"-fx-background-color: " + ACCENT_HOVER + ";" +
				"-fx-text-fill: white;" +
				"-fx-background-radius: 8;" +
				"-fx-padding: 8 16 8 16;" +
				"-fx-font-weight: bold;" +
				"-fx-cursor: hand;"));

		// Restores the primary appearance when the pointer leaves the button
		button.setOnMouseExited((_) -> button.setStyle(
				"-fx-background-color: " + ACCENT_COLOR + ";" +
				"-fx-text-fill: white;" +
				"-fx-background-radius: 8;" +
				"-fx-padding: 8 16 8 16;" +
				"-fx-font-weight: bold;" +
				"-fx-cursor: hand;"));
	}

	/*******
	 *
	 * <p> Method: setupSecondaryButton(Button button, double buttonWidth,
	 * double xPosition) </p>
	 *
	 * <p> Description: Applies the white-and-blue appearance and position
	 * to a secondary page button </p>
	 *
	 * @param button specifies the button being configured
	 *
	 * @param buttonWidth specifies the width of the button
	 *
	 * @param xPosition specifies the horizontal position of the button
	 *
	 */

	private static void setupSecondaryButton(Button button, double buttonWidth, double xPosition) {

		// Applies the standard button font
		button.setFont(Font.font("Arial", 15));

		// Sets the button width
		button.setPrefWidth(buttonWidth);

		// Centers the button text
		button.setAlignment(Pos.CENTER);

		// Places the button horizontally
		button.setLayoutX(xPosition);

		// Places the button near the bottom of the page
		button.setLayoutY(540);

		// Applies the default secondary-button appearance
		button.setStyle(
				"-fx-background-color: " + CARD_BACKGROUND + ";" +
				"-fx-text-fill: " + PRIMARY_TEXT + ";" +
				"-fx-border-color: " + BORDER_COLOR + ";" +
				"-fx-border-radius: 8;" +
				"-fx-background-radius: 8;" +
				"-fx-padding: 8 16 8 16;" +
				"-fx-cursor: hand;");

		// Applies the pale-blue appearance while the pointer is over the button
		button.setOnMouseEntered((_) -> button.setStyle(
				"-fx-background-color: " + HOVER_BACKGROUND + ";" +
				"-fx-text-fill: " + PRIMARY_TEXT + ";" +
				"-fx-border-color: " + ACCENT_COLOR + ";" +
				"-fx-border-radius: 8;" +
				"-fx-background-radius: 8;" +
				"-fx-padding: 8 16 8 16;" +
				"-fx-cursor: hand;"));

		// Restores the secondary appearance when the pointer leaves the button
		button.setOnMouseExited((_) -> button.setStyle(
				"-fx-background-color: " + CARD_BACKGROUND + ";" +
				"-fx-text-fill: " + PRIMARY_TEXT + ";" +
				"-fx-border-color: " + BORDER_COLOR + ";" +
				"-fx-border-radius: 8;" +
				"-fx-background-radius: 8;" +
				"-fx-padding: 8 16 8 16;" +
				"-fx-cursor: hand;"));
	}

    /*******
	 *
	 * <p> Method: displayAccountSummaries(
	 * List&lt;UserAccountSummary&gt; summaries) </p>
	 *
	 * <p> Description: Displays one account card for each user account
	 * returned by the database </p>
	 *
	 * @param summaries specifies the password-free accounts to display
	 *
	 */

	protected static void displayAccountSummaries(List<UserAccountSummary> summaries) {

		// Stores the account summaries currently displayed by the page
		accountSummaries = summaries;

		// Removes account cards left over from an earlier display or refresh
		accountListContainer.getChildren().clear();

		// Hides the account-details panel while displaying the account list
		accountDetailsContainer.setVisible(false);

		// Hides the Back to User List button while displaying the account list
		button_BackToList.setVisible(false);

		// Displays the Refresh button while displaying the account list
		button_Refresh.setVisible(true);

		// Checks whether the provided account list is unavailable
		if (summaries == null) {

			// Hides the scrollable account list
			accountScrollPane.setVisible(false);

			// Displays an understandable database error message
			label_StatusMessage.setText("User accounts could not be loaded. Please try again.");

			// Makes the database-error message visible
			label_StatusMessage.setVisible(true);

			// Stops before trying to process an unavailable list
			return;

		}

		// Checks whether the database contains no user accounts
		if (summaries.isEmpty()) {

			// Hides the empty scrollable account list
			accountScrollPane.setVisible(false);

			// Displays an understandable empty-state message
			label_StatusMessage.setText("No user accounts were found.");

			// Makes the empty-state message visible
			label_StatusMessage.setVisible(true);

			// Stops because there are no account cards to construct
			return;

		}

		// Hides any earlier empty state or database error message(s)
		label_StatusMessage.setVisible(false);

		// Displays the scrollable account list
		accountScrollPane.setVisible(true);

		// Processes every account summary (without passwords showing)
		for (UserAccountSummary account : summaries) {

			// Creates and adds a card for the current account
			accountListContainer.getChildren().add(
					createAccountCard(account));

		}
	}

	/*******
	 *
	 * <p> Method: createAccountCard(UserAccountSummary account) </p>
	 *
	 * <p> Description: Constructs a modern account card containing the
	 * user's display name and username </p>
	 *
	 * @param account specifies the password-free account displayed by the card
	 *
	 * @return the constructed account card
	 *
	 */

	private static Button createAccountCard(UserAccountSummary account) {

		// Creates the primary name displayed on the account card
		Label nameLabel = new Label(account.getDisplayName());

		// Applies the primary account name appearance
		nameLabel.setStyle(
				"-fx-text-fill: " + PRIMARY_TEXT + ";" +
			    "-fx-font-size: 17px;" +
				"-fx-font-weight: bold;");

		// Retrieves the username displayed beneath the account name
		String displayedUsername = account.getUserName();

		// Checks whether the account has a usable username
		if (displayedUsername == null || displayedUsername.isBlank()) {

			// Provides readable text instead of displaying null
			displayedUsername = "Username unavailable";

		}

		// Creates the smaller username displayed on the account card
		Label usernameLabel = new Label(displayedUsername);

		// Applies the secondary username appearance
		usernameLabel.setStyle(
				"-fx-text-fill: " + SECONDARY_TEXT + ";" + "-fx-font-size: 13px;");

		// Creates the vertical text layout used inside the card
		VBox cardText = new VBox();

		// Adds a small amount of space between the name and username
		cardText.setSpacing(4);

		// Aligns the card text to the left
		cardText.setAlignment(Pos.CENTER_LEFT);

		// Adds the name and username to the card layout
		cardText.getChildren().addAll(nameLabel, usernameLabel);

		// Creates the clickable account card
		Button accountCard = new Button();

		// Places the name and username layout inside the card
		accountCard.setGraphic(cardText);

		// Aligns the card contents to the left
		accountCard.setAlignment(Pos.CENTER_LEFT);

		// Allows the card to use the full available width
		accountCard.setMaxWidth(Double.MAX_VALUE);

		// Gives the card a consistent minimum height
		accountCard.setMinHeight(70);

		// Applies the default modern card appearance
		accountCard.setStyle(
				"-fx-background-color: " + CARD_BACKGROUND + ";" +
				"-fx-border-color: " + BORDER_COLOR + ";" +
				"-fx-border-radius: 10;" +
				"-fx-background-radius: 10;" +
				"-fx-padding: 12;" +
				"-fx-cursor: hand;");

		// Applies the pale-blue appearance while the pointer is over the card
		accountCard.setOnMouseEntered((_) -> accountCard.setStyle(
				"-fx-background-color: " + HOVER_BACKGROUND + ";" +
				"-fx-border-color: " + ACCENT_COLOR + ";" +
				"-fx-border-radius: 10;" +
				"-fx-background-radius: 10;" +
				"-fx-padding: 12;" +
				"-fx-cursor: hand;"));

		// Restores the white card appearance when the pointer leaves the card
		accountCard.setOnMouseExited((_) -> accountCard.setStyle(
				"-fx-background-color: " + CARD_BACKGROUND + ";" +
				"-fx-border-color: " + BORDER_COLOR + ";" +
				"-fx-border-radius: 10;" +
				"-fx-background-radius: 10;" +
				"-fx-padding: 12;" +
				"-fx-cursor: hand;"));

        // Displays the permitted account details when the admin clicks this card
		accountCard.setOnAction((_) -> displayAccountDetails(account));

		// Returns the completed account card
		return accountCard;

	}

	/*******
	 *
	 * <p> Method: displayAccountDetails(UserAccountSummary account) </p>
	 *
	 * <p> Description: Displays the permitted information for the account
	 * selected by the admin </p>
	 *
	 * @param account specifies the selected password-free account summary
	 *
	 */

	private static void displayAccountDetails(UserAccountSummary account) {

		// Hides the account card list
		accountScrollPane.setVisible(false);

		// Hides any empty list or database error message
		label_StatusMessage.setVisible(false);

		// Hides the Refresh button while displaying account details
		button_Refresh.setVisible(false);

		// Displays the Back to User List button
		button_BackToList.setVisible(true);

		// Removes information from any previously selected account
		accountDetailsContainer.getChildren().clear();

		// Creates the heading for the selected account
		Label detailHeading = new Label(account.getDisplayName());

		// Applies the heading appearance
		detailHeading.setStyle(
				"-fx-text-fill: " + PRIMARY_TEXT + ";" +
				"-fx-font-size: 21px;" +
				"-fx-font-weight: bold;");

		// Adds the account heading to the details panel
		accountDetailsContainer.getChildren().add(detailHeading);

		// Adds the account username to the details panel
		accountDetailsContainer.getChildren().add(
				createDetailRow("Username", account.getUserName()));

		// Adds the account first name to the details panel
		accountDetailsContainer.getChildren().add(
				createDetailRow("First name", account.getFirstName()));

		// Adds the account middle name to the details panel
		accountDetailsContainer.getChildren().add(
				createDetailRow("Middle name", account.getMiddleName()));

		// Adds the account last name to the details panel
		accountDetailsContainer.getChildren().add(
				createDetailRow("Last name", account.getLastName()));

		// Adds the account preferred first name to the details panel
		accountDetailsContainer.getChildren().add(
				createDetailRow(
						"Preferred first name",
						account.getPreferredFirstName()));

		// Adds the account email address to the details panel
		accountDetailsContainer.getChildren().add(
				createDetailRow("Email address", account.getEmailAddress()));

		// Adds the account's readable assigned roles to the details panel
		accountDetailsContainer.getChildren().add(
				createDetailRow("Assigned roles", account.getDisplayRoles()));

		// Displays the completed account-details panel
		accountDetailsContainer.setVisible(true);
	}

	/*******
	 *
	 * <p> Method: createDetailRow(String fieldName, String fieldValue) </p>
	 *
	 * <p> Description: Constructs one labeled row for the selected account's
	 * permitted information </p>
	 *
	 * @param fieldName specifies the readable name of the account field
	 *
	 * @param fieldValue specifies the account field's stored value
	 *
	 * @return the completed account-detail row
	 *
	 */

	private static VBox createDetailRow(String fieldName, String fieldValue) {

		// Creates the label identifying the account field
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

		// Returns the completed account-detail row
		return detailRow;

	}

	/*******
	 *
	 * <p> Method: getDisplayValue(String value) </p>
	 *
	 * <p> Description: Replaces a missing optional account value with
	 * readable text so the interface never displays null </p>
	 *
	 * @param value specifies the account value being prepared for display
	 *
	 * @return the stored value or Not provided when the value is blank
	 *
	 */

	private static String getDisplayValue(String value) {

		// Checks whether the stored account value is missing or blank
		if (value == null || value.isBlank()) {

			// Returns readable text for a missing optional value
			return "Not provided";
		}

		// Returns the stored account value without surrounding spaces
		return value.trim();

	}

	/*******
	 *
	 * <p> Method: displayAccountList() </p>
	 *
	 * <p> Description: Returns from the selected account details panel to
	 * the current list of account cards </p>
	 *
	 */

	protected static void displayAccountList() {

		// Redisplays the currently loaded account summaries
		displayAccountSummaries(accountSummaries);

	}
}
