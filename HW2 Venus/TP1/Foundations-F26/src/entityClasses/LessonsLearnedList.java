package entityClasses;

import java.util.ArrayList;

import java.util.List;


/******
 * 
 * <p> Title: LessonsLearnedList Class </p>
 * 
 * <p> Description: This LessonsLearnedList class represents a collection of LessonsLearned obejcts in the system. </p>
 * 
 * <p> Copyright: ASU Team 15 (Fall 2026)</p>
 * 
 * @author Alexander Murray
 * @author Venus Ho
 * @author Alan Shimp
 * @author Caleb Beaven
 * @author Krithik Duraisamy
 * 
 */

public class LessonsLearnedList {

    // stores the lessons in a list
    private final List<LessonsLearned> lessons;


    /******
     * 
     * <p> Constructor: LessonsLearnedList() </p>
     * 
     * <p> Description: Constructs an empty LessonsLearnedList object. </p>
     * 
     */

	public LessonsLearnedList(){
		
        // create the lesson list
        this.lessons = new ArrayList<>();

	}

    /******
     * 
     * <p> Method: addLesson(LessonsLearned lesson) </p>
     *
     * <p> Description: Adds a lesson to the list. </p>
     *
     * @param lesson specifies the lesson to add
     *
     */

    public void addLesson(LessonsLearned lesson) {

        // adds the lesson to the list
        lessons.add(lesson);
    }

    /******
     * 
     * <p> Method: getLessons() </p>
     *
     * <p> Description: Returns the lessons in the list. </p>
     *
     * @return a copy of the lesson list
     *
     */

    public List<LessonsLearned> getLessons() {

        // returns a copy of the lesson list
        return new ArrayList<>(lessons);
    }

    /******
     * 
     * <p> Method: size() </p>
     *
     * <p> Description: Returns the number of lessons in the list. </p>
     *
     * @return the number of lessons
     *
     */

    public int size() {

        // returns the number of lessons
        return lessons.size();
    }

    /******
     * 
     * <p> Method: isEmpty() </p>
     *
     * <p> Description: Determines whether the lesson list is empty. </p>
     *
     * @return true when the list is empty; otherwise, false
     *
     */

    public boolean isEmpty() {

        // returns whether the list is empty
        return lessons.isEmpty();
    }


}
