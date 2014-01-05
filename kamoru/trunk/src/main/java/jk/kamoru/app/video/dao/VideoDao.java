package jk.kamoru.app.video.dao;

import java.util.List;

import jk.kamoru.app.video.domain.Actress;
import jk.kamoru.app.video.domain.Studio;
import jk.kamoru.app.video.domain.Video;

/**
 * Video DAO
 * @author kamoru
 */
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
	 * @param studio name
	 * @return
	 */
	Studio getStudio(String name);
	
	/**
	 * actress by actress name
	 * @param actress name
	 * @return
	 */
	Actress getActress(String name);

	/**
	 * delete video
	 * @param opus
	 */
	void deleteVideo(String opus);

	/**
	 * move video
	 * @param opus
	 * @param destination path
	 */
	void moveVideo(String opus, String destPath);
	
	/**
	 * reload video source
	 */
	void reload();

	/**
	 * arrange video
	 * @param opus
	 */
	void arrangeVideo(String opus);
}
