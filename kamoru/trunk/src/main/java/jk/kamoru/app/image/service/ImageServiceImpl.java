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

}
