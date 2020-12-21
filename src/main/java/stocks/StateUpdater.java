package stocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import com.sun.jna.platform.win32.WinDef.HWND;

import stocks.interfaces.StockStateListener;
import stocks.system.WindowsNative;


/*
 * Class that updates states of things
 */
public class StateUpdater {

    private static Timer t = null;
    
    
    
    public static List<TimerTask> timerTasks = new ArrayList<TimerTask>();


    // Checks every *ms if price has changed and updates price
    // Set ssl to "null" if no additional code
    // Tradingview data if you want to take dat from tradingview window instead of Yahoo
    public static TimerTask updatePrice(StockStateListener ssl, int time, Stock stock, boolean tradingviewData) {

	if (t == null)
	    t = new Timer();

	TimerTask tt1 = new TimerTask() {
	    
	    @Override
	    public void run() {
		    
		if (stock.symbol != null) {
		    double currentPrice = stock.price;

			HWND activeWindow = WindowsNative.getActiveWindow();
			String windowTitle = WindowsNative.getActiveWindowTitle(activeWindow);

		    // Difference of chorme window and desktop tradingview app
		    if (tradingviewData && windowTitle.contains("% Unnamed") && stock.symbol.equalsIgnoreCase(windowTitle.split(" ")[0])) {
			stock.price = Double.parseDouble(windowTitle.split(" ")[1]);
		    }
		    else if (tradingviewData && windowTitle.contains("% / Unnamed") && stock.symbol.equalsIgnoreCase(windowTitle.split(" ")[0])) {
			// else Desktop app, and the"%" is for for it not crashing when switching between stocks
			stock.price = Double.parseDouble(windowTitle.split(" ")[3]);
		    } 
		    else { 
			// Yahoo data
			stock.get(stock.symbol);
		    }



		    if (currentPrice != stock.price && ssl != null) {

			ssl.onNewPrice(stock); // if want to do more on price update
		    }
		    
		    
		}

	    };
	};
	t.scheduleAtFixedRate(tt1, 0, time);

	timerTasks.add(tt1);
	return tt1;
    }

    // Check every *ms if viewing a different tradingview window and if so it sets a new stock symbol
    // Set ssl to "null" if no additional code
    public static TimerTask updateSymbol(StockStateListener ssl, int time, Stock stock) {

	if (t == null)
	    t = new Timer();

	TimerTask tt2 = new TimerTask() {
	    @Override
	    public void run() {
		

		HWND activeWindow = WindowsNative.getActiveWindow();
		String windowTitle = WindowsNative.getActiveWindowTitle(activeWindow);

		if (windowTitle.contains("% Unnamed") || windowTitle.contains("% / Unnamed")) {

		    if (stock == null || !stock.symbol.equalsIgnoreCase(windowTitle.split(" ")[0])) {
			stock.get(windowTitle.split(" ")[0]);

			if (ssl != null)
			ssl.onNewTradingviewWindow(stock); // If want to do more on new tradingview window

		    }
		    
		    
		}
		
		
	    };
	};
	t.scheduleAtFixedRate(tt2, 0, time);


	timerTasks.add(tt2);
	return tt2;
    }


    // stops it from updating
    public void close(TimerTask tt) {
	if(tt != null) {
	    tt.cancel();
	    timerTasks.remove(tt);   
	}
    }

    // close all and the timer aswell
    public void closeAll() {
	for (TimerTask tt : timerTasks) {
	    if (tt != null) {
		tt.cancel();
		timerTasks.remove(tt);   
	    }
	}

	if (t != null)
	    t.cancel();
    }
}
