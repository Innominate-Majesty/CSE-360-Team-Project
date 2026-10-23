package entityClasses;

import java.util.ArrayList;
import java.util.List;

/**
 * <p> Title: ExperiencesList Class. </p>
 * 
 * <p> Description: Entity class supporting storing all current experiences as well
 * as any subset of them (e.g., matching a category filter or contributor query). Supports
 * empty, single, and large collections of Experience instances. </p>
 * 
 * <p>Copyright: ASU Team 15 (Fall 2026)</p>
 * 
 * @author Alexander Murray
 * @author Venus Ho
 * @author Alan Shimp
 * @author Caleb Beaven
 * @author Krithik Duraisamy
 * 
 * @version 1.00	2026-10-22 Initial File
 */
public class ExperiencesList {
	
	//Variables
	private List<Experience> experiences;

	//Constructors
    /******
     * <p> Constructor: ExperinecesList() </p>
     * 
     * <p> Description: Default constructor initializing an empty list of experiences. </p>
     * 
     */
	public ExperiencesList(){
		this.experiences = new ArrayList<>();
	}
	
	/**
	 * <p> Constructor: ExperiencesList(List{@code<Experiences>} experiencess) </p>
	 * 
	 * <p> Description: Parameterized constructor wrapping an existing list of experiences. </p>
	 * 
	 * @param experiences the List of Experience entities to wrap
	 */
	public LessonsLearnedList(List<Experience> experiences) {
		if (experiences != null) {
			this.experiences = new ArrayList<>(experiences);
		} else {
			this.experiences = new ArrayList<>();
		}
	}
	
	//Methods
	/**
	 * <p> Method: addExperience() </p>
	 * 
	 * <p> Description: Adds an experience to the collection. </p>
	 * 
	 * @param experience - the Experience instance to add
	 */
	public void addExperience(Experience experience) {
		if (experience != null) {
			this.experiences.add(experience);
		}
	}
	
	/**
	 * <p> Method: get() </p>
	 * 
	 * <p> Description: Retrieves the experience at the specified index. </p>
	 * 
	 * @param index - the zero-based index of the experience
	 * @return the Experience instance at that index, or null if index is out of bounds
	 */
	public Experience get(int index) {
		if (index >= 0 && index < experiences.size()) {
			return experiences.get(index);
		}
		return null;
	}

	/**
	 * <p> Method: size() </p>
	 * 
	 * <p> Description: Returns the number of experiences in this collection. </p>
	 * 
	 * @return the integer count of experiences
	 */
	public int size() {
		return experiences.size();
	}

	/**
	 * <p> Method: isEmpty() </p>
	 * 
	 * <p> Description: Checks whether the collection contains no elements. </p>
	 * 
	 * @return true if the collection is empty; false otherwise
	 */
	public boolean isEmpty() {
		return experiences.isEmpty();
	}

	/**
	 * <p> Method: clear() </p>
	 * 
	 * <p> Description: Clears all experiences from this collection. </p>
	 */
	public void clear() {
		experiences.clear();
	}

	/**
	 * <p> Method: getExperiences() </p>
	 * 
	 * <p> Description: Retrieves the underlying List of experiences. </p>
	 * 
	 * @return the List of Experience entities
	 */
	public List<Experience> getExperiences() {
		return experiences;
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
		sb.append("Experiences List (Total: ").append(experiences.size()).append("):\n");
		for (Experience l : experiences) {
			sb.append("  - ").append(l.toString()).append("\n");
		}
		return sb.toString();
	}
}