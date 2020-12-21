package stocks;

import java.io.IOException;
import java.util.TimerTask;

import stocks.interfaces.StockStateListener;
import yahoofinance.quotes.query1v7.StockQuotesQuery1V7Request;

public class Stock {
    
    static {
	System.setProperty("yahoofinance.baseurl.quotesquery1v7", "https://query1.finance.yahoo.com/v7/finance/quote");

	// Disable slf4j Logging
	System.setProperty(org.slf4j.impl.SimpleLogger.DEFAULT_LOG_LEVEL_KEY, "ERROR");
    }

    public String symbol;
    public double price;
    
    TimerTask updater;

    public Stock(String symbol) {
	get(symbol);
    }
    
    
    public Stock(String symbol, double price) {
	this.symbol = symbol;
	this.price = price;
    }
    
    

    public void get(String symbol) {
	try {


	    // Sends request to Yahoo
	    StockQuotesQuery1V7Request request = new StockQuotesQuery1V7Request(symbol);
	    yahoofinance.Stock stock  = request.getSingleResult();


	    // sets info
	    if (stock != null) {
		this.symbol = stock.getQuote().getSymbol();
		this.price = stock.getQuote().getPrice().doubleValue();
	    } else {
		System.err.println("Could not retrieve stock data from Yahoo");
	    }

	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }
    
    
    public void updatePrice (StockStateListener ssl, int time, boolean tradingviewData) {
	StateUpdater.updatePrice(ssl, time, this, tradingviewData);
    }
    
    public void updateSymbol(StockStateListener ssl, int time) {
	StateUpdater.updateSymbol(ssl, time, this);
    }
    
}
