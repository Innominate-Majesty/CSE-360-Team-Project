package lessonValidator;

/******
 * 
 * <p> Title: LessonValidationResult Class. </p>
 *
 * <p> Description: This LessonValidationResult class represents the result
 * produced when lesson information is validated. </p>
 *
 * @author Venus Ho
 *
 */

public class LessonValidationResult {

	// stores whether validation succeeded
	private final boolean valid;

	// stores the invalid field name
	private final String fieldName;

	// stores the validation message
	private final String message;

	/******
	 * 
	 * <p> Constructor: LessonValidationResult(boolean valid, String fieldName, String message) </p>
	 *
	 * <p> Description: Constructs a LessonValidationResult object. </p>
	 *
	 * @param valid specifies whether validation succeeded
	 * 
	 * @param fieldName specifies the invalid field name
	 * 
	 * @param message specifies the validation message
	 *
	 */

	public LessonValidationResult(boolean valid, String fieldName, String message) {

		// stores the validation status
		this.valid = valid;

		// stores the invalid field name
		this.fieldName = fieldName;

		// stores the validation message
		this.message = message;
	}

	/******
	 * 
	 * <p> Method: isValid() </p>
	 *
	 * <p> Description: Returns whether validation succeeded. </p>
	 *
	 * @return true when validation succeeded; otherwise, false
	 *
	 */

	public boolean isValid() {

		// returns the validation status
		return valid;
	}

	/******
	 * 
	 * <p> Method: getFieldName() </p>
	 *
	 * <p> Description: Returns the invalid field name. </p>
	 *
	 * @return the invalid field name
	 *
	 */

	public String getFieldName() {

		// returns the invalid field name
		return fieldName;
	}

	/******
	 * 
	 * <p> Method: getMessage() </p>
	 *
	 * <p> Description: Returns the validation message. </p>
	 *
	 * @return the validation message
	 *
	 */

	public String getMessage() {

		// returns the validation message
		return message;
	}

}
