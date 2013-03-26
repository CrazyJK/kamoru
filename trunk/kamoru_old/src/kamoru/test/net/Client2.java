package kamoru.test.net;

import java.awt.*;

import java.net.*;

import java.io.*;

import java.awt.event.*;

public class Client2 extends Frame {

	private TextArea msgView = new TextArea(); // 메시지를 보여주는 텍스트영역

	private TextField sendBox = new TextField(); // 문자열을 입력하는 텍스트필드

	private PrintWriter writer; // 소켓의 출력 스트림

	Socket socket; // 소켓

	public Client2(String title) { // 생성자

		super(title);

		msgView.setEditable(false); // 텍스트영역을 사용자가 편집할 수 없도록 한다.

		add(msgView, "North"); // 컴포넌트 배치

		add(sendBox, "South");

		// sendBox에 문자열을 입력하고 엔터를 누르면

		// 문자열을 전송한다.

		sendBox.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent ae) {

				// sendBox의 문자열을 출력 스트림으로 전송한다.

				writer.println(sendBox.getText());

				// msgView에 보낸 문자열을 추가한다.

				msgView.append(sendBox.getText() + "\n");

				// sendBox의 내용을 지운다.

				sendBox.setText("");

			}

		});

		pack(); // 프레임의 크기를 자동으로 맞춘다.

	}

	private void connect() { // 서버와 연결하는 메소드

		try {

			msgView.append("서버와의 연결을 시도합니다.\n");

			socket = new Socket("127.0.0.1", 7777);

			msgView.append("연결되었습니다. 전송할 문자열을 입력하세요.\n");

			// socket의 출력 스트림을 얻는다.

			writer = new PrintWriter(socket.getOutputStream(), true);

		} catch (Exception e) {

			msgView.append("연결 실패..");

		}

	}

	public static void main(String[] args) {

		Client2 client = new Client2("서버로 데이터 보내기");

		client.setVisible(true);

		client.connect();

	}

}
