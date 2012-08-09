package kamoru.app.bbs.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kamoru.app.bbs.bean.Article;
import kamoru.app.bbs.form.BbsForm;
import kamoru.app.bbs.logic.Bbs;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.MappingDispatchAction;

/**
 * @author  kamoru
 */
public class BbsAction extends MappingDispatchAction {

	protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * @uml.property  name="bbs"
	 * @uml.associationEnd  
	 */
	private Bbs bbs;
	
	/**
	 * @param bbs
	 * @uml.property  name="bbs"
	 */
	public void setBbs(Bbs bbs) {
		this.bbs = bbs;
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("Start");
		BbsForm bbsForm = (BbsForm)form;
		if(bbsForm == null) {
			bbsForm = new BbsForm();
		}
		List list = this.bbs.getArticleList(bbsForm);
		bbsForm.setArticleList(list);
		logger.info("End");
		return mapping.findForward("success");
	}
	
	public ActionForward article(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		return mapping.findForward("success");
	}
	
	public ActionForward createArticle(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("Start");
		BbsForm bbsForm = (BbsForm)form;
		if(bbsForm == null) {
			throw new Exception("잘못된 접근! bbsForm=" + bbsForm);
		}
		int categoryid = bbsForm.getCategoryid();
		if(categoryid == 0) {
			throw new Exception("잘못된 접근! categoryid="+categoryid);
		}
		Article article = this.bbs.getNewArticle(categoryid);
		bbsForm.setArticle(article);
		return mapping.findForward("success");
	}
	
	public ActionForward updateArticle(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		return mapping.findForward("success");
	}
	
	public ActionForward deleteArticle(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		return mapping.findForward("success");
	}
	
	public ActionForward createArticleAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		BbsForm bbsForm = (BbsForm)form;
		logger.info(bbsForm.toString()); 
		Article article = bbsForm.populateArticle();
		this.bbs.createArticle(article);
		return mapping.findForward("success");
	}
	
}
