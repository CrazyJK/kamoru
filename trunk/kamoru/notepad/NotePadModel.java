import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.datatransfer.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JTextField;

public class NotePadModel {
	NotePadControler notePadControler;

	String buffer = ""; // 클립보드 내용
	String textBuffer = ""; // 찾기나 바꾸기할때 텍스트 필드 항목
	String replaceTextBuffer = ""; // 바꾸기할때 텍스트 필드 항목

	// 블록된것이 있는가 판별
	public boolean setStatusBar(String blockedString) {
		try {
			if (blockedString.length() > 0 || blockedString != null) {
				return true;
			} else {
				return false;
			}
		} catch (NullPointerException ne) {
			return false;
		}
	}

	// 버퍼(클립보드)내용을 받아옴
	public boolean checkBuffered() {
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		Transferable clipData = clipboard.getContents(clipboard);
		if (clipData != null) {
			try {
				buffer = (String) (clipData
						.getTransferData(DataFlavor.stringFlavor));
			} catch (Exception ce) {
				ce.printStackTrace();
			}
		}
		if (!buffer.equals("")) {
			return true;
		} else {
			return false;
		}

	}// 현재 커서의 위치를 1차원배열(y축,x축)에 넣어 리턴

	public int[] setCursor(String areaText) {
		int y = 1;
		int x = 1;
		int z = 0; // y:줄 x:칸 z최종적으로 잘라내어지는 길이
		for (int i = 0; i < areaText.length(); i++) {
			if (areaText.charAt(i) == '\n') {
				y = y + 1;
				z = i + 1;
			}
		}
		x = x + areaText.length() - z;
		return new int[] { y, x };
	}

	public void fileMenuHander(String fileMenu) {
		if (fileMenu.equals("새로만들기")) {
		} else if (fileMenu.equals("열기")) {
			notePadControler.openFile();
		} else if (fileMenu.equals("저장")) {
			notePadControler.saveFile();
		} else if (fileMenu.equals("다른이름으로  저장")) {
		} else if (fileMenu.equals("끝내기")) {
			notePadControler.exit();
		}

	}

	public void editMenuHander(String editMenu) {
		if (editMenu.equals("실행취소")) {
		} else if (editMenu.equals("잘라내기")) {
			notePadControler.cut();
		} else if (editMenu.equals("복사")) {
			notePadControler.copy();
		} else if (editMenu.equals("붙여넣기")) {
			if (checkBuffered())
				notePadControler.replaceRange(buffer);
		} else if (editMenu.equals("삭제")) {
			notePadControler.replaceRange("");
		} else if (editMenu.equals("찾기")) {
			notePadControler.find();
		} else if (editMenu.equals("다음찾기")) {
			notePadControler.nextFind();
		} else if (editMenu.equals("바꾸기")) {
			notePadControler.replace();
		} else if (editMenu.equals("모두선택")) {
			notePadControler.selectAll();
		}
	}

	public void formMenuHander(String formMenu) {
		if (formMenu.equals("자동  줄  바꿈")) {
		} else if (formMenu.equals("글꼴")) {
			notePadControler.fontStyle();
		}
	}

	public void viewMenuHander(String viewMenu) {
	}

	public void helpMenuHander(String helpMenu) {
	}

	public String getBuffer() {
		return buffer;
	}

	public void setTextBuffer(String textBuffer) {
		this.textBuffer = textBuffer;
	}

	public String getTextBuffer() {
		return textBuffer;
	}

	public void setReplaceTextBuffer(String replaceTextBuffer) {
		this.replaceTextBuffer = replaceTextBuffer;
	}

	public String getReplaceTextBuffer() {
		return replaceTextBuffer;
	}

}
