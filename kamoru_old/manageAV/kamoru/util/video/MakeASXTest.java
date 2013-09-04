package kamoru.util.video;

import java.io.IOException;

import junit.framework.Assert;
import junit.framework.TestCase;
import kamoru.frmwk.util.FileUtils;

public class MakeASXTest extends TestCase {

	public void testMakeASXString() throws IOException {
		String s = ";ssdksjfsdf";
		s.toUpperCase();
//		FileUtils.getFileList("/home/kamoru/ETC/Download", new String[]{"avi pdf mp3"}, null, true, null);
		FileUtils.getFileList("/home/kamoru/ETC/Download", null, null, true, null);
		Assert.assertEquals(0, 0);
	}

	public void testGetFilelist() {
		fail("Not yet implemented");
	}

	public void testGetFileInfo() {
		fail("Not yet implemented");
	}


}
