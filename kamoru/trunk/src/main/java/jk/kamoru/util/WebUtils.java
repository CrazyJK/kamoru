package jk.kamoru.util;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

public class WebUtils {
	
	private static final String UTF8 = "UTF-8";
	private static final String Latin1 = "8859_1";
	private static final String POST = "POST";
	private static final String GET  = "GET";
	
	/**
	 * UTF-8 인코딩을 거친 파라미터 value 반환
	 * @param req
	 * @param name
	 * @return
	 */
	public static String getParameter(HttpServletRequest request, String name) {
		return getParameter(request, name, null);
	}
	
	/**
	 * UTF-8 인코딩을 거친 파라미터 value 반환
	 * @param req
	 * @param name
	 * @param defaultValue
	 * @return
	 */
	public static String getParameter(HttpServletRequest request, String name, String defaultValue) {
		String value = null;
		String method = request.getMethod();
		try {
			if(POST.equalsIgnoreCase(method)) {
				request.setCharacterEncoding(UTF8);
			}
			value = request.getParameter(name);
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

	public static int getParameterInt(HttpServletRequest request, String name, int defaultValue) {
		try {
			return Integer.parseInt(getParameter(request, name, String.valueOf(defaultValue)));
		}
		catch (NumberFormatException e) {
			return defaultValue;
		}
	}
	
	public static int getParameterInt(HttpServletRequest request, String name) {
		return getParameterInt(request, name, 0);
	}

	public static String[] getParameterArray(HttpServletRequest request, String name, String separator) {
		String value = getParameter(request, name, "");
		if (value.trim().length() > 0) {
			return StringUtils.splitByWholeSeparator(value, separator);
		}
		else {
			return null;
		}
	}
}
