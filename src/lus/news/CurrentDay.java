package lus.news;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class CurrentDay extends JFrame{
	static int day,month,year;
	static GregorianCalendar cal;
	static Calendar c;
	public CurrentDay() {
		// TODO Auto-generated constructor stub
		JButton bt= new JButton("¿Ã¿¸");
		cal = new GregorianCalendar(Locale.KOREA); 
		c = Calendar.getInstance(Locale.KOREA);
		//Date date = c.getTime();
		
		day = c.get(Calendar.DATE);
		System.out.println(day);
		month = c.get(Calendar.MONTH)+1;
		year = c.get(Calendar.YEAR);
		if(month<10) {
			String str="0";
			str = str+month;
			System.out.println(year+str+day);
		}else {
			System.out.println(year+month+day);
	
		}
		add(bt);
		bt.addActionListener(new ActionListener() {
			

			public void actionPerformed(ActionEvent e) {
				getCurrentDate();
				
			}
		});
		setSize(400,200);
		setVisible(true);
	}
	public static String getCurrentDate() {
		String str="";
		c = Calendar.getInstance(Locale.KOREA);
		day = c.get(Calendar.DATE);
		month = c.get(Calendar.MONTH)+1;
		year = c.get(Calendar.YEAR);
		if(month<10) {
			str="0";
			str = str+month;
			str = year+str+day;
		}else {
			str = Integer.toString(year)+Integer.toString(month)+Integer.toString(day);
	
		}
		return str;
	}
	public static String getCurrentMonth() {
		String str="";
		c = Calendar.getInstance(Locale.KOREA);
		day = c.get(Calendar.DATE);
		month = c.get(Calendar.MONTH)+1;
		year = c.get(Calendar.YEAR);
		if(month<10) {
			str="0";
			str = str+month;
			str = year+str+day;
		}else {
			str = Integer.toString(year)+Integer.toString(month);
	
		}
		return str;
	}

}
