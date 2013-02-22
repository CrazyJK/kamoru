package kamoru.app.spring.video.service;

import java.io.File;
import java.util.List;
import java.util.Map;

import kamoru.app.spring.video.domain.Video;

public interface VideoService {

	List<Video> getVideoListByParams(Map<String, String> params);

	List<Video> getVideoListByActress(String actress);

	List<Video> getVideoListByStudio(String studio);

	List<Video> getVideoListByTitle(String title);

	Map<String, Integer> getActressMap();

	Map<String, Integer> getStudioMap();

	Video getVideo(String opus);

	void deleteVideo(String opus);

	void playVideo(String opus);

	File getBGImageFile(String curr);

	File getVideoCoverFile(String opus);

	void saveVideoOverview(String opus, String overViewTxt);

	void editVideoSubtitles(String opus);

}
