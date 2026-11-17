package aiServices;

/******
 *
 * <p> Title: AIRequest Class. </p>
 *
 * <p> Description: Stores the instructions and user content that a TP3 feature
 * sends to the shared AI service. The request does not contain the API key or
 * any JavaFX controls, which keeps shared AI requests separate from the user
 * interface and configuration. </p>
 *
 * @author Venus Ho
 * @author Alexander Murray
 * @author Alan Shimp
 * @author Caleb Beaven
 * @author Krithik Duraisamy
 *
 * @version 1.0 2026-11-14 Initial shared AI request structure
 * @version 1.1 2026-11-16 Added the shared AI request fields and validation
 *
 */

public class AIRequest {

	// Stores the instructions that define how the AI should respond
	private final String systemPrompt;

	// Stores the lesson information or question submitted to the AI
	private final String userPrompt;

	/******
	 *
	 * <p> Constructor: AIRequest() </p>
	 *
	 * <p> Description: Creates one AI request after confirming that its system
	 * instructions and user content are present. </p>
	 *
	 * @param systemPrompt specifies the instructions that guide the AI response
	 * @param userPrompt specifies the lesson information or question sent to the AI
	 * @throws IllegalArgumentException when either required prompt is missing
	 *
	 */

	public AIRequest(String systemPrompt, String userPrompt) {
		this.systemPrompt = requirePrompt(systemPrompt, "System prompt");
		this.userPrompt = requirePrompt(userPrompt, "User prompt");
	}

	/******
	 *
	 * <p> Method: getSystemPrompt() </p>
	 *
	 * <p> Description: Returns the instructions that define the expected purpose,
	 * behavior, and format of the AI response. </p>
	 *
	 * @return the system instructions for the AI request
	 *
	 */

	public String getSystemPrompt() {
		return systemPrompt;
	}

	/******
	 *
	 * <p> Method: getUserPrompt() </p>
	 *
	 * <p> Description: Returns the lesson information or question supplied by the
	 * TP3 feature that created the request. </p>
	 *
	 * @return the user content for the AI request
	 *
	 */

	public String getUserPrompt() {
		return userPrompt;
	}

	/******
	 *
	 * <p> Method: requirePrompt() </p>
	 *
	 * <p> Description: Cleans and checks a required prompt before it is stored in
	 * the request. </p>
	 *
	 * @param prompt specifies the prompt being checked
	 * @param promptName specifies the readable name used by an error message
	 * @return the prompt with surrounding spaces removed
	 * @throws IllegalArgumentException when the prompt is missing or empty
	 *
	 */
    
	private static String requirePrompt(String prompt, String promptName) {
		if (prompt == null || prompt.isBlank()) {
			throw new IllegalArgumentException(promptName + " is missing");
		}

		return prompt.trim();
	}

}
