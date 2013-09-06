package mail;

public class MailProcessor {

	private Sender mySender;
	private Receiver myReceiver;
	
	
	public MailProcessor() {
		mySender = new Sender();
		myReceiver = new Receiver();
	}
	
}
