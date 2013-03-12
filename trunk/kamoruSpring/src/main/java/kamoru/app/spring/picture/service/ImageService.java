package kamoru.app.spring.picture.service;

import kamoru.app.spring.picture.domain.Image;

public interface ImageService {

	Image getImage(int idx);

	int getImageSourceSize();

	void reload();

}
