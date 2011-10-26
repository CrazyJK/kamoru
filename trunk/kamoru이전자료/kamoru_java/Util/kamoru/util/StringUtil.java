/*
 * @(#)StringUtil.java 1.0 2009/05/15
 *
 * Copyright (c) 2009 kAmOrU. All Rights Reserved.
 */

package kamoru.util;

/**
 * String 객체를 좀더 편하게 쓰도록 도와 주는 클래스
 * @author 	Nam Jongkwan
 * @version 1.0 2009/05/15
 * @since	kAmOrU Util 1.0
 */
public class StringUtil implements java.io.Serializable{
	static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(StringUtil.class);
	
	/**
	 * <code>substring(int beginIndex, int endIndex)</code>의 
	 * <code>StringIndexOutOfBoundsException</code> 처리<br>
	 *  
	 * @param string the input string.
	 * @param beginIndex the beginning index.
	 * @param endIndex the ending index.
	 * @param excepStr return string, When exception occurs 
	 * @return the specified substring.
	 */
	public static String substring(String string, int beginIndex, int endIndex, String excepStr){
		try{
			if(logger.isTraceEnabled())
				logger.trace("substring(" + string + ", " + beginIndex + ", " + endIndex + ", " + excepStr + ")");
			return string.substring(beginIndex, endIndex);
		}catch(StringIndexOutOfBoundsException e){
			return excepStr;
		}
	}

	/**
	 * <code>substring(int beginIndex, int endIndex)</code>의 
	 * <code>StringIndexOutOfBoundsException</code> 처리<br>
	 * object를 String으로 변환하여 처리한다.
	 * 
	 * @param object the input object.
	 * @param beginIndex the beginning index.
	 * @param endIndex the ending index.
	 * @param excepStr return string, When exception occurs.
	 * @return the specified substring.
	 */
	public static String substring(Object object, int beginIndex, int endIndex, String excepStr){
		try{
			if(logger.isTraceEnabled())
				logger.trace("substring(" + object + ", " + beginIndex + ", " + endIndex + ", " + excepStr + ")");
			String string = object == null ? null : String.valueOf(object);
			return substring(string, beginIndex, endIndex, excepStr);
		}catch(Exception e){
			return excepStr;
		}
	}
	
	/**
	 * if object is null, return null string 
	 * @param object
	 * @return
	 */
	public static String getNullString(Object object){
		return getNullString(object, "");
	}
	/**
	 * if string is null, return null string 
	 * @param object
	 * @return
	 */
	public static String getNullString(String string){
		return getNullString(string, "");
	}
	/**
	 * if object is null, return nullString 
	 * @param object
	 * @return
	 */
	public static String getNullString(Object object, String nullString){
		if(logger.isTraceEnabled())
			logger.trace("getNullString(" + object + ", " + nullString + ") "  + ((object == null) ? "object is null" : ""));
		return object == null ? nullString : object.toString();
	}
	/**
	 * if string is null, return nullString 
	 * @param object
	 * @return
	 */
	public static String getNullString(String string, String nullString){
		if(logger.isTraceEnabled())
			logger.trace("getNullString(" + string + ", " + nullString + ") "  + ((string == null) ? "string is null" : ""));
		return string == null ? nullString : string;
	}
	
	/**
	 * replaceAll. PatternSyntaxException를 피할수 있다.
	 * @param s  대상 문자열
	 * @param s1 찾을 문자열
	 * @param s2 바꿀 문자열
	 * @return
	 */
    public static String replaceAll(String s, String s1, String s2)
    {
        int i = s.indexOf(s1);
        if(i != -1)
        {
            int j = 0;
            int k = s1.length();
            StringBuffer stringbuffer = new StringBuffer();
            for(; i != -1; i = s.indexOf(s1, j))
            {
                stringbuffer.append(s.substring(j, i));
                stringbuffer.append(s2);
                j = i + k;
            }

            stringbuffer.append(s.substring(j));
            return stringbuffer.toString();
        } else
        {
            return s;
        }
    }

	
	public static void main(String[] args){
		System.out.println(
				StringUtil.substring("2009-05-15 22:32:00.0000", 2, 27, "NAN")
		);
		Object obj = null;
		String str = String.valueOf(obj);
		int leng = str == null ? -1 : str.length();
		System.out.println("str:[" + str + "] length:[" + leng + "]");
	}
}
