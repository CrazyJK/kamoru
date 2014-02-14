package jk.kamoru.app.video.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jk.kamoru.app.video.VideoException;
import jk.kamoru.app.video.dao.HistoryDao;
import jk.kamoru.app.video.domain.Action;
import jk.kamoru.app.video.domain.Actress;
import jk.kamoru.app.video.domain.History;
import jk.kamoru.app.video.domain.Studio;
import jk.kamoru.app.video.domain.Video;
import jk.kamoru.util.StringUtils;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class HistoryServiceImpl implements HistoryService {

	@Autowired HistoryDao historyDao;
	
	@Override
	public void persist(History history) {
		try {
			historyDao.persist(history);
		} catch (IOException e) {
			throw new VideoException(e);
		}
	}

	@Override
	public List<History> findByOpus(String opus) {
		log.info("find opus {}", opus);
		if (StringUtils.isBlank(opus))
			return dummyList();
		return historyDao.findByOpus(opus);
	}

	@Override
	public List<History> findByVideo(Video video) {
		return historyDao.findByVideo(video);
	}

	@Override
	public List<History> findByStudio(Studio studio) {
		return historyDao.findByVideo(studio.getVideoList());
	}

	@Override
	public List<History> findByActress(Actress actress) {
		return historyDao.findByVideo(actress.getVideoList());
	}

	@Override
	public List<History> findByQuery(String query) {
		if (StringUtils.isBlank(query))
			return dummyList();
		return historyDao.find(query);
	}

	@Override
	public List<History> getAll() {
		return historyDao.getList();
	}

	@Override
	public List<History> findByDate(Date date) {
		return historyDao.findByDate(date);
	}

	@Override
	public List<History> findByAction(Action action) {
		return historyDao.findByAction(action);
	}
	
	private List<History> dummyList() {
		return new ArrayList<History>();
	}
}
