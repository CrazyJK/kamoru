package kamoru.app.spring.video.source;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.scheduling.annotation.Scheduled;

import kamoru.app.spring.video.domain.Video;

public class FileBasedVideoSource implements VideoSource {
	protected static final Log logger = LogFactory.getLog(FileBasedVideoSource.class);

	private final String UNKNOWN = "_Unknown";
	private final String unclassifiedStudio = UNKNOWN;
	private final String unclassifiedOpus = UNKNOWN;
	private final String unclassifiedActress = UNKNOWN;

	private volatile Map<String, Video> dataMap;
	private String[] paths;
	private String video_extensions;
	private String cover_extensions;
	private String subtitles_extensions;
	private String overview_extensions;
		
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

	private Map<String, Video> createVideoSource() {
		if(dataMap != null)
			return dataMap;
		
		load();
		
		return dataMap;
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

		dataMap = new HashMap<String, Video>();
		int unclassifiedNo = 1;
		for(File file : files) {
			String filename = file.getName();
			String name = getFileName(file);
			String ext  = getFileExtension(file);
			
			String[] names = StringUtils.split(name, "]");
			String studio  = UNKNOWN;
			String opus    = UNKNOWN;
			String title   = filename;
			String actress = UNKNOWN;
			String etcInfo = "";
			
			switch(names.length) {
			case 5:
				etcInfo = trimName(names[4]);
			case 4:
				actress = trimName(names[3]);
			case 3:
				title = trimName(names[2]);
			case 2:
				opus = trimName(names[1]);
				studio = trimName(names[0]);
				break;
			case 1:
				studio 	= unclassifiedStudio;
				opus 	= unclassifiedOpus + unclassifiedNo++;
				title 	= filename;
				actress = unclassifiedActress;
				break;
			default: // if names length is over 6
				studio 	= trimName(names[0]);
				opus 	= trimName(names[1]);
				title 	= trimName(names[2]);
				actress = trimName(names[3]);
				for(int i=4, iEnd=names.length; i<iEnd; i++)
					etcInfo = trimName(names[i]);
			}
			Video video = null;
			if((video = dataMap.get(opus)) == null) {
				video = new Video();
				video.setStudio(studio);
				video.setOpus(opus);
				video.setTitle(title);
				video.setActress(actress);
				video.setEtcInfo(etcInfo);
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
			dataMap.put(opus, video);
		}
		logger.debug("Found video size : " + dataMap.size());
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
	public Video get(Object opus) {
		Video video = createVideoSource().get(opus);
		if(video == null)
			throw new RuntimeException("Not found video");
		return video;
	}

	@Override
	public void put(String opus, Video video) {
		createVideoSource().put(opus, video);		
	}

	@Override
	public void remove(Object opus) {
		for(File file : createVideoSource().get(opus).getFileAll())
			FileUtils.deleteQuietly(file);
		createVideoSource().remove(opus);
	}

	@Override
	public Set<String> keySet() {
		return createVideoSource().keySet();
	}

	@Override
	public Map<String, Video> getMap() {
		return createVideoSource();
	}

	@Override
	public List<Video> getList() {
		return new ArrayList<Video>(createVideoSource().values());
	}

	@Override
	public boolean contains(Object opus) {
		return createVideoSource().containsKey(opus);
	}

	@Override
	public void reload() {
		load();
	}
}
