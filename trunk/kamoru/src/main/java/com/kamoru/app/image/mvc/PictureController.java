package com.kamoru.app.image.mvc;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Random;

import com.kamoru.app.image.domain.PictureType;
import com.kamoru.app.image.service.ImageService;
import com.kamoru.app.image.source.ImageSource;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/image")
public class PictureController {

	@Autowired
	private ImageService imageService;
	
	@RequestMapping(value="/list")
	public String viewImageList(Model model) {
		int count = imageService.getImageSourceSize();
		model.addAttribute("imageCount", count);
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
	
	@RequestMapping(value="/random")
	public HttpEntity<byte[]> viewImageRandom() {
		int random = new Random().nextInt(imageService.getImageSourceSize());
		byte[] imageBytes = imageService.getImage(random).getImageBytes(PictureType.MASTER);
		
		return getImageEntity(imageBytes, MediaType.IMAGE_JPEG);
	}
	
	private HttpEntity<byte[]> getImageEntity(byte[] imageBytes, MediaType type) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(type);
		headers.setContentLength(imageBytes.length);
		headers.setCacheControl("max-age=" + 86400);
		headers.setDate(new Date().getTime() + 86400*1000l);
		headers.setExpires(new Date().getTime() + 86400*1000l);

		return new HttpEntity<byte[]>(imageBytes, headers);		
	}
	
}
