package kamoru.app.sample.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ibatis.sqlmap.client.SqlMapClient;

import kamoru.frmwk.db.ibatis.SqlMapFactory;

public class SampleDaoImpl implements SampleDao {

	protected static final Log logger = LogFactory.getLog(SampleDaoImpl.class);

	@Override
	public List getList() throws SQLException {
		logger.debug("Start getList()");
		List girlsgenerationList = new ArrayList();
		try {
			SqlMapClient sqlMap = SqlMapFactory.getSqlMap();
			girlsgenerationList = (List)sqlMap.queryForList("sample.getGirlsGeneration", null);
		} catch (SQLException e) {
			logger.error("Error getList()", e);
			throw e;
		}
		
		return girlsgenerationList;
	}

}
