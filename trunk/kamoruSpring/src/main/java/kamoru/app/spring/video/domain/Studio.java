package kamoru.app.spring.video.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Studio implements Serializable{

	private static final long serialVersionUID = 3627610711503320177L;

	private String name;
	private String homepage;
	private String companyName;
	private List<Video> videoList;
	private List<Actress> actressList;
	
	public Studio() {
		videoList = new ArrayList<Video>();
		actressList = new ArrayList<Actress>();
	}

	public Studio(String name) {
		this();
		this.name = name;
	}

	public void putVideo(Video video) {
		if(!videoList.contains(video))
			this.videoList.add(video);		
	}
	public void putActressList(List<Actress> actressList) {
		for(Actress actress : actressList) {
			if(!this.actressList.contains(actress))
				this.actressList.add(actress);
		}
	}
	
	public String getName() {
		return name;
	}
	public String getHomepage() {
		return homepage;
	}
	public String getCompanyName() {
		return companyName;
	}
	public List<Actress> getActressList() {
		return actressList;
	}
	public List<Video> getVideoList() {
		return videoList;
	}

	public void setName(String name) {
		this.name = name;
	}
	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public void setActressList(List<Actress> actressList) {
		this.actressList = actressList;
	}
	public void setVideoList(List<Video> videoList) {
		this.videoList = videoList;
	}
	
}
