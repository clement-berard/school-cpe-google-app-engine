/**
 * 
 */
package sport;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * @author rhumblot
 * 
 * Manipulate JSON OBJECT
 */
public class JSONConverter {

	// Name of method to call
	private String method;
	private JSONObject jsonObject;
	// String in format JSon
	private String dataString;
	
	public JSONConverter(HttpServletRequest request){
		
		method = request.getParameter("method");
		dataString = (String)request.getParameter("data");
		
		JSONParser parser = new JSONParser();
		try {
			jsonObject = (JSONObject)parser.parse(dataString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	public String getMethod() {
		return method;
	}

	public JSONObject getJsonObject() {
		return jsonObject;
	}
}
