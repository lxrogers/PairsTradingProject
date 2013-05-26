package simulation;

import java.sql.ResultSet;
import java.util.ArrayList;

import database.DBConnection;
import database.DatabaseUtils;

public class SimulationStrategy {
	public final String name;
	public final double enter_threshold;
	public final double exit_threshold;
	public final double stationarity_cutoff;
	public final double cointegration_cutoff;
	
	
	public SimulationStrategy(ResultSet r) {
		String temp_name = "";
		double temp_enter_threshold = 0;
		double temp_exit_threshold = 0;
		double temp_stationarity_cutoff = 0;
		double temp_cointegration_cutoff = 0;
		try {
			temp_name = r.getString(NAME_INDEX);
			temp_enter_threshold = r.getDouble(ENTER_THRESHOLD_INDEX);
			temp_exit_threshold = r.getDouble(EXIT_THRESHOLD_INDEX);
			temp_stationarity_cutoff = r.getDouble(STATIONARITY_CUTOFF_INDEX);
			temp_cointegration_cutoff = r.getDouble(COINTEGRATION_CUTOFF_INDEX);
		}
		catch (Exception e) {
			e.printStackTrace();
			
		}
		name = temp_name;
		enter_threshold = temp_enter_threshold;
		exit_threshold = temp_exit_threshold;
		stationarity_cutoff = temp_stationarity_cutoff;
		cointegration_cutoff = temp_cointegration_cutoff;
	}
	
	public static void insertPermutations(DBConnection db) {
		for (double enter = ENTER_THRESHOLD_START; enter <= ENTER_THRESHOLD_END; enter += ENTER_THRESHOLD_STEP) {
			for (double exit = EXIT_THRESHOLD_START; exit <= EXIT_THRESHOLD_END; exit += EXIT_THRESHOLD_STEP) {
				for (double stat = STATIONARITY_CUTOFF_START; stat <= STATIONARITY_CUTOFF_END; stat += STATIONARITY_CUTOFF_STEP) {
					for (double co = COINTEGRATION_CUTOFF_START; co <= COINTEGRATION_CUTOFF_END; co += COINTEGRATION_CUTOFF_STEP) {
						if (exit > enter) {
							String query = getInsertQuery("permutation", enter, exit, stat, co);
							DatabaseUtils.updateDatabase(db, query);
						}
					}
				}
			}
		}
	}
	public static ArrayList<SimulationStrategy> getAllStrategies(DBConnection db) {
		ArrayList<SimulationStrategy> strats = new ArrayList<SimulationStrategy>();
		String query = "SELECT * FROM tstrategy;";
		ResultSet r = DatabaseUtils.getResultSetFromDatabase(db, query);
		try {
			r.beforeFirst();
			while (r.next()) {
				strats.add(new SimulationStrategy(r));
			}
		}
		catch (Exception e)  {
			e.printStackTrace();
		}
		return strats;
	}
	public String getSummary() {
		return name + " enter: " + enter_threshold + "std. dev., cap: " + exit_threshold + "std. dev., stationarity cutoff: " + stationarity_cutoff 
				+ ", cointegration cutoff: " + cointegration_cutoff;
	}
	public static String getInsertQuery(String n, double en, double ex, double s, double c) {
		return "INSERT INTO tstrategy (name, enter_threshold, exit_threshold, stationarity_cutoff, cointegration_cutoff)" + 
					" VALUES (\"" + n + "\", "+ en + ", " + ex + ", " + s + ", " + c + ");";
	}
	public static final int NAME_INDEX = 2;
	public static final int ENTER_THRESHOLD_INDEX = 3;
	public static final int EXIT_THRESHOLD_INDEX = 4;
	public static final int STATIONARITY_CUTOFF_INDEX = 5;
	public static final int COINTEGRATION_CUTOFF_INDEX = 6;
	
	public static final double ENTER_THRESHOLD_START = .5;
	public static final double ENTER_THRESHOLD_END = 2.5;
	public static final double ENTER_THRESHOLD_STEP = .5;
	
	public static final double EXIT_THRESHOLD_START = 1;
	public static final double EXIT_THRESHOLD_END = 3;
	public static final double EXIT_THRESHOLD_STEP = .5;
	
	public static final double STATIONARITY_CUTOFF_START = .05;
	public static final double STATIONARITY_CUTOFF_END = .3;
	public static final double STATIONARITY_CUTOFF_STEP = .05;
	
	public static final double COINTEGRATION_CUTOFF_START = .05;
	public static final double COINTEGRATION_CUTOFF_END = .3;
	public static final double COINTEGRATION_CUTOFF_STEP = .05;
}
