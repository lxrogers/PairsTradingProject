package pairtrading;

import java.sql.ResultSet;
import java.util.ArrayList;

import org.rosuda.JRI.REXP;
import org.rosuda.JRI.RVector;
import org.rosuda.JRI.Rengine;

import algorithms.DataProcessing;

public class MStockPair {
	public int dbID;
	Stock mStockA, mStockB;
	ArrayList<Double> pvalues;
	public MStockPair(ResultSet r) {
		pvalues = new ArrayList<Double>();
		try {
			dbID = r.getInt(1);
			String ticker1 = r.getString(TICKER1_INDEX);
			String ticker2 = r.getString(TICKER2_INDEX);
			mStockA = new Stock(ticker1);
			mStockB = new Stock(ticker2);


		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	public MStockPair(String ticker1, String ticker2, ArrayList<Double> p) {
		this(new Stock(ticker1), new Stock(ticker2), p);
	}
	public MStockPair(Stock stocka, Stock stockb, ArrayList<Double> p) {
		mStockA = stocka;
		mStockB = stockb;
		pvalues = p;
	}
	public static String getListHTMLHeader() {
		String header = "<table width=30%>" +
				"<td>Ticker1</td><td>Ticker2</td><td>P-Value</td>";
		return header;
	}
	public static String getPairURL(String t1, String t2, int index) {
		return "mstockpair.jsp?t1=" +t1 + "&t2=" + t2 + "&d=" + index;
	}
	public String getListHTML(int index) {
		String url = getPairURL(mStockA.getTicker(), mStockB.getTicker(), 0);
		String html ="<tr style=\"cursor:pointer\" onclick=\"document.location.href='" + url + "';\" >" +
				"<td>" +mStockA.getTicker() + "</a></td>" + 
				"<td>" + mStockB.getTicker() + "</td>" + 
				"<td>" + pvalues.get(index) + "</td>" +
				"</tr>";
		return html;
	}
	public static String getListHTMLFooter() {
		String footer = "</table>";
		return footer;
	}
	public double getPValue(int index) {
		return pvalues.get(index);
	}
	public String getLink() {
		return "";
	}
	public double runDickeyFuller(Rengine re) {
		String date = DataProcessing.getNowRString();
		int days = 135 * 7/5;
		return runDickeyFuller(re, days, date);
	}
	public double runDickeyFuller(Rengine re, int days, String date) {
		String start = DataProcessing.getDateOffsetRString(date, days);
		String end = DataProcessing.getDateOffsetRString(date, 0);
		String t1 = mStockA.getTicker();
		String t2 = mStockB.getTicker();
		String call = "df_test('" + t1 + "', '" + t2 + "', '" + start + "', '" + end + "')";
		REXP e = re.eval(call);
		
		re.end();
		if (e!= null) {
			RVector results = ((RVector) (e.getContent()));
			return results.at(3).asDouble();
		}
		return 1;
	}
	public double runCointegration(Rengine re) {
		String date = DataProcessing.getNowRString();
		int days = 135 * 7/5;
		return runCointegration(re, days, date);
	}
	public double runCointegration(Rengine re, int days, String date) {
		String start = DataProcessing.getDateOffsetRString(date, days);
		String end = DataProcessing.getDateOffsetRString(date, 0);
		String t1 = mStockA.getTicker();
		String t2 = mStockB.getTicker();
		String call = "cointegration_check('" + t1 + "', '" + t2 + "', '" + start + "', '" + end + "')";
		REXP e = re.eval(call);
		
		re.end();
		if (e!= null) {
			RVector results = ((RVector) (e.getContent()));
			return results.at(3).asDouble();
		}
		return 1;
	}
		
	public static double[] getHistoricalPrices(String t, Rengine re, String date) {
		String start = DataProcessing.getDateOffsetRString(date, 135 * 7 / 5);
		String end = DataProcessing.getDateOffsetRString(date, 0);
		re.eval("stock1 = getSymbols('" + t + "', src='yahoo', from= '" + start + "', to ='" + end + "', auto.assign=FALSE)");
		re.eval("prices=stock1[,1]");
		REXP x = (re.eval("prices"));
		double[] ans = x.asDoubleArray();
		return ans;
	}
	public static double[] getHistoricalRatio(String t1, String t2, Rengine re, String date) {
		String start = DataProcessing.getDateOffsetRString(date, 135 * 7 / 5);
		String end = DataProcessing.getDateOffsetRString(date, 0);
		re.eval("stock1 = getSymbols('" + t1 + "', src='yahoo', from= '" + start + "', to ='" + end + "', auto.assign=FALSE)");
		re.eval("stock2 = getSymbols('" + t2 + "', src='yahoo', from= '"+ start+ "', to = '" + end + "', auto.assign=FALSE)");
		re.eval("ratio=stock1[,1] / stock2[,1]");
		REXP x = (re.eval("ratio"));
		double[] ans = x.asDoubleArray();
		re.end();
		return ans;
	}
	public static double getOUDays(Rengine re, String t1, String t2, String date) {
		String start = DataProcessing.getDateOffsetRString(date, 135 * 7 / 5);
		String end = DataProcessing.getDateOffsetRString(date, 0);
		REXP x = re.eval("ou_check(" + t1 + ", " + t2 + ", " + start + "," + end + ")");
		return x.asDouble();
	}
	public static double getHistorialRatioAvg(String t1, String t2, Rengine re, String date) {
		double[] ratios = getHistoricalRatio(t1, t2, re, date);
		double sum = 0;
		for (int i = 0; i < ratios.length; i++) {
			sum+= ratios[i];
		}
		return sum/ratios.length;
	}
	public static double getCurrentRatio(String t1, String t2, Rengine re, String date) {
		double[] ratios = getHistoricalRatio(t1, t2, re, date);
		return ratios[ratios.length - 1];
	}
	public static String getRDateString(int index) {
		if (index == 0) {
			return DataProcessing.getNowRString();
		}
		else {
			return PVALUE_COLUMNS[index];
		}
	}
	public static final int TICKER1_INDEX = 2, TICKER2_INDEX = 3;
	public static final String[] PVALUE_COLUMNS = {"current", "2012-01-01","2011-01-01","2010-01-01","2009-01-01","2008-01-01"};
	public static final String[] INDECES = {"NYE.ID", "BTK", "NYFIN"};
}

