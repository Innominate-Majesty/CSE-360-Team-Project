package guiUpdateLesson;

import entityClasses.Experience;
import entityClasses.ExperiencesList;
import entityClasses.LessonsLearned;
import entityClasses.User;
import javafx.stage.Stage;

/*******
 *
 * <p> Title: ControllerUpdateLesson Class. </p>
 *
 * <p> Description: The Update Lesson Controller. This class verifies contributor
 * ownership, validates updated values, and coordinates Lesson and Experience
 * updates between the View and Model. </p>
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

public class ControllerUpdateLesson {

	/******
	 *
	 * <p> Constructor: ControllerUpdateLesson() </p>
	 *
	 * <p> Description: Creates the Update Lesson Controller. Its operations are
	 * static, so the application does not need to instantiate this class. </p>
	 *
	 */
	public ControllerUpdateLesson() {
		// Default constructor
	}

	/******
	 *
	 * <p> Method: displayUpdateLesson() </p>
	 *
	 * <p> Description: Verifies access to the selected lesson, retrieves its
	 * Experiences, and opens the Update Lesson View. </p>
	 *
	 * @param owner window that owns the Update Lesson form
	 * @param user active contributor requesting the update
	 * @param lessonId unique identifier of the lesson being updated
	 * @param onUpdated operation used to refresh the calling page
	 * @param onCancel operation used to return to the previous page
	 *
	 */
	public static void displayUpdateLesson(
			Stage owner,
			User user,
			int lessonId,
			Runnable onUpdated,
			Runnable onCancel) {

		// Reads the active contributor's username
		String author = user == null ? "" : user.getUserName();

		// Retrieves the contributor-owned lesson
		LessonsLearned lesson = ModelUpdateLesson.getContributorLessonById(
				lessonId,
				author);

		// Reports an unavailable or unauthorized lesson
		if (lesson == null) {
			ViewUpdateLesson.showMessage(
					owner,
					"Unable to Update Lesson",
					"Lesson with ID " + lessonId + " does not exist",
					true);
			return;
		}

		// Retrieves every Experience associated with the lesson
		ExperiencesList experiences =
				ModelUpdateLesson.getExperiencesForLesson(lessonId);

		// Displays the populated Update Lesson form
		ViewUpdateLesson.displayUpdateLesson(
				owner,
				lesson,
				experiences,
				onUpdated,
				onCancel);
	}

	/******
	 *
	 * <p> Method: getExperienceById() </p>
	 *
	 * <p> Description: Retrieves an Experience for display only when it belongs to
	 * the lesson currently being updated. </p>
	 *
	 * @param experienceId unique identifier of the requested Experience
	 * @param lessonId unique identifier of the lesson being updated
	 * @return matching Experience, or null when it is unavailable
	 *
	 */

	protected static Experience getExperienceById(int experienceId, int lessonId) {
		Experience experience = ModelUpdateLesson.getExperienceById(experienceId);
		if (experience == null || experience.getLessonId() != lessonId) {
			return null;
		}
		return experience;
	}

	/******
	 *
	 * <p> Method: handleUpdateLesson() </p>
	 *
	 * <p> Description: Validates and persists the updated Lesson Details and the
	 * selected Experience information. Lessons without Experiences can still have
	 * their Lesson Details updated. </p>
	 *
	 * @param formStage window containing the Update Lesson form
	 * @param lesson lesson entity being updated
	 * @param title updated lesson title
	 * @param category updated lesson category
	 * @param problemDescription updated problem description
	 * @param solution updated solution
	 * @param experienceId selected Experience ID, or null when none exists
	 * @param timeText updated Experience time entered as text
	 * @param whatWasDone updated description of what was completed
	 * @param howItWasDone updated description of how the work was completed
	 * @param teamEffort updated description of the team's effort
	 * @param onUpdated operation used to refresh the calling page
	 *
	 */
	
	protected static void handleUpdateLesson(
			Stage formStage,
			LessonsLearned lesson,
			String title,
			String category,
			String problemDescription,
			String solution,
			Integer experienceId,
			String timeText,
			String whatWasDone,
			String howItWasDone,
			String teamEffort,
			Runnable onUpdated) {

		// Removes unnecessary outer spacing from the Lesson Details
		String cleanTitle = title == null ? "" : title.trim();
		String cleanCategory = category == null ? "" : category.trim();
		String cleanProblem = problemDescription == null ? "" : problemDescription.trim();
		String cleanSolution = solution == null ? "" : solution.trim();

		// Validates the Title length
		if (cleanTitle.length() < 5 || cleanTitle.length() > 100) {
			ViewUpdateLesson.showMessage(
					formStage,
					"Unable to Update Lesson",
					"Title must be between 5 and 100 characters",
					true);
			return;
		}

		// Validates the required Lesson Details
		if (cleanCategory.isEmpty() || cleanProblem.isEmpty() || cleanSolution.isEmpty()) {
			ViewUpdateLesson.showMessage(
					formStage,
					"Unable to Update Lesson",
					"Category, problem description, and solution are required",
					true);
			return;
		}

		// Stores the selected Experience when one is available
		Experience selectedExperience = null;

		// Validates and updates the selected Experience values
		if (experienceId != null) {
			double timeSpentHours;
			try {
				timeSpentHours = Double.parseDouble(timeText == null ? "" : timeText.trim());
				if (timeSpentHours < 0) {
					throw new NumberFormatException();
				}
			} catch (NumberFormatException exception) {
				ViewUpdateLesson.showMessage(
						formStage,
						"Unable to Update Lesson",
						"Time spent must be a non-negative number",
						true);
				return;
			}

			// Retrieves and verifies the selected Experience
			selectedExperience = getExperienceById(
					experienceId,
					lesson.getLessonId());

			if (selectedExperience == null) {
				ViewUpdateLesson.showMessage(
						formStage,
						"Unable to Update Experience",
						"The selected Experience does not exist",
						true);
				return;
			}

			// Applies the permitted Experience updates
			selectedExperience.setTimeSpentHours(timeSpentHours);
			selectedExperience.setWhatWasDone(whatWasDone == null ? "" : whatWasDone.trim());
			selectedExperience.setHowItWasDone(howItWasDone == null ? "" : howItWasDone.trim());
			selectedExperience.setTeamEffort(teamEffort == null ? "" : teamEffort.trim());
		}

		// Applies the permitted Lesson Details updates
		lesson.setTitle(cleanTitle);
		lesson.setCategory(cleanCategory);
		lesson.setProblemDescription(cleanProblem);
		lesson.setSolution(cleanSolution);

		// Persists the updated Lesson Details
		if (!ModelUpdateLesson.updateLesson(lesson)) {
			ViewUpdateLesson.showMessage(
					formStage,
					"Unable to Update Lesson",
					"The Lesson Details could not be updated",
					true);
			return;
		}

		// Persists the selected Experience when one exists
		if (selectedExperience != null
				&& !ModelUpdateLesson.updateExperience(selectedExperience)) {
			ViewUpdateLesson.showMessage(
					formStage,
					"Unable to Update Experience",
					"The Lesson Details were updated, but the selected Experience could not be updated",
					true);
			return;
		}

		// Reports the successful update
		ViewUpdateLesson.showMessage(
				formStage,
				"Lesson Updated",
				selectedExperience == null
						? "The Lesson Details were updated successfully"
						: "The Lesson Details and selected Experience were updated successfully",
				false);

		// Refreshes the calling page when a callback is available
		if (onUpdated != null) {
			onUpdated.run();
		}

		// Closes the completed Update Lesson form
		formStage.close();
	}
}
