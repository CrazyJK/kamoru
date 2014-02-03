package jk.kamoru.web;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ModelAttribute;

public class AbstractController {

	@ModelAttribute("auth")
	public Authentication getAuth() {
		return SecurityContextHolder.getContext().getAuthentication();
	}

}
