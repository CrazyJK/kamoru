package kamoru.app.spring.picture.source;

import java.util.List;

import kamoru.app.spring.picture.domain.Image;

public interface ImageSource {

	Image getImage(int idx);
	
	List<Image> getImageList();

	int getImageSize();
	
}
