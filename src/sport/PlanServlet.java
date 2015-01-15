/**
 * 
 */
package sport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import sport.models.Exercice;
import sport.models.Plan;
import sport.models.User;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

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

		}else if(jC.getMethod().equalsIgnoreCase("details")){
			details(response);
		}else if(jC.getMethod().equalsIgnoreCase("getListPlanFromCat")){
			getListPlanFromCat(response);
		}else if(jC.getMethod().equalsIgnoreCase("setEntrainement")){
			setTraining(response);
		}else if(jC.getMethod().equalsIgnoreCase("getPersonnalData")){
			getTraining(response);
		}
	}
	
	private void getTraining(HttpServletResponse response) throws IOException {
		
		String userId  = (String) jC.getJsonObject().get("id");
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query q = new Query("user");
		q.addFilter("id", Query.FilterOperator.EQUAL, userId);	
		PreparedQuery pq = datastore.prepare(q);
		JSONObject obj;
		JSONArray objArray = new JSONArray();
		for (Entity result : pq.asIterable()) {
			obj = new JSONObject();
			obj.put("plan", result.getProperty("plan").toString());
			obj.put("title", result.getProperty("title").toString());
			obj.put("date", result.getProperty("date").toString());
			obj.put("duration", result.getProperty("duration").toString());
			obj.put("statut", result.getProperty("statut"));

			objArray.add(obj);
		}
		response.setContentType("application/json");
		response.getWriter().write(objArray.toString());
		response.getWriter().close();
	}
	
	private void setTraining(HttpServletResponse resp) throws IOException {
		User user = new User();
		long id = 0, idTitle = 0, idPlan = 0;
		try{
			id = Long.parseLong((String) jC.getJsonObject().get("id"));
			idTitle = Long.parseLong((String) jC.getJsonObject().get("idTitle"));
			idPlan = Long.parseLong((String) jC.getJsonObject().get("idPlan"));
		}catch(Exception e){
			
		}
		
		user.setId(id);
		user.setIdPlan(idPlan);
		user.setIdTitle(idTitle);
		user.setDate((String) jC.getJsonObject().get("date"));
		user.setDuration((String) jC.getJsonObject().get("duration"));
		user.setStatut((String) jC.getJsonObject().get("statut"));
		
		user.Save();
	}

	private void getListPlanFromCat(HttpServletResponse resp) throws IOException {
		String cat  = (String) jC.getJsonObject().get("categorie");
		System.out.println(cat);

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		Query q = new Query("plan");
		q.addFilter("domain", Query.FilterOperator.EQUAL, cat);
		//
		PreparedQuery pq = datastore.prepare(q);

		List<String> myList = new ArrayList<String>();
		JSONObject obj=new JSONObject();
		int i = 0;
		for (Entity result : pq.asIterable()) {
			JSONObject obj2=new JSONObject();
			obj2.put("title", result.getProperty("title").toString());
			obj2.put("description", result.getProperty("description").toString());
			obj2.put("domain", result.getProperty("domain").toString());
			obj2.put("id", result.getKey().getId());
			obj.put("plan_"+i, obj2.toString());
			i++;
		}
		resp.setContentType("application/json");
		resp.getWriter().write(obj.toString());
		resp.getWriter().close();

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
		plan.setUser(user);
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
		JSONObject obj;
		long valPlan = 0;
		try{
			valPlan = Long.parseLong((String) jC.getJsonObject().get("plan"));
		}catch(Exception e){
			
		}
		String valExo   = (String) jC.getJsonObject().get("exo");
		
		Query q = new Query("exercice");
		
		if(valPlan != 0 && valExo != ""){
			q.addFilter("idPlan", Query.FilterOperator.EQUAL, valPlan);
		} else {
			q.addFilter("title", Query.FilterOperator.EQUAL, valExo);
		}
		
		// Récupération du résultat de la requète à l'aide de PreparedQuery
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
