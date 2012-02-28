package kamoru.app.bbs.form;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import kamoru.app.bbs.bean.Article;
import kamoru.frmwk.util.ArrayUtils;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

public class BbsForm extends ActionForm {

	private int    categoryid;
	
	private int    schArticleid, newArticleid;
	private String schWriter,	 newWriter;
	private String schWriterid,	 newWriterid;
	private String schTitle,	 newTitle;
	private String schTag,		 newTag;
	private String schBody,		 newBody;
	private String schBodypath,	 newBodypath;

	private FormFile[] newAttachFiles = new FormFile[10];
	
	private int    pageNumbers;
	private int    numberOfArticlesPerPage;

	private List   articleList;
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
	
	public int getCategoryid() {
		return categoryid;
	}
	public void setCategoryid(int categoryid) {
		this.categoryid = categoryid;
	}
	public int getSchArticleid() {
		return schArticleid;
	}
	public void setSchArticleid(int schArticleid) {
		this.schArticleid = schArticleid;
	}
	public int getNewArticleid() {
		return newArticleid;
	}
	public void setNewArticleid(int newArticleid) {
		this.newArticleid = newArticleid;
	}
	public String getSchWriter() {
		return schWriter;
	}
	public void setSchWriter(String schWriter) {
		this.schWriter = schWriter;
	}
	public String getNewWriter() {
		return newWriter;
	}
	public void setNewWriter(String newWriter) {
		this.newWriter = newWriter;
	}
	public String getSchWriterid() {
		return schWriterid;
	}
	public void setSchWriterid(String schWriterid) {
		this.schWriterid = schWriterid;
	}
	public String getNewWriterid() {
		return newWriterid;
	}
	public void setNewWriterid(String newWriterid) {
		this.newWriterid = newWriterid;
	}
	public String getSchTitle() {
		return schTitle;
	}
	public void setSchTitle(String schTitle) {
		this.schTitle = schTitle;
	}
	public String getNewTitle() {
		return newTitle;
	}
	public void setNewTitle(String newTitle) {
		this.newTitle = newTitle;
	}
	public String getSchTag() {
		return schTag;
	}
	public void setSchTag(String schTag) {
		this.schTag = schTag;
	}
	public String getNewTag() {
		return newTag;
	}
	public void setNewTag(String newTag) {
		this.newTag = newTag;
	}
	public String getSchBody() {
		return schBody;
	}
	public void setSchBody(String schBody) {
		this.schBody = schBody;
	}
	public String getNewBody() {
		return newBody;
	}
	public void setNewBody(String newBody) {
		this.newBody = newBody;
	}
	public String getSchBodypath() {
		return schBodypath;
	}
	public void setSchBodypath(String schBodypath) {
		this.schBodypath = schBodypath;
	}
	public String getNewBodypath() {
		return newBodypath;
	}
	public void setNewBodypath(String newBodypath) {
		this.newBodypath = newBodypath;
	}
	public int getPageNumbers() {
		return pageNumbers;
	}
	public void setPageNumbers(int pageNumbers) {
		this.pageNumbers = pageNumbers;
	}
	public int getNumberOfArticlesPerPage() {
		return numberOfArticlesPerPage;
	}
	public void setNumberOfArticlesPerPage(int numberOfArticlesPerPage) {
		this.numberOfArticlesPerPage = numberOfArticlesPerPage;
	}
	public List getArticleList() {
		return articleList;
	}
	public void setArticleList(List articleList) {
		this.articleList = articleList;
	}
	public Article getArticle() {
		return article;
	}
	public void setArticle(Article article) {
		this.article = article;
	}
	public FormFile[] getNewAttachFiles() {
		return newAttachFiles;
	}
	public void setNewAttachFiles(FormFile[] newAttachFiles) {
		this.newAttachFiles = newAttachFiles;
	}
	
}
