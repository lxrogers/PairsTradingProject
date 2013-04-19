package testbed;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import algorithms.StockData;

public class TestBedGUI extends JFrame implements ActionListener{
	JMenu fileMenu, testMenu;
	JMenuBar menuBar;
	JMenuItem retrievePrices, dfTest;
	StockData testSecurity1;
	StockData testSecurity2;
	JTextArea console;
	public TestBedGUI() {
		super("Pairs Trading TestBed");
		setupMenu();
		
		console = new JTextArea();
		console.setEditable(false);
		add(console);
		setSize(new Dimension(1000,600));
		setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private void setupMenu() {
		menuBar = new JMenuBar();
		fileMenu = new JMenu("File");
		testMenu = new JMenu("Test");
		menuBar.add(fileMenu);
		menuBar.add(testMenu);
		
		retrievePrices = new JMenuItem("Retrieve Prices");
		retrievePrices.addActionListener(this);
		fileMenu.add(retrievePrices);
		
		
		dfTest = new JMenuItem("Dickey Fuller");
		dfTest.addActionListener(this);
		dfTest.setEnabled(false);
		testMenu.add(dfTest);
		
		
		this.add(menuBar, BorderLayout.NORTH);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == retrievePrices) {
			int days = Integer.parseInt(JOptionPane.showInputDialog(null, "Number of Days:"));
			String s1 = JOptionPane.showInputDialog(null, "Ticker 1:");
			testSecurity1 = StockData.readInData(days, s1);
			String s2 = JOptionPane.showInputDialog(null, "Ticker 2:");
			testSecurity2 = StockData.readInData(days, s2);
			console.append(testSecurity1.getStockSymbol() + ": " + testSecurity1.getPricesString());
			console.append("\n");
			console.append(testSecurity2.getStockSymbol() + ": " + testSecurity2.getPricesString());
			dfTest.setEnabled(true);
		}
		else if (e.getSource() == dfTest) {
			console.append("\nperforming dickey fuller test");
			StockData.runDickeyFuller(testSecurity1, testSecurity2);
		}
	}
	public static void main(String[] args) {
		TestBedGUI tbg = new TestBedGUI();
	}
}
