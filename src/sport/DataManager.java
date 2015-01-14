package sport;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.labs.repackaged.org.json.JSONException;

public class DataManager extends HttpServlet{

	private JSONConverter jC;


	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException{

		resetDataStore();

	}



	private void resetDataStore() {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
				Entity accueil = new Entity("accueil");
				accueil.setProperty("message","Personal Trainer, votre coach personel vous souhaite la bienvenue !");
				datastore.put(accueil);

				Entity sport1 = new Entity("sport");
				sport1.setProperty("nom","Run");
				datastore.put(sport1);
		
				Entity sport2 = new Entity("sport");
				sport2.setProperty("nom","Firness");
				datastore.put(sport2);
				
				Entity sport3 = new Entity("sport");
				sport3.setProperty("nom","Swimming");
				datastore.put(sport3);
				
				Entity sport4 = new Entity("sport");
				sport4.setProperty("nom","Tennis");
				datastore.put(sport4);
				
				Entity sport5 = new Entity("sport");
				sport5.setProperty("nom","Box");
				datastore.put(sport5);
				
				Entity sport6 = new Entity("sport");
				sport6.setProperty("nom","Soccer");
				datastore.put(sport6);
				
				Entity sport7 = new Entity("sport");
				sport7.setProperty("nom","Ping Pong");
				datastore.put(sport7);
		//
		//		Entity sport3 = new Entity("sport");
		//		sport3.setProperty("nom","Big patate");
		//		datastore.put(sport3);
		//
		//		Entity sport4 = new Entity("sport");
		//		sport4.setProperty("nom","Moto");
		//		datastore.put(sport4);
	}



	public  String getOneEgualValue(String entity,String key){
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query q = new Query(entity);
		PreparedQuery pq = datastore.prepare(q);
		if(pq.countEntities() == 1){
			Entity e = pq.asSingleEntity();
			return e.getProperty(key).toString();
		}
		else{
			return null;
		}
	}



	public void clearAndFillDataStore(){






	}






}
