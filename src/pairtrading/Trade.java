package pairtrading;



public class Trade {
	public static enum TradeType {SHORT, LONG};
	public final String mTicker;
	public final TradeType mTradeType;
	public final Double mTradeAmount;
	public final Double mStartingPrice;
	
	public Trade(String t1, TradeType tp, Double amount, double sp) {
		mTicker = t1;
		mTradeType = tp;
		mTradeAmount = amount;
		mStartingPrice = sp;
	}
}