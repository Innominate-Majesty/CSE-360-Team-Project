package guiRole1;

import database.Database;
import entityClasses.LessonsLearned;
import entityClasses.LessonsLearnedList;
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
 * <p> Title: ControllerRole1Home Class. </p>
 * 
 * <p> Description: The JavaFX-based Role 1 Home Page controller. This class provides
 * the controller actions based on the user's interaction with the JavaFX GUI widgets
 * defined by the View class.
 * 
 * This controller supports contributor lesson creation, searching, viewing, updating,
 * deleting, and experience management.
 * 
 * The class assumes that its protected methods are invoked by the View or Model
 * classes within this package. </p>
 * 
 * <p> Copyright: Lynn Robert Carter © 2025 </p>
 * 
 * @author Lynn Robert Carter
 * 
 * @version 1.00		2025-08-17 Initial version
 * @version 1.01		2025-09-16 Update Javadoc documentation
 * 
 */

public class ControllerRole1Home {
	
	//Variables
	private static Database theDatabase = applicationMain.FoundationsMain.database;

	/*-*******************************************************************************************

	User Interface Actions for this page
	
	This controller is not a class that gets instantiated.  Rather, it is a collection of protected
	static methods that can be called by the View (which is a singleton instantiated object) and 
	the Model is often just a stub, or will be a singleton instantiated object.
	
	 */

	/**
	 * Default constructor is not used.
	 */
	public ControllerRole1Home() {
	}

	/**********
	 * <p> Method: performUpdate() </p>
	 * 
	 * <p> Description: This method directs the user to the User Update Page so the user can change
	 * the user account attributes. </p>
	 * 
	 */
	protected static void performUpdate () {
		guiUserUpdate.ViewUserUpdate.displayUserUpdate(ViewRole1Home.theStage, ViewRole1Home.theUser);
	}	

	/**********
	 * <p> Method: performLogout() </p>
	 * 
	 * <p> Description: This method logs out the current user and proceeds to the normal login
	 * page where existing users can log in or potential new users with a invitation code can
	 * start the process of setting up an account. </p>
	 * 
	 */
	protected static void performLogout() {
		guiUserLogin.ViewUserLogin.displayUserLogin(ViewRole1Home.theStage);
	}
	
	/**********
	 * <p> Method: performQuit() </p>
	 * 
	 * <p> Description: This method terminates the execution of the program.  It leaves the
	 * database in a state where the normal login page will be displayed when the application is
	 * restarted.</p>
	 * 
	 */	
	protected static void performQuit() {
		System.exit(0);
	}
	
	/**********
	 * <p> Method: handleCreateLesson() </p>
	 * 
	 * <p> Description: Opens the Create Lesson form where the contributor can enter
	 * the information required to create a new lesson. </p>
	 */

	protected static void handleCreateLesson() {

		// Opens the complete Create Lesson form
		openCreateLessonForm();
	}

	/**********
	 * 
	 * <p> Method: handleReadAllLessons() </p>
	 * 
	 * <p> Description: Retrieves all lessons created by the active contributor. The
	 * lessons are displayed as clickable title cards, or an empty-result message is
	 * displayed when the contributor has not created any lessons. </p>
	 * 
	 */
	
	protected static void handleReadAllLessons() {

		// Retrieves the active contributor's username
		String author = ViewRole1Home.theUser != null
				? ViewRole1Home.theUser.getUserName()
				: "Unknown User";

		// Retrieves every lesson belonging to the contributor
		LessonsLearnedList list =
				theDatabase.getAllLessonsForContributor(author);

		// Checks whether the contributor has any lessons
		if (list.isEmpty()) {

			// Hides the empty lesson-card container
			ViewRole1Home.scroll_MyLessons.setVisible(false);
			ViewRole1Home.scroll_MyLessons.setManaged(false);

			// Displays the text-results area
			ViewRole1Home.textarea_Display.setVisible(true);
			ViewRole1Home.textarea_Display.setManaged(true);

			// Hides Back because this is the default view
			ViewRole1Home.button_BackToLessons.setVisible(false);

			// Explains that the contributor has no lessons
			ViewRole1Home.textarea_Display.setText(
					"No lessons found for contributor " + author);
		} else {
			// Displays the contributor's lessons as clickable title cards
			ViewRole1Home.displayMyLessons(list);
		}
	}

	/**********
	 * <p> Method: handleReadSingleLesson() </p>
	 * 
	 * <p> Description: Examines a specific lesson in detail. Displays core information, experience
	 * data, and indicates which fields are locked and unavailable for editing. </p>
	 */
	protected static void handleReadSingleLesson() {
		String idStr = ViewRole1Home.textfield_LessonId.getText().trim();
		String currentUser = (ViewRole1Home.theUser != null) ? ViewRole1Home.theUser.getUserName() : "";

		try {
			int id = Integer.parseInt(idStr);
			LessonsLearned lesson = theDatabase.getLessonById(id);

			// Check existence AND enforce ownership
			if (lesson == null || !lesson.getAuthor().equals(currentUser)) {
				ViewRole1Home.textarea_Display.setText("No lesson record exists with ID " + id + ".");
				return;
			}

			// Populate UI with authorized lesson details
			ViewRole1Home.textfield_Title.setText(lesson.getTitle());
			ViewRole1Home.textfield_Category.setText(lesson.getCategory());
			ViewRole1Home.textarea_Display.setText("Found Lesson:\n" 
					+ lesson.toString()
					+ "\n  - Author: " + lesson.getAuthor()
					+ "\n  - Problem Description: " + lesson.getProblemDescription()
					+ "\n  - Solution: " + lesson.getSolution()
					+ "\n  - What Was Done: " + lesson.getWhatWasDone()
					+ "\n  - How It Was Done: " + lesson.getHowItWasDone()
					+ "\n  - Team Effort: " + lesson.getTeamEffort());

		} catch (NumberFormatException ex) {
			ViewRole1Home.textarea_Display.setText("No lesson record exists with ID " + idStr + ".");
		}
	}

	/**********
	 * <p> Method: handleReadFilteredSubset() </p>
	 * 
	 * <p> Description: Reads the entered filter value and retrieves lessons matching
	 * the category or keyword for the active contributor. An error message is displayed
	 * when the search value is blank. </p>
	 * 
	 */

	protected static void handleReadFilteredSubset() {
		String filter = ViewRole1Home.textfield_FilterCategory.getText();
		if (filter == null || filter.trim().isEmpty()) {
			ViewRole1Home.textarea_Display.setText("Search query cannot be blank. Enter a valid keyword or category.");
			return;
		}

		String author = (ViewRole1Home.theUser != null) ? ViewRole1Home.theUser.getUserName() : "";
		LessonsLearnedList subset = theDatabase.getLessonsByFilter(filter.trim(), author);
		ViewRole1Home.textarea_Display.setText("Lessons List:\n" 
				+ subset.toString());
	}

	/**********
	 * 
	 * <p> Method: handleOpenUpdateWindow() </p>
	 * 
	 * <p> Description: Validates the selected lesson and opens a styled form that
	 * allows the contributor to update the Title, Category, Problem Description,
	 * Solution, and experience information. The Author remains tied to the original
	 * creator, and administrator-locked fields remain disabled. Update Lesson saves
	 * the permitted changes, while Cancel returns to the Lesson Details window. </p>
	 * 
	 */

	protected static void handleOpenUpdateWindow() {

		// Reads the selected Lesson ID
		String idText = ViewRole1Home.textfield_LessonId.getText().trim();

		// Stores the parsed Lesson ID
		int id;

		// Validates the Lesson ID
		try {
			id = Integer.parseInt(idText);

		} catch (NumberFormatException exception) {
			ViewRole1Home.textarea_Display.setText(
					"Lesson with ID " + idText + " does not exist");
			return;
		}

		// Retrieves the selected lesson
		LessonsLearned existing =
				theDatabase.getLessonById(id);

		// Retrieves the active contributor's username
		String currentUser =
				ViewRole1Home.theUser != null
						? ViewRole1Home.theUser.getUserName()
						: "";

		// Verifies that the lesson exists and belongs to the contributor
		if (existing == null
				|| !existing.getAuthor().equals(currentUser)) {
			ViewRole1Home.textarea_Display.setText(
					"Lesson with ID " + id + " does not exist");
			return;
		}

		// Creates the Update Lesson window
		Stage updateStage = new Stage();

		// Keeps the form associated with the contributor page
		updateStage.initModality(Modality.WINDOW_MODAL);

		// Sets the contributor page as the form owner
		updateStage.initOwner(ViewRole1Home.theStage);

		// Sets the form-window title
		updateStage.setTitle("Update Lesson #" + id);

		// Creates the form layout
		GridPane grid = new GridPane();

		// Adds horizontal spacing between columns
		grid.setHgap(14);

		// Adds vertical spacing between rows
		grid.setVgap(10);

		// Adds space around the form
		grid.setPadding(new Insets(24));

		// Applies the sage page background
		grid.setStyle("-fx-background-color: #F1F4F0;");

		// Creates the main form heading
		Label heading = new Label("Update Lesson");

		// Applies the main heading style
		heading.setStyle(
				"-fx-font-family: 'Helvetica';"
				+ "-fx-font-size: 24;"
				+ "-fx-font-weight: bold;"
				+ "-fx-text-fill: #203029;");

		// Creates the lesson-details section heading
		Label coreHeading = createFormSectionLabel("LESSON DETAILS");

		// Creates the experience section heading
		Label experienceHeading = createFormSectionLabel("EXPERIENCE");

		// Creates the Title field with its current value
		TextField txtTitle = new TextField(existing.getTitle());

		// Creates the Category field with its current value
		TextField txtCategory = new TextField(existing.getCategory());

		// Creates the Author field with its current value
		TextField txtAuthor = new TextField(existing.getAuthor());

		// Prevents the contributor from changing the Author
		txtAuthor.setEditable(false);

		// Creates the Problem Description field
		TextArea txtProblem = createFormTextArea("Describe the problem or situation", 2);

		// Places the current problem description into the field
		txtProblem.setText(existing.getProblemDescription());

		// Creates the Solution field
		TextArea txtSolution = createFormTextArea("Describe the solution or lesson learned", 2);

		// Places the current solution into the field
		txtSolution.setText(existing.getSolution());

		// Creates the Time Spent field with its current value
		TextField txtTime = new TextField(String.valueOf(existing.getTimeSpentHours()));

		// Creates the What Was Done field
		TextArea txtWhatDone = createFormTextArea("What was done?", 2);

		// Places the current What Was Done value into the field
		txtWhatDone.setText(existing.getWhatWasDone());

		// Creates the How It Was Done field
		TextArea txtHowDone = createFormTextArea("How was it done?", 2);

		// Places the current How It Was Done value into the field
		txtHowDone.setText(existing.getHowItWasDone());

		// Creates the Team Effort field
		TextArea txtTeamEffort = createFormTextArea("How did the team contribute?", 2);

		// Places the current Team Effort value into the field
		txtTeamEffort.setText(existing.getTeamEffort());

		// Styles the Title field
		styleFormField(txtTitle);

		// Styles the Category field
		styleFormField(txtCategory);

		// Styles the Author field
		styleFormField(txtAuthor);

		// Styles the Time Spent field
		styleFormField(txtTime);

		// Gives the locked Author field a distinct appearance
		txtAuthor.setStyle(
				txtAuthor.getStyle()
				+ "-fx-opacity: 0.75;"
				+ "-fx-background-color: #E8EDE9;");

		// Locks Title when required by the lesson
		if (existing.isTitleLocked()) {
			lockFormField(txtTitle);
		}

		// Locks Category when required by the lesson
		if (existing.isCategoryLocked()) {
			lockFormField(txtCategory);
		}

		// Locks Problem Description when required by the lesson
		if (existing.isProblemDescriptionLocked()) {
			lockFormField(txtProblem);
		}

		// Locks Solution when required by the lesson
		if (existing.isSolutionLocked()) {
			lockFormField(txtSolution);
		}

		// Places the main heading across both columns
		grid.add(heading, 0, 0, 2, 1);

		// Places the lesson-details heading
		grid.add(coreHeading, 0, 1, 2, 1);

		// Adds the lesson-detail fields
		addFormRow(grid, "Title", txtTitle, 2);
		addFormRow(grid, "Category", txtCategory, 3);
		addFormRow(grid, "Author", txtAuthor, 4);
		addFormRow(grid, "Problem Description", txtProblem, 5);
		addFormRow(grid, "Solution", txtSolution, 6);

		// Places the experience heading
		grid.add(experienceHeading, 0, 7, 2, 1);

		// Adds the experience fields
		addFormRow(grid, "Time Spent (hours)", txtTime, 8);
		addFormRow(grid, "What Was Done", txtWhatDone, 9);
		addFormRow(grid, "How It Was Done", txtHowDone, 10);
		addFormRow(grid, "Team Effort", txtTeamEffort, 11);

		// Creates the Update Lesson button
		Button btnConfirm = new Button("Update Lesson");

		// Creates the Cancel button
		Button btnCancel = new Button("Cancel");

		// Styles Update Lesson as the primary action
		styleFormPrimaryButton(btnConfirm);

		// Styles Cancel as a secondary action
		styleFormSecondaryButton(btnCancel);

		// Places the form actions in one row
		HBox btnBox = new HBox(10, btnCancel, btnConfirm);

		// Aligns the form actions to the right
		btnBox.setAlignment(Pos.CENTER_RIGHT);

		// Adds the actions beneath the fields
		grid.add(btnBox, 1, 12);

		// Returns to the selected Lesson Details popup
		btnCancel.setOnAction((_) -> {
			updateStage.close();
			ViewRole1Home.showLessonDetails(existing);
		});

		// Validates and saves the updated lesson
		btnConfirm.setOnAction((_) -> {

			// Reads the updated required values
			String newTitle = txtTitle.getText().trim();
			String newCategory = txtCategory.getText().trim();
			String newProblem = txtProblem.getText().trim();
			String newSolution = txtSolution.getText().trim();

			// Stores the parsed Time Spent value
			double newTime;

			// Validates the Time Spent value
			try {
				newTime = Double.parseDouble(
						txtTime.getText().trim());
			} catch (NumberFormatException exception) {
				showCreateMessage(
						updateStage,
						"Unable to Update Lesson",
						"Time spent must be a non-negative number",
						true);
				return;
			}

			// Validates the updated Title length
			if (newTitle.length() < 5 || newTitle.length() > 100) {
				showCreateMessage(
						updateStage,
						"Unable to Update Lesson",
						"Title must be between 5 and 100 characters",
						true);
				return;
			}

			// Validates the required lesson information
			if (newCategory.isEmpty() || newProblem.isEmpty() || newSolution.isEmpty()) {
				showCreateMessage(
						updateStage,
						"Unable to Update Lesson",
						"Category, problem description, and solution are required",
						true);
				return;
			}

			// Rejects a negative Time Spent value
			if (newTime < 0) {
				showCreateMessage(
						updateStage,
						"Unable to Update Lesson",
						"Time spent must be a non-negative number",
						true);
				return;
			}

			// Updates the permitted lesson-detail values
			existing.setTitle(newTitle);
			existing.setCategory(newCategory);
			existing.setProblemDescription(newProblem);
			existing.setSolution(newSolution);

			// Updates the experience values
			existing.setTimeSpentHours(newTime);
			existing.setWhatWasDone(
					txtWhatDone.getText().trim());
			existing.setHowItWasDone(
					txtHowDone.getText().trim());
			existing.setTeamEffort(
					txtTeamEffort.getText().trim());

			// Attempts to save the updated lesson
			boolean updated = theDatabase.updateLesson(existing);

			// Checks whether the update succeeded
			if (updated) {

				// Stores the updated title for controller compatibility
				ViewRole1Home.textfield_Title.setText(newTitle);

				// Stores the updated category for controller compatibility
				ViewRole1Home.textfield_Category.setText(newCategory);

				// Refreshes the contributor's lesson cards
				handleReadAllLessons();

				// Displays the successful update message
				showCreateMessage(
						updateStage,
						"Lesson Updated",
						"The lesson was updated successfully",
						false);
			} else {
				// Displays the missing-lesson error
				showCreateMessage(
						updateStage,
						"Unable to Update Lesson",
						"Lesson with ID " + id + " does not exist",
						true);
				return;
			}

			// Closes the completed Update form
			updateStage.close();
		});

		// Makes the complete form scrollable
		ScrollPane formScroll = new ScrollPane(grid);

		// Makes the form fill the available width
		formScroll.setFitToWidth(true);

		// Applies the sage background to the scrollable form
		formScroll.setStyle(
				"-fx-background-color: #F1F4F0;"
				+ "-fx-background: #F1F4F0;"
				+ "-fx-border-color: transparent;");

		// Adds the form to its scene
		updateStage.setScene(new Scene(formScroll, 690, 610));

		// Displays the Update Lesson form
		updateStage.show();
	}
	
	/**********
	 * 
	 * <p> Method: handleOpenExperienceWindow() </p>
	 * 
	 * <p> Description: Opens a styled form that allows the contributor to select
	 * one of their lessons by Lesson ID and add or update its Time Spent, What Was
	 * Done, How It Was Done, and Team Effort information. The method validates the
	 * Lesson ID, verifies ownership, validates Time Spent, and saves the experience
	 * information to the database. </p>
	 * 
	 */

	protected static void handleOpenExperienceWindow() {

		// Creates the Add Experience window
		Stage experienceStage = new Stage();

		// Keeps the form associated with the contributor page
		experienceStage.initModality(Modality.WINDOW_MODAL);

		// Sets the contributor page as the form owner
		experienceStage.initOwner(ViewRole1Home.theStage);

		// Sets the form-window title
		experienceStage.setTitle("Add Experience");

		// Creates the form layout
		GridPane grid = new GridPane();

		// Adds horizontal spacing between columns
		grid.setHgap(14);

		// Adds vertical spacing between rows
		grid.setVgap(10);

		// Adds space around the form
		grid.setPadding(new Insets(24));

		// Applies the sage page background
		grid.setStyle("-fx-background-color: #F1F4F0;");

		// Creates the main form heading
		Label heading = new Label("Add Experience");

		// Applies the main heading style
		heading.setStyle(
				"-fx-font-family: 'Helvetica';"
				+ "-fx-font-size: 24;"
				+ "-fx-font-weight: bold;"
				+ "-fx-text-fill: #203029;");

		// Creates the experience section heading
		Label experienceHeading = createFormSectionLabel("EXPERIENCE");

		// Creates the Lesson ID field with the currently selected ID
		TextField txtLessonId = new TextField(ViewRole1Home.textfield_LessonId.getText().trim());

		// Explains the expected Lesson ID
		txtLessonId.setPromptText("Enter the lesson ID");

		// Creates the Time Spent field
		TextField txtTime = new TextField("0");

		// Explains the expected Time Spent value
		txtTime.setPromptText("Hours spent");

		// Creates the What Was Done field
		TextArea txtWhatDone = createFormTextArea("What was done?", 2);

		// Creates the How It Was Done field
		TextArea txtHowDone = createFormTextArea("How was it done?", 2);

		// Creates the Team Effort field
		TextArea txtTeamEffort = createFormTextArea("How did the team contribute?", 2);

		// Styles the Lesson ID field
		styleFormField(txtLessonId);

		// Styles the Time Spent field
		styleFormField(txtTime);

		// Places the main heading across both columns
		grid.add(heading, 0, 0, 2, 1);

		// Places the experience section heading
		grid.add(experienceHeading, 0, 1, 2, 1);

		// Adds the experience fields
		addFormRow(grid, "Lesson ID", txtLessonId, 2);
		addFormRow(grid, "Time Spent (hours)", txtTime, 3);
		addFormRow(grid, "What Was Done", txtWhatDone, 4);
		addFormRow(grid, "How It Was Done", txtHowDone, 5);
		addFormRow(grid, "Team Effort", txtTeamEffort, 6);

		// Creates the Save Experience button
		Button btnConfirm = new Button("Save Experience");

		// Creates the Cancel button
		Button btnCancel = new Button("Cancel");

		// Styles Save Experience as the primary action
		styleFormPrimaryButton(btnConfirm);

		// Styles Cancel as a secondary action
		styleFormSecondaryButton(btnCancel);

		// Places the form actions in one row
		HBox btnBox = new HBox(10, btnCancel, btnConfirm);

		// Aligns the form actions to the right
		btnBox.setAlignment(Pos.CENTER_RIGHT);

		// Adds the actions beneath the fields
		grid.add(btnBox, 1, 7);

		// Closes the form without saving
		btnCancel.setOnAction((_) -> experienceStage.close());

		// Validates and saves the experience information
		btnConfirm.setOnAction((_) -> {

			// Stores the parsed Lesson ID
			int id;

			// Validates the Lesson ID
			try {
				id = Integer.parseInt(
						txtLessonId.getText().trim());

				// Rejects zero or negative Lesson IDs
				if (id <= 0) {
					throw new NumberFormatException();
				}
			} catch (NumberFormatException exception) {
				showCreateMessage(
						experienceStage,
						"Unable to Save Experience",
						"Lesson ID must be a positive integer",
						true);
				return;
			}

			// Retrieves the selected lesson
			LessonsLearned existing =
					theDatabase.getLessonById(id);

			// Retrieves the active contributor's username
			String currentUser =
					ViewRole1Home.theUser != null
							? ViewRole1Home.theUser.getUserName()
							: "";

			// Verifies that the lesson exists and belongs to the contributor
			if (existing == null
					|| !existing.getAuthor().equals(currentUser)) {
				showCreateMessage(
						experienceStage,
						"Unable to Save Experience",
						"Lesson with ID " + id + " does not exist",
						true);
				return;
			}

			// Reads the Time Spent field
			String timeText =
					txtTime.getText().trim();

			// Stores the parsed Time Spent value
			double time;

			// Validates the Time Spent value
			try {
				time = Double.parseDouble(
						timeText.replace("hrs", "").trim());

				// Rejects negative Time Spent values
				if (time < 0) {
					throw new NumberFormatException();
				}
			} catch (NumberFormatException exception) {
				showCreateMessage(
						experienceStage,
						"Unable to Save Experience",
						"Time spent must be a non-negative numeric value in hours",
						true);
				return;
			}

			// Updates the lesson's Time Spent value
			existing.setTimeSpentHours(time);

			// Updates the lesson's What Was Done value
			existing.setWhatWasDone(txtWhatDone.getText().trim());

			// Updates the lesson's How It Was Done value
			existing.setHowItWasDone(txtHowDone.getText().trim());

			// Updates the lesson's Team Effort value
			existing.setTeamEffort(txtTeamEffort.getText().trim());

			// Attempts to save the updated lesson
			boolean updated = theDatabase.updateLesson(existing);

			// Checks whether the experience was saved
			if (updated) {

				// Stores the selected Lesson ID for controller compatibility
				ViewRole1Home.textfield_LessonId.setText(String.valueOf(id));

				// Refreshes the contributor's lesson cards
				handleReadAllLessons();

				// Displays the successful save message
				showCreateMessage(
						experienceStage,
						"Experience Saved",
						"Experience was saved for lesson ID " + id,
						false);
			} else {
				// Displays the missing-lesson error
				showCreateMessage(
						experienceStage,
						"Unable to Save Experience",
						"Lesson with ID " + id + " does not exist",
						true);
				return;
			}

			// Closes the completed Experience form
			experienceStage.close();
		});

		// Makes the complete form scrollable
		ScrollPane formScroll = new ScrollPane(grid);

		// Makes the form fill the available width
		formScroll.setFitToWidth(true);

		// Applies the sage background to the scrollable form
		formScroll.setStyle(
				"-fx-background-color: #F1F4F0;"
				+ "-fx-background: #F1F4F0;"
				+ "-fx-border-color: transparent;");

		// Adds the form to its scene
		experienceStage.setScene(new Scene(formScroll, 690, 500));

		// Displays the Add Experience form
		experienceStage.show();
	}

	/**********
	 * 
	 * <p> Method: handleDeleteLesson() </p>
	 * 
	 * <p> Description: Validates the selected Lesson ID, verifies that the lesson
	 * belongs to the active contributor, prevents deletion of locked lessons, and
	 * displays a styled confirmation window before permanently deleting the lesson.
	 * A successful deletion refreshes the contributor's My Lessons view. </p>
	 * 
	 */

	protected static void handleDeleteLesson() {

		// Reads the selected Lesson ID
		String idText = ViewRole1Home.textfield_LessonId.getText().trim();

		// Stores the parsed Lesson ID
		int id;

		// Validates the Lesson ID
		try {
			id = Integer.parseInt(idText);

			// Rejects zero or negative Lesson IDs
			if (id <= 0) {
				throw new NumberFormatException();
			}
		} catch (NumberFormatException exception) {
			showCreateMessage(
					ViewRole1Home.theStage,
					"Unable to Delete Lesson",
					"Lesson ID must be a positive integer",
					true);
			return;
		}

		// Retrieves the selected lesson
		LessonsLearned existing = theDatabase.getLessonById(id);

		// Retrieves the active contributor's username
		String currentUser =
				ViewRole1Home.theUser != null
						? ViewRole1Home.theUser.getUserName()
						: "";

		// Verifies that the lesson exists and belongs to the contributor
		if (existing == null
				|| !existing.getAuthor().equals(currentUser)) {
			showCreateMessage(
					ViewRole1Home.theStage,
					"Unable to Delete Lesson",
					"No lesson record was found with ID " + id,
					true);
			return;
		}

		// Prevents deletion when the lesson or one of its fields is locked
		if (existing.isLocked()) {
			showCreateMessage(
					ViewRole1Home.theStage,
					"Unable to Delete Lesson",
					"Locked lessons cannot be deleted",
					true);
			return;
		}

		// Creates the deletion-confirmation window
		Stage confirmStage = new Stage();

		// Keeps the confirmation associated with the contributor page
		confirmStage.initModality(Modality.WINDOW_MODAL);

		// Sets the contributor page as the confirmation owner
		confirmStage.initOwner(ViewRole1Home.theStage);

		// Sets the confirmation-window title
		confirmStage.setTitle("Confirm Deletion");

		// Creates the confirmation heading
		Label heading = new Label("Delete Lesson?");

		// Applies the destructive heading style
		heading.setStyle(
				"-fx-font-family: 'Helvetica';"
				+ "-fx-font-size: 20;"
				+ "-fx-font-weight: bold;"
				+ "-fx-text-fill: #9B3A3A;");

		// Creates the confirmation message
		Label message = new Label(
				"Are you sure you want to permanently delete \""
				+ existing.getTitle()
				+ "\"?");

		// Allows a long lesson title to wrap
		message.setWrapText(true);

		// Limits the message width
		message.setMaxWidth(390);

		// Applies the primary text style
		message.setStyle(
				"-fx-font-family: 'Helvetica';"
				+ "-fx-font-size: 13;"
				+ "-fx-text-fill: #203029;");

		// Creates the Cancel button
		Button btnCancel =
				new Button("Cancel");

		// Applies the secondary form style
		styleFormSecondaryButton(btnCancel);

		// Creates the Delete button
		Button btnDelete = new Button("Delete");

		// Applies the normal destructive-action style
		String deleteBase =
				"-fx-background-color: white;"
				+ "-fx-text-fill: #B44747;"
				+ "-fx-border-color: #E3BABA;"
				+ "-fx-border-width: 1;"
				+ "-fx-border-radius: 8;"
				+ "-fx-background-radius: 8;"
				+ "-fx-padding: 8 16 8 16;"
				+ "-fx-cursor: hand;";

		// Applies the destructive-action hover style
		String deleteHover =
				"-fx-background-color: #F7E8E8;"
				+ "-fx-text-fill: #9B3A3A;"
				+ "-fx-border-color: #C96A6A;"
				+ "-fx-border-width: 1;"
				+ "-fx-border-radius: 8;"
				+ "-fx-background-radius: 8;"
				+ "-fx-padding: 8 16 8 16;"
				+ "-fx-cursor: hand;";

		// Applies the normal Delete style
		btnDelete.setStyle(deleteBase);

		// Applies the hover appearance
		btnDelete.setOnMouseEntered(
				(_) -> btnDelete.setStyle(deleteHover));

		// Restores the normal appearance
		btnDelete.setOnMouseExited(
				(_) -> btnDelete.setStyle(deleteBase));

		// Places the confirmation actions in one row
		HBox actions = new HBox(10, btnCancel, btnDelete);

		// Aligns the confirmation actions to the right
		actions.setAlignment(Pos.CENTER_RIGHT);

		// Arranges the confirmation content vertically
		VBox content = new VBox(14, heading, message, actions);

		// Adds space around the confirmation content
		content.setPadding(new Insets(24));

		// Applies the sage page background
		content.setStyle("-fx-background-color: #F1F4F0;");

		// Closes the confirmation without deleting
		btnCancel.setOnAction((_) -> confirmStage.close());

		// Permanently deletes the selected lesson
		btnDelete.setOnAction((_) -> {

			// Attempts to delete the lesson
			boolean deleted = theDatabase.deleteLesson(id);

			// Checks whether deletion succeeded
			if (deleted) {

				// Clears the selected Lesson ID
				ViewRole1Home.textfield_LessonId.clear();

				// Clears the retained Title field
				ViewRole1Home.textfield_Title.clear();

				// Clears the selected Category
				ViewRole1Home.textfield_Category.clear();

				// Refreshes the contributor's lesson cards
				handleReadAllLessons();

				// Displays the successful deletion message
				showCreateMessage(
						confirmStage,
						"Lesson Deleted",
						"The lesson was deleted successfully",
						false);
			} else {
				// Displays the database deletion error
				showCreateMessage(
						confirmStage,
						"Unable to Delete Lesson",
						"No lesson record was found with ID " + id,
						true);
				return;
			}

			// Closes the completed confirmation
			confirmStage.close();
		});

		// Adds the confirmation content to its scene
		confirmStage.setScene(new Scene(content, 440, 210));

		// Keeps the confirmation at its intended size
		confirmStage.setResizable(false);

		// Displays the deletion confirmation
		confirmStage.showAndWait();
	}

	/******
	 * 
	 * <p> Method: addFormRow() </p>
	 * 
	 * <p> Description: Adds a consistently styled label and input control to a
	 * lesson form. </p>
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

		// Creates the field label
		Label label = new Label(labelText);

		// Applies the secondary label style
		label.setStyle(
				"-fx-font-family: 'Helvetica';"
				+ "-fx-font-size: 12;"
				+ "-fx-font-weight: bold;"
				+ "-fx-text-fill: #65736B;");

		// Places the label in the first column
		grid.add(label, 0, row);

		// Places the input control in the second column
		grid.add(field, 1, row);
	}

	/******
	 * 
	 * <p> Method: createFormTextArea() </p>
	 * 
	 * <p> Description: Creates a compact wrapped text area for a lesson form. </p>
	 * 
	 * @param prompt instruction displayed while the field is empty
	 * @param rows preferred number of visible text rows
	 * @return configured lesson-form text area
	 * 
	 */

	private static TextArea createFormTextArea(String prompt, int rows) {

		// Creates the text area
		TextArea area = new TextArea();

		// Displays the field instruction
		area.setPromptText(prompt);

		// Sets the preferred number of visible rows
		area.setPrefRowCount(rows);

		// Wraps long text inside the field
		area.setWrapText(true);

		// Sets the preferred form-field width
		area.setPrefWidth(455);

		// Applies the smooth white field style
		area.setStyle(
				"-fx-control-inner-background: white;"
				+ "-fx-background-color: white;"
				+ "-fx-border-color: #CAD7D0;"
				+ "-fx-border-width: 1;"
				+ "-fx-border-radius: 8;"
				+ "-fx-background-radius: 8;"
				+ "-fx-font-family: 'Helvetica';"
				+ "-fx-font-size: 12;"
				+ "-fx-focus-color: transparent;"
				+ "-fx-faint-focus-color: transparent;");

		// Returns the configured field
		return area;
	}

	/******
	 * 
	 * <p> Method: styleFormField() </p>
	 * 
	 * <p> Description: Applies the updated UI style to a lesson-form text
	 * field. </p>
	 * 
	 * @param field text field being styled
	 * 
	 */

	private static void styleFormField(TextField field) {

		// Sets the preferred form-field width
		field.setPrefWidth(455);

		// Applies the smooth white field style
		field.setStyle(
				"-fx-background-color: white;"
				+ "-fx-border-color: #CAD7D0;"
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
	 * <p> Method: createFormSectionLabel() </p>
	 * 
	 * <p> Description: Creates an uppercase heading for a section of a lesson
	 * form. </p>
	 * 
	 * @param text section heading text
	 * @return configured section label
	 * 
	 */

	private static Label createFormSectionLabel(String text) {

		// Creates the section label
		Label label = new Label(text);

		// Applies the compact sage heading style
		label.setStyle(
				"-fx-font-family: 'Helvetica';"
				+ "-fx-font-size: 11;"
				+ "-fx-font-weight: bold;"
				+ "-fx-text-fill: #405D52;"
				+ "-fx-padding: 10 0 2 0;");

		// Returns the configured section label
		return label;
	}

	/******
	 * 
	 * <p> Method: styleFormPrimaryButton() </p>
	 * 
	 * <p> Description: Applies the primary sage style to a lesson-form
	 * button. </p>
	 * 
	 * @param button primary action button being styled
	 * 
	 */

	private static void styleFormPrimaryButton(Button button) {

		// Applies the primary action style
		button.setStyle(
				"-fx-background-color: #405D52;"
				+ "-fx-text-fill: white;"
				+ "-fx-background-radius: 8;"
				+ "-fx-padding: 8 16 8 16;"
				+ "-fx-cursor: hand;");
	}

	/******
	 * 
	 * <p> Method: styleFormSecondaryButton() </p>
	 * 
	 * <p> Description: Applies the secondary style to a lesson-form
	 * button. </p>
	 * 
	 * @param button secondary action button being styled
	 * 
	 */

	private static void styleFormSecondaryButton(Button button) {

		// Applies the secondary action style
		button.setStyle(
				"-fx-background-color: white;"
				+ "-fx-text-fill: #203029;"
				+ "-fx-border-color: #CAD7D0;"
				+ "-fx-border-width: 1;"
				+ "-fx-border-radius: 8;"
				+ "-fx-background-radius: 8;"
				+ "-fx-padding: 8 16 8 16;"
				+ "-fx-cursor: hand;");
	}

	/******
	 * 
	 * <p> Method: showCreateMessage() </p>
	 * 
	 * <p> Description: Displays a styled confirmation or error window after a
	 * lesson-creation attempt. </p>
	 * 
	 * @param owner window that opened the message
	 * @param title title and heading displayed by the message
	 * @param message confirmation or error information displayed to the user
	 * @param error true when the message represents an error
	 * 
	 */

	private static void showCreateMessage(
			Stage owner,
			String title,
			String message,
			boolean error) {

		// Creates the message window
		Stage messageStage = new Stage();

		// Keeps the message associated with its form
		messageStage.initModality(Modality.WINDOW_MODAL);

		// Sets the form as the message owner
		messageStage.initOwner(owner);

		// Sets the message-window title
		messageStage.setTitle(title);

		// Creates the message heading
		Label heading = new Label(title);

		// Applies confirmation or error heading styling
		heading.setStyle(
				"-fx-font-family: 'Helvetica';"
				+ "-fx-font-size: 18;"
				+ "-fx-font-weight: bold;"
				+ "-fx-text-fill: "
				+ (error ? "#9B3A3A" : "#405D52")
				+ ";");

		// Creates the detailed message
		Label details = new Label(message);

		// Allows long messages to wrap
		details.setWrapText(true);

		// Limits the message to the available content width
		details.setMaxWidth(360);

		// Applies the primary text style
		details.setStyle(
				"-fx-font-family: 'Helvetica';"
				+ "-fx-font-size: 13;"
				+ "-fx-text-fill: #203029;");

		// Creates the Close button
		Button closeButton = new Button("Close");

		// Applies the primary sage button style
		styleFormPrimaryButton(closeButton);

		// Allows Enter to activate Close
		closeButton.setDefaultButton(true);

		// Closes the message window
		closeButton.setOnAction((_) -> messageStage.close());

		// Places Close inside an action row
		HBox actions = new HBox(closeButton);

		// Aligns Close to the right
		actions.setAlignment(Pos.CENTER_RIGHT);

		// Arranges the message content vertically
		VBox content = new VBox(14, heading, details, actions);

		// Adds space around the message
		content.setPadding(new Insets(24));

		// Applies the sage page background
		content.setStyle("-fx-background-color: #F1F4F0;");

		// Adds the message content to its scene
		messageStage.setScene(new Scene(content, 430, 175));

		// Keeps the message at its intended size
		messageStage.setResizable(false);

		// Displays the message and waits for it to close
		messageStage.showAndWait();
	}

	/******
	 * 
	 * <p> Method: openCreateLessonForm() </p>
	 * 
	 * <p> Description: Opens a styled form that collects the required lesson
	 * information and optional experience information, validates the input, and
	 * creates the lesson in the database. </p>
	 * 
	 */

	private static void openCreateLessonForm() {

		// Creates the Create Lesson window
		Stage createStage = new Stage();

		// Keeps the form associated with the contributor page
		createStage.initModality(Modality.WINDOW_MODAL);

		// Sets the contributor page as the form owner
		createStage.initOwner(ViewRole1Home.theStage);

		// Sets the form-window title
		createStage.setTitle("Create Lesson");

		// Creates the form layout
		GridPane grid = new GridPane();

		// Adds horizontal spacing between columns
		grid.setHgap(14);

		// Adds vertical spacing between rows
		grid.setVgap(10);

		// Adds space around the form
		grid.setPadding(new Insets(24));

		// Applies the sage page background
		grid.setStyle("-fx-background-color: #F1F4F0;");

		// Creates the main form heading
		Label heading = new Label("Create a Lesson");

		// Applies the main heading style
		heading.setStyle(
				"-fx-font-family: 'Helvetica';"
				+ "-fx-font-size: 24;"
				+ "-fx-font-weight: bold;"
				+ "-fx-text-fill: #203029;");

		// Creates the required-details section heading
		Label coreHeading = createFormSectionLabel("LESSON DETAILS");

		// Creates the experience section heading
		Label experienceHeading = createFormSectionLabel("EXPERIENCE");

		// Creates the Title field
		TextField txtTitle = new TextField();

		// Explains the Title requirements
		txtTitle.setPromptText("A clear title between 5 and 100 characters");

		// Creates the Category field
		TextField txtCategory = new TextField();

		// Provides examples of lesson categories
		txtCategory.setPromptText("Example: Testing, Teamwork, or Design");

		// Creates the locked Author field
		TextField txtAuthor = new TextField(
				ViewRole1Home.theUser != null
						? ViewRole1Home.theUser.getUserName()
						: "Test Student");

		// Prevents the contributor from changing the Author
		txtAuthor.setEditable(false);

		// Creates the Problem Description field
		TextArea txtProblem = createFormTextArea("Describe the problem or situation", 2);

		// Creates the Solution field
		TextArea txtSolution = createFormTextArea("Describe the solution or lesson learned", 2);

		// Creates the Time Spent field with a default value
		TextField txtTime = new TextField("0");

		// Explains the expected Time Spent value
		txtTime.setPromptText("Hours spent");

		// Creates the What Was Done field
		TextArea txtWhatDone = createFormTextArea("What was done?", 2);

		// Creates the How It Was Done field
		TextArea txtHowDone = createFormTextArea("How was it done?", 2);

		// Creates the Team Effort field
		TextArea txtTeamEffort = createFormTextArea("How did the team contribute?", 2);

		// Styles the Title field
		styleFormField(txtTitle);

		// Styles the Category field
		styleFormField(txtCategory);

		// Styles the Author field
		styleFormField(txtAuthor);

		// Styles the Time Spent field
		styleFormField(txtTime);

		// Gives the locked Author field a distinct appearance
		txtAuthor.setStyle(
				txtAuthor.getStyle()
				+ "-fx-opacity: 0.75;"
				+ "-fx-background-color: #E8EDE9;");

		// Places the main heading across both columns
		grid.add(heading, 0, 0, 2, 1);

		// Places the required-details heading
		grid.add(coreHeading, 0, 1, 2, 1);

		// Adds the required lesson fields
		addFormRow(grid, "Title", txtTitle, 2);
		addFormRow(grid, "Category", txtCategory, 3);
		addFormRow(grid, "Author", txtAuthor, 4);
		addFormRow(grid, "Problem Description", txtProblem, 5);
		addFormRow(grid, "Solution", txtSolution, 6);

		// Places the experience heading
		grid.add(experienceHeading, 0, 7, 2, 1);

		// Adds the experience fields
		addFormRow(grid, "Time Spent (hours)", txtTime, 8);
		addFormRow(grid, "What Was Done", txtWhatDone, 9);
		addFormRow(grid, "How It Was Done", txtHowDone, 10);
		addFormRow(grid, "Team Effort", txtTeamEffort, 11);

		// Creates the Cancel button
		Button btnCancel = new Button("Cancel");

		// Creates the submission button
		Button btnCreate = new Button("Create Lesson");

		// Styles Cancel as a secondary action
		styleFormSecondaryButton(btnCancel);

		// Styles Create Lesson as the primary action
		styleFormPrimaryButton(btnCreate);

		// Places the form actions in one row
		HBox actions = new HBox(10, btnCancel, btnCreate);

		// Aligns the form actions to the right
		actions.setAlignment(Pos.CENTER_RIGHT);

		// Adds the form actions beneath the fields
		grid.add(actions, 1, 12);

		// Closes the form without creating a lesson
		btnCancel.setOnAction((_) -> createStage.close());

		// Validates the input and creates the lesson
		btnCreate.setOnAction((_) -> {

			// Reads the required text values
			String title = txtTitle.getText().trim();
			String category = txtCategory.getText().trim();
			String problem = txtProblem.getText().trim();
			String solution = txtSolution.getText().trim();

			// Stores the parsed Time Spent value
			double time;

			// Attempts to convert Time Spent into a number
			try {
				time = Double.parseDouble(txtTime.getText().trim());
			} catch (NumberFormatException exception) {
				showCreateMessage(
						createStage,
						"Unable to Create Lesson",
						"Time spent must be a non-negative number",
						true);
				return;
			}

			// Validates the Title length
			if (title.length() < 5 || title.length() > 100) {
				showCreateMessage(
						createStage,
						"Unable to Create Lesson",
						"Title must be between 5 and 100 characters",
						true);
				return;
			}

			// Validates the required lesson fields
			if (category.isEmpty()
					|| problem.isEmpty()
					|| solution.isEmpty()) {
				showCreateMessage(
						createStage,
						"Unable to Create Lesson",
						"Category, problem description, and solution are required",
						true);
				return;
			}

			// Rejects negative Time Spent values
			if (time < 0) {
				showCreateMessage(
						createStage,
						"Unable to Create Lesson",
						"Time spent must be a non-negative number",
						true);
				return;
			}

			// Stores the validated lesson entity
			LessonsLearned lesson;

			// Creates the lesson from the completed form
			try {
				lesson = new LessonsLearned(
						0,
						title,
						category,
						txtAuthor.getText(),
						problem,
						solution,
						time,
						txtWhatDone.getText().trim(),
						txtHowDone.getText().trim(),
						txtTeamEffort.getText().trim(),
						false);
			} catch (IllegalArgumentException exception) {
				showCreateMessage(
						createStage,
						"Unable to Create Lesson",
						exception.getMessage(),
						true);
				return;
			}

			// Attempts to save the lesson
			if (theDatabase.createLesson(lesson)) {

				// Stores the generated Lesson ID
				ViewRole1Home.textfield_LessonId.setText(
						String.valueOf(lesson.getLessonId()));

				// Stores the lesson title for existing controller compatibility
				ViewRole1Home.textfield_Title.setText(title);

				// Stores the lesson category for existing controller compatibility
				ViewRole1Home.textfield_Category.setText(category);

				// Displays the successful creation message
				showCreateMessage(
						createStage,
						"Lesson Created",
						"Lesson created successfully with ID "
								+ lesson.getLessonId(),
						false);

				// Refreshes the contributor's lesson cards
				handleReadAllLessons();

				// Closes the completed form
				createStage.close();
			} else {
				// Displays the database error
				showCreateMessage(
						createStage,
						"Unable to Create Lesson",
						"Database error: The lesson could not be created",
						true);
			}
		});

		// Makes the complete form scrollable
		ScrollPane formScroll = new ScrollPane(grid);

		// Makes the form fill the available width
		formScroll.setFitToWidth(true);

		// Applies the sage background to the scrollable form
		formScroll.setStyle(
				"-fx-background-color: #F1F4F0;"
				+ "-fx-background: #F1F4F0;"
				+ "-fx-border-color: transparent;");

		// Adds the form to its scene
		createStage.setScene(
				new Scene(formScroll, 690, 610));

		// Displays the Create Lesson form
		createStage.show();
	}

	/******
	 * 
	 * <p> Method: lockFormField() </p>
	 * 
	 * <p> Description: Disables a locked lesson field and gives it a muted
	 * appearance while preserving the updated UI style. </p>
	 * 
	 * @param field locked form control being styled
	 * 
	 */

	private static void lockFormField(Control field) {

		// Prevents the contributor from editing the locked field
		field.setDisable(true);

		// Applies the muted locked-field appearance
		field.setStyle(
				field.getStyle()
				+ "-fx-opacity: 0.65;"
				+ "-fx-background-color: #E8EDE9;");
	}

}
