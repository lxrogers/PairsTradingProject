package pairtrading;

import java.sql.ResultSet;
import java.util.ArrayList;

import org.rosuda.JRI.REXP;
import org.rosuda.JRI.RVector;
import org.rosuda.JRI.Rengine;

import algorithms.DataProcessing;

public class MStockPair {
	Stock mStockA, mStockB;
	ArrayList<Double> pvalues;
	public MStockPair(ResultSet r) {
		pvalues = new ArrayList<Double>();
		try {
			String ticker1 = r.getString(TICKER1_INDEX);
			String ticker2 = r.getString(TICKER2_INDEX);
			mStockA = new Stock(ticker1);
			mStockB = new Stock(ticker2);
			for (int i = 0; i < PVALUE_COLUMNS.length; i++) {
				double p = r.getDouble(PVALUE_INDEX + i);
				pvalues.add(p);
			}
			
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
	public double runDickeyFuller(Rengine re, int days, String date) {
		
		String start = DataProcessing.getDateOffsetRString(date, days);
		String end = DataProcessing.getDateOffsetRString(date, 0);
		String t1 = mStockA.getTicker();
		String t2 = mStockB.getTicker();
        re.eval("stock1 = getSymbols('" + t1 + "', src='yahoo', from= '" + start + "', to ='" + end + "', auto.assign=FALSE)");
        re.eval("stock2 = getSymbols('" + t2 + "', src='yahoo', from= '"+ start+ "', to = '" + end + "', auto.assign=FALSE)");
        if (re.eval("stock1") == null ||re.eval("stock2") == null) {
        	System.out.println (t1 + ", " + t2);
        	return 1;
        }
        re.eval("ratio=stock1[,1] / stock2[,1]");
        RVector results = ((RVector) (re.eval("adf.test(unclass(ratio))").getContent()));
        Double ans = results.at(3).asDouble();
        re.end();
        
        return ans;
	}
	/*
	 * @param ratios: an arraylist of stock ratios
	 * returns a double array, Double[0] is the p value, Double[1] is the dickey fuller value
	 */
	public double runDickeyFuller(Rengine re) {
		String date = DataProcessing.getNowRString();
		int days = 135 * 7/5;
		return runDickeyFuller(re, days, date);
	}
	public static double[] getHistoricalPrices(String t, Rengine re, String date) {
		String start = DataProcessing.getDateOffsetRString(date, 135 * 7 / 5);
		String end = DataProcessing.getDateOffsetRString(date, 1);
		re.eval("stock1 = getSymbols('" + t + "', src='yahoo', from= '" + start + "', to ='" + end + "', auto.assign=FALSE)");
		 re.eval("prices=stock1[,1]");
		REXP x = (re.eval("prices"));
		double[] ans = x.asDoubleArray();
		return ans;
	}
	public static double[] getHistoricalRatio(String t1, String t2, Rengine re) {
		String start = DataProcessing.getDateOffsetRString(DataProcessing.getNow(), 135 * 7/5);
		String end = DataProcessing.getNowRString();
        re.eval("stock1 = getSymbols('" + t1 + "', src='yahoo', from= '" + start + "', to ='" + end + "', auto.assign=FALSE)");
        re.eval("stock2 = getSymbols('" + t2 + "', src='yahoo', from= '"+ start+ "', to = '" + end + "', auto.assign=FALSE)");
        re.eval("ratio=stock1[,1] / stock2[,1]");
        REXP x = (re.eval("ratio"));
        double[] ans = x.asDoubleArray();
        re.end();
        return ans;
	}
	public static String getRDateString(int index) {
		if (index == 0) {
			return DataProcessing.getNowRString();
		}
		else {
			return PVALUE_COLUMNS[index];
		}
	}
	public static final int TICKER1_INDEX = 1, TICKER2_INDEX = 2, PVALUE_INDEX = 3;
	public static final String[] PVALUE_COLUMNS = {"pvalue", "2012-01-01"};
}

