/**
 * 
 */
package sport.models;

/**
 * @author rhumblot
 * 
 * 
 */
public class Exercice {
	
	// Attributs
	private String title;
	private String description;
	private int duration;
	
	
	/**
	 * Constructor
	 * @param title 
	 * @param description
	 * @param duration
	 */
	public Exercice(String title, String description, int duration) {
		super();
		this.title = title;
		this.description = description;
		this.duration = duration;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	
	
}
