package jk.kamoru.app.image.service;

import jk.kamoru.app.image.domain.Image;
import jk.kamoru.app.image.source.ImageSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageServiceImpl implements ImageService {

	@Autowired
	private ImageSource imageSource;
	
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
		 return imageSource.getImage((int)Math.random() * imageSource.getImageSourceSize());
	}

}
