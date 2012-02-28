package kamoru.app.bbs.dao;

import java.sql.SQLException;
import java.util.List;

import kamoru.app.bbs.bean.Article;

public interface BbsDao {
	
	List getArticleList(int category, String title, String writer, String tag, int pageNumbers, int numberOfPostsPerPage) throws Exception;
	Article getArticle(int category, int articleid);
	
	void createArticle(Article article) throws SQLException;
	void updateArticle(Article article);
	void deleteArticle(Article article);
	Article getNewArticle(int categoryid);
}
