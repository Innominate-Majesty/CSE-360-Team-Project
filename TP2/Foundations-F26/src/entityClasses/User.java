package entityClasses;

/**
 * Represents a user entity in the system.
 * 
 * <p>Contains user details such as userName, password, name fields,
 * email address, and assigned roles.</p>
 * 
 * <p>Copyright: Lynn Robert Carter © 2025</p>
 * 
 * @author Lynn Robert Carter
 */
public class User {
	
	/*
	 * These are the private attributes for this entity object
	 */
    private String userName;
    private String password;
    private String firstName;
    private String middleName;
    private String lastName;
    private String preferredFirstName;
    private String emailAddress;
    private boolean adminRole;
    private boolean role1;
    private boolean role2;
    
    /**
     * Default constructor for the User class.
     */
    public User() {
    	
    }

    /**
     * Initializes a new User object with full profile details and role flags.
     * 
     * @param userName specifies the account username
     * @param password specifies the account password
     * @param fn specifies the user's first name
     * @param mn specifies the user's middle name
     * @param ln specifies the user's last name
     * @param pfn specifies the user's preferred first name
     * @param ea specifies the user's email address
     * @param r1 specifies the Admin role flag
     * @param r2 specifies the Role1 flag
     * @param r3 specifies the Role2 flag
     */
    public User(String userName, String password, String fn, String mn, String ln, String pfn, 
    		String ea, boolean r1, boolean r2, boolean r3) {
        this.userName = userName;
        this.password = password;
        this.firstName = fn;
        this.middleName = mn;
        this.lastName = ln;
        this.preferredFirstName = pfn;
        this.emailAddress = ea;
        this.adminRole = r1;
        this.role1 = r2;
        this.role2 = r3;
    }

    /**
     * Sets the Admin role status.
     * 
     * @param role {@code true} if this user plays the Admin role, else {@code false}
     */
    public void setAdminRole(boolean role) {
    	this.adminRole = role;
    }

    /**
     * Sets the Role1 status.
     * 
     * @param role {@code true} if this user plays Role1, else {@code false}
     */
    public void setRole1User(boolean role) {
    	this.role1 = role;
    }

    /**
     * Sets the Role2 status.
     * 
     * @param role {@code true} if this user plays Role2, else {@code false}
     */
    public void setRole2User(boolean role) {
    	this.role2 = role;
    }

    /**
     * Gets the username.
     * 
     * @return the username string
     */
    public String getUserName() { return userName; }

    /**
     * Gets the password.
     * 
     * @return the password string
     */
    public String getPassword() { return password; }

    /**
     * Gets the first name.
     * 
     * @return the first name string
     */
    public String getFirstName() { return firstName; }

    /**
     * Gets the middle name.
     * 
     * @return the middle name string
     */
    public String getMiddleName() { return middleName; }

    /**
     * Gets the last name.
     * 
     * @return the last name string
     */
    public String getLastName() { return lastName; }

    /**
     * Gets the preferred first name.
     * 
     * @return the preferred first name string
     */
    public String getPreferredFirstName() { return preferredFirstName; }

    /**
     * Gets the email address.
     * 
     * @return the email address string
     */
    public String getEmailAddress() { return emailAddress; }

    /**
     * Sets the username.
     * 
     * @param s the username to assign
     */
    public void setUserName(String s) { userName = s; }

    /**
     * Sets the password.
     * 
     * @param s the password to assign
     */
    public void setPassword(String s) { password = s; }

    /**
     * Sets the first name.
     * 
     * @param s the first name to assign
     */
    public void setFirstName(String s) { firstName = s; }

    /**
     * Sets the middle name.
     * 
     * @param s the middle name to assign
     */
    public void setMiddleName(String s) { middleName = s; }

    /**
     * Sets the last name.
     * 
     * @param s the last name to assign
     */
    public void setLastName(String s) { lastName = s; }

    /**
     * Sets the preferred first name.
     * 
     * @param s the preferred first name to assign
     */
    public void setPreferredFirstName(String s) { preferredFirstName = s; }

    /**
     * Sets the email address.
     * 
     * @param s the email address to assign
     */
    public void setEmailAddress(String s) { emailAddress = s; }

    /**
     * Gets the current value of the Admin role attribute.
     * 
     * @return {@code true} if the user has the Admin role, else {@code false}
     */
    public boolean getAdminRole() { return adminRole; }

    /**
     * Gets the current value of the Role1 attribute.
     * 
     * @return {@code true} if the user has Role1, else {@code false}
     */
	public boolean getNewRole1() { return role1; }

    /**
     * Gets the current value of the Role2 attribute.
     * 
     * @return {@code true} if the user has Role2, else {@code false}
     */
    public boolean getNewRole2() { return role2; }

    /**
     * Calculates the total number of roles this user holds.
     * 
     * @return the count of active roles (0 - 3)
     */
    public int getNumRoles() {
    	int numRoles = 0;
    	if (adminRole) numRoles++;
    	if (role1) numRoles++;
    	if (role2) numRoles++;
    	return numRoles;
    }
}