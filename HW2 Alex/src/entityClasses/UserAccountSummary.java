package entityClasses;

/*******
 * 
 * <p> Title: UserAccountSummary Class. </p>
 *
 * <p> Description: Constructs a password free summary containing the user account information that an admin is allowed to view </p>
 * 
 */

public class UserAccountSummary{

    // Stores the username that uniquely identifies the user account
    private String userName;

    // Stores the user's first name
    private String firstName;

    // Stores the user's middle name
    private String middleName;

    // Stores the user's last name
    private String lastName;

    // Stores the user's preferred first name
    private String preferredFirstName;

    // Stores the user's email address
    private String emailAddress;

    // Stores whether the user account has the Admin role
    private boolean adminRole;

    // Stores whether the user account has the existing Role1 assignment
    private boolean role1;

    // Stores whether the user account has the existing Role2 assignment
    private boolean role2;

    /******
     * 
     * <p> Method: UserAccountSummary </p>
     * 
     * <p> Description: Constructs a password free user account summary
     * 
     * @param userName specifies the account username
     * @param firstName specifies the user's first name
     * @param middleName specifies the user's middle name
     * @param lastName specifies the user's last name
     * @param preferredFirstName specifies the user's preferred first name
     * @param emailAddress specifies the user's email address
     * @param adminRole specifies whether the account has the Admin role
     * @param role1 specifies whether the account has the existing Role1 assignment
     * @param role2 specifies whether the account has the existing Role2 assignment
     * 
     */

    public UserAccountSummary(String userName, String firstName, String middleName, String lastName, String preferredFirstName, String emailAddress, boolean adminRole, boolean role1, boolean role2){

        // Stores the provided username
        this.userName = userName;

        // Stores the provided first name
        this.firstName = firstName;

        // Stores the provided middle name
        this.middleName = middleName;

        // Stores the provided last name
        this.lastName = lastName;

        // Stores the provided preferred first name
        this.preferredFirstName = preferredFirstName;

        // Stores the provided email address
        this.emailAddress = emailAddress;

        // Stores whether the account has the Admin role
        this.adminRole = adminRole;

        // Stores whether the account has Role1
        this.role1 = role1;

        // Stores whether the account has Role2
        this.role2 = role2;

    }

    /******
     * 
     * <p> Method: getUserName() </p>
     * 
     * <p> Description: Returns the username stored in this account summary </p>
     * 
     * @return the account username
     * 
     */

    public String getUserName() {

        // Returns the stored username
        return userName;

    }

    /******
     * 
     * <p> Method: getFirstName() </p>
     * 
     * <p> Description: Returns the first name stored in this account summary </p>
     * 
     * @return the user's first name
     * 
     */

    public String getFirstName() {

        // Returns the stored first name
        return firstName;

    }

    /******
     * 
     * <p> Method: getMiddleName() </p>
     * 
     * <p> Description: Returns the middle name stored in this account summary </p>
     * 
     * @return the user's middle name
     * 
     */

    public String getMiddleName() {

        // Returns the stored middle name
        return middleName;

    }

    /******
     * 
     * <p> Method: getLastName() </p>
     * 
     * <p> Description: Returns the last name stored in this account summary </p>
     * 
     * @return the user's last name
     * 
     */

    public String getLastName() {

        // Returns the stored last name
        return lastName;

    }

    /******
     * 
     * <p> Method: getPreferredFirstName() </p>
     * 
     * <p> Description: Returns the preferred first name stored in this account summary </p>
     * 
     * @return the user's preferred first name
     * 
     */

    public String getPreferredFirstName() {

        // Returns the stored preferred first name
        return preferredFirstName;

    }

    /******
     * 
     * <p> Method: getEmailAddress() </p>
     * 
     * <p> Description: Returns the email address stored in this account summary </p>
     * 
     * @return the user's email address
     * 
     */

    public String getEmailAddress() {

        // Returns the stored email address
        return emailAddress;

    }

    /******
     * 
     * <p> Method: getAdminRole() </p>
     * 
     * <p> Description: Returns whether this account has the Admin role</p>
     * 
     * @return true when the account has the Admin role...otherwise, false
     * 
     */

    public boolean getAdminRole() {

        // Returns the stored Admin role value
        return adminRole;
        
    }

    /******
     * 
     * <p> Method: getRole1() </p>
     * 
     * <p> Description: Returns whether this account has the existing Role1 assignment </p>
     * 
     * @return true when the account has Role1...otherwise, false
     * 
     */

    public boolean getRole1() {

        // Returns the stored Role1 value
        return role1;
        
    }

    /******
     * 
     * <p> Method: getRole2() </p>
     * 
     * <p> Description: Returns whether this account has the existing Role2 assignment </p>
     * 
     * @return true when the account has Role2...otherwise, false
     * 
     */

    public boolean getRole2() {

        // Returns the stored Role2 value
        return role2;
        
    }

    /*******
     * 
     * <p> Method: getDisplayName() </p>
     *
     * <p> Description: Formats the user's name for display on an account card.
     * The preferred format is "Last name, First name". Missing names are handled
     * without displaying extra punctuation or null values. </p>
     *
     * @return the formatted account card name
     * 
     */

    public String getDisplayName() {

        // Converts a null first name into an empty string and removes extra spaces
        String safeFirstName = firstName == null ? "" : firstName.trim();

        // Converts a null last name into an empty string and removes extra spaces
        String safeLastName = lastName == null ? "" : lastName.trim();

        // Returns "Last name, First name" when both names are available
        if (!safeLastName.isEmpty() && !safeFirstName.isEmpty()) {

            // Combines the last and first names in the required format
            return safeLastName + ", " + safeFirstName;
        }

        // Returns only the last name when the first name is missing
        if (!safeLastName.isEmpty()) {

            // Returns the available last name
            return safeLastName;
        }

        // Returns only the first name when the last name is missing
        if (!safeFirstName.isEmpty()) {

            // Returns the available first name
            return safeFirstName;
        }

        // Converts a null username into an empty string and removes extra spaces
        String safeUserName = userName == null ? "" : userName.trim();

        // Returns the username when both the first and last names are missing
        if (!safeUserName.isEmpty()) {

            // Returns the available username
            return safeUserName;
        }

        // Returns a readable fallback if the account has no usable name or username
        return "Unknown User";
    }

    /*******
     * 
     * <p> Method: getDisplayRoles() </p>
     *
     * <p> Description: Converts the stored role values into readable role names.
     * Role1 and Role2 retain their current names until their meanings are defined
     * during TP2. </p>
     *
     * @return the account's formatted role names
     */
    public String getDisplayRoles() {

        // Creates the readable list of assigned roles
        StringBuilder displayedRoles = new StringBuilder();

        // Checks whether the account has the Admin role
        if (adminRole) {

            // Adds the Admin role to the readable list
            displayedRoles.append("Admin");
        }

        // Checks whether the account has Role1
        if (role1) {

            // Adds a separator when another role is already displayed
            if (displayedRoles.length() > 0) displayedRoles.append(", ");

            // Adds Role1 to the readable list
            displayedRoles.append("Role1");
        }

        // Checks whether the account has Role2
        if (role2) {

            // Adds a separator when another role is already displayed
            if (displayedRoles.length() > 0) displayedRoles.append(", ");

            // Adds Role2 to the readable list
            displayedRoles.append("Role2");
        }

        // Checks whether the account has no assigned roles
        if (displayedRoles.length() == 0) {

            // Returns a readable value when no role is assigned
            return "No assigned role";
        }

        // Returns every assigned role using its current readable name
        return displayedRoles.toString();
    }

}
