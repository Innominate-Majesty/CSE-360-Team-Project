package guiContributor;

import entityClasses.Experience;
import entityClasses.ExperiencesList;
import entityClasses.LessonsLearned;
import entityClasses.LessonsLearnedList;
import guiCreateLesson.ViewCreateLesson;
import guiExperience.ControllerExperience;
import guiLessonDetails.ControllerLessonDetails;
import guiUpdateLesson.ControllerUpdateLesson;

/*******
 * <p> Title: ControllerContributorHome Class. </p>
 * 
 * <p> Description: Controls Contributor Home navigation, lesson searches, and the
 * contributor's lesson list. Feature actions are delegated to their corresponding
 * Create Lesson, Update Lesson, Lesson Details, and Experience MVC packages. </p>
 * 
 * <p> Copyright: Lynn Robert Carter © 2025 </p>
 * 
 * @author Lynn Robert Carter
 * 
 * @author Alexander Murray
 * @author Venus Ho
 * @author Alan Shimp
 * @author Caleb Beaven
 * @author Krithik Duraisamy
 * 
 * @version 1.00        2025-08-17 Initial version
 * @version 1.01        2025-09-16 Update Javadoc documentation
 * 
 * @version 2.0 		2026-10-22 Refactor class name
 * @version 2.1			2026-10-23 Update class
 * 
 */
public class ControllerContributorHome {

    /******
     * <p> Constructor: ControllerContributorHome() </p>
     *
     * <p> Description: This default constructor is not used because Contributor
     * Home actions are implemented as static controller methods. </p>
     *
     */
    public ControllerContributorHome() {
    }

    /**********
     * <p> Method: performUpdate() </p>
     * 
     * <p> Description: Directs the contributor to the User Update page. </p>
     * 
     */
    protected static void performUpdate() {
        guiUserUpdate.ViewUserUpdate.displayUserUpdate(
                ViewContributorHome.theStage, ViewContributorHome.theUser);
    }

    /**********
     * <p> Method: performLogout() </p>
     * 
     * <p> Description: Logs out the current contributor and displays the login page. </p>
     * 
     */
    protected static void performLogout() {
        guiUserLogin.ViewUserLogin.displayUserLogin(ViewContributorHome.theStage);
    }

    /**********
     * <p> Method: performQuit() </p>
     * 
     * <p> Description: Terminates the application. </p>
     * 
     */
    protected static void performQuit() {
        System.exit(0);
    }

    /**********
     * <p> Method: handleCreateLesson() </p>
     * 
     * <p> Description: Delegates the Create action to the Create Lesson MVC package. </p>
     * 
     */
    protected static void handleCreateLesson() {
        ViewCreateLesson.displayCreateLesson(
                ViewContributorHome.theStage,
                ViewContributorHome.theUser,
                ControllerContributorHome::handleReadAllLessons);
    }

    /**********
     * <p> Method: handleReadAllLessons() </p>
     * 
     * <p> Description: Retrieves and displays every lesson belonging to the active
     * contributor. </p>
     * 
     */
    protected static void handleReadAllLessons() {
        String author = ViewContributorHome.theUser != null
                ? ViewContributorHome.theUser.getUserName() : "Unknown User";
        LessonsLearnedList list =
                ModelContributorHome.getAllLessonsForContributor(author);

        if (list.isEmpty()) {
            ViewContributorHome.scroll_MyLessons.setVisible(false);
            ViewContributorHome.scroll_MyLessons.setManaged(false);
            ViewContributorHome.textarea_Display.setVisible(true);
            ViewContributorHome.textarea_Display.setManaged(true);
            ViewContributorHome.button_BackToLessons.setVisible(false);
            ViewContributorHome.textarea_Display.setText(
                    "No lessons found for contributor " + author);
        } else {
            ViewContributorHome.displayMyLessons(list);
        }
    }

    /**********
     * <p> Method: handleReadSingleLesson() </p>
     * 
     * <p> Description: Reads a contributor-owned lesson and its Experience history
     * into the retained text-results area. </p>
     * 
     */
    protected static void handleReadSingleLesson() {
        String idText = ViewContributorHome.textfield_LessonId.getText().trim();
        String author = ViewContributorHome.theUser == null
                ? "" : ViewContributorHome.theUser.getUserName();
        try {
            int lessonId = Integer.parseInt(idText);
            LessonsLearned lesson =
                    ModelContributorHome.getContributorLessonById(lessonId, author);
            if (lesson == null) {
                ViewContributorHome.textarea_Display.setText(
                        "No lesson record exists with ID " + lessonId + ".");
                return;
            }

            ExperiencesList experiences =
                    ModelContributorHome.getAllExperiencesForLesson(lessonId);
            StringBuilder output = new StringBuilder()
                    .append("Found Lesson:\n").append(lesson)
                    .append("\n  - Author: ").append(lesson.getAuthor())
                    .append("\n  - Problem Description: ").append(lesson.getProblemDescription())
                    .append("\n  - Solution: ").append(lesson.getSolution())
                    .append("\n\nExperiences:\n");

            if (experiences == null || experiences.isEmpty()) {
                output.append("  No Experiences have been added");
            } else {
                for (Experience experience : experiences.getExperiences()) {
                    output.append("\n  Experience ").append(experience.getExperienceId())
                            .append("\n    - Author: ").append(experience.getAuthor())
                            .append("\n    - Time Spent: ").append(experience.getTime()).append(" hours")
                            .append("\n    - What Was Done: ").append(experience.getWhat())
                            .append("\n    - How It Was Done: ").append(experience.getHow())
                            .append("\n    - Team Effort: ").append(experience.getEffort()).append("\n");
                }
            }
            ViewContributorHome.textarea_Display.setText(output.toString());
        } catch (NumberFormatException exception) {
            ViewContributorHome.textarea_Display.setText(
                    "No lesson record exists with ID " + idText + ".");
        }
    }

    /**********
     * <p> Method: handleReadFilteredSubset() </p>
     * 
     * <p> Description: Retrieves contributor lessons matching the entered category
     * or keyword. </p>
     * 
     */
    protected static void handleReadFilteredSubset() {
        String filter = ViewContributorHome.textfield_FilterCategory.getText();
        if (filter == null || filter.trim().isEmpty()) {
            ViewContributorHome.textarea_Display.setText(
                    "Search query cannot be blank. Enter a valid keyword or category.");
            return;
        }
        String author = ViewContributorHome.theUser == null
                ? "" : ViewContributorHome.theUser.getUserName();
        LessonsLearnedList subset =
                ModelContributorHome.getLessonsByFilter(filter.trim(), author);
        ViewContributorHome.textarea_Display.setText("Lessons List:\n" + subset);
    }

    /**********
     * <p> Method: handleOpenLessonDetails() </p>
     * 
     * <p> Description: Delegates the selected lesson to the Lesson Details MVC
     * package and connects its Update and Delete callbacks. </p>
     * 
     * @param lesson lesson selected from Contributor Home
     * 
     */
    protected static void handleOpenLessonDetails(LessonsLearned lesson) {
        if (lesson == null) return;
        int lessonId = lesson.getLessonId();
        ControllerLessonDetails.displayLessonDetails(
                ViewContributorHome.theStage,
                ViewContributorHome.theUser,
                lessonId,
                () -> openUpdateLesson(lessonId),
                ControllerContributorHome::handleReadAllLessons);
    }

    /**********
     * <p> Method: openUpdateLesson() </p>
     * 
     * <p> Description: Opens the Update Lesson MVC package and returns to Lesson
     * Details when the contributor cancels the update. </p>
     * 
     * @param lessonId unique identifier of the selected lesson
     * 
     */
    private static void openUpdateLesson(int lessonId) {
        ControllerUpdateLesson.displayUpdateLesson(
                ViewContributorHome.theStage,
                ViewContributorHome.theUser,
                lessonId,
                ControllerContributorHome::handleReadAllLessons,
                () -> reopenLessonDetails(lessonId));
    }

    /**********
     * <p> Method: reopenLessonDetails() </p>
     * 
     * <p> Description: Reloads and displays a lesson after the Update form closes. </p>
     * 
     * @param lessonId unique identifier of the lesson to reload
     * 
     */
    private static void reopenLessonDetails(int lessonId) {
        String author = ViewContributorHome.theUser == null
                ? "" : ViewContributorHome.theUser.getUserName();
        LessonsLearned lesson =
                ModelContributorHome.getContributorLessonById(lessonId, author);
        if (lesson != null) handleOpenLessonDetails(lesson);
    }

    /**********
     * <p> Method: handleOpenExperienceWindow() </p>
     * 
     * <p> Description: Delegates the Add Experience action to the Experience MVC
     * package and supplies a selected Lesson ID when available. </p>
     * 
     */
    protected static void handleOpenExperienceWindow() {
        Integer lessonId = null;
        String idText = ViewContributorHome.textfield_LessonId.getText().trim();
        try {
            if (!idText.isEmpty()) lessonId = Integer.valueOf(idText);
        } catch (NumberFormatException exception) {
            lessonId = null;
        }
        ControllerExperience.displayAddExperience(
                ViewContributorHome.theStage,
                ViewContributorHome.theUser,
                lessonId,
                ControllerContributorHome::handleReadAllLessons);
    }
}
