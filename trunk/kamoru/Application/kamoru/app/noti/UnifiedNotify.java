package kamoru.app.noti;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.imap.IMAPClient;

public class UnifiedNotify {

	protected final Log logger = LogFactory.getLog(UnifiedNotify.class);

	public UnifiedNotify() {
	}

	public void start() {
		logger.debug("Unified Notify start...");
		// 1. noti file watchdog start
		NotiWatchdog dog = new NotiWatchdog();
		dog.start();
		// 2. BPM noti receiver start
		BPMnotiReceiver bpm = new BPMnotiReceiver();
		bpm.start();
		// 3. GW mail checker start
		GWmailChecker gw = new GWmailChecker();
		gw.start();
	}
	
	public static void main(String[] args) {
		org.apache.log4j.PropertyConfigurator.configureAndWatch(NotiConst.log4jpath);
		
		UnifiedNotify un = new UnifiedNotify();
		un.start();
	}

}

class BPMnotiReceiver extends Thread {
	protected final Log logger = LogFactory.getLog(BPMnotiReceiver.class);
	
    public void run() {
    	DatagramSocket socket = null;
    	try {
        	int port = NotiConst.bpmUDPport;
            byte msg[] = new byte[1024];
            DatagramPacket packet = new DatagramPacket(msg, msg.length);
    		socket = new DatagramSocket(port);
            do
            {
                logger.debug("Waiting to receive by port " + port);
                socket.receive(packet);
                String message = new String(packet.getData(), 0, packet.getLength());
                InetAddress from = packet.getAddress();
                String currDate = new SimpleDateFormat("yyyy-MM-dd' 'HH:mm:ss").format(new Date());
                String stdout = "From[" + from.getHostAddress() + ":" + packet.getPort() + "]" + "[" + message + "]";
                logger.debug("received " + stdout);
                
                FileUtils.writeStringToFile(new File(NotiConst.notiFileDir, NotiConst.bpmnotiPrefixTXT + "_" + System.currentTimeMillis() + ".noti"), stdout, "UTF-8");
                
            } while(true);
    	} catch(Exception e) {
    		e.printStackTrace();
    	} finally {
    		if(socket != null) socket.close();
    	}
    }

}
class GWmailChecker extends Thread {
	protected final Log logger = LogFactory.getLog(GWmailChecker.class);

	private static int prevCount = 0;
	
	public void run() {
		while(true) {
			try {
				int count = getUnseenCount();
				logger.debug(prevCount + " -> " + count);
				if(prevCount < count) {
					saveNotifyFile(count);
				}
				prevCount = count;
				Thread.sleep(NotiConst.interval);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private int getUnseenCount() throws SocketException, IOException {
        String server = "imap.handysoft.co.kr";
        String username = "namjk24@handysoft.co.kr";
        String password = "22222";

        IMAPClient imap = new IMAPClient();
        imap.setDefaultTimeout(60000);
        //System.out.println("Connecting to server " + server + " on " + imap.getDefaultPort());

    	File imap_log_file = new File(".IMAMP-UNSEEN");
//    	System.out.println(imap_log_file.getAbsolutePath() + " " + new Date().toString());
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
        		
//        System.out.println(unseenCount);
        return unseenCount;
	}

	private void saveNotifyFile(int count) throws IOException {
		FileUtils.writeStringToFile(new File(NotiConst.notiFileDir, NotiConst.gwmailPrefixTXT + "_" + System.currentTimeMillis()+".noti"), count + " unseen mails", "UTF-8");
	}
}
class NotiWatchdog extends Thread {
	protected final Log logger = LogFactory.getLog(NotiWatchdog.class);
	
	public NotiWatchdog() {
		clearNotiDir();
	}
	
	public void run() {
		while(true) {
			try {
				Collection<File> files = FileUtils.listFiles(new File(NotiConst.notiFileDir), new String[]{"noti"}, false);
				logger.debug("found file size " + files.size());
				for(File file : files) {
					NotiWindow noti = new NotiWindow(file);
					noti.start();
				}
				Thread.sleep(NotiConst.interval);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void clearNotiDir() {
		try {
			FileUtils.forceMkdir(new File(NotiConst.notiFileDir));
			FileUtils.cleanDirectory(new File(NotiConst.notiFileDir));
			logger.debug(NotiConst.notiFileDir + " clean");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
class NotiWindow extends Thread {
	protected final Log logger = LogFactory.getLog(NotiWindow.class);

	private File file;
	private JFrame frame;

	public NotiWindow(File file) {
		this.file = file;
		init();
	}
	
	public void run() {
		try {
			String str = FileUtils.readFileToString(file, "UTF-8");
			String[] filenames = file.getName().split("_");
			popupNotifyWindow(filenames[0], str);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void init() {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(250, 100);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(screenSize.width/2 - 125, screenSize.height/2 - 50);
	}
	
	private void popupNotifyWindow(String title, String str) {
		init();
		frame.setTitle(title);
		Container contentPane = frame.getContentPane();
		contentPane.removeAll();
		JPanel panel = new JPanel();
		panel.setBackground(new Color(71, 70, 65));
	
		JLabel mailLabel = new JLabel(str);
		mailLabel.setFont(new Font("", Font.BOLD, 20));
		mailLabel.setForeground(Color.WHITE);
		
		JLabel dateLabel = new JLabel(new Date().toString());
		dateLabel.setForeground(Color.LIGHT_GRAY);
		
		panel.add(mailLabel);
		panel.add(dateLabel);
		contentPane.add(panel, BorderLayout.CENTER);
		frame.setVisible(true);
		logger.debug("popup");
		FileUtils.deleteQuietly(file);
	}
	
}
class NotiConst {
	protected final static String notiFileDir = "/home/kamoru/bin/noti";
	protected final static int interval = 60000;
	protected final static int bpmUDPport = 7213;
	protected final static String gwmailPrefixTXT = "New-GW-Mail";
	protected final static String bpmnotiPrefixTXT = "CS-RnD-Request";
	public final static String log4jpath = "/home/kamoru/bin/log4j.lcf";
}