package guiLessonDetails;

import database.Database;
import entityClasses.ExperiencesList;
import entityClasses.LessonsLearned;

/*******
 *
 * <p> Title: ModelLessonDetails Class. </p>
 *
 * <p> Description: The Lesson Details Model. This class retrieves contributor-owned
 * lessons and their Experience history and performs permanent lesson deletion
 * outside of the JavaFX View and Controller. </p>
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

public class ModelLessonDetails {

	// Provides access to the shared application database
	private static Database theDatabase = applicationMain.FoundationsMain.database;

	/******
	 *
	 * <p> Constructor: ModelLessonDetails() </p>
	 *
	 * <p> Description: Creates the Lesson Details Model. Its operations are static,
	 * so the application does not need to instantiate this class. </p>
	 *
	 */
	public ModelLessonDetails() {
		// Default constructor
	}

	/******
	 *
	 * <p> Method: getContributorLessonById() </p>
	 *
	 * <p> Description: Retrieves a lesson only when it belongs to the specified
	 * contributor. </p>
	 *
	 * @param lessonId unique identifier of the requested lesson
	 * @param author username identifying the active contributor
	 * @return contributor-owned lesson, or null when it is unavailable
	 *
	 */
	protected static LessonsLearned getContributorLessonById(
			int lessonId,
			String author) {
		LessonsLearned lesson = theDatabase.getLessonById(lessonId);
		if (lesson == null || author == null || !author.equals(lesson.getAuthor())) {
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
