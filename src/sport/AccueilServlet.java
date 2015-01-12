/**
 * 
 */
package sport;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

/**
 * @author rHumblot
 *
 */
@SuppressWarnings("serial")
public class AccueilServlet extends HttpServlet{

	public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException{
		
		try {
			this.getMessage(response);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public void getMessage(HttpServletResponse resp) throws IOException, JSONException{
		DataManager dm = new DataManager();
		dm.clearAndFillDataStore();
		CacheManager cm = new CacheManager();

		//		Entity accueil = new Entity("accueil");
		//		accueil.setProperty("message","Personal Trainer, votre coach personel vous souhaite la bienvenue !");
		//		datastore.put(accueil);

		resp.setContentType("application/json");
		JSONObject obj=new JSONObject();
		obj.put("mess", cm.getValueCache("accueil", "message"));
		resp.getWriter().write(obj.toString());
		resp.getWriter().close();
		
	}
}
