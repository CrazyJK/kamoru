package kamoru.app.vlist.form;

import java.util.List;

import org.apache.struts.action.ActionForm;

public class VlistForm extends ActionForm {

	private String pathName;
	private String searchName;
	private int key;
	private String method = "all";
	private int sort = 1;
	private String extension;
	private String delimiter;
	private boolean reverse;
	private List vfileList;
	
	public List getVfileList() {
		return vfileList;
	}
	public void setVfileList(List vfileList) {
		this.vfileList = vfileList;
	}
	public String getExtension() {
		return extension;
	}
	public void setExtension(String extension) {
		this.extension = extension;
	}
	public String getDelimiter() {
		return delimiter;
	}
	public void setDelimiter(String delimiter) {
		this.delimiter = delimiter;
	}
	public String getPathName() {
		return pathName;
	}
	public void setPathName(String pathName) {
		this.pathName = pathName;
	}
	public String getSearchName() {
		return searchName;
	}
	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}
	public int getKey() {
		return key;
	}
	public void setKey(int key) {
		this.key = key;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public int getSort() {
		return sort;
	}
	public void setSort(int sort) {
		this.sort = sort;
	}
	public boolean isReverse() {
		return reverse;
	}
	public void setReverse(boolean reverse) {
		this.reverse = reverse;
	}
	
}
