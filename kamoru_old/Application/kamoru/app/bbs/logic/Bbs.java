package kamoru.app.bbs.logic;

import java.sql.SQLException;
import java.util.List;

import kamoru.app.bbs.bean.Article;
import kamoru.app.bbs.form.BbsForm;

public interface Bbs {

	List getArticleList(BbsForm form) throws Exception;
	Article getArticle(BbsForm form);
	void createArticle(Article article) throws SQLException;
	void updateArticle(Article article);
	void deleteArticle(Article article);
	Article getNewArticle(int categoryid);
}
