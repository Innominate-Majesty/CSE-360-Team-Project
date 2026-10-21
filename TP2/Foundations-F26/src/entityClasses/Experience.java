package entityClasses;

/*******
 * <p> Title: Experience Class </p>
 * 
 * <p> Description: This Experience class represents an experience entity in the system.  It is a helper
 * for the lessonLearned class </p>
 * 
 * <p> Copyright: Alan G. Shimp © 2026 </p>
 * 
 * @author Alan G. Shimp
 * 
 * 
 */

public class Experience {
	
	/*
	 * These are the private attributes for this entity object
	 */
	private int experienceId;			// Unique identifier
	private int lessonId;				// Lesson this experience is connected to
	private String author;				// Name of Author
	private double timeSpentHours;		// Time Spent in Hours
	private String whatWasDone;			// Experience Info
	private String howItWasDone;		// Experience Set Up
	private String teamEffort;			// How Much Effort?
	private int index;					// Index in the list of experiences for this lesson
	private Experience prev;			// Previous experience in the list of experiences for this
										// lesson
	private Experience next;			// Next experience in the list of experiences for this
										// lesson
	private boolean[] locked = {false, false, false, false};	// Tracks the locked status of
															//timeSpentHours, whatWasDone,
															//howItWasDone, and teamEffort as an
															//array of booleans
	
	/*****
     * <p> Method: Experience() </p>
     * 
     * <p> Description: This default constructor is not used in this system. </p>
     */
    public Experience() {
    	experienceId = 0;
		lessonId = 0;
    	author = "";
    	timeSpentHours = 0.0;
    	whatWasDone = "";
    	howItWasDone = "";
    	teamEffort = "";
    	index = -1;
    	prev = null;
    	next = null;
    	locked[0] = false;
    	locked[1] = false;
    	locked[2] = false;
    	locked[3] = false;
    }
	
	/*****
	 * <p> Method: Experience(int experienceId, int lessonId, String author, double timeSpentHours,
	 * String whatWasDone, String howItWasDone, String teamEffort) </p>
	 * 
	 * <p> Description: This constructor is used to establish experience entity objects. </p>
	 * 
	 * @param experienceId specifies the unique ID number of this experience
	 * 
	 * @param lessonId specifies the unique ID number of the lesson this experience is associated
	 * with
	 * 
	 * @param author specifies the creator of this experience
	 * 
	 * @param timeSpentHours specifies how long the experience took
	 * 
	 * @param whatWasDone specifies the user's explanation of what was done in the experience
	 * 
	 * @param howItWasDone specifies the user's explanation of how the experience was completed
	 * 
	 * @param teamEffort specifies the user's explanation of the effort the team expended
	 * 
	 */
	public Experience(int experienceId, int lessonId, String author, double timeSpentHours,
			String whatWasDone, String howItWasDone, String teamEffort) {
		this.experienceId = experienceId;
		this.lessonId = lessonId;
		this.author = author;
		this.timeSpentHours = timeSpentHours;
		this.whatWasDone = whatWasDone;
		this.howItWasDone = howItWasDone;
		this.teamEffort = teamEffort;
		index = -1;
		prev = null;
		next = null;
    	locked[0] = false;
    	locked[1] = false;
    	locked[2] = false;
    	locked[3] = false;
	}
	
	/*****
     * <p> Method: int getExperienceId() </p>
     * 
     * <p> Description: This getter returns the experience's unique ID number. </p>
     * 
     * @return an int of the experience's ID
     */
    public int getExperienceId() { return experienceId; }
    
    /*****
     * <p> Method: int getLessonId() </p?
     * 
     * <p> Description: This getter returns the unique ID number of the lesson the experience is
     * associated with. </p>
     * 
     * @return an int of the associated lesson's ID
     */
    public int getLessonId() { return lessonId; }
    
    /*****
     * <p> Method: String getAuthor() </p>
     * 
     * <p> Description: This getter returns the name of the experience's creator. </p>
     * 
     * @return a String of the creator's name
     */
    public String getAuthor() { return author; }
    
    /*****
     * <p> Method: double getTime() </p>
     * 
     * <p> Description: This getter returns the time spent on the experience. </p>
     * 
     * @return a double of the hours spent on the experience
     */
    public double getTime() { return timeSpentHours; }
    
    /*****
     * <p> Method: String getWhat() </p>
     * 
     * <p> Description: This getter returns the whatWasDone attribute. </p>
     * 
     * @return a String of the whatWasDone attribute
     */
    public String getWhat() { return whatWasDone; }
    
    /*****
     * <p> Method: String getHow() </p>
     * 
     * <p> Description: This getter returns the howItWasDone attribute. </p>
     * 
     * @return a String of the howItWasDone attribute
     */
    public String getHow() { return howItWasDone; }
    
    /*****
     * <p> Method: String getEffort() </p>
     * 
     * <p> Description: This getter returns the teamEffort attribute. </p>
     * 
     * @return a String of the teamEffort attribute
     */
    public String getEffort() { return teamEffort; }
    
    /*****
     * <p> Method: int getIndex() </p>
     * 
     * <p> Description: This getter returns the index. </p>
     * 
     * @return an int of the experience's index
     */
    public int getIndex() { return index; }
    
    /*****
     * <p> Method: Experience getPrev() </p>
     * 
     * <p> Description: This getter returns the previous experience. </p>
     * 
     * @return an Experience that's previous in the linked list
     */
    public Experience getPrev() { return prev; }
    
    /*****
     * <p> Method: Experience getNext() </p>
     * 
     * <p> Description: This getter returns the next experience. </p>
     * 
     * @return an Experience that's next in the linked list
     */
    public Experience getNext() { return next; }
    
    /*****
     * <p> Method: boolean getExpLocked() </p>
     * 
     * <p> Description: This getter returns the locked status of the experience. </p>
     * 
     * @return a boolean true if the experience is locked or false if not
     */
    public boolean getExpLocked() {
    	return ((locked[0] && locked[1]) && (locked[2] && locked[3]));
    }
    
    /*****
     * <p> Method: boolean getTimeLocked() </p>
     * 
     * <p> Description: This getter returns the locked status of the time spent. </p>
     * 
     * @return a boolean true if the time spent is locked or false if not
     */
    public boolean getTimeLocked() { return locked[0]; }
    
    /*****
     * <p> Method: boolean getWhatLocked() </p>
     * 
     * <p> Description: This getter returns the locked status of the whatWasDone attribute. </p>
     * 
     * @return a boolean true if the whatWasDone attribute is locked or false if not
     */
    public boolean getWhatLocked() { return locked[1]; }
    
    /*****
     * <p> Method: boolean getHowLocked() </p>
     * 
     * <p> Description: This getter returns the locked status of the howItWasDone attribute. </p>
     * 
     * @return a boolean true if the howItWasDone attribute is locked or false if not
     */
    public boolean getHowLocked() { return locked[2]; }
    
    /*****
     * <p> Method: boolean getEffortLocked() </p>
     * 
     * <p> Description: This getter returns the locked status of the teamEffort attribute. </p>
     * 
     * @return a boolean true if the teamEffort attribute is locked or false if not
     */
    public boolean getEffortLocked() { return locked[3]; }
    
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
	 * <p> Method: setTimeSpentHours() </p>
	 * 
	 * <p> Description: Sets the numerical effort in hours spent on the lesson and returns true if
	 * unlocked. Does nothing and returns false if locked. </p>
	 * 
	 * @param timeSpentHours - the time value to set (must be non-negative)
	 * @throws IllegalArgumentException if timeSpentHours is negative
	 */
	public boolean setTimeSpentHours(double timeSpentHours) {
		validateTime(timeSpentHours);
		if (locked[0]) {
			return false;
		}
		else {
			this.timeSpentHours = timeSpentHours;
			return true;
		}
	}
	
	/**
	 * <p> Method: setWhatWasDone() </p>
	 * 
	 * <p> Description: Sets the effort done to use the lesson and returns true if unlocked. Does
	 * nothing and returns false if locked. </p>
	 * 
	 * @param whatWasDone - the string that describes the effort done to use the lesson.
	 */
	public boolean setWhatWasDone(String whatWasDone) { 
		if (locked[1]) {
			return false;
		}
		else {
			this.whatWasDone = whatWasDone;
			return true;
		}
	}
	
	/**
	 * <p> Method: setHowItWasDone() </p>
	 * 
	 * <p> Description: Sets the way the lesson was used and returns true if unlocked. Does nothing
	 * and returns false if locked. </p>
	 * 
	 * @param howItWasDone - the string that describes the way the lesson was used.
	 */
	public boolean setHowItWasDone(String howItWasDone) {
		if (locked[2]) {
			return false;
		}
		else {
			this.howItWasDone = howItWasDone;
			return true;
		}
	}
	
	/**
	 * <p> Method: setTeamEffort() </p>
	 * 
	 * <p> Description: Sets the effort done to use the lesson and returns true if unlocked. Does
	 * nothing and returns false if locked. </p>
	 * 
	 * @param teamEffort - the string that describes the team effort.
	 */
	public boolean setTeamEffort(String teamEffort) { 
		if (locked[3]) {
			return false;
		}
		else {
			this.teamEffort = teamEffort;
			return true;
		}
	}
	
	/*****
	 * <p> Method: void setIndex(int newIndex) </p>
	 * 
	 * <p> Description: Changes the index of the experience in the list. Should only be called if
	 * this experience is being added or one before it in the list is being deleted, so it is
	 * protected and should only be called by the LessonsLearned class. </p>
	 * 
	 * @param newIndex is the new index of the experience.
	 * 
	 */
	protected void setIndex(int newIndex) { index = newIndex; }
	
	/*****
	 * <p> Method: void setPrev(Experience newPrev) </p>
	 * 
	 * <p> Description: Changes the previous experience in the list. Called under the same
	 * circumstances as setIndex. </p>
	 * 
	 * @param newPrev is the new previous experience.
	 * 
	 */
	protected void setPrev(Experience newPrev) { prev = newPrev; }
	
	/*****
	 * <p> Method: void setNext(Experience newNext) </p>
	 * 
	 * <p> Description: Changes the nextexperience in the list. Called under the same
	 * circumstances as setIndex. </p>
	 * 
	 * @param newNext is the new next experience.
	 * 
	 */
	protected void setNext(Experience newNext) { prev = newNext; }
	
	/*****
	 * <p> Method: void lockUnlockExperience() </p>
	 * 
	 * <p> Description: Quick toggles the locked status of the whole experience. </p>
	 * 
	 */
	public void lockUnlockExperience() {
		if ((locked[0] && locked[1]) && (locked[2] && locked[3])) {
			locked[0] = false;
			locked[1] = false;
			locked[2] = false;
			locked[3] = false;
		}
		else {
			locked[0] = true;
			locked[1] = true;
			locked[2] = true;
			locked[3] = true;
		}
	}
	
	/*****
	 * <p> Method: void lockUnlockTime(boolean newStatus) </p>
	 * 
	 * <p> Description: Sets the locked status of the time spent. </p>
	 * 
	 * @param newStatus is the new locked status of the time.
	 * 
	 */
	public void lockUnlockTime(boolean newStatus) { locked[0] = newStatus; }
	
	/*****
	 * <p> Method: void lockUnlockWhat(boolean newStatus) </p>
	 * 
	 * <p> Description: Sets the locked status of what was done. </p>
	 * 
	 * @param newStatus is the new locked status of the whatWasDone attribute.
	 * 
	 */
	public void lockUnlockWhat(boolean newStatus) { locked[1] = newStatus; }
	
	/*****
	 * <p> Method: void lockUnlockHow(boolean newStatus) </p>
	 * 
	 * <p> Description: Sets the locked status of how the experience was done. </p>
	 * 
	 * @param newStatus is the new locked status of the howItWasDone attribute.
	 * 
	 */
	public void lockUnlockHow(boolean newStatus) { locked[2] = newStatus; }
	
	/*****
	 * <p> Method: void lockUnlockEffort(boolean newStatus) </p>
	 * 
	 * <p> Description: Sets the locked status of the team effort. </p>
	 * 
	 * @param newStatus is the new locked status of the team effort.
	 * 
	 */
	public void lockUnlockEffort(boolean newStatus) { locked[3] = newStatus; }
}