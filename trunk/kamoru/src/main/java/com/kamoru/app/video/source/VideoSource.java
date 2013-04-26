package com.kamoru.app.video.source;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.kamoru.app.video.domain.Actress;
import com.kamoru.app.video.domain.Studio;
import com.kamoru.app.video.domain.Video;

public interface VideoSource {

	Map<String, Video> getVideoMap();
	
	Map<String, Studio> getStudioMap();
	
	Map<String, Actress> getActressMap();

	void reload();
}
