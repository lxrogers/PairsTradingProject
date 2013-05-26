package simulation;

import java.util.Calendar;

import pairtrading.Trade;
import pairtrading.TradePair;

public final class SimulationTradePair extends TradePair{
	Simulation mSimulation;
	public boolean isOpen;
	public Calendar endDate;
	public SimulationTradePair(Simulation s, Trade t1, Trade t2, Calendar o) {
		super(t1, t2, o);
		mSimulation = s;
		isOpen = true;
	}
	public void doDay(Calendar day) {
		if (!isOpen) {
			return;
		}
		if (day.equals(endDate)) {
			System.out.println("close trade");
			isOpen = false;
		}
	}
}
