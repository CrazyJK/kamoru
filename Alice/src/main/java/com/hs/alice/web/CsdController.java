package com.hs.alice.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hs.alice.psa.csd.domain.RndRqst;
import com.hs.alice.psa.csd.migrate.CsdDataWrapper;

@Controller
@RequestMapping("/csd")
public class CsdController {

	@Autowired
	CsdDataWrapper csd;
	
	@RequestMapping("/rndrqst/count")
	@ResponseBody
	public int rndRqstCount() {
		return csd.getRndRqstCount();
	}
	
	@RequestMapping("/rndrqst")
	@ResponseBody
	public List<RndRqst> rndRqstList() {
		return csd.wrapSrRequest();
	}
	
	
}
