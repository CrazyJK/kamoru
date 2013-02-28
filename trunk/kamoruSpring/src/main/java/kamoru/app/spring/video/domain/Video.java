package kamoru.app.spring.video.domain;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import kamoru.app.spring.video.util.VideoUtils;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * AV Bean class<br>
 * include studio, opus, title, overview info and video, cover, subtitles, log file<br>
 * action play, random play, editing subtitles and overview  
 * @author kamoru
 *
 */
public class Video implements Comparable<Object>, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected static final Log logger = LogFactory.getLog(Video.class);

	private String mainBasePath;
	private String editor;
	private String player;
	
	private String studio;
	private String opus;
	private String title;
	private List<String> actressList;
	private String etcInfo;
	
	private List<File> videoFileList;
	private List<File> subtitlesFileList;
	private File coverFile;
	private File overviewFile;
	private File historyFile;
	private List<File> etcFileList;
	
	private String sortMethod;

	private final String FileEncoding = "UTF-8";
	
	public Video() {
		videoFileList = new ArrayList<File>();
		subtitlesFileList = new ArrayList<File>();
		etcFileList = new ArrayList<File>();
		actressList = new ArrayList<String>();
	}
	
	public Video(String mainBasePath, String player, String editor) {
		this();
		this.mainBasePath = mainBasePath;
		this.editor = editor;
		this.player = player;
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


	// action method
	private void saveHistory(Action action) {
		String msg = null; 
		String currDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
	
		if(action.equals(Action.PLAY)) {
			msg = "play video : " + getVideoFileListPath();
		} else if(action.equals(Action.OVERVIEW)) {
			msg = "write overview : " + getOverviewFilePath();
		} else if(action.equals(Action.COVER)) {
			msg = "view cover : " + getCoverFilePath();
		} else if(action.equals(Action.SUBTITLES)) {
			msg = "edit subtitles : " + getSubtitlesFileListPath();
		} else if(action.equals(Action.DELETE)) {
			msg = "delete all : " + this.toString();
		} else {
			throw new IllegalStateException("Undefined Action : " + action.toString());
		}
		String historymsg = MessageFormat.format("{0}, {1}, {2},\"{3}\"{4}", 
				currDate, getOpus(), action, msg, System.getProperty("line.separator"));
		
		logger.debug("save history - " + historymsg);
		try {
			if(getHistoryFile() == null)
				setHistoryFile(new File(getVideoPathWithoutExtension() + ".log"));
			FileUtils.writeStringToFile(getHistoryFile(), historymsg, FileEncoding, true);
		} catch (IOException e) {
			logger.error(historymsg, e);
		}
		try {
			FileUtils.writeStringToFile(new File(mainBasePath, "history.log"), historymsg, FileEncoding, true);
		} catch (IOException e) {
			logger.error(historymsg, e);
		}
	}
	public void deleteVideo() {
		this.saveHistory(Action.DELETE);

		for(File videoFile : getVideoFileList())
			FileUtils.deleteQuietly(videoFile);
		FileUtils.deleteQuietly(getCoverFile());
		for(File subtitlesFile : getSubtitlesFileList())
			FileUtils.deleteQuietly(subtitlesFile);
		FileUtils.deleteQuietly(getOverviewFile());
		FileUtils.deleteQuietly(getHistoryFile());
		for(File etcFile : getEtcFileList())
			FileUtils.deleteQuietly(etcFile);
	}
	public void saveOverViewText(String newOverviewTxt) {
		if(!isExistOverviewFile()) makeOverviewFile();
		logger.debug("saveOverViewTxt : " + getOpus() + " [" + getOverviewFile() + "]");
		try {
			FileUtils.writeStringToFile(getOverviewFile(), newOverviewTxt, FileEncoding);
			saveHistory(Action.OVERVIEW);
		} catch (IOException e) {
			logger.error(newOverviewTxt, e);
		}
	}
	private void makeOverviewFile() {
		setOverviewFile(new File(getVideoPathWithoutExtension() + ".txt"));
	}
	private String getVideoPathWithoutExtension() {
		if(isExistVideoFileList()) {
			String videoPath = getVideoFileList().get(0).getAbsolutePath();
			return videoPath.substring(0, videoPath.lastIndexOf("."));
		} else if(isExistCoverFile()) {
			return getCoverFilePath().substring(0, getCoverFilePath().lastIndexOf("."));
		} else if(isExistOverviewFile()) {
			return getOverviewFilePath().substring(0, getOverviewFilePath().lastIndexOf("."));
		} else if(isExistSubtitlesFileList()) {
			String subtitlesPath = getSubtitlesFileList().get(0).getAbsolutePath();
			return subtitlesPath.substring(0, subtitlesPath.lastIndexOf("."));
		} else {
			return null;
		}
	}
	public String getOverviewText() {
		if(isExistOverviewFile()) {
			try {
				return FileUtils.readFileToString(getOverviewFile(), FileEncoding );
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
				return FileUtils.readFileToString(getHistoryFile(), FileEncoding );
			}catch(IOException ioe){
				return "Error:" + ioe.getMessage();
			}
		} else {
			return "";
		}
	}
	/*
	public void viewImage(HttpServletResponse response) throws IOException {
		String img = getCover() == null ? prop.noImagePath : getCover();
		response.setContentType("image/" + img.substring(img.lastIndexOf(".")+1));
		response.getOutputStream().write(getCoverImageByte(img));
		response.getOutputStream().flush();
		response.getOutputStream().close();
	}
	public byte[] getCoverImageByte(String img) {
		File imageFile = new File(img);
		byte[] b = new byte[(int)imageFile.length()];
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(imageFile);
			fis.read(b);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(fis != null) try {fis.close();}catch(IOException e){}
		}
		return b;
	}
	*/
	
	public void editSubtitles() {
		try {
			if(isExistSubtitlesFileList()) {
				String[] cmdArray = ArrayUtils.addAll(new String[]{editor}, getSubtitlesFileListPathArray());
				Runtime.getRuntime().exec(cmdArray);
				logger.debug("edit subtitles : [" + VideoUtils.arrayToString(cmdArray) + "]");
				saveHistory(Action.SUBTITLES);
			}
		} catch (IOException e) {
			logger.error("Error : edit subtitles", e);
		}
	}
	
	public void playVideo() {
		try {
			if(isExistVideoFileList()) {
				String[] cmdArray = ArrayUtils.addAll(new String[]{player.toString()}, getVideoFileListPathArray());
				logger.debug("play video : [" + VideoUtils.arrayToString(cmdArray) + "]");
				Runtime.getRuntime().exec(cmdArray);
				saveHistory(Action.PLAY);
			}
		} catch (IOException e) {
			logger.error("Error : play video", e);
		}
	}

	public String getActress() {
		return VideoUtils.arrayToString(actressList);
	}
	public void setActress(String names) {
		String[] namesArr = names.split(",");
		for(String name : namesArr) {
			name = StringUtils.join(StringUtils.split(name), " ");
			boolean bFindSameName = false;
			for(String actress : actressList) {
				if(equalsName(actress, name)) {
					bFindSameName = true;
					break;
				}
			}
			if(!bFindSameName) {
				actressList.add(name.trim());
			}
		}
	}
	public List<String> getActressList() {
		Collections.sort(actressList);
		return actressList;
	}
	public void setActressList(List<String> actressList) {
		this.actressList = actressList;
	}
	private String forwardNameSort(String name) {
		if(name == null) return null;
		String[] nameArr = StringUtils.split(name);
		Arrays.sort(nameArr);
		String retName = "";
		for(String part : nameArr) {
			retName += part + " ";
		}
		return retName.trim();
	}
	private boolean equalsName(String name1, String name2) {
		if(name1 == null || name2 == null) return false;
		return forwardNameSort(name1).equalsIgnoreCase(forwardNameSort(name2)) || name1.toLowerCase().indexOf(name2.toLowerCase()) > -1;
	}
	
	public boolean containsActress(String name) {
		for(String actress : actressList) {
			if(equalsName(actress, name)) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public int compareTo(Object o) {
		Video comp = (Video)o;
		String thisStr = null;
		String compStr = null;
		if("S".equals(sortMethod)) {
			thisStr = this.getStudio();
			compStr = comp.getStudio();
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
	public String getStudio() {
		return studio;
	}

	public void setStudio(String studio) {
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
	
}
