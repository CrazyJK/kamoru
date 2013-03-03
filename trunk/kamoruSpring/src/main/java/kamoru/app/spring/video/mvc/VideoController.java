package kamoru.app.spring.video.mvc;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import kamoru.app.spring.video.domain.Video;
import kamoru.app.spring.video.service.VideoService;
import kamoru.app.spring.video.util.VideoUtils;

import org.apache.commons.io.FileUtils;
import org.apache.tika.Tika;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.util.CookieGenerator;

@Controller
public class VideoController {

	private static final Logger logger = LoggerFactory.getLogger(VideoController.class);
	
	@Autowired
	private VideoService videoService;
	
	@RequestMapping(value="/video")
	public String av(Model model, @RequestParam Map<String, String> params) {
		List<Video> videoList =  videoService.getVideoListByParams(params);
		String opusArrayStyleString = VideoUtils.getOpusArrayStyleString(videoList);
		model.addAttribute("videoList", videoList);
		model.addAttribute("opusArray", opusArrayStyleString);
		model.addAttribute("actressMap", videoService.getActressMap());
		model.addAttribute("studioMap", videoService.getStudioMap());
		model.addAttribute("params", params);
		return "video/video";
	}

	@RequestMapping(value="/video/{opus}", method=RequestMethod.GET)
	public String showAVOpus(Model model, @PathVariable String opus) {
		model.addAttribute("video", videoService.getVideo(opus));
		return "video/opus";
	}

	@RequestMapping(value="/video/{opus}", method=RequestMethod.DELETE)
	public void doDeleteVideo(@PathVariable("opus") String opus) {
		videoService.deleteVideo(opus);
	}

	@RequestMapping(value="/video/{opus}/cover", method=RequestMethod.GET)
	public HttpEntity<byte[]> image(@PathVariable String opus) throws IOException {
		File imageFile = videoService.getVideoCoverFile(opus);
		byte[] imageBytes = FileUtils.readFileToByteArray(imageFile);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.parseMediaType(new Tika().detect(imageFile)));
		headers.setContentLength(imageBytes.length);
		headers.setCacheControl("max-age=" + 86400);
		headers.setDate(new Date().getTime() + 86400*1000l);
		headers.setExpires(new Date().getTime() + 86400*1000l);
		return new HttpEntity<byte[]>(imageBytes, headers);
	}

	@RequestMapping(value="/video/{opus}/overview", method=RequestMethod.GET)
	public String showOverview(Model model, @PathVariable("opus") String opus) {
		model.addAttribute("video", videoService.getVideo(opus));
		return "video/overview";
	}
	@RequestMapping(value="/video/{opus}/overview", method=RequestMethod.POST) //	@ResponseStatus(HttpStatus.NO_CONTENT)
	public String doSaveOverview(@PathVariable("opus") String opus, @RequestParam("overViewTxt") String overViewTxt) {
		videoService.saveVideoOverview(opus, overViewTxt);
		return "video/overviewSave";
	}
	@RequestMapping(value="/video/{opus}/play", method=RequestMethod.GET)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void doPlayVideo(@PathVariable String opus) {
		videoService.playVideo(opus);
	}

	@RequestMapping(value="/video/{opus}/subtitles", method=RequestMethod.GET)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void doEditSubtitles(@PathVariable String opus) {
		videoService.editVideoSubtitles(opus);
	}
	
	@RequestMapping(value="/video/actress", method=RequestMethod.GET)
	public String showActress(Model model) {
		model.addAttribute(videoService.getActressMap());
		return "video/actress";
	}
	
	@RequestMapping(value="/video/actress/{actress}", method=RequestMethod.GET)
	public String showVideoListByActress(Model model, @PathVariable String actress) {
		model.addAttribute(videoService.getVideoListByActress(actress));
		return "video/list";
	}
	
	@RequestMapping(value="/video/studio", method=RequestMethod.GET)
	public String showStudio(Model model) {
		model.addAttribute(videoService.getStudioMap());
		return "video/studio";
	}
	
	@RequestMapping(value="/video/studio/{studio}", method=RequestMethod.GET)
	public String showVideoListByStudio(Model model, @PathVariable String studio) {
		model.addAttribute(videoService.getVideoListByStudio(studio));
		return "video/list";
	}
	
	@RequestMapping(value="/video/title/{title}", method=RequestMethod.GET)
	public String showVideoListByTitle(Model model, @PathVariable String title) {
		model.addAttribute(videoService.getVideoListByTitle(title));
		return "video/list";
	}

	@RequestMapping(value="/video/bgimage", method=RequestMethod.GET)
	public void showBGImage(@RequestParam(value="curr", required=false) String curr,
			HttpServletResponse response) throws IOException {
		logger.info("start");
		File imageFile = videoService.getBGImageFile(curr);
		Tika tika = new Tika();
	    String mimeType = tika.detect(imageFile);
		response.setContentType(mimeType);
		response.getOutputStream().write(FileUtils.readFileToByteArray(imageFile));
		response.getOutputStream().flush();
		response.getOutputStream().close();
	}
	
	@RequestMapping(value="/image/slide")
	public String showBGImageSlide() {
		
		return "image/slide";
	}
}
