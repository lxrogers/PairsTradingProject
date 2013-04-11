/*
 * File: DataProcessing.java
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

public class DataProcessing extends ConsoleProgram {
	
	/* Class Variables */
	StockData firstStock;
	StockData secondStock;
	int numberOfDays = 135;
	
	/* Runs our program */
	public void run() {
		readInData("dataSets/Coca-Cola NYSE.csv", "dataSets/Pepsico NYSE.csv");
	}
	
	/* Reads the data in using the specified filenames */
	public void readInData(String FirstDataFile, String SecondDataFile) {
		processFile(FirstDataFile, 1);
		processFile(SecondDataFile, 2);
	}
	
	/* Read the data into each of the respective StockData structures */
	public void processFile(String fileName, int stockNumber) {
		try {			
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			br.readLine(); // Get rid of the first line which is just a header.
			for(int i = 0; i < numberOfDays; i++) {
				String currentLine = br.readLine();
				int start = currentLine.indexOf(',') + 1;
				int dailyPrice = Integer.parseInt(currentLine.substring(start));
			}
		} catch (FileNotFoundException FNF) {
			FNF.printStackTrace();
		} catch (IOException IOE) {
			IOE.printStackTrace();
		}
		
	}
	
}
