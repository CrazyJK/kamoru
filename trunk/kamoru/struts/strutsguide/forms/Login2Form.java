package strutsguide.forms;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

public class Login2Form extends ActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1015329395224000126L;
	private String username = null;
	private String password = null;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
		ActionErrors errors = new ActionErrors();
		
		if(username == null || username.length() == 0) {
			errors.add("invalidUsernameError", new ActionMessage("error.invalidUsername", "사용자명을 입력해주세요."));
		} else if(username.indexOf(" ") >= 0 || username.indexOf("\t") >= 0 || username.indexOf("\n") >= 0) {
			errors.add("invalidUsernameError", new ActionMessage("error.invalidUsername", "사용자명은 공백을 포함할 수 없습니다."));
		}
		
		if(password == null | password.length() == 0) {
			errors.add("invalidPasswordError", new ActionMessage("error.invalidPassword"));
		}
		return errors;
	}
}
