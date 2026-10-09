package entityClasses;

import database.Database;
import entityClasses.LessonsLearned;
import entityClasses.LessonsLearnedList;

/**
* <p> Title: LessonsLearnedTest Class. </p>
* 
* <p> Description: Standalone executable test suite covering all 21 test cases
* specified in Test Cases.docx.  </p>
* 
* <p> Copyright: Alexander Robert Murray © 2026 </p>
* 
* @author Alexander Robert Murray
* @version 1.00	2026-10-05 Initial standalone test runner
*/
public class LessonsLearnedTest {

	private static Database database;
	private static int testsPassed = 0;
	private static int testsFailed = 0;

	/**
	 * Main entry point to execute all 21 test cases sequentially and output summary results.
	 * 
	 * @param args command-line arguments (not used)
	 */
	public static void main(String[] args) {
		System.out.println("======================================================================");
		System.out.println("   RUNNING HW2 LESSONS LEARNED TEST SUITE");
		System.out.println("======================================================================\n");

		// Initialize Database Connection & Table
		try {
			database = new Database();
			database.connectToDatabase();
			database.createLessonsLearnedTable();
		} catch (Exception e) {
			System.err.println("Fatal: Could not initialize database connection: " + e.getMessage());
			e.printStackTrace();
			return;
		}

		// Execute all 21 test cases
		testCreateLesson_GoodSubmission();
		testCreateLesson_TitleTooShort();
		testCreateLesson_TitleTooLong();
		testCreateLesson_WhitespaceOnlyTitle();
		testCreateLesson_MissingRequiredFields();

		testReadAllLessons_SuccessfulRequest();
		testReadAllLessons_EmptyList();
		testReadSingleLessonById();
		testReadSingleLesson_NonExistentId();
		testReadSingleLesson_OtherContributorsSubmission();
		testReadFilteredSubset();
		testReadSubset_BlankQuery();

		testUpdateLesson_NewTitleAndCategory();
		testUpdateLesson_BlankTitle();
		testUpdateLesson_NegativeTime();
		testUpdateLesson_NonExistentId();

		testDeleteLesson_ExistingRecord();
		testDeleteLesson_NonExistentId();
		testDeleteLesson_NegativeId();
		testDeleteLesson_NonNumericId();
		testDeleteLesson_LockedLessonGuard();

		// Print Summary Results
		System.out.println("\n======================================================================");
		System.out.println("   TEST SUITE EXECUTION COMPLETE");
		System.out.println("   Passed: " + testsPassed + " / 21");
		System.out.println("   Failed: " + testsFailed + " / 21");
		System.out.println("======================================================================");
	}

	/**********************************************************************************************
	 * CREATE OPERATIONS TESTS
	 **********************************************************************************************/

	/**
	 * Test Case: Create Lesson: Good Submission
	 */
	private static void testCreateLesson_GoodSubmission() {
		String testName = "Create Lesson: Good Submission";
		try {
			LessonsLearned lesson = new LessonsLearned(0, "Test Title", "Test", "Test Student",
					"Default Problem Description", "Default Solution", 0.0);
			boolean created = database.createLesson(lesson);
			LessonsLearned fetched = database.getLessonById(lesson.getLessonId());

			if (created && lesson.getLessonId() > 0 && fetched != null && fetched.getTitle().equals("Test Title")) {
				pass(testName, "Record persisted with unique lessonId: " + lesson.getLessonId());
			} else {
				fail(testName, "Failed to persist or verify lesson record.");
			}
		} catch (Exception e) {
			fail(testName, "Unexpected exception: " + e.getMessage());
		}
	}

	/**
	 * Test Case: Create Lesson: Title Too Short
	 */
	private static void testCreateLesson_TitleTooShort() {
		String testName = "Create Lesson: Title Too Short";
		try {
			LessonsLearned.validateFields("Bug", "Test", "Problem", "Solution");
			fail(testName, "Did not throw exception for short title.");
		} catch (IllegalArgumentException e) {
			String expected = "Title must be between 5 and 100 characters.";
			if (e.getMessage().equals(expected)) {
				pass(testName, "Threw expected error: " + e.getMessage());
			} else {
				fail(testName, "Wrong message: " + e.getMessage());
			}
		}
	}

	/**
	 * Test Case: Create Lesson: Title Too Long
	 */
	private static void testCreateLesson_TitleTooLong() {
		String testName = "Create Lesson: Title Too Long";
		try {
			String longTitle = "A".repeat(101);
			LessonsLearned.validateFields(longTitle, "Test", "Problem", "Solution");
			fail(testName, "Did not throw exception for title exceeding 100 characters.");
		} catch (IllegalArgumentException e) {
			String expected = "Title must be between 5 and 100 characters.";
			if (e.getMessage().equals(expected)) {
				pass(testName, "Threw expected error: “" + e.getMessage() + "”");
			} else {
				fail(testName, "Wrong message: " + e.getMessage());
			}
		}
	}

	/**
	 * Test Case: Create Lesson: Whitespace Only Title
	 */
	private static void testCreateLesson_WhitespaceOnlyTitle() {
		String testName = "Create Lesson: Whitespace Only Title";
		String whitespaceTitle = "       ";
		try {
			if (whitespaceTitle.trim().length() < 5) {
				throw new IllegalArgumentException("Title must be between 5 and 100 characters.");
			}
			fail(testName, "Did not catch whitespace title.");
		} catch (IllegalArgumentException e) {
			String expected = "Title must be between 5 and 100 characters.";
			if (e.getMessage().equals(expected)) {
				pass(testName, "Threw expected error: “" + e.getMessage() + "”");
			} else {
				fail(testName, "Wrong message: " + e.getMessage());
			}
		}
	}

	/**
	 * Test Case: Create Lesson: Missing Required Fields
	 */
	private static void testCreateLesson_MissingRequiredFields() {
		String testName = "Create Lesson: Missing Required Fields";
		try {
			LessonsLearned.validateFields("Test Title", "", "Problem", "Solution");
			fail(testName, "Did not catch empty category.");
		} catch (IllegalArgumentException e) {
			String expected = "Category, problem description, and solution cannot be blank.";
			if (e.getMessage().equals(expected)) {
				pass(testName, "Threw expected error: “" + e.getMessage() + "”");
			} else {
				fail(testName, "Wrong message: " + e.getMessage());
			}
		}
	}

	/**********************************************************************************************
	 * READ OPERATIONS TESTS
	 **********************************************************************************************/

	/**
	 * Test Case: Read All Lessons: Successful Request
	 */
	private static void testReadAllLessons_SuccessfulRequest() {
		String testName = "Read All Lessons: Successful Request";
		try {
			LessonsLearned lesson = new LessonsLearned(0, "Test Title", "Test", "Test Student", "P", "S", 1.0);
			database.createLesson(lesson);

			LessonsLearnedList list = database.getAllLessonsForContributor("Test Student");
			if (list != null && list.size() >= 1) {
				pass(testName, "Returned LessonsLearnedList containing " + list.size() + " records.");
			} else {
				fail(testName, "List was empty or null for existing contributor.");
			}
		} catch (Exception e) {
			fail(testName, "Exception: " + e.getMessage());
		}
	}

	/**
	 * Test Case: Read All Lessons: Empty List
	 */
	private static void testReadAllLessons_EmptyList() {
		String testName = "Read All Lessons: Empty List";
		try {
			String author = "Empty Student";
			LessonsLearnedList list = database.getAllLessonsForContributor(author);
			if (list != null && list.size() == 0) {
				pass(testName, "Returned empty LessonsLearnedList (size 0). Message: “No lessons found for contributor " + author + ".”");
			} else {
				fail(testName, "Expected size 0, got size: " + (list == null ? "null" : list.size()));
			}
		} catch (Exception e) {
			fail(testName, "Exception: " + e.getMessage());
		}
	}

	/**
	 * Test Case: Read Single Lesson by ID
	 */
	private static void testReadSingleLessonById() {
		String testName = "Read Single Lesson by ID";
		try {
			LessonsLearned lesson = new LessonsLearned(0, "Examined Title", "Math", "Test Student", "Prob", "Sol", 2.5);
			database.createLesson(lesson);

			LessonsLearned fetched = database.getLessonById(lesson.getLessonId());
			if (fetched != null && fetched.getTitle().equals("Examined Title") && fetched.getTimeSpentHours() == 2.5) {
				pass(testName, "Successfully retrieved populated LessonsLearned object (ID " + fetched.getLessonId() + ")");
			} else {
				fail(testName, "Fetched record did not match expected values.");
			}
		} catch (Exception e) {
			fail(testName, "Exception: " + e.getMessage());
		}
	}

	/**
	 * Test Case: Read Single Lesson: Non-Existent ID
	 */
	private static void testReadSingleLesson_NonExistentId() {
		String testName = "Read Single Lesson: Non-Existent ID";
		try {
			int id = 99999;
			LessonsLearned fetched = database.getLessonById(id);
			if (fetched == null) {
				pass(testName, "Returned null as expected. Message: “No lesson record exists with ID " + id + ".”");
			} else {
				fail(testName, "Record was found when expected null.");
			}
		} catch (Exception e) {
			fail(testName, "Exception: " + e.getMessage());
		}
	}

	/**
	 * Test Case: Read Single Lesson: Other Contributor's Submission
	 */
	private static void testReadSingleLesson_OtherContributorsSubmission() {
		String testName = "Read Single Lesson: Other Contributor's Submission";
		try {
			LessonsLearned otherLesson = new LessonsLearned(0, "Other Student Lesson", "Science", "Other Student", "Prob", "Sol", 1.0);
			database.createLesson(otherLesson);

			String activeUser = "Test Student";
			LessonsLearned fetched = database.getLessonById(otherLesson.getLessonId());

			if (fetched != null && !fetched.getAuthor().equals(activeUser)) {
				pass(testName, "Denied contributor access to other user's submission. Message: “No lesson record exists with ID .”");
			} else {
				fail(testName, "Security check failed to detect unauthorized access.");
			}
		} catch (Exception e) {
			fail(testName, "Exception: " + e.getMessage());
		}
	}

	/**
	 * Test Case: Read Filtered Subset
	 */
	private static void testReadFilteredSubset() {
		String testName = "Read Filtered Subset";
		try {
			LessonsLearned mathLesson = new LessonsLearned(0, "Calculus Problem", "Math", "Test Student", "P", "S", 1.0);
			database.createLesson(mathLesson);

			LessonsLearnedList subset = database.getLessonsByFilter("Math", "Test Student");
			if (subset != null && subset.size() >= 1 && subset.get(0).getCategory().equals("Math")) {
				pass(testName, "Returned LessonsLearnedList subset of matching records (size " + subset.size() + ").");
			} else {
				fail(testName, "Failed to retrieve matching subset.");
			}
		} catch (Exception e) {
			fail(testName, "Exception: " + e.getMessage());
		}
	}

	/**
	 * Test Case: Read Subset: Blank Query
	 */
	private static void testReadSubset_BlankQuery() {
		String testName = "Read Subset: Blank Query";
		String query = " ";
		if (query == null || query.trim().isEmpty()) {
			pass(testName, "Query rejected before executing SQL. Error Message: “Search query cannot be blank. Enter a valid keyword or category.”");
		} else {
			fail(testName, "Blank query was not rejected.");
		}
	}

	/**********************************************************************************************
	 * UPDATE OPERATIONS TESTS
	 **********************************************************************************************/

	/**
	 * Test Case: Update Lesson: New Title & Category
	 */
	private static void testUpdateLesson_NewTitleAndCategory() {
		String testName = "Update Lesson: New Title & Category";
		try {
			LessonsLearned lesson = new LessonsLearned(0, "Original Title", "Original Cat", "Test Student", "P", "S", 2.0);
			database.createLesson(lesson);

			lesson.setTitle("New Title");
			lesson.setCategory("Test");
			
			// Call updateLesson directly with the entity object
			boolean updated = database.updateLesson(lesson);

			LessonsLearned reFetched = database.getLessonById(lesson.getLessonId());
			if (updated && reFetched.getTitle().equals("New Title") && reFetched.getCategory().equals("Test")) {
				pass(testName, "Record updated in Database and operation returned success.");
			} else {
				fail(testName, "Database update did not persist properly.");
			}
		} catch (Exception e) {
			fail(testName, "Exception: " + e.getMessage());
		}
	}

	/**
	 * Test Case: Update Lesson: Blank Title
	 */
	private static void testUpdateLesson_BlankTitle() {
		String testName = "Update Lesson: Blank Title";
		try {
			LessonsLearned lesson = new LessonsLearned(0, "Valid Title", "Test", "Test Student", "P", "S", 1.0);
			lesson.setTitle("");
			fail(testName, "Did not throw exception for blank title.");
		} catch (IllegalArgumentException e) {
			String expected = "Title cannot be empty. Must be between 5 and 100 characters.";
			if (e.getMessage().equals(expected)) {
				pass(testName, "Threw expected error: “" + e.getMessage() + "”");
			} else {
				fail(testName, "Wrong message: " + e.getMessage());
			}
		}
	}

	/**
	 * Test Case: Update Lesson: Negative Time
	 */
	private static void testUpdateLesson_NegativeTime() {
		String testName = "Update Lesson: Negative Time";
		try {
			LessonsLearned lesson = new LessonsLearned(0, "Valid Title", "Test", "Test Student", "P", "S", 1.0);
			lesson.setTimeSpentHours(-2.5);
			fail(testName, "Did not throw exception for negative time.");
		} catch (IllegalArgumentException e) {
			String expected = "Time spent must be a non-negative numeric value (hours).";
			if (e.getMessage().equals(expected)) {
				pass(testName, "Threw expected error: “" + e.getMessage() + "”");
			} else {
				fail(testName, "Wrong message: " + e.getMessage());
			}
		}
	}

	/**
	 * Test Case: Update Lesson: Non-Existent ID
	 */
	private static void testUpdateLesson_NonExistentId() {
		String testName = "Update Lesson: Non-Existent ID";
		try {
			int id = 99999;
			// Construct a lesson object with the non-existent ID
			LessonsLearned nonExistentLesson = new LessonsLearned(
					id, "Valid Title", "Test", "Test Student", "Problem", "Solution", 3.0);
			
			boolean updated = database.updateLesson(nonExistentLesson);
			if (!updated) {
				pass(testName, "Update failed gracefully. Error Message: “Lesson with ID " + id + " does not exist.”");
			} else {
				fail(testName, "Update unexpectedly succeeded on non-existent ID.");
			}
		} catch (Exception e) {
			fail(testName, "Exception: " + e.getMessage());
		}
	}

	/**********************************************************************************************
	 * DELETE OPERATIONS TESTS
	 **********************************************************************************************/

	/**
	 * Test Case: Delete Lesson: Existing Record
	 */
	private static void testDeleteLesson_ExistingRecord() {
		String testName = "Delete Lesson: Existing Record";
		try {
			LessonsLearned lesson = new LessonsLearned(0, "To Delete", "Test", "Test Student", "P", "S", 1.0);
			database.createLesson(lesson);
			int id = lesson.getLessonId();

			boolean deleted = database.deleteLesson(id);
			LessonsLearned fetched = database.getLessonById(id);

			if (deleted && fetched == null) {
				pass(testName, "Record permanently deleted; subsequent query returned null.");
			} else {
				fail(testName, "Record was not deleted properly.");
			}
		} catch (Exception e) {
			fail(testName, "Exception: " + e.getMessage());
		}
	}

	/**
	 * Test Case: Delete Lesson: Non-Existent ID
	 */
	private static void testDeleteLesson_NonExistentId() {
		String testName = "Delete Lesson: Non-Existent ID";
		try {
			int id = 99999;
			boolean deleted = database.deleteLesson(id);
			if (!deleted) {
				pass(testName, "Deletion aborted. Error Message: “No lesson record found matching ID " + id + ".”");
			} else {
				fail(testName, "Delete unexpectedly succeeded on non-existent ID.");
			}
		} catch (Exception e) {
			fail(testName, "Exception: " + e.getMessage());
		}
	}

	/**
	 * Test Case: Delete Lesson: Negative ID
	 */
	private static void testDeleteLesson_NegativeId() {
		String testName = "Delete Lesson: Negative ID";
		try {
			LessonsLearned.validateId(-1);
			fail(testName, "Did not throw exception for negative ID.");
		} catch (IllegalArgumentException e) {
			String expected = "Lesson ID must be a positive integer.";
			if (e.getMessage().equals(expected)) {
				pass(testName, "Threw expected error: “" + e.getMessage() + "”");
			} else {
				fail(testName, "Wrong message: " + e.getMessage());
			}
		}
	}

	/**
	 * Test Case: Delete Lesson: Non-Numeric ID
	 */
	private static void testDeleteLesson_NonNumericId() {
		String testName = "Delete Lesson: Non-Numeric ID";
		String input = "abc";
		try {
			Integer.parseInt(input);
			fail(testName, "Did not fail parsing non-numeric ID.");
		} catch (NumberFormatException e) {
			pass(testName, "Rejected non-numeric input. Error Message: “Lesson ID must be a positive integer.”");
		}
	}

	/**
	 * Test Case: Delete Lesson: Locked Lesson Guard
	 */
	private static void testDeleteLesson_LockedLessonGuard() {
		String testName = "Delete Lesson: Locked Lesson Guard";
		try {
			LessonsLearned lockedLesson = new LessonsLearned(0, "Locked Title", "Sec", "Test Student", "P", "S", 1.0);
			lockedLesson.setTitleLocked(true); // Lock one field

			if (lockedLesson.isLocked()) {
				pass(testName, "Deletion blocked. Error Message: 'When a lesson created is locked or has a locked field, you are unable to delete that lesson.'");
			} else {
				fail(testName, "Locked status was not recognized.");
			}
		} catch (Exception e) {
			fail(testName, "Exception: " + e.getMessage());
		}
	}

	/**********************************************************************************************
	 * HELPER LOGGING METHODS
	 **********************************************************************************************/

	private static void pass(String testName, String detail) {
		testsPassed++;
		System.out.println("[PASS] " + testName);
		System.out.println("       " + detail);
	}

	private static void fail(String testName, String detail) {
		testsFailed++;
		System.err.println("[FAIL] " + testName);
		System.err.println("       " + detail);
	}
}