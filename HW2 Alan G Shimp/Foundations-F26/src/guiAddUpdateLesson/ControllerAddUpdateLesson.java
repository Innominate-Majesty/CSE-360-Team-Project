package guiAddUpdateLesson;

import java.sql.SQLException;

import database.Database;
import entityClasses.User;
import guiDeleteUser.ViewDeleteUser;
import guiListUsers.ViewListUsers;
import entityClasses.Experience;
import entityClasses.LessonLearned;
import entityClasses.LessonsLearnedList;
import javafx.scene.paint.Color;
import javafx.animation.PauseTransition;
import javafx.util.Duration;

/*******
 * <p> Title: ControllerAddUpdateLesson Class. </p>
 * 
 * <p> Description: The Java/FX-based Add/Update Lesson Page.  This class provides the controller
 * actions to allow the user to create or modify a Lesson Learned.
 * 
 * The class has been written assuming that the View or the Model are the only class methods that
 * can invoke these methods.  This is why each has been declared at "protected".  Do not change any
 * of these methods to public.</p>
 * 
 * <p> Copyright: Alan G. Shimp © 2026 </p>
 * 
 * @author Alan G. Shimp
 * 
 * @version 1.00	2026-10-04 Initial version
 *  
 */

@SuppressWarnings("unused")
public class ControllerAddUpdateLesson {
	/*-********************************************************************************************

	The User Interface Actions for this page
	
	This controller is not a class that gets instantiated.  Rather, it is a collection of protected
	static methods that can be called by the View (which is a singleton instantiated object) and 
	the Model is often just a stub, or will be a singleton instantiated object.
	
	*/
	
	// Reference for the in-memory database so this package has access
	private static Database theDatabase = applicationMain.FoundationsMain.database;
	
	/**
	 * Default constructor is not used.
	 */
	public ControllerAddUpdateLesson() {
	}
	
	/**
	 * <p> Method: void repaintTheWindow() </p>
	 * 
	 * <p> Description: On subsequent calls of this page after the first, updates context-sensitive
	 * widgets. </p>
	 */
	public static void repaintTheWindow() {
		if (ViewAddUpdateLesson.isNew) {
			// Set page title
			ViewAddUpdateLesson.label_PageTitle.setText("Add Lesson Learned Page");
			
			// Empty the text fields
			ViewAddUpdateLesson.text_Title.setText("");
			ViewAddUpdateLesson.text_CoreInfo.setText("");
			
			//Set the confirm button to add a new lesson or show an error if needed
			ViewAddUpdateLesson.button_Confirm.setOnAction((_) -> {
				try {
					addLesson();
				}
				catch (SQLException exception) {
					ViewAddUpdateLesson.alert_AddUpdate.setTitle("Database Error");
					ViewAddUpdateLesson.alert_AddUpdate.
						setHeaderText("Could not add lesson to database.");
					ViewAddUpdateLesson.alert_AddUpdate.setContentText("Please try again.");
					ViewAddUpdateLesson.alert_AddUpdate.showAndWait();
					
					System.err.println("Unable to load lessons: " + exception.getMessage());
				}
			});
		}
		else {
			// Set page title
			ViewAddUpdateLesson.label_PageTitle.setText("Update Lesson Learned Page");
			
			// Fill the text fields with the existing information from the lesson to be updated
			ViewAddUpdateLesson.text_Title.setText(ViewAddUpdateLesson.theLesson.getTitle());
			ViewAddUpdateLesson.text_CoreInfo.setText(ViewAddUpdateLesson.theLesson.getCoreInfo());
			
			// Set the confirm button to update the current lesson or show an error if needed
			ViewAddUpdateLesson.button_Confirm.setOnAction((_) -> {
				try {
					updateLesson();
				}
				catch (SQLException exception) {
					ViewAddUpdateLesson.alert_AddUpdate.setTitle("Database Error");
					ViewAddUpdateLesson.alert_AddUpdate.
						setHeaderText("Could not update lesson in database.");
					ViewAddUpdateLesson.alert_AddUpdate.setContentText("Please try again.");
					ViewAddUpdateLesson.alert_AddUpdate.showAndWait();
					
					// Print error message for debugging
		            System.err.println("Unable to load lessons: " + exception.getMessage());
				}
			});
		}
		
		// Place all of the established GUI elements into the pane
    	ViewAddUpdateLesson.theRootPane.getChildren().clear();
    	ViewAddUpdateLesson.theRootPane.getChildren().addAll(ViewAddUpdateLesson.label_PageTitle,
    			ViewAddUpdateLesson.label_UserDetails, ViewAddUpdateLesson.button_UpdateThisUser,
    			ViewAddUpdateLesson.text_Title, ViewAddUpdateLesson.text_CoreInfo,
    			ViewAddUpdateLesson.label_TitleField, ViewAddUpdateLesson.label_InfoField,
    			ViewAddUpdateLesson.button_Confirm, ViewAddUpdateLesson.button_Return,
    			ViewAddUpdateLesson.button_Logout, ViewAddUpdateLesson.button_Quit);
	}
	
	/**
	 * <p> Method: void addLesson() </p>
	 * 
	 * <p> Description: Adds a new lesson learned using the inputs from the text boxes. </p>
	 */
	public static void addLesson() throws SQLException {
		// Save the title from the text box
		String title = ViewAddUpdateLesson.text_Title.getText();
		// Get the user's name
		String creatorName = ViewAddUpdateLesson.theUser.getUserName();
		// Save the core information from the text box
		String coreInfo = ViewAddUpdateLesson.text_CoreInfo.getText();
		
		// Validate the title and core information before creating a lesson and saving it.
		boolean titleGood = validateTitle(title);
		boolean infoGood = validateCoreInfo(coreInfo);
		
		if (!titleGood && title.length() < 4) {
			ViewAddUpdateLesson.alert_AddUpdate.setTitle("Title Too Short");
			ViewAddUpdateLesson.alert_AddUpdate.setHeaderText("Title must be at least four " +
					"characters.");
			ViewAddUpdateLesson.alert_AddUpdate.setContentText("Please consider providing more " +
					"information.");
			ViewAddUpdateLesson.alert_AddUpdate.showAndWait();
		}
		else if (!titleGood) {
			ViewAddUpdateLesson.alert_AddUpdate.setTitle("Title Too Long");
			ViewAddUpdateLesson.alert_AddUpdate.setHeaderText("Title must be no more than 255 " +
					"characters.");
			ViewAddUpdateLesson.alert_AddUpdate.setContentText("Please consider moving some " +
					"information to the core information box.");
			ViewAddUpdateLesson.alert_AddUpdate.showAndWait();
		}
		else if (!infoGood && coreInfo.length() < 4) {
			ViewAddUpdateLesson.alert_AddUpdate.setTitle("Info Too Short");
			ViewAddUpdateLesson.alert_AddUpdate.setHeaderText("Core information must be at " +
					"least four characters.");
			ViewAddUpdateLesson.alert_AddUpdate.setContentText("Please consider providing more " +
					"information.");
			ViewAddUpdateLesson.alert_AddUpdate.showAndWait();
		}
		else if (!infoGood) {
			ViewAddUpdateLesson.alert_AddUpdate.setTitle("Info Too Long");
			ViewAddUpdateLesson.alert_AddUpdate.setHeaderText("Core information must be no more " +
					"than 2047 characters.");
			ViewAddUpdateLesson.alert_AddUpdate.setContentText("Brevity is the soul of wit. " +
					"Summarize?");
			ViewAddUpdateLesson.alert_AddUpdate.showAndWait();
		}
		else {
			LessonLearned toAdd = new LessonLearned(title, creatorName, coreInfo, -1);
			
			theDatabase.addLesson(toAdd);
			
			ViewAddUpdateLesson.alert_AddUpdate.setTitle("Success");
			ViewAddUpdateLesson.alert_AddUpdate.setHeaderText("Lesson Added to Database");
			ViewAddUpdateLesson.alert_AddUpdate.setContentText("Returning to user home");
			ViewAddUpdateLesson.alert_AddUpdate.showAndWait();
			
			performReturn();
		}
	}
	
	/**
	 * <p> Method: void updateLesson() </p>
	 * 
	 * <p> Description: Saves the inputs from the text boxes to the database. </p>
	 * 
	 * @throws SQLException if the database addition fails
	 */
	public static void updateLesson() throws SQLException {
		String title = ViewAddUpdateLesson.text_Title.getText();
		String coreInfo = ViewAddUpdateLesson.text_CoreInfo.getText();
		
		boolean titleGood = validateTitle(title);
		boolean infoGood = validateCoreInfo(coreInfo);
		
		if (!titleGood && title.length() < 4) {
			ViewAddUpdateLesson.alert_AddUpdate.setTitle("Title Too Short");
			ViewAddUpdateLesson.alert_AddUpdate.setHeaderText("Title must be at least four " +
					"characters.");
			ViewAddUpdateLesson.alert_AddUpdate.setContentText("Please consider providing more " +
					"information.");
			ViewAddUpdateLesson.alert_AddUpdate.showAndWait();
		}
		else if (!titleGood) {
			ViewAddUpdateLesson.alert_AddUpdate.setTitle("Title Too Long");
			ViewAddUpdateLesson.alert_AddUpdate.setHeaderText("Title must be no more than 255 " +
					"characters.");
			ViewAddUpdateLesson.alert_AddUpdate.setContentText("Please consider moving some " +
					"information to the core information box.");
			ViewAddUpdateLesson.alert_AddUpdate.showAndWait();
		}
		else if (!infoGood && coreInfo.length() < 4) {
			ViewAddUpdateLesson.alert_AddUpdate.setTitle("Info Too Short");
			ViewAddUpdateLesson.alert_AddUpdate.setHeaderText("Core information must be at " +
					"least four characters.");
			ViewAddUpdateLesson.alert_AddUpdate.setContentText("Please consider providing more " +
					"information.");
			ViewAddUpdateLesson.alert_AddUpdate.showAndWait();
		}
		else if (!infoGood) {
			ViewAddUpdateLesson.alert_AddUpdate.setTitle("Info Too Long");
			ViewAddUpdateLesson.alert_AddUpdate.setHeaderText("Core information must be no more " +
					"than 2047 characters.");
			ViewAddUpdateLesson.alert_AddUpdate.setContentText("Brevity is the soul of wit. " +
					"Summarize?");
			ViewAddUpdateLesson.alert_AddUpdate.showAndWait();
		}
		else {
			int id = ViewAddUpdateLesson.theLesson.getId();
			
			theDatabase.updateLessonTitle(id, title);
			theDatabase.updateLessonCoreInfo(id, coreInfo);
			
			ViewAddUpdateLesson.alert_AddUpdate.setTitle("Success");
			ViewAddUpdateLesson.alert_AddUpdate.setHeaderText("Lesson information updated.");
			ViewAddUpdateLesson.alert_AddUpdate.setContentText("Returning to user home");
			ViewAddUpdateLesson.alert_AddUpdate.showAndWait();
			
			performReturn();
		}
	}
	
	/**********
	 * <p> Method: performReturn() </p>
	 * 
	 * <p> Description: This method returns the user (who must be a Contributor as contributors are
	 * the only users who have access to this page) to the Contributor Home page. </p>
	 * 
	 */
	protected static void performReturn() {
		guiContributorHome.ViewContributorHome.displayContributorHome(ViewAddUpdateLesson.theStage,
				ViewAddUpdateLesson.theUser);
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
		guiUserLogin.ViewUserLogin.displayUserLogin(ViewAddUpdateLesson.theStage);
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
	
	/**
	 * <p> Method: boolean validateTitle(String title) </p>
	 * 
	 * <p> Description: Checks if the title is between 4 and 255 characters inclusive, returns true
	 * if so, false if not. It also displays an error message if false. </p>
	 * 
	 * @param title is the title to validate.
	 * 
	 * @return a boolean TRUE if valid or FALSE if not.
	 */
	protected static boolean validateTitle(String title) {
		if (title.length() < 4) {
			return false;
		}
		else if (title.length() > 255) {
			return false;
		}
		else return true;
	}
	
	/**
	 * <p> Method: boolean validateCoreInfo(String coreInfo) </p>
	 * 
	 * <p> Description: Checks if the core information is between 4 and 2047 characters inclusive,
	 * returns true if so, false if not. It also displays an error message if false. </p>
	 * 
	 * @param coreInfo is the core information to validate.
	 * 
	 * @return a boolean TRUE if valid or FALSE if not.
	 */
	protected static boolean validateCoreInfo(String coreInfo) {
		if (coreInfo.length() < 4) {
			return false;
		}
		else if (coreInfo.length() > 2047) {
			return false;
		}
		else return true;
	}
}