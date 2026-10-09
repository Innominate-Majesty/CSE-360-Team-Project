package database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import entityClasses.User;
import entityClasses.UserAccountSummary;

/**
 * An in-memory database built on H2. Detailed documentation of H2 can
 * be found at <a href="https://www.h2database.com/html/main.html">h2database.com</a>.
 * This class leverages H2 and provides numerous special supporting methods.
 * 
 * <p>Copyright: Lynn Robert Carter © 2025</p>
 * 
 * @author Lynn Robert Carter
 * @author Alan G. Shimp
 * 
 * @version 2.00        2025-04-29 Updated and expanded from the version produced by Pravalika 
 *                             Mukkiri and Ishwarya Hidkimath Basavaraj
 * @version 2.01        2025-12-17 Minor updates for Spring 2026
 * @version 2.02        2026-09-12 Added deleteUser() method for TP1
 * @version 2.03        2026-09-16 Added updatePassword() method for TP1
 */
public class Database {

	// JDBC driver name and database URL 
	static final String JDBC_DRIVER = "org.h2.Driver";   
	static final String DB_URL = "jdbc:h2:~/FoundationDatabase";  

	// Database credentials 
	static final String USER = "sa"; 
	static final String PASS = ""; 

	// Shared variables used within this class
	private Connection connection = null;		// Singleton to access the database 
	private Statement statement = null;			// The H2 Statement is used to construct queries
	
	// These are the easily accessible attributes of the currently logged-in user
	// This is only useful for single user applications
	private String currentUsername;
	private String currentPassword;
	private String currentFirstName;
	private String currentMiddleName;
	private String currentLastName;
	private String currentPreferredFirstName;
	private String currentEmailAddress;
	private boolean currentAdminRole;
	private boolean currentNewRole1;
	private boolean currentNewRole2;
	private String tempPassword;

	/**
	 * Default constructor used to establish this database object.
	 */
	public Database() {
		
	}
	
	/**
	 * Establishes the in-memory instance of the H2 database from secondary storage.
	 *
	 * @throws SQLException when the DriverManager is unable to establish a connection
	 */
	public void connectToDatabase() throws SQLException {
		try {
			Class.forName(JDBC_DRIVER); // Load the JDBC driver
			connection = DriverManager.getConnection(DB_URL, USER, PASS);
			statement = connection.createStatement(); 
			
			System.out.println(">>> USING THIS DATABASE.JAVA <<<");

			// You can use this command to clear the database and restart from fresh.
			// statement.execute("DROP ALL OBJECTS");

			createTables();  // Create the necessary tables if they don't exist
		} catch (ClassNotFoundException e) {
			System.err.println("JDBC Driver not found: " + e.getMessage());
		}
	}

	/**
	 * Creates new instances of the database tables used by this class.
	 * 
	 * @throws SQLException if a database access error occurs during table creation
	 */
	private void createTables() throws SQLException {
		// Create the user database
		String userTable = "CREATE TABLE IF NOT EXISTS userDB ("
				+ "id INT AUTO_INCREMENT PRIMARY KEY, "
				+ "userName VARCHAR(255) UNIQUE, "
				+ "password VARCHAR(255), "
				+ "firstName VARCHAR(255), "
				+ "middleName VARCHAR(255), "
				+ "lastName VARCHAR (255), "
				+ "preferredFirstName VARCHAR(255), "
				+ "emailAddress VARCHAR(255), "
				+ "adminRole BOOL DEFAULT FALSE, "
				+ "newRole1 BOOL DEFAULT FALSE, "
				+ "newRole2 BOOL DEFAULT FALSE, "
				+ "tempPassword VARCHAR(255))";
		statement.execute(userTable);
		
		// Create the invitation codes table
	    String invitationCodesTable = "CREATE TABLE IF NOT EXISTS InvitationCodes ("
	            + "code VARCHAR(10) PRIMARY KEY, "
	    		+ "emailAddress VARCHAR(255), "
	            + "role VARCHAR(10))";
	    statement.execute(invitationCodesTable);
	}

	/**
	 * Checks whether the user database contains any records.
	 * 
	 * @return {@code true} if the database is empty, else {@code false}
	 */
	public boolean isDatabaseEmpty() {
	    String query = "SELECT COUNT(*) AS count FROM userDB";

	    try {
	        ResultSet resultSet = statement.executeQuery(query);

	        if (resultSet.next()) {
	            int count = resultSet.getInt("count");
	            System.out.println(">>> USER COUNT = " + count);
	            return count == 0;
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }

	    return true;
	}
	
	/**
	 * Returns the number of users currently in the user database.
	 * 
	 * @return the number of user records in the database
	 */
	public int getNumberOfUsers() {
		String query = "SELECT COUNT(*) AS count FROM userDB";
		try {
			ResultSet resultSet = statement.executeQuery(query);
			if (resultSet.next()) {
				return resultSet.getInt("count");
			}
		} catch (SQLException e) {
	        return 0;
	    }
		return 0;
	}
	
	/**
	 * Returns the number of users with a specified role currently in the user database.
	 *
	 * @param role specifies the role whose users are to be counted
	 * @return the count of the specified role in the database
	 */
	public int getRoleCount(String role) {
		String columnName = "";
		
		// Set string columnName to the corresponding column name
		if (role.equals("Admin")) {
			columnName = "adminRole";
		}
		else if (role.equals("Role1")) {
			columnName = "newRole1";
		}
		else if (role.equals("Role2")) {
			columnName = "newRole2";
		}
		else {
			return 0;
		}
		
		String query = "SELECT COUNT(*) AS count FROM userDB WHERE " + columnName + " = TRUE";
		try {
			ResultSet resultSet = statement.executeQuery(query);
			if (resultSet.next()) {
				return resultSet.getInt("count");
			}
		} catch (SQLException e) {
	        return 0;
	    }
		return 0;
	}	

	/**
	 * Creates a new row in the database using the provided user parameter.
	 * 
	 * @param user specifies a user object to be added to the database
	 * @throws SQLException when there is an issue creating or executing the SQL command
	 */
	public void register(User user) throws SQLException {
		String insertUser = "INSERT INTO userDB (userName, password, firstName, middleName, "
				+ "lastName, preferredFirstName, emailAddress, adminRole, newRole1, newRole2) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try (PreparedStatement pstmt = connection.prepareStatement(insertUser)) {
			currentUsername = user.getUserName();
			pstmt.setString(1, currentUsername);
			
			currentPassword = user.getPassword();
			pstmt.setString(2, currentPassword);
			
			currentFirstName = user.getFirstName();
			pstmt.setString(3, currentFirstName);
			
			currentMiddleName = user.getMiddleName();			
			pstmt.setString(4, currentMiddleName);
			
			currentLastName = user.getLastName();
			pstmt.setString(5, currentLastName);
			
			currentPreferredFirstName = user.getPreferredFirstName();
			pstmt.setString(6, currentPreferredFirstName);
			
			currentEmailAddress = user.getEmailAddress();
			pstmt.setString(7, currentEmailAddress);
			
			currentAdminRole = user.getAdminRole();
			pstmt.setBoolean(8, currentAdminRole);
			
			currentNewRole1 = user.getNewRole1();
			pstmt.setBoolean(9, currentNewRole1);
			
			currentNewRole2 = user.getNewRole2();
			pstmt.setBoolean(10, currentNewRole2);
			
			pstmt.executeUpdate();
		}
	}
	
	/**
	 * Generates a list of strings, one for each user in the database,
	 * starting with {@code "<Select a User>"} at the start of the list.
	 *  
	 * @return a list of userNames found in the database, or {@code null} on failure
	 */
	public List<String> getUserList() {
		List<String> userList = new ArrayList<String>();
		userList.add("<Select a User>");
		String query = "SELECT userName FROM userDB";
		try (PreparedStatement pstmt = connection.prepareStatement(query)) {
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				userList.add(rs.getString("userName"));
			}
		} catch (SQLException e) {
	        return null;
	    }
		return userList;
	}

	/**
	 * Retrieves the account information that an admin is allowed to view.
	 * Passwords and database ID are not retrieved.
	 * 
	 * @return a list containing a summary of every user account without revealing passwords
	 * @throws SQLException when the account information cannot be retrieved
	 */
	public List<UserAccountSummary> getAllUserAccountSummaries() throws SQLException {
		List<UserAccountSummary> accountSummaries = new ArrayList<UserAccountSummary>();

		String query = "SELECT userName, firstName, middleName, lastName, " 
				+ "preferredFirstName, emailAddress, adminRole, newRole1, newRole2 " 
				+ "FROM userDB " 
				+ "ORDER BY lastName, firstName, userName";

		try (PreparedStatement pstmt = connection.prepareStatement(query)) {
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					UserAccountSummary accountSummary = new UserAccountSummary(
							rs.getString("userName"), 
							rs.getString("firstName"), 
							rs.getString("middleName"), 
							rs.getString("lastName"), 
							rs.getString("preferredFirstName"), 
							rs.getString("emailAddress"), 
							rs.getBoolean("adminRole"), 
							rs.getBoolean("newRole1"), 
							rs.getBoolean("newRole2"));
					accountSummaries.add(accountSummary);
				}
			}
		}
		return accountSummaries;
	}

	/**
	 * Validates an admin user's credentials against the database.
	 * 
	 * @param user specifies the specific user attempting to log in as Admin
	 * @return {@code true} if the specified user has been authenticated as an Admin, else {@code false}
	 */
	public boolean loginAdmin(User user){
		String query = "SELECT * FROM userDB WHERE userName = ? AND password = ? AND "
				+ "adminRole = TRUE";
		try (PreparedStatement pstmt = connection.prepareStatement(query)) {
			pstmt.setString(1, user.getUserName());
			pstmt.setString(2, user.getPassword());
			ResultSet rs = pstmt.executeQuery();
			return rs.next();	
		} catch (SQLException e) {
	        e.printStackTrace();
	    }
		return false;
	}
	
	/**
	 * Validates a Role1 user's credentials against the database.
	 * 
	 * @param user specifies the specific user attempting to log in under Role1
	 * @return {@code true} if the specified user has been authenticated under Role1, else {@code false}
	 */
	public boolean loginRole1(User user) {
		String query = "SELECT * FROM userDB WHERE userName = ? AND password = ? AND "
				+ "newRole1 = TRUE";
		try (PreparedStatement pstmt = connection.prepareStatement(query)) {
			pstmt.setString(1, user.getUserName());
			pstmt.setString(2, user.getPassword());
			ResultSet rs = pstmt.executeQuery();
			return rs.next();
		} catch (SQLException e) {
		    e.printStackTrace();
		}
		return false;
	}

	/**
	 * Validates a Role2 user's credentials against the database.
	 * 
	 * @param user specifies the specific user attempting to log in under Role2
	 * @return {@code true} if the specified user has been authenticated under Role2, else {@code false}
	 */
	public boolean loginRole2(User user) {
		String query = "SELECT * FROM userDB WHERE userName = ? AND password = ? AND "
				+ "newRole2 = TRUE";
		try (PreparedStatement pstmt = connection.prepareStatement(query)) {
			pstmt.setString(1, user.getUserName());
			pstmt.setString(2, user.getPassword());
			ResultSet rs = pstmt.executeQuery();
			return rs.next();
		} catch (SQLException e) {
		    e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * Checks if a user already exists in the database based on their username.
	 * 
	 * @param userName specifies the username to query
	 * @return {@code true} if the specified user is in the table, else {@code false}
	 */
	public boolean doesUserExist(String userName) {
	    String query = "SELECT COUNT(*) FROM userDB WHERE userName = ?";
	    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
	        pstmt.setString(1, userName);
	        ResultSet rs = pstmt.executeQuery();
	        if (rs.next()) {
	            return rs.getInt(1) > 0;
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return false;
	}

	/**
	 * Determines the number of roles a specified user possesses.
	 * 
	 * @param user specifies the user object to inspect
	 * @return the number of roles this user plays (0 - 3)
	 */	
	public int getNumberOfRoles(User user) {
		int numberOfRoles = 0;
		if (user.getAdminRole()) numberOfRoles++;
		if (user.getNewRole1()) numberOfRoles++;
		if (user.getNewRole2()) numberOfRoles++;
		return numberOfRoles;
	}	

	/**
	 * Generates a random invitation code and inserts it into the database.
	 * 
	 * @param emailAddress specifies the email address for this new user
	 * @param role specifies the role that this new user will play
	 * @return the six-character invitation code
	 */
	public String generateInvitationCode(String emailAddress, String role) {
	    String code = UUID.randomUUID().toString().substring(0, 6);
	    String query = "INSERT INTO InvitationCodes (code, emailaddress, role) VALUES (?, ?, ?)";

	    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
	        pstmt.setString(1, code);
	        pstmt.setString(2, emailAddress);
	        pstmt.setString(3, role);
	        pstmt.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return code;
	}

	/**
	 * Determines the number of outstanding invitations in the table.
	 *  
	 * @return the number of invitations in the table
	 */
	public int getNumberOfInvitations() {
		String query = "SELECT COUNT(*) AS count FROM InvitationCodes";
		try {
			ResultSet resultSet = statement.executeQuery(query);
			if (resultSet.next()) {
				return resultSet.getInt("count");
			}
		} catch (SQLException e) {
	        e.printStackTrace();
	    }
		return 0;
	}
	
	/**
	 * Determines if an invitation email address is already present in the table.
	 * 
	 * @param emailAddress is a string that identifies a user email
	 * @return {@code true} if the email address is in the table, else {@code false}
	 */
	public boolean emailaddressHasBeenUsed(String emailAddress) {
	    String query = "SELECT COUNT(*) AS count FROM InvitationCodes WHERE emailAddress = ?";
	    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
	        pstmt.setString(1, emailAddress);
	        ResultSet rs = pstmt.executeQuery();
	        if (rs.next()) {
	        	return rs.getInt("count") > 0;
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
		return false;
	}
	
	/**
	 * Gets the role associated with an invitation code.
	 * 
	 * @param code the 6-character invitation code
	 * @return the role for the code, or an empty string if not found
	 */
	public String getRoleGivenAnInvitationCode(String code) {
	    String query = "SELECT * FROM InvitationCodes WHERE code = ?";
	    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
	        pstmt.setString(1, code);
	        ResultSet rs = pstmt.executeQuery();
	        if (rs.next()) {
	            return rs.getString("role");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return "";
	}

	/**
	 * Gets the email address associated with an invitation code.
	 * 
	 * @param code the 6-character invitation code
	 * @return the email address for the code, or an empty string if not found
	 */
	public String getEmailAddressUsingCode(String code) {
	    String query = "SELECT emailAddress FROM InvitationCodes WHERE code = ?";
	    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
	        pstmt.setString(1, code);
	        ResultSet rs = pstmt.executeQuery();
	        if (rs.next()) {
	            return rs.getString("emailAddress");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
		return "";
	}
	
	/**
	 * Removes an invitation record from the database once it has been used.
	 * 
	 * @param code the 6-character invitation code
	 */
	public void removeInvitationAfterUse(String code) {
	    String query = "SELECT COUNT(*) AS count FROM InvitationCodes WHERE code = ?";
	    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
	        pstmt.setString(1, code);
	        ResultSet rs = pstmt.executeQuery();
	        if (rs.next()) {
	        	int counter = rs.getInt(1);
	        	if (counter > 0) {
        			query = "DELETE FROM InvitationCodes WHERE code = ?";
	        		try (PreparedStatement pstmt2 = connection.prepareStatement(query)) {
	        			pstmt2.setString(1, code);
	        			pstmt2.executeUpdate();
	        		} catch (SQLException e) {
	        	        e.printStackTrace();
	        	    }
	        	}
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	/**
	 * Updates the password of a user given their username and new password.
	 * 
	 * Alexander Robert Murray
	 * @param username is the username of the user
	 * @param password is the new password for the user
	 */
	public void updatePassword(String username, String password) {
	    String query = "UPDATE userDB SET password = ? WHERE username = ?";
	    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
	        pstmt.setString(1, password);
	        pstmt.setString(2, username);
	        pstmt.executeUpdate();
	        currentPassword = password;
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	/**
	 * Gets the first name of a user given their username.
	 * 
	 * @param username is the username of the user
	 * @return the first name of the user, or {@code null} if not found
	 */
	public String getFirstName(String username) {
		String query = "SELECT firstName FROM userDB WHERE userName = ?";
		try (PreparedStatement pstmt = connection.prepareStatement(query)) {
			pstmt.setString(1, username);
	        ResultSet rs = pstmt.executeQuery();
	        if (rs.next()) {
	            return rs.getString("firstName");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
		return null;
	}

	/**
	 * Updates the first name of a user given their username.
	 * 
	 * @param username is the username of the user
	 * @param firstName is the new first name for the user
	 */
	public void updateFirstName(String username, String firstName) {
	    String query = "UPDATE userDB SET firstName = ? WHERE username = ?";
	    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
	        pstmt.setString(1, firstName);
	        pstmt.setString(2, username);
	        pstmt.executeUpdate();
	        currentFirstName = firstName;
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	/**
	 * Gets the middle name of a user given their username.
	 * 
	 * @param username is the username of the user
	 * @return the middle name of the user, or {@code null} if not found
	 */
	public String getMiddleName(String username) {
		String query = "SELECT MiddleName FROM userDB WHERE userName = ?";
		try (PreparedStatement pstmt = connection.prepareStatement(query)) {
			pstmt.setString(1, username);
	        ResultSet rs = pstmt.executeQuery();
	        if (rs.next()) {
	            return rs.getString("middleName");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
		return null;
	}

	/**
	 * Updates the middle name of a user given their username.
	 * 
	 * @param username is the username of the user
	 * @param middleName is the new middle name for the user
	 */
	public void updateMiddleName(String username, String middleName) {
	    String query = "UPDATE userDB SET middleName = ? WHERE username = ?";
	    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
	        pstmt.setString(1, middleName);
	        pstmt.setString(2, username);
	        pstmt.executeUpdate();
	        currentMiddleName = middleName;
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	/**
	 * Gets the last name of a user given their username.
	 * 
	 * @param username is the username of the user
	 * @return the last name of the user, or {@code null} if not found
	 */
	public String getLastName(String username) {
		String query = "SELECT LastName FROM userDB WHERE userName = ?";
		try (PreparedStatement pstmt = connection.prepareStatement(query)) {
			pstmt.setString(1, username);
	        ResultSet rs = pstmt.executeQuery();
	        if (rs.next()) {
	            return rs.getString("lastName");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
		return null;
	}
	
	/**
	 * Updates the last name of a user given their username.
	 * 
	 * @param username is the username of the user
	 * @param lastName is the new last name for the user
	 */
	public void updateLastName(String username, String lastName) {
	    String query = "UPDATE userDB SET lastName = ? WHERE username = ?";
	    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
	        pstmt.setString(1, lastName);
	        pstmt.setString(2, username);
	        pstmt.executeUpdate();
	        currentLastName = lastName;
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	/**
	 * Gets the preferred first name of a user given their username.
	 * 
	 * @param username is the username of the user
	 * @return the preferred first name of the user, or {@code null} if not found
	 */
	public String getPreferredFirstName(String username) {
		String query = "SELECT preferredFirstName FROM userDB WHERE userName = ?";
		try (PreparedStatement pstmt = connection.prepareStatement(query)) {
			pstmt.setString(1, username);
	        ResultSet rs = pstmt.executeQuery();
	        if (rs.next()) {
	            return rs.getString("firstName");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
		return null;
	}
	
	/**
	 * Updates the preferred first name of a user given their username.
	 * 
	 * @param username is the username of the user
	 * @param preferredFirstName is the new preferred first name for the user
	 */
	public void updatePreferredFirstName(String username, String preferredFirstName) {
	    String query = "UPDATE userDB SET preferredFirstName = ? WHERE username = ?";
	    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
	        pstmt.setString(1, preferredFirstName);
	        pstmt.setString(2, username);
	        pstmt.executeUpdate();
	        currentPreferredFirstName = preferredFirstName;
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	/**
	 * Gets the email address of a user given their username.
	 * 
	 * @param username is the username of the user
	 * @return the email address of the user, or {@code null} if not found
	 */
	public String getEmailAddress(String username) {
		String query = "SELECT emailAddress FROM userDB WHERE userName = ?";
		try (PreparedStatement pstmt = connection.prepareStatement(query)) {
			pstmt.setString(1, username);
	        ResultSet rs = pstmt.executeQuery();
	        if (rs.next()) {
	            return rs.getString("emailAddress");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
		return null;
	}
	
	/**
	 * Updates the email address of a user given their username.
	 * 
	 * @param username is the username of the user
	 * @param emailAddress is the new email address for the user
	 */
	public void updateEmailAddress(String username, String emailAddress) {
	    String query = "UPDATE userDB SET emailAddress = ? WHERE username = ?";
	    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
	        pstmt.setString(1, emailAddress);
	        pstmt.setString(2, username);
	        pstmt.executeUpdate();
	        currentEmailAddress = emailAddress;
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	/**
	 * Gets all attributes of a user given their username and caches them in the instance variables.
	 * 
	 * @param username is the username of the user
	 * @return {@code true} if the retrieval was successful, else {@code false}
	 */
	public boolean getUserAccountDetails(String username) {
		String query = "SELECT * FROM userDB WHERE username = ?";
		try (PreparedStatement pstmt = connection.prepareStatement(query)) {
			pstmt.setString(1, username);
	        ResultSet rs = pstmt.executeQuery();			
			rs.next();
	    	currentUsername = rs.getString(2);
	    	currentPassword = rs.getString(3);
	    	currentFirstName = rs.getString(4);
	    	currentMiddleName = rs.getString(5);
	    	currentLastName = rs.getString(6);
	    	currentPreferredFirstName = rs.getString(7);
	    	currentEmailAddress = rs.getString(8);
	    	currentAdminRole = rs.getBoolean(9);
	    	currentNewRole1 = rs.getBoolean(10);
	    	currentNewRole2 = rs.getBoolean(11);
	    	tempPassword = rs.getString(12);
			return true;
	    } catch (SQLException e) {
			return false;
	    }
	}
	
	/**
	 * Updates a specified role for a specified user and refreshes the cached state.
	 * 
	 * @param username is the username of the user
	 * @param role string that specifies the role to update ("Admin", "Role1", or "Role2")
	 * @param value string that specifies "true" or "false"
	 * @return {@code true} if the update was successful, else {@code false}
	 */
	public boolean updateUserRole(String username, String role, String value) {
		if (role.compareTo("Admin") == 0) {
			String query = "UPDATE userDB SET adminRole = ? WHERE username = ?";
			try (PreparedStatement pstmt = connection.prepareStatement(query)) {
				pstmt.setString(1, value);
				pstmt.setString(2, username);
				pstmt.executeUpdate();
				if (value.compareTo("true") == 0)
					currentAdminRole = true;
				else
					currentAdminRole = false;
				return true;
			} catch (SQLException e) {
				return false;
			}
		}
		if (role.compareTo("Role1") == 0) {
			String query = "UPDATE userDB SET newRole1 = ? WHERE username = ?";
			try (PreparedStatement pstmt = connection.prepareStatement(query)) {
				pstmt.setString(1, value);
				pstmt.setString(2, username);
				pstmt.executeUpdate();
				if (value.compareTo("true") == 0)
					currentNewRole1 = true;
				else
					currentNewRole1 = false;
				return true;
			} catch (SQLException e) {
				return false;
			}
		}
		if (role.compareTo("Role2") == 0) {
			String query = "UPDATE userDB SET newRole2 = ? WHERE username = ?";
			try (PreparedStatement pstmt = connection.prepareStatement(query)) {
				pstmt.setString(1, value);
				pstmt.setString(2, username);
				pstmt.executeUpdate();
				if (value.compareTo("true") == 0)
					currentNewRole2 = true;
				else
					currentNewRole2 = false;
				return true;
			} catch (SQLException e) {
				return false;
			}
		}
		return false;
	}

	/**
	 * Deletes a user record given their username.
	 * 
	 *  Alan G. Shimp
	 * @param username is the username of the user
	 * @return {@code true} if the deletion was successful, else {@code false}
	 */
	public boolean deleteUser(String username) {
		String query = "DELETE FROM userDB WHERE username = ?";
		try (PreparedStatement pstmt = connection.prepareStatement(query)) {
			pstmt.setString(1, username);
			pstmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Gets the current user's username.
	 * 
	 * @return the cached username
	 */
	public String getCurrentUsername() { return currentUsername; }

	/**
	 * Gets the current user's password.
	 * 
	 * @return the cached password
	 */
	public String getCurrentPassword() { return currentPassword; }
	
	/**
	 * Gets the current user's first name.
	 * 
	 * @return the cached first name
	 */
	public String getCurrentFirstName() { return currentFirstName; }

	/**
	 * Gets the current user's middle name.
	 * 
	 * @return the cached middle name
	 */
	public String getCurrentMiddleName() { return currentMiddleName; }

	/**
	 * Gets the current user's last name.
	 * 
	 * @return the cached last name
	 */
	public String getCurrentLastName() { return currentLastName; }

	/**
	 * Gets the current user's preferred first name.
	 * 
	 * @return the cached preferred first name
	 */
	public String getCurrentPreferredFirstName() { return currentPreferredFirstName; }

	/**
	 * Gets the current user's email address.
	 * 
	 * @return the cached email address
	 */
	public String getCurrentEmailAddress() { return currentEmailAddress; }

	/**
	 * Gets the current user's Admin role status.
	 * 
	 * @return {@code true} if this user has an Admin role, else {@code false}
	 */
	public boolean getCurrentAdminRole() { return currentAdminRole; }

	/**
	 * Gets the current user's Role1 status.
	 * 
	 * @return {@code true} if this user has Role1, else {@code false}
	 */
	public boolean getCurrentNewRole1() { return currentNewRole1; }

	/**
	 * Gets the current user's Role2 status.
	 * 
	 * @return {@code true} if this user has Role2, else {@code false}
	 */
	public boolean getCurrentNewRole2() { return currentNewRole2; }

	/**
	 * Debugging method that dumps the entire userDB table to the console.
	 * 
	 * @throws SQLException if there is an issue accessing the database metadata or records
	 */
	public void dump() throws SQLException {
		String query = "SELECT * FROM userDB";
		ResultSet resultSet = statement.executeQuery(query);
		ResultSetMetaData meta = resultSet.getMetaData();
		while (resultSet.next()) {
			for (int i = 0; i < meta.getColumnCount(); i++) {
				System.out.println(meta.getColumnLabel(i + 1) + ": " + resultSet.getString(i + 1));
			}
			System.out.println();
		}
		resultSet.close();
	}

	/**
	 * Closes the database statement and connection resources.
	 */
	public void closeConnection() {
		try { 
			if (statement != null) statement.close(); 
		} catch (SQLException se2) { 
			se2.printStackTrace();
		} 
		try { 
			if (connection != null) connection.close(); 
		} catch (SQLException se) { 
			se.printStackTrace(); 
		} 
	}
	
	/**
	 * Gets the cached temporary password of the current user.
	 * 
	 * @return the temporary password string, or {@code null} if none exists
	 */
	public String getTemporaryPassword() { 
		return tempPassword; 
	}
	
	/**
	 * Clears the temporary password column for the specified user.
	 * 
	 * @param username the username whose temporary password should be cleared
	 */
	public void removeTemporaryPassword(String username) { 
		String query = "UPDATE userDB SET tempPassword = null WHERE username = ?";
		try (PreparedStatement pstmt = connection.prepareStatement(query)) {
			pstmt.setString(1, username);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Updates the permanent password for the specified user.
	 * 
	 * @param username the username of the user
	 * @param password the new permanent password
	 */
	public void passwordChange(String username, String password) {
		String query = "UPDATE userDB SET password = ? WHERE username = ?";
		try (PreparedStatement pstmt = connection.prepareStatement(query)) {
			pstmt.setString(1, password);
			pstmt.setString(2, username);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Sets a temporary one-time password for the specified user.
	 * 
	 * @param username the username of the user
	 * @param newPassword the one-time temporary password to assign
	 */
	public void oneTimePasswordSet(String username, String newPassword) {
		String query = "UPDATE userDB SET tempPassword = ? WHERE username = ?";
		try (PreparedStatement pstmt = connection.prepareStatement(query)) {
			pstmt.setString(1, newPassword);
			pstmt.setString(2, username);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * <p> Method: createLessonsLearnedTable() </p>
	 * 
	 * <p> Description: Ensures the database table for LessonsLearned exists. </p>
	 */
	public void createLessonsLearnedTable() {
		String sql = "CREATE TABLE IF NOT EXISTS lessons_learned ("
				+ "lessonId INT PRIMARY KEY AUTO_INCREMENT, "
				+ "title VARCHAR(255) NOT NULL, "
				+ "category VARCHAR(255) NOT NULL, "
				+ "author VARCHAR(255) NOT NULL, "
				+ "problemDescription VARCHAR(4000) NOT NULL, "
				+ "solution VARCHAR(4000) NOT NULL, "
				+ "timeSpentHours DOUBLE DEFAULT 0.0, "
				+ "whatWasDone VARCHAR(4000) DEFAULT '', "
				+ "howItWasDone VARCHAR(4000) DEFAULT '', "
				+ "teamEffort VARCHAR(4000) DEFAULT '', "
				+ "isLocked BOOLEAN DEFAULT FALSE);";
		try (java.sql.Statement stmt = connection.createStatement()) {
			stmt.execute(sql);
		} catch (java.sql.SQLException e) {
			System.err.println("Error initializing lessons_learned table: " + e.getMessage());
		}
	}
	
	/**
	 * <p> Method: createLesson() </p>
	 * 
	 * <p> Description: Inserts a new lesson learned into the database. </p>
	 * 
	 * @param lesson - the LessonsLearned object to persist
	 * @return true if successful; false otherwise
	 */
	public boolean createLesson(entityClasses.LessonsLearned lesson) {
		createLessonsLearnedTable();
		String sql = "INSERT INTO lessons_learned(title, category, author, problemDescription, solution, timeSpentHours, whatWasDone, howItWasDone, teamEffort, isLocked) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try (java.sql.PreparedStatement pstmt = connection.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS)) {
			pstmt.setString(1, lesson.getTitle());
			pstmt.setString(2, lesson.getCategory());
			pstmt.setString(3, lesson.getAuthor());
			pstmt.setString(4, lesson.getProblemDescription());
			pstmt.setString(5, lesson.getSolution());
			pstmt.setDouble(6, lesson.getTimeSpentHours());
			pstmt.setString(7, lesson.getWhatWasDone());
			pstmt.setString(8, lesson.getHowItWasDone());
			pstmt.setString(9, lesson.getTeamEffort());
			pstmt.setBoolean(10, lesson.isLocked());
			int affected = pstmt.executeUpdate();
			if (affected > 0) {
				try (java.sql.ResultSet rs = pstmt.getGeneratedKeys()) {
					if (rs.next()) {
						lesson.setLessonId(rs.getInt(1));
					}
				}
				return true;
			}
		} catch (java.sql.SQLException e) {
			System.err.println("Database insert error: " + e.getMessage());
		}
		return false;
	}
	
	/**
	 * <p> Method: getLessonById() </p>
	 * 
	 * <p> Description: Retrieves a single lesson by ID. </p>
	 * 
	 * @param lessonId - the primary key identifier
	 * @return the LessonsLearned object, or null if not found
	 */
	public entityClasses.LessonsLearned getLessonById(int lessonId) {
		createLessonsLearnedTable();
		String sql = "SELECT * FROM lessons_learned WHERE lessonId = ?";
		try (java.sql.PreparedStatement pstmt = connection.prepareStatement(sql)) {
			pstmt.setInt(1, lessonId);
			try (java.sql.ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					return new entityClasses.LessonsLearned(
							rs.getInt("lessonId"),
							rs.getString("title"),
							rs.getString("category"),
							rs.getString("author"),
							rs.getString("problemDescription"),
							rs.getString("solution"),
							rs.getDouble("timeSpentHours"),
							rs.getString("whatWasDone"),
							rs.getString("howItWasDone"),
							rs.getString("teamEffort"),
							rs.getBoolean("isLocked")
					);
				}
			}
		} catch (java.sql.SQLException e) {
			System.err.println("Database lookup error: " + e.getMessage());
		}
		return null;
	}
	
	/**
	 * <p> Method: getAllLessonsForContributor() </p>
	 * 
	 * <p> Description: Retrieves all lessons authored by a given contributor. </p>
	 * 
	 * @param author - the author username
	 * @return a LessonsLearnedList of results
	 */
	public entityClasses.LessonsLearnedList getAllLessonsForContributor(String author) {
		createLessonsLearnedTable();
		entityClasses.LessonsLearnedList list = new entityClasses.LessonsLearnedList();
		String sql = "SELECT * FROM lessons_learned WHERE author = ?";
		try (java.sql.PreparedStatement pstmt = connection.prepareStatement(sql)) {
			pstmt.setString(1, author);
			try (java.sql.ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					list.addLesson(new entityClasses.LessonsLearned(
							rs.getInt("lessonId"),
							rs.getString("title"),
							rs.getString("category"),
							rs.getString("author"),
							rs.getString("problemDescription"),
							rs.getString("solution"),
							rs.getDouble("timeSpentHours"),
							rs.getString("whatWasDone"),
							rs.getString("howItWasDone"),
							rs.getString("teamEffort"),
							rs.getBoolean("isLocked")
					));
				}
			}
		} catch (java.sql.SQLException e) {
			System.err.println("Database query error: " + e.getMessage());
		}
		return list;
	}
	
	/**
	 * <p> Method: getLessonsByFilter() </p>
	 * 
	 * <p> Description: Searches/filters lessons matching a specific category. </p>
	 * 
	 * @param category - the category string filter
	 * @param author - the contributor's username
	 * @return a LessonsLearnedList of matching records
	 */
	public entityClasses.LessonsLearnedList getLessonsByFilter(String category, String author) {
		createLessonsLearnedTable();
		entityClasses.LessonsLearnedList list = new entityClasses.LessonsLearnedList();
		String sql = "SELECT * FROM lessons_learned WHERE category = ? AND author = ?";
		try (java.sql.PreparedStatement pstmt = connection.prepareStatement(sql)) {
			pstmt.setString(1, category);
			pstmt.setString(2, author);
			try (java.sql.ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					list.addLesson(new entityClasses.LessonsLearned(
							rs.getInt("lessonId"),
							rs.getString("title"),
							rs.getString("category"),
							rs.getString("author"),
							rs.getString("problemDescription"),
							rs.getString("solution"),
							rs.getDouble("timeSpentHours"),
							rs.getString("whatWasDone"),
							rs.getString("howItWasDone"),
							rs.getString("teamEffort"),
							rs.getBoolean("isLocked")
					));
				}
			}
		} catch (java.sql.SQLException e) {
			System.err.println("Database filter error: " + e.getMessage());
		}
		return list;
	}
	
	/**
	 * <p> Method: updateLesson() </p>
	 * 
	 * <p> Description: Updates core and experience information for an existing lesson. </p>
	 * 
	 * @param lesson - the updated LessonsLearned instance
	 * @return true if updated; false otherwise
	 */
	public boolean updateLesson(entityClasses.LessonsLearned lesson) {
		createLessonsLearnedTable();
		String sql = "UPDATE lessons_learned SET title = ?, category = ?, problemDescription = ?, "
				+ "solution = ?, timeSpentHours = ?, whatWasDone = ?, howItWasDone = ?, teamEffort = ? WHERE lessonId = ?";
		try (java.sql.PreparedStatement pstmt = connection.prepareStatement(sql)) {
			pstmt.setString(1, lesson.getTitle());
			pstmt.setString(2, lesson.getCategory());
			pstmt.setString(3, lesson.getProblemDescription());
			pstmt.setString(4, lesson.getSolution());
			pstmt.setDouble(5, lesson.getTimeSpentHours());
			pstmt.setString(6, lesson.getWhatWasDone());
			pstmt.setString(7, lesson.getHowItWasDone());
			pstmt.setString(8, lesson.getTeamEffort());
			pstmt.setInt(9, lesson.getLessonId());
			int rows = pstmt.executeUpdate();
			return rows > 0;
		} catch (java.sql.SQLException e) {
			System.err.println("Database full update error: " + e.getMessage());
		}
		return false;
	}
	
	/**
	 * <p> Method: deleteLesson() </p>
	 * 
	 * <p> Description: Permanently deletes a lesson from the database. </p>
	 * 
	 * @param lessonId - the ID of the lesson to delete
	 * @return true if record existed and was removed; false if record does not exist
	 */
	public boolean deleteLesson(int lessonId) {
		createLessonsLearnedTable();
		String sql = "DELETE FROM lessons_learned WHERE lessonId = ?";
		try (java.sql.PreparedStatement pstmt = connection.prepareStatement(sql)) {
			pstmt.setInt(1, lessonId);
			int rows = pstmt.executeUpdate();
			return rows > 0;
		} catch (java.sql.SQLException e) {
			System.err.println("Database delete error: " + e.getMessage());
		}
		return false;
	}
}