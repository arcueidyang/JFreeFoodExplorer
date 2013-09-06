package gui;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class CalendarPanel {

	public static final String RESOURCE_PATH = "./resources/";
	
	public static final String LEFT_ARROW_FILENAME = "arrow_left.jpg";
	
	public static final String RIGHT_ARROW_FILENAME = "arrow_right.jpg";

	
	final private JPanel myPanel;
	private JButton previousButton;
	private JButton nextButton;
	
	public CalendarPanel() {
		myPanel = new JPanel();
		myPanel.setLayout(new GridLayout(5,7));
		myPanel.setSize(new Dimension(700, 500));
		myPanel.setPreferredSize(new Dimension(700, 500));
		generateCalendarUnits();
		generateButtons(myPanel);
	}
	
	public void generateCalendarUnits() {
		Calendar current = Calendar.getInstance();
		int year = current.get(Calendar.YEAR);
		int month = current.get(Calendar.MONTH) + 1;
		int date = current.get(Calendar.DATE);
		current.get(month);
		int numberOfDays = current.getActualMaximum(Calendar.DAY_OF_MONTH);
		for(int i = 0; i < numberOfDays; i++) {
			CalendarUnit cu = new CalendarUnit(year, month, i + 1);
			if(i == date - 1) {
				cu.switchToTodayImage();
			}
			myPanel.add(cu);
		}	
	}
	
	
	public void generateButtons(Container panel) {
		generatePreviousButton(panel);
		generateNextButton(panel);
	}
	
	public void generatePreviousButton(Container panel) {
		previousButton = new JButton();
		ImageIcon ic = new ImageIcon(RESOURCE_PATH + LEFT_ARROW_FILENAME);
		previousButton.setIcon(ic);
		ActionListener listener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
		        //TODO    
			}
			
		};
		previousButton.addActionListener(listener);
		panel.add(previousButton);
	}
	
	public void generateNextButton(Container panel) {
		nextButton = new JButton();
		ImageIcon ic = new ImageIcon(RESOURCE_PATH + RIGHT_ARROW_FILENAME);
		nextButton.setIcon(ic);
		ActionListener listener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
			
		};
		nextButton.addActionListener(listener);
		panel.add(nextButton);
	}
	
	public Container getPanel() {
		return myPanel;
	}
	
}
