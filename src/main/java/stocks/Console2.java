package stocks;

import java.io.IOException;
import java.util.List;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.openqa.selenium.PageLoadStrategy;

import stocks.data.Stock;
import stocks.selenium.ChromeBot;
import stocks.utils.UFilings;
import stocks.utils.ULoggings;
import stocks.selenium.CBAvanza;
import stocks.selenium.CBTradingview;
import stocks.selenium.CBTradingview.Info;
import stocks.system.Hotkey;

public class Console2 {


    static ChromeBot buyBot = new ChromeBot();
    static ChromeBot tradingviewBot = new ChromeBot();

    public static void main( String... args ) throws Exception {


	ULoggings.disableKeyLogging();
	ULoggings.disableSlf4j();

	//----
	//----


	Hotkey h2 = new Hotkey(() -> 
	{
	    buyBot.close();
	    tradingviewBot.close();
	    System.exit(0); // Terminate program
	}
	, NativeKeyEvent.VC_ESCAPE);

	//----

	
	
	System.out.print("Starting Buy bot...");
	buyBot.start(PageLoadStrategy.NORMAL, ChromeBot.USERAGENT, ChromeBot.DEFAULT_WINDOW_SIZE);
	System.out.print("Trying to log in, please start BankID app...");
	CBAvanza.loginToAvanza(buyBot);
	System.out.println("OK!");



	Stock viewingStock = new Stock("AAPL");

	viewingStock.updateSymbol(100, (String newSymbol) -> 
	{
	    CBAvanza.getStockBuyPage(buyBot, newSymbol);
	});


	tradingviewBot.start(PageLoadStrategy.NORMAL, ChromeBot.USERAGENT, ChromeBot.DEFAULT_WINDOW_SIZE);
	CBTradingview.login(
		tradingviewBot
		, UFilings.readFile(System.getProperty("user.dir") + "\\src\\main\\resources\\FileStorage\\" + "Credentials.txt"
			, "tradingviewUsername").get(0)
		, UFilings.readFile(System.getProperty("user.dir") + "\\src\\main\\resources\\FileStorage\\" + "Credentials.txt"
			, "tradingviewPassword").get(0)
		);

	//--------------------------------------------------------------------------------------




	//tradingviewBot.start(ChromeBot.USERAGENT, ChromeBot.DEFAULT_WINDOW_SIZE);


	// WATCHLIST:
	/*
	 * Displays watchlist or colored watchlist
	 * Add stocks from screen to watchlist
	 * Display stats from stocktwits, message count and watchers
	 * Display average move in % per day for the 5,10,20 days
	 * 
	 */


	/* STOCK ANALYSIS - PAST
	 * Check all above 7% in chg% and open%
	 * for each ->
	 * check stocktwits watchers and pressreleases on yahoo maybe, https://seekingalpha.com/symbol/SNDL maybe
	 * Get average gain or move per day on 5,10,20 days, yahoo historical quotes and date
	 * or from high of day to high of next day
	 * or from low of day to high of day, and listing 10 numbers for 10 days
	 * 
	 * 
	 * Modify tradingview price section, to show price and then news only
	 * https://stackoverflow.com/questions/8473024/selenium-can-i-set-any-of-the-attribute-value-of-a-webelement-in-selenium
	 * 
	 * */



	    //-- Click one stock, get all wanted info*, store info, do for all stocks, then organize cmd. 
	    // *complemented with integer parse checking, and - for null

	    // YAHOO
	    // Averages per day close and opening

	    // INDIVIDUALLY
	    // java script  


	    // Display cmd, all in watchlist.
	    // Display individually, using javascript. 
	    // colors, list of names horizontal above in respective color.
	    // get stocks as elements from the watchlist. DONE
	    // sorting the ranking.Collections.sort(testList);
	    // Collections.reverse(testList);
	    // increase in font size. DONE
	    // automation. 
	    // performance. DONE
	    // number of employess. DONE
	    // strange things, like character popularity addition on characters of symbol LATER
	    // vol to shares ratio LATER

	    // javascript to remove for each individual stock and add info, and modify. TEST

	    // Count flashing stock price ticks.
	    // Patterns on value changing. Possible ways(combinatorics and graphs) into statistical,
	    //,, basicly grouping portions of ticks red and green, green only, red only, green to red, red to green, green to one red to green, different patterns,,
	    //,, After that one can introduce another variable like time, to further increase pattern possibility,
	    //,, then find odds and value of that.

	/* Pattern and candles, and javafx, and draw on tradingview
	 * https://stackoverflow.com/questions/55214628/draw-on-a-canvas-using-selenium --- draw on canvas
	 * 
	 * 
	 */
	
    }



}