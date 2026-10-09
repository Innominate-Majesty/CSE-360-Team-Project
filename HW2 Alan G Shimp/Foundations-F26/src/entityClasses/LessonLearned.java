package entityClasses;

/*******
 * <p> Title: LessonLearned Class </p>
 * 
 * <p> Description: This LessonLearned class represents a lesson learned entity in the system.
 * Since there is no firm cap on the amount of experience data that can be added, the class
 * includes a doubly linked list of a separate Experience class. </p>
 * 
 * <p> Copyright: Alan G. Shimp © 2026 </p>
 * 
 * @author Alan G. Shimp
 * 
 * 
 */

public class LessonLearned {
	
	/*
	 * These are the private attributes for this entity object
	 */
	private int id;
	private String title;
	private String creatorName;
	private boolean locked;
	private String coreInfo;
	private int experienceCount;
	private Experience experienceHead;
	private Experience experienceTail;
	private int index;
	private LessonLearned prev;
	private LessonLearned next;
	
	/*****
     * <p> Method: LessonLearned() </p>
     * 
     * <p> Description: This default constructor is not used in this system. </p>
     */
    public LessonLearned() {
    	
    }
    
    /*****
     * <p> Method: LessonLearned(String title, String creatorName, boolean locked, String coreInfo,
     * 		int index) </p>
     * 
     * <p> Description: This constructor is used to establish lesson learned entity objects. </p>
     * 
     * @param title specifies the title of this lesson learned
     * 
     * @param creatorName specifies the creator of this lesson learned
     * 
     * @param coreInfo specifies the core information for this lesson learned
     * 
     * @param index specifies the index of the lesson learned in the list
     * 
     */
    public LessonLearned(String title, String creatorName, String coreInfo, int index) {
    	// ID is -1 until the lesson is added to the database.
    	this.id = -1;
    	this.title = title;
    	this.creatorName = creatorName;
    	this.locked = false;
    	this.coreInfo = coreInfo;
    	this.experienceCount = 0;
    	this.experienceHead = null;
    	this.experienceTail = null;
    	this.index = index;
    	this.prev = null;
    	this.next = null;
    }
    
    /*****
     * <p> Method: int getId() </p>
     * 
     * <p> Description: This getter returns the unique ID number of the lesson learned. </p>
     * 
     * @return an int of the ID number
     */
    public int getId() { return id; }
    
    /*****
     * <p> Method: String getTitle() </p>
     * 
     * <p> Description: This getter returns the title of the lesson learned. </p>
     * 
     * @return a String of the title
     */
    public String getTitle() { return title; }
    
    /*****
     * <p> Method: String getCreatorName() </p>
     * 
     * <p> Description: This getter returns the name of the lesson's creator. </p>
     * 
     * @return a String of the creator's name
     */
    public String getCreatorName() { return creatorName; }
    
    /*****
     * <p> Method: boolean getLockedStatus() </p>
     * 
     * <p> Description: This getter returns the value of the locked lesson learned attribute. </p>
     * 
     * @return a String of "TRUE" or "FALSE" based on state of the attribute
	 *
     */
    public boolean getLockedStatus() { return locked; }
    
    /*****
     * <p> Method: String getCoreInfo() </p>
     * 
     * <p> Description: This getter returns the coreInfo attribute. </p>
     * 
     * @return a String of the coreInfo attribute
     */
    public String getCoreInfo() { return coreInfo; }
    
    /*****
     * <p> Method: int getCount() </p>
     * 
     * <p> Description: This getter returns the experienceCount attribute. </p>
     * 
     * @return an int of the experienceCount attribute
     */
    public int getCount() { return experienceCount; }
    
    /*****
     * <p> Method: Experience getExperience(int index) </p>
     * 
     * <p> Description: This getter returns the experience object at a given index in the linked
     * list. If the index is not valid, it returns null. This error should be handled by the
     * caller. </p>
     * 
     * @param index is the index to retrieve from
     * 
     * @return an Experience object at the given index or null if none
     */
    public Experience getExperience(int index) {
    	if (index >= experienceCount) return null;
    	else if (index <= 0) return null;
    	else {
    		Experience curr = experienceHead;
    		
    		while (curr.getIndex() != index) curr = curr.getNext();
    		
    		return curr;
    	}
    }
    
    /*****
     * <p> Method: int getIndex() </p>
     * 
     * <p> Description: This getter returns the index. </p>
     * 
     * @return an int of the lesson's index
     */
    public int getIndex() { return index; }
    
    /*****
     * <p> Method: LessonLearned getPrev() </p>
     * 
     * <p> Description: This getter returns the previous lesson learned. </p>
     * 
     * @return a LessonLearned that's previous in the linked list
     */
    public LessonLearned getPrev() { return prev; }
    
    /*****
     * <p> Method: LessonLearned getNext() </p>
     * 
     * <p> Description: This getter returns the next lesson learned. </p>
     * 
     * @return a LessonLearned that's next in the linked list
     */
    public LessonLearned getNext() { return next; }
    
    /*****
     * <p> Method: void setId(int id) </p>
     * 
     * <p> Description: This setter sets the id attribute. It should only be used to update the ID
     * number to the correct number from the database after storing the lesson. </p>
     * 
     * @param id is the input id attribute.
     */
    public void setId(int id) { this.id = id; }
    
    /*****
     * <p> Method: void setTitle(String newTitle) </p>
     * 
     * <p> Description: This setter sets the title attribute. </p>
     * 
     * @param newTitle is the input title attribute
     */
    public void setTitle(String newTitle) { title = newTitle; }
    
    /*****
     * <p> Method: void setLockedStatus(boolean newStatus) </p>
     * 
     * <p> Description: This setter sets the locked attribute. </p>
     * 
     * @param newStatus is the input locked attribute
     */
    public void setLockedStatus(boolean newStatus) { locked = newStatus; }
    
    /*****
     * <p> Method: void setCoreInfo(String newInfo) </p>
     * 
     * <p> Description: This setter sets the coreInfo attribute. </p>
     * 
     * @param newInfo is the input coreInfo attribute
     */
    public void setCoreInfo(String newInfo) { coreInfo = newInfo; }
    
    /*****
     * <p> Method: void addExperience(Experience toAdd) </p>
     * 
     * <p> Description: Adds a new experience object to the list. </p>
     * 
     * @param toAdd is the Experience to add
     */
    public void addExperience(Experience toAdd) {
    	toAdd.setPrev(experienceTail);
    	
    	if (experienceHead == null) experienceHead = toAdd;
    	
    	if (experienceTail != null) experienceTail.setNext(toAdd);
    	
    	experienceTail = toAdd;
    	
    	experienceCount++;
    }
    
    /*****
     * <p> Method: void deleteExperience(int index) </p>
     * 
     * <p> Description: Given a valid index, deletes an experience from the list </p>
     * 
     * @param index is the index in the list to delete
     */
    public void deleteExperience(int index) {
    	if (index < experienceCount) {
    		Experience curr = experienceHead;
    		
    		while (curr.getIndex() != index) curr = curr.getNext();
    		
    		Experience prevExp = curr.getPrev();
    		Experience nextExp = curr.getNext();
    		
    		if (index == 0) experienceHead = nextExp;
    		else prevExp.setNext(nextExp);
    		
    		if (index == experienceCount - 1) experienceTail = prevExp;
    		else nextExp.setPrev(prevExp);
    		
    		experienceCount--;
    	}
    }
    
    /*****
     * <p> Method: void setPrev(LessonLearned newPrev) </p>
     * 
     * <p> Description: This setter sets the previous lesson learned. </p>
     * 
     * @param newPrev is the input previous lesson learned
     */
    public void setPrev(LessonLearned newPrev) { prev = newPrev; }
    
    /*****
     * <p> Method: void setNext(LessonLearned newNext) </p>
     * 
     * <p> Description: This setter sets the next lesson learned. </p>
     * 
     * @param newNext is the input next lesson learned
     */
    public void setNext(LessonLearned newNext) { next = newNext; }
}