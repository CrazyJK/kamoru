package kamoru.app.spring.video.service;

import java.io.File;
import java.util.List;
import java.util.Map;

import kamoru.app.spring.video.domain.Actress;
import kamoru.app.spring.video.domain.Studio;
import kamoru.app.spring.video.domain.Video;

public interface VideoService {

	List<Video> getVideoListByParams(Map<String, String> params);

	List<Studio> getStudioList();

	List<Actress> getActressList();

	Video getVideo(String opus);

	Studio getStudio(String studioName);

	Actress getActress(String actressName);

	void deleteVideo(String opus);

	void playVideo(String opus);

	File getVideoCoverFile(String opus);

	void saveVideoOverview(String opus, String overViewTxt);

	void editVideoSubtitles(String opus);

	void reload();



}
