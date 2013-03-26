package kamoru.app.vlist.dao;

import java.io.IOException;
import java.net.URI;
import java.util.List;

import kamoru.app.vlist.bean.Vfile;

public interface VlistDao {

	List getVlist(String pathName, String extension, String delimiter, String searchName, String method, int sort, boolean reverse) throws IOException;

	Vfile getVfile(URI uri);

	
	
}
