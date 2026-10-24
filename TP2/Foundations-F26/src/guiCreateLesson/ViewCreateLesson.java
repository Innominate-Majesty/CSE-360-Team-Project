package guiCreateLesson;

import entityClasses.User;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/*******
 *
 * <p> Title: ViewCreateLesson Class. </p>
 *
 * <p> Description: The JavaFX-based Create Lesson View. This class displays the
 * Lesson Details and Experience sections and forwards submitted values to the
 * Controller for validation and storage. </p>
 *
 * <p> Copyright: Lynn Robert Carter © 2025 </p>
 *
 * @author Venus Ho
 * @author Alexander Murray
 * @author Alan Shimp
 * @author Caleb Beaven
 * @author Krithik Duraisamy
 *
 * @version 1.0         2026-10-23 Initial version after MVC refactoring
 *
 */

public class ViewCreateLesson {

	// Defines the page background color
	private static final String PAGE_BACKGROUND = "#F1F4F0";

	// Defines the primary text color
	private static final String PRIMARY_TEXT = "#203029";

	// Defines the primary sage action color
	private static final String ACCENT_COLOR = "#405D52";

	// Defines the form border color
	private static final String BORDER_COLOR = "#CAD7D0";

	/******
	 *
	 * <p> Constructor: ViewCreateLesson() </p>
	 *
	 * <p> Description: Creates the Create Lesson View. The display operation is
	 * static, so the application does not need to instantiate this class. </p>
	 *
	 */
	public ViewCreateLesson() {
		// Default constructor
	}

	/******
	 *
	 * <p> Method: displayCreateLesson() </p>
	 *
	 * <p> Description: Displays the Create Lesson form using the Contributor sage
	 * theme and connects its actions to the Create Lesson Controller. </p>
	 *
	 * @param owner Contributor Home window that owns this form
	 * @param user active contributor creating the lesson
	 * @param onCreated operation used to refresh the Contributor Home page
	 *
	 */
	public static void displayCreateLesson(
			Stage owner,
			User user,
			Runnable onCreated) {

		// Creates the Create Lesson window
		Stage createStage = new Stage();

		// Keeps the form associated with the Contributor Home page
		createStage.initModality(Modality.WINDOW_MODAL);

		// Sets the Contributor Home page as the owner when available
		if (owner != null) {
			createStage.initOwner(owner);
		}

		// Sets the form-window title
		createStage.setTitle("Create Lesson");

		// Creates the two-column form layout
		GridPane grid = new GridPane();
		grid.setHgap(14);
		grid.setVgap(10);
		grid.setPadding(new Insets(24));
		grid.setStyle("-fx-background-color: " + PAGE_BACKGROUND + ";");

		// Creates the main form heading
		Label heading = new Label("Create a Lesson");
		heading.setStyle(
				"-fx-font-family: 'Helvetica';"
				+ "-fx-font-size: 24;"
				+ "-fx-font-weight: bold;"
				+ "-fx-text-fill: " + PRIMARY_TEXT + ";");

		// Creates the two form-section headings
		Label lessonHeading = createSectionLabel("LESSON DETAILS");
		Label experienceHeading = createSectionLabel("EXPERIENCE");

		// Creates the Lesson Details controls
		TextField title = new TextField();
		TextField category = new TextField();
		TextField author = new TextField(user == null ? "" : user.getUserName());
		TextArea problem = createTextArea("Describe the problem or situation", 2);
		TextArea solution = createTextArea("Describe the solution or lesson learned", 2);

		// Provides instructions for the Lesson Details controls
		title.setPromptText("A clear title between 5 and 100 characters");
		category.setPromptText("Example: Testing, Teamwork, or Design");

		// Prevents the contributor from changing the Author
		author.setEditable(false);

		// Creates the Experience controls
		TextField time = new TextField("0");
		TextArea whatWasDone = createTextArea("What was done?", 2);
		TextArea howItWasDone = createTextArea("How was it done?", 2);
		TextArea teamEffort = createTextArea("How did the team contribute?", 2);

		// Explains the expected Time Spent value
		time.setPromptText("Hours spent");

		// Styles the single-line form controls
		styleTextField(title);
		styleTextField(category);
		styleTextField(author);
		styleTextField(time);

		// Gives the read-only Author field a distinct appearance
		author.setStyle(
				author.getStyle()
				+ "-fx-opacity: 0.75;"
				+ "-fx-background-color: #E8EDE9;");

		// Adds the headings and fields to the form
		grid.add(heading, 0, 0, 2, 1);
		grid.add(lessonHeading, 0, 1, 2, 1);
		addFormRow(grid, "Title", title, 2);
		addFormRow(grid, "Category", category, 3);
		addFormRow(grid, "Author", author, 4);
		addFormRow(grid, "Problem Description", problem, 5);
		addFormRow(grid, "Solution", solution, 6);
		grid.add(experienceHeading, 0, 7, 2, 1);
		addFormRow(grid, "Time Spent (hours)", time, 8);
		addFormRow(grid, "What Was Done", whatWasDone, 9);
		addFormRow(grid, "How It Was Done", howItWasDone, 10);
		addFormRow(grid, "Team Effort", teamEffort, 11);

		// Creates and styles the form actions
		Button cancel = new Button("Cancel");
		Button create = new Button("Create Lesson");
		styleSecondaryButton(cancel);
		stylePrimaryButton(create);

		// Arranges the form actions on the right
		HBox actions = new HBox(10, cancel, create);
		actions.setAlignment(Pos.CENTER_RIGHT);
		grid.add(actions, 1, 12);

		// Closes the form without creating records
		cancel.setOnAction((_) -> createStage.close());

		// Sends the entered values to the Controller
		create.setOnAction((_) -> ControllerCreateLesson.handleCreateLesson(
				createStage,
				author.getText(),
				title.getText(),
				category.getText(),
				problem.getText(),
				solution.getText(),
				time.getText(),
				whatWasDone.getText(),
				howItWasDone.getText(),
				teamEffort.getText(),
				onCreated));

		// Makes the complete form scrollable
		ScrollPane formScroll = new ScrollPane(grid);
		formScroll.setFitToWidth(true);
		formScroll.setStyle(
				"-fx-background-color: " + PAGE_BACKGROUND + ";"
				+ "-fx-background: " + PAGE_BACKGROUND + ";"
				+ "-fx-border-color: transparent;");

		// Displays the Create Lesson form
		createStage.setScene(new Scene(formScroll, 690, 610));
		createStage.show();
	}

	/******
	 *
	 * <p> Method: addFormRow() </p>
	 *
	 * <p> Description: Adds a consistently styled label and input control to one
	 * row of the Create Lesson form. </p>
	 *
	 * @param grid form layout receiving the row
	 * @param labelText text displayed beside the field
	 * @param field input control placed into the form
	 * @param row grid row receiving the controls
	 *
	 */
	private static void addFormRow(
			GridPane grid,
			String labelText,
			Control field,
			int row) {

		// Creates and styles the field label
		Label label = new Label(labelText);
		label.setStyle(
				"-fx-font-family: 'Helvetica';"
				+ "-fx-font-size: 12;"
				+ "-fx-font-weight: bold;"
				+ "-fx-text-fill: #65736B;");

		// Places the label and control in the form row
		grid.add(label, 0, row);
		grid.add(field, 1, row);
	}

	/******
	 *
	 * <p> Method: createTextArea() </p>
	 *
	 * <p> Description: Creates a wrapped multiline field using the Create Lesson
	 * form style. </p>
	 *
	 * @param prompt instruction displayed while the field is empty
	 * @param rows preferred number of visible text rows
	 * @return configured multiline field
	 *
	 */
	private static TextArea createTextArea(String prompt, int rows) {
		TextArea area = new TextArea();
		area.setPromptText(prompt);
		area.setPrefRowCount(rows);
		area.setWrapText(true);
		area.setPrefWidth(455);
		area.setStyle(
				"-fx-control-inner-background: white;"
				+ "-fx-background-color: white;"
				+ "-fx-border-color: " + BORDER_COLOR + ";"
				+ "-fx-border-width: 1;"
				+ "-fx-border-radius: 8;"
				+ "-fx-background-radius: 8;"
				+ "-fx-font-family: 'Helvetica';"
				+ "-fx-font-size: 12;"
				+ "-fx-focus-color: transparent;"
				+ "-fx-faint-focus-color: transparent;");
		return area;
	}

	/******
	 *
	 * <p> Method: styleTextField() </p>
	 *
	 * <p> Description: Applies the Create Lesson form style to a single-line
	 * text field. </p>
	 *
	 * @param field text field being styled
	 *
	 */
	private static void styleTextField(TextField field) {
		field.setPrefWidth(455);
		field.setStyle(
				"-fx-background-color: white;"
				+ "-fx-border-color: " + BORDER_COLOR + ";"
				+ "-fx-border-width: 1;"
				+ "-fx-border-radius: 8;"
				+ "-fx-background-radius: 8;"
				+ "-fx-padding: 8 10 8 10;"
				+ "-fx-font-family: 'Helvetica';"
				+ "-fx-font-size: 12;"
				+ "-fx-focus-color: transparent;"
				+ "-fx-faint-focus-color: transparent;");
	}

	/******
	 *
	 * <p> Method: createSectionLabel() </p>
	 *
	 * <p> Description: Creates an uppercase heading for a Create Lesson form
	 * section. </p>
	 *
	 * @param text section heading text
	 * @return configured section label
	 *
	 */
	private static Label createSectionLabel(String text) {
		Label label = new Label(text);
		label.setStyle(
				"-fx-font-family: 'Helvetica';"
				+ "-fx-font-size: 11;"
				+ "-fx-font-weight: bold;"
				+ "-fx-text-fill: " + ACCENT_COLOR + ";"
				+ "-fx-padding: 10 0 2 0;");
		return label;
	}

	/******
	 *
	 * <p> Method: stylePrimaryButton() </p>
	 *
	 * <p> Description: Applies the primary sage style to a Create Lesson action. </p>
	 *
	 * @param button primary action button being styled
	 *
	 */
	private static void stylePrimaryButton(Button button) {
		button.setStyle(
				"-fx-background-color: " + ACCENT_COLOR + ";"
				+ "-fx-text-fill: white;"
				+ "-fx-background-radius: 8;"
				+ "-fx-padding: 8 16 8 16;"
				+ "-fx-cursor: hand;");
	}

	/******
	 *
	 * <p> Method: styleSecondaryButton() </p>
	 *
	 * <p> Description: Applies the secondary style to a Create Lesson action. </p>
	 *
	 * @param button secondary action button being styled
	 *
	 */
	private static void styleSecondaryButton(Button button) {
		button.setStyle(
				"-fx-background-color: white;"
				+ "-fx-text-fill: " + PRIMARY_TEXT + ";"
				+ "-fx-border-color: " + BORDER_COLOR + ";"
				+ "-fx-border-width: 1;"
				+ "-fx-border-radius: 8;"
				+ "-fx-background-radius: 8;"
				+ "-fx-padding: 8 16 8 16;"
				+ "-fx-cursor: hand;");
	}

	/******
	 *
	 * <p> Method: showMessage() </p>
	 *
	 * <p> Description: Displays a styled success or error message associated with
	 * the Create Lesson form. </p>
	 *
	 * @param owner form that owns the message window
	 * @param title message-window title
	 * @param message information displayed to the contributor
	 * @param error true when the message reports an error
	 *
	 */
	protected static void showMessage(
			Stage owner,
			String title,
			String message,
			boolean error) {

		// Creates and configures the message window
		Stage messageStage = new Stage();
		messageStage.initModality(Modality.WINDOW_MODAL);
		messageStage.initOwner(owner);
		messageStage.setTitle(title);

		// Creates the message heading and details
		Label heading = new Label(title);
		Label details = new Label(message);
		heading.setStyle(
				"-fx-font-family: 'Helvetica';"
				+ "-fx-font-size: 18;"
				+ "-fx-font-weight: bold;"
				+ "-fx-text-fill: " + (error ? "#B44747" : PRIMARY_TEXT) + ";");
		details.setWrapText(true);
		details.setStyle(
				"-fx-font-family: 'Helvetica';"
				+ "-fx-font-size: 12;"
				+ "-fx-text-fill: #65736B;");

		// Creates and configures the Close action
		Button close = new Button("Close");
		stylePrimaryButton(close);
		close.setDefaultButton(true);
		close.setOnAction((_) -> messageStage.close());

		// Arranges and displays the message content
		HBox actions = new HBox(close);
		actions.setAlignment(Pos.CENTER_RIGHT);
		VBox content = new VBox(14, heading, details, actions);
		content.setPadding(new Insets(24));
		content.setStyle("-fx-background-color: " + PAGE_BACKGROUND + ";");
		messageStage.setScene(new Scene(content, 430, 175));
		messageStage.setResizable(false);
		messageStage.showAndWait();
	}
}
