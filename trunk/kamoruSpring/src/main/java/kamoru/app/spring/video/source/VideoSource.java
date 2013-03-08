package kamoru.app.spring.video.source;

import java.util.List;
import java.util.Map;
import java.util.Set;

import kamoru.app.spring.video.domain.Actress;
import kamoru.app.spring.video.domain.Studio;
import kamoru.app.spring.video.domain.Video;

public interface VideoSource {

	Video getVideo(Object opus);
	
	Studio getStudio(String name);
	
	Actress getActress(String name);

	void removeVideo(Object opus);

	Map<String, Video> getVideoMap();
	
	Map<String, Studio> getStudioMap();
	
	Map<String, Actress> getActressMap();

	List<Video> getVideoList();
	
	List<Studio> getStudioList();
	
	List<Actress> getActressList();

	boolean containsVideo(Object opus);
	
	boolean containsStudio(Object name);
	
	boolean containsActress(Object name);
	
	void reload();
}
