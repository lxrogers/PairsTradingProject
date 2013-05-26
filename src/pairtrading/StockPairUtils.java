package pairtrading;

import java.sql.ResultSet;
import java.util.ArrayList;

import org.rosuda.JRI.Rengine;

import database.DBConnection;
import database.DatabaseUtils;
public class StockPairUtils {
	public static ArrayList<MStockPair> getStockPairs(DBConnection db, String composite, String date, int sort, double s, double c) {
		ArrayList<MStockPair> pairs = new ArrayList<MStockPair>();
		String query = sort_pairs_query(composite, date, sort, s, c);
		System.out.println("sort: " + query);
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
	public static ArrayList<MStockPair> getStockPairs(DBConnection db, String composite) {
		ArrayList<MStockPair> pairs = new ArrayList<MStockPair>();
		String query = list_pairs_query(composite );
		System.out.println("sort: " + query);
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
		//stationarity
		
		System.out.println("batch complete");
	}

	public static String[] update_pvalue_query(int id, String date, double stationarity, double cointegration) {
		String[] q = new String[2];
		q[0] = "UPDATE stationarityvalues SET `" + date + "` =" + stationarity +" WHERE mstockpairid=" + id + "; ";
		q[1] = 	"UPDATE cointegrationvalues SET `" + date + "` =" + cointegration+" WHERE mstockpairid=" + id + ";";
		return q;
	}
	public static String list_pairs_query(String composite) {
		return "SELECT mstockpairid, ticker1, ticker2 " +
				"FROM mstockpair " +
				"WHERE compositeticker='" + composite + "';";
				
	}

	public static String sort_pairs_query(String composite, String date, int sort, double s_cutoff, double c_cutoff ) {
		String stationarity_value = "s.`" + date + "`";
		String cointegration_value = "c.`" + date + "`";
		String sort_value = (sort == SORT_STATIONARITY) ? stationarity_value : cointegration_value;
		return "SELECT p.mstockpairid, p.ticker1, p.ticker2," + stationarity_value + "," + cointegration_value + 
				" FROM pairstradingdb.mstockpair as p" + 
				" join stationarityvalues as s on p.mstockpairid = s.mstockpairid" +
				" join cointegrationvalues as c on p.mstockpairid = c.mstockpairid" +
				" WHERE  compositeticker=\"" +composite +"\" AND " + stationarity_value + " <= " + s_cutoff + " AND " + cointegration_value + " <= " + c_cutoff + " ORDER BY " + sort_value +" ASC;"; 
	}
	public static final int SORT_STATIONARITY = 0;
	public static final int SORT_COINTEGRATION = 1;
}
