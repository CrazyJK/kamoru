package kamoru.app.vlist.logic;

import java.util.List;

import kamoru.app.vlist.bean.Vfile;

public interface VlistLogic {

	List getVlist();
	Vfile getVfile(int key);
	List getVlistBySearchName(String searchName);
	List getVlistOfSamesize();
}
