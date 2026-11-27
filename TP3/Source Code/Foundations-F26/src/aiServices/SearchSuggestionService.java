package aiServices;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/******
 *
 * <p> Title: SearchSuggestionService Class. </p>
 *
 * <p> Description: Uses the shared AI service to recommend three search terms.
 * Terms relate to the user's search when one exists; otherwise generic terms are
 * requested. Generic built-in terms also fill in when the AI is unavailable or
 * returns fewer than three usable terms. </p>
 *
 * @author Venus Ho
 * @author Alexander Murray
 * @author Alan Shimp
 * @author Caleb Beaven
 * @author Krithik Duraisamy
 *
 * @version 1.0 2026-11-27 Initial AI search suggestion service
 *
 */

public class SearchSuggestionService {

	// Defines the generic terms used when the AI cannot supply enough suggestions
	private static final List<String> GENERIC_TERMS = List.of(
			"teamwork", "testing", "planning", "communication", "debugging", "deadlines");

	// Limits how many lesson summaries are shared with the AI service
	private static final int MAX_LESSONS = 30;

	// Limits the length of one suggested term
	private static final int MAX_TERM_LENGTH = 40;

	// Defines the number of suggestions displayed
	private static final int TERM_COUNT = 3;

	/******
	 *
	 * <p> Constructor: SearchSuggestionService() </p>
	 *
	 * <p> Description: This class only provides static methods and is not
	 * instantiated. </p>
	 *
	 */

	private SearchSuggestionService() {
	}

	/******
	 *
	 * <p> Method: suggest() </p>
	 *
	 * <p> Description: Returns exactly three recommended search terms. This method
	 * blocks while waiting for the AI, so it must not run on the JavaFX thread. It
	 * never throws; any failure results in generic terms. </p>
	 *
	 * @param query specifies the user's current search, or an empty string when none exists
	 * @param lessonSummaries specifies "Title (Category)" summaries of existing lessons
	 * @return three search terms
	 *
	 */

	public static List<String> suggest(String query, List<String> lessonSummaries) {
		String cleanQuery = query == null ? "" : query.trim();
		List<String> terms = new ArrayList<>();

		try {
			AIConfig config = AIConfig.load();

			// Skips the network request when no API key is available
			if (config.isConfigured()) {
				AIResponse response = requestTerms(config, buildRequest(cleanQuery, lessonSummaries));
				if (response.isSuccessful()) {
					terms = parseTerms(response.getGeneratedText(), cleanQuery);
				}
			}
		} catch (IOException | IllegalArgumentException exception) {
			// Falls through to the generic terms below
		}

		return fillWithGenericTerms(terms, cleanQuery);
	}

	/******
	 *
	 * <p> Method: requestTerms() </p>
	 *
	 * <p> Description: Sends one request through the shared AI service and
	 * converts every outcome into an AIResponse. </p>
	 *
	 * @param config specifies the loaded AI configuration
	 * @param request specifies the prompts to send
	 * @return a successful response containing text, or a failed response
	 *
	 */

	private static AIResponse requestTerms(AIConfig config, AIRequest request) {
		try {
			String text = AIService.sendRequest(
					config.getApiKey(),
					config.getServiceUrl(),
					config.getModel(),
					request.getSystemPrompt(),
					request.getUserPrompt());
			return AIResponse.success(text);
		} catch (IOException | IllegalArgumentException exception) {
			return AIResponse.failure("Search suggestions are unavailable");
		} catch (InterruptedException exception) {
			Thread.currentThread().interrupt();
			return AIResponse.failure("Search suggestions were interrupted");
		}
	}

	/******
	 *
	 * <p> Method: buildRequest() </p>
	 *
	 * <p> Description: Builds the prompts for either a related-terms request, when
	 * the user has searched, or a generic-terms request, when no search exists. </p>
	 *
	 * @param query specifies the user's current search, or an empty string
	 * @param lessonSummaries specifies "Title (Category)" summaries of existing lessons
	 * @return the completed AI request
	 *
	 */

	private static AIRequest buildRequest(String query, List<String> lessonSummaries) {
		String systemPrompt = "You help users search a library of lessons learned from team "
				+ "projects. Reply with exactly three short search terms (one to three words "
				+ "each), one per line, with no numbering, bullets, quotes, or extra text.";

		StringBuilder userPrompt = new StringBuilder();
		if (query.isEmpty()) {
			userPrompt.append("The user has not searched yet. Suggest three generic search "
					+ "terms that are useful for browsing the lessons.");
		} else {
			userPrompt.append("The user searched for: \"").append(query)
					.append("\". Suggest three search terms related to this search.");
		}

		if (lessonSummaries != null && !lessonSummaries.isEmpty()) {
			userPrompt.append("\nPrefer terms that appear in or relate to these existing lessons "
					+ "(title and category):");
			for (int i = 0; i < Math.min(MAX_LESSONS, lessonSummaries.size()); i++) {
				userPrompt.append("\n- ").append(lessonSummaries.get(i));
			}
		}

		return new AIRequest(systemPrompt, userPrompt.toString());
	}

	/******
	 *
	 * <p> Method: parseTerms() </p>
	 *
	 * <p> Description: Cleans the AI text into distinct terms, removing bullets,
	 * numbering, quotation marks, and the user's own search. </p>
	 *
	 * @param text specifies the text generated by the AI
	 * @param query specifies the user's current search, which is never suggested back
	 * @return up to three cleaned terms
	 *
	 */

	private static List<String> parseTerms(String text, String query) {
		List<String> terms = new ArrayList<>();

		for (String line : text.split("[\\r\\n,]+")) {
			String term = line.trim()
					.replaceFirst("^\\s*(?:[-*•]|\\d+[.)])\\s*", "")
					.replaceAll("^[\"'“”]+|[\"'“”]+$", "")
					.trim();

			if (!term.isEmpty() && term.length() <= MAX_TERM_LENGTH
					&& !term.equalsIgnoreCase(query) && !containsIgnoreCase(terms, term)) {
				terms.add(term);
			}

			if (terms.size() == TERM_COUNT) {
				break;
			}
		}

		return terms;
	}

	/******
	 *
	 * <p> Method: fillWithGenericTerms() </p>
	 *
	 * <p> Description: Pads the list to three terms using the generic
	 * recommendations, skipping duplicates and the user's own search. </p>
	 *
	 * @param terms specifies the terms already available
	 * @param query specifies the user's current search
	 * @return a list containing three terms
	 *
	 */

	private static List<String> fillWithGenericTerms(List<String> terms, String query) {
		List<String> result = new ArrayList<>(terms);

		for (String generic : GENERIC_TERMS) {
			if (result.size() >= TERM_COUNT) {
				break;
			}
			if (!generic.equalsIgnoreCase(query) && !containsIgnoreCase(result, generic)) {
				result.add(generic);
			}
		}

		return result;
	}

	/******
	 *
	 * <p> Method: containsIgnoreCase() </p>
	 *
	 * <p> Description: Checks whether a list already contains a term, ignoring
	 * capitalization. </p>
	 *
	 * @param terms specifies the list being searched
	 * @param term specifies the term being looked for
	 * @return true when the term is already in the list; otherwise, false
	 *
	 */

	private static boolean containsIgnoreCase(List<String> terms, String term) {
		for (String existing : terms) {
			if (existing.equalsIgnoreCase(term)) {
				return true;
			}
		}
		return false;
	}

}
