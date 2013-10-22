package jk.kamoru.app.video.domain;

public enum Sort {

	S("Studio"), O("Opus"), T("Title"), A("Actress"), M("Modified"), P("PlayCount"), R("Rank"), L("Length");
	
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
