package kamoru.app.video.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kamoru.app.video.av.AVOpus;
import kamoru.app.video.form.AVForm;
import kamoru.app.video.logic.AVLogic;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.MappingDispatchAction;

public class AVAction extends MappingDispatchAction {

	protected static final Log logger = LogFactory.getLog(AVAction.class);
	private AVLogic avLogic;

	public void setAvLogic(AVLogic avLogic) {
		this.avLogic = avLogic;
	}
	
	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		AVForm avForm = (AVForm)form;
		List<AVOpus> list 				= avLogic.getList(avForm);
		Map<String, Integer>  studioMap = avLogic.getStudioMap();
		Map<String, Integer> actressMap = avLogic.getActressMap();
		
		avForm.setAvlist(list);
		avForm.setStudioMap(studioMap);
		avForm.setActressMap(actressMap);
		return mapping.findForward("success");
	}
}
