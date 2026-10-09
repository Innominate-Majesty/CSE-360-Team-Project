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
	private int id;
	private int lessonId;
	private String creatorName;
	private boolean locked;
	private String what;
	private String how;
	private String howLong;
	private String effort;
	private int index;
	private Experience prev;
	private Experience next;
	
	/*****
     * <p> Method: Experience() </p>
     * 
     * <p> Description: This default constructor is not used in this system. </p>
     */
    public Experience() {
    	
    }
    
    /*****
     * <p> Method: Experience(String creatorName, boolean locked, String what, String how, String
     * 		howLong, String effort, Experience next) </p>
     * 
     * <p> Description: This constructor is used to establish experience entity objects. </p>
     * 
     * @param id specifies the unique ID number of this experience
     * 
     * @param lessonId specifies the unique ID number of the lesson this experience is associated
     * with
     * 
     * @param creatorName specifies the creator of this experience
     * 
     * @param what specifies what was done with the experience
     * 
     * @param how specifies how the experience was done
     * 
     * @param howLong specifies how long the experience took
     * 
     * @param effort specifies the amount of effort used in the experience
     * 
     * @param index specifies the index of the experience in the list
     * 
     */
    public Experience(int id, int lessonId, String creatorName, String what, String how, String howLong,
    		String effort, int index) {
    	this.id = id;
    	this.lessonId = lessonId;
    	this.creatorName = creatorName;
    	this.locked = false;
    	this.what = what;
    	this.how = how;
    	this.howLong = howLong;
    	this.effort = effort;
    	this.index = index;
    	this.prev = null;
    	this.next = null;
    }
    
    /*****
     * <p> Method: int getId() </p>
     * 
     * <p> Description: This getter returns the experience's unique ID number. </p>
     * 
     * @return an int of the experience's ID
     */
    public int getId() { return id; }
    
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
     * <p> Method: String getCreatorName() </p>
     * 
     * <p> Description: This getter returns the name of the experience's creator. </p>
     * 
     * @return a String of the creator's name
     */
    public String getCreatorName() { return creatorName; }
    
    /*****
     * <p> Method: boolean getLockedStatus() </p>
     * 
     * <p> Description: This getter returns the value of the locked experience attribute. </p>
     * 
     * @return a String of "TRUE" or "FALSE" based on state of the attribute
	 *
     */
    public boolean getLockedStatus() { return locked; }
    
    /*****
     * <p> Method: String getWhat() </p>
     * 
     * <p> Description: This getter returns the what attribute. </p>
     * 
     * @return a String of the what attribute
     */
    public String getWhat() { return what; }
    
    /*****
     * <p> Method: String getHow() </p>
     * 
     * <p> Description: This getter returns the how attribute. </p>
     * 
     * @return a String of the how attribute
     */
    public String getHow() { return how; }
    
    /*****
     * <p> Method: String getHowLong() </p>
     * 
     * <p> Description: This getter returns the howLong attribute. </p>
     * 
     * @return a String of the howLong attribute
     */
    public String getHowLong() { return howLong; }
    
    /*****
     * <p> Method: String getEffort() </p>
     * 
     * <p> Description: This getter returns the effort attribute. </p>
     * 
     * @return a String of the effort attribute
     */
    public String getEffort() { return effort; }
    
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
     * <p> Method: void setLockedStatus(boolean newStat8us) </p>
     * 
     * <p> Description: This setter defines the locked experience attribute. </p>
     * 
     * @param newStatus is a boolean that specifies if this experience should be locked.
     * 
     */
    public void setLockedStatus(boolean newStatus) { locked = newStatus; }
    
    /*****
     * <p> Method: void setWhat(String newWhat) </p>
     * 
     * <p> Description: This setter sets the what attribute. </p>
     * 
     * @param newWhat is the input what attribute
     */
    public void setWhat(String newWhat) { what = newWhat; }
    
    /*****
     * <p> Method: void setHow(String newHow) </p>
     * 
     * <p> Description: This setter sets the how attribute. </p>
     * 
     * @param newHow is the input how attribute
     */
    public void setHow(String newHow) { how = newHow; }
    
    /*****
     * <p> Method: void setHowLong(String newHowLong) </p>
     * 
     * <p> Description: This setter sets the howLong attribute. </p>
     * 
     * @param newHowLong is the input howLong attribute
     */
    public void setHowLong(String newHowLong) { howLong = newHowLong; }
    
    /*****
     * <p> Method: void setEffort(String newEffort) </p>
     * 
     * <p> Description: This setter sets the effort attribute. </p>
     * 
     * @param newEffort is the input effort attribute
     */
    public void setEffort(String newEffort) { effort = newEffort; }
    
    /*****
     * <p> Method: void setPrev(Experience newPrev) </p>
     * 
     * <p> Description: This setter sets the previous experience. </p>
     * 
     * @param newPrev is the input previous experience
     */
    public void setPrev(Experience newPrev) { prev = newPrev; }
    
    /*****
     * <p> Method: void setNext(Experience newNext) </p>
     * 
     * <p> Description: This setter sets the next experience. </p>
     * 
     * @param newNext is the input next experience
     */
    public void setNext(Experience newNext) { next = newNext; }
}