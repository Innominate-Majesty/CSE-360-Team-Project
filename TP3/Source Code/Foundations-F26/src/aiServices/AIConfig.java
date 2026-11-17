package aiServices;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

/******
 *
 * <p> Title: AIConfig Class. </p>
 *
 * <p> Description: Loads and stores the shared AI configuration needed by the
 * TP3 features that use AI. The API key is read from a local properties file
 * or environment variable instead of being written directly in the code. </p>
 *
 * @author Venus Ho
 * @author Alexander Murray
 * @author Alan Shimp
 * @author Caleb Beaven
 * @author Krithik Duraisamy
 *
 * @version 1.0 2026-11-14 Initial shared AI configuration structure
 * @version 1.1 2026-11-16 Added secure loading for the shared AI configuration
 *
 */

public class AIConfig {

	// Identifies the default local file containing the AI configuration
	private static final Path DEFAULT_CONFIG_PATH = Path.of("config.properties");

	// Identifies the property used to locate the Groq API key
	private static final String API_KEY_PROPERTY = "groq.api.key";

	// Identifies the optional property used to change the AI service URL
	private static final String SERVICE_URL_PROPERTY = "groq.api.url";

	// Identifies the optional property used to change the selected AI model
	private static final String MODEL_PROPERTY = "groq.model";

	// Identifies the optional environment variable containing the API key
	private static final String API_KEY_ENVIRONMENT_VARIABLE = "GROQ_API_KEY";

	// Defines the default OpenAI-compatible Groq service URL
	private static final String DEFAULT_SERVICE_URL =
			"https://api.groq.com/openai/v1/chat/completions";

	// Defines the default Groq model used by the shared AI service
	private static final String DEFAULT_MODEL = "openai/gpt-oss-120b";

	// Stores the API key loaded from the local configuration
	private final String apiKey;

	// Stores the OpenAI-compatible service URL
	private final String serviceUrl;

	// Stores the model selected for AI requests
	private final String model;

	/******
	 *
	 * <p> Constructor: AIConfig() </p>
	 *
	 * <p> Description: Stores the completed AI configuration after its values
	 * have been loaded and checked. </p>
	 *
	 * @param apiKey specifies the API key used to authorize AI requests
	 * @param serviceUrl specifies the OpenAI-compatible service URL
	 * @param model specifies the AI model used to generate responses
	 *
	 */

	private AIConfig(String apiKey, String serviceUrl, String model) {
		this.apiKey = apiKey;
		this.serviceUrl = serviceUrl;
		this.model = model;
	}

	/******
	 *
	 * <p> Method: load() </p>
	 *
	 * <p> Description: Loads the shared AI configuration from config.properties
	 * in the current project directory. </p>
	 *
	 * @return the loaded AI configuration
	 * @throws IOException when the configuration file exists but cannot be read
	 *
	 */

	public static AIConfig load() throws IOException {
		return load(DEFAULT_CONFIG_PATH);
	}

	/******
	 *
	 * <p> Method: load() </p>
	 *
	 * <p> Description: Loads the shared AI configuration from a specified
	 * properties file. This version also allows tests to use a separate temporary
	 * configuration file. </p>
	 *
	 * @param configPath specifies the properties file to read
	 * @return the loaded AI configuration
	 * @throws IOException when the configuration file exists but cannot be read
	 *
	 */

	public static AIConfig load(Path configPath) throws IOException {
		Properties properties = new Properties();

		// Reads the local properties only when the specified file exists
		if (configPath != null && Files.exists(configPath)) {
			try (InputStream input = Files.newInputStream(configPath)) {
				properties.load(input);
			}
		}

		// Uses the environment variable only when the properties file has no key
		String apiKey = readProperty(properties, API_KEY_PROPERTY, "");
		if (apiKey.isEmpty()) {
			apiKey = cleanValue(System.getenv(API_KEY_ENVIRONMENT_VARIABLE));
		}

		// Uses safe defaults when the URL and model are not changed locally
		String serviceUrl = readProperty(
				properties,
				SERVICE_URL_PROPERTY,
				DEFAULT_SERVICE_URL);
		String model = readProperty(properties, MODEL_PROPERTY, DEFAULT_MODEL);

		return new AIConfig(apiKey, serviceUrl, model);
	}

	/******
	 *
	 * <p> Method: isConfigured() </p>
	 *
	 * <p> Description: Checks whether an API key is available before an AI
	 * feature attempts to send a request. </p>
	 *
	 * @return true when an API key is available; otherwise, false
	 *
	 */

	public boolean isConfigured() {
		return !apiKey.isEmpty();
	}

	/******
	 *
	 * <p> Method: getApiKey() </p>
	 *
	 * <p> Description: Returns the API key used to authorize a request. The value
	 * should only be passed to AIService and should not be displayed or logged.
	 * </p>
	 *
	 * @return the configured API key, or an empty string when no key is available
	 *
	 */

	public String getApiKey() {
		return apiKey;
	}

	/******
	 *
	 * <p> Method: getServiceUrl() </p>
	 *
	 * <p> Description: Returns the OpenAI-compatible URL used by AIService. </p>
	 *
	 * @return the configured AI service URL
	 *
	 */

	public String getServiceUrl() {
		return serviceUrl;
	}

	/******
	 *
	 * <p> Method: getModel() </p>
	 *
	 * <p> Description: Returns the model selected for shared AI requests. </p>
	 *
	 * @return the configured AI model
	 *
	 */

	public String getModel() {
		return model;
	}

	/******
	 *
	 * <p> Method: readProperty() </p>
	 *
	 * <p> Description: Reads and cleans one property while providing a default
	 * value when the property is missing or empty. </p>
	 *
	 * @param properties specifies the configuration values being searched
	 * @param propertyName specifies the property to read
	 * @param defaultValue specifies the value used when the property is missing
	 * @return the cleaned property or its default value
	 *
	 */

	private static String readProperty(
			Properties properties,
			String propertyName,
			String defaultValue) {

		String value = cleanValue(properties.getProperty(propertyName));
		return value.isEmpty() ? defaultValue : value;
	}

	/******
	 *
	 * <p> Method: cleanValue() </p>
	 *
	 * <p> Description: Removes surrounding spaces from a configuration value and
	 * converts a missing value into an empty string. </p>
	 *
	 * @param value specifies the configuration value being cleaned
	 * @return the cleaned value or an empty string
	 *
	 */
    
	private static String cleanValue(String value) {
		return value == null ? "" : value.trim();
	}

}
