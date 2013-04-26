package com.kamoru.app.video.domain;

public enum Action {
	PLAY("Play"), OVERVIEW("Overview"), COVER("Cover"), SUBTITLES("Subtitles"), DELETE("Delete");
	
	private String actionString;
	Action(String action) {
		this.actionString = action;
	}
	public String toString() {
		return actionString;
	}
}
