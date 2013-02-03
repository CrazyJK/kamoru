package kamoru.app.spring.av;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AVController {

	private static final Logger logger = LoggerFactory.getLogger(AVController.class);
	
	@Autowired
	private AVService service;
	
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
		List<AVOpus> list = service.getAV(params);
		Map<String, Integer> actressMap = service.getActressMap();
		Map<String, Integer> studioMap  = service.getStudioMap();
		
		model.addAttribute("list", list);
		model.addAttribute("actressMap", actressMap);
		model.addAttribute("studioMap", studioMap);
		model.addAttribute("params", params);
		
		return "av";
	}
	
	@RequestMapping(value="/av/image")
	public String image(Model model, 
			@RequestParam(value="opus", required=false, defaultValue="") String opus) {
		
		File imageFile = service.getImageFile(opus, model);
		model.addAttribute("imageFile", imageFile);
		return "image";
	}
	
	@RequestMapping(value="/av/action")
	public String action(Model model, @RequestParam("opus") String opus, @RequestParam("mode") String mode) {
		String result = service.action(mode, opus);
		model.addAttribute("result", result);
		return "action";
	}
	
	@RequestMapping(value="/av/overview")
	public String overview(Model model, @RequestParam("opus") String opus) {
		String overview = service.getOverview(opus);
		model.addAttribute("overview", overview);
		model.addAttribute("opus", opus);
		return "overview";
	}

	@RequestMapping(value="/av/overviewSave")
	public String overviewSave(Model model, @RequestParam("opus") String opus, @RequestParam("overViewTxt") String overViewTxt) {
		service.getOverviewSave(opus, overViewTxt);
		return "overviewSave";
	}
}
