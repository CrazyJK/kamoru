import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


public class MessageBox extends JFrame {

	JTextArea taMain;
	JScrollPane scrollPane;

	public MessageBox(String title, String msg) {
		Container con = getContentPane();
		con.setLayout(new BorderLayout());
		setTitle(title);
		
		taMain = new JTextArea(msg);
		taMain.setEditable(false);
		taMain.setLineWrap(true);
		scrollPane = new JScrollPane(taMain);
		con.add(scrollPane, "Center");
		setBounds(600, 800, 550, 200);
		setVisible(true);

	}
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		MessageBox msg = new MessageBox("Test-title", "message");
		msg.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

}
