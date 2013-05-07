package com.kamoru.app.video.mvc;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.kamoru.app.image.service.ImageService;
import com.kamoru.app.video.VideoCore;
import com.kamoru.app.video.domain.Sort;
import com.kamoru.app.video.domain.View;
import com.kamoru.app.video.domain.Video;
import com.kamoru.app.video.domain.VideoSearch;
import com.kamoru.app.video.service.VideoService;
import com.kamoru.app.video.util.VideoUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

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

		model.addAttribute("views", View.values());
		model.addAttribute("sorts", Sort.values());
		model.addAttribute("videoList", videoList);
		model.addAttribute("opusArray", VideoUtils.getOpusArrayStyleStringWithVideofile(videoList));
		model.addAttribute("actressList", videoService.getActressListOfVideoes(videoList));
		model.addAttribute("studioList", videoService.getStudioListOfVideoes(videoList));
		model.addAttribute("bgImageCount", imageService.getImageSourceSize());
		return "video/videoMain";
	}

	@RequestMapping(value="/list", method=RequestMethod.GET)
	public String showVideoList(Model model) {
		model.addAttribute("videoList", videoService.getVideoList());
		return "video/videoList";
	}

	@RequestMapping(value="/{opus}", method=RequestMethod.POST)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void videoPost() {
		logger.info("POST do not something yet");
	}

	@RequestMapping(value="/{opus}", method=RequestMethod.GET)
	public String showAVOpus(Model model, @PathVariable String opus) {
		model.addAttribute("video", videoService.getVideo(opus));
		return "video/videoDetail";
	}

	@RequestMapping(value="/{opus}", method=RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void doDeleteVideo(@PathVariable("opus") String opus) {
		logger.info(opus);
		videoService.deleteVideo(opus);
	}

	@RequestMapping(value="/{opus}/cover", method=RequestMethod.GET)
	public HttpEntity<byte[]> image(@PathVariable String opus, HttpServletResponse response) throws IOException {
		File imageFile = videoService.getVideoCoverFile(opus);
		if(imageFile == null) {
			response.sendRedirect("../no/cover");
			return null;
		}
		return httpEntity(videoService.getVideoCoverByteArray(opus), VideoUtils.getFileExtension(imageFile));
	}

	@RequestMapping(value="/no/cover", method=RequestMethod.GET)
	public HttpEntity<byte[]> noimage() {
		return httpEntity(videoService.getDefaultCoverFileByteArray(), "jpg");
	}

	private HttpEntity<byte[]> httpEntity(byte[] imageBytes, String suffix) {
		long today = new Date().getTime();
		
		HttpHeaders headers = new HttpHeaders();
		headers.setCacheControl("max-age=" + VideoCore.CacheTime_sec);
		headers.setContentLength(imageBytes.length);
		headers.setContentType(MediaType.parseMediaType("image/" + suffix));
		headers.setDate(		today + VideoCore.CacheTime_Mili);
		headers.setExpires(		today + VideoCore.CacheTime_Mili);
		headers.setLastModified(today - VideoCore.CacheTime_Mili);
		
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
