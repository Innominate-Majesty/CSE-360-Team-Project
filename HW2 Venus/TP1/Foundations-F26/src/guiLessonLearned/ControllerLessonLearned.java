package guiLessonLearned;

import java.sql.SQLException;

import entityClasses.LessonsLearnedList;
import entityClasses.LessonsLearned;
import lessonValidator.LessonValidationResult;

/******
 * 
 * <p> Title: ControllerLessonLearned Class. </p>
 *
 * <p> Description: This ControllerLessonLearned class handles user actions
 * from the Lesson Learned page. </p>
 *
 * @author Venus Ho
 *
 */

public class ControllerLessonLearned {

	// creates the model using the shared database
	private static ModelLessonLearned theModel = new ModelLessonLearned(applicationMain.FoundationsMain.database);

	/******
     * 
	 * <p> Constructor: ControllerLessonLearned() </p>
	 *
	 * <p> Description: Constructs the Lesson Learned controller. </p>
	 *
	 */

	public ControllerLessonLearned() {

		// controller initialization is not needed
	}

    /******
     * 
     * <p> Method: loadLessons() </p>
     *
     * <p> Description: Loads the current contributor's lessons. </p>
     *
     */

    protected static void loadLessons() {

        // attempts to retrieve the lessons
        try {

            // retrieves the contributor's lessons
            LessonsLearnedList lessons = theModel.getLessonsByContributor(ViewLessonLearned.theUser.getUserName());

            // sends the lessons to the view
            ViewLessonLearned.displayLessonList(lessons);
        }

        // handles a database error
        catch (SQLException exception) {

            // prints the database error
            System.err.println("Unable to load lessons: " + exception.getMessage());

            // displays the error state
            ViewLessonLearned.displayLessonList(null);
        }
    }

    /******
     * 
     * <p> Method: performRefresh() </p>
     *
     * <p> Description: Reloads the current contributor's lessons. </p>
     *
     */

    protected static void performRefresh() {

        // loads the lessons again
        loadLessons();
    }

    /******
     * 
     * <p> Method: performSave() </p>
     *
     * <p> Description: Validates and saves the current lesson form. </p>
     *
     */

    protected static void performSave() {

        // builds the lesson from the form
        LessonsLearned lesson = ViewLessonLearned.buildLessonFromForm();

        // validates the lesson
        LessonValidationResult validationResult = theModel.validateLesson(lesson);

        // checks whether validation failed
        if (!validationResult.isValid()) {

            // checks whether a lesson is being created
            if (ViewLessonLearned.selectedLesson == null) {

                // displays the create validation error
                ViewLessonLearned.label_FormStatusMessage.setText("Lesson cannot be created: " + validationResult.getFieldName() + " is missing or invalid.");
            }

            // handles an edited lesson
            else {

                // displays the update validation error
                ViewLessonLearned.label_FormStatusMessage.setText("Changes were not saved: " + validationResult.getFieldName() + " is missing or invalid.");
            }

            // stops the save operation
            return;
        }

        // attempts to save the lesson
        try {

            // checks whether a lesson is being created
            if (ViewLessonLearned.selectedLesson == null) {

                // creates the lesson
                int lessonId = theModel.createLesson(lesson);

                // checks whether the lesson was created
                if (lessonId > 0) {

                    // returns to the lesson list
                    ViewLessonLearned.showLessonList();

                    // displays the success message
                    ViewLessonLearned.label_StatusMessage.setText("Lesson created successfully!");
                }

                // stops after the create operation
                return;
            }

            // updates the lesson
            boolean updated = theModel.updateLesson(lesson);

            // checks whether the lesson was updated
            if (updated) {

                // returns to the lesson list
                ViewLessonLearned.showLessonList();

                // displays the success message
                ViewLessonLearned.label_StatusMessage.setText("Lesson updated successfully.");
            }

            // handles a missing lesson
            else {

                // displays the update failure
                ViewLessonLearned.label_FormStatusMessage.setText("Lesson not found. Changes were not saved.");
            }
        }

        // handles a database error
        catch (SQLException exception) {

            // prints the database error
            System.err.println("Unable to save lesson: " + exception.getMessage());

            // displays the save error
            ViewLessonLearned.label_FormStatusMessage.setText("Unable to save the lesson.");
        }
    }

    /******
     * 
     * <p> Method: performDelete(LessonsLearned lesson) </p>
     *
     * <p> Description: Deletes a lesson belonging to the current contributor. </p>
     *
     * @param lesson specifies the lesson to delete
     *
     */

    protected static void performDelete(LessonsLearned lesson) {

        // attempts to delete the lesson
        try {

            // deletes the contributor's lesson
            boolean deleted = theModel.deleteLesson(lesson.getLessonId(), ViewLessonLearned.theUser.getUserName());

            // checks whether the lesson was deleted
            if (deleted) {

                // returns to the refreshed lesson list
                ViewLessonLearned.showLessonList();

                // displays the success message
                ViewLessonLearned.label_StatusMessage.setText("Lesson deleted successfully.");
            }

            // handles a missing lesson
            else {

                // displays the delete failure
                ViewLessonLearned.label_StatusMessage.setText("Lesson not found. It was not deleted.");
            }
        }

        // handles a database error
        catch (SQLException exception) {

            // prints the database error
            System.err.println("Unable to delete lesson: " + exception.getMessage());

            // displays the delete error
            ViewLessonLearned.label_StatusMessage.setText("Unable to delete the lesson.");
        }
    }

    /******
     * 
     * <p> Method: performReturn() </p>
     *
     * <p> Description: Returns to the Contributor home page. </p>
     *
     */

    protected static void performReturn() {

        // displays the Contributor home page
        guiRole1.ViewRole1Home.displayRole1Home(ViewLessonLearned.theStage, ViewLessonLearned.theUser);
    }

    /******
     * 
     * <p> Method: performLogout() </p>
     *
     * <p> Description: Logs out and returns to the login page. </p>
     *
     */

    protected static void performLogout() {

        // displays the login page
        guiUserLogin.ViewUserLogin.displayUserLogin(ViewLessonLearned.theStage);
    }

    /******
     * 
     * <p> Method: performQuit() </p>
     *
     * <p> Description: Closes the application. </p>
     *
     */

    protected static void performQuit() {

        // closes the application
        System.exit(0);
    }

}