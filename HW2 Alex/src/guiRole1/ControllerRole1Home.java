package guiRole1;

import database.Database;
import entityClasses.LessonsLearned;
import entityClasses.LessonsLearnedList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/*******
 * <p> Title: ControllerRole1Home Class. </p>
 * 
 * <p> Description: The Java/FX-based Role 1 Home Page.  This class provides the controller
 * actions basic on the user's use of the JavaFX GUI widgets defined by the View class.
 * 
 * This page is a stub for establish future roles for the application.
 * 
 * The class has been written assuming that the View or the Model are the only class methods that
 * can invoke these methods.  This is why each has been declared at "protected".  Do not change any
 * of these methods to public.</p>
 * 
 * <p> Copyright: Lynn Robert Carter © 2025 </p>
 * 
 * @author Lynn Robert Carter
 * 
 * @version 1.00		2025-08-17 Initial version
 * @version 1.01		2025-09-16 Update Javadoc documentation *  
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
	 * <p> Description: Handles creating a lesson by reading user input from ViewRole1Home,
	 * applying boundary and non-null validation, and invoking the database insertion.
	 * Formats and outputs the exact test plan messages upon success or error. </p>
	 */
	protected static void handleCreateLesson() {
		String title = ViewRole1Home.textfield_Title.getText();
		String category = ViewRole1Home.textfield_Category.getText();
		String author = (ViewRole1Home.theUser != null) ? ViewRole1Home.theUser.getUserName() : "Test Student";
		
		// Validate title length bounds
		if (title == null || title.length() < 5 || title.length() > 100) {
			ViewRole1Home.textarea_Display.setText("“Title must be between 5 and 100 characters.”");
			return;
		}

		// Validate mandatory field completeness
		if (category == null || category.trim().isEmpty()) {
			ViewRole1Home.textarea_Display.setText("“Category, problem description, and solution cannot be blank.”");
			return;
		}

		LessonsLearned lesson = new LessonsLearned(0, title, category, author, "Default Problem Description", "Default Solution", 0.0);
		boolean success = theDatabase.createLesson(lesson);
		if (success) {
			ViewRole1Home.textfield_LessonId.setText(String.valueOf(lesson.getLessonId()));
			ViewRole1Home.textarea_Display.setText("Record was successfully created in the Foundations Database and received a unique lessonId (" 
					+ lesson.getLessonId() + ").");
		} else {
			ViewRole1Home.textarea_Display.setText("Database error: Could not create lesson record.");
		}
	}

	/**********
	 * <p> Method: handleReadAllLessons() </p>
	 * 
	 * <p> Description: Retrieves all lessons authored by the active contributor or specified user.
	 * Outputs empty list notices or the populated LessonsLearnedList. </p>
	 */
	protected static void handleReadAllLessons() {
		String author = (ViewRole1Home.theUser != null) ? ViewRole1Home.theUser.getUserName() : "Unknown User";
		
		LessonsLearnedList list = theDatabase.getAllLessonsForContributor(author);
		if (list.size() == 0) {
			ViewRole1Home.textarea_Display.setText("No lessons found for contributor " + author + ".");
		} else {
			ViewRole1Home.textarea_Display.setText(list.toString());
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
	 * <p> Description: Filters stored lessons by category query. Rejects empty queries
	 * and displays the returned LessonsLearnedList subset. </p>
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
	 * <p> Method: handleOpenUpdateWindow() </p>
	 * 
	 * <p> Description: Opens a dedicated modal window allowing the user to edit Title,
	 * Category, Problem Description, and Solution. Author is permanently tied to the creator
	 * and cannot be edited. Any administrator-locked fields are grayed out and disabled.
	 * Includes Confirm and Cancel buttons. </p>
	 */
	@SuppressWarnings("unused")
	protected static void handleOpenUpdateWindow() {
		String idStr = ViewRole1Home.textfield_LessonId.getText().trim();
		int id;
		try {
			id = Integer.parseInt(idStr);
		} catch (NumberFormatException ex) {
			ViewRole1Home.textarea_Display.setText("Lesson with ID " + idStr + " does not exist.");
			return;
		}

		LessonsLearned existing = theDatabase.getLessonById(id);
		String currentUser = (ViewRole1Home.theUser != null) ? ViewRole1Home.theUser.getUserName() : "";

		if (existing == null || !existing.getAuthor().equals(currentUser)) {
			ViewRole1Home.textarea_Display.setText("Lesson with ID " + id + " does not exist.");
			return;
		}

		// Build Update Window Dialog
		Stage updateStage = new Stage();
		updateStage.initModality(Modality.WINDOW_MODAL);
		updateStage.initOwner(ViewRole1Home.theStage);
		updateStage.setTitle("Update Lesson #" + id);

		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20));

		TextField txtTitle = new TextField(existing.getTitle());
		TextField txtCategory = new TextField(existing.getCategory());
		
		// Author Field: permanently tied to creator, grayed out and non-editable
		TextField txtAuthor = new TextField(existing.getAuthor());
		txtAuthor.setDisable(true);
		txtAuthor.setStyle("-fx-opacity: 0.6; -fx-background-color: lightgray;");
		
		TextArea txtProblem = new TextArea(existing.getProblemDescription());
		txtProblem.setPrefRowCount(3);
		TextArea txtSolution = new TextArea(existing.getSolution());
		txtSolution.setPrefRowCount(3);

		// If individual fields are locked, disable them
		if (existing.isTitleLocked()) {
			txtTitle.setDisable(true);
			txtTitle.setStyle("-fx-opacity: 0.6; -fx-background-color: lightgray;");
		}
		if (existing.isCategoryLocked()) {
			txtCategory.setDisable(true);
			txtCategory.setStyle("-fx-opacity: 0.6; -fx-background-color: lightgray;");
		}
		if (existing.isProblemDescriptionLocked()) {
			txtProblem.setDisable(true);
			txtProblem.setStyle("-fx-opacity: 0.6; -fx-background-color: lightgray;");
		}
		if (existing.isSolutionLocked()) {
			txtSolution.setDisable(true);
			txtSolution.setStyle("-fx-opacity: 0.6; -fx-background-color: lightgray;");
		}

		grid.add(new Label("Title:"), 0, 0);
		grid.add(txtTitle, 1, 0);
		grid.add(new Label("Category:"), 0, 1);
		grid.add(txtCategory, 1, 1);
		grid.add(new Label("Author (Locked):"), 0, 2);
		grid.add(txtAuthor, 1, 2);
		grid.add(new Label("Problem Description:"), 0, 3);
		grid.add(txtProblem, 1, 3);
		grid.add(new Label("Solution:"), 0, 4);
		grid.add(txtSolution, 1, 4);

		Button btnConfirm = new Button("Confirm");
		Button btnCancel = new Button("Cancel");
		HBox btnBox = new HBox(15, btnConfirm, btnCancel);
		btnBox.setAlignment(Pos.CENTER_RIGHT);
		grid.add(btnBox, 1, 5);

		btnCancel.setOnAction(e -> updateStage.close());

		btnConfirm.setOnAction(e -> {
			String newTitle = txtTitle.getText();
			String newCategory = txtCategory.getText();
			String newProblem = txtProblem.getText();
			String newSolution = txtSolution.getText();

			if (newTitle == null || newTitle.isEmpty()) {
				ViewRole1Home.textarea_Display.setText("Title cannot be empty. Must be between 5 and 100 characters.");
				updateStage.close();
				return;
			}
			if (newTitle.length() < 5 || newTitle.length() > 100) {
				ViewRole1Home.textarea_Display.setText("Title must be between 5 and 100 characters.");
				updateStage.close();
				return;
			}

			// Author remains existing.getAuthor() and is never updated from user input
			existing.setTitle(newTitle);
			existing.setCategory(newCategory);
			existing.setProblemDescription(newProblem);
			existing.setSolution(newSolution);

			boolean updated = theDatabase.updateLesson(existing);
			if (updated) {
				ViewRole1Home.textfield_Title.setText(newTitle);
				ViewRole1Home.textfield_Category.setText(newCategory);
				ViewRole1Home.textarea_Display.setText("Updated Lesson Successfully.");
			} else {
				ViewRole1Home.textarea_Display.setText("“Lesson with ID " + id + " does not exist.”");
			}
			updateStage.close();
		});

		updateStage.setScene(new Scene(grid, 640, 420));
		updateStage.show();
	}
	
	/**********
	 * <p> Method: handleOpenExperienceWindow() </p>
	 * 
	 * <p> Description: Opens a dedicated modal window allowing editing of experience data:
	 * Time Spent (hours), What Was Done, How It Was Done, and Team Effort. Includes Confirm
	 * and Cancel buttons. </p>
	 */
	@SuppressWarnings("unused")
	protected static void handleOpenExperienceWindow() {
		String idStr = ViewRole1Home.textfield_LessonId.getText().trim();
		int id;
		try {
			id = Integer.parseInt(idStr);
		} catch (NumberFormatException ex) {
			ViewRole1Home.textarea_Display.setText("Lesson with ID " + idStr + " does not exist.");
			return;
		}

		LessonsLearned existing = theDatabase.getLessonById(id);
		String currentUser = (ViewRole1Home.theUser != null) ? ViewRole1Home.theUser.getUserName() : "";

		if (existing == null || !existing.getAuthor().equals(currentUser)) {
			ViewRole1Home.textarea_Display.setText("Lesson with ID " + id + " does not exist.");
			return;
		}

		// Build Experience Window Dialog
		Stage expStage = new Stage();
		expStage.initModality(Modality.WINDOW_MODAL);
		expStage.initOwner(ViewRole1Home.theStage);
		expStage.setTitle("Experience Information - Lesson #" + id);

		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20));

		TextField txtTime = new TextField(String.valueOf(existing.getTimeSpentHours()));
		TextArea txtWhatDone = new TextArea(existing.getWhatWasDone());
		txtWhatDone.setPrefRowCount(3);
		TextArea txtHowDone = new TextArea(existing.getHowItWasDone());
		txtHowDone.setPrefRowCount(3);
		TextArea txtTeamEffort = new TextArea(existing.getTeamEffort());
		txtTeamEffort.setPrefRowCount(3);

		grid.add(new Label("Time Spent(hours):"), 0, 0);
		grid.add(txtTime, 1, 0);
		grid.add(new Label("What Was Done:"), 0, 1);
		grid.add(txtWhatDone, 1, 1);
		grid.add(new Label("How It Was Done:"), 0, 2);
		grid.add(txtHowDone, 1, 2);
		grid.add(new Label("Team Effort:"), 0, 3);
		grid.add(txtTeamEffort, 1, 3);

		Button btnConfirm = new Button("Confirm");
		Button btnCancel = new Button("Cancel");
		HBox btnBox = new HBox(15, btnConfirm, btnCancel);
		btnBox.setAlignment(Pos.CENTER_RIGHT);
		grid.add(btnBox, 1, 4);

		btnCancel.setOnAction(e -> expStage.close());

		btnConfirm.setOnAction(e -> {
			String timeStr = txtTime.getText().trim();
			double time;
			try {
				time = Double.parseDouble(timeStr.replace("hrs", "").trim());
				if (time < 0) {
					ViewRole1Home.textarea_Display.setText("Time spent must be a non-negative numeric value (hours).");
					expStage.close();
					return;
				}
			} catch (NumberFormatException ex) {
				ViewRole1Home.textarea_Display.setText("Time spent must be a non-negative numeric value (hours).");
				expStage.close();
				return;
			}

			existing.setTimeSpentHours(time);
			existing.setWhatWasDone(txtWhatDone.getText().trim());
			existing.setHowItWasDone(txtHowDone.getText().trim());
			existing.setTeamEffort(txtTeamEffort.getText().trim());

			boolean updated = theDatabase.updateLesson(existing);
			if (updated) {
				ViewRole1Home.textarea_Display.setText("Experience Updated Successfully.");
			} else {
				ViewRole1Home.textarea_Display.setText("Lesson with ID " + id + " does not exist.");
			}
			expStage.close();
		});

		expStage.setScene(new Scene(grid, 630, 280));
		expStage.show();
	}

	/**********
	 * <p> Method: handleDeleteLesson() </p>
	 * 
	 * <p> Description: Validates ID, verifies existing records, and permanently deletes
	 *  database record. Displays error messages for negative or non-existent IDs. </p>
	 */
	@SuppressWarnings("unused")
	protected static void handleDeleteLesson() {
		String idStr = ViewRole1Home.textfield_LessonId.getText().trim();
		int id;
		try {
			id = Integer.parseInt(idStr);
			if (id <= 0) {
				ViewRole1Home.textarea_Display.setText("Lesson ID must be a positive integer.");
				return;
			}
		} catch (NumberFormatException ex) {
			ViewRole1Home.textarea_Display.setText("Lesson ID must be a positive integer.");
			return;
		}

		LessonsLearned existing = theDatabase.getLessonById(id);
		String currentUser = (ViewRole1Home.theUser != null) ? ViewRole1Home.theUser.getUserName() : "";

		if (existing == null || !existing.getAuthor().equals(currentUser)) {
			ViewRole1Home.textarea_Display.setText("No lesson record found matching ID " + id + ".");
			return;
		}

		// When a lesson is locked or has any locked field, contributor cannot delete it
		if (existing.isLocked()) {
			ViewRole1Home.textarea_Display.setText("Error: When a lesson is locked or has a locked field, you are unable to delete that lesson.");
			return;
		}

		// Delete Confirmation Window
		Stage confirmStage = new Stage();
		confirmStage.initModality(Modality.WINDOW_MODAL);
		confirmStage.initOwner(ViewRole1Home.theStage);
		confirmStage.setTitle("Confirm Deletion");

		VBox box = new VBox(15);
		box.setPadding(new Insets(20));
		box.setAlignment(Pos.CENTER);

		Label lblMessage = new Label("Are you sure you want to delete Lesson #" + id + "?");
		lblMessage.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

		Button btnDeleteConfirm = new Button("Delete");
		btnDeleteConfirm.setStyle("-fx-background-color: #d9534f; -fx-text-fill: white;");
		Button btnCancel = new Button("Cancel");

		HBox buttonBox = new HBox(20, btnDeleteConfirm, btnCancel);
		buttonBox.setAlignment(Pos.CENTER);

		btnCancel.setOnAction(e -> confirmStage.close());

		btnDeleteConfirm.setOnAction(e -> {
			boolean deleted = theDatabase.deleteLesson(id);
			if (deleted) {
				ViewRole1Home.textarea_Display.setText("Lesson has been deleted.");
				ViewRole1Home.textfield_LessonId.clear();
				ViewRole1Home.textfield_Title.clear();
				ViewRole1Home.textfield_Category.clear();
			} else {
				ViewRole1Home.textarea_Display.setText("No lesson record found matching ID " + id + ".");
			}
			confirmStage.close();
		});

		box.getChildren().addAll(lblMessage, buttonBox);
		confirmStage.setScene(new Scene(box, 360, 140));
		confirmStage.show();
	}
}
