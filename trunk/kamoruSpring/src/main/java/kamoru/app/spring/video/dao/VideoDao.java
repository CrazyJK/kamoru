package kamoru.app.spring.video.dao;

import java.util.List;
import java.util.Map;

import kamoru.app.spring.video.domain.Video;

public interface VideoDao {

	List<Video> getVideoListByParams(Map<String, String> params);

	List<Video> getVideoListByActress(String actress);

	List<Video> getVideoListByStudio(String studio);

	List<Video> getVideoListByTitle(String title);

	Map<String, Integer> getActressMap();

	Map<String, Integer> getStudioMap();

	Video getVideo(String opus);

	void deleteVideo(String opus);

}
