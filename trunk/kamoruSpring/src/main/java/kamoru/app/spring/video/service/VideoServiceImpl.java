package kamoru.app.spring.video.service;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.inject.Inject;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import kamoru.app.spring.video.dao.VideoDao;
import kamoru.app.spring.video.domain.Action;
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
	private List<File> list;
	private File currentBackgroundImageFile;

	@Value("#{videoProp['editor']}") 
	private String editor;
	@Value("#{videoProp['player']}") 
	private String player;
	@Value("#{videoProp['mainBasePath']}")
	private String mainBasePath;
	
	public final String FileEncoding = "UTF-8";

	public VideoServiceImpl() {
		history = new HashMap<String, String>();
	}
	
	@Inject
	public void setVideoDao(VideoDao videoDao) {
		this.videoDao = videoDao;
	}

	@Scheduled(fixedRate=3600000)
	public void listBackgroundImages() {
		logger.info("start");
		String[] bgImgPoolPath = StringUtils.split(backgroundImagePoolPath, ",");
		list = new ArrayList<File>();
		for(String bgImgPath : bgImgPoolPath) {
			File dir = new File(bgImgPath);
			if(dir.isDirectory())
				list.addAll(FileUtils.listFiles(dir, new String[]{"jpg"}, true));
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
		Video video = getVideo(opus);
		if(!video.isExistOverviewFile()) video.setOverviewFile(new File(getVideoPathWithoutExtension(video) + ".txt"));
		try {
			FileUtils.writeStringToFile(video.getOverviewFile(), overViewTxt, FileEncoding);
		} catch (IOException e) {
			logger.error("save overview error", e);
			throw new RuntimeException(e);
		}
		logger.debug("saveOverViewTxt : " + opus + " [" + video.getOverviewFile() + "]");
	}

	private String getVideoPathWithoutExtension(Video video) {
		if(video.isExistVideoFileList()) {
			String videoPath = video.getVideoFileList().get(0).getAbsolutePath();
			return videoPath.substring(0, videoPath.lastIndexOf("."));
		} else if(video.isExistCoverFile()) {
			return video.getCoverFilePath().substring(0, video.getCoverFilePath().lastIndexOf("."));
		} else if(video.isExistOverviewFile()) {
			return video.getOverviewFilePath().substring(0, video.getOverviewFilePath().lastIndexOf("."));
		} else if(video.isExistSubtitlesFileList()) {
			String subtitlesPath = video.getSubtitlesFileList().get(0).getAbsolutePath();
			return subtitlesPath.substring(0, subtitlesPath.lastIndexOf("."));
		} else {
			throw new RuntimeException("No video file");
		}
	}

	@Override
	public void deleteVideo(String opus) {
		saveHistory(getVideo(opus), Action.DELETE);
		videoDao.deleteVideo(opus);
	}

	@Override
	public void playVideo(String opus) {
		saveHistory(getVideo(opus), Action.PLAY);
		executeCommand(Action.PLAY, getVideo(opus));
		history.put(opus, "play");
	}

	@Override
	public void editVideoSubtitles(String opus) {
		executeCommand(Action.SUBTITLES, getVideo(opus));
	}
	
	@Async
	private void executeCommand(Action play, Video video) {
		String command = null;
		String[] argumentsArray = null;
		switch(play) {
		case PLAY:
			command = player;
			argumentsArray = video.getVideoFileListPathArray();
			break;
		case SUBTITLES:
			command = editor;
			argumentsArray = video.getSubtitlesFileListPathArray();
			break;
		default:
			throw new RuntimeException("Unknown Action");
		}
		if(argumentsArray == null)
			throw new RuntimeException("No arguments");
		
		String[] cmdArray = ArrayUtils.addAll(new String[]{command}, argumentsArray);
		try {
			Runtime.getRuntime().exec(cmdArray);
		} catch (IOException e) {
			logger.error("Error : executes command", e);
			throw new RuntimeException(e);
		}

		
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

	// action method
	private void saveHistory(Video video, Action action) {
		String msg = null; 
		String currDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
	
		switch(action) {
		case PLAY :
			msg = "play video : " + video.getVideoFileListPath();
			break;
		case OVERVIEW :
			msg = "write overview : " + video.getOverviewFilePath();
			break;
		case COVER :
			msg = "view cover : " + video.getCoverFilePath();
			break;
		case SUBTITLES :
			msg = "edit subtitles : " + video.getSubtitlesFileListPath();
			break;
		case DELETE :
			msg = "delete all : " + video.toString();
			break;
		default:
			throw new IllegalStateException("Undefined Action : " + action.toString());
		}
		String historymsg = MessageFormat.format("{0}, {1}, {2},\"{3}\"{4}", 
				currDate, video.getOpus(), action, msg, System.getProperty("line.separator"));
		
		logger.debug("save history - " + historymsg);
		try {
			if(video.getHistoryFile() == null)
				video.setHistoryFile(new File(getVideoPathWithoutExtension(video) + ".log"));
			FileUtils.writeStringToFile(video.getHistoryFile(), historymsg, FileEncoding, true);
		} catch (IOException e) {
			logger.error(historymsg, e);
		}
		try {
			FileUtils.writeStringToFile(new File(mainBasePath, "history.log"), historymsg, FileEncoding, true);
		} catch (IOException e) {
			logger.error(historymsg, e);
		}
	}

}
