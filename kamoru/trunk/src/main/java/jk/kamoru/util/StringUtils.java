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

	/**
	 * 문자열 비교. null일 경우 ""으로 변환. trim처리
	 * @param name1
	 * @param name2
	 * @return
	 */
	public static int compateTo(String name1, String name2) {
		name1 = name1 == null ? "" : name1.trim();
		name2 = name2 == null ? "" : name2.trim();
		return name1.compareTo(name2);
	}

	public static int compateTo(Object obj1, Object obj2) {
		String str1 = obj1 == null ? "" : obj1.toString();
		String str2 = obj2 == null ? "" : obj2.toString();
		return str1.compareTo(str2);
	}

}
