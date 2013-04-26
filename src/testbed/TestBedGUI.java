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

import algorithms.DataProcessing;
import algorithms.StockData;
import database.DBConnection;

public class TestBedGUI extends JFrame implements ActionListener{
	JMenu fileMenu, testMenu;
	JMenuBar menuBar;
	JMenuItem getUser, dfTest;
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
		
		getUser = new JMenuItem("Retrieve Prices");
		getUser.addActionListener(this);
		fileMenu.add(getUser);
		
		dfTest = new JMenuItem("Dickey Fuller");
		dfTest.addActionListener(this);
		testMenu.add(dfTest);
		
		
		this.add(menuBar, BorderLayout.NORTH);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == getUser) {
			System.out.println("getting user");
			DBConnection db = new DBConnection();
		
			
		}
		else if (e.getSource() == dfTest) {
			String t1 = JOptionPane.showInputDialog(null, "Ticker 1:");
			String t2 = JOptionPane.showInputDialog(null, "Ticker 2:");
			int days = Integer.parseInt(JOptionPane.showInputDialog(null, "Number of Days:"));
			console.append("Running Dickey Fuller...\n");
			//double p = StockData.runDickeyFuller();
			console.append("p = \n");
		}
	}
	public static void main(String[] args) {
		TestBedGUI tbg = new TestBedGUI();
	}
}
