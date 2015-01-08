/**
 * 
 */
package sport.models;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;

/**
 * @author rhumblot
 * 
 * 
 */
public class Exercice {
	
	// Attributs
	private long idPlan;
	private String title;
	private String description;
	private int duration;
	
	private final DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();;
	
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
	
	public Exercice() {
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
	public long getIdPlan() {
		return idPlan;
	}
	public void setIdPlan(long idPlan) {
		this.idPlan = idPlan;
	}
	
	public void Save(){
		
		Entity exo = new Entity("Exercice");
		exo.setProperty("title", this.title);
		exo.setProperty("description", this.description );
		exo.setProperty("idPlan", this.idPlan);
		datastore.put(exo);
	}
	
}
