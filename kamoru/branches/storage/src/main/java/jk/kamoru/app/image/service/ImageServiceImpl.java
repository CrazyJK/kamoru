package jk.kamoru.app.image.service;

import java.util.List;

import jk.kamoru.app.image.domain.Image;
import jk.kamoru.app.image.source.ImageSource;
import jk.kamoru.tools.gnom.GnomImageDownloader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageServiceImpl implements ImageService {

	@Autowired
	private ImageSource imageSource;
	
	@Autowired
	private GnomImageDownloader gnomImageDownloader; 
	
	@Override
	public Image getImage(int idx) {
		return imageSource.getImage(idx);
	}

	@Override
	public int getImageSourceSize() {
		return imageSource.getImageSourceSize();
	}

	@Override
	public void reload() {
		imageSource.reload();
	}

	@Override
	public Image getImageByRandom() {
		return imageSource.getImage((int)(Math.random() * imageSource.getImageSourceSize()));
	}

	@Override
	public void downloadGnomImage() {
		gnomImageDownloader.process();		
	}

	@Override
	public List<Image> getImageList() {
		return imageSource.getImageList();
	}

	@Override
	public String getImageNameJSON() {
		StringBuilder sb = new StringBuilder("{");
		int index = 0;
		for (Image image : imageSource.getImageList()) {
			if (index > 0)
				sb.append(",");
			sb.append(String.format("\"%s\":\"%s\"", index++, image.getName()));
		}
		sb.append("}");
		return sb.toString();
	}

	@Override
	public void delete(int idx) {
		imageSource.delete(idx);
	}
}
