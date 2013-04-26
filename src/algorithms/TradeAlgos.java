/*
 * File: TradeAlgos.java
 * ---------------------
 * This file is what we run to actually make trades using
 * the data we've generated.
 */

/* Package Designation */
package algorithms;

/* Imports */
import java.util.ArrayList;

public class TradeAlgos {
	
	static TradeOrder shortA = new TradeOrder("none", "none", 0.0, 0);
	static TradeOrder longA = new TradeOrder("none", "none", 0.0, 0);
	
	/*
	 * Function: callTrades
	 * -----------------------
	 * Essentially the main function
	 * that calls all others.
	 */
	
	public static void evaluateAndInitiateTrades(String ticker1, String ticker2) {
		
		// Calculate ratios and standard deviation.
		double historicalRatio = RCalls.getRatio(); // Call R.
		double historicalStdDev = RCalls.getStdDev(); // Call R.
		double currentRatio = RCalls.getCurrentRatio(); // Call R.
		double absoluteRatioDifference = Math.abs(historicalRatio - currentRatio);
		
		// Running the algorithm if we're a standard deviation diverged.
		if(absoluteRatioDifference >= historicalStdDev) {
			
			// If we want to short stock A.
			if(currentRatio > historicalRatio) {
				// Get some of the data we need for the following process.
				double currentOverHistRatio = currentRatio / historicalRatio;
				double oldPercentage = shortA.percentage;	
				// Sets up longAShortB if it's not already there.				
				if(shortA.ticker.equals("none")) {
					shortA.ticker = ticker1;
					shortA.aTradeType = "short";
					shortA.percentage = 10.0 * currentOverHistRatio;
					shortA.OUValue = RCalls.calculateOU(ticker1);
				} else {
					// If we're not investing more, just leave this function.
					if(10.0*currentOverHistRatio <= oldPercentage) return;
					// Set the new percentage if it is more.
					shortA.percentage = 10.0*currentOverHistRatio;
				}				
				// Make the trades, investing the same amount in each stock!
				executeAndRecordTrade(ticker1, "short", shortA.percentage - oldPercentage);
				executeAndRecordTrade(ticker2, "long", shortA.percentage - oldPercentage);				
			}

			// If we want to long stock A.
			if(currentRatio < historicalRatio) {
				// Get some of the data we need for the following process.
				double currentOverHistRatio = currentRatio / historicalRatio;
				double oldPercentage = longA.percentage;
				// Sets up longA if it's not already there.	
				if(longA.ticker.equals("none")) {
					longA.ticker = ticker2;
					longA.aTradeType = "long";
					longA.percentage = 10.0 * currentOverHistRatio;
					longA.OUValue = RCalls.calculateOU(ticker2);
				} else {
					// If we're not investing more, just leave this function.
					if(10.0*currentOverHistRatio <= oldPercentage) return;
					// Set the new percentage if it is more.
					longA.percentage = 10.0*currentOverHistRatio;
				}
				// Make the trades, investing the same amount in each stock!
				executeAndRecordTrade(ticker1, "long", longA.percentage - oldPercentage);
				executeAndRecordTrade(ticker2, "short", longA.percentage - oldPercentage);		
				
				
				
			}
		}		
	}
	
	/*
	 * Function: analyzePositions
	 * --------------------------
	 * 
	 */
	public static void analyzePositions(String ticker1, String ticker2) {
		
		
		
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