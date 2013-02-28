package kamoru.app.spring.video.dao;

import static org.junit.Assert.*;
import kamoru.app.spring.video.source.FileBasedVideoSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="/av-context.xml")
public class FileBasedVideoSourceTest {

	@Autowired
	private FileBasedVideoSource videoSource;
	
	@Test
	public void testInstance() {
		assertTrue((videoSource instanceof kamoru.app.spring.video.source.FileBasedVideoSource));
	}
	@Test
	public void testGet() {		
		videoSource.get("");
		//Assert.("Not yet implemented");
	}

	@Test
	public void testGetList() {
		videoSource.getList();
	}
	
//	@Test
//	public void testCreateVideoSource() {
//		videoSource.listVideo();
//	}

}
