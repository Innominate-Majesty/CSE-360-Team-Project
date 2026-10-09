package guiContributorHome;

import java.sql.SQLException;

import database.Database;
import entityClasses.LessonsLearnedList;
import entityClasses.LessonLearned;

/*******
 * <p> Title: ControllerContributorHome Class. </p>
 * 
 * <p> Description: The Java/FX-based Role 1 Home Page.  This class provides the controller
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
 * @author Alan G. Shimp
 * 
 * @version 1.00		2025-08-17 Initial version
 * @version 1.01		2025-09-16 Update Javadoc documentation *  
 * @version 1.02		2026-10-04 Update to Contributor role
 */

public class ControllerContributorHome {

	// Creates the model using the application's shared database
    private static ModelContributorHome theModel = new ModelContributorHome(applicationMain.FoundationsMain.database);
    
    // Reference for the in-memory database so this package has access
 	private static Database theDatabase = applicationMain.FoundationsMain.database;
	
	/*-*******************************************************************************************

	User Interface Actions for this page
	
	This controller is not a class that gets instantiated.  Rather, it is a collection of protected
	static methods that can be called by the View (which is a singleton instantiated object) and 
	the Model is often just a stub, or will be a singleton instantiated object.
	
	 */

	/**
	 * Default constructor is not used.
	 */
	public ControllerContributorHome() {
	}

	/**********
	 * <p> Method: performUpdate() </p>
	 * 
	 * <p> Description: This method directs the user to the User Update Page so the user can change
	 * the user account attributes. </p>
	 * 
	 */
	protected static void performUpdate () {
		guiUserUpdate.ViewUserUpdate.displayUserUpdate(ViewContributorHome.theStage, ViewContributorHome.theUser);
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
		guiUserLogin.ViewUserLogin.displayUserLogin(ViewContributorHome.theStage);
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
	
	/******
     * 
     * <p> Method: loadLessonsLearned() </p>
     * 
     * <p> Description: Loads all the user's lessons learned and sends them to the view </p>
     * 
     */

    protected static void loadLessonsLearned() {

        // Try to retrieve the account summaries from the model
        try {

            // Retrieves every account summary
            LessonsLearnedList theList = theModel.getAllUserLessonsLearned();

            // Sends the retrieved account summaries to the view
            ViewContributorHome.displayLessonsLearned(theList);

        }

        catch (SQLException exception) {

            // Print error message for debugging
            System.err.println("Unable to load lessons: " + exception.getMessage());

            // Tells the view to display its database error state
            ViewContributorHome.displayLessonsLearned(null);

        }
    }
    
    /******
     * 
     * <p> Method: performRefresh() </p>
     * 
     * <p> Description: Reloads the lessons learned so the page displays the database's current information </p>
     * 
     */

    protected static void performRefresh() {

        // Loads the account summaries again from the database
        loadLessonsLearned();

    }
    
    /******
     * 
     * <p> Method: performBackToList() </p>
     * 
     * <p> Description: Returns from the selected lesson details panel to the current lesson card list </p>
     * 
     */

    protected static void performBackToList() {

        // Tells the view to display its current account list
        ViewContributorHome.displayLessonList();
        
    }
    
    /******
     * 
     * <p> Method: deleteLesson(LessonLearned toDelete) </p>
     * 
     * <p> Description: Deletes the selected lesson and returns to the list </p>
     * 
     */
    protected static void deleteLesson(LessonLearned toDelete) {
    	int id = toDelete.getId();
    	
    	theDatabase.deleteLessonLearned(id);
    	
    	ViewContributorHome.displayLessonList();
    }
}
