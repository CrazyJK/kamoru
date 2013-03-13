package kamoru.app.spring.video.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import kamoru.app.spring.video.domain.Actress;
import kamoru.app.spring.video.domain.Studio;
import kamoru.app.spring.video.domain.Video;
import kamoru.app.spring.video.source.VideoSource;
import kamoru.app.spring.video.util.VideoUtils;

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

	@Override
	public List<Video> getVideoList(
			String studio, String opus, String title, String actress, 
			boolean addCond, boolean existVideo, boolean existSubtitles, 
			String sortMethod, boolean sortReverse) {
//		logger.debug("getAV : params[studio:" + studio + ", opus:" + opus + ", title:" + title + ", actress:" + actress + ", addCond:" + addCond + ", existVideo:" + existVideo + ", existSubtitles:" + existSubtitles + ", sortMethod:" + sortMethod + ", sortReverse:" + sortReverse + "]");
		
		List<Video> list = new ArrayList<Video>();
		for(Video video : getVideoList()) {
			if((studio  == null || studio.trim().length()  == 0 || studio.equalsIgnoreCase(video.getStudio().getName())) 
			&& (opus    == null || opus.trim().length()    == 0 || opus.equalsIgnoreCase(video.getOpus()))
			&& (title   == null || title.trim().length()   == 0 || video.getTitle().toLowerCase().indexOf(title.toLowerCase()) > -1) 
			&& (actress == null || actress.trim().length() == 0 || video.containsActress(actress))
			&& (addCond ? (existVideo ? video.isExistVideoFileList() : !video.isExistVideoFileList()) && (existSubtitles ? video.isExistSubtitlesFileList() : !video.isExistSubtitlesFileList()) : true)
			) {
				if(sortMethod != null && sortMethod.trim().length() > 0) {
					video.setSortMethod(sortMethod);
				}
				list.add(video);
			}
		}
//		logger.debug("getAV : found opus size " + list.size());
		if(sortReverse)
			Collections.sort(list, Collections.reverseOrder());
		else
			Collections.sort(list);
		return list;
	}

	@Override
	public List<Video> getVideoList() {
		List<Video> list = new ArrayList<Video>(videoSource.getVideoMap().values());
		Collections.sort(list);
		return list;
	}
	@Override
	public List<Studio> getStudioList() {
		List<Studio> list = new ArrayList<Studio>(videoSource.getStudioMap().values()); 
		Collections.sort(list);
		return list;
	}
	@Override
	public List<Actress> getActressList() {
		List<Actress> list = new ArrayList<Actress>(videoSource.getActressMap().values()); 
		Collections.sort(list);
		return list;
	}

	@Override
	public void deleteVideo(String opus) {
		videoSource.getVideoMap().get(opus).removeVideo();
		videoSource.reload();
	}

	@Override
	@Cacheable(value="videoCache")
	public Video getVideo(String opus) {
		Video video = videoSource.getVideoMap().get(opus.toLowerCase());
		if(video == null) throw new RuntimeException("Not found video : " + opus);
		return video;
	}

	@Override
	@Cacheable("studioCache")
	public Studio getStudio(String name) {
		Studio studio = videoSource.getStudioMap().get(name.toLowerCase());
		if(studio == null) throw new RuntimeException("Not found Studio : " + name);
		return studio;
	}

	@Override
	@Cacheable("actressCache")
	public Actress getActress(String name) {
		Actress actress = videoSource.getActressMap().get(VideoUtils.reverseActressName(name));
		if(actress == null) throw new RuntimeException("Not found actress : " + name);
		return actress;
	}

}

