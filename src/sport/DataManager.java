package sport;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

public class DataManager {



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

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
//		Entity accueil = new Entity("accueil");
//		accueil.setProperty("message","Personal Trainer, votre coach personel vous souhaite la bienvenue !");
//		datastore.put(accueil);

//		Entity sport1 = new Entity("sport");
//		sport1.setProperty("nom","Luge");
//		datastore.put(sport1);
//
//		Entity sport2 = new Entity("sport");
//		sport2.setProperty("nom","ski");
//		datastore.put(sport2);
//
//		Entity sport3 = new Entity("sport");
//		sport3.setProperty("nom","Big patate");
//		datastore.put(sport3);
//
//		Entity sport4 = new Entity("sport");
//		sport4.setProperty("nom","Moto");
//		datastore.put(sport4);




	}






}
