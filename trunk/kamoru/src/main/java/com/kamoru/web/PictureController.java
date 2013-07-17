package com.kamoru.web;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.kamoru.app.image.domain.PictureType;
import com.kamoru.app.image.service.ImageService;
import com.kamoru.app.video.VideoCore;

@Controller
@RequestMapping("/image")
public class PictureController {

	@Autowired
	private ImageService imageService;
	
	@RequestMapping(method=RequestMethod.GET)
	public String viewImageList(Model model, @RequestParam(value="n", required=false, defaultValue="-1") int n) {
		int count = imageService.getImageSourceSize();
		model.addAttribute("imageCount", count);
		model.addAttribute("selectedNumber", n > count ? count -1 : n);
		return "/picture/slide";
	}
	
	@RequestMapping(value="/{idx}/thumbnail")
	public HttpEntity<byte[]> viewImageThumbnail(@PathVariable int idx) {
		byte[] imageBytes = imageService.getImage(idx).getImageBytes(PictureType.THUMBNAIL);
		
		return getImageEntity(imageBytes, MediaType.IMAGE_GIF);
	}
	
	@RequestMapping(value="/{idx}/WEB")
	public HttpEntity<byte[]> viewImageWEB(@PathVariable int idx) {
		byte[] imageBytes = imageService.getImage(idx).getImageBytes(PictureType.WEB);
		
		return getImageEntity(imageBytes, MediaType.IMAGE_JPEG);
	}
	
	@RequestMapping(value="/{idx}")
	public HttpEntity<byte[]> viewImage(@PathVariable int idx) {
		byte[] imageBytes = imageService.getImage(idx).getImageBytes(PictureType.MASTER);
		
		return getImageEntity(imageBytes, MediaType.IMAGE_JPEG);
	}
	
	private HttpEntity<byte[]> getImageEntity(byte[] imageBytes, MediaType type) {
		long today = new Date().getTime();

		HttpHeaders headers = new HttpHeaders();
		headers.setCacheControl("max-age=" + VideoCore.WebCacheTime_sec);
		headers.setContentLength(imageBytes.length);
		headers.setContentType(type);
		headers.setDate(today + VideoCore.WebCacheTime_Mili);
		headers.setExpires(today + VideoCore.WebCacheTime_Mili);
		headers.setLastModified(today - VideoCore.WebCacheTime_Mili);

		return new HttpEntity<byte[]>(imageBytes, headers);		
	}
	
}
