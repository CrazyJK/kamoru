package jk.kamoru.app.image.source;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import jk.kamoru.app.image.ImageException;
import jk.kamoru.app.image.domain.Image;
import jk.kamoru.util.FileUtils;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
public class LocalImageSource implements ImageSource {

//	private static final Logger logger = LoggerFactory.getLogger(LocalImageSource.class);

	private List<Image> imageList;

	@Value("#{prop['image.basePath']}")	private String[] backgroundImagePoolPath;

	private void listImages() {
		List<File> imageFileList = new ArrayList<File>();
		for (String path : this.backgroundImagePoolPath) {
			File dir = new File(path);
			if (dir.isDirectory()) {
				log.debug("directory scanning : {}", dir);
				Collection<File> found = FileUtils.listFiles(dir, new String[] {"jpg", "jpeg", "gif", "png" }, true);
				imageFileList.addAll(found);
			}
		}

		imageList = new ArrayList<Image>();
		for (File file : imageFileList) {
			imageList.add(new Image(file));
		}
		log.info("Total found image size : {}", imageList.size());
	}

	private List<Image> createImageSource() {
		if (imageList == null)
			reload();

		return imageList;
	}

	@Override
	public Image getImage(int idx) {
		try {
			return createImageSource().get(idx);
		}
		catch(IndexOutOfBoundsException  e) {
			throw new ImageException("Image not found", e);
		}
	}

	@Override
	public List<Image> getImageList() {
		return createImageSource();
	}

	@Override
	public int getImageSourceSize() {
		return createImageSource().size();
	}

	@Override
	@Scheduled(cron = "0 */7 * * * *")
	public void reload() {
		listImages();
	}

}
