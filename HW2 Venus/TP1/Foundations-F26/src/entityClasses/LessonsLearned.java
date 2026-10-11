package entityClasses;

import java.time.LocalDateTime;


/**
 * 
 * <p> Title: LessonsLearned Class. </p>
 * 
 * <p> Description: This LessonsLearned class represents a lesson learned in the system. 
 * It contains the lesson content, contributor username, unique lesson ID, creation date, and most recent update date.</p>
 * 
 * <p> Copyright: ASU Team 15 (Fall 2026)</p>
 * 
 * @author Alexander Murray
 * @author Venus Ho
 * @author Alan Shimp
 * @author Caleb Beaven
 * @author Krithik Duraisamy
 * 
 */

public class LessonsLearned {

	// stores the lesson ID
	private int lessonId;

	// stores the username of the contributor
	private String contributorId;

	// stores the title of the lesson
	private String title;

	// stores the context of the lesson
	private String context;

	// stores the expected outcome
	private String expectedOutcome;

	// stores what actually happened
	private String whatHappened;

	// stores the root cause
	private String rootCause;

	// stores the impact of the event
	private String impact;

	// stores what should be repeated or changed in the future
	private String recommendation;

	// stores when the lesson was first saved
	private LocalDateTime dateCreated;

	// stores when the lesson was most recently updated
	private LocalDateTime lastUpdated;

	/******
	 * 
	 * <p> Constructor: LessonsLearned(int lessonId, String contributorId, String title,
	 * String context, String expectedOutcome, String whatHappened, String rootCause,
	 * String impact, String recommendation, LocalDateTime dateCreated,
	 * LocalDateTime lastUpdated) </p>
	 *
	 * <p> Description: Constructs a populated LessonsLearned object. </p>
	 *
	 * @param lessonId specifies the lesson ID
	 * 
	 * @param contributorId specifies the contributor username
	 * 
	 * @param title specifies the lesson title
	 * 
	 * @param context specifies the lesson context
	 * 
	 * @param expectedOutcome specifies the expected outcome
	 * 
	 * @param whatHappened specifies what actually happened
	 * 
	 * @param rootCause specifies the root cause
	 * 
	 * @param impact specifies the lesson impact
	 * 
	 * @param recommendation specifies the lesson recommendation
	 * 
	 * @param dateCreated specifies when the lesson was created
	 * 
	 * @param lastUpdated specifies when the lesson was last updated
	 *
	 */

	public LessonsLearned(int lessonId, String contributorId, String title,
			String context, String expectedOutcome, String whatHappened,
			String rootCause, String impact, String recommendation,
			LocalDateTime dateCreated, LocalDateTime lastUpdated) {

		// stores the lesson ID
		this.lessonId = lessonId;

		// stores the contributor username
		this.contributorId = contributorId;

		// stores the lesson title
		this.title = title;

		// stores the lesson context
		this.context = context;

		// stores the expected outcome
		this.expectedOutcome = expectedOutcome;

		// stores what actually happened
		this.whatHappened = whatHappened;

		// stores the root cause
		this.rootCause = rootCause;

		// stores the lesson impact
		this.impact = impact;

		// stores the lesson recommendation
		this.recommendation = recommendation;

		// stores the creation date
		this.dateCreated = dateCreated;

		// stores the last update date
		this.lastUpdated = lastUpdated;
	}

	/******
	 * 
	 * <p> Method: getLessonId() </p>
	 *
	 * <p> Description: Returns the lesson ID. </p>
	 *
	 * @return the lesson ID
	 *
	 */

	public int getLessonId() {

		// returns the lesson ID
		return lessonId;
	}

	/******
	 * 
	 * <p> Method: getContributorId() </p>
	 *
	 * <p> Description: Returns the contributor username. </p>
	 *
	 * @return the contributor username
	 *
	 */

	public String getContributorId() {

		// returns the contributor username
		return contributorId;
	}

	/******
	 * 
	 * <p> Method: getDateCreated() </p>
	 *
	 * <p> Description: Returns the lesson creation date. </p>
	 *
	 * @return the lesson creation date
	 *
	 */

	public LocalDateTime getDateCreated() {

		// returns the creation date
		return dateCreated;
	}

	/******
	 * 
	 * <p> Method: getLastUpdated() </p>
	 *
	 * <p> Description: Returns the lesson's last update date. </p>
	 *
	 * @return the lesson's last update date
	 *
	 */

	public LocalDateTime getLastUpdated() {

		// returns the last update date
		return lastUpdated;
	}

	/******
	 * 
	 * <p> Method: getTitle() </p>
	 *
	 * <p> Description: Returns the lesson title. </p>
	 *
	 * @return the lesson title
	 *
	 */

	public String getTitle() {

		// returns the lesson title
		return title;
	}

	/******
	 * 
	 * <p> Method: getContext() </p>
	 *
	 * <p> Description: Returns the lesson context. </p>
	 *
	 * @return the lesson context
	 *
	 */

	public String getContext() {

		// returns the lesson context
		return context;
	}

	/******
	 * 
	 * <p> Method: getExpectedOutcome() </p>
	 *
	 * <p> Description: Returns the expected outcome. </p>
	 *
	 * @return the expected outcome
	 *
	 */

	public String getExpectedOutcome() {

		// returns the expected outcome
		return expectedOutcome;
	}

	/******
	 * 
	 * <p> Method: getWhatHappened() </p>
	 *
	 * <p> Description: Returns what actually happened. </p>
	 *
	 * @return what actually happened
	 *
	 */

	public String getWhatHappened() {

		// returns what actually happened
		return whatHappened;
	}

	/******
	 * 
	 * <p> Method: getRootCause() </p>
	 *
	 * <p> Description: Returns the root cause. </p>
	 *
	 * @return the root cause
	 *
	 */

	public String getRootCause() {

		// returns the root cause
		return rootCause;
	}

	/******
	 * 
	 * <p> Method: getImpact() </p>
	 *
	 * <p> Description: Returns the lesson impact. </p>
	 *
	 * @return the lesson impact
	 *
	 */

	public String getImpact() {

		// returns the lesson impact
		return impact;
	}

	/******
	 * 
	 * <p> Method: getRecommendation() </p>
	 *
	 * <p> Description: Returns the lesson recommendation. </p>
	 *
	 * @return the lesson recommendation
	 *
	 */

	public String getRecommendation() {

		// returns the lesson recommendation
		return recommendation;
	}

	/******
	 * 
	 * <p> Method: setTitle(String title) </p>
	 *
	 * <p> Description: Sets the lesson title. </p>
	 *
	 * @param title specifies the lesson title
	 *
	 */

	public void setTitle(String title) {

		// stores the lesson title
		this.title = title;
	}

	/******
	 * 
	 * <p> Method: setContext(String context) </p>
	 *
	 * <p> Description: Sets the lesson context. </p>
	 *
	 * @param context specifies the lesson context
	 *
	 */

	public void setContext(String context) {

		// stores the lesson context
		this.context = context;
	}

	/******
	 * 
	 * <p> Method: setExpectedOutcome(String expectedOutcome) </p>
	 *
	 * <p> Description: Sets the expected outcome. </p>
	 *
	 * @param expectedOutcome specifies the expected outcome
	 *
	 */

	public void setExpectedOutcome(String expectedOutcome) {

		// stores the expected outcome
		this.expectedOutcome = expectedOutcome;
	}

	/******
	 * 
	 * <p> Method: setWhatHappened(String whatHappened) </p>
	 *
	 * <p> Description: Sets what actually happened. </p>
	 *
	 * @param whatHappened specifies what actually happened
	 *
	 */

	public void setWhatHappened(String whatHappened) {

		// stores what actually happened
		this.whatHappened = whatHappened;
	}

	/******
	 * 
	 * <p> Method: setRootCause(String rootCause) </p>
	 *
	 * <p> Description: Sets the root cause. </p>
	 *
	 * @param rootCause specifies the root cause
	 *
	 */

	public void setRootCause(String rootCause) {

		// stores the root cause
		this.rootCause = rootCause;
	}

	/******
	 * 
	 * <p> Method: setImpact(String impact) </p>
	 *
	 * <p> Description: Sets the lesson impact. </p>
	 *
	 * @param impact specifies the lesson impact
	 *
	 */

	public void setImpact(String impact) {

		// stores the lesson impact
		this.impact = impact;
	}

	/******
	 * 
	 * <p> Method: setRecommendation(String recommendation) </p>
	 *
	 * <p> Description: Sets the lesson recommendation. </p>
	 *
	 * @param recommendation specifies the lesson recommendation
	 *
	 */

	public void setRecommendation(String recommendation) {

		// stores the lesson recommendation
		this.recommendation = recommendation;
	}


}
