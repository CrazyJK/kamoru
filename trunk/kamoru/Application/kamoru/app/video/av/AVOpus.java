package kamoru.app.video.av;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class AVOpus implements Comparable<Object> {
	protected static final Log logger = LogFactory.getLog(AVOpus.class);
	private AVProp prop = AVProp.getInstance();

	protected String studio;
	protected String opus;
	protected List<String> actressList;
	protected String title;
	
	protected List<String> videoList;
	protected List<String> subtitlesList;
	protected String cover;
	protected String overview;
	
	private String sortMethod;
	
	public AVOpus() {
		this.videoList = new ArrayList<String>();
		this.subtitlesList = new ArrayList<String>();
	}
	
	public AVOpus(String studio, String opus, List<String> actressList, String title) {
		this();
		this.studio = studio;
		this.opus = opus;
		this.actressList = actressList;
		this.title = title;
	}
	
	public void saveOverViewTxt(String newOverviewTxt) {
		String overviewPath = getOverviewPath();
		logger.debug("saveOverViewTxt : " + opus + " [" + overviewPath + "]");
		try {
			FileUtils.writeStringToFile(new File(overviewPath), newOverviewTxt, System.getProperty("file.encoding"));
			this.setOverview(overviewPath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private String getOverviewPath() {
		if(existOverview()) {
			return getOverview();
		} else if(existVideo()) {
			String videoPath = getVideo().get(0);
			return videoPath.substring(0, videoPath.lastIndexOf(".")) + ".txt";
		} else if(existCover()) {
			return getCover().substring(0, getCover().lastIndexOf(".")) + ".txt";
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
	
	public void playVideo() {
		try {
			if(videoList.size() > 0) {
				String[] cmdArray = ArrayUtils.addAll(new String[]{prop.player}, getVideoPathArray());
				Runtime.getRuntime().exec(cmdArray);
				logger.debug("playvideo : [" + ArrayUtils.toString(cmdArray) + "]");
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
	
	public void setActress(String name) {
		if(actressList == null) actressList = new ArrayList<String>();
//		logger.debug(opus + " setActress name:" + name);
		String[] nameArr = name.split(",");
		for(String _name : nameArr) {
//			logger.debug(opus + " setActress _name:" + _name);
			boolean bFindSameName = false;
			for(String actress : actressList) {
//				logger.debug(opus + " setActress actress:" + actress);
				if(equalsName(actress, _name)) {
					bFindSameName = true;
				}
			}
			if(!bFindSameName) {
				actressList.add(_name.trim());
//				logger.debug(opus + " setActress add:" + _name);
			}
		}
	}
	private boolean equalsName(String name1, String name2) {
//		logger.debug("equalsName #1: " + name1 + " - " + name2);
		if(name1 == null || name2 == null) return false;
		String[] name1Arr = name1.toLowerCase().trim().split(" ");
		String[] name2Arr = name2.toLowerCase().trim().split(" ");
		ArrayUtils.reverse(name1Arr);
		ArrayUtils.reverse(name2Arr);
		name1 = ArrayUtils.toString(name1Arr);
		name2 = ArrayUtils.toString(name2Arr);
//		logger.debug("equalsName #2: " + name1 + " - " + name2);
		if(name1.equals(name2))
			return true;
		else 
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

	public List<String> getSubtitles() {
		return subtitlesList;
	}

	public void setSubtitles(String subtitles) {
		this.subtitlesList.add(subtitles);
	}

	public String getCover() {
		return cover;
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
		AVOpus comp = (AVOpus)o;
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
