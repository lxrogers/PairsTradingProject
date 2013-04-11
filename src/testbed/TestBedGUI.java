package testbed;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JMenuBar;

import acmx.export.javax.swing.JMenu;

public class TestBedGUI extends JFrame{
	JMenu fileMenu;
	JMenuBar menuBar;
	
	public TestBedGUI() {
		super("Pairs Trading TestBed");
		setSize(new Dimension(600,1000));
		setVisible(true);
		
	}
	public static void main(String[] args) {
		TestBedGUI tbg = new TestBedGUI();
	}
}
