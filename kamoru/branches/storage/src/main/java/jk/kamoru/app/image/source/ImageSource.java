package jk.kamoru.app.image.source;

import java.util.List;

import jk.kamoru.app.image.domain.Image;

public interface ImageSource {

	Image getImage(int idx);
	
	List<Image> getImageList();

	int getImageSourceSize();
	
	void reload();

	void delete(int idx);
	
}
