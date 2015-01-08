/**
 * 
 */
package sport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import sport.models.Exercice;
import sport.models.Plan;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.labs.repackaged.org.json.JSONException;

/**
 * @author rHumblot
 *
 */
@SuppressWarnings("serial")
public class PlanServlet extends HttpServlet{
	
	private JSONConverter jC;
	private Exercice exo;
	private Plan plan;
	
	private final DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	private final DatastoreService datastoreq = DatastoreServiceFactory.getDatastoreService();
	
	public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException{
		jC = new JSONConverter(request);
		
		if(jC.getMethod().equalsIgnoreCase("addPlan")){
			addPlan();
			response.setContentType("application/json");
			JSONObject jsonToSend;
			jsonToSend=new JSONObject();
			jsonToSend.put("message", "Enregistrement réussi");
			response.getWriter().write(jsonToSend.toString());
			response.getWriter().close();
			
		} else {
			details(response);
		}
	}
	
	public void addPlan(){
		CacheManager cache = new CacheManager();
		String user = cache.getValue("user_connected");
		//TODO
		//Save the user 
		
		plan = new Plan();
		plan.setTitle((String)jC.getJsonObject().get("title"));
		plan.setDescription((String)jC.getJsonObject().get("description"));
		plan.setDomain((String)jC.getJsonObject().get("domain"));
		plan.Save();
		addExercice();
	}
	
	public void addExercice(){
		
		String title;
		String desc;
		String dur;
		String durSec;
		int numberExos = Integer.parseInt((String)jC.getJsonObject().get("number"));
		
		for(int i =1; i<(numberExos+1); i++){
			title = "exos[" + i + "][title]";
			desc = "exos[" + i + "][description]";
			dur = "exos[" + i + "][duration]";
			durSec = "exos[" + i + "][durationSec]";
			System.out.println((String)jC.getJsonObject().get(dur));
			exo = new Exercice();
			exo.setTitle((String)jC.getJsonObject().get(title));
			exo.setDescription((String)jC.getJsonObject().get(desc));
			exo.setDuration((String)jC.getJsonObject().get(dur));
			exo.setDurationSec(Integer.parseInt((String)jC.getJsonObject().get(durSec)));
			exo.setIdPlan(plan.getId());
			exo.Save();
		}
		System.out.println();
	}
	
	@SuppressWarnings("unchecked")
	public void details(HttpServletResponse response) throws IOException{
		// Utilisation Query afin de rassembler les éléments a appeler/filter

		Query q = new Query("exercice");
		
		Query qq = new Query("plan");
		// Récupération du résultat de la requète à l'aide de PreparedQuery
		
		PreparedQuery pqq = datastoreq.prepare(qq);
		String title;
		long key = 0;
		List<String> myList = new ArrayList<String>();
		JSONObject obj;
		
		
		for (Entity result : pqq.asIterable()) {
			title = result.getProperty("title").toString();
			key = result.getKey().getId();
		}
		//q.addFilter("idPlan", Query.FilterOperator.EQUAL, key);
		
		PreparedQuery pq = datastore.prepare(q);
		JSONArray objArray = new JSONArray();
		for (Entity result : pq.asIterable()) {
			obj = new JSONObject();
			obj.put("title", result.getProperty("title").toString());
			obj.put("description", result.getProperty("description").toString());
			obj.put("idPlan", result.getKey().getId());
			obj.put("duration", result.getProperty("duration").toString());
			obj.put("durationSec", result.getProperty("durationSec"));
			
			objArray.add(obj);
		}
		response.setContentType("application/json");
		response.getWriter().write(objArray.toString());
		response.getWriter().close();
		
	}
}
