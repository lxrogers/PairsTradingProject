package pairtrading;

import java.sql.ResultSet;
import java.util.ArrayList;

import org.rosuda.JRI.Rengine;

import database.DBConnection;
import database.DatabaseUtils;
public class StockPairUtils {
	public static ArrayList<MStockPair> getStockPairs(DBConnection db, String ticker, String teamID, String pvaluecolumn) {
		ArrayList<MStockPair> pairs = new ArrayList<MStockPair>();
		
		String query = list_pairs_query(ticker, teamID, pvaluecolumn);
		ResultSet r = DatabaseUtils.getResultSetFromDatabase(db, query);
		try {
			r.beforeFirst();
			while (r.next()) {
				pairs.add(new MStockPair(r));
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return pairs;
	}
	public static void batchPValues(DBConnection db, Rengine re) {
		for (String col : MStockPair.PVALUE_COLUMNS) {
			ArrayList<MStockPair> pairs = StockPairUtils.getStockPairs(db, "BTK", "", "pvalue");
			for (MStockPair pair : pairs) {
				double p = 0;
				if (col.equals("pvalue")) {
					p = pair.runDickeyFuller(re);
				}
				else {
					p = pair.runDickeyFuller(re, 135 * 7 /5, col);
				}
				DatabaseUtils.updateDatabase(db, update_pvalue_query(pair.mStockA.getTicker(), pair.mStockB.getTicker(), col, p));
			}
		}

	}
	public static String update_pvalue_query(String ticker1, String ticker2, String column, double p) {
		String q = "UPDATE mstockpair SET `" + column + "` =" + p +" WHERE ticker1=\"" + ticker1 + "\" AND ticker2=\"" + ticker2 + "\";";
		return q;
	}
	public static String list_pairs_query(String composite, String teamID, String sortcolumn) {
		return "SELECT ticker1, ticker2,  pvalue, `2012-01-01`, teamID, mode " +
				"FROM " + 
				"(SELECT * FROM mstockpair WHERE composite =\"" +composite + "\") as pairs " +
				"left join " +
				"tstockpair as tsp " +
				"on pairs.ticker1 = tsp.stockIDA AND pairs.ticker2 = tsp.stockIDB AND tsp.teamID=\"" + teamID + "\" " +
				"ORDER BY pairs.`" + sortcolumn + "` , pairs.ticker1, pairs.ticker2 ASC;";
	}
	
}
