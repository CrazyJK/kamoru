public class NotePadControler {
	NotePadView notePadView;
	NotePadModel notePadModel;

	// /생성자 부분
	public NotePadControler(NotePadModel notePadModel) {
		this.notePadModel = notePadModel;
		notePadModel.notePadControler = this;
		// 뷰어를 생성
		notePadView = new NotePadView(this);
		notePadView.setBounds(600, 400, 400, 400);
		notePadView.setVisible(true);

	}

	// 블록 여부를 체크해 뷰에 반영
	public void blockSelected(String blockedString) {
		if (notePadModel.setStatusBar(blockedString))
			notePadView.setHasBlocked();
		else
			notePadView.setHasNotBloked();
	}

	// 버퍼(클립보드) 내용 유무를 판별해 뷰에 반영
	public void checkBufferd() {
		if (notePadModel.checkBuffered())
			notePadView.setHasBufferd();
		else
			notePadView.setHasNotBufferd();
	}

	// 현재 좌표 위치를 뷰의 상태표시줄에 알림
	public void checkCursor(String areaText) {
		notePadView.setTextStatusBar(notePadModel.setCursor(areaText));
	}

	// 리스너 탑재
	public void fileMenuHander(String fileMenu) {
		notePadModel.fileMenuHander(fileMenu);
	}

	public void editMenuHander(String editMenu) {
		notePadModel.editMenuHander(editMenu);
	}

	public void formMenuHander(String formMenu) {
		notePadModel.formMenuHander(formMenu);
	}

	public void viewMenuHander(String viewMenu) {
		notePadModel.viewMenuHander(viewMenu);
	}

	public void helpMenuHander(String helpMenu) {
		notePadModel.helpMenuHander(helpMenu);
	}

	public void newFile() {
		notePadView.newFile();
	}

	public void openFile() {
		notePadView.openFile();
	}

	public void saveFile() {
		notePadView.saveFile();
	}

	public void savAsFile() {
		notePadView.saveAsFile();
	}

	public void exit() {
		notePadView.exit();
	}

	public void cut() {
		notePadView.cut();
	}

	public void copy() {
		notePadView.copy();
	}

	public void replaceRange(String replaceString) {
		notePadModel.checkBuffered();
		notePadView.replaceRange(replaceString);
	}

	public void find() {
		notePadView.find();
	}

	public void nextFind() {
		notePadView.nextFind();
	}

	public void replace() {
		notePadView.replace();
	}

	public void selectAll() {
		notePadView.selectAll();
	}

	public void fontStyle() {
		notePadView.fontStyle();
	}

	// Buffer란 클립보드 내용 textBuffer란 바꾸기내용할 내용
	public String getBuffer() {
		return notePadModel.getBuffer();
	}

	public void setTextBuffer(String textBuffer) {
		notePadModel.setTextBuffer(textBuffer);
	}

	public String getTextBuffer() {
		return notePadModel.getTextBuffer();
	}

	public void setReplaceTextBuffer(String replaceTextBuffer) {
		notePadModel.setReplaceTextBuffer(replaceTextBuffer);
	}

	public String getReplaceTextBuffer() {
		return notePadModel.getReplaceTextBuffer();
	}
}
