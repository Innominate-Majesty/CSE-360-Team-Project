package guiLessonDetails;

import entityClasses.Experience;
import entityClasses.ExperiencesList;
import entityClasses.LessonsLearned;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/*******
 *
 * <p> Title: ViewLessonDetails Class. </p>
 *
 * <p> Description: The JavaFX-based Lesson Details View. This class displays the
 * selected Lesson Details and every associated Experience and provides Update,
 * Delete, and Close actions. </p>
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

public class ViewLessonDetails {

	// Defines the page background color
	private static final String PAGE_BACKGROUND = "#F1F4F0";

	// Defines the white surface color
	private static final String SURFACE_COLOR = "#FFFFFF";

	// Defines the primary text color
	private static final String PRIMARY_TEXT = "#203029";

	// Defines the secondary text color
	private static final String SECONDARY_TEXT = "#65736B";

	// Defines the primary sage action color
	private static final String ACCENT_COLOR = "#405D52";

	// Defines the soft sage hover color
	private static final String HOVER_BACKGROUND = "#E4ECE7";

	// Defines the form border color
	private static final String BORDER_COLOR = "#CAD7D0";

	/******
	 *
	 * <p> Constructor: ViewLessonDetails() </p>
	 *
	 * <p> Description: Creates the Lesson Details View. The display operation is
	 * static, so the application does not need to instantiate this class. </p>
	 *
	 */
	public ViewLessonDetails() {
		// Default constructor
	}

	/******
	 *
	 * <p> Method: displayLessonDetails() </p>
	 *
	 * <p> Description: Displays the selected lesson and its Experience history in
	 * a scrollable popup using the Contributor sage theme. </p>
	 *
	 * @param owner window that owns the Lesson Details popup
	 * @param lesson lesson whose information is displayed
	 * @param experiences Experiences associated with the lesson
	 * @param onUpdate operation used to open the Update Lesson form
	 * @param onDeleted operation used to refresh the calling page after deletion
	 *
	 */
	protected static void displayLessonDetails(
			Stage owner,
			LessonsLearned lesson,
			ExperiencesList experiences,
			Runnable onUpdate,
			Runnable onDeleted) {

		// Creates and configures the Lesson Details window
		Stage detailStage = new Stage();
		detailStage.initModality(Modality.WINDOW_MODAL);
		if (owner != null) {
			detailStage.initOwner(owner);
		}
		detailStage.setTitle("Lesson Details");

		// Creates and styles the lesson-title heading
		Label heading = new Label(lesson.getTitle());
		heading.setWrapText(true);
		heading.setMaxWidth(Double.MAX_VALUE);
		heading.setStyle(
				"-fx-font-family: 'Helvetica';"
				+ "-fx-font-size: 22;"
				+ "-fx-font-weight: bold;"
				+ "-fx-text-fill: " + PRIMARY_TEXT + ";");

		// Creates the Lesson Details section
		VBox lessonDetails = new VBox(
				11,
				createDetailLabel("Lesson ID", String.valueOf(lesson.getLessonId())),
				createDetailLabel("Category", lesson.getCategory()),
				createDetailLabel("Author", lesson.getAuthor()),
				createDetailLabel("Problem Description", lesson.getProblemDescription()),
				createDetailLabel("Solution", lesson.getSolution()));

		// Creates the complete Experience-history section
		VBox experienceHistory = createExperienceHistory(experiences);

		// Creates and styles the Update action
		Button update = new Button("Update");
		styleSecondaryButton(update);

		// Opens the Update feature supplied by the calling page
		update.setOnAction((_) -> {
			detailStage.close();
			if (onUpdate != null) {
				onUpdate.run();
			}
		});

		// Creates and styles the Delete action
		Button delete = new Button("Delete");
		styleDeleteButton(delete);

		// Opens the deletion confirmation popup
		delete.setOnAction((_) -> showDeleteConfirmation(
				detailStage,
				lesson,
				onDeleted));

		// Allows the heading to use the remaining header width
		HBox.setHgrow(heading, Priority.ALWAYS);

		// Places Update and Delete in the upper-right corner
		HBox header = new HBox(10, heading, update, delete);
		header.setAlignment(Pos.TOP_RIGHT);

		// Creates and styles the Close action
		Button close = new Button("Close");
		stylePrimaryButton(close);
		close.setDefaultButton(true);
		close.setOnAction((_) -> detailStage.close());

		// Places Close in its own lower-right row
		HBox closeAction = new HBox(close);
		closeAction.setAlignment(Pos.CENTER_RIGHT);

		// Arranges the complete popup content
		VBox content = new VBox(
				16,
				header,
				lessonDetails,
				experienceHistory,
				closeAction);
		content.setPadding(new Insets(24));
		content.setStyle("-fx-background-color: " + PAGE_BACKGROUND + ";");

		// Makes long details and Experience histories scrollable
		ScrollPane scroll = new ScrollPane(content);
		scroll.setFitToWidth(true);
		scroll.setStyle(
				"-fx-background: " + PAGE_BACKGROUND + ";"
				+ "-fx-background-color: " + PAGE_BACKGROUND + ";");

		// Displays the Lesson Details popup
		detailStage.setScene(new Scene(scroll, 570, 500));
		detailStage.setResizable(false);
		detailStage.showAndWait();
	}

	/******
	 *
	 * <p> Method: createExperienceHistory() </p>
	 *
	 * <p> Description: Creates the Experiences section and displays each associated
	 * Experience in its own card. </p>
	 *
	 * @param experiences collection of Experiences associated with the lesson
	 * @return vertically arranged Experience heading and cards
	 *
	 */
	private static VBox createExperienceHistory(ExperiencesList experiences) {
		Label heading = new Label("EXPERIENCES");
		heading.setStyle(
				"-fx-font-family: 'Helvetica';"
				+ "-fx-font-size: 12;"
				+ "-fx-font-weight: bold;"
				+ "-fx-text-fill: " + ACCENT_COLOR + ";");

		VBox cards = new VBox(12);
		if (experiences == null || experiences.isEmpty()) {
			Label emptyMessage = new Label("No Experiences have been added to this lesson");
			emptyMessage.setStyle(
					"-fx-font-family: 'Helvetica';"
					+ "-fx-font-size: 12;"
					+ "-fx-text-fill: " + SECONDARY_TEXT + ";");
			cards.getChildren().add(emptyMessage);
		} else {
			for (Experience experience : experiences.getExperiences()) {
				Label experienceHeading = new Label(
						"Experience " + experience.getExperienceId());
				experienceHeading.setStyle(
						"-fx-font-family: 'Helvetica';"
						+ "-fx-font-size: 14;"
						+ "-fx-font-weight: bold;"
						+ "-fx-text-fill: " + PRIMARY_TEXT + ";");

				VBox card = new VBox(
						9,
						experienceHeading,
						createDetailLabel("Author", experience.getAuthor()),
						createDetailLabel("Time Spent", experience.getTime() + " hours"),
						createDetailLabel("What Was Done", experience.getWhat()),
						createDetailLabel("How It Was Done", experience.getHow()),
						createDetailLabel("Team Effort", experience.getEffort()));
				card.setPadding(new Insets(16));
				card.setStyle(
						"-fx-background-color: " + SURFACE_COLOR + ";"
						+ "-fx-border-color: " + BORDER_COLOR + ";"
						+ "-fx-border-width: 1;"
						+ "-fx-border-radius: 10;"
						+ "-fx-background-radius: 10;");
				cards.getChildren().add(card);
			}
		}

		return new VBox(10, heading, cards);
	}

	/******
	 *
	 * <p> Method: createDetailLabel() </p>
	 *
	 * <p> Description: Creates one labeled and wrapped information block for the
	 * Lesson Details popup. </p>
	 *
	 * @param name field name displayed above the value
	 * @param value information displayed beneath the field name
	 * @return vertically arranged field name and value
	 *
	 */
	private static VBox createDetailLabel(String name, String value) {
		Label nameLabel = new Label(name.toUpperCase());
		nameLabel.setStyle(
				"-fx-font-family: 'Helvetica';"
				+ "-fx-font-size: 10;"
				+ "-fx-font-weight: bold;"
				+ "-fx-text-fill: " + ACCENT_COLOR + ";");

		Label valueLabel = new Label(value == null || value.isBlank() ? "Not provided" : value);
		valueLabel.setWrapText(true);
		valueLabel.setMaxWidth(Double.MAX_VALUE);
		valueLabel.setStyle(
				"-fx-font-family: 'Helvetica';"
				+ "-fx-font-size: 12;"
				+ "-fx-text-fill: " + PRIMARY_TEXT + ";");
		return new VBox(3, nameLabel, valueLabel);
	}

	/******
	 *
	 * <p> Method: showDeleteConfirmation() </p>
	 *
	 * <p> Description: Asks the contributor to confirm permanent lesson deletion
	 * before sending the action to the Controller. </p>
	 *
	 * @param detailStage Lesson Details window associated with the deletion
	 * @param lesson lesson selected for deletion
	 * @param onDeleted operation used to refresh the calling page
	 *
	 */
	private static void showDeleteConfirmation(
			Stage detailStage,
			LessonsLearned lesson,
			Runnable onDeleted) {
		Stage confirmation = new Stage();
		confirmation.initModality(Modality.WINDOW_MODAL);
		confirmation.initOwner(detailStage);
		confirmation.setTitle("Confirm Deletion");

		Label heading = new Label("Delete this lesson?");
		Label details = new Label(
				"This permanently deletes \"" + lesson.getTitle()
				+ "\" and all of its Experiences.");
		heading.setStyle(
				"-fx-font-family: 'Helvetica';"
				+ "-fx-font-size: 18;"
				+ "-fx-font-weight: bold;"
				+ "-fx-text-fill: " + PRIMARY_TEXT + ";");
		details.setWrapText(true);
		details.setStyle(
				"-fx-font-family: 'Helvetica';"
				+ "-fx-font-size: 12;"
				+ "-fx-text-fill: " + SECONDARY_TEXT + ";");

		Button cancel = new Button("Cancel");
		Button delete = new Button("Delete");
		styleSecondaryButton(cancel);
		styleDeleteButton(delete);
		cancel.setOnAction((_) -> confirmation.close());
		delete.setOnAction((_) -> {
			confirmation.close();
			ControllerLessonDetails.handleDeleteLesson(
					detailStage,
					lesson,
					onDeleted);
		});

		HBox actions = new HBox(10, cancel, delete);
		actions.setAlignment(Pos.CENTER_RIGHT);
		VBox content = new VBox(14, heading, details, actions);
		content.setPadding(new Insets(24));
		content.setStyle("-fx-background-color: " + PAGE_BACKGROUND + ";");
		confirmation.setScene(new Scene(content, 450, 185));
		confirmation.setResizable(false);
		confirmation.showAndWait();
	}

	/******
	 *
	 * <p> Method: stylePrimaryButton() </p>
	 *
	 * <p> Description: Applies the primary sage style to a Lesson Details action. </p>
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
	 * <p> Description: Applies the bordered sage-hover style to a Lesson Details
	 * action. </p>
	 *
	 * @param button secondary action button being styled
	 *
	 */
	private static void styleSecondaryButton(Button button) {
		String base =
				"-fx-background-color: " + SURFACE_COLOR + ";"
				+ "-fx-text-fill: " + PRIMARY_TEXT + ";"
				+ "-fx-border-color: " + BORDER_COLOR + ";"
				+ "-fx-border-width: 1;"
				+ "-fx-border-radius: 8;"
				+ "-fx-background-radius: 8;"
				+ "-fx-padding: 8 14 8 14;"
				+ "-fx-cursor: hand;";
		String hover =
				"-fx-background-color: " + HOVER_BACKGROUND + ";"
				+ "-fx-text-fill: " + PRIMARY_TEXT + ";"
				+ "-fx-border-color: " + ACCENT_COLOR + ";"
				+ "-fx-border-width: 1;"
				+ "-fx-border-radius: 8;"
				+ "-fx-background-radius: 8;"
				+ "-fx-padding: 8 14 8 14;"
				+ "-fx-cursor: hand;";
		button.setStyle(base);
		button.setOnMouseEntered((_) -> button.setStyle(hover));
		button.setOnMouseExited((_) -> button.setStyle(base));
	}

	/******
	 *
	 * <p> Method: styleDeleteButton() </p>
	 *
	 * <p> Description: Applies the destructive red style to a lesson-deletion
	 * action. </p>
	 *
	 * @param button destructive action button being styled
	 *
	 */
	private static void styleDeleteButton(Button button) {
		button.setStyle(
				"-fx-background-color: white;"
				+ "-fx-text-fill: #B44747;"
				+ "-fx-border-color: #E7B9B9;"
				+ "-fx-border-width: 1;"
				+ "-fx-border-radius: 8;"
				+ "-fx-background-radius: 8;"
				+ "-fx-padding: 8 14 8 14;"
				+ "-fx-cursor: hand;");
	}

	/******
	 *
	 * <p> Method: showMessage() </p>
	 *
	 * <p> Description: Displays a styled success or error message associated with
	 * the Lesson Details popup. </p>
	 *
	 * @param owner window that owns the message
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
				+ "-fx-text-fill: " + SECONDARY_TEXT + ";");

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
