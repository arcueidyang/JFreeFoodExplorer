package mail;

import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class Sender {

	private static final String HOST = "smtp.gmail.com";
	
	private Properties props;
	private Session mySession;
	
	
	public Sender() {
		props = new Properties();
		props.put("mail.smtps.auth", "true");
		mySession = Session.getInstance(props, null);
	}
	
	public void send(String username, String password, String from, String recipient, String subject, String text, List<String> fileName) {
		MimeMessage msg = new MimeMessage(mySession);
	    Multipart mp = new MimeMultipart();
		try {
			msg.setFrom(new InternetAddress(from));
			msg.addRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient, false));
			msg.setSubject(subject);
			msg.setText(text);
			msg.setSentDate(new Date());
			addAttachment(mp, fileName);
			
		} catch (AddressException e1) {
			e1.printStackTrace();
		} catch (MessagingException e1) {
			e1.printStackTrace();
		}
		
		Transport t = null;
		try {
			t = mySession.getTransport("smtps");
		    t.connect(HOST, username, password);
		    t.sendMessage(msg, msg.getAllRecipients());
		} catch (NoSuchProviderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			try {
				t.close();
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	} 
	
	
	public void addAttachment(Multipart mp, List<String> fileName) {
		if(fileName == null || fileName.size() == 0) return;
		MimeBodyPart mbp = null;
		for(String str : fileName) {
			mbp = new MimeBodyPart();
			FileDataSource fds = new FileDataSource(str);
			try {
				mbp.setDataHandler(new DataHandler(fds));
				mbp.setFileName(fds.getName());
				mp.addBodyPart(mbp);
			} catch (MessagingException e) {
				e.printStackTrace();
			}
		}
		
	}
}
