package guiContributorHome;

import java.sql.SQLException;
import java.util.List;

import database.Database;
import entityClasses.LessonsLearnedList;
import entityClasses.LessonLearned;

/*******
 * <p> Title: ModelContributorHome Class. </p>
 * 
 * <p> Description: The ContributorHome Page Model.</p>
 * 
 * <p> Copyright: Lynn Robert Carter © 2025 </p>
 * 
 * @author Lynn Robert Carter
 * @author Alan G. Shimp
 * 
 * @version 1.00		2025-08-15 Initial version
 * @version 1.01		2025-09-13 Updated JavaDoc description
 * @version 1.02		2026-10-03 Contributor update
 *  
 */

public class ModelContributorHome {
	
	// Stores the shared database used by the application
    private Database theDatabase;
    
    /******
     * 
     * <p> Method: ModelContributorHome </p>
     * 
     * <p> Description: Constructs the model using the application's shared database </p>
     * 
     * @param database specifies the shared application database
     * 
     */

    public ModelContributorHome(Database database) {

        // Stores the provided database for retrieving account summaries
        this.theDatabase = database;

    }
    
    /******
     * 
     * <p> Method: getAllUserLessonsLearned() </p>
     * 
     * <p> Description: Requests all lessons learned created by the current user from the
     * database </p>
     * 
     * @return the LessonsLearnedList of user-created lessons learned
     * 
     * @throws SQLException when the account summaries cannot be retrieved
     * 
     */

    public LessonsLearnedList getAllUserLessonsLearned() throws SQLException {

        // Initialize a LessonsLearnedList
    	LessonsLearnedList theList = new LessonsLearnedList();
    	
    	// Retrieve a list of the unique ID numbers of every lesson created by the current user
    	// from the database.
        List<Integer> idList = theDatabase.getLessonIdsByName(ViewContributorHome.theUser.getUserName());
        
        // Use each ID to extract the information for a full lesson learned and add it to the list
        for (int i = 0; i < idList.size(); i++) {
        	// Save the ID number.
        	int id = idList.get(i);
        	
        	// Load the selected lesson.
        	theDatabase.getLessonDetails(id);
        	
        	// Create the lesson learned object.
        	LessonLearned lesson = new LessonLearned(theDatabase.getCurrentLessonTitle(),
        			theDatabase.getCurrentLessonCreatorName(),
        			theDatabase.getCurrentLessonCoreInfo(), i);
        	
        	// Update the id of the lesson to match the database.
        	lesson.setId(id);
        	
        	// Update the locked status of the lesson as needed.
        	lesson.setLockedStatus(theDatabase.getCurrentLessonLockedStatus());
        	
        	// Add the lesson to the list.
        	theList.addLesson(lesson);
        }
        return theList;
    }
}
