package kamoru.test.net;

import java.awt.*;
import java.net.*;
import java.io.*;
import java.awt.event.*;

public class Client5 extends Frame implements Runnable {

	private TextField nameBox = new TextField("<이름>"); // 사용자 이름을 나타낸다.
	private TextArea msgView = new TextArea();
	private TextField sendBox = new TextField();
	private BufferedReader reader;
	private PrintWriter writer;
	private Socket socket;

	public Client5(String title) {

		super(title);
		msgView.setEditable(false);

		// 컨트롤들을 배치한다.
		add(nameBox, "North");
		add(msgView, "Center");
		add(sendBox, "South");

		// sendBox 액션 이벤트 처리
		sendBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				try {
					// 사용자의 이름과 메시지 내용을 전송한다.
					writer.println(nameBox.getText() + " : "
							+ sendBox.getText());
					sendBox.setText(""); // sendBox의 내용을 지운다.
				} catch (Exception ie) {
				}
			}
		});
		pack();
	}

	public void run() {
		while (true) {
			try {
				// 입력 스트림으로부터 데이터를 읽어서 msgView에 추가한다.
				msgView.append(reader.readLine() + "\n");
			} catch (IOException ie) {
			}
		}
	}

	private void connect() {
		try {
			msgView.append("서버소켓과의 연결을 시도합니다.\n");
			socket = new Socket("127.0.0.1", 7777);
			msgView.append("채팅 준비가 완료되었습니다.\n");
			reader = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			writer = new PrintWriter(socket.getOutputStream(), true);
			new Thread(this).start();
		} catch (Exception e) {
			msgView.append("연결 실패..");
		}
	}

	public static void main(String[] args) {
		Client5 client = new Client5("채팅");
		client.setVisible(true);
		client.connect();
	}
}