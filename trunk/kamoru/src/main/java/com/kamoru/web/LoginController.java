package com.kamoru.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

	@RequestMapping(value="/login") 
	public String loginForm(@RequestParam(value="login_error", required=false, defaultValue="false") boolean login_error) {
		return "login/loginForm";
	}

}
