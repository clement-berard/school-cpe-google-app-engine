/**
 * 
 */
package sport;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONValue;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.google.gson.Gson;

/**
 * @author rHumblot
 *
 */
@SuppressWarnings("serial")
public class RechercheServlet extends HttpServlet{


	private JSONConverter jC;

	public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException{
		jC = new JSONConverter(request);

		if(jC.getMethod().equalsIgnoreCase("getAllSports")){
			try {
				getAllSports(response);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void getAllSports(HttpServletResponse resp) throws JSONException, IOException {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		// Utilisation Query afin de rassembler les éléments a appeler/filter
		Query q = new Query("sport");
		// Récupération du résultat de la requète à l’aide de PreparedQuery
		PreparedQuery pq = datastore.prepare(q);
		List<String> myList = new ArrayList<String>();
		JSONObject obj=new JSONObject();
		for (Entity result : pq.asIterable()) {
//			myList.add(result.getProperty("nom").toString());	
			obj.put(result.getProperty("nom").toString(), result.getProperty("nom").toString());
		}
//		String json = new Gson().toJson(myList);
		resp.setContentType("application/json");
		
		resp.getWriter().write(obj.toString());
		resp.getWriter().close();
	}




}
