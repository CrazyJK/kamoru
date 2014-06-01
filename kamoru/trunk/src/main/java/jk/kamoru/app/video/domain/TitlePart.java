package jk.kamoru.app.video.domain;

import jk.kamoru.app.video.VideoException;
import jk.kamoru.app.video.util.VideoUtils;
import jk.kamoru.util.StringUtils;
import lombok.Data;

@Data
public class TitlePart implements Comparable<TitlePart> {
	String studio;
	String opus;
	String title;
	String actress;
	String releaseDate;

	public TitlePart() {
	}
	
	public TitlePart(String title) {
		String[] parts = StringUtils.split(title, "]");
		if (parts != null)
			for (int i = 0; i < parts.length; i++) {
				setDate(i, VideoUtils.removeUnnecessaryCharacter(parts[i]));
			}
		else
			throw new VideoException(String.format("parsing error : %s", title));
	}

	private void setDate(int i, String data) {
		switch (i) {
		case 0:
			studio = data;
			break;
		case 1:
			opus = data.toUpperCase();
			break;
		case 2:
			title = data;
			break;
		case 3:
			actress = data;
			break;
		case 4:
			releaseDate = data;
			break;
		default:
			throw new VideoException(String.format(
					"invalid title data. %s : %s", i, data));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.format("[%s][%s][%s][%s][%s]", studio, opus, title,
				actress, releaseDate);
	}

	@Override
	public int compareTo(TitlePart o) {
		return StringUtils.compareTo(this.toString(), o.toString());
	}


}