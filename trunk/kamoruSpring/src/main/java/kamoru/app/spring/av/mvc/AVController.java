package kamoru.app.spring.av.mvc;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import kamoru.app.spring.av.domain.AVOpus;
import kamoru.app.spring.av.service.AVService;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class AVController {

	private static final Logger logger = LoggerFactory.getLogger(AVController.class);
	
	@Autowired
	private AVService avService;
	
	@RequestMapping(value="/av")
	public String av(Model model, @RequestParam Map<String, String> params ) {
//			@RequestParam(value="selectedBasePathId", required=false, defaultValue="null") String[] selectedBasePathId,
//			@RequestParam(value="studio", required=false, defaultValue="null") String studio,
//			@RequestParam(value="opus", required=false, defaultValue="null") String opus,
//			@RequestParam(value="title", required=false, defaultValue="null") String title,
//			@RequestParam(value="actress", required=false, defaultValue="null") String actress,
//			@RequestParam(value="addCond", required=false, defaultValue="false") boolean addCond,
//			@RequestParam(value="existVideo", required=false, defaultValue="false") boolean existVideo,
//			@RequestParam(value="existSubtitles", required=false, defaultValue="false") boolean existSubtitles,
//			@RequestParam(value="sortMethod", required=false, defaultValue="null") String sortMethod,
//			@RequestParam(value="sortReverse", required=false, defaultValue="false") boolean sortReverse,
//			@RequestParam(value="useCache", required=false, defaultValue="false") boolean useCache
		logger.info("start");
		
//		List<AVOpus> list = ctrl.getAV(selectedBasePathId, studio, opus, title, actress, addCond, existVideo, existSubtitles, sortMethod, sortReverse, useCache);
		List<AVOpus> list = avService.getAV(params);
		Map<String, Integer> actressMap = avService.getActressMap();
		Map<String, Integer> studioMap  = avService.getStudioMap();
		
		model.addAttribute("list", list);
		model.addAttribute("actressMap", actressMap);
		model.addAttribute("studioMap", studioMap);
		model.addAttribute("params", params);
		
		return "av/av";
	}
//	RESTful URL
//	/av
	
	//	/av/{opus}			- GET : opus 전체보기
	@RequestMapping(value="/av/{opus}", method=RequestMethod.GET)
	public String showAVOpus(Model model, @PathVariable String opus) {
		AVOpus avOpus = avService.getAVOpus(opus);
		model.addAttribute(avOpus);
		return "/av/opus";
	}
	
	//	/av/{opus}/image	- GET : 이미지 보기
	@RequestMapping(value="/av/{opus}/image", method=RequestMethod.GET)
	public String showCover(Model model, @PathVariable String opus) {
		
		opus = "bg".equals(opus) ? "" : opus;
		File imageFile = avService.getImageFile(opus, model);
		model.addAttribute("imageFile", imageFile);
		return "av/image";
	}

/*	@RequestMapping("/av/image")
	public @ResponseBody byte[] image(@RequestParam(value="opus", required=false, defaultValue="") String opus, 
			HttpServletResponse response) throws IOException {

		File imageFile = avService.getImageFile(opus);

		response.setContentType(new MimetypesFileTypeMap().getContentType(imageFile.getName()));

		if(opus.length() == 0) {
			response.setDateHeader("Expires", new Date().getTime() + 86400*1000l);
			response.setHeader("Cache-Control", "max-age=" + 86400);
		}

		return FileUtils.readFileToByteArray(imageFile);
	}
*/
/*	@RequestMapping(value="/av/image/{opus}", method=RequestMethod.GET)
	public void image(@PathVariable String opus, HttpServletResponse response) throws IOException {

		opus = "bg".equals(opus) ? "" : opus;
		File imageFile = avService.getImageFile(opus);

		response.setContentType(new MimetypesFileTypeMap().getContentType(imageFile.getName()));
		if(opus.length() != 0) {
			response.setDateHeader("Expires", new Date().getTime() + 86400*1000l);
			response.setHeader("Cache-Control", "max-age=" + 86400);
		}
		response.getOutputStream().write(FileUtils.readFileToByteArray(imageFile));
		response.getOutputStream().flush();
		response.getOutputStream().close();
	}
*/
	@RequestMapping(value="/av/action")
	public String action(Model model, @RequestParam("opus") String opus, @RequestParam("mode") String mode) {
		String result = avService.action(mode, opus);
		model.addAttribute("result", result);
		return "av/action";
	}
	
	//	/av/{opus}/overview	- GET : 품평 보기, POST : 품평 수정
	@RequestMapping(value="/av/{opus}/overview", method=RequestMethod.GET)
	public String showOverview(Model model, @PathVariable("opus") String opus) {
		String overview = avService.getOverview(opus);
		model.addAttribute("overview", overview);
		model.addAttribute("opus", opus);
		return "av/overview";
	}

	@RequestMapping(value="/av/{opus}/overview", method=RequestMethod.POST)
	public void doSaveOverview(Model model, @PathVariable("opus") String opus, @RequestParam("overViewTxt") String overViewTxt) {
		avService.saveOverview(opus, overViewTxt);
	}

	@RequestMapping(value="/av/{opus}", method=RequestMethod.DELETE)
	public void doDeleteAV(Model model, @PathVariable("opus") String opus) {
		avService.deleteAV(opus);
	}

	
	//	/av/{opus}/play		- GET : play
	@RequestMapping(value="/av/{opus}/play", method=RequestMethod.GET)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void doPlayAV(@PathVariable String opus) {
		avService.playAV(opus);
	}
	
	//	/av/{opus}/smi		- GET : 자막 editor open
	@RequestMapping(value="/av/{opus}/smi", method=RequestMethod.GET)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void doEditAVSubtitles(@PathVariable String opus) {
		avService.editAVSubtitles(opus);
	}
	
	//	/av/random			- GET : random play
	@RequestMapping(value="/av/random", method=RequestMethod.GET)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void doPlayRandomAV(@PathVariable String opus) {
		avService.playRandomAV(opus);
	}
	
	//	/av/actress			- GET : actress 목록 보기
	@RequestMapping(value="/av/actress", method=RequestMethod.GET)
	public String showAVopusListByActress(Model model) {
		model.addAttribute(avService.getAVopusListByActress());
		return "av/actresslist";
	}
	
	//	/av/actress/{actress} - GET : 선택 actress 작품 보기
	@RequestMapping(value="/av/actress/{actress}", method=RequestMethod.GET)
	public String showAVopusListByActress(Model model, @PathVariable String actress) {
		model.addAttribute(avService.getAVopusListByActress(actress));
		return "av/actress";
	}
	
	//	/av/studio			- GET : studio 목록 보기
	@RequestMapping(value="/av/studio", method=RequestMethod.GET)
	public String showAVopusListByStudio(Model model) {
		model.addAttribute(avService.getAVopusListByStudio());
		return "av/studiolist";
	}
	
	//	/av/studio/{studio}	- GET : 선택 studio 작품 보기
	@RequestMapping(value="/av/studio/{studio}", method=RequestMethod.GET)
	public String showAVopusListByStudio(Model model, @PathVariable String studio) {
		model.addAttribute(avService.getAVopusListByStudio(studio));
		return "av/actress";
	}
	//	/av/title/{title}	- GET : 제목 검색하여 작품 보기
	@RequestMapping(value="/av/title/{title}", method=RequestMethod.GET)
	public String showAVopusListByTitle(Model model, @PathVariable String title) {
		model.addAttribute(avService.getAVopusListByTitle(title));
		return "av/title";
	}

}
