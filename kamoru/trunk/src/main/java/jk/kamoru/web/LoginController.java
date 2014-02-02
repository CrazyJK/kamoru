package jk.kamoru.web;

import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

	@RequestMapping(value="/login") 
	public String loginForm(
			@RequestParam(value="error", required=false, defaultValue="false") boolean error,
			HttpSession session, 
			Model model, Locale locale) {
		if (error) {
			AuthenticationException exception = (AuthenticationException)session.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
			if (exception != null)
				model.addAttribute("login_msg", exception.getMessage());
			String lastReqMethod = (String)session.getAttribute("LAST_REQUEST_METHOD");
			if (lastReqMethod != null)
				model.addAttribute("access_msg", lastReqMethod + " 요청 처리 실패");
		}
		model.addAttribute("locale", locale);
		
		return "login/loginForm";
	}

	@RequestMapping("/accessDenied")
	public String accessDenied() {
		return "login/accessDenied";
	}
}
