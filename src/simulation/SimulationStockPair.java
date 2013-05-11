package simulation;

import java.util.HashMap;

import pairtrading.Trade;
import pairtrading.TradePair;

public class SimulationStockPair{
	HashMap<String, Double> stocks;
	public SimulationStockPair(String t1, String t2) {
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
	public String toString() {
		String ans = "";
		for (String s : stocks.keySet()) {
			ans += s + " ";
		}
		return ans;
	}
}
