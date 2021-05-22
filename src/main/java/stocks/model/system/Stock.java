package stocks.model.system;

import java.util.Timer;
import java.util.TimerTask;

import com.sun.jna.platform.win32.WinDef.HWND;

import yahoofinance.quotes.query1v7.StockQuotesQuery1V7Request;

public class Stock {

    static {
	System.setProperty("yahoofinance.baseurl.quotesquery1v7", "https://query1.finance.yahoo.com/v7/finance/quote");

	// Disable slf4j Logging
	System.setProperty(org.slf4j.impl.SimpleLogger.DEFAULT_LOG_LEVEL_KEY, "ERROR");
    }

    public String symbol;
    public double price;

    Timer timerPrice = null;
    TimerTask taskPrice = null;

    Timer timerSymbol = null;
    TimerTask taskSymbol = null;

    public Stock(String symbol) {
	yahoofinance.Stock stock = get(symbol);
	this.symbol = stock.getQuote().getSymbol();
	this.price = stock.getQuote().getPrice().doubleValue();
    }


    public Stock(String symbol, double price) {
	this.symbol = symbol;
	this.price = price;
    }


    public yahoofinance.Stock get(String symbol) {
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

    public void stopUpdatingPrice() {
	if (timerPrice != null) 
	    timerPrice.cancel();
	if (taskPrice != null) 
	    taskPrice.cancel();
    }

    public void stopUpdatingSymbol() {
	if(timerSymbol != null)
	    timerSymbol.cancel();
	if(timerSymbol != null)
	    timerSymbol.cancel();
    }


    // Checks every *ms if price has changed and updates price
    // Checks first for price in title if set to true, then from yahoo
    // Implemented interface is optional, will be called if there is a change in price
    public void updatePrice(int time, boolean tradingviewData, StockStateListener ...ssl) {


	if (timerPrice == null)
	    timerPrice = new Timer();

	if(taskPrice == null) {
	    taskPrice = new TimerTask() {

		@Override
		public void run() {
		    //----------------------------------------------------------
		    try {


			if (symbol != null) {
			    double currentPrice = price;
			    double newPrice = 0;

			    HWND activeWindow = WindowsNative.getActiveWindow();
			    String windowTitle = WindowsNative.getActiveWindowTitle(activeWindow);

			    // Difference of chorme window and desktop tradingview app
			    if (tradingviewData && windowTitle.contains("% Unnamed") && symbol.equalsIgnoreCase(windowTitle.split(" ")[0])) {
				newPrice = Double.parseDouble(windowTitle.split(" ")[1]);
			    }
			    else if (tradingviewData && windowTitle.contains("% / Unnamed") && symbol.equalsIgnoreCase(windowTitle.split(" ")[0])) {
				// else Desktop app, and the"%" is for for it not crashing when switching between stocks
				newPrice = Double.parseDouble(windowTitle.split(" ")[3]);
			    } 
			    else { 
				// Yahoo data
				newPrice = get(symbol).getQuote().getPrice().doubleValue();
			    }


			    // if new price
			    if (currentPrice != newPrice && ssl != null) {


				price = newPrice;

				for(StockStateListener ssl : ssl)
				ssl.onNewPrice(newPrice); 

			    }

			}

		    }catch(Exception e){
			return;
		    }


		    //----------------------------------------------------------
		}
	    };
	    timerPrice.scheduleAtFixedRate(taskPrice, 0, time);
	}

    }



    // Checks every *ms if viewing a different tradingview window and if so it sets a new stock symbol
    // Implemented interface is optional, will be called if there is a change to another symbol
    public void updateSymbol(int time, StockStateListener ...ssl) {

	if (timerSymbol == null)
	    timerSymbol = new Timer();

	if(taskSymbol == null) {
	    taskSymbol = new TimerTask() {
		@Override
		public void run() {
		    //----------------------------------------------------------
		    try {
			HWND activeWindow = WindowsNative.getActiveWindow();
			String windowTitle = WindowsNative.getActiveWindowTitle(activeWindow);



			if (windowTitle.contains("% Unnamed") || windowTitle.contains("% / Unnamed")) {

			    if (!symbol.equalsIgnoreCase(windowTitle.split(" ")[0])) {
				String newSymbol = windowTitle.split(" ")[0];


				symbol = newSymbol;

				for(StockStateListener ssl : ssl)
				    ssl.onNewSymbol(newSymbol); 


			    }


			}

		    }catch(Exception e) {
			return;
		    }
		    //----------------------------------------------------------
		};
	    };
	    timerSymbol.scheduleAtFixedRate(taskSymbol, 0, time);

	}
    }

}
