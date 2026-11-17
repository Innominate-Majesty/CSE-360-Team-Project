package aiServices;

/******
 *
 * <p> Title: AIResponse Class. </p>
 *
 * <p> Description: Stores either the generated text returned by the shared AI
 * service or a readable error message. This gives each TP3 AI feature the same
 * way to check whether a request succeeded before displaying or using its
 * result. </p>
 *
 * @author Venus Ho
 * @author Alexander Murray
 * @author Alan Shimp
 * @author Caleb Beaven
 * @author Krithik Duraisamy
 *
 * @version 1.0 2026-11-14 Initial shared AI response structure
 * @version 1.1 2026-11-16 Added successful and failed AI response results
 *
 */

public class AIResponse {

	// Records whether the AI request completed successfully
	private final boolean successful;

	// Stores the generated text when the request succeeds
	private final String generatedText;

	// Stores a readable explanation when the request fails
	private final String errorMessage;

	/******
	 *
	 * <p> Constructor: AIResponse() </p>
	 *
	 * <p> Description: Stores one completed AI result. Factory methods are used
	 * to prevent a response from containing success text and an error at the same
	 * time. </p>
	 *
	 * @param successful specifies whether the AI request succeeded
	 * @param generatedText specifies the generated text for a successful request
	 * @param errorMessage specifies the explanation for a failed request
	 *
	 */

	private AIResponse(boolean successful, String generatedText, String errorMessage) {
		this.successful = successful;
		this.generatedText = generatedText;
		this.errorMessage = errorMessage;
	}

	/******
	 *
	 * <p> Method: success() </p>
	 *
	 * <p> Description: Creates a successful response containing generated AI
	 * text. </p>
	 *
	 * @param generatedText specifies the text returned by the AI service
	 * @return a successful AI response
	 * @throws IllegalArgumentException when the generated text is missing
	 *
	 */

	public static AIResponse success(String generatedText) {
		String cleanedText = cleanValue(generatedText);

		if (cleanedText.isEmpty()) {
			throw new IllegalArgumentException("Generated AI text is missing");
		}

		return new AIResponse(true, cleanedText, "");
	}

	/******
	 *
	 * <p> Method: failure() </p>
	 *
	 * <p> Description: Creates a failed response containing a readable error
	 * message for the calling feature. </p>
	 *
	 * @param errorMessage specifies why the AI request could not be completed
	 * @return a failed AI response
	 * @throws IllegalArgumentException when the error message is missing
	 *
	 */

	public static AIResponse failure(String errorMessage) {
		String cleanedMessage = cleanValue(errorMessage);

		if (cleanedMessage.isEmpty()) {
			throw new IllegalArgumentException("AI error message is missing");
		}

		return new AIResponse(false, "", cleanedMessage);
	}

	/******
	 *
	 * <p> Method: isSuccessful() </p>
	 *
	 * <p> Description: Checks whether the AI service returned generated text
	 * without an error. </p>
	 *
	 * @return true when the request succeeded; otherwise, false
	 *
	 */

	public boolean isSuccessful() {
		return successful;
	}

	/******
	 *
	 * <p> Method: getGeneratedText() </p>
	 *
	 * <p> Description: Returns the generated text from a successful request. A
	 * failed response returns an empty string. </p>
	 *
	 * @return the generated AI text or an empty string
	 *
	 */

	public String getGeneratedText() {
		return generatedText;
	}

	/******
	 *
	 * <p> Method: getErrorMessage() </p>
	 *
	 * <p> Description: Returns the readable error from a failed request. A
	 * successful response returns an empty string. </p>
	 *
	 * @return the AI error message or an empty string
	 *
	 */

	public String getErrorMessage() {
		return errorMessage;
	}

	/******
	 *
	 * <p> Method: cleanValue() </p>
	 *
	 * <p> Description: Removes surrounding spaces from response text and converts
	 * a missing value into an empty string. </p>
	 *
	 * @param value specifies the response value being cleaned
	 * @return the cleaned value or an empty string
	 *
	 */
    
	private static String cleanValue(String value) {
		return value == null ? "" : value.trim();
	}

}
