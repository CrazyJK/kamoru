package kamoru.sql;
import java.io.*;
import java.util.*;
import java.sql.*;
public class SQLUtil {

	static int count = 0;
	
	public static void insertCSV(String filepath, String delim) throws Exception{
		BufferedReader reader = new BufferedReader(new FileReader(filepath));
		String line = null;
		Connection conn = kamoru.util.DBUtil.getConnection("oracle.jdbc.driver.OracleDriver", "jdbc:oracle:thin:@192.168.12.216:1521:orcl", "hong", "hong");
		PreparedStatement pstmt = conn.prepareStatement("INSERT INTO RXCMAIN VALUES(?,?,?,?,?,?,?,?,?,?,?)");

		count = 0;
		while((line = reader.readLine()) != null){
			count++;
			if(count == 1){
				continue;
			}
			String[] vals = line.split(delim);
			for(int i=0 ; i<vals.length ; i++){
				pstmt.setString(i+1, vals[i]);
			}
			pstmt.executeUpdate();
//			pstmt.addBatch();
			if(count % 1000 == 0){
				System.out.println("Insert " + count);
			}
		}
		conn.commit();
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try{
			SQLUtil.insertCSV("F:\\temp\\정기권임시.txt", "\t");
		}catch(Exception e){
			System.err.println(count);
			System.err.println(e);
		}
	}

}
