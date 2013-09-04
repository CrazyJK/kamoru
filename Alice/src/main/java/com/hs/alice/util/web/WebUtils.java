package com.hs.alice.util.web;

import java.io.UnsupportedEncodingException;

public class WebUtils {

	public static final String UTF8 = "UTF-8";
	
	public static String getEncodingString(String str, String enc) {
		try {
			if(str != null)
				str = new String(str.getBytes("8859_1"), enc);
		} catch (UnsupportedEncodingException e1) {
		}
		return str;
	}
	
	public static String getUTF8EncodingString(String str) {
		return getEncodingString(str, UTF8);
	}
}
