package jk.kamoru.web;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import jk.kamoru.util.StringUtils;
import jk.springframework.context.support.ReloadableResourceBundleMessageSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

	@RequestMapping("/")
	public String root() {
		return "root";
	}
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping("/home")
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);

		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL, locale);

		model.addAttribute("serverTime", dateFormat.format(new Date()));
		return "home";
	}

	@RequestMapping(value = "/requestMappingList")
	public String requestMapping(HttpServletRequest request, Model model, @RequestParam(value="sort", required=false, defaultValue="P") final String sort) {
		logger.trace("Request mapping list");

		ConfigurableApplicationContext cac = (ConfigurableApplicationContext) request
				.getSession()
				.getServletContext()
				.getAttribute(FrameworkServlet.SERVLET_CONTEXT_PREFIX + "appServlet");
		RequestMappingHandlerMapping rmhm = cac.getBean(RequestMappingHandlerMapping.class);

		List<Map<String, String>> mappingList = new ArrayList<Map<String, String>>();
		
		for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : rmhm.getHandlerMethods().entrySet()) {
			Map<String, String> mappingData = new HashMap<String, String>();
			mappingData.put("reqPattern", StringUtils.substringBetween(entry.getKey().getPatternsCondition().toString(), "[", "]"));
			mappingData.put("reqMethod",  StringUtils.substringBetween(entry.getKey().getMethodsCondition().toString(), "[", "]"));
			mappingData.put("beanType",   StringUtils.substringAfterLast(entry.getValue().getBeanType().getName(), "."));
			mappingData.put("beanMethod", entry.getValue().getMethod().getName());

			mappingList.add(mappingData);
		}
		Collections.sort(mappingList, new Comparator<Map<String, String>>() {
			@Override
			public int compare(Map<String, String> o1, Map<String, String> o2) {
				if (sort.equals("P")) {
					return StringUtils.compareTo(o1.get("reqPattern"), o2.get("reqPattern"));
				}
				else if (sort.equals("M")) {
					return StringUtils.compareTo(o1.get("reqMethod"), o2.get("reqMethod"));
				}
				else if (sort.equals("C")) {
					int firstCompare = StringUtils.compareTo(o1.get("beanType"), o2.get("beanType"));
					int secondCompare = StringUtils.compareTo(o1.get("beanMethod"), o2.get("beanMethod"));
					return firstCompare == 0 ? secondCompare : firstCompare;
				}
				else {
					return StringUtils.compareTo(o1.get("reqPattern"), o2.get("reqPattern"));
				}
			}
		});
		model.addAttribute("mappingList", mappingList);
		return "util/requestMappingList";
	}
	
	@RequestMapping("/hitMessageCodeList")
	public String hitMessageCodeList(Model model) {
		logger.trace("hit Message Code List");
		model.addAttribute("hitMessageCodeMap", ReloadableResourceBundleMessageSource.hitMessageCodeMap);
		return "util/hitMessageCodeList";
	}
	
	@RequestMapping("/error")
	public void error() {
		throw new RuntimeException("error");
	}

}
