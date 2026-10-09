package entityClasses;
/**
 * <p> Title: LessonsLearned Class. </p>
 * 
 * <p> Description: Entity class representing an individual Lesson Learned. Contains
 * core information (title, category, author, problem description, solution) and
 * time data (timeSpentHours). Provides input validation.</p>
 * 
 * <p>Copyright: ASU Team 15 (Fall 2026)</p>
 * 
 * @author Alexander Murray
 * Not yet @author Venus Ho
 * Not yet @author Alan Shimp
 * Not yet @author Caleb Beaven
 * Not yet @author Krithik Duraisamy
 * 
 * @version 1.00	2026-10-03 Initial File
 */
public class LessonsLearned {
	
	//Variables
	private int lessonId;				//Unique Identifier
	private String title;				//Title of Lesson
	private String category;			//Category (e.g. "Math" or "History")
	private String author;				//Name of Author
	private String problemDescription;	//Description of Lesson
	private String solution;			//Lesson Solution
	private double timeSpentHours;		//Time Spent in Hours
	private String whatWasDone;			//Experience Info
	private String howItWasDone;		//Experience Set Up
	private String teamEffort;			//How Much Effort?
	private boolean titleLocked;
	private boolean categoryLocked;
	private boolean authorLocked;
	private boolean problemDescriptionLocked;
	private boolean solutionLocked;
	private boolean lessonLocked; 		//Boolean for Locking Lesson

	//Constructors
	
    /******
     * <p> Constructor: LessonsLearned() </p>
     * 
     * <p> Description: Default constructor initializing default values for a LessonLearned entity. </p>
     * 
     */
	public LessonsLearned() {
		this.lessonId = 0;
		this.title = "";
		this.category = "";
		this.author = "";
		this.problemDescription = "";
		this.solution = "";
		this.timeSpentHours = 0.0;
		this.whatWasDone = "";
		this.howItWasDone = "";
		this.teamEffort = "";
		this.titleLocked = false;
		this.categoryLocked = false;
		this.authorLocked = false;
		this.problemDescriptionLocked = false;
		this.solutionLocked = false;
		this.lessonLocked = false;
	}
	
	/**
	 * <p> Constructor: LessonsLearned(int lessonId, String title, String category, String author, 
	 *  String problemDescription, String solution, double timeSpentHours) </p>
	 * 
	 * <p> Description: Parameterized constructor initializing a LessonLearned with core information.</p>
	 * 
	 * @param lessonId - the unique integer identifier of the lesson
	 * @param title - the title of the lesson (must be between 5 and 100 characters)
	 * @param category - the category of the lesson (cannot be blank)
	 * @param author - the author/contributor username.
	 * @param problemDescription - the description of the problem (cannot be blank)
	 * @param solution - the description of the solution (cannot be blank)
	 * @param timeSpentHours - the numerical effort spent in hours (must be non-negative)
	 */
	public LessonsLearned(int lessonId, String title, String category, String author, 
			String problemDescription, String solution, double timeSpentHours) {
		validateFields(title, category, problemDescription, solution);
		validateTime(timeSpentHours);
		this.lessonId = lessonId;
		this.title = title;
		this.category = category;
		this.author = author;
		this.problemDescription = problemDescription;
		this.solution = solution;
		this.timeSpentHours = timeSpentHours;
		this.whatWasDone = "";
		this.howItWasDone = "";
		this.teamEffort = "";
		this.titleLocked = false;
		this.categoryLocked = false;
		this.authorLocked = false;
		this.problemDescriptionLocked = false;
		this.solutionLocked = false;
		this.lessonLocked = false;
	}
	
	/**
	 * <p> Constructor: LessonsLearned(int lessonId, String title, String category, String author, 
			String problemDescription, String solution, double timeSpentHours,
			String whatWasDone, String howItWasDone, String teamEffort, boolean isLocked)</p>
	 * 
	 * <p> Description: Convenience constructor for creating a new lesson prior to database ID assignment.</p>
	 * 
	 * @param title - the title of the lesson (must be between 5 and 100 characters)
	 * @param category - the category of the lesson (cannot be blank)
	 * @param author - the author/contributor username.
	 * @param problemDescription - the description of the problem (cannot be blank)
	 * @param solution - the description of the solution (cannot be blank)
	 * @param timeSpentHours - hours spent
	 * @param whatWasDone - what actions were performed
	 * @param howItWasDone - method used
	 * @param teamEffort - team member effort breakdown
	 * @param isLocked - overall lock status
	 */
	public LessonsLearned(int lessonId, String title, String category, String author, 
			String problemDescription, String solution, double timeSpentHours,
			String whatWasDone, String howItWasDone, String teamEffort, boolean isLocked) {
		this(lessonId, title, category, author, problemDescription, solution, timeSpentHours);
		this.whatWasDone = whatWasDone != null ? whatWasDone : "";
		this.howItWasDone = howItWasDone != null ? howItWasDone : "";
		this.teamEffort = teamEffort != null ? teamEffort : "";
		this.lessonLocked = isLocked;
	}
	
	//Validation Methods
	/**
	 * <p> Method: validateFields() </p>
	 * 
	 * <p> Description: Validates title length and ensures category, problem description, and solution are present.</p>
	 * 
	 * @param title - the title string to check
	 * @param category - the category string to check
	 * @param problemDescription - the problem description string to check
	 * @param solution - the solution string to check
	 * @throws IllegalArgumentException if validation constraints are violated
	 */
	public static void validateFields(String title, String category, String problemDescription, String solution) {
		if (title == null || title.length() < 5 || title.length() > 100) {
			throw new IllegalArgumentException("Title must be between 5 and 100 characters.");
		}
		if (category == null || category.trim().isEmpty() || 
			problemDescription == null || problemDescription.trim().isEmpty() || 
			solution == null || solution.trim().isEmpty()) {
			throw new IllegalArgumentException("Category, problem description, and solution cannot be blank.");
		}
	}
	
	/**
	 * <p> Method: validateTime() </p>
	 * 
	 * <p> Description: Validates that timeSpentHours is non-negative.</p>
	 * 
	 * @param time - the time value in hours
	 * @throws IllegalArgumentException if time is negative
	 */
	public static void validateTime(double time) {
		if (time < 0) {
			throw new IllegalArgumentException("Time spent must be a non-negative numeric value (hours).");
		}
	}
	
	/**
	 * <p> Method: validateId() </p>
	 * 
	 * <p> Description: Validates that a lesson ID is a positive integer. </p>
	 * 
	 * @param id - the lesson ID integer
	 * @throws IllegalArgumentException if id is less than or equal to 0
	 */
	public static void validateId(int id) {
		if (id <= 0) {
			throw new IllegalArgumentException("Lesson ID must be a positive integer.");
		}
	}
	
	//Getter & Setter Methods
	/**
	 * <p> Method: getLessonId() </p>
	 * 
	 * <p> Description: Gets the unique identifier of the lesson. </p>
	 * 
	 * @return the lessonId integer
	 */
	public int getLessonId() {
		return lessonId;
	}

	/**
	 * <p> Method: setLessonId() </p>
	 * 
	 * <p> Description: Sets the unique identifier of the lesson. </p>
	 * 
	 * @param lessonId - the lessonId integer to set
	 */
	public void setLessonId(int lessonId) {
		this.lessonId = lessonId;
	}

	/**
	 * <p> Method: getTitle() </p>
	 * 
	 * <p> Description: Gets the title of the lesson. </p>
	 * 
	 * @return the title string
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * <p> Method: setTitle() </p>
	 * 
	 * <p> Description: Sets the title of the lesson after checking length and empty constraints. </p>
	 * 
	 * @param title - the title string to set
	 * @throws IllegalArgumentException if title is empty or not between 5 and 100 characters
	 */
	public void setTitle(String title) {
		if (this.titleLocked) {
			throw new IllegalStateException("Title field is locked and cannot be modified.");
		}
		if (title == null || title.isEmpty()) {
			throw new IllegalArgumentException("Title cannot be empty. Must be between 5 and 100 characters.");
		}
		if (title.length() < 5 || title.length() > 100) {
			throw new IllegalArgumentException("Title must be between 5 and 100 characters.");
		}
		this.title = title;
	}
	/**
	 * <p> Method: getCategory() </p>
	 * 
	 * <p> Description: Gets the category of the lesson. </p>
	 * 
	 * @return the category string
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * <p> Method: setCategory() </p>
	 * 
	 * <p> Description: Sets the category of the lesson. </p>
	 * 
	 * @param category - the category string to set
	 */
	public void setCategory(String category) {
		if (this.categoryLocked) {
			throw new IllegalStateException("Category field is locked and cannot be modified.");
		}
		if (category == null || category.trim().isEmpty()) {
			throw new IllegalArgumentException("Category cannot be blank.");
		}
		this.category = category;
	}

	/**
	 * <p> Method: getAuthor() </p>
	 * 
	 * <p> Description: Gets the author username of the lesson. </p>
	 * 
	 * @return the author string
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * <p> Method: setAuthor() </p>
	 * 
	 * <p> Description: Sets the author username of the lesson. </p>
	 * 
	 * @param author - the author string to set
	 */
	public void setAuthor(String author) {
		if (this.authorLocked) {
			throw new IllegalStateException("Author field is locked and cannot be modified.");
		}
		this.author = author;
	}

	/**
	 * <p> Method: getProblemDescription() </p>
	 * 
	 * <p> Description: Gets the description of the problem encountered. </p>
	 * 
	 * @return the problem description string
	 */
	public String getProblemDescription() {
		return problemDescription;
	}

	/**
	 * <p> Method: setProblemDescription() </p>
	 * 
	 * <p> Description: Sets the description of the problem encountered. </p>
	 * 
	 * @param problemDescription - the problem description string to set
	 */
	public void setProblemDescription(String problemDescription) {
		if (this.problemDescriptionLocked) {
			throw new IllegalStateException("Problem Description field is locked and cannot be modified.");
		}
		if (problemDescription == null || problemDescription.trim().isEmpty()) {
			throw new IllegalArgumentException("Problem Description cannot be blank.");
		}
		this.problemDescription = problemDescription;
	}

	/**
	 * <p> Method: getSolution() </p>
	 * 
	 * <p> Description: Gets the description of the solution for the lesson. </p>
	 * 
	 * @return the solution string
	 */
	public String getSolution() {
		return solution;
	}

	/**
	 * <p> Method: setSolution() </p>
	 * 
	 * <p> Description: Sets the description of the solution for the lesson. </p>
	 * 
	 * @param solution - the solution string to set
	 */
	public void setSolution(String solution) {
		if (this.solutionLocked) {
			throw new IllegalStateException("Solution field is locked and cannot be modified.");
		}
		if (solution == null || solution.trim().isEmpty()) {
			throw new IllegalArgumentException("Solution cannot be blank.");
		}
		this.solution = solution;
	}

	/**
	 * <p> Method: getTimeSpentHours() </p>
	 * 
	 * <p> Description: Gets the numerical effort in hours spent on the lesson. </p>
	 * 
	 * @return the timeSpentHours value
	 */
	public double getTimeSpentHours() {
		return timeSpentHours;
	}

	/**
	 * <p> Method: setTimeSpentHours() </p>
	 * 
	 * <p> Description: Sets the numerical effort in hours spent on the lesson. </p>
	 * 
	 * @param timeSpentHours - the time value to set (must be non-negative)
	 * @throws IllegalArgumentException if timeSpentHours is negative
	 */
	public void setTimeSpentHours(double timeSpentHours) {
		validateTime(timeSpentHours);
		this.timeSpentHours = timeSpentHours;
	}
	
	/**
	 * <p> Method: getWhatWasDone() </p>
	 * 
	 * <p> Description: Gets the effort done to use the lesson. </p>
	 * 
	 *  @return the whatWasDone string
	 */
	public String getWhatWasDone() { 
		return whatWasDone; 
	}
	
	/**
	 * <p> Method: setWhatWasDone() </p>
	 * 
	 * <p> Description: Sets the effort done to use the lesson. </p>
	 * 
	 * @param whatWasDone - the string that describes the effort done to use the lesson.
	 */
	public void setWhatWasDone(String whatWasDone) { 
		this.whatWasDone = whatWasDone; 
	}
	
	/**
	 * <p> Method: getHowItWasDone() </p>
	 * 
	 * <p> Description: Gets the way the lesson was used. </p>
	 * 
	 * @return the howItWasDone string
	 */
	public String getHowItWasDone() { 
		return howItWasDone; 
	}
	
	/**
	 * <p> Method: setHowItWasDone() </p>
	 * 
	 * <p> Description: Sets the way the lesson was used. </p>
	 * 
	 * @param howItWasDone - the string that describes the way the lesson was used.
	 */
	public void setHowItWasDone(String howItWasDone) { this.howItWasDone = howItWasDone; }

	/**
	 * <p> Method: getTeamEffort() </p>
	 * 
	 * <p> Description: Gets the team effort specifics. </p>
	 * 
	 * @return the teamEffort string
	 */
	public String getTeamEffort() { 
		return teamEffort; 
	}
	
	/**
	 * <p> Method: setTeamEffort() </p>
	 * 
	 * <p> Description: Sets the effort done to use the lesson. </p>
	 * 
	 * @param teamEffort - the string that describes the team effort.
	 */
	public void setTeamEffort(String teamEffort) { 
		this.teamEffort = teamEffort; 
	}

	/**
	 * <p> Method: isLocked() </p>
	 * 
	 * <p> Description: Gets whether the lesson is locked. </p>
	 * 
	 * @return the isLocked boolean value
	 */
	public boolean isLocked() {
		return lessonLocked || titleLocked || categoryLocked || authorLocked || problemDescriptionLocked || solutionLocked;
	}
	
	/**
	 * <p> Method: setLocked() </p>
	 * 
	 * <p> Description: Sets the Locked status of the lesson. </p>
	 * 
	 * @param locked - the boolean that represents whether the lesson should be locked.
	 */
	public void setLocked(boolean locked) { 
		this.lessonLocked = locked; 
	}
	
	/**
	 * <p> Method: isTitleLocked() </p>
	 * 
	 * <p> Description: Gets whether the title is locked. </p>
	 * 
	 * @return the titleLocked boolean value
	 */
	public boolean isTitleLocked() { 
		return titleLocked; 
	}
	
	/**
	 * <p> Method: setTitleLocked() </p>
	 * 
	 * <p> Description: Sets the Locked status of the title. </p>
	 * 
	 * @param titleLocked - the boolean that represents whether the title should be locked.
	 */
	public void setTitleLocked(boolean titleLocked) { 
		this.titleLocked = titleLocked; 
	}

	/**
	 * <p> Method: isCategoryLocked() </p>
	 * 
	 * <p> Description: Gets whether the Category is locked. </p>
	 * 
	 * @return the categoryLocked boolean value
	 */
	public boolean isCategoryLocked() { 
		return categoryLocked; 
	}
	
	/**
	 * <p> Method: setCategoryLocked() </p>
	 * 
	 * <p> Description: Sets the Locked status of the Category. </p>
	 * 
	 * @param categoryLocked - the boolean that represents whether the Category should be locked.
	 */
	public void setCategoryLocked(boolean categoryLocked) { 
		this.categoryLocked = categoryLocked; 
	}

	/**
	 * <p> Method: isAuthorLocked() </p>
	 * 
	 * <p> Description: Gets whether the Author is locked. </p>
	 * 
	 * @return the authorLocked boolean value
	 */
	public boolean isAuthorLocked() { 
		return authorLocked; 
	}
	
	/**
	 * <p> Method: setAuthorLocked() </p>
	 * 
	 * <p> Description: Sets the Locked status of the Author. </p>
	 * 
	 * @param authorLocked - the boolean that represents whether the Author should be locked.
	 */
	public void setAuthorLocked(boolean authorLocked) { 
		this.authorLocked = authorLocked; 
	}

	/**
	 * <p> Method: isProblemDescriptionLocked() </p>
	 * 
	 * <p> Description: Gets whether the problem description is locked. </p>
	 * 
	 * @return the problemDescriptionLocked boolean value
	 */
	public boolean isProblemDescriptionLocked() { 
		return problemDescriptionLocked; 
	}
	
	/**
	 * <p> Method: setProblemDescriptionLocked() </p>
	 * 
	 * <p> Description: Sets the Locked status of the problem description. </p>
	 * 
	 * @param problemDescriptionLocked - the boolean that represents whether the problem description should be locked.
	 */
	public void setProblemDescriptionLocked(boolean problemDescriptionLocked) { 
		this.problemDescriptionLocked = problemDescriptionLocked; 
	}

	/**
	 * <p> Method: isSolutionLocked() </p>
	 * 
	 * <p> Description: Gets whether the solution is locked. </p>
	 * 
	 * @return the solutionLocked boolean value
	 */
	public boolean isSolutionLocked() { 
		return solutionLocked; 
	}
	
	/**
	 * <p> Method: setSolutionLocked() </p>
	 * 
	 * <p> Description: Sets the Locked status of the solution. </p>
	 * 
	 * @param solutionLocked - the boolean that represents whether the solution should be locked.
	 */
	public void setSolutionLocked(boolean solutionLocked) { 
		this.solutionLocked = solutionLocked; 
	}
	
	//Overrides
	/**
	 * <p> Method: toString() </p>
	 * 
	 * <p> Description: Returns a string representation of the lesson. </p>
	 * 
	 * @return a string containing lesson ID, locked status, title, category, author, and time spent.
	 */
	@Override
	public String toString() {
		String lockStatus = isLocked() ? " [LOCKED]" : " [EDITABLE]";
		return "Lesson #" + lessonId + lockStatus + ": [" + category + "] " + title + " (By: " + author + ")";
	}
}
