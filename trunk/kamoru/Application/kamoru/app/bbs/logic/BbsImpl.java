package kamoru.app.bbs.logic;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import kamoru.app.bbs.bean.Article;
import kamoru.app.bbs.dao.BbsDao;
import kamoru.app.bbs.form.BbsForm;

public class BbsImpl implements Bbs {

	Log logger = LogFactory.getLog(this.getClass());
	
	private BbsDao bbsDao;
		
	public void setBbsDao(BbsDao bbsDao) {
		this.bbsDao = bbsDao;
	}

	@Override
	public List getArticleList(BbsForm form) throws Exception {
		logger.info("Start");
		try {
			int    category 			= form.getCategoryid();
			String title 				= form.getSchTitle();
			String writer 				= form.getSchWriter();
			String tag 					= form.getSchTag();
			int    pageNumbers 			= form.getPageNumbers();
			int	   numberOfArticlesPerPage 	= form.getNumberOfArticlesPerPage();
			logger.info(form.toString());
			List list = this.bbsDao.getArticleList(category, title, writer, tag, pageNumbers, numberOfArticlesPerPage); 
			logger.info("End");
			return list;
		} catch(Exception e) {
			logger.error(e);
			throw e;
		}
	}

	@Override
	public Article getArticle(BbsForm form) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void createArticle(Article article) throws SQLException {
		logger.info("Start");
		this.bbsDao.createArticle(article);
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
		return this.bbsDao.getNewArticle(categoryid);
	}

}
