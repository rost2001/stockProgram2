package stocks.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jnativehook.keyboard.NativeKeyEvent;
import org.openqa.selenium.Keys;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebElement;

import stocks.model.selenium.CBAvanza;
import stocks.model.selenium.CBStocktwits;
import stocks.model.selenium.CBTradingview;
import stocks.model.selenium.CBTradingview.Info;
import stocks.model.selenium.ChromeBot;
import stocks.model.system.Hotkey;
import stocks.model.system.Stock;
import stocks.model.utilities.UFilings;
import stocks.model.utilities.ULoggings;

public class Test2 {



    static ChromeBot tradingviewBot = new ChromeBot();
    static ChromeBot stocktwitsBot = new ChromeBot();
    static ChromeBot buyBot = new ChromeBot();


    static List<WebElement> el = new ArrayList<WebElement>();


    static StringBuffer currentSymbol = new StringBuffer("Hello");

    public static void main(String[] args) throws IOException, Exception {

	ULoggings.disableKeyLogging();
	ULoggings.disableSlf4j();

	//----
	//----

	Hotkey h2 = new Hotkey(() -> 
	{
	    tradingviewBot.close();
	    stocktwitsBot.close();
	    buyBot.close();
	    System.exit(0); // Terminate program
	}
	, NativeKeyEvent.VC_F4);

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



/*

	el = CBTradingview.getWatchList(tradingviewBot);

	Map<Info, String> info = null;
	List<Map<Info, String>> stocksInfo = new ArrayList<Map<Info, String>>();

	for (WebElement element : el) {
	    element.click();

	    Thread.sleep(500);
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

*/

	buyBot.start(PageLoadStrategy.NORMAL, ChromeBot.USERAGENT, ChromeBot.DEFAULT_WINDOW_SIZE);
	CBAvanza.loginToAvanza(buyBot);


	Stock.checkSymbol(1000, currentSymbol, (String newSymbol) -> 
	{
	    currentSymbol.delete(0, currentSymbol.length());
	    currentSymbol.append(newSymbol);
	    buyBot.driver.get("https://www.avanza.se/ab/sok/inline?query=" + newSymbol);


	    try {
		el = buyBot.findElements("//a");
	    } catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	    el.get(1).sendKeys(Keys.ENTER);
	});


    }
}