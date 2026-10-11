package lessonValidator;

import entityClasses.LessonsLearned;

/******
 * 
 * <p> Title: LessonValidator Class. </p>
 *
 * <p> Description: This LessonValidator class validates the seven required lesson fields. </p>
 *
 * @author Venus Ho
 *
 */

public class LessonValidator {

	/******
     * 
	 * <p> Constructor: LessonValidator() </p>
	 *
	 * <p> Description: Constructs a LessonValidator object. </p>
	 *
	 */

	public LessonValidator() {

	}

	/******
     * 
	 * <p> Method: isMissing(String value) </p>
	 *
	 * <p> Description: Determines whether a required value is missing. </p>
	 *
	 * @param value specifies the value to check
	 *
	 * @return true when the value is missing; otherwise, false
	 *
	 */

	private boolean isMissing(String value) {

		// checks for null, empty, or whitespace
		return value == null || value.isBlank();
	}

    /******
     * 
     * <p> Method: validate(LessonsLearned lesson) </p>
     *
     * <p> Description: Validates the seven required lesson fields. </p>
     *
     * @param lesson specifies the lesson to validate
     *
     * @return the lesson validation result
     *
     */

    public LessonValidationResult validate(LessonsLearned lesson) {

        // checks whether the lesson exists
        if (lesson == null) {

            // returns the failed validation result
            return new LessonValidationResult(false, "Lesson", "Lesson is missing or invalid.");

        }

        // checks the title
        if (isMissing(lesson.getTitle())) {

            // returns the failed validation result
            return new LessonValidationResult(false, "Title", "Title is missing or invalid.");
        }

        // checks the context
        if (isMissing(lesson.getContext())) {

            // returns the failed validation result
            return new LessonValidationResult(false, "Context", "Context is missing or invalid.");
        }

        // checks the expected outcome
        if (isMissing(lesson.getExpectedOutcome())) {

            // returns the failed validation result
            return new LessonValidationResult(false, "Expected Outcome", "Expected Outcome is missing or invalid.");
        }

        // checks what happened
        if (isMissing(lesson.getWhatHappened())) {

            // returns the failed validation result
            return new LessonValidationResult(false, "What Happened", "What Happened is missing or invalid.");
        }

        // checks the root cause
        if (isMissing(lesson.getRootCause())) {

            // returns the failed validation result
            return new LessonValidationResult(false, "Root Cause", "Root Cause is missing or invalid.");
        }

        // checks the impact
        if (isMissing(lesson.getImpact())) {

            // returns the failed validation result
            return new LessonValidationResult(false, "Impact", "Impact is missing or invalid.");
        }

        // checks the recommendation
        if (isMissing(lesson.getRecommendation())) {

            // returns the failed validation result
            return new LessonValidationResult(false, "Recommendation", "Recommendation is missing or invalid.");
        }

        // returns the successful validation result
        return new LessonValidationResult(true, "", "Lesson information is valid.");
    }

}
