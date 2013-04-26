package com.kamoru.app.video.dao;

import java.util.List;
import java.util.Map;

import com.kamoru.app.video.domain.Actress;
import com.kamoru.app.video.domain.Studio;
import com.kamoru.app.video.domain.Video;
import com.kamoru.app.video.domain.VideoSearch;

public interface VideoDao {

	List<Video> getVideoList();
	
	List<Studio> getStudioList();

	List<Actress> getActressList();

	Video getVideo(String opus);
	
	Studio getStudio(String name);
	
	Actress getActress(String name);

	void deleteVideo(String opus);

	List<Video> searchVideo(VideoSearch videoSearch);

}
