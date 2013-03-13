package kamoru.app.spring.video.dao;

import java.util.List;
import java.util.Map;

import kamoru.app.spring.video.domain.Actress;
import kamoru.app.spring.video.domain.Studio;
import kamoru.app.spring.video.domain.Video;

public interface VideoDao {

	List<Video> getVideoList(
			String studio, String opus, String title, String actress, 
			boolean addCond, boolean existVideo, boolean existSubtitles, 
			String sortMethod, boolean sortReverse);
	
	List<Video> getVideoList();
	
	List<Studio> getStudioList();

	List<Actress> getActressList();

	Video getVideo(String opus);
	
	Studio getStudio(String name);
	
	Actress getActress(String name);

	void deleteVideo(String opus);

}
