package kamoru.app.bbs.form;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import kamoru.app.bbs.bean.Article;
import kamoru.frmwk.util.ArrayUtils;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

/**
 * @author  kamoru
 */
public class BbsForm extends ActionForm {

	/**
	 * @uml.property  name="categoryid"
	 */
	private int    categoryid;
	
	/**
	 * @uml.property  name="schArticleid"
	 */
	private int    schArticleid;

	/**
	 * @uml.property  name="newArticleid"
	 */
	private int newArticleid;
	/**
	 * @uml.property  name="schWriter"
	 */
	private String schWriter;

	/**
	 * @uml.property  name="newWriter"
	 */
	private String newWriter;
	/**
	 * @uml.property  name="schWriterid"
	 */
	private String schWriterid;

	/**
	 * @uml.property  name="newWriterid"
	 */
	private String newWriterid;
	/**
	 * @uml.property  name="schTitle"
	 */
	private String schTitle;

	/**
	 * @uml.property  name="newTitle"
	 */
	private String newTitle;
	/**
	 * @uml.property  name="schTag"
	 */
	private String schTag;

	/**
	 * @uml.property  name="newTag"
	 */
	private String newTag;
	/**
	 * @uml.property  name="schBody"
	 */
	private String schBody;

	/**
	 * @uml.property  name="newBody"
	 */
	private String newBody;
	/**
	 * @uml.property  name="schBodypath"
	 */
	private String schBodypath;

	/**
	 * @uml.property  name="newBodypath"
	 */
	private String newBodypath;

	/**
	 * @uml.property  name="newAttachFiles"
	 */
	private FormFile[] newAttachFiles = new FormFile[10];
	
	/**
	 * @uml.property  name="pageNumbers"
	 */
	private int    pageNumbers;
	/**
	 * @uml.property  name="numberOfArticlesPerPage"
	 */
	private int    numberOfArticlesPerPage;

	/**
	 * @uml.property  name="articleList"
	 */
	private List   articleList;
	/**
	 * @uml.property  name="article"
	 * @uml.associationEnd  
	 */
	private Article article;
	
	@Override
	public String toString() {
		return "BbsForm [categoryid=" + categoryid + ", schArticleid="
				+ schArticleid + ", newArticleid=" + newArticleid
				+ ", schWriter=" + schWriter + ", newWriter=" + newWriter
				+ ", schWriterid=" + schWriterid + ", newWriterid="
				+ newWriterid + ", schTitle=" + schTitle + ", newTitle="
				+ newTitle + ", schTag=" + schTag + ", newTag=" + newTag
				+ ", schBody=" + schBody + ", newBody=" + newBody
				+ ", schBodypath=" + schBodypath + ", newBodypath="
				+ newBodypath + ", newAttachFiles="
				+ Arrays.toString(newAttachFiles) + ", pageNumbers="
				+ pageNumbers + ", numberOfArticlesPerPage="
				+ numberOfArticlesPerPage + ", articleList=" + articleList
				+ ", article=" + article + "]";
	}
	
	public Article populateArticle() {
		Article article = new Article();
		article.setCategoryid(categoryid);
		article.setTitle(newTitle);
		article.setWriter(StringUtils.stripToEmpty(newWriter));
		article.setWriterid(StringUtils.stripToEmpty(newWriterid));
		article.setTag(newTag);
		article.setBody(newBody);
		article.setAttachFiles(newAttachFiles);
		article.setAttach(ArrayUtils.getRealLength(newAttachFiles));
		article.setCreated(new Date());
		article.setBodypath(DateFormatUtils.format(article.getCreated(), "yyyy/MM/dd/"));
		return article;
	}
	
	/**
	 * @return
	 * @uml.property  name="categoryid"
	 */
	public int getCategoryid() {
		return categoryid;
	}
	/**
	 * @param categoryid
	 * @uml.property  name="categoryid"
	 */
	public void setCategoryid(int categoryid) {
		this.categoryid = categoryid;
	}
	/**
	 * @return
	 * @uml.property  name="schArticleid"
	 */
	public int getSchArticleid() {
		return schArticleid;
	}
	/**
	 * @param schArticleid
	 * @uml.property  name="schArticleid"
	 */
	public void setSchArticleid(int schArticleid) {
		this.schArticleid = schArticleid;
	}
	/**
	 * @return
	 * @uml.property  name="newArticleid"
	 */
	public int getNewArticleid() {
		return newArticleid;
	}
	/**
	 * @param newArticleid
	 * @uml.property  name="newArticleid"
	 */
	public void setNewArticleid(int newArticleid) {
		this.newArticleid = newArticleid;
	}
	/**
	 * @return
	 * @uml.property  name="schWriter"
	 */
	public String getSchWriter() {
		return schWriter;
	}
	/**
	 * @param schWriter
	 * @uml.property  name="schWriter"
	 */
	public void setSchWriter(String schWriter) {
		this.schWriter = schWriter;
	}
	/**
	 * @return
	 * @uml.property  name="newWriter"
	 */
	public String getNewWriter() {
		return newWriter;
	}
	/**
	 * @param newWriter
	 * @uml.property  name="newWriter"
	 */
	public void setNewWriter(String newWriter) {
		this.newWriter = newWriter;
	}
	/**
	 * @return
	 * @uml.property  name="schWriterid"
	 */
	public String getSchWriterid() {
		return schWriterid;
	}
	/**
	 * @param schWriterid
	 * @uml.property  name="schWriterid"
	 */
	public void setSchWriterid(String schWriterid) {
		this.schWriterid = schWriterid;
	}
	/**
	 * @return
	 * @uml.property  name="newWriterid"
	 */
	public String getNewWriterid() {
		return newWriterid;
	}
	/**
	 * @param newWriterid
	 * @uml.property  name="newWriterid"
	 */
	public void setNewWriterid(String newWriterid) {
		this.newWriterid = newWriterid;
	}
	/**
	 * @return
	 * @uml.property  name="schTitle"
	 */
	public String getSchTitle() {
		return schTitle;
	}
	/**
	 * @param schTitle
	 * @uml.property  name="schTitle"
	 */
	public void setSchTitle(String schTitle) {
		this.schTitle = schTitle;
	}
	/**
	 * @return
	 * @uml.property  name="newTitle"
	 */
	public String getNewTitle() {
		return newTitle;
	}
	/**
	 * @param newTitle
	 * @uml.property  name="newTitle"
	 */
	public void setNewTitle(String newTitle) {
		this.newTitle = newTitle;
	}
	/**
	 * @return
	 * @uml.property  name="schTag"
	 */
	public String getSchTag() {
		return schTag;
	}
	/**
	 * @param schTag
	 * @uml.property  name="schTag"
	 */
	public void setSchTag(String schTag) {
		this.schTag = schTag;
	}
	/**
	 * @return
	 * @uml.property  name="newTag"
	 */
	public String getNewTag() {
		return newTag;
	}
	/**
	 * @param newTag
	 * @uml.property  name="newTag"
	 */
	public void setNewTag(String newTag) {
		this.newTag = newTag;
	}
	/**
	 * @return
	 * @uml.property  name="schBody"
	 */
	public String getSchBody() {
		return schBody;
	}
	/**
	 * @param schBody
	 * @uml.property  name="schBody"
	 */
	public void setSchBody(String schBody) {
		this.schBody = schBody;
	}
	/**
	 * @return
	 * @uml.property  name="newBody"
	 */
	public String getNewBody() {
		return newBody;
	}
	/**
	 * @param newBody
	 * @uml.property  name="newBody"
	 */
	public void setNewBody(String newBody) {
		this.newBody = newBody;
	}
	/**
	 * @return
	 * @uml.property  name="schBodypath"
	 */
	public String getSchBodypath() {
		return schBodypath;
	}
	/**
	 * @param schBodypath
	 * @uml.property  name="schBodypath"
	 */
	public void setSchBodypath(String schBodypath) {
		this.schBodypath = schBodypath;
	}
	/**
	 * @return
	 * @uml.property  name="newBodypath"
	 */
	public String getNewBodypath() {
		return newBodypath;
	}
	/**
	 * @param newBodypath
	 * @uml.property  name="newBodypath"
	 */
	public void setNewBodypath(String newBodypath) {
		this.newBodypath = newBodypath;
	}
	/**
	 * @return
	 * @uml.property  name="pageNumbers"
	 */
	public int getPageNumbers() {
		return pageNumbers;
	}
	/**
	 * @param pageNumbers
	 * @uml.property  name="pageNumbers"
	 */
	public void setPageNumbers(int pageNumbers) {
		this.pageNumbers = pageNumbers;
	}
	/**
	 * @return
	 * @uml.property  name="numberOfArticlesPerPage"
	 */
	public int getNumberOfArticlesPerPage() {
		return numberOfArticlesPerPage;
	}
	/**
	 * @param numberOfArticlesPerPage
	 * @uml.property  name="numberOfArticlesPerPage"
	 */
	public void setNumberOfArticlesPerPage(int numberOfArticlesPerPage) {
		this.numberOfArticlesPerPage = numberOfArticlesPerPage;
	}
	/**
	 * @return
	 * @uml.property  name="articleList"
	 */
	public List getArticleList() {
		return articleList;
	}
	/**
	 * @param articleList
	 * @uml.property  name="articleList"
	 */
	public void setArticleList(List articleList) {
		this.articleList = articleList;
	}
	/**
	 * @return
	 * @uml.property  name="article"
	 */
	public Article getArticle() {
		return article;
	}
	/**
	 * @param article
	 * @uml.property  name="article"
	 */
	public void setArticle(Article article) {
		this.article = article;
	}
	/**
	 * @return
	 * @uml.property  name="newAttachFiles"
	 */
	public FormFile[] getNewAttachFiles() {
		return newAttachFiles;
	}
	/**
	 * @param newAttachFiles
	 * @uml.property  name="newAttachFiles"
	 */
	public void setNewAttachFiles(FormFile[] newAttachFiles) {
		this.newAttachFiles = newAttachFiles;
	}
	
}
