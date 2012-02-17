package kamoru.app.sample.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kamoru.app.sample.logic.SampleLogic;
import kamoru.app.sample.form.SampleForm;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.MappingDispatchAction;

public class SampleAction extends MappingDispatchAction {

	protected static final Log logger = LogFactory.getLog(SampleAction.class);
	
	private SampleLogic sampleLogic;
	
	public void setSampleLogic(SampleLogic sampleLogic) {
		this.sampleLogic = sampleLogic;
	}
	public ActionForward sample(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("Start index()");
		SampleForm sampleForm = (SampleForm)form;
		List sampleList = this.sampleLogic.getSampleList();
		sampleForm.setSampleList(sampleList);
		return mapping.findForward("success");
	}
	
}
