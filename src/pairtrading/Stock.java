package pairtrading;

import java.util.ArrayList;

import org.rosuda.JRI.Rengine;

import database.DBConnection;
import database.DatabaseUtils;

public class Stock {
	private String mTicker;
	private String mFullName;
	
	public Stock(String ticker) {
		mTicker = ticker;
	}
	
	public String getTicker() {
		return mTicker;
	}
	public static ArrayList<String> getComposites(DBConnection db) {
		String query = "SELECT * FROM mcomposite;";
		return DatabaseUtils.getStringArrayFromDatabase(db, query);
	}
	
}
