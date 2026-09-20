package guiListUsers;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import database.Database;
import entityClasses.UserAccountSummary;

/******
 * 
 * <p> Title: ModelListUsersTest Class </p>
 *
 * <p> Description: Contains JUnit tests for ModelListUsers.
 * This class uses a controlled TestingDatabase to verify that the model returns populated and empty user account lists
 * and properly handles database errors without connecting to the real H2 database </p>
 * 
 */

public class ModelListUsersTest {

	// Provides controlled database results without connecting to the real H2 database
	private static class TestingDatabase extends Database {

		// Stores the account summaries that should be returned during a test
		private List<UserAccountSummary> summaries;

		// Stores an optional database exception that should be thrown during a test
		private SQLException exceptionToThrow;

		/******
         * 
		 * <p> Method: TestingDatabase </p>
		 *
		 * <p> Description: Constructs a testing database that returns the provided
		 * user account summaries without connecting to H2 </p>
		 *
		 * @param summaries specifies the account summaries to return during a test
         * 
		 */

		TestingDatabase(List<UserAccountSummary> summaries) {

			// Stores the prepared account summaries for the test
			this.summaries = summaries;
		}

		/******
         * 
		 * <p> Method: setExceptionToThrow </p>
		 *
		 * <p> Description: Configures this testing database to simulate a database
		 * failure </p>
		 *
		 * @param exception specifies the database exception to throw
         * 
		 */

		void setExceptionToThrow(SQLException exception) {

			// Stores the exception that will be thrown when summaries are requested
			this.exceptionToThrow = exception;
		}

		/******
         * 
		 * <p> Method: getAllUserAccountSummaries </p>
		 *
		 * <p> Description: Returns the prepared summaries or throws the configured
		 * database exception </p>
		 *
		 * @return the prepared list of user account summaries
		 *
		 * @throws SQLException when this testing database is configured to simulate
		 * a database failure
         * 
		 */

		@Override
		public List<UserAccountSummary> getAllUserAccountSummaries() throws SQLException {

			// Simulates a database failure when an exception has been configured
			if (exceptionToThrow != null) {
				throw exceptionToThrow;
			}

			// Returns the prepared account summaries without accessing H2
			return summaries;
		}
	}

	/******
     * 
	 * <p> Method: testGetAllUserAccountSummariesReturnsDatabaseResults </p>
	 *
	 * <p> Description: Verifies that ModelListUsers returns the complete list of
	 * user account summaries given by the database </p>
	 *
	 * @throws SQLException when the model cannot retrieve the account summaries
     * 
	 */

	@Test
	void testGetAllUserAccountSummariesReturnsDatabaseResults() throws SQLException {

		// Creates the first account summary returned by the testing database
		UserAccountSummary firstAccount = new UserAccountSummary("venus.admin", "Venus", "", "Admin",
				"Venus", "venus@example.com", true, false, false);

		// Creates the second account summary returned by the testing database
		UserAccountSummary secondAccount = new UserAccountSummary("second.user", "Second", "Sample", "User",
				"Sam", "secondsampleuser@example.com", false, true, false);

		// Stores both prepared summaries in the expected result list
		List<UserAccountSummary> expectedSummaries = List.of(firstAccount, secondAccount);

		// Creates a testing database that returns the prepared summaries
		TestingDatabase testingDatabase = new TestingDatabase(expectedSummaries);

		// Creates the model using the controlled testing database
		ModelListUsers model = new ModelListUsers(testingDatabase);

		// Requests all account summaries through the model
		List<UserAccountSummary> actualSummaries = model.getAllUserAccountSummaries();

		// Verifies that the model did not return null
		assertNotNull(actualSummaries);

		// Verifies that the model returned the same list supplied by the database
		assertSame(expectedSummaries, actualSummaries);

		// Verifies that both prepared accounts were returned
		assertEquals(2, actualSummaries.size());

		// Verifies the username of the first returned account
		assertEquals("venus.admin", actualSummaries.get(0).getUserName());

		// Verifies the username of the second returned account
		assertEquals("second.user", actualSummaries.get(1).getUserName());

	}

	/******
     * 
	 * <p> Method: testGetAllUserAccountSummariesReturnsEmptyList </p>
	 *
	 * <p> Description: Verifies that ModelListUsers returns a non-null empty list
	 * when the database contains no user accounts </p>
	 *
	 * @throws SQLException when the model cannot retrieve the account summaries
     * 
	 */

	@Test
	void testGetAllUserAccountSummariesReturnsEmptyList() throws SQLException {

		// Creates an empty list representing a database with no user accounts
		List<UserAccountSummary> expectedSummaries = List.of();

		// Creates a testing database that returns the empty list
		TestingDatabase testingDatabase = new TestingDatabase(expectedSummaries);

		// Creates the model using the controlled testing database
		ModelListUsers model = new ModelListUsers(testingDatabase);

		// Requests all account summaries through the model
		List<UserAccountSummary> actualSummaries = model.getAllUserAccountSummaries();

		// Verifies that the model did not return null for an empty database
		assertNotNull(actualSummaries);

		// Verifies that the returned list contains no user accounts
		assertTrue(actualSummaries.isEmpty());

		// Verifies that the model returned the same empty list supplied by the database
		assertSame(expectedSummaries, actualSummaries);
        
	}

	/******
     * 
	 * <p> Method: testGetAllUserAccountSummariesPropagatesSQLException </p>
	 *
	 * <p> Description: Verifies that ModelListUsers passes a database error to
	 * its caller instead of hiding the error or returning an empty list </p>
     * 
	 */

	@Test
	void testGetAllUserAccountSummariesPropagatesSQLException() {

		// Creates a testing database with an empty prepared account list
		TestingDatabase testingDatabase = new TestingDatabase(List.of());

		// Creates the database exception that should be produced during the test
		SQLException expectedException = new SQLException("Unable to retrieve user accounts");

		// Configures the testing database to simulate the database failure
		testingDatabase.setExceptionToThrow(expectedException);

		// Creates the model using the controlled testing database
		ModelListUsers model = new ModelListUsers(testingDatabase);

		// Calls the model and captures the database exception passed to its caller
		SQLException actualException = assertThrows(SQLException.class, () -> model.getAllUserAccountSummaries());

		// Verifies that the model passed through the same database exception
		assertSame(expectedException, actualException);

	}
}
