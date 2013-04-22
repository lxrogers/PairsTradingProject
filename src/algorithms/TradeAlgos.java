/*
 * File: TradeAlgos.java
 * ---------------------
 * This file is what we run to actually make trades using
 * the data we've generated.
 */

/* Package Designation */
package algorithms;

/* Imports */
public class TradeAlgos {
	/*
	 * Function: callTrades
	 * -----------------------
	 * Essentially the main function
	 * that calls all others.
	 */
	public static void callTrades() {

		double historicalRatio = getRatio(); // Call R.
		double historicalStdDev = getStdDev(); // Call R.
		double currentRatio = Math.abs(stockACurrentPrice/stockBCurrentPrice); // Potentially call R. Up to us.

		// Running the algorithm if we're a standard deviation diverged.
		if(Math.abs(historicalRatio - currentRatio) >= historicalStdDev) {

			// If we want to short stock A.
			if(currentRatio > historicalRatio) {
				// Action if standard deviation between 1 and 2.
				double ratioDifference = currentRatio - historicalRatio;
				if(ratioDifference < 2*historicalStdDev) {
					// Call the helper function to do the trades.
					executeAndRecordTrade(stockA, "short", 10.0);
					executeAndRecordTrade(stockB, "long", 10.0);
				} else { // Take the 2 standard deviations trade.
					executeAndRecordTrade(stockA, "short", 20.0);
					executeAndRecordTrade(stockB, "long", 20.0);
				}
			}

			// If we want to short stock B.
			if(currentRatio < historicalRatio) {
				// Action if standard deviation between 1 and 2.
				double ratioDifference = historicalRatio - currentRatio;
				if(ratioDifference < 2*historicalStdDev) {
					executeAndRecordTrade(stockB, "short", 10.0);
					executeAndRecordTrade(stockA, "long", 10.0);
				} else {
					executeAndRecordTrade(stockB, "short", 20.0);
					executeAndRecordTrade(stockA, "long", 20.0);
				}
			}
		}

	}

	/*
	 * Function: executeAndRecordTrade
	 * -------------------------------
	 * Interacts with the API and records
	 * the trade in the ArrayList.
	 */
	public static void executeAndRecordTrade(String stockName, String tradeType, double percentage) {

		// Calculate OU for storage purposes.
		double OUValue = calculateOU(stockName);

		if(tradeType,equals("short")) {
			// The below line will be an API call in the future.
			PlaceShortOrder(stockName, amount);
			TradeOrder short = new TradeOrder(stockName, "short", percentage, OUValue);
			tradeList.add(short);
		} else if(tradeType.equals("long")) {
			// The below line will be an API call in the future.
			PlaceLongOrder(stockName, amount);
			TradeOrder long = new TradeOrder(stockName, "long", percentage, OUValue);
			tradeList.add(long);
		}

	}
}