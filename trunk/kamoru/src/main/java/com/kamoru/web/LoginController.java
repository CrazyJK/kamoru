package com.kamoru.web;

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
	public String loginForm(@RequestParam(value="login_error", required=false, defaultValue="false") boolean login_error, HttpSession session, Model model) {
		if (login_error) {
			AuthenticationException exception = (AuthenticationException)session.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
			model.addAttribute("msg", exception.getMessage());
		}
		return "login/loginForm";
	}

}
