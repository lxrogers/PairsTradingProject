package simulation;

import java.util.Calendar;

import pairtrading.Trade;
import pairtrading.TradePair;

public final class SimulationTradePair extends TradePair{
	Simulation mSimulation;
	public boolean isOpen;
	
	public SimulationTradePair(Simulation s, Trade t1, Trade t2, Calendar o) {
		super(t1, t2, o);
		mSimulation = s;
		isOpen = true;
	}
	private Calendar getOUEndDate() {
		return Calendar.getInstance();
	}
	public void doDay() {
		if (!isOpen) {
			return;
		}
		
		
	}
}
