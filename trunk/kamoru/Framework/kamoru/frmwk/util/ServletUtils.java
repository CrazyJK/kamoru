package kamoru.frmwk.util;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ServletUtils {

	public static String getParameter(HttpServletRequest req, String name) {
		return getParameter(req, name, null);
	}
	
	public static String getParameter(HttpServletRequest req, String name, String defaultValue) {
		String ret = req.getParameter(name) == null ? defaultValue : req.getParameter(name);
		if("get".equalsIgnoreCase(req.getMethod())) {
			try {
				ret = new String(ret.getBytes("8859_1"), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return ret; 
	}
}
