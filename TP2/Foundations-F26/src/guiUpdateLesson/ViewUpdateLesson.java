package guiUpdateLesson;

import entityClasses.Experience;
import entityClasses.ExperiencesList;
import entityClasses.LessonsLearned;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
 * <p> Title: ViewUpdateLesson Class. </p>
 *
 * <p> Description: The JavaFX-based Update Lesson View. This class displays the
 * existing Lesson Details, allows one associated Experience to be selected, and
 * forwards the updated values to the Controller. </p>
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

public class ViewUpdateLesson {

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
	 * <p> Constructor: ViewUpdateLesson() </p>
	 *
	 * <p> Description: Creates the Update Lesson View. The display operation is
	 * static, so the application does not need to instantiate this class. </p>
	 *
	 */
	public ViewUpdateLesson() {
		// Default constructor
	}

	/******
	 *
	 * <p> Method: displayUpdateLesson() </p>
	 *
	 * <p> Description: Displays a populated Update Lesson form using the Contributor
	 * sage theme and connects its controls to the Update Lesson Controller. </p>
	 *
	 * @param owner window that owns the Update Lesson form
	 * @param lesson lesson whose details are being updated
	 * @param experiences Experiences associated with the lesson
	 * @param onUpdated operation used to refresh the calling page
	 * @param onCancel operation used to return to the previous page
	 *
	 */
	protected static void displayUpdateLesson(
			Stage owner,
			LessonsLearned lesson,
			ExperiencesList experiences,
			Runnable onUpdated,
			Runnable onCancel) {

		// Creates and configures the Update Lesson window
		Stage updateStage = new Stage();
		updateStage.initModality(Modality.WINDOW_MODAL);
		if (owner != null) {
			updateStage.initOwner(owner);
		}
		updateStage.setTitle("Update Lesson #" + lesson.getLessonId());

		// Creates the two-column form layout
		GridPane grid = new GridPane();
		grid.setHgap(14);
		grid.setVgap(10);
		grid.setPadding(new Insets(24));
		grid.setStyle("-fx-background-color: " + PAGE_BACKGROUND + ";");

		// Creates and styles the main heading
		Label heading = new Label("Update Lesson");
		heading.setStyle(
				"-fx-font-family: 'Helvetica';"
				+ "-fx-font-size: 24;"
				+ "-fx-font-weight: bold;"
				+ "-fx-text-fill: " + PRIMARY_TEXT + ";");

		// Creates the form-section headings
		Label lessonHeading = createSectionLabel("LESSON DETAILS");
		Label experienceHeading = createSectionLabel("EXPERIENCE");

		// Creates and populates the Lesson Details controls
		TextField title = new TextField(lesson.getTitle());
		TextField category = new TextField(lesson.getCategory());
		TextField author = new TextField(lesson.getAuthor());
		TextArea problem = createTextArea("Describe the problem or situation", 2);
		TextArea solution = createTextArea("Describe the solution or lesson learned", 2);
		problem.setText(lesson.getProblemDescription());
		solution.setText(lesson.getSolution());
		author.setEditable(false);

		// Creates the Experience controls
		ComboBox<Integer> experienceId = new ComboBox<>();
		TextField time = new TextField();
		TextArea whatWasDone = createTextArea("What was done?", 2);
		TextArea howItWasDone = createTextArea("How was it done?", 2);
		TextArea teamEffort = createTextArea("How did the team contribute?", 2);
		experienceId.setPromptText("Select an Experience");
		time.setPromptText("Hours spent");

		// Styles the Lesson and Experience controls
		styleTextField(title);
		styleTextField(category);
		styleTextField(author);
		styleTextField(time);
		styleComboBox(experienceId);
		author.setStyle(
				author.getStyle()
				+ "-fx-opacity: 0.75;"
				+ "-fx-background-color: #E8EDE9;");

		// Applies the existing Lesson locking behavior
		if (lesson.isTitleLocked()) {
			lockField(title);
		}
		if (lesson.isCategoryLocked()) {
			lockField(category);
		}
		if (lesson.isProblemDescriptionLocked()) {
			lockField(problem);
		}
		if (lesson.isSolutionLocked()) {
			lockField(solution);
		}

		// Adds every associated Experience ID to the selector
		if (experiences != null) {
			for (Experience experience : experiences.getExperiences()) {
				experienceId.getItems().add(experience.getExperienceId());
			}
		}

		// Loads the selected Experience into the form
		experienceId.setOnAction((_) -> populateExperienceFields(
				experienceId.getValue(),
				lesson.getLessonId(),
				time,
				whatWasDone,
				howItWasDone,
				teamEffort));

		// Selects the first Experience or disables unavailable controls
		if (!experienceId.getItems().isEmpty()) {
			experienceId.getSelectionModel().selectFirst();
			populateExperienceFields(
					experienceId.getValue(),
					lesson.getLessonId(),
					time,
					whatWasDone,
					howItWasDone,
					teamEffort);
		} else {
			experienceId.setPromptText("No Experiences available");
			experienceId.setDisable(true);
			time.setDisable(true);
			whatWasDone.setDisable(true);
			howItWasDone.setDisable(true);
			teamEffort.setDisable(true);
		}

		// Adds the headings and controls to the form
		grid.add(heading, 0, 0, 2, 1);
		grid.add(lessonHeading, 0, 1, 2, 1);
		addFormRow(grid, "Title", title, 2);
		addFormRow(grid, "Category", category, 3);
		addFormRow(grid, "Author", author, 4);
		addFormRow(grid, "Problem Description", problem, 5);
		addFormRow(grid, "Solution", solution, 6);
		grid.add(experienceHeading, 0, 7, 2, 1);
		addFormRow(grid, "Experience ID", experienceId, 8);
		addFormRow(grid, "Time Spent (hours)", time, 9);
		addFormRow(grid, "What Was Done", whatWasDone, 10);
		addFormRow(grid, "How It Was Done", howItWasDone, 11);
		addFormRow(grid, "Team Effort", teamEffort, 12);

		// Creates and styles the form actions
		Button cancel = new Button("Cancel");
		Button update = new Button("Update Lesson");
		styleSecondaryButton(cancel);
		stylePrimaryButton(update);

		// Arranges the form actions on the right
		HBox actions = new HBox(10, cancel, update);
		actions.setAlignment(Pos.CENTER_RIGHT);
		grid.add(actions, 1, 13);

		// Closes the form and returns to the previous page
		cancel.setOnAction((_) -> {
			updateStage.close();
			if (onCancel != null) {
				onCancel.run();
			}
		});

		// Sends the updated values to the Controller
		update.setOnAction((_) -> ControllerUpdateLesson.handleUpdateLesson(
				updateStage,
				lesson,
				title.getText(),
				category.getText(),
				problem.getText(),
				solution.getText(),
				experienceId.getValue(),
				time.getText(),
				whatWasDone.getText(),
				howItWasDone.getText(),
				teamEffort.getText(),
				onUpdated));

		// Makes the complete form scrollable and displays it
		ScrollPane formScroll = new ScrollPane(grid);
		formScroll.setFitToWidth(true);
		formScroll.setStyle(
				"-fx-background-color: " + PAGE_BACKGROUND + ";"
				+ "-fx-background: " + PAGE_BACKGROUND + ";"
				+ "-fx-border-color: transparent;");
		updateStage.setScene(new Scene(formScroll, 690, 650));
		updateStage.show();
	}

	/******
	 *
	 * <p> Method: populateExperienceFields() </p>
	 *
	 * <p> Description: Loads the selected Experience into the Experience fields. </p>
	 *
	 * @param experienceId selected Experience ID
	 * @param lessonId lesson that must own the selected Experience
	 * @param time Time Spent field
	 * @param whatWasDone What Was Done field
	 * @param howItWasDone How It Was Done field
	 * @param teamEffort Team Effort field
	 *
	 */
	private static void populateExperienceFields(
			Integer experienceId,
			int lessonId,
			TextField time,
			TextArea whatWasDone,
			TextArea howItWasDone,
			TextArea teamEffort) {
		if (experienceId == null) {
			return;
		}
		Experience experience = ControllerUpdateLesson.getExperienceById(
				experienceId,
				lessonId);
		if (experience == null) {
			return;
		}
		time.setText(String.valueOf(experience.getTime()));
		whatWasDone.setText(experience.getWhat());
		howItWasDone.setText(experience.getHow());
		teamEffort.setText(experience.getEffort());
	}

	/******
	 *
	 * <p> Method: addFormRow() </p>
	 *
	 * <p> Description: Adds a consistently styled label and control to one row of
	 * the Update Lesson form. </p>
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
	 * <p> Description: Creates a wrapped multiline Update Lesson field. </p>
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
		area.setStyle(fieldStyle(true));
		return area;
	}

	/******
	 *
	 * <p> Method: styleTextField() </p>
	 *
	 * <p> Description: Applies the Update Lesson style to a text field. </p>
	 *
	 * @param field text field being styled
	 *
	 */
	private static void styleTextField(TextField field) {
		field.setPrefWidth(455);
		field.setStyle(fieldStyle(false));
	}

	/******
	 *
	 * <p> Method: styleComboBox() </p>
	 *
	 * <p> Description: Applies the Update Lesson style to an Experience selector. </p>
	 *
	 * @param comboBox Experience selector being styled
	 *
	 */
	private static void styleComboBox(ComboBox<Integer> comboBox) {
		comboBox.setPrefWidth(455);
		comboBox.setStyle(
				"-fx-background-color: white;"
				+ "-fx-border-color: " + BORDER_COLOR + ";"
				+ "-fx-border-width: 1;"
				+ "-fx-border-radius: 8;"
				+ "-fx-background-radius: 8;"
				+ "-fx-font-family: 'Helvetica';"
				+ "-fx-font-size: 12;"
				+ "-fx-focus-color: transparent;"
				+ "-fx-faint-focus-color: transparent;");
	}

	/******
	 *
	 * <p> Method: fieldStyle() </p>
	 *
	 * <p> Description: Builds the shared style used by Update Lesson text fields
	 * and text areas. </p>
	 *
	 * @param textArea true when the control is a multiline text area
	 * @return JavaFX style used by the requested control
	 *
	 */
	private static String fieldStyle(boolean textArea) {
		return (textArea ? "-fx-control-inner-background: white;" : "")
				+ "-fx-background-color: white;"
				+ "-fx-border-color: " + BORDER_COLOR + ";"
				+ "-fx-border-width: 1;"
				+ "-fx-border-radius: 8;"
				+ "-fx-background-radius: 8;"
				+ "-fx-padding: 8 10 8 10;"
				+ "-fx-font-family: 'Helvetica';"
				+ "-fx-font-size: 12;"
				+ "-fx-focus-color: transparent;"
				+ "-fx-faint-focus-color: transparent;";
	}

	/******
	 *
	 * <p> Method: createSectionLabel() </p>
	 *
	 * <p> Description: Creates an uppercase Update Lesson section heading. </p>
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
	 * <p> Method: lockField() </p>
	 *
	 * <p> Description: Disables a locked Lesson field while preserving the
	 * existing locking foundation for TP3. </p>
	 *
	 * @param field locked control
	 *
	 */
	private static void lockField(Control field) {
		field.setDisable(true);
		field.setStyle(
				field.getStyle()
				+ "-fx-opacity: 0.70;"
				+ "-fx-background-color: #E8EDE9;");
	}

	/******
	 *
	 * <p> Method: stylePrimaryButton() </p>
	 *
	 * <p> Description: Applies the primary sage style to an Update Lesson action. </p>
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
	 * <p> Description: Applies the secondary style to an Update Lesson action. </p>
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
	 * the Update Lesson form. </p>
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
		messageStage.setScene(new Scene(content, 430, 175));
		messageStage.setResizable(false);
		messageStage.showAndWait();
	}
}
