package jk.kamoru.web;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ModelAttribute;

public abstract class AbstractController {

	@JsonIgnore
	@ModelAttribute("auth")
	public Authentication getAuth() {
		return SecurityContextHolder.getContext().getAuthentication();
	}

}
