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
	public List getVlist(String pathName, String extension, String delimiter, String searchName) throws IOException {
		String[] extensions = null;
		if(!StringUtils.isNullOrBlank(extension)) {
			if(StringUtils.isNullOrBlank(delimiter)) {
				extensions = new String[]{extension};
			}
			else {
				extensions = extension.split(delimiter);
			}
		}
		return FileUtils.getFileList(pathName, extensions, searchName, true, "kamoru.app.vlist.bean.Vfile");
	}

	@Override
	public List getVlistOfSamesize(List list) {
		
		return null;
	}
	
	public static void main(String[] args) throws IOException {
		VlistDao dao = new VlistDaoImpl();
		List list = dao.getVlist("/home/kamoru/ETC/Download", "avi mp3", " ", null);
		for(int i=0; i<list.size(); i++) {
			FileBean bean = (FileBean)list.get(i);
			System.out.println(bean.jsonText());
		}
	}

	@Override
	public Vfile getVfile(URI uri) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
