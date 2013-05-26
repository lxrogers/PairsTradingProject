package pairtrading;

import java.util.Calendar;

public class TradePair {
	
	private Trade trade1, trade2;
	public final Calendar openDate;
	private Calendar OUEndDate;
	public TradePair(Trade t1, Trade t2, Calendar o) {
		trade1 = t1;
		trade2 = t2;
		openDate = o;
		OUEndDate = calculateOUEndDate();
	}
	
	public Trade getFirstTrade() {
		return trade1;
	}
	public Trade getSecondTrade() {
		return trade2;
	}
	private Calendar calculateOUEndDate() {
		Calendar end = openDate;
		end.add(Calendar.DAY_OF_YEAR,  5);
		return end;
	}
	public Calendar getOUEndDate() {
		return OUEndDate;
	}
	private double calculateProfit(Calendar day) {
		return 0;
	}
}
