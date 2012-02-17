package strutsguide.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import strutsguide.beans.LoginProcess;
import strutsguide.beans.UserInfoBean;
import strutsguide.forms.Login2Form;

public class Login2Action extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		LoginProcess loginProcess = LoginProcess.getInstance();
		
		Login2Form login2Form = (Login2Form)form;
		
		String userName = login2Form.getUsername();
		String password = login2Form.getPassword();
		
		UserInfoBean userInfo = loginProcess.login(userName, password);
		
		ActionForward forward = null;
		
		if(userInfo == null) {
			ActionMessages messages = new ActionMessages();
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.invalidLogin"));
			saveMessages(request, messages);
			return mapping.getInputForward();
		}
		
		HttpSession session = request.getSession();
		session.setAttribute("userInfo", userInfo);
		
		forward = mapping.findForward("login2Form");
		
		return forward;
	}
}
