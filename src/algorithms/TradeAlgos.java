/*
 * File: TradeAlgos.java
 * ---------------------
 * This file is what we run to actually make trades using
 * the data we've generated.
 */

/* Package Designation */
package algorithms;

/* Imports */
import org.rosuda.JRI.Rengine;

import pairtrading.MStockPair;
import pairtrading.Trade;
import pairtrading.TradePair;
import simulation.Simulation;
import simulation.SimulationStockPair;
import simulation.SimulationTradePair;

public class TradeAlgos {
	public static final int TOTAL_INVESTMENT_EACH = 10000;
	/**
	 * 
	 * @param ticker1 first stock
	 * @param ticker2 second stock
	 * @param re instance of REngine. Must have libraries loaded
	 * @return the trade if one was determined, null if no trade
	 */
	public static SimulationTradePair evaluateAndInitiateTrades(Simulation s, SimulationStockPair pair,  Rengine re, String date) {
		SimulationTradePair tradepair = null;
		// Calculate ratios and standard deviation.
		double historicalRatio = MStockPair.getHistorialRatioAvg(pair.getTicker1(), pair.getTicker2(), re, date); 
		double historicalStdDev = RUtils.getStdDev(re, date); 
		double currentRatio = MStockPair.getCurrentRatio(pair.getTicker1(), pair.getTicker2(), re, date); 
		double absoluteRatioDifference = Math.abs(historicalRatio - currentRatio);
		
		// Running the algorithm if we're a standard deviation diverged. We've also added safeguards.
		if(absoluteRatioDifference > historicalStdDev && absoluteRatioDifference <= 3*historicalStdDev) {
			
			//double currentOverHistRatio = currentRatio / historicalRatio; -- what is this and why did you use it
			double oldPercentage = 	pair.getPosition(pair.getTicker1()) / TOTAL_INVESTMENT_EACH;
			double newPercentage = currentRatio / (historicalStdDev);
			double tradeAmount = (oldPercentage - newPercentage) * TOTAL_INVESTMENT_EACH;
			
			// SHORT A, LONG B
			if(currentRatio > historicalRatio) {
				Trade shortTrade = new Trade(pair.getTicker1(), Trade.TradeType.SHORT, tradeAmount, 0.0);
				Trade longTrade = new Trade(pair.getTicker2(), Trade.TradeType.LONG, tradeAmount, 0.0);	
				tradepair = new SimulationTradePair(s,shortTrade, longTrade, DataProcessing.getCalendar(date));
						
			}

			// LONG A, SHORT B
			if(currentRatio < historicalRatio) {
				//Calendar c = calculate OUEndDate
				Trade longTrade = new Trade(pair.getTicker1(), Trade.TradeType.LONG, tradeAmount, 0.0);	
				Trade shortTrade = new Trade(pair.getTicker2(), Trade.TradeType.SHORT, tradeAmount, 0.0);
				tradepair = new SimulationTradePair(s,longTrade, shortTrade, DataProcessing.getCalendar(date));
			}
			
		}	
		return tradepair;
		
	}
	
	/*
	 * Function: analyzePositions
	 * --------------------------
	 * Called daily. Look at the Ornstein-Uhlenbeck expected values,
	 * update the amount of time that has passed so far.
	 * --------------------------
	 * Unwind a short-long position under two circumstances:
	 * 1. We've reverted to within x of the mean.
	 * 2. The OU expected value has been reached.
	 */
	public static void analyzePositions(String ticker1, String ticker2) {
		
		// Get historical ratio.
		// Get the ratio today.
		
		// Firstly, unwind if the ratio is >= 3*historicalStdDev.
		// If we're within 1/2 of a standard deviation of the mean, divest.
		// Get the Ornstein-Uhlenbeck value, then OU--.
		// If the O-U value is zero, divest.
		
	}
	
	
	/*
	 * Function: executeAndRecordTrade
	 * -------------------------------
	 * Interacts with the API and records
	 * the trade in the ArrayList.
	 */
	private static void executeAndRecordTrade(String ticker, String tradeType, double percentage) {
		
		// Calculate the amount to invest using the StockPair.
		int amount = 0;
		
		
		if(tradeType.equals("short")) {
			APICalls.PlaceShortOrder(ticker, amount); // Call the API
		} else if(tradeType.equals("long")) {
			// The below line will be an API call in the future.
			APICalls.PlaceLongOrder(ticker, amount); // Call the API
		}

	}
}