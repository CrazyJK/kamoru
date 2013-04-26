package com.kamoru.app.image.service;

import com.kamoru.app.image.domain.Image;

public interface ImageService {

	Image getImage(int idx);

	int getImageSourceSize();

	void reload();

}
