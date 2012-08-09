package kamoru.app.vlist.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kamoru.app.vlist.form.VlistForm;
import kamoru.app.vlist.logic.VlistLogic;
import kamoru.frmwk.util.StringUtils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.MappingDispatchAction;

/**
 * @author  kamoru
 */
public class VlistAction extends MappingDispatchAction {

	protected static final Log logger = LogFactory.getLog(VlistAction.class);
	
	/**
	 * @uml.property  name="vlistLogic"
	 * @uml.associationEnd  
	 */
	private VlistLogic vlistLogic;

	/**
	 * @param vlistLogic
	 * @uml.property  name="vlistLogic"
	 */
	public void setVlistLogic(VlistLogic vlistLogic) {
		this.vlistLogic = vlistLogic;
	}
	
	public ActionForward vlist(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("Start");
		try {
			VlistForm vlistForm = (VlistForm)form;

			List list = this.vlistLogic.getVlist(vlistForm);

			vlistForm.setVfileList(list);

			return mapping.findForward("success");
		} catch(Exception e) {
			logger.error("list error", e);
			throw e;
		}
	}
}
