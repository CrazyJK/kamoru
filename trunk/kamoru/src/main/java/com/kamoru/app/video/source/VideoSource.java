package com.kamoru.app.video.source;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.kamoru.app.video.domain.Actress;
import com.kamoru.app.video.domain.Studio;
import com.kamoru.app.video.domain.Video;

public interface VideoSource {

	/**
	 * 전체 Video 맵. &lt;opus, video&gt;
	 * @return
	 */
	Map<String, Video> getVideoMap();
	
	/**
	 * 전체 Studio 맵. &lt;opus, studio&gt;
	 * @return
	 */
	Map<String, Studio> getStudioMap();
	
	/**
	 * 전체 Actress 맵. &lt;opus, actress&gt;
	 * @return
	 */
	Map<String, Actress> getActressMap();

	/**
	 * 비디오 리로드.
	 */
	void reload();
}
