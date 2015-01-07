/**
 * 
 */
package sport.models;

import java.util.List;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;

/**
 * @author rhumblot
 *
 */
public class Plan {

	private String title;
	private String description;
	private String domain;
	private List<Exercice> exos;
	
	private final DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();;	
	
	public Plan(String title, String description, String domain, List<Exercice> exos) {
		super();
		this.title = title;
		this.description = description;
		this.domain = domain;
		this.exos = exos;
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
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public List<Exercice> getExos() {
		return exos;
	}
	public void setExos(List<Exercice> exos) {
		this.exos = exos;
	}
	
	public void addExercice(Exercice e){
		this.exos.add(e);
	}
	
	
	public void SavePlan(){
		
		Entity plan = new Entity("Plan");
		plan.setProperty(title.toString(), this.title);
		plan.setProperty(description.toString(), this.description );
		plan.setProperty(domain.toString(), this.domain);
		plan.setProperty("exos", this.exos);
		
		datastore.put(plan);
	}
	
}
