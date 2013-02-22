package kamoru.app.spring.video.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;

import kamoru.app.spring.video.domain.Video;

public interface VideoSource {

	Video get(Object opus);
	void put(String opus, Video video);
	void remove(Object opus);
	void delete(String opus);
	Set<String> keySet();
	Map<String, Video> getMap();
	List<Video> getList();
	boolean contains(Object opus);
	void reload();
}
