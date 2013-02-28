package kamoru.app.spring.picture.source;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import kamoru.app.spring.HomeController;
import kamoru.app.spring.picture.domain.Image;

@Repository
public class LocalImageSource implements ImageSource {

	private static final Logger logger = LoggerFactory.getLogger(LocalImageSource.class);

	private List<Image> imageList;
	
	@Value("#{videoProp['backgroundImagePoolPath']}") 
	private String[] backgroundImagePoolPath;

	private void listImages() {
		List<File> imageFileList = new ArrayList<File>();
		for(String path : this.backgroundImagePoolPath) {
			logger.info(path + " listfile");
			File dir = new File(path);
			if(dir.isDirectory()) {
				Collection<File> found = FileUtils.listFiles(dir, new String[]{"jpg", "jpeg", "gif", "png"}, true);
				imageFileList.addAll(found);
			}
		}
		
		imageList = new ArrayList<Image>();
		for(File file : imageFileList) {
			Image image = new Image(file);
			imageList.add(image);
		}
	}
	
	private List<Image> createImageSource() {
		if(imageList == null)
			listImages();
		
		return imageList;
	}
	
	@Override
	public Image getImage(int idx) {
		return createImageSource().get(idx);
	}

	@Override
	public List<Image> getImageList() {
		return createImageSource();
	}

	@Override
	public int getImageSize() {
		return createImageSource().size();
	}

}
