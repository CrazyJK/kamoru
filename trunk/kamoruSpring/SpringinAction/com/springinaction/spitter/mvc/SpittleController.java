package com.springinaction.spitter.mvc;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.springinaction.spitter.domain.Spittle;
import com.springinaction.spitter.service.SpitterService;

@Controller
@RequestMapping("/spittles")
public class SpittleController {

	private SpitterService spitterService;

	@Inject
	public SpittleController(SpitterService spitterService) {
		this.spitterService = spitterService;
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public String getSpittle(@PathVariable("id") long id, Model model) {
		model.addAttribute(spitterService.getSpittleById(id));
		return "spittles/view";
	}
	
	@RequestMapping(value="{id}", method=RequestMethod.PUT)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void saveSpittle(@PathVariable("id") long id, @Valid Spittle spittle) {
		spitterService.saveSpittle(spittle);
	}

	@RequestMapping(value="{id}", method=RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteSpittle(@PathVariable("id") long id) {
		spitterService.deleteSpittle(id);
	}
	
	@RequestMapping(method=RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public @ResponseBody Spittle createSpittle(@Valid Spittle spittle, BindingResult result, HttpServletResponse response) throws BindException {
		if(result.hasErrors()) {
			throw new BindException(result);
		}
		
		spitterService.saveSpittle(spittle);
		
		response.setHeader("Locationn", "spittles" + spittle.getId());
		return spittle;
	}
}
