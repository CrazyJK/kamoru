package kamoru.app.imap;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.net.SocketException;
import java.util.Date;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.apache.commons.io.FileUtils;
import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.imap.IMAPClient;

public class IMapMailChecker extends Thread{

	private static int prevCount = 0;
	
	public void run() {
		while(true) {
			try {
				int count = getUnseenCount();
				if(prevCount < count) {
					notifyWindow(count);
					prevCount = count;
				}
				Thread.sleep(60000);
			} catch (Exception e) {
				break;
			}
		}
	}
	
	public IMapMailChecker() {
		init();
	}
	
	private JFrame frame;
	private void init() {
		frame = new JFrame("New Mail");
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		frame.setSize(250, 100);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(screenSize.width/2 - 125, screenSize.height/2 - 50);
	}
	
	private void notifyWindow(int cnt) {
		Container contentPane = frame.getContentPane();
		contentPane.removeAll();
		JPanel panel = new JPanel();
		panel.add(new JLabel(cnt + " unseen mail!"));
		panel.add(new JLabel(new Date().toString()));
		contentPane.add(panel, BorderLayout.CENTER);
		frame.setVisible(true);
	}
	
	private int getUnseenCount() throws SocketException, IOException {
        String server = "imap.handysoft.co.kr";
        String username = "namjk24@handysoft.co.kr";
        String password = "22222";

        IMAPClient imap = new IMAPClient();
        imap.setDefaultTimeout(60000);
        //System.out.println("Connecting to server " + server + " on " + imap.getDefaultPort());

    	File imap_log_file = new File("IMAMP-UNSEEN");
    	System.out.println(imap_log_file.getAbsolutePath() + " " + new Date().toString());
    	PrintStream ps = new PrintStream(imap_log_file);
        // suppress login details
        imap.addProtocolCommandListener(new PrintCommandListener(ps, true));
        //imap.addProtocolCommandListener(new PrintCommandListener(System.out, true));
        imap.connect(server);
        imap.login(username, password);
        imap.setSoTimeout(6000);
        //imap.capability();
    	//imap.select("inbox");
        //imap.examine("inbox");
        imap.status("inbox", new String[]{"UNSEEN"});
        imap.logout();
        imap.disconnect();
            
        List<String> imap_log = FileUtils.readLines(imap_log_file);
        String unseenText = imap_log.get(4);
        unseenText = unseenText.substring(unseenText.indexOf('(')+1,unseenText.indexOf(')'));
        int unseenCount = Integer.parseInt(unseenText.split(" ")[1]);
        		
        System.out.println(unseenCount);
        return unseenCount;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		IMapMailChecker check = new IMapMailChecker();
		check.start();

	}

}
