package kamoru.app.spring.video;

import static org.junit.Assert.*;
import kamoru.app.spring.video.source.AbstractVideoSource;
import kamoru.app.spring.video.source.FileBaseVideoSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="/video-context.xml")
public class FileBaseVideoSourceTest {

	@Autowired
	private FileBaseVideoSource videoSource;
	
	@Test
	public void testInstance() {
		assertTrue((videoSource instanceof kamoru.app.spring.video.source.AbstractVideoSource));
	}
	@Test
	public void testGet() {		
//		videoSource.get("");
		//Assert.("Not yet implemented");
	}

	@Test
	public void testGetList() {
//		videoSource.getList();
	}
	
//	@Test
//	public void testCreateVideoSource() {
//		videoSource.listVideo();
//	}

}
