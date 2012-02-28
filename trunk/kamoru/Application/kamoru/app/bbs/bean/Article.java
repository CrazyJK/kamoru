package kamoru.app.bbs.bean;

import java.io.File;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

import org.apache.struts.upload.FormFile;

public class Article implements Serializable {

	private int    	categoryid;
	private int    	articleid;
	private String 	title;
	private String 	bodypath;
	private String 	body;
	private int   	attach;
	private int    	readnum;
	private int    	likenum;
	private String 	tag;
	private String 	writer;
	private String 	writerid;
	private Date   	created;
	private Date   	modified;
	private FormFile[]	attachFiles = new FormFile[10];
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
	public int getCategoryid() {
		return categoryid;
	}
	public void setCategoryid(int categoryid) {
		this.categoryid = categoryid;
	}
	public String getBodypath() {
		return bodypath;
	}
	public void setBodypath(String bodypath) {
		this.bodypath = bodypath;
	}
	public int getReadnum() {
		return readnum;
	}
	public void setReadnum(int readnum) {
		this.readnum = readnum;
	}
	public int getLikenum() {
		return likenum;
	}
	public void setLikenum(int likenum) {
		this.likenum = likenum;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getAttach() {
		return attach;
	}
	public void setAttach(int attach) {
		this.attach = attach;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public Date getModified() {
		return modified;
	}
	public void setModified(Date modified) {
		this.modified = modified;
	}
	public String getWriterid() {
		return writerid;
	}
	public void setWriterid(String writerid) {
		this.writerid = writerid;
	}
	public int getArticleid() {
		return articleid;
	}
	public void setArticleid(int articleid) {
		this.articleid = articleid;
	}
	public int getComment() {
		return comment;
	}
	public void setComment(int comment) {
		this.comment = comment;
	}
	public FormFile[] getAttachFiles() {
		return attachFiles;
	}
	public void setAttachFiles(FormFile[] attachFiles) {
		this.attachFiles = attachFiles;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	
	
}
