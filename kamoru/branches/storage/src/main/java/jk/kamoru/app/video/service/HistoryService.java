package jk.kamoru.app.video.service;

import java.util.Date;
import java.util.List;

import jk.kamoru.app.video.domain.Action;
import jk.kamoru.app.video.domain.Actress;
import jk.kamoru.app.video.domain.History;
import jk.kamoru.app.video.domain.Studio;
import jk.kamoru.app.video.domain.Video;

public interface HistoryService {

	/**<code>History</code> 저장
	 * @param history
	 */
	void persist(History history);
	
	/**opus로 찾기
	 * @param opus
	 * @return list of found history
	 */
	List<History> findByOpus(String opus);

	/**<code>Video</code>로 찾기
	 * @param video
	 * @return list of found history
	 */
	List<History> findByVideo(Video video);

	/**<code>Studio<code>로 찾기
	 * @param studio
	 * @return list of found history
	 */
	List<History> findByStudio(Studio studio);

	/**<code>Actress</code>로 찾기
	 * @param actress
	 * @return list of found history
	 */
	List<History> findByActress(Actress actress);

	/**검색어로 찾기
	 * @param query 검색어
	 * @return list of found history
	 */
	List<History> findByQuery(String query);
	
	/**날자로 찾기
	 * @param date
	 * @return list of found history
	 */
	List<History> findByDate(Date date);

	/**<code>Action</code>로 찾기
	 * @param action
	 * @return
	 */
	List<History> findByAction(Action action);
	
	/**모든 <code>History</code>
	 * @return list of all history
	 */
	List<History> getAll();

}
