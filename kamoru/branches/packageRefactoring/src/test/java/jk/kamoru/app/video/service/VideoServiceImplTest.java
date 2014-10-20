package jk.kamoru.app.video.service;

import static org.junit.Assert.*;
import jk.kamoru.crazy.video.domain.Video;
import jk.kamoru.crazy.video.service.VideoService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"file:src/main/webapp/WEB-INF/spring/root-context.xml", 
		"file:src/main/webapp/WEB-INF/spring/security-context.xml", 
		"file:src/main/webapp/WEB-INF/spring/video-context.xml"})
public class VideoServiceImplTest {

	@Autowired VideoService videoService;
	
	@Test
	public void testGetVideo() {
		Video video = videoService.getVideo("ABP-170");
		assertEquals("ABP-170", video.getOpus());
	}

}
