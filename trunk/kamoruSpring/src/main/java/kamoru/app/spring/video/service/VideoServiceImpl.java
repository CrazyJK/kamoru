package kamoru.app.spring.video.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.inject.Inject;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import kamoru.app.spring.video.dao.VideoDao;
import kamoru.app.spring.video.dao.VideoFileDaoImpl;
import kamoru.app.spring.video.domain.Video;

@Service
public class VideoServiceImpl implements VideoService {
	protected static final Log logger = LogFactory.getLog(VideoServiceImpl.class);
	
	private VideoDao videoDao;
	private Map<String, String> history;
	
	@Value("#{videoProp['defaultCoverFilePath']}") 
	private String defaultCoverFilePath;
	@Value("#{videoProp['backgroundImagePoolPath']}") 
	private String backgroundImagePoolPath;
	@Value("#{videoProp['tempDirectory']}") 
	private String tempDirectory;
	private List<File> list;
	private File currentBackgroundImageFile;

	public VideoServiceImpl() {
		history = new HashMap<String, String>();
	}
	
	@Inject
	public void setVideoDao(VideoDao videoDao) {
		this.videoDao = videoDao;
	}

	private void listBackgroundImages() {
		String[] bgImgPoolPath = StringUtils.split(backgroundImagePoolPath, ";");
		list = new ArrayList<File>();
		for(String bgImgPath : bgImgPoolPath) {
			list.addAll(FileUtils.listFiles(new File(bgImgPath), new String[]{"jpg"}, true));
		}
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
	public void saveVideoOverview(String opus, String overViewTxt) {
		getVideo(opus).saveOverViewText(overViewTxt);
	}

	@Override
	public void deleteVideo(String opus) {
		videoDao.deleteVideo(opus);
	}

	@Override
	public void playVideo(String opus) {
		getVideo(opus).playVideo();
		history.put(opus, "play");
	}

	@Override
	public void editVideoSubtitles(String opus) {
		getVideo(opus).editSubtitles();
	}
	
	@Override
	public File getBGImageFile(String curr) {
		if(list == null) 
			listBackgroundImages();
	    if(!BooleanUtils.toBoolean(curr) || currentBackgroundImageFile == null) {
			currentBackgroundImageFile = list.get(new Random().nextInt(list.size()));
	    }
	    return currentBackgroundImageFile;
	}
	
	@Override
	public File getVideoCoverFile(String opus) {
		File coverFile = videoDao.getVideo(opus).getCoverFile();
		if(coverFile == null)
			coverFile = getDefaultCoverFile();
		return coverFile;
	}

	private File getDefaultCoverFile() {
		return new File(defaultCoverFilePath);
	}

}
