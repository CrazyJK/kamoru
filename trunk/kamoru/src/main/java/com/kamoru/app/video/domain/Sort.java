package com.kamoru.app.video.domain;

public enum Sort {

	S("Studis"), O("Opus"), T("Title"), A("Actress"), M("Modified");
	
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
