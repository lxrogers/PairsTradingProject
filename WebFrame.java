package assign4;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

public class WebFrame extends JFrame implements ActionListener{
	private DefaultTableModel model;
	private JTable table;
	private JPanel panel;
	private JButton singleFetchButton, concurrentFetchButton, stopButton;
	private JLabel runningLabel, completedLabel, elapsedLabel;
	private JTextField threadField;
	private JProgressBar fetchProgressBar;
	public ArrayList<WebWorker> mWorkers;
	private int numRunningThreads;
	public Semaphore mWorkerSemaphore;
	private LauncherThread launcher;
	private int completed;
	private long fetch_start_time_millis;
	public Object interruptLock;
	public WebFrame() {
		initGUI();
		readURLS("links.txt");
		interruptLock = new Object();

	}
	public void initGUI() {
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

		model = new DefaultTableModel(new String[] { "url", "status"}, 0);
		table = new JTable(model);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.setEnabled(false);

		JScrollPane scrollpane = new JScrollPane(table);
		scrollpane.setPreferredSize(new Dimension(600,300));
		panel = new JPanel();
		panel.add(scrollpane);

		singleFetchButton = new JButton("Single Thread Fetch");
		singleFetchButton.addActionListener(this);
		concurrentFetchButton = new JButton("Concurrent Fetch");
		concurrentFetchButton.addActionListener(this);

		stopButton = new JButton("Stop");
		stopButton.addActionListener(this);
		stopButton.setEnabled(false);
		threadField = new JTextField();
		threadField.setMaximumSize(new Dimension(50, 20));

		runningLabel = new JLabel("running: 0");
		completedLabel = new JLabel("completed: 0");
		elapsedLabel = new JLabel("elsapsed: 0");

		fetchProgressBar = new JProgressBar();

		add(panel);
		add(Box.createRigidArea(new Dimension(0, 5)));
		add(singleFetchButton);
		add(Box.createRigidArea(new Dimension(0, 5)));
		add(concurrentFetchButton);
		add(Box.createRigidArea(new Dimension(0, 5)));
		add(threadField);
		add(Box.createRigidArea(new Dimension(0, 5)));
		add(runningLabel);
		add(Box.createRigidArea(new Dimension(0, 5)));
		add(completedLabel);
		add(Box.createRigidArea(new Dimension(0, 5)));
		add(elapsedLabel);
		add(Box.createRigidArea(new Dimension(0, 5)));
		add(fetchProgressBar);
		add(Box.createRigidArea(new Dimension(0, 5)));
		add(stopButton);
		add(Box.createRigidArea(new Dimension(0, 5)));
		pack();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	public void readURLS(String fname) {
		try {
			FileInputStream fstream = new FileInputStream(fname);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String line = br.readLine();
			while (line != null) {
				String[] row = {line, ""};
				model.addRow(row);
				line = br.readLine();
			}
		}
		catch (IOException e){
			System.out.println("Error reading file");
		}
	}
	private static void createAndShowGUI() {
		WebFrame w = new WebFrame();
	}

	private void startFetching(int limit) {
		fetch_start_time_millis = System.currentTimeMillis();
		completed = 0;
		fetchProgressBar.setMaximum(model.getRowCount());
		singleFetchButton.setEnabled(false);
		concurrentFetchButton.setEnabled(false);
		stopButton.setEnabled(true);
		for (int i = 0; i < model.getRowCount(); i++) {
			model.setValueAt("", i, 1);
		}
		initWorkers(limit);

	}
	private void  stopFetching() {
		singleFetchButton.setEnabled(true);
		concurrentFetchButton.setEnabled(true);
		stopButton.setEnabled(false);
		launcher.interrupt();
		synchronized(interruptLock) {
			for (WebWorker w : mWorkers) {
				w.interrupt();
			}
		}

	}

	private void initWorkers(final int limit) {
		mWorkers = new ArrayList<WebWorker>();
		mWorkerSemaphore = new Semaphore(limit);
		numRunningThreads = 0;
		launcher = new LauncherThread(this);
		launcher.start();
	}
	private class LauncherThread extends Thread{
		public LauncherThread(WebFrame wf) {mWebFrame = wf;}
		private WebFrame mWebFrame;
		public void run() {
			increaseRunningThreads();
			int numRows = model.getRowCount();
			for (int i = 0; i < numRows && !isInterrupted(); i++) {
				try {
					mWorkerSemaphore.acquire();
				} catch (InterruptedException e) {
					break;
				}
				synchronized(mWebFrame.interruptLock) {
					if (!isInterrupted()) {
						String url = "" + model.getValueAt(i, 0);
						WebWorker currWorker = new WebWorker(url, mWebFrame, i);
						mWorkers.add(currWorker);
						currWorker.start();
					}
				}
			}
			decreaseRunningThreads();
			updateLabels();
		}
	}
	public void updateModel(final String r, final int row) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				model.setValueAt(r, row, 1);
			}
		});
	}
	/*
	 * updates the display.
	 * if row == -1, then do not update the model, just update the labels
	 */
	public void updateLabels() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				runningLabel.setText("running: " + numRunningThreads);
				completedLabel.setText("completed: " + completed);
				elapsedLabel.setText("elapsed: " + (System.currentTimeMillis() - fetch_start_time_millis) + "ms");
				fetchProgressBar.setValue(completed);
				
			}
		});
	}
	public synchronized void increaseCompleted() {
		completed++;
	}
	public synchronized void increaseRunningThreads() {
		numRunningThreads++;
	}
	public synchronized void decreaseRunningThreads() {
		numRunningThreads--;
		if (numRunningThreads == 0) {
			stopFetching();
		}
	}
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == concurrentFetchButton) {
			startFetching(Integer.parseInt(threadField.getText()));
		}
		else if (e.getSource() == singleFetchButton) {
			startFetching(1);
		}
		else if (e.getSource() == stopButton) {
			stopFetching();
		}
	}
}
