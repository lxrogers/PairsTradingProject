package batch;

import java.util.ArrayList;

import org.rosuda.JRI.Rengine;

import algorithms.RUtils;

import pairtrading.MStockPair;
import pairtrading.StockPairUtils;
import database.DBConnection;
import database.DatabaseUtils;

public class Batcher {
	private DBConnection db;
	private Rengine re;
	public Batcher() {
		db = new DBConnection();
		re = RUtils.getInitializedInstance();
	}

	public void batchPValues() {
		int count = 0;
		//for (String composite : MStockPair.INDECES) {
			String composite = MStockPair.INDECES[0];
			ArrayList<MStockPair> pairs = StockPairUtils.getStockPairs(db, composite);
			System.out.println(pairs.size());
			String date = "";
			for (int d = 1; d < MStockPair.PVALUE_COLUMNS.length; d++) {
				date = MStockPair.PVALUE_COLUMNS[d];
				for (MStockPair pair : pairs) {
					pvalue(pair, date, composite);
					System.out.println(count++);
					
				}
			}
		//}
			
			System.out.println("done!");
			
	}
	public void pvalue(MStockPair pair, String date, String composite) {
		double stationarity = 0;
		double cointegration = 0;
		if (date.equals("current")) {
			//stationarity = pair.runDickeyFuller(re);
			cointegration = pair.runCointegration(re);
		}
		else {
			//stationarity = pair.runDickeyFuller(re, 135 * 7 /5, date);
			cointegration = pair.runCointegration(re, 135 * 7/5, date);
		}
		if (stationarity != 1.0 || cointegration != 1) {
			String[] q = StockPairUtils.update_pvalue_query(pair.dbID, date, stationarity, cointegration);
			//DatabaseUtils.updateDatabse(db, q[0]);
			DatabaseUtils.updateDatabase(db, q[1]);
			//System.out.println(q[0]);
			System.out.println(q[1]);
		}
		else {
			System.out.println("nothing: " + pair.dbID);
		}
	}
	public static void main(String[] args) {
		Batcher b = new Batcher();
		b.batchPValues();
	}


}
