package guiContributor;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import database.Database;
import entityClasses.Experience;
import entityClasses.ExperiencesList;
import entityClasses.LessonsLearned;
import entityClasses.LessonsLearnedList;

/*******
 * <p> Title: ModelContributorHome Class. </p>
 * 
 * <p> Description: The Contributor Home Page Model. This class provides the data-access
 * and lesson and experience processing operations required by the Contributor Home
 * controller and view. It separates database communication, contributor ownership
 * checks, keyword searching, filtering, and sorting from the JavaFX user interface. </p>
 * 
 * <p> Copyright: Lynn Robert Carter © 2025 </p>
 * 
 * @author Lynn Robert Carter
 * 
 * @version 1.00		2025-08-15 Initial version
 * @version 1.01		2025-09-13 Updated JavaDoc description
 * @version 1.02		2026-10-23 Added Contributor lesson data operations
 *  
 */

public class ModelContributorHome {

	// Provides access to the shared application database
	private static Database theDatabase = applicationMain.FoundationsMain.database;

	/******
	 * 
	 * <p> Constructor: ModelContributorHome() </p>
	 * 
	 * <p> Description: Creates the Contributor Home model. The model operations are static,
	 * so the application does not need to instantiate this class. </p>
	 * 
	 */
	public ModelContributorHome() {
		// Default constructor
	}

	/******
	 * 
	 * <p> Method: loadUserAccountDetails() </p>
	 * 
	 * <p> Description: Loads the stored account details for the active contributor. </p>
	 * 
	 * @param username username identifying the contributor account
	 * 
	 */
	protected static void loadUserAccountDetails(String username) {
		theDatabase.getUserAccountDetails(username);
	}

	/******
	 * 
	 * <p> Method: getAllLessonsForContributor() </p>
	 * 
	 * <p> Description: Retrieves every lesson owned by the specified contributor. </p>
	 * 
	 * @param author username identifying the lesson owner
	 * @return collection containing the contributor's lessons
	 * 
	 */
	protected static LessonsLearnedList getAllLessonsForContributor(String author) {
		return theDatabase.getAllLessonsForContributor(author);
	}

	/******
	 * 
	 * <p> Method: getLessonById() </p>
	 * 
	 * <p> Description: Retrieves the lesson associated with the specified database ID. </p>
	 * 
	 * @param lessonId unique identifier of the requested lesson
	 * @return matching lesson, or null when the lesson does not exist
	 * 
	 */
	protected static LessonsLearned getLessonById(int lessonId) {
		return theDatabase.getLessonById(lessonId);
	}

	/******
	 * 
	 * <p> Method: getContributorLessonById() </p>
	 * 
	 * <p> Description: Retrieves a lesson only when it exists and belongs to the
	 * specified contributor. This prevents one contributor from accessing another
	 * contributor's lesson through its numeric identifier. </p>
	 * 
	 * @param lessonId unique identifier of the requested lesson
	 * @param author username identifying the active contributor
	 * @return matching contributor-owned lesson, or null when access is unavailable
	 * 
	 */
	protected static LessonsLearned getContributorLessonById(int lessonId, String author) {
		LessonsLearned lesson = theDatabase.getLessonById(lessonId);

		if (lesson == null || author == null || !author.equals(lesson.getAuthor())) {
			return null;
		}

		return lesson;
	}

	/******
	 * 
	 * <p> Method: getLessonsByFilter() </p>
	 * 
	 * <p> Description: Retrieves lessons matching the entered category or database
	 * filter while limiting results to the specified contributor. </p>
	 * 
	 * @param filter category or filter text entered by the contributor
	 * @param author username identifying the active contributor
	 * @return collection containing matching lessons
	 * 
	 */
	protected static LessonsLearnedList getLessonsByFilter(String filter, String author) {
		return theDatabase.getLessonsByFilter(filter, author);
	}

	/******
	 * 
	 * <p> Method: searchLessons() </p>
	 * 
	 * <p> Description: Performs a case-insensitive partial keyword search across the
	 * contributor's lesson titles, problem descriptions, solutions, and experience
	 * information. </p>
	 * 
	 * @param query keyword entered by the contributor
	 * @param author username identifying the active contributor
	 * @return collection containing every matching lesson
	 * 
	 */

    protected static LessonsLearnedList searchLessons(
            String query,
            String author) {

        // Retrieves every lesson owned by the contributor
        LessonsLearnedList storedLessons = getAllLessonsForContributor(author);

        // Creates the collection for matching lessons
        LessonsLearnedList matches = new LessonsLearnedList();

        // Normalizes the entered keyword for comparison
        String normalizedQuery = query == null ? "" : query.trim().toLowerCase();

        // Checks every lesson owned by the contributor
        for (LessonsLearned lesson : storedLessons.getLessons()) {

            // Checks the searchable Lesson Details
            boolean lessonMatches =
                    containsKeyword(
                            lesson.getTitle(),
                            normalizedQuery)
                    || containsKeyword(
                            lesson.getProblemDescription(),
                            normalizedQuery)
                    || containsKeyword(
                            lesson.getSolution(),
                            normalizedQuery);

            // Retrieves the Experiences associated with the lesson
            ExperiencesList experiences = getAllExperiencesForLesson(lesson.getLessonId());

            // Checks the associated Experiences when the Lesson Details do not match
            if (!lessonMatches
                    && experiences != null
                    && !experiences.isEmpty()) {

                // Checks every Experience for the entered keyword
                for (Experience experience : experiences.getExperiences()) {

                    // Checks the searchable Experience information
                    boolean experienceMatches =
                            containsKeyword(
                                    experience.getWhat(),
                                    normalizedQuery)
                            || containsKeyword(
                                    experience.getHow(),
                                    normalizedQuery)
                            || containsKeyword(
                                    experience.getEffort(),
                                    normalizedQuery);

                    // Records that the lesson has a matching Experience
                    if (experienceMatches) {
                        lessonMatches = true;
                        break;
                    }
                }
            }

            // Adds each matching lesson only once
            if (lessonMatches) {
                matches.addLesson(lesson);
            }
        }

        // Returns all matching lessons
        return matches;
    }

	/******
	 * 
	 * <p> Method: getContributorCategories() </p>
	 * 
	 * <p> Description: Collects the contributor's unique nonempty lesson categories
	 * in case-insensitive alphabetical order. </p>
	 * 
	 * @param author username identifying the active contributor
	 * @return sorted set containing the available categories
	 * 
	 */
	protected static Set<String> getContributorCategories(String author) {
		LessonsLearnedList lessons = getAllLessonsForContributor(author);
		Set<String> categories = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);

		for (LessonsLearned lesson : lessons.getLessons()) {
			if (lesson.getCategory() != null && !lesson.getCategory().isBlank()) {
				categories.add(lesson.getCategory());
			}
		}

		return categories;
	}

	/******
	 * 
	 * <p> Method: filterAndSortLessons() </p>
	 * 
	 * <p> Description: Applies the selected category filter and ordering rule to the
	 * active contributor's lessons without exposing a mutable database collection to
	 * the View. </p>
	 * 
	 * @param author username identifying the active contributor
	 * @param category selected category or All Categories
	 * @param order selected lesson ordering rule
	 * @return filtered and sorted lesson collection
	 * 
	 */
	protected static LessonsLearnedList filterAndSortLessons(
			String author,
			String category,
			String order) {

		LessonsLearnedList storedLessons = getAllLessonsForContributor(author);
		List<LessonsLearned> lessons = new ArrayList<>(storedLessons.getLessons());

		if (category != null && !category.equals("All Categories")) {
			lessons.removeIf((lesson) -> !category.equalsIgnoreCase(lesson.getCategory()));
		}

		Comparator<LessonsLearned> comparator;

		if ("Oldest first".equals(order)) {
			comparator = Comparator.comparingInt(LessonsLearned::getLessonId);
		} else if ("Title A–Z".equals(order)) {
			comparator = Comparator.comparing(
					LessonsLearned::getTitle,
					String.CASE_INSENSITIVE_ORDER);
		} else if ("Title Z–A".equals(order)) {
			comparator = Comparator.comparing(
					LessonsLearned::getTitle,
					String.CASE_INSENSITIVE_ORDER).reversed();
		} else if ("Category A–Z".equals(order)) {
			comparator = Comparator.comparing(
					LessonsLearned::getCategory,
					String.CASE_INSENSITIVE_ORDER).thenComparing(
					LessonsLearned::getTitle,
					String.CASE_INSENSITIVE_ORDER);
		} else {
			comparator = Comparator.comparingInt(
					LessonsLearned::getLessonId).reversed();
		}

		lessons.sort(comparator);
		return new LessonsLearnedList(lessons);
	}

    /******
     * 
     * <p> Method: getAllExperiencesForLesson() </p>
     * 
     * <p> Description: Retrieves every experience associated with the specified lesson.
     * An empty collection is returned when the lesson does not have any experiences. </p>
     * 
     * @param lessonId unique identifier of the associated lesson
     * @return collection containing the lesson's experiences
     * 
     */
    protected static ExperiencesList getAllExperiencesForLesson(int lessonId) {

        // Delegates Experience retrieval to the shared database
        return theDatabase.getAllExperiencesForLesson(lessonId);
    }

	/******
	 * 
	 * <p> Method: containsKeyword() </p>
	 * 
	 * <p> Description: Performs a null-safe and case-insensitive partial keyword
	 * comparison for one searchable lesson value. </p>
	 * 
	 * @param value lesson text being searched
	 * @param query normalized keyword entered by the contributor
	 * @return true when the available lesson text contains the keyword
	 * 
	 */
	private static boolean containsKeyword(String value, String query) {
		return value != null && value.toLowerCase().contains(query);
	}
}
