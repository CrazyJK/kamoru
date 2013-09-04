package kamoru.app.spring.video;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kamoru.app.spring.video.dao.VideoDao;
import kamoru.app.spring.video.domain.Actress;
import kamoru.app.spring.video.domain.Studio;
import kamoru.app.spring.video.domain.Video;
import kamoru.app.spring.video.service.VideoService;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="/video-context.xml")
public class VideoServiceImplTest {

	@Autowired VideoService videoService;
	
	@Test
	public void testGetVideoListByParams() {
		Map<String, String> params = new HashMap<String, String>();
		params.put("studio", "IdeaPocket");
		List<Video> list = videoService.getVideoListByParams(params);
		
		assertThat(list.size(), is(1));
		assertThat(list.get(0).getStudio().getName(), is("IdeaPocket"));
	}

	@Test
	public void testGetVideoListByParamsWithInvalidParam() {
		Map<String, String> params = new HashMap<String, String>();
		params.put("studio", "NoneExist");
		List<Video> list = videoService.getVideoListByParams(params);
		
		assertThat(list.size(), is(0));
	}

	@Test
	public void testGetActressList() {
		List<Actress> list = videoService.getActressList();
		assertThat(list, notNullValue());
	}

	@Test
	public void testGetStudioList() {
		List<Studio> list = videoService.getStudioList();
		assertThat(list, notNullValue());
	}

	@Test
	public void testGetVideo() {
		Video video = videoService.getVideo("IPTD-713");
		assertThat(video.getOpus(), is("IPTD-713"));
	}

	@Test(expected=RuntimeException.class)
	public void testGetVideoWithInvalidOpus() {
		videoService.getVideo("invalid opus");
	}

	@Test
	public void testSaveVideoOverview() {
		String opus = "IPTD-713";
		String overViewTxt = "Video overView Text";
		videoService.saveVideoOverview(opus, overViewTxt);
		Video video = videoService.getVideo(opus);
		assertThat(video.getOverviewText(), is(overViewTxt));
	}

	@Test
	public void testDeleteVideo() {
		File testOpusVideoFile = new File("/home/kamoru/ETC/collection", "[studio][opus][title][actress].avi");
		File testOpusCoverFile = new File("/home/kamoru/ETC/collection", "[studio][opus][title][actress].jpg");
		try {
			FileUtils.writeStringToFile(testOpusVideoFile, "testOpusVideoFile", true);
			FileUtils.writeStringToFile(testOpusCoverFile, "testOpusCoverFile", true);
		} catch (IOException e) {
			e.printStackTrace();
			fail("File touch error");
		}
		assertThat(testOpusVideoFile.exists(), is(true));
		assertThat(testOpusCoverFile.exists(), is(true));
		videoService.deleteVideo("opus");
		assertThat(testOpusVideoFile.exists(), is(false));
		assertThat(testOpusCoverFile.exists(), is(false));
	}

	@Test
	public void testPlayVideo() {
		String opus = "IPTD-713";
		videoService.playVideo(opus);
	}

	@Test
	public void testEditVideoSubtitles() {
		String opus = "IPTD-713";
		videoService.editVideoSubtitles(opus);
	}

	@Test
	public void testGetVideoCoverFile() {
		String opus = "IPTD-713";
		File coverFile = videoService.getVideoCoverFile(opus);
		assertThat(coverFile, notNullValue());
	}

	@Test
	public void testGetActress() {
		String actressName = "Kiritani Yuria";
		Actress actress = videoService.getActress(actressName);
		assertThat(actress.getName(), is(actressName));
	}

	@Test
	public void testGetStudio() {
		String studioName = "IdeaPocket";
		Studio studio = videoService.getStudio(studioName);
		assertThat(studio.getName(), is(studioName));
	}

}
