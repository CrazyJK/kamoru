package kamoru.app.spring;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import kamoru.app.spring.test.TestService;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {

	@RequestMapping(value="/test/image", method=RequestMethod.GET)
	public HttpEntity<byte[]> image() throws IOException {
		byte[] imageBytes = imageBytes();
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.IMAGE_JPEG);
		headers.setContentLength(imageBytes.length);
		headers.setCacheControl("max-age=" + 86400);
		headers.setDate(new Date().getTime() + 86400*1000l);
		headers.setExpires(new Date().getTime() + 86400*1000l);
		return new HttpEntity<byte[]>(imageBytes, headers);
	}

	@RequestMapping(value="/test/image2", method=RequestMethod.GET)
	public void image2(HttpServletResponse response) throws IOException {
		byte[] imageBytes = imageBytes();
		
		Tika tika = new Tika();
//	    String mimeType = tika.detect(imageFile);
//		response.setContentType(mimeType);
		response.setDateHeader("Expires", new Date().getTime() + 86400*1000l);
		response.setHeader("Cache-Control", "max-age=" + 86400);
		response.getOutputStream().write(imageBytes);
		response.getOutputStream().flush();
		response.getOutputStream().close();
	}

	@RequestMapping(value="/test/image3", method=RequestMethod.GET)
	public byte[] image3(HttpServletResponse response) throws IOException {
		return imageBytes();
	}
	
	@RequestMapping(value="/test/image4", method=RequestMethod.GET)
	@ResponseBody
	public byte[] image4(HttpServletResponse response) throws IOException {
		return imageBytes();
	}
	
	@RequestMapping(value="/test/image5", method=RequestMethod.GET)
	public BufferedImage image5(HttpServletResponse response) throws IOException {
		return ImageIO.read(new ByteArrayInputStream(imageBytes()));
	}
	
	@RequestMapping(value="/test/image6", method=RequestMethod.GET)
	@ResponseBody 
	public BufferedImage image6(HttpServletResponse response) throws IOException {
		return ImageIO.read(new ByteArrayInputStream(imageBytes()));
	}
	
	
	@Value("#{videoProp['backgroundImagePoolPath']}") 
	private String[] backgroundImagePoolPath;

	private byte[] imageBytes() throws IOException {
		File directory = new File(backgroundImagePoolPath[0]);
		Collection<File> found = FileUtils.listFiles(directory, new String[]{"jpg"}, true);
		File[] files = found.toArray(new File[0]);
		int random = new Random().nextInt(files.length);
//		random = 0;
		File imageFile = files[random];
		return FileUtils.readFileToByteArray(imageFile);
	}

	@Autowired
	TestService testService;
	
	@RequestMapping(value="/cachetest1")
	public String testCache1(Model model) {
		model.addAttribute("now", testService.getNow());
		return "test/cachetest";
	}

	@RequestMapping(value="/cachetest2")
	public String testCache2(Model model) {
		model.addAttribute("now", testService.getNow(2));
		return "test/cachetest";
	}

}
