package kamoru.frmwk.util;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ServletUtils {

	private static final String UTF8 = "UTF-8";
	private static final String Latin1 = "8859_1";
	private static final String POST = "POST";
	private static final String GET  = "GET";
	
	public static String getParameter(HttpServletRequest req, String name) {
		return getParameter(req, name, null);
	}
	
	public static String getParameter(HttpServletRequest req, String name, String defaultValue) {
		String value = null;
		String method = req.getMethod();
		try {
			if(POST.equalsIgnoreCase(method)) {
				req.setCharacterEncoding(UTF8);
			}
			value = req.getParameter(name);
			if(value == null)
				value = defaultValue;
			else if(GET.equalsIgnoreCase(method))
				value = new String(value.getBytes(Latin1), UTF8);
			return value;
		} catch (UnsupportedEncodingException e) {
			// Do nothing
			return null;
		}
	}
}
