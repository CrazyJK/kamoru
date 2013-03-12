package kamoru.app.spring.video.dao;

import java.util.List;
import java.util.Map;

import kamoru.app.spring.video.domain.Actress;
import kamoru.app.spring.video.domain.Studio;
import kamoru.app.spring.video.domain.Video;

public interface VideoDao {

	List<Video> getVideoListByParams(Map<String, String> params);

	List<Actress> getActressList();

	List<Studio> getStudioList();

	Video getVideo(String opus);
	
	Studio getStudio(String name);
	
	Actress getActress(String name);

	void deleteVideo(String opus);

	void reload();

	List<Video> findVideoList(String query);

}
