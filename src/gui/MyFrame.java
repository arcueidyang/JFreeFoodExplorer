package gui;

import java.awt.BorderLayout;
import javax.swing.JFrame;


public class MyFrame {
	
	private JFrame myFrame;
	private CalendarPanel myPanel;
	private AdvertiseButton myAdvertise;
	
	public MyFrame() {
		myFrame = new JFrame("JFreeFoodExplorer");	
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		myPanel = new CalendarPanel();
		myFrame.add(myPanel.getPanel(), BorderLayout.NORTH);
//		myAdvertise = new AdvertiseButton();
//		myFrame.add(myAdvertise.getButton());
		myFrame.setSize(700,530);
		myFrame.setResizable(false);
		myFrame.setLocationRelativeTo(null);
		myFrame.setVisible(true);
	}
	
	
}
