package guiLessonLearned;

import database.Database;

import lessonValidator.LessonValidator;
import lessonValidator.LessonValidationResult;

import entityClasses.LessonsLearned;
import entityClasses.LessonsLearnedList;

import java.sql.SQLException;

/******
 * 
 * <p> Title: ModelLessonLearned Class. </p>
 *
 * <p> Description: This ModelLessonLearned class validates lesson information
 * and requests lesson database operations. </p>
 *
 * @author Venus Ho
 *
 */

public class ModelLessonLearned {

	// stores the shared database
	private Database theDatabase;

	// stores the lesson validator
	private LessonValidator theValidator;

	/******
     * 
	 * <p> Constructor: ModelLessonLearned(Database database) </p>
	 *
	 * <p> Description: Constructs the model using the shared database. </p>
	 *
	 * @param database specifies the shared application database
	 *
	 */

	public ModelLessonLearned(Database database) {

		// stores the shared database
		this.theDatabase = database;

		// creates the lesson validator
		this.theValidator = new LessonValidator();
	}

    /******
     * 
     * <p> Method: validateLesson(LessonsLearned lesson) </p>
     *
     * <p> Description: Validates the required lesson fields. </p>
     *
     * @param lesson specifies the lesson to validate
     *
     * @return the lesson validation result
     *
     */

    public LessonValidationResult validateLesson(LessonsLearned lesson) {

        // returns the validation result
        return theValidator.validate(lesson);
    }

    /******
     * 
     * <p> Method: createLesson(LessonsLearned lesson) </p>
     *
     * <p> Description: Validates and creates a lesson. </p>
     *
     * @param lesson specifies the lesson to create
     *
     * @return the generated lesson ID, or -1 when validation fails
     *
     * @throws SQLException when the lesson cannot be stored
     *
     */

    public int createLesson(LessonsLearned lesson) throws SQLException {

        // validates the lesson
        LessonValidationResult validationResult = validateLesson(lesson);

        // checks whether validation failed
        if (!validationResult.isValid()) {

            // returns failure for invalid information
            return -1;
        }

        // creates the lesson
        return theDatabase.createLesson(lesson);
    }

    /******
     * 
     * <p> Method: getLessonById(int lessonId, String contributorId) </p>
     *
     * <p> Description: Retrieves one lesson belonging to the contributor. </p>
     *
     * @param lessonId specifies the lesson ID
     * @param contributorId specifies the contributor username
     *
     * @return the lesson when found; otherwise, null
     *
     * @throws SQLException when the lesson cannot be retrieved
     *
     */

    public LessonsLearned getLessonById(int lessonId, String contributorId) throws SQLException {

        // returns the requested lesson
        return theDatabase.getLessonById(lessonId, contributorId);
    }

    /******
     * 
     * <p> Method: getLessonsByContributor(String contributorId) </p>
     *
     * <p> Description: Retrieves every lesson belonging to the contributor. </p>
     *
     * @param contributorId specifies the contributor username
     *
     * @return the contributor's lessons
     *
     * @throws SQLException when the lessons cannot be retrieved
     *
     */

    public LessonsLearnedList getLessonsByContributor(String contributorId) throws SQLException {

        // returns the contributor's lessons
        return theDatabase.getLessonsByContributor(contributorId);
    }

    /******
     * 
     * <p> Method: updateLesson(LessonsLearned lesson) </p>
     *
     * <p> Description: Validates and updates a lesson. </p>
     *
     * @param lesson specifies the lesson to update
     *
     * @return true when the lesson was updated; otherwise, false
     *
     * @throws SQLException when the lesson cannot be updated
     *
     */

    public boolean updateLesson(LessonsLearned lesson) throws SQLException {

        // validates the lesson
        LessonValidationResult validationResult = validateLesson(lesson);

        // checks whether validation failed
        if (!validationResult.isValid()) {

            // returns failure for invalid information
            return false;
        }

        // updates the lesson
        return theDatabase.updateLesson(lesson);
    }

    /******
     * 
     * <p> Method: deleteLesson(int lessonId, String contributorId) </p>
     *
     * <p> Description: Deletes one lesson belonging to the contributor. </p>
     *
     * @param lessonId specifies the lesson ID
     * @param contributorId specifies the contributor username
     *
     * @return true when the lesson was deleted; otherwise, false
     *
     * @throws SQLException when the lesson cannot be deleted
     *
     */

    public boolean deleteLesson(int lessonId, String contributorId)
            throws SQLException {

        // deletes the lesson
        return theDatabase.deleteLesson(lessonId, contributorId);
    }

}