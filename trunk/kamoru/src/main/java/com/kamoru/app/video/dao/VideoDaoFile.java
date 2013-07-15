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
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

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
		logger.info(search.toString());
		List<Video> list = new ArrayList<Video>();
		for(Video video : videoSource.getVideoList()) {
			if((equals(video.getStudio().getName(), search.getSearchText()) || equals(video.getOpus(), search.getSearchText()) ||
					containsName(video.getTitle(), search.getSearchText()) || containsActress(video, search.getSearchText())) 
					&& (search.isNeverPlay() ? (video.getPlayCount() == 0) : true)
					&& (search.isAddCond()   
						? ((search.isExistVideo() ? video.isExistVideoFileList() : !video.isExistVideoFileList()) && 
						   (search.isExistSubtitles() ? video.isExistSubtitlesFileList() : !video.isExistSubtitlesFileList())) 
						: true)) {
				video.setSortMethod(search.getSortMethod());
				list.add(video);
			}
		}
		if(search.isSortReverse())
			Collections.sort(list, Collections.reverseOrder());
		else
			Collections.sort(list);
		logger.debug("found video size : " + list.size());
		return list;
	}

	@Override
	@Cacheable(value="videoCache")
	public List<Video> getVideoList() {
		logger.info(new String());
		List<Video> list = videoSource.getVideoList(); 
		Collections.sort(list);
		return list;
	}
	
	@Override
	@Cacheable("studioCache")
	public List<Studio> getStudioList() {
		logger.info(new String());
		List<Studio> list = videoSource.getStudioList(); 
		Collections.sort(list);
		return list; 
	}

	@Override
	@Cacheable("actressCache")
	public List<Actress> getActressList() {
		logger.info(new String());
		List<Actress> list = videoSource.getActressList(); 
		Collections.sort(list);
		return list; 
	}

	@Override
	@Cacheable(value="videoCache")
	public Video getVideo(String opus) {
		logger.info(opus);
		return videoSource.getVideo(opus);
	}

	@Override
	@Cacheable("studioCache")
	public Studio getStudio(String name) {
		logger.info(name);
		return videoSource.getStudio(name);
	}

	@Override
	@Cacheable("actressCache")
	public Actress getActress(String name) {
		logger.info(name);
		return videoSource.getActress(name);
	}

	@Override
	@CacheEvict(value = { "videoCache" }, allEntries=true)
	public void deleteVideo(String opus) {
		logger.info(opus);
		videoSource.removeVideo(opus);
	}
	@Override
	@CacheEvict(value = { "videoCache", "studioCache", "actressCache" }, allEntries=true)
	public void reload() {
		logger.info(new String());
		videoSource.reload();
	}


}

