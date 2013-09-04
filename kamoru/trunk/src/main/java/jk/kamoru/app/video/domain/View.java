package jk.kamoru.app.video.domain;

public enum View {
	C("Card"), B("Box"), SB("SmallBox"), T("Table"), S("Slide"), L("Large"), V("Video");
	
	private String desc;
	
	View(String desc) {
		this.desc = desc;
	}
	
	public String toString() {
		return desc;
	}

	public String getDesc() {
		return desc;
	}

}
