package guiCreateLesson;

import database.Database;
import entityClasses.Experience;
import entityClasses.LessonsLearned;

/*******
 *
 * <p> Title: ModelCreateLesson Class. </p>
 *
 * <p> Description: The Create Lesson Model. This class creates the lesson record
 * and its initial Experience record while keeping database access outside of the
 * JavaFX View and Controller. </p>
 *
 * <p> Copyright: Lynn Robert Carter © 2025 </p>
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

public class ModelCreateLesson {

	// Provides access to the shared application database
	private static Database theDatabase = applicationMain.FoundationsMain.database;

	/******
	 *
	 * <p> Constructor: ModelCreateLesson() </p>
	 *
	 * <p> Description: Creates the Create Lesson Model. Its operations are static,
	 * so the application does not need to instantiate this class. </p>
	 *
	 */
	public ModelCreateLesson() {
		// Default constructor
	}

	/******
	 *
	 * <p> Method: createLessonWithExperience() </p>
	 *
	 * <p> Description: Creates a lesson and then creates its initial Experience
	 * using the generated Lesson ID. The lesson is removed if its initial
	 * Experience cannot be saved. </p>
	 *
	 * @param lesson lesson entity containing the Lesson Details
	 * @param timeSpentHours number of hours recorded for the initial Experience
	 * @param whatWasDone description of what was completed
	 * @param howItWasDone description of how the work was completed
	 * @param teamEffort description of the team's effort
	 * @return true when both records are created successfully
	 *
	 */
	protected static boolean createLessonWithExperience(
			LessonsLearned lesson,
			double timeSpentHours,
			String whatWasDone,
			String howItWasDone,
			String teamEffort) {

		// Attempts to create the Lesson Details record
		if (!theDatabase.createLesson(lesson)) {
			return false;
		}

		// Creates the initial Experience using the generated Lesson ID
		Experience experience = new Experience(
				0,
				lesson.getLessonId(),
				lesson.getAuthor(),
				timeSpentHours,
				whatWasDone,
				howItWasDone,
				teamEffort);

		// Returns success when the initial Experience is saved
		if (theDatabase.createExperience(experience)) {
			return true;
		}

		// Removes the incomplete lesson when its Experience cannot be saved
		theDatabase.deleteLesson(lesson.getLessonId());
		return false;
	}
}
