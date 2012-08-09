package kamoru.app.vlist.form;

import java.util.List;

import org.apache.struts.action.ActionForm;

/**
 * @author  kamoru
 */
public class VlistForm extends ActionForm {

	/**
	 * @uml.property  name="pathName"
	 */
	private String pathName;
	/**
	 * @uml.property  name="searchName"
	 */
	private String searchName;
	/**
	 * @uml.property  name="key"
	 */
	private int key;
	/**
	 * @uml.property  name="method"
	 */
	private String method = "all";
	/**
	 * @uml.property  name="sort"
	 */
	private int sort = 1;
	/**
	 * @uml.property  name="extension"
	 */
	private String extension;
	/**
	 * @uml.property  name="delimiter"
	 */
	private String delimiter;
	/**
	 * @uml.property  name="reverse"
	 */
	private boolean reverse;
	/**
	 * @uml.property  name="vfileList"
	 */
	private List vfileList;
	
	/**
	 * @return
	 * @uml.property  name="vfileList"
	 */
	public List getVfileList() {
		return vfileList;
	}
	/**
	 * @param vfileList
	 * @uml.property  name="vfileList"
	 */
	public void setVfileList(List vfileList) {
		this.vfileList = vfileList;
	}
	/**
	 * @return
	 * @uml.property  name="extension"
	 */
	public String getExtension() {
		return extension;
	}
	/**
	 * @param extension
	 * @uml.property  name="extension"
	 */
	public void setExtension(String extension) {
		this.extension = extension;
	}
	/**
	 * @return
	 * @uml.property  name="delimiter"
	 */
	public String getDelimiter() {
		return delimiter;
	}
	/**
	 * @param delimiter
	 * @uml.property  name="delimiter"
	 */
	public void setDelimiter(String delimiter) {
		this.delimiter = delimiter;
	}
	/**
	 * @return
	 * @uml.property  name="pathName"
	 */
	public String getPathName() {
		return pathName;
	}
	/**
	 * @param pathName
	 * @uml.property  name="pathName"
	 */
	public void setPathName(String pathName) {
		this.pathName = pathName;
	}
	/**
	 * @return
	 * @uml.property  name="searchName"
	 */
	public String getSearchName() {
		return searchName;
	}
	/**
	 * @param searchName
	 * @uml.property  name="searchName"
	 */
	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}
	/**
	 * @return
	 * @uml.property  name="key"
	 */
	public int getKey() {
		return key;
	}
	/**
	 * @param key
	 * @uml.property  name="key"
	 */
	public void setKey(int key) {
		this.key = key;
	}
	/**
	 * @return
	 * @uml.property  name="method"
	 */
	public String getMethod() {
		return method;
	}
	/**
	 * @param method
	 * @uml.property  name="method"
	 */
	public void setMethod(String method) {
		this.method = method;
	}
	/**
	 * @return
	 * @uml.property  name="sort"
	 */
	public int getSort() {
		return sort;
	}
	/**
	 * @param sort
	 * @uml.property  name="sort"
	 */
	public void setSort(int sort) {
		this.sort = sort;
	}
	/**
	 * @return
	 * @uml.property  name="reverse"
	 */
	public boolean isReverse() {
		return reverse;
	}
	/**
	 * @param reverse
	 * @uml.property  name="reverse"
	 */
	public void setReverse(boolean reverse) {
		this.reverse = reverse;
	}
	
}
