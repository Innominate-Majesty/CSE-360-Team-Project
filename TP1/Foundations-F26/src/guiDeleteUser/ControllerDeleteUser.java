package guiDeleteUser;

import java.util.ArrayList;
import javafx.beans.value.ObservableValue;
import database.Database;
import guiDeleteUser.ViewDeleteUser;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;

/*******
 * <p> Title: ControllerDeleteUser Class. </p>
 * 
 * <p> Description: The Java/FX-based Delete User Page.  This class provides the controller
 * actions basic on the user's use of the JavaFX GUI widgets defined by the View class.
 * 
 * The class has been written assuming that the View or the Model are the only class methods that
 * can invoke these methods.  This is why each has been declared at "protected".  Do not change any
 * of these methods to public.</p>
 * 
 * <p> Copyright: Alan G. Shimp © 2026 </p>
 * 
 * @author Alan G. Shimp
 * 
 * @version 1.00		2026-09-12 Initial version  
 */

public class ControllerDeleteUser {
    
    /*-********************************************************************************************

	User Interface Actions for this page
	
	This controller is not a class that gets instantiated.  Rather, it is a collection of protected
	static methods that can be called by the View (which is a singleton instantiated object) and 
	the Model is often just a stub, or will be a singleton instantiated object.
	
	 */

	/**
	 * Default constructor is not used.
	 */
	public ControllerDeleteUser() {
	}
	
	// Reference for the in-memory database so this package has access
	private static Database theDatabase = applicationMain.FoundationsMain.database;		

	/**********
	 * <p> Method: doSelectUser() </p>
	 * 
	 * <p> Description: This method uses the ComboBox widget, fetches which item in the ComboBox
	 * was selected (a user in this case), and establishes that user and the current user, setting
	 * easily accessible values without needing to do a query. </p>
	 * 
	 */
	protected static void doSelectUser() {
		ViewDeleteUser.theSelectedUser = 
				(String) ViewDeleteUser.combobox_SelectUser.getValue();
		theDatabase.getUserAccountDetails(ViewDeleteUser.theSelectedUser);
		repaintTheWindow();
	}
	
	
	/**********
	 * <p> Method: repaintTheWindow() </p>
	 * 
	 * <p> Description: This method determines the current state of the window and then establishes
	 * the appropriate list of widgets in the Pane to show the proper set of current values. </p>
	 * 
	 */
	protected static void repaintTheWindow() {
		// Clear what had been displayed
		ViewDeleteUser.theRootPane.getChildren().clear();
		
		// Determine which of the three views to show to the user
		if (ViewDeleteUser.theSelectedUser.compareTo("<Select a User>") == 0) {
			// Only show the request to select a user to be updated and the ComboBox
			ViewDeleteUser.theRootPane.getChildren().addAll(
					ViewDeleteUser.label_PageTitle, ViewDeleteUser.label_UserDetails, 
					ViewDeleteUser.button_UpdateThisUser, ViewDeleteUser.label_SelectUser,
					ViewDeleteUser.combobox_SelectUser, ViewDeleteUser.button_Return,
					ViewDeleteUser.button_Logout, ViewDeleteUser.button_Quit);
		}
        else if (ViewDeleteUser.theSelectedUser.compareTo(ViewDeleteUser.theUser.getUserName()) ==
            0) {
            // If the user selects themself, show the message telling them they cannot delete.
            ViewDeleteUser.theRootPane.getChildren().addAll(
			    ViewDeleteUser.label_PageTitle, ViewDeleteUser.label_UserDetails,
				ViewDeleteUser.button_UpdateThisUser,
				ViewDeleteUser.label_SelectUser,
				ViewDeleteUser.combobox_SelectUser, 
                ViewDeleteUser.label_SelectSelf, 
				ViewDeleteUser.button_Return,
				ViewDeleteUser.button_Logout,
				ViewDeleteUser.button_Quit);
            }
		else {
			// Show the fields to delete a user as a valid selection has been made.
			ViewDeleteUser.theRootPane.getChildren().addAll(
					ViewDeleteUser.label_PageTitle, ViewDeleteUser.label_UserDetails,
					ViewDeleteUser.button_UpdateThisUser,
					ViewDeleteUser.label_SelectUser,
					ViewDeleteUser.combobox_SelectUser, 
                    ViewDeleteUser.label_AreYouSure,
                    ViewDeleteUser.button_Yes, 
					ViewDeleteUser.button_Return,
					ViewDeleteUser.button_Logout,
					ViewDeleteUser.button_Quit);
		}
		
		// Add the list of widgets to the stage and show it
		
		// Set the title for the window
		ViewDeleteUser.theStage.setTitle("CSE 360 Foundation Code: Admin Operations Page");
		ViewDeleteUser.theStage.setScene(ViewDeleteUser.theDeleteUserScene);
		ViewDeleteUser.theStage.show();
	}
	
	
	/**********
     * <p> Method: performDeleteUser() </p>
     * 
     * <p> Description: This method deletes the selected user and resets the ComboBox. </p>
     * 
     */
    protected static void performDeleteUser() {
        // Determine which item in the ComboBox list was selected.
    	ViewDeleteUser.theSelectedUser = ViewDeleteUser.
    			combobox_SelectUser.getValue();
    	
    	// If the selection is the list header (e.g., "<Select a User>") don't do anything
    	if (ViewDeleteUser.theSelectedUser.compareTo("<Select a User>") != 0) {
    		
    		// If an actual user was selected, delete that user from the database
    		if (theDatabase.deleteUser(ViewDeleteUser.theSelectedUser)) {
    			// Reset the page
				ViewDeleteUser.combobox_SelectUser = new ComboBox <String>();
    			ViewDeleteUser.userList = new ArrayList<String>();
    			ViewDeleteUser.setupComboBoxUI(ViewDeleteUser.combobox_SelectUser, "Arial", 16, 250, 280,
    				125);
    			ViewDeleteUser.userList = theDatabase.getUserList();
    			ViewDeleteUser.combobox_SelectUser.setItems(FXCollections.
    				observableArrayList(ViewDeleteUser.userList));
    			ViewDeleteUser.combobox_SelectUser.getSelectionModel().select(0);
    			ViewDeleteUser.combobox_SelectUser.getSelectionModel().selectedItemProperty()
    	    	.addListener((@SuppressWarnings("unused") ObservableValue<? extends String> observable, 
    	    		@SuppressWarnings("unused") String oldvalue, 
    	    		@SuppressWarnings("unused") String newValue) -> {doSelectUser();});
    			doSelectUser();

				// Show a confirmation message
				ViewDeleteUser.alertDeleted.setHeaderText("The User " +
					ViewDeleteUser.theSelectedUser + " Was Deleted.");
				ViewDeleteUser.alertDeleted.showAndWait();
    		}
    	}
    }
	
	
	/**********
	 * <p> Method: performReturn() </p>
	 * 
	 * <p> Description: This method returns the user (who must be an Admin as only admins are the
	 * only users who have access to this page) to the Admin Home page. </p>
	 * 
	 */
	protected static void performReturn() {
		guiAdminHome.ViewAdminHome.displayAdminHome(ViewDeleteUser.theStage,
				ViewDeleteUser.theUser);
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
		guiUserLogin.ViewUserLogin.displayUserLogin(ViewDeleteUser.theStage);
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
}
