package kamoru.frmwk.db.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author  kamoru
 */
public class Query {

	org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(this.getClass()); 
	
	private Connection conn;
	
	/**
	 * @uml.property  name="query"
	 * @uml.associationEnd  
	 */
	private static Query query;
	
	public static Query getInstance() {
		if(query == null) {
			query = new Query();
		}
		return query;
	}
	
	private Query() {
		super();
	}
	
	public Query(Connection conn) {
		this.conn = conn;
	}
	
	public Query(String jdbcDriver, String jdbcUrl, String jdbcId, String jdbcPwd) throws ClassNotFoundException, SQLException {
		this.conn = getConnection(jdbcDriver, jdbcUrl, jdbcId, jdbcPwd);
	}
	
	public Connection getConnection() {
		return conn;
	}
	
	public Connection getConnection(String jdbcDriver, String jdbcUrl, String jdbcId, String jdbcPwd) throws ClassNotFoundException, SQLException {
		Class.forName(jdbcDriver);
		return DriverManager.getConnection(jdbcUrl, jdbcId, jdbcPwd);
	}
	
	public void setConnection(Connection conn) {
		close();
		this.conn = conn;
	}
	
	public ResultList executeQuery(String sql) throws SQLException {
		return executeQuery(sql, new ArrayList());
	}
	
	public ResultList executeQuery(String sql, String param) throws SQLException {
		ArrayList paramList = null;
		if(param != null) {
			paramList = new ArrayList();
			paramList.add(param);
		}
		return executeQuery(sql, paramList);
	}
	
	
	public ResultList executeQuery(String sql, ArrayList param) throws SQLException {
		return executeQuery(conn, sql, param);
	}
	
	public ResultList executeQuery(Connection conn, String sql, ArrayList param) throws SQLException {
		PreparedStatement pstmt = null;
		try {
			logger.debug("SQL: " + sql);
			pstmt = conn.prepareStatement(sql);
			if(param != null && param.size() > 0) {
				String paramVal = null;
				for(int i=0; i<param.size(); i++) {
					paramVal = (String)param.get(i);
					logger.debug("param" + (i+1) + "=" + paramVal);
					pstmt.setString(i+1, paramVal);
				}
			}
			ResultSet rs = pstmt.executeQuery();

			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			String[] columnName = new String[columnCount];
			logger.debug("column length " + columnName.length);
			for(int i=0; i<columnCount; i++) {
				columnName[i] = rsmd.getColumnName(i+1);
				logger.debug("column name : " + columnName[i]);
			}
			
			ResultList record = new ResultList();
			while(rs.next()) {
				HashMap map = new HashMap();
				for(int i=0; i<columnCount; i++) {
					String value = rs.getString(columnName[i]);
					logger.debug("Result: " + columnName[i] + "=" + value);
					map.put(columnName[i], value);
				}
				logger.debug("Record:" + map.toString());
				record.add(map);
			}
			logger.info("SQL:[" + sql + "]\n\tResult size = " + record.size());
			return record;
		} catch (SQLException e) {
			throw e;
		} finally {
			if(pstmt != null)
				pstmt.close();
		}
		
	}

	public void close() {
		if(conn != null)
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}

}
