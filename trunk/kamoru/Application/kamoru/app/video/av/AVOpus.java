package kamoru.app.video.av;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;

public class AVOpus implements Comparable<Object> {
	protected String label;
	protected String opus;
	protected String actress;
	protected String title;
	
	protected List<String> videoList;
	protected List<String> subtitlesList;
	protected String cover;
	protected String overview;
	
	
	public AVOpus() {
		this.videoList = new ArrayList<String>();
		this.subtitlesList = new ArrayList<String>();
	}
	
	public AVOpus(String label, String opus, String actress, String title) {
		this();
		this.label = label;
		this.opus = opus;
		this.actress = actress;
		this.title = title;
	}
	
	public void viewImage(HttpServletResponse response) throws IOException {
		String img = getCover() == null ? AVProp.noImagePath : getCover();
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
	
	public void playVideo() {
		try {
			if(videoList.size() > 0)
				Runtime.getRuntime().exec(
					AVProp.player + " " + getVideoPathForPlay());
			System.out.println(AVProp.player + " " + getVideoPathForPlay());
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
	private String getVideoPathForPlay() {
		String videopaths = new String();
		for(String videopath : videoList) {
			videopaths += "\"" + videopath + "\" ";
		}
		return videopaths;
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

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getOpus() {
		return opus;
	}

	public void setOpus(String opus) {
		this.opus = opus;
	}

	public String getActress() {
		return actress;
	}

	public void setActress(String actress) {
		this.actress = actress;
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

	@Override
	public int compareTo(Object o) {
		AVOpus comp = (AVOpus)o;
		String[] s = {this.getOpus(), comp.getOpus()};
		Arrays.sort(s);
		return s[0].equals(this.getOpus()) ? -1 : 1;
	}
	
	
}
