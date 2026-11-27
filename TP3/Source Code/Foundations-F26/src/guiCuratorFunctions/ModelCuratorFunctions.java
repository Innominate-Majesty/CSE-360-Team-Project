package guiCuratorFunctions;

import database.Database;
import entityClasses.ExperiencesList;
import entityClasses.LessonsLearned;

/*******
 *
 * <p> Title: ModelCuratorFunctions Class. </p>
 *
 * <p> Description: The Curator Functions Model. This class retrieves lessons and their Experience
 * history and performs permanent lesson deletion outside of the JavaFX View and Controller. </p>
 *
 * @author Venus Ho
 * @author Alexander Murray
 * @author Alan Shimp
 * @author Caleb Beaven
 * @author Krithik Duraisamy
 *
 * @version 1.0         2026-10-23 Initial version
 *
 */

public class ModelCuratorFunctions {

	// Provides access to the shared application database
	private static Database theDatabase = applicationMain.FoundationsMain.database;

	/******
	 *
	 * <p> Constructor: ModelCuratorFunctions() </p>
	 *
	 * <p> Description: Creates the Curator Functions Model. Its operations are static,
	 * so the application does not need to instantiate this class. </p>
	 *
	 */
	public ModelCuratorFunctions() {
		// Default constructor
	}

	/******
	 *
	 * <p> Method: getLessonById() </p>
	 *
	 * <p> Description: Retrieves a lesson. </p>
	 *
	 * @param lessonId unique identifier of the requested lesson
	 * @return lesson, or null when it is unavailable
	 *
	 */
	protected static LessonsLearned getLessonById(
			int lessonId) {
		LessonsLearned lesson = theDatabase.getLessonById(lessonId);
		if (lesson == null) {
			return null;
		}
		return lesson;
	}

	/******
	 *
	 * <p> Method: getExperiencesForLesson() </p>
	 *
	 * <p> Description: Retrieves every Experience associated with a lesson. </p>
	 *
	 * @param lessonId unique identifier of the associated lesson
	 * @return collection containing the associated Experiences
	 *
	 */
	protected static ExperiencesList getExperiencesForLesson(int lessonId) {
		return theDatabase.getAllExperiencesForLesson(lessonId);
	}

	/******
	 *
	 * <p> Method: deleteLesson() </p>
	 *
	 * <p> Description: Permanently deletes the selected lesson and its associated
	 * Experience records through the shared database. </p>
	 *
	 * @param lessonId unique identifier of the lesson being deleted
	 * @return true when the lesson is deleted successfully
	 *
	 */
	protected static boolean deleteLesson(int lessonId) {
		return theDatabase.deleteLesson(lessonId);
	}
}
