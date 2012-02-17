package kamoru.app.sample.logic;

import java.sql.SQLException;
import java.util.List;

import kamoru.app.sample.dao.SampleDao;

public class SampleLogicImpl implements SampleLogic {

	private SampleDao sampleDao;
	
	public void setSampleDao(SampleDao sampleDao) {
		this.sampleDao = sampleDao;
	}
	
	@Override
	public List getSampleList() throws SQLException {
		return this.sampleDao.getList();
	}

}
