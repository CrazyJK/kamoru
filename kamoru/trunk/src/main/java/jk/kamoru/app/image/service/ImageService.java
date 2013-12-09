package jk.kamoru.app.image.service;

import jk.kamoru.app.image.domain.Image;

public interface ImageService {

	Image getImage(int idx);

	int getImageSourceSize();

	void reload();

	Image getImageByRandom();

	void downloadGnomImage();

}
