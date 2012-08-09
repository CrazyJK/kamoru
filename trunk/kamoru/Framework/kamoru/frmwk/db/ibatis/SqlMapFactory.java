package kamoru.frmwk.db.ibatis;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import kamoru.test.Singleton;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

/**
 * iBatis 2 - SqlMapClient pool class<br/> used. SqlMapFactory.getSqlMap([key])
 * @author  kamoru
 */
public final class SqlMapFactory {

	protected static final Log logger = LogFactory.getLog(SqlMapFactory.class);

	private static final String PROPERTIES_FILE = "/resources/SqlMapConfig.properties"; 
    private static final String DEFAULT_KEY = "default";
	
	private static Map sqlMapClientMap = new HashMap();
	
	/**
	 * @uml.property  name="instance"
	 * @uml.associationEnd  
	 */
	private static final SqlMapFactory instance = new SqlMapFactory();

	private SqlMapFactory() {
		initialize();
	}
	
	private static void initialize() {
		try {
			ClassLoader cl = Thread.currentThread().getContextClassLoader();
	        if( cl == null ) cl = ClassLoader.getSystemClassLoader();
			logger.debug("ClassLoader " + cl);
			
	        InputStream inStream = cl.getResourceAsStream(PROPERTIES_FILE); 
	        logger.debug(PROPERTIES_FILE + " InputStream " + inStream);
	        
	        if(inStream == null) {
	        	throw new IOException(PROPERTIES_FILE + " is not exist!");
	        }
	        
			Properties props = new Properties();
	        props.load(inStream);
	        
	        String sqlMapConfigSetValue = props.getProperty("SqlMapConfig.Set");
	        String[] sqlMapConfigSetArray = sqlMapConfigSetValue.split(",");
	        logger.info("sqlMapConfigSet : " + sqlMapConfigSetValue);

	        sqlMapClientMap.clear();
	        
	        for(int i=0; i<sqlMapConfigSetArray.length; i++) {
	        	String[] sqlMapConfigArray = sqlMapConfigSetArray[i].split(":");
	        	if(sqlMapConfigArray.length != 2) {
	        		logger.info((i+1) + " ignore. [" + sqlMapConfigSetArray[i] + "]");
	        	} 
	        	else {
	        		String key = sqlMapConfigArray[0].trim();
	        		String res = sqlMapConfigArray[1].trim();
	        		if(key.length() == 0 || res.length() == 0) {
	        			logger.info((i+1) + " ignore. [" + key + ":" + res + "]");
	        		} 
	        		else {
	        			Reader reader = Resources.getResourceAsReader(res);
	        			sqlMapClientMap.put(key, SqlMapClientBuilder.buildSqlMapClient(reader));
	        			logger.info((i+1) + " built. [" + key + ":" + res + "]");
	        		}
	        	}
	        }
		} 
		catch (Exception e) {
			logger.fatal("SqlMapFactory Error", e);
		}
	}
	
	/**
	 * return default SqlMapClient 
	 * @return
	 * @throws SQLException
	 */
	public static SqlMapClient getSqlMap() throws SQLException {
		return getSqlMap(DEFAULT_KEY);
	}
	/**
	 * return SqlMapClient by key
	 * @param key
	 * @return
	 * @throws SQLException
	 */
	public static SqlMapClient getSqlMap(String key) throws SQLException {
		if(!sqlMapClientMap.containsKey(key)) {
			throw new SQLException("Can not find sqlMap key : " + key);
		}
		return (SqlMapClient)sqlMapClientMap.get(key);
	}

	public static void reload() throws Exception {
		synchronized(java.lang.Object.class) {
			initialize();
		}
	}

}
