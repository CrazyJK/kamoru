package jk.kamoru.app.video.service;

import java.io.File;
import java.util.List;
import java.util.Map;

import jk.kamoru.app.video.domain.Actress;
import jk.kamoru.app.video.domain.ActressSort;
import jk.kamoru.app.video.domain.Sort;
import jk.kamoru.app.video.domain.Studio;
import jk.kamoru.app.video.domain.StudioSort;
import jk.kamoru.app.video.domain.Video;
import jk.kamoru.app.video.domain.VideoSearch;

public interface VideoService {

	void deleteVideo(String opus);

	void editVideoSubtitles(String opus);

	List<Map<String, String>> findHistory(String query);

	List<Map<String, String>> findVideoList(String query);

	Actress getActress(String actressName);

	List<Actress> getActressList();

	List<Actress> getActressListInVideoList(List<Video> videoList);

	byte[] getDefaultCoverFileByteArray();

	Studio getStudio(String studioName);

	List<Studio> getStudioList();
	
	List<Studio> getStudioListInVideoList(List<Video> videoList);
	
	Video getVideo(String opus);

	byte[] getVideoCoverByteArray(String opus, boolean isChrome);

	File getVideoCoverFile(String opus, boolean isChrome);

	List<Video> getVideoList();

	void playVideo(String opus);

	void rankVideo(String opus, int rank);

	void saveVideoOverview(String opus, String overViewTxt);

	List<Video> searchVideo(VideoSearch videoSearch);

	Map<String, Long[]> groupByPath();

	void saveActressInfo(String name, Map<String, String> params);

	Map<String, List<Video>> groupByDate();
	
	Map<Integer, List<Video>> groupByRank();
	
	Map<Integer, List<Video>> groupByPlay();

	void moveVideo(String opus, String path);

	void reload();

	void saveStudioInfo(String studio, Map<String, String> params);

	List<Actress> getActressList(ActressSort sort);

	List<Studio> getStudioList(StudioSort sort);

	List<Video> getVideoList(Sort sort);

	List<Integer> getPlayRange();

	Integer minRank();

	Integer maxRank();

	List<Integer> getRankRange();

	void removeLowerRankVideo();

	void removeLowerScoreVideo();

	void deleteGarbageFile();

	void moveWatchedVideo();

	void arrangeVideo();
	
}
