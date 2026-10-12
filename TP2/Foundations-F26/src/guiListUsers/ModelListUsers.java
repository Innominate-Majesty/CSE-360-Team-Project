package guiListUsers;

import java.sql.SQLException;

import java.util.List;

import database.Database;

import entityClasses.UserAccountSummary;


/******
 * 
 * <p> Title: ModelListUsers Class </p>
 * 
 * <p> Description: The model for the View User Accounts page. This class retrieves user account summaries from the database </p>
 * 
 */

public class ModelListUsers {

    // Stores the shared database used by the application
    private Database theDatabase;

    /******
     * 
     * <p> Method: ModelListUsers </p>
     * 
     * <p> Description: Constructs the model using the application's shared database </p>
     * 
     * @param database specifies the shared application database
     * 
     */

    public ModelListUsers(Database database) {

        // Stores the provided database for retrieving account summaries
        this.theDatabase = database;

    }

    /******
     * 
     * <p> Method: getAllUserAccountSummaries() </p>
     * 
     * <p> Description: Requests every user account summary from the database </p>
     * 
     * @return the list of user account summaries
     * 
     * @throws SQLException when the account summaries cannot be retrieved
     * 
     */

    public List<UserAccountSummary> getAllUserAccountSummaries() throws SQLException {

        // Returns the account summaries retrieved from the database
        return theDatabase.getAllUserAccountSummaries();
    }
}