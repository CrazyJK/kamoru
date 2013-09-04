package kamoru.test.net;

import java.awt.*;

import java.net.*;

import java.io.*;

import java.awt.event.*;

public class PageViewer extends Frame {

	private TextField tURL = new TextField(""); // URL을 입력하는 텍스트필드

	// 웹 페이지의 소스를 보여주는 텍스트영역

	private TextArea tPage = new TextArea();

	private BufferedReader reader; // 입력 스트림

	public PageViewer(String title) { // 생성자

		super(title);

		tPage.setEditable(false);

		add(tURL, "North");

		add(tPage, "Center");

		// tURL의 액션 이벤트 처리

		tURL.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent ae) {

				try {

					// tURL의 내용에 해당하는 URL 객체가 참조하는 웹 페이지를 읽는다.

					readPage(new URL(tURL.getText()));

				} catch (MalformedURLException ie) {

					tPage.setText("URL이 잘못되었습니다.");

				}

			}

		});

	}

	// url 객체가 참조하는 웹 페이지를 읽어오는 메소드

	void readPage(URL url) {

		tPage.setText("");

		String line;

		try {

			// url의 입력 스트림을 얻는다.

			reader = new BufferedReader(new InputStreamReader(url.openStream()));

			// 한 줄 단위로 읽어서 tPage에 추가한다.

			while ((line = reader.readLine()) != null)

				tPage.append(line + "\n");

		} catch (IOException ie) {

			tPage.setText("입출력 예외가 발생하였습니다.");

		} finally {

			try {

				if (reader != null)
					reader.close();

			} catch (Exception e) {
			}

		}

	}

	public static void main(String[] args) {

		PageViewer me = new PageViewer("Page Viewer");

		me.setSize(400, 400);

		me.setVisible(true);

	}

}