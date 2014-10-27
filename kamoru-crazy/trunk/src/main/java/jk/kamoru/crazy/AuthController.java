package jk.kamoru.crazy;

import java.util.Locale;

import javax.servlet.http.HttpSession;

import lombok.extern.slf4j.Slf4j;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Slf4j
@RequestMapping("/auth") 
public class AuthController extends AbstractController {
	
	@RequestMapping("/login") 
	public String loginForm(Model model, Locale locale, HttpSession session,
			@RequestParam(value="error", required=false, defaultValue="false") boolean error) {
		log.debug("show login form");
		if (error) {
			AuthenticationException exception = (AuthenticationException)session.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
			if (exception != null)
				model.addAttribute("login_msg", exception.getMessage());
			String lastReqMethod = (String)session.getAttribute("LAST_REQUEST_METHOD");
			if (lastReqMethod != null)
				model.addAttribute("access_msg", lastReqMethod + " 요청 처리 실패");
		}
		model.addAttribute(locale);
		
		return "auth/loginForm";
	}

	@RequestMapping("/accessDenied")
	public String accessDenied() {
		log.debug("show access denied page");
		return "auth/accessDenied";
	}
	
	@RequestMapping("/expiredSession")
	public String expiredSession() {
		log.debug("show expired session page");
		return "auth/expiredSession";
	}
}
