package guiCreateLesson;

import entityClasses.LessonsLearned;
import javafx.stage.Stage;

/*******
 *
 * <p> Title: ControllerCreateLesson Class. </p>
 *
 * <p> Description: The Create Lesson Controller. This class validates the
 * contributor's form values, creates the lesson entity, and asks the Model to
 * store the Lesson Details and initial Experience. </p>
 *
 * <p> Copyright: Lynn Robert Carter © 2025 </p>
 *
 * @author Alexander Murray
 * @author Venus Ho
 * @author Alan Shimp
 * @author Caleb Beaven
 * @author Krithik Duraisamy
 *
 * @version 1.00		2026-10-23 Initial version after refactoring
 *
 */

public class ControllerCreateLesson {

	/******
	 *
	 * <p> Constructor: ControllerCreateLesson() </p>
	 *
	 * <p> Description: Creates the Create Lesson Controller. Its operations are
	 * static, so the application does not need to instantiate this class. </p>
	 *
	 */
	public ControllerCreateLesson() {
		// Default constructor
	}

	/******
	 *
	 * <p> Method: handleCreateLesson() </p>
	 *
	 * <p> Description: Validates the Create Lesson form, creates the lesson and
	 * initial Experience, reports the result, and refreshes the caller after a
	 * successful operation. </p>
	 *
	 * @param formStage window containing the Create Lesson form
	 * @param author username of the contributor creating the lesson
	 * @param title lesson title entered by the contributor
	 * @param category lesson category entered by the contributor
	 * @param problemDescription problem or situation being documented
	 * @param solution solution or lesson learned
	 * @param timeText number of Experience hours entered as text
	 * @param whatWasDone description of what was completed
	 * @param howItWasDone description of how the work was completed
	 * @param teamEffort description of the team's effort
	 * @param onCreated operation used to refresh the Contributor Home page
	 *
	 */
	protected static void handleCreateLesson(
			Stage formStage,
			String author,
			String title,
			String category,
			String problemDescription,
			String solution,
			String timeText,
			String whatWasDone,
			String howItWasDone,
			String teamEffort,
			Runnable onCreated) {

		// Removes unnecessary outer spacing from the entered values
		String cleanTitle = title == null ? "" : title.trim();
		String cleanCategory = category == null ? "" : category.trim();
		String cleanProblem = problemDescription == null ? "" : problemDescription.trim();
		String cleanSolution = solution == null ? "" : solution.trim();

		// Validates the Title length
		if (cleanTitle.length() < 5 || cleanTitle.length() > 100) {
			ViewCreateLesson.showMessage(
					formStage,
					"Unable to Create Lesson",
					"Title must be between 5 and 100 characters",
					true);
			return;
		}

		// Validates the required Lesson Details
		if (cleanCategory.isEmpty()
				|| cleanProblem.isEmpty()
				|| cleanSolution.isEmpty()) {
			ViewCreateLesson.showMessage(
					formStage,
					"Unable to Create Lesson",
					"Category, problem description, and solution are required",
					true);
			return;
		}

		// Stores the parsed Time Spent value
		double timeSpentHours;

		// Validates the Time Spent value
		try {
			timeSpentHours = Double.parseDouble(timeText == null ? "" : timeText.trim());

			// Rejects negative Time Spent values
			if (timeSpentHours < 0) {
				throw new NumberFormatException();
			}
		} catch (NumberFormatException exception) {
			ViewCreateLesson.showMessage(
					formStage,
					"Unable to Create Lesson",
					"Time spent must be a non-negative number",
					true);
			return;
		}

		// Creates the Lesson Details entity
		LessonsLearned lesson;

		try {
			lesson = new LessonsLearned(
					0,
					cleanTitle,
					cleanCategory,
					author,
					cleanProblem,
					cleanSolution,
					0,
					"",
					"",
					"",
					false);
		} catch (IllegalArgumentException exception) {
			ViewCreateLesson.showMessage(
					formStage,
					"Unable to Create Lesson",
					exception.getMessage(),
					true);
			return;
		}

		// Attempts to create the lesson and its initial Experience
		boolean created = ModelCreateLesson.createLessonWithExperience(
				lesson,
				timeSpentHours,
				whatWasDone == null ? "" : whatWasDone.trim(),
				howItWasDone == null ? "" : howItWasDone.trim(),
				teamEffort == null ? "" : teamEffort.trim());

		// Reports a database failure
		if (!created) {
			ViewCreateLesson.showMessage(
					formStage,
					"Unable to Create Lesson",
					"The lesson and its initial Experience could not be created",
					true);
			return;
		}

		// Displays the generated Lesson ID
		ViewCreateLesson.showMessage(
				formStage,
				"Lesson Created",
				"Lesson and initial Experience created successfully with Lesson ID "
						+ lesson.getLessonId(),
				false);

		// Refreshes the calling page when a callback is available
		if (onCreated != null) {
			onCreated.run();
		}

		// Closes the completed Create Lesson form
		formStage.close();
	}
}
