package kamoru.app.vlist.logic;

import java.io.IOException;
import java.net.URI;
import java.util.List;

import kamoru.app.vlist.bean.Vfile;

public interface VlistLogic {

	List getVlist(String pathName, String extension, String delimiter, String searchName) throws IOException;
	Vfile getVfile(URI uri);
	List getVlistOfSamesize(List list);
}
