package guiCuratorFunctions;

import entityClasses.Experience;
import entityClasses.ExperiencesList;
import entityClasses.LessonsLearned;
import entityClasses.User;
import javafx.stage.Stage;

/*******
 *
 * <p> Title: ControllerCuratorFunctions Class. </p>
 *
 * <p> Description: The Curator Functions Controller. This class coordinates Lesson and Experience
 * retrieval, and processes confirmed lesson deletion. </p>
 *
 * @author Venus Ho
 * @author Alexander Murray
 * @author Alan Shimp
 * @author Caleb Beaven
 * @author Krithik Duraisamy
 *
 * @version 1.0         2026-11-27
 *
 */

public class ControllerCuratorFunctions {

	/******
	 *
	 * <p> Constructor: ControllerCuratorFunctions() </p>
	 *
	 * <p> Description: Creates the Lesson Details Controller for Curators. Its operations are
	 * static, so the application does not need to instantiate this class. </p>
	 *
	 */
	public ControllerCuratorFunctions() {
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
	 * @param user active curator requesting the lesson
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
		//String author = user == null ? "" : user.getUserName();

		// Retrieves the contributor-owned lesson
		LessonsLearned lesson = ModelCuratorFunctions.getLessonById(
				lessonId);

		// Reports an unavailable or unauthorized lesson
		if (lesson == null) {
			ViewCuratorFunctions.showMessage(
					owner,
					"Lesson Unavailable",
					"Lesson with ID " + lessonId + " does not exist",
					true);
			return;
		}

		// Retrieves every Experience associated with the lesson
		ExperiencesList experiences =
				ModelCuratorFunctions.getExperiencesForLesson(lessonId);

		// Displays the Lesson Details and Experience history
		ViewCuratorFunctions.displayLessonDetails(
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
		boolean deleted = ModelCuratorFunctions.deleteLesson(lesson.getLessonId());

		// Reports a database deletion failure
		if (!deleted) {
			ViewCuratorFunctions.showMessage(
					detailStage,
					"Unable to Delete Lesson",
					"Lesson with ID " + lesson.getLessonId() + " does not exist",
					true);
			return;
		}

		// Reports the successful deletion
		ViewCuratorFunctions.showMessage(
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
	
	/******
	 * 
	 * <p> Method: handleLockUnlockLesson(LessonsLearned lesson, String field) </p>
	 * 
	 * <p> Description: Locks or unlocks the fields of a lesson based on input from the View. </p>
	 * 
	 * @param lesson The lesson to perform the operation on
	 * @param field The field to be updated
	 * 
	 */
	protected static void handleLockUnlockLesson(LessonsLearned lesson, String field) {
		switch (field) {
		case "lesson":
			lesson.setLocked(!lesson.isLocked());
			break;
			
		case "title":
			lesson.setTitleLocked(!lesson.isTitleLocked());
			break;
			
		case "category":
			lesson.setCategoryLocked(!lesson.isCategoryLocked());
			break;
			
		case "problem":
			lesson.setProblemDescriptionLocked(!lesson.isProblemDescriptionLocked());
			break;
			
		case "solution":
			lesson.setSolutionLocked(!lesson.isSolutionLocked());
			break;
			
		default:
			throw new IllegalArgumentException("Attempted to toggle lock of invalid field");
		}
	}
	
	/******
	 * 
	 * <p> Method: handleLockUnlockExperience(Experience experience, String field) </p>
	 * 
	 * <p> Description: Locks or unlocks the fields of an experienced based on input from the View. </p>
	 * 
	 * @param experience The experience to perform the operation on
	 * @param field The field to be updated
	 * 
	 */
	protected static void handleLockUnlockExperience(Experience experience, String field) {
		switch (field) {
		case "experience":
			experience.lockUnlockExperience();
			break;
			
		case "time":
			experience.lockUnlockTime(!experience.getTimeLocked());
			break;
			
		case "what":
			experience.lockUnlockWhat(!experience.getWhatLocked());
			break;
			
		case "how":
			experience.lockUnlockHow(!experience.getHowLocked());
			break;
			
		case "effort":
			experience.lockUnlockEffort(!experience.getEffortLocked());
			break;
			
		default:
			throw new IllegalArgumentException("Attempted to toggle lock of invalid field");
		}
	}
}
