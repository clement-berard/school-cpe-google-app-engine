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

import com.google.appengine.labs.repackaged.org.json.JSONException;

/**
 * @author rHumblot
 *
 */
@SuppressWarnings("serial")
public class PlanServlet extends HttpServlet{
	
	// Name of method to call 
	private String method = null;
	// String in format JSon
	private String dataString;
	JSONObject jsonObject;
	
	public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException{
		method = request.getParameter("method");
		dataString = (String)request.getParameter("data");
		
		JSONParser parser=new JSONParser();
		try {
			jsonObject = (JSONObject)parser.parse(dataString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		if(method.equalsIgnoreCase("addPlan")){
			addPlan();
		} else {
			details();
		}
		
	}
	
	public void addPlan(){
		
	}
	
	public void details(){
		
	}
}
