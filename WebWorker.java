package assign4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class WebWorker extends Thread {
	String mURL;
	final WebFrame mWebFrame;
	int mRow;
	public WebWorker(String u, WebFrame wf, int r) {
		mURL = u;
		mWebFrame = wf;
		mRow = r;
	}
	@Override
	public void run() {
		super.run();
		mWebFrame.increaseRunningThreads();
		mWebFrame.updateModel("",mRow);
		mWebFrame.updateLabels();
		
		String r = download();
		
		mWebFrame.updateModel(r,mRow);
		mWebFrame.mWorkerSemaphore.release();
		mWebFrame.decreaseRunningThreads();
		mWebFrame.increaseCompleted();
		mWebFrame.updateLabels();
		
		mWebFrame.mWorkers.remove(this);
	}
	
	private String download() {
		InputStream input = null;
		StringBuilder contents = null;
		String report = "err";
		long curr_time_millis = System.currentTimeMillis();
		try {
			URL url = new URL(mURL);
			URLConnection connection = url.openConnection();

			// Set connect() to throw an IOException
			// if connection does not succeed in this many msecs.
			connection.setConnectTimeout(5000);

			connection.connect();
			input = connection.getInputStream();

			BufferedReader reader  = new BufferedReader(new InputStreamReader(input));

			char[] array = new char[1000];
			int len;
			contents = new StringBuilder(1000);
			while ((len = reader.read(array, 0, array.length)) > 0) {
				contents.append(array, 0, len);
				Thread.sleep(100);
				if (isInterrupted()) {
					return "interrupted";
				}
			}
			long time = System.currentTimeMillis() - curr_time_millis;
			// Successful download if we get here
			report = getTimeStamp() + " " + time + "ms " + contents.length() + " bytes";

		}
		// Otherwise control jumps to a catch...
		catch(MalformedURLException ignored) {}
		catch(InterruptedException exception) {
			return "interrupted";
		}
		catch(IOException ignored) {}
		// "finally" clause, to close the input stream
		// in any case
		finally {
			try{
				if (input != null) input.close();
			}
			catch(IOException ignored) {}
		}
		return report;

	}
	private String getTimeStamp() {
		Calendar cal = Calendar.getInstance();
    	cal.getTime();
    	SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
    	return sdf.format(cal.getTime());
	}


}
