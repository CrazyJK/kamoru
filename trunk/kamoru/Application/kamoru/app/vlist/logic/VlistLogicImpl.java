package kamoru.app.vlist.logic;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import kamoru.app.vlist.bean.Vfile;
import kamoru.app.vlist.dao.VlistDao;
import kamoru.app.vlist.form.VlistForm;

public class VlistLogicImpl implements VlistLogic {

	private VlistDao vlistDao;
		
	public void setVlistDao(VlistDao vlistDao) {
		this.vlistDao = vlistDao;
	}

	@Override
	public List getVlist(VlistForm form) throws IOException {
		
		String pathName		= form.getPathName();
		String extension	= form.getExtension();
		String delimiter	= form.getDelimiter();
		String searchName	= form.getSearchName();
		String method		= form.getMethod();
		int    sort			= form.getSort();
		boolean reverse 	= form.isReverse();

		List list = new ArrayList();
		if(StringUtils.isNotBlank(pathName)) {
			list = this.vlistDao.getVlist(pathName, extension, delimiter, searchName, method, sort, reverse);
		}
		return list;
	}

	@Override
	public Vfile getVfile(URI uri) {
		Vfile vfile = this.vlistDao.getVfile(uri);
		return vfile;
	}

}
