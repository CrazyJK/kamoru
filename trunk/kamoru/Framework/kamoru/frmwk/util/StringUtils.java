package kamoru.frmwk.util;

public class StringUtils {

	public static boolean isNullOrBlank(String str) {
		return str == null || str.equalsIgnoreCase("null") || str.length() == 0 ? true : false;
	}
}
