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
	
	public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException{
		jC = new JSONConverter(request);
		
		if(jC.getMethod().equalsIgnoreCase("addPlan")){
			addPlan();
		} else {
			details();
		}
	}
	
	public void addPlan(){
		exo = new Exercice((String)jC.getJsonObject().get("title"), (String)jC.getJsonObject().get("description"), (int)jC.getJsonObject().get("duration"));
	}
	
	public void details(){
		
	}
}
