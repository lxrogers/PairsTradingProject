package pairtrading;

import java.sql.ResultSet;
import java.util.ArrayList;

import org.rosuda.JRI.Rengine;

import database.DBConnection;
import database.DatabaseUtils;
public class StockPairUtils {
	public static ArrayList<MStockPair> getStockPairs(DBConnection db, String ticker, String teamID) {
		ArrayList<MStockPair> pairs = new ArrayList<MStockPair>();
		String query = list_pairs_query(ticker, teamID);
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
		ArrayList<MStockPair> pairs = StockPairUtils.getStockPairs(db, "BTK", "");
		
		for (MStockPair pair : pairs) {
			double p = pair.runDickeyFuller(re);
			DatabaseUtils.updateDatabase(db, update_pvalue_query(pair.mStockA.getTicker(), pair.mStockB.getTicker(), p));
		}
		
	}
	public static String update_pvalue_query(String ticker1, String ticker2, double p) {
		return "UPDATE mstockpair SET pvalue=" + p +" WHERE ticker1=\"" + ticker1 + "\" AND ticker2=\"" + ticker2 + "\";";
	}
	public static String list_pairs_query(String composite, String teamID) {
		return "SELECT ticker1, ticker2, pvalue, teamID, mode " +
				"FROM " + 
					"(SELECT * FROM mstockpair WHERE composite =\"" +composite + "\") as pairs " +
				"left join " +
					"tstockpair as tsp " +
					"on pairs.ticker1 = tsp.stockIDA AND pairs.ticker2 = tsp.stockIDB AND tsp.teamID=\"" + teamID + "\" " +
					"ORDER BY pairs.pvalue, pairs.ticker1, pairs.ticker2 ASC;";
	}
}
