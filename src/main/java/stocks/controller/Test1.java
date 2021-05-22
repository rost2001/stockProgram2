package stocks.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jnativehook.keyboard.NativeKeyEvent;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebElement;

import stocks.model.selenium.CBStocktwits;
import stocks.model.selenium.CBTradingview;
import stocks.model.selenium.ChromeBot;
import stocks.model.selenium.CBTradingview.Info;
import stocks.model.system.Hotkey;
import stocks.model.utils.UFilings;
import stocks.model.utils.ULoggings;

public class Test1 {



    static ChromeBot tradingviewBot = new ChromeBot();
    static ChromeBot stocktwitsBot = new ChromeBot();

    public static void main(String[] args) throws IOException, Exception {

	ULoggings.disableKeyLogging();
	ULoggings.disableSlf4j();

	//----
	//----

	Hotkey h2 = new Hotkey(() -> 
	{
	    tradingviewBot.close();
	    stocktwitsBot.close();
	    System.exit(0); // Terminate program
	}
	, NativeKeyEvent.VC_F4);
	
	
	new Hotkey(() -> 
	{
	    System.out.println("Hello");
	}
	, NativeKeyEvent.VC_F3);

	//----


	stocktwitsBot.start(PageLoadStrategy.NONE, ChromeBot.USERAGENT, ChromeBot.DEFAULT_WINDOW_SIZE);



	tradingviewBot.start(PageLoadStrategy.EAGER, ChromeBot.USERAGENT, ChromeBot.DEFAULT_WINDOW_SIZE);
	tradingviewBot.maximizeWindow();
	CBTradingview.login(
		tradingviewBot
		, UFilings.readFile(System.getProperty("user.dir") + "\\src\\main\\resources\\FileStorage\\" + "Credentials.txt"
			, "tradingviewUsername").get(0)
		, UFilings.readFile(System.getProperty("user.dir") + "\\src\\main\\resources\\FileStorage\\" + "Credentials.txt"
			, "tradingviewPassword").get(0)
		);


	List<WebElement> el = new ArrayList<WebElement>();
	el = CBTradingview.getWatchList(tradingviewBot);





	Map<Info, String> info = null;
	List<Map<Info, String>> stocksInfo = new ArrayList<Map<Info, String>>();

	for (WebElement element : el) {
	    element.click();

	    Thread.sleep(100);
	    info = CBTradingview.getStockInfo
		    (
			    tradingviewBot,
			    Info.NAME,
			    Info.PROCENTAGE, 
			    Info.LAST, 
			    Info.VOL, 
			    Info.SHARES, 
			    Info.MKT, 
			    Info.EMPLOYEES
			    );

	    stocksInfo.add(info);
	}


	List<String> watchers = new ArrayList<String>();


	for(Map<Info, String> information : stocksInfo) {
	    for (Map.Entry<Info, String> entry : information.entrySet()) {

		if(entry.getKey().name().equals("NAME")) {
		    watchers.add(CBStocktwits.getWatchers(stocktwitsBot, entry.getValue()));
		    System.out.println("Watchers: " + watchers.get(watchers.size()-1));
		}

		System.out.print(entry.getKey().name() + ": ");
		System.out.println(entry.getValue());
	    }
	    System.out.print("\n---\n\n");
	}

    }


/* Print.
 * Calculate.
 * Sort.
 * Coloring.
 * PointSystem.
 * 
 * ** Adapt points, after winning and losing stocks, as they turn out, high/low points supposedly.
 * 
 * * get watchlist info.
 */

	 
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


    static void printInfo() {
	try {
	    new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
	} catch (InterruptedException | IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

	String out = "";

	//

	System.out.println(out);
    }



}
