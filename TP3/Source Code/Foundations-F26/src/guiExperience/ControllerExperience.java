package guiExperience;

import entityClasses.Experience;
import entityClasses.LessonsLearned;
import entityClasses.User;
import javafx.stage.Stage;

/*******
 *
 * <p> Title: ControllerExperience Class. </p>
 *
 * <p> Description: The Experience Controller. This class validates the Add
 * Experience form, verifies lesson ownership, and coordinates Experience creation
 * between the View and Model. </p>
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

public class ControllerExperience {

	/******
	 *
	 * <p> Constructor: ControllerExperience() </p>
	 *
	 * <p> Description: Creates the Experience Controller. Its operations are static,
	 * so the application does not need to instantiate this class. </p>
	 *
	 */
	public ControllerExperience() {
		// Default constructor
	}

	/******
	 *
	 * <p> Method: displayAddExperience() </p>
	 *
	 * <p> Description: Opens the Add Experience View with an optional Lesson ID
	 * already selected by the Contributor Home page. </p>
	 *
	 * @param owner window that owns the Add Experience form
	 * @param user active contributor creating the Experience
	 * @param lessonId optional Lesson ID used to populate the form
	 * @param onCreated operation used to refresh the calling page
	 *
	 */
	public static void displayAddExperience(
			Stage owner,
			User user,
			Integer lessonId,
			Runnable onCreated) {
		ViewExperience.displayAddExperience(
				owner,
				user,
				lessonId,
				onCreated);
	}

	/******
	 *
	 * <p> Method: handleCreateExperience() </p>
	 *
	 * <p> Description: Validates the Add Experience form, verifies that the lesson
	 * belongs to the active contributor, and creates the Experience record. </p>
	 *
	 * @param formStage window containing the Add Experience form
	 * @param user active contributor creating the Experience
	 * @param lessonIdText Lesson ID entered by the contributor
	 * @param timeText Time Spent value entered as text
	 * @param whatWasDone description of what was completed
	 * @param howItWasDone description of how the work was completed
	 * @param teamEffort description of the team's effort
	 * @param onCreated operation used to refresh the calling page
	 *
	 */
	protected static void handleCreateExperience(
			Stage formStage,
			User user,
			String lessonIdText,
			String timeText,
			String whatWasDone,
			String howItWasDone,
			String teamEffort,
			Runnable onCreated) {

		// Stores the parsed Lesson ID
		int lessonId;

		// Validates the Lesson ID
		try {
			lessonId = Integer.parseInt(
					lessonIdText == null ? "" : lessonIdText.trim());
			if (lessonId <= 0) {
				throw new NumberFormatException();
			}
		} catch (NumberFormatException exception) {
			ViewExperience.showMessage(
					formStage,
					"Unable to Save Experience",
					"Lesson ID must be a positive integer",
					true);
			return;
		}

		// Reads the active contributor's username
		String author = user == null ? "" : user.getUserName();

		// Verifies that the lesson exists and belongs to the contributor
		LessonsLearned lesson = ModelExperience.getContributorLessonById(
				lessonId,
				author);
		if (lesson == null) {
			ViewExperience.showMessage(
					formStage,
					"Unable to Save Experience",
					"Lesson with ID " + lessonId + " does not exist",
					true);
			return;
		}

		// Stores the parsed Time Spent value
		double timeSpentHours;

		// Validates the Time Spent value
		try {
			timeSpentHours = Double.parseDouble(
					timeText == null ? "" : timeText.replace("hrs", "").trim());
			if (timeSpentHours < 0) {
				throw new NumberFormatException();
			}
		} catch (NumberFormatException exception) {
			ViewExperience.showMessage(
					formStage,
					"Unable to Save Experience",
					"Time spent must be a non-negative numeric value in hours",
					true);
			return;
		}

		// Creates the Experience entity
		Experience experience = new Experience(
				0,
				lessonId,
				author,
				timeSpentHours,
				whatWasDone == null ? "" : whatWasDone.trim(),
				howItWasDone == null ? "" : howItWasDone.trim(),
				teamEffort == null ? "" : teamEffort.trim());

		// Attempts to save the Experience
		if (!ModelExperience.createExperience(experience)) {
			ViewExperience.showMessage(
					formStage,
					"Unable to Save Experience",
					"The Experience could not be saved",
					true);
			return;
		}

		// Reports the generated Experience ID
		ViewExperience.showMessage(
				formStage,
				"Experience Saved",
				"Experience " + experience.getExperienceId()
						+ " was saved for Lesson ID " + lessonId,
				false);

		// Refreshes the calling page when a callback is available
		if (onCreated != null) {
			onCreated.run();
		}

		// Closes the completed Add Experience form
		formStage.close();
	}
}
