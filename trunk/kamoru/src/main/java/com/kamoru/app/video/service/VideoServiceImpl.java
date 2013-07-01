package com.kamoru.app.video.service;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.kamoru.app.video.VideoCore;
import com.kamoru.app.video.VideoException;
import com.kamoru.app.video.dao.VideoDao;
import com.kamoru.app.video.domain.Action;
import com.kamoru.app.video.domain.Actress;
import com.kamoru.app.video.domain.Studio;
import com.kamoru.app.video.domain.Video;
import com.kamoru.app.video.domain.VideoSearch;

@Service
public class VideoServiceImpl implements VideoService {
	protected static final Log logger = LogFactory.getLog(VideoServiceImpl.class);

	@Autowired private VideoDao videoDao;
	
	@Value("#{videoProp['defaultCoverFilePath']}") private String defaultCoverFilePath;
	@Value("#{videoProp['editor']}") private String editor;
	@Value("#{videoProp['player']}") private String player;
	@Value("#{videoProp['mainBasePath']}") private String mainBasePath;
	@Value("#{videoProp['webp.mode']}") private boolean webpMode;

	private byte[] defaultCoverFileBytes;
	
	@Override
	public List<Video> searchVideo(VideoSearch videoSearch) {
		return videoDao.searchVideo(videoSearch);
	}

	@Override
	public List<Video> getVideoList() {
		return videoDao.getVideoList();
	}

	@Override
	public List<Actress> getActressList() {
		return videoDao.getActressList();
	}

	@Override
	public List<Studio> getStudioList() {
		return videoDao.getStudioList();
	}

	@Override
	public Video getVideo(String opus) {
		try {
			return videoDao.getVideo(opus);
		}
		catch(VideoException ve) {
			logger.error(ve);
			return null;
		}
	}

	@Override
	public void saveVideoOverview(String opus, String overViewText) {
		videoDao.getVideo(opus).saveOverView(overViewText);
	}

	@Override
	public void deleteVideo(String opus) {
		logger.info(opus);
		saveHistory(getVideo(opus), Action.DELETE);
		videoDao.deleteVideo(opus);
	}

	@Override
	public void playVideo(String opus) {
		logger.info(opus);
		saveHistory(getVideo(opus), Action.PLAY);
		executeCommand(Action.PLAY, getVideo(opus));
	}

	@Override
	public void editVideoSubtitles(String opus) {
		logger.info(opus);
		executeCommand(Action.SUBTITLES, getVideo(opus));
	}
	
	@Async
	private void executeCommand(Action action, Video video) {
		String command = null;
		String[] argumentsArray = null;
		switch(action) {
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
	public File getVideoCoverFile(String opus) {
		if(webpMode)
			return videoDao.getVideo(opus).getCoverWebpFile();
		else
			return videoDao.getVideo(opus).getCoverFile();
	}

	@Override
	public byte[] getVideoCoverByteArray(String opus) {
		if(webpMode)
			return videoDao.getVideo(opus).getCoverWebpByteArray();
		else 
			return videoDao.getVideo(opus).getCoverByteArray();
	}
	
	// action method
	private void saveHistory(Video video, Action action) {
		String msg = null; 
		String currDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
	
		switch(action) {
		case PLAY :
			if(video.getVideoFileListPath() != null) {
				msg = "play video : " + video.getVideoFileListPath();
				video.increasePlayCount();
			}
			else {
				throw new IllegalStateException("No video to play. " + video.getOpus());
			}
			break;
		case OVERVIEW :
			msg = "save overview : " + video.getInfoFile().getName();
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
		if(action != Action.DELETE)
			video.addHistory(historymsg);
//		try {
//			if(action != Action.DELETE)
//				FileUtils.writeStringToFile(video.getHistoryFile(), historymsg, VideoCore.FileEncoding, true);
//		} catch (IOException e) {
//			logger.error(historymsg, e);
//		} catch (VideoException ve) {
//			logger.error(ve.getMessage());
//		}
		try {
			FileUtils.writeStringToFile(new File(mainBasePath, "history.log"), historymsg, VideoCore.FileEncoding, true);
		} catch (IOException e) {
			logger.error(historymsg, e);
		}
	}

	@Override
	public Actress getActress(String actressName) {
		return videoDao.getActress(actressName);
	}

	@Override
	public Studio getStudio(String studioName) {
		return videoDao.getStudio(studioName);
	}

	@Override
	public List<Video> findVideoList(String query) {
		List<Video> found = new ArrayList<Video>();
		if(query == null || query.trim().length() == 0)
			return found;

		query = query.toLowerCase();
		for(Video video : videoDao.getVideoList()) {
			if(StringUtils.containsIgnoreCase(video.getOpus(), query)
				|| StringUtils.containsIgnoreCase(video.getStudio().getName(), query)
				|| StringUtils.containsIgnoreCase(video.getTitle(), query)
				|| StringUtils.containsIgnoreCase(video.getActress(), query)) {
				found.add(video);
			} 
		}
		return found;
	}

	@Override
	public byte[] getDefaultCoverFileByteArray() {
		if(defaultCoverFileBytes == null)
			try {
				defaultCoverFileBytes = FileUtils.readFileToByteArray(new File(defaultCoverFilePath));
			} catch (IOException e) {
				logger.error(e);
			}
		return defaultCoverFileBytes;
	}

	@Override
	public List<Actress> getActressListOfVideoes(List<Video> videoList) {
		List<Actress> actressList = new ArrayList<Actress>();
		for(Video video : videoList) {
			for(Actress actress : video.getActressList()) {
				if(!actressList.contains(actress))
					actressList.add(actress);
			}
		}
		Collections.sort(actressList);
		return actressList;
	}

	@Override
	public List<Studio> getStudioListOfVideoes(List<Video> videoList) {
		List<Studio> studioList = new ArrayList<Studio>();
		for(Video video : videoList) {
			if(!studioList.contains(video.getStudio()))
				studioList.add(video.getStudio());
		}
		Collections.sort(studioList);
		return studioList;
	}

	@Override
	public void rankVideo(String opus, int rank) {
		logger.info(opus + " : " + rank);
		videoDao.getVideo(opus).setRank(rank);
		
	}

}

