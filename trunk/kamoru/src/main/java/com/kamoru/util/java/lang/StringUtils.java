package com.kamoru.util.java.lang;

public class StringUtils {

	public static boolean isNullOrBlank(String str) {
		return str == null || str.equalsIgnoreCase("null") || str.length() == 0 ? true : false;
	}
}
