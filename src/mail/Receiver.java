package mail;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.InternetAddress;

import com.sun.xml.internal.messaging.saaj.packaging.mime.internet.MimeUtility;


public class Receiver {

	private static final String HOST = "pop.gmail.com";
    
	private static final String USER_NAME = "wangdeyangzi@gmail.com";
	
	private static final String USER_PASSWORD = "416690802531";
	
	private static final int THRESHOLD_DAYS = 7;
	
	private Properties props;
	private Session mySession;
	private Store myStore;
	private Folder myInbox;
	private Message[] myMessages;
	private Date myThresholdDate;
	
	
	public Receiver() {
		props = new Properties();
		mySession = Session.getInstance(props, null);
		try {
			myStore = mySession.getStore("pop3s");
		} catch (NoSuchProviderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		myThresholdDate = createThresholdDate();
	}
	
	@SuppressWarnings("deprecation")
	public Date createThresholdDate() {
		Calendar currentDate = Calendar.getInstance();
		int day = currentDate.get(Calendar.DAY_OF_MONTH);
		int month = currentDate.get(Calendar.MONTH);
		int year = currentDate.get(Calendar.YEAR);
		if(month == 1) {
			if(day < THRESHOLD_DAYS){
				int difference = THRESHOLD_DAYS - day;
				year --;
				month = 12;
				currentDate.set(Calendar.YEAR, year);
				currentDate.set(Calendar.MONTH, month);
				day = currentDate.getActualMaximum(Calendar.DATE);
				day -= difference;
			}else {
				day -= THRESHOLD_DAYS;
			}
		}else {
			if(day < THRESHOLD_DAYS) {
				int difference = THRESHOLD_DAYS - day;
				month --;
				currentDate.set(Calendar.MONTH, month);
				day = currentDate.getActualMaximum(Calendar.DATE);
				day -= difference;
			}else{
				day -= THRESHOLD_DAYS;
			}
		}
		Date thresholdDate = new Date();
		thresholdDate.setDate(day);
		thresholdDate.setMonth(month);
		thresholdDate.setYear(year);
		return thresholdDate; 
	}
	
	public void fetchMail() {
		try {
			myStore.connect(HOST, USER_NAME, USER_PASSWORD);
			myInbox = myStore.getFolder("INBOX");
			myInbox.open(Folder.READ_ONLY);
			myMessages = myInbox.getMessages();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
    }
	
	//test method
	public void printMail() throws MessagingException, IOException {
		int length = myMessages.length;
		for(int i = length - 1; i >= 0 ; i --) {
			Message msg = myMessages[i];
			String from = decodeText(msg.getFrom()[0].toString());
			InternetAddress ia = new InternetAddress(from);
			System.out.println("From : " + ia.getPersonal() + "(" + ia.getAddress() + ")");
			System.out.println("Title : " + msg.getSubject());
			System.out.println("Size : " + msg.getSize());
			System.out.println("Date : " + msg.getSentDate());
            System.out.println(getTextContent(msg));
		}
	}
	
	public String getTextContent(Message msg) throws IOException, MessagingException {
		if(!(msg.getContent() instanceof Multipart)) {
			return msg.getContent().toString();
		}
		Multipart content = (Multipart)msg.getContent();
		int bodypartNumber = content.getCount();
		BodyPart bp = null;
		for(int i = 0; i < bodypartNumber; i++) {
		    bp = content.getBodyPart(i);
		    if(bp.getContentType().toLowerCase().contains("text")) {
		    	return bp.getContent().toString();
		    }
		}
		return "Empty";
	}
	
	public Message[] getMessages() {
		return myMessages;
	}
	
	public List<Message> extractLatestMails() {
		Date msgDate = null;
		List<Message> latestMails = new ArrayList<Message>();
		for(int i = myMessages.length - 1; i >= 0; i --) {
			try {
				msgDate = myMessages[i].getSentDate();
				if(msgDate.after(myThresholdDate)) {
					latestMails.add(myMessages[i]);
				}
			} catch (MessagingException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Mails Extracted");
		return latestMails;
	}
	
	
	public void close() {
		try {
			myInbox.close(false);
			myStore.close();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected static String decodeText(String text) throws UnsupportedEncodingException {
		if(text == null) return null;
		if(text.startsWith("=?GB") ||text.startsWith("=?gb"))
			 return MimeUtility.decodeText(text);
		else  return new String(text.getBytes("ISO8859_1"));
	}
	
	public int getMailSize() {
		return myMessages.length;
	}
	
	public Date getThresholdDate() {
		return myThresholdDate;
	}
	
	public static void main(String[] args) {
		Receiver receiver = new Receiver();
		receiver.fetchMail();
		List<Message> msgs = receiver.extractLatestMails();
		System.out.println(msgs.size());
		try {
			receiver.printMail();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(receiver.getMailSize());
		receiver.close();
	}
}
