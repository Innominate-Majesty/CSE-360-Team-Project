package passwordValidator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class PasswordValidatorTest {

    @BeforeEach
    void setUp() {
        PasswordValidator.resetFlags();
    }

    // --- Valid Passwords ---

    @Test
    @DisplayName("Valid password meeting all requirements should pass")
    void testValidPassword() {
        String result = PasswordValidator.evaluatePassword("Abcdef1!");
        
        assertEquals("", result);
        assertTrue(PasswordValidator.isValid("Abcdef1!"));
        assertTrue(PasswordValidator.foundUpperCase);
        assertTrue(PasswordValidator.foundLowerCase);
        assertTrue(PasswordValidator.foundNumericDigit);
        assertTrue(PasswordValidator.foundSpecialChar);
        assertTrue(PasswordValidator.foundCorrectLength);
        assertEquals(-1, PasswordValidator.passwordIndexOfError);
    }

    @Test
    @DisplayName("Boundary: Minimum valid length (8 characters)")
    void testBoundaryMinLength() {
        // Exactly 8 chars: 1 upper, 1 lower, 1 digit, 1 special, 4 lowers
        String input = "Aa1!bcde";
        assertEquals(8, input.length());
        assertTrue(PasswordValidator.isValid(input));
    }

    @Test
    @DisplayName("Boundary: Maximum valid length (58 characters)")
    void testBoundaryMaxLength() {
        // Exactly 58 chars
        String input = "Aa1!" + "a".repeat(54);
        assertEquals(58, input.length());
        assertTrue(PasswordValidator.isValid(input));
    }

    // --- Null and Empty Checks ---

    @Test
    @DisplayName("Null input returns empty error message")
    void testNullPassword() {
        String result = PasswordValidator.evaluatePassword(null);
        assertEquals("*** Error *** The password is empty!", result);
        assertFalse(PasswordValidator.isValid(null));
    }

    @Test
    @DisplayName("Empty string returns empty error message")
    void testEmptyPassword() {
        String result = PasswordValidator.evaluatePassword("");
        assertEquals("*** Error *** The password is empty!", result);
        assertFalse(PasswordValidator.isValid(""));
    }

    // --- Invalid Characters ---

    @ParameterizedTest
    @ValueSource(strings = {
        "Abcdef1! ",   // Contains space
        "Abcdef1!\t",  // Contains tab
        "Abcdef1!©",   // Non-ASCII symbol
        "Abcdef1!😊"   // Emoji
    })
    @DisplayName("Fails immediately when an invalid character is encountered")
    void testInvalidCharacters(String input) {
        String result = PasswordValidator.evaluatePassword(input);
        
        assertEquals("*** Error *** An invalid character has been found!", result);
        assertEquals(8, PasswordValidator.passwordIndexOfError); // The invalid char is at index 8
        assertFalse(PasswordValidator.isValid(input));
    }

    // --- Length Violations ---

    @Test
    @DisplayName("Length too short (7 characters)")
    void testTooShort() {
        String input = "Aa1!bcd"; // 7 chars
        String result = PasswordValidator.evaluatePassword(input);
        
        assertTrue(result.contains("Long Enough;"));
        assertFalse(PasswordValidator.foundCorrectLength);
        assertFalse(PasswordValidator.isValid(input));
        assertEquals(7, PasswordValidator.passwordIndexOfError);
    }

    @Test
    @DisplayName("Length too long (59 characters)")
    void testTooLong() {
        String input = "Aa1!" + "a".repeat(55); // 59 chars
        String result = PasswordValidator.evaluatePassword(input);
        
        assertTrue(result.contains("Long Enough;"));
        assertFalse(PasswordValidator.foundCorrectLength);
        assertFalse(PasswordValidator.isValid(input));
        assertEquals(59, PasswordValidator.passwordIndexOfError);
    }

    // --- Missing Individual Criteria ---

    @Test
    @DisplayName("Missing upper case letter")
    void testMissingUpperCase() {
        String result = PasswordValidator.evaluatePassword("abcdef1!");
        
        assertTrue(result.contains("Upper case;"));
        assertFalse(PasswordValidator.foundUpperCase);
        assertFalse(PasswordValidator.isValid("abcdef1!"));
    }

    @Test
    @DisplayName("Missing lower case letter")
    void testMissingLowerCase() {
        String result = PasswordValidator.evaluatePassword("ABCDEF1!");
        
        assertTrue(result.contains("Lower case;"));
        assertFalse(PasswordValidator.foundLowerCase);
        assertFalse(PasswordValidator.isValid("ABCDEF1!"));
    }

    @Test
    @DisplayName("Missing numeric digit")
    void testMissingDigit() {
        String result = PasswordValidator.evaluatePassword("Abcdefgh!");
        
        assertTrue(result.contains("Numeric digits;"));
        assertFalse(PasswordValidator.foundNumericDigit);
        assertFalse(PasswordValidator.isValid("Abcdefgh!"));
    }

    @Test
    @DisplayName("Missing special character")
    void testMissingSpecialChar() {
        String result = PasswordValidator.evaluatePassword("Abcdef12");
        
        assertTrue(result.contains("Special character;"));
        assertFalse(PasswordValidator.foundSpecialChar);
        assertFalse(PasswordValidator.isValid("Abcdef12"));
    }

    @Test
    @DisplayName("Multiple missing criteria reported together")
    void testMultipleMissingCriteria() {
        // Missing upper case, numeric digits, and special characters
        String result = PasswordValidator.evaluatePassword("abcdefgh");
        
        assertTrue(result.contains("Upper case;"));
        assertTrue(result.contains("Numeric digits;"));
        assertTrue(result.contains("Special character;"));
        assertFalse(result.contains("Lower case;"));
        assertFalse(result.contains("Long Enough;"));
        assertTrue(result.endsWith("conditions were not satisfied"));
    }
}