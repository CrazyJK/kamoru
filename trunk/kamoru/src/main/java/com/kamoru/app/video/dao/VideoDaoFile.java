package com.kamoru.app.video.dao;

import java.util.List;

import com.kamoru.app.video.domain.Actress;
import com.kamoru.app.video.domain.Studio;
import com.kamoru.app.video.domain.Video;
import com.kamoru.app.video.source.VideoSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

@Repository
public class VideoDaoFile implements VideoDao {
	protected static final Log logger = LogFactory.getLog(VideoDaoFile.class);

	@Autowired
	private VideoSource videoSource;

	@Override
	@Cacheable(value="videoCache")
	public List<Video> getVideoList() {
		logger.info(new String());
		return videoSource.getVideoList();
	}
	
	@Override
	@Cacheable("studioCache")
	public List<Studio> getStudioList() {
		logger.info(new String());
		return videoSource.getStudioList();
	}

	@Override
	@Cacheable("actressCache")
	public List<Actress> getActressList() {
		logger.info(new String());
		return videoSource.getActressList();
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

