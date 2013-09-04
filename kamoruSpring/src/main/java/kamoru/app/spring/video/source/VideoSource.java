package kamoru.app.spring.video.source;

import java.util.List;
import java.util.Map;
import java.util.Set;

import kamoru.app.spring.video.domain.Actress;
import kamoru.app.spring.video.domain.Studio;
import kamoru.app.spring.video.domain.Video;

public interface VideoSource {

	Map<String, Video> getVideoMap();
	
	Map<String, Studio> getStudioMap();
	
	Map<String, Actress> getActressMap();

	void reload();
}
