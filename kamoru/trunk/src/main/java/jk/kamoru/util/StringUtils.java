package jk.kamoru.util;

/**
 * commons.lang3.StringUtils 상속하고 필요한 기능 추가
 * @author kamoru
 *
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {

	public static boolean isNullOrBlank(String str) {
		return str == null || str.equalsIgnoreCase("null") || str.length() == 0 ? true : false;
	}
	
	public static String addZero(int number, int digit) {
		String s = String.valueOf(number);
		while(s.length() < digit) {
			s = "0" + s;
		}
		return s;
	}

}
