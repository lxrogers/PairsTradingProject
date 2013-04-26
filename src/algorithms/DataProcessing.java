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
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;

public class DataProcessing  {

	/* Class Variables */

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
	static public String getStartDateString(int days) {
		Calendar before = Calendar.getInstance();
		before.add(Calendar.DAY_OF_YEAR, days * -1);
		return "" + before.get(Calendar.YEAR) + "-" + before.get(Calendar.MONTH) + "-" + before.get(Calendar.DATE);
	}
	static public String getNowDateString() {
		Calendar now = Calendar.getInstance();
		return "" + now.get(Calendar.YEAR) + "-" + now.get(Calendar.MONTH) + "-" + now.get(Calendar.DATE);
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
