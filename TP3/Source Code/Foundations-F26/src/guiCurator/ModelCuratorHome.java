package guiCurator;

import database.Database;
import entityClasses.LessonsLearnedList;
import entityClasses.User;

public class ModelCuratorHome {

/*******
 * <p> Title: ModelCuratorHome Class. </p>
 * 
 * <p> Description: The CuratorHome Page Model.  This class is a stub for future expansion.
 * 
 * This class is not used as there is no unique data manipulation for this GUI page.</p>
 * 
 * <p> Copyright: Lynn Robert Carter © 2025 </p>
 * 
 * @author Lynn Robert Carter
 * 
 * @author Venus Ho
 * @author Alexander Murray
 * @author Alan Shimp
 * @author Caleb Beaven
 * @author Krithik Duraisamy
 * 
 * @version 1.00		2025-08-15 Initial version
 * @version 1.01		2025-09-13 Updated JavaDoc description
 * 
 * @version 2.0         2026-10-22 Refactored Role 2 to Curator
 * @version 2.1         2026-11-17 Added lesson publication behavior
 *  
 */

	// Provides access to the shared application database
	private static final Database theDatabase = applicationMain.FoundationsMain.database;

	/******
	 *
	 * <p> Method: getAllLessons() </p>
	 *
	 * <p> Description: Retrieves every lesson for publication review on the
	 * Curator Home page. </p>
	 *
	 * @return all lessons currently stored in the database
	 *
	 */
	protected static LessonsLearnedList getAllLessons() {
		return theDatabase.getAllLessons();
	}

	/******
	 *
	 * <p> Method: publishLesson() </p>
	 *
	 * <p> Description: Verifies the active user's Curator role before publishing
	 * the selected lesson. </p>
	 *
	 * @param activeUser specifies the user requesting the publication change
	 * @param lessonId specifies the lesson that should be published
	 * @return true when the lesson exists and was published; otherwise, false
	 * @throws SecurityException when the active user does not have the Curator role
	 *
	 */
	protected static boolean publishLesson(User activeUser, int lessonId) {
		verifyCurator(activeUser);
		return theDatabase.publishLesson(lessonId);
	}

	/******
	 *
	 * <p> Method: unpublishLesson() </p>
	 *
	 * <p> Description: Verifies the active user's Curator role before removing the
	 * selected lesson from the Viewer collection. </p>
	 *
	 * @param activeUser specifies the user requesting the publication change
	 * @param lessonId specifies the lesson that should be unpublished
	 * @return true when the lesson exists and was unpublished; otherwise, false
	 * @throws SecurityException when the active user does not have the Curator role
	 *
	 */
	protected static boolean unpublishLesson(User activeUser, int lessonId) {
		verifyCurator(activeUser);
		return theDatabase.unpublishLesson(lessonId);
	}

	/******
	 *
	 * <p> Method: verifyCurator() </p>
	 *
	 * <p> Description: Prevents publication methods from running unless the active
	 * user has the Curator role. </p>
	 *
	 * @param activeUser specifies the user whose Curator role is checked
	 * @throws SecurityException when the user is missing or is not a Curator
	 *
	 */
	private static void verifyCurator(User activeUser) {
		if (activeUser == null || !activeUser.getNewCurator()) {
			throw new SecurityException(
					"Only a Curator can change lesson publication status.");
		}
	}

}
