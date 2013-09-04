package kamoru.util;

import java.util.*;

public class SystemProperty {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Properties prop = System.getProperties();
		Enumeration enum1 = prop.keys();
		while(enum1.hasMoreElements()){
			String key = (String)enum1.nextElement();
			System.out.println(key + ":[" + System.getProperty(key) + "]");
		}
	}

}
