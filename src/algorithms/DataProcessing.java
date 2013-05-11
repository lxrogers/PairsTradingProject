/*
 * File: DataProcessing.java
 * -------------------------
 * This file pulls in stock data in .csv form and tokenizes it,
 * putting the prices into an array of daily opens.
 */

/* Package Designation */
package algorithms;

/* Imports */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class DataProcessing  {

	/* Class Variables */
	/*
	 * format: 
	 */
	static public Calendar getCalendar(String rdate) {
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date date = formatter.parse(rdate);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			return calendar;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;

	}
	static public String getDateRString(Calendar c) {
		return "" + c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH) + 1) + "-" + c.get(Calendar.DATE);
	}
	static public String getXMostRecentDaysDataURL(String ticker, int days) {
		Calendar now = Calendar.getInstance();
		Calendar before = Calendar.getInstance();
		before.add(Calendar.DAY_OF_YEAR, days * -1);  
		//http://ichart.finance.yahoo.com/table.csv?s=PEP&a=08&b=26&c=2012&d=03&e=11&f=2013&g=d&ignore=.csv
		String url = "http://ichart.finance.yahoo.com/table.csv?s=" + ticker + "&a=" +
				before.get(Calendar.MONTH) + "&b=" + before.get(Calendar.DAY_OF_MONTH) + "&c=" + before.get(Calendar.YEAR) + "&d=" +
				now.get(Calendar.MONTH) + "&e=" + now.get(Calendar.DAY_OF_MONTH) + "&f=" + now.get(Calendar.YEAR)+ "&g=ignore=.csv";


		return url;
	}
	static public String getNowRString() {
		return getDateRString(getNow());
	}
	static public String getDateOffsetRString(String date, int days) {
		Calendar c = getCalendar(date);
		return getDateOffsetRString(c, days);
	}
	static public String getDateOffsetRString(Calendar c, int days) {
		Calendar before = c;
		before.add(Calendar.DAY_OF_YEAR, days * -1);
		return getDateRString(before);
	}

	static public Calendar getNow() {
		Calendar now = Calendar.getInstance();
		return now;
	}
	static public ArrayList<String> getXMostRecentDaysData(String ticker, int days) {
		ArrayList<String> dataLines = new ArrayList<String>();
		try {
			String url = getXMostRecentDaysDataURL(ticker, days);
			URL oracle = new URL(url);
			BufferedReader in = new BufferedReader(	new InputStreamReader(oracle.openStream()));
			String inputLine;
			while ((inputLine = in.readLine()) != null)
				dataLines.add(inputLine);
			in.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return dataLines;
	}
}
