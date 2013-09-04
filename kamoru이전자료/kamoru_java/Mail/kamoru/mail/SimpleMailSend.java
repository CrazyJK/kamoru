package kamoru.mail;
import java.io.*;
import java.net.*;
public class SimpleMailSend {

	static PrintStream ps = null;
	static DataInputStream dis = null;

	final String HELO = "HELO ";
	final String MAIL_FROM = "MAIL FROM: ";
	final String RCPT_TO = "RCPT TO: ";
	final String DATA = "DATA";
	final String SUBJECT = "SUBJECT: ";
	final String EOF = "\n\r.\n\r";

	final String SMTP_SERVER = "mail.handysoft.co.kr";
	final int   SMTP_PORT = 25;
	
	private void send(String str) throws IOException{
		ps.println(str);
		ps.flush();
		System.out.println("Java sent: " + str);
	}
	
	private void receive() throws IOException{
		String readstr = dis.readLine();
		System.out.println("SMTP response: "+ readstr);
	}
	
	public void sendMail(){		
		String from = "namjk24@handydata.co.kr";
		String to = from;
		String subject = " subject by kamoru";
		String body = "Body by kamoru";

		Socket smtp = null;
		try{
			smtp = new Socket(SMTP_SERVER, SMTP_PORT);
			
			OutputStream os = smtp.getOutputStream();
			ps = new PrintStream(os);
			InputStream is = smtp.getInputStream();
			dis = new DataInputStream(is);
		}catch(IOException e){
			System.out.println("Error connection: " + e);
		}
		
		try{
			String loc = InetAddress.getLocalHost().getHostName();
			send(HELO + loc);
			receive();
			send(MAIL_FROM + from);
			receive();
			send(RCPT_TO + to);
			receive();
			send(DATA);
			receive();
			send(SUBJECT + subject);
			receive();
			send(body + EOF);
			receive();
			smtp.close();
		}catch(IOException e){
			System.out.println("Error sending: " + e);
		}
		System.out.println("Mail sent!");
	}
	
	public static void main(String[] args) {
		new SimpleMailSend().sendMail();

	}

}
