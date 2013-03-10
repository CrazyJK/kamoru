package kamoru.app.spring.video.source;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.scheduling.annotation.Scheduled;

import kamoru.app.spring.video.domain.Actress;
import kamoru.app.spring.video.domain.Studio;
import kamoru.app.spring.video.domain.Video;
import kamoru.app.spring.video.util.VideoUtils;

public abstract class AbstractVideoSource implements VideoSource {
	protected static final Log logger = LogFactory.getLog(AbstractVideoSource.class);

	@Override
	public abstract Map<String, Video> getVideoMap();
	@Override
	public abstract Map<String, Studio> getStudioMap();
	@Override
	public abstract Map<String, Actress> getActressMap();
	@Override
	public abstract void reload();
	
	@Override
	public Video getVideo(Object opus) {
		Video video = getVideoMap().get(opus);
		if(video == null) throw new RuntimeException("Not found video : " + opus);
		return video;
	}
	
	@Override
	public Studio getStudio(String name) {
		Studio studio = getStudioMap().get(name.toLowerCase());
		if(studio == null) throw new RuntimeException("Not found Studio : " + name);
		return studio;
	}

	@Override
	public Actress getActress(String name) {
		Actress actress = getActressMap().get(VideoUtils.reverseActressName(name));
		if(actress == null) throw new RuntimeException("Not found actress : " + name);
		return actress;
	}

	@Override
	public void removeVideo(Object opus) {
		for(File file : getVideoMap().get(opus).getFileAll())
			FileUtils.deleteQuietly(file);
		getVideoMap().remove(opus);
	}

	@Override
	public List<Video> getVideoList() {
		List<Video> list = new ArrayList<Video>(getVideoMap().values());
		Collections.sort(list);
		return list;
	}
	@Override
	public List<Studio> getStudioList() {
		List<Studio> list = new ArrayList<Studio>(getStudioMap().values()); 
		Collections.sort(list);
		return list;
	}
	@Override
	public List<Actress> getActressList() {
		List<Actress> list = new ArrayList<Actress>(getActressMap().values()); 
		Collections.sort(list);
		return list;
	}

	@Override
	public boolean containsVideo(Object opus) {
		return getVideoMap().containsKey(opus);
	}
	@Override
	public boolean containsStudio(Object studioName) {
		return getStudioMap().containsKey(studioName);
	}
	@Override
	public boolean containsActress(Object actressName) {
		return getActressMap().containsKey(actressName);
	}

}
