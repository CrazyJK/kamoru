package jk.kamoru.app.video.domain;

/**Video action
 * @author kamoru
 */
public enum Action {
	/**
	 * play video
	 */
	PLAY("Play"), 
	/**
	 * save overview
	 */
	OVERVIEW("Overview"), 
	/**
	 * view cover
	 */
	COVER("Cover"), 
	/**
	 * edit subtitles
	 */
	SUBTITLES("Subtitles"), 
	/**
	 * delete video
	 */
	DELETE("Delete");
	
	/**
	 * action description
	 */
	private String actionString;
	
	Action(String action) {
		this.actionString = action;
	}
	
	public String toString() {
		return actionString;
	}
}
