package kamoru.app.spring.video.domain;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

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

	String basePath;
	String editor;
	String player;
	String noImagePath;
	
	protected String studio;
	protected String opus;
	protected List<String> actressList;
	protected String title;
	
	protected List<String> videoList;
	protected List<String> subtitlesList;
	protected String cover;
	protected String overview;
	protected String history;
	protected List<String> etcList;
	
	private String sortMethod;

	private final String PLAY = "play";
	private final String OVERVIEW = "overview";
	private final String COVER = "cover";
	private final String SUBTITLES = "subtitles";
	private final String DELETE = "delete";
	
	public Video() {
		this.videoList = new ArrayList<String>();
		this.subtitlesList = new ArrayList<String>();
		this.etcList = new ArrayList<String>();
	}
	public Video(String studio, String opus, List<String> actressList, String title) {
		this();
		this.studio = studio;
		this.opus = opus;
		this.actressList = actressList;
		this.title = title;
	}
	
	public Video(String basePath, String editor, String player, String noImagePath) {
		this();
		this.basePath = basePath;
		this.editor = editor;
		this.player = player;
		this.noImagePath = noImagePath;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("video : ").append(this.getVideoPath()).append(",");
		sb.append("subtitles : ").append(this.getSubtitlesPath()).append(",");
		sb.append("cover : ").append(this.getCover()).append(",");
		sb.append("overview : ").append(this.getOverview()).append(",");
		sb.append("history : ").append(this.getHistory()).append(",");
		sb.append("etc : ").append(this.getEtcPath());
		return sb.toString();
	}
	
	public String getHistory() {
		return history;
	}

	public void setHistory(String history) {
		this.history = history;
	}
	
	public boolean existHistory() {
		return history != null;
	}

	private void saveHistory(String historyMode) {
		history = existHistory() ? getHistory() : getOpusFileName() + ".log";
		String currDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		String msg = null; 
		if(historyMode.equals(PLAY)) {
			msg = "play video : " + this.getVideoPath();
		} else if(historyMode.equals(OVERVIEW)) {
			msg = "write overview : " + this.getOverview();
		} else if(historyMode.equals(COVER)) {
			msg = "view cover : " + this.getCover();
		} else if(historyMode.equals(SUBTITLES)) {
			msg = "edit subtitles : " + this.getSubtitlesPath();
		} else if(historyMode.equals(DELETE)) {
			msg = "delete all : " + this.toString();
		} else {
			msg = "unknown action ";
		}
		String historymsg = MessageFormat.format("{0}, {1}, {2},\"{3}\"{4}", currDate, getOpus(), historyMode, msg, System.getProperty("line.separator"));
		
		try {
			FileUtils.writeStringToFile(new File(history), historymsg, true);
			FileUtils.writeStringToFile(new File(basePath.split(";")[0], "history.log"), historymsg, true);
			logger.debug("save history - " + historymsg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void deleteOpus() {
		this.saveHistory(this.DELETE);
		// video list
		if(this.existVideo())
			for(String video : this.videoList)
				FileUtils.deleteQuietly(new File(video));
		// cover
		if(this.existCover())
			FileUtils.deleteQuietly(new File(this.cover));
		// subtitles list
		if(this.existSubtitles())
		for(String subtitles : this.subtitlesList)
			FileUtils.deleteQuietly(new File(subtitles));
		// overview
		if(this.existOverview())
			FileUtils.deleteQuietly(new File(this.overview));
		// history
		if(this.existHistory())
			FileUtils.deleteQuietly(new File(this.history));
		// etc
		if(this.existEtc())
			for(String etc : this.etcList)
				FileUtils.deleteQuietly(new File(etc));
	}
	public void saveOverViewTxt(String newOverviewTxt) {
		String overviewPath = existOverview() ? getOverview() : getOpusFileName() + ".txt";
		logger.debug("saveOverViewTxt : " + opus + " [" + overviewPath + "]");
		try {
			FileUtils.writeStringToFile(new File(overviewPath), newOverviewTxt, System.getProperty("file.encoding"));
			this.setOverview(overviewPath);
			saveHistory(OVERVIEW);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private String getOpusFileName() {
		if(existVideo()) {
			String videoPath = getVideo().get(0);
			return videoPath.substring(0, videoPath.lastIndexOf("."));
		} else if(existCover()) {
			return getCover().substring(0, getCover().lastIndexOf("."));
		} else if(existOverview()) {
			return getOverview().substring(0, getOverview().lastIndexOf("."));
		} else if(existSubtitles()) {
			String subtitlesPath = getSubtitles().get(0);
			return subtitlesPath.substring(0, subtitlesPath.lastIndexOf("."));
		} else {
			return null;
		}
	}
	public String getOverviewTxt() {
		if(overview == null) {
			return "";
		} else {
			try {
				return FileUtils.readFileToString(new File(overview), System.getProperty("file.encoding"));
			}catch(IOException ioe){
				return "Error:" + ioe.getMessage();
			}
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
			if(subtitlesList.size() > 0) {
				String[] cmdArray = ArrayUtils.addAll(new String[]{editor}, getSubtitlesPathArray());
				Runtime.getRuntime().exec(cmdArray);
				logger.debug("edit subtitles : [" + ArrayUtils.toString(cmdArray) + "]");
				saveHistory(this.SUBTITLES);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void playVideo() {
		try {
			if(videoList.size() > 0) {
				String[] cmdArray = ArrayUtils.addAll(new String[]{player.toString()}, getVideoPathArray());
				logger.debug("play video : [" + ArrayUtils.toString(cmdArray) + "]");
				Runtime.getRuntime().exec(cmdArray);
				this.saveHistory(this.PLAY);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public String getVideoPath() {
		String videopaths = new String();
		for(String videopath : videoList) {
			videopaths += videopath + " ";
		}
		return videopaths;
	}
	private String[] getVideoPathArray() {
		return (String[]) videoList.toArray(new String[0]);
	}
	private String getSubtitlesPath() {
		return ArrayUtils.toString(subtitlesList);
	}
	private String getEtcPath() {
		return ArrayUtils.toString(this.etcList);
	}
	private String[] getSubtitlesPathArray() {
		return (String[]) subtitlesList.toArray(new String[0]);
	}
	public boolean existVideo() {
		return videoList.size() == 0 ? false : true;
	}
	public boolean existSubtitles() {
		return subtitlesList.size() == 0 ? false : true;
	}
	public boolean existCover() {
		return cover == null ? false : true;
	}
	public boolean existOverview() {
		return overview == null ? false : true;
	}
	public boolean existEtc() {
		return etcList.size() == 0 ? false : true;
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

	public String getActress() {
		return actressList == null ? "" : actressList.toString();
	}
	public List<String> getActressList() {
		Collections.sort(actressList);
		return actressList;
	}

	public void setActressList(List<String> actressList) {
		this.actressList = actressList;
	}
	
	public void setActress(String names) {
		if(actressList == null) actressList = new ArrayList<String>();
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
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<String> getVideo() {
		return videoList;
	}

	public void setVideo(String videofile) {
		this.videoList.add(videofile);
	}

	public void setEtc(String etcfile) {
		this.etcList.add(etcfile);
	}

	public List<String> getSubtitles() {
		return subtitlesList;
	}

	public void setSubtitles(String subtitles) {
		this.subtitlesList.add(subtitles);
	}

	public String getCover() {
		return cover;
	}
	public File getCoverImageFile() {
		return new File(cover == null ? noImagePath : cover);
	}
	public void setCover(String cover) {
		this.cover = cover;
	}

	public String getOverview() {
		return overview;
	}

	public void setOverview(String overview) {
		this.overview = overview;
	}

	public void setSortMethod(String sortMethod) {
		this.sortMethod = sortMethod;
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
			thisStr = String.valueOf(this.existVideo() ? new File(this.getVideoPathArray()[0]).lastModified() : 0);
			compStr = String.valueOf(comp.existVideo() ? new File(comp.getVideoPathArray()[0]).lastModified() : 0);
		}
		String[] s = {thisStr, compStr};
		Arrays.sort(s);
		return s[0].equals(thisStr) ? -1 : 1;
	}
	
	
}
