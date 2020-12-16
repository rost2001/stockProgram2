package stocks;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

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

    // Checks every *ms if price has changed and updates price
    public void updatePrice(int time) {

	if (t == null)
	    t = new Timer();

	tt1 = new TimerTask() {
	    @Override
	    public void run() {

		if (stock != null) {
		    double currentPrice = stock.getQuote().getPrice().doubleValue();
		    // getQuote(stock);
		    try {
			stock.getQuote(true);
		    } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		    }
		    if (currentPrice != stock.getQuote().getPrice().doubleValue())

			stl.onNewPrice(stock); // if want to do more on price update
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

		String windowTitle = WindowsNative.getActiveWindowTitle();

		if (windowTitle.contains("% Unnamed") && windowTitle.contains("Chrome")) {

		    if (stock == null || !stock.getSymbol().contains(windowTitle.split(" ")[0])) {
			try {
			    stock = YahooFinance.get(windowTitle.split(" ")[0]);
			} catch (IOException e) {
			    // TODO Auto-generated catch block
			    e.printStackTrace();
			}

			stl.onNewTradingviewWindow(stock); // If want to do more on new tradingview window

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
