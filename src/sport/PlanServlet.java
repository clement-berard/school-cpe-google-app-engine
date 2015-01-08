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
			addExercice();
		} else {
			details();
		}
	}
	
	public void addPlan(){
		plan = new Plan();
		plan.setTitle((String)jC.getJsonObject().get("title"));
		plan.setDescription((String)jC.getJsonObject().get("description"));
		plan.setDomain((String)jC.getJsonObject().get("domain"));
		plan.Save();
	}
	
	public void details(){
		
	}
	
	public void addExercice(){
		
		String title;
		String desc;
		String dur;
		int numberExos = Integer.parseInt((String)jC.getJsonObject().get("number"));
		
		for(int i =1; i<(numberExos+1); i++){
			title = "exos[" + i + "][title]";
			desc = "exos[" + i + "][description]";
			dur = "exos[" + i + "][duration]";
			System.out.println((String)jC.getJsonObject().get(dur));
			exo = new Exercice();
			exo.setTitle((String)jC.getJsonObject().get(title));
			exo.setDescription((String)jC.getJsonObject().get(desc));
			exo.setDuration(30);
			exo.setIdPlan(plan.getId());
			exo.Save();
		}
		System.out.println();
	}
}
