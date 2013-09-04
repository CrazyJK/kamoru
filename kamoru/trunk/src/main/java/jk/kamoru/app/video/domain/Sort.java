package jk.kamoru.app.video.domain;

public enum Sort {

	S("Studis"), O("Opus"), T("Title"), A("Actress"), M("Modified"), P("PlayCount"), R("Rank");
	
	private String sortString;
	
	Sort(String sort) {
		this.sortString = sort;
	}
	
	public String toString() {
		return sortString;
	}

	public String getDesc() {
		return sortString;
	}
}
