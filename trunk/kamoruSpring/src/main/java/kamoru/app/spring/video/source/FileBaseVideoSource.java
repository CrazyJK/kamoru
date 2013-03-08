package kamoru.app.spring.video.source;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kamoru.app.spring.video.domain.Actress;
import kamoru.app.spring.video.domain.Studio;
import kamoru.app.spring.video.domain.Video;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class FileBaseVideoSource extends AbstractVideoSource {
	protected static final Log logger = LogFactory.getLog(FileBaseVideoSource.class);

	private final String UNKNOWN = "_Unknown";
	private final String unclassifiedStudio = UNKNOWN;
	private final String unclassifiedOpus = UNKNOWN;
	private final String unclassifiedActress = UNKNOWN;

	private String[] paths;
	private String video_extensions;
	private String cover_extensions;
	private String subtitles_extensions;
	private String overview_extensions;

	private Map<String, Video> videoMap;
	private Map<String, Studio> studioMap;
	private Map<String, Actress> actressMap;
		
	// setter
	public void setPaths(String[] paths) {
		this.paths = paths;
	}
	public void setVideo_extensions(String video_extensions) {
		this.video_extensions = video_extensions;
	}
	public void setCover_extensions(String cover_extensions) {
		this.cover_extensions = cover_extensions;
	}
	public void setSubtitles_extensions(String subtitles_extensions) {
		this.subtitles_extensions = subtitles_extensions;
	}
	public void setOverview_extensions(String overview_extensions) {
		this.overview_extensions = overview_extensions;
	}

	public final void createVideoSource() {
		if(videoMap == null || studioMap == null || actressMap == null)
			load();
	}
	private void load() {
		Collection<File> files = new ArrayList<File>();
		for(String path : paths) {
			File directory = new File(path);
			if(directory.isDirectory()) {
				Collection<File> found = FileUtils.listFiles(directory, null, true);
				files.addAll(found);
			}
		}
		logger.debug("found file size : " + files.size());

		videoMap = new HashMap<String, Video>();
		studioMap = new HashMap<String, Studio>();
		actressMap = new HashMap<String, Actress>();
		
		int unclassifiedNo = 1;
		for(File file : files) {
			String filename = file.getName();
			String name = getFileName(file);
			String ext  = getFileExtension(file);
			
			String[] names = StringUtils.split(name, "]");
			String studioName  = UNKNOWN;
			String opus    = UNKNOWN;
			String title   = filename;
			String actressName = UNKNOWN;
			String etcInfo = "";
			
			switch(names.length) {
			case 5:
				etcInfo = trimName(names[4]);
			case 4:
				actressName = trimName(names[3]);
			case 3:
				title = trimName(names[2]);
			case 2:
				opus = trimName(names[1]);
				studioName = trimName(names[0]);
				break;
			case 1:
				studioName 	= unclassifiedStudio;
				opus 	= unclassifiedOpus + unclassifiedNo++;
				title 	= filename;
				actressName = unclassifiedActress;
				break;
			default: // if names length is over 6
				studioName 	= trimName(names[0]);
				opus 	= trimName(names[1]);
				title 	= trimName(names[2]);
				actressName = trimName(names[3]);
				for(int i=4, iEnd=names.length; i<iEnd; i++)
					etcInfo = trimName(names[i]);
			}
			
			Video video = null;
			if((video = videoMap.get(opus)) == null) {
				video = new Video();
//				video.setStudio(studioName);
				video.setOpus(opus);
				video.setTitle(title);
//				video.setActress(actressName);
				video.setEtcInfo(etcInfo);
				videoMap.put(opus, video);
			}
			if(video_extensions.indexOf(ext) > -1) {
				video.setVideoFile(file);
			} 
			else if(cover_extensions.indexOf(ext) > -1) {
				video.setCoverFile(file);
			}
			else if(subtitles_extensions.indexOf(ext) > -1) {
				video.setSubtitlesFile(file);
			}
			else if(overview_extensions.indexOf(ext) > -1) {
				video.setOverviewFile(file);
			}
			else if("log".indexOf(ext) > -1) {
				video.setHistoryFile(file);
			} 
			else {
				video.setEtcFile(file);
			}
			
			Studio studio = null;
			if((studio = studioMap.get(studioName)) == null) {
				studio = new Studio(studioName);
				studioMap.put(studioName, studio);
			}

			List<Actress> actressList = ActressFactory.getActress(actressName);
			for(Actress actress : actressList) {
				actressMap.put(actress.getName(), actress);
			}

			// inject reference
			studio.putVideo(video);
			studio.putActressList(actressList);
			
			video.setStudio(studio);
			video.setActressList(actressList);
			
			for(Actress actress : actressList) {
				actress.putVideo(video);
				actress.putStudio(studio);
			}
			
		}
		logger.debug("Found video size : " + videoMap.size());
	}
	private String getFileName(File file) {
		String name = file.getName();
		int idx = name.lastIndexOf(".");
		return idx < 0 ? name : name.substring(0, idx);
	}
	private String getFileExtension(File file) {
		String filename = file.getName();
		int index = filename.lastIndexOf(".");
		return index < 0 ? "" : filename.substring(index + 1); 
	}
	private String trimName(String str) {
		if(str == null)
			return "";
		return StringUtils.replace(str, "[", "").trim();
	}

	@Override
	public void reload() {
		load();
	}
	@Override
	public Map<String, Video> getVideoMap() {
		createVideoSource();
		return videoMap;
	}
	@Override
	public Map<String, Studio> getStudioMap() {
		createVideoSource();
		return studioMap;
	}
	@Override
	public Map<String, Actress> getActressMap() {
		createVideoSource();
		return actressMap;
	}


}
