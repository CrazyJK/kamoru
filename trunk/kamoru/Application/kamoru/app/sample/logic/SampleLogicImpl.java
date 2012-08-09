package kamoru.app.sample.logic;

import java.sql.SQLException;
import java.util.List;

import kamoru.app.sample.dao.SampleDao;

/**
 * @author  kamoru
 */
public class SampleLogicImpl implements SampleLogic {

	/**
	 * @uml.property  name="sampleDao"
	 * @uml.associationEnd  
	 */
	private SampleDao sampleDao;
	
	/**
	 * @param sampleDao
	 * @uml.property  name="sampleDao"
	 */
	public void setSampleDao(SampleDao sampleDao) {
		this.sampleDao = sampleDao;
	}
	
	@Override
	public List getSampleList() throws SQLException {
		return this.sampleDao.getList();
	}

}
