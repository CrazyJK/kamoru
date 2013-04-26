package com.hs.alice.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hs.alice.sr.domain.RndRqst;
import com.hs.alice.sr.service.SrService;
import com.hs.alice.web.security.AliceUserDetails;

@Controller
@RequestMapping("/csr")
public class CsrController {

	@Autowired SrService srService;
	
	@RequestMapping(method=RequestMethod.GET)
	public String csdHome() {
		return "csr/home";
	}
	
	@RequestMapping("/list")
	public String rndList(Model model) {
		String rndUrl = "http://csd.handysoft.co.kr:8080/addon/sso/bpmsso.jsp?returnURL=/rnd/rndindex.jsp&loginID=" 
				+ this.getPrincipal().getAuthUser().getRefkey();
		rndUrl = "http://csd.handysoft.co.kr:8080/rnd/totallist2.jsp";
		model.addAttribute("rndUrl", rndUrl);
		return "csr/rndList";
	}

	@RequestMapping("/rqst")
	public String rqstlist(Model model) {
		List<RndRqst> rndRqstList = this.srService.getRqstList();
		model.addAttribute("rqstlist", rndRqstList);
		return "csr/list";
	}
	@RequestMapping("/rqst/{rqstid}")
	public String getRqst(Model model, @PathVariable Integer rqstid) {
		RndRqst rqst = this.srService.getRqstById(rqstid);
		model.addAttribute("rqst", rqst);
		return "csr/rqst";
	}
	
	
	// ModelAttribute
	@ModelAttribute("currentUser")
	public AliceUserDetails currentUser() {
		return this.getPrincipal();
	}

	private AliceUserDetails getPrincipal() {
		return (AliceUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}
}
