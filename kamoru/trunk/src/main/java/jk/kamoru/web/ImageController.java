package jk.kamoru.web;

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

import jk.kamoru.app.image.domain.PictureType;
import jk.kamoru.app.image.service.ImageService;
import jk.kamoru.app.video.VideoCore;

@Controller
@RequestMapping("/image")
public class ImageController {

	@Autowired
	private ImageService imageService;
	
	@RequestMapping(method=RequestMethod.GET)
	public String viewImageList(Model model, @RequestParam(value="n", required=false, defaultValue="-1") int n) {
		int count = imageService.getImageSourceSize();
		model.addAttribute("imageCount", count);
		model.addAttribute("selectedNumber", n > count ? count -1 : n);
		return "/picture/slide";
	}

	@RequestMapping(value="/slides", method=RequestMethod.GET)
	public String slides(Model model, @RequestParam(value="n", required=false, defaultValue="-1") int n) {
		int count = imageService.getImageSourceSize();
		model.addAttribute("imageCount", count);
		model.addAttribute("selectedNumber", n > count ? count -1 : n);
		return "/picture/slidesjs";
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
		headers.setCacheControl("max-age=" + VideoCore.WEBCACHETIME_SEC);
		headers.setContentLength(imageBytes.length);
		headers.setContentType(type);
		headers.setDate(today + VideoCore.WEBCACHETIME_MILI);
		headers.setExpires(today + VideoCore.WEBCACHETIME_MILI);
		headers.setLastModified(today - VideoCore.WEBCACHETIME_MILI);

		return new HttpEntity<byte[]>(imageBytes, headers);		
	}
	
}
