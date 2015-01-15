/**
 * 
 */
package sport.models;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;

/**
 * @author rhumblot
 * Enregistrement des informations du User
 */
public class User {
	
	// Id User (username)
	private long id;
	// Id Titre exo
	private long idTitle;
	// Date de l'exercice
	private String date;
	// Id du Plan
	private long idPlan;
	// Durée du plan d'exercice
	private String duration;
	// Statut sur l'exercice
	private String statut;
	
	private final DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public String getStatut() {
		return statut;
	}
	public void setStatut(String statut) {
		this.statut = statut;
	}
	public long getIdPlan() {
		return idPlan;
	}
	public void setIdPlan(long idPlan) {
		this.idPlan = idPlan;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getIdTitle() {
		return idTitle;
	}
	public void setIdTitle(long idTitle) {
		this.idTitle = idTitle;
	}
	
	public void Save(){
		Entity user = new Entity("user");
		user.setProperty("date", this.date);
		user.setProperty("idPlan", this.idPlan);
		user.setProperty("duration", this.duration);
		user.setProperty("statut", this.statut);
		user.setProperty("id", this.id);
		user.setProperty("idTitle", this.idTitle);
		datastore.put(user);
	}

}
