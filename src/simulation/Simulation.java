package simulation;

import java.util.ArrayList;
import java.util.Calendar;

import org.rosuda.REngine.REngine;

public class Simulation {
	private String ticker1;
	private String ticker2;
	private double money;
	private Calendar startDate;
	private Calendar endDate;
	REngine re;
	ArrayList<SimulationTrade> mTrades;

	public Simulation(String t1, String t2, REngine r, Calendar start, Calendar end) {		
		mTrades = new ArrayList<SimulationTrade>();
		ticker1 = t1;
		ticker2 = t2;
		money = 0;
		re = r;
		startDate = start;
		endDate = end;
	}

	public void run() {
		for (Calendar currentDate = startDate; !currentDate.equals(endDate) ; currentDate.add(Calendar.DAY_OF_YEAR,1)) {
			doDay(currentDate);
		}
	}
	public void doDay(Calendar day) {
		
		
		
	}
	public void updateTrades() {
		for (SimulationTrade t : mTrades) {
			t.doDay();
		}
	}
}
