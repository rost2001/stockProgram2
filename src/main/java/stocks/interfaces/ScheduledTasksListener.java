package stocks.interfaces;

import yahoofinance.Stock;

public interface ScheduledTasksListener {
	
	/*
	 * Interface for extending the code on when something happens like a new price update or new tradingview window or something else
	 * The class that is to extend the code, is to implement the interface and to the methods add whatever code one wish to have executed on an event
	 * This way multiple classes or implementations can use the same code or classes, like a console implementation and a gui implementation
	 */
	
	
	
	void onNewPrice(Stock stock);
	
	void onNewTradingviewWindow(Stock stock);

}
