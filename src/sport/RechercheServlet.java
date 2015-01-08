/**
 * 
 */
package sport;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.google.appengine.labs.repackaged.org.json.XML;


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
		else if(jC.getMethod().equalsIgnoreCase("getNewsRss")){
			try {
				getNewsRss(response);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(jC.getMethod().equalsIgnoreCase("getSearchPlan")){
			try {
				getSearchPlan(response,request,jC);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(jC.getMethod().equalsIgnoreCase("getSearchExercices")){
			try {
				getSearchExercices(response,request,jC);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void getAllSports(HttpServletResponse resp) throws JSONException, IOException {

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		// Utilisation Query afin de rassembler les eÌ�leÌ�ments a appeler/filter

		//		Entity sport1 = new Entity("sport");
		//		sport1.setProperty("nom","trompette");
		//		datastore.put(sport1);
		// Utilisation Query afin de rassembler les éléments a appeler/filter

		Query q = new Query("sport");
		// ReÌ�cupeÌ�ration du reÌ�sultat de la requeÌ€te aÌ€ lâ€™aide de PreparedQuery
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

	private void getNewsRss(HttpServletResponse resp) throws JSONException, IOException {

		String str = "https://fr.news.yahoo.com/rss/world";
		URL url = new URL(str);
		InputStream is = url.openStream();
		int ptr = 0;
		StringBuilder builder = new StringBuilder();
		while ((ptr = is.read()) != -1) {
			builder.append((char) ptr);
		}
		String xml = builder.toString();
		JSONObject jsonObject = XML.toJSONObject(xml);
		String jsonPrettyPrintString = jsonObject.toString(4);
		resp.setContentType("application/json");
		resp.getWriter().write(jsonPrettyPrintString.toString());
		resp.getWriter().close();

	}


	private void getSearchPlan(HttpServletResponse resp,HttpServletRequest req, JSONConverter jC) throws JSONException, IOException {


		String term  = (String) jC.getJsonObject().get("term");
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		Query q = new Query("user");
		q.addFilter("login", Query.FilterOperator.EQUAL, term);

		PreparedQuery pq = datastore.prepare(q);

		if(pq.countEntities() > 0){
			Entity e = pq.asSingleEntity();
			// redirect
			resp.getWriter().println(e.getProperty("prenom"));
			resp.sendRedirect("/index.html");
		}




	}

	private void getSearchExercices(HttpServletResponse resp, HttpServletRequest req, JSONConverter jC2) throws JSONException, IOException {





	}




}
