package simulation;

import java.util.ArrayList;
import java.util.HashMap;

import pairtrading.MStockPair;
import pairtrading.Trade;
import pairtrading.TradePair;

public class SimulationStockPair{
	private HashMap<String, Double> stocks;
	private String ticker1, ticker2;
	private MStockPair mMStockPair;
	public SimulationStockPair(String t1, String t2) {
		ticker1 = t1;
		ticker2 = t2;
		mMStockPair = new MStockPair(t1, t2, new ArrayList<Double>());
		stocks = new HashMap<String, Double>();
		stocks.put(t1, 0.0);
		stocks.put(t2, 0.0);
	}
	public void doTrades(TradePair tp) {
		doTrade(tp.getFirstTrade());
		doTrade(tp.getSecondTrade());
		
	}
	public double doTrade(Trade t) {
		Double newAmount;
		if (t.mTradeType == Trade.TradeType.SHORT) {
			newAmount = stocks.get(t.mTradeType) - t.mTradeAmount;
		}
		else {
			newAmount = stocks.get(t.mTradeType) + t.mTradeAmount;
		}
		stocks.put(t.mTicker, newAmount);
		return newAmount;
	}
	public String getTicker1() {
		return ticker1;
	}
	public String getTicker2() {
		return ticker2;
	}
	public Double getPosition(String t) {
		return stocks.get(t);
	}
	
	
	public String toString() {
		String ans = "";
		for (String s : stocks.keySet()) {
			ans += s + " ";
		}
		return ans;
	}
}
