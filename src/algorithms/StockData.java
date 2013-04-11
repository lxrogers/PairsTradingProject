/*
 * File: StockData.java
 * -------------------------
 * This file pulls in stock data in .csv form and tokenizes it,
 * putting the prices into an array of daily opens.
 */

/* Package Designation */
package algorithms;

/* Imports */
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;
import acm.program.*;

public class StockData {
	
	/* Class Variables */
	private int[] prices;
	private String stockSymbol;
	private String fullName;
	
	// Allows us to set the prices.
	public void setArr(int[] inputPrices) {
		this.prices = inputPrices;
	}
	
	// Allows us to set the stockSymbol.
	public void setStockSymbol(String inputSymbol) {
		this.stockSymbol = inputSymbol;
	}
	
	// Allows us to set the fullName.
	public void setFullName(String inputFullName) {
		this.fullName = inputFullName;
	}
	
	// Allows us to get the array.
	public int[] getPrices() {
		return prices;
	}
	
	// Allows us to get the stockSymbol.
	public String getStockSymbol() {
		return stockSymbol;
	}
	
	// Allows us to get the stockSymbol.
	public String getFullName() {
		return fullName;
	}
	
}