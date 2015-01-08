/**
 * 
 */
package sport;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

/**
 * @author rHumblot
 *
 */
@SuppressWarnings("serial")
public class CompteServlet extends HttpServlet{

	private JSONConverter jC;
	private CacheManager cm = new CacheManager();
	private static final Map<String, String> openIdProviders;
	static {
		openIdProviders = new HashMap<String, String>();
		openIdProviders.put("Google", "https://www.google.com/accounts/o8/id");
		openIdProviders.put("Yahoo", "yahoo.com");
		openIdProviders.put("MyOpenId.com", "myopenid.com");
	}


	public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException{
		jC = new JSONConverter(request);

		if(jC.getMethod().equalsIgnoreCase("logout")){
			logout();
		}
	}


	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException{
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser(); // or req.getUserPrincipal()
		Set<String> attributes = new HashSet();

		String type  = req.getParameter("type");
		String currentUrl  = req.getParameter("url");
		String action  = req.getParameter("action");
		if (user != null) {
			if(cm.getValue("user_connected") == null)
				cm.setValue("user_connected", user.getNickname());
			if(action == null){

			}
			else if(action.equalsIgnoreCase("info")){

				JSONObject obj=new JSONObject();
				try {
					obj.put("name", user.getNickname());
					obj.put("logout", userService.createLogoutURL(currentUrl));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				resp.setContentType("application/json");
				resp.getWriter().write(obj.toString());
				resp.getWriter().close();

			}
			else if(action.equalsIgnoreCase("isConnected")){

				JSONObject obj=new JSONObject();
				try {
					obj.put("is", 1);
				} catch (JSONException e) {
					e.printStackTrace();
				}
				resp.setContentType("application/json");
				resp.getWriter().write(obj.toString());
				resp.getWriter().close();
			}

		} else { // si on est pas connecte
			String providerUrl = openIdProviders.get(type);
			String loginUrl = userService.createLoginURL(currentUrl, null, providerUrl, attributes);
			JSONObject obj=new JSONObject();
			try {
				obj.put("url", loginUrl.toString());
			} catch (JSONException e) {
				e.printStackTrace();
			}
			resp.setContentType("application/json");
			resp.getWriter().write(obj.toString());
			resp.getWriter().close();
		}
	}

	public void logout(){
		if(cm.getValue("user_connected") != null)
			cm.deleteValue("user_connected");
		System.out.println("on se deconnecte");

	}



}
