package jk.kamoru.app.video.source;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Provider;

import jk.kamoru.app.video.VideoCore;
import jk.kamoru.app.video.VideoException;
import jk.kamoru.app.video.domain.Actress;
import jk.kamoru.app.video.domain.Studio;
import jk.kamoru.app.video.domain.Video;
import jk.kamoru.app.video.util.VideoUtils;
import jk.kamoru.util.FileUtils;
import jk.kamoru.util.StringUtils;
import jk.kamoru.util.WebpUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;


public class FileBaseVideoSource implements VideoSource {
	protected static final Logger logger = LoggerFactory.getLogger(FileBaseVideoSource.class);

	private final String UNKNOWN = "_Unknown";
	private final String unclassifiedStudio = UNKNOWN;
	private final String unclassifiedOpus = UNKNOWN;
	private final String unclassifiedActress = "Amateur";

	// data source
	private Map<String, Video> videoMap = new HashMap<String, Video>();
	private Map<String, Studio> studioMap = new HashMap<String, Studio>();
	private Map<String, Actress> actressMap = new HashMap<String, Actress>();
	
	// Domain provider
	@Inject Provider<Video> videoProvider;
	@Inject Provider<Studio> studioProvider;
	@Inject Provider<Actress> actressProvider;

	// property
	private String[] paths;
	private String video_extensions;
	private String cover_extensions;
	private String subtitles_extensions;
	private boolean webp_mode;
	private String webp_exec;

	// logic variables
	private boolean loaded = false;
	
	// property setter
	public void setPaths(String[] paths) {
		Assert.notNull(paths, "base paths must not be null");
		this.paths = paths;
	}
	public void setVideo_extensions(String video_extensions) {
		Assert.notNull(video_extensions, "video ext must not be null");
		this.video_extensions = video_extensions;
	}
	public void setCover_extensions(String cover_extensions) {
		Assert.notNull(cover_extensions, "cover ext must not be null");
		this.cover_extensions = cover_extensions;
	}
	public void setSubtitles_extensions(String subtitles_extensions) {
		Assert.notNull(subtitles_extensions, "subtitles ext must not be null");
		this.subtitles_extensions = subtitles_extensions;
	}
	public void setWebp_mode(boolean webp_mode) {
		this.webp_mode = webp_mode;
	}
	public void setWebp_exec(String webp_exec) {
		if (webp_mode)
			Assert.notNull(webp_exec, "Webp mode is true. webp_exec must not be null");
		this.webp_exec = webp_exec;
	}
	
	/**
	 * 기존에 만든적이 없으면, video source를 로드를 호출한다.
	 */
	public final void createVideoSource() {
		logger.trace("createVideoSource");
		if(!loaded)
			load();
	}
	/**
	 * video데이터를 로드한다.
	 */
	private synchronized void load() {
		logger.trace("load");

		// 1. data source initialize
//		videoMap = new HashMap<String, Video>();
//		studioMap = new HashMap<String, Studio>();
//		actressMap = new HashMap<String, Actress>();

		// 2. file find
		Collection<File> files = new ArrayList<File>();
		for (String path : paths) {
			File directory = new File(path);
			logger.debug("directory scanning : {}", directory);
			if(directory.isDirectory()) {
				Collection<File> found = FileUtils.listFiles(directory, null, true);
				logger.debug("\tfound file size is {}", found.size());
				files.addAll(found);
			}
			else {
				logger.debug("\tIt is not directory. Pass!!!");
			}
		}
		logger.debug("total found file size : {}", files.size());

		// 3. domain create & data source   
		int unclassifiedNo = 1;
		for (File file : files) {
			String filename = file.getName();
			String name = FileUtils.getNameExceptExtension(file);
			String ext  = FileUtils.getExtension(file).toLowerCase();
			
			//연속 스페이스 제거
			name = StringUtils.normalizeSpace(name);
			
			if(filename.equals(VideoCore.HISTORY_LOG) || filename.endsWith(VideoCore.EXT_ACTRESS) || filename.endsWith(VideoCore.EXT_STUDIO))
				continue;
			
			//   1      2     3       4       5     6
			//[studio][opus][title][actress][date][etc...]
			String[] names = StringUtils.split(name, "]");
			String studioName  = UNKNOWN;
			String opus    = UNKNOWN;
			String title   = filename;
			String actressNames = UNKNOWN;
			String releaseDate = "";
			String etcInfo = "";
			
			switch (names.length) {
			case 6:
				etcInfo 	= VideoUtils.removeUnnecessaryCharacter(names[5]);
			case 5:
				releaseDate 	= VideoUtils.removeUnnecessaryCharacter(names[4]);
			case 4:
				actressNames = VideoUtils.removeUnnecessaryCharacter(names[3], unclassifiedActress);
			case 3:
				title 		= VideoUtils.removeUnnecessaryCharacter(names[2], UNKNOWN);
			case 2:
				opus 		= VideoUtils.removeUnnecessaryCharacter(names[1], unclassifiedOpus);
				studioName 	= VideoUtils.removeUnnecessaryCharacter(names[0], unclassifiedStudio);
				break;
			case 1:
				studioName 	= unclassifiedStudio;
				opus 		= unclassifiedOpus + unclassifiedNo++;
				title 		= filename;
				actressNames = unclassifiedActress;
				break;
			default: // if names length is over 6
				studioName 	= VideoUtils.removeUnnecessaryCharacter(names[0], unclassifiedStudio);
				opus 		= VideoUtils.removeUnnecessaryCharacter(names[1], unclassifiedOpus);
				title 		= VideoUtils.removeUnnecessaryCharacter(names[2], UNKNOWN);
				actressNames = VideoUtils.removeUnnecessaryCharacter(names[3], unclassifiedActress);
				releaseDate = VideoUtils.removeUnnecessaryCharacter(names[4]);
				for (int i=5, iEnd=names.length; i<iEnd; i++)
					etcInfo = etcInfo + " " + VideoUtils.removeUnnecessaryCharacter(names[i]);
			}
			
			Video video = videoMap.get(opus.toLowerCase());
			if (video == null) {
				video = this.videoProvider.get();
				video.setOpus(opus.toUpperCase());
				video.setTitle(title);
				video.setReleaseDate(releaseDate);
				video.setEtcInfo(etcInfo);
				videoMap.put(opus.toLowerCase(), video);
				logger.trace("add video - {}", video);
			}
			
			// set File
			if (video_extensions.toLowerCase().indexOf(ext) > -1) {
				video.setVideoFile(file);
			}
			else if (cover_extensions.toLowerCase().indexOf(ext) > -1) {
				if (webp_mode) {
					video.setCoverWebpFile(convertWebpFile(file));
				}
				video.setCoverFile(file);
			}
			else if (subtitles_extensions.toLowerCase().indexOf(ext) > -1) {
				video.setSubtitlesFile(file);
			}
			else if ("info".equalsIgnoreCase(ext)) {
				video.setInfoFile(file);
			}
			else if ("webp".equalsIgnoreCase(ext)) {
				video.setCoverWebpFile(file);
			}
			else {
				video.setEtcFile(file);
			}
			
			Studio studio = studioMap.get(studioName.toLowerCase());
			if (studio == null) {
				studio = this.studioProvider.get();
				studio.setName(studioName);
				studioMap.put(studioName.toLowerCase(), studio);
				logger.trace("add studio - {}", studio);
			}

			// inject reference
			studio.putVideo(video);
			
			video.setStudio(studio);
			
			for (String actressName : StringUtils.split(actressNames, ",")) { 
				String forwardActressName = VideoUtils.forwardNameSort(actressName);
				Actress actress = actressMap.get(forwardActressName);
				if (actress == null) {
					actress = actressProvider.get();
					actress.setName(actressName);
					
					actressMap.put(forwardActressName, actress);
					logger.trace("add actress - {}", actress);
				}
				actress.putVideo(video);
				actress.putStudio(studio);

				studio.putActress(actress);
				video.putActress(actress);
			}
			
		}
		loaded = true;
		logger.debug("total found video size : {}", videoMap.size());
	}

	/**
	 * 이미지를 webp파일로 반환. 없으면 만드는 작업 호출
	 * @param file
	 * @return
	 */
	private File convertWebpFile(File file) {
		logger.trace("{}", file.getAbsolutePath());
		File webpfile = new File(file.getParent(), FileUtils.getNameExceptExtension(file) + ".webp");
		if(!webpfile.exists()) {
			WebpUtils.convert(webp_exec, file);
		}
		return webpfile;
	}
	
	/**
	 * 컴마(,)로 구분된 이름을 <code>List&lt;Actress&gt;</code>로 반환
	 * @param actressNames
	 * @return
	 *//*
	private List<Actress> makeActressList(String actressNames) {
		logger.trace("getActressList {}", actressNames);
		List<Actress> actressList = new ArrayList<Actress>();
		
		String[] namesArr = StringUtils.split(actressNames, ",");
		for(String name : namesArr) {
			name = StringUtils.join(StringUtils.split(name), " ");
			boolean bFindSameName = false;
			for(Actress actress : actressList) {
				if(VideoUtils.equalsName(actress.getName(), name)) {
					bFindSameName = true;
					break;
				}
			}
			if(!bFindSameName) {
				Actress actress = this.actressProvider.get();
				actress.setName(name);
				actressList.add(actress);
			}
		}
		logger.trace("found actress list - {}", actressList);
		return actressList;
	}
	*/
	@Override
	public void reload() {
		logger.trace("reload");
		load();
	}
	
	@Override
	public Map<String, Video> getVideoMap() {
		logger.trace("getVideoMap");
		createVideoSource();
		return videoMap;
	}
	@Override
	public Map<String, Studio> getStudioMap() {
		logger.trace("getStudioMap");
		createVideoSource();
		return studioMap;
	}
	@Override
	public Map<String, Actress> getActressMap() {
		logger.trace("getActressMap");
		createVideoSource();
		return actressMap;
	}
	@Override
	public void removeVideo(String opus) {
		logger.trace("remove {}" + opus);
		createVideoSource();
		videoMap.get(opus.toLowerCase()).removeVideo();
		videoMap.remove(opus.toLowerCase());
	}
	@Override
	public Video getVideo(String opus) {
		logger.trace(opus);
		createVideoSource();
		if (videoMap.containsKey(opus.toLowerCase())) {
			return videoMap.get(opus.toLowerCase());
		}
		else {
			throw new VideoException("Video not found : " + opus);
		}
	}
	@Override
	public Studio getStudio(String name) {
		logger.trace(name);
		createVideoSource();
		if (studioMap.containsKey(name.toLowerCase())) {
			return studioMap.get(name.toLowerCase());
		}
		else {
			throw new VideoException("Studio not found : " + name);
		}
	}
	@Override
	public Actress getActress(String name) {
		logger.trace(name);
		createVideoSource();
		if (actressMap.containsKey(VideoUtils.forwardNameSort(name))) {
			return actressMap.get(VideoUtils.forwardNameSort(name));
		}
		else {
			throw new VideoException("Actress not found : " + name);
		}
	}
	@Override
	public List<Video> getVideoList() {
		logger.trace("getVideoList");
		createVideoSource();
		return new ArrayList<Video>(videoMap.values());
	}
	@Override
	public List<Studio> getStudioList() {
		logger.trace("getStudioList");
		createVideoSource();
		return new ArrayList<Studio>(studioMap.values());
	}
	@Override
	public List<Actress> getActressList() {
		logger.trace("getActressList");
		createVideoSource();
		return new ArrayList<Actress>(actressMap.values());
	}
	@Override
	public void moveVideo(String opus, String destPath) {
		logger.trace("moveVideo {} to {}", opus, destPath);
		createVideoSource();
		videoMap.get(opus.toLowerCase()).move(destPath);
	}
	@Override
	public void arrangeVideo(String opus) {
		logger.trace(opus);
		createVideoSource();
		videoMap.get(opus.toLowerCase()).arrange();
	}

}
