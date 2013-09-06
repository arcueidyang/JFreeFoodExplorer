package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import javax.imageio.ImageIO;
import javax.swing.JButton;

public class CalendarUnit extends JButton {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2783292092231494367L;

	public static final int DEFAULT_WIDTH = 60;
	
	public static final int DEFAULT_HEIGHT = 60;
	
	public static final String NORMAL_IMAGE_PATH = "./resources/normalBackground.jpg";
	
	public static final String TODAY_IMAGE_PATH = "./resources/todayBackground.png";
	
	public enum DayOfWeek {
		Mon, Tue, Wed, Thu, Fri, Sat, Sun
	}
	
	private int myDate;
	private int myMonth;
	private int myYear;
	private int myDayOfWeek;
	private BufferedImage myBackgroundImage;
	
	public CalendarUnit(int x, int y, Dimension size, int date, int month, int year) {
		super();
		setLocation(x, y);
		setSize(size);
		myDate = date;
		myMonth = month;
		myYear = year;
		myDayOfWeek = computeDayOfWeek();
		loadBackgroundImage();
		addListener();
	} 
	
	private void loadBackgroundImage() {
		try {
			myBackgroundImage = ImageIO.read(new File(NORMAL_IMAGE_PATH));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void switchToTodayImage() {
		try {
			myBackgroundImage = ImageIO.read(new File(TODAY_IMAGE_PATH));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public CalendarUnit(int x, int y, int date, int month, int year) {
		this(x, y, new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT), date, month, year);
	}
	
	public CalendarUnit(int date, int month, int year) {
		this(0, 0, date, month, year);
	}
	
	public int computeDayOfWeek() {
		Calendar cal = Calendar.getInstance();
		cal.set(myYear, myMonth, myDate);
		return cal.get(Calendar.DAY_OF_WEEK);
	}
	
	public void addListener() {
		ActionListener listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
			    //TODO
			}
		};
	    addActionListener(listener);
	}
	
	public int getDate() {
		return myDate;
	}
	
	public int getMonth() {
		return myMonth;
	}
	
	public int getYear() {
		return myYear;
	}
	
	public int getDayOfWeek() {
		return myDayOfWeek;
	} 
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		BufferedImage image = new BufferedImage(myBackgroundImage.getWidth(),
				myBackgroundImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics g2 = image.createGraphics();
		g2.drawImage(myBackgroundImage, 0, 0, this);
		paintCalendarInfo(g2);
		paintFoodInfo(g2);
		g.drawImage(image, 0, 0, this);
	}
	
	public void paintCalendarInfo(Graphics g) {
		g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
		g.setColor(Color.BLACK);
		g.drawString(Integer.toString(myYear), (int)g.getFont().getSize()/2,
				           (int)g.getFont().getSize());
		g.drawString(Integer.toString(myMonth), (int)g.getFont().getSize()/2,
				           (int)g.getFont().getSize() * 2);
		g.drawString(Integer.toString(myDate), (int)g.getFont().getSize()/2, 
				           (int)g.getFont().getSize() * 3);
	}
	
	public void paintFoodInfo(Graphics g) {
		//TODO
	}
}
