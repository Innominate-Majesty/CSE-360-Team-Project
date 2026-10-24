package guiLessonDetails;

import entityClasses.ExperiencesList;
import entityClasses.LessonsLearned;
import entityClasses.User;
import javafx.stage.Stage;

/*******
 *
 * <p> Title: ControllerLessonDetails Class. </p>
 *
 * <p> Description: The Lesson Details Controller. This class verifies contributor
 * ownership, coordinates Lesson and Experience retrieval, and processes confirmed
 * lesson deletion. </p>
 *
 * @author Alexander Murray
 * @author Venus Ho
 * @author Alan Shimp
 * @author Caleb Beaven
 * @author Krithik Duraisamy
 *
 * @version 1.00		2026-10-23 Initial version
 *
 */

public class ControllerLessonDetails {

	/******
	 *
	 * <p> Constructor: ControllerLessonDetails() </p>
	 *
	 * <p> Description: Creates the Lesson Details Controller. Its operations are
	 * static, so the application does not need to instantiate this class. </p>
	 *
	 */
	public ControllerLessonDetails() {
		// Default constructor
	}

	/******
	 *
	 * <p> Method: displayLessonDetails() </p>
	 *
	 * <p> Description: Verifies access to the requested lesson, retrieves its
	 * Experience history, and opens the Lesson Details View. </p>
	 *
	 * @param owner window that owns the Lesson Details popup
	 * @param user active contributor requesting the lesson
	 * @param lessonId unique identifier of the requested lesson
	 * @param onUpdate operation used to open the Update Lesson form
	 * @param onDeleted operation used to refresh the calling page after deletion
	 *
	 */
	public static void displayLessonDetails(
			Stage owner,
			User user,
			int lessonId,
			Runnable onUpdate,
			Runnable onDeleted) {

		// Reads the active contributor's username
		String author = user == null ? "" : user.getUserName();

		// Retrieves the contributor-owned lesson
		LessonsLearned lesson = ModelLessonDetails.getContributorLessonById(
				lessonId,
				author);

		// Reports an unavailable or unauthorized lesson
		if (lesson == null) {
			ViewLessonDetails.showMessage(
					owner,
					"Lesson Unavailable",
					"Lesson with ID " + lessonId + " does not exist",
					true);
			return;
		}

		// Retrieves every Experience associated with the lesson
		ExperiencesList experiences =
				ModelLessonDetails.getExperiencesForLesson(lessonId);

		// Displays the Lesson Details and Experience history
		ViewLessonDetails.displayLessonDetails(
				owner,
				lesson,
				experiences,
				onUpdate,
				onDeleted);
	}

	/******
	 *
	 * <p> Method: handleDeleteLesson() </p>
	 *
	 * <p> Description: Permanently deletes the selected lesson after confirmation,
	 * closes the details window, and refreshes the calling page. </p>
	 *
	 * @param detailStage Lesson Details window being closed after deletion
	 * @param lesson lesson selected for deletion
	 * @param onDeleted operation used to refresh the calling page
	 *
	 */
	protected static void handleDeleteLesson(
			Stage detailStage,
			LessonsLearned lesson,
			Runnable onDeleted) {

		// Attempts to delete the lesson and associated Experiences
		boolean deleted = ModelLessonDetails.deleteLesson(lesson.getLessonId());

		// Reports a database deletion failure
		if (!deleted) {
			ViewLessonDetails.showMessage(
					detailStage,
					"Unable to Delete Lesson",
					"Lesson with ID " + lesson.getLessonId() + " does not exist",
					true);
			return;
		}

		// Reports the successful deletion
		ViewLessonDetails.showMessage(
				detailStage,
				"Lesson Deleted",
				"The lesson and its associated Experiences were deleted successfully",
				false);

		// Closes the deleted lesson's details
		detailStage.close();

		// Refreshes the calling page when a callback is available
		if (onDeleted != null) {
			onDeleted.run();
		}
	}
}
