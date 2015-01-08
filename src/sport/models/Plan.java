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
	private long id;
	
	public long getId() {
		return id;
	}

	private final DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();;	
	
	public Plan(){
		
	}
	
	public Plan(String title, String description, String domain) {
		super();
		this.title = title;
		this.description = description;
		this.domain = domain;
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
	
	public void Save(){
		
		Entity plan = new Entity("plan");
		plan.setProperty("title", this.title);
		plan.setProperty("description", this.description );
		plan.setProperty("domain", this.domain);
		datastore.put(plan);
		this.id = plan.getKey().getId();
	}
	
}
