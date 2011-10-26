package kamoru.mail;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;

public class SMTPMailSendManager {

	Logger logger = Logger.getLogger(getClass());

	private String protocol = "smtp";
	private String type = "text/html; charset=KSC5601";

	private String userName;
	private String password;
	private String host = "127.0.0.1";
	private int port = 25;
	private boolean starttlsEnable = false;

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setStarttlsEnable(boolean starttlsEnable) {
		this.starttlsEnable = starttlsEnable;
	}

	public void send(
			String[] toAddress,  String[] toName, 
			String[] ccAddress,  String[] ccName, 
			String[] bccAddress, String[] bccName, 
			String fromAddress,  String fromName, 
			String subject,      String content) throws Exception 
	{
		logger.debug("[{}] 메일 발송 시작" + toAddress);
		try 
		{
			Properties props = new Properties();
			props.put("mail.transport.protocol", protocol);
			props.put("mail.smtp.host", host);
			props.put("mail.smtp.port", String.valueOf(port));

			Authenticator authenticator = null;
			if (null != userName && userName.trim().length() > 0) {
				props.put("mail.smtp.auth", "true");
				authenticator = new SMTPAuthenticator(userName, password);
			}

			if (starttlsEnable) {
				props.put("mail.smtp.starttls.enable", Boolean.toString(starttlsEnable));
			}

			Session session = Session.getInstance(props, authenticator);

			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(fromAddress, fromName));
			
			if(toAddress != null)
			for(int i=0 ; i<toAddress.length ; i++){
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(toAddress[i], toName[i]));
			}
			if(ccAddress != null)
			for(int i=0 ; i<ccAddress.length ; i++){
				message.addRecipient(Message.RecipientType.CC, new InternetAddress(ccAddress[i], ccName[i]));
			}
			if(bccAddress != null)
			for(int i=0 ; i<bccAddress.length ; i++){
				message.addRecipient(Message.RecipientType.BCC, new InternetAddress(bccAddress[i], bccName[i]));
			}
			
			message.setSubject(subject);
			
			message.setContent(content, type);

			Transport.send(message);
			
			logger.debug("[{}] 메일 발송 성공 " + toAddress);
		} 
		catch (UnsupportedEncodingException e) 
		{
			logger.error("[{}] 메일 발송 실패" + toAddress);
			throw new Exception("메일을 발송하는 중 에러가 발생했습니다.", e);
		} 
		catch (MessagingException e) 
		{
			logger.error("[{}] 메일 발송 실패" + toAddress);
			throw new Exception("메일을 발송하는 중 에러가 발생했습니다.", e);
		}
	}

	class SMTPAuthenticator extends Authenticator 
	{
		PasswordAuthentication passwordAuthentication;

		SMTPAuthenticator(String userName, String password) {
			passwordAuthentication = new PasswordAuthentication(userName, password);
		}

		public PasswordAuthentication getPasswordAuthentication() {
			return passwordAuthentication;
		}
	}
	
	public static void main(String[] args) throws Exception{
		SMTPMailSendManager mail = new SMTPMailSendManager();
		mail.setHost("lgekrhqmh01.lge.com");
		mail.setHost("phoenix.handysoft.co.kr");
//		mail.setHost("smtp.gmail.com");
		mail.setPort(25);
		mail.setUserName("namjk24@handydata.co.kr");
		mail.setPassword("22222");
		mail.setStarttlsEnable(true);
		mail.send(
				new String[]{"namjk24@handydata.co.kr"}, new String[]{"남종관"}, 
				null,null,null,null,
				"namjk24@gmail.com", "남종관kAmOrU", 
				"메일테스트2", "Test 테스트");
	}
}