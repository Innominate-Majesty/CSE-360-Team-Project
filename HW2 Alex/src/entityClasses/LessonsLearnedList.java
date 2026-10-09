package entityClasses;

import java.util.ArrayList;
import java.util.List;

/**
 * <p> Title: LessonsLearnedList Class. </p>
 * 
 * <p> Description: Entity class supporting storing all current lessons learned as well
 * as any subset of them (e.g., matching a category filter or contributor query). Supports
 * empty, single, and large collections of LessonsLearned instances. </p>
 * 
 * <p>Copyright: ASU Team 15 (Fall 2026)</p>
 * 
 * @author Alexander Murray
 * not yet @author Venus Ho
 * not yet @author Alan Shimp
 * not yet @author Caleb Beaven
 * not yet @author Krithik Duraisamy
 * 
 * @version 1.00	2026-10-03 Initial File
 */
public class LessonsLearnedList {
	
	//Variables
	private List<LessonsLearned> lessons;

	//Constructors
    /******
     * <p> Constructor: LessonsLearnedList() </p>
     * 
     * <p> Description: Default constructor initializing an empty list of lessons. </p>
     * 
     */
	public LessonsLearnedList(){
		this.lessons = new ArrayList<>();
	}
	
	/**
	 * <p> Constructor: LessonsLearnedList(List<LessonsLearned> lessons) </p>
	 * 
	 * <p> Description: Parameterized constructor wrapping an existing list of lessons. </p>
	 * 
	 * @param lessons the List of LessonsLearned entities to wrap
	 */
	public LessonsLearnedList(List<LessonsLearned> lessons) {
		if (lessons != null) {
			this.lessons = new ArrayList<>(lessons);
		} else {
			this.lessons = new ArrayList<>();
		}
	}
	
	//Methods
	/**
	 * <p> Method: addLesson() </p>
	 * 
	 * <p> Description: Adds a lesson to the collection. </p>
	 * 
	 * @param lesson - the LessonsLearned instance to add
	 */
	public void addLesson(LessonsLearned lesson) {
		if (lesson != null) {
			this.lessons.add(lesson);
		}
	}
	
	/**
	 * <p> Method: get() </p>
	 * 
	 * <p> Description: Retrieves the lesson at the specified index. </p>
	 * 
	 * @param index - the zero-based index of the lesson
	 * @return the LessonsLearned instance at that index, or null if index is out of bounds
	 */
	public LessonsLearned get(int index) {
		if (index >= 0 && index < lessons.size()) {
			return lessons.get(index);
		}
		return null;
	}

	/**
	 * <p> Method: size() </p>
	 * 
	 * <p> Description: Returns the number of lessons in this collection. </p>
	 * 
	 * @return the integer count of lessons
	 */
	public int size() {
		return lessons.size();
	}

	/**
	 * <p> Method: isEmpty() </p>
	 * 
	 * <p> Description: Checks whether the collection contains no elements. </p>
	 * 
	 * @return true if the collection is empty; false otherwise
	 */
	public boolean isEmpty() {
		return lessons.isEmpty();
	}

	/**
	 * <p> Method: clear() </p>
	 * 
	 * <p> Description: Clears all lessons from this collection. </p>
	 */
	public void clear() {
		lessons.clear();
	}

	/**
	 * <p> Method: getLessons() </p>
	 * 
	 * <p> Description: Retrieves the underlying List of lessons. </p>
	 * 
	 * @return the List of LessonsLearned entities
	 */
	public List<LessonsLearned> getLessons() {
		return lessons;
	}

	//Overrides
	/**
	 * <p> Method: toString() </p>
	 * 
	 * <p> Description: Returns a string summary of the collection contents. </p>
	 * 
	 * @return formatted string listing all elements in the collection
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Lessons List (Total: ").append(lessons.size()).append("):\n");
		for (LessonsLearned l : lessons) {
			sb.append("  - ").append(l.toString()).append("\n");
		}
		return sb.toString();
	}
}
