package com.hs.alice.web;

import java.text.DateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hs.alice.auth.domain.AuthUser;

/**
 * Handles requests for the application home, login page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@RequestMapping(value = {"/", "/home"}, method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "home";
	}
	
	@RequestMapping("/userinfo")
	@ResponseBody
	public String userinfo(HttpSession session, Model model, HttpServletRequest request) {
		@SuppressWarnings("rawtypes")
		Enumeration attNames = session.getAttributeNames();
		while(attNames.hasMoreElements()) {
			String name = (String)attNames.nextElement();
			logger.info(name + " : " + session.getAttribute(name));
		}
		
		AuthUser user = getLoginUser();
		
		request.getUserPrincipal();
		request.isUserInRole("ROLE_USER");
		
		logger.info("User : " + user);
		
		return user.toString();
	}
	
	@RequestMapping(value="/login") 
	public String loginForm(@RequestParam(value="login_error", required=false, defaultValue="false") boolean login_error) {
		return "loginForm";
	}
	
	private AuthUser getLoginUser() {
		return (AuthUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}
	
}
