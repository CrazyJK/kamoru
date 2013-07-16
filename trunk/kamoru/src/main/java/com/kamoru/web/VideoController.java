package com.kamoru.web;

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
import com.kamoru.app.video.VideoException;
import com.kamoru.app.video.domain.Sort;
import com.kamoru.app.video.domain.View;
import com.kamoru.app.video.domain.Video;
import com.kamoru.app.video.domain.VideoSearch;
import com.kamoru.app.video.service.VideoService;
import com.kamoru.app.video.util.VideoUtils;

import org.apache.bcel.generic.NEW;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Video Collection controller<br>
 * data loading, searching, background-image setting
 * @author kamoru
 *
 */
@Controller
@RequestMapping("/video")
public class VideoController {

	protected static final Log logger = LogFactory.getLog(VideoController.class);
	
	@Autowired private ImageService imageService;
	@Autowired private VideoService videoService;

	@RequestMapping(value="/actress", method=RequestMethod.GET)
	public String actress(Model model) {
		logger.info(new String());
		model.addAttribute(videoService.getActressList());
		return "video/actressList";
	}

	@RequestMapping(value="/actress/{actress}", method=RequestMethod.GET)
	public String actressName(Model model, @PathVariable String actress) {
		logger.info(actress);
		model.addAttribute(videoService.getActress(actress));
		return "video/actressDetail";
	}

	@RequestMapping("/error")
	public void error() {
		throw new RuntimeException("error");
	}

	@RequestMapping("/videoError")
	public void errorVideo() {
		throw new VideoException("error");
	}

	private HttpEntity<byte[]> httpEntity(byte[] imageBytes, String suffix) {
		long today = new Date().getTime();
		
		HttpHeaders headers = new HttpHeaders();
		headers.setCacheControl("max-age=" + VideoCore.WebCacheTime_sec);
		headers.setContentLength(imageBytes.length);
		headers.setContentType(MediaType.parseMediaType("image/" + suffix));
		headers.setDate(		today + VideoCore.WebCacheTime_Mili);
		headers.setExpires(		today + VideoCore.WebCacheTime_Mili);
		headers.setLastModified(today - VideoCore.WebCacheTime_Mili);
		
		return new HttpEntity<byte[]>(imageBytes, headers);
	}

	@RequestMapping(value="/list", method=RequestMethod.GET)
	public String list(Model model) {
		logger.info(new String());
		model.addAttribute("videoList", videoService.getVideoList());
		return "video/videoList";
	}

	@RequestMapping(value="/no/cover", method=RequestMethod.GET)
	public HttpEntity<byte[]> noCover() {
		logger.info(new String());
		return httpEntity(videoService.getDefaultCoverFileByteArray(), "jpg");
	}

	@RequestMapping(value="/{opus}", method=RequestMethod.GET)
	public String opus(Model model, @PathVariable String opus) {
		logger.info(opus);
		model.addAttribute("video", videoService.getVideo(opus));
		return "video/videoDetail";
	}
	
	@RequestMapping(value="/{opus}/cover", method=RequestMethod.GET)
	public HttpEntity<byte[]> opusCover(@PathVariable String opus, HttpServletResponse response, @RequestHeader("User-Agent") String agent) throws IOException {
		logger.info(opus + " - agent:" + agent);
		boolean isChrome = agent.indexOf("Chrome") > -1;
		File imageFile = videoService.getVideoCoverFile(opus, isChrome);
		if(imageFile == null) {
			response.sendRedirect("../no/cover");
			return null;
		}
		return httpEntity(videoService.getVideoCoverByteArray(opus, isChrome), VideoUtils.getFileExtension(imageFile));
	}
	@RequestMapping(value="/{opus}", method=RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void opusDelete(@PathVariable("opus") String opus) {
		logger.info(opus);
		videoService.deleteVideo(opus);
	}
	@RequestMapping(value="/{opus}/overview", method=RequestMethod.GET)
	public String opusOverview(Model model, @PathVariable("opus") String opus) {
		logger.info(opus);
		model.addAttribute("video", videoService.getVideo(opus));
		return "video/videoOverview";
	}

	@RequestMapping(value="/{opus}/overview", method=RequestMethod.POST) //	@ResponseStatus(HttpStatus.NO_CONTENT)
	public String opusOverviewPost(@PathVariable("opus") String opus, @RequestParam("overViewTxt") String overViewTxt) {
		logger.info(opus + " - " + overViewTxt);
		videoService.saveVideoOverview(opus, overViewTxt);
		return "video/videoOverviewSave";
	}
	
	@RequestMapping(value="/{opus}/play", method=RequestMethod.GET)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void opusPlay(@PathVariable String opus) {
		logger.info(opus);
		videoService.playVideo(opus);
	}
	
	@RequestMapping(value="/{opus}", method=RequestMethod.POST)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void opusPost() {
		logger.info(new String());
		logger.info("POST do not something yet");
	}
	
	@RequestMapping(value="/{opus}/rank/{rank}", method=RequestMethod.PUT)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void opusRank(@PathVariable String opus, @PathVariable int rank) {
		logger.info(opus + " : " + rank);
		videoService.rankVideo(opus, rank);
	}
	
	@RequestMapping(value="/{opus}/subtitles", method=RequestMethod.GET)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void opusSubtitles(@PathVariable String opus) {
		logger.info(opus);
		videoService.editVideoSubtitles(opus);
	}
	
	@RequestMapping(value="/search", method=RequestMethod.GET)
	public String search(Model model, @RequestParam(value="q", required=false, defaultValue="") String query) {
		List<Video> foundVideoList = videoService.findVideoList(query);
		logger.info("query=" + query + " found count=" + foundVideoList.size());
		List<Map<String, String>> foundMapList = new ArrayList<Map<String, String>>();
		for (Video video : foundVideoList) {
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
	
	@RequestMapping(value="/studio", method=RequestMethod.GET)
	public String studio(Model model) {
		logger.info(new String());
		model.addAttribute(videoService.getStudioList());
		return "video/studioList";
	}

	@RequestMapping(value="/studio/{studio}", method=RequestMethod.GET)
	public String studioName(Model model, @PathVariable String studio) {
		logger.info(studio);
		model.addAttribute(videoService.getStudio(studio));
		return "video/studioDetail";
	}
	@RequestMapping(method=RequestMethod.GET)
	public String video(Model model, @ModelAttribute VideoSearch videoSearch) {
		logger.info(videoSearch);
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
	
	@RequestMapping(value="/history", method=RequestMethod.GET)
	public String history(Model model, @RequestParam(value="q", required=false, defaultValue="") String query) {
		List<String> foundHistoryList = videoService.findHistory(query);
		logger.info("query=" + query + " found count=" + foundHistoryList.size());
		
		List<Map<String, String>> foundMapList = new ArrayList<Map<String, String>>();
		for (String history : foundHistoryList) {
			String[] hisStrings = StringUtils.split(history, ",", 4);
			
			Map<String, String> map = new HashMap<String, String>();
			map.put("date", hisStrings[0]);
			map.put("opus", hisStrings[1]);
			map.put("act", hisStrings[2]);
			map.put("desc", hisStrings[3]);
			foundMapList.add(map);
		}

		
		model.addAttribute("historyList", foundMapList);
		return "video/history";
	}
}
