package guiListUsers;

import java.sql.SQLException;
import java.util.List;

import entityClasses.UserAccountSummary;

/******
 * 
 * <p> Title: ControllerListUsers Class </p>
 * 
 * <p> Description: The controller for the View User Accounts page. This class loads the account summaries and handles the admin's actions on the page </p>
 * 
 */

public class ControllerListUsers {

    // Creates the model using the application's shared database
    private static ModelListUsers theModel = new ModelListUsers(applicationMain.FoundationsMain.database);

    /*******
     * 
     * <p> Method: ControllerListUsers() </p>
     * 
     * <p> Description: Constructs the View User Accounts controller </p>
     * 
     */

    public ControllerListUsers() {

        // Controller initialization is not needed

    }

    /******
     * 
     * <p> Method: loadUserAccounts() </p>
     * 
     * <p> Description: Loads all account summaries and sends them to the view (without showing the password) </p>
     * 
     */

    protected static void loadUserAccounts() {

        // Try to retrieve the account summaries from the model
        try {

            // Retrieves every account summary
            List<UserAccountSummary> accountSummaries = theModel.getAllUserAccountSummaries();

            // Sends the retrieved account summaries to the view
            ViewListUsers.displayAccountSummaries(accountSummaries);

        }

        catch (SQLException exception) {

            // Print error message for debugging
            System.err.println("Unable to load user accounts: " + exception.getMessage());

            // Tells the view to display its database error state
            ViewListUsers.displayAccountSummaries(null);

        }
    }

    /******
     * 
     * <p> Method: performRefresh() </p>
     * 
     * <p> Description: Reloads the account summaries so the page displays the database's current information </p>
     * 
     */

    protected static void performRefresh() {

        // Loads the account summaries again from the database
        loadUserAccounts();

    }

    /******
     * 
     * <p> Method: performBackToList() </p>
     * 
     * <p> Description: Returns from the selected account details panel to the current account card list </p>
     * 
     */

    protected static void performBackToList() {

        // Tells the view to display its current account list
        ViewListUsers.displayAccountList();
        
    }

    /******
     * 
     * <p> Method: performReturn() </p>
     * 
     * <p> Description: Returns the admin to the Admin Home page </p>
     * 
     */

    protected static void performReturn() {

        // Displays the Admin Home page using the current stage and admin
        guiAdminHome.ViewAdminHome.displayAdminHome(ViewListUsers.theStage, ViewListUsers.theUser);

    }

    /******
     * 
     * <p> Method: performLogout() </p>
     * 
     * <p> Description: Logs the admin out and returns to the login page </p>
     * 
     */

    protected static void performLogout() {

        // Displays the log in page using the current application stage
        guiUserLogin.ViewUserLogin.displayUserLogin(ViewListUsers.theStage);

    }

    /******
     * 
     * <p> Method: performQuit() </p>
     * 
     * <p> Description: Terminates the application </p>
     * 
     */

    protected static void performQuit() {

        // Terminates the running application
        System.exit(0);

    }
}