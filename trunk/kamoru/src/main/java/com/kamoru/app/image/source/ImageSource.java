package com.kamoru.app.image.source;

import java.util.List;

import com.kamoru.app.image.domain.Image;

public interface ImageSource {

	Image getImage(int idx);
	
	List<Image> getImageList();

	int getImageSourceSize();
	
	void reload();
	
}
