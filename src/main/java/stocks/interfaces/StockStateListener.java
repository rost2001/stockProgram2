package stocks.interfaces;

import stocks.Stock;

public interface StockStateListener {
	
	/*
	 * 
	 * 
	 * Implement todo something on an event like a on a new price
	 */
	
	
	

	void onNewPrice(double price);

	void onNewSymbol(String symbol);

}
