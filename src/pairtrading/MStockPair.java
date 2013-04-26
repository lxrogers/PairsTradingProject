package pairtrading;

import java.sql.ResultSet;
import java.util.ArrayList;

import org.rosuda.JRI.REXP;
import org.rosuda.JRI.RVector;
import org.rosuda.JRI.Rengine;

import algorithms.DataProcessing;

public class MStockPair {
	Stock mStockA, mStockB;
	double mPValue;
	public MStockPair(ResultSet r) {
		try {
			String ticker1 = r.getString(TICKER1_INDEX);
			String ticker2 = r.getString(TICKER2_INDEX);
			mStockA = new Stock(ticker1);
			mStockB = new Stock(ticker2);
			double p = r.getDouble(PVALUE_INDEX);
			mPValue = p;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	public MStockPair(String ticker1, String ticker2, double p) {
		this(new Stock(ticker1), new Stock(ticker2), p);
	}
	public MStockPair(Stock stocka, Stock stockb, double p) {
		mStockA = stocka;
		mStockB = stockb;
		mPValue = p;
	}
	public static String getListHTMLHeader() {
		String header = "<table width=30%>" +
					"<td>Ticker1</td><td>Ticker2</td><td>P-Value</td>";
		return header;
	}
	public String getListHTML() {
		String url = "mstockpair.jsp?t1=" +mStockA.getTicker() + "&t2=" + mStockB.getTicker();
		String html ="<tr style=\"cursor:pointer\" onclick=\"document.location.href='" + url + "';\" >" +
					"<td>" +mStockA.getTicker() + "</a></td>" + 
					"<td>" + mStockB.getTicker() + "</td>" + 
					"<td>" + getPValue() + "</td>" +
					"</tr>";
		return html;
	}
	public static String getListHTMLFooter() {
		String footer = "</table>";
		return footer;
	}
	public double getPValue() {
		return mPValue;
	}
	public String getLink() {
		return "";
	}
	/*
	 * @param ratios: an arraylist of stock ratios
	 * returns a double array, Double[0] is the p value, Double[1] is the dickey fuller value
	 */
	public double runDickeyFuller(Rengine re) {
		String start = DataProcessing.getStartDateString(135 * 7/5);
		String end = DataProcessing.getNowDateString();
		String t1 = mStockA.getTicker();
		String t2 = mStockB.getTicker();
        re.eval("stock1 = getSymbols('" + t1 + "', src='yahoo', from= '" + start + "', to ='" + end + "', auto.assign=FALSE)");
        re.eval("stock2 = getSymbols('" + t2 + "', src='yahoo', from= '"+ start+ "', to = '" + end + "', auto.assign=FALSE)");
        re.eval("ratio=stock1[,1] / stock2[,1]");
        RVector results = ((RVector) (re.eval("adf.test(ratio)").getContent()));
        Double ans = results.at(3).asDouble();
        re.end();
        
        return ans;
	}
	public static double[] getHistoricalPrices(String t, Rengine re) {
		String start = DataProcessing.getStartDateString(135 * 7/5);
		String end = DataProcessing.getNowDateString();
		re.eval("stock1 = getSymbols('" + t + "', src='yahoo', from= '" + start + "', to ='" + end + "', auto.assign=FALSE)");
		 re.eval("prices=stock1[,1]");
		REXP x = (re.eval("prices"));
		double[] ans = x.asDoubleArray();
		System.out.println(re.eval("stock1"));
		return ans;
	}
	public static double[] getHistoricalRatio(String t1, String t2, Rengine re) {
		String start = DataProcessing.getStartDateString(135 * 7/5);
		String end = DataProcessing.getNowDateString();
        re.eval("stock1 = getSymbols('" + t1 + "', src='yahoo', from= '" + start + "', to ='" + end + "', auto.assign=FALSE)");
        re.eval("stock2 = getSymbols('" + t2 + "', src='yahoo', from= '"+ start+ "', to = '" + end + "', auto.assign=FALSE)");
        re.eval("ratio=stock1[,1] / stock2[,1]");
        REXP x = (re.eval("ratio"));
        System.out.println(re.eval("ratio"));
        double[] ans = x.asDoubleArray();
        re.end();
        return ans;
	}
	public static final int TICKER1_INDEX = 1, TICKER2_INDEX = 2, PVALUE_INDEX = 3;
}
