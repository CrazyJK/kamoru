package kamoru.util;

import java.sql.*;

public class DBUtil {
	
	public static Connection getConnection(String driver, String url, String user, String password) throws Exception{
		Class.forName(driver);
		return DriverManager.getConnection(url, user, password);
	}
}
