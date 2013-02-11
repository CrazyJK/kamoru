package kamoru.app.spring.video.service;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import kamoru.app.spring.video.dao.VideoDao;
import kamoru.app.spring.video.domain.Video;

@Component
public class VideoServiceImpl implements VideoService {

	private VideoDao videoDao;
	
	@Inject
	public void setVideoDao(VideoDao videoDao) {
		this.videoDao = videoDao;
	}

	@Override
	public List<Video> getVideoListByParams(Map<String, String> params) {
		return videoDao.getVideoListByParams(params);
	}

	@Override
	public List<Video> getVideoListByActress(String actress) {
		return videoDao.getVideoListByActress(actress);
	}

	@Override
	public List<Video> getVideoListByStudio(String studio) {
		return videoDao.getVideoListByStudio(studio);
	}

	@Override
	public List<Video> getVideoListByTitle(String title) {
		return videoDao.getVideoListByTitle(title);
	}

	@Override
	public Map<String, Integer> getActressMap() {
		return videoDao.getActressMap();
	}

	@Override
	public Map<String, Integer> getStudioMap() {
		return videoDao.getStudioMap();
	}

	@Override
	public Video getVideo(String opus) {
		return videoDao.getVideo(opus);
	}

	@Override
	public void saveOverview(String opus, String overViewTxt) {
		videoDao.saveOverview(opus, overViewTxt);
	}

	@Override
	public void deleteVideo(String opus) {
		videoDao.deleteVideo(opus);
	}

	@Override
	public void playVideo(String opus) {
		videoDao.playVideo(opus);
	}

	@Override
	public void editSubtitles(String opus) {
		videoDao.editSubtitles(opus);
	}

	@Override
	public void playRandomVideo() {
		videoDao.playRandomVideo();
	}

	@Override
	public File getBGImageFile(String curr) {
		return videoDao.getBGImageFile(curr);
	}

}
