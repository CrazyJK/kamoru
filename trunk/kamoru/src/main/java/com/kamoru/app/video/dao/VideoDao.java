package com.kamoru.app.video.dao;

import java.util.List;

import com.kamoru.app.video.domain.Actress;
import com.kamoru.app.video.domain.Studio;
import com.kamoru.app.video.domain.Video;

public interface VideoDao {

	/**
	 * @return total video list
	 */
	List<Video> getVideoList();
	
	/**
	 * @return total studio list
	 */
	List<Studio> getStudioList();

	/**
	 * @return total actress list
	 */
	List<Actress> getActressList();

	/**
	 * video by opus
	 * @param opus
	 * @return
	 */
	Video getVideo(String opus);
	
	/**
	 * studio by studio name
	 * @param name
	 * @return
	 */
	Studio getStudio(String name);
	
	/**
	 * actress by actress name
	 * @param name
	 * @return
	 */
	Actress getActress(String name);

	/**
	 * delete video
	 * @param opus
	 */
	void deleteVideo(String opus);

	void reload();
}
