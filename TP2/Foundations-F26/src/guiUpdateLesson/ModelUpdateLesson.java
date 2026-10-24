package guiUpdateLesson;

import database.Database;
import entityClasses.Experience;
import entityClasses.ExperiencesList;
import entityClasses.LessonsLearned;

/*******
 *
 * <p> Title: ModelUpdateLesson Class. </p>
 *
 * <p> Description: The Update Lesson Model. This class retrieves contributor-owned
 * lessons and their Experiences and persists permitted updates outside of the
 * JavaFX View and Controller. </p>
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

public class ModelUpdateLesson {

	// Provides access to the shared application database
	private static Database theDatabase = applicationMain.FoundationsMain.database;

	/******
	 *
	 * <p> Constructor: ModelUpdateLesson() </p>
	 *
	 * <p> Description: Creates the Update Lesson Model. Its operations are static,
	 * so the application does not need to instantiate this class. </p>
	 *
	 */
	public ModelUpdateLesson() {
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
	 * <p> Method: getExperienceById() </p>
	 *
	 * <p> Description: Retrieves one Experience by its unique database ID. </p>
	 *
	 * @param experienceId unique identifier of the requested Experience
	 * @return matching Experience, or null when it does not exist
	 *
	 */
	protected static Experience getExperienceById(int experienceId) {
		return theDatabase.getExperienceById(experienceId);
	}

	/******
	 *
	 * <p> Method: updateLesson() </p>
	 *
	 * <p> Description: Persists permitted changes to the selected lesson. </p>
	 *
	 * @param lesson lesson entity containing the updated Lesson Details
	 * @return true when the lesson is updated successfully
	 *
	 */
	protected static boolean updateLesson(LessonsLearned lesson) {
		return theDatabase.updateLesson(lesson);
	}

	/******
	 *
	 * <p> Method: updateExperience() </p>
	 *
	 * <p> Description: Persists permitted changes to the selected Experience. The
	 * existing locking mechanism remains available for future TP3 behavior. </p>
	 *
	 * @param experience Experience entity containing the updated information
	 * @return true when the Experience is updated successfully
	 *
	 */
	protected static boolean updateExperience(Experience experience) {
		return theDatabase.updateExperience(experience);
	}
}
