package com.kamoru.app.video.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.kamoru.app.video.VideoException;
import com.kamoru.app.video.domain.Actress;
import com.kamoru.app.video.domain.Studio;
import com.kamoru.app.video.domain.Video;
import com.kamoru.app.video.domain.VideoSearch;
import com.kamoru.app.video.source.VideoSource;
import com.kamoru.app.video.util.VideoUtils;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * AV Collection controller<br>
 * data loading, searching, background-image setting
 * @author kamoru
 *
 */
@Repository
public class VideoDaoFile implements VideoDao {
	protected static final Log logger = LogFactory.getLog(VideoDaoFile.class);

	@Autowired
	private VideoSource videoSource;

	private boolean equals(String target, String compare) {
		return compare == null || compare.trim().length() == 0 || target.equalsIgnoreCase(compare);
	}
	private boolean containsName(String target, String compare) {
		return compare == null || compare.trim().length() == 0 || StringUtils.containsIgnoreCase(target, compare);
	}
	private boolean containsActress(Video target, String compare) {
		return compare == null || compare.trim().length() == 0 || target.containsActress(compare);
	}
	@Override
	public List<Video> searchVideo(VideoSearch search) {
		logger.debug(search.toString());
/*		return this.getVideoList(
				videoSearch.getStudio(), videoSearch.getOpus(), videoSearch.getTitle(), videoSearch.getActress(), 
				videoSearch.isAddCond(), videoSearch.isExistVideo(), videoSearch.isExistSubtitles(), 
				videoSearch.getSortMethod(), videoSearch.isSortReverse(), videoSearch.isNeverPlay());
	}
	
	private List<Video> getVideoList(
			String studio, String opus, String title, String actress, 
			boolean addCond, boolean existVideo, boolean existSubtitles, 
			String sortMethod, boolean sortReverse, boolean neverPlay) {
*/		
		List<Video> list = new ArrayList<Video>();
		for(Video video : getVideoList()) {
			if(    equals(video.getStudio().getName(), search.getStudio()) 
				&& equals(video.getOpus(),             search.getOpus())
				&& containsName(video.getTitle(),      search.getTitle()) 
				&& containsActress(video,              search.getActress())
				&& (search.isNeverPlay() ? (video.getPlayCount() == 0) : true)
				&& (search.isAddCond()   
						? ((search.isExistVideo() ? video.isExistVideoFileList() : !video.isExistVideoFileList()) 
								&& (search.isExistSubtitles() ? video.isExistSubtitlesFileList() : !video.isExistSubtitlesFileList())) 
						: true)
			) {
				video.setSortMethod(search.getSortMethod());
				list.add(video);
			}
			else {
//				logger.debug("제외된 비디오 : " + video);
			}
		}
//		logger.debug("getAV : found opus size " + list.size());
		if(search.isSortReverse())
			Collections.sort(list, Collections.reverseOrder());
		else
			Collections.sort(list);
//		logger.debug("getAV : found opus size " + list.size());
		return list;
	}

	@Override
	@Cacheable(value="videoCache")
	public List<Video> getVideoList() {
		return new ArrayList<Video>(videoSource.getVideoMap().values());
	}
	
	@Override
	@Cacheable("studioCache")
	public List<Studio> getStudioList() {
		return new ArrayList<Studio>(videoSource.getStudioMap().values()); 
	}

	@Override
	@Cacheable("actressCache")
	public List<Actress> getActressList() {
		return new ArrayList<Actress>(videoSource.getActressMap().values()); 
	}

	@Override
	@Cacheable(value="videoCache")
	public Video getVideo(String opus) {
		Video video = videoSource.getVideoMap().get(opus.toLowerCase());
		if(video == null) throw new VideoException("Not found video : " + opus);
		return video;
	}

	@Override
	@Cacheable("studioCache")
	public Studio getStudio(String name) {
		Studio studio = videoSource.getStudioMap().get(name.toLowerCase());
		if(studio == null) throw new VideoException("Not found Studio : " + name);
		return studio;
	}

	@Override
	@Cacheable("actressCache")
	public Actress getActress(String name) {
		Actress actress = videoSource.getActressMap().get(VideoUtils.forwardNameSort(name));
		if(actress == null) throw new VideoException("Not found actress : " + name);
		return actress;
	}

	@Override
	public void deleteVideo(String opus) {
		videoSource.getVideoMap().get(opus.toLowerCase()).removeVideo();
		videoSource.reload();
	}


}

