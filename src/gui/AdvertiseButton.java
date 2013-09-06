package gui;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class AdvertiseButton {

	private static final String ADVERTISE_IMAGE_FILENAME = "advertise.jpg";
	
	private static final String ADVERTISE_URL = "http://www.baozijj.com/";
	
	private JButton myButton;
	
	public AdvertiseButton() {
		myButton = new JButton();
		myButton.setIcon(new ImageIcon(CalendarPanel.RESOURCE_PATH + ADVERTISE_IMAGE_FILENAME));
		addListener();
	}
	
	public void addListener() {
		ActionListener listener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(Desktop.isDesktopSupported()) {
					URI uri;
					try {
						uri = new URI(ADVERTISE_URL);
						Desktop dp = Desktop.getDesktop();
						if(dp.isSupported(Desktop.Action.BROWSE)) {
							dp.browse(uri);
						}
					} catch (URISyntaxException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			}
		};
		myButton.addActionListener(listener);
	}
	
	public JButton getButton() {
		return myButton;
	}
	
}
