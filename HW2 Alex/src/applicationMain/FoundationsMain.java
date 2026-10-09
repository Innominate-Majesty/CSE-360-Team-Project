package applicationMain;
	
import java.sql.SQLException;
import database.Database;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * Main Class that launches the Foundations demonstration application.
 * 
 * <p>This is a JavaFX application designed to serve as a foundation for the CSE360
 * Individual Homework and Team Project assignments and demonstrate the use of the following:</p>
 * 
 * <ul>
 *   <li>The Singleton Design Pattern - The GUI uses the MVC Design Pattern, and each of the
 *       three components is instantiated once. This requires special coding. See this
 *       <a href="https://en.wikipedia.org/wiki/Singleton_pattern">article</a> for more insights.</li>
 *   <li>Javadoc documentation</li>
 *   <li>Internal documentation beyond Javadoc with a focus on "why" (as well as "what" when it
 *       might not be obvious). The goal of the documentation is to help those who follow
 *       you to benefit from your work without needing to do all the research and the
 *       sometimes frustrating, if not painful, experimentation until you get it working.
 *       This is especially true when the obvious way to do something does not work!</li>
 * </ul>
 *
 * <p>On startup, the application tries to connect with the Foundations in-memory database. If a
 * connection to the database is currently active, an alert is displayed explaining the situation
 * to the users and the application quits when the user acknowledges the alert.</p>
 *
 * <p>If the connection is successful, a check is made to see if the database is empty. If so, this
 * must be the first execution of the application and the person running the application is assumed
 * to be an administrator. That user is required to provide an Admin username and password before 
 * anything else can happen. Doing this eliminates a common weakness of "hard coded credentials".
 * With that done, the admin can provide more details to the system (e.g., name and email address),
 * and then proceed to do other admin activities.</p>
 * 
 * <p>If the database is not empty, the system brings up the standard login page and requires 
 * an existing user to log in or a potential user to provide an invitation code to establish a new
 * account. Once logged in or after creating an account, the user can perform whatever role(s) are set
 * for that user. This class's method stops as soon as the Graphical User Interface (GUI) for
 * one of the two options has been set up. From that point forward, actions are performed in
 * reaction to the user engaging with widgets on the currently visible page.</p>
 * 
 * <p>This application uses singletons and Model View Controller (MVC) View pattern to control the 
 * use of memory by avoiding multiple copies of the same page.</p>
 * 
 * <p>This application does not use the command line arguments (i.e., {@code "String[] args"}), but Java and
 * JavaFX in Eclipse require they be made available in the application's main method, even if they
 * are not needed.</p>
 *
 * <p>Copyright: Lynn Robert Carter © 2025</p>
 *
 * @author Lynn Robert Carter
 * @version 3.00	2025-08-17 Rewrite of this application for the Fall offering of CSE 360 and
 * other ASU courses.
 * @version 3.02	2025-12-17 Enhancements in support of Spring 2026
 */
public class FoundationsMain extends Application {
	
	/**
     * Constructs a new {@code FoundationsMain} instance.
     */
	public FoundationsMain() {
		//Default constructor
	}
	
	/*-*******************************************************************************************

	Attributes
	
	**********************************************************************************************/
	
	/**
	 * Application window width required by the user interface.
	 */
	public final static double WINDOW_WIDTH = 800;

	/**
	 * Application window height required by the user interface.
	 */
	public final static double WINDOW_HEIGHT = 600;

	/**
	 * Shared reference to the application database instance.
	 */
	public static Database database = new Database();

    private Alert databaseInUse = new Alert(AlertType.INFORMATION);

	/**
	 * Tracks which role's home page is currently active.
	 */
	public static int activeHomePage = 0;		// Which role's home page is currently active?
												// Role 0 is the admin role number

	/**
	 * Initializes and starts the primary JavaFX stage, verifies database connectivity,
	 * and loads either the first-time setup or the login view.
	 *
	 * @param theStage the primary stage for this application
	 */
	@Override
	public void start(Stage theStage) {
		
		// Connect to the in-memory database
		try {
			// Connect to the database
			database.connectToDatabase();

		} catch (SQLException e) {
			// If the connection request fails, it usually means some other app is using it
			databaseInUse.setTitle("*** ERROR ***");
			databaseInUse.setHeaderText("Database Is Already Being Used");
			databaseInUse.setContentText("Please stop the other instance and try again!");
			databaseInUse.showAndWait();
			System.exit(0);
		}
		
		// If the database is empty, no users have been established, so this user must be an admin
		// user doing initial system startup activities and we need to set that admin's username
		// and password using a special start page.
		
		boolean empty = database.isDatabaseEmpty();
		
		System.out.println(">>> DATABASE EMPTY = " + empty);

		if (empty) {
		    System.out.println(">>> OPENING FIRST ADMIN PAGE <<<");
		    guiFirstAdmin.ViewFirstAdmin.displayFirstAdmin(theStage);
		}
		else {
		    System.out.println(">>> OPENING LOGIN PAGE <<<");
		    guiUserLogin.ViewUserLogin.displayUserLogin(theStage);
		}
	}

	/**
	 * Launches the JavaFX runtime environment.
	 * 
	 * <p>This main method does not perform any special function for this application
	 * beyond launching JavaFX. If command line arguments are provided, they are ignored.</p>
	 * 
	 * @param args the array of command-line parameters (not used)
	 */
	public static void main(String[] args) {
		launch(args);	// The launch method loads JavaFX and invokes its initialization.  When it
						// is done, it calls the start method shown above.
	}
}