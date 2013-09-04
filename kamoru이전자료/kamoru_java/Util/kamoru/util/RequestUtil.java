package kamoru.util;

import java.io.Serializable;
import javax.servlet.http.HttpServletRequest;

public class RequestUtil implements Serializable {
	static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(RequestUtil.class);

	/**
	 * get request parameter.<br> if value is null, return null. 
	 * @param request
	 * @param name
	 * @return
	 */
	public static String getParameter(HttpServletRequest request, String name){
		return getParameter(request, name, null, false, null);
	}

	public static String getParameter(HttpServletRequest request, String name, String defaultvalue){
		return getParameter(request, name, defaultvalue, false, null);
	}

	public static String getParameter(HttpServletRequest request, String name, String defaultvalue, boolean decode){
		return getParameter(request, name, defaultvalue, decode, null);
	}
	
	/**
	 * get request parameter.<br> if value is null, return defaultvalue.
	 * @param request
	 * @param name
	 * @param defaultvalue
	 * @return
	 */
	public static String getParameter(HttpServletRequest request, String name, String defaultvalue, boolean decode, String enc){
		String paramValue = null;
		if(	logger.isTraceEnabled()){
			logger.trace("getParameter(request, '" +  name + "', '" + defaultvalue + "')");
		}
		paramValue = request.getParameter(name);
		if(logger.isTraceEnabled()){
			logger.trace("\tvalue:[" + paramValue + "]");
		}
		if(paramValue != null && decode){
			try{
				if(enc == null){
					paramValue = java.net.URLDecoder.decode(paramValue);
				}else{
					paramValue = java.net.URLDecoder.decode(paramValue, enc);
				}
			}catch(Exception e){
				paramValue = java.net.URLDecoder.decode(paramValue);
			}
			if(logger.isTraceEnabled()){
				logger.trace("\tdecoding value:[" + paramValue + "]");
			}
		}
		return paramValue == null ? defaultvalue : paramValue;
	}
	
}
