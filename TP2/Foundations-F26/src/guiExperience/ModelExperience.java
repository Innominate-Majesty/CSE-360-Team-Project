package guiExperience;

import database.Database;
import entityClasses.Experience;
import entityClasses.ExperiencesList;
import entityClasses.LessonsLearned;

/*******
 *
 * <p> Title: ModelExperience Class. </p>
 *
 * <p> Description: The Experience Model. This class verifies contributor-owned
 * lessons and provides the Experience data operations required by the Experience
 * View and Controller. </p>
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

public class ModelExperience {

	// Provides access to the shared application database
	private static Database theDatabase = applicationMain.FoundationsMain.database;

	/******
	 *
	 * <p> Constructor: ModelExperience() </p>
	 *
	 * <p> Description: Creates the Experience Model. Its operations are static,
	 * so the application does not need to instantiate this class. </p>
	 *
	 */
	public ModelExperience() {
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
	 * <p> Method: createExperience() </p>
	 *
	 * <p> Description: Persists a new Experience associated with an existing
	 * contributor-owned lesson. </p>
	 *
	 * @param experience Experience entity being created
	 * @return true when the Experience is stored successfully
	 *
	 */
	protected static boolean createExperience(Experience experience) {
		return theDatabase.createExperience(experience);
	}

	/******
	 *
	 * <p> Method: getExperiencesForLesson() </p>
	 *
	 * <p> Description: Retrieves every Experience associated with a lesson for
	 * future Experience-history presentation. </p>
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
	 * <p> Description: Retrieves one Experience using its unique database ID. </p>
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
	 * <p> Method: updateExperience() </p>
	 *
	 * <p> Description: Persists permitted changes to an existing Experience while
	 * retaining the locking foundation for TP3. </p>
	 *
	 * @param experience Experience entity containing updated information
	 * @return true when the Experience is updated successfully
	 *
	 */
	protected static boolean updateExperience(Experience experience) {
		return theDatabase.updateExperience(experience);
	}

	/******
	 *
	 * <p> Method: deleteExperience() </p>
	 *
	 * <p> Description: Permanently deletes one Experience using its unique ID. </p>
	 *
	 * @param experienceId unique identifier of the Experience being deleted
	 * @return true when the Experience is deleted successfully
	 *
	 */
	protected static boolean deleteExperience(int experienceId) {
		return theDatabase.deleteExperience(experienceId);
	}
}
