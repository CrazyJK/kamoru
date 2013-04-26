package com.kamoru.app.video.mvc;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import com.kamoru.app.image.service.ImageService;
import com.kamoru.app.video.domain.Video;
import com.kamoru.app.video.domain.VideoSearch;
import com.kamoru.app.video.service.VideoService;
import com.kamoru.app.video.util.VideoUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.io.FileUtils;
import org.apache.tika.Tika;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.util.CookieGenerator;

@Controller
@RequestMapping("/video")
public class VideoController {

	private static final Logger logger = LoggerFactory.getLogger(VideoController.class);
	
	@Autowired
	private VideoService videoService;
	@Autowired
	private ImageService imageService;

	@RequestMapping(method=RequestMethod.GET)
	public String av(Model model, @ModelAttribute VideoSearch videoSearch) {
		List<Video> videoList =  videoService.searchVideo(videoSearch);
		String opusArrayStyleString = VideoUtils.getOpusArrayStyleString(videoList);

		model.addAttribute("videoList", videoList);
		model.addAttribute("opusArray", opusArrayStyleString);
		model.addAttribute("actressList", videoService.getActressList());
		model.addAttribute("studioList", videoService.getStudioList());
		model.addAttribute("bgImageCount", imageService.getImageSourceSize());
		return "video/videoMain";
	}
/*	
	@RequestMapping(method=RequestMethod.GET)
	public String av(Model model, @RequestParam Map<String, String> params) {
		List<Video> videoList =  videoService.getVideoListByParams(params);
		String opusArrayStyleString = VideoUtils.getOpusArrayStyleString(videoList);
		model.addAttribute("videoList", videoList);
		model.addAttribute("opusArray", opusArrayStyleString);
		model.addAttribute("actressList", videoService.getActressList());
		model.addAttribute("studioList", videoService.getStudioList());
		model.addAttribute("params", params);
		model.addAttribute("bgImageCount", imageService.getImageSourceSize());
		return "video/videoMain";
	}
*/
	@RequestMapping(value="/list", method=RequestMethod.GET)
	public String showVideoList(Model model) {
		model.addAttribute("videoList", videoService.getVideoList());
		return "video/videoList";
	}

		
	@RequestMapping(value="/{opus}", method=RequestMethod.GET)
	public String showAVOpus(Model model, @PathVariable String opus) {
		model.addAttribute("video", videoService.getVideo(opus));
		return "video/videoDetail";
	}

	@RequestMapping(value="/{opus}", method=RequestMethod.DELETE)
	public void doDeleteVideo(@PathVariable("opus") String opus) {
		videoService.deleteVideo(opus);
	}

	@RequestMapping(value="/{opus}/cover", method=RequestMethod.GET)
	public HttpEntity<byte[]> image(@PathVariable String opus) throws IOException {
//		File imageFile = videoService.getVideoCoverFile(opus);
//		byte[] imageBytes = FileUtils.readFileToByteArray(imageFile);
		byte[] imageBytes = videoService.getVideoCoverByteArray(opus);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.parseMediaType("image/webp"));
		headers.setContentLength(imageBytes.length);
		headers.setCacheControl("max-age=" + 86400);
		headers.setDate(new Date().getTime() + 86400*1000l);
		headers.setExpires(new Date().getTime() + 86400*1000l);
		return new HttpEntity<byte[]>(imageBytes, headers);
	}

	@RequestMapping(value="/{opus}/overview", method=RequestMethod.GET)
	public String showOverview(Model model, @PathVariable("opus") String opus) {
		model.addAttribute("video", videoService.getVideo(opus));
		return "video/videoOverview";
	}
	@RequestMapping(value="/{opus}/overview", method=RequestMethod.POST) //	@ResponseStatus(HttpStatus.NO_CONTENT)
	public String doSaveOverview(@PathVariable("opus") String opus, @RequestParam("overViewTxt") String overViewTxt) {
		videoService.saveVideoOverview(opus, overViewTxt);
		return "video/videoOverviewSave";
	}
	@RequestMapping(value="/{opus}/play", method=RequestMethod.GET)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void doPlayVideo(@PathVariable String opus) {
		videoService.playVideo(opus);
	}

	@RequestMapping(value="/{opus}/subtitles", method=RequestMethod.GET)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void doEditSubtitles(@PathVariable String opus) {
		videoService.editVideoSubtitles(opus);
	}
	
	@RequestMapping(value="/actress", method=RequestMethod.GET)
	public String showActressList(Model model) {
		model.addAttribute(videoService.getActressList());
		return "video/actressList";
	}
	
	@RequestMapping(value="/actress/{actress}", method=RequestMethod.GET)
	public String showActress(Model model, @PathVariable String actress) {
		model.addAttribute(videoService.getActress(actress));
		return "video/actressDetail";
	}
	
	@RequestMapping(value="/studio", method=RequestMethod.GET)
	public String showStudioList(Model model) {
		model.addAttribute(videoService.getStudioList());
		return "video/studioList";
	}
	
	@RequestMapping(value="/studio/{studio}", method=RequestMethod.GET)
	public String showStudio(Model model, @PathVariable String studio) {
		model.addAttribute(videoService.getStudio(studio));
		return "video/studioDetail";
	}
	
/*	@RequestMapping(value="/video/search", method=RequestMethod.GET)
	public String videoSearch() {
		return "video/search";
	}
*/
/*	
	@RequestMapping(value="/video/search.json", method=RequestMethod.GET)
	public ResponseEntity<String> findVideo(@RequestParam(value="q", required=false, defaultValue="") String query) {
		List<Video> foundVideoList = videoService.findVideoList(query);
		logger.info("query=" + query + " found count=" + foundVideoList.size());
		List<Map<String, String>> foundMapList = new ArrayList<Map<String, String>>();
		for(Video video : foundVideoList) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("opus", video.getOpus());
			map.put("title", video.getTitle());
			map.put("studio", video.getStudio().getName());
			map.put("actress", video.getActress());
			map.put("existVideo", String.valueOf(video.isExistVideoFileList()));
			map.put("existCover", String.valueOf(video.isExistCoverFile()));
			map.put("existSubtitles", String.valueOf(video.isExistSubtitlesFileList()));
			foundMapList.add(map);
		}
		JSONArray json = JSONArray.fromObject(foundMapList);
		
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Content-Type", "text/html; charset=UTF-8");
        return new ResponseEntity<String>(json.toString(), responseHeaders, HttpStatus.CREATED);		
	}
*/	
	@RequestMapping(value="/search", method=RequestMethod.GET)
	public String queryVideo(Model model, @RequestParam(value="q", required=false, defaultValue="") String query) {
		List<Video> foundVideoList = videoService.findVideoList(query);
		logger.info("query=" + query + " found count=" + foundVideoList.size());
		List<Map<String, String>> foundMapList = new ArrayList<Map<String, String>>();
		for(Video video : foundVideoList) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("opus", video.getOpus());
			map.put("title", video.getTitle());
			map.put("studio", video.getStudio().getName());
			map.put("actress", video.getActress());
			map.put("existVideo", String.valueOf(video.isExistVideoFileList()));
			map.put("existCover", String.valueOf(video.isExistCoverFile()));
			map.put("existSubtitles", String.valueOf(video.isExistSubtitlesFileList()));
			foundMapList.add(map);
		}
		model.addAttribute("videoList", foundMapList);
        return "video/search";		
	}

}
