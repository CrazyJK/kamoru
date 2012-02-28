package kamoru.app.vlist.logic;

import java.io.IOException;
import java.net.URI;
import java.util.List;

import kamoru.app.vlist.bean.Vfile;
import kamoru.app.vlist.dao.VlistDao;

public class VlistLogicImpl implements VlistLogic {

	private VlistDao vlistDao;
		
	public void setVlistDao(VlistDao vlistDao) {
		this.vlistDao = vlistDao;
	}

	@Override
	public List getVlist(String pathName, String extension, String delimiter, String searchName) throws IOException {
		List vlist = this.vlistDao.getVlist(pathName, extension, delimiter, searchName);
		return vlist;
	}

	@Override
	public Vfile getVfile(URI uri) {
		Vfile vfile = this.vlistDao.getVfile(uri);
		return vfile;
	}

	@Override
	public List getVlistOfSamesize(List list) {
		List vlist = this.vlistDao.getVlistOfSamesize(list);
		return null;
	}

}
