package com.kamoru.web;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.FrameworkServlet;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping("")
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "home";
	}
	
	@RequestMapping(value="/requestMappingList")
	public String requestMapping(HttpServletRequest request, Model model) {
		Map<String, String> handlerMethodMap = new TreeMap<String, String>();
		
		ConfigurableApplicationContext cac = (ConfigurableApplicationContext)request.getSession().getServletContext().getAttribute(FrameworkServlet.SERVLET_CONTEXT_PREFIX + "appServlet");
		RequestMappingHandlerMapping rmhm = cac.getBean(RequestMappingHandlerMapping.class);
		
		for(Map.Entry<RequestMappingInfo, HandlerMethod> hm : rmhm.getHandlerMethods().entrySet())
			handlerMethodMap.put(hm.getKey().toString(), hm.getValue().toString());
		
		model.addAttribute("handlerMethodMap", handlerMethodMap);
		return "requestMappingList";
	}
}
