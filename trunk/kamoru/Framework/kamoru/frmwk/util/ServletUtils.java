package kamoru.frmwk.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ServletUtils {

	public static String getParameter(HttpServletRequest req, String name) {
		return getParameter(req, name, null);
	}
	
	public static String getParameter(HttpServletRequest req, String name, String defaultValue) {
		return req.getParameter(name) == null ? defaultValue : req.getParameter(name); 
	}
}
