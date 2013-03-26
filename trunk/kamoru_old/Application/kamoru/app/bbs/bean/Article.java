package kamoru.app.bbs.bean;

import java.io.File;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

import org.apache.struts.upload.FormFile;

/**
 * @author  kamoru
 */
public class Article implements Serializable {

	/**
	 * @uml.property  name="categoryid"
	 */
	private int    	categoryid;
	/**
	 * @uml.property  name="articleid"
	 */
	private int    	articleid;
	/**
	 * @uml.property  name="title"
	 */
	private String 	title;
	/**
	 * @uml.property  name="bodypath"
	 */
	private String 	bodypath;
	/**
	 * @uml.property  name="body"
	 */
	private String 	body;
	/**
	 * @uml.property  name="attach"
	 */
	private int   	attach;
	/**
	 * @uml.property  name="readnum"
	 */
	private int    	readnum;
	/**
	 * @uml.property  name="likenum"
	 */
	private int    	likenum;
	/**
	 * @uml.property  name="tag"
	 */
	private String 	tag;
	/**
	 * @uml.property  name="writer"
	 */
	private String 	writer;
	/**
	 * @uml.property  name="writerid"
	 */
	private String 	writerid;
	/**
	 * @uml.property  name="created"
	 */
	private Date   	created;
	/**
	 * @uml.property  name="modified"
	 */
	private Date   	modified;
	/**
	 * @uml.property  name="attachFiles"
	 */
	private FormFile[]	attachFiles = new FormFile[10];
	/**
	 * @uml.property  name="comment"
	 */
	private int		comment;
	
	
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Article [categoryid=");
		builder.append(categoryid);
		builder.append(", articleid=");
		builder.append(articleid);
		builder.append(", title=");
		builder.append(title);
		builder.append(", bodypath=");
		builder.append(bodypath);
		builder.append(", body=");
		builder.append(body);
		builder.append(", attach=");
		builder.append(attach);
		builder.append(", readnum=");
		builder.append(readnum);
		builder.append(", likenum=");
		builder.append(likenum);
		builder.append(", tag=");
		builder.append(tag);
		builder.append(", writer=");
		builder.append(writer);
		builder.append(", writerid=");
		builder.append(writerid);
		builder.append(", created=");
		builder.append(created);
		builder.append(", modified=");
		builder.append(modified);
		builder.append(", attachFiles=");
		builder.append(Arrays.toString(attachFiles));
		builder.append(", comment=");
		builder.append(comment);
		builder.append("]");
		return builder.toString();
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
	 * @uml.property  name="bodypath"
	 */
	public String getBodypath() {
		return bodypath;
	}
	/**
	 * @param bodypath
	 * @uml.property  name="bodypath"
	 */
	public void setBodypath(String bodypath) {
		this.bodypath = bodypath;
	}
	/**
	 * @return
	 * @uml.property  name="readnum"
	 */
	public int getReadnum() {
		return readnum;
	}
	/**
	 * @param readnum
	 * @uml.property  name="readnum"
	 */
	public void setReadnum(int readnum) {
		this.readnum = readnum;
	}
	/**
	 * @return
	 * @uml.property  name="likenum"
	 */
	public int getLikenum() {
		return likenum;
	}
	/**
	 * @param likenum
	 * @uml.property  name="likenum"
	 */
	public void setLikenum(int likenum) {
		this.likenum = likenum;
	}
	/**
	 * @return
	 * @uml.property  name="title"
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title
	 * @uml.property  name="title"
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return
	 * @uml.property  name="attach"
	 */
	public int getAttach() {
		return attach;
	}
	/**
	 * @param attach
	 * @uml.property  name="attach"
	 */
	public void setAttach(int attach) {
		this.attach = attach;
	}
	/**
	 * @return
	 * @uml.property  name="tag"
	 */
	public String getTag() {
		return tag;
	}
	/**
	 * @param tag
	 * @uml.property  name="tag"
	 */
	public void setTag(String tag) {
		this.tag = tag;
	}
	/**
	 * @return
	 * @uml.property  name="writer"
	 */
	public String getWriter() {
		return writer;
	}
	/**
	 * @param writer
	 * @uml.property  name="writer"
	 */
	public void setWriter(String writer) {
		this.writer = writer;
	}
	/**
	 * @return
	 * @uml.property  name="created"
	 */
	public Date getCreated() {
		return created;
	}
	/**
	 * @param created
	 * @uml.property  name="created"
	 */
	public void setCreated(Date created) {
		this.created = created;
	}
	/**
	 * @return
	 * @uml.property  name="modified"
	 */
	public Date getModified() {
		return modified;
	}
	/**
	 * @param modified
	 * @uml.property  name="modified"
	 */
	public void setModified(Date modified) {
		this.modified = modified;
	}
	/**
	 * @return
	 * @uml.property  name="writerid"
	 */
	public String getWriterid() {
		return writerid;
	}
	/**
	 * @param writerid
	 * @uml.property  name="writerid"
	 */
	public void setWriterid(String writerid) {
		this.writerid = writerid;
	}
	/**
	 * @return
	 * @uml.property  name="articleid"
	 */
	public int getArticleid() {
		return articleid;
	}
	/**
	 * @param articleid
	 * @uml.property  name="articleid"
	 */
	public void setArticleid(int articleid) {
		this.articleid = articleid;
	}
	/**
	 * @return
	 * @uml.property  name="comment"
	 */
	public int getComment() {
		return comment;
	}
	/**
	 * @param comment
	 * @uml.property  name="comment"
	 */
	public void setComment(int comment) {
		this.comment = comment;
	}
	/**
	 * @return
	 * @uml.property  name="attachFiles"
	 */
	public FormFile[] getAttachFiles() {
		return attachFiles;
	}
	/**
	 * @param attachFiles
	 * @uml.property  name="attachFiles"
	 */
	public void setAttachFiles(FormFile[] attachFiles) {
		this.attachFiles = attachFiles;
	}
	/**
	 * @return
	 * @uml.property  name="body"
	 */
	public String getBody() {
		return body;
	}
	/**
	 * @param body
	 * @uml.property  name="body"
	 */
	public void setBody(String body) {
		this.body = body;
	}
	
	
}
