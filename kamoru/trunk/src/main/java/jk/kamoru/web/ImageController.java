package jk.kamoru.web;

import java.util.Date;

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
import org.springframework.web.bind.annotation.ResponseStatus;

import jk.kamoru.app.image.domain.PictureType;
import jk.kamoru.app.image.service.ImageService;
import jk.kamoru.app.video.VideoCore;
import jk.kamoru.app.video.util.VideoUtils;

@Controller
@RequestMapping("/image")
public class ImageController extends AbstractController {

	@Autowired
	private ImageService imageService;
	
	@RequestMapping(method=RequestMethod.GET)
	public String viewImageList(Model model, @RequestParam(value="n", required=false, defaultValue="-1") int n) {
		int count = imageService.getImageSourceSize();
		model.addAttribute("imageCount", count);
		model.addAttribute("selectedNumber", n > count ? count -1 : n);
		model.addAttribute(imageService.getImageList());
		return "image/slide";
	}

	@RequestMapping(value="/slides", method=RequestMethod.GET)
	public String slides(Model model, @RequestParam(value="n", required=false, defaultValue="-1") int n) {
		int count = imageService.getImageSourceSize();
		model.addAttribute("imageCount", count);
		model.addAttribute("selectedNumber", n > count ? count -1 : n);
		return "image/slidesjs";
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
	public HttpEntity<byte[]> viewImageByRandom() {
		byte[] imageBytes = imageService.getImageByRandom().getImageBytes(PictureType.MASTER);

		HttpHeaders headers = new HttpHeaders();
		headers.setCacheControl("max-age=1");
		headers.setContentLength(imageBytes.length);
		headers.setContentType(MediaType.IMAGE_JPEG);
		
		return new HttpEntity<byte[]>(imageBytes, headers);		
	}
	
	@RequestMapping(value="/downloadGnomImage")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void downloadGnomImage() {
		imageService.downloadGnomImage();
	}

	@RequestMapping(value="/google")
	public String searchGoogle(Model model, @RequestParam(value="q", required=false, defaultValue="") String query) {
		model.addAttribute(VideoUtils.getGoogleImage(query));
		return "image/google";
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
