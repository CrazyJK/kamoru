package kamoru.app.spring.picture.service;

import kamoru.app.spring.picture.domain.Image;
import kamoru.app.spring.picture.source.ImageSource;

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

}
