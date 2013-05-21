package kamoru.test.system;

import java.util.Properties;

public class SystemProperties {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Properties prop = System.getProperties();
		
		for(String name : prop.stringPropertyNames()) {
			System.out.format("%s=%s%n", name, prop.getProperty(name));
		}

	}

}
