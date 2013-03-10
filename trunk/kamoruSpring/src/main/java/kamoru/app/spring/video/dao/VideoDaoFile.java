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

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

/**
 * AV Collection controller<br>
 * data loading, searching, background-image setting
 * @author kamoru
 *
 */
@Component
public class VideoDaoFile implements VideoDao {
	protected static final Log logger = LogFactory.getLog(VideoDaoFile.class);

	@Autowired
	private VideoSource videoSource;

	/**
	 * returns a list that match the given conditions
	 * @param studio
	 * @param opus
	 * @param title
	 * @param actress
	 * @param addCond if true, existVideo and existSubtitles are activated
	 * @param existVideo
	 * @param existSubtitles
	 * @param sortMethod S:studio, O:opus, T:title, A:actress, M:modified
	 * @param sortReverse if true, reverse sort
	 * @param useCache if true, use cache data
	 * @return
	 */
	private List<Video> getAV(
			String[] selectedBasePathId, String studio, String opus, String title, String actress, 
			boolean addCond, boolean existVideo, boolean existSubtitles, 
			String sortMethod, boolean sortReverse, boolean useCache) {
		logger.info("getAV : params[selectedBasePathId:" + ArrayUtils.toString(selectedBasePathId) + ", studio:" + studio + ", opus:" + opus + ", title:" + title + ", actress:" + actress + ", addCond:" + addCond + ", existVideo:" + existVideo + ", existSubtitles:" + existSubtitles + ", sortMethod:" + sortMethod + ", sortReverse:" + sortReverse + ", useCache:" + useCache + "]");
		
		if(!useCache) {
			videoSource.reload();
		}
		
		List<Video> list = new ArrayList<Video>();
		for(Video video : videoSource.getVideoList()) {
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
		logger.info("getAV : found opus size " + list.size());
		if(sortReverse)
			Collections.sort(list, Collections.reverseOrder());
		else
			Collections.sort(list);
		return list;
	}
	
	@Override
	public List<Studio> getStudioList() {
		return videoSource.getStudioList();
	}
	@Override
	public List<Actress> getActressList() {
		return videoSource.getActressList();
	}

	@Override
	public List<Video> getVideoListByParams(Map<String, String> params) {
		String[] selectedBasePathId = null; 
		String studio 	= StringUtils.trim(params.get("studio")); 
		String opus 	= StringUtils.trim(params.get("opus"));
		String title 	= StringUtils.trim(params.get("title"));
		String actress 	= StringUtils.trim(params.get("actress"));
		boolean addCond 		= BooleanUtils.toBoolean(params.get("addCond"));
		boolean existVideo 		= BooleanUtils.toBoolean(params.get("existVideo")); 
		boolean existSubtitles 	= BooleanUtils.toBoolean(params.get("existSubtitles"));
		String listViewType 	= params.get("listViewType");
		if(listViewType == null) {
			listViewType = "box";
			params.put("listViewType", listViewType); 
		}
		String sortMethod 		= params.get("sortMethod");
		boolean sortReverse 	= BooleanUtils.toBoolean(params.get("sortReverse")); 
		boolean useCacheData 	= BooleanUtils.toBoolean(params.get("useCacheData"));
		return getAV(selectedBasePathId, studio, opus, title, actress, addCond, existVideo, existSubtitles, sortMethod, sortReverse, useCacheData);
	}


	@Override
	public Video getVideo(String opus) {
		return videoSource.getVideo(opus);
	}

	@Override
	public void deleteVideo(String opus) {
		videoSource.removeVideo(opus);
	}

	@Override
	public Studio getStudio(String name) {
		return videoSource.getStudio(name);
	}

	@Override
	public Actress getActress(String name) {
		return videoSource.getActress(name);
	}

}

