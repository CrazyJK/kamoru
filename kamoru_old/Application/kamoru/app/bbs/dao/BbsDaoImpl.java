package kamoru.app.bbs.dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.upload.FormFile;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.opensymphony.module.propertyset.xml.XMLPropertySet;

import kamoru.app.bbs.bean.Article;
import kamoru.frmwk.config.ConfigBag;
import kamoru.frmwk.db.ibatis.SqlMapFactory;
import kamoru.frmwk.struts.upload.UploadUtils;
import kamoru.frmwk.util.ArrayUtils;

public class BbsDaoImpl implements BbsDao {

	Log logger = LogFactory.getLog(this.getClass());
	
	@Override
	public List getArticleList(int category, String title, String writer,
			String tag, int pageNumbers, int numberOfArticlesPerPage) throws Exception {
		logger.info("Start");
		Map conditionMap = new HashMap(); 
		if(category != 0) {
			conditionMap.put("category", "AND categoryid = " + category);
		}
		if(StringUtils.isNotBlank(title)) {
			conditionMap.put("title", "AND UPPER(title) like '%" + title.toUpperCase() + "%'");
		}
		if(StringUtils.isNotBlank(writer)) {
			conditionMap.put("writer", "AND UPPER(writer) like '%" + writer.toUpperCase() + "%'");
		}
		if(StringUtils.isNotBlank(tag)) {
			conditionMap.put("tag", "AND UPPER(tag) like '%" + tag.toUpperCase() + "%'");
		}
		conditionMap.put("pageNumbers", pageNumbers);
		conditionMap.put("numberOfArticlesPerPage", numberOfArticlesPerPage);
		
		SqlMapClient sqlMap = (SqlMapClient)SqlMapFactory.getSqlMap("kamoru");
		
		List list = sqlMap.queryForList("bbs.getArticleList", conditionMap);
		logger.info("list size" + list.size());
		return list;
	}

	@Override
	public Article getArticle(int category, int articleid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void createArticle(Article article) throws SQLException {
		logger.info("Start");
		logger.info(article.toString());
		// 0. Start Transaction.
		XMLPropertySet config = ConfigBag.getInstance().getConfig();
		String[] attachFilePath = null;
		String bodyFilePath = null;
		SqlMapClient sqlMap = null;
		try {
			sqlMap = SqlMapFactory.getSqlMap("kamoru");
			Integer articleid = (Integer)sqlMap.queryForObject("bbs.selectMaxArticleid", new Integer(article.getCategoryid()).intValue());
			article.setArticleid(articleid.intValue() + 1);
			logger.info("new Articleid is " + article.getArticleid());
			
			// 1. save uploaded attach file, if it is exist.
			String basePath = config.getString("file.storage.path");
			String path = article.getBodypath();
			File dir = new File(basePath + path);
			dir.mkdirs();
			logger.info("basePath:" + basePath + ", bodypath:" + path);
			
			FormFile[] files = article.getAttachFiles();
			attachFilePath = new String[ArrayUtils.getRealLength(files)];
			int idx = 0;
			for(int i=0; i<files.length; i++) {
				if(files[i] != null) {
					logger.info("attachFiles[" + i + "] save : " + basePath + path + files[i].getFileName());
					attachFilePath[idx++] = basePath + path + files[i].getFileName();
					UploadUtils.doFileUpload(files[i], basePath + path);
				}
			}
			
			// 2. save body file.
			String bodyFileName = article.getCategoryid() + "-" + article.getArticleid();
			String body = article.getBody();
			saveMessage(basePath + path + bodyFileName, body);
			bodyFilePath = basePath + path + bodyFileName; 
			logger.info("bodyFilePath : " + bodyFilePath);
			
			// 3. save header info in db.
			sqlMap.startTransaction();
			
			sqlMap.insert("bbs.insertArticle", article);
			sqlMap.commitTransaction();

		// 9. End Transaction.
		} catch(Exception e) {
			logger.error(e);
			e.printStackTrace();
			logger.info("rollback start");
			if(sqlMap != null) {
				sqlMap.endTransaction();
			}
			deleteFile(bodyFilePath);
			for(int i=0; i<attachFilePath.length; i++) {
				deleteFile(attachFilePath[i]);
			}
			logger.info("rollback end");
		}
		logger.info("End");
	}

	@Override
	public void updateArticle(Article article) {
		// TODO Auto-generated method stub
	}

	@Override
	public void deleteArticle(Article article) {
		// TODO Auto-generated method stub

	}

	@Override
	public Article getNewArticle(int categoryid) {
		Article article = new Article();
		article.setCategoryid(categoryid);
		return article;
	}

	private void saveMessage(String filename, String msg) throws IOException {
		logger.info("attempt to save msg. filename=" + filename);
		PrintWriter out = new PrintWriter(new FileWriter(new File(filename)));
		out.write(msg);
		out.flush();
		out.close();
	}
	private void deleteFile(String path) {
		File f = new File(path);
		if(f.exists()) {
			f.delete();
		}
	}
}
