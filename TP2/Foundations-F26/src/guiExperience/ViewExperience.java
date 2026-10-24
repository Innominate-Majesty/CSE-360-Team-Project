package guiExperience;

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
 * <p> Title: ViewExperience Class. </p>
 *
 * <p> Description: The JavaFX-based Experience View. This class displays the Add
 * Experience form using the Contributor sage theme and forwards submitted values
 * to the Experience Controller. </p>
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

public class ViewExperience {

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
	 * <p> Constructor: ViewExperience() </p>
	 *
	 * <p> Description: Creates the Experience View. The display operation is static,
	 * so the application does not need to instantiate this class. </p>
	 *
	 */
	public ViewExperience() {
		// Default constructor
	}

	/******
	 *
	 * <p> Method: displayAddExperience() </p>
	 *
	 * <p> Description: Displays the Add Experience form and connects its actions to
	 * the Experience Controller. </p>
	 *
	 * @param owner window that owns the Add Experience form
	 * @param user active contributor creating the Experience
	 * @param selectedLessonId optional Lesson ID used to populate the form
	 * @param onCreated operation used to refresh the calling page
	 *
	 */
	protected static void displayAddExperience(
			Stage owner,
			User user,
			Integer selectedLessonId,
			Runnable onCreated) {

		// Creates and configures the Add Experience window
		Stage experienceStage = new Stage();
		experienceStage.initModality(Modality.WINDOW_MODAL);
		if (owner != null) {
			experienceStage.initOwner(owner);
		}
		experienceStage.setTitle("Add Experience");

		// Creates the two-column form layout
		GridPane grid = new GridPane();
		grid.setHgap(14);
		grid.setVgap(10);
		grid.setPadding(new Insets(24));
		grid.setStyle("-fx-background-color: " + PAGE_BACKGROUND + ";");

		// Creates and styles the main heading
		Label heading = new Label("Add an Experience");
		heading.setStyle(
				"-fx-font-family: 'Helvetica';"
				+ "-fx-font-size: 24;"
				+ "-fx-font-weight: bold;"
				+ "-fx-text-fill: " + PRIMARY_TEXT + ";");

		// Creates the Experience section heading
		Label experienceHeading = createSectionLabel("EXPERIENCE");

		// Creates the Experience controls
		TextField lessonId = new TextField(
				selectedLessonId == null ? "" : String.valueOf(selectedLessonId));
		TextField time = new TextField("0");
		TextArea whatWasDone = createTextArea("What was done?", 2);
		TextArea howItWasDone = createTextArea("How was it done?", 2);
		TextArea teamEffort = createTextArea("How did the team contribute?", 2);
		lessonId.setPromptText("Enter the Lesson ID");
		time.setPromptText("Hours spent");

		// Styles the single-line form controls
		styleTextField(lessonId);
		styleTextField(time);

		// Adds the heading and Experience controls to the form
		grid.add(heading, 0, 0, 2, 1);
		grid.add(experienceHeading, 0, 1, 2, 1);
		addFormRow(grid, "Lesson ID", lessonId, 2);
		addFormRow(grid, "Time Spent (hours)", time, 3);
		addFormRow(grid, "What Was Done", whatWasDone, 4);
		addFormRow(grid, "How It Was Done", howItWasDone, 5);
		addFormRow(grid, "Team Effort", teamEffort, 6);

		// Creates and styles the form actions
		Button cancel = new Button("Cancel");
		Button save = new Button("Save Experience");
		styleSecondaryButton(cancel);
		stylePrimaryButton(save);

		// Arranges the form actions on the right
		HBox actions = new HBox(10, cancel, save);
		actions.setAlignment(Pos.CENTER_RIGHT);
		grid.add(actions, 1, 7);

		// Closes the form without saving an Experience
		cancel.setOnAction((_) -> experienceStage.close());

		// Sends the entered values to the Controller
		save.setOnAction((_) -> ControllerExperience.handleCreateExperience(
				experienceStage,
				user,
				lessonId.getText(),
				time.getText(),
				whatWasDone.getText(),
				howItWasDone.getText(),
				teamEffort.getText(),
				onCreated));

		// Makes the complete form scrollable and displays it
		ScrollPane formScroll = new ScrollPane(grid);
		formScroll.setFitToWidth(true);
		formScroll.setStyle(
				"-fx-background-color: " + PAGE_BACKGROUND + ";"
				+ "-fx-background: " + PAGE_BACKGROUND + ";"
				+ "-fx-border-color: transparent;");
		experienceStage.setScene(new Scene(formScroll, 690, 500));
		experienceStage.show();
	}

	/******
	 *
	 * <p> Method: addFormRow() </p>
	 *
	 * <p> Description: Adds a consistently styled label and input control to one
	 * row of the Add Experience form. </p>
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
		Label label = new Label(labelText);
		label.setStyle(
				"-fx-font-family: 'Helvetica';"
				+ "-fx-font-size: 12;"
				+ "-fx-font-weight: bold;"
				+ "-fx-text-fill: #65736B;");
		grid.add(label, 0, row);
		grid.add(field, 1, row);
	}

	/******
	 *
	 * <p> Method: createTextArea() </p>
	 *
	 * <p> Description: Creates a wrapped multiline Add Experience field. </p>
	 *
	 * @param prompt instruction displayed while the field is empty
	 * @param rows preferred number of visible rows
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
	 * <p> Description: Applies the Add Experience style to a text field. </p>
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
	 * <p> Description: Creates the uppercase Experience section heading. </p>
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
	 * <p> Description: Applies the primary sage style to an Experience action. </p>
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
	 * <p> Description: Applies the secondary style to an Experience action. </p>
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
	 * the Add Experience form. </p>
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
		Stage messageStage = new Stage();
		messageStage.initModality(Modality.WINDOW_MODAL);
		if (owner != null) {
			messageStage.initOwner(owner);
		}
		messageStage.setTitle(title);

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

		Button close = new Button("Close");
		stylePrimaryButton(close);
		close.setDefaultButton(true);
		close.setOnAction((_) -> messageStage.close());

		HBox actions = new HBox(close);
		actions.setAlignment(Pos.CENTER_RIGHT);
		VBox content = new VBox(14, heading, details, actions);
		content.setPadding(new Insets(24));
		content.setStyle("-fx-background-color: " + PAGE_BACKGROUND + ";");
		messageStage.setScene(new Scene(content, 460, 185));
		messageStage.setResizable(false);
		messageStage.showAndWait();
	}
}
