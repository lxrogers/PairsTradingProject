package simulation;

import java.util.Calendar;

public class SimulationTrade {
	Simulation mSimulation;
	Calendar openDate;
	Calendar OUEndDate;
	public boolean isOpen;
	public SimulationTrade(Simulation s) {
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
