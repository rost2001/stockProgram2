package stocks;

import stocks.selenium.CBTradingview;
import stocks.selenium.CBTradingview.Column;
import stocks.selenium.ChromeBot;

public class PeriodicStockRetrieval {

    public static void main(String[] args) throws Exception {
	
	/* Jar file, starts with computer, runs 1 time per day, at certain time.
	 * ---------------------------------------------------------------------
	 * *List of stock names. From anywhere. 
	 * Mainly from tradingview within certain intervals on some categories.
	 * 
	 * 
	 * *Retrieves data from yahoo using list of names
	 * 
	 * 
	 * *Inserts data into database
	 * 
	 * 
	 * 
	 */
	
	
	
	ChromeBot bot = new ChromeBot();
	bot.start(ChromeBot.USERAGENT);
	bot.maximize();
	
	CBTradingview.login(bot, "fredrik.jonsson.s@hotmail.com", "Uctrickme12");
	
	CBTradingview.getStockScreenerList(bot, Column.getState(Column.BY_CHG, 10, 20));
	
	
	
	
	Thread.sleep(10000);
	bot.close();
    }

}
