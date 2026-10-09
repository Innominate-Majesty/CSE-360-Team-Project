package entityClasses;

/*******
 * <p> Title: LessonsLearnedList Class </p>
 * 
 * <p> Description: This LessonsLearnedList class represents a doubly linked list of lessons
 * learned in the system.. </p>
 * 
 * <p> Copyright: Alan G. Shimp © 2026 </p>
 * 
 * @author Alan G. Shimp
 * 
 * 
 */

public class LessonsLearnedList {
	
	/*
	 * These are the private attributes for this entity object
	 */
	private int listSize = 0;
	private LessonLearned listTail = null;
	private LessonLearned listHead = null;
	
	/*****
     * <p> Method: LessonLearned() </p>
     * 
     * <p> Description: This default constructor initializes the size of the list to 0, the head to
     * null, and the tail to null. </p>
     */
    public LessonsLearnedList() {
    	listSize = 0;
    	listTail = null;
    	listHead = null;
    }
    
    /*****
     * <p> Method: int getSize() </p>
     * 
     * <p> Description: This getter returns the number of lessons learned in the list. </p>
     * 
     * @return an int of the listSize attribute
     */
    public int getSize() { return listSize; }
    
    /*****
     * <p> Method: LessonLearned getLesson(int index) </p>
     * 
     * <p> Description: This getter returns the lesson learned object at a given index in the
     * linked list. If the index is not valid, it returns null. This error should be handled by the
     * caller. </p>
     * 
     * @param index is the index to retrieve from
     * 
     * @return a Lesson Learned object at the given index or null if none
     */
    public LessonLearned getLesson(int index) {
    	if (index >= listSize) return null;
    	else {
    		LessonLearned curr = listHead;
    		
    		while (curr.getIndex() != index) curr = curr.getNext();
    		
    		return curr;
    	}
    }
    
    /*****
     * <p> Method: void addLesson(Lesson toAdd) </p>
     * 
     * <p> Description: Adds a new LessonLearned object to the list. </p>
     * 
     * @param toAdd is the LessonLearned to add
     */
    public void addLesson(LessonLearned toAdd) {
    	toAdd.setPrev(listTail);
    	
    	if (listHead == null) listHead = toAdd;
    	
    	if (listTail != null) listTail.setNext(toAdd);
    	
    	listTail = toAdd;
    	
    	listSize++;
    }
    
    /*****
     * <p> Method: void deleteLesson(int index) </p>
     * 
     * <p> Description: Given a valid index, deletes a lesson learned from the list </p>
     * 
     * @param index is the index in the list to delete
     */
    public void deleteLesson(int index) {
    	if (index < listSize) {
    		LessonLearned curr = listHead;
    		
    		while (curr.getIndex() != index) curr = curr.getNext();
    		
    		LessonLearned prevLesson = curr.getPrev();
    		LessonLearned nextLesson = curr.getNext();
    		
    		if (index == 0) listHead = nextLesson;
    		else prevLesson.setNext(nextLesson);
    		
    		if (index == listSize - 1) listTail = prevLesson;
    		else nextLesson.setPrev(prevLesson);
    		
    		listSize--;
    	}
    }
}