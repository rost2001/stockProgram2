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
	

	private Stock stock;
	ScheduledTasksListener stl;
	
	public StockInfo(ScheduledTasksListener stl, String symbol) throws IOException{
		this.stl = stl;
		stock =  YahooFinance.get(symbol);
	}
	
	StockInfo(ScheduledTasksListener stl){
		this.stl = stl;
		stock = null;
	}
	
	public Stock getStock() {
		return stock;
	}
	
	
	// Checks every *ms if price has changed and updates price
	public void updatePrice(int time) {
		

        Timer t = new Timer();
		TimerTask tt = new TimerTask() {
			@Override
			public void run() {
				
            		if (stock != null) {
            			double currentPrice = stock.getQuote().getPrice().doubleValue();
					//getQuote(stock);
					try {
						stock.getQuote(true);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if(currentPrice != stock.getQuote().getPrice().doubleValue())
						
						stl.onNewPrice(stock); 			// if want to do more on price update
            		}

            };  
        };  
        t.scheduleAtFixedRate(tt,0,time); 
		
	}
	
	// Check every *ms if viewing a different tradingview window and if so it sets the new stock
	public void updateStockWindow(int time) {


		Timer t = new Timer();
		TimerTask tt = new TimerTask() {
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

						stl.onNewTradingviewWindow(stock); 			// If want to do more on new tradingview window
						
					}
				}
			};
		};
		t.scheduleAtFixedRate(tt, 0, time);

	}
	
}
