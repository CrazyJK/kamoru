package kamoru.app.vlist.dao;

import java.io.IOException;
import java.net.URI;
import java.util.List;

import kamoru.app.vlist.bean.Vfile;
import kamoru.frmwk.util.FileBean;
import kamoru.frmwk.util.FileUtils;
import kamoru.frmwk.util.StringUtils;

public class VlistDaoImpl implements VlistDao {

	@Override
	public List getVlist(String pathName, String extension, String delimiter, String searchName, String method, int sort, boolean reverse) throws IOException {
		String[] extensions = null;
		if(!StringUtils.isNullOrBlank(extension)) {
			if(StringUtils.isNullOrBlank(delimiter)) {
				extensions = new String[]{extension};
			}
			else {
				extensions = extension.split(delimiter);
			}
		}
		List list = FileUtils.getFileList(pathName, extensions, searchName, true, "kamoru.app.vlist.bean.Vfile");
		
		if("sameSize".equals(method)) {
			list = FileUtils.getSameSizeFileList(list);
		}
		
		FileUtils.sort(list, sort, reverse);
		
		return list;
	}
	
	@Override
	public Vfile getVfile(URI uri) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
