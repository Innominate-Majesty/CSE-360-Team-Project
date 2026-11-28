package aiServices;

import java.io.IOException;

import entityClasses.LessonsLearned;
import entityClasses.ExperiencesList;

/******
*
* <p> Title: ExperienceInsightService Class. </p>
*
* <p> Description: Uses the shared AI service to provide a brief analysis of the Experiences
* associated with a lesson. </p>
*
* @author Venus Ho
* @author Alexander Murray
* @author Alan Shimp
* @author Caleb Beaven
* @author Krithik Duraisamy
*
* @version 1.0 2026-11-28 Initial AI experience analysis service
*
*/

public class ExperienceInsightService {
	
	/******
	 *
	 * <p> Constructor: ExperienceInsightService() </p>
	 *
	 * <p> Description: This class only provides static methods and is not
	 * instantiated. </p>
	 *
	 */

	private ExperienceInsightService() {
	}
	
	/******
	 * 
	 * <p> Method: analyze() </p>
	 * 
	 * <p> Description: Returns a String of the AI service's insights on the experience data. 
	 * It never throws; any failure results in a generic message.</p>
	 * 
	 */
	
	public static String analyze(LessonsLearned lesson, ExperiencesList experiences) {
		if (experiences.size() <= 1) {
			return "Not enough data to provide insights.";
		}
		
		try {
			AIConfig config = AIConfig.load();
			
			// Skips the network request when no API key is available
			if (config.isConfigured()) {
				AIResponse response = requestAnalysis(config, buildRequest(lesson, experiences));
				if (response.isSuccessful()) {
					return response.getGeneratedText();
				}
			}
		} catch (IOException | IllegalArgumentException exception) {
			//Fall through to default error.
		}
		
		return "Error: Unable to submit prompt to AI service";
	}
	
	/******
	 *
	 * <p> Method: requestAnalysis() </p>
	 *
	 * <p> Description: Sends one request through the shared AI service and
	 * converts every outcome into an AIResponse. </p>
	 *
	 * @param config specifies the loaded AI configuration
	 * @param request specifies the prompts to send
	 * @return a successful response containing text, or a failed response
	 *
	 */

	private static AIResponse requestAnalysis(AIConfig config, AIRequest request) {
		try {
			String text = AIService.sendRequest(
					config.getApiKey(),
					config.getServiceUrl(),
					config.getModel(),
					request.getSystemPrompt(),
					request.getUserPrompt());
			return AIResponse.success(text);
		} catch (IOException | IllegalArgumentException exception) {
			return AIResponse.failure("Unable to Provide Analysis");
		} catch (InterruptedException exception) {
			Thread.currentThread().interrupt();
			return AIResponse.failure("Analysis was interrupted");
		}
	}
	
	/******
	 *
	 * <p> Method: buildRequest() </p>
	 *
	 * <p> Description: Builds the prompt for the analysis request. </p>
	 *
	 * @param lesson specifies the lesson to analyze
	 * @param lessonSummaries specifies "Title (Category)" summaries of existing lessons
	 * @return the completed AI request
	 *
	 */

	private static AIRequest buildRequest(LessonsLearned lesson, ExperiencesList experiences) {
		String systemPrompt = "You analyze data on students' experiences applying a lesson "
				+ "learned in a team project. Each experience includes information on how long "
				+ "the experience took in hours and narrative descriptions of what was done, how "
				+ "it was done, and how much effort it took the team. For each field, provide a "
				+ "paragraph on whether there are any trends or commonalities among the "
				+ "experiences.";

		StringBuilder userPrompt = new StringBuilder();
		userPrompt.append("Lesson title: " + lesson.getTitle() + "\n Lesson category: "
				+ lesson.getCategory() + "\n Problem to be solved in the lesson: "
				+ lesson.getProblemDescription() + "\n Solution: " + lesson.getSolution() + "\n");
		
		for (int i = 0; i < experiences.size(); i++) {
			String displayNo = String.valueOf(i + 1);
			userPrompt.append("Experience #"
					+ displayNo
					+ " Time: "
					+ String.valueOf(experiences.get(i).getTime())
					+ "\n Experience #"
					+ displayNo
					+ " What was done: "
					+ experiences.get(i).getWhat()
					+ "\n Experience #"
					+ displayNo
					+ " How it was done: "
					+ experiences.get(i).getHow()
					+ "\n Experience #"
					+ displayNo
					+ " How much effort it took from the team: "
					+ experiences.get(i).getEffort()
					+ "\n");
		}

		return new AIRequest(systemPrompt, userPrompt.toString());
	}
	
}