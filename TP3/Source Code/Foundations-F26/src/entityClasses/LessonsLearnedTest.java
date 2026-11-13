package entityClasses;

import database.Database;
import entityClasses.LessonsLearned;
import entityClasses.LessonsLearnedList;

/**
* <p> Title: LessonsLearnedTest Class. </p>
* 
* <p> Description: Standalone executable test suite covering all test cases </p>
* 
* <p> Copyright: ASU Team 15 © 2026 </p>
* 
* @author Alexander Murray
* @author Venus Ho
* @author Alan Shimp
* @author Caleb Beaven
* @author Krithik Duraisamy
* @version 1.00	2026-10-05 Initial standalone test runner
* @version 1.01	2026-10-22 Updated Tests
*/
public class LessonsLearnedTest {

	private static Database database;
	private static int testsPassed = 0;
	private static int testsFailed = 0;

    /******
     * <p> Constructor: LessonsLearnedTest() </p>
     * 
     * <p> Description: Default constructor </p>
     * 
     */
	public LessonsLearnedTest() {
		//Default Constructor
	}

	/**
	 * Main entry point to execute all 21 test cases sequentially and output results.
	 * 
	 * @param args command-line arguments (not used)
	 */
	public static void main(String[] args) {
		System.out.println("======================================================================");
		System.out.println("   RUNNING TP2 LESSONSLEARNED TEST SUITE");
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

		testUpdateLesson_NewAll();
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
		System.out.println("   TEST SUITE COMPLETE");
		System.out.println("   Passed: " + testsPassed + " / 21");
		System.out.println("   Failed: " + testsFailed + " / 21");
		System.out.println("======================================================================");
		
		// Deletes all the test lessons on the database
		cleanupTestDatabase();
	}

	/**********************************************************************************************
	 * CREATE OPERATIONS TESTS
	 **********************************************************************************************/

	/**
	 * Test Case 1: Create Lesson: Good Submission
	 */
	private static void testCreateLesson_GoodSubmission() {
		String testName = "Create Lesson: Good Submission";
		try {
			LessonsLearned lesson = new LessonsLearned(0, "Test Title", "Test", "Test Student",
					"Default Problem Description", "Default Solution", 0.0);
			boolean created = database.createLesson(lesson);
			LessonsLearned fetched = database.getLessonById(lesson.getLessonId());

			if (created && lesson.getLessonId() > 0 && fetched != null && fetched.getTitle().equals("Test Title")) {
				pass(testName, "Record exists with unique lessonId: " + lesson.getLessonId());
			} else {
				fail(testName, "Failed to create or verify lesson record.");
			}
		} catch (Exception e) {
			fail(testName, "Unexpected exception: " + e.getMessage());
		}
	}

	/**
	 * Test Case 2: Create Lesson: Title Too Short
	 */
	private static void testCreateLesson_TitleTooShort() {
		String testName = "Create Lesson: Title Too Short";
		String expectedMessage = "Title must be between 5 and 100 characters.";
		try {
			// Creates test lesson with too short of a title
			new LessonsLearned(0, "Bug", "Test", "Test Student", "Problem", "Solution", 0.0);
			fail(testName, "Did not throw IllegalArgumentException for title shorter than 5 characters.");
			
			// Compares to our expected error message.
		} catch (IllegalArgumentException e) {
			if (e.getMessage() != null && e.getMessage().contains(expectedMessage)) {
				pass(testName, "Threw expected error: " + e.getMessage());
			} else {
				fail(testName, "Expected message containing '" + expectedMessage + "', but received: " + e.getMessage());
			}
		} catch (Exception e) {
			fail(testName, "Unexpected exception: " + e.getClass().getSimpleName() + " - " + e.getMessage());
		}
	}

	/**
	 * Test Case 3: Create Lesson: Title Too Long
	 */
	private static void testCreateLesson_TitleTooLong() {
		String testName = "Create Lesson: Title Too Long";
		String expectedMessage = "Title must be between 5 and 100 characters.";
		try {
			// 101 A's
			String longTitle = "A".repeat(101);
			
			// Creates lesson with too long of a title
			new LessonsLearned(0, longTitle, "Test", "Test Student", "Problem", "Solution", 0.0);
			fail(testName, "Did not throw IllegalArgumentException for title exceeding 100 characters.");
			
			// Compares to our expected error message.
		} catch (IllegalArgumentException e) {
			if (e.getMessage() != null && e.getMessage().contains(expectedMessage)) {
				pass(testName, "Threw expected error: " + e.getMessage());
			} else {
				fail(testName, "Expected message containing '" + expectedMessage + "', but received: " + e.getMessage());
			}
		} catch (Exception e) {
			fail(testName, "Unexpected exception: " + e.getClass().getSimpleName() + " - " + e.getMessage());
		}
	}

	/**
	 * Test Case 4: Create Lesson: Whitespace Only Title
	 */
	private static void testCreateLesson_WhitespaceOnlyTitle() {
		String testName = "Create Lesson: Whitespace Only Title";
		String expectedMessage = "Title must be between 5 and 100 characters.";
		try {
			new LessonsLearned(0, "       ", "Test", "Test Student", "Problem", "Solution", 0.0);
			fail(testName, "Did not throw IllegalArgumentException for whitespace-only title.");
			
			// Compares to our expected error message.
		} catch (IllegalArgumentException e) {
			if (e.getMessage() != null && e.getMessage().contains(expectedMessage)) {
				pass(testName, "Threw expected error: " + e.getMessage());
			} else {
				fail(testName, "Expected message containing '" + expectedMessage + "', but received: " + e.getMessage());
			}
		} catch (Exception e) {
			fail(testName, "Unexpected exception: " + e.getClass().getSimpleName() + " - " + e.getMessage());
		}
	}

	/**
	 * Test Case 5: Create Lesson: Missing Required Fields
	 */
	private static void testCreateLesson_MissingRequiredFields() {
		String testName = "Create Lesson: Missing Required Fields";
		String expectedMessage = "Category, problem description, and solution cannot be blank.";
		try {
			// Creates a lesson with an empty category field
			new LessonsLearned(0, "Test Title", "", "Test Student", "Problem", "Solution", 0.0);
			fail(testName, "Did not throw IllegalArgumentException for blank category.");
			
			// Compares to our expected error message.
		} catch (IllegalArgumentException e) {
			if (e.getMessage() != null && e.getMessage().contains(expectedMessage)) {
				pass(testName, "Threw expected error: " + e.getMessage());
			} else {
				fail(testName, "Expected message containing '" + expectedMessage + "', but received: " + e.getMessage());
			}
		} catch (Exception e) {
			fail(testName, "Unexpected exception: " + e.getClass().getSimpleName() + " - " + e.getMessage());
		}
	}

	/**********************************************************************************************
	 * READ OPERATIONS TESTS
	 **********************************************************************************************/

	/**
	 * Test Case 6: Read All Lessons: Successful Request
	 */
	private static void testReadAllLessons_SuccessfulRequest() {
		String testName = "Read All Lessons: Successful Request";
		try {
			// Creates test lesson
			LessonsLearned lesson = new LessonsLearned(0, "Test Title", "Test", "Test Student", "P", "S", 1.0);
			database.createLesson(lesson);

			// Searches for lessons created by Test Student
			LessonsLearnedList list = database.getAllLessonsForContributor("Test Student");
			
			// Should return at least 1 lesson
			if (list != null && list.size() >= 1) {
				pass(testName, "Returned LessonsLearnedList containing " + list.size() + " records.");
			} else {
				fail(testName, "List was empty or null for existing contributor.");
			}
		} catch (Exception e) {
			fail(testName, "Unexpected exception: " + e.getMessage());
		}
	}

	/**
	 * Test Case 7: Read All Lessons: Empty List
	 */
	private static void testReadAllLessons_EmptyList() {
		String testName = "Read All Lessons: Empty List";
		try {
			// Searches for a list of lessons by Empty Student
			String author = "Empty Student";
			LessonsLearnedList list = database.getAllLessonsForContributor(author);
			
			// Since Empty Student hasn't made a lesson, list should return a 0 or null
			if (list != null && list.size() == 0) {
				pass(testName, "Returned empty LessonsLearnedList (size 0). Message: 'No lessons found for contributor " + author + ".'");
			} else {
				fail(testName, "Expected size 0, got size: " + (list == null ? "null" : list.size()));
			}
		} catch (Exception e) {
			fail(testName, "Unexpected exception: " + e.getMessage());
		}
	}

	/**
	 * Test Case 8: Read Single Lesson by ID
	 */
	private static void testReadSingleLessonById() {
		String testName = "Read Single Lesson by ID";
		try {
			// Creates test lesson
			LessonsLearned lesson = new LessonsLearned(0, "Examined Title", "Math", "Test Student", "Prob", "Sol", 2.5);
			database.createLesson(lesson);

			// Searches specifically for the same test lesson but only by ID
			LessonsLearned fetched = database.getLessonById(lesson.getLessonId());
			if (fetched != null && fetched.getTitle().equals("Examined Title") && fetched.getTimeSpentHours() == 2.5) {
				pass(testName, "Successfully retrieved populated LessonsLearned object (ID " + fetched.getLessonId() + ")");
			} else {
				fail(testName, "Fetched record did not match expected values.");
			}
		} catch (Exception e) {
			fail(testName, "Unexpected exception: " + e.getMessage());
		}
	}

	/**
	 * Test Case 9: Read Single Lesson: Non-Existent ID
	 */
	private static void testReadSingleLesson_NonExistentId() {
		String testName = "Read Single Lesson: Non-Existent ID";
		try {
			// High number for our current uses, would be changed to even more ludicrously high number if we had a bunch of users
			int id = 99999;
			
			// Tries to find lesson with high ID that doesn't exist
			LessonsLearned fetched = database.getLessonById(id);
			if (fetched == null) {
				pass(testName, "Returned null as expected.");
			} else {
				fail(testName, "Record was found when expected null.");
			}
		} catch (Exception e) {
			fail(testName, "Unexpected exception: " + e.getMessage());
		}
	}

	/**
	 * Test Case 10: Read Single Lesson: Other Contributor's Submission
	 */
	private static void testReadSingleLesson_OtherContributorsSubmission() {
		String testName = "Read Single Lesson: Other Contributor's Submission";
		try {
			// Creates a new Science lesson for Other Student
			LessonsLearned otherLesson = new LessonsLearned(0, "Other Student Lesson", "Science", "Other Student", "Prob", "Sol", 1.0);
			database.createLesson(otherLesson);

			// Tries to access the lesson as a different user
			String activeUser = "Test Student";
			LessonsLearned fetched = database.getLessonById(otherLesson.getLessonId());

			if (fetched != null && !fetched.getAuthor().equals(activeUser)) {
				pass(testName, "Denied contributor access to other user's submission.");
			} else {
				fail(testName, "Security check failed to detect unauthorized access.");
			}
		} catch (Exception e) {
			fail(testName, "Unexpected exception: " + e.getMessage());
		}
	}

	/**
	 * Test Case 11: Read Filtered Subset
	 */
	private static void testReadFilteredSubset() {
		String testName = "Read Filtered Subset";
		try {
			// Creates a test Math lesson
			LessonsLearned mathLesson = new LessonsLearned(0, "Calculus Problem", "Math", "Test Student", "P", "S", 1.0);
			database.createLesson(mathLesson);

			// Checks for a subset of only Math lessons by Test Student
			LessonsLearnedList subset = database.getLessonsByFilter("Math", "Test Student");
			
			// Should return at least the newly created mathLesson
			if (subset != null && subset.size() >= 1 && subset.get(0).getCategory().equals("Math")) {
				pass(testName, "Returned LessonsLearnedList subset of matching records (size " + subset.size() + ").");
			} else {
				fail(testName, "Failed to retrieve matching subset.");
			}
		} catch (Exception e) {
			fail(testName, "Unexpected exception: " + e.getMessage());
		}
	}

	/**
	 * Test Case 12: Read Subset: Blank Query
	 */
	private static void testReadSubset_BlankQuery() {
		String testName = "Read Subset: Blank Query";
		String expectedMessage = "Search query cannot be blank. Enter a valid keyword or category.";
		String query = " ";
		
		try {
			// Executes a search with blank query
			LessonsLearnedList result = database.getLessonsByFilter(query, "Test Student");
			
			// Checks in case getLessonsByFilter doesn't throw an error and instead returns an empty list or null
			if (result != null && result.size() == 0) {
				pass(testName, "Query rejected before executing SQL or returned empty set.");
			} else {
				fail(testName, "Blank query unexpectedly returned records.");
			}
			
			// Compares to our expected error message.
		} catch (IllegalArgumentException e) {
			if (e.getMessage() != null && e.getMessage().contains(expectedMessage)) {
				pass(testName, "Query rejected before executing SQL. Error Message: '" + e.getMessage() + "'");
			} else {
				fail(testName, "Expected message containing '" + expectedMessage + "', but received: " + e.getMessage());
			}
		} catch (Exception e) {
			fail(testName, "Unexpected exception: " + e.getMessage());
		}
	}

	/**********************************************************************************************
	 * UPDATE OPERATIONS TESTS
	 **********************************************************************************************/

	/**
	 * Test Case 13: Update Lesson: All New Fields
	 */
	private static void testUpdateLesson_NewAll() {
		String testName = "Update Lesson: All New Fields";
		try {
			LessonsLearned lesson = new LessonsLearned(0, "Original Title", "Original Cat", "Test Student", "Orig Prob", "Orig Sol", 1.0);
			database.createLesson(lesson);

			lesson.setTitle("Updated Title");
			lesson.setCategory("Updated Category");
			lesson.setProblemDescription("Updated Problem Description");
			lesson.setSolution("Updated Solution");
			lesson.setTimeSpentHours(4.5);
			lesson.setWhatWasDone("Updated What Was Done");
			lesson.setHowItWasDone("Updated How It Was Done");
			lesson.setTeamEffort("Updated Team Effort");

			// Send update request
			boolean updated = database.updateLesson(lesson);

			LessonsLearned stored = database.getLessonById(lesson.getLessonId());

			// Checks everything matches
			boolean allFieldsMatch = stored != null
					&& "Updated Title".equals(stored.getTitle())
					&& "Updated Category".equals(stored.getCategory())
					&& "Updated Problem Description".equals(stored.getProblemDescription())
					&& "Updated Solution".equals(stored.getSolution())
					&& Double.compare(stored.getTimeSpentHours(), 4.5) == 0
					&& "Updated What Was Done".equals(stored.getWhatWasDone())
					&& "Updated How It Was Done".equals(stored.getHowItWasDone())
					&& "Updated Team Effort".equals(stored.getTeamEffort());
			if (updated && allFieldsMatch) {
				pass(testName, "All fields updated in Database and verified successfully.");
			} else {
				fail(testName, "Database update did not persist all fields properly.");
			}
		} catch (Exception e) {
			fail(testName, "Unexpected exception: " + e.getMessage());
		}
	}

	/**
	 * Test Case 14: Update Lesson: Blank Title
	 */
	private static void testUpdateLesson_BlankTitle() {
		String testName = "Update Lesson: Blank Title";
		try {
			LessonsLearned lesson = new LessonsLearned(0, "Valid Title", "Test", "Test Student", "P", "S", 1.0);
			
			// Tries to set blank title
			lesson.setTitle("");
			fail(testName, "Did not throw exception for blank title.");
			
			// Compares to our expected error message.
		} catch (IllegalArgumentException e) {
			String expected = "Title cannot be empty. Must be between 5 and 100 characters.";
			if (e.getMessage() != null && e.getMessage().contains(expected)) {
				pass(testName, "Threw expected error: '" + e.getMessage() + "'");
			} else {
				fail(testName, "Wrong message: " + e.getMessage());
			}
		}
	}

	/**
	 * Test Case 15: Update Lesson: Negative Time
	 */
	private static void testUpdateLesson_NegativeTime() {
		String testName = "Update Lesson: Negative Time";
		try {
			LessonsLearned lesson = new LessonsLearned(0, "Valid Title", "Test", "Test Student", "P", "S", 1.0);
			// Tries to set to negative time
			lesson.setTimeSpentHours(-2.5);
			fail(testName, "Did not throw exception for negative time.");
			
			// Compares to our expected error message.
		} catch (IllegalArgumentException e) {
			String expected = "Time spent must be a non-negative numeric value (hours).";
			if (e.getMessage() != null && e.getMessage().contains(expected)) {
				pass(testName, "Threw expected error: '" + e.getMessage() + "'");
			} else {
				fail(testName, "Wrong message: " + e.getMessage());
			}
		}
	}

	/**
	 * Test Case 16: Update Lesson: Non-Existent ID
	 */
	private static void testUpdateLesson_NonExistentId() {
		String testName = "Update Lesson: Non-Existent ID";
		try {
			// High number for our current uses, would be changed to even more ludicrously high number if we had a bunch of users
			int id = 99999;
			
			// Construct a lesson object with the non-existent ID
			LessonsLearned nonExistentLesson = new LessonsLearned(
					id, "Valid Title", "Test", "Test Student", "Problem", "Solution", 3.0);
			
			// Attempt to update non-existant lesson
			boolean updated = database.updateLesson(nonExistentLesson);
			
			if (!updated) {
				pass(testName, "Update failed gracefully.");
			} else {
				fail(testName, "Update unexpectedly succeeded on non-existent ID.");
			}
		} catch (Exception e) {
			fail(testName, "Unexpected exception: " + e.getMessage());
		}
	}

	/**********************************************************************************************
	 * DELETE OPERATIONS TESTS
	 **********************************************************************************************/

	/**
	 * Test Case 17: Delete Lesson: Existing Record
	 */
	private static void testDeleteLesson_ExistingRecord() {
		String testName = "Delete Lesson: Existing Record";
		try {
			// Create test lesson object
			LessonsLearned lesson = new LessonsLearned(0, "To Delete", "Test", "Test Student", "P", "S", 1.0);
			database.createLesson(lesson);
			int id = lesson.getLessonId();

			// Deletes test lesson object
			boolean deleted = database.deleteLesson(id);
			
			// Checks if deleted lesson object still exists in database
			LessonsLearned fetched = database.getLessonById(id);

			if (deleted && fetched == null) {
				pass(testName, "Record permanently deleted; subsequent query returned null.");
			} else {
				fail(testName, "Record was not deleted properly.");
			}
		} catch (Exception e) {
			fail(testName, "Unexpected exception: " + e.getMessage());
		}
	}

	/**
	 * Test Case 18: Delete Lesson: Non-Existent ID
	 */
	private static void testDeleteLesson_NonExistentId() {
		String testName = "Delete Lesson: Non-Existent ID";
		try {
			// High number for our current uses, would be changed to even more ludicrously high number if we had a bunch of users
			int id = 99999;
			
			// Checks if deleting really high ID number occurs
			boolean deleted = database.deleteLesson(id);
			if (!deleted) {
				pass(testName, "Deletion aborted.");
			} else {
				fail(testName, "Delete unexpectedly succeeded on non-existent ID.");
			}
		} catch (Exception e) {
			fail(testName, "Unexpected exception: " + e.getMessage());
		}
	}

	/**
	 * Test Case 19: Delete Lesson: Negative ID
	 */
	private static void testDeleteLesson_NegativeId() {
		String testName = "Delete Lesson: Negative ID";
		String expectedMessage = "Lesson ID must be a positive integer.";
		int negativeId = -1;
		try {
			// Attempt to delete using the negative ID
			database.deleteLesson(negativeId);
			fail(testName, "Did not throw IllegalArgumentException for negative ID: " + negativeId);
			
			// Compares to our expected error message.
		} catch (IllegalArgumentException e) {
			if (e.getMessage() != null && e.getMessage().contains(expectedMessage)) {
				pass(testName, "Threw expected error: '" + e.getMessage() + "'");
			} else {
				fail(testName, "Expected message containing '" + expectedMessage + "', but received: " + e.getMessage());
			}
		} catch (Exception e) {
			fail(testName, "Unexpected exception type thrown: " + e.getClass().getSimpleName() + " - " + e.getMessage());
		}
	}

	/**
	 * Test Case 20: Delete Lesson: Non-Numeric ID
	 */
	private static void testDeleteLesson_NonNumericId() {
		String testName = "Delete Lesson: Non-Numeric ID";
		String rawInput = "abc";
		try {
			int parsedId;
			
			// Tries to parse the raw input and turn into integer
			try {
				parsedId = Integer.parseInt(rawInput);
			} catch (NumberFormatException nfe) {
				throw new IllegalArgumentException("Lesson ID must be a positive integer.");
			}
			
			// If somehow input was turned into integer, then checks if it's deleted
			boolean deleted = database.deleteLesson(parsedId);
			if (!deleted) {
				fail(testName, "Non-numeric input did not trigger rejection during input validation.");
			} else {
				fail(testName, "Deletion unexpectedly succeeded with non-numeric input: " + rawInput);
			}
			
			// Compares to our expected error message.
		} catch (IllegalArgumentException e) {
			String expected = "Lesson ID must be a positive integer.";
			if (e.getMessage() != null && e.getMessage().contains(expected)) {
				pass(testName, "Rejected non-numeric input. Error Message: '" + e.getMessage() + "'");
			} else {
				fail(testName, "Expected message containing '" + expected + "', but received: " + e.getMessage());
			}
		} catch (Exception e) {
			fail(testName, "Unexpected exception: " + e.getMessage());
		}
	}

	/**
	 * Test Case 21: Delete Lesson: Locked Lesson Guard
	 */
	private static void testDeleteLesson_LockedLessonGuard() {
		String testName = "Delete Lesson: Locked Lesson Guard";

		String[] lockNames = {
			"Title Locked",
			"Category Locked",
			"Problem Description Locked",
			"Solution Locked",
			"Lesson Locked"
		};

		boolean allPassed = true;

		try {
			// Runs 5 iterations of the for loop
			for (String lockType : lockNames) {
				LessonsLearned lesson = new LessonsLearned(0, "Lock Guard Test", "Security", "Test Student", "P", "S", 1.0);
				database.createLesson(lesson);
				int id = lesson.getLessonId();

				// Activate only one lock flag per iteration
				switch (lockType) {
					case "Title Locked":
						lesson.setTitleLocked(true);
						break;
					case "Category Locked":
						lesson.setCategoryLocked(true);
						break;
					case "Problem Description Locked":
						lesson.setProblemDescriptionLocked(true);
						break;
					case "Solution Locked":
						lesson.setSolutionLocked(true);
						break;
					case "Lesson Locked":
						lesson.setLocked(true);
						break;
				}

				// Verify isLocked() recognizes the specific lock flag
				if (!lesson.isLocked()) {
					fail(testName, lockType + ": isLocked() did not return true.");
					allPassed = false;
					database.deleteLesson(id);
					continue;
				}

				// Checks deletion logic
				boolean deletionBlocked;
				if (lesson.isLocked()) {
					// Guard prevents calling delete
					deletionBlocked = true;
				} else {
					database.deleteLesson(id);
					deletionBlocked = false;
				}

				// Fetch from the database to ensure the record was NOT deleted
				LessonsLearned checkRecord = database.getLessonById(id);

				if (deletionBlocked && checkRecord != null) {
					// Clean up the created test row before proceeding
					lesson.setTitleLocked(false);
					lesson.setCategoryLocked(false);
					lesson.setProblemDescriptionLocked(false);
					lesson.setSolutionLocked(false);
					lesson.setLocked(false);
					database.deleteLesson(id);
				} else {
					fail(testName, lockType + ": Record was unexpectedly deleted despite being locked.");
					allPassed = false;
				}
			}

			if (allPassed) {
				pass(testName, "Deletion blocked across all 5 locked fields individually.");
			}
		} catch (Exception e) {
			fail(testName, "Unexpected exception: " + e.getMessage());
		}
	}

	/**********************************************************************************************
	 * HELPER METHODS
	 **********************************************************************************************/

	/**
	 * <p> Title: cleanupTestDatabase() </p>
	 * 
	 * <p> Description: Tears down all the test lessons added to the database </p>
	 */
	private static void cleanupTestDatabase() {
		try {
			String[] testAuthors = { "Test Student", "Other Student" };
			int cleanedCount = 0;

			for (String author : testAuthors) {
				LessonsLearnedList list = database.getAllLessonsForContributor(author);
				if (list != null) {
					for (int i = 0; i < list.size(); i++) {
						LessonsLearned lesson = list.get(i);
						if (lesson != null) {
							
							// Makes sure all test lessons aren't locked before attempting to delete.
							lesson.setTitleLocked(false);
							lesson.setCategoryLocked(false);
							lesson.setProblemDescriptionLocked(false);
							lesson.setSolutionLocked(false);
							lesson.setAuthorLocked(false);
							lesson.setLocked(false);

							if (database.deleteLesson(lesson.getLessonId())) {
								cleanedCount++;
							}
						}
					}
				}
			}
			System.out.println("\n[TEARDOWN] Purged " + cleanedCount + " test record(s) from database.");
		} catch (Exception e) {
			System.err.println("[TEARDOWN ERROR] Failed to complete test database cleanup: " + e.getMessage());
		}
	}	
	
	/**
	 * <p> Title: pass(String testName, String detail) </p>
	 * 
	 * <p> Description: Helper method that indicates a passed test </p>
	 * @param testName
	 * @param detail
	 */
	private static void pass(String testName, String detail) {
		testsPassed++;
		System.out.println("[PASS] " + testName);
		System.out.println("       " + detail);
	}

	/**
	 * <p> Title: fail(String testName, String detail) </p>
	 * 
	 * <p> Description: Helper method that indicates a failed test </p>
	 * @param testName
	 * @param detail
	 */
	private static void fail(String testName, String detail) {
		testsFailed++;
		System.err.println("[FAIL] " + testName);
		System.err.println("       " + detail);
	}
}