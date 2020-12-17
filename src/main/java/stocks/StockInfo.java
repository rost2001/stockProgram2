package stocks;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Timer;
import java.util.TimerTask;
import com.sun.jna.platform.win32.WinDef.HWND;

import stocks.interfaces.ScheduledTasksListener;
import stocks.system.WindowsNative;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;

// A class about a single stock and where any related info about that stock can be stored
// Where methods that operate on the stock can be added
public class StockInfo {

    private Timer t = null;
    private TimerTask tt1 = null;
    private TimerTask tt2 = null;
    private boolean tradingviewData = false;
    private String tradingviewTitle;

    private Stock stock;
    ScheduledTasksListener stl;

    public StockInfo(ScheduledTasksListener stl, String symbol) throws IOException {
	this.stl = stl;
	if (symbol.isEmpty())
	    stock = null;
	else
	    stock = YahooFinance.get(symbol);
    }

    StockInfo(ScheduledTasksListener stl) {
	this.stl = stl;
	stock = null;
    }

    public Stock getStock() {
	return stock;
    }
    
    // Turns on to get data from tradinview instead
    public void setTradingviewData(boolean trueFalse) {
	tradingviewData = trueFalse;
    }

    // Checks every *ms if price has changed and updates price
    public void updatePrice(int time) {

	if (t == null)
	    t = new Timer();

	tt1 = new TimerTask() {
	    @Override
	    public void run() {

		if (stock != null && tradingviewTitle != null) {
		    double currentPrice = stock.getQuote().getPrice().doubleValue();
		    // getQuote(stock);
		    try {
			if (tradingviewData) {
			    // Difference of chorme window and desktop tradingview app
			    if (tradingviewTitle.contains("Chrome")) 
				    stock.getQuote().setPrice(new BigDecimal(tradingviewTitle.split(" ")[1]));
			    else // Desktop app
				    stock.getQuote().setPrice(new BigDecimal(tradingviewTitle.split(" ")[3]));

			// Yahoo data
			} else {
			    stock.getQuote(true);
			}

			
		    } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		    }
		    if (currentPrice != stock.getQuote().getPrice().doubleValue()) {

			stl.onNewPrice(stock); // if want to do more on price update
		    }
		}

	    };
	};
	t.scheduleAtFixedRate(tt1, 0, time);

    }

    // Check every *ms if viewing a different tradingview window and if so it sets
    // the new stock
    public void updateStockWindow(int time) {

	if (t == null)
	    t = new Timer();

	tt2 = new TimerTask() {
	    @Override
	    public void run() {

		HWND activeWindow = WindowsNative.getActiveWindow();
		String windowTitle = WindowsNative.getActiveWindowTitle(activeWindow);

		if (windowTitle.contains("% Unnamed") || windowTitle.contains("% / Unnamed")) {

		    if (stock == null || !stock.getSymbol().contains(windowTitle.split(" ")[0])) {
			try {
			    stock = YahooFinance.get(windowTitle.split(" ")[0]);
			} catch (IOException e) {
			    // TODO Auto-generated catch block
			    e.printStackTrace();
			}

			stl.onNewTradingviewWindow(stock); // If want to do more on new tradingview window

		    }
		    
		    // If tradingview window, store it so it can be used later to update price
		    if (windowTitle.contains("% / Unnamed") || windowTitle.contains("% Unnamed")) {
			tradingviewTitle = windowTitle;
		    }
		    
		}
	    };
	};
	t.scheduleAtFixedRate(tt2, 0, time);

    }

    public void closeTasks() {
	if (tt1 != null)
	    tt1.cancel();
	if (tt2 != null)
	    tt2.cancel();
	if (t != null)
	    t.cancel();
    }
}
