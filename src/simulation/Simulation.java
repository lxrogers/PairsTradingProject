package simulation;

import java.util.ArrayList;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

import org.rosuda.JRI.Rengine;

import pairtrading.MStockPair;
import algorithms.DataProcessing;

public class Simulation {
	SimulationStockPair mSimulationStockPair;
	private Calendar startDate;
	private Calendar endDate;
	Rengine re;
	ArrayList<SimulationTradePair> mTrades;

	public Simulation(String t1, String t2, Rengine r, Calendar start, Calendar end) {		
		mTrades = new ArrayList<SimulationTradePair>();
		mSimulationStockPair = new SimulationStockPair(t1, t2);
		re = r;
		startDate = start;
		endDate = end;
	}
	public Simulation(HttpServletRequest request) {
		mTrades = new ArrayList<SimulationTradePair>();
		String t1 = request.getParameter("t1");
		String t2 = request.getParameter("t2");
		mSimulationStockPair = new SimulationStockPair(t1, t2);
		
		re = (Rengine)(request.getServletContext().getAttribute("rengine"));
		
		int startindex = Integer.parseInt(request.getParameter("starttime"));
		String start = MStockPair.PVALUE_COLUMNS[startindex];
		int duration_days = Integer.parseInt(request.getParameter("duration"));
		String end;
		if (duration_days > 0) {
			end = DataProcessing.getDateOffsetRString(start, duration_days);
		}
		else {
			end = DataProcessing.getNowRString();
		}
		startDate = DataProcessing.getCalendar(start);
		endDate = DataProcessing.getCalendar(end);
		
	}
	public String toString() {
		return "for " + mSimulationStockPair + " from " + DataProcessing.getDateRString(startDate) + " to " + DataProcessing.getDateRString(endDate);
	}
	public void run() {
		for (Calendar currentDate = startDate; currentDate.compareTo(endDate) < 0 ; currentDate.add(Calendar.DAY_OF_YEAR,1)) {
			doDay(currentDate);
		}
	}
	public void doDay(Calendar day) {
		System.out.println("Day: " + DataProcessing.getDateRString(day));
	}
	public void updateTrades() {
		for (SimulationTradePair t : mTrades) {
			t.doDay();
		}
	}
}
