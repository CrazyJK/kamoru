package jk.kamoru.app.image.service;

import java.util.List;

import jk.kamoru.app.image.domain.Image;

public interface ImageService {

	Image getImage(int idx);

	int getImageSourceSize();

	void reload();

	Image getImageByRandom();

	void downloadGnomImage();

	List<Image> getImageList();

}
