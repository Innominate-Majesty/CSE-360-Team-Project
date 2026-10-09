package passwordValidator;

/**
 * Validates a password based on character rules and length requirements using a
 * finite state machine (FSM) approach.
 * 
 * <p>Copyright: ASU Fall2026 CSE360 75096 Team 15 (c) 2026</p>
 * 
 * @author Alexander Robert Murray
 * @version 1.0
 */
public class PasswordValidator {

    /**
     * Stores the error description generated during the last evaluation.
     */
    public static String passwordErrorMessage = "";   // The error message text

    /**
     * Stores the index within the password string where an error was encountered,
     * or {@code -1} if no error occurred.
     */
    public static int passwordIndexOfError = -1;      // The index where the error was located

    /**
     * Flag indicating whether an uppercase character was found.
     */
    public static boolean foundUpperCase = false;

    /**
     * Flag indicating whether a lowercase character was found.
     */
    public static boolean foundLowerCase = false;

    /**
     * Flag indicating whether a numeric digit was found.
     */
    public static boolean foundNumericDigit = false;

    /**
     * Flag indicating whether an allowed special character was found.
     */
    public static boolean foundSpecialChar = false;

    /**
     * Flag indicating whether the password meets the specified length criteria.
     */
    public static boolean foundCorrectLength = false;

    /**
     * Resets all evaluation flags and error trackers before checking a new string.
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
     * Evaluates a password against character rules and length requirements.
     * 
     * @param input the password string to evaluate
     * @return an empty string if completely valid; an error description otherwise
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
     * Determines whether the provided password passes all validation criteria.
     * 
     * @param input the password string to check
     * @return {@code true} if the password satisfies all criteria, else {@code false}
     */
    public static boolean isValid(String input) {
        return evaluatePassword(input).isEmpty();
    }
}