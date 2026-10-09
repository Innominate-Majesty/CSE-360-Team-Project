package passwordValidator;

/**
 * <p> Title: PasswordValidator - establishes the required FSM and validation.</p>
 * 
 * <p> Description: This class is responsible for validating a password based on the requirements of the Foundations-F26 Team 15 
 * Project specifications.</p>
 * 
 * <p> Copyright: ASU Fall2026 CSE360 75096 Team 15 (c) 2026</p>
 * 
 * @author Alexander Robert Murray
 * 
 * @version 1.0
 */

public class PasswordValidator {

    /**
     * The error message text
     */
	public static String passwordErrorMessage = "";
	/**
	 * The index where the error was located
	 */
    public static int passwordIndexOfError = -1;
    /**
     * A boolean to identify whether there is an uppercase letter in the string
     */
    public static boolean foundUpperCase = false;
    /**
     * A flag for whether there is a lowercase letter
     */
    public static boolean foundLowerCase = false;
    /**
     * A flag for whether there is a numeric digit
     */
    public static boolean foundNumericDigit = false;
    /**
     * A flag for whether there is a special character
     */
    public static boolean foundSpecialChar = false;
    /**
     * A flag for whether the length is valdi
     */
    public static boolean foundCorrectLength = false;
    
    /**
	 * Default constructor is not used.
	 */
	public PasswordValidator(){}


    /**
     * <p> Title: resetFlags - Public Method </p>
     * 
     * <p> Description: Resets all evaluation flags before checking a new string. </p>
     */

    public static void resetFlags() {
        passwordErrorMessage = "";
        passwordIndexOfError = -1;
        foundUpperCase = false;
        foundLowerCase = false;
        foundNumericDigit = false;
        foundSpecialChar = false;
        foundCorrectLength = false;
    }

    /**
     * <p> Title: evaluatePassword - Public Method </p>
     * <p> Description: Evaluates a password against character rules and length requirements. </p>
     * 
     * @param input The password string to evaluate
     * @return Empty string if completely valid; error description otherwise.
     */

    public static String evaluatePassword(String input) {
        resetFlags();

        if (input == null || input.isEmpty()) {
            return "*** Error *** The password is empty!";
        }

        int currentCharNdx = 0;       // The index of the current character
        char currentChar;             // The current character in the line
        boolean running = true;       // The flag that specifies if the FSM is running

		// Since the input is not empty, the FSM begins running until the end of the string is reached or an invalid character is found.
        while (running) {
            currentChar = input.charAt(currentCharNdx); // The current character from the above indexed position

            // Checks for Upper Case Character.
            if (currentChar >= 'A' && currentChar <= 'Z') {
                foundUpperCase = true;
            } 
            //Checks for Lower Case Character.
            else if (currentChar >= 'a' && currentChar <= 'z') {
                foundLowerCase = true;
            } 
            //Checks for Numeric Digit.
            else if (currentChar >= '0' && currentChar <= '9') {
                foundNumericDigit = true;
            } 
            //Checks for Special Character.
            else if ("~`!@#$%^&*()_-+={}[]|\\:;\"'<>,.?/".indexOf(currentChar) >= 0) {
                foundSpecialChar = true;
            } 
            //Checks for Invalid Character, and if found displays error message.
            else {
                passwordIndexOfError = currentCharNdx;
                passwordErrorMessage = "*** Error *** An invalid character has been found!";
                return passwordErrorMessage;
            }

            currentCharNdx++;
            if (currentCharNdx >= input.length()) {
                running = false;
            }
        }

        // Verify that the password is between 8 and 58 characters long
        if (input.length() >= 8 && input.length() <= 58) {
            foundCorrectLength = true;
        }

        // Check if all criteria have been met
        String unmet = "";
        if (!foundUpperCase) unmet += "Upper case; ";
        if (!foundLowerCase) unmet += "Lower case; ";
        if (!foundNumericDigit) unmet += "Numeric digits; ";
        if (!foundSpecialChar) unmet += "Special character; ";
        if (!foundCorrectLength) unmet += "Long Enough; ";

        if (unmet.isEmpty()) {
            return "";
        }

        passwordIndexOfError = input.length();
        passwordErrorMessage = unmet + "conditions were not satisfied";
        return passwordErrorMessage;
    }

    /**
     * <p> Title: isValid - Public Method </p>
     * 
     * <p> Description: Convenience boolean check. </p>
     * 
     * @param input The password input string
     * 
     * @return true if the string is a valid password
     */
    public static boolean isValid(String input) {
        return evaluatePassword(input).isEmpty();
    }
    
}
