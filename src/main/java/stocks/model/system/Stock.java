package stocks.model.system;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import com.sun.jna.platform.win32.WinDef.HWND;

import yahoofinance.quotes.query1v7.StockQuotesQuery1V7Request;

public class Stock{

    static {
	System.setProperty("yahoofinance.baseurl.quotesquery1v7", "https://query1.finance.yahoo.com/v7/finance/quote");

	// Disable slf4j Logging
	System.setProperty(org.slf4j.impl.SimpleLogger.DEFAULT_LOG_LEVEL_KEY, "ERROR");
    }

    
    public static yahoofinance.Stock get(String symbol) {
	try {

	    // Sends request to Yahoo
	    StockQuotesQuery1V7Request request = new StockQuotesQuery1V7Request(symbol);
	    yahoofinance.Stock stock  = request.getSingleResult();

	    // sets info
	    if (stock != null) {
		return stock;

	    } else {
		System.err.println("Could not retrieve stock data from Yahoo");
		return null;
	    }
	} catch (Exception e) {
	    return null;
	}
    }

    public interface OnUpdatedPrice {
	void run(double str);
    }
    public static Map<Timer, TimerTask> checkPrice(int time, boolean tradingviewData, double price, StringBuffer symbol, OnUpdatedPrice function) {

	Timer timer = new Timer();
	TimerTask task = new TimerTask() {

	    @Override
	    public void run() {
		//----------------------------------------------------------
		try {

		    double newPrice = 0;

		    HWND activeWindow = WindowsNative.getActiveWindow();
		    String windowTitle = WindowsNative.getActiveWindowTitle(activeWindow);

		    // Difference of chorme window and desktop tradingview app
		    if (tradingviewData && windowTitle.contains("% Unnamed") && symbol.toString().equalsIgnoreCase(windowTitle.split(" ")[0])) {
			newPrice = Double.parseDouble(windowTitle.split(" ")[1]);
		    }
		    else if (tradingviewData && windowTitle.contains("% / Unnamed") && symbol.toString().equalsIgnoreCase(windowTitle.split(" ")[0])) {
			// else Desktop app, and the"%" is for for it not crashing when switching between stocks
			newPrice = Double.parseDouble(windowTitle.split(" ")[3]);
		    } 
		    else { 
			// Yahoo data
			newPrice = get(symbol.toString()).getQuote().getPrice().doubleValue();
		    }

		    // if new price
		    if (price != newPrice) {
			function.run(newPrice);
		    }


		}catch(Exception e){
		    return;
		}


		//----------------------------------------------------------
	    }
	};
	timer.scheduleAtFixedRate(task, 0, time);

	Map<Timer, TimerTask> tt = new HashMap<Timer, TimerTask>();
	tt.put(timer, task);
	return tt;


    }


    public interface OnUpdatedSymbol {
	void run(String str);
    }
    public static Map<Timer, TimerTask> checkSymbol(int time, StringBuffer symbol, OnUpdatedSymbol function) {

	Timer timer = new Timer();
	TimerTask task = new TimerTask() {
	    @Override
	    public void run() {
		//----------------------------------------------------------
		try {
		    HWND activeWindow = WindowsNative.getActiveWindow();
		    String windowTitle = WindowsNative.getActiveWindowTitle(activeWindow);

		    if (windowTitle.contains("% Unnamed") || windowTitle.contains("% / Unnamed")) {

			if (!symbol.toString().equalsIgnoreCase(windowTitle.split(" ")[0])) {
			    String newSymbol = windowTitle.split(" ")[0];
			    function.run(newSymbol); 

			}

		    }

		}catch(Exception e) {
		    return;
		}
		//----------------------------------------------------------
	    };
	};
	timer.scheduleAtFixedRate(task, 0, time);
	Map<Timer, TimerTask> tt = new HashMap<Timer, TimerTask>();
	tt.put(timer, task);
	return tt;
    }

}
