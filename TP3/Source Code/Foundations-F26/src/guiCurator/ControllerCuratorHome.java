package guiCurator;

import entityClasses.LessonsLearned;
import entityClasses.LessonsLearnedList;

/*******
 * <p> Title: ControllerCuratorHome Class. </p>
 * 
 * <p> Description: The Java/FX-based Curator Home Page.  This class provides the controller
 * actions basic on the user's use of the JavaFX GUI widgets defined by the View class.
 * 
 * This page is a stub for establish future roles for the application.
 * 
 * The class has been written assuming that the View or the Model are the only class methods that
 * can invoke these methods.  This is why each has been declared at "protected".  Do not change any
 * of these methods to public.</p>
 * 
 * <p> Copyright: Lynn Robert Carter © 2025 </p>
 * 
 * @author Lynn Robert Carter
 * @author Venus Ho
 * @author Alexander Murray
 * @author Alan Shimp
 * @author Caleb Beaven
 * @author Krithik Duraisamy
 * 
 * @version 1.00		2025-08-17 Initial version
 * @version 1.01		2025-09-16 Update Javadoc documentation *  
 * 
 * @version 2.0			2026-10-22 Refactored Role 2 to Curator
 * 
 * @version 2.1			2026-11-17 Added lesson publication behavior
 */

public class ControllerCuratorHome {
	
	/*-*******************************************************************************************

	User Interface Actions for this page
	
	This controller is not a class that gets instantiated.  Rather, it is a collection of protected
	static methods that can be called by the View (which is a singleton instantiated object) and 
	the Model is often just a stub, or will be a singleton instantiated object.
	
	 */

	/**
	 * Default constructor is not used.
	 */
	public ControllerCuratorHome() {
	}

	/******
	 *
	 * <p> Method: performRefreshLessons() </p>
	 *
	 * <p> Description: Loads every lesson from the Curator model and refreshes the
	 * review list displayed by the View. </p>
	 *
	 */
	protected static void performRefreshLessons() {
		LessonsLearnedList lessons = ModelCuratorHome.getAllLessons();
		ViewCuratorHome.displayLessons(lessons);

		if (lessons.isEmpty()) {
			ViewCuratorHome.showStatus(
					"No lessons are currently available for review.", false);
		} else {
			ViewCuratorHome.showStatus(
					"Select a lesson to change its publication status.", false);
		}
	}

	/******
	 *
	 * <p> Method: performSelectLesson() </p>
	 *
	 * <p> Description: Stores the lesson selected by the Curator and updates the
	 * publication action displayed by the View. </p>
	 *
	 * @param lesson specifies the lesson selected for review
	 *
	 */
	protected static void performSelectLesson(LessonsLearned lesson) {
		ViewCuratorHome.setSelectedLesson(lesson);
	}

	/******
	 *
	 * <p> Method: performTogglePublication() </p>
	 *
	 * <p> Description: Publishes an unpublished lesson or unpublishes a published
	 * lesson after the model verifies the active user's Curator role. </p>
	 *
	 */
	protected static void performTogglePublication() {
		LessonsLearned selectedLesson = ViewCuratorHome.getSelectedLesson();

		// Stops when the Curator has not selected a lesson
		if (selectedLesson == null) {
			ViewCuratorHome.showStatus(
					"Select a lesson before using Publish.", true);
			return;
		}

		try {
			// Stores the status that should be applied to the selected lesson
			boolean shouldPublish = !selectedLesson.isPublished();

			// Calls the matching publication method through the Model
			boolean updated = shouldPublish
					? ModelCuratorHome.publishLesson(
							ViewCuratorHome.theUser,
							selectedLesson.getLessonId())
					: ModelCuratorHome.unpublishLesson(
							ViewCuratorHome.theUser,
							selectedLesson.getLessonId());

			if (!updated) {
				ViewCuratorHome.showStatus(
						"The selected lesson could not be updated.", true);
				return;
			}

			// Refreshes the list so it displays the saved database status
			performRefreshLessons();
			ViewCuratorHome.showStatus(
					shouldPublish
							? "Lesson published successfully."
							: "Lesson unpublished successfully.",
					false);
		} catch (IllegalArgumentException | SecurityException exception) {
			ViewCuratorHome.showStatus(exception.getMessage(), true);
		}
	}

	/**********
	 * <p> Method: performUpdate() </p>
	 * 
	 * <p> Description: This method directs the user to the User Update Page so the user can change
	 * the user account attributes. </p>
	 * 
	 */
	protected static void performUpdate () {
		guiUserUpdate.ViewUserUpdate.displayUserUpdate(ViewCuratorHome.theStage, ViewCuratorHome.theUser);
	}	

	/**********
	 * <p> Method: performLogout() </p>
	 * 
	 * <p> Description: This method logs out the current user and proceeds to the normal login
	 * page where existing users can log in or potential new users with a invitation code can
	 * start the process of setting up an account. </p>
	 * 
	 */
	protected static void performLogout() {
		guiUserLogin.ViewUserLogin.displayUserLogin(ViewCuratorHome.theStage);
	}
	
	/**********
	 * <p> Method: performQuit() </p>
	 * 
	 * <p> Description: This method terminates the execution of the program.  It leaves the
	 * database in a state where the normal login page will be displayed when the application is
	 * restarted.</p>
	 * 
	 */	
	protected static void performQuit() {
		System.exit(0);
	}
}
