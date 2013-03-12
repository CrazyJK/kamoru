package kamoru.app.spring.video.domain;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import kamoru.app.spring.video.util.VideoUtils;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * AV Bean class<br>
 * include studio, opus, title, overview info and video, cover, subtitles, log file<br>
 * action play, random play, editing subtitles and overview  
 * @author kamoru
 *
 */
@Component
@Scope("prototype")
public class Video implements Comparable<Object>, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected static final Log logger = LogFactory.getLog(Video.class);

//	private String studio;
	private String opus;
	private String title;
//	private List<String> actressList;
	private String etcInfo;
	
	private List<File> videoFileList;
	private List<File> subtitlesFileList;
	private File coverFile;
	private File overviewFile;
	private File historyFile;
	private List<File> etcFileList;
	
	private final String DEFAULT_SORTMETHOD = "O";
	private String sortMethod = DEFAULT_SORTMETHOD;
	
	private Studio studio;
	private List<Actress> actressList;
	
	public Video() {
		videoFileList = new ArrayList<File>();
		subtitlesFileList = new ArrayList<File>();
		etcFileList = new ArrayList<File>();
		actressList = new ArrayList<Actress>();
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("video : ").append(this.getVideoFileListPath()).append(",");
		sb.append("subtitles : ").append(this.getSubtitlesFileListPath()).append(",");
		sb.append("cover : ").append(this.getCoverFilePath()).append(",");
		sb.append("overview : ").append(this.getOverviewFilePath()).append(",");
		sb.append("history : ").append(this.getHistoryFilePath()).append(",");
		sb.append("etc : ").append(this.getEtcFileListPath());
		return sb.toString();
	}
	
	public String getActress() {
		List<String> list = new ArrayList<String>();
		for(Actress actress : actressList) {
			list.add(actress.getName());
		}
		return VideoUtils.toListToSimpleString(list);
	}

	public List<Actress> getActressList() {
		Collections.sort(actressList);
		return actressList;
	}
	public void setActressList(List<Actress> actressList) {
		this.actressList = actressList;
	}
	public void putActress(Actress actress) {
		boolean found = false;
		for(Actress actressInList : this.actressList) {
			if(actressInList.getName().equalsIgnoreCase(actress.getName())) {
				found = true;
				actressInList = actress;
			}
		}
		if(!found)
			this.actressList.add(actress);
	}

	public boolean containsActress(String actressName) {
		for(Actress actress : actressList)
			if(actress.contains(actressName))
				return true;
		return false;
	}
	
	@Override
	public int compareTo(Object o) {
		Video comp = (Video)o;
		String thisStr = null;
		String compStr = null;
		if("S".equals(sortMethod)) {
			thisStr = this.getStudio().getName();
			compStr = comp.getStudio().getName();
		} else if("O".equals(sortMethod)) {
			thisStr = this.getOpus();
			compStr = comp.getOpus();
		} else if("T".equals(sortMethod)) {
			thisStr = this.getTitle();
			compStr = comp.getTitle();
		} else if("A".equals(sortMethod)) {
			thisStr = this.getActress();
			compStr = comp.getActress();
		} else if("L".equals(sortMethod)) {
			thisStr = String.valueOf(this.isExistVideoFileList() ? this.getVideoFileList().get(0).lastModified() : 0);
			compStr = String.valueOf(comp.isExistVideoFileList() ? comp.getVideoFileList().get(0).lastModified() : 0);
		}
		String[] s = {thisStr, compStr};
		Arrays.sort(s);
		return s[0].equals(thisStr) ? -1 : 1;
	}
	
	// isExist file method
	public boolean isExistVideoFileList() {
		return getVideoFileList() != null && getVideoFileList().size() > 0;  
	}
	public boolean isExistSubtitlesFileList() {
		return getSubtitlesFileList() != null && getSubtitlesFileList().size() > 0;
	}
	public boolean isExistCoverFile() {
		return getCoverFile() != null;
	}
	public boolean isExistOverviewFile() {
		return getOverviewFile() != null; 
	}
	public boolean isExistHistoryFile() {
		return getHistoryFile() != null;
	}
	public boolean isExistEtcFileList() {
		return getEtcFileList() != null && getEtcFileList().size() > 0;
	}
	// file path method
	public String getVideoFileListPath() {
		if(isExistVideoFileList()) 
			return VideoUtils.arrayToString(getVideoFileList()); 
		return null;
	}
	public String[] getVideoFileListPathArray() {
		if(isExistVideoFileList()) {
			String[] filePathes = new String[getVideoFileList().size()];
			for(int i=0; i<filePathes.length; i++)
				filePathes[i] = getVideoFileList().get(i).getAbsolutePath();
			return filePathes;
		}
		return null;
	}
	public String getSubtitlesFileListPath() {
		if(isExistSubtitlesFileList())
			return VideoUtils.arrayToString(getSubtitlesFileList());
		return null;
	}
	public String[] getSubtitlesFileListPathArray() {
		if(isExistSubtitlesFileList()) {
			String[] filePathes = new String[getSubtitlesFileList().size()];
			for(int i=0; i<filePathes.length; i++)
				filePathes[i] = getSubtitlesFileList().get(i).getAbsolutePath();
			return filePathes;
		}
		return null;
	}
	public String getCoverFilePath() {
		if(isExistCoverFile())
			return getCoverFile().getAbsolutePath();
		return null;
	}
	public String getOverviewFilePath() {
		if(isExistOverviewFile())
			return getOverviewFile().getAbsolutePath();
		return null;
	}
	public String getHistoryFilePath() {
		if(isExistHistoryFile())
			return getHistoryFile().getAbsolutePath();
		return null;
	}
	public String getEtcFileListPath() {
		if(isExistEtcFileList())
			return VideoUtils.arrayToString(getEtcFileList());
		return null;
	}
	// getter & setter method end
	public List<File> getVideoFileList() {
		return videoFileList;
	}

	public void setVideoFileList(List<File> videoFileList) {
		this.videoFileList = videoFileList;
	}

	public List<File> getSubtitlesFileList() {
		return subtitlesFileList;
	}

	public void setSubtitlesFileList(List<File> subtitlesFileList) {
		this.subtitlesFileList = subtitlesFileList;
	}

	public File getCoverFile() {
		return coverFile;
	}

	public void setCoverFile(File coverFile) {
		this.coverFile = coverFile;
	}

	public File getOverviewFile() {
		return overviewFile;
	}

	public void setOverviewFile(File overviewFile) {
		this.overviewFile = overviewFile;
	}

	public File getHistoryFile() {
		return historyFile;
	}

	public void setHistoryFile(File historyFile) {
		this.historyFile = historyFile;
	}

	public List<File> getEtcFileList() {
		return etcFileList;
	}

	public void setEtcFileList(List<File> etcFileList) {
		this.etcFileList = etcFileList;
	}
	public Studio getStudio() {
		return studio;
	}

	public void setStudio(Studio studio) {
		this.studio = studio;
	}

	public String getOpus() {
		return opus;
	}

	public void setOpus(String opus) {
		this.opus = opus;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getEtcInfo() {
		return etcInfo;
	}

	public void setEtcInfo(String etcInfo) {
		this.etcInfo = etcInfo;
	}

	public void setVideoFile(File file) {
		this.videoFileList.add(file);
	}

	public void setSubtitlesFile(File file) {
		this.subtitlesFileList.add(file);		
	}

	public void setEtcFile(File file) {
		this.etcFileList.add(file);		
	}

	public void setSortMethod(String sortMethod) {
		this.sortMethod = sortMethod;
	}

	public List<File> getFileAll() {
		List<File> list = new ArrayList<File>();
		list.addAll(getVideoFileList());
		list.addAll(getSubtitlesFileList());
		list.add(getCoverFile());
		list.add(getHistoryFile());
		list.add(getOverviewFile());
		list.addAll(getEtcFileList());
		return list;
	}
	
	public String getOverviewText() {
		if(isExistOverviewFile()) {
			try {
				return FileUtils.readFileToString(getOverviewFile());
			}catch(IOException ioe){
				return "Error:" + ioe.getMessage();
			}
		} else {
			return "";
		}
	}
	
	public String getHistoryText() {
		if(isExistHistoryFile()) {
			try {
				return FileUtils.readFileToString(getHistoryFile());
			}catch(IOException ioe){
				return "Error:" + ioe.getMessage();
			}
		} else {
			return "";
		}
	}

}
