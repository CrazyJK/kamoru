package com.springinaction.spitter.mvc;

import java.util.Map;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.springinaction.spitter.service.SpitterService;

@Controller
public class HomeController {
	
	public static final int DEFAULT_SPITTLES_PER_PAGE = 25;
	
	@Autowired
	private SpitterService spitterService;

//	public HomeController(SpitterService spitterService) {
//		this.spitterService = spitterService;
//	}

	@RequestMapping({"/home"})
	public String showHomePage(Map<String, Object> model) {
		model.put("spitters", spitterService.getRecentSpittles(DEFAULT_SPITTLES_PER_PAGE));
		return "spitter/home";
	}
}
