package guiLessonLearned;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import entityClasses.LessonsLearned;
import entityClasses.LessonsLearnedList;
import lessonValidator.LessonValidationResult;

import database.Database;

import java.sql.SQLException;

/******
 * 
 * <p> Title: ModelLessonLearnedTest Class. </p>
 *
 * <p> Description: This class tests lesson validation and CRUD model behavior. </p>
 *
 * @author Venus Ho
 *
 */

class ModelLessonLearnedTest {

	// stores the controlled database
    private TestingDatabase testingDatabase;

    // stores the model being tested
	private ModelLessonLearned model;

	/******
	 * 
	 * <p> Method: setUp() </p>
	 *
	 * <p> Description: Creates the model before each test. </p>
	 *
	 */

	@BeforeEach
	void setUp() {

		// creates the controlled database
        testingDatabase = new TestingDatabase();

        // creates the model using the controlled database
        model = new ModelLessonLearned(testingDatabase);
	}

	/******
	 * 
	 * <p> Method: createValidLesson() </p>
	 *
	 * <p> Description: Creates a lesson containing valid field values. </p>
	 *
	 * @return a valid lesson
	 *
	 */

	private LessonsLearned createValidLesson() {

		// stores the current time
		LocalDateTime now = LocalDateTime.now();

		// returns a valid lesson
		return new LessonsLearned(
				1,
				"contributor",
				"Improving team communication",
				"The team was completing a group project.",
				"Tasks would be completed before the deadline.",
				"Several tasks were completed late.",
				"Task ownership was unclear.",
				"The project schedule was delayed.",
				"Assign one owner to every task.",
				now,
				now);
	}

    /******
	 * 
	 * <p> Method: testValidLesson() </p>
	 *
	 * <p> Description: Tests that a complete lesson passes validation. </p>
	 *
	 * <p> Note: Test Case 1: Valid - Create a lesson learned. </p>
	 *
	 */

	@Test
	void testValidLesson() {

		// creates a valid lesson
		LessonsLearned lesson = createValidLesson();

		// validates the lesson
		LessonValidationResult result = model.validateLesson(lesson);

		// verifies that validation succeeded
		assertTrue(result.isValid());

        // verifies that no field caused an error
        assertEquals("", result.getFieldName());

        // verifies the successful validation message
        assertEquals("Lesson information is valid.", result.getMessage());
	}

    /******
	 * 
	 * <p> Method: testMissingRequiredField(String fieldKey,
	 * String expectedFieldName) </p>
	 *
	 * <p> Description: Tests that each required lesson field rejects
	 * whitespace-only values. </p>
	 *
	 * <p> Note: Test Case 2: Invalid - Validate a new lesson before creation. </p>
	 *
	 * @param fieldKey specifies the field to clear
	 *
	 * @param expectedFieldName specifies the reported field name
	 *
	 */

	@ParameterizedTest
	@CsvSource({
			"title, Title",
			"context, Context",
			"expectedOutcome, Expected Outcome",
			"whatHappened, What Happened",
			"rootCause, Root Cause",
			"impact, Impact",
			"recommendation, Recommendation"
	})
    
	void testMissingRequiredField(
			String fieldKey,
			String expectedFieldName) {

		// creates a valid lesson
		LessonsLearned lesson = createValidLesson();

		// clears the selected field
		switch (fieldKey) {
			case 
                "title" -> lesson.setTitle("   ");
			case 
                "context" -> lesson.setContext("   ");
			case 
                "expectedOutcome" -> lesson.setExpectedOutcome("   ");
			case 
                "whatHappened" -> lesson.setWhatHappened("   ");
			case 
                "rootCause" -> lesson.setRootCause("   ");
			case 
                "impact" -> lesson.setImpact("   ");
			case 
                "recommendation" -> lesson.setRecommendation("   ");
			default 
                -> fail("Unknown lesson field: " + fieldKey);
		}

		// validates the lesson
		LessonValidationResult result = model.validateLesson(lesson);

		// verifies that validation failed
		assertFalse(result.isValid());

		// verifies the reported field
		assertEquals(expectedFieldName, result.getFieldName());

		// verifies the validation message
		assertEquals(expectedFieldName + " is missing or invalid.", result.getMessage());
	}

    /******
	 * 
	 * <p> Method: testNullLesson() </p>
	 *
	 * <p> Description: Tests that a null lesson fails validation. </p>
	 *
	 * <p> Note: Test Case 2: Invalid - Validate a new lesson before creation. </p>
	 *
	 */

	@Test
	void testNullLesson() {

		// validates a null lesson
		LessonValidationResult result = model.validateLesson(null);

		// verifies that validation failed
		assertFalse(result.isValid());

		// verifies the reported field
		assertEquals("Lesson", result.getFieldName());

		// verifies the validation message
		assertEquals("Lesson is missing or invalid.", result.getMessage());

	}

    /******
	 * 
	 * <p> Title: TestingDatabase Class. </p>
	 *
	 * <p> Description: This class provides controlled database behavior
	 * without changing the real database. </p>
	 *
	 */

	private static class TestingDatabase extends Database {

		// stores the created lesson
		private LessonsLearned createdLesson;

        // stores the lesson returned by a read operation
        private LessonsLearned returnedLesson;

        // stores the requested lesson ID
        private int requestedLessonId;

        // stores the requested contributor
        private String requestedContributor;

        // stores the lesson list returned by a read operation
        private LessonsLearnedList returnedLessons;

        // stores the lesson sent for updating
        private LessonsLearned updatedLesson;

        // stores the controlled update result
        private boolean updateResult;

        // stores the lesson ID sent for deletion
        private int deletedLessonId;

        // stores the contributor sent for deletion
        private String deletedContributor;

        // stores the controlled delete result
        private boolean deleteResult;

		/******
		 * 
		 * <p> Method: createLesson(LessonsLearned lesson) </p>
		 *
		 * <p> Description: Stores a lesson for the create test. </p>
		 *
		 * @param lesson specifies the lesson to store
		 *
		 * @return the generated lesson ID
		 *
		 */

		@Override
		public int createLesson(LessonsLearned lesson) {

			// stores the created lesson
			createdLesson = lesson;

			// returns a controlled lesson ID
			return 10;
		}

        /******
		 * 
		 * <p> Method: getLessonById(int lessonId, String contributorId) </p>
		 *
		 * <p> Description: Returns a controlled lesson for the read test. </p>
		 *
		 * @param lessonId specifies the requested lesson ID
		 *
		 * @param contributorId specifies the requested contributor
		 *
		 * @return the controlled lesson
		 *
		 */

		@Override
		public LessonsLearned getLessonById(int lessonId, String contributorId) {

			// stores the requested lesson ID
			requestedLessonId = lessonId;

			// stores the requested contributor
			requestedContributor = contributorId;

			// returns the controlled lesson
			return returnedLesson;
		}

        /******
		 * 
		 * <p> Method: getLessonsByContributor(String contributorId) </p>
		 *
		 * <p> Description: Returns a controlled contributor lesson list. </p>
		 *
		 * @param contributorId specifies the requested contributor
		 *
		 * @return the controlled lesson list
		 *
		 */

		@Override
		public LessonsLearnedList getLessonsByContributor(String contributorId) {

			// stores the requested contributor
			requestedContributor = contributorId;

			// returns the controlled lesson list
			return returnedLessons;
		}

        /******
         * 
         * <p> Method: updateLesson(LessonsLearned lesson) </p>
         *
         * <p> Description: Stores a lesson for the update test. </p>
         *
         * @param lesson specifies the lesson to update
         *
         * @return the controlled update result
         *
         */

        @Override
        public boolean updateLesson(LessonsLearned lesson) {

            // stores the updated lesson
            updatedLesson = lesson;

            // returns the controlled result
            return updateResult;
        }

        /******
		 * 
		 * <p> Method: deleteLesson(int lessonId,
		 * String contributorId) </p>
		 *
		 * <p> Description: Stores deletion information for the delete test. </p>
		 *
		 * @param lessonId specifies the lesson to delete
		 *
		 * @param contributorId specifies the lesson owner
		 *
		 * @return the controlled delete result
		 *
		 */

		@Override
		public boolean deleteLesson(
				int lessonId,
				String contributorId) {

			// stores the deleted lesson ID
			deletedLessonId = lessonId;

			// stores the lesson owner
			deletedContributor = contributorId;

			// returns the controlled result
			return deleteResult;
		}

	}

	/******
	 * 
	 * <p> Method: testCreateValidLesson() </p>
	 *
	 * <p> Description: Tests that the model creates a valid lesson. </p>
	 *
	 * <p> Note: Test Case 1: Valid - Create a lesson learned. </p>
	 *
	 * @throws SQLException when the create operation fails
	 *
	 */

	@Test
	void testCreateValidLesson() throws SQLException {

		// creates a valid lesson
		LessonsLearned lesson = createValidLesson();

		// creates the lesson through the model
		int lessonId = model.createLesson(lesson);

		// verifies the generated lesson ID
		assertEquals(10, lessonId);

		// verifies that the database received the lesson
		assertSame(lesson, testingDatabase.createdLesson);
	}

    /******
	 * 
	 * <p> Method: testCreateInvalidLesson() </p>
	 *
	 * <p> Description: Tests that the model does not create an invalid lesson. </p>
	 *
	 * <p> Note: Test Case 2: Invalid - Validate a new lesson before creation. </p>
	 *
	 * @throws SQLException when the create operation fails
	 *
	 */

	@Test
	void testCreateInvalidLesson() throws SQLException {

		// creates a valid lesson
		LessonsLearned lesson = createValidLesson();

		// removes the required title
		lesson.setTitle("");

		// attempts to create the lesson
		int lessonId = model.createLesson(lesson);

		// verifies the failure result
		assertEquals(-1, lessonId);

		// verifies that the database was not called
		assertNull(testingDatabase.createdLesson);
	}

    /******
	 * 
	 * <p> Method: testGetLessonById() </p>
	 *
	 * <p> Description: Tests retrieving a lesson by its ID and contributor. </p>
	 *
	 * <p> Note: Test Case 5: Valid - View one lesson in detail. </p>
	 *
	 * @throws SQLException when the read operation fails
	 *
	 */

	@Test
	void testGetLessonById() throws SQLException {

		// creates the lesson returned by the database
		LessonsLearned expectedLesson = createValidLesson();

		// configures the controlled database result
		testingDatabase.returnedLesson = expectedLesson;

		// retrieves the lesson through the model
		LessonsLearned actualLesson = model.getLessonById(1, "contributor");

		// verifies the returned lesson
		assertSame(expectedLesson, actualLesson);

		// verifies the requested lesson ID
		assertEquals(1, testingDatabase.requestedLessonId);

		// verifies the requested contributor
		assertEquals("contributor", testingDatabase.requestedContributor);
	}

    /******
	 * 
	 * <p> Method: testGetLessonsByContributor() </p>
	 *
	 * <p> Description: Tests retrieving all lessons owned by a contributor. </p>
	 *
	 * <p> Note: Test Cases 3 and 7: View my lessons and ownership filtering. </p>
	 *
	 * @throws SQLException when the read operation fails
	 *
	 */

	@Test
	void testGetLessonsByContributor() throws SQLException {

		// creates the controlled lesson list
		LessonsLearnedList expectedLessons = new LessonsLearnedList();

		// adds a lesson to the controlled list
		expectedLessons.addLesson(createValidLesson());

		// configures the controlled database result
		testingDatabase.returnedLessons = expectedLessons;

		// retrieves the contributor's lessons
		LessonsLearnedList actualLessons = model.getLessonsByContributor("contributor");

		// verifies the returned lesson list
		assertSame(expectedLessons, actualLessons);

		// verifies the requested contributor
		assertEquals("contributor", testingDatabase.requestedContributor);
	}

    /******
	 * 
	 * <p> Method: testUpdateValidLesson() </p>
	 *
	 * <p> Description: Tests that the model updates a valid lesson. </p>
	 *
	 * <p> Note: Test Case 8: Valid - Update an existing lesson. </p>
	 *
	 * @throws SQLException when the update operation fails
	 *
	 */

	@Test
	void testUpdateValidLesson() throws SQLException {

		// creates a valid lesson
		LessonsLearned lesson = createValidLesson();

		// configures a successful update
		testingDatabase.updateResult = true;

		// updates the lesson through the model
		boolean updated = model.updateLesson(lesson);

		// verifies the successful update
		assertTrue(updated);

		// verifies that the database received the lesson
		assertSame(lesson, testingDatabase.updatedLesson);
	}

    /******
	 * 
	 * <p> Method: testUpdateInvalidLesson() </p>
	 *
	 * <p> Description: Tests that the model does not update an invalid lesson. </p>
	 *
	 * <p> Note: Test Case 10: Invalid - Validate updated information. </p>
	 *
	 * @throws SQLException when the update operation fails
	 *
	 */

	@Test
	void testUpdateInvalidLesson() throws SQLException {

		// creates a valid lesson
		LessonsLearned lesson = createValidLesson();

		// removes the required context
		lesson.setContext("");

		// attempts to update the lesson
		boolean updated = model.updateLesson(lesson);

		// verifies the failed update
		assertFalse(updated);

		// verifies that the database was not called
		assertNull(testingDatabase.updatedLesson);
	}

    /******
	 * 
	 * <p> Method: testDeleteLesson(boolean expectedResult) </p>
	 *
	 * <p> Description: Tests successful and unsuccessful lesson deletion. </p>
	 *
	 * <p> Note: Test Cases 11 and 12: Valid deletion and not-found deletion. </p>
	 *
	 * @param expectedResult specifies the controlled delete result
	 *
	 * @throws SQLException when the delete operation fails
	 *
	 */

	@ParameterizedTest
	@ValueSource(booleans = {true, false})
	void testDeleteLesson(boolean expectedResult) throws SQLException {

		// configures the delete result
		testingDatabase.deleteResult = expectedResult;

		// deletes the lesson through the model
		boolean actualResult = model.deleteLesson(1, "contributor");

		// verifies the returned delete result
		assertEquals(expectedResult, actualResult);

		// verifies the deleted lesson ID
		assertEquals(1, testingDatabase.deletedLessonId);

		// verifies the lesson owner
		assertEquals("contributor", testingDatabase.deletedContributor);
	}

}
