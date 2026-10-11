package guiLessonLearned;

import entityClasses.User;
import entityClasses.LessonsLearned;
import entityClasses.LessonsLearnedList;

import java.time.format.DateTimeFormatter;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.text.Font;

/******
 * 
 * <p> Title: ViewLessonLearned Class. </p>
 *
 * <p> Description: This ViewLessonLearned class displays the Contributor's Lesson Learned page. </p>
 *
 * @author Venus Ho
 *
 */

public class ViewLessonLearned {

	// stores the application window width
	private static double width = applicationMain.FoundationsMain.WINDOW_WIDTH;

	// stores the application window height
	private static double height = applicationMain.FoundationsMain.WINDOW_HEIGHT;

    // stores the page background color
    private static final String PAGE_BACKGROUND = "#F5F7F4";

    // stores the primary button color
    private static final String ACCENT_COLOR = "#607D70";

    // stores the button hover color
    private static final String ACCENT_HOVER = "#4F6A5E";

    // stores the primary text color
    private static final String PRIMARY_TEXT = "#26332D";

    // stores the secondary text color
    private static final String SECONDARY_TEXT = "#69776F";

    // stores the border color
    private static final String BORDER_COLOR = "#D8E2DC";

    // displays the page title
    private static Label label_PageTitle = new Label("My Lessons Learned");

    // displays the current contributor
    private static Label label_ContributorDetails = new Label();

	// displays status and error messages
	protected static Label label_StatusMessage = new Label();

	// displays form status and error messages
	protected static Label label_FormStatusMessage = new Label();

    // holds the contributor's lessons
    protected static VBox lessonListContainer = new VBox();

    // allows the lesson list to scroll
    protected static ScrollPane lessonScrollPane = new ScrollPane();

    // opens the create lesson form
    protected static Button button_Create = new Button("Create Lesson");

    // reloads the lesson list
    protected static Button button_Refresh = new Button("Refresh");

    // returns to the Contributor home page
    protected static Button button_Return = new Button("Return to Contributor Home");

    // logs the contributor out
    protected static Button button_Logout = new Button("Logout");

    // closes the application
    protected static Button button_Quit = new Button("Quit");

    // displays the form title
    protected static Label label_FormTitle = new Label();

    // accepts the lesson title
    protected static TextField text_Title = new TextField();

    // accepts the lesson context
    protected static TextArea text_Context = new TextArea();

    // accepts the expected outcome
    protected static TextArea text_ExpectedOutcome = new TextArea();

    // accepts what happened
    protected static TextArea text_WhatHappened = new TextArea();

    // accepts the root cause
    protected static TextArea text_RootCause = new TextArea();

    // accepts the lesson impact
    protected static TextArea text_Impact = new TextArea();

    // accepts the recommendation
    protected static TextArea text_Recommendation = new TextArea();

    // holds the lesson form fields
    protected static VBox lessonFormContainer = new VBox();

    // allows the lesson form to scroll
    protected static ScrollPane lessonFormScrollPane = new ScrollPane();

    // saves the lesson form
    protected static Button button_Save = new Button("Save Lesson");

    // cancels the lesson form
    protected static Button button_Cancel = new Button("Cancel");

    // stores the lesson being viewed or edited
    protected static LessonsLearned selectedLesson;

	// stores the single view instance
	private static ViewLessonLearned theView;

	// stores the JavaFX application window
	protected static Stage theStage;

	// stores the page container
	protected static Pane theRootPane;

	// stores the current contributor
	protected static User theUser;

	// stores the Lesson Learned scene
	protected static Scene theLessonLearnedScene;

	/******
     * 
	 * <p> Method: displayLessonLearned(Stage ps, User user) </p>
	 *
	 * <p> Description: Displays the Lesson Learned page for the current contributor. </p>
	 *
	 * @param ps specifies the JavaFX application window
     * 
	 * @param user specifies the current contributor
	 *
	 */

	public static void displayLessonLearned(Stage ps, User user) {

		// stores the application window
		theStage = ps;

		// stores the current contributor
		theUser = user;

		// checks whether the view must be created
		if (theView == null) {

			// creates the single view instance
			theView = new ViewLessonLearned();
		}

        // displays the contributor username
        label_ContributorDetails.setText("Contributor: " + theUser.getUserName());

        /// displays the lesson list
        showLessonList();

		// sets the window title
		theStage.setTitle("Contributor Lessons Learned");

		// places the scene in the window
		theStage.setScene(theLessonLearnedScene);

		// displays the window
		theStage.show();
	}

    /******
     * 
     * <p> Method: displayLessonList(LessonsLearnedList lessons) </p>
     *
     * <p> Description: Displays the contributor's lessons in the list. </p>
     *
     * @param lessons specifies the lessons to display
     *
     */

    protected static void displayLessonList(LessonsLearnedList lessons) {

        // removes the previous lesson entries
        lessonListContainer.getChildren().clear();

        // checks whether the lessons could be loaded
        if (lessons == null) {

            // displays the database error
            label_StatusMessage.setText("Unable to load lessons.");

            // stops the display update
            return;
        }

        // checks whether the lesson list is empty
        if (lessons.isEmpty()) {

            // displays the empty-list message
            label_StatusMessage.setText("No lessons found.");

            // stops the display update
            return;
        }

        // displays the successful load message
        label_StatusMessage.setText("Lessons loaded successfully.");

        // creates the date format
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MMM d, yyyy h:mm a");

        // processes each lesson
        for (LessonsLearned lesson : lessons.getLessons()) {

            // creates the lesson entry
            VBox lessonEntry = new VBox(4);

            // adds spacing inside the lesson entry
            lessonEntry.setPadding(new Insets(12));

            // allows the entry to fill the list width
            lessonEntry.setMaxWidth(Double.MAX_VALUE);

            // styles the lesson entry
            lessonEntry.setStyle(
                    "-fx-background-color: white;"
                    + "-fx-border-color: " + BORDER_COLOR + ";"
                    + "-fx-border-radius: 4;"
                    + "-fx-background-radius: 4;");

            // creates the lesson title
            Label titleLabel = new Label(lesson.getTitle());

            // sets the title font
            titleLabel.setFont(Font.font("Arial", 17));

            // styles the lesson title
            titleLabel.setStyle(
                    "-fx-text-fill: " + PRIMARY_TEXT + ";"
                    + "-fx-font-weight: bold;");

            // creates the update-date label
            Label updatedLabel = new Label("Updated: " + lesson.getLastUpdated().format(dateFormat));

            // sets the update-date font
            updatedLabel.setFont(Font.font("Arial", 12));

            // styles the update date
            updatedLabel.setStyle("-fx-text-fill: " + SECONDARY_TEXT + ";");

			// creates the lesson action row
			HBox lessonActions = new HBox(8);

			// creates the view button
			Button viewButton = new Button("View");

			// styles the view button
			styleSecondaryButton(viewButton);

			// displays the lesson details
			viewButton.setOnAction((_) -> showLessonDetails(lesson));

            // creates the edit button
            Button editButton = new Button("Edit");

            // styles the edit button
            styleSecondaryButton(editButton);

            // creates the delete button
            Button deleteButton = new Button("Delete");

            // styles the delete button
            styleSecondaryButton(deleteButton);

            // requests confirmation before deletion
            deleteButton.setOnAction((_) -> confirmLessonDelete(lesson));

            // opens the lesson edit form
            editButton.setOnAction((_) -> showLessonForm(lesson));

            // adds the lesson buttons to the action row
            lessonActions.getChildren().addAll(viewButton, editButton, deleteButton);

			// adds the lesson information to the entry
			lessonEntry.getChildren().addAll(titleLabel, updatedLabel, lessonActions);

            // adds the lesson entry to the list
            lessonListContainer.getChildren().add(lessonEntry);
        }
    }

    /******
     * 
     * <p> Method: showLessonDetails(LessonsLearned lesson) </p>
     *
     * <p> Description: Displays all information for one lesson. </p>
     *
     * @param lesson specifies the lesson to display
     *
     */

    protected static void showLessonDetails(LessonsLearned lesson) {

        // creates the date format
        DateTimeFormatter dateFormat =
                DateTimeFormatter.ofPattern("MMM d, yyyy h:mm a");

        // creates the lesson dialog
        Alert lessonDialog = new Alert(Alert.AlertType.INFORMATION);

        // sets the dialog title
        lessonDialog.setTitle("Lesson Learned Details");

        // displays the lesson title
        lessonDialog.setHeaderText(lesson.getTitle());

        // displays the lesson information
        lessonDialog.setContentText(
                "Lesson ID: " + lesson.getLessonId()
                + "\nContributor: " + lesson.getContributorId()
                + "\nDate Created: "
                + lesson.getDateCreated().format(dateFormat)
                + "\nLast Updated: "
                + lesson.getLastUpdated().format(dateFormat)
                + "\n\nContext:\n" + lesson.getContext()
                + "\n\nExpected Outcome:\n"
                + lesson.getExpectedOutcome()
                + "\n\nWhat Happened:\n"
                + lesson.getWhatHappened()
                + "\n\nRoot Cause:\n" + lesson.getRootCause()
                + "\n\nImpact:\n" + lesson.getImpact()
                + "\n\nRecommendation:\n"
                + lesson.getRecommendation());

        // allows the dialog text to wrap
        lessonDialog.getDialogPane().setContent(
                new Label(lessonDialog.getContentText()));

        // gets the dialog content label
        Label detailLabel =
                (Label) lessonDialog.getDialogPane().getContent();

        // wraps long lesson information
        detailLabel.setWrapText(true);

        // sets the dialog width
        lessonDialog.getDialogPane().setPrefWidth(600);

        // displays the lesson dialog
        lessonDialog.showAndWait();
    }

    /******
     * 
     * <p> Method: confirmLessonDelete(LessonsLearned lesson) </p>
     *
     * <p> Description: Confirms whether a lesson should be deleted. </p>
     *
     * @param lesson specifies the lesson to delete
     *
     */

    protected static void confirmLessonDelete(LessonsLearned lesson) {

        // creates the confirmation dialog
        Alert deleteDialog = new Alert(Alert.AlertType.CONFIRMATION);

        // sets the dialog title
        deleteDialog.setTitle("Delete Lesson");

        // displays the confirmation heading
        deleteDialog.setHeaderText("Delete \"" + lesson.getTitle() + "\"?");

        // displays the confirmation message
        deleteDialog.setContentText("This lesson will be permanently deleted.");

        // displays the confirmation dialog
        ButtonType response = deleteDialog.showAndWait().orElse(ButtonType.CANCEL);

        // checks whether deletion was confirmed
        if (response == ButtonType.OK) {

            // deletes the lesson
            ControllerLessonLearned.performDelete(lesson);
        }
    }

    /******
     * 
     * <p> Method: showLessonList() </p>
     *
     * <p> Description: Displays the contributor's lesson list. </p>
     *
     */

    protected static void showLessonList() {

        // clears the current screen
        theRootPane.getChildren().clear();

        // clears the selected lesson
        selectedLesson = null;

        // restores the list page title
        label_PageTitle.setText("My Lessons Learned");

        // adds the lesson-list controls
        theRootPane.getChildren().addAll(
                label_PageTitle,
                label_ContributorDetails,
                button_Create,
                button_Refresh,
                lessonScrollPane,
                label_StatusMessage,
                button_Return,
                button_Logout,
                button_Quit);

        // loads the contributor's lessons
        ControllerLessonLearned.loadLessons();
    }

    /******
     * 
     * <p> Method: showLessonForm(LessonsLearned lesson) </p>
     *
     * <p> Description: Displays the form for creating or editing a lesson. </p>
     *
     * @param lesson specifies the lesson to edit or null when creating
     *
     */

    protected static void showLessonForm(LessonsLearned lesson) {

        // stores the selected lesson
        selectedLesson = lesson;

        // clears the form status
        label_FormStatusMessage.setText("");

        // checks whether a new lesson is being created
        if (lesson == null) {

            // displays the create form title
            label_FormTitle.setText("Create Lesson Learned");

            // displays the create button text
            button_Save.setText("Create Lesson");

            // clears the title
            text_Title.clear();

            // clears the context
            text_Context.clear();

            // clears the expected outcome
            text_ExpectedOutcome.clear();

            // clears what happened
            text_WhatHappened.clear();

            // clears the root cause
            text_RootCause.clear();

            // clears the impact
            text_Impact.clear();

            // clears the recommendation
            text_Recommendation.clear();
        }

        // handles an existing lesson
        else {

            // displays the edit form title
            label_FormTitle.setText("Edit Lesson Learned");

            // displays the update button text
            button_Save.setText("Update Lesson");

            // displays the title
            text_Title.setText(lesson.getTitle());

            // displays the context
            text_Context.setText(lesson.getContext());

            // displays the expected outcome
            text_ExpectedOutcome.setText(
                    lesson.getExpectedOutcome());

            // displays what happened
            text_WhatHappened.setText(
                    lesson.getWhatHappened());

            // displays the root cause
            text_RootCause.setText(lesson.getRootCause());

            // displays the impact
            text_Impact.setText(lesson.getImpact());

            // displays the recommendation
            text_Recommendation.setText(
                    lesson.getRecommendation());
        }

        // clears the current screen
        theRootPane.getChildren().clear();

        // adds the form controls
        theRootPane.getChildren().addAll(label_ContributorDetails, lessonFormScrollPane);
    }

    /******
     * 
     * <p> Method: buildLessonFromForm() </p>
     *
     * <p> Description: Builds a lesson using the current form values. </p>
     *
     * @return the lesson containing the current form values
     *
     */

    protected static LessonsLearned buildLessonFromForm() {

        // checks whether a new lesson is being created
        if (selectedLesson == null) {

            // builds a new lesson
            return new LessonsLearned(
                    0,
                    theUser.getUserName(),
                    text_Title.getText(),
                    text_Context.getText(),
                    text_ExpectedOutcome.getText(),
                    text_WhatHappened.getText(),
                    text_RootCause.getText(),
                    text_Impact.getText(),
                    text_Recommendation.getText(),
                    null,
                    null);
        }

        // builds the edited lesson
        return new LessonsLearned(
                selectedLesson.getLessonId(),
                selectedLesson.getContributorId(),
                text_Title.getText(),
                text_Context.getText(),
                text_ExpectedOutcome.getText(),
                text_WhatHappened.getText(),
                text_RootCause.getText(),
                text_Impact.getText(),
                text_Recommendation.getText(),
                selectedLesson.getDateCreated(),
                selectedLesson.getLastUpdated());
    }

	/******
     * 
	 * <p> Constructor: ViewLessonLearned() </p>
	 *
	 * <p> Description: Constructs the Lesson Learned page. </p>
	 *
	 */

	private ViewLessonLearned() {

		// creates the page container
		theRootPane = new Pane();

		// creates the scene
		theLessonLearnedScene = new Scene(theRootPane, width, height);

        // sets the page background
        theRootPane.setStyle("-fx-background-color: " + PAGE_BACKGROUND + ";");

        // sets the page title font
        label_PageTitle.setFont(Font.font("Arial", 28));

        // gives the page title the window width
        label_PageTitle.setMinWidth(width);

        // centers the page title
        label_PageTitle.setAlignment(Pos.CENTER);

        // positions the page title
        label_PageTitle.setLayoutY(15);

        // styles the page title
        label_PageTitle.setStyle("-fx-text-fill: " + PRIMARY_TEXT + "; -fx-font-weight: bold;");

        // sets the contributor label font
        label_ContributorDetails.setFont(Font.font("Arial", 16));

        // positions the contributor label
        label_ContributorDetails.setLayoutX(25);

        // positions the contributor label
        label_ContributorDetails.setLayoutY(60);

        // styles the contributor label
        label_ContributorDetails.setStyle("-fx-text-fill: " + SECONDARY_TEXT + ";");

        // spaces the lesson entries
        lessonListContainer.setSpacing(8);

        // adds padding around the lesson entries
        lessonListContainer.setPadding(new Insets(10));

        // places the lesson list inside the scroll pane
        lessonScrollPane.setContent(lessonListContainer);

        // fits lesson entries to the scroll pane width
        lessonScrollPane.setFitToWidth(true);

        // positions the lesson list
        lessonScrollPane.setLayoutX(25);

        // positions the lesson list
        lessonScrollPane.setLayoutY(135);

        // sets the lesson list width
        lessonScrollPane.setPrefWidth(750);

        // sets the lesson list height
        lessonScrollPane.setPrefHeight(340);

        // styles the lesson list
        lessonScrollPane.setStyle(
                "-fx-background-color: transparent;"
                + "-fx-border-color: " + BORDER_COLOR + ";"
                + "-fx-border-radius: 4;");

        // sets the status label font
        label_StatusMessage.setFont(Font.font("Arial", 14));

        // positions the status label
        label_StatusMessage.setLayoutX(25);

        // positions the status label
        label_StatusMessage.setLayoutY(485);

        // sets the status label width
        label_StatusMessage.setMinWidth(750);

        // Adds the lesson-list controls
        theRootPane.getChildren().addAll(
                label_PageTitle,
                label_ContributorDetails,
                button_Create,
                button_Refresh,
                lessonScrollPane,
                label_StatusMessage,
                button_Return,
                button_Logout,
                button_Quit);

        // styles the status label
        label_StatusMessage.setStyle("-fx-text-fill: " + SECONDARY_TEXT + ";");

        // styles the create button
        stylePrimaryButton(button_Create);

        // opens an empty lesson form
        button_Create.setOnAction((_) -> showLessonForm(null));

        // sets the create button width
        button_Create.setMinWidth(140);

        // positions the create button
        button_Create.setLayoutX(25);

        // positions the create button
        button_Create.setLayoutY(92);

        // styles the refresh button
        styleSecondaryButton(button_Refresh);

        // reloads the lessons when Refresh is pressed
        button_Refresh.setOnAction((_) -> ControllerLessonLearned.performRefresh());

        // sets the refresh button width
        button_Refresh.setMinWidth(110);

        // positions the refresh button
        button_Refresh.setLayoutX(180);

        // positions the refresh button
        button_Refresh.setLayoutY(92);

        // styles the return button
        styleSecondaryButton(button_Return);

        // returns to the Contributor home page
        button_Return.setOnAction((_) -> ControllerLessonLearned.performReturn());

        // sets the return button width
        button_Return.setMinWidth(220);

        // positions the return button
        button_Return.setLayoutX(25);

        // positions the return button
        button_Return.setLayoutY(535);

        // styles the logout button
        styleSecondaryButton(button_Logout);

        // logs out the contributor
        button_Logout.setOnAction((_) -> ControllerLessonLearned.performLogout());

        // sets the logout button width
        button_Logout.setMinWidth(110);

        // positions the logout button
        button_Logout.setLayoutX(510);

        // positions the logout button
        button_Logout.setLayoutY(535);

        // styles the quit button
        styleSecondaryButton(button_Quit);

        // closes the application
        button_Quit.setOnAction((_) -> ControllerLessonLearned.performQuit());

        // sets the quit button width
        button_Quit.setMinWidth(110);

        // positions the quit button
        button_Quit.setLayoutX(640);

		// positions the quit button
		button_Quit.setLayoutY(535);

		// sets the form title font
		label_FormTitle.setFont(Font.font("Arial", 24));

		// styles the form title
		label_FormTitle.setStyle(
				"-fx-text-fill: " + PRIMARY_TEXT + ";"
				+ "-fx-font-weight: bold;");

		// configures the title field
		configureTextField(text_Title);

		// displays the title prompt
		text_Title.setPromptText("Lesson title");

		// configures the context field
		configureTextArea(text_Context);

		// displays the context prompt
		text_Context.setPromptText("Lesson context");

		// configures the expected-outcome field
		configureTextArea(text_ExpectedOutcome);

		// displays the expected-outcome prompt
		text_ExpectedOutcome.setPromptText("Expected outcome");

		// configures the what-happened field
		configureTextArea(text_WhatHappened);

		// displays the what-happened prompt
		text_WhatHappened.setPromptText("What happened");

		// configures the root-cause field
		configureTextArea(text_RootCause);

		// displays the root-cause prompt
		text_RootCause.setPromptText("Root cause");

		// configures the impact field
		configureTextArea(text_Impact);

		// displays the impact prompt
		text_Impact.setPromptText("Impact");

		// configures the recommendation field
		configureTextArea(text_Recommendation);

		// displays the recommendation prompt
		text_Recommendation.setPromptText("Recommendation");

		// sets the form-status font
		label_FormStatusMessage.setFont(Font.font("Arial", 14));

		// styles the form-status label
		label_FormStatusMessage.setStyle("-fx-text-fill: " + SECONDARY_TEXT + ";");

		// spaces the form fields
		lessonFormContainer.setSpacing(6);

		// adds padding around the form
		lessonFormContainer.setPadding(new Insets(16));

		// allows fields to fill the form width
		lessonFormContainer.setFillWidth(true);

		// creates the form-button row
		HBox formButtons = new HBox(10);

			// styles the save button
		stylePrimaryButton(button_Save);

        // saves the lesson form
        button_Save.setOnAction((_) -> ControllerLessonLearned.performSave());

		// sets the save button width
		button_Save.setMinWidth(140);

		// styles the cancel button
		styleSecondaryButton(button_Cancel);

        // returns to the lesson list
        button_Cancel.setOnAction((_) -> showLessonList()); 

		// sets the cancel button width
		button_Cancel.setMinWidth(110);

		// adds the buttons to their row
		formButtons.getChildren().addAll(button_Save, button_Cancel);

		// adds the fields to the form
		lessonFormContainer.getChildren().addAll(
				label_FormTitle,
				createFormLabel("Title"),
				text_Title,
				createFormLabel("Context"),
				text_Context,
				createFormLabel("Expected Outcome"),
				text_ExpectedOutcome,
				createFormLabel("What Happened"),
				text_WhatHappened,
				createFormLabel("Root Cause"),
				text_RootCause,
				createFormLabel("Impact"),
				text_Impact,
				createFormLabel("Recommendation"),
				text_Recommendation,
				label_FormStatusMessage,
				formButtons);

		// places the form inside its scroll pane
		lessonFormScrollPane.setContent(lessonFormContainer);

		// fits the form to the scroll-pane width
		lessonFormScrollPane.setFitToWidth(true);

		// positions the lesson form
		lessonFormScrollPane.setLayoutX(25);

		// positions the lesson form
		lessonFormScrollPane.setLayoutY(80);

		// sets the lesson form width
		lessonFormScrollPane.setPrefWidth(750);

		// sets the lesson form height
		lessonFormScrollPane.setPrefHeight(490);

		// styles the lesson form
		lessonFormScrollPane.setStyle(
				"-fx-background-color: transparent;"
				+ "-fx-border-color: " + BORDER_COLOR + ";"
				+ "-fx-border-radius: 4;");

	}

    /******
     * 
     * <p> Method: stylePrimaryButton(Button button) </p>
     *
     * <p> Description: Applies the primary button style. </p>
     *
     * @param button specifies the button to style
     *
     */

    private static void stylePrimaryButton(Button button) {

        // sets the button font
        button.setFont(Font.font("Arial", 15));

        // styles the primary button
        button.setStyle(
                "-fx-background-color: " + ACCENT_COLOR + ";"
                + "-fx-text-fill: white;"
                + "-fx-background-radius: 4;");

        // styles the button when hovered
        button.setOnMouseEntered((_) -> button.setStyle(
                "-fx-background-color: " + ACCENT_HOVER + ";"
                + "-fx-text-fill: white;"
                + "-fx-background-radius: 4;"));

        // restores the button style
        button.setOnMouseExited((_) -> button.setStyle(
                "-fx-background-color: " + ACCENT_COLOR + ";"
                + "-fx-text-fill: white;"
                + "-fx-background-radius: 4;"));
    }

    /******
     * 
     * <p> Method: styleSecondaryButton(Button button) </p>
     *
     * <p> Description: Applies the secondary button style. </p>
     *
     * @param button specifies the button to style
     *
     */

    private static void styleSecondaryButton(Button button) {

        // sets the button font
        button.setFont(Font.font("Arial", 15));

        // styles the secondary button
        button.setStyle(
                "-fx-background-color: transparent;"
                + "-fx-text-fill: " + ACCENT_COLOR + ";"
                + "-fx-border-color: " + ACCENT_COLOR + ";"
                + "-fx-border-radius: 4;"
                + "-fx-background-radius: 4;");

        // styles the button when hovered
        button.setOnMouseEntered((_) -> button.setStyle(
                "-fx-background-color: " + ACCENT_COLOR + ";"
                + "-fx-text-fill: white;"
                + "-fx-border-color: " + ACCENT_COLOR + ";"
                + "-fx-border-radius: 4;"
                + "-fx-background-radius: 4;"));

        // restores the button style
        button.setOnMouseExited((_) -> button.setStyle(
                "-fx-background-color: transparent;"
                + "-fx-text-fill: " + ACCENT_COLOR + ";"
                + "-fx-border-color: " + ACCENT_COLOR + ";"
                + "-fx-border-radius: 4;"
                + "-fx-background-radius: 4;"));
    }

    /******
     * 
     * <p> Method: createFormLabel(String text) </p>
     *
     * <p> Description: Creates a label for a lesson form field. </p>
     *
     * @param text specifies the label text
     *
     * @return the configured form label
     *
     */

    private static Label createFormLabel(String text) {

        // creates the form label
        Label label = new Label(text);

        // sets the label font
        label.setFont(Font.font("Arial", 14));

        // styles the form label
        label.setStyle(
                "-fx-text-fill: " + PRIMARY_TEXT + ";"
                + "-fx-font-weight: bold;");

        // returns the form label
        return label;
    }

    /******
     * 
     * <p> Method: configureTextField(TextField field) </p>
     *
     * <p> Description: Configures a lesson form text field. </p>
     *
     * @param field specifies the text field to configure
     *
     */

    private static void configureTextField(TextField field) {

        // allows the field to fill the form width
        field.setMaxWidth(Double.MAX_VALUE);

        // sets the field font
        field.setFont(Font.font("Arial", 14));

        // styles the text field
        field.setStyle(
                "-fx-background-color: white;"
                + "-fx-border-color: " + BORDER_COLOR + ";"
                + "-fx-border-radius: 4;"
                + "-fx-background-radius: 4;");
    }

    /******
     * 
     * <p> Method: configureTextArea(TextArea area) </p>
     *
     * <p> Description: Configures a lesson form text area. </p>
     *
     * @param area specifies the text area to configure
     *
     */

    private static void configureTextArea(TextArea area) {

        // allows the area to fill the form width
        area.setMaxWidth(Double.MAX_VALUE);

        // sets the visible row count
        area.setPrefRowCount(3);

        // wraps long text
        area.setWrapText(true);

        // sets the area font
        area.setFont(Font.font("Arial", 14));

        // styles the text area
        area.setStyle(
                "-fx-background-color: white;"
                + "-fx-border-color: " + BORDER_COLOR + ";"
                + "-fx-border-radius: 4;"
                + "-fx-background-radius: 4;");
    }

}
